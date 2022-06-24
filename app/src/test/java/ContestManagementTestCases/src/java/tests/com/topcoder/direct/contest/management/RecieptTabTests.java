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
public class RecieptTabTests extends TestCase {
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
     * FTC 122: Verify the Receipt tab works well.
     *
     * @throws Exception if any error
     */
    public void testFTC122() throws Exception {
		browser.click("//div[@id='tabs0']/ul/li[2]/a/span");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("link=Client 1 Billing Account 1 Project 1");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("//div[@id='tabs1']/ul/li[2]/a/span");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("//table[@id='ProjectContests']/tbody/tr[9]/td[6]/a");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		browser.click("//li[@id='rReceiptTab']/a");
		browser.waitForPageToLoad(TestHelper.getTimeout());
		assertTrue("Receipt should be presented", browser.isTextPresent("Estimated Cost"));
		assertTrue("Receipt should be presented", browser.isTextPresent("Design"));
		assertTrue("Receipt should be presented", browser.isTextPresent("Client 1 Billing Account 1 Project 1 Design Contest 9"));
		assertTrue("Receipt should be presented", browser.isTextPresent("Client 1 Billing Account 1 Project 1"));
		assertTrue("Receipt should be presented", browser.isTextPresent("PO BOX 10000001"));
		assertTrue("Receipt should be presented", browser.isTextPresent("10/13/2011 08:41 CST"));
		assertTrue("Receipt should be presented", browser.isTextPresent("First Place Cost : $800.0"));
		assertTrue("Receipt should be presented", browser.isTextPresent("Second Place Cost : $400.0"));
		assertTrue("Receipt should be presented", browser.isTextPresent("DR points : $360.0"));
		assertTrue("Receipt should be presented", browser.isTextPresent("Reliability Bonus Cost : $240.0"));
		assertTrue("Receipt should be presented", browser.isTextPresent("Contest Fee : $6001.0"));
		assertTrue("Receipt should be presented", browser.isTextPresent("Specification Review : $30.0"));
		assertTrue("Receipt should be presented", browser.isTextPresent("Review : $605.0"));
		assertTrue("Receipt should be presented", browser.isTextPresent("Copilot Fee : $0.0"));
		assertTrue("Receipt should be presented", browser.isTextPresent("Bug Race : $0.0"));
    }

}
