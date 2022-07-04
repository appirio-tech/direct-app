/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.stresstests;

import java.util.Arrays;

import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.project.ProjectValidator;
import com.topcoder.management.project.validation.DefaultProjectValidator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * Stress test case of DefaultProjectValidator class.<br>
 * The test is performed against validateProject method.
 *
 * @author King_Bette
 * @version 1.0
 */
public class DefaultProjectValidatorTest extends TestCase {
    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(DefaultProjectValidatorTest.class);
    }

    /**
     * test validateProject method.<br>
     * run 100 times with a simple test case.
     *
     * @throws Exception
     *             throw exception to junit.
     */
    public void testValidateProject1() throws Exception {
        long startTime = System.currentTimeMillis();
        ProjectValidator validator = new DefaultProjectValidator("");
        for (int i = 0; i < 100; i++) {
            validator.validateProject(getSimpleProject());
        }
        long endTime = System.currentTimeMillis();
        System.out.println("validate simple project 100 times takes " + (endTime - startTime) + "ms");
    }

    /**
     * test validateProject method.<br>
     * run 500 times with a simple test case.
     *
     * @throws Exception
     *             throw exception to junit.
     */
    public void testValidateProject2() throws Exception {
        long startTime = System.currentTimeMillis();
        ProjectValidator validator = new DefaultProjectValidator("");
        for (int i = 0; i < 500; i++) {
            validator.validateProject(getSimpleProject());
        }
        long endTime = System.currentTimeMillis();
        System.out.println("validate simple project 500 times takes " + (endTime - startTime) + "ms");
    }

    /**
     * test validateProject method.<br>
     * run 500 times with a complex test case.
     *
     * @throws Exception
     *             throw exception to junit.
     */
    public void testValidateProject3() throws Exception {
        long startTime = System.currentTimeMillis();
        ProjectValidator validator = new DefaultProjectValidator("");
        for (int i = 0; i < 500; i++) {
            validator.validateProject(getComplexProject());
        }
        long endTime = System.currentTimeMillis();
        System.out.println("validate complex project 500 times takes " + (endTime - startTime) + "ms");
    }
    /**
     * Create a complex project.
     * @return a complex project.
     */
    private static Project getComplexProject() {
        ProjectType projectType = new ProjectType(1, getString(64), getString(256));
        ProjectCategory projectCategory = new ProjectCategory(1, getString(64), getString(256), projectType);
        ProjectStatus projectStatus = new ProjectStatus(1, getString(64), getString(256));
        Project project = new Project(projectCategory, projectStatus);
        for (int i = 0; i < 26; i++) {
            project.setProperty(getString(64).replace('a', (char) ('a' + i)), getString(4096));   
        }
        return project;
    }
    /**
     * Create a simple project.
     * @return a simple project.
     */
    private static Project getSimpleProject() {
        ProjectType projectType = new ProjectType(1, "type1");
        ProjectCategory projectCategory = new ProjectCategory(1, "category1", projectType);
        ProjectStatus projectStatus = new ProjectStatus(1, "status1");
        Project project = new Project(projectCategory, projectStatus);
        return project;
    }
    /**
     * This method is used to get a normal string in given length.
     * @param length
     *         length of returned string.
     * @return
     *         string in given length.
     */
    private static String getString(int length) {
        char[] s = new char[length];
        Arrays.fill(s, 'a');
        return new String(s);
    }
}
