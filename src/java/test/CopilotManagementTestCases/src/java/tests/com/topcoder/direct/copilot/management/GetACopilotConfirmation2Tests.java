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
public class GetACopilotConfirmation2Tests extends BaseTestCase {

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
     * Verify the user can go to get a copilot confirmation 2 page by pressing "Hire the Copilot" button .
     *
     * @throws Exception if any error
     */
    public void testFTC130() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Choose");
        browser.click("css=span.right");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Hire the Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertEquals("the page content is incorrect", "Confirmation", browser.getText("//div[@class='stepTitle']/h3"));
        assertTrue("the page content is incorrect",
            browser.isTextPresent(
                "Your selected copilot has been inform about this oppurtunity."
                    + " The copilot will contact you to confirm the message."));
    }

    /**
     * Verify breadcrumb is correct on Confirmation page .
     *
     * @throws Exception if any error
     */
    public void testFTC131() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Choose");
        browser.click("css=span.right");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Hire the Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the page content is incorrect", browser.isTextPresent("Dashboard > Post a Copilot"));
    }

    /**
     * Verify "Back to Dashboard" button on Confirmation page is working well .
     *
     * @throws Exception if any error
     */
    public void testFTC132() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Choose");
        browser.click("css=span.right");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Hire the Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Back to Dashboard");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the page content is incorrect", browser.isTextPresent("Dashboard > FOL 3.3 Merchandising"));
    }

    /**
     * Verify click "View Contest" link on Confirmation page will navigate to contest page   .
     *
     * @throws Exception if any error
     */
    public void testFTC133() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Choose");
        browser.click("css=span.right");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Hire the Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the page content is incorrect", browser.isElementPresent("link=View Contest"));
    }

    /**
     * Verify click "Forum" link on Confirmation page will open the corresponding forum  .
     *
     * @throws Exception if any error
     */
    public void testFTC134() throws Exception {
        browser.click("link=Select from Copilot Pool");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Choose");
        browser.click("css=span.right");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Hire the Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the page content is incorrect", browser.isElementPresent("link=Forum"));
    }
}
