/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.contest.management;

import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;

/**
 * Create one project which will be used for launching the contest.
 *
 * @author gjw99
 * @version 1.1
 */
public class SetupDataForTests extends TestCase {
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
     * Create the test project.
     *
     * @throws Exception if any error
     */
    public void testSetupProject() throws Exception {
    	browser.click("link=Launch Contest");
    	browser.waitForPageToLoad(TestHelper.getTimeout());
    	browser.click("css=span.right");
		Thread.sleep(TestHelper.SLEEP);
    	browser.type("id=newProjectName", "test");
    	browser.type("id=newProjectDescription", "test");
    	browser.click("css=#addNewProjectModal > div.modalBody > div.modalCommandBox > a.newButton1 > span.btnR > span.btnC");
		Thread.sleep(TestHelper.SLEEP);
    	browser.click("css=span.btnC");
		Thread.sleep(TestHelper.SLEEP);
    }

}
