package com.github.imthenico.gmlib;

import com.github.imthenico.gmlib.exception.MapCreationException;
import com.github.imthenico.gmlib.exception.NoDataManipulatorFoundException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This converter clones the {@link MapModel} worlds and uses
 * a {@link CustomMapFactory} to create the map.
 */
public interface ModelConverter {

    /**
     * @param model - The model to convert
     * @param mapClass - The target class
     * @param mapName - The map name
     * @return a new created map
     * @throws NoDataManipulatorFoundException If no {@link CustomMapFactory} found to create the map
     * @throws MapCreationException If any error occurs while map creation or
     *                              the {@link CustomMapFactory} returns null
     * @param <D> - The data type
     * @param <T> - The map type
     */
    <D extends ModelData, T extends GameMap> @Nullable T createMap(
            @NotNull MapModel<D> model, @NotNull Class<T> mapClass, @NotNull String mapName
    ) throws NoDataManipulatorFoundException, MapCreationException;
}