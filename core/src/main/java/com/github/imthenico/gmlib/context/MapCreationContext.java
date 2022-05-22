package com.github.imthenico.gmlib.context;

import com.github.imthenico.gmlib.ModelData;
import com.github.imthenico.gmlib.world.AWorld;
import com.github.imthenico.gmlib.world.WorldContainer;

public class MapCreationContext {

    private final ModelData modelData;
    private final AWorld mainWorld;
    private final WorldContainer<AWorld> additionalWorlds;
    private final String mapName;

    public MapCreationContext(ModelData modelData, AWorld mainWorld, WorldContainer<AWorld> additionalWorlds, String mapName) {
        this.modelData = modelData;
        this.mainWorld = mainWorld;
        this.additionalWorlds = additionalWorlds;
        this.mapName = mapName;
    }

    public ModelData getModelData() {
        return modelData;
    }

    public AWorld getMainWorld() {
        return mainWorld;
    }

    public WorldContainer<AWorld> getAdditionalWorlds() {
        return additionalWorlds;
    }

    public String getMapName() {
        return mapName;
    }

    public WorldContainer<AWorld> getAllWorlds() {
        WorldContainer<AWorld> container = new WorldContainer<>();

        container.add(mainWorld);
        container.addAll(additionalWorlds);

        return container;
    }
}