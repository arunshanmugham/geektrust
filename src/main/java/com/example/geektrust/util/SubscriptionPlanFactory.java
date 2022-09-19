package com.example.geektrust.util;

import com.example.geektrust.model.plan.SubscriptionPlan;

import java.util.ArrayList;

public class SubscriptionPlanFactory {

    public static ArrayList<SubscriptionPlan> getSubscriptionPlans(StreamList stream){
        ArrayList<SubscriptionPlan> plans = new ArrayList<SubscriptionPlan>();

            switch(stream){
                case MUSIC:
                    plans.add(new SubscriptionPlan("FREE",0,1));
                    plans.add(new SubscriptionPlan("PERSONAL",100,1));
                    plans.add(new SubscriptionPlan("PREMIUM",250,3));
                    break;
                case VIDEO:
                    plans.add(new SubscriptionPlan("FREE",0,1));
                    plans.add(new SubscriptionPlan("PERSONAL",200,1));
                    plans.add(new SubscriptionPlan("PREMIUM",500,3));
                    break;
                case PODCAST:
                    plans.add(new SubscriptionPlan("FREE",0,1));
                    plans.add(new SubscriptionPlan("PERSONAL",100,1));
                    plans.add(new SubscriptionPlan("PREMIUM",300,3));

            }

        return plans;
    }
}
