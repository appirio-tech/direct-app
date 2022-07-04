/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.accuracytests;

import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.project.service.FullProjectData;

/**
 * <p>
 * Tests the functionality of <code>FullProjectDataTest</code> class for accuracy.
 * </p>
 *
 * @author kshatriyan
 * @version 1.0
 */
public class FullProjectDataTest extends TestCase {
    /**
     * <p>
     * Instance of <code>FullProjectData</code> for test.
     * </p>
     */
    private FullProjectData fullProjectData;

    /**
     * <p>
     * Array of <code>Resource</code> for test.
     * </p>
     */
    private Resource[] resources;

    /**
     * <p>
     * Instance of <code>Project</code> for test.
     * </p>
     */
    private Project projectHeader;

    /**
     * <p>
     * Array of <code>TeamHeader</code> for test.
     * </p>
     */
    private TeamHeader[] teams;

    /**
     * <p>
     * Array of <code>String</code> holding technologies for test.
     * </p>
     */
    private String[] technologies;

    /**
     * <p>
     * Integrates all tests in this class.
     * </p>
     *
     * @return Test suite of all tests of <code>FullProjectDataTest</code>.
     */
    public static Test suite() {
        return new TestSuite(FullProjectDataTest.class);
    }

    /**
     * <p>
     * Sets up the environment before each TestCase.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    protected void setUp() throws Exception {
        resources = new Resource[] {new Resource(1), new Resource(2) };
        projectHeader = new Project(new ProjectCategory(1, "Component Development", new ProjectType(1, "level1")),
                new ProjectStatus(1, "accuracy_review"));
        teams = new TeamHeader[] {new TeamHeader() };
        technologies = new String[] {"Java", "C#" };

    }

    /**
     * <p>
     * Tears down the environment after execution of each TestCase.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    protected void tearDown() throws Exception {
        fullProjectData = null;
        resources = null;
        projectHeader = null;
        teams = null;
        technologies = null;
    }

    /**
     * <p>
     * Accuracy test of <code>FullProjectData(Date startDate, Workdays workdays)</code> constructor for accuracy.
     * </p>
     *
     * <p>
     * Tests for instantiation and inheritance.
     * </p>
     *
     */
    public void testFullProjectData_accuracy1() {
        fullProjectData = new FullProjectData(new Date(), new DefaultWorkdays());
        assertNotNull("Unable to create the instance properly", fullProjectData);
        assertTrue("FullProjectData should extend Project",
                fullProjectData instanceof com.topcoder.project.phases.Project);
    }

    /**
     * <p>
     * Accuracy test of <code>FullProjectData(Resource[], Project, TeamHeader[], String[], Date, Workdays)</code>
     * constructor for accuracy.
     * </p>
     *
     * <p>
     * Tests for instantiation and inheritance.
     * </p>
     *
     */
    public void testFullProjectData_accuracy2() {
        fullProjectData = new FullProjectData(resources, projectHeader, teams, technologies, new Date(),
                new DefaultWorkdays());
        assertNotNull("Unable to create the instance properly", fullProjectData);
        assertTrue("FullProjectData should extend Project",
                fullProjectData instanceof com.topcoder.project.phases.Project);
    }

    /**
     * <p>
     * Accuracy test of <code>getResources()</code> method.
     * </p>
     *
     * <p>
     * Tests for proper retrieval of resources.
     * </p>
     *
     */
    public void testGetResources_accuracy() {
        fullProjectData = new FullProjectData(resources, projectHeader, teams, technologies, new Date(),
                new DefaultWorkdays());
        assertEquals("Unable to get the resources properly", resources, fullProjectData.getResources());

        // Non null resources of length zero should be retrieved.
        fullProjectData = new FullProjectData(new Date(), new DefaultWorkdays());
        assertNotNull("Unable to get the resources properly", fullProjectData.getResources());
        assertEquals("Unable to get the resources properly", 0, fullProjectData.getResources().length);
    }

    /**
     * <p>
     * Accuracy test of <code>setResources(Resource[] resources)</code> method.
     * </p>
     *
     * <p>
     * Tests whether the resources are set properly.
     * </p>
     *
     */
    public void testSetResources_accuracy1() {
        // sets resources
        fullProjectData = new FullProjectData(new Date(), new DefaultWorkdays());
        fullProjectData.setResources(resources);
        // test for proper setting of resources
        assertEquals("Unable to set the resources properly", resources, fullProjectData.getResources());

    }

    /**
     * <p>
     * Accuracy test of <code>setResources(Resource[] resources)</code> method.
     * </p>
     *
     * <p>
     * Tests whether null resources are set properly.
     * </p>
     *
     */
    public void testSetResources_accuracy2() {
        fullProjectData = new FullProjectData(resources, projectHeader, teams, technologies, new Date(),
                new DefaultWorkdays());
        // sets null resources
        fullProjectData.setResources(null);
        // Non null resources of length zero should be retrieved.
        assertNotNull("Unable to set the resources properly", fullProjectData.getResources());
        assertEquals("Unable to set the resources properly", 0, fullProjectData.getResources().length);
    }

    /**
     * <p>
     * Accuracy test of <code>getProjectHeader()</code> method.
     * </p>
     *
     * <p>
     * Tests for proper retrieval of project header.
     * </p>
     *
     */
    public void testGetProjectHeader_accuracy() {
        fullProjectData = new FullProjectData(resources, projectHeader, teams, technologies, new Date(),
                new DefaultWorkdays());
        assertEquals("Unable to get project header properly", projectHeader, fullProjectData.getProjectHeader());

        // Null project header should be retrieved
        fullProjectData = new FullProjectData(new Date(), new DefaultWorkdays());
        assertNull("Unable to get project header properly", fullProjectData.getProjectHeader());
    }

    /**
     * <p>
     * Accuracy test of <code>setProjectHeader(Project projectHeader)</code> method.
     * </p>
     *
     * <p>
     * Tests whether the project header is set properly.
     * </p>
     *
     */
    public void testSetProjectHeader_accuracy() {
        // Sets project header
        fullProjectData = new FullProjectData(new Date(), new DefaultWorkdays());
        fullProjectData.setProjectHeader(projectHeader);
        // Non null project header should be retrieved.
        assertEquals("Unable to set the project header properly", projectHeader, fullProjectData.getProjectHeader());
    }

    /**
     * <p>
     * Accuracy test of <code>getTeams()</code> method.
     * </p>
     *
     * <p>
     * Tests for proper retrieval of project header.
     * </p>
     *
     */
    public void testGetTeams_accuracy() {
        fullProjectData = new FullProjectData(resources, projectHeader, teams, technologies, new Date(),
                new DefaultWorkdays());
        assertEquals("Unable to get the teams properly", teams, fullProjectData.getTeams());
        // Non null teams of length zero should be retrieved.
        fullProjectData = new FullProjectData(new Date(), new DefaultWorkdays());
        assertNotNull("Unable to get the teams properly", fullProjectData.getTeams());
        assertEquals("Unable to get the teams properly", 0, fullProjectData.getTeams().length);

    }

    /**
     * <p>
     * Accuracy test of <code>setTeams(TeamHeader[] teams)</code> method.
     * </p>
     *
     * <p>
     * Tests whether the teams are set properly.
     * </p>
     *
     */
    public void testSetTeams_accuracy1() {
        // sets team
        fullProjectData = new FullProjectData(new Date(), new DefaultWorkdays());
        fullProjectData.setTeams(teams);
        // test for proper setting of teams
        assertEquals("Unable to set the teams properly", teams, fullProjectData.getTeams());

    }

    /**
     * <p>
     * Accuracy test of <code>setTeams(TeamHeader[] teams)</code> method.
     * </p>
     *
     * <p>
     * Tests whether null teams are set properly.
     * </p>
     *
     */
    public void testSetTeams_accuracy2() {
        fullProjectData = new FullProjectData(resources, projectHeader, teams, technologies, new Date(),
                new DefaultWorkdays());
        // sets null team
        fullProjectData.setTeams(null);
        // Non null teams of length zero should be retrieved.
        assertNotNull("Unable to set the teams properly", fullProjectData.getTeams());
        assertEquals("Unable to set the teams properly", 0, fullProjectData.getTeams().length);
    }

    /**
     * <p>
     * Accuracy test of <code>getTechnologies()</code> method.
     * </p>
     *
     * <p>
     * Tests for proper retrieval of technologies.
     * </p>
     *
     */
    public void testGetTechnologies_accuracy() {
        fullProjectData = new FullProjectData(resources, projectHeader, teams, technologies, new Date(),
                new DefaultWorkdays());
        assertEquals("Unable to get the technologies properly", technologies, fullProjectData.getTechnologies());
        // Non null technologies of length zero should be retrieved.
        fullProjectData = new FullProjectData(new Date(), new DefaultWorkdays());
        assertNotNull("Unable to get the technologies properly", fullProjectData.getTechnologies());
        assertEquals("Unable to get the technologies properly", 0, fullProjectData.getTechnologies().length);

    }

    /**
     * <p>
     * Accuracy test of <code>setTechnologies(String[] technologies)</code> method.
     * </p>
     *
     * <p>
     * Tests whether technologies are set properly.
     * </p>
     *
     */
    public void testSetTechnologies_accuracy1() {
        // sets technologies
        fullProjectData = new FullProjectData(new Date(), new DefaultWorkdays());
        fullProjectData.setTechnologies(technologies);
        // test for proper setting of technologies
        assertEquals("Unable to set the technologies properly", technologies, fullProjectData.getTechnologies());
    }

    /**
     * <p>
     * Accuracy test of <code>setTechnologies(String[] technologies)</code> method.
     * </p>
     *
     * <p>
     * Tests whether null technologies are set properly.
     * </p>
     *
     */
    public void testSetTechnologies_accuracy2() {
        fullProjectData = new FullProjectData(resources, projectHeader, teams, technologies, new Date(),
                new DefaultWorkdays());
        // sets null technologies
        fullProjectData.setTechnologies(null);
        // Non null technologies of length zero should be retrieved.
        assertNotNull("Unable to set the technologies properly", fullProjectData.getTechnologies());
        assertEquals("Unable to set the technologies properly", 0, fullProjectData.getTechnologies().length);
    }

}