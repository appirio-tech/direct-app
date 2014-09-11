/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.accuracytests;

import com.topcoder.management.project.ProjectStatus;

import junit.framework.TestCase;


/**
 * Accuracy test cases for <code>ProjectStatus</code>.
 *
 * @author skatou
 * @version 1.0
 */
public class ProjectStatusAccuracyTests extends TestCase {
    /** Represents a project type id. */
    private final long id = 9394;

    /** Represents a project type name. */
    private final String name = "TestType";

    /** Represents a project type description. */
    private final String description = "This is a description";

    /** The <code>ProjectStatus</code> instance to be tested. */
    private ProjectStatus status = new ProjectStatus(id, name, description);

    /**
     * Tests constructor without description.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructorNoDescription() throws Exception {
        status = new ProjectStatus(id, name);
        assertNotNull(status);
        assertEquals(id, status.getId());
        assertEquals(name, status.getName());
    }

    /**
     * Tests constructor with description.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructorWithDescription() throws Exception {
        status = new ProjectStatus(id, name, description);
        assertNotNull(status);
        assertEquals(id, status.getId());
        assertEquals(name, status.getName());
        assertEquals(description, status.getDescription());
    }

    /**
     * Tests setId and getId methods.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSetAndGetId() throws Exception {
        long testId = 9234;
        status.setId(testId);
        assertEquals(testId, status.getId());
    }

    /**
     * Tests setName and getName methods.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSetAndGetName() throws Exception {
        String testName = "TestName";
        status.setName(testName);
        assertEquals(testName, status.getName());
    }

    /**
     * Tests setDescription and getDescription methods.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSetAndGetDescription() throws Exception {
        String testDesc = "test description";
        status.setDescription(testDesc);
        assertEquals(testDesc, status.getDescription());
    }
}
