package com.github.imthenico.gmlib;

import com.github.imthenico.gmlib.metadata.SimpleMetadataHolder;
import com.github.imthenico.gmlib.world.TemplateWorld;
import com.github.imthenico.gmlib.world.WorldContainer;
import org.jetbrains.annotations.NotNull;

import static java.util.Objects.*;

public class SimpleMapModel<T extends ModelData> extends SimpleMetadataHolder implements MapModel<T> {

    private final T mapData;
    private final String name;
    private TemplateWorld mainWorld;
    private final WorldContainer<TemplateWorld> additionalWorlds;

    public SimpleMapModel(T mapData, String name, TemplateWorld mainWorld, WorldContainer<TemplateWorld> additionalWorlds) {
        this.mapData = mapData;
        this.name = name;
        this.mainWorld = mainWorld;
        this.additionalWorlds = additionalWorlds;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public @NotNull T getData() {
        return mapData;
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull Class<T> getDataType() {
        return (Class<T>) mapData.getClass();
    }

    @Override
    public @NotNull TemplateWorld getMainWorld() {
        return mainWorld;
    }

    @Override
    public void setMainWorld(@NotNull TemplateWorld world) {
        this.mainWorld = requireNonNull(world);
    }

    @Override
    public WorldContainer<TemplateWorld> getAdditionalWorlds() {
        return additionalWorlds;
    }
}