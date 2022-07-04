/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.contest.management;

import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

/**
 * Functional test for cockpit contest management.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class SpecReviewTabTests extends TestCase {
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
     * FTC 91: Verify contest spec review can be displayed successfully (Assembly contest, Spec Review is not started).
     *
     * @throws Exception if any error
     */
    public void testFTC91() throws Exception {
    	browser.click("link=Launch New Contest");
    	browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=div.selectedTxt");
		browser.click("link=Software Assembly");
		browser.check("id=lccCheckBox");
		String projectName = "Project " + System.currentTimeMillis();
		browser.type("id=contestName", projectName);
		browser.select("projects", "Client 1 Billing Account 1 Project 2");
		browser.select("billingProjects", "Client 1 Billing Account 1");
		browser.select("contestCopilot", "Unassigned");
		browser.type("startDate", "11/22/2022");
		browser.click("//a[@href='javascript:continueContestSelection();']");
		Thread.sleep(TestHelper.SLEEP);
		browser.type("dom=document.getElementById('swDetailedRequirements_ifr').contentDocument.body", "This is test contest.");
		browser.type("dom=document.getElementById('swGuidelines_ifr').contentDocument.body", "This is test contest.");
		browser.type("css=#swUploadButtonDiv > input[name='document']", TestHelper.getUploadFile());
		browser.type("swFileDescription", "This is requirement doc.");
		browser.click("swFileUploadBtn");
		Thread.sleep(TestHelper.SLEEP);
		browser.addSelection("id=masterTechnologiesSelect", "label=.NET 3.5");
		browser.click("id=addTechnologies");
		Thread.sleep(TestHelper.SLEEP);
		browser.click("//a[@href='javascript:continueOverview();']");
		Thread.sleep(TestHelper.SLEEP);
		browser.click("//a[@href='javascript:continueReview();']");
		Thread.sleep(TestHelper.SLEEP);
		browser.click("//a[@href='javascript:activateContest();']");
		Thread.sleep(TestHelper.SLEEP);
		browser.click("css=span.btnC");
		Thread.sleep(TestHelper.SLEEP);
		assertTrue("confirmation must be displayed", browser.isTextPresent("We have scheduled your competition and processed payment."));
		browser.click("//div[@id='tabs0']/ul/li[2]/a/span");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("link=Client 1 Billing Account 1 Project 2");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		Thread.sleep(TestHelper.SLEEP*10);
		browser.click("link=" + projectName);
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("css=li.on > a > span.left > span.right");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		assertTrue("spec review is not started", browser.isTextPresent("No Spec Review Now..."));
    }

    /**
     * FTC 92: Verify contest spec review can be displayed successfully (Assembly contest, Spec Review is started but no spec reviewer).
     *
     * @throws Exception if any error
     */
    public void testFTC92() throws Exception {
    	browser.click("link=Launch New Contest");
    	browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=div.selectedTxt");
		browser.click("link=Software Assembly");
		browser.check("id=lccCheckBox");
		String projectName = "Project " + System.currentTimeMillis();
		browser.type("id=contestName", projectName);
		browser.select("projects", "Client 1 Billing Account 1 Project 2");
		browser.select("billingProjects", "Client 1 Billing Account 1");
		browser.select("contestCopilot", "Unassigned");
		// browser.type("startDate", "11/22/2022");
		browser.click("//a[@href='javascript:continueContestSelection();']");
		Thread.sleep(TestHelper.SLEEP);
		browser.type("dom=document.getElementById('swDetailedRequirements_ifr').contentDocument.body", "This is test contest.");
		browser.type("dom=document.getElementById('swGuidelines_ifr').contentDocument.body", "This is test contest.");
		browser.type("css=#swUploadButtonDiv > input[name='document']", TestHelper.getUploadFile());
		browser.type("swFileDescription", "This is requirement doc.");
		browser.click("swFileUploadBtn");
		Thread.sleep(TestHelper.SLEEP);
		browser.addSelection("id=masterTechnologiesSelect", "label=.NET 3.5");
		browser.click("id=addTechnologies");
		Thread.sleep(TestHelper.SLEEP);
		browser.click("//a[@href='javascript:continueOverview();']");
		Thread.sleep(TestHelper.SLEEP);
		browser.click("//a[@href='javascript:continueReview();']");
		Thread.sleep(TestHelper.SLEEP);
		browser.click("//a[@href='javascript:activateContest();']");
		Thread.sleep(TestHelper.SLEEP);
		browser.click("css=span.btnC");
		Thread.sleep(TestHelper.SLEEP);
		assertTrue("confirmation must be displayed", browser.isTextPresent("We have scheduled your competition and processed payment."));
		browser.click("//div[@id='tabs0']/ul/li[2]/a/span");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("link=Client 1 Billing Account 1 Project 2");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		Thread.sleep(TestHelper.SLEEP*10);
		browser.click("link=" + projectName);
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("css=li.on > a > span.left > span.right");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		assertTrue("spec review is not started", browser.isTextPresent("No Spec Reviewer Now"));
    }

    /**
     * FTC 93: Verify contest spec review can be displayed successfully (Assembly contest, Spec Review is started but has not finished).
     *
     * @throws Exception if any error
     */
    public void testFTC93() throws Exception {
		browser.click("//div[@id='tabs0']/ul/li[2]/a/span");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("link=Client 1 Billing Account 1 Project 1");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("//div[@id='tabs1']/ul/li[2]/a/span");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("link=Client 1 Billing Account 1 Project 1 Design Contest 2");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("//div[@id='tabs3']/ul/li[2]/a/span/span");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		assertTrue("spec review is not in progress", browser.isTextPresent("Spec Review in Progress..."));
    }

    /**
     * FTC 94: Verify contest spec review can be displayed successfully (Assembly contest, Spec Review is finished).
     *
     * @throws Exception if any error
     */
    public void testFTC94() throws Exception {
		browser.click("//div[@id='tabs0']/ul/li[2]/a/span");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("link=Client 1 Billing Account 1 Project 1");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("//div[@id='tabs1']/ul/li[2]/a/span");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("link=Client 1 Billing Account 1 Project 1 Design Contest 3");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("//div[@id='tabs3']/ul/li[2]/a/span/span");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		assertTrue("spec review is not in progress", browser.isTextPresent("Spec Review is Complete"));
    }

    /**
     * FTC 96: Verify contest spec review can be displayed successfully (Web design contest, Spec Review is not started).
     *
     * @throws Exception if any error
     */
    public void testFTC96() throws Exception {
    	browser.click("link=Launch New Contest");
    	browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the create contest page should be displayed",
            browser.isTextPresent("Please Select your Contest Type"));
        browser.click("css=div.selectedTxt");
		browser.click("link=Web Design");
		String projectName = "Project " + System.currentTimeMillis();
		browser.type("id=contestName", projectName);
		browser.select("projects", "Client 1 Billing Account 1 Project 2");
		browser.select("billingProjects", "Client 1 Billing Account 1");
		browser.select("contestCopilot", "Unassigned");
		browser.select("roundTypes", "Contest will be run in single-rounds");
		browser.type("startDate", "11/22/2022");
		browser.click("//a[@href='javascript:continueContestSelection();']");
		Thread.sleep(TestHelper.SLEEP);
		browser.type("dom=document.getElementById('contestIntroduction_ifr').contentDocument.body", "This is test contest.");
		browser.type("dom=document.getElementById('contestDescription_ifr').contentDocument.body", "This is test contest.");
		browser.type("css=#swUploadButtonDiv > input[name='document']", TestHelper.getUploadFile());
		browser.type("swFileDescription", "This is requirement doc.");
		browser.click("swFileUploadBtn");
		Thread.sleep(TestHelper.SLEEP);
		browser.click("//a[@href='javascript:continueOverview();']");
		Thread.sleep(TestHelper.SLEEP);
		browser.click("//a[@href='javascript:continueReview();']");
		Thread.sleep(TestHelper.SLEEP);
		browser.click("//a[@href='javascript:activateContest();']");
		Thread.sleep(TestHelper.SLEEP);
		browser.click("css=span.btnC");
		Thread.sleep(TestHelper.SLEEP);
		assertTrue("confirmation must be displayed", browser.isTextPresent("We have scheduled your competition and processed payment."));
		browser.click("//div[@id='tabs0']/ul/li[2]/a/span");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("link=Client 1 Billing Account 1 Project 2");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		Thread.sleep(TestHelper.SLEEP*10);
		browser.click("link=" + projectName);
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("css=li.on > a > span.left > span.right");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		assertTrue("spec review is not started", browser.isTextPresent("No Spec Review Now..."));
    }

    /**
     * FTC 97: Verify contest spec review can be displayed successfully (Web design contest, Spec Review is started but has not finished).
     *
     * @throws Exception if any error
     */
    public void testFTC97() throws Exception {
    	browser.open("/direct/contest/detail?projectId=10000211");
    	browser.click("link=Spec Review");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		assertTrue("spec review is not started", browser.isTextPresent("Spec Review in Progress... "));
    }

}
