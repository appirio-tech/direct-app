/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.stresstests;

import java.util.Iterator;

import com.topcoder.management.project.ProjectManager;
import com.topcoder.management.project.ProjectManagerImpl;
import com.topcoder.util.config.ConfigManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * Stress test case of ProjectManagerImpl class.<br>
 * The test is performed against constructor.
 *
 * @author King_Bette
 * @version 1.0
 */
public class ProjectManagerImplTest extends TestCase {
    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ProjectManagerImplTest.class);
    }
    /**
     * Sets up the environment for the TestCase.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace((String) iter.next());
        }
        ConfigManager.getInstance().add("stress/config.xml");
    }
    /**
     * Tears down the environment for the TestCase.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void tearDown() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace((String) iter.next());
        }
    }
    /**
     * test ProjectManagerImpl constructor.<br>
     * Create ProjectManagerImpl instance 100 times use default namespace.
     *
     * @throws Exception
     *             throw exception to junit.
     */
    public void testConstructor1() throws Exception {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            ProjectManager projectManager = new ProjectManagerImpl();
            assertNotNull("projectManager can not be null.", projectManager);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Create ProjectManagerImpl instance 100 times use default namespace takes "
            + (endTime - startTime) + "ms");
    }
    /**
     * test ProjectManagerImpl constructor.<br>
     * Create ProjectManagerImpl instance 100 times use given namespace.
     *
     * @throws Exception
     *             throw exception to junit.
     */
    public void testConstructor2() throws Exception {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            ProjectManager projectManager = new ProjectManagerImpl("stress.ProjectManagerImpl");
            assertNotNull("projectManager can not be null.", projectManager);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Create ProjectManagerImpl instance 100 times use given namespace takes "
            + (endTime - startTime) + "ms");
    }
    /**
     * test ProjectManagerImpl constructor.<br>
     * Create ProjectManagerImpl instance 500 times use given namespace.
     *
     * @throws Exception
     *             throw exception to junit.
     */
    public void testConstructor3() throws Exception {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 500; i++) {
            ProjectManager projectManager = new ProjectManagerImpl("stress.ProjectManagerImpl");
            assertNotNull("projectManager can not be null.", projectManager);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Create ProjectManagerImpl instance 500 times use given namespace takes "
            + (endTime - startTime) + "ms");
    }
}
