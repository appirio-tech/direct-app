/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management.accuracytests;

import java.sql.Timestamp;

import com.cronos.onlinereview.autoscreening.management.ScreeningResult;
import com.cronos.onlinereview.autoscreening.management.ScreeningStatus;
import com.cronos.onlinereview.autoscreening.management.ScreeningTask;

import junit.framework.TestCase;

/**
 * <p>The accuracy test cases for ScreeningTask class.</p>
 *
 * @author oodinary
 * @version 1.0
 */
public class ScreeningTaskAccuracyTest extends TestCase {

	/**
     * <p>The default id of this class testing.</p>
     */
    private final long defaultID = 100;
    
    /**
     * <p>The default upload of this class testing.</p>
     */
    private final long defaultUpload = 1000;
    
    /**
     * <p>The default screener used for testing.</p>
     */
    private static final long defaultScreener = 10000;
    
    /**
     * <p>The default timestamp used for testing.</p>
     */
    private static final Timestamp defaultTimestamp = new Timestamp(System.currentTimeMillis());
    
	/**
     * <p>A ScreeningTask instance for testing.</p>
     */
    private ScreeningTask screeningTask = null;
    
    /**
     * <p>A ScreeningResult instance for testing.</p>
     */
    private ScreeningResult screeningResult = null;
    
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
    	screeningTask = new ScreeningTask();
    	screeningStatus = new ScreeningStatus();
    	screeningResult = new ScreeningResult();
    }

    /**
     * <p>Set screeningTask to null.</p>
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
    	screeningTask = null;
    	screeningStatus = null;
    	screeningResult = null;
    }
    
    /**
     * <p>Tests the ctor().</p>
     * <p>The ScreeningTask instance should be created successfully.</p>
     */
    public void testCtor_Accuracy() {

        assertNotNull("ScreeningTask should be accurately created.", screeningTask);
        
        assertTrue("screeningTask should be instance of ScreeningTask.",
        		screeningTask instanceof ScreeningTask);
    }
    
    /**
     * <p>Tests the accuracy of the setter setId(long).</p>
     * <p>Using reflection.</p>
     */
    public void testSetter_SetId() {

    	screeningTask.setId(defaultID);

        // Gets id by reflection.
        Object id = AccuracyTestHelper.getPrivateField(ScreeningTask.class, screeningTask, "id");

        assertEquals("The id should be set correctly.",
        		new Long(defaultID), id);
    }
    
    /**
     * <p>Tests the accuracy of the getter getId().</p>
     * <p>Using validated setter to test.</p>
     */
    public void testGetter_GetId() {

    	screeningTask.setId(defaultID);

        assertEquals("The id should be got correctly.",
        		defaultID, screeningTask.getId());
    }
    
    /**
     * <p>Tests the accuracy of the setter setScreeningStatus(ScreeningStatus).</p>
     * <p>Using reflection.</p>
     */
    public void testSetter_SetScreeningStatus() {

    	screeningTask.setScreeningStatus(screeningStatus);

        // Gets screeningResult by reflection.
        Object status = AccuracyTestHelper.getPrivateField(ScreeningTask.class,
        		screeningTask, "screeningStatus");

        assertEquals("The screeningStatus should be set correctly.",
        		screeningStatus, status);
    }
    
    /**
     * <p>Tests the accuracy of the getter getScreeningStatus()().</p>
     * <p>Using validated setter to test.</p>
     */
    public void testGetter_GetScreeningStatus() {

    	screeningTask.setScreeningStatus(screeningStatus);

        assertEquals("The screeningStatus should be got correctly.",
        		screeningStatus, screeningTask.getScreeningStatus());
    }
    
    /**
     * <p>Tests the accuracy of the setter setUpload(long).</p>
     * <p>Using reflection.</p>
     */
    public void testSetter_SetUpload() {

    	screeningTask.setUpload(defaultUpload);

        // Gets id by reflection.
        Object upload = AccuracyTestHelper.getPrivateField(ScreeningTask.class, screeningTask, "upload");

        assertEquals("The upload should be set correctly.",
        		new Long(defaultUpload), upload);
    }
    
    /**
     * <p>Tests the accuracy of the getter getUpload().</p>
     * <p>Using validated setter to test.</p>
     */
    public void testGetter_GetUpload() {

    	screeningTask.setUpload(defaultUpload);

        assertEquals("The upload should be got correctly.",
        		defaultUpload, screeningTask.getUpload());
    }
    
    /**
     * <p>Tests the accuracy of the setter setScreener() and getter getScreener().</p>
     */
    public void testSetter_SetScreener() {
    	
    	screeningTask.setScreener(defaultScreener);
    	
        assertEquals("The screener should be set and got correctly.",
        		defaultScreener, screeningTask.getScreener());
    }

    /**
     * <p>Tests the accuracy of the setter setStartTimestamp() and getter getStartTimestamp().</p>
     */
    public void testSetGetStartTimestamp_Accuracy() {
    	
        screeningTask.setStartTimestamp(defaultTimestamp);
        
        assertEquals("The timestamp should be set and got correctly.", 
        		defaultTimestamp, screeningTask.getStartTimestamp());
    }

    /**
     * <p>Tests the accuracy of the method addScreeningResult().</p>
     */
    public void testAddScreeningResult_Accuracy() {
    	
        screeningTask.addScreeningResult(screeningResult);
        
        assertEquals("There is only one screeningResult.", 1, screeningTask.getAllScreeningResults().length);
        assertEquals("They should be the same.", screeningResult, screeningTask.getAllScreeningResults()[0]);
    }

    /**
     * <p>Tests the accuracy of the method removeScreeningResult().</p>
     */
    public void testRemoveScreeningResult_Accuracy() {
    	
        screeningTask.addScreeningResult(screeningResult);
        screeningTask.removeScreeningResult(screeningResult);
        
        assertEquals("There is no screeningResult.", 0, screeningTask.getAllScreeningResults().length);
    }

    /**
     * <p>Tests the accuracy of the method getAllScreeningResults().</p>
     */
    public void testGetAllScreeningResults_Accuracy() {
    	
    	screeningTask.addScreeningResult(screeningResult);
    	
    	assertEquals("There is only one screeningResult.", 1, screeningTask.getAllScreeningResults().length);
        assertEquals("They should be the same.", screeningResult, screeningTask.getAllScreeningResults()[0]);
    }

    /**
     * <p>Tests the accuracy of the method clearAllScreeningResults().</p>
     */
    public void testClearAllScreeningResults_Accuracy() {
    	
        screeningTask.addScreeningResult(screeningResult);
        screeningTask.clearAllScreeningResults();
        
        assertEquals("There is no screeningResult.", 0, screeningTask.getAllScreeningResults().length);
    }

    /**
     * <p>Tests the accuracy of the setter setCreationUser() and getter getCreationUser().</p>
     */
    public void testSetCreationUser_Accuracy() {
    	
        screeningTask.setCreationUser("User A");
        
        assertEquals("The CreationUser should be set and got correctly.",
        		"User A", screeningTask.getCreationUser());
    }

    /**
     * <p>Tests the accuracy of the setter setCreationTimestamp() and getter getCreationTimestamp().</p>
     */
    public void testSetGetCreationTimestamp_Accuracy() {
    	
        screeningTask.setCreationTimestamp(defaultTimestamp);
        
        assertEquals("The CreationTimestamp should be set and got correctly.",
        		defaultTimestamp, screeningTask.getCreationTimestamp());
    }

    /**
     * <p>Tests the accuracy of the setter setModificationUser() and getter getModificationUser().</p>
     */
    public void testSetGetModificationUser_Accuracy() {

    	screeningTask.setModificationUser("User B");
        
        assertEquals("The ModificationUser should be set and got correctly.",
        		"User B", screeningTask.getModificationUser());
    }

    /**
     * <p>Tests the accuracy of the setter setModificationTimestamp() and getter getModificationTimestamp().</p>
     */
    public void testSetGetModificationTimestamp_Accuracy() {

    	screeningTask.setModificationTimestamp(defaultTimestamp);
        
        assertEquals("The ModificationTimestamp should be set and got correctly.",
        		defaultTimestamp, screeningTask.getModificationTimestamp());
    }
}
