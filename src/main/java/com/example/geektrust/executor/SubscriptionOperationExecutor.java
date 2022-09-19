package com.example.geektrust.executor;


import com.example.geektrust.error.StreamingErrors;
import com.example.geektrust.model.category.StreamCategory;
import com.example.geektrust.model.plan.SubscriptionPlan;
import com.example.geektrust.model.topup.TopupPlan;
import com.example.geektrust.subscription.UserSubscription;
import com.example.geektrust.util.CategoryUtil;
import com.example.geektrust.util.DateValidator;
import com.example.geektrust.util.StreamList;
import com.example.geektrust.util.SubscriptionPlanFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class SubscriptionOperationExecutor {

    /**
     * Below variables are command line argument position after splitting.
     */
    private static final int COMMAND_ARG = 0;

    private static final int  CATEGORY_ARG = 1;

    private static final int  SUBSCRIPTION_PLAN_ARG = 2;

    /**
     * Below variables are command action argument action for about COMMAND_ARG.
     */

    private static final int PRINT_RENEWAL_DETAILS = 1;

    private static final int  START_SUBSCRIPTION = 2;

    private static final int ADD_SUBSCRIPTION_TOPUP = 3;

    private UserSubscription userSubscription;

    public SubscriptionOperationExecutor(UserSubscription userSubscription) {
        this.userSubscription = userSubscription;
    }


    public void execute(List<String> commandList ){

        switch(commandList.size()){
            case PRINT_RENEWAL_DETAILS:

                userSubscription.printRenewals();
                break;

            case START_SUBSCRIPTION:

                userSubscription.setSubscriptionStartDate(commandList.get(CATEGORY_ARG));

                break;
            case ADD_SUBSCRIPTION_TOPUP:

                if(SubscriptionCommand.valueOf(commandList.get(COMMAND_ARG)).equals(SubscriptionCommand.ADD_SUBSCRIPTION)) {

                    userSubscription = userSubscription.addSubscription(CategoryUtil.getStreamCategory(commandList));
                }

                if(SubscriptionCommand.valueOf(commandList.get(COMMAND_ARG)).equals(SubscriptionCommand.ADD_TOPUP)) {
                     userSubscription.addTopup(commandList);
                }

                break;
        }

    }


}
