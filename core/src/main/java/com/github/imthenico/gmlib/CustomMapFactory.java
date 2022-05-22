package com.github.imthenico.gmlib;

import com.github.imthenico.gmlib.metadata.MetadataSnapshot;
import com.github.imthenico.gmlib.world.AWorld;
import com.github.imthenico.gmlib.world.WorldContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface CustomMapFactory<D extends ModelData, T extends GameMap> {

    /**
     * @param mainWorld - The main world
     * @param additionalWorlds - The complementary worlds
     * @param mapName - The map name
     * @param modelData - The model data
     * @param metadataSnapshot - The metadata snapshot of the model used to create the map
     * @return A new created map.
     */
    @Nullable T createMap(
            @NotNull AWorld mainWorld,
            @NotNull WorldContainer<AWorld> additionalWorlds,
            @NotNull String mapName,
            @NotNull D modelData,
            @NotNull MetadataSnapshot metadataSnapshot
    );
}