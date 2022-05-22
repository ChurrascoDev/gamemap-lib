package com.github.imthenico.gmlib;

import com.github.imthenico.eventbus.EventBus;
import com.github.imthenico.eventbus.result.PublishResult;
import com.github.imthenico.gmlib.event.GameMapCreationEvent;
import com.github.imthenico.gmlib.event.MapModelSerializationEvent;
import com.github.imthenico.gmlib.event.MapModelDeserializationEvent;
import com.github.imthenico.gmlib.event.ModelEvent;
import com.github.imthenico.gmlib.exception.MapCreationException;
import com.github.imthenico.gmlib.exception.NoDataManipulatorFoundException;
import com.github.imthenico.gmlib.exception.NoWorldFoundException;
import com.github.imthenico.gmlib.handler.DataDeserializer;
import com.github.imthenico.gmlib.handler.HandlerRegistry;
import com.github.imthenico.gmlib.handler.DataSerializer;
import com.github.imthenico.gmlib.pool.TemplatePool;
import com.github.imthenico.gmlib.world.*;
import com.google.gson.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;

public class GameMapHandlerImpl implements GameMapHandler {

    private final EventBus<ModelEvent> eventBus = EventBus.create(ModelEvent.class);
    private final TemplatePool templatePool;
    private final HandlerRegistry handlerRegistry;
    private final Gson gson;

    GameMapHandlerImpl(
            TemplatePool templatePool,
            HandlerRegistry handlerRegistry,
            Gson gson
    ) {
        this.templatePool = templatePool;
        this.handlerRegistry = handlerRegistry;
        this.gson = gson;
    }

    @Override
    public @NotNull HandlerRegistry getDataManipulation() {
        return handlerRegistry;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <D extends ModelData> @NotNull MapModel<D> fromJson(
            @NotNull JsonObject jsonObject,
            @NotNull Class<D> dataClass,
            @Nullable Consumer<WorldRequest> requestConsumer
    ) throws NoWorldFoundException, NoDataManipulatorFoundException {
        DataDeserializer<D> dataDeserializer = getDataManipulation().getDeserializer(dataClass);

        if (dataDeserializer == null)
            throw new NoDataManipulatorFoundException("No deserializer found for " + dataClass);

        String name = getElement(jsonObject, "name")
                .getAsString();

        D data = dataDeserializer.deserializeData(jsonObject);

        String worldName = getElement(jsonObject, "world")
                .getAsString();

        List<String> additionalWorlds = gson.fromJson(getElement(jsonObject, "additional-worlds"), List.class);

        WorldRequest worldRequest = WorldRequest.of(worldName);

        if (requestConsumer == null)
            requestConsumer = request -> {};

        requestConsumer.accept(worldRequest);

        TemplateWorld world = templatePool.getOrLoad(worldRequest);

        WorldContainer<TemplateWorld> additionalWorldsContainer = new WorldContainer<>();

        for (String additionalWorldName : additionalWorlds) {
            WorldRequest request = WorldRequest.of(additionalWorldName);
            requestConsumer.accept(request);

            TemplateWorld additionalWorld = templatePool.getOrLoad(request);
            additionalWorldsContainer.add(additionalWorld);
        }

        MapModel<D> mapModel = MapModel.of(name, data, world, additionalWorldsContainer);

        MapModelDeserializationEvent event = new MapModelDeserializationEvent(mapModel);
        PublishResult publishResult = eventBus.dispatch(event);
        handlePublishResult(publishResult);

        return mapModel;
    }

    @Override
    @SuppressWarnings("rawtypes, unchecked")
    public @NotNull Serialized serialize(@NotNull MapModel<?> model) throws NoDataManipulatorFoundException {
        DataSerializer dataSerializer = getDataManipulation().getSerializer(model.getDataType());

        if (dataSerializer == null)
            throw new NoDataManipulatorFoundException("No serializer found for " + model.getDataType());

        JsonObject jsonObject = dataSerializer.serializeData(model.getData());
        jsonObject.add("name", new JsonPrimitive(model.getName()));
        jsonObject.add("world", new JsonPrimitive(model.getMainWorld().getTemplateName()));
        JsonArray additionalWorlds = new JsonArray();

        model.getAdditionalWorlds().forEach(aWorld -> additionalWorlds.add(new JsonPrimitive(aWorld.getTemplateName())));

        jsonObject.add("additional-worlds", additionalWorlds);

        String jsonText = gson.toJson(jsonObject);

        MapModelSerializationEvent event = new MapModelSerializationEvent(model, jsonObject);
        PublishResult publishResult = eventBus.dispatch(event);
        handlePublishResult(publishResult);

        return new Serialized(jsonObject, jsonText);
    }

    @Override
    public @NotNull EventBus<ModelEvent> getEventBus() {
        return eventBus;
    }

    @Override
    public <D extends ModelData, T extends GameMap> T createMap(@NotNull MapModel<D> model, @NotNull Class<T> mapClass, @NotNull String mapName) {
        D modelData = model.getData();

        CustomMapFactory<D, T> mapFactory = getDataManipulation().getMapFactory(model.getDataType(), mapClass);

        if (mapFactory == null)
            throw new NoDataManipulatorFoundException("No map factory found for (data=" + model.getDataType() + ", target=" + mapClass + ")");

        WorldContainer<AWorld> additionalWorlds = new WorldContainer<>();
        AWorld mainWorld = null;

        try {
            mainWorld = clone(mapName, model.getMainWorld());

            for (TemplateWorld world : model.getAdditionalWorlds()) {
                AWorld clone = clone(mapName, world);
                additionalWorlds.add(clone);
            }

            T createdMap = mapFactory.createMap(
                    mainWorld,
                    additionalWorlds,
                    mapName,
                    modelData,
                    model.newMetadataSnapshot()
            );

            if (createdMap == null)
                throw new MapCreationException("Unable to create map");

            createdMap.init(mainWorld, additionalWorlds, mapName, modelData);

            PublishResult publishResult = eventBus.dispatch(new GameMapCreationEvent(model, createdMap));
            handlePublishResult(publishResult);

            return createdMap;
        } catch (Exception e) {
            if (mainWorld != null) mainWorld.unload();
            additionalWorlds.forEach(AWorld::unload);
            throw new MapCreationException(e);
        }
    }

    private void handlePublishResult(PublishResult publishResult) {
        if (!publishResult.isSuccess()) {
            throw new RuntimeException("An unexpected error has occurred while event dispatching", publishResult.getError());
        }
    }

    private AWorld clone(String mapName, TemplateWorld toClone) {
        try {
            return toClone.clone(mapName + "_" + toClone.getTemplateName()).call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private JsonElement getElement(JsonObject jsonObject, String name) {
        JsonElement jsonElement = jsonObject.get("name");

        if (jsonElement == null)
            throw new IllegalStateException("Missing element: name");

        return jsonElement;
    }
}
