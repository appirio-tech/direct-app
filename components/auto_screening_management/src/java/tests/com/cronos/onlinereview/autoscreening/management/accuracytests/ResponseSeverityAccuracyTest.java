/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management.accuracytests;

import com.cronos.onlinereview.autoscreening.management.ResponseSeverity;

import junit.framework.TestCase;

/**
 * <p>The accuracy test cases for ResponseSeverity class.</p>
 *
 * @author oodinary
 * @version 1.0
 */
public class ResponseSeverityAccuracyTest extends TestCase {

	/**
     * <p>The default id of this class testing.</p>
     */
    private final long defaultID = 100;
    
    /**
     * <p>The default name of this class testing.</p>
     */
    private final String defaultName = "defaultName";
    
	/**
     * <p>A ResponseSeverity instance for testing.</p>
     */
    private ResponseSeverity responseSeverity = null;

    /**
     * <p>Initialization.</p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
    	responseSeverity = new ResponseSeverity();
    }

    /**
     * <p>Set responseSeverity to null.</p>
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
    	responseSeverity = null;
    }
    
    /**
     * <p>Tests the ctor().</p>
     * <p>The ResponseSeverity instance should be created successfully.</p>
     */
    public void testCtor_Accuracy() {

        assertNotNull("ResponseSeverity should be accurately created.", responseSeverity);
        
        assertTrue("responseSeverity should be instance of ResponseSeverity.",
        		responseSeverity instanceof ResponseSeverity);
    }
    
    /**
     * <p>Tests the accuracy of the setter setId(long).</p>
     * <p>Using reflection.</p>
     */
    public void testSetter_SetId() {

    	responseSeverity.setId(defaultID);

        // Gets id by reflection.
        Object id = AccuracyTestHelper.getPrivateField(ResponseSeverity.class, responseSeverity, "id");

        assertEquals("The id should be set correctly.",
        		new Long(defaultID), id);
    }
    
    /**
     * <p>Tests the accuracy of the getter getId().</p>
     * <p>Using validated setter to test.</p>
     */
    public void testGetter_GetId() {

    	responseSeverity.setId(defaultID);

        assertEquals("The id should be got correctly.",
        		defaultID, responseSeverity.getId());
    }
    
    /**
     * <p>Tests the accuracy of the setter setName(String).</p>
     * <p>Using reflection.</p>
     */
    public void testSetter_SetName() {

    	responseSeverity.setName(defaultName);

        // Gets name by reflection.
        Object name = AccuracyTestHelper.getPrivateField(ResponseSeverity.class, responseSeverity, "name");

        assertEquals("The name should be set correctly.",
        		defaultName, name);
    }
    
    /**
     * <p>Tests the accuracy of the getter getName().</p>
     * <p>Using validated setter to test.</p>
     */
    public void testGetter_GetName() {

    	responseSeverity.setName(defaultName);

        assertEquals("The name should be got correctly.",
        		defaultName, responseSeverity.getName());
    }
}
