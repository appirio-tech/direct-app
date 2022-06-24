/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.stresstests;

import java.io.File;

import com.topcoder.project.service.ProjectServicesFactory;

import junit.framework.Test;
import junit.framework.TestSuite;


/**
 * <p>
 * Stress test cases for <code>ProjectServicesFactory</code> class.
 * </p>
 *
 * @author woodatxc
 * @version 1.1
 */
public class ProjectServicesFactoryStressTests extends BaseStressTests {

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator
                + "stresstest_configfiles" + File.separator + "configuration.xml");
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
        // set ProjectServicesFactory to null.
        TestHelper.setPrivateField(
                ProjectServicesFactory.class, ProjectServicesFactory.class, "projectServices", null);
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ProjectServicesFactoryStressTests.class);
    }

    /**
     * <p>
     * Tests <code>getProjectServices()</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetProjectServices1() throws Exception {
        this.beginTest();

        for (int i = 0; i < RUN_TIMES; i++) {
            ProjectServicesFactory.getProjectServices();
        }

        this.endTest("ExpressionParser's getProjectServices()", MAX_TIME);
    }

    /**
     * <p>
     * Tests <code>getProjectServices(String)</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetProjectServices2() throws Exception {
        this.beginTest();

        for (int i = 0; i < RUN_TIMES; i++) {
            ProjectServicesFactory.getProjectServices("com.topcoder.project.service.ProjectServicesFactory");
        }

        this.endTest("ExpressionParser's getProjectServices(String)", MAX_TIME);
    }
}
