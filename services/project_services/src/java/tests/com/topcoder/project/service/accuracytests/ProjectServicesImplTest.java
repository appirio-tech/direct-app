/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.accuracytests;

import java.util.Iterator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cronos.onlinereview.external.ProjectRetrieval;
import com.topcoder.management.phase.PhaseManager;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectFilterUtility;
import com.topcoder.management.project.ProjectManager;
import com.topcoder.management.resource.ResourceManager;
import com.topcoder.management.team.TeamManager;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.project.service.ProjectServices;
import com.topcoder.project.service.impl.ProjectServicesImpl;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * Tests the functionality of <code>ProjectServicesImplTest</code> class for accuracy.
 * </p>
 * 
 * @author kshatriyan
 * @version 1.0
 */
public class ProjectServicesImplTest extends TestCase {
    /**
     * <p>
     * Instance of <code>ProjectServicesImpl</code> for test.
     * </p>
     */
    private ProjectServicesImpl projectServicesImpl;
    /**
     * <p>
     * Instance of <code>ProjectServicesImpl</code> for test.
     * </p>
     */
    private ProjectRetrieval projectRetrieval;
    /**
     * <p>
     * Instance of <code>ProjectServicesImpl</code> for test.
     * </p>
     */
    private ResourceManager resourceManager;
    /**
     * <p>
     * Instance of <code>ProjectServicesImpl</code> for test.
     * </p>
     */
    private PhaseManager phaseManager;
    /**
     * <p>
     * Instance of <code>ProjectServicesImpl</code> for test.
     * </p>
     */
    private TeamManager teamManager;
    /**
     * <p>
     * Instance of <code>ProjectServicesImpl</code> for test.
     * </p>
     */
    private ProjectManager projectManager;

    /**
     * <p>
     * Integrates all tests in this class.
     * </p>
     * 
     * @return Test suite of all tests of <code>ProjectServicesImplTest</code>.
     */
    public static Test suite() {
        return new TestSuite(ProjectServicesImplTest.class);
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
        // adds the accuracy configuration file
        ConfigManager configManager = ConfigManager.getInstance();
        configManager.add("configuration_accuracy.xml");

        projectRetrieval = new MockProjectRetrieval();
        resourceManager = new MockResourceManager();
        phaseManager = new MockPhaseManager();
        teamManager = new MockTeamManager();
        projectManager = new MockProjectManager();
        projectServicesImpl = new ProjectServicesImpl(projectRetrieval, resourceManager, phaseManager, teamManager,
                projectManager, LogManager.getLog(), 1);
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
        projectRetrieval = null;
        resourceManager = null;
        phaseManager = null;
        teamManager = null;
        projectManager = null;
        projectServicesImpl = null;

        // removes the loaded configurations
        ConfigManager configManager = ConfigManager.getInstance();
        for (Iterator iterator = configManager.getAllNamespaces(); iterator.hasNext();) {
            configManager.removeNamespace((String) iterator.next());
        }
    }

    /**
     * <p>
     * Accuracy test of <code>ProjectServicesImpl()</code> constructor.
     * </p>
     * 
     * <p>
     * Tests for instantiation and inheritance.
     * </p>
     * 
     */
    public void testProjectServicesImpl_accuracy1() {
        projectServicesImpl = new ProjectServicesImpl();
        assertNotNull("Unable to create the instance properly", projectServicesImpl);
        assertTrue("projectServicesImpl should extend ProjectServices",
                projectServicesImpl instanceof ProjectServices);
    }

    /**
     * <p>
     * Accuracy test of <code>ProjectServicesImpl(String namespace)</code> constructor.
     * </p>
     * 
     * <p>
     * Tests for instantiation and inheritance.
     * </p>
     * 
     */
    public void testProjectServicesImpl_accuracy2() {
        projectServicesImpl = new ProjectServicesImpl(ProjectServicesImpl.class.getName());
        assertNotNull("Unable to create the instance properly", projectServicesImpl);
        assertTrue("projectServicesImpl should extend ProjectServices",
                projectServicesImpl instanceof ProjectServices);
    }

