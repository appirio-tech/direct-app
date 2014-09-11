/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.stresstests;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Date;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectFilterUtility;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.service.ProjectServicesFactory;
import com.topcoder.project.service.ejb.ProjectServicesBean;
import com.topcoder.search.builder.filter.Filter;

import junit.framework.Test;
import junit.framework.TestSuite;


/**
 * <p>
 * Stress test cases for <code>ProjectServicesBean</code> class.
 * </p>
 *
 * @author woodatxc
 * @version 1.1
 */
public class ProjectServicesBeanStressTests extends BaseStressTests {

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
     * Setup test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator
                + "stresstest_configfiles" + File.separator + "configuration.xml");
        projectServicesBean = new ProjectServicesBean();

        // inject the session context
        Field sessionContext = ProjectServicesBean.class.getDeclaredField("sessionContext");
        sessionContext.setAccessible(true);
        sessionContext.set(projectServicesBean, new MockSessionContext());
        // inject the project services factory namespace
        Field factoryNamespace = ProjectServicesBean.class.getDeclaredField("projectServicesFactoryNamespace");
        factoryNamespace.setAccessible(true);
        factoryNamespace.set(projectServicesBean, "com.topcoder.project.service.ProjectServicesFactory");
        // inject the log name
        Field logName = ProjectServicesBean.class.getDeclaredField("logName");
        logName.setAccessible(true);
        logName.set(projectServicesBean, "default");
        // reset the project services factory
        Field field = ProjectServicesFactory.class.getDeclaredField("projectServices");
        field.setAccessible(true);
        field.set(ProjectServicesFactory.class, null);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
        projectServicesBean = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ProjectServicesBeanStressTests.class);
    }

    /**
     * <p>
     * Tests <code>findActiveProjects()</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testFindActiveProjects() throws Exception {
        this.beginTest();

        for (int i = 0; i < RUN_TIMES; i++) {
            projectServicesBean.findActiveProjects();
        }

        this.endTest("ExpressionParser's findActiveProjects()", MAX_TIME);
    }

    /**
     * <p>
     * Tests <code>findActiveProjectsHeaders()</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testFindActiveProjectsHeaders() throws Exception {
        this.beginTest();

        for (int i = 0; i < RUN_TIMES; i++) {
            projectServicesBean.findActiveProjectsHeaders();
        }

        this.endTest("ExpressionParser's findActiveProjectsHeaders()", MAX_TIME);
    }

    /**
     * <p>
     * Tests <code>findFullProjects(Filter filter)</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testFindFullProjects() throws Exception {
        this.beginTest();

        for (int i = 0; i < RUN_TIMES; i++) {
            projectServicesBean.findFullProjects(filter);
        }

        this.endTest("ExpressionParser's findFullProjects(Filter filter)", MAX_TIME);
    }

    /**
     * <p>
     * Tests <code>findProjectsHeaders(Filter filter)</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testFindProjectsHeaders() throws Exception {
        this.beginTest();

        for (int i = 0; i < RUN_TIMES; i++) {
            projectServicesBean.findProjectsHeaders(filter);
        }

        this.endTest("ExpressionParser's findProjectsHeaders(Filter filter)", MAX_TIME);
    }

    /**
     * <p>
     * Tests <code>getFullProjectData(long projectId)</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetFullProjectData() throws Exception {
        this.beginTest();

        for (int i = 0; i < RUN_TIMES; i++) {
            projectServicesBean.getFullProjectData(1);
        }

        this.endTest("ExpressionParser's getFullProjectData(long projectId)", MAX_TIME);
    }

    /**
     * <p>
     * Tests <code>createProject(Project, Project, Resource[], String)</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateProject() throws Exception {
        this.beginTest();

        Project projectHeader = new Project(1, new ProjectCategory(1, "dev", new ProjectType(1, "Java")),
                new ProjectStatus(1, "submission"));
        com.topcoder.project.phases.Project projectPhases = new com.topcoder.project.phases.Project(new Date(),
                new DefaultWorkdays());
        projectPhases.addPhase(new Phase(projectPhases, 1));
        Resource rs = new Resource();
        rs.setResourceRole(new ResourceRole());
        Resource[] projectResources = new Resource[]{rs};
        String operator = "topcoder";

        for (int i = 0; i < RUN_TIMES; i++) {
            projectServicesBean.createProject(projectHeader, projectPhases, projectResources, operator);
        }

        this.endTest("ExpressionParser's createProject(Project, Project, Resource[], String)", MAX_TIME);
    }

    /**
     * <p>
     * Tests <code>updateProject(Project, String, Project, Resource[], String)</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProject() throws Exception {
        this.beginTest();

        Project projectHeader = new Project(1, new ProjectCategory(1, "dev", new ProjectType(1, "Java")),
                new ProjectStatus(1, "submission"));
        String projectHeaderReason = "Some thing new comes.";
        com.topcoder.project.phases.Project projectPhases = new com.topcoder.project.phases.Project(new Date(),
                new DefaultWorkdays());
        projectPhases.setId(1);
        Phase phase = new Phase(projectPhases, 1);
        phase.setId(1);
        projectPhases.addPhase(phase);
        Resource rs = new Resource();
        ResourceRole role = new ResourceRole();
        rs.setResourceRole(role);
        rs.setProject(new Long(1));
        rs.setPhase(new Long(1));
        Resource[] projectResources = new Resource[]{rs};
        String operator = "topcoder";

        for (int i = 0; i < RUN_TIMES; i++) {
            projectServicesBean.updateProject(
                    projectHeader, projectHeaderReason, projectPhases, projectResources, operator);
        }

        this.endTest("ExpressionParser's updateProject(Project, String, Project, Resource[], String)", MAX_TIME);
    }
}
