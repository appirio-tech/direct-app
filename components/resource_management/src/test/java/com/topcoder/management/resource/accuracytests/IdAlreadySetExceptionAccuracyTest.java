/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.accuracytests;

import com.topcoder.management.resource.IdAlreadySetException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This accuracy tests addresses the functionality provided by the
 * <code>IdAlreadySetException</code> class. It tests the
 * IdAlreadySetException(String) constructor.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class IdAlreadySetExceptionAccuracyTest extends TestCase {
    /**
     * <p>
     * The string of message for test.
     * </p>
     */
    private static final String TEST_MESSAGE = "IdAlreadySetException occurs.";

    /**
     * <p>
     * The instance of <code>IdAlreadySetException</code> for test.
     * </p>
     */
    private IdAlreadySetException test = null;

    /**
     * <p>
     * Test suite of <code>IdAlreadySetExceptionAccuracyTest</code>.
     * </p>
     * @return Test suite of <code>IdAlreadySetExceptionAccuracyTest</code>.
     */
    public static Test suite() {
        return new TestSuite(IdAlreadySetExceptionAccuracyTest.class);
    }

    /**
     * <p>
     * Basic test of <code>IdAlreadySetException(String)</code> constructor.
     * </p>
     */
    public void testIdAlreadySetExceptionCtor1_Basic() {
        // check null here.
        assertNotNull("Create IdAlreadySetException failed.", new IdAlreadySetException(TEST_MESSAGE));
    }

    /**
     * <p>
     * Detail test of <code>IdAlreadySetException(String)</code> constructor.
     * Creates a instance and get its attributes for test.
     * </p>
     */
    public void testIdAlreadySetExceptionCtor1_Detail() {
        // create a exception instance for test.
        test = new IdAlreadySetException(TEST_MESSAGE);

        // check null here.
        assertNotNull("Create IdAlreadySetException failed.", test);

        // check the exception type here.
        assertTrue("IdAlreadySetException should extend IllegalStateException.",
                test instanceof IllegalStateException);

        // check the error message here.
        assertEquals("Equal error message expected.", TEST_MESSAGE, test.getMessage());
    }

    /**
     * <p>
     * Test for <code>IdAlreadySetException</code> works properly.
     * </p>
     */
    public void testIdAlreadySetExceptionUsage() {
        // test with ctor1.
        try {
            throw new IdAlreadySetException(TEST_MESSAGE);
        } catch (IdAlreadySetException e) {
            // success
        }
    }
}
