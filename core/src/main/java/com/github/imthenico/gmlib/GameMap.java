package com.github.imthenico.gmlib;

import com.github.imthenico.gmlib.metadata.SimpleMetadataHolder;
import com.github.imthenico.gmlib.world.AWorld;
import com.github.imthenico.gmlib.world.WorldContainer;
import com.github.imthenico.gmlib.world.WorldHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GameMap extends SimpleMetadataHolder implements WorldHolder<AWorld> {

    private final WorldContainer<AWorld> additionalWorlds = new WorldContainer<>();
    private AWorld mainWorld;
    private String mapName;
    protected ModelData modelData;

    @Override
    public final @Nullable AWorld getMainWorld() {
        return mainWorld;
    }

    @Override
    public final void setMainWorld(@NotNull AWorld world) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final @NotNull WorldContainer<AWorld> getAdditionalWorlds() {
        return new WorldContainer<>(additionalWorlds);
    }

    @Override
    public final WorldContainer<AWorld> allWorlds() {
        return WorldHolder.super.allWorlds();
    }

    public final String getMapName() {
        return mapName;
    }

    void init(
            AWorld mainWorld,
            WorldContainer<AWorld> additionalWorlds,
            String mapName,
            ModelData modelData
    ) {
        this.mainWorld = mainWorld;
        this.additionalWorlds.addAll(additionalWorlds);
        this.mapName = mapName;
        this.modelData = modelData;
    }
}