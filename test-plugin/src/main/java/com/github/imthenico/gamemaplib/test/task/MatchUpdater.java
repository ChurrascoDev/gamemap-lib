package com.github.imthenico.gamemaplib.test.task;

import com.github.imthenico.gamemaplib.test.arena.Match;
import com.github.imthenico.gamemaplib.test.arena.MatchManager;
import com.github.imthenico.gamemaplib.test.util.SimpleTimer;

public class MatchUpdater implements Runnable {

    private final MatchManager matchManager;

    public MatchUpdater(MatchManager matchManager) {
        this.matchManager = matchManager;
    }

    @Override
    public void run() {
        for (Match match : matchManager) {
            if (match.isEnded())
                continue;

            SimpleTimer timer = match.getTimer();

            timer.elapse(1);

            if (!timer.isOver()) {
                match.broadcast("&eMatch tied!");

                matchManager.delayedFinalizeMatch(match.getId());
            }
        }
    }
}