package com.example.geektrust.model.topup;


import java.util.Arrays;
import java.util.Optional;

public enum TopupPlan
{
    FOUR_DEVICE("FOUR_DEVICE", 4, 50),
    TEN_DEVICE("TEN_DEVICE", 10, 100);

    private final String shortName;
    private final int numOfDevices;
    private final int totalCost;


    TopupPlan(String shortName, int numOfDevices, int totalCost) {
        this.shortName = shortName;
        this.numOfDevices = numOfDevices;
        this.totalCost = totalCost;
    }

    public String getShortName() {
        return shortName;
    }

    public int getNumOfDevices() {
        return numOfDevices;
    }

    public int getTotalCost() {
        return totalCost;
    }

    // Reverse lookup methods
    public static Optional<TopupPlan> getTopupByShortName(String value) {
        return Arrays.stream(TopupPlan.values())
                .filter(topup -> topup.shortName.equals(value))
                .findFirst();
    }



}