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
public class DetailsTabTests extends TestCase {
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
     * FTC 84: Verify contest details can be displayed successfully (Conceptualization contest, multi-rounds).
     *
     * @throws Exception if any error
     */
    public void testFTC84() throws Exception {
    	String projectName = TestHelper.performSoftwareStep1(browser);
    	TestHelper.performSoftwareStep2(browser);
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
		assertTrue("the project information is incorrect", browser.isTextPresent("Software Conceptualization"));
		assertTrue("the project information is incorrect", browser.isTextPresent("Client 1 Billing Account 1 Project 2"));
		assertTrue("the project information is incorrect", browser.isTextPresent("Client 1 Billing Account 1"));
		assertTrue("the project information is incorrect", browser.isTextPresent("This is test contest."));
		assertTrue("the project information is incorrect", browser.isTextPresent("1,000.00"));
		assertTrue("the project information is incorrect", browser.isTextPresent("500.00"));
    }

    /**
     * FTC 85: Verify contest details can be displayed successfully (Conceptualization contest, single-round).
     *
     * @throws Exception if any error
     */
    public void testFTC85() throws Exception {
    	browser.click("link=Launch New Contest");
    	browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=div.selectedTxt");
		browser.click("link=Software Conceptualization");
		browser.check("id=lccCheckBox");
		String projectName = "Project " + System.currentTimeMillis();
		browser.type("id=contestName", projectName);
		browser.select("projects", "Client 1 Billing Account 1 Project 2");
		browser.select("billingProjects", "Client 1 Billing Account 1");
		browser.select("contestCopilot", "Unassigned");
		browser.select("roundTypes", "Contest will be run in single-rounds");
		browser.type("startDate", "11/22/2022");
		browser.click("//a[@href='javascript:continueContestSelection();']");
		Thread.sleep(TestHelper.SLEEP);
    	TestHelper.performSoftwareStep2(browser);
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
		assertTrue("the project information is incorrect", browser.isTextPresent("Software Conceptualization"));
		assertTrue("the project information is incorrect", browser.isTextPresent("Client 1 Billing Account 1 Project 2"));
		assertTrue("the project information is incorrect", browser.isTextPresent("Client 1 Billing Account 1"));
		assertTrue("the project information is incorrect", browser.isTextPresent("This is test contest."));
		assertTrue("the project information is incorrect", browser.isTextPresent("1,000.00"));
		assertTrue("the project information is incorrect", browser.isTextPresent("500.00"));
    }

    /**
     * FTC 86: Verify contest can be viewed successfully (Assembly contest).
     *
     * @throws Exception if any error
     */
    public void testFTC86() throws Exception {
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
		assertTrue("the project information is incorrect", browser.isTextPresent("Software Assembly"));
		assertTrue("the project information is incorrect", browser.isTextPresent("Client 1 Billing Account 1 Project 2"));
		assertTrue("the project information is incorrect", browser.isTextPresent("Client 1 Billing Account 1"));
		assertTrue("the project information is incorrect", browser.isTextPresent("This is test contest."));
		assertTrue("the project information is incorrect", browser.isTextPresent("1,000.00"));
		assertTrue("the project information is incorrect", browser.isTextPresent("500.00"));
    }

    /**
     * FTC 87: Verify contest details can be displayed successfully (Development contest, Dev-Only).
     *
     * @throws Exception if any error
     */
    public void testFTC87() throws Exception {
    	browser.click("link=Launch New Contest");
    	browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the create contest page should be displayed",
            browser.isTextPresent("Please Select your Contest Type"));
        browser.click("css=div.selectedTxt");
		browser.click("link=Component Development");
		browser.click("id=devOnlyCheckBox");
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
		browser.click("//div[@id='swCatalogDiv']/div/div/div");
		browser.click("link=.NET Custom");
		browser.addSelection("id=select1_categories", "label=.NET Custom");
		browser.click("id=addCategories");
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
		assertTrue("the project information is incorrect", browser.isTextPresent("Component Development"));
		assertTrue("the project information is incorrect", browser.isTextPresent("Client 1 Billing Account 1 Project 2"));
		assertTrue("the project information is incorrect", browser.isTextPresent("Client 1 Billing Account 1"));
		assertTrue("the project information is incorrect", browser.isTextPresent("This is test contest."));
		assertTrue("the project information is incorrect", browser.isTextPresent("500"));
		assertTrue("the project information is incorrect", browser.isTextPresent("250"));
    }

    /**
     * FTC 88: Verify contest details can be displayed successfully (Studio contest, multi-rounds).
     *
     * @throws Exception if any error
     */
    public void testFTC88() throws Exception {
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
		browser.select("roundTypes", "Contest will be run in multi-rounds");
		browser.type("startDate", "11/22/2022");
		browser.click("//a[@href='javascript:continueContestSelection();']");
		Thread.sleep(TestHelper.SLEEP);
		browser.type("dom=document.getElementById('contestIntroduction_ifr').contentDocument.body", "This is test contest.");
		browser.type("dom=document.getElementById('contestDescription_ifr').contentDocument.body", "This is test contest.");
		browser.type("dom=document.getElementById('round1Info_ifr').contentDocument.body", "This is test contest.");
		browser.type("dom=document.getElementById('round2Info_ifr').contentDocument.body", "This is test contest.");
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
		assertTrue("the project information is incorrect", browser.isTextPresent("Web Design"));
		assertTrue("the project information is incorrect", browser.isTextPresent("Client 1 Billing Account 1 Project 2"));
		assertTrue("the project information is incorrect", browser.isTextPresent("Client 1 Billing Account 1"));
		assertTrue("the project information is incorrect", browser.isTextPresent("This is test contest."));
		assertTrue("the project information is incorrect", browser.isTextPresent("1,250"));
		assertTrue("the project information is incorrect", browser.isTextPresent("250"));
    }

    /**
     * FTC 89: Verify contest details can be displayed successfully (Studio contest, single-round).
     *
     * @throws Exception if any error
     */
    public void testFTC89() throws Exception {
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
		assertTrue("the project information is incorrect", browser.isTextPresent("Web Design"));
		assertTrue("the project information is incorrect", browser.isTextPresent("Client 1 Billing Account 1 Project 2"));
		assertTrue("the project information is incorrect", browser.isTextPresent("Client 1 Billing Account 1"));
		assertTrue("the project information is incorrect", browser.isTextPresent("This is test contest."));
		assertTrue("the project information is incorrect", browser.isTextPresent("1,250"));
		assertTrue("the project information is incorrect", browser.isTextPresent("250"));
    }

}
