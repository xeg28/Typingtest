package com.client.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Quote implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static int idSeed = 1;
    private int id;
    private String quote;
    private String title;

    private String username;

    private List<LeaderboardPair> topFiveTests = new ArrayList<>();

    public Quote() {
        this.id = idSeed++;
    }


    public Quote(String title, String quote, String username) {
        this.id = idSeed++;
        this.title = title;
        this.quote = quote;
        this.username = username;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setQuote(String quote) {this.quote = quote; }

    public void setTopFiveTests(List<LeaderboardPair> topFiveTests) {
        this.topFiveTests = topFiveTests;
    }
    public List<LeaderboardPair> getTopFiveTests() {
        return topFiveTests;
    }

    public static void setIdSeed(int newSeed) {
        idSeed = newSeed;
    }

    public static int getIdSeed() {
        return idSeed;
    }

    public String getTitle() {
        return this.title;
    }

    public String getQuote() {
        return this.quote;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return this.username;
    }
    @Override
    public String toString() {
        return this.quote;
    }
}
