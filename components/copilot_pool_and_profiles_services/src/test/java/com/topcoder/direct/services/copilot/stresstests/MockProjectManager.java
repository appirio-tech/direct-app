package com.topcoder.direct.services.copilot.stresstests;

import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectManager;
import com.topcoder.management.project.ProjectPropertyType;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.project.ValidationException;
import com.topcoder.search.builder.filter.Filter;

public class MockProjectManager implements ProjectManager {

    public void createProject(Project project, String operator) throws PersistenceException, ValidationException {

    }

    public ProjectCategory[] getAllProjectCategories() throws PersistenceException {
        ProjectCategory[] projectCategory = new ProjectCategory[1];
        projectCategory[0] = new ProjectCategory(1l, "n", new ProjectType(1l, "n"));
        return projectCategory;
    }

    public ProjectPropertyType[] getAllProjectPropertyTypes() throws PersistenceException {

        return null;
    }

    public ProjectStatus[] getAllProjectStatuses() throws PersistenceException {

        return null;
    }

    public ProjectType[] getAllProjectTypes() throws PersistenceException {

        return null;
    }

    public Project getProject(long id) throws PersistenceException {

        return null;
    }

    public Project[] getProjects(long[] ids) throws PersistenceException {

        return null;
    }

    public Project[] getProjectsByCreateDate(int days) throws PersistenceException {

        return null;
    }

    public Project[] getUserProjects(long user) throws PersistenceException {

        return null;
    }

    public Project[] searchProjects(Filter filter) throws PersistenceException {

        return null;
    }

    public void updateProject(Project project, String reason, String operator) throws PersistenceException,
        ValidationException {

    }

}
