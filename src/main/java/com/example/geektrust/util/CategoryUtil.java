package com.example.geektrust.util;

import com.example.geektrust.model.category.StreamCategory;
import com.example.geektrust.model.plan.SubscriptionPlan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CategoryUtil {

    public static List<String> getCategoryDuplicates(List<StreamCategory> subscribedStreams,String newCategory){

        List<String> catergoryNames = subscribedStreams.stream()
                .map(category -> category.getCategoryName())
                .collect(Collectors.toList());


        List<String> duplicateCategory= catergoryNames.stream()
                .filter(categoryName ->
                        Collections.frequency(catergoryNames,newCategory)>1)
                .collect(Collectors.toList());

        return duplicateCategory;
    }

    public static StreamCategory getStreamCategory(List<String> commandList){
        ArrayList<SubscriptionPlan> supportedPlans =
                SubscriptionPlanFactory.getSubscriptionPlans(
                        StreamList.valueOf(commandList.get(1)));

        Stream<SubscriptionPlan> selectedPlan = supportedPlans.stream().
                filter(plan -> plan.getPlanName().equals(commandList.get(StreamConstants.SUBSCRIPTION_PLAN_ARG)));

        SubscriptionPlan plan = selectedPlan.collect(Collectors.toList()).get(0);
        StreamCategory category = new StreamCategory(commandList.get(StreamConstants.CATEGORY_ARG), supportedPlans);
        category.setSelectedPlan(plan);
        return category;

    }


}
