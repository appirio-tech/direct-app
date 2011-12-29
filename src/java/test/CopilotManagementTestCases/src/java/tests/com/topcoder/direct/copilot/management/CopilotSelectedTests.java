/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.copilot.management;

import com.thoughtworks.selenium.Selenium;

import junit.framework.TestCase;


/**
 * Functional test for cockpit copilot management.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CopilotSelectedTests extends TestCase {
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
     * Verify the user can go to copilot selected page(From pool grid view) .
     *
     * @throws Exception if any error
     */
    public void testFTC127() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Choose");
        browser.click("css=span.right");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertEquals("the page content is incorrect", "Introduction Get a Copilot",
            browser.getText("//div[@class='stepTitle']/h3"));
    }

    /**
     * Verify the user can go to copilot selected page(From pool list view) .
     *
     * @throws Exception if any error
     */
    public void testFTC128() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Switch to List View");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Choose");
        browser.click("css=span.right");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertEquals("the page content is incorrect", "Introduction Get a Copilot",
            browser.getText("//div[@class='stepTitle']/h3"));
    }

    /**
     * Verify the user can see selected  copilot on the right bottom of the copilot selected page .
     *
     * @throws Exception if any error
     */
    public void testFTC129() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Choose");
        browser.click("css=span.right");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertEquals("the page content is incorrect", "Introduction Get a Copilot",
            browser.getText("//div[@class='stepTitle']/h3"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Selected Copilot"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Handle :"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Status:"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Fullfilment:"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Reability:"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Current Working On"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Number of Project:"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Number of Contest:"));
    }
}
