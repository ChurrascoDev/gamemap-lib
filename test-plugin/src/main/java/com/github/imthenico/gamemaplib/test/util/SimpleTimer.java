package com.github.imthenico.gamemaplib.test.util;

public class SimpleTimer {

    private final int totalTime;
    private int elapsedTime;

    public SimpleTimer(int totalTime) {
        if (totalTime <= 0)
            throw new IllegalArgumentException("invalid time");

        this.totalTime = totalTime;
    }

    public void elapse(int toElapse) {
        if (toElapse <= 0)
            throw new IllegalArgumentException("zero or negative number");

        elapsedTime += totalTime;
    }

    public boolean isOver() {
        return elapsedTime >= totalTime;
    }

    public int getRemainingTime() {
        return totalTime - elapsedTime;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }

    public int getTotalTime() {
        return totalTime;
    }
}