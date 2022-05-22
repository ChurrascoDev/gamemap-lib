package com.github.imthenico.gmlib.world;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public abstract class AbstractAWorld implements AWorld {

    private final String name;
    private final String persistentName;
    private final UUID uuid;
    private final Object handle;

    protected AbstractAWorld(String name, String persistentName, UUID uuid, Object handle) {
        this.name = name;
        this.persistentName = persistentName;
        this.uuid = uuid;
        this.handle = handle;
    }

    @Override
    public @NotNull String getTemplateName() {
        return persistentName;
    }

    @Override
    public @NotNull String getHandleName() {
        return name;
    }

    @Override
    public @NotNull UUID getUUID() {
        return uuid;
    }

    @Override
    public @NotNull Object getHandle() {
        return handle;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> @NotNull T handle() {
        return (T) handle;
    }
}