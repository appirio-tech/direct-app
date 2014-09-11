/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads.accuracytests;

import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectManager;
import com.topcoder.management.project.ProjectPropertyType;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.project.ValidationException;
import com.topcoder.search.builder.filter.Filter;

/**
 * A mock implementation of ProjectManager for testing.
 *
 * @author kshatriyan
 * @version 1.0
 */
public class MockProjectManager implements ProjectManager {

    /**
     * Not used.
     *
     * @param arg0
     *            project
     * @param arg1
     *            desc
     * @throws PersistenceException
     *             not thrown
     * @throws ValidationException
     *             not thrown
     */
    public void createProject(Project arg0, String arg1) throws PersistenceException, ValidationException {
    }

    /**
     * Not used.
     *
     * @param arg0
     *            project
     * @param arg1
     *            desc
     * @param arg2
     *            desc
     * @throws PersistenceException
     *             not thrown
     * @throws ValidationException
     *             not thrown
     */
    public void updateProject(Project arg0, String arg1, String arg2) throws PersistenceException,
            ValidationException {

    }

    /**
     * A mock implementation.
     *
     * @param arg0
     *            project id
     * @return project
     * @throws PersistenceException
     *             if the flag is set
     */
    public Project getProject(long arg0) throws PersistenceException {
        ProjectType type = new ProjectType(1, "java");
        ProjectCategory category = new ProjectCategory(1, "accuracy", type);
        ProjectStatus status = new ProjectStatus(1, "accuracy");
        Project project = new Project(arg0, category, status);
        project.setProperty("Winner External Reference ID", AccuracyHelper.USER_ID);
        project.setProperty("Allow multiple submissions", "true");
        return project;
    }

    /**
     * Not used.
     *
     * @param arg0
     *            project id
     * @return always null
     * @throws PersistenceException
     *             not thrown
     */
    public Project[] getProjects(long[] arg0) throws PersistenceException {
        return null;
    }

    /**
     * Not used.
     *
     * @param arg0
     *            filter
     * @return always null
     * @throws PersistenceException
     *             not thrown
     */
    public Project[] searchProjects(Filter arg0) throws PersistenceException {
        return null;
    }

    /**
     * Not used.
     *
     * @param arg0
     *            project id
     * @return always null
     * @throws PersistenceException
     *             not thrown
     */
    public Project[] getUserProjects(long arg0) throws PersistenceException {
        return null;
    }

    /**
     * Not used.
     *
     * @return always null
     * @throws PersistenceException
     *             not thrown
     */
    public ProjectType[] getAllProjectTypes() throws PersistenceException {
        return null;
    }

    /**
     * Not used.
     *
     * @return always null
     * @throws PersistenceException
     *             not thrown
     */
    public ProjectCategory[] getAllProjectCategories() throws PersistenceException {
        return null;
    }

    /**
     * Not used.
     *
     * @return always null
     * @throws PersistenceException
     *             not thrown
     */
    public ProjectStatus[] getAllProjectStatuses() throws PersistenceException {
        return null;
    }

    /**
     * Not used.
     *
     * @return always null
     * @throws PersistenceException
     *             not thrown
     */
    public ProjectPropertyType[] getAllProjectPropertyTypes() throws PersistenceException {
        return null;
    }
}
