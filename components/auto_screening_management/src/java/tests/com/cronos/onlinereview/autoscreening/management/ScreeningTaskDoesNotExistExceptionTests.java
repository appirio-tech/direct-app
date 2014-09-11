/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management;

import junit.framework.TestCase;

/**
 * <p>
 * Test cases of ScreeningTaskDoesNotExistException. Tests the class for proper saving the provided
 * arguments.
 * </p>
 *
 * @author haozhangr
 * @version 1.0
 */
public class ScreeningTaskDoesNotExistExceptionTests extends TestCase {
    /**
     * <p>
     * A <code>upload</code> used for testing.
     * </p>
     */
    private static final long UPLOAD = 123;

    /**
     * <p>
     * A <code>uploads</code> used for testing.
     * </p>
     */
    private static final long[] UPLOADS = new long[] {1, 2, 3};

    /**
     * <p>
     * An instance of <code>ScreeningTaskDoesNotExistException</code> which is tested.
     * </p>
     */
    private ScreeningTaskDoesNotExistException target = null;

    /**
     * <p>
     * setUp() routine. Creates test ScreeningTaskDoesNotExistException instance.
     * </p>
     */
    protected void setUp() {
        this.target = new ScreeningTaskDoesNotExistException(UPLOAD);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies ScreeningTaskDoesNotExistException subclasses ScreeningManagementException.
     * </p>
     */
    public void testInheritance() {
        assertTrue(
                "ScreeningTaskDoesNotExistException does not subclass ScreeningManagementException.",
                target instanceof ScreeningManagementException);
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>ScreeningTaskDoesNotExistException(long)</code> Create for
     * proper behavior. Verifies that the upload is saved as is.
     * </p>
     */
    public void testCreate_Long_1_accuracy() {
        assertEquals("The upload should be saved as is", UPLOAD, target.getUploads()[0]);
        assertTrue("There should be message set up.", target.getMessage().length() > 0);
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>ScreeningTaskDoesNotExistException(long[])</code> Create for
     * proper behavior. Verifies that the uploads is saved as is.
     * </p>
     */
    public void testCreate_LongArray_1_accuracy() {
        target = new ScreeningTaskDoesNotExistException(UPLOADS);
        assertNotSame("A shallow copy should be made.", UPLOADS, target.getUploads());
        assertEquals("The length should be correctly.", UPLOADS.length, target.getUploads().length);
        assertEquals("The uploads should be saved as is", UPLOADS[0], target.getUploads()[0]);
        assertEquals("The uploads should be saved as is", UPLOADS[1], target.getUploads()[1]);
        assertEquals("The uploads should be saved as is", UPLOADS[2], target.getUploads()[2]);
        assertTrue("There should be message set up.", target.getMessage().length() > 0);
    }

    /**
     * <p>
     * Failure test. Tests the <code>ScreeningTaskDoesNotExistException(long[])</code> failed for
     * invalid parameter.
     * </p>
     */
    public void testCreate_LongArray_1_failure() {
        try {
            new ScreeningTaskDoesNotExistException(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>ScreeningTaskDoesNotExistException(long[])</code> failed for
     * invalid parameter.
     * </p>
     */
    public void testCreate_LongArray_2_failure() {
        try {
            new ScreeningTaskDoesNotExistException(new long[] {});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getUploads()</code> for proper behavior.
     * </p>
     */
    public void testGetUploads() {
        target = new ScreeningTaskDoesNotExistException(UPLOADS);
        assertEquals("The length should be correctly.", UPLOADS.length, target.getUploads().length);
        assertEquals("The uploads should be returned as is", UPLOADS[0], target.getUploads()[0]);
        assertEquals("The uploads should be returned as is", UPLOADS[1], target.getUploads()[1]);
        assertEquals("The uploads should be returned as is", UPLOADS[2], target.getUploads()[2]);
    }
}
