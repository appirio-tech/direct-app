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
public class CopilotPoolListTests extends TestCase {
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
        super.tearDown();
    }

    /**
     * Verify the user can go to copilot pool list page by pressing "Select from Copilot Pool" button .
     *
     * @throws Exception if any error
     */
    public void testFTC107() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Switch to List View");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the page content is incorrect", browser.isElementPresent("//div[@class='copilotListTable']"));
    }

    /**
     * Verify the user can go to dashboard page by pressing "Back to Dashboard" button on copilot pool list
     * page .
     *
     * @throws Exception if any error
     */
    public void testFTC108() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Switch to List View");
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
    public void testFTC109() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Switch to List View");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=view profile");
    }

    /**
     * Verify the user can go to selected copilot popup window by click "Choose" button on select copilot
     * section .
     *
     * @throws Exception if any error
     */
    public void testFTC110() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Switch to List View");
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
    public void testFTC111() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Switch to List View");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the page content is incorrect", browser.isTextPresent("Member"));
        assertTrue("the page content is incorrect", browser.isTextPresent("# of Projects"));
        assertTrue("the page content is incorrect", browser.isTextPresent("# of Contest"));
        assertTrue("the page content is incorrect", browser.isTextPresent("# of Reposts"));
        assertTrue("the page content is incorrect", browser.isTextPresent("# of Failures"));
        assertTrue("the page content is incorrect", browser.isTextPresent("# of Bug Races"));
        assertTrue("the page content is incorrect", browser.isTextPresent("# of Current Projects"));
        assertTrue("the page content is incorrect", browser.isTextPresent("# of Current Contest"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Action"));
    }

    /**
     * Verify the default value on show per page dropdown list is 10 .
     *
     * @throws Exception if any error
     */
    public void testFTC112() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Switch to List View");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertEquals("the page content is incorrect", "10",
                browser.getSelectedLabel("//ul[@id='display']/li[2]/select"));
    }

    /**
     * Verify all value can be listed on  show per page dropdown list .
     *
     * @throws Exception if any error
     */
    public void testFTC113() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Switch to List View");
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
    public void testFTC114() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Switch to List View");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertEquals("the page content is incorrect", "No. of Completed Projects #",
            browser.getSelectedLabel("//ul[@id='display']/li/select"));
    }

    /**
     * Verify all value can be listed on  Sort by dropdown list .
     *
     * @throws Exception if any error
     */
    public void testFTC115() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Switch to List View");
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
     * Verify pagination function on copilot pool list page is working well(Change the number of items per
     * page) .
     *
     * @throws Exception if any error
     */
    public void testFTC116() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Switch to List View");
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
    public void testFTC117() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Switch to List View");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("//ul[@id='display']/li[2]/select", "10");
    }

    /**
     * Verify pagination features is working well(Change the current page which have 20 items per page) .
     *
     * @throws Exception if any error
     */
    public void testFTC118() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Switch to List View");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("//ul[@id='display']/li[2]/select", "20");
    }

    /**
     * Verify pagination features is working well(Change the current page which have 20 items per page) .
     *
     * @throws Exception if any error
     */
    public void testFTC119() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Switch to List View");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("//ul[@id='display']/li[2]/select", "30");
    }

    /**
     * Verify sorting function is working well(Sort by No. of Completed Projects #) .
     *
     * @throws Exception if any error
     */
    public void testFTC122() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Switch to List View");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("//ul[@id='display']/li/select", "No. of Completed Contests #");
    }

    /**
     * Verify sorting function is working well(Sort No. of Completed Contests #) .
     *
     * @throws Exception if any error
     */
    public void testFTC123() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Switch to List View");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("//ul[@id='display']/li/select", "No. of Completed Contests #");
    }

    /**
     * Verify sorting function is working well(Sort No. of Current Projects #) .
     *
     * @throws Exception if any error
     */
    public void testFTC124() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Switch to List View");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("//ul[@id='display']/li/select", "No. of Current Projects #");
    }

    /**
     * Verify sorting function is working well(Sort No. of Current Contests #) .
     *
     * @throws Exception if any error
     */
    public void testFTC125() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Switch to List View");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("//ul[@id='display']/li/select", "No. of Current Contests #");
    }

    /**
     * Verify the user can switch between list view and grid view .
     *
     * @throws Exception if any error
     */
    public void testFTC126() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Switch to List View");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Switch to Grid View");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertFalse("the page content is incorrect", browser.isElementPresent("//div[@class='copilotListTable']"));
    }
}
