package com.github.imthenico.gamemaplib.test.arena;

import com.github.imthenico.gamemaplib.test.map.Arena;
import com.github.imthenico.gamemaplib.test.util.SimpleTimer;
import com.github.imthenico.gmlib.world.AWorld;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.ref.WeakReference;

import static java.util.Objects.requireNonNull;

public class Match {

    private final String id;
    private final WeakReference<Player> playerOne;
    private final WeakReference<Player> playerTwo;
    private WeakReference<Player> winner;
    private final SimpleTimer timer;
    private Arena arena;

    public Match(
            String id,
            Player playerOne,
            Player playerTwo,
            Arena arena,
            int matchDuration
    ) {
        this.id = id;
        this.playerOne = new WeakReference<>(requireNonNull(playerOne));
        this.playerTwo = new WeakReference<>(requireNonNull(playerTwo));
        this.arena = requireNonNull(arena);
        this.timer = new SimpleTimer(matchDuration);
    }

    public SimpleTimer getTimer() {
        return timer;
    }

    public String getId() {
        return id;
    }

    public Player getPlayerOne() {
        return playerOne.get();
    }

    public Player getPlayerTwo() {
        return playerTwo.get();
    }

    public void setWinner(Player player) {
        if (isEnded())
            throw new IllegalArgumentException("Match is ended");

        this.winner = new WeakReference<>(player);
    }

    public void broadcast(String message) {
        message = ChatColor.translateAlternateColorCodes('&', message);
        Player playerOne = this.playerOne.get();
        Player playerTwo = this.playerTwo.get();

        if (playerOne != null) playerOne.sendMessage(message);
        if (playerTwo != null) playerTwo.sendMessage(message);
    }

    public Player getWinner() {
        if (winner != null)
            return winner.get();

        return null;
    }

    public Player getRival(Player player) {
        if (equalsPlayers(player, getPlayerOne()))
            return getPlayerTwo();

        if (equalsPlayers(player, getPlayerTwo()))
            return getPlayerOne();

        return null;
    }

    public boolean isEnded() {
        return timer.isOver() || winner != null;
    }

    public Arena getArenaMap() {
        return arena;
    }

    private boolean equalsPlayers(Player p1, Player p2) {
        if (p1 == null || p2 == null)
            return false;

        return p1.getUniqueId().equals(p2.getUniqueId());
    }

    void handleFinalization() {
        this.arena.allWorlds().forEach(AWorld::unload);
        this.arena = null;
    }
}