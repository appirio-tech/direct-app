/*
 * ComponentName: WorkDays
 * FileName: ConfigurationFileExceptionTest.java
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
 * This class contains the accuracy tests for ConfigurationFileException.java
 * </p>
 *
 * @author woodjhon
 * @version 1.0
 */
public class ConfigurationFileExceptionTest extends TestCase {
    /**
     * <p>
     * Represents an instance of ConfigurationFileException to be used for
     * testing.
     * </p>
     */
    private ConfigurationFileException s = null;

    /**
     * <p>
     * Creates an instance for the Test.
     * </p>
     *
     * @param name the name of the TestCase.
     */
    public ConfigurationFileExceptionTest(String name) {
        super(name);
    }

    /**
     * <p>
     * Sets up the fixture.
     * </p>
     */
    protected void setUp() {
    } // end setUp()

    /**
     * <p>
     * Tears down the fixture.
     * </p>
     */
    protected void tearDown() {
    } // end tearDown()

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(ConfigurationFileExceptionTest.class);
    }

    /**
     * <p>
     * Tests accuracy of the constructor ConfigurationFileException(String
     * message, Exception cause)
     * </p>
     */
    public void testConstructorAccuracy() {
        ConfigurationFileException exp = new ConfigurationFileException("msg",
                new NullPointerException());
        assertTrue(exp.getMessage().indexOf("msg") != -1);
        assertTrue(exp.getCause() instanceof NullPointerException);
    }
} // end ConfigurationFileExceptionTest
