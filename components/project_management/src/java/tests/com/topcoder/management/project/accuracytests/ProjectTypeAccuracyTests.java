/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.accuracytests;

import com.topcoder.management.project.ProjectType;

import junit.framework.TestCase;


/**
 * Accuracy test cases for <code>ProjectType</code>.
 *
 * @author skatou
 * @version 1.0
 */
public class ProjectTypeAccuracyTests extends TestCase {
    /** Represents a project type id. */
    private final long id = 9394;

    /** Represents a project type name. */
    private final String name = "TestType";

    /** Represents a project type description. */
    private final String description = "This is a description";

    /** The <code>ProjectType</code> instance to be tested. */
    private ProjectType type = new ProjectType(id, name, description);

    /**
     * Tests constructor without description.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructorNoDescription() throws Exception {
        type = new ProjectType(id, name);
        assertNotNull(type);
        assertEquals(id, type.getId());
        assertEquals(name, type.getName());
    }

    /**
     * Tests constructor with description.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructorWithDescription() throws Exception {
        type = new ProjectType(id, name, description);
        assertNotNull(type);
        assertEquals(id, type.getId());
        assertEquals(name, type.getName());
        assertEquals(description, type.getDescription());
    }

    /**
     * Tests setId and getId methods.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSetAndGetId() throws Exception {
        long testId = 9234;
        type.setId(testId);
        assertEquals(testId, type.getId());
    }

    /**
     * Tests setName and getName methods.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSetAndGetName() throws Exception {
        String testName = "TestName";
        type.setName(testName);
        assertEquals(testName, type.getName());
    }

    /**
     * Tests setDescription and getDescription methods.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSetAndGetDescription() throws Exception {
        String testDesc = "test description";
        type.setDescription(testDesc);
        assertEquals(testDesc, type.getDescription());
    }
}
