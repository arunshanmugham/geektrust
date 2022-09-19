package com.example.geektrust;

import com.example.geektrust.executor.SubscriptionOperationExecutor;
import com.example.geektrust.model.category.StreamCategory;
import com.example.geektrust.model.plan.SubscriptionPlan;
import com.example.geektrust.subscription.UserSubscription;
import com.example.geektrust.util.CategoryUtil;
import com.example.geektrust.util.StreamConstants;
import junit.framework.Assert;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SubscriptionTest {

    UserSubscription userSubscription;

    SubscriptionOperationExecutor executor;

    @BeforeEach
    public  void setup(){
         userSubscription = new UserSubscription();
         executor = new SubscriptionOperationExecutor(userSubscription);

    }

    @Test
    @DisplayName("Test for calling Main method with file input")
    public void testMain(){
        String args [] = {"sample_input/input2.txt"};
        Main.main(args);

    }

    @Test
    @DisplayName("Test for adding Subscription Start Date")
    public void testSubscriptionStartDate(){
        ArrayList<String> commandList = new ArrayList<String>();
        commandList.add("START_SUBSCRIPTION");
        commandList.add("10-02-2022");
        executor.execute(commandList);
        Assert.assertTrue(!userSubscription.getSubscriptionStartDate().isEmpty());
    }

    @Test
    @DisplayName("Test for invalid Subscription Start Date")
    public void testInvalidSubscriptionStartDate(){
        userSubscription = new UserSubscription();
        ArrayList<String> commandList = new ArrayList<String>();
        commandList.add("START_SUBSCRIPTION");
        commandList.add("22-22-2022");
        executor.execute(commandList);
        Assert.assertTrue(userSubscription.getSubscriptionStartDate()==null);
    }



    @Test
    @DisplayName("Test for adding Subscription")
    public void testAddSubscription(){
        ArrayList<String> commandList = new ArrayList<String>();
        commandList.add("START_SUBSCRIPTION");
        commandList.add("10-02-2022");
        executor.execute(commandList);
        Assert.assertTrue(!userSubscription.getSubscriptionStartDate().isEmpty());

        commandList.clear();
        commandList.add("ADD_SUBSCRIPTION");
        commandList.add("MUSIC");
        commandList.add("PERSONAL");
        executor.execute(commandList);
        Assert.assertFalse(userSubscription==null);
        Assert.assertTrue(!userSubscription.getSubscriptionStartDate().isEmpty());
        Assert.assertFalse(userSubscription.getSubscribedStreams().isEmpty());
        Assert.assertEquals(1,userSubscription.getSubscribedStreams().size());
        Assert.assertEquals("MUSIC",userSubscription.getSubscribedStreams().stream()
                .map(StreamCategory::getCategoryName).findFirst().get());
    }

    @Test
    @DisplayName("Test for adding duplicate Subscription")
    public void testDuplicateSubscription(){
        ArrayList<String> commandList = new ArrayList<String>();
        commandList.add("START_SUBSCRIPTION");
        commandList.add("10-02-2022");
        executor.execute(commandList);
        Assert.assertTrue(!userSubscription.getSubscriptionStartDate().isEmpty());

        commandList.clear();
        commandList.add("ADD_SUBSCRIPTION");
        commandList.add("MUSIC");
        commandList.add("PERSONAL");
        commandList.clear();
        commandList.add("ADD_SUBSCRIPTION");
        commandList.add("MUSIC");
        commandList.add("PREMIUM");
        executor.execute(commandList);
        Assert.assertFalse(userSubscription==null);
        Assert.assertTrue(!userSubscription.getSubscriptionStartDate().isEmpty());
        Assert.assertFalse(userSubscription.getSubscribedStreams().isEmpty());
        Assert.assertEquals(1,userSubscription.getSubscribedStreams().size());
        userSubscription.getSubscribedStreams().add(userSubscription.getSubscribedStreams().get(0));
        Assert.assertTrue(!CategoryUtil.getCategoryDuplicates(userSubscription.getSubscribedStreams(),"MUSIC").isEmpty());
    }

    @Test
    public void testAddTopup(){
        ArrayList<String> commandList = new ArrayList<String>();
        commandList.add("START_SUBSCRIPTION");
        commandList.add("10-02-2022");
        executor.execute(commandList);
        Assert.assertTrue(!userSubscription.getSubscriptionStartDate().isEmpty());

        commandList.clear();
        commandList.add("ADD_SUBSCRIPTION");
        commandList.add("MUSIC");
        commandList.add("PERSONAL");
        executor.execute(commandList);
        Assert.assertFalse(userSubscription==null);

        commandList.clear();
        commandList.add("ADD_TOPUP");
        commandList.add("FOUR_DEVICE");
        commandList.add("3");
        executor.execute(commandList);
        Assert.assertTrue(!userSubscription.getTopupPlan().getShortName().isEmpty());

    }

    @Test
    public void testForDuplicateTopup(){
        ArrayList<String> commandList = new ArrayList<String>();
        commandList.add("START_SUBSCRIPTION");
        commandList.add("10-02-2022");
        executor.execute(commandList);
        Assert.assertTrue(!userSubscription.getSubscriptionStartDate().isEmpty());

        commandList.clear();
        commandList.add("ADD_SUBSCRIPTION");
        commandList.add("MUSIC");
        commandList.add("PERSONAL");
        executor.execute(commandList);
        Assert.assertFalse(userSubscription==null);

        commandList.clear();
        commandList.add("ADD_TOPUP");
        commandList.add("FOUR_DEVICE");
        commandList.add("3");

        commandList.clear();
        commandList.add("ADD_TOPUP");
        commandList.add("FOUR_DEVICE");
        commandList.add("3");

        executor.execute(commandList);
        Assert.assertTrue(!userSubscription.getTopupPlan().getShortName().isEmpty());


    }

    @Test
    public void testPrintRenewal(){

        ArrayList<String> commandList = new ArrayList<String>();
        commandList.add("START_SUBSCRIPTION");
        commandList.add("10-02-2022");
        executor.execute(commandList);
        Assert.assertTrue(!userSubscription.getSubscriptionStartDate().isEmpty());

        commandList.clear();
        commandList.add("ADD_SUBSCRIPTION");
        commandList.add("MUSIC");
        commandList.add("PERSONAL");
        executor.execute(commandList);
        Assert.assertFalse(userSubscription==null);

        commandList.clear();
        commandList.add("ADD_TOPUP");
        commandList.add("FOUR_DEVICE");
        commandList.add("3");
        executor.execute(commandList);
        Assert.assertTrue(!userSubscription.getTopupPlan().getShortName().isEmpty());

        commandList.clear();
        commandList.add("PRINT_RENWAL_DETAILS");
        executor.execute(commandList);
        Assert.assertEquals(250,userSubscription.getTotalRenewalAmt());


    }

    @Test
    public void testStreamConstants(){
        Assert.assertEquals("RENEWAL_REMINDER", StreamConstants.RENEWAL_REMINDER);
        Assert.assertEquals("RENEWAL_AMOUNT", StreamConstants.RENEWAL_AMOUNT);
    }


    @Test
    public void testRenewalDate(){
        ArrayList<String> commandList = new ArrayList<String>();
        commandList.add("START_SUBSCRIPTION");
        commandList.add("10-02-2022");
        executor.execute(commandList);
        Assert.assertTrue(!userSubscription.getSubscriptionStartDate().isEmpty());

        commandList.clear();
        commandList.add("ADD_SUBSCRIPTION");
        commandList.add("MUSIC");
        commandList.add("PERSONAL");
        executor.execute(commandList);
        Assert.assertFalse(userSubscription==null);

        StreamCategory category = userSubscription.getSubscribedStreams().get(0);
        SubscriptionPlan plan = category.getSelectedPlan();
        String renewalDate = category.getRenewalDate("20-02-2022");
        Assert.assertEquals("10-03-2022",renewalDate);
    }


}
