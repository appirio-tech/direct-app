/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This class tests the <code>ReviewEntityNotFoundException</code> class. It tests the
 * ReviewEntityNotFoundException(String, long) constructor and getId() method.
 * </p>
 *
 * @author icyriver
 * @version 1.0
 */
public class ReviewEntityNotFoundExceptionTest extends TestCase {
    /**
     * <p>
     * The string of exception message for unit test.
     * </p>
     */
    private static final String TEST_MESSAGE = "ReviewEntityNotFound Exception.";

    /**
     * <p>
     * The instance of <code>ReviewEntityNotFoundException</code> for unit test.
     * </p>
     */
    private ReviewEntityNotFoundException test = null;

    /**
     * <p>
     * The entity id that can't be found in persistence for unit test.
     * </p>
     */
    private long id = 100;

    /**
     * <p>
     * Returns the test suite of <code>ReviewEntityNotFoundExceptionTest</code>.
     * </p>
     *
     * @return the test suite of <code>ReviewEntityNotFoundExceptionTest</code>.
     */
    public static Test suite() {
        return new TestSuite(ReviewEntityNotFoundExceptionTest.class);
    }

    /**
     * <p>
     * Basic test of <code>ReviewEntityNotFoundException(String, long)</code> constructor.
     * </p>
     */
    public void testReviewEntityNotFoundExceptionCtor_Basic() {
        // check null here.
        assertNotNull("Create ReviewEntityNotFoundException failed.",
            new ReviewEntityNotFoundException(TEST_MESSAGE, id));
    }

    /**
     * <p>
     * Detail Test of <code>ReviewEntityNotFoundException(String, long)</code> constructor.
     * Creates a instance and get it's attributes for test.
     * </p>
     */
    public void testReviewEntityNotFoundExceptionCtor_Detail() {
        // create a exception instance for test.
        test = new ReviewEntityNotFoundException(TEST_MESSAGE, id);

        // check null here.
        assertNotNull("Create ReviewEntityNotFoundException failed.", test);

        // check the type here.
        assertTrue("The ReviewEntityNotFoundException should extend from ReviewPersistenceException.",
            test instanceof ReviewPersistenceException);

        // check error message and id here.
        assertEquals("Equal error message expected.", TEST_MESSAGE, test.getMessage());
        assertEquals("Equal review id expected.", id, test.getId());
    }

    /**
     * <p>
     * Test of <code>getId()</code> method. It tests with getting id only once.
     * </p>
     */
    public void testGetId_Once() {
        // create a exception instance for test.
        test = new ReviewEntityNotFoundException(TEST_MESSAGE, id);

        // check the id here.
        assertEquals("Equal review id expected.", id, test.getId());
    }

    /**
     * <p>
     * Test of <code>getId()</code> method. It tests with getting id more times.
     * </p>
     */
    public void testGetId_More() {
        // create a exception instance for test.
        test = new ReviewEntityNotFoundException(TEST_MESSAGE, id);

        // check the id here.
        assertEquals("Equal review id expected.", id, test.getId());

        // create a exception instance with a new id for test.
        test = new ReviewEntityNotFoundException(TEST_MESSAGE, -1);

        // check the id here.
        assertEquals("Equal review id expected.", -1, test.getId());
    }

    /**
     * <p>
     * Test if <code>ReviewEntityNotFoundException</code> works properly. With the constructor of
     * <code>ReviewEntityNotFoundException(String, long)</code>.
     * </p>
     */
    public void testReviewEntityNotFoundExceptionUsage() {
        try {
            throw new ReviewEntityNotFoundException(TEST_MESSAGE, id);
        } catch (ReviewEntityNotFoundException e) {
            // success
        }
    }
}
