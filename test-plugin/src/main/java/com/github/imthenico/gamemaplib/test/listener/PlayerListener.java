package com.github.imthenico.gamemaplib.test.listener;

import com.github.imthenico.gamemaplib.test.arena.Match;
import com.github.imthenico.gamemaplib.test.arena.MatchManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerListener implements Listener {

    private final MatchManager matchManager;

    public PlayerListener(MatchManager matchManager) {
        this.matchManager = matchManager;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        Player player = event.getEntity() instanceof Player ? (Player) event.getEntity() : null;

        Match match = matchManager.findByPlayer(player);

        if (match == null || player == null)
            return;

        if (match.isEnded()) {
            event.setCancelled(true);
            return;
        }

        int remainingHealth = (int) (player.getHealth() - event.getDamage());

        if (remainingHealth <= 0) {
            player.setHealth(player.getMaxHealth());

            Player winner = match.getRival(player);
            match.setWinner(winner);
            match.broadcast("&bStop! &eThe Winner is &a" + winner.getName());

            event.setCancelled(true);

            matchManager.delayedFinalizeMatch(match.getId());
        }
    }
}