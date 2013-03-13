/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.copilot.management;

import com.thoughtworks.selenium.Selenium;

import junit.framework.TestCase;


/**
 * Functional test for cockpit copilot management.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GetACopilotStep2Tests extends TestCase {
    /** Represents the Selenium Instance. */
    private Selenium browser;

    /**
     * Sets up the testing environment.
     *
     * @throws Exception if any error occurs.
     */
    public void setUp() throws Exception {
        browser = TestHelper.getIndexPage();
        TestHelper.loginUser(browser);
        super.setUp();
    }

    /**
     * Tears down the testing environment.
     *
     * @throws Exception if any error occurs.
     */
    public void tearDown() throws Exception {
        browser.stop();
        TestHelper.tearDown();
        super.tearDown();
    }

    /**
     * Verify the user can go to get a copilot step 2 page by pressing "Next Step" button after input valid
     * value .
     *
     * @throws Exception if any error
     */
    public void testFTC41() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("id=projects", "label=Client 1 Billing Account 1 Project 2");
        browser.type("dom=document.getElementById('allDescription_ifr').contentDocument.body",
                "Test Public Description");
        browser.type("dom=document.getElementById('privateDescription_ifr').contentDocument.body",
            "Test Specification Description");

        String contest = "Contest " + System.currentTimeMillis();
        browser.type("id=contestName", contest);
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP * 10);
        assertTrue("the page content is incorrect", browser.isTextPresent("Dashboard > Copilots > Post a Copilot"));
        assertEquals("the page content is incorrect", "2Schedule", browser.getText("//div[@class='stepTitle']"));
    }

    /**
     * Verify breadcrumb is correct on Get a copilot step 2 page .
     *
     * @throws Exception if any error
     */
    public void testFTC42() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("id=projects", "label=Client 1 Billing Account 1 Project 2");
        browser.type("dom=document.getElementById('allDescription_ifr').contentDocument.body",
                "Test Public Description");
        browser.type("dom=document.getElementById('privateDescription_ifr').contentDocument.body",
            "Test Specification Description");

        String contest = "Contest " + System.currentTimeMillis();
        browser.type("id=contestName", contest);
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP * 10);
        assertTrue("the page content is incorrect", browser.isTextPresent("Dashboard > Copilots > Post a Copilot"));
        browser.click("//a[contains(text(),'Copilots')]");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the introduction must exist",
            browser.isTextPresent(
                "A Copilot is a TopCoder Member who manages the TopCoder process for a customer in order to"
                    + " deliver a requested asset. For example, a customer may ask to build a website. A Copilot"
                    + " will work with that customer to agree on a plan and pricing to build that website and"
                    + " then they would manage the process using the TopCoder Platform to deliver the website"
                    + " back to the customer."));
    }

    /**
     * Verify "Back to Dashboard" button on Get a copilot step 2 page is working well .
     *
     * @throws Exception if any error
     */
    public void testFTC44() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("id=projects", "label=Client 1 Billing Account 1 Project 2");
        browser.type("dom=document.getElementById('allDescription_ifr').contentDocument.body",
                "Test Public Description");
        browser.type("dom=document.getElementById('privateDescription_ifr').contentDocument.body",
            "Test Specification Description");

        String contest = "Contest " + System.currentTimeMillis();
        browser.type("id=contestName", contest);
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP * 10);
        browser.click("link=Back to Dashboard");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the page content is incorrect",
                browser.isTextPresent("Dashboard > All Active Contests"));
        assertTrue("the page content is incorrect",
                browser.isElementPresent("//img[@src='/images/dashboard_logo.png']"));
    }

    /**
     * Verify "Prev Step" button on Get a copilot step 2 page is working well .
     *
     * @throws Exception if any error
     */
    public void testFTC45() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("id=projects", "label=Client 1 Billing Account 1 Project 2");
        browser.type("dom=document.getElementById('allDescription_ifr').contentDocument.body",
                "Test Public Description");
        browser.type("dom=document.getElementById('privateDescription_ifr').contentDocument.body",
            "Test Specification Description");

        String contest = "Contest " + System.currentTimeMillis();
        browser.type("id=contestName", contest);
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP * 10);
        browser.click("link=PREV STEP");
        Thread.sleep(TestHelper.SLEEP);
        assertEquals("the page content is incorrect", "1Basic", browser.getText("//div[@class='stepTitle']"));
    }

    /**
     * Verify user can go to Step 1 page by pressing "Step 1" link  .
     *
     * @throws Exception if any error
     */
    public void testFTC46() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("id=projects", "label=Client 1 Billing Account 1 Project 2");
        browser.type("dom=document.getElementById('allDescription_ifr').contentDocument.body",
                "Test Public Description");
        browser.type("dom=document.getElementById('privateDescription_ifr').contentDocument.body",
            "Test Specification Description");

        String contest = "Contest " + System.currentTimeMillis();
        browser.type("id=contestName", contest);
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP * 10);
        browser.click("id=stepLink_1");
        Thread.sleep(TestHelper.SLEEP);
        assertEquals("the page content is incorrect", "1Basic", browser.getText("//div[@class='stepTitle']"));
    }

    /**
     * Verify all fields can be displayed on Get a copilot step 2 page .
     *
     * @throws Exception if any error
     */
    public void testFTC47() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("id=projects", "label=Client 1 Billing Account 1 Project 2");
        browser.type("dom=document.getElementById('allDescription_ifr').contentDocument.body",
                "Test Public Description");
        browser.type("dom=document.getElementById('privateDescription_ifr').contentDocument.body",
            "Test Specification Description");

        String contest = "Contest " + System.currentTimeMillis();
        browser.type("id=contestName", contest);
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP * 10);
        assertTrue("the page content is incorrect", browser.isElementPresent("link=YES, POST NOW"));
        assertTrue("the page content is incorrect", browser.isElementPresent("link=YES, DO IT LATER"));
    }

    /**
     * Verify there will be some elements displayed after click "Yes, Post Now" button .
     *
     * @throws Exception if any error
     */
    public void testFTC48() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("id=projects", "label=Client 1 Billing Account 1 Project 2");
        browser.type("dom=document.getElementById('allDescription_ifr').contentDocument.body",
                "Test Public Description");
        browser.type("dom=document.getElementById('privateDescription_ifr').contentDocument.body",
            "Test Specification Description");

        String contest = "Contest " + System.currentTimeMillis();
        browser.type("id=contestName", contest);
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP * 10);
        browser.click("link=YES, POST NOW");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the page content is incorrect",
            browser.isTextPresent(
                "Most copilot postings are listed with $150 first place and $75 second place"
                    + " payments for a total cost of $225."));
        assertTrue("the page content is incorrect",
            browser.isTextPresent("Would you like to proceed with this amount or set your own amount?"));
        assertTrue("the page content is incorrect", browser.isElementPresent("amount"));
    }

    /**
     * Verify there will be some elements displayed after click "Yes, Do it later" button .
     *
     * @throws Exception if any error
     */
    public void testFTC49() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("id=projects", "label=Client 1 Billing Account 1 Project 2");
        browser.type("dom=document.getElementById('allDescription_ifr').contentDocument.body",
                "Test Public Description");
        browser.type("dom=document.getElementById('privateDescription_ifr').contentDocument.body",
            "Test Specification Description");

        String contest = "Contest " + System.currentTimeMillis();
        browser.type("id=contestName", contest);
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP * 10);
        browser.click("link=YES, DO IT LATER");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the page content is incorrect", browser.isTextPresent("Most copilot posting are listed for $150."));
        assertTrue("the page content is incorrect", browser.isElementPresent("amount"));
    }

    /**
     * Verify "Save As Draft" button on get a copilot step 2 page is working well .
     *
     * @throws Exception if any error
     */
    public void testFTC50() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("id=projects", "label=Client 1 Billing Account 1 Project 2");
        browser.type("dom=document.getElementById('allDescription_ifr').contentDocument.body",
                "Test Public Description");
        browser.type("dom=document.getElementById('privateDescription_ifr').contentDocument.body",
            "Test Specification Description");

        String contest = "Contest " + System.currentTimeMillis();
        browser.type("id=contestName", contest);
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP * 10);
        browser.click("css=span.right");
        Thread.sleep(TestHelper.SLEEP * 10);
        assertTrue("the page content is incorrect", browser.isVisible("demoModal"));
        assertTrue("the page content is incorrect",
            browser.isTextPresent("Your Copilot Posting " + contest + " has been saved as draft successfully."));
        browser.click("css=span.btnC");
        assertFalse("the page content is incorrect", browser.isVisible("demoModal"));
    }

    /**
     * Verify step 2 form validation is working well(Invalid amount on "Yes, Post Now" section) .
     *
     * @throws Exception if any error
     */
    public void testFTC51() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("id=projects", "label=Client 1 Billing Account 1 Project 2");
        browser.type("dom=document.getElementById('allDescription_ifr').contentDocument.body",
                "Test Public Description");
        browser.type("dom=document.getElementById('privateDescription_ifr').contentDocument.body",
            "Test Specification Description");

        String contest = "Contest " + System.currentTimeMillis();
        browser.type("id=contestName", contest);
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP * 10);
        browser.click("link=YES, POST NOW");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("//div[@id='stepContainer']/div[4]/div/div[5]/div/div[2]/div[3]/div[2]/div/ul/li[2]/input");
        browser.type("css=input.amountText", "fffff");
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("error popup should be displayed", browser.isVisible("errortModal"));
        assertTrue("error popup should be displayed", browser.isTextPresent("The amount is invalid."));
    }

    /**
     * Verify step 2 form validation is working well(Not select option on "Yes, Post Now" section) .
     *
     * @throws Exception if any error
     */
    public void testFTC52() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("id=projects", "label=Client 1 Billing Account 1 Project 2");
        browser.type("dom=document.getElementById('allDescription_ifr').contentDocument.body",
                "Test Public Description");
        browser.type("dom=document.getElementById('privateDescription_ifr').contentDocument.body",
            "Test Specification Description");

        String contest = "Contest " + System.currentTimeMillis();
        browser.type("id=contestName", contest);
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP * 10);
        browser.click("link=YES, POST NOW");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("error popup should be displayed", browser.isVisible("errortModal"));
        assertTrue("error popup should be displayed", browser.isTextPresent("Please set the amount."));
    }

    /**
     * Verify step 2 form validation is working well(Invalid amount on "Yes, Do it later" section) .
     *
     * @throws Exception if any error
     */
    public void testFTC53() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("id=projects", "label=Client 1 Billing Account 1 Project 2");
        browser.type("dom=document.getElementById('allDescription_ifr').contentDocument.body",
                "Test Public Description");
        browser.type("dom=document.getElementById('privateDescription_ifr').contentDocument.body",
            "Test Specification Description");

        String contest = "Contest " + System.currentTimeMillis();
        browser.type("id=contestName", contest);
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP * 10);
        browser.click("link=YES, DO IT LATER");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("//div[@id='stepContainer']/div[4]/div/div[5]/div/div[2]/div[3]/div[4]/div/ul/li[2]/input");
        browser.type("css=input.text.amountText", "fffffff");
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("error popup should be displayed", browser.isVisible("errortModal"));
        assertTrue("error popup should be displayed", browser.isTextPresent("The amount is invalid."));
    }

    /**
     * Verify step 2 form validation is working well(Not select option on "Yes, Do it later" section) .
     *
     * @throws Exception if any error
     */
    public void testFTC54() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("id=projects", "label=Client 1 Billing Account 1 Project 2");
        browser.type("dom=document.getElementById('allDescription_ifr').contentDocument.body",
                "Test Public Description");
        browser.type("dom=document.getElementById('privateDescription_ifr').contentDocument.body",
            "Test Specification Description");

        String contest = "Contest " + System.currentTimeMillis();
        browser.type("id=contestName", contest);
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP * 10);
        browser.click("link=YES, DO IT LATER");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("error popup should be displayed", browser.isVisible("errortModal"));
        assertTrue("error popup should be displayed", browser.isTextPresent("Please set the amount."));
    }

    /**
     * Verify step 2 form validation is working well(Start Date before current time) .
     *
     * @throws Exception if any error
     */
    public void testFTC55() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("id=projects", "label=Client 1 Billing Account 1 Project 2");
        browser.type("dom=document.getElementById('allDescription_ifr').contentDocument.body",
                "Test Public Description");
        browser.type("dom=document.getElementById('privateDescription_ifr').contentDocument.body",
            "Test Specification Description");

        String contest = "Contest " + System.currentTimeMillis();
        browser.type("id=contestName", contest);
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP * 10);
        browser.click("link=YES, DO IT LATER");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("error popup should be displayed", browser.isVisible("errortModal"));
        assertTrue("error popup should be displayed",
                browser.isTextPresent("The start date should after current time"));
    }
}
