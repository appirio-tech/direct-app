/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.contest.management;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

/**
 * Functional test for cockpit contest management.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class RegistraintsTabTests extends TestCase {
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
     * FTC 100: Verify registrants list is empty if no registrants in the Registrants tab.
     *
     * @throws Exception if any error
     */
    public void testFTC100() throws Exception {
		browser.click("//div[@id='tabs0']/ul/li[2]/a/span");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("link=Client 1 Billing Account 1 Project 1");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("//div[@id='tabs1']/ul/li[2]/a/span");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("//table[@id='ProjectContests']/tbody/tr[2]/td[5]/a");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		assertTrue("no registrant found", browser.isTextPresent("No matching records found"));
		assertTrue("no registrant found", browser.isElementPresent("//table[@class='projectStats contests resultTable paginatedDataTable']"));
		assertEquals("the table column is incorrect", "Handle", browser.getText("//table[@class='projectStats contests resultTable paginatedDataTable']/thead/tr/th[1]"));
		assertEquals("the table column is incorrect", "Rating", browser.getText("//table[@class='projectStats contests resultTable paginatedDataTable']/thead/tr/th[2]"));
		assertEquals("the table column is incorrect", "Reliability", browser.getText("//table[@class='projectStats contests resultTable paginatedDataTable']/thead/tr/th[3]"));
		assertEquals("the table column is incorrect", "Registration Date", browser.getText("//table[@class='projectStats contests resultTable paginatedDataTable']/thead/tr/th[4]"));
		assertEquals("the table column is incorrect", "Submission Date", browser.getText("//table[@class='projectStats contests resultTable paginatedDataTable']/thead/tr/th[5]"));
    }

    /**
     * FTC 101: Verify registrants list can be shown in the Registrants tab.
     *
     * @throws Exception if any error
     */
    public void testFTC101() throws Exception {
		browser.open("/direct/contest/detail.action?projectId=10000213");
		browser.click("link=Registrants (3)");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		assertTrue("user list is not found", browser.isElementPresent("//table[@class='projectStats contests resultTable paginatedDataTable']"));
		assertTrue("user list is not found", browser.isTextPresent("Partha"));
		assertTrue("user list is not found", browser.isTextPresent("twight"));
		assertTrue("user list is not found", browser.isTextPresent("dok_tester"));
		assertTrue("user list is not found", browser.isTextPresent("dok_tester"));
		assertEquals("user list is not found", "Not Rated", browser.getText("//table[@class='projectStats contests resultTable paginatedDataTable']/tbody/tr[3]/td[2]"));
		assertEquals("user list is not found", "9%", browser.getText("//table[@class='projectStats contests resultTable paginatedDataTable']/tbody/tr[3]/td[3]"));
    }

    /**
     * FTC 102: Verify registrants list is sorted by Handle descendingly by default.
     *
     * @throws Exception if any error
     */
    public void testFTC102() throws Exception {
		browser.open("/direct/contest/detail.action?projectId=10000213");
		browser.click("link=Registrants (3)");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		assertTrue("user list is not found", browser.isElementPresent("//table[@class='projectStats contests resultTable paginatedDataTable']"));
		String handle1 = browser.getText("//table[@class='projectStats contests resultTable paginatedDataTable']/tbody/tr[1]/td[1]");
		String handle2 = browser.getText("//table[@class='projectStats contests resultTable paginatedDataTable']/tbody/tr[2]/td[1]");
		String handle3 = browser.getText("//table[@class='projectStats contests resultTable paginatedDataTable']/tbody/tr[3]/td[1]");
		assertTrue("should be sorted", handle1.compareToIgnoreCase(handle2) < 0);
		assertTrue("should be sorted", handle2.compareToIgnoreCase(handle3) < 0);
    }

    /**
     * FTC 103: Verify handle can be clicked in registrants list.
     *
     * @throws Exception if any error
     */
    public void testFTC103() throws Exception {
		browser.open("/direct/contest/detail.action?projectId=10000213");
		browser.click("link=Registrants (3)");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		assertTrue("the handle is not clickable", browser.isElementPresent("link=dok_tester"));
		assertTrue("the handle is not clickable", browser.isElementPresent("link=Partha"));
		assertTrue("the handle is not clickable", browser.isElementPresent("link=twight"));
    }

    /**
     * FTC 104: Verify registrants list can be sorted by column header (Handle).
     *
     * @throws Exception if any error
     */
    public void testFTC104() throws Exception {
		browser.open("/direct/contest/detail.action?projectId=10000213");
		browser.click("link=Registrants (3)");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("//div[@id='ProjectRegistrants']/div[3]/div/table/thead/tr/th");
		Thread.sleep(TestHelper.SLEEP);
		String handle1 = browser.getText("//table[@class='projectStats contests resultTable paginatedDataTable']/tbody/tr[1]/td[1]");
		String handle2 = browser.getText("//table[@class='projectStats contests resultTable paginatedDataTable']/tbody/tr[2]/td[1]");
		String handle3 = browser.getText("//table[@class='projectStats contests resultTable paginatedDataTable']/tbody/tr[3]/td[1]");
		assertTrue("should be sorted", handle1.compareToIgnoreCase(handle2) > 0);
		assertTrue("should be sorted", handle2.compareToIgnoreCase(handle3) > 0);
		browser.click("//div[@id='ProjectRegistrants']/div[3]/div/table/thead/tr/th");
		Thread.sleep(TestHelper.SLEEP);
		handle1 = browser.getText("//table[@class='projectStats contests resultTable paginatedDataTable']/tbody/tr[1]/td[1]");
		handle2 = browser.getText("//table[@class='projectStats contests resultTable paginatedDataTable']/tbody/tr[2]/td[1]");
		handle3 = browser.getText("//table[@class='projectStats contests resultTable paginatedDataTable']/tbody/tr[3]/td[1]");
		assertTrue("should be sorted", handle1.compareToIgnoreCase(handle2) < 0);
		assertTrue("should be sorted", handle2.compareToIgnoreCase(handle3) < 0);
    }

    /**
     * FTC 105: Verify registrants list can be sorted by column header (Rating).
     *
     * @throws Exception if any error
     */
    public void testFTC105() throws Exception {
		browser.open("/direct/contest/detail.action?projectId=10000213");
		browser.click("link=Registrants (3)");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("//div[@id='ProjectRegistrants']/div[3]/div/table/thead/tr/th[2]");
		Thread.sleep(TestHelper.SLEEP);
		String cell1 = browser.getText("//table[@class='projectStats contests resultTable paginatedDataTable']/tbody/tr[1]/td[2]");
		String cell2 = browser.getText("//table[@class='projectStats contests resultTable paginatedDataTable']/tbody/tr[2]/td[2]");
		String cell3 = browser.getText("//table[@class='projectStats contests resultTable paginatedDataTable']/tbody/tr[3]/td[2]");
		assertTrue("should be sorted", cell1.compareToIgnoreCase(cell2) <= 0);
		assertTrue("should be sorted", cell2.compareToIgnoreCase(cell3) <= 0);
		browser.click("//div[@id='ProjectRegistrants']/div[3]/div/table/thead/tr/th[2]");
		Thread.sleep(TestHelper.SLEEP);
		cell1 = browser.getText("//table[@class='projectStats contests resultTable paginatedDataTable']/tbody/tr[1]/td[2]");
		cell2 = browser.getText("//table[@class='projectStats contests resultTable paginatedDataTable']/tbody/tr[2]/td[2]");
		cell3 = browser.getText("//table[@class='projectStats contests resultTable paginatedDataTable']/tbody/tr[3]/td[2]");
		assertTrue("should be sorted", cell1.compareToIgnoreCase(cell2) >= 0);
		assertTrue("should be sorted", cell2.compareToIgnoreCase(cell3) >= 0);
    }

    /**
     * FTC 106: Verify registrants list can be sorted by column header (Reliability).
     *
     * @throws Exception if any error
     */
    public void testFTC106() throws Exception {
		browser.open("/direct/contest/detail.action?projectId=10000213");
		browser.click("link=Registrants (3)");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("//div[@id='ProjectRegistrants']/div[3]/div/table/thead/tr/th[3]");
		Thread.sleep(TestHelper.SLEEP);
		String cell1 = browser.getText("//table[@class='projectStats contests resultTable paginatedDataTable']/tbody/tr[1]/td[3]");
		String cell2 = browser.getText("//table[@class='projectStats contests resultTable paginatedDataTable']/tbody/tr[2]/td[3]");
		String cell3 = browser.getText("//table[@class='projectStats contests resultTable paginatedDataTable']/tbody/tr[3]/td[3]");
		cell1 = cell1.equalsIgnoreCase("n/a") ? "0%" : cell1;
		cell2 = cell2.equalsIgnoreCase("n/a") ? "0%" : cell2;
		cell3 = cell3.equalsIgnoreCase("n/a") ? "0%" : cell3;
		assertTrue("should be sorted", cell1.compareToIgnoreCase(cell2) <= 0);
		assertTrue("should be sorted", cell2.compareToIgnoreCase(cell3) <= 0);
		browser.click("//div[@id='ProjectRegistrants']/div[3]/div/table/thead/tr/th[3]");
		Thread.sleep(TestHelper.SLEEP);
		cell1 = browser.getText("//table[@class='projectStats contests resultTable paginatedDataTable']/tbody/tr[1]/td[3]");
		cell2 = browser.getText("//table[@class='projectStats contests resultTable paginatedDataTable']/tbody/tr[2]/td[3]");
		cell3 = browser.getText("//table[@class='projectStats contests resultTable paginatedDataTable']/tbody/tr[3]/td[3]");
		cell1 = cell1.equalsIgnoreCase("n/a") ? "0%" : cell1;
		cell2 = cell2.equalsIgnoreCase("n/a") ? "0%" : cell2;
		cell3 = cell3.equalsIgnoreCase("n/a") ? "0%" : cell3;
		assertTrue("should be sorted", cell1.compareToIgnoreCase(cell2) >= 0);
		assertTrue("should be sorted", cell2.compareToIgnoreCase(cell3) >= 0);
    }

    /**
     * FTC 107: Verify registrants list can be sorted by column header (Registration Date).
     *
     * @throws Exception if any error
     */
    public void testFTC107() throws Exception {
		browser.open("/direct/contest/detail.action?projectId=10000213");
		browser.click("link=Registrants (3)");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("//div[@id='ProjectRegistrants']/div[3]/div/table/thead/tr/th[4]");
		Thread.sleep(TestHelper.SLEEP);
		DateFormat df = new SimpleDateFormat("mm/dd/yyyy hh:mm");
		Date cell1 = df.parse(browser.getText("//table[@class='projectStats contests resultTable paginatedDataTable']/tbody/tr[1]/td[4]"));
		Date cell2 = df.parse(browser.getText("//table[@class='projectStats contests resultTable paginatedDataTable']/tbody/tr[2]/td[4]"));
		Date cell3 = df.parse(browser.getText("//table[@class='projectStats contests resultTable paginatedDataTable']/tbody/tr[3]/td[4]"));
		assertTrue("should be sorted", cell1.compareTo(cell2) <= 0);
		assertTrue("should be sorted", cell2.compareTo(cell3) <= 0);
		browser.click("//div[@id='ProjectRegistrants']/div[3]/div/table/thead/tr/th[4]");
		Thread.sleep(TestHelper.SLEEP);
		cell1 = df.parse(browser.getText("//table[@class='projectStats contests resultTable paginatedDataTable']/tbody/tr[1]/td[4]"));
		cell2 = df.parse(browser.getText("//table[@class='projectStats contests resultTable paginatedDataTable']/tbody/tr[2]/td[4]"));
		cell3 = df.parse(browser.getText("//table[@class='projectStats contests resultTable paginatedDataTable']/tbody/tr[3]/td[4]"));
		assertTrue("should be sorted", cell1.compareTo(cell2) >= 0);
		assertTrue("should be sorted", cell2.compareTo(cell3) >= 0);
    }

    /**
     * FTC 108: Verify dropdown list works well on registrants list (default items per page is 10).
     *
     * @throws Exception if any error
     */
    public void testFTC108() throws Exception {
		browser.open("/direct/contest/detail.action?projectId=10000213");
		browser.click("link=Registrants (3)");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		assertEquals("default selected option is wrong", "10", browser.getSelectedLabel("dataTableLength"));
    }

    /**
     * FTC 109: Verify dropdown list works well on registrants list.
     *
     * @throws Exception if any error
     */
    public void testFTC109() throws Exception {
		browser.open("/direct/contest/detail.action?projectId=10000213");
		browser.click("link=Registrants (3)");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		assertEquals("default selected option is wrong", "10", browser.getSelectedLabel("dataTableLength"));
		browser.select("dataTableLength", "5");
		assertEquals("selected option is wrong", "5", browser.getSelectedLabel("dataTableLength"));
		browser.select("dataTableLength", "25");
		assertEquals("selected option is wrong", "25", browser.getSelectedLabel("dataTableLength"));
		browser.select("dataTableLength", "All");
		assertEquals("selected option is wrong", "All", browser.getSelectedLabel("dataTableLength"));
    }

    /**
     * FTC 110/111: Verify dropdown list works well on registrants list.
     *
     * @throws Exception if any error
     */
    public void testFTC110111() throws Exception {
		browser.open("/direct/contest/detail.action?projectId=10000220");
		browser.click("link=Registrants (7)");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		assertTrue("page is incorrect", browser.isTextPresent("Showing 1 to 7 of 7 entries"));
		browser.select("id=dataTableLength", "label=5");
		Thread.sleep(TestHelper.SLEEP);
		assertTrue("page is incorrect", browser.isTextPresent("Showing 1 to 5 of 7 entries"));
		browser.click("css=span.next.paginate_button");
		Thread.sleep(TestHelper.SLEEP);
		assertTrue("page is incorrect", browser.isTextPresent("Showing 6 to 7 of 7 entries"));
		browser.click("css=span.previous.paginate_button");
		Thread.sleep(TestHelper.SLEEP);
		assertTrue("page is incorrect", browser.isTextPresent("Showing 1 to 5 of 7 entries"));
		browser.click("//div[@id='ProjectRegistrants']/div[3]/div/div[2]/div/span[3]/span[2]");
		Thread.sleep(TestHelper.SLEEP);
		assertTrue("page is incorrect", browser.isTextPresent("Showing 6 to 7 of 7 entries"));
		browser.click("//div[@id='ProjectRegistrants']/div[3]/div/div[2]/div/span[3]/span");
		Thread.sleep(TestHelper.SLEEP);
		assertTrue("page is incorrect", browser.isTextPresent("Showing 1 to 5 of 7 entries"));
    }

}
