/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads.impl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cronos.onlinereview.services.uploads.ConfigurationException;
import com.cronos.onlinereview.services.uploads.TestHelper;
import com.topcoder.management.project.ProjectManager;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * Tests the functionality of <code>{@link Helper}</code> class.
 * </p>
 *
 * @author cyberjag
 * @version 1.0
 */
public class HelperTest extends TestCase {
    /**
     * <p>
     * Logger used during tesing.
     * </p>
     */
    private static final Log LOG = LogManager.getLog();

    /**
     * <p>
     * Integrates all tests in this class.
     * </p>
     *
     * @return Test suite of all tests of <code>HelperTest</code>.
     */
    public static Test suite() {
        return new TestSuite(HelperTest.class);
    }

    /**
     * <p>
     * Accuracy test of <code>{@link Helper#checkNull(Object obj, String paramName)}</code> method.
     * </p>
     *
     * <p>
     * Nothing happens for a valid object.
     * </p>
     *
     */
    public void testCheckNull_accuracy() {
        Helper.checkNull(new Object(), "paramName", LOG);
    }

    /**
     * <p>
     * Failure test of <code>{@link Helper#checkNull(Object obj, String paramName)}</code> method.
     * </p>
     *
     * <p>
     * obj is <code>null</code>.
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     */
    public void testCheckNull_failure() {
        try {
            Helper.checkNull(null, "paramName", LOG);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test of <code>{@link Helper#checkString(String str, String paramName)}</code> method.
     * </p>
     *
     * <p>
     * Nothing happens for a valid string.
     * </p>
     *
     */
    public void testCheckString_accuracy() {
        Helper.checkString("str", "paramName", LOG);
    }

    /**
     * <p>
     * Failure test of <code>{@link Helper#checkString(String str, String paramName)}</code> method.
     * </p>
     *
     * <p>
     * str is <code>null</code>.
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     */
    public void testCheckString_failure_1() {
        try {
            Helper.checkString(null, "paramName", LOG);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of <code>{@link Helper#checkString(String str, String paramName)}</code> method.
     * </p>
     *
     * <p>
     * str is empty.
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     */
    public void testCheckString_failure_2() {
        try {
            Helper.checkString(" ", "paramName", LOG);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test of <code>{@link Helper#checkId(long, String, Log)}</code> method.
     * </p>
     *
     * <p>
     * Nothing happens for a valid id.
     * </p>
     *
     */
    public void testCheckId_accuracy() {
        Helper.checkId(1, "paramName", LOG);
    }

    /**
     * <p>
     * Failure test of <code>{@link Helper#checkId(long, String, Log)}</code> method.
     * </p>
     *
     * <p>
     * id is negative
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     */
    public void testCheckId_failure() {
        try {
            Helper.checkId(-1, "paramName", LOG);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of <code>{@link Helper#createObject(String, String, String, Log, Class)}</code> method.
     * </p>
     *
     * <p>
     * Wrong class type
     * </p>
     *
     * <p>
     * Expects <code>ConfigurationException</code>.
     * </p>
     *
     */
    public void testcreateObject_failure_1() {
        try {
            Helper.createObject("com.cronos.onlinereview.services.uploads.impl.DefaultManagersProvider",
                    "resourceManagerIdentifier", null, LOG, Integer.class);
            fail("Expect ConfigurationException.");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test of <code>{@link Helper#createObject(String, String, String, Log, Class, Object)}</code>
     * method.
     * </p>
     *
     * <p>
     * objectFactoryNamespace is null
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testcreateObject_accuracy() throws Exception {
        TestHelper.loadConfigs("config.xml");
        Helper.createObject("HelperTest", "managersProviderIdentifier", null, LOG, ProjectManager.class,
                new MockProjectManager());
    }
}
