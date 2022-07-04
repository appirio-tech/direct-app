/*
 * ComponentName: WorkDays
 * FileName: WorkdaysUnitOfTimeTest.java
 * Version: 1.0
 * Date: 01/06/2005
 *
 * <p>Copyright (c) 2005, TopCoder, Inc. All rights reserved</p>
 */

package com.topcoder.date.workdays.accuracytests;

import com.topcoder.date.workdays.*;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This class contains the accuracy tests for WorkdaysUnitOfTime.java</p>
 *
 * @author woodjhon
 * @version 1.0
 */
public class WorkdaysUnitOfTimeTest extends TestCase {

    /**
     * <p>
     * Represents an instance of WorkdaysUnitOfTime to be used for testing.</p>
     */
    private WorkdaysUnitOfTime s = null;

    /**
     * <p>
     * Creates an instance for the Test.</p>
     *
     * @param name the name of the TestCase.
     */
    public WorkdaysUnitOfTimeTest(String name) {
        super(name);
    }

    /**
     *<p>
     * Sets up the fixture.</p>
     */
    protected void setUp() {
    }

    /**
     *<p>
     * Tears down the fixture.</p>
     */
    protected void tearDown() {
    }

    /**
     *<p>
     * Creates a test suite of the tests contained in this class.</p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(WorkdaysUnitOfTimeTest.class);
    }

    /**
     *<p>
     * Tests accuracy of the method getValue()</p>
     *
     */
    public void testGetValueAccuracy() {
        WorkdaysUnitOfTime days = WorkdaysUnitOfTime.DAYS;
        assertEquals("The num of day should be 2", days.getValue(), 2);
    }

    /**
     *<p>
     * Tests accuracy of the method toString()</p>
     *
     */
    public void testToStringAccuracy() {
        WorkdaysUnitOfTime days = WorkdaysUnitOfTime.DAYS;
        assertTrue("The name should be returned", days.toString().toUpperCase().indexOf("DAYS") != -1);
    }

}
