/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.copilot.management;

import com.thoughtworks.selenium.Selenium;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.List;


/**
 * Functional test for cockpit copilot management.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CopilotPoolGridTests extends TestCase {
    /** Represents the Selenium Instance. */
    private Selenium browser;

    /**
     * Sets up the testing environment.
     *
     * @throws Exception if any error occurs.
     */
    public void setUp() throws Exception {
        browser = TestHelper.getCopilotPoolPage();
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
     * Verify the user can go to copilot pool grid page by pressing "Select from Copilot Pool" button .
     *
     * @throws Exception if any error
     */
    public void testFTC89() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertEquals("the page content is incorrect", "Copilot Pool", browser.getText("//div[@class='stepTitle']/h3"));
    }

    /**
     * Verify the user can go to dashboard page by pressing "Back to Dashboard" button on copilot pool grid
     * page .
     *
     * @throws Exception if any error
     */
    public void testFTC90() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Back to Dashboard");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the page content is incorrect", browser.isTextPresent("Dashboard > FOL 3.3 Merchandising"));
    }

    /**
     * Verify the user can go to copilot profile page by pressing "view profile" link on the choose copilot
     * section .
     *
     * @throws Exception if any error
     */
    public void testFTC91() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=view profile");
    }

    /**
     * Verify the user can go to selected copilot popup window by click "Choose" button on select copilot
     * section .
     *
     * @throws Exception if any error
     */
    public void testFTC92() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Choose");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("popup should be displayed", browser.isVisible("copilotPopUp"));
    }

    /**
     * Verify all elements will be displayed on each copilot section .
     *
     * @throws Exception if any error
     */
    public void testFTC93() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the page content is incorrect", browser.isTextPresent("Handle :"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Status:"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Fullfilment:"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Reability:"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Current Working On"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Number of Project:"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Number of Contest:"));
    }

    /**
     * Verify the default value on show per page dropdown list is 10 .
     *
     * @throws Exception if any error
     */
    public void testFTC94() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertEquals("the page content is incorrect", "10",
                browser.getSelectedLabel("//ul[@id='display']/li[2]/select"));
    }

    /**
     * Verify all value can be listed on  show per page dropdown list .
     *
     * @throws Exception if any error
     */
    public void testFTC95() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());

        List<String> labels = Arrays.asList(browser.getSelectOptions("//ul[@id='display']/li[2]/select"));
        assertTrue("the page content is incorrect", labels.contains("10"));
        assertTrue("the page content is incorrect", labels.contains("20"));
        assertTrue("the page content is incorrect", labels.contains("30"));
        assertTrue("the page content is incorrect", labels.size() == 3);
    }

    /**
     * Verify default value on Sort by dropdown list is "No. of Completed Projects #" .
     *
     * @throws Exception if any error
     */
    public void testFTC96() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertEquals("the page content is incorrect", "No. of Completed Projects #",
            browser.getSelectedLabel("//ul[@id='display']/li/select"));
    }

    /**
     * Verify all value can be listed on  Sort by dropdown list .
     *
     * @throws Exception if any error
     */
    public void testFTC97() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());

        List<String> labels = Arrays.asList(browser.getSelectOptions("//ul[@id='display']/li/select"));
        assertTrue("the page content is incorrect", labels.contains("No. of Completed Projects #"));
        assertTrue("the page content is incorrect", labels.contains("No. of Completed Contests #"));
        assertTrue("the page content is incorrect", labels.contains("No. of Current Projects #"));
        assertTrue("the page content is incorrect", labels.contains("No. of Current Contests #"));
        assertTrue("the page content is incorrect", labels.contains("No. of Completed Projects #"));
        assertTrue("the page content is incorrect", labels.contains("No. of Completed Contests #"));
    }

    /**
     * Verify pagination function on copilot pool grid page is working well(Change the number of items per
     * page) .
     *
     * @throws Exception if any error
     */
    public void testFTC98() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("//ul[@id='display']/li[2]/select", "10");
        browser.select("//ul[@id='display']/li[2]/select", "20");
        browser.select("//ul[@id='display']/li[2]/select", "30");
    }

    /**
     * Verify pagination features is working well(Change the current page which have 10 items per page) .
     *
     * @throws Exception if any error
     */
    public void testFTC99() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("//ul[@id='display']/li[2]/select", "10");
    }

    /**
     * Verify pagination features is working well(Change the current page which have 20 items per page) .
     *
     * @throws Exception if any error
     */
    public void testFTC100() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("//ul[@id='display']/li[2]/select", "20");
    }

    /**
     * Verify sorting function is working well(Sort by No. of Completed Projects #) .
     *
     * @throws Exception if any error
     */
    public void testFTC103() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("//ul[@id='display']/li/select", "No. of Completed Projects #");
    }

    /**
     * Verify sorting function is working well(Sort No. of Completed Contests #) .
     *
     * @throws Exception if any error
     */
    public void testFTC104() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("//ul[@id='display']/li/select", "No. of Completed Contests #");
    }

    /**
     * Verify sorting function is working well(Sort No. of Current Projects #) .
     *
     * @throws Exception if any error
     */
    public void testFTC105() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("//ul[@id='display']/li/select", "No. of Current Projects #");
    }

    /**
     * Verify sorting function is working well(Sort No. of Current Contests #) .
     *
     * @throws Exception if any error
     */
    public void testFTC106() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("//ul[@id='display']/li/select", "No. of Current Contests #");
    }
}
