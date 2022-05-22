package com.github.imthenico.gamemaplib.test.command;

import com.github.imthenico.gamemaplib.test.GMTestPlugin;
import com.github.imthenico.gamemaplib.test.arena.Match;
import com.github.imthenico.gamemaplib.test.arena.MatchManager;
import com.github.imthenico.gamemaplib.test.map.Arena;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;

import java.util.Arrays;

@Command(names = "match")
public class MatchCommand implements CommandClass {

    private final MatchManager matchManager;
    private final GMTestPlugin gmTestPlugin;

    public MatchCommand(MatchManager matchManager, GMTestPlugin gmTestPlugin) {
        this.matchManager = matchManager;
        this.gmTestPlugin = gmTestPlugin;
    }

    @Command(names = "")
    public boolean startMatch(@Sender Player player, Player another) {
        Match match = matchManager.createNewMatch(
                player, another, gmTestPlugin.getMapModel(), 600, "test"
        );

        Arena arena = match.getArenaMap();

        player.teleport(arena.getFirstSpawn());
        another.teleport(arena.getSecondSpawn());
        
        return true;
    }
}