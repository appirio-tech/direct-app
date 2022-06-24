/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.accuracytests;

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
 * <p>
 * Mock implementation of ProjectManager used for accuracy test cases.
 * </p>
 *
 * @author kshatriyan
 * @version 1.0
 */
public class MockProjectManager implements ProjectManager {

    /**
     * Represents the status flag for returning the project category.
     */
    private static boolean EMPTY_PROJECT_CATEGORY = false;

    /**
     * Not implemented.
     *
     * @param arg0
     *            Project parameter.
     * @param arg1
     *            String parameter.
     *
     * @throws PersistenceException
     *             Not thrown.
     * @throws ValidationException
     *             Not thrown.
     */
    public void createProject(Project arg0, String arg1) throws PersistenceException, ValidationException {

    }

    /**
     * Returns a new instance of ProjectCategory based on the status flag {@link #EMPTY_PROJECT_CATEGORY}.
     *
     * @return a new instance of ProjectCategory based on the status flag {@link #EMPTY_PROJECT_CATEGORY}.
     *
     * @throws PersistenceException
     *             Not thrown.
     */
    public ProjectCategory[] getAllProjectCategories() throws PersistenceException {
        if (EMPTY_PROJECT_CATEGORY) {
            return new ProjectCategory[0];
        }
        return new ProjectCategory[] { new ProjectCategory(1, "Project Services", new ProjectType(1, "type2")) };
    }

    /**
     * Not implemented.
     *
     * @return null always.
     *
     * @throws PersistenceException
     *             Not thrown.
     */
    public ProjectPropertyType[] getAllProjectPropertyTypes() throws PersistenceException {
        return null;
    }

    /**
     * Not implemented.
     *
     * @return null always.
     *
     * @throws PersistenceException
     *             Not thrown.
     */
    public ProjectStatus[] getAllProjectStatuses() throws PersistenceException {
        return null;
    }

    /**
     * Not implemented.
     *
     * @return null always.
     *
     * @throws PersistenceException
     *             Not thrown.
     */
    public ProjectType[] getAllProjectTypes() throws PersistenceException {
        return null;
    }

    /**
     * Returns a project if the id is 1 or 2. Returns null otherwise.
     *
     * @param arg0
     *            The project id.
     * @return a new instance of project if the id is 1 or 2. Returns null otherwise.
     *
     * @throws PersistenceException
     *             Not thrown.
     */
    public Project getProject(long arg0) throws PersistenceException {
        if (arg0 == 1) {
            Project ret = new Project(1, new ProjectCategory(1, "Project Services", new ProjectType(1, "type1")),
                    new ProjectStatus(1, "active"));
            ret.setProperty("External Reference ID", "1");
            return ret;
            
        } else if (arg0 == 2) {
            Project ret = new Project(2, new ProjectCategory(2, "Project Services", new ProjectType(1, "type1")),
                    new ProjectStatus(2, "inactive"));
            ret.setProperty("External Reference ID", "2");
            return ret;
        }

        return null;

    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            array of project ids.
     * @return null always.
     *
     * @throws PersistenceException
     *             Not thrown.
     */
    public Project[] getProjects(long[] arg0) throws PersistenceException {
        return null;
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            array of project ids.
     * @return null always.
     *
     * @throws PersistenceException
     *             Not thrown.
     */
    public Project[] getUserProjects(long arg0) throws PersistenceException {
        return null;
    }

    public Project[] searchProjects(Filter arg0) throws PersistenceException {
        if (EMPTY_PROJECT_CATEGORY) {
            return new Project[0];
        }
        Project project = new Project(1, new ProjectCategory(1, "Project Services", new ProjectType(1, "type1")),
                new ProjectStatus(1, "active"));
        Project inactiveProject = new Project(2,
                new ProjectCategory(2, "Project Services", new ProjectType(1, "type1")), new ProjectStatus(2,
                        "inactive"));
        return new Project[] { project, inactiveProject };
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            Project parameter
     * @param arg1
     *            String parameter
     * @param arg2
     *            String parameter
     *
     * @throws PersistenceException
     *             Not thrown.
     * @throws ValidationException
     *             Not thrown.
     */
    public void updateProject(Project arg0, String arg1, String arg2) throws PersistenceException,
            ValidationException {

    }

    /**
     * Sets the status of the flag for returning the project category.
     *
     * @param status
     *            Flag to set the field {@link #EMPTY_PROJECT_CATEGORY}.
     *
     */
    public static void setEmptyProjectCategory(boolean status) {
        EMPTY_PROJECT_CATEGORY = status;
    }
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.topcoder.management.project.ProjectManager#getAllTcDirectProjects()
	 */
	public Project[] getAllTcDirectProjects() throws PersistenceException {
		Project project = new Project(1, new ProjectCategory(1,
				"Project Services", new ProjectType(1, "type1")),
				new ProjectStatus(1, "active"));
		project.setTcDirectProjectId(1);
		project.setCreationUser("user");
		Project inactiveProject = new Project(2, new ProjectCategory(2,
				"Project Services", new ProjectType(1, "type1")),
				new ProjectStatus(2, "inactive"));
		inactiveProject.setTcDirectProjectId(2);
		inactiveProject.setCreationUser("user2");
		return new Project[] { project, inactiveProject };

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.topcoder.management.project.ProjectManager#getAllTcDirectProjects
	 * (java.lang.String)
	 */
	public Project[] getAllTcDirectProjects(String operator) throws PersistenceException {
		Project project = new Project(1, new ProjectCategory(1, "Project Services", new ProjectType(1, "type1")),
                new ProjectStatus(1, "active"));
		project.setTcDirectProjectId(1);
		project.setCreationUser("user");
	    Project inactiveProject = new Project(2,
                new ProjectCategory(2, "Project Services", new ProjectType(1, "type1")), new ProjectStatus(2,
                        "inactive"));
     inactiveProject.setTcDirectProjectId(2);
     inactiveProject.setCreationUser("user2");
     if(project.getCreationUser().equals(operator))
    	 return new Project[] { project};
     else if(inactiveProject.getCreationUser().equals(operator))
    	 return new Project[] { inactiveProject};
     else return new Project[0];

	}

}
