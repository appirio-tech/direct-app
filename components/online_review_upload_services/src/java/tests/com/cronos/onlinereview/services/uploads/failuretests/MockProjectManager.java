/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads.failuretests;

import com.cronos.onlinereview.services.uploads.TestHelper;
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
 * A mock implementation of ProjectManager for testing purpose.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockProjectManager implements ProjectManager {
    /**
     * A static state variable for the mock.
     */
    private static int state = 0;

    /**
     * A flag to indicate whether to throw error.
     */
    private static boolean throwError = false;

    /**
     * Not implemented.
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
     * Not implemented.
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
     * A mock implementation. It will throw exception if the throwError flag is set.
     *
     * @param arg0
     *            project id
     * @return project
     * @throws PersistenceException
     *             if the flag is set
     */
    public Project getProject(long arg0) throws PersistenceException {
        if (isThrowError()) {
            throw new PersistenceException("Mock");
        }
        if (getState() == 0) {
            ProjectType type = new ProjectType(1, ".Net");
            ProjectCategory category = new ProjectCategory(1, "test", type);
            ProjectStatus status = new ProjectStatus(1, "testing");
            Project project = new Project(arg0, category, status);
            project.setProperty("Winner External Reference ID", TestHelper.USER_ID);
            return project;
        }
        return null;
    }

    /**
     * Not implemented.
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
     * Not implemented.
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
     * Not implemented.
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
     * Not implemented.
     *
     * @return always null
     * @throws PersistenceException
     *             not thrown
     */
    public ProjectType[] getAllProjectTypes() throws PersistenceException {
        return null;
    }

    /**
     * Not implemented.
     *
     * @return always null
     * @throws PersistenceException
     *             not thrown
     */
    public ProjectCategory[] getAllProjectCategories() throws PersistenceException {
        return null;
    }

    /**
     * Not implemented.
     *
     * @return always null
     * @throws PersistenceException
     *             not thrown
     */
    public ProjectStatus[] getAllProjectStatuses() throws PersistenceException {
        return null;
    }

    /**
     * Not implemented.
     *
     * @return always null
     * @throws PersistenceException
     *             not thrown
     */
    public ProjectPropertyType[] getAllProjectPropertyTypes() throws PersistenceException {
        return null;
    }

    /**
     * Sets the state.
     *
     * @param state
     *            the state to set
     */
    static void setState(int state) {
        MockProjectManager.state = state;
    }

    /**
     * Gets the state.
     *
     * @return the state
     */
    static int getState() {
        return state;
    }

    /**
     * Sets the throwError.
     *
     * @param throwError
     *            the throwError to set
     */
    static void setThrowError(boolean throwError) {
        MockProjectManager.throwError = throwError;
    }

    /**
     * Gets the throwError.
     *
     * @return the throwError
     */
    static boolean isThrowError() {
        return throwError;
    }

}
