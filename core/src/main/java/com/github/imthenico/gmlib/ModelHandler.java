package com.github.imthenico.gmlib;

import com.github.imthenico.eventbus.EventBus;
import com.github.imthenico.gmlib.event.ModelEvent;
import com.github.imthenico.gmlib.exception.NoDataManipulatorFoundException;
import com.github.imthenico.gmlib.exception.NoWorldFoundException;
import com.github.imthenico.gmlib.world.WorldRequest;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public interface ModelHandler {

    /**
     * Load a model from json.
     *
     * @param jsonObject - The model json data
     * @param dataClass - The model data class
     * @param requestConsumer - The request configurator
     * @return A new {@link MapModel}
     * @param <D> - The model data type
     * @throws NoWorldFoundException If any world specified in the jsonData is not found
     * @throws NoDataManipulatorFoundException If no {@link com.github.imthenico.gmlib.handler.DataDeserializer}
     *                                         found to deserialize the model data.
     */
    <D extends ModelData> @NotNull MapModel<D> fromJson(
            @NotNull JsonObject jsonObject,
            @NotNull Class<D> dataClass,
            @Nullable Consumer<WorldRequest> requestConsumer
    ) throws NoWorldFoundException, NoDataManipulatorFoundException;

    /**
     * Serialize a model in json.
     *
     * @param model - The model to serialize
     * @return A {@link Serialized} that contains the serialized model data
     * @throws NoDataManipulatorFoundException If no {@link com.github.imthenico.gmlib.handler.DataSerializer}
     *                                         found to serialize the model data.
     */
    @NotNull Serialized serialize(@NotNull MapModel<?> model) throws NoDataManipulatorFoundException;

    /**
     * @return The event bus that manages the internal events
     */
    @NotNull EventBus<ModelEvent> getEventBus();

}