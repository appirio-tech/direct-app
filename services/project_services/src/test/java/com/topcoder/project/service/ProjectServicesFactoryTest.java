/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service;

import java.io.File;
import java.lang.reflect.Constructor;

import com.topcoder.project.service.impl.TestHelper;

import junit.framework.TestCase;

/**
 * <p>
 * Tests functionality and error cases of <code>ProjectServicesFactory</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProjectServicesFactoryTest extends TestCase {
    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "configuration.xml");
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
        // set ProjectServicesFactory to null.
        TestHelper.setPrivateField(ProjectServicesFactory.class, ProjectServicesFactory.class, "projectServices",
                null);
    }

    /**
     * <p>
     * Accuracy test for the constructor.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCtorAccuracy() throws Exception {
        Constructor<ProjectServicesFactory> constructor = ProjectServicesFactory.class
                .getDeclaredConstructor(new Class[0]);
        constructor.setAccessible(true);
        constructor.newInstance(new Object[0]);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getProjectServices()</code>.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProjectServices1Accuracy() throws Exception {
        ProjectServicesFactory.getProjectServices();
    }

    /**
     * <p>
     * Accuracy test for the method <code>getProjectServices(String namespace)</code>.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProjectServices2Accuracy1() throws Exception {
        ProjectServicesFactory.getProjectServices("com.topcoder.project.service.ProjectServicesFactory");
    }

    /**
     * <p>
     * Accuracy test for the method <code>getProjectServices(String namespace)</code> with the
     * projectServices is not null.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProjectServices2Accuracy2() throws Exception {
        ProjectServicesFactory.getProjectServices();
        ProjectServicesFactory.getProjectServices("com.topcoder.project.service.ProjectServicesFactory");
    }

    /**
     * <p>
     * Failure test for the method <code>getProjectServices(String namespace)</code> with
     * namespace is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProjectServices2WithNamespaceNull() throws Exception {
        try {
            ProjectServicesFactory.getProjectServices(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>getProjectServices(String namespace)</code> with
     * namespace is empty, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProjectServices2WithNamespaceEmpty() throws Exception {
        try {
            ProjectServicesFactory.getProjectServices("");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>getProjectServices(String namespace)</code> with the
     * namespace doesn't exist, ConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProjectServices2WithCE() throws Exception {
        TestHelper.setPrivateField(ProjectServicesFactory.class, ProjectServicesFactory.class, "projectServices",
                null);

        try {
            ProjectServicesFactory.getProjectServices("UnknownNamespace");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }
}