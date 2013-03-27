/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.copilot.management;

import com.thoughtworks.selenium.Selenium;
import junit.framework.TestCase;
import junit.framework.TestResult;

/**
 * <p>A base class for functional test cases for <code>Copilot Management</code>.</p>
 * 
 * @author isv
 * @version 1.0
 */
public abstract class BaseTestCase extends TestCase {

    /**
     * <p>A <code>FailedTestPageContentLogger</code> to be used for logging the content of the browser's page in case
     * the test fails.</p>
     */
    private static final FailedTestPageContentLogger testListener = new FailedTestPageContentLogger(); 

    /**
     * <p>A <code>Selenium</code> instance providing access to browser functionalities.</p>
     */
    protected Selenium browser;

    /**
     *  <p>Constructs new <code>BaseTestCase</code> instance. This implementation does nothing.</p>
     */
    protected BaseTestCase() {
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

    @Override
    public void run(TestResult result) {
        result.addListener(testListener);
        super.run(result);
    }
}
