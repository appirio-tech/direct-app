/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.accuracytests;

import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.project.accuracytests.persistence.MockProjectPersistence;


/**
 * Accuracy test cases for <code>ProjectManagerImpl</code>'s delegated method. Just verifies correct methods are
 * called.
 *
 * @author skatou
 * @version 1.0
 */
public class ProjectManagerImplDelegateAccuracyTests extends ProjectManagerImplAccuracyTestsHelper {
    /** A <code>Project</code> instance as parameter. */
    private Project project = null;

    /**
     * Sets up the test environment. New instance of <code>Project</code> is created.
     *
     * @throws Exception pass to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        ProjectType type = new ProjectType(1, "name");
        ProjectCategory category = new ProjectCategory(1, "name", type);
        ProjectStatus status = new ProjectStatus(1, "name");
        project = new Project(category, status);
    }

    /**
     * Tests method createProject. Verifies the job is delegated to <code>ProjectPersistence</code>.
     *
     * @throws Exception pass to JUnit.
     */
    public void testCreateProject() throws Exception {
        manager.createProject(project, "operator");
        assertEquals("createProject" + project + "operator", MockProjectPersistence.getLastCalled());
    }

    /**
     * Tests method updateProject. Verifies the job is delegated to <code>ProjectPersistence</code>.
     *
     * @throws Exception pass to JUnit.
     */
    public void testUpdateProject() throws Exception {
        manager.updateProject(project, "reason", "operator");
        assertEquals("updateProject" + project + "reasonoperator", MockProjectPersistence.getLastCalled());
    }

    /**
     * Tests method getProject. Verifies the job is delegated to <code>ProjectPersistence</code>.
     *
     * @throws Exception pass to JUnit.
     */
    public void testGetProject() throws Exception {
        manager.getProject(88);
        assertEquals("getProject88", MockProjectPersistence.getLastCalled());
    }

    /**
     * Tests method getProjects. Verifies the job is delegated to <code>ProjectPersistence</code>.
     *
     * @throws Exception pass to JUnit.
     */
    public void testGetProjects() throws Exception {
        long[] ids = new long[] {88 };
        manager.getProjects(ids);
        assertEquals("getProjects" + ids, MockProjectPersistence.getLastCalled());
    }

    /**
     * Tests method getAllProjectTypes. Verifies the job is delegated to <code>ProjectPersistence</code>.
     *
     * @throws Exception pass to JUnit.
     */
    public void testGetAllProjectTypes() throws Exception {
        manager.getAllProjectTypes();
        assertEquals("getAllProjectTypes", MockProjectPersistence.getLastCalled());
    }

    /**
     * Tests method getAllProjectCategories. Verifies the job is delegated to <code>ProjectPersistence</code>.
     *
     * @throws Exception pass to JUnit.
     */
    public void testGetAllProjectCategories() throws Exception {
        manager.getAllProjectCategories();
        assertEquals("getAllProjectCategories", MockProjectPersistence.getLastCalled());
    }

    /**
     * Tests method getAllProjectStatuses. Verifies the job is delegated to <code>ProjectPersistence</code>.
     *
     * @throws Exception pass to JUnit.
     */
    public void testGetAllProjectStatuses() throws Exception {
        manager.getAllProjectStatuses();
        assertEquals("getAllProjectStatuses", MockProjectPersistence.getLastCalled());
    }

    /**
     * Tests method getAllProjectPropertyTypes. Verifies the job is delegated to <code>ProjectPersistence</code>.
     *
     * @throws Exception pass to JUnit.
     */
    public void testGetAllProjectPropertyTypes() throws Exception {
        manager.getAllProjectPropertyTypes();
        assertEquals("getAllProjectPropertyTypes", MockProjectPersistence.getLastCalled());
    }
}
