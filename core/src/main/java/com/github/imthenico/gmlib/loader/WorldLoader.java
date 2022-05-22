package com.github.imthenico.gmlib.loader;

import com.github.imthenico.gmlib.exception.NoWorldFoundException;
import com.github.imthenico.gmlib.world.TemplateWorld;
import com.github.imthenico.gmlib.world.WorldRequest;

import java.util.concurrent.Callable;

public interface WorldLoader {

    TemplateWorld load(WorldRequest request) throws NoWorldFoundException;

    Callable<TemplateWorld> prepareLoad(WorldRequest request);

}