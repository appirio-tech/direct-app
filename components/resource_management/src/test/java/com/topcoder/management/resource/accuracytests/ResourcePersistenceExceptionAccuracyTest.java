/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.accuracytests;

import com.topcoder.management.resource.persistence.ResourcePersistenceException;

import com.topcoder.util.errorhandling.BaseException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This accuracy tests addresses the functionality provided by the
 * <code>ResourcePersistenceException</code> class. It tests the
 * ResourcePersistenceException(String) and ResourcePersistenceException(String,
 * Exception) constructors.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ResourcePersistenceExceptionAccuracyTest extends TestCase {
    /**
     * <p>
     * The string of message for test.
     * </p>
     */
    private static final String TEST_MESSAGE = "ResourcePersistenceException occurs.";

    /**
     * <p>
     * The instance of <code>ResourcePersistenceException</code> for test.
     * </p>
     */
    private ResourcePersistenceException test = null;

    /**
     * <p>
     * Test suite of <code>ResourcePersistenceExceptionAccuracyTest</code>.
     * </p>
     * @return Test suite of
     *         <code>ResourcePersistenceExceptionAccuracyTest</code>.
     */
    public static Test suite() {
        return new TestSuite(ResourcePersistenceExceptionAccuracyTest.class);
    }

    /**
     * <p>
     * Basic test of <code>ResourcePersistenceException(String)</code>
     * constructor.
     * </p>
     */
    public void testResourcePersistenceExceptionCtor1_Basic() {
        // check null here.
        assertNotNull("Create ResourcePersistenceException failed.", new ResourcePersistenceException(
                TEST_MESSAGE));
    }

    /**
     * <p>
     * Detail test of <code>ResourcePersistenceException(String)</code>
     * constructor. Creates a instance and get its attributes for test.
     * </p>
     */
    public void testResourcePersistenceExceptionCtor1_Detail() {
        // create a exception instance for test.
        test = new ResourcePersistenceException(TEST_MESSAGE);

        // check null here.
        assertNotNull("Create ResourcePersistenceException failed.", test);

        // check the exception type here.
        assertTrue("ResourcePersistenceException should extend BaseException.", test instanceof BaseException);

        // check the error message here.
        assertEquals("Equal error message expected.", TEST_MESSAGE, test.getMessage());
    }

    /**
     * <p>
     * Basic test of
     * <code>ResourcePersistenceException(String, Exception)</code>
     * constructor.
     * </p>
     */
    public void testResourcePersistenceExceptionCtor2_Basic() {
        // check null here.
        assertNotNull("Create ResourcePersistenceException failed.", new ResourcePersistenceException(
                TEST_MESSAGE, new Exception()));
    }

    /**
     * <p>
     * Test of <code>ResourcePersistenceException(String, Exception)</code>
     * constructor. Creates a instance and get its attributes for test.
     * </p>
     */
    public void testResourcePersistenceExceptionCtor2_Detail() {
        Exception cause = new Exception();

        // create a exception instance for test.
        test = new ResourcePersistenceException(TEST_MESSAGE, cause);

        // check null here.
        assertNotNull("Create ResourcePersistenceException failed.", test);

        // check type here.
        assertTrue("ResourcePersistenceException should extend BaseException.", test instanceof BaseException);

        // check cause here.
        assertEquals("Equal cause expected.", cause, test.getCause());
    }

    /**
     * <p>
     * Test for <code>ResourcePersistenceException</code> works properly.
     * </p>
     */
    public void testResourcePersistenceExceptionUsage() {
        // test with ctor1.
        try {
            throw new ResourcePersistenceException(TEST_MESSAGE);
        } catch (ResourcePersistenceException e) {
            // success
        }

        // test with ctor2.
        try {
            throw new ResourcePersistenceException(TEST_MESSAGE, new Exception());
        } catch (ResourcePersistenceException e) {
            // success
        }
    }
}
