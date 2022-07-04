/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management;

import junit.framework.TestCase;

/**
 * <p>
 * Test cases of ScreeningManagerFactory. Tests the class for proper saving the provided arguments.
 * </p>
 *
 * @author haozhangr
 * @version 1.0
 */
public class ScreeningManagerFactoryTests extends TestCase {
    /**
     * <p>
     * Represents the configuration namespace to use for instantiation of the ScreeningManager
     * subclasses.
     * </p>
     */
    public static final String NAMESPACE = "com.cronos.onlinereview.autoscreening.management";

    /**
     * <p>
     * setUp() routine. It will load namespace and prepare test data.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.loadNamespaces();
        TestHelper.prepareData();
    }

    /**
     * tearDown() routine. Clear namespaces loaded and test data.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearData();
        TestHelper.releaseNamespaces();
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>createScreeningManager(String)</code> for proper behavior.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateScreeningManagerString_accuracy() throws Exception {
        ScreeningManager sm = (ScreeningManager) ScreeningManagerFactory
                .createScreeningManager(NAMESPACE);
        assertNotNull("Failed to create ScreeningManager using given namespace.", sm);
    }

    /**
     * <p>
     * Failure test. Tests the <code>createScreeningManager(String)</code> for proper behavior.
     * Verify IllegalArgumentException will be thrown for invalid namespace.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateScreeningManagerString_1_failure() throws Exception {
        try {
            ScreeningManagerFactory.createScreeningManager(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>createScreeningManager(String)</code> for proper behavior.
     * Verify IllegalArgumentException will be thrown for invalid namespace.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateScreeningManagerString_2_failure() throws Exception {
        try {
            ScreeningManagerFactory.createScreeningManager("  ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }
}
