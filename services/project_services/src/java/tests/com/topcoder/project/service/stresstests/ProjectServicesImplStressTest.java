/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.stresstests;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.project.service.impl.ProjectServicesImpl;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.NullFilter;

/**
 * <p>
 * This stress test addresses the performance provided by the {@link ProjectServicesImpl} class.
 * </p>
 * 
 * @author stylecheck
 * @author woodatxc
 * @version 1.1
 * @since 1.0
 */
public class ProjectServicesImplStressTest extends TestCase {

    /**
     * <p>
     * Represents the <code>ProjectServicesImpl</code> for testing overloaded constructor.
     * </p>
     */
    private ProjectServicesImpl projectServicesImpl;

    /**
     * <p>
     * Sets up the environment for each TestCase.
     * </p>
     * 
     * @throws Exception
     *             throws exception if any to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator
                + "stresstest_configfiles" + File.separator + "configuration.xml");
        projectServicesImpl = new ProjectServicesImpl();
    }

    /**
     * <p>
     * Tears down the environment after execution of each TestCase.
     * </p>
     * 
     * @throws Exception
     *             throws exception if any to JUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
        projectServicesImpl = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ProjectServicesImplStressTest.class);
    }

    /**
     * <p>
     * Stress Test for {@link ProjectServicesImpl#findActiveProjects()} method.
     * </p>
     */
    public void testFindActiveProjects() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < StressHelper.STRESS_COUNT; i++) {
            projectServicesImpl.findActiveProjects();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Calling ProjectServicesImpl#findActiveProjects() " + StressHelper.STRESS_COUNT
                + " took " + (endTime - startTime) + " milli seconds.");
    }

    /**
     * <p>
     * Stress Test for {@link ProjectServicesImpl#findActiveProjectsHeaders()} method.
     * </p>
     */
    public void testFindActiveProjectsHeaders() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < StressHelper.STRESS_COUNT; i++) {
            projectServicesImpl.findActiveProjectsHeaders();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Calling ProjectServicesImpl#findActiveProjectsHeaders() " + StressHelper.STRESS_COUNT
                + " took " + (endTime - startTime) + " milli seconds.");
    }

    /**
     * <p>
     * Stress Test for {@link ProjectServicesImpl#findFullProjects(Filter)} method.
     * </p>
     */
    public void testFindFullProjects() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < StressHelper.STRESS_COUNT; i++) {
            projectServicesImpl.findFullProjects(new NullFilter("temp"));
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Calling ProjectServicesImpl#findFullProjects() " + StressHelper.STRESS_COUNT
                + " took " + (endTime - startTime) + " milli seconds.");
    }


    /**
     * <p>
     * Stress Test for {@link ProjectServicesImpl#findProjectsHeaders(Filter)} method.
     * </p>
     */
    public void testFindProjectsHeaders() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < StressHelper.STRESS_COUNT; i++) {
            projectServicesImpl.findProjectsHeaders(new NullFilter("temp"));
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Calling ProjectServicesImpl#findProjectsHeaders() " + StressHelper.STRESS_COUNT
                + " took " + (endTime - startTime) + " milli seconds.");
    }


    /**
     * <p>
     * Stress Test for {@link ProjectServicesImpl#getFullProjectData(long)} method.
     * </p>
     */
    public void testGetFullProjectData() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < StressHelper.STRESS_COUNT; i++) {
            projectServicesImpl.getFullProjectData(1);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Calling ProjectServicesImpl#getFullProjectData() " + StressHelper.STRESS_COUNT
                + " took " + (endTime - startTime) + " milli seconds.");
    }
}
