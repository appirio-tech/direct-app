/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review;

import com.topcoder.util.errorhandling.BaseException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This class tests the <code>ReviewManagementException</code> class. It tests the
 * ReviewManagementException(String), ReviewManagementException(String, Throwable) constructors.
 * </p>
 *
 * @author icyriver
 * @version 1.0
 */
public class ReviewManagementExceptionTest extends TestCase {
    /**
     * <p>
     * The string of exception message for unit test.
     * </p>
     */
    private static final String TEST_MESSAGE = "ReviewManagement Exception.";

    /**
     * <p>
     * The instance of <code>ReviewManagementException</code> for unit test.
     * </p>
     */
    private ReviewManagementException test = null;

    /**
     * <p>
     * Returns the test suite of <code>ReviewManagementExceptionTest</code>.
     * </p>
     *
     * @return the test suite of <code>ReviewManagementExceptionTest</code>.
     */
    public static Test suite() {
        return new TestSuite(ReviewManagementExceptionTest.class);
    }

    /**
     * <p>
     * Basic test of <code>ReviewManagementException(String)</code> constructor.
     * </p>
     */
    public void testReviewManagementExceptionCtor1_Basic() {
        // check null here.
        assertNotNull("Create ReviewManagementException failed.",
            new ReviewManagementException(TEST_MESSAGE));
    }

    /**
     * <p>
     * Detail Test of <code>ReviewManagementException(String)</code> constructor. Creates a
     * instance and get it's attributes for test.
     * </p>
     */
    public void testReviewManagementExceptionCtor1_Detail() {
        // create a exception instance for test.
        test = new ReviewManagementException(TEST_MESSAGE);

        // check null here.
        assertNotNull("Create ReviewManagementException failed.", test);

        // check the type here.
        assertTrue("The ReviewManagementException should extend from BaseException.",
            test instanceof BaseException);

        // check error message here.
        assertEquals("Equal error message expected.", TEST_MESSAGE, test.getMessage());
    }

    /**
     * <p>
     * Test of <code>ReviewManagementException(String, Throwable)</code> constructor with error.
     * </p>
     */
    public void testReviewManagementExceptionCtor2_Error() {
        Throwable cause = new Error();

        // create a exception instance for test.
        test = new ReviewManagementException(TEST_MESSAGE, cause);

        // check null here.
        assertNotNull("Create ReviewManagementException failed.", test);

        // check the type here.
        assertTrue("The ReviewManagementException should extend from BaseException.",
            test instanceof BaseException);

        // check error message and cause here.
        assertNotNull("Error message expected.", test.getMessage());
        assertEquals("Equal inner cause expected.", cause, test.getCause());
    }

    /**
     * <p>
     * Test of <code>ReviewManagementException(String, Throwable)</code> constructor with
     * exception.
     * </p>
     */
    public void testReviewManagementExceptionCtor2_Exception() {
        Throwable cause = new Exception();

        // create a exception instance for test.
        test = new ReviewManagementException(TEST_MESSAGE, cause);

        // check null here.
        assertNotNull("Create ReviewManagementException failed.", test);

        // check the type here.
        assertTrue("The ReviewManagementException should extend from BaseException.",
            test instanceof BaseException);

        // check error message and cause here.
        assertNotNull("Error message expected.", test.getMessage());
        assertEquals("Equal inner cause expected.", cause, test.getCause());
    }

    /**
     * <p>
     * Test if <code>ReviewManagementException</code> works properly. With the constructor of
     * <code>ReviewManagementException(String)</code>.
     * </p>
     */
    public void testReviewManagementExceptionUsage_Ctor1() {
        try {
            throw new ReviewManagementException(TEST_MESSAGE);
        } catch (ReviewManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Test if <code>ReviewManagementException</code> works properly. With the constructor of
     * <code>ReviewManagementException(String, Throwable)</code>.
     * </p>
     */
    public void testReviewManagementExceptionUsage_Ctor2() {
        try {
            throw new ReviewManagementException(TEST_MESSAGE, new Exception());
        } catch (ReviewManagementException e) {
            // success
        }
    }
}
