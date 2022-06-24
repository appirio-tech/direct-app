/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads.failuretests;

import java.io.File;

import com.cronos.onlinereview.autoscreening.management.ScreeningManager;
import com.cronos.onlinereview.services.uploads.ConfigurationException;
import com.cronos.onlinereview.services.uploads.impl.DefaultManagersProvider;
import com.topcoder.management.deliverable.UploadManager;
import com.topcoder.management.phase.PhaseManager;
import com.topcoder.management.project.ProjectManager;
import com.topcoder.management.resource.ResourceManager;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Failure test cases for DefaultManagersProvider.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class DefaultManagersProviderFailureTests extends TestCase {
    /**
     * <p>
     * The ResourceManager instance used for testing.
     * </p>
     */
    private ResourceManager resourceManager;

    /**
     * <p>
     * The ProjectManager instance used for testing.
     * </p>
     */
    private ProjectManager projectManager;

    /**
     * <p>
     * The PhaseManager instance used for testing.
     * </p>
     */
    private PhaseManager phaseManager;

    /**
     * <p>
     * The ScreeningManager instance used for testing.
     * </p>
     */
    private ScreeningManager screeningManager;

    /**
     * <p>
     * The UploadManager instance used for testing.
     * </p>
     */
    private UploadManager uploadManager;

    /**
     * <p>
     * Setup test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        FailureTestHelper.loadXMLConfig(FailureTestHelper.CONFIG_FILE);
        FailureTestHelper.loadXMLConfig("test_files" + File.separator + "failuretests" + File.separator
            + "invalid_config.xml");

        resourceManager = new MockResourceManager();
        projectManager = new MockProjectManager();
        phaseManager = new MockPhaseManager();
        screeningManager = new MockScreeningManager();
        uploadManager = new MockUploadManager();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
        resourceManager = null;
        projectManager = null;
        phaseManager = null;
        screeningManager = null;
        uploadManager = null;

        FailureTestHelper.clearConfig();
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(DefaultManagersProviderFailureTests.class);
    }

    /**
     * <p>
     * Tests ctor DefaultManagersProvider#DefaultManagersProvider(String) for failure.
     * It tests the case that when namespace is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor_NullNamespace() throws Exception {
        try {
            new DefaultManagersProvider(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DefaultManagersProvider#DefaultManagersProvider(String) for failure.
     * It tests the case that when namespace is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor_EmptyNamespace() throws Exception {
        try {
            new DefaultManagersProvider(" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DefaultManagersProvider#DefaultManagersProvider(String) for failure.
     * Expects for ConfigurationException.
     * </p>
     */
    public void testCtor1_UnknowNamespace() {
        try {
            new DefaultManagersProvider("unknownNamespace");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DefaultManagersProvider#DefaultManagersProvider(String) for failure.
     * Expects for ConfigurationException.
     * </p>
     */
    public void testCtor1_ObjectFactoryNamespaceMissing() {
        try {
            new DefaultManagersProvider("objectFactoryNamespace_missing");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DefaultManagersProvider#DefaultManagersProvider(String) for failure.
     * Expects for ConfigurationException.
     * </p>
     */
    public void testCtor1_ObjectFactoryNamespaceEmpty() {
        try {
            new DefaultManagersProvider("objectFactoryNamespace_empty");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DefaultManagersProvider#DefaultManagersProvider(String) for failure.
     * Expects for ConfigurationException.
     * </p>
     */
    public void testCtor1_ObjectFactoryNamespaceInvalid() {
        try {
            new DefaultManagersProvider("objectFactoryNamespace_invalid");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DefaultManagersProvider#DefaultManagersProvider(String) for failure.
     * Expects for ConfigurationException.
     * </p>
     */
    public void testCtor1_ResourceManagerIdentifierMissing() {
        try {
            new DefaultManagersProvider("resourceManagerIdentifier_missing");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DefaultManagersProvider#DefaultManagersProvider(String) for failure.
     * Expects for ConfigurationException.
     * </p>
     */
    public void testCtor1_ResourceManagerIdentifierEmpty() {
        try {
            new DefaultManagersProvider("resourceManagerIdentifier_empty");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DefaultManagersProvider#DefaultManagersProvider(String) for failure.
     * Expects for ConfigurationException.
     * </p>
     */
    public void testCtor1_ResourceManagerIdentifierInvalid() {
        try {
            new DefaultManagersProvider("resourceManagerIdentifier_invalid");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DefaultManagersProvider#DefaultManagersProvider(String) for failure.
     * Expects for ConfigurationException.
     * </p>
     */
    public void testCtor1_PhaseManagerIdentifierMissing() {
        try {
            new DefaultManagersProvider("phaseManagerIdentifier_missing");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DefaultManagersProvider#DefaultManagersProvider(String) for failure.
     * Expects for ConfigurationException.
     * </p>
     */
    public void testCtor1_PhaseManagerIdentifierEmpty() {
        try {
            new DefaultManagersProvider("phaseManagerIdentifier_empty");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DefaultManagersProvider#DefaultManagersProvider(String) for failure.
     * Expects for ConfigurationException.
     * </p>
     */
    public void testCtor1_PhaseManagerIdentifierInvalid() {
        try {
            new DefaultManagersProvider("phaseManagerIdentifier_invalid");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DefaultManagersProvider#DefaultManagersProvider(String) for failure.
     * Expects for ConfigurationException.
     * </p>
     */
    public void testCtor1_ProjectManagerIdentifierMissing() {
        try {
            new DefaultManagersProvider("projectManagerIdentifier_missing");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DefaultManagersProvider#DefaultManagersProvider(String) for failure.
     * Expects for ConfigurationException.
     * </p>
     */
    public void testCtor1_ProjectManagerIdentifierEmpty() {
        try {
            new DefaultManagersProvider("projectManagerIdentifier_empty");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DefaultManagersProvider#DefaultManagersProvider(String) for failure.
     * Expects for ConfigurationException.
     * </p>
     */
    public void testCtor1_ProjectManagerIdentifierInvalid() {
        try {
            new DefaultManagersProvider("projectManagerIdentifier_invalid");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DefaultManagersProvider#DefaultManagersProvider(String) for failure.
     * Expects for ConfigurationException.
     * </p>
     */
    public void testCtor1_ScreeningManagerIdentifierMissing() {
        try {
            new DefaultManagersProvider("screeningManagerIdentifier_missing");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DefaultManagersProvider#DefaultManagersProvider(String) for failure.
     * Expects for ConfigurationException.
     * </p>
     */
    public void testCtor1_ScreeningManagerIdentifierEmpty() {
        try {
            new DefaultManagersProvider("screeningManagerIdentifier_empty");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DefaultManagersProvider#DefaultManagersProvider(String) for failure.
     * Expects for ConfigurationException.
     * </p>
     */
    public void testCtor1_ScreeningManagerIdentifierInvalid() {
        try {
            new DefaultManagersProvider("screeningManager_invalid");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DefaultManagersProvider#DefaultManagersProvider(String) for failure.
     * Expects for ConfigurationException.
     * </p>
     */
    public void testCtor1_UploadManagerIdentifierMissing() {
        try {
            new DefaultManagersProvider("uploadManagerIdentifier_missing");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DefaultManagersProvider#DefaultManagersProvider(String) for failure.
     * Expects for ConfigurationException.
     * </p>
     */
    public void testCtor1_UploadManagerIdentifierEmpty() {
        try {
            new DefaultManagersProvider("uploadManagerIdentifier_empty");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DefaultManagersProvider#DefaultManagersProvider(String) for failure.
     * Expects for ConfigurationException.
     * </p>
     */
    public void testCtor1_UploadManagerIdentifierInvalid() {
        try {
            new DefaultManagersProvider("uploadManagerIdentifier_invalid");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DefaultManagersProvider#DefaultManagersProvider(ResourceManager,ProjectManager,
     * PhaseManager,ScreeningManager,UploadManager) for failure.
     * It tests the case that when resourceManager is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_NullResourceManager() {
        try {
            new DefaultManagersProvider(null, projectManager, phaseManager, screeningManager, uploadManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DefaultManagersProvider#DefaultManagersProvider(ResourceManager,ProjectManager,
     * PhaseManager,ScreeningManager,UploadManager) for failure.
     * It tests the case that when projectManager is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_NullProjectManager() {
        try {
            new DefaultManagersProvider(resourceManager, null, phaseManager, screeningManager, uploadManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DefaultManagersProvider#DefaultManagersProvider(ResourceManager,ProjectManager,
     * PhaseManager,ScreeningManager,UploadManager) for failure.
     * It tests the case that when phaseManager is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_NullPhaseManager() {
        try {
            new DefaultManagersProvider(resourceManager, projectManager, null, screeningManager, uploadManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DefaultManagersProvider#DefaultManagersProvider(ResourceManager,ProjectManager,
     * PhaseManager,ScreeningManager,UploadManager) for failure.
     * It tests the case that when screeningManager is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_NullScreeningManager() {
        try {
            new DefaultManagersProvider(resourceManager, projectManager, phaseManager, null, uploadManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DefaultManagersProvider#DefaultManagersProvider(ResourceManager,ProjectManager,
     * PhaseManager,ScreeningManager,UploadManager) for failure.
     * It tests the case that when uploadManager is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_NullUploadManager() {
        try {
            new DefaultManagersProvider(resourceManager, projectManager, phaseManager, screeningManager, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

}