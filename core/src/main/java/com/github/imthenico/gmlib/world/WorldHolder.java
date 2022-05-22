package com.github.imthenico.gmlib.world;

import org.jetbrains.annotations.NotNull;

public interface WorldHolder<T extends AWorld> {

    T getMainWorld();

    void setMainWorld(@NotNull T world);

    WorldContainer<T> getAdditionalWorlds();

    default WorldContainer<T> allWorlds() {
        WorldContainer<T> worldContainer = new WorldContainer<>();

        worldContainer.add(getMainWorld());
        getAdditionalWorlds().forEach(worldContainer::add);

        return worldContainer;
    }
}