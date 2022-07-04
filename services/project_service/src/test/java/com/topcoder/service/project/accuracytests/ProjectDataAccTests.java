/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.project.ProjectData;

/**
 * <p>
 * Unit test for <code>{@link ProjectData}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public class ProjectDataAccTests extends TestCase {

    /**
     * <p>
     * Represents the <code>ProjectData</code> instance.
     * </p>
     */
    private ProjectData projectData;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ProjectDataAccTests.class);
    }

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        projectData = new ProjectData();
    }

    /**
     * <p>
     * Unit test for <code>ProjectData#ProjectData()</code> constructor.
     * </p>
     * <p>
     * Instance should be always created.
     * </p>
     */
    public void testProjectData_accuracy() {
        assertNotNull("Instance isn't created in setUp.", projectData);
    }

    /**
     * <p>
     * Unit test for <code>ProjectData#getProjectId()</code> method.
     * </p>
     * <p>
     * It should return default value - null.
     * </p>
     */
    public void testGetProjectId_default() {
        assertNull("The project id is default to null.", projectData.getProjectId());
    }

    /**
     * <p>
     * Unit test for <code>ProjectData#setProjectId(Long)</code> method.
     * </p>
     * <p>
     * Null value is valid. Should set internally.
     * </p>
     */
    public void testSetProjectId_null() {
        // nothing happens when set null value.
        projectData.setProjectId(null);
        assertNull("The project id should be null.", projectData.getProjectId());
    }

    /**
     * <p>
     * Unit test for <code>ProjectData#setProjectId(Long)</code> method.
     * </p>
     * <p>
     * Any Long value is valid.
     * </p>
     */
    public void testSetProjectId_Negative() {
        projectData.setProjectId(-1L);

        assertEquals("Incorrect project id.", new Long(-1L), projectData.getProjectId());
    }

    /**
     * <p>
     * Unit test for <code>ProjectData#setProjectId(Long)</code> method.
     * </p>
     * <p>
     * Any Long value is valid.
     * </p>
     */
    public void testSetProjectId_Zero() {
        projectData.setProjectId(0L);

        assertEquals("Incorrect project id.", new Long(0L), projectData.getProjectId());
    }

    /**
     * <p>
     * Unit test for <code>ProjectData#setProjectId(Long)</code> method.
     * </p>
     * <p>
     * Any Long value is valid.
     * </p>
     */
    public void testSetProjectId_Positive() {
        projectData.setProjectId(1L);

        assertEquals("Incorrect project id.", new Long(1L), projectData.getProjectId());
    }

    /**
     * <p>
     * Unit test for <code>ProjectData#getName()</code> method.
     * </p>
     * <p>
     * It should return null by default.
     * </p>
     */
    public void testGetName_default() {
        assertNull("Null should return.", projectData.getName());
    }

    /**
     * <p>
     * Unit test for <code>ProjectData#setName(String)</code> method.
     * </p>
     * <p>
     * Null value is valid.
     * </p>
     */
    public void testSetName_Null() {
        projectData.setName(null);

        assertNull("Incorrect name.", projectData.getName());
    }

    /**
     * <p>
     * Unit test for <code>ProjectData#setName(String)</code> method.
     * </p>
     * <p>
     * Empty value is valid.
     * </p>
     */
    public void testSetName_Empty() {
        projectData.setName("");

        assertEquals("Incorrect name.", "", projectData.getName());
    }

    /**
     * <p>
     * Unit test for <code>ProjectData#setName(String)</code> method.
     * </p>
     * <p>
     * Trimmed empty value is valid.
     * </p>
     */
    public void testSetName_TrimmedEmpty() {
        projectData.setName("  ");

        assertEquals("Incorrect name.", "  ", projectData.getName());
    }

    /**
     * <p>
     * Unit test for <code>ProjectData#setName(String)</code> method.
     * </p>
     * <p>
     * Non null or empty values are definitely valid to set.
     * </p>
     */
    public void testSetName_NonNullOrEmpty() {
        projectData.setName("Project Service");

        assertEquals("Incorrect name.", "Project Service", projectData.getName());
    }

    /**
     * <p>
     * Unit test for <code>ProjectData#getDescription()</code> method.
     * </p>
     * <p>
     * It should return null by default.
     * </p>
     */
    public void testGetDescription_default() {
        assertNull("Null should return.", projectData.getDescription());
    }

    /**
     * <p>
     * Unit test for <code>ProjectData#setDescription(String)</code> method.
     * </p>
     * <p>
     * Null value is valid.
     * </p>
     */
    public void testSetDescription_Null() {
        projectData.setDescription(null);

        assertNull("Incorrect description.", projectData.getDescription());
    }

    /**
     * <p>
     * Unit test for <code>ProjectData#setDescription(String)</code> method.
     * </p>
     * <p>
     * Empty value is valid.
     * </p>
     */
    public void testSetDescription_Empty() {
        projectData.setDescription("");

        assertEquals("Incorrect description.", "", projectData.getDescription());
    }

    /**
     * <p>
     * Unit test for <code>ProjectData#setDescription(String)</code> method.
     * </p>
     * <p>
     * Trimmed empty value is valid.
     * </p>
     */
    public void testSetDescription_TrimmedEmpty() {
        projectData.setDescription("  ");

        assertEquals("Incorrect description.", "  ", projectData.getDescription());
    }

    /**
     * <p>
     * Unit test for <code>ProjectData#setDescription(String)</code> method.
     * </p>
     * <p>
     * Non null or empty values are definitely valid to set.
     * </p>
     */
    public void testSetDescription_NonNullOrEmpty() {
        projectData.setDescription("Project Service");

        assertEquals("Incorrect description.", "Project Service", projectData.getDescription());
    }
}
