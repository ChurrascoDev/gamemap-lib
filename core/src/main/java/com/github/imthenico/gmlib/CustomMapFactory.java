package com.github.imthenico.gmlib;

import com.github.imthenico.gmlib.metadata.MetadataSnapshot;
import com.github.imthenico.gmlib.world.AWorld;
import com.github.imthenico.gmlib.world.WorldContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface CustomMapFactory<D extends ModelData, T extends GameMap> {

    @Nullable T createMap(
            @NotNull AWorld mainWorld,
            @NotNull WorldContainer<AWorld> additionalWorlds,
            @NotNull String mapName,
            @NotNull D modelData,
            @NotNull MetadataSnapshot metadataSnapshot
    );
}