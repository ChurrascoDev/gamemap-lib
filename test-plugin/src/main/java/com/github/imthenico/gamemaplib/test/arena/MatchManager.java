package com.github.imthenico.gamemaplib.test.arena;

import com.github.imthenico.gamemaplib.test.map.Arena;
import com.github.imthenico.gamemaplib.test.map.ArenaModelData;
import com.github.imthenico.gmlib.GameMapHandler;
import com.github.imthenico.gmlib.MapModel;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MatchManager implements Iterable<Match> {

    private final Plugin plugin;
    private final GameMapHandler gameMapHandler;
    private final Map<String, Match> activeMatches = new HashMap<>();

    public MatchManager(Plugin plugin, GameMapHandler gameMapHandler) {
        this.plugin = plugin;
        this.gameMapHandler = gameMapHandler;
    }

    public Match createNewMatch(
            Player playerOne,
            Player playerTwo,
            MapModel<ArenaModelData> mapModel,
            int matchDuration,
            String id
    ) {
        Arena arenaMap = gameMapHandler.createMap(mapModel, Arena.class, id);

        Match match = new Match(id, playerOne, playerTwo, arenaMap, matchDuration);

        activeMatches.put(id, match);

        return match;
    }

    public Match getArena(String id) {
        return activeMatches.get(id);
    }

    public void delayedFinalizeMatch(String id) {
        Match match = activeMatches.get(id);

        if (match == null)
            return;

        match.broadcast("&cFinalizing in 5 seconds...");

        Bukkit.getScheduler().runTaskLater(plugin, () -> finalizeMatch(match.getId()), 100);
    }

    public void finalizeMatch(String id) {
        Match arena = activeMatches.get(id);

        if (arena != null) {
            World world = Bukkit.getWorlds().get(0);

            arena.getPlayerOne().teleport(world.getSpawnLocation());
            arena.getPlayerTwo().teleport(world.getSpawnLocation());

            activeMatches.remove(id);
            arena.handleFinalization();
        }
    }

    public Match findByPlayer(Player player) {
        for (Match match : this) {
            Player playerOne = match.getPlayerOne();
            Player playerTwo = match.getPlayerTwo();

            if (equalsPlayers(player, playerOne) || equalsPlayers(player, playerTwo))
                return match;
        }

        return null;
    }

    private boolean equalsPlayers(Player p1, Player p2) {
        if (p1 == null || p2 == null)
            return false;

        return p1.getUniqueId().equals(p2.getUniqueId());
    }

    @NotNull
    @Override
    public Iterator<Match> iterator() {
        return activeMatches.values().iterator();
    }
}