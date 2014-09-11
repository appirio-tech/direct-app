/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.failuretests;

import java.util.Date;

import com.topcoder.date.workdays.DefaultWorkdaysFactory;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectFilterUtility;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.service.ProjectServicesException;
import com.topcoder.project.service.ProjectServicesFactory;
import com.topcoder.project.service.ejb.ProjectServicesBean;
import com.topcoder.search.builder.filter.Filter;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>ProjectServicesBean</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 * @since 1.1
 */
public class ProjectServicesBeanFailureTest extends TestCase {
    /**
     * <p>
     * Represents the <code>ProjectServicesBean</code> instance for test.
     * </p>
     */
    private ProjectServicesBean projectServicesBean;

    /**
     * <p>
     * Represents the <code>Filter</code> instance for test.
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
        FailureTestHelper.releaseConfigs();
        FailureTestHelper.loadConfigs("configuration.xml");
        projectServicesBean = new ProjectServicesBean();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    protected void tearDown() throws Exception {
        FailureTestHelper.releaseConfigs();
        projectServicesBean = null;
    }

    /**
     * <p>
     * Failure test for the method <code>findActiveProjects</code> with ProjectServicesException
     * occurs, ProjectServicesException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testFindActiveProjectsWithPSE() throws Exception {
        preExceptionCondition();

        try {
            projectServicesBean.findActiveProjects();
            fail("ProjectServicesException should be thrown.");
        } catch (ProjectServicesException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>findActiveProjectsHeaders</code> with
     * ProjectServicesException occurs, ProjectServicesException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testFindActiveProjectsHeadersWithPSE() throws Exception {
        preExceptionCondition();

        try {
            projectServicesBean.findActiveProjectsHeaders();
            fail("ProjectServicesException should be thrown.");
        } catch (ProjectServicesException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>findFullProjects</code> with filter is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testFindFullProjectsWithNullFilter() throws Exception {
        try {
            projectServicesBean.findFullProjects(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>findFullProjects</code> with ProjectServicesException
     * occurs, ProjectServicesException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testFindFullProjectsWithPSE() throws Exception {
        preExceptionCondition();

        try {
            projectServicesBean.findFullProjects(filter);
            fail("ProjectServicesException should be thrown.");
        } catch (ProjectServicesException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>findFullProjects</code> with filter is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testFindProjectsHeadersWithNullFilter() throws Exception {
        try {
            projectServicesBean.findProjectsHeaders(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>findFullProjects</code> with ProjectServicesException
     * occurs, ProjectServicesException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testFindProjectsHeadersWithPSE() throws Exception {
        preExceptionCondition();

        try {
            projectServicesBean.findProjectsHeaders(filter);
            fail("ProjectServicesException should be thrown.");
        } catch (ProjectServicesException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>getFullProjectData(projectId)</code> method with the projectId is
     * negative, IllegalArgumentException is expected.
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
     *             to JUnit.
     */
    public void testGetFullProjectDataWithPSE() throws Exception {
        preExceptionCondition();

        try {
            projectServicesBean.getFullProjectData(1);
            fail("ProjectServicesException should be thrown.");
        } catch (ProjectServicesException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createProject</code> with ProjectServicesException
     * occurs, ProjectServicesException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
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
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>updateProject</code> with ProjectServicesException
     * occurs, ProjectServicesException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
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
            // expected
        }
    }

    /**
     * <p>
     * Prepares the condition to create <code>ProjectServicesException</code> while getting
     * <code>ProjectServices</code> instance.
     * </p>
     */
    private void preExceptionCondition() {
        // set ProjectServicesFactory to null.
        FailureTestHelper.setPrivateField(ProjectServicesFactory.class, ProjectServicesFactory.class,
                "projectServices", null);
        // set projectServicesFactoryNamespace to an unknown namespace
        FailureTestHelper.setPrivateField(ProjectServicesBean.class, projectServicesBean,
                "projectServicesFactoryNamespace", "unknown");
    }
}