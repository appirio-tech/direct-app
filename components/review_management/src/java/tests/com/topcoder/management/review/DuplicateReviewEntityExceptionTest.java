/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This class tests the <code>DuplicateReviewEntityException</code> class. It tests the
 * DuplicateReviewEntityException(String, long) constructor and getId() method.
 * </p>
 *
 * @author icyriver
 * @version 1.0
 */
public class DuplicateReviewEntityExceptionTest extends TestCase {
    /**
     * <p>
     * The string of exception message for unit test.
     * </p>
     */
    private static final String TEST_MESSAGE = "DuplicateReviewEntity Exception.";

    /**
     * <p>
     * The instance of <code>DuplicateReviewEntityException</code> for unit test.
     * </p>
     */
    private DuplicateReviewEntityException test = null;

    /**
     * <p>
     * The id of the duplicated review entity for unit test.
     * </p>
     */
    private long id = 100;

    /**
     * <p>
     * Returns the test suite of <code>DuplicateReviewEntityExceptionTest</code>.
     * </p>
     *
     * @return the test suite of <code>DuplicateReviewEntityExceptionTest</code>.
     */
    public static Test suite() {
        return new TestSuite(DuplicateReviewEntityExceptionTest.class);
    }

    /**
     * <p>
     * Basic test of <code>DuplicateReviewEntityException(String, long)</code> constructor.
     * </p>
     */
    public void testDuplicateReviewEntityExceptionCtor_Basic() {
        // check null here.
        assertNotNull("Create DuplicateReviewEntityException failed.",
            new DuplicateReviewEntityException(TEST_MESSAGE, id));
    }

    /**
     * <p>
     * Detail Test of <code>DuplicateReviewEntityException(String, long)</code> constructor.
     * Creates a instance and get it's attributes for test.
     * </p>
     */
    public void testDuplicateReviewEntityExceptionCtor_Detail() {
        // create a exception instance for test.
        test = new DuplicateReviewEntityException(TEST_MESSAGE, id);

        // check null here.
        assertNotNull("Create DuplicateReviewEntityException failed.", test);

        // check the type here.
        assertTrue("The DuplicateReviewEntityException should extend from ReviewPersistenceException.",
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
        test = new DuplicateReviewEntityException(TEST_MESSAGE, id);

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
        test = new DuplicateReviewEntityException(TEST_MESSAGE, id);

        // check the id here.
        assertEquals("Equal review id expected.", id, test.getId());

        // create a exception instance with a new id for test.
        test = new DuplicateReviewEntityException(TEST_MESSAGE, -1);

        // check the id here.
        assertEquals("Equal review id expected.", -1, test.getId());
    }

    /**
     * <p>
     * Test if <code>DuplicateReviewEntityException</code> works properly. With the constructor of
     * <code>DuplicateReviewEntityException(String, long)</code>.
     * </p>
     */
    public void testDuplicateReviewEntityExceptionUsage() {
        try {
            throw new DuplicateReviewEntityException(TEST_MESSAGE, id);
        } catch (DuplicateReviewEntityException e) {
            // success
        }
    }
}
