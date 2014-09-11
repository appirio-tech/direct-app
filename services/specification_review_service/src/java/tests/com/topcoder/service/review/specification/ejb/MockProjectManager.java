/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.specification.ejb;

import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectManager;
import com.topcoder.management.project.ProjectPropertyType;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;

import com.topcoder.search.builder.filter.Filter;


/**
 * A mock implementation of ProjectManager for testing purpose.
 *
 * @author myxgyy
 * @version 1.0
 */
public class MockProjectManager implements ProjectManager {
    /**
     * Not implemented.
     *
     * @param arg0 project
     * @param arg1 desc
     */
    public void createProject(Project arg0, String arg1) {
    }

    /**
     * Not implemented.
     *
     * @param arg0 project
     * @param arg1 desc
     * @param arg2 desc
     */
    public void updateProject(Project arg0, String arg1, String arg2) {
    }

    /**
     * A mock implementation.
     *
     * @param arg0 project id
     *
     * @return project
     */
    public Project getProject(long arg0) {
        ProjectType type = new ProjectType(1, ".Net");
        ProjectCategory category = new ProjectCategory(1, "test", type);
        ProjectStatus status = new ProjectStatus(1, "testing");
        Project project = new Project(arg0, category, status);

        return project;
    }

    /**
     * Not implemented.
     *
     * @param arg0 project id
     *
     * @return always null
     */
    public Project[] getProjects(long[] arg0) {
        return null;
    }

    /**
     * Not implemented.
     *
     * @param creationDate creation date
     *
     * @return always returns empty array
     */
    public Project[] getProjectsByCreateDate(int creationDate) {
        return new Project[0];
    }

    /**
     * A mock implementation.
     *
     * @param arg0 filter
     *
     * @return project array with three elements.
     */
    public Project[] searchProjects(Filter arg0) {
        ProjectType type = new ProjectType(1, ".Net");
        ProjectCategory category = new ProjectCategory(1, "test", type);
        ProjectStatus status = new ProjectStatus(1, "testing");
        Project project1 = new Project(1, category, status);
        Project project2 = new Project(2, category, status);
        Project project3 = new Project(3, category, status);

        return new Project[] {project1, project2, project3};
    }

    /**
     * Not implemented.
     *
     * @param arg0 project id
     *
     * @return always null
     */
    public Project[] getUserProjects(long arg0) {
        return null;
    }

    /**
     * Not implemented.
     *
     * @return always null
     */
    public ProjectType[] getAllProjectTypes() {
        return null;
    }

    /**
     * Not implemented.
     *
     * @return always null
     */
    public ProjectCategory[] getAllProjectCategories() {
        return null;
    }

    /**
     * Not implemented.
     *
     * @return always null
     */
    public ProjectStatus[] getAllProjectStatuses() {
        return null;
    }

    /**
     * Not implemented.
     *
     * @return always null
     */
    public ProjectPropertyType[] getAllProjectPropertyTypes() {
        return null;
    }
}
