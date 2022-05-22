package com.github.imthenico.gmlib.pool;

import com.github.imthenico.gmlib.concurrent.ExceptionHandler;
import com.github.imthenico.gmlib.exception.NoWorldFoundException;
import com.github.imthenico.gmlib.loader.WorldLoader;
import com.github.imthenico.gmlib.world.TemplateWorld;
import com.github.imthenico.gmlib.world.WorldRequest;
import com.github.imthenico.gmlib.world.AWorld;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

public class TemplatePoolImpl implements TemplatePool {

    private final Executor executor;
    private final Map<String, TemplateWorld> templateMap = new ConcurrentHashMap<>();
    private final WorldLoader loader;

    TemplatePoolImpl(Executor executor, WorldLoader loader) {
        this.executor = executor;
        this.loader = loader;
    }

    @Override
    public @Nullable TemplateWorld get(String name) {
        return templateMap.get(name);
    }

    @Override
    public @NotNull Map<String, AWorld> cached() {
        return new LinkedHashMap<>(templateMap);
    }

    // Check if the key is introduced on the mapStructure when no source is found
    @Override
    public @NotNull TemplateWorld getOrLoad(WorldRequest request) throws NoWorldFoundException {
        TemplateWorld template = templateMap.get(request.getName());

        if (template == null) {
            template = loader.load(request);

            templateMap.put(request.getName(), template);
        }

        return template;
    }

    @Override
    public @NotNull CompletableFuture<TemplateWorld> getOrLoadAsync(WorldRequest request) {
        CompletableFuture<TemplateWorld> future;

        TemplateWorld found = get(request.getName());

        if (found != null) {
            future = CompletableFuture.completedFuture(found);
        } else {
            future = CompletableFuture.supplyAsync(
                    () -> {
                        try {
                            return loader.load(request);
                        } catch (NoWorldFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }, executor)
                    .exceptionally(ExceptionHandler.create())
                    .whenComplete((aWorld, throwable) -> templateMap.put(aWorld.getHandleName(), aWorld));
        }

        return future;
    }

    @Override
    public @NotNull WorldLoader getLoader() {
        return loader;
    }

    @Override
    public void add(@NotNull TemplateWorld world) {
        if (templateMap.containsKey(world.getHandleName()))
            return;

        templateMap.put(world.getHandleName(), world);
    }

    @Override
    public boolean isCached(@NotNull String name) {
        return templateMap.containsKey(name);
    }
}