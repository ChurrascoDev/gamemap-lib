package com.github.imthenico.gmlib.world;

import com.github.imthenico.gmlib.pool.TemplatePool;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class TemplatePack {

    private final TemplateWorld mainWorld;
    private final WorldContainer<TemplateWorld> additionalWorlds;

    public TemplatePack(TemplateWorld mainWorld, WorldContainer<TemplateWorld> additionalWorlds) {
        this.mainWorld = Objects.requireNonNull(mainWorld);
        this.additionalWorlds = Objects.requireNonNull(additionalWorlds);
    }

    public @NotNull TemplateWorld mainWorld() {
        return mainWorld;
    }

    public @NotNull WorldContainer<TemplateWorld> additionalWorlds() {
        return additionalWorlds;
    }

    public void install(@NotNull WorldHolder<TemplateWorld> worldHolder) {
        worldHolder.setMainWorld(mainWorld());

        additionalWorlds().forEach(worldHolder.getAdditionalWorlds()::add);
    }

    public static CompletableFuture<TemplatePack> fromPool(
            TemplatePool templatePool,
            String mainWorldName,
            List<String> additionalWorldsNames,
            Consumer<Map<String, Object>> dataConsumer
    ) {
        CompletableFuture<TemplatePack> completableFuture = new CompletableFuture<>();

        List<String> worldsToLoad = new ArrayList<>();
        worldsToLoad.add(mainWorldName);
        worldsToLoad.addAll(additionalWorldsNames);

        loadAll(templatePool, dataConsumer, worldsToLoad)
                .whenComplete((worldMap, throwable) -> {
                    WorldContainer<TemplateWorld> additionalWorlds = new WorldContainer<>();

                    for (String additionalWorldsName : additionalWorldsNames) {
                        additionalWorlds.add(worldMap.get(additionalWorldsName));
                    }

                    TemplatePack templatePack = new TemplatePack(worldMap.get(mainWorldName), additionalWorlds);
                    completableFuture.complete(templatePack);
                });

        return completableFuture;
    }

    private static CompletableFuture<Map<String, TemplateWorld>> loadAll(
            TemplatePool templatePool,
            Consumer<Map<String, Object>> mapConsumer,
            List<String> worldsToLoad
    ) {
        CompletableFuture<Map<String, TemplateWorld>> completableFuture = new CompletableFuture<>();

        List<CompletableFuture<TemplateWorld>> futures = new ArrayList<>();

        for (String name : worldsToLoad) {
            futures.add(createFuture(templatePool, name, mapConsumer));
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .whenComplete((a, b) -> {
                    Map<String, TemplateWorld> worldMap = new HashMap<>();

                    for (CompletableFuture<TemplateWorld> future : futures) {
                        try {
                            TemplateWorld aWorld = future.get();
                            worldMap.put(aWorld.getHandleName(), aWorld);
                        } catch (InterruptedException | ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    completableFuture.complete(worldMap);
                });

        return completableFuture;
    }

    private static CompletableFuture<TemplateWorld> createFuture(TemplatePool templatePool, String name, Consumer<Map<String, Object>> mapConsumer) {
        return templatePool.getOrLoadAsync(WorldRequest.configure(name, mapConsumer));
    }
}