package com.github.imthenico.gmlib.world;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface AWorld {

    @NotNull String getTemplateName();

    @NotNull String getHandleName();

    @NotNull UUID getUUID();

    @NotNull Object getHandle();

    <T> @NotNull T handle();

    void save();

    void unload();

}