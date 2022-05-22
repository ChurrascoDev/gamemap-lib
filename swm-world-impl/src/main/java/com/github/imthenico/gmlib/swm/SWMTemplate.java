package com.github.imthenico.gmlib.swm;

import com.github.imthenico.gmlib.world.AWorld;
import com.github.imthenico.gmlib.world.TemplateWorld;
import com.grinderwolf.swm.api.world.SlimeWorld;
import com.grinderwolf.swm.plugin.SWMPlugin;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.Callable;

public class SWMTemplate extends SWMAWorld implements TemplateWorld {

    public SWMTemplate(String name, String persistentName, UUID uuid, World handle, SlimeWorld slimeWorld) {
        super(name, persistentName, uuid, handle, slimeWorld);
    }

    @Override
    public @NotNull Callable<AWorld> clone(String newName) {
        return () -> {
            if (Bukkit.getWorld(newName) != null)
                throw new IllegalArgumentException("There's already a world with this name");

            SlimeWorld cloned = getSlimeWorld().clone(newName);
            SWMPlugin.getInstance().generateWorld(cloned);

            World world = Bukkit.getWorld(newName);
            return new SWMAWorld(world.getName(), getTemplateName(), world.getUID(), world, cloned);
        };
    }
}