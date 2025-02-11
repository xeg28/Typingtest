package com.client.models;

public class LeaderboardPair {
    private int userId;
    private double wpm;

    public LeaderboardPair() {}
    public LeaderboardPair(int userId, double wpm) {
        this.userId = userId;
        this.wpm = wpm;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getWpm() {
        return this.wpm;
    }

    public void setWpm(double wpm) {
        this.wpm = wpm;
    }
}
