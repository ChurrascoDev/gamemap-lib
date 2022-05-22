package com.github.imthenico.gmlib;

import com.github.imthenico.eventbus.EventBus;
import com.github.imthenico.gmlib.event.ModelEvent;
import com.github.imthenico.gmlib.exception.NoDataManipulatorFoundException;
import com.github.imthenico.gmlib.exception.NoWorldFoundException;
import com.github.imthenico.gmlib.world.WorldRequest;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public interface ModelHandler {

    <D extends ModelData> @NotNull MapModel<D> fromJson(
            @NotNull JsonObject jsonObject,
            @NotNull Class<D> dataClass,
            @Nullable Consumer<WorldRequest> requestConsumer
    ) throws NoWorldFoundException, NoDataManipulatorFoundException;

    @NotNull Serialized serialize(@NotNull MapModel<?> model) throws NoDataManipulatorFoundException;

    @NotNull EventBus<ModelEvent> getEventBus();

}