package com.models;

public class User {
    private String name;
    private static int idSeed = 1;
    private static int id;
    private double bestWPM;
    private double totalSumWPM;
    private int tests;
    private double averageWPM;

    public User() {
        this.id = idSeed++;
    }

    public User(String name) {
        this.id = idSeed++;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }


}
