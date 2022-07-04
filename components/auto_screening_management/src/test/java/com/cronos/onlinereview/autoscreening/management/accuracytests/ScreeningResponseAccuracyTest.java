/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management.accuracytests;

import com.cronos.onlinereview.autoscreening.management.ResponseSeverity;
import com.cronos.onlinereview.autoscreening.management.ScreeningResponse;

import junit.framework.TestCase;

/**
 * <p>The accuracy test cases for ScreeningResponse class.</p>
 *
 * @author oodinary
 * @version 1.0
 */
public class ScreeningResponseAccuracyTest extends TestCase {

	/**
     * <p>The default id of this class testing.</p>
     */
    private final long defaultID = 100;
    
    /**
     * <p>The default response code of this class testing.</p>
     */
    private final String defaultCode = "defaultCode";
    
    /**
     * <p>The default response text of this class testing.</p>
     */
    private final String defaultText = "defaultText";
    
	/**
     * <p>A ResponseSeverity instance for testing.</p>
     */
    private ResponseSeverity responseSeverity = null;
    
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
    	responseSeverity = new ResponseSeverity();
    	screeningResponse = new ScreeningResponse();
    }

    /**
     * <p>Set screeningResponse to null.</p>
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
    	screeningResponse = null;
    	responseSeverity = null;
    }
    
    /**
     * <p>Tests the ctor().</p>
     * <p>The ScreeningResponse instance should be created successfully.</p>
     */
    public void testCtor_Accuracy() {

        assertNotNull("ScreeningResponse should be accurately created.", screeningResponse);
        
        assertTrue("screeningResponse should be instance of ScreeningResponse.",
        		screeningResponse instanceof ScreeningResponse);
    }
    
    /**
     * <p>Tests the accuracy of the setter setId(long).</p>
     * <p>Using reflection.</p>
     */
    public void testSetter_SetId() {

    	screeningResponse.setId(defaultID);

        // Gets id by reflection.
        Object id = AccuracyTestHelper.getPrivateField(ScreeningResponse.class, screeningResponse, "id");

        assertEquals("The id should be set correctly.",
        		new Long(defaultID), id);
    }
    
    /**
     * <p>Tests the accuracy of the getter getId().</p>
     * <p>Using validated setter to test.</p>
     */
    public void testGetter_GetId() {

    	screeningResponse.setId(defaultID);

        assertEquals("The id should be got correctly.",
        		defaultID, screeningResponse.getId());
    }
    
    /**
     * <p>Tests the accuracy of the setter setResponseSeverity(ResponseSeverity).</p>
     * <p>Using reflection.</p>
     */
    public void testSetter_SetResponseSeverity() {

    	screeningResponse.setResponseSeverity(responseSeverity);

        // Gets responseSeverity by reflection.
        Object resp = AccuracyTestHelper.getPrivateField(ScreeningResponse.class,
        		screeningResponse, "responseSeverity");

        assertEquals("The responseSeverity should be set correctly.",
        		responseSeverity, resp);
    }
    
    /**
     * <p>Tests the accuracy of the getter getResponseSeverity().</p>
     * <p>Using validated setter to test.</p>
     */
    public void testGetter_GetResponseSeverity() {

    	screeningResponse.setResponseSeverity(responseSeverity);

        assertEquals("The responseSeverity should be got correctly.",
        		responseSeverity, screeningResponse.getResponseSeverity());
    }
    
    /**
     * <p>Tests the accuracy of the setter setResponseCode(String).</p>
     * <p>Using reflection.</p>
     */
    public void testSetter_SetResponseCode() {

    	screeningResponse.setResponseCode(defaultCode);

        // Gets responseCode by reflection.
        Object responseCode = AccuracyTestHelper.getPrivateField(ScreeningResponse.class,
        		screeningResponse, "responseCode");

        assertEquals("The responseCode should be set correctly.",
        		defaultCode, responseCode);
    }
    
    /**
     * <p>Tests the accuracy of the getter getResponseCode().</p>
     * <p>Using validated setter to test.</p>
     */
    public void testGetter_GetResponseCode() {

    	screeningResponse.setResponseCode(defaultCode);

        assertEquals("The responseCode should be got correctly.",
        		defaultCode, screeningResponse.getResponseCode());
    }
    
    /**
     * <p>Tests the accuracy of the setter setResponseText(String).</p>
     * <p>Using reflection.</p>
     */
    public void testSetter_SetResponseText() {

    	screeningResponse.setResponseText(defaultText);

        // Gets responseCode by reflection.
        Object responseText = AccuracyTestHelper.getPrivateField(ScreeningResponse.class,
        		screeningResponse, "responseText");

        assertEquals("The responseText should be set correctly.",
        		defaultText, responseText);
    }
    
    /**
     * <p>Tests the accuracy of the getter getResponseText().</p>
     * <p>Using validated setter to test.</p>
     */
    public void testGetter_GetResponseText() {

    	screeningResponse.setResponseText(defaultText);

        assertEquals("The responseText should be got correctly.",
        		defaultText, screeningResponse.getResponseText());
    }
}
