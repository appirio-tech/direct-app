/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.copilot.management;

import java.util.Arrays;
import java.util.List;

import com.thoughtworks.selenium.Selenium;

import junit.framework.TestCase;


/**
 * Functional test for cockpit copilot management.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MyCopilotPostingSubmissionsTests extends TestCase {
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
     * Verify all elements will be displayed on Submissions tab .
     *
     * @throws Exception if any error
     */
    public void testFTC193() throws Exception {
        browser.click("link=Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=My Copilot Postings");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 114");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Submissions");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertEquals("the column is invalid", "Copilot",
                browser.getText("//table[@class='projectStats newContestsStatus paginatedDataTable']/thead/tr/th[1]"));
        assertEquals("the column is invalid", "Profiles",
                browser.getText("//table[@class='projectStats newContestsStatus paginatedDataTable']/thead/tr/th[2]"));
        assertEquals("the column is invalid", "Submission Time",
                browser.getText("//table[@class='projectStats newContestsStatus paginatedDataTable']/thead/tr/th[3]"));
    }

    /**
     * Verify the default value on show per page dropdown list is 10 .
     *
     * @throws Exception if any error
     */
    public void testFTC194() throws Exception {
        browser.click("link=Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=My Copilot Postings");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 114");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Submissions");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertEquals("the page is incorrect", "10",
                browser.getSelectedLabel("dataTableLength"));
    }

    /**
     * Verify all value can be listed on  show per page dropdown list .
     *
     * @throws Exception if any error
     */
    public void testFTC195() throws Exception {
        browser.click("link=Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=My Copilot Postings");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 114");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Submissions");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        List<String> options = Arrays.asList(browser.getSelectOptions("dataTableLength"));
        assertEquals("the page is incorrect", 4, options.size());
        assertTrue("the page is incorrect", options.contains("5"));
        assertTrue("the page is incorrect", options.contains("10"));
        assertTrue("the page is incorrect", options.contains("25"));
        assertTrue("the page is incorrect", options.contains("All"));
    }

    /**
     * Verify pagination function on Submissions tab is working well(Change the number of items per page) .
     *
     * @throws Exception if any error
     */
    public void testFTC196() throws Exception {
        browser.click("link=Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=My Copilot Postings");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 114");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Submissions");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("dataTableLength", "5");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the pagination information is wrong", browser.isTextPresent("Showing 1 to 5 of 7 entries"));
        browser.select("dataTableLength", "10");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the pagination information is wrong", browser.isTextPresent("Showing 1 to 7 of 7 entries"));
        browser.select("dataTableLength", "25");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the pagination information is wrong", browser.isTextPresent("Showing 1 to 7 of 7 entries"));
        browser.select("dataTableLength", "All");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the pagination information is wrong", browser.isTextPresent("Showing 1 to 7 of 7 entries"));
    }

    /**
     * Verify pagination features is working well(Change the current page which have 5 items per page) .
     *
     * @throws Exception if any error
     */
    public void testFTC197() throws Exception {
        browser.click("link=Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=My Copilot Postings");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 114");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Submissions");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("dataTableLength", "5");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the pagination information is wrong", browser.isTextPresent("Showing 1 to 5 of 7 entries"));
        browser.click(
                "//div[@id='CopilotPostingSubmissions']/div[3]/div/div/div/div/div/div/div[3]/div/span[3]/span[2]");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the pagination information is wrong", browser.isTextPresent("Showing 6 to 7 of 7 entries"));
        browser.click(
                "//div[@id='CopilotPostingSubmissions']/div[3]/div/div/div/div/div/div/div[3]/div/span[3]/span[1]");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the pagination information is wrong", browser.isTextPresent("Showing 1 to 5 of 7 entries"));
        browser.click("css=span.next.paginate_button");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the pagination information is wrong", browser.isTextPresent("Showing 6 to 7 of 7 entries"));
        browser.click("css=span.previous.paginate_button");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the pagination information is wrong", browser.isTextPresent("Showing 1 to 5 of 7 entries"));
    }

    /**
     * Verify pagination features is working well(Change the current page which have 10 items per page) .
     *
     * @throws Exception if any error
     */
    public void testFTC198() throws Exception {
        browser.click("link=Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=My Copilot Postings");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 114");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Submissions");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("dataTableLength", "10");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the pagination information is wrong", browser.isTextPresent("Showing 1 to 7 of 7 entries"));
        browser.click("css=span.next.paginate_button");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the pagination information is wrong", browser.isTextPresent("Showing 1 to 7 of 7 entries"));
        browser.click("css=span.previous.paginate_button");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the pagination information is wrong", browser.isTextPresent("Showing 1 to 7 of 7 entries"));
    }

    /**
     * Verify pagination features is working well(Change the current page which have 25 items per page) .
     *
     * @throws Exception if any error
     */
    public void testFTC199() throws Exception {
        browser.click("link=Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=My Copilot Postings");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 114");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Submissions");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("dataTableLength", "25");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the pagination information is wrong", browser.isTextPresent("Showing 1 to 7 of 7 entries"));
        browser.click("css=span.next.paginate_button");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the pagination information is wrong", browser.isTextPresent("Showing 1 to 7 of 7 entries"));
        browser.click("css=span.previous.paginate_button");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the pagination information is wrong", browser.isTextPresent("Showing 1 to 7 of 7 entries"));
    }

    /**
     * Verify pagination features is working well(Change the current page which have all items per page) .
     *
     * @throws Exception if any error
     */
    public void testFTC200() throws Exception {
        browser.click("link=Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=My Copilot Postings");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 114");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Submissions");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("dataTableLength", "All");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the pagination information is wrong", browser.isTextPresent("Showing 1 to 7 of 7 entries"));
        browser.click("css=span.next.paginate_button");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the pagination information is wrong", browser.isTextPresent("Showing 1 to 7 of 7 entries"));
        browser.click("css=span.previous.paginate_button");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the pagination information is wrong", browser.isTextPresent("Showing 1 to 7 of 7 entries"));
    }

    /**
     * Verify the user can successful go to Receipt tab .
     *
     * @throws Exception if any error
     */
    public void testFTC203() throws Exception {
        browser.click("link=Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=My Copilot Postings");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 114");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=a.last > span.left > span.right");
        browser.waitForPageToLoad(TestHelper.getTimeout());
    }
}
