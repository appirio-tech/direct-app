/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.stresstests;

import java.io.File;
import java.util.Date;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.service.impl.ProjectServicesImpl;

import junit.framework.Test;
import junit.framework.TestSuite;


/**
 * <p>
 * Stress test cases for <code>ProjectServicesImpl</code> class' new method added in version 1.1.
 * </p>
 *
 * @author woodatxc
 * @version 1.1
 */
public class ProjectServicesImplNewMethodsStressTests extends BaseStressTests {

    /**
     * <p>
     * Represents an instance of <code>ProjectServicesImpl</code> for test.
     * </p>
     */
    private ProjectServicesImpl services;

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
        services = new ProjectServicesImpl();
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
        services = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ProjectServicesImplNewMethodsStressTests.class);
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
            services.createProject(projectHeader, projectPhases, projectResources, operator);
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
            services.updateProject(projectHeader, projectHeaderReason, projectPhases, projectResources, operator);
        }

        this.endTest("ExpressionParser's updateProject(Project, String, Project, Resource[], String)", MAX_TIME);
    }
}
