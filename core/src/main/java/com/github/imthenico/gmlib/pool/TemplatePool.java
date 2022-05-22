package com.github.imthenico.gmlib.pool;

import com.github.imthenico.gmlib.exception.NoWorldFoundException;
import com.github.imthenico.gmlib.loader.WorldLoader;
import com.github.imthenico.gmlib.world.TemplateWorld;
import com.github.imthenico.gmlib.world.WorldRequest;
import com.github.imthenico.gmlib.world.AWorld;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static java.util.Objects.requireNonNull;

/**
 * Charge of provide, store and load {@link TemplateWorld}.
 *
 * You can load and store worlds in sync or async mode with total security.
 */
public interface TemplatePool {

    @Nullable TemplateWorld get(String name);

    @NotNull Map<String, AWorld> cached();

    @NotNull TemplateWorld getOrLoad(WorldRequest request) throws NoWorldFoundException;

    @NotNull CompletableFuture<TemplateWorld> getOrLoadAsync(WorldRequest request);

    @NotNull WorldLoader getLoader();

    void add(@NotNull TemplateWorld template);

    boolean isCached(@NotNull String name);

    static @NotNull TemplatePool create(@NotNull WorldLoader worldLoader) {
        return new TemplatePoolImpl(
                Executors.newCachedThreadPool(),
                requireNonNull(worldLoader, "templateLoader")
        );
    }

    static @NotNull TemplatePool create(@NotNull Executor executor, @NotNull WorldLoader worldLoader) {
        return new TemplatePoolImpl(
                requireNonNull(executor, "executor"),
                requireNonNull(worldLoader, "templateLoader")
        );
    }
}