    /**
     * <p>
     * Accuracy test of<code>
     * ProjectServicesImpl(ProjectRetrieval,ResourceManager,PhaseManager,TeamManager,ProjectManager,Log,long)</code>
     * constructor.
     * </p>
     * 
     * <p>
     * Tests for instantiation and inheritance.
     * </p>
     * 
     */
    public void testProjectServicesImpl_accuracy3() {
        assertNotNull("Unable to create the instance properly", projectServicesImpl);
        assertTrue("projectServicesImpl should extend ProjectServices",
                projectServicesImpl instanceof ProjectServices);
    }

    /**
     * <p>
     * Accuracy test of <code>findActiveProjects()</code> method.
     * </p>
     * 
     * <p>
     * Tests for valid active projects.
     * </p>
     * 
     * @throws Exception
     *             to junit.
     * 
     */
    public void testFindActiveProjects_accuracy1() throws Exception {
        MockProjectManager.setEmptyProjectCategory(false);
        FullProjectData[] fullProjectDatas = projectServicesImpl.findActiveProjects();

        assertEquals("Unable to find FullProjectData properly", 2, fullProjectDatas.length);

        FullProjectData fullProjectData = fullProjectDatas[0];

        assertNotNull("Unable to get a non null FullProjectData", fullProjectData);

        // Tests whether FullProjectData details are accurate.
        assertAccuracyFullProjectData(fullProjectData, 1);
    }

    /**
     * <p>
     * Accuracy test of <code>findActiveProjects()</code> method.
     * </p>
     * 
     * <p>
     * Tests for no active categories in the database. Empty FullProjectData[] must be retrieved.
     * </p>
     * 
     * @throws Exception
     *             to junit.
     * 
     */
    public void testFindActiveProjects_accuracy2() throws Exception {
        MockProjectManager.setEmptyProjectCategory(true);
        FullProjectData[] fullProjectDatas = projectServicesImpl.findActiveProjects();

        assertEquals("Unable to find FullProjectData properly", 0, fullProjectDatas.length);

    }

    /**
     * <p>
     * Accuracy test of <code>findActiveProjectsHeaders()</code> method.
     * </p>
     * 
     * <p>
     * Tests for no active categories in the database. Empty Project[] must be retrieved.
     * </p>
     * 
     */
    public void testFindActiveProjectsHeaders_accuracy1() {
        MockProjectManager.setEmptyProjectCategory(true);
        assertEquals("Unable to find empty active Project", 0,
                projectServicesImpl.findActiveProjectsHeaders().length);
    }

    /**
     * <p>
     * Accuracy test of <code>findActiveProjectsHeaders()</code> method.
     * </p>
     * 
     * <p>
     * Tests for active categories. Non empty Project[] must be retrieved.
     * </p>
     * 
     */
    public void testFindActiveProjectsHeaders_accuracy2() {
        MockProjectManager.setEmptyProjectCategory(false);
        assertEquals("Unable to find empty active Project", 2,
                projectServicesImpl.findActiveProjectsHeaders().length);
    }

	/**
	 *
	 * <p>
	 * Accuracy test of <code>findAllTcDirectProjects()</code> method.
	 * </p>
	 *
	 * <p>
	 * Tests for all tc direct project. Non empty Project[] must be retrieved.
	 * </p>
	 *
	 */
	public void testfindAllTcDirectProjects_accuracy() {
		assertEquals("find all tc direct Project", 2, projectServicesImpl
				.findAllTcDirectProjects().length);

	}

	/**
	 *
	 * <p>
	 * Accuracy test of
	 * <code>findAllTcDirectProjectsForUser(String user) </code> method.
	 * </p>
	 *
	 * <p>
	 * Tests for all user tc direct project. Non empty Project[] must be
	 * retrieved.
	 * </p>
	 *
	 */
	public void testfindAllTcDirectProjectsForUser_accuracy1() {
		assertEquals("find all user tc direct Project", 1, projectServicesImpl
				.findAllTcDirectProjectsForUser("user").length);
		assertEquals("find all user tc direct Project", 1, projectServicesImpl
				.findAllTcDirectProjectsForUser("user2").length);
	}

	/**
	 *
	 * <p>
	 * Accuracy test of
	 * <code>findAllTcDirectProjectsForUser(String user) </code> method.
	 * </p>
	 *
	 * <p>
	 * Tests for no tc direct project for user. Empty Project[] must be
	 * retrieved.
	 * </p>
	 *
	 */
	public void testfindAllTcDirectProjectsForUser_accuracy2() {
		assertEquals("Unable to find empty active Project", 0,
				projectServicesImpl.findAllTcDirectProjectsForUser("user3").length);
	}



