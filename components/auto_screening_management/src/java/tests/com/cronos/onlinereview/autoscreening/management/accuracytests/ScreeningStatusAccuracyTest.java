/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management.accuracytests;

import com.cronos.onlinereview.autoscreening.management.ScreeningStatus;

import junit.framework.TestCase;

/**
 * <p>The accuracy test cases for ScreeningStatus class.</p>
 *
 * @author oodinary
 * @version 1.0
 */
public class ScreeningStatusAccuracyTest extends TestCase {

	/**
     * <p>The default id of this class testing.</p>
     */
    private final long defaultID = 100;
    
    /**
     * <p>The default name of this class testing.</p>
     */
    private final String defaultName = ScreeningStatus.PASSED;
    
	/**
     * <p>A ScreeningStatus instance for testing.</p>
     */
    private ScreeningStatus screeningStatus = null;

    /**
     * <p>Initialization.</p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
    	screeningStatus = new ScreeningStatus();
    }

    /**
     * <p>Set screeningStatus to null.</p>
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
    	screeningStatus = null;
    }
    
    /**
     * <p>Tests the accuracy of the setter setId(long).</p>
     * <p>Using reflection.</p>
     */
    public void testSetter_SetId() {

    	screeningStatus.setId(defaultID);

        // Gets id by reflection.
        Object id = AccuracyTestHelper.getPrivateField(ScreeningStatus.class, screeningStatus, "id");

        assertEquals("The id should be set correctly.",
        		new Long(defaultID), id);
    }
    
    /**
     * <p>Tests the accuracy of the getter getId().</p>
     * <p>Using validated setter to test.</p>
     */
    public void testGetter_GetId() {

    	screeningStatus.setId(defaultID);

        assertEquals("The id should be got correctly.",
        		defaultID, screeningStatus.getId());
    }
    
    /**
     * <p>Tests the accuracy of the setter setName(String).</p>
     * <p>Using reflection.</p>
     */
    public void testSetter_SetName() {

    	screeningStatus.setName(defaultName);

        // Gets name by reflection.
        Object name = AccuracyTestHelper.getPrivateField(ScreeningStatus.class, screeningStatus, "name");

        assertEquals("The name should be set correctly.",
        		defaultName, name);
    }
    
    /**
     * <p>Tests the accuracy of the getter getName().</p>
     * <p>Using validated setter to test.</p>
     */
    public void testGetter_GetName() {

    	screeningStatus.setName(defaultName);

        assertEquals("The name should be got correctly.",
        		defaultName, screeningStatus.getName());
    }
}
