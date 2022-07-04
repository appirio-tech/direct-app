/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management.failuretests;

import java.util.Iterator;

import junit.framework.TestCase;

import com.cronos.onlinereview.autoscreening.management.ConfigurationException;
import com.cronos.onlinereview.autoscreening.management.ScreeningManagerFactory;
import com.topcoder.util.config.ConfigManager;


/**
 * <p>
 * Failure test for ScreeningManagerFactory.
 * </p>
 *
 * @author GavinWang
 * @version 1.0
 */
public class ScreeningManagerFactoryFailureTests extends TestCase {
    /**
     * <p>
     * Set up each test.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        clean();
        ConfigManager.getInstance().add("failure/config.xml");
    }

    /**
     * <p>
     * Clean up each test.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        clean();
    }

    /**
     * <p>
     * Clean up configurations.
     * </p>
     *
     * @throws Exception to JUnit
     */
    private static void clean() throws Exception {
        for (Iterator iter = ConfigManager.getInstance().getAllNamespaces(); iter.hasNext();) {
            String ns = (String) iter.next();

            if (ConfigManager.getInstance().existsNamespace(ns)) {
                ConfigManager.getInstance().removeNamespace(ns);
                iter.remove();
            }
        }
    }

    /**
     * Failure test for createScreeningManager(String). Inputs: null namespace.  Expectation: IllegalArgumentException
     * should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testCreateScreeningManagerNullNamespace()
        throws Exception {
        try {
            ScreeningManagerFactory.createScreeningManager(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for createScreeningManager(String). Inputs: empty namespace.  Expectation: IllegalArgumentException
     * should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testCreateScreeningManagerEmptyNamespace()
        throws Exception {
        try {
            ScreeningManagerFactory.createScreeningManager("    ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for createScreeningManager(String). Inputs: legal configuration. Expectation: should be ok.
     * @throws Exception to JUnit
     */
    public void testCreateScreeningManagerString() throws Exception {
        assertNotNull("should be ok.",
            ScreeningManagerFactory.createScreeningManager("com.cronos.onlinereview.autoscreening.management"));
    }

    /**
     * Failure test for createScreeningManager(String). Inputs: unknown namespace. Expectation: ConfigurationException
     * should be thrown.
     * @throws Exception to JUnit
     */
    public void testCreateScreeningManagerStringUnknownNamespace()
        throws Exception {
        try {
            ScreeningManagerFactory.createScreeningManager("com.cronos.onlinereview.autoscreening.management.unknown");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for createScreeningManager(String). Inputs: class not found. Expectation: ConfigurationException
     * should be thrown.
     * @throws Exception to JUnit
     */
    public void testCreateScreeningManagerStringClassnotfound()
        throws Exception {
        try {
            ScreeningManagerFactory.createScreeningManager(
                "com.cronos.onlinereview.autoscreening.management.classnotfound");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for createScreeningManager(String). Inputs: class not ScreeningManager instance. Expectation:
     * ConfigurationException should be thrown.
     * @throws Exception to JUnit
     */
    public void testCreateScreeningManagerStringClassNotScreeningManager()
        throws Exception {
        try {
            ScreeningManagerFactory.createScreeningManager(
                "com.cronos.onlinereview.autoscreening.management.notScreeningManager");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for createScreeningManager(). Inputs: legal configuration. Expectation: should be ok.
     * @throws Exception to JUnit
     */
    public void testCreateScreeningManager() throws Exception {
        assertNotNull("should be ok.", ScreeningManagerFactory.createScreeningManager());
    }
}
