/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cronos.onlinereview.autoscreening.management.ScreeningManager;
import com.cronos.onlinereview.services.uploads.impl.DefaultManagersProvider;
import com.topcoder.management.deliverable.UploadManager;
import com.topcoder.management.phase.PhaseManager;
import com.topcoder.management.project.ProjectManager;
import com.topcoder.management.resource.ResourceManager;

/**
 * <p>
 * This Junit Test suite contains the accuracy test cases for {@link DefaultManagersProvider} class.
 * </p>
 * 
 * @author kshatriyan
 * @version 1.0
 */
public class DefaultManagersProviderAccuracyTest extends TestCase {

    /**
     * DefaultManagersProvider instance to be used for the testing.
     */
    private DefaultManagersProvider defaultManagersProvider = null;

    /**
     * <p>
     * Sets up the environment for the TestCase.
     * </p>
     * 
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        AccuracyHelper.loadConfig();
        defaultManagersProvider = new DefaultManagersProvider();
    }

    /**
     * <p>
     * Tears down the environment after the TestCase.
     * </p>
     * 
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void tearDown() throws Exception {
        AccuracyHelper.release();
        defaultManagersProvider = null;
    }

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     * 
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(DefaultManagersProviderAccuracyTest.class);
    }

    /**
     * <p>
     * Accuracy test for{@link DefaultManagersProvider#DefaultManagersProvider()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation and the variable initialization.
     * </p>
     * 
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAccuracy_DefaultManagersProvider() throws Exception {
        // check for null
        assertNotNull("DefaultManagersProvider creation failed", defaultManagersProvider);
        // check whether everything is initialized properly
        assertNotNull("DefaultManagersProvider creation failed", defaultManagersProvider.getResourceManager());
        assertNotNull("DefaultManagersProvider creation failed", defaultManagersProvider.getPhaseManager());
        assertNotNull("DefaultManagersProvider creation failed", defaultManagersProvider.getProjectManager());
        assertNotNull("DefaultManagersProvider creation failed", defaultManagersProvider.getScreeningManager());
        assertNotNull("DefaultManagersProvider creation failed", defaultManagersProvider.getUploadManager());
    }

    /**
     * <p>
     * Accuracy test for{@link DefaultManagersProvider#DefaultManagersProvider(String)} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation and the variable initialization.
     * </p>
     * 
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAccuracy_DefaultManagersProvider1() throws Exception {
        DefaultManagersProvider defaultManagersProvider = new DefaultManagersProvider(
                DefaultManagersProvider.class.getName());
        // check for null
        assertNotNull("DefaultManagersProvider creation failed", defaultManagersProvider);

        // check whether everything is initialized properly
        assertNotNull("DefaultManagersProvider creation failed", defaultManagersProvider.getResourceManager());
        assertNotNull("DefaultManagersProvider creation failed", defaultManagersProvider.getPhaseManager());
        assertNotNull("DefaultManagersProvider creation failed", defaultManagersProvider.getProjectManager());
        assertNotNull("DefaultManagersProvider creation failed", defaultManagersProvider.getScreeningManager());
        assertNotNull("DefaultManagersProvider creation failed", defaultManagersProvider.getUploadManager());
    }

    /**
     * <p>
     * Accuracy test for{@link DefaultManagersProvider#DefaultManagersProvider(ResourceManager, ProjectManager, PhaseManager, ScreeningManager, UploadManager)}
     * constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation and the variable initialization.
     * </p>
     * 
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAccuracy_DefaultManagersProvider2() throws Exception {
        ResourceManager resourceManager = new MockResourceManager();
        ProjectManager projectManager = new MockProjectManager();
        PhaseManager phaseManager = new MockPhaseManager();
        ScreeningManager screeningManager = new MockScreeningManager();
        UploadManager uploadManager = new MockUploadManager();
        DefaultManagersProvider defaultManagersProvider = new DefaultManagersProvider(resourceManager,
                projectManager, phaseManager, screeningManager, uploadManager);
        // check for null
        assertNotNull("DefaultManagersProvider creation failed", defaultManagersProvider);

        // check whether everything is initialized properly
        assertEquals("DefaultManagersProvider creation failed", defaultManagersProvider.getResourceManager(),
                resourceManager);
        assertEquals("DefaultManagersProvider creation failed", defaultManagersProvider.getPhaseManager(),
                phaseManager);
        assertEquals("DefaultManagersProvider creation failed", defaultManagersProvider.getProjectManager(),
                projectManager);
        assertEquals("DefaultManagersProvider creation failed", defaultManagersProvider.getScreeningManager(),
                screeningManager);
        assertEquals("DefaultManagersProvider creation failed", defaultManagersProvider.getUploadManager(),
                uploadManager);
    }

}
