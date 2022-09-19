package com.example.geektrust.subscription;

import com.example.geektrust.error.StreamingErrors;
import com.example.geektrust.model.category.StreamCategory;
import com.example.geektrust.model.renewal.Renewal;
import com.example.geektrust.model.topup.TopupPlan;
import com.example.geektrust.util.DateValidator;
import com.example.geektrust.util.StreamConstants;

import java.util.ArrayList;
import java.util.List;

public class UserSubscription implements Renewal {

    private final String OUTPUT_DELIMETER=" ";
    private String subscriptionStartDate;
    private ArrayList<StreamCategory>  subscribedStreams = new ArrayList<StreamCategory>();

    private boolean isDuplicateCategory;

    private TopupPlan topupPlan;

    private boolean isDuplicateTopup;

    private int topupDuration;

    private int totalRenewalAmt=0;

    private boolean isValidSubscriptionDate;

    private ArrayList<String> renewalOutput = new ArrayList<String>();

    private ArrayList<String> errorOutput  = new ArrayList<String>();

    public UserSubscription() {
    }

    public UserSubscription(String subscriptionStartDate, ArrayList<StreamCategory> subscribedStreams, TopupPlan topupPlan) {
        this.subscriptionStartDate = subscriptionStartDate;
        this.subscribedStreams = subscribedStreams;
        this.topupPlan = topupPlan;
        isDuplicateCategory=false;
    }

    public String getSubscriptionStartDate() {
        return subscriptionStartDate;
    }

    public void setSubscriptionStartDate(String subscriptionStartDate) {
        if(!DateValidator.validateSubscriptionDate(subscriptionStartDate)) {
            errorOutput.add(StreamingErrors.INVALID_DATE.name());
            setValidSubscriptionDate(false);
        }
        else {
            this.subscriptionStartDate = subscriptionStartDate;
            setValidSubscriptionDate(true);
        }
    }

    public boolean isValidSubscriptionDate() {
        return isValidSubscriptionDate;
    }

    public void setValidSubscriptionDate(boolean validSubscriptionDate) {
        isValidSubscriptionDate = validSubscriptionDate;
    }

    public ArrayList<String> getRenewalOutput() {
        return renewalOutput;
    }

    public void setRenewalOutput(ArrayList<String> renewalOutput) {
        this.renewalOutput = renewalOutput;
    }

    public ArrayList<StreamCategory> getSubscribedStreams() {
        return subscribedStreams;
    }

    public void setSubscribedStreams(ArrayList<StreamCategory> subscribedStreams) {
        this.subscribedStreams = subscribedStreams;
    }

    public boolean isDuplicateCategory() {
        return isDuplicateCategory;
    }

    public void setDuplicateCategory(boolean duplicateCategory) {
        isDuplicateCategory = duplicateCategory;
    }

    public TopupPlan getTopupPlan() {
        return topupPlan;
    }

    public void setTopupPlan(TopupPlan topupPlan) {
        this.topupPlan = topupPlan;
    }

    public int getTopupDuration() {
        return topupDuration;
    }

    public void setTopupDuration(int topupDuration) {
        this.topupDuration = topupDuration;
    }

    public boolean isDuplicateTopup() {
        return isDuplicateTopup;
    }

    public void setDuplicateTopup(boolean duplicateTopup) {
        isDuplicateTopup = duplicateTopup;
    }

    public int getTotalRenewalAmt() {
        return totalRenewalAmt;
    }

    public void setTotalRenewalAmt(int totalRenewalAmt) {
        this.totalRenewalAmt = totalRenewalAmt;
    }

    @Override
    public void printRenewals(){

        if(getSubscribedStreams().size()==0 || renewalOutput.size()==0)
            errorOutput.add(StreamingErrors.SUBSCRIPTIONS_NOT_FOUND.name());

        if(getTotalRenewalAmt()>0)
            renewalOutput.add(StreamConstants.RENEWAL_AMOUNT+OUTPUT_DELIMETER+getTotalRenewalAmt());


        //Print all failure outputs
        errorOutput.stream().forEach(System.out::println);

        //Print all renewal success outputs
        renewalOutput.stream().forEach(System.out::println);


    }


    public UserSubscription addSubscription(StreamCategory streamCategory){
        String result=null;

        if(DateValidator.validateSubscriptionDate(getSubscriptionStartDate())) {
            boolean isCategoryFound=false;
            isCategoryFound = getSubscribedStreams().stream().anyMatch(cat -> cat.getCategoryName().equals(streamCategory.getCategoryName()));

            if(!isCategoryFound) {
                getSubscribedStreams().add(streamCategory);
                result = StreamConstants.RENEWAL_REMINDER + OUTPUT_DELIMETER + streamCategory.getCategoryName() + OUTPUT_DELIMETER
                        + streamCategory.getRenewalDate(getSubscriptionStartDate());
                renewalOutput.add(result);
                setTotalRenewalAmt(getTotalRenewalAmt()+streamCategory.getSelectedPlan().getCost());
            }
            else
                errorOutput.add(StreamingErrors.ADD_SUBSCRIPTION_FAILED.name()+OUTPUT_DELIMETER+StreamingErrors.DUPLICATE_CATEGORY);

        }
        else
            errorOutput.add(StreamingErrors.ADD_SUBSCRIPTION_FAILED+OUTPUT_DELIMETER+ OUTPUT_DELIMETER+StreamingErrors.INVALID_DATE);



        return this;
    }

    public void addTopup(List<String> commandList) {
        String result=null;
        TopupPlan topupPlan = TopupPlan.getTopupByShortName(commandList.get(StreamConstants.CATEGORY_ARG)).get();

        if(getTopupPlan()!=null) {
            errorOutput.add(StreamingErrors.ADD_TOPUP_FAILED+" "+StreamingErrors.DUPLICATE_TOPUP);
            setDuplicateTopup(true);
        }
        else {
            if(getSubscribedStreams().size()>0) {
                setTopupPlan(topupPlan);
                setTopupDuration(Integer.parseInt(commandList.get(StreamConstants.SUBSCRIPTION_PLAN_ARG)));
                setTotalRenewalAmt(getTotalRenewalAmt()+topupPlan.getTotalCost() * Integer.parseInt(commandList.get(StreamConstants.SUBSCRIPTION_PLAN_ARG)));

            }
            else {
                if(isValidSubscriptionDate)
                    errorOutput.add(StreamingErrors.ADD_TOPUP_FAILED + " "
                            + StreamingErrors.SUBSCRIPTIONS_NOT_FOUND);
                else
                    errorOutput.add(StreamingErrors.ADD_TOPUP_FAILED.name()+OUTPUT_DELIMETER+StreamingErrors.INVALID_DATE.name());
            }
        }

    }

}
