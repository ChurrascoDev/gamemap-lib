package com.github.imthenico.gamemaplib.test.map;

import com.github.imthenico.gmlib.GameMap;
import org.bukkit.Location;

import static java.util.Objects.requireNonNull;

public class Arena extends GameMap {

    private final Location firstSpawn;
    private final Location secondSpawn;

    protected Arena(Location firstSpawn, Location secondSpawn) {
        this.firstSpawn = requireNonNull(firstSpawn);
        this.secondSpawn = requireNonNull(secondSpawn);
    }

    public Location getFirstSpawn() {
        return firstSpawn;
    }

    public Location getSecondSpawn() {
        return secondSpawn;
    }
}