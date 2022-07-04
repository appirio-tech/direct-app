/*
 * ComponentName: WorkDays
 * FileName: DefaultWorkdaysFactoryTest.java
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
 * This class contains the accuracy unit tests for DefaultWorkdaysFactory.java
 * </p>
 *
 * @author woodjhon
 * @version 1.0
 */
public class DefaultWorkdaysFactoryTest extends TestCase {
    /**
     * <p>
     * Represents an instance of DefaultWorkdaysFactory to be used for
     * testing.
     * </p>
     */
    private DefaultWorkdaysFactory factory = null;

    /**
     * <p>
     * Creates an instance for the Test.
     * </p>
     *
     * @param name the name of the TestCase.
     */
    public DefaultWorkdaysFactoryTest(String name) {
        super(name);
    }

    /**
     * <p>
     * Sets up the fixture.
     * </p>
     * 
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        ConfigHelper.initialConfigManager();
        factory = new DefaultWorkdaysFactory();
    } 

    /**
     * <p>
     * Tears down the fixture.
     * </p>
     */
    protected void tearDown() {
    } 

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(DefaultWorkdaysFactoryTest.class);
    }
    /**
     * <p>
     * Tests accuracy of the method createWorkdaysInstance()
     * </p>
     * 
     * @throws Exception to JUnit
     */
    public void testCreateWorkdaysInstanceAccuracy() throws Exception {
        Workdays wd = factory.createWorkdaysInstance();
        assertNotNull(wd);
        assertTrue(wd.isSaturdayWorkday());
        assertFalse(wd.isSundayWorkday());
    } 
} 
