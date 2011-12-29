/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.copilot.management;

import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;


/**
 * Functional test for cockpit copilot management.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ManageCopilotsTests extends TestCase {
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
     * Verify all elements can be displayed on Manage Copilots page .
     *
     * @throws Exception if any error
     */
    public void testFTC204() throws Exception {
        browser.click("link=Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Manage Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the page is incorrect", browser.isElementPresent("link=Project View"));
        assertTrue("the page is incorrect", browser.isElementPresent("link=Contest View"));
    }

    /**
     * Verify breadcrumb is correct on my copilot postings page .
     *
     * @throws Exception if any error
     */
    public void testFTC205() throws Exception {
        browser.click("link=Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Manage Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the page is incorrect", browser.isTextPresent("Dashboard > Copilots > Manage Copilots"));
    }

    /**
     * Verify all elements can be displayed on Project View page .
     *
     * @throws Exception if any error
     */
    public void testFTC206() throws Exception {
        browser.click("link=Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Manage Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertEquals("the column is invalid", "Project / Copilot",
                browser.getText("//table[@id='copilotProjectTable']/thead/tr/th[1]"));
        assertEquals("the column is invalid", "No Of Copilots",
                browser.getText("//table[@id='copilotProjectTable']/thead/tr/th[2]"));
        assertEquals("the column is invalid", "Copilot Type",
                browser.getText("//table[@id='copilotProjectTable']/thead/tr/th[3]"));
        assertEquals("the column is invalid", "Manage Copilot",
                browser.getText("//table[@id='copilotProjectTable']/thead/tr/th[4]"));
    }

    /**
     * Verify sorting function Project View page is working well .
     *
     * @throws Exception if any error
     */
    public void testFTC207() throws Exception {
        browser.click("link=Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Manage Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("//table[@id='copilotProjectTable']/thead/tr/th[1]");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("//table[@id='copilotProjectTable']/thead/tr/th[2]");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("//table[@id='copilotProjectTable']/thead/tr/th[3]");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("//table[@id='copilotProjectTable']/thead/tr/th[4]");
        browser.waitForPageToLoad(TestHelper.getTimeout());
    }

    /**
     * Verify popup window will be displayed after click "Add Copilot" button .
     *
     * @throws Exception if any error
     */
    public void testFTC208() throws Exception {
        browser.click("link=Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Manage Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.fireEvent("//table[@id='copilotProjectTable']/tbody/tr/td[4]/a", "click");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the popup window should be displayed", browser.isVisible("manageUserDialog"));
        assertTrue("the page is incorrect", browser.isTextPresent("Available Copilots"));
        assertTrue("the page is incorrect", browser.isTextPresent("Chosen Copilots"));
    }

    /**
     * Verify ">" button on Copilot Management popup window is working well  .
     *
     * @throws Exception if any error
     */
    public void testFTC210() throws Exception {
        browser.click("link=Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Manage Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.fireEvent("//table[@id='copilotProjectTable']/tbody/tr/td[4]/a", "click");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("//a[@class='addItem']");
    }

    /**
     * Verify &lt; button on Copilot Management popup window is working well  .
     *
     * @throws Exception if any error
     */
    public void testFTC211() throws Exception {
        browser.click("link=Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Manage Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.fireEvent("//table[@id='copilotProjectTable']/tbody/tr/td[4]/a", "click");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("//a[@class='removeItem']");
    }

    /**
     * Verify "Remove all" on Copilot Management popup window is working well  .
     *
     * @throws Exception if any error
     */
    public void testFTC212() throws Exception {
        browser.click("link=Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Manage Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.fireEvent("//table[@id='copilotProjectTable']/tbody/tr/td[4]/a", "click");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("link=Remove All");
    }

    /**
     * Verify Search function on Copilot Management popup window is working well  .
     *
     * @throws Exception if any error
     */
    public void testFTC213() throws Exception {
        browser.click("link=Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Manage Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.fireEvent("//table[@id='copilotProjectTable']/tbody/tr/td[4]/a", "click");
        Thread.sleep(TestHelper.SLEEP);
        browser.type("//input[@value='adr']", "iRabbit");
        browser.click("link=Search");
    }

    /**
     * Verify Search function on Copilot Management popup window is case sensitive .
     *
     * @throws Exception if any error
     */
    public void testFTC214() throws Exception {
        browser.click("link=Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Manage Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.fireEvent("//table[@id='copilotProjectTable']/tbody/tr/td[4]/a", "click");
        Thread.sleep(TestHelper.SLEEP);
        browser.type("//input[@value='adr']", "irabbit");
        browser.click("link=Search");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("no result can be found",
                browser.getText("//div[@id='manageUserDialog']/div[2]/div/div[3]").length() == 0);
    }

    /**
     * Verify can Add Copilot successful .
     *
     * @throws Exception if any error
     */
    public void testFTC215() throws Exception {
        browser.click("link=Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Manage Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.fireEvent("//table[@id='copilotProjectTable']/tbody/tr/td[4]/a", "click");
        Thread.sleep(TestHelper.SLEEP);
    }

    /**
     * Verify can cancel Add Copilot operation .
     *
     * @throws Exception if any error
     */
    public void testFTC216() throws Exception {
        browser.click("link=Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Manage Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.fireEvent("//table[@id='copilotProjectTable']/tbody/tr/td[4]/a", "click");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the popup window should be displayed", browser.isVisible("manageUserDialog"));
        browser.fireEvent("//div[@id='manageUserDialog']/div[3]/div[2]/a[2]", "click");
        Thread.sleep(TestHelper.SLEEP);
        assertFalse("the popup window should be closed", browser.isVisible("manageUserDialog"));
    }

    /**
     * Verify "+" and "-" button on project view tab is working well .
     *
     * @throws Exception if any error
     */
    public void testFTC217() throws Exception {
        browser.click("link=Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Manage Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertEquals("the page is incorrect", "expand",
                browser.getAttribute("//table[@id='copilotProjectTable']/tbody/tr/td[1]/a@class"));
        browser.fireEvent("//table[@id='copilotProjectTable']/tbody/tr/td[1]/a", "click");
        Thread.sleep(TestHelper.SLEEP);
        assertEquals("the page is incorrect", "expand fold",
                browser.getAttribute("//table[@id='copilotProjectTable']/tbody/tr/td[1]/a@class"));
        browser.fireEvent("//table[@id='copilotProjectTable']/tbody/tr/td[1]/a", "click");
        Thread.sleep(TestHelper.SLEEP);
        assertEquals("the page is incorrect", "expand",
                browser.getAttribute("//table[@id='copilotProjectTable']/tbody/tr/td[1]/a@class"));
    }

    /**
     * Verify all elements can be displayed on Project View page .
     *
     * @throws Exception if any error
     */
    public void testFTC218() throws Exception {
        browser.click("link=Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Manage Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Contest View");
        Thread.sleep(TestHelper.SLEEP);
        assertEquals("the column is invalid", "Project / Contest name",
                browser.getText("//table[@id='copilotContestTable']/thead/tr/th[1]"));
        assertEquals("the column is invalid", "Contest Number",
                browser.getText("//table[@id='copilotContestTable']/thead/tr/th[2]"));
        assertEquals("the column is invalid", "Copilot",
                browser.getText("//table[@id='copilotContestTable']/thead/tr/th[3]"));
    }

    /**
     * Verify the "+" and "-" icon on the contest view tab is working well .
     *
     * @throws Exception if any error
     */
    public void testFTC219() throws Exception {
        browser.click("link=Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Manage Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Contest View");
        Thread.sleep(TestHelper.SLEEP);
        assertEquals("the page is incorrect", "expand",
                browser.getAttribute("//table[@id='copilotContestTable']/tbody/tr/td[1]/a@class"));
        browser.fireEvent("//table[@id='copilotContestTable']/tbody/tr/td[1]/a", "click");
        Thread.sleep(TestHelper.SLEEP);
        assertEquals("the page is incorrect", "expand fold",
                browser.getAttribute("//table[@id='copilotContestTable']/tbody/tr/td[1]/a@class"));
        browser.fireEvent("//table[@id='copilotContestTable']/tbody/tr/td[1]/a", "click");
        Thread.sleep(TestHelper.SLEEP);
        assertEquals("the page is incorrect", "expand",
                browser.getAttribute("//table[@id='copilotContestTable']/tbody/tr/td[1]/a@class"));
    }
}
