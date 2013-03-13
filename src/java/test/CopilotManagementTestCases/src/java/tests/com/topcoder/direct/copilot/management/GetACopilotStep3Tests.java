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
public class GetACopilotStep3Tests extends TestCase {
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
     * Verify the user can go to get a copilot step 3 page by pressing "Next Step" button after input valid
     * value .
     *
     * @throws Exception if any error
     */
    public void testFTC56() throws Exception {
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
        browser.click("name=amount");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the page content is incorrect", browser.isTextPresent("Dashboard > Copilots > Post a Copilot"));
        assertEquals("the page content is incorrect", "3Billing", browser.getText("//div[@class='stepTitle']"));
    }

    /**
     * Verify breadcrumb is correct on Get a copilot step 3 page .
     *
     * @throws Exception if any error
     */
    public void testFTC57() throws Exception {
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
        browser.click("name=amount");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
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
     * Verify "Back to Dashboard" button on Get a copilot step 3 page is working well .
     *
     * @throws Exception if any error
     */
    public void testFTC59() throws Exception {
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
        browser.click("name=amount");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the page content is incorrect", browser.isTextPresent("Dashboard > Copilots > Post a Copilot"));
        browser.click("link=Back to Dashboard");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the page content is incorrect", browser.isTextPresent("Dashboard > All Active Contests"));
        assertTrue("the page content is incorrect",
                browser.isElementPresent("//img[@src='/images/dashboard_logo.png']"));
    }

    /**
     * Verify "Prev Step" button on Get a copilot step 3 page is working well .
     *
     * @throws Exception if any error
     */
    public void testFTC60() throws Exception {
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
        browser.click("name=amount");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("link=PREV STEP");
        Thread.sleep(TestHelper.SLEEP);
        assertEquals("the page content is incorrect", "2Schedule", browser.getText("//div[@class='stepTitle']"));
    }

    /**
     * Verify user can go to Step 1 page by pressing "Step 1" link  .
     *
     * @throws Exception if any error
     */
    public void testFTC61() throws Exception {
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
        browser.click("name=amount");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=div.BottomBar > a.nextStepButton");
        browser.click("id=stepLink_1");
        Thread.sleep(TestHelper.SLEEP);
        assertEquals("the page content is incorrect", "1Basic", browser.getText("//div[@class='stepTitle']"));
    }

    /**
     * Verify user can go to Step 2 page by pressing "Step 2" link  .
     *
     * @throws Exception if any error
     */
    public void testFTC62() throws Exception {
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
        browser.click("name=amount");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=div.BottomBar > a.nextStepButton");
        browser.click("id=stepLink_4");
        Thread.sleep(TestHelper.SLEEP);
        assertEquals("the page content is incorrect", "2Schedule", browser.getText("//div[@class='stepTitle']"));
    }

    /**
     * Verify all fields can be displayed on Get a copilot step 3 page .
     *
     * @throws Exception if any error
     */
    public void testFTC63() throws Exception {
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
        browser.click("name=amount");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=div.BottomBar > a.nextStepButton");
        assertTrue("the page content is incorrect", browser.isTextPresent("Billing Account"));
        assertTrue("the page content is incorrect", browser.isElementPresent("billingProjects"));
    }

    /**
     * Verify "Save As Draft" button on get a copilot step 3 page is working well .
     *
     * @throws Exception if any error
     */
    public void testFTC64() throws Exception {
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
        browser.click("name=amount");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=div.BottomBar > a.nextStepButton");
        browser.click("css=span.right");
        Thread.sleep(TestHelper.SLEEP * 10);
        assertTrue("the page content is incorrect", browser.isVisible("demoModal"));
        assertTrue("the page content is incorrect",
            browser.isTextPresent("Your Copilot Posting " + contest + " has been saved as draft successfully."));
        browser.click("css=span.btnC");
        assertFalse("the page content is incorrect", browser.isVisible("demoModal"));
    }

    /**
     * Verify step 3 form validation is working well(Empty billing account) .
     *
     * @throws Exception if any error
     */
    public void testFTC65() throws Exception {
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
        browser.click("name=amount");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=div.BottomBar > a.nextStepButton");
        browser.click("css=span.right");
        Thread.sleep(TestHelper.SLEEP * 10);
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("error popup should be displayed", browser.isVisible("errortModal"));
        assertTrue("error popup should be displayed",
            browser.isTextPresent("Select your billing account could not be empty."));
    }
}
