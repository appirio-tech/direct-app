/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.accuracytests;

import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectType;

import junit.framework.TestCase;


/**
 * Accuracy test cases for <code>ProjectCategory</code>.
 *
 * @author skatou
 * @version 1.0
 */
public class ProjectCategoryAccuracyTests extends TestCase {
    /** Represents a project category id. */
    private final long id = 9394;

    /** Represents a project category name. */
    private final String name = "TestType";

    /** Represents a project category description. */
    private final String description = "This is a description";

    /** Represents a project type. */
    private final ProjectType type = new ProjectType(id, name, description);

    /** The <code>ProjectCategory</code> instance to be tested. */
    private ProjectCategory category = new ProjectCategory(id, name, description, type);

    /**
     * Tests constructor without description.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructorNoDescription() throws Exception {
        category = new ProjectCategory(id, name, type);
        assertNotNull(category);
        assertEquals(id, category.getId());
        assertEquals(name, category.getName());
        assertEquals(type, category.getProjectType());
    }

    /**
     * Tests constructor with description.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructorWithDescription() throws Exception {
        category = new ProjectCategory(id, name, description, type);
        assertNotNull(category);
        assertEquals(id, category.getId());
        assertEquals(name, category.getName());
        assertEquals(description, category.getDescription());
        assertEquals(type, category.getProjectType());
    }

    /**
     * Tests setId and getId methods.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSetAndGetId() throws Exception {
        long testId = 9234;
        category.setId(testId);
        assertEquals(testId, category.getId());
    }

    /**
     * Tests setName and getName methods.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSetAndGetName() throws Exception {
        String testName = "TestName";
        category.setName(testName);
        assertEquals(testName, category.getName());
    }

    /**
     * Tests setDescription and getDescription methods.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSetAndGetDescription() throws Exception {
        String testDesc = "test description";
        category.setDescription(testDesc);
        assertEquals(testDesc, category.getDescription());
    }

    /**
     * Tests setProjectType and getProjectType methods.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSetAndGetProjectType() throws Exception {
        ProjectType testType = new ProjectType(999, "test type");
        category.setProjectType(testType);
        assertEquals(testType, category.getProjectType());
    }
}
