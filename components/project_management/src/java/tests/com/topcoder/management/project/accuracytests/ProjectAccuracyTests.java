/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.accuracytests;

import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;

import junit.framework.TestCase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Accuracy test cases for <code>Project</code>.
 *
 * @author skatou
 * @version 1.0
 */
public class ProjectAccuracyTests extends TestCase {
    /** Represents a project category id. */
    private final long id = 9394;

    /** Represents a project category name. */
    private final String name = "TestType";

    /** Represents a project category. */
    private final ProjectCategory category = new ProjectCategory(id, name, new ProjectType(id, name));

    /** Represents a project status. */
    private final ProjectStatus status = new ProjectStatus(id, name);

    /** The <code>Project</code> instance to be tested. */
    private Project project = new Project(id, category, status);

    /**
     * Tests constructor without id and properties.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructorNoIdAndProperties() throws Exception {
        project = new Project(category, status);
        assertNotNull(project);
        assertEquals(category, project.getProjectCategory());
        assertEquals(status, project.getProjectStatus());
        assertTrue(project.getAllProperties().isEmpty());
        assertEquals(null, project.getCreationUser());
        assertEquals(null, project.getModificationUser());
        assertEquals(null, project.getCreationTimestamp());
        assertEquals(null, project.getModificationTimestamp());
    }

    /**
     * Tests constructor without properties.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructorNoProperties() throws Exception {
        project = new Project(id, category, status);
        assertNotNull(project);
        assertEquals(id, project.getId());
        assertEquals(category, project.getProjectCategory());
        assertEquals(status, project.getProjectStatus());
        assertTrue(project.getAllProperties().isEmpty());
        assertEquals(null, project.getCreationUser());
        assertEquals(null, project.getModificationUser());
        assertEquals(null, project.getCreationTimestamp());
        assertEquals(null, project.getModificationTimestamp());
    }

    /**
     * Tests constructor with id and properties.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructorWithIdAndProperties() throws Exception {
        Map properties = new HashMap();
        properties.put("key", "value");
        project = new Project(id, category, status, properties);
        assertNotNull(project);
        assertEquals(id, project.getId());
        assertEquals(category, project.getProjectCategory());
        assertEquals(status, project.getProjectStatus());
        assertEquals(1, project.getAllProperties().size());
        assertEquals("value", project.getAllProperties().get("key"));
        assertEquals(null, project.getCreationUser());
        assertEquals(null, project.getModificationUser());
        assertEquals(null, project.getCreationTimestamp());
        assertEquals(null, project.getModificationTimestamp());
    }

    /**
     * Tests setCreationUser and getCreationUser methods.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSetAndGetCreationUser() throws Exception {
        String creationUser = "CreationUser";
        project.setCreationUser(creationUser);
        assertEquals(creationUser, project.getCreationUser());
    }

    /**
     * Tests setModificationUser and getModificationUser methods.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSetAndGetModificationUser() throws Exception {
        String modificationUser = "ModificationUser";
        project.setModificationUser(modificationUser);
        assertEquals(modificationUser, project.getModificationUser());
    }

    /**
     * Tests setCreationTimestamp and getCreationTimestamp methods.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSetAndGetCreationTimestamp() throws Exception {
        Date now = new Date();
        project.setCreationTimestamp(now);
        assertEquals(now, project.getCreationTimestamp());
    }

    /**
     * Tests setModificationTimestamp and getModificationTimestamp methods.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSetAndGetModificationTimestamp() throws Exception {
        Date now = new Date();
        project.setModificationTimestamp(now);
        assertEquals(now, project.getModificationTimestamp());
    }

    /**
     * Tests setId and getId methods.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSetAndGetId() throws Exception {
        long testId = 8;
        project.setId(testId);
        assertEquals(testId, project.getId());
    }

    /**
     * Tests setProjectStatus and getProjectStatus methods.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSetAndGetProjectStatus() throws Exception {
        ProjectStatus testStatus = new ProjectStatus(id, name);
        project.setProjectStatus(testStatus);
        assertEquals(testStatus, project.getProjectStatus());
    }

    /**
     * Tests setProjectCategory and getProjectCategory methods.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSetAndGetProjectCategoty() throws Exception {
        ProjectCategory testCategoty = new ProjectCategory(id, name, new ProjectType(id, name));
        project.setProjectCategory(testCategoty);
        assertEquals(testCategoty, project.getProjectCategory());
    }

    /**
     * Tests setProperty and getProperty methods.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSetAndGetProperty() throws Exception {
        project.setProperty("p1", "value1");
        assertEquals("value1", project.getProperty("p1"));
        project.setProperty("p2", "value2");
        assertEquals("value2", project.getProperty("p2"));
    }

    /**
     * Tests getAllProperties method.
     *
     * @throws Exception pass to JUnit.
     */
    public void testGetAllProperties() throws Exception {
        project.setProperty("p1", "value1");
        project.setProperty("p2", "value2");

        Map properties = project.getAllProperties();
        assertNotNull(properties);
        assertEquals(2, properties.size());
        assertEquals("value1", properties.get("p1"));
        assertEquals("value2", properties.get("p2"));
    }
}
