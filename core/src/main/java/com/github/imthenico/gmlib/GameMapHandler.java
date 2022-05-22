package com.github.imthenico.gmlib;

import com.github.imthenico.gmlib.handler.HandlerRegistry;
import com.github.imthenico.gmlib.pool.TemplatePool;
import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

import static java.util.Objects.*;

/**
 * Charge of serialize and deserialize models and handle map creations.
 */
public interface GameMapHandler extends ModelHandler, ModelConverter {

    @NotNull HandlerRegistry getDataManipulation();

    static @NotNull GameMapHandler create(
            TemplatePool templatePool,
            HandlerRegistry handlerRegistry,
            Gson gson
    ) {
        return new GameMapHandlerImpl(
                requireNonNull(templatePool),
                requireNonNull(handlerRegistry),
                requireNonNull(gson)
        );
    }
}