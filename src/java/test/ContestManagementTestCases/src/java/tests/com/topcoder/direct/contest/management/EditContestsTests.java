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
public class EditContestsTests extends TestCase {
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
     * FTC 79: Verify contest can be edited successfully (draft contest, on the contest detail page).
     *
     * @throws Exception if any error
     */
    public void testFTC79() throws Exception {
    	String projectName = TestHelper.performSoftwareStep1(browser);
    	TestHelper.performSoftwareStep2(browser);
		browser.click("//a[@href='javascript:continueOverview();']");
		Thread.sleep(TestHelper.SLEEP);
		browser.click("//a[@href='javascript:saveAsDraftReview();']");
		Thread.sleep(TestHelper.SLEEP);
		browser.click("//div[@id='tabs0']/ul/li[2]/a/span");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("link=Client 1 Billing Account 1 Project 2");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		assertFalse("no error is expected", browser.isVisible("//div[@class='modalBody serverError']"));
		browser.click("link=" + projectName);
		browser.waitForPageToLoad(TestHelper.getTimeout());
    }

    /**
     * FTC 80: Verify contest can not be edited if activated already.
     *
     * @throws Exception if any error
     */
    public void testFTC80() throws Exception {
		browser.click("//div[@id='tabs0']/ul/li[2]/a/span");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("link=Client 1 Billing Account 1 Project 2");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		Thread.sleep(TestHelper.SLEEP*10);
		browser.click("link=Test Contest");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		assertTrue("edit button is not available", browser.isElementPresent("editBtn"));
    }

    /**
     * FTC 81: Verify contest can be edited successfully (during contest launching process).
     *
     * @throws Exception if any error
     */
    public void testFTC81() throws Exception {
    	TestHelper.performSoftwareStep1(browser);
		browser.click("//a[@href='javascript:backOverview();']");
		Thread.sleep(TestHelper.SLEEP);
		browser.type("id=contestName", "new name");
		browser.click("//a[@href='javascript:continueContestSelection();']");
		Thread.sleep(TestHelper.SLEEP);
    	TestHelper.performSoftwareStep2(browser);
		browser.click("//a[@href='javascript:continueOverview();']");
		Thread.sleep(TestHelper.SLEEP);
		browser.click("//a[@href='javascript:backReview();']");
		Thread.sleep(TestHelper.SLEEP);
		browser.type("dom=document.getElementById('swDetailedRequirements_ifr').contentDocument.body", "updated requirement");
		browser.click("//a[@href='javascript:continueOverview();']");
		Thread.sleep(TestHelper.SLEEP);
		assertEquals("contest information is incorrect", "new name", browser.getText("rswContestName"));
		assertEquals("contest information is incorrect", "updated requirement", browser.getText("rswDetailedRequirements"));
    }

    /**
     * FTC 82: Verify contest can be edited on step 3 while launching contest (actually any edit button pressed will lead to step 1 or step 2 again).
     *
     * @throws Exception if any error
     */
    public void testFTC82() throws Exception {
    	TestHelper.performSoftwareStep1(browser);
    	TestHelper.performSoftwareStep2(browser);
		browser.click("//a[@href='javascript:continueOverview();']");
		Thread.sleep(TestHelper.SLEEP);
		browser.click("css=#reviewSoftwarePage > div.contentList > dl > dd > a.tipLink > img[alt=\"Edit\"]");
		assertTrue("should be back to the step 2 editing page", browser.isElementPresent("dom=document.getElementById('swDetailedRequirements_ifr').contentDocument.body"));
    }

    /**
     * FTC 83: Verify contest can be edited on step 4 while launching contest (actually any edit button pressed will lead to step 1 or step 2 again).
     *
     * @throws Exception if any error
     */
    public void testFTC83() throws Exception {
    	TestHelper.performSoftwareStep1(browser);
    	TestHelper.performSoftwareStep2(browser);
		browser.click("//a[@href='javascript:continueOverview();']");
		Thread.sleep(TestHelper.SLEEP);
		browser.click("//a[@href='javascript:continueReview();']");
		Thread.sleep(TestHelper.SLEEP);
		browser.click("//div[@id='orderReviewSoftwarePage']/div/div/table/tbody/tr[4]/td/a/img");
		Thread.sleep(TestHelper.SLEEP);
		assertTrue("should be back to the step 1 editing page", browser.isTextPresent("Please Select your Contest Type"));
		browser.click("//a[@href='javascript:continueContestSelection();']");
		Thread.sleep(TestHelper.SLEEP);
		browser.click("//a[@href='javascript:continueOverview();']");
		Thread.sleep(TestHelper.SLEEP);
		browser.click("//a[@href='javascript:continueReview();']");
		Thread.sleep(TestHelper.SLEEP);
		browser.click("css=#orderReviewSoftwarePage > div.contestDetails > table.prizesTable > tbody > tr > td > a.tipLink > img[alt=\"Edit\"]");
		assertTrue("should be back to the step 2 editing page", browser.isElementPresent("dom=document.getElementById('swDetailedRequirements_ifr').contentDocument.body"));
    }

}
