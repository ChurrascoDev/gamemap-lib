package com.github.imthenico.gamemaplib.test.command;

import com.github.imthenico.gamemaplib.test.GMTestPlugin;
import com.github.imthenico.gamemaplib.test.map.ArenaModelData;
import com.github.imthenico.gamemaplib.test.util.LocationModel;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;

@Command(names = "mapeditor")
public class ModelEditorCommand implements CommandClass {

    private final ArenaModelData arenaModelData;
    private final GMTestPlugin gmTestPlugin;

    public ModelEditorCommand(ArenaModelData arenaModelData, GMTestPlugin gmTestPlugin) {
        this.arenaModelData = arenaModelData;
        this.gmTestPlugin = gmTestPlugin;
    }

    @Command(names = "setfirstspawn")
    public boolean setFirstSpawn(@Sender Player player) {
        arenaModelData.setFirstSpawn(LocationModel.fromBukkit(player.getLocation()));
        player.sendMessage("§aSuccess");
        return true;
    }

    @Command(names = "setsecondspawn")
    public boolean setSecondSpawn(@Sender Player player) {
        arenaModelData.setSecondSpawn(LocationModel.fromBukkit(player.getLocation()));
        player.sendMessage("§aSuccess");
        return true;
    }

    @Command(names = "savemodel")
    public boolean save(@Sender Player player) {
        gmTestPlugin.saveModel();
        player.sendMessage("§aSaved!");
        return true;
    }
}