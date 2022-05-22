package com.github.imthenico.gamemaplib.test.map;

import com.github.imthenico.gamemaplib.test.util.LocationModel;
import com.github.imthenico.gmlib.ModelData;

import java.util.Objects;

public class ArenaModelData extends ModelData {

    private LocationModel firstSpawn;
    private LocationModel secondSpawn;

    public LocationModel getFirstSpawn() {
        return firstSpawn;
    }

    public void setFirstSpawn(LocationModel firstSpawn) {
        this.firstSpawn = Objects.requireNonNull(firstSpawn);
    }

    public LocationModel getSecondSpawn() {
        return secondSpawn;
    }

    public void setSecondSpawn(LocationModel secondSpawn) {
        this.secondSpawn = Objects.requireNonNull(secondSpawn);
    }
}