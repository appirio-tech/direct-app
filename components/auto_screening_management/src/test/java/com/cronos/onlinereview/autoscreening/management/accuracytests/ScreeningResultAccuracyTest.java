/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management.accuracytests;

import com.cronos.onlinereview.autoscreening.management.ScreeningResponse;
import com.cronos.onlinereview.autoscreening.management.ScreeningResult;

import junit.framework.TestCase;

/**
 * <p>The accuracy test cases for ScreeningResult class.</p>
 *
 * @author oodinary
 * @version 1.0
 */
public class ScreeningResultAccuracyTest extends TestCase {

	/**
     * <p>The default id of this class testing.</p>
     */
    private final long defaultID = 100;
    
    /**
     * <p>The default dynamic text of this class testing.</p>
     */
    private final String defaultDynamicText = "defaultDynamicText";
    
    /**
     * <p>A ScreeningResult instance for testing.</p>
     */
    private ScreeningResult screeningResult = null;
    
	/**
     * <p>A ScreeningResponse instance for testing.</p>
     */
    private ScreeningResponse screeningResponse = null;

    /**
     * <p>Initialization.</p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
    	screeningResult = new ScreeningResult();
    	screeningResponse = new ScreeningResponse();
    }

    /**
     * <p>Set screeningResponse to null.</p>
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
    	screeningResponse = null;
    	screeningResult = null;
    }
    
    /**
     * <p>Tests the ctor().</p>
     * <p>The ScreeningResponse instance should be created successfully.</p>
     */
    public void testCtor_Accuracy() {

        assertNotNull("ScreeningResult should be accurately created.", screeningResult);
        
        assertTrue("screeningResult should be instance of ScreeningResult.",
        		screeningResult instanceof ScreeningResult);
    }
    
    /**
     * <p>Tests the accuracy of the setter setId(long).</p>
     * <p>Using reflection.</p>
     */
    public void testSetter_SetId() {

    	screeningResult.setId(defaultID);

        // Gets id by reflection.
        Object id = AccuracyTestHelper.getPrivateField(ScreeningResult.class, screeningResult, "id");

        assertEquals("The id should be set correctly.",
        		new Long(defaultID), id);
    }
    
    /**
     * <p>Tests the accuracy of the getter getId().</p>
     * <p>Using validated setter to test.</p>
     */
    public void testGetter_GetId() {

    	screeningResult.setId(defaultID);

        assertEquals("The id should be got correctly.",
        		defaultID, screeningResult.getId());
    }
    
    /**
     * <p>Tests the accuracy of the setter setScreeningResponse(ScreeningResponse).</p>
     * <p>Using reflection.</p>
     */
    public void testSetter_SetScreeningResponse() {

    	screeningResult.setScreeningResponse(screeningResponse);

        // Gets screeningResult by reflection.
        Object resp = AccuracyTestHelper.getPrivateField(ScreeningResult.class,
        		screeningResult, "screeningResponse");

        assertEquals("The screeningResponse should be set correctly.",
        		screeningResponse, resp);
    }
    
    /**
     * <p>Tests the accuracy of the getter getScreeningResponse()().</p>
     * <p>Using validated setter to test.</p>
     */
    public void testGetter_GetScreeningResponse() {

    	screeningResult.setScreeningResponse(screeningResponse);

        assertEquals("The screeningResponse should be got correctly.",
        		screeningResponse, screeningResult.getScreeningResponse());
    }
    
    /**
     * <p>Tests the accuracy of the setter setDynamicText(String).</p>
     * <p>Using reflection.</p>
     */
    public void testSetter_SetDynamicText() {

    	screeningResult.setDynamicText(defaultDynamicText);

        // Gets dynamicText by reflection.
        Object dynamicText = AccuracyTestHelper.getPrivateField(ScreeningResult.class,
        		screeningResult, "dynamicText");

        assertEquals("The dynamicText should be set correctly.",
        		defaultDynamicText, dynamicText);
    }
    
    /**
     * <p>Tests the accuracy of the getter getDynamicText().</p>
     * <p>Using validated setter to test.</p>
     */
    public void testGetter_GetDynamicText() {

    	screeningResult.setDynamicText(defaultDynamicText);

        assertEquals("The dynamicText should be got correctly.",
        		defaultDynamicText, screeningResult.getDynamicText());
    }
}
