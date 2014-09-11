/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.failuretests;

import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.date.workdays.Workdays;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.project.service.FullProjectData;

/**
 * Failure test for <code>{@link com.topcoder.project.service.FullProjectData}</code> class.
 * 
 * @author mittu
 * @version 1.0
 */
public class FullProjectDataTest extends TestCase {
    /**
     * <p>
     * Represents the array of Resources.
     * </p>
     */
    private Resource[] resources;

    /**
     * <p>
     * Represents the Project used for testing.
     * </p>
     */
    private Project projectHeader;

    /**
     * <p>
     * Represents the TeamHeader used for testing.
     * </p>
     */
    private TeamHeader[] teams;

    /**
     * <p>
     * Represents the technologies used for testing.
     * </p>
     */
    private String[] technologies;

    /**
     * <p>
     * Represents the Workdays used for testing.
     * </p>
     */
    private Workdays workdays;

    /**
     * <p>
     * Represents the FullProjectData instance for testing.
     * </p>
     */
    private FullProjectData fullProjectData;

    /**
     * Sets up test environment.
     * 
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        resources = new Resource[] { new Resource(1), new Resource(2) };
        projectHeader = new Project(new ProjectCategory(1, "Dev", new ProjectType(1, "Level1")),
                new ProjectStatus(1, "registration"));
        teams = new TeamHeader[] { new TeamHeader() };
        technologies = new String[] { "Java", "JDBC" };
        workdays = new DefaultWorkdays();
        fullProjectData = new FullProjectData(resources, projectHeader, teams, technologies, new Date(),
                workdays);
    }

    /**
     * Tears down test environment.
     * 
     * @throws Exception
     *             to junit
     */
    protected void tearDown() throws Exception {
        resources = null;
        fullProjectData = null;
        projectHeader = null;
        teams = null;
        technologies = null;
        workdays = null;
    }

    /**
     * Failure test for <code>{@link FullProjectData#FullProjectData(Date,Workdays)}</code>.
     */
    public void testConstructor_Date_Workdays_1() {
        try {
            new FullProjectData(null, workdays);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>{@link FullProjectData#FullProjectData(Date,Workdays)}</code>.
     */
    public void testConstructor_Date_Workdays_2() {
        try {
            new FullProjectData(new Date(), null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for
     * <code>{@link FullProjectData#FullProjectData(Resource[],Project,TeamHeader[],String[],Date,Workdays)}</code>.
     */
    public void testConstructor_ResourceArray_Project_TeamHeaderArray_StringArray_Date_Workdays_1() {
        try {
            new FullProjectData(resources, null, teams, technologies, new Date(), workdays);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for
     * <code>{@link FullProjectData#FullProjectData(Resource[],Project,TeamHeader[],String[],Date,Workdays)}</code>.
     */
    public void testConstructor_ResourceArray_Project_TeamHeaderArray_StringArray_Date_Workdays_2() {
        try {
            new FullProjectData(new Resource[] { null }, projectHeader, teams, technologies, new Date(),
                    workdays);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for
     * <code>{@link FullProjectData#FullProjectData(Resource[],Project,TeamHeader[],String[],Date,Workdays)}</code>.
     */
    public void testConstructor_ResourceArray_Project_TeamHeaderArray_StringArray_Date_Workdays_3() {
        try {
            new FullProjectData(resources, projectHeader, new TeamHeader[] { null }, technologies,
                    new Date(), workdays);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for
     * <code>{@link FullProjectData#FullProjectData(Resource[],Project,TeamHeader[],String[],Date,Workdays)}</code>.
     */
    public void testConstructor_ResourceArray_Project_TeamHeaderArray_StringArray_Date_Workdays_4() {
        try {
            new FullProjectData(resources, projectHeader, teams, new String[] { null }, new Date(), workdays);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // success
        }
        try {
            new FullProjectData(resources, projectHeader, teams, new String[] { "" }, new Date(), workdays);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for <code>{@link FullProjectData#setTechnologies(String[])}</code>.
     */
    public void testMethodSetTechnologies_StringArray() {
        try {
            fullProjectData.setTechnologies(new String[]{""});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // success
        }
        try {
            fullProjectData.setTechnologies(new String[]{null});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for <code>{@link FullProjectData#setResources(Resource[])}</code>.
     */
    public void testMethodSetResources_ResourceArray() {
        try {
            fullProjectData.setResources(new Resource[]{null});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for <code>{@link FullProjectData#setProjectHeader(Project)}</code>.
     */
    public void testMethodSetProjectHeader_Project() {
        try {
            fullProjectData.setProjectHeader(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for <code>{@link FullProjectData#setTeams(TeamHeader[])}</code>.
     */
    public void testMethodSetTeams_TeamHeaderArray() {
        try {
            fullProjectData.setTeams(new TeamHeader[]{null});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Returns all tests.
     * 
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(FullProjectDataTest.class);
    }
}
