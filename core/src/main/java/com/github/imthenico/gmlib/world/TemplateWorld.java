package com.github.imthenico.gmlib.world;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Callable;

public interface TemplateWorld extends AWorld {

    @NotNull Callable<AWorld> clone(String newName);

}