/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management;

import junit.framework.TestCase;

/**
 * <p>
 * Test cases of ScreeningTaskAlreadyExistsException. Tests the class for proper saving the provided
 * arguments.
 * </p>
 *
 * @author haozhangr
 * @version 1.0
 */
public class ScreeningTaskAlreadyExistsExceptionTests extends TestCase {

    /**
     * <p>
     * A <code>upload</code> used for testing.
     * </p>
     */
    private static final long UPLOAD = 123;

    /**
     * <p>
     * An instance of <code>ScreeningTaskAlreadyExistsException</code> which is tested.
     * </p>
     */
    private ScreeningTaskAlreadyExistsException target = null;

    /**
     * <p>
     * setUp() routine. Creates test ScreeningTaskAlreadyExistsException instance.
     * </p>
     */
    protected void setUp() {
        this.target = new ScreeningTaskAlreadyExistsException(UPLOAD);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies ScreeningTaskAlreadyExistsException subclasses ScreeningManagementException.
     * </p>
     */
    public void testInheritance() {
        assertTrue(
                "ScreeningTaskAlreadyExistsException does not subclass ScreeningManagementException.",
                target instanceof ScreeningManagementException);
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>ScreeningTaskAlreadyExistsException(long)</code> Create for
     * proper behavior. Verifies that the upload is saved as is.
     * </p>
     */
    public void testCreate_Long_1_accuracy() {
        assertEquals("The upload should be saved as is", UPLOAD, target.getUpload());
        assertTrue("There should be message set up.", target.getMessage().length() > 0);
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getUpload()</code> for proper behavior. Verifies that the
     * upload is returned correctly.
     * </p>
     */
    public void testGetUpload() {
        assertEquals("The upload should be returned as is", UPLOAD, target.getUpload());
    }
}
