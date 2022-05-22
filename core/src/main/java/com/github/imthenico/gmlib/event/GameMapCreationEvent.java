package com.github.imthenico.gmlib.event;

import com.github.imthenico.gmlib.GameMap;
import com.github.imthenico.gmlib.MapModel;

public class GameMapCreationEvent extends ModelEvent {

    private final GameMap gameMap;

    public GameMapCreationEvent(MapModel<?> mapModel, GameMap gameMap) {
        super(mapModel);
        this.gameMap = gameMap;
    }

    public GameMap getGameMap() {
        return gameMap;
    }
}