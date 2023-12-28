package com.models;

public class LeaderboardRow {
    private int position;
    private String userName;
    private double wpm;

    public LeaderboardRow(int position, String userName, double wpm) {
        this.position = position;
        this.userName = userName;
        this.wpm = wpm;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getWpm() {
        return wpm;
    }

    public void setWpm(double wpm) {
        this.wpm = wpm;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