    /**
     * <p>
     * Accuracy test of <code>findFullProjects(Filter filter)</code> method.
     * </p>
     * 
     * <p>
     * Tests for proper retrieval of FullProjects.
     * </p>
     * 
     */
    public void testFindFullProjects_accuracy() throws Exception {
        FullProjectData[] fullProjectDatas = projectServicesImpl.findFullProjects(ProjectFilterUtility
                .buildCategoryIdEqualFilter(1));

        assertEquals("Unable to find FullProjectData properly", 2, fullProjectDatas.length);

        FullProjectData fullProjectData = fullProjectDatas[0];

        assertNotNull("Unable to get a non null FullProjectData", fullProjectData);

        // Tests whether FullProjectData details are accurate.

        assertAccuracyFullProjectData(fullProjectData, 1);
    }

    /**
     * <p>
     * Accuracy test of <code>findProjectsHeaders(Filter filter)</code> method.
     * </p>
     * 
     * <p>
     * Tests for proper retrieval of projects.
     * </p>
     * 
     */
    public void testFindProjectsHeaders_accuracy() {
        assertEquals("Unable to retrieve the projects properly", 2, projectServicesImpl
                .findProjectsHeaders(ProjectFilterUtility.buildCategoryIdEqualFilter(1)).length);
    }

    /**
     * <p>
     * Accuracy test of <code>getFullProjectData(long projectId)</code> method.
     * </p>
     * 
     * <p>
     * Tests for non null retrieval of FullProjectData also tests for proper retrieval of Project details.
     * </p>
     * 
     */
    public void testGetFullProjectData_accuracy() throws Exception {
        FullProjectData fullProjectData = projectServicesImpl.getFullProjectData(1);

        assertNotNull("Unable to get a non null FullProjectData", fullProjectData);

        // Tests whether FullProjectData details are accurate.

        assertAccuracyFullProjectData(fullProjectData, 1);
    }

    /**
     * Helper method used for asserting FullProjectData used in the test cases.
     * 
     * @param fullProjectData
     *            a non null instance of FullProjectData to validate.
     * 
     * @throws Exception
     *             to junit.
     */
    private void assertAccuracyFullProjectData(FullProjectData fullProjectData, int projectId) throws Exception {

        // Tests whether FullProjectData details are accurate.

        // Tests for proper ProjectHeader details
        Project expectedProjectHeader = projectManager.getProject(projectId);
        Project retrievedProjectHeader = fullProjectData.getProjectHeader();
        assertEquals("Unable to get the project header of FullProjectData properly", expectedProjectHeader
                .getProjectCategory().getId(), retrievedProjectHeader.getProjectCategory().getId());

        assertEquals("Unable to get the project header of FullProjectData properly", expectedProjectHeader
                .getProjectCategory().getName(), retrievedProjectHeader.getProjectCategory().getName());

        assertEquals("Unable to get the project header of FullProjectData properly", expectedProjectHeader
                .getProjectCategory().getProjectType().getId(), retrievedProjectHeader.getProjectCategory()
                .getProjectType().getId());

        assertEquals("Unable to get the project header of FullProjectData properly", expectedProjectHeader
                .getProjectCategory().getProjectType().getName(), retrievedProjectHeader.getProjectCategory()
                .getProjectType().getName());

        assertEquals("Unable to get the project header of FullProjectData properly", expectedProjectHeader
                .getProjectStatus().getId(), retrievedProjectHeader.getProjectStatus().getId());

        assertEquals("Unable to get the project header of FullProjectData properly", expectedProjectHeader
                .getProjectStatus().getName(), retrievedProjectHeader.getProjectStatus().getName());

        // Tests for proper resources details
        assertEquals("Unable to get the resource details of FullProjectData properly", 1, fullProjectData
                .getResources().length);

        // Tests for proper team details
        assertEquals("Unable to get the team details of FullProjectData properly", 1,
                fullProjectData.getTeams().length);

        // Tests for proper technology details
        assertEquals("Unable to get the technology details of FullProjectData properly", 2, fullProjectData
                .getTechnologies().length);

    }

}
