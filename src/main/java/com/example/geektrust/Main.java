package com.example.geektrust;

import com.example.geektrust.error.StreamingErrors;
import com.example.geektrust.executor.SubscriptionCommand;
import com.example.geektrust.executor.SubscriptionOperationExecutor;
import com.example.geektrust.model.category.StreamCategory;
import com.example.geektrust.model.plan.SubscriptionPlan;
import com.example.geektrust.model.topup.TopupPlan;
import com.example.geektrust.subscription.UserSubscription;
import com.example.geektrust.util.CategoryUtil;
import com.example.geektrust.util.DateValidator;
import com.example.geektrust.util.StreamList;
import com.example.geektrust.util.SubscriptionPlanFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        /*
        Sample code to read from file passed as command line argument
        */
        try {
            // the file to be opened for reading

            FileInputStream fis = new FileInputStream(args[0]);
            Scanner sc = new Scanner(fis); // file to be scanned
            UserSubscription userSubscription = new UserSubscription();
            SubscriptionOperationExecutor commandExecutor = null;
            String command=null;
            // returns true if there is another line to read
            while (sc.hasNextLine()) {
                command = sc.nextLine();
                List<String> commandList = Arrays.stream(command.split(" "))
                        .collect(Collectors.toList());

                commandExecutor = new SubscriptionOperationExecutor(userSubscription);
                commandExecutor.execute(commandList);

            }
            sc.close(); // closes the scanner
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




}
