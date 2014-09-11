/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.ejb;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Date;

import com.topcoder.date.workdays.DefaultWorkdaysFactory;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectFilterUtility;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.project.service.ProjectServicesException;
import com.topcoder.project.service.ProjectServicesFactory;
import com.topcoder.project.service.impl.TestHelper;
import com.topcoder.search.builder.filter.Filter;

import junit.framework.TestCase;

/**
 * <p>
 * Tests functionality and error cases of <code>ProjectServicesBean</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProjectServicesBeanTest extends TestCase {
    /**
     * <p>
     * Represents an instance of <code>ProjectServicesBean</code> for test.
     * </p>
     */
    private ProjectServicesBean projectServicesBean;

    /**
     * <p>
     * Represents an instance of <code>Filter</code> for test.
     * </p>
     */
    private final Filter filter = ProjectFilterUtility.buildCategoryIdEqualFilter(1);

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "configuration.xml");
        projectServicesBean = new ProjectServicesBean();

        Method initializeMethod = projectServicesBean.getClass().getDeclaredMethod("initialize", new Class[0]);
        initializeMethod.setAccessible(true);
        initializeMethod.invoke(projectServicesBean);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
        projectServicesBean = null;
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ProjectServicesBean</code>.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCtorAccuracy() throws Exception {
        assertNotNull("Failed to create ProjectServicesBean instance.", projectServicesBean);
    }

    /**
     * <p>
     * Accuracy test for the method <code>findActiveProjects</code>. Verifies the active projects
     * are found properly.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testFindActiveProjectsAccuracy() throws Exception {
        FullProjectData[] fullProjectDatas = projectServicesBean.findActiveProjects();
        assertNotNull("The active projects should be found properly.", fullProjectDatas);
        assertEquals("The active projects should be found properly.", 3, fullProjectDatas.length);
    }

    /**
     * <p>
     * Failure test for the method <code>findActiveProjects</code> with ProjectServicesException
     * occurs, ProjectServicesException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testFindActiveProjectsWithPSE() throws Exception {
        preExceptionCondition();

        try {
            projectServicesBean.findActiveProjects();
            fail("ProjectServicesException should be thrown.");
        } catch (ProjectServicesException e) {
            // good
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>findActiveProjectsHeaders</code>. Verifies the projects
     * are found properly.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testFindActiveProjectsHeadersAccuracy() throws Exception {
        Project[] projects = projectServicesBean.findActiveProjectsHeaders();
        assertNotNull("The active projects should be found properly.", projects);
        assertEquals("The active projects should be found properly.", 3, projects.length);
    }

    /**
     * <p>
     * Failure test for the method <code>findActiveProjectsHeaders</code> with
     * ProjectServicesException occurs, ProjectServicesException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testFindActiveProjectsHeadersWithPSE() throws Exception {
        preExceptionCondition();

        try {
            projectServicesBean.findActiveProjectsHeaders();
            fail("ProjectServicesException should be thrown.");
        } catch (ProjectServicesException e) {
            // good
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>findFullProjects</code>.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testFindFullProjectsAccuracy() throws Exception {
        FullProjectData[] fullProjectDatas = projectServicesBean.findFullProjects(filter);
        assertNotNull("The active projects should be found properly.", fullProjectDatas);
        assertEquals("The active projects should be found properly.", 3, fullProjectDatas.length);
    }

    /**
     * <p>
     * Failure test for the method <code>findFullProjects</code> with filter is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testFindFullProjectsWithNullFilter() throws Exception {
        try {
            projectServicesBean.findFullProjects(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>findFullProjects</code> with ProjectServicesException
     * occurs, ProjectServicesException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testFindFullProjectsWithPSE() throws Exception {
        preExceptionCondition();

        try {
            projectServicesBean.findFullProjects(filter);
            fail("ProjectServicesException should be thrown.");
        } catch (ProjectServicesException e) {
            // good
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>findProjectsHeaders</code>.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testFindProjectsHeadersAccuracy() throws Exception {
        Project[] projects = projectServicesBean.findProjectsHeaders(filter);
        assertNotNull("The active projects should be found properly.", projects);
        assertEquals("The active projects should be found properly.", 3, projects.length);
    }

    /**
     * <p>
     * Failure test for the method <code>findFullProjects</code> with filter is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testFindProjectsHeadersWithNullFilter() throws Exception {
        try {
            projectServicesBean.findProjectsHeaders(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>findFullProjects</code> with ProjectServicesException
     * occurs, ProjectServicesException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testFindProjectsHeadersWithPSE() throws Exception {
        preExceptionCondition();

        try {
            projectServicesBean.findProjectsHeaders(filter);
            fail("ProjectServicesException should be thrown.");
        } catch (ProjectServicesException e) {
            // good
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>getFullProjectData</code>.
     * </p>
     * <p>
     * Tests it for accuracy, a non-null instance of FullProjectData should be returned.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetFullProjectDataAccuracy() throws Exception {
        FullProjectData fullData = projectServicesBean.getFullProjectData(1);
        assertEquals("Project id mismatched.", 1, fullData.getProjectHeader().getId());
        assertEquals("Project category mismatched.", 1, fullData.getProjectHeader().getProjectCategory().getId());
        assertEquals("Project status mismatched.", 1, fullData.getProjectHeader().getProjectStatus().getId());
        assertEquals("There should be two resources.", 2, fullData.getResources().length);
        assertEquals("There should be only one team.", 1, fullData.getTeams().length);
        assertEquals("There should be two technologies.", 2, fullData.getTechnologies().length);
    }

    /**
     * <p>
     * Accuracy test for <code>getFullProjectData(projectId)</code> method.
     * </p>
     * <p>
     * Tests it against negative project id. expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetFullProjectDataWithNegativeProjectId() throws Exception {
        try {
            projectServicesBean.getFullProjectData(-1);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for the method <code>getFullProjectData</code> with ProjectServicesException
     * occurs, ProjectServicesException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetFullProjectDataWithPSE() throws Exception {
        preExceptionCondition();

        try {
            projectServicesBean.getFullProjectData(1);
            fail("ProjectServicesException should be thrown.");
        } catch (ProjectServicesException e) {
            // good
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>createProject</code>.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProjectAccuracy() throws Exception {
        com.topcoder.management.project.Project projectHeader = new com.topcoder.management.project.Project(
                new ProjectCategory(123, "projectCategory", new ProjectType(1, "projectType")), new ProjectStatus(2,
                        "projectStatus"));

        com.topcoder.project.phases.Project projectPhases = new com.topcoder.project.phases.Project(new Date(),
                new DefaultWorkdaysFactory().createWorkdaysInstance());
        Phase phase = new Phase(projectPhases, 1);
        projectPhases.addPhase(phase);

        Resource resource = new Resource();
        resource.setResourceRole(new ResourceRole());
        Resource[] projectResources = new Resource[] {resource};

        projectServicesBean.createProject(projectHeader, projectPhases, projectResources, "operator");

        long projectHeaderId = projectHeader.getId();
        // verify the projectHeader.id is set properly.
        assertTrue("The project should be created properly.", projectHeaderId > 0);
        // verify the projectPhases is set to projectHeader.id
        assertEquals("The project should be created properly.", projectHeaderId, projectPhases.getId());

        // verify the projectPhases.phases.ids are set to 0 (id not set).
        assertEquals("The project should be created properly.", 0, phase.getId());
        // verify the projectPhases.phases.projects are set to projectHeader.id
        assertEquals("The project should be created properly.", projectHeaderId, phase.getProject().getId());

        // verify the projectResources.resources.projects are set to projectHeader.id
        assertTrue("The project should be created properly.", projectHeaderId == resource.getProject());
    }

    /**
     * <p>
     * Failure test for the method <code>createProject</code> with ProjectServicesException
     * occurs, ProjectServicesException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProjectWithPSE() throws Exception {
        com.topcoder.management.project.Project projectHeader = new com.topcoder.management.project.Project(
                new ProjectCategory(123, "projectCategory", new ProjectType(1, "projectType")), new ProjectStatus(2,
                        "projectStatus"));

        com.topcoder.project.phases.Project projectPhases = new com.topcoder.project.phases.Project(new Date(),
                new DefaultWorkdaysFactory().createWorkdaysInstance());
        Phase phase = new Phase(projectPhases, 1);
        projectPhases.addPhase(phase);

        Resource resource = new Resource();
        resource.setResourceRole(new ResourceRole());
        Resource[] projectResources = new Resource[] {resource};

        preExceptionCondition();

        try {
            projectServicesBean.createProject(projectHeader, projectPhases, projectResources, "operator");
            fail("ProjectServicesException should be thrown.");
        } catch (ProjectServicesException e) {
            // good
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>updateProject</code>. Verifies the project is updated
     * properly.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProjectAccuracy() throws Exception {
        com.topcoder.management.project.Project projectHeader = new com.topcoder.management.project.Project(
                new ProjectCategory(123, "projectCategory", new ProjectType(1, "projectType")), new ProjectStatus(2,
                        "projectStatus"));

        com.topcoder.project.phases.Project projectPhases = new com.topcoder.project.phases.Project(new Date(),
                new DefaultWorkdaysFactory().createWorkdaysInstance());
        Phase phase = new Phase(projectPhases, 1);
        projectPhases.addPhase(phase);

        Resource resource = new Resource();
        resource.setResourceRole(new ResourceRole());
        Resource[] projectResources = new Resource[] {resource};

        long projectHeaderId = 1;
        projectHeader.setId(projectHeaderId);
        projectPhases.setId(projectHeaderId);
        resource.setProject(projectHeaderId);

        phase.setId(123);
        resource.setPhase(123L);
        resource.getResourceRole().setPhaseType(123L);

        projectServicesBean.updateProject(projectHeader, "The project is changed", projectPhases, projectResources,
                "admin");
    }

    /**
     * <p>
     * Failure test for the method <code>updateProject</code> with ProjectServicesException
     * occurs, ProjectServicesException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    /**
     * <p>
     * Failure test for the method <code>updateProject</code> with ProjectServicesException
     * occurs, ProjectServicesException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProjectWithPSE() throws Exception {
        com.topcoder.management.project.Project projectHeader = new com.topcoder.management.project.Project(
                new ProjectCategory(123, "projectCategory", new ProjectType(1, "projectType")), new ProjectStatus(2,
                        "projectStatus"));

        com.topcoder.project.phases.Project projectPhases = new com.topcoder.project.phases.Project(new Date(),
                new DefaultWorkdaysFactory().createWorkdaysInstance());
        Phase phase = new Phase(projectPhases, 1);
        projectPhases.addPhase(phase);

        Resource resource = new Resource();
        resource.setResourceRole(new ResourceRole());
        Resource[] projectResources = new Resource[] {resource};

        preExceptionCondition();

        try {
            projectServicesBean.updateProject(projectHeader, "The project is changed", projectPhases,
                    projectResources, "admin");
            fail("ProjectServicesException should be thrown.");
        } catch (ProjectServicesException e) {
            // good
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>getSessionContext</code>.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetSessionContextAccuracy() throws Exception {
        assertNull("The sessionContext should be null.", projectServicesBean.getSessionContext());
    }

    /**
     * <p>
     * Prepares the condition to create <code>ProjectServicesException</code> while getting
     * <code>ProjectServices</code> instance.
     * </p>
     */
    private void preExceptionCondition() {
        // set ProjectServicesFactory to null.
        TestHelper.setPrivateField(ProjectServicesFactory.class, ProjectServicesFactory.class, "projectServices",
                null);
        // set projectServicesFactoryNamespace to an unknown namespace
        TestHelper.setPrivateField(ProjectServicesBean.class, projectServicesBean, "projectServicesFactoryNamespace",
                "unknown");
    }
}