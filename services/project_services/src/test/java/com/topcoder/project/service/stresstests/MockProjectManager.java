/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.stresstests;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.management.project.ConfigurationException;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectManager;
import com.topcoder.management.project.ProjectPersistence;
import com.topcoder.management.project.ProjectPropertyType;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.project.ProjectValidator;
import com.topcoder.management.project.ValidationException;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This is a mock implementation of {@link ProjectManager}.
 * 
 * @author stylecheck
 * @version 1.1
 * @since 1.0
 */
public class MockProjectManager implements ProjectManager {
    /**
     * The Instance variable.
     */
    private final ProjectPersistence persistence;

    /**
     * The project validator instance. It is initialized in the constructor using reflection and never changed
     * after that. It is used to validate projects before create/update them.
     */
    private final ProjectValidator validator;

    /**
     * Create a new instance of ProjectManagerImpl using the default configuration namespace. First it load the
     * 'PersistenceClass' and 'PersistenceNamespace' properties to initialize the persistence plug-in
     * implementation. The 'PersistenceNamespace' is optional and if it does not present, value of
     * 'PersistenceClass' property will be used. Then it load the 'SearchBuilderNamespace' property to initialize
     * SearchBuilder component.
     * <p>
     * 
     * @throws ConfigurationException
     *             if error occurs while loading configuration settings, or required configuration parameter is
     *             missing.
     */
    public MockProjectManager() throws ConfigurationException {
        // do nothing
        persistence = null;
        validator = null;
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
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
        validator.validateProject(project);
        persistence.createProject(project, operator);
    }

    /**
     * *
     * <p>
     * Mock Implementation.
     * </p>
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
        validator.validateProject(project);
        persistence.updateProject(project, reason, operator);
    }

    /**
     * *
     * <p>
     * Mock Implementation.
     * </p>
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
        Project ret = new Project(1, new ProjectCategory(1, "Project1", new ProjectType(1, "design")), new ProjectStatus(
                1, "active"));
        ret.setProperty("External Reference ID", "1");
        return ret;
    }

    /**
     * <p>
     * Mock Implementation.
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
        List arrays = new ArrayList();
        return (Project[]) arrays.toArray(new Project[arrays.size()]);
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
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
        // use persistence to get projects.
        return (getProjects(new long[] {}));

    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @return An array of project instances associated with the given user id.
     * @param user
     *            The user id to search for projects.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public Project[] getUserProjects(long user) throws PersistenceException {
        return searchProjects(null);
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @return An array of all project types in the persistence.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public ProjectType[] getAllProjectTypes() throws PersistenceException {
        return persistence.getAllProjectTypes();
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @return An array of all project categories in the persistence.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public ProjectCategory[] getAllProjectCategories() throws PersistenceException {
        return persistence.getAllProjectCategories();
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @return An array of all project statuses in the persistence.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public ProjectStatus[] getAllProjectStatuses() throws PersistenceException {
        return persistence.getAllProjectStatuses();
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @return An array of all property assignments in the persistence.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public ProjectPropertyType[] getAllProjectPropertyTypes() throws PersistenceException {
        return persistence.getAllProjectPropertyTypes();
    }
}
