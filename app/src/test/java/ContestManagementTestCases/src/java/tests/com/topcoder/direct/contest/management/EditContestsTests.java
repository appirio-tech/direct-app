/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.contest.management;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.thoughtworks.selenium.Selenium;

/**
 * Functional test for cockpit contest management.
 *
 * @author gjw99
 * @version 1.1
 * @since 1.0
 */
public class EditContestsTests {
    /** Represents the Selenium Instance. */
    private Selenium browser;

    /**
     * <p>Adapter for earlier versions of JUnit.</p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(EditContestsTests.class);
    }

    /**
     * Create new Project.
     */
    @BeforeClass
    public static void prepare() throws Exception {
        Selenium selenium = TestHelper.getIndexPage();
        TestHelper.loginUser(selenium);
    	selenium.click("link=Launch Contest");
    	selenium.waitForPageToLoad(TestHelper.getTimeout());
    	selenium.click("css=span.right");
		Thread.sleep(TestHelper.SLEEP);
    	selenium.type("id=newProjectName", "test");
    	selenium.type("id=newProjectDescription", "test");
    	selenium.click("css=#addNewProjectModal > div.modalBody > div.modalCommandBox > a.newButton1 > span.btnR > span.btnC");
		Thread.sleep(TestHelper.SLEEP);
    	selenium.click("css=span.btnC");
		Thread.sleep(TestHelper.SLEEP);
		selenium.stop();
    }

    /**
     * Clear the project from DB.
     */
    @AfterClass
    public static void clear() throws Exception {
        TestHelper.clearProject();
    }

    /**
     * Sets up the testing environment.
     *
     * @throws Exception if any error occurs.
     */
    @Before
    public void setUp() throws Exception {
        browser = TestHelper.getIndexPage();
        TestHelper.loginUser(browser);
    }

    /**
     * Tears down the testing environment.
     *
     * @throws Exception if any error occurs.
     */
    @After
    public void tearDown() throws Exception {
        browser.stop();
        TestHelper.tearDown();
    }

    /**
     * FTC 79: Verify contest can be edited successfully (draft contest, on the contest detail page).
     *
     * @throws Exception if any error
     */
    @Test
    public void testFTC79() throws Exception {
    	TestHelper.performSoftwareStep1(browser);
    	TestHelper.performSoftwareStep2(browser);
		browser.click("//a[@href='javascript:continueOverview();']");
		Thread.sleep(TestHelper.SLEEP);
		browser.click("//a[@href='javascript:saveAsDraftReview();']");
		Thread.sleep(TestHelper.SLEEP);
		browser.click("//div[@id='tabs0']/ul/li[2]/a/span");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("link=test");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("link=Edit");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("css=div.datePickerView.disable > input[name=\"projectDuration\"]");
		browser.type("startDate", "11/22/2022");
		browser.type("endDate", "11/26/2022");
		browser.click("css=div.editProjectSaveBtnContainer > a[name=\"saveProject\"] > span");
		browser.waitForPopUp("_self", TestHelper.getTimeout());
    }

    /**
     * FTC 80: Verify contest can not be edited if activated already.
     *
     * @throws Exception if any error
     */
    @Test
    public void testFTC80() throws Exception {
		browser.click("//div[@id='tabs0']/ul/li[2]/a/span");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("link=test");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		Thread.sleep(TestHelper.SLEEP*10);
    }

    /**
     * FTC 81: Verify contest can be edited successfully (during contest launching process).
     *
     * @throws Exception if any error
     */
    @Test
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
		browser.runScript("CKEDITOR.instances['swDetailedRequirements'].setData('updated requirement');");
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
    @Test
    public void testFTC82() throws Exception {
    	TestHelper.performSoftwareStep1(browser);
    	TestHelper.performSoftwareStep2(browser);
		browser.click("//a[@href='javascript:continueOverview();']");
		Thread.sleep(TestHelper.SLEEP);
		browser.click("css=#reviewSoftwarePage > div.contentList > dl > dd > a.tipLink > img[alt=\"Edit\"]");
		assertTrue("should be back to the step 2 editing page", browser.isElementPresent("swDetailedRequirements"));
    }

    /**
     * FTC 83: Verify contest can be edited on step 4 while launching contest (actually any edit button pressed will lead to step 1 or step 2 again).
     *
     * @throws Exception if any error
     */
    @Test
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
		assertTrue("should be back to the step 2 editing page", browser.isElementPresent("swDetailedRequirements"));
    }

}
