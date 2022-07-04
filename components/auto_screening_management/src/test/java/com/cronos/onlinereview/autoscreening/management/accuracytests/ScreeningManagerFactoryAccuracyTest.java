/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management.accuracytests;

import com.cronos.onlinereview.autoscreening.management.ConfigurationException;
import com.cronos.onlinereview.autoscreening.management.ScreeningManager;
import com.cronos.onlinereview.autoscreening.management.ScreeningManagerFactory;

import junit.framework.TestCase;

/**
 * <p>The accuracy test cases for ScreeningManagerFactory class.</p>
 *
 * @author oodinary
 * @version 1.0
 */
public class ScreeningManagerFactoryAccuracyTest extends TestCase {

	/**
     * <p>A ScreeningManager instance for testing.</p>
     */
    private ScreeningManager screeningManager = null;

    /**
     * <p>Initialization.</p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
    	AccuracyTestHelper.addConfig("accuracytests/config.xml");
    	AccuracyTestHelper.insertTestingData();
    	screeningManager = ScreeningManagerFactory.createScreeningManager();
    }

    /**
     * <p>Set screeningManager to null.</p>
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
    	AccuracyTestHelper.deleteTestingData();
    	screeningManager = null;
    	AccuracyTestHelper.clearConfig();
    }
    
    /**
     * <p>Tests the accuracy of the createScreeningManager().</p>
     */
    public void testCreateScreeningManager_Accuracy() {

        assertNotNull("ScreeningManager should be accurately created.", screeningManager);
    }
    
    /**
     * <p>Tests the accuracy of the createScreeningManager(String).</p>
     * 
     * @throws ConfigurationException will never be thrown.
     */
    public void testCreateScreeningManager_String_Accuracy() throws ConfigurationException {

    	screeningManager = ScreeningManagerFactory.createScreeningManager(ScreeningManagerFactory.DEFAULT_NAMESPACE);
        assertNotNull("ScreeningManager should be accurately created.", screeningManager);
    }
}
