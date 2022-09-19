package com.example.geektrust.model.plan;

public class SubscriptionPlan {
    private String planName;
    private int cost;
    private int duration;

    public SubscriptionPlan(String planName, int cost, int duration) {
        this.planName = planName;
        this.cost = cost;
        this.duration = duration;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "SubscriptionPlan{" +
                "planName='" + planName + '\'' +
                ", cost=" + cost +
                ", duration=" + duration +
                '}';
    }
}
