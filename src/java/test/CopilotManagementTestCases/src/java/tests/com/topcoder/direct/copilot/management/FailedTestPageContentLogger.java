/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.copilot.management;

import com.thoughtworks.selenium.SeleniumException;
import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestListener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>A test listener responsible for logging the contents of the browser's page in case the test is failing.</p>
 * 
 * @author isv
 * @version 1.0
 */
public class FailedTestPageContentLogger implements TestListener{

    /**
     * <p>A <code>DateFormat</code> to be used for formatting the dates.</p>
     */
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH_mm");

    /**
     * <p>Constructs new <code>FailedTestPageContentLogger</code> instance. This implementation does nothing.</p>
     */
    public FailedTestPageContentLogger() {
    }

    public void addError(Test test, Throwable throwable) {
        BaseTestCase directTest = (BaseTestCase) test;
        if (throwable instanceof SeleniumException) {
            PrintWriter pw = null;
            try {
                File file = new File(TestHelper.getBrowserPageContentsDir(),
                        directTest.getName() + "." + dateFormat.format(new Date()) + ".log");
                pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
                throwable.printStackTrace(pw);
                pw.println();
                pw.println("********** CONTENT OF THE CURRENT BROWSER'S PAGE **********");
                pw.println(directTest.browser.getBodyText());
                pw.println("********** END **********");
            } catch (Exception e) {
                e.printStackTrace();
                if (pw != null) {
                    pw.println("Error while printing: " + e);
                }
            } finally {
                if (pw != null) {
                    pw.close();
                }
            }
        }
    }

    public void addFailure(Test test, AssertionFailedError assertionFailedError) {
        BaseTestCase directTest = (BaseTestCase) test;
            PrintWriter pw = null;
            try {
                File file = new File(TestHelper.getBrowserPageContentsDir(),
                        directTest.getName() + "." + dateFormat.format(new Date()) + ".log");
                pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
                assertionFailedError.printStackTrace(pw);
                pw.println();
                pw.println("********** CONTENT OF THE CURRENT BROWSER'S PAGE **********");
                pw.println(directTest.browser.getBodyText());
                pw.println("********** END **********");
            } catch (Exception e) {
                if (pw != null) {
                    pw.println("Error while printing: " + e);
                }
            } finally {
                if (pw != null) {
                    pw.close();
                }
            }
    }

    public void endTest(Test test) {
    }

    public void startTest(Test test) {
    }
}
