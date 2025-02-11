package com.client.models;


public class Test
{
    private int id;
    private String username;
    private int quoteId;
    private double wpm;
    private String testTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(int quoteId) {
        this.quoteId = quoteId;
    }

    public double getWpm() {
        return wpm;
    }

    public void setWpm(double wpm) {
        this.wpm = wpm;
    }

    public String getTestTime() {
        return testTime;
    }

    public void setTestTime(String testTime) {
        int index = testTime.indexOf(".");
        this.testTime = testTime.substring(0, index);
    }
}
