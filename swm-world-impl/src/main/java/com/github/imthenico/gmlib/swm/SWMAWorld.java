package com.github.imthenico.gmlib.swm;

import com.github.imthenico.gmlib.world.AbstractAWorld;
import com.grinderwolf.swm.api.world.SlimeWorld;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.UUID;

public class SWMAWorld extends AbstractAWorld {

    private final SlimeWorld slimeWorld;

    public SWMAWorld(String name, String persistentName, UUID uuid, World handle, SlimeWorld slimeWorld) {
        super(name, persistentName, uuid, new WeakReference<>(handle));
        this.slimeWorld = slimeWorld;
    }

    @Override
    public void save() {
        getHandle().save();
    }

    @Override
    public void unload() {
        Bukkit.unloadWorld(getHandle(), false);
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull World getHandle() {
        World world = ((WeakReference<World>) super.getHandle()).get();

        if (world == null)
            throw new IllegalStateException("World is unloaded");

        return world;
    }

    public SlimeWorld getSlimeWorld() {
        return slimeWorld;
    }
}