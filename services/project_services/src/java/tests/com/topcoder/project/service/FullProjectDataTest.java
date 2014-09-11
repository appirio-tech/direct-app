/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service;

import java.util.Date;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.date.workdays.Workdays;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.team.TeamHeader;

import junit.framework.TestCase;

/**
 * <p>
 * This is a test case for <code>FullProjectData</code> class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FullProjectDataTest extends TestCase {

    /**
     * <p>
     * Represents an array of Resources.
     * </p>
     */
    private Resource[] resources = null;

    /**
     * <p>
     * Represents an instance of Project.
     * </p>
     */
    private Project projectHeader = null;

    /**
     * <p>
     * Represents a TeamHeader array.
     * </p>
     */
    private TeamHeader[] teams = null;

    /**
     * <p>
     * Represents the technologies array.
     * </p>
     */
    private String[] technologies = null;

    /**
     * <p>
     * Represents an instance of Workdays.
     * </p>
     */
    private Workdays workdays = null;

    /**
     * <p>
     * Represents an new instance of FullProjectData.
     * </p>
     */
    private FullProjectData projectData = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        resources = new Resource[] {new Resource(1), new Resource(2)};
        projectHeader = new Project(new ProjectCategory(1, "Dev", new ProjectType(1, "type1")),
            new ProjectStatus(1, "submission"));
        teams = new TeamHeader[] {new TeamHeader()};
        technologies = new String[] {"Java", "JSP"};
        workdays = new DefaultWorkdays();
        projectData = new FullProjectData(resources, projectHeader, teams, technologies,
            new Date(), workdays);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        this.resources = null;
        this.projectData = null;
        this.projectHeader = null;
        this.teams = null;
        this.technologies = null;
        this.workdays = null;
    }

    /**
     * <p>
     * Test for <code>FullProjectData(startDate,workdays)</code> method.
     * </p>
     * <p>
     * Tests it with null startDate, expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1WithNullStartDate() throws Exception {
        try {
            new FullProjectData(null, workdays);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>FullProjectData(startDate,workdays)</code> method.
     * </p>
     * <p>
     * Tests it with null workdays, expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1WithNullWorkdays() throws Exception {
        try {
            new FullProjectData(new Date(), null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>FullProjectData(startDate,workdays)</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, non-null instance should be thrown.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1Accuracy() throws Exception {
        projectData = new FullProjectData(new Date(), workdays);
        assertNotNull("'projectData' should not be null.", projectData);
    }

    /**
     * <p>
     * Test for constructor with full parameters.
     * </p>
     * <p>
     * Tests it with null projectHeader, expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithNullProjectheader() throws Exception {
        try {
            new FullProjectData(resources, null, teams, technologies, new Date(), workdays);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with full parameters.
     * </p>
     * <p>
     * Tests it for accuracy with full parameters. non-null instance should be returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2Accuracy1() throws Exception {
        projectData = new FullProjectData(resources, projectHeader, teams, technologies,
            new Date(), workdays);
        assertNotNull("'projectData' should not be null.", projectData);
    }

    /**
     * <p>
     * Test for constructor with full parameters.
     * </p>
     * <p>
     * Tests it for accuracy with full parameters, all array parameter are null. non-null instance
     * should be returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2Accuracy2() throws Exception {
        projectData = new FullProjectData(null, projectHeader, null, null, new Date(), workdays);
        assertNotNull("'projectData' should not be null.", projectData);
    }

    /**
     * <p>
     * Test for <code>getResources()</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, an array containing two Resources should be returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetResources() throws Exception {
        Resource[] rs = projectData.getResources();
        assertEquals("Resource array mismatched.", rs, resources);
    }

    /**
     * <p>
     * Test for <code>setResources(resources)</code> method.
     * </p>
     * <p>
     * Tests it against array having null elements. expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetResourcesWithArrayHasNull() throws Exception {
        try {
            projectData.setResources(new Resource[] {null});
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>setResources(resources)</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, with null argument, empty array should be set.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetResourcesWithNullArg() throws Exception {
        assertEquals("There should be two resources currently.", 2,
            projectData.getResources().length);
        projectData.setResources(null);
        assertEquals("There should be no resource now.", 0, projectData.getResources().length);
    }

    /**
     * <p>
     * Test for <code>setResources(resources)</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, with real resource array, resource array with two elements should be
     * set.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetResourcesWithRealArg() throws Exception {
        projectData.setResources(resources);
        assertEquals("There should be two resources currently.", 2,
            projectData.getResources().length);
    }

    /**
     * <p>
     * Test for <code>getProjectHeader()</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, non-null projectHeader should be returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetProjectHeader() throws Exception {
        Project header = projectData.getProjectHeader();
        assertEquals("Project header mismatched.", header, projectHeader);
    }

    /**
     * <p>
     * Test for <code>setProjectHeader(projectHeader)</code> method.
     * </p>
     * <p>
     * Tests it with null header, expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetProjectHeaderWithNullArg() throws Exception {
        try {
            projectData.setProjectHeader(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>setProjectHeader(projectHeader)</code> method.
     * </p>
     * <p>
     * Tests it with valid header, should set successfully.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetProjectHeaderAccuracy() throws Exception {
        projectData.setProjectHeader(projectHeader);
        assertEquals("Project header mismatched.", projectHeader, projectData.getProjectHeader());
    }

    /**
     * <p>
     * Test for <code>getTeams()</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, with null argument, empty array should be set.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetTeamsWithNullArg() throws Exception {
        assertEquals("There should be one team currently.", 1, projectData.getTeams().length);
        projectData.setTeams(null);
        assertEquals("There should be no team now.", 0, projectData.getTeams().length);
    }

    /**
     * <p>
     * Test for <code>getTeams()</code> method.
     * </p>
     * <p>
     * Tests it for accuracy with team having one element, should set successfully.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetTeamsWithRealTeamsArray() throws Exception {
        projectData.setTeams(teams);
        assertEquals("There should be one team.", 1, projectData.getTeams().length);
    }

    /**
     * <p>
     * Test for <code>getTechnologies()</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, an array with two elements should be returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetTechnologies() throws Exception {
        String[] techs = projectData.getTechnologies();
        assertEquals("There should be two elements.", 2, techs.length);
    }

    /**
     * <p>
     * Test for <code>setTechnologies(techs)</code> method.
     * </p>
     * <p>
     * Tests it with null argument, empty array should be set.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetTechnologiesWithNull() throws Exception {
        assertEquals("There should be two technologies currently.", 2, projectData
            .getTechnologies().length);
        projectData.setTechnologies(null);
        assertEquals("There should be no technology now.", 0, projectData.getTechnologies().length);
    }

    /**
     * <p>
     * Test for <code>setTechnologies(techs)</code> method.
     * </p>
     * <p>
     * Tests it with array containing two elements, should set successfully.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetTechnologiesWithRealArray() throws Exception {
        projectData.setTechnologies(technologies);
        assertEquals("There should be two technologies currently.", 2, projectData
            .getTechnologies().length);
    }
}
