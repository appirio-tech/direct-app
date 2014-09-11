/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads.impl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cronos.onlinereview.services.uploads.ConfigurationException;
import com.cronos.onlinereview.services.uploads.TestHelper;

/**
 * <p>
 * Tests the functionality of <code>{@link DefaultManagersProvider}</code> class.
 * </p>
 *
 * @author cyberjag
 * @version 1.0
 */
public class DefaultManagersProviderTest extends TestCase {
    /**
     * <p>
     * Represents the <code>DefaultManagersProvider</code> to test.
     * </p>
     */
    private DefaultManagersProvider defaultManagersProvider;

    /**
     * <p>
     * Integrates all tests in this class.
     * </p>
     *
     * @return Test suite of all tests of <code>DefaultManagersProviderTest</code>.
     */
    public static Test suite() {
        return new TestSuite(DefaultManagersProviderTest.class);
    }

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    protected void setUp() throws Exception {
        TestHelper.loadConfigs("config.xml");
        defaultManagersProvider = new DefaultManagersProvider();
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    protected void tearDown() throws Exception {
        TestHelper.releaseConfigs();
        defaultManagersProvider = null;
    }

    /**
     * <p>
     * Accuracy test of <code>{@link DefaultManagersProvider#DefaultManagersProvider()}</code> constructor.
     * Creates an instance and get its attributes for test.
     * </p>
     *
     * <p>
     * Expects non null instance.
     * </p>
     *
     */
    public void testDefaultManagersProvider_accuracy_1() {
        assertNotNull("Failed to create DefaultManagersProvider", defaultManagersProvider);
    }

    /**
     * <p>
     * Failure test of <code>{@link DefaultManagersProvider#DefaultManagersProvider()}</code> constructor.
     * </p>
     *
     * <p>
     * No configuration to load
     * </p>
     *
     * <p>
     * Expects <code>ConfigurationException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDefaultManagersProvider_failure_1() throws Exception {
        TestHelper.releaseConfigs();
        try {
            new DefaultManagersProvider();
            fail("Expect ConfigurationException.");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test of <code>{@link DefaultManagersProvider#DefaultManagersProvider(String namespace)}</code>
     * constructor. Creates an instance and get its attributes for test.
     * </p>
     *
     * <p>
     * Expects non null instance.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDefaultManagersProvider_accuracy_2() throws Exception {
        defaultManagersProvider = new DefaultManagersProvider(
                "com.cronos.onlinereview.services.uploads.impl.DefaultManagersProvider");
        assertNotNull("Failed to create DefaultManagersProvider", defaultManagersProvider);
    }

    /**
     * <p>
     * Failure test of <code>{@link DefaultManagersProvider#DefaultManagersProvider(String namespace)}</code>
     * constructor.
     * </p>
     *
     * <p>
     * Wrong namespace
     * </p>
     *
     * <p>
     * Expects <code>ConfigurationException</code>.
     * </p>
     *
     */
    public void testDefaultManagersProvider_failure_2() {
        try {
            new DefaultManagersProvider("wrong namespace");
            fail("Expect ConfigurationException.");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test of <code>{@link DefaultManagersProvider#DefaultManagersProvider(String namespace)}</code>
     * constructor.
     * </p>
     *
     * <p>
     * namespace is null.
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDefaultManagersProvider_failure_3() throws Exception {
        try {
            new DefaultManagersProvider(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of <code>{@link DefaultManagersProvider#DefaultManagersProvider(String namespace)}</code>
     * constructor.
     * </p>
     *
     * <p>
     * namespace is empty.
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDefaultManagersProvider_failure_4() throws Exception {
        try {
            new DefaultManagersProvider("");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of <code>{@link DefaultManagersProvider#DefaultManagersProvider(String namespace)}</code>
     * constructor.
     * </p>
     *
     * <p>
     * objectFactoryNamespace is missing.
     * </p>
     *
     * <p>
     * Expects <code>ConfigurationException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDefaultManagersProvider_failure_5() throws Exception {
        TestHelper.loadConfigs("config_obj_missing.xml");
        try {
            new DefaultManagersProvider("com.cronos.onlinereview.services.uploads.impl.DefaultManagersProvider");
            fail("Expect ConfigurationException.");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test of <code>{@link DefaultManagersProvider#DefaultManagersProvider(String namespace)}</code>
     * constructor.
     * </p>
     *
     * <p>
     * resourceManager invalid.
     * </p>
     *
     * <p>
     * Expects <code>ConfigurationException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDefaultManagersProvider_failure_6() throws Exception {
        TestHelper.loadConfigs("config_res_missing.xml");
        try {
            new DefaultManagersProvider("com.cronos.onlinereview.services.uploads.impl.DefaultManagersProvider");
            fail("Expect ConfigurationException.");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test of <code>{@link DefaultManagersProvider#DefaultManagersProvider(String namespace)}</code>
     * constructor.
     * </p>
     *
     * <p>
     * phaseManager invalid.
     * </p>
     *
     * <p>
     * Expects <code>ConfigurationException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDefaultManagersProvider_failure_7() throws Exception {
        TestHelper.loadConfigs("config_pha_missing.xml");
        try {
            new DefaultManagersProvider("com.cronos.onlinereview.services.uploads.impl.DefaultManagersProvider");
            fail("Expect ConfigurationException.");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test of <code>{@link DefaultManagersProvider#DefaultManagersProvider(String namespace)}</code>
     * constructor.
     * </p>
     *
     * <p>
     * projectManager invalid.
     * </p>
     *
     * <p>
     * Expects <code>ConfigurationException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDefaultManagersProvider_failure_8() throws Exception {
        TestHelper.loadConfigs("config_pro_missing.xml");
        try {
            new DefaultManagersProvider("com.cronos.onlinereview.services.uploads.impl.DefaultManagersProvider");
            fail("Expect ConfigurationException.");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test of <code>{@link DefaultManagersProvider#DefaultManagersProvider(String namespace)}</code>
     * constructor.
     * </p>
     *
     * <p>
     * screeningManager invalid.
     * </p>
     *
     * <p>
     * Expects <code>ConfigurationException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDefaultManagersProvider_failure_9() throws Exception {
        TestHelper.loadConfigs("config_scr_missing.xml");
        try {
            new DefaultManagersProvider("com.cronos.onlinereview.services.uploads.impl.DefaultManagersProvider");
            fail("Expect ConfigurationException.");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test of <code>{@link DefaultManagersProvider#DefaultManagersProvider(String namespace)}</code>
     * constructor.
     * </p>
     *
     * <p>
     * uploadManager invalid.
     * </p>
     *
     * <p>
     * Expects <code>ConfigurationException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDefaultManagersProvider_failure_10() throws Exception {
        TestHelper.loadConfigs("config_upl_missing.xml");
        try {
            new DefaultManagersProvider("com.cronos.onlinereview.services.uploads.impl.DefaultManagersProvider");
            fail("Expect ConfigurationException.");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test of
     * <code>{@link DefaultManagersProvider#DefaultManagersProvider(com.topcoder.management.resource.ResourceManager,
     * com.topcoder.management.project.ProjectManager, com.topcoder.management.phase.PhaseManager,
     * com.cronos.onlinereview.autoscreening.management.ScreeningManager,
     * com.topcoder.management.deliverable.UploadManager)}</code>
     * constructor. Creates an instance and get its attributes for test.
     * </p>
     *
     * <p>
     * Expects non null instance.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDefaultManagersProvider_accuracy_3() throws Exception {
        defaultManagersProvider = new DefaultManagersProvider(new MockResourceManager(), new MockProjectManager(),
                new MockPhaseManager(), new MockScreeningManager(), new MockUploadManager());
        assertNotNull("Failed to create DefaultManagersProvider", defaultManagersProvider);
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultManagersProvider#DefaultManagersProvider(com.topcoder.management.resource.ResourceManager,
     * com.topcoder.management.project.ProjectManager, com.topcoder.management.phase.PhaseManager,
     * com.cronos.onlinereview.autoscreening.management.ScreeningManager,
     * com.topcoder.management.deliverable.UploadManager)}</code>
     * constructor.
     * </p>
     *
     * <p>
     * resourceManager is null
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDefaultManagersProvider_failure_11() throws Exception {
        try {
            new DefaultManagersProvider(null, new MockProjectManager(), new MockPhaseManager(),
                    new MockScreeningManager(), new MockUploadManager());
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultManagersProvider#DefaultManagersProvider(com.topcoder.management.resource.ResourceManager,
     * com.topcoder.management.project.ProjectManager, com.topcoder.management.phase.PhaseManager,
     * com.cronos.onlinereview.autoscreening.management.ScreeningManager,
     * com.topcoder.management.deliverable.UploadManager)}</code>
     * constructor.
     * </p>
     *
     * <p>
     * projectManager is null
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDefaultManagersProvider_failure_12() throws Exception {
        try {
            new DefaultManagersProvider(new MockResourceManager(), null, new MockPhaseManager(),
                    new MockScreeningManager(), new MockUploadManager());
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultManagersProvider#DefaultManagersProvider(com.topcoder.management.resource.ResourceManager,
     * com.topcoder.management.project.ProjectManager, com.topcoder.management.phase.PhaseManager,
     * com.cronos.onlinereview.autoscreening.management.ScreeningManager,
     * com.topcoder.management.deliverable.UploadManager)}</code>
     * constructor.
     * </p>
     *
     * <p>
     * phaseManager is null
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDefaultManagersProvider_failure_13() throws Exception {
        try {
            new DefaultManagersProvider(new MockResourceManager(), new MockProjectManager(), null,
                    new MockScreeningManager(), new MockUploadManager());
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultManagersProvider#DefaultManagersProvider(com.topcoder.management.resource.ResourceManager,
     * com.topcoder.management.project.ProjectManager, com.topcoder.management.phase.PhaseManager,
     * com.cronos.onlinereview.autoscreening.management.ScreeningManager,
     * com.topcoder.management.deliverable.UploadManager)}</code>
     * constructor.
     * </p>
     *
     * <p>
     * screeningManager is null
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDefaultManagersProvider_failure_14() throws Exception {
        try {
            new DefaultManagersProvider(new MockResourceManager(), new MockProjectManager(),
                    new MockPhaseManager(), null, new MockUploadManager());
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link DefaultManagersProvider#DefaultManagersProvider(com.topcoder.management.resource.ResourceManager,
     * com.topcoder.management.project.ProjectManager, com.topcoder.management.phase.PhaseManager,
     * com.cronos.onlinereview.autoscreening.management.ScreeningManager,
     * com.topcoder.management.deliverable.UploadManager)}</code>
     * constructor.
     * </p>
     *
     * <p>
     * uploadManager is null
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDefaultManagersProvider_failure_15() throws Exception {
        try {
            new DefaultManagersProvider(new MockResourceManager(), new MockProjectManager(),
                    new MockPhaseManager(), new MockScreeningManager(), null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test of <code>{@link DefaultManagersProvider#getResourceManager()}</code> method.
     * </p>
     *
     * <p>
     * Expects non null instance.
     * </p>
     *
     */
    public void testGetResourceManager_accuracy() {
        assertNotNull("Failed to get resource manager", defaultManagersProvider.getResourceManager());
    }

    /**
     * <p>
     * Accuracy test of <code>{@link DefaultManagersProvider#getProjectManager()}</code> method.
     * </p>
     *
     * <p>
     * Expects non null instance.
     * </p>
     *
     */
    public void testGetProjectManager_accuracy() {
        assertNotNull("Failed to get resource manager", defaultManagersProvider.getResourceManager());
    }

    /**
     * <p>
     * Accuracy test of <code>{@link DefaultManagersProvider#getPhaseManager()}</code> method.
     * </p>
     *
     * <p>
     * Expects non null instance.
     * </p>
     *
     */
    public void testGetPhaseManager_accuracy() {
        assertNotNull("Failed to get phase manager", defaultManagersProvider.getPhaseManager());
    }

    /**
     * <p>
     * Accuracy test of <code>{@link DefaultManagersProvider#getScreeningManager()}</code> method.
     * </p>
     *
     * <p>
     * Expects non null instance.
     * </p>
     *
     */
    public void testGetScreeningManager_accuracy() {
        assertNotNull("Failed to get screening manager", defaultManagersProvider.getScreeningManager());
    }

    /**
     * <p>
     * Accuracy test of <code>{@link DefaultManagersProvider#getUploadManager()}</code> method.
     * </p>
     *
     * <p>
     * Expects non null instance.
     * </p>
     *
     */
    public void testGetUploadManager_accuracy() {
        assertNotNull("Failed to get upload manager", defaultManagersProvider.getUploadManager());
    }
}
