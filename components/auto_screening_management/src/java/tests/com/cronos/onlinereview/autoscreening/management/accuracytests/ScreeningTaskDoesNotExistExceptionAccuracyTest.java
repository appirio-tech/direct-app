/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management.accuracytests;

import com.cronos.onlinereview.autoscreening.management.ScreeningManagementException;
import com.cronos.onlinereview.autoscreening.management.ScreeningTaskDoesNotExistException;

import junit.framework.TestCase;

/**
 * <p>The accuracy test cases for ScreeningTaskDoesNotExistException class.</p>
 *
 * @author oodinary
 * @version 1.0
 */
public class ScreeningTaskDoesNotExistExceptionAccuracyTest extends TestCase {

	/**
     * <p>The default upload value of this Exception.</p>
     */
    private final long defaultUpload = 100;
    
    /**
     * <p>The default upload array of this Exception.</p>
     */
    private final long[] defaultUploads = new long[] {1, 10, 100, 1000};
    
	/**
     * <p>An instance for testing.</p>
     */
    private ScreeningTaskDoesNotExistException defaultException = null;

    /**
     * <p>Initialization.</p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        defaultException = new ScreeningTaskDoesNotExistException(defaultUpload);
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
     * <p>The ScreeningTaskDoesNotExistException instance should be created successfully.</p>
     */
    public void testCtor_Long_Accuracy() {

        assertNotNull("ScreeningTaskDoesNotExistException should be accurately created.", defaultException);
        
        assertTrue("defaultException should be instance of ScreeningTaskDoesNotExistException.",
                defaultException instanceof ScreeningTaskDoesNotExistException);
        assertTrue("defaultException should be instance of ScreeningManagementException.",
                defaultException instanceof ScreeningManagementException);
        
        assertEquals("ScreeningTaskDoesNotExistException should be accurately created with the same upload value.",
        		1, defaultException.getUploads().length);
        assertEquals("ScreeningTaskDoesNotExistException should be accurately created with the same upload value.",
        		defaultUpload, defaultException.getUploads()[0]);
    }
    
    /**
     * <p>Tests the ctor(long[]).</p>
     * <p>The ScreeningTaskDoesNotExistException instance should be created successfully.</p>
     */
    public void testCtor_LongArray_Accuracy() {

    	defaultException = new ScreeningTaskDoesNotExistException(defaultUploads);
        assertNotNull("ScreeningTaskDoesNotExistException should be accurately created.", defaultException);
        
        assertTrue("defaultException should be instance of ScreeningTaskDoesNotExistException.",
                defaultException instanceof ScreeningTaskDoesNotExistException);
        assertTrue("defaultException should be instance of ScreeningManagementException.",
                defaultException instanceof ScreeningManagementException);

        assertEquals("ScreeningTaskDoesNotExistException should be accurately created with the same upload value.",
        		4, defaultException.getUploads().length);
        assertEquals("ScreeningTaskDoesNotExistException should be accurately created with the same upload value.",
        		1, defaultException.getUploads()[0]);
        assertEquals("ScreeningTaskDoesNotExistException should be accurately created with the same upload value.",
        		10, defaultException.getUploads()[1]);
        assertEquals("ScreeningTaskDoesNotExistException should be accurately created with the same upload value.",
        		100, defaultException.getUploads()[2]);
        assertEquals("ScreeningTaskDoesNotExistException should be accurately created with the same upload value.",
        		1000, defaultException.getUploads()[3]);
    }
}
