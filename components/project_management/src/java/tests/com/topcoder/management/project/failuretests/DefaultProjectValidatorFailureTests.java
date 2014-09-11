/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.failuretests;

import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.project.ValidationException;
import com.topcoder.management.project.validation.DefaultProjectValidator;

import junit.framework.TestCase;

/**
 * Failure tests for <code>DefaultProjectValidator</code> class.
 *
 * @author vividmxx
 * @version 1.0
 */
public class DefaultProjectValidatorFailureTests extends TestCase {

    /**
     * The <code>Project</code> instance used for test.
     */
    private Project project = null;

    /**
     * The <code>DefaultProjectValidator</code> instance used to test against.
     */
    private DefaultProjectValidator validator = new DefaultProjectValidator("");

    /**
     * The <code>String</code> instance with the length 65.
     */
    private String s65 = null;

    /**
     * The <code>String</code> instance with the length 257.
     */
    private String s257 = null;

    /**
     * The <code>String</code> instance with the length 4097.
     */
    private String s4097 = null;

    /**
     * Set up the test environment.
     */
    protected void setUp() {
        ProjectStatus status = new ProjectStatus(200601, "status");
        ProjectCategory category = new ProjectCategory(200602, "category", new ProjectType(2006, "type"));
        project = new Project(category, status);

        for (s65 = ""; s65.length() < 65;) {
            s65 += "W";
        }

        for (s257 = ""; s257.length() < 257;) {
            s257 += "W";
        }

        for (s4097 = ""; s4097.length() < 4097;) {
            s4097 += "W";
        }
    }

    /**
     * Test the method validateProject(Project) with null project. IllegalArgumentException should be thrown.
     */
    public void testValidateProjectWithNullProject() {
        try {
            validator.validateProject(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        } catch (ValidationException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test the method validateProject(Project) with invalid project whose type name is too long (more than 64
     * characters). ValidationException should be thrown.
     */
    public void testValidateProjectWithTooLongTypeName() {
        project.getProjectCategory().getProjectType().setName(s65);
        try {
            validator.validateProject(project);
            fail("ValidationException should be thrown.");
        } catch (ValidationException e) {
            // success
        }
    }

    /**
     * Test the method validateProject(Project) with invalid project whose category name is too long (more than 64
     * characters). ValidationException should be thrown.
     */
    public void testValidateProjectWithTooLongCategoryName() {
        project.getProjectCategory().setName(s65);
        try {
            validator.validateProject(project);
            fail("ValidationException should be thrown.");
        } catch (ValidationException e) {
            // success
        }
    }

    /**
     * Test the method validateProject(Project) with invalid project whose status name is too long (more than 64
     * characters). ValidationException should be thrown.
     */
    public void testValidateProjectWithTooLongStatusName() {
        project.getProjectStatus().setName(s65);
        try {
            validator.validateProject(project);
            fail("ValidationException should be thrown.");
        } catch (ValidationException e) {
            // success
        }
    }

    /**
     * Test the method validateProject(Project) with invalid project whose type description is too long (more than 256
     * characters). ValidationException should be thrown.
     */
    public void testValidateProjectWithTooLongTypeDescription() {
        project.getProjectCategory().getProjectType().setDescription(s257);
        try {
            validator.validateProject(project);
            fail("ValidationException should be thrown.");
        } catch (ValidationException e) {
            // success
        }
    }

    /**
     * Test the method validateProject(Project) with invalid project whose category description is too long (more than
     * 256 characters). ValidationException should be thrown.
     */
    public void testValidateProjectWithTooLongCategoryDescription() {
        project.getProjectCategory().setDescription(s257);
        try {
            validator.validateProject(project);
            fail("ValidationException should be thrown.");
        } catch (ValidationException e) {
            // success
        }
    }

    /**
     * Test the method validateProject(Project) with invalid project whose status description is too long (more than 256
     * characters). ValidationException should be thrown.
     */
    public void testValidateProjectWithTooLongStatusDescription() {
        project.getProjectStatus().setDescription(s257);
        try {
            validator.validateProject(project);
            fail("ValidationException should be thrown.");
        } catch (ValidationException e) {
            // success
        }
    }

    /**
     * Test the method validateProject(Project) with invalid project whose property key is too long (more than 64
     * characters). ValidationException should be thrown.
     */
    public void testValidateProjectWithTooLongPropertyKey() {
        project.setProperty(s65, "valid value");
        try {
            validator.validateProject(project);
            fail("ValidationException should be thrown.");
        } catch (ValidationException e) {
            // success
        }
    }

    /**
     * Test the method validateProject(Project) with invalid project whose property value is too long (more than 4096
     * characters). ValidationException should be thrown.
     */
    public void testValidateProjectWithTooLongPropertyValue() {
        project.setProperty("value key", s4097);
        try {
            validator.validateProject(project);
            fail("ValidationException should be thrown.");
        } catch (ValidationException e) {
            // success
        }
    }
}
