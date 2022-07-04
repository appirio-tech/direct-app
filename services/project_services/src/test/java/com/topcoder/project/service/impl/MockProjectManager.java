/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.impl;

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
 * This is a mock implementation of <code>ProjectManager</code>.
 * </p>
 * <p>
 * It is only used for test.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockProjectManager implements ProjectManager {

    /**
     * <p>
     * Represents an instance of ProjectCategory.
     * </p>
     */
    private ProjectCategory category;

    /**
     * <p>
     * Constructs an instance.
     * </p>
     */
    public MockProjectManager() {
        category = new ProjectCategory(1, "Java", new ProjectType(1, "type1"));
    }

    /**
     * Create the project in the database using the given project instance. The project information
     * is stored to 'project' table, while its properties are stored in 'project_info' table. The
     * project's associating scorecards are stored in 'project_scorecard' table. For the project,
     * its properties and associating scorecards, the operator parameter is used as the
     * creation/modification user and the creation date and modification date will be the current
     * date time when the project is created. The given project instance will be validated before
     * persisting.
     *
     * @param project
     *            The project instance to be created in the database.
     * @param operator
     *            The creation user of this project.
     * @throws IllegalArgumentException
     *             if any input is null or the operator is empty string.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     * @throws ValidationException
     *             if error occurred while validating the project instance.
     */
    public void createProject(Project project, String operator) throws PersistenceException, ValidationException {
        project.setId(1);
    }

    /**
     * Update the given project instance into the database. The project information is stored to
     * 'project' table, while its properties are stored in 'project_info' table. The project's
     * associating scorecards are stored in 'project_scorecard' table. All related items in these
     * tables will be updated. If items are removed from the project, they will be deleted from the
     * persistence. Likewise, if new items are added, they will be created in the persistence. For
     * the project, its properties and associating scorecards, the operator parameter is used as the
     * modification user and the modification date will be the current date time when the project is
     * updated. The given project instance will be validated before persisting.
     *
     * @param project
     *            The project instance to be updated into the database.
     * @param reason
     *            The update reason. It will be stored in the persistence for future references.
     * @param operator
     *            The modification user of this project.
     * @throws IllegalArgumentException
     *             if any input is null or the operator is empty string.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     * @throws ValidationException
     *             if error occurred while validating the project instance.
     */
    public void updateProject(Project project, String reason, String operator) throws PersistenceException,
            ValidationException {
    }

    /**
     * Retrieves the project instance from the persistence given its id. The project instance is
     * retrieved with its related items, such as properties and scorecards.
     *
     * @return The project instance.
     * @param id
     *            The id of the project to be retrieved.
     * @throws IllegalArgumentException
     *             if the input id is less than or equal to zero.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public Project getProject(long id) throws PersistenceException {
        if (id == 11) {
            throw new PersistenceException("Persistence error occurred.");
        }
        Project ret = new Project(1, category, new ProjectStatus(1, "active"));
        if (id == 12) {
            ret.setProperty("External Reference ID", "12");
        } else {
            ret.setProperty("External Reference ID", "1");
        }
        return ret;
    }

    /**
     * <p>
     * Retrieves an array of project instance from the persistence given their ids. The project
     * instances are retrieved with their properties.
     * </p>
     *
     * @param ids
     *            The ids of the projects to be retrieved.
     * @return An array of project instances.
     * @throws IllegalArgumentException
     *             if ids is null or empty or contain an id that is less than or equal to zero.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public Project[] getProjects(long[] ids) throws PersistenceException {
        return null;
    }

    /**
     * <p>
     * Searches project instances using the given filter parameter. The filter parameter decides the
     * condition of searching. This method use the Search Builder component to perform searching.
     * The search condition can be the combination of any of the followings:
     * </p>
     * <ul>
     * <li>Project type id</li>
     * <li>Project type name</li>
     * <li>Project category id</li>
     * <li>Project category name</li>
     * <li>Project status id</li>
     * <li>Project status name</li>
     * <li>Project property name</li>
     * <li>Project property value</li>
     * </ul>
     * The filter is created using the ProjectFilterUtility class. This class provide method to
     * create filter of the above condition and any combination of them.
     *
     * @return An array of project instance as the search result.
     * @param filter
     *            The filter to search for projects.
     * @throws IllegalArgumentException
     *             if the filter is null.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public Project[] searchProjects(Filter filter) throws PersistenceException {
        Project project1 = new Project(1, category, new ProjectStatus(1, "active"));
        Project project2 = new Project(3, category, new ProjectStatus(1, "active"));
        Project project3 = new Project(2, category, new ProjectStatus(2, "inactive"));
        return new Project[] {project1, project2, project3};
    }

    /**
     * Gets the projects associated with an external user id. The user id is defined as a property
     * of of a resource that belong to the project. The resource property name is 'External
     * Reference ID'. and the property value is the given user id converted to string.
     *
     * @return An array of project instances associated with the given user id.
     * @param user
     *            The user id to search for projects.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public Project[] getUserProjects(long user) throws PersistenceException {
        return null;
    }

    /**
     * Gets an array of all project types in the persistence. The project types are stored in
     * 'project_type_lu' table.
     *
     * @return An array of all project types in the persistence.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public ProjectType[] getAllProjectTypes() throws PersistenceException {
        return null;
    }

    /**
     * Gets an array of all project categories in the persistence. The project categories are stored
     * in 'project_category_lu' table.
     *
     * @return An array of all project categories in the persistence.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public ProjectCategory[] getAllProjectCategories() throws PersistenceException {
        return new ProjectCategory[] {category};
    }

    /**
     * Gets an array of all project statuses in the persistence. The project statuses are stored in
     * 'project_status_lu' table.
     *
     * @return An array of all project statuses in the persistence.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public ProjectStatus[] getAllProjectStatuses() throws PersistenceException {
        return null;
    }

    /**
     * Gets an array of all project property type in the persistence. The project property types are
     * stored in 'project_info_type_lu' table.
     *
     * @return An array of all scorecard assignments in the persistence.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public ProjectPropertyType[] getAllProjectPropertyTypes() throws PersistenceException {
        return null;
    }

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.topcoder.management.project.ProjectManager#getAllTcDirectProjects()
	 */
	public Project[] getAllTcDirectProjects() throws PersistenceException {
		Project project1 = new Project(1, category, new ProjectStatus(1,
				"active"));
		project1.setTcDirectProjectId(1);
		Project project2 = new Project(3, category, new ProjectStatus(1,
				"active"));
		project2.setTcDirectProjectId(2);
		Project project3 = new Project(2, category, new ProjectStatus(2,
				"inactive"));
		project3.setTcDirectProjectId(1);
		return new Project[] { project1, project2, project3 };
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.topcoder.management.project.ProjectManager#getAllTcDirectProjects
	 * (java.lang.String)
	 */
	public Project[] getAllTcDirectProjects(String user)
			throws PersistenceException {
		Project project1 = new Project(1, category, new ProjectStatus(1,
				"active"));
		project1.setTcDirectProjectId(1);
		project1.setCreationUser("user");
		Project project2 = new Project(3, category, new ProjectStatus(1,
				"active"));
		project2.setTcDirectProjectId(2);
		project2.setCreationUser("user2");
		Project project3 = new Project(2, category, new ProjectStatus(2,
				"inactive"));
		project3.setTcDirectProjectId(1);
		project3.setCreationUser("user");
		if("user".equals(user)) {
			return new Project[] { project1, project3 };
		} else if("user2".equals(user)) {
			return new Project[] {  project2};
		} else {
			return new Project[0];
		}

	}
}
