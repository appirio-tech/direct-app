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
public class SubmissionsTabTests extends TestCase {
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
     * FTC 115: Verify the page in submissions tab is empty if there is no submission submitted .
     *
     * @throws Exception if any error
     */
    public void testFTC115() throws Exception {
		browser.click("//div[@id='tabs0']/ul/li[2]/a/span");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("link=Client 1 Billing Account 1 Project 1");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("//div[@id='tabs1']/ul/li[2]/a/span");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("//table[@id='ProjectContests']/tbody/tr[2]/td[6]/a");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		assertEquals("no submission found", "", browser.getText("//tr[@class='reviewerScoreBg']"));
		assertTrue("no submission found", browser.isElementPresent("//table[@class='softwareStats']"));
		assertEquals("the table column is incorrect", "Handle", browser.getText("//table[@class='softwareStats']/thead/tr/th[2]"));
		assertEquals("the table column is incorrect", "ID", browser.getText("//table[@class='softwareStats']/thead/tr/th[3]"));
		assertEquals("the table column is incorrect", "Date Submitted", browser.getText("//table[@class='softwareStats']/thead/tr/th[4]"));
		assertEquals("the table column is incorrect", "Screening Score", browser.getText("//table[@class='softwareStats']/thead/tr/th[5]"));
		assertEquals("the table column is incorrect", "Initial Score", browser.getText("//table[@class='softwareStats']/thead/tr/th[6]"));
		assertEquals("the table column is incorrect", "Final Score", browser.getText("//table[@class='softwareStats']/thead/tr/th[7]"));
		assertEquals("the table column is incorrect", "Reviewer's Score", browser.getText("//table[@class='softwareStats']/thead/tr/th[8]"));
    }

    /**
     * FTC 117: Verify user handle can be clicked on Submissions tab.
     *
     * @throws Exception if any error
     */
    public void testFTC117() throws Exception {
		browser.click("//div[@id='tabs0']/ul/li[2]/a/span");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("link=Client 1 Billing Account 1 Project 1");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("//div[@id='tabs1']/ul/li[2]/a/span");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("//table[@id='ProjectContests']/tbody/tr[4]/td[6]/a");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		assertTrue("the handle link must exist", browser.isElementPresent("link=Hung"));
    }

    /**
     * FTC 118: Verify user's review score is unavailable on Submissions tab if the review phase is not started.
     *
     * @throws Exception if any error
     */
    public void testFTC118() throws Exception {
		browser.click("//div[@id='tabs0']/ul/li[2]/a/span");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("link=Client 1 Billing Account 1 Project 1");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("//div[@id='tabs1']/ul/li[2]/a/span");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("//table[@id='ProjectContests']/tbody/tr[4]/td[6]/a");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		assertEquals("the table column is incorrect", "n/a", browser.getText("//table[@class='softwareStats']/tbody/tr/td[5]"));
		assertEquals("the table column is incorrect", "n/a", browser.getText("//table[@class='softwareStats']/tbody/tr/td[6]"));
		assertEquals("the table column is incorrect", "n/a", browser.getText("//table[@class='softwareStats']/tbody/tr/td[7]"));
		assertEquals("the table column is incorrect", "n/a", browser.getText("//table[@class='softwareStats']/tbody/tr/td[8]"));
		assertEquals("the table column is incorrect", "n/a", browser.getText("//table[@class='softwareStats']/tbody/tr/td[9]"));
		assertEquals("the table column is incorrect", "n/a", browser.getText("//table[@class='softwareStats']/tbody/tr/td[10]"));
    }

    /**
     * FTC 119: Verify user's review score is available on Submissions tab if the review is finished.
     *
     * @throws Exception if any error
     */
    public void testFTC119() throws Exception {
		browser.click("//div[@id='tabs0']/ul/li[2]/a/span");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("link=Client 1 Billing Account 1 Project 1");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("//div[@id='tabs1']/ul/li[2]/a/span");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("//table[@id='ProjectContests']/tbody/tr[9]/td[6]/a");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		assertEquals("the table column is incorrect", "100.00", browser.getText("//table[@class='softwareStats']/tbody/tr/td[5]"));
		assertEquals("the table column is incorrect", "91.67", browser.getText("//table[@class='softwareStats']/tbody/tr/td[6]"));
		assertEquals("the table column is incorrect", "91.67", browser.getText("//table[@class='softwareStats']/tbody/tr/td[7]"));
		assertEquals("the table column is incorrect", "100.00", browser.getText("//table[@class='softwareStats']/tbody/tr/td[8]"));
		assertEquals("the table column is incorrect", "100.00", browser.getText("//table[@class='softwareStats']/tbody/tr/td[9]"));
		assertEquals("the table column is incorrect", "75.00", browser.getText("//table[@class='softwareStats']/tbody/tr/td[10]"));
    }

    /**
     * FTC 120: Verify reviewers' handles can be clicked on Submissions tab.
     *
     * @throws Exception if any error
     */
    public void testFTC120() throws Exception {
		browser.click("//div[@id='tabs0']/ul/li[2]/a/span");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("link=Client 1 Billing Account 1 Project 1");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("//div[@id='tabs1']/ul/li[2]/a/span");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("//table[@id='ProjectContests']/tbody/tr[9]/td[6]/a");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		assertTrue("the reviewer link must exist", browser.isElementPresent("link=reassembler"));
		assertTrue("the reviewer link must exist", browser.isElementPresent("link=ksmith"));
		assertTrue("the reviewer link must exist", browser.isElementPresent("link=wyzmo"));
    }

    /**
     * FTC 121: Verify user's review score is available on Submissions tab if the review is finished.
     *
     * @throws Exception if any error
     */
    public void testFTC121() throws Exception {
		browser.click("//div[@id='tabs0']/ul/li[2]/a/span");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("link=Client 1 Billing Account 1 Project 1");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("//div[@id='tabs1']/ul/li[2]/a/span");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("//table[@id='ProjectContests']/tbody/tr[9]/td[6]/a");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		assertEquals("the table column is incorrect", "100.00", browser.getText("//table[@class='softwareStats']/tbody/tr/td[5]"));
		assertEquals("the table column is incorrect", "91.67", browser.getText("//table[@class='softwareStats']/tbody/tr/td[6]"));
		assertEquals("the table column is incorrect", "91.67", browser.getText("//table[@class='softwareStats']/tbody/tr/td[7]"));
		assertEquals("the table column is incorrect", "100.00", browser.getText("//table[@class='softwareStats']/tbody/tr/td[8]"));
		assertEquals("the table column is incorrect", "100.00", browser.getText("//table[@class='softwareStats']/tbody/tr/td[9]"));
		assertEquals("the table column is incorrect", "75.00", browser.getText("//table[@class='softwareStats']/tbody/tr/td[10]"));
    }

}
