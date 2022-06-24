/*
 * Copyright (C) 2006 - 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectPersistence;
import com.topcoder.management.project.ProjectPropertyType;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;

/**
 * This TestCase demonstrates the usage of this component.
 *
 * @author urtks, TCSDEVELOPER
 * @version 1.2
 * @since 1.0
 */
public class DemoTest extends TestCase {
    /**
     * Represents the <code>InformixProjectPersistence</code> instance which is used to test the functionality of
     * <code>AbstractInformixProjectPersistence</code>.
     *
     * @since 1.2
     */
    private AbstractInformixProjectPersistence persistence;

    /**
     * Aggregates all tests in this class.
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(DemoTest.class);
    }

    /**
     * Sets up the test environment. The configuration will be loaded.
     *
     * @throws Exception
     *             throw any exception to JUnit
     * @since 1.0
     */
    protected void setUp() throws Exception {
        TestHelper.prepareConfig();

        persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        TestHelper.prepareData(persistence);
    }

    /**
     * Clean up the test environment. The configuration will be unloaded.
     *
     * @throws Exception
     *             throw any exception to JUnit
     * @since 1.0
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();

        TestHelper.clearData(persistence);
    }

    /**
     * This method demonstrates the method to create a InformixProjectPersistence.
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testCreation() throws Exception {
        // create the instance with the given namespace
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");
    }

    /**
     * This method demonstrates the management of projects.
     *
     * @throws Exception
     *             throw any exception to JUnit
     * @since 1.0
     */
    public void testManageProject() throws Exception {
        // get the sample project object
        Project project = TestHelper.getSampleProject1();

        persistence.createProject(project, "user");

        // first create an instance of InformixProjectPersistence class
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        // get all project categories from the persistence
        ProjectCategory[] projectCategories = persistence.getAllProjectCategories();

        // get all project statuses from the persistence
        ProjectStatus[] projectStatuses = persistence.getAllProjectStatuses();

        // project types can also be retrieved from the persistence, each
        // project type can contains 0-n project category.
        ProjectType[] projectTypes = persistence.getAllProjectTypes();

        // get all project property types from the persistence.
        ProjectPropertyType[] propertyTypes = persistence.getAllProjectPropertyTypes();

        // create the project using a project category and a project status. The
        // project id will be zero, which mean it isn't persisted
        project = TestHelper.getSampleProject1();

        // persist the project instance with the operator name "admin"
        // the operator will be the creation/modification of this project
        // instance
        persistence.createProject(project, "admin");

        // after creation, new values will be set to the project instance such
        // as project id, creation user, creation date, modification user,
        // modification date.

        // set new project category to the project instance
        project.setProjectCategory(projectCategories[1]);

        // update project with the operator "programmer"
        persistence.updateProject(project, "update reason", "programmer");

        // finally, we can get the project from the persistence.
        Project project1 = persistence.getProject(1);

        // we can also get a set of projects from the persistence in one method.
        Project[] projects = persistence.getProjects(new long[]{1, 2, 3, 4});
    }
}
