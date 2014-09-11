/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.util.Arrays;

import com.topcoder.management.project.validation.DefaultProjectValidator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for DefaultProjectValidator class.
 *
 * @author iamajia
 * @version 1.0
 */
public class DefaultProjectValidatorTest extends TestCase {
    /**
     * This validator is used in the test.
     */
    private ProjectValidator validator = new DefaultProjectValidator("");
    /**
     * This project is used in the test.
     */
    private Project project;

    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(DefaultProjectValidatorTest.class);
    }

    /**
     * Sets up the environment for the TestCase.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        // create a project type.
        ProjectType projectType = new ProjectType(1, "type1");
        // create a project category.
        ProjectCategory projectCategory = new ProjectCategory(1, "category1", projectType);
        // create a project status.
        ProjectStatus projectStatus = new ProjectStatus(1, "status1");
        // create a project.
        project = new Project(projectCategory, projectStatus);
    }

    /**
     * Accuracy test of <code>DefaultProjectValidator(String namespace)</code> constructor.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testDefaultProjectValidatorAccuracy() throws Exception {
        new DefaultProjectValidator("test");
    }

    /**
     * Accuracy test of <code>validateProject(Project project)</code> method.
     * <p>
     * a simple project.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testValidateProjectAccuracy1() throws Exception {
        validator.validateProject(project);
    }

    /**
     * Accuracy test of <code>validateProject(Project project)</code> method.
     * <p>
     * complex project.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testValidateProjectAccuracy2() throws Exception {
        project.getProjectCategory().setName(getString(64));
        project.getProjectCategory().setDescription(getString(256));
        project.getProjectStatus().setName(getString(64));
        project.getProjectStatus().setDescription(getString(256));
        project.getProjectCategory().getProjectType().setName(getString(64));
        project.getProjectCategory().getProjectType().setDescription(getString(256));
        project.setProperty(getString(64), getString(4096));
        validator.validateProject(project);
    }

    /**
     * Failure test of <code>validateProject(Project project)</code> method.
     * <p>
     * project type name's length is greater than 64
     * </p>
     * <p>
     * Expect ValidationException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testValidateProjectFailure1() throws Exception {
        project.getProjectCategory().getProjectType().setName(getString(65));
        try {
            validator.validateProject(project);
            fail("Expect ValidationException.");
        } catch (ValidationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>validateProject(Project project)</code> method.
     * <p>
     * project status name's length is greater than 64
     * </p>
     * <p>
     * Expect ValidationException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testValidateProjectFailure2() throws Exception {
        project.getProjectStatus().setName(getString(65));
        try {
            validator.validateProject(project);
            fail("Expect ValidationException.");
        } catch (ValidationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>validateProject(Project project)</code> method.
     * <p>
     * project category name's length is greater than 64
     * </p>
     * <p>
     * Expect ValidationException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testValidateProjectFailure3() throws Exception {
        project.getProjectCategory().setName(getString(65));
        try {
            validator.validateProject(project);
            fail("Expect ValidationException.");
        } catch (ValidationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>validateProject(Project project)</code> method.
     * <p>
     * project type description's length is greater than 256
     * </p>
     * <p>
     * Expect ValidationException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testValidateProjectFailure4() throws Exception {
        project.getProjectCategory().getProjectType().setDescription(getString(257));
        try {
            validator.validateProject(project);
            fail("Expect ValidationException.");
        } catch (ValidationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>validateProject(Project project)</code> method.
     * <p>
     * project status description's length is greater than 256
     * </p>
     * <p>
     * Expect ValidationException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testValidateProjectFailure5() throws Exception {
        project.getProjectStatus().setDescription(getString(257));
        try {
            validator.validateProject(project);
            fail("Expect ValidationException.");
        } catch (ValidationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>validateProject(Project project)</code> method.
     * <p>
     * project category description's length is greater than 256
     * </p>
     * <p>
     * Expect ValidationException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testValidateProjectFailure6() throws Exception {
        project.getProjectCategory().setDescription(getString(257));
        try {
            validator.validateProject(project);
            fail("Expect ValidationException.");
        } catch (ValidationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>validateProject(Project project)</code> method.
     * <p>
     * Project property key's length is greater than 64
     * </p>
     * <p>
     * Expect ValidationException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testValidateProjectFailure7() throws Exception {
        project.setProperty(getString(65), getString(4096));
        try {
            validator.validateProject(project);
            fail("Expect ValidationException.");
        } catch (ValidationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>validateProject(Project project)</code> method.
     * <p>
     * Project property value's length is greater than 4096
     * </p>
     * <p>
     * Expect ValidationException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testValidateProjectFailure8() throws Exception {
        project.setProperty(getString(64), getString(4097));
        try {
            validator.validateProject(project);
            fail("Expect ValidationException.");
        } catch (ValidationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>validateProject(Project project)</code> method.
     * <p>
     * project is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testValidateProjectFailure9() throws Exception {
        try {
            validator.validateProject(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * This method is used to get a normal string in given length.
     *
     * @param length
     *            length of returned string.
     * @return string in given length.
     */
    private String getString(int length) {
        char[] s = new char[length];
        Arrays.fill(s, 'a');
        return new String(s);
    }
}
