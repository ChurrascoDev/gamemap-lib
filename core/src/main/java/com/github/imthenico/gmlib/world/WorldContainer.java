package com.github.imthenico.gmlib.world;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Predicate;

public class WorldContainer<T extends AWorld> implements Iterable<T> {

    private final List<T> worlds;

    public WorldContainer(List<T> worlds) {
        this.worlds = new ArrayList<>(worlds);
    }

    public WorldContainer(WorldContainer<T> worldContainer) {
        this.worlds = worldContainer.asList();
    }

    public WorldContainer() {
        this(Collections.emptyList());
    }

    public AWorld getByName(String name) {
        for (AWorld world : worlds) {
            if (world.getTemplateName().equals(name)) return world;
        }

        return null;
    }

    public AWorld getById(UUID uuid) {
        for (AWorld world : worlds) {
            if (world.getUUID().equals(uuid)) return world;
        }

        return null;
    }

    public AWorld getByIndex(int index) {
        return worlds.get(index);
    }

    public AWorld getIf(Predicate<AWorld> filter) {
        for (AWorld aWorld : this) {
            if (filter.test(aWorld)) return aWorld;
        }

        return null;
    }

    public void add(T world) {
        remove(world.getTemplateName());
        this.worlds.add(world);
    }

    public void addAll(Iterable<T> iterable) {
        for (T aWorld : iterable) {
            add(aWorld);
        }
    }

    @SuppressWarnings("UnusedReturnValue")
    public T remove(String name) {
        Iterator<T> iterator = worlds.iterator();
        T removed = null;

        while (iterator.hasNext()) {
            T world = iterator.next();

            if (world.getTemplateName().equals(name)) {
                iterator.remove();
                removed = world;
            }
        }

        return removed;
    }

    public void remove(AWorld world) {
        remove(world.getTemplateName());
    }

    public List<T> asList() {
        List<T> worlds = new ArrayList<>();

        for (T world : this) {
            worlds.add(world);
        }

        return worlds;
    }

    @Override
    public @NotNull Iterator<T> iterator() {
        return worlds.iterator();
    }
}