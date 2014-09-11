/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.accuracytests;

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
 * Mock for testing!
 *
 * @author onsky
 * @version 1.0
  */
public class MockProjectManager implements ProjectManager {
    /**
     * Mock for testing!
     *
     * @param arg0 Mock for testing!
     * @param arg1 Mock for testing!
     *
     * @throws PersistenceException Mock for testing!
     * @throws ValidationException Mock for testing!
     */
    public void createProject(Project arg0, String arg1)
        throws PersistenceException, ValidationException {
    }

    /**
     * Mock for testing!
     *
     * @return Mock for testing!
     *
     * @throws PersistenceException Mock for testing!
     */
    public ProjectCategory[] getAllProjectCategories()
        throws PersistenceException {
        return null;
    }

    /**
     * Mock for testing!
     *
     * @return Mock for testing!
     *
     * @throws PersistenceException Mock for testing!
     */
    public ProjectPropertyType[] getAllProjectPropertyTypes()
        throws PersistenceException {
        return null;
    }

    /**
     * Mock for testing!
     *
     * @return Mock for testing!
     *
     * @throws PersistenceException Mock for testing!
     */
    public ProjectStatus[] getAllProjectStatuses() throws PersistenceException {
        return null;
    }

    /**
     * Mock for testing!
     *
     * @return Mock for testing!
     *
     * @throws PersistenceException Mock for testing!
     */
    public ProjectType[] getAllProjectTypes() throws PersistenceException {
        return null;
    }

    /**
     * Mock for testing!
     *
     * @param arg0 Mock for testing!
     *
     * @return Mock for testing!
     *
     * @throws PersistenceException Mock for testing!
     */
    public Project getProject(long arg0) throws PersistenceException {
        return null;
    }

    /**
     * Mock for testing!
     *
     * @param arg0 Mock for testing!
     *
     * @return Mock for testing!
     *
     * @throws PersistenceException Mock for testing!
     */
    public Project[] getProjects(long[] arg0) throws PersistenceException {
        return new Project[] {  };
    }

    /**
     * Mock for testing!
     *
     * @param arg0 Mock for testing!
     *
     * @return Mock for testing!
     *
     * @throws PersistenceException Mock for testing!
     */
    public Project[] getProjectsByCreateDate(int arg0)
        throws PersistenceException {
        return null;
    }

    /**
     * Mock for testing!
     *
     * @param arg0 Mock for testing!
     *
     * @return Mock for testing!
     *
     * @throws PersistenceException Mock for testing!
     */
    public Project[] getUserProjects(long arg0) throws PersistenceException {
        return null;
    }

    /**
     * Mock for testing!
     *
     * @param arg0 Mock for testing!
     *
     * @return Mock for testing!
     *
     * @throws PersistenceException Mock for testing!
     */
    public Project[] searchProjects(Filter arg0) throws PersistenceException {
        return null;
    }

    /**
     * Mock for testing!
     *
     * @param arg0 Mock for testing!
     * @param arg1 Mock for testing!
     * @param arg2 Mock for testing!
     *
     * @throws PersistenceException Mock for testing!
     * @throws ValidationException Mock for testing!
     */
    public void updateProject(Project arg0, String arg1, String arg2)
        throws PersistenceException, ValidationException {
    }
}
