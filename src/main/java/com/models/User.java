package com.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private static int idSeed = 1;
    private int id;
    private double bestWPM = 0;
    private double totalSumWPM = 0;
    private int tests = 0;
    private double averageWPM = 0;
    private HashMap<Integer, List<Double>> topFive = new HashMap<>();

    public User() {
        this.id = idSeed++;
    }

    public User(String name) {
        this.id = idSeed++;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<Integer,  List<Double> > getTopFive() {
        return this.topFive;
    }

    public void setTopFive(HashMap<Integer, List<Double> > topFive) {
        this.topFive = topFive;
    }

    public double getBestWPM() {
        return bestWPM;
    }

    public void setBestWPM(double bestWPM) {
        this.bestWPM = bestWPM;
    }

    public double getTotalSumWPM() {
        return totalSumWPM;
    }

    public void setTotalSumWPM(double totalSumWPM) {
        this.totalSumWPM = totalSumWPM;
    }

    public int getTests() {
        return tests;
    }

    public void setTests(int tests) {
        this.tests = tests;
    }

    public double getAverageWPM() {
        return averageWPM;
    }

    public void setAverageWPM(double averageWPM) {
        this.averageWPM = averageWPM;
    }

    public static int getIdSeed() { return idSeed; }

    public static void setIdSeed(int seed) { idSeed = seed; }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }




}
