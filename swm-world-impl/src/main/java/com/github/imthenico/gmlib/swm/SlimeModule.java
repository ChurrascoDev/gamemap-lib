package com.github.imthenico.gmlib.swm;

import com.github.imthenico.gmlib.pool.TemplatePool;
import com.grinderwolf.swm.plugin.SWMPlugin;
import org.bukkit.Bukkit;

public interface SlimeModule {

    static SWMWorldLoader newLoader() {
        SWMPlugin plugin = (SWMPlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");

        if (plugin == null)
            throw new RuntimeException("No swm plugin found");

        return new SWMWorldLoader();
    }

    static TemplatePool newPool() {
        return TemplatePool.create(newLoader());
    }

}