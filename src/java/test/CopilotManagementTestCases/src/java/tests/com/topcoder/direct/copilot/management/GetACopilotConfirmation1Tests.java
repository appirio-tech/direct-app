/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.copilot.management;

import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;


/**
 * Functional test for cockpit copilot management.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GetACopilotConfirmation1Tests extends TestCase {
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
        super.tearDown();
    }

    /**
     * Verify the user can go to confirmation page by pressing "Submit" button on summary page .
     *
     * @throws Exception if any error
     */
    public void testFTC84() throws Exception {
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
        Thread.sleep(TestHelper.SLEEP);
        browser.click("link=YES, POST NOW");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("name=amount");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.select("id=billingProjects", "label=Liquid (CA)");
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("submitButton");
        Thread.sleep(TestHelper.SLEEP * 10);
        assertEquals("the page content is incorrect", "Confirmation", browser.getText("//div[@class='stepTitle']/h3"));
        assertTrue("the page content is incorrect",
            browser.isTextPresent(
                "The copilot Oppurtunity has been posted. It will be getting published"
                    + " after being review by Topcoder Platform Manager "));
    }

    /**
     * Verify breadcrumb is correct on Confirmation page .
     *
     * @throws Exception if any error
     */
    public void testFTC85() throws Exception {
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
        Thread.sleep(TestHelper.SLEEP);
        browser.click("link=YES, POST NOW");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("name=amount");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.select("id=billingProjects", "label=Liquid (CA)");
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("submitButton");
        Thread.sleep(TestHelper.SLEEP * 10);
        assertTrue("the page content is incorrect", browser.isTextPresent("Dashboard > Copilots > Post a Copilot"));
        browser.click("//a[contains(text(),'Copilots')]");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the introduction must exist",
            browser.isTextPresent(
                "A Copilot is a TopCoder Member who manages the TopCoder process for a customer in order to deliver"
                    + " a requested asset. For example, a customer may ask to build a website. A Copilot will work with"
                    + " that customer to agree on a plan and pricing to build that website and then they would manage"
                    + " the process using the TopCoder Platform to deliver the website back to the customer."));
    }

    /**
     * Verify "Back to Dashboard" button on Confirmation page is working well .
     *
     * @throws Exception if any error
     */
    public void testFTC86() throws Exception {
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
        Thread.sleep(TestHelper.SLEEP);
        browser.click("link=YES, POST NOW");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("name=amount");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.select("id=billingProjects", "label=Liquid (CA)");
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("submitButton");
        Thread.sleep(TestHelper.SLEEP * 10);
        browser.click("link=Back to Dashboard");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the page content is incorrect", browser.isTextPresent("Dashboard > All Active Contests"));
        assertTrue("the page content is incorrect",
                browser.isElementPresent("//img[@src='/images/dashboard_logo.png']"));
    }

    /**
     * Verify click "View Contest" link on Confirmation page will navigate to contest page   .
     *
     * @throws Exception if any error
     */
    public void testFTC87() throws Exception {
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
        Thread.sleep(TestHelper.SLEEP);
        browser.click("link=YES, POST NOW");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("name=amount");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.select("id=billingProjects", "label=Liquid (CA)");
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("submitButton");
        Thread.sleep(TestHelper.SLEEP * 10);
        browser.click("previewButton");
        Thread.sleep(TestHelper.SLEEP * 10);
        assertEquals("a preview window should be displayed", 2, browser.getAllWindowIds().length);
    }

    /**
     * Verify click "Forum" link on Confirmation page will open the corresponding forum  .
     *
     * @throws Exception if any error
     */
    public void testFTC88() throws Exception {
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
        Thread.sleep(TestHelper.SLEEP);
        browser.click("link=YES, POST NOW");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("name=amount");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.select("id=billingProjects", "label=Liquid (CA)");
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("submitButton");
        Thread.sleep(TestHelper.SLEEP * 10);
        assertTrue("the forum link is absent", browser.isElementPresent("forumButton"));
    }
}
