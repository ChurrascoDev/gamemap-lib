package com.github.imthenico.gmlib.swm;

import com.github.imthenico.gmlib.exception.NoWorldFoundException;
import com.github.imthenico.gmlib.loader.WorldLoader;
import com.github.imthenico.gmlib.world.TemplateWorld;
import com.github.imthenico.gmlib.world.WorldRequest;
import com.grinderwolf.swm.api.world.SlimeWorld;
import com.grinderwolf.swm.nms.SlimeNMS;
import com.grinderwolf.swm.plugin.SWMPlugin;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.concurrent.*;

public class SWMWorldLoader implements WorldLoader {

    private final SWMPlugin swmPlugin;

    public SWMWorldLoader() {
        this.swmPlugin = SWMPlugin.getInstance();
    }

    @Override
    public TemplateWorld load(WorldRequest request) throws NoWorldFoundException {
        String name = request.getName();

        World world = Bukkit.getWorld(name);

        if (world == null)
            throw new NoWorldFoundException(name);

        SlimeNMS slimeNMS = swmPlugin.getNms();

        SlimeWorld slimeWorld = slimeNMS.getSlimeWorld(world);

        if (slimeWorld == null) {
            throw new RuntimeException("Found world loaded with name '%s' but not an instance of SWM");
        }

        return new SWMTemplate(
                name,
                slimeWorld.getName(),
                world.getUID(),
                world,
                slimeWorld
        );
    }

    @Override
    public Callable<TemplateWorld> prepareLoad(WorldRequest request) {
        return () -> load(request);
    }
}