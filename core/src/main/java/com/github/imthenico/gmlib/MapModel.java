package com.github.imthenico.gmlib;

import com.github.imthenico.gmlib.metadata.MetadataHolder;
import com.github.imthenico.gmlib.world.*;
import org.jetbrains.annotations.NotNull;

public interface MapModel<T extends ModelData> extends WorldHolder<TemplateWorld>, MetadataHolder {

    @NotNull String getName();

    @NotNull T getData();

    @NotNull Class<T> getDataType();

    static <T extends ModelData> MapModel<T> of(
            @NotNull String name, @NotNull T mapData, @NotNull TemplateWorld mainWorld, @NotNull WorldContainer<TemplateWorld> additionalWorlds
    ) {
        return new SimpleMapModel<>(mapData, name, mainWorld, additionalWorlds);
    }

    static <T extends ModelData> MapModel<T> of(
            @NotNull String name, @NotNull T mapData, @NotNull TemplatePack templatePack
    ) {
        return new SimpleMapModel<>(mapData, name, templatePack.mainWorld(), templatePack.additionalWorlds());
    }
}