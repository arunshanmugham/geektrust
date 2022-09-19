package com.example.geektrust.model.category;

import com.example.geektrust.error.StreamingErrors;
import com.example.geektrust.model.plan.SubscriptionPlan;
import com.example.geektrust.util.DateValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class StreamCategory {

    private String categoryName;

    private ArrayList<String> renewalOutputs = new ArrayList<String>();

    private SubscriptionPlan selectedPlan;
    private ArrayList<SubscriptionPlan> availablePlans = new ArrayList<SubscriptionPlan>();

    private int renewalAmt;


    public StreamCategory(String categoryName, ArrayList<SubscriptionPlan> availablePlans) {
        this.categoryName = categoryName;
        this.availablePlans = availablePlans;
    }

   
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


    public ArrayList<SubscriptionPlan> getAvailablePlans() {
        return availablePlans;
    }

    public void setAvailablePlans(ArrayList<SubscriptionPlan> availablePlans) {
        this.availablePlans = availablePlans;
    }


    public String getRenewalDate(String subScriptionStartDate){
        DateTimeFormatter inputDateFormat = DateTimeFormatter.ofPattern("d-M-yyyy");
        DateTimeFormatter outputDateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate subscriptionDate = LocalDate.parse(subScriptionStartDate,inputDateFormat);

        LocalDate renewalDate = subscriptionDate.plusMonths(getSelectedPlan().getDuration())
                                        .minusDays(10);
        String renewalDateStr = renewalDate.format(outputDateFormat);

        return renewalDateStr;
    }


    public SubscriptionPlan getSelectedPlan() {
        return selectedPlan;
    }

    public void setSelectedPlan(SubscriptionPlan selectedPlan) {
        this.selectedPlan = selectedPlan;
    }

    public int getRenewalAmt() {
        return renewalAmt;
    }

    public void setRenewalAmt(int renewalAmt) {
        this.renewalAmt = renewalAmt;
    }

    @Override
    public String toString() {
        return "StreamCategory{" +
                "categoryName='" + categoryName + '\'' +
                ", selectedPlans=" + selectedPlan +
                '}';
    }
}
