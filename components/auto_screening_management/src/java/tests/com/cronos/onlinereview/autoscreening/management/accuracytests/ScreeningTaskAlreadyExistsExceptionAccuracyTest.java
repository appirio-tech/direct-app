/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management.accuracytests;

import com.cronos.onlinereview.autoscreening.management.ScreeningManagementException;
import com.cronos.onlinereview.autoscreening.management.ScreeningTaskAlreadyExistsException;

import junit.framework.TestCase;

/**
 * <p>The accuracy test cases for ScreeningTaskAlreadyExistsException class.</p>
 *
 * @author oodinary
 * @version 1.0
 */
public class ScreeningTaskAlreadyExistsExceptionAccuracyTest extends TestCase {

	/**
     * <p>The default upload value of this Exception.</p>
     */
    private final long defaultUpload = 100;
    
	/**
     * <p>An instance for testing.</p>
     */
    private ScreeningTaskAlreadyExistsException defaultException = null;

    /**
     * <p>Initialization.</p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        defaultException = new ScreeningTaskAlreadyExistsException(defaultUpload);
    }

    /**
     * <p>Set defaultException to null.</p>
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        defaultException = null;
    }
    
    /**
     * <p>Tests the ctor(long).</p>
     * <p>The ScreeningTaskAlreadyExistsException instance should be created successfully.</p>
     */
    public void testCtor_Long_Accuracy() {

        assertNotNull("ScreeningTaskAlreadyExistsException should be accurately created.", defaultException);
        
        assertTrue("defaultException should be instance of ScreeningTaskAlreadyExistsException.",
                defaultException instanceof ScreeningTaskAlreadyExistsException);
        assertTrue("defaultException should be instance of ScreeningManagementException.",
                defaultException instanceof ScreeningManagementException);
        assertEquals("ScreeningTaskAlreadyExistsException should be accurately created with the same upload value.",
        		defaultUpload, defaultException.getUpload());
    }
}
