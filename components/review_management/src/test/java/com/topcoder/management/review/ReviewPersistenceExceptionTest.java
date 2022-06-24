/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This class tests the <code>ReviewPersistenceException</code> class. It tests the
 * ReviewPersistenceException(String), ReviewPersistenceException(String, Throwable) constructors.
 * </p>
 *
 * @author icyriver
 * @version 1.0
 */
public class ReviewPersistenceExceptionTest extends TestCase {
    /**
     * <p>
     * The string of exception message for unit test.
     * </p>
     */
    private static final String TEST_MESSAGE = "ReviewPersistence Exception.";

    /**
     * <p>
     * The instance of <code>ReviewPersistenceException</code> for unit test.
     * </p>
     */
    private ReviewPersistenceException test = null;

    /**
     * <p>
     * Returns the test suite of <code>ReviewPersistenceExceptionTest</code>.
     * </p>
     *
     * @return the test suite of <code>ReviewPersistenceExceptionTest</code>.
     */
    public static Test suite() {
        return new TestSuite(ReviewPersistenceExceptionTest.class);
    }

    /**
     * <p>
     * Basic test of <code>ReviewPersistenceException(String)</code> constructor.
     * </p>
     */
    public void testReviewPersistenceExceptionCtor1_Basic() {
        // check null here.
        assertNotNull("Create ReviewPersistenceException failed.",
            new ReviewPersistenceException(TEST_MESSAGE));
    }

    /**
     * <p>
     * Detail Test of <code>ReviewPersistenceException(String)</code> constructor. Creates a instance
     * and get it's attributes for test.
     * </p>
     */
    public void testReviewPersistenceExceptionCtor1_Detail() {
        // create a exception instance for test.
        test = new ReviewPersistenceException(TEST_MESSAGE);

        // check null here.
        assertNotNull("Create ReviewPersistenceException failed.", test);

        // check the type here.
        assertTrue("The ReviewPersistenceException should extend from ReviewManagementException.",
            test instanceof ReviewManagementException);

        // check error message here.
        assertEquals("Equal error message expected.", TEST_MESSAGE, test.getMessage());
    }

    /**
     * <p>
     * Test of <code>ReviewPersistenceException(String, Throwable)</code> constructor with error.
     * </p>
     */
    public void testReviewPersistenceExceptionCtor2_Error() {
        Throwable cause = new Error();

        // create a exception instance for test.
        test = new ReviewPersistenceException(TEST_MESSAGE, cause);

        // check null here.
        assertNotNull("Create ReviewPersistenceException failed.", test);

        // check the type here.
        assertTrue("The ReviewPersistenceException should extend from ReviewManagementException.",
            test instanceof ReviewManagementException);

        // check error message and cause here.
        assertNotNull("Error message expected.", test.getMessage());
        assertEquals("Equal inner cause expected.", cause, test.getCause());
    }

    /**
     * <p>
     * Test of <code>ReviewPersistenceException(String, Throwable)</code> constructor with exception.
     * </p>
     */
    public void testReviewPersistenceExceptionCtor2_Exception() {
        Throwable cause = new Exception();

        // create a exception instance for test.
        test = new ReviewPersistenceException(TEST_MESSAGE, cause);

        // check null here.
        assertNotNull("Create ReviewPersistenceException failed.", test);

        // check the type here.
        assertTrue("The ReviewPersistenceException should extend from ReviewManagementException.",
            test instanceof ReviewManagementException);

        // check error message and cause here.
        assertNotNull("Error message expected.", test.getMessage());
        assertEquals("Equal inner cause expected.", cause, test.getCause());
    }

    /**
     * <p>
     * Test if <code>ReviewPersistenceException</code> works properly. With the constructor of
     * <code>ReviewPersistenceException(String)</code>.
     * </p>
     */
    public void testReviewPersistenceExceptionUsage_Ctor1() {
        try {
            throw new ReviewPersistenceException(TEST_MESSAGE);
        } catch (ReviewPersistenceException e) {
            // success
        }
    }

    /**
     * <p>
     * Test if <code>ReviewPersistenceException</code> works properly. With the constructor of
     * <code>ReviewPersistenceException(String, Throwable)</code>.
     * </p>
     */
    public void testReviewPersistenceExceptionUsage_Ctor2() {
        try {
            throw new ReviewPersistenceException(TEST_MESSAGE, new Exception());
        } catch (ReviewPersistenceException e) {
            // success
        }
    }
}
