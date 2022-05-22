package com.github.imthenico.gamemaplib.test.map;

import com.github.imthenico.gmlib.CustomMapFactory;
import com.github.imthenico.gmlib.metadata.MetadataSnapshot;
import com.github.imthenico.gmlib.world.AWorld;
import com.github.imthenico.gmlib.world.WorldContainer;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ArenaMapFactory implements CustomMapFactory<ArenaModelData, Arena> {

    @Override
    public @Nullable Arena createMap(
            @NotNull AWorld mainWorld,
            @NotNull WorldContainer<AWorld> additionalWorlds,
            @NotNull String mapName,
            @NotNull ArenaModelData modelData,
            @NotNull MetadataSnapshot metadataSnapshot
    ) {
        WorldContainer<AWorld> allWorlds = new WorldContainer<>();
        allWorlds.add(mainWorld);
        allWorlds.addAll(additionalWorlds);

        Location firstSpawn = modelData.getFirstSpawn()
                .toBukkit(allWorlds);
        Location secondSpawn = modelData.getSecondSpawn()
                .toBukkit(allWorlds);

        return new Arena(firstSpawn, secondSpawn);
    }
}