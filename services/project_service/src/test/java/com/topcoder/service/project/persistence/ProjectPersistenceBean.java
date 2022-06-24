/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project.persistence;

import java.util.List;

import javax.ejb.Stateless;

import com.topcoder.service.project.ConfigurationException;
import com.topcoder.service.project.PersistenceException;
import com.topcoder.service.project.Project;
import com.topcoder.service.project.ProjectNotFoundException;
import com.topcoder.service.project.ProjectPersistence;

/**
 * <p>
 * This is the stateless session bean which makes use of <code>{@link JPAProjectPersistence}</code> class. It is just
 * used as testing purpose.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@Stateless
public class ProjectPersistenceBean implements ProjectPersistenceLocal, ProjectPersistenceRemote {

    /**
     * <p>
     * Represents the <code>ProjectPersistence</code> instance.
     * </p>
     */
    private ProjectPersistence projectPersistence;

    /**
     * <p>
     * Creates a <code>ProjectPersistenceBean</code> instance.
     * </p>
     *
     * @throws ConfigurationException
     *             If any problem to create the JPAProjectPersistence instance.
     */
    public ProjectPersistenceBean() throws ConfigurationException {
        projectPersistence = new JPAProjectPersistence();
    }

    /**
     * <p>
     * Creates the given project.
     * </p>
     *
     * @param project
     *            The project. Must not be null. The name, description must also not be null/empty. The ProjectId , if
     *            any, is ignored. CreateDate must not be null. The competitions set, if present, must not contain nulls
     *            or contain Competition elements whose project element is not the given arg.
     * @return The project as it was created, with the projectId set.
     * @throws PersistenceException
     *             If a generic persistence error.
     * @throws IllegalArgumentException
     *             If the arg given was illegal.
     */
    public Project createProject(Project project) throws PersistenceException {
        return projectPersistence.createProject(project);
    }

    /**
     * <p>
     * Deletes the project with the given project ID.
     * </p>
     *
     * @param projectId
     *            The ID of the project to delete.
     * @return Whether the project was found, and thus deleted.
     * @throws PersistenceException
     *             If a generic persistence error.
     */
    public boolean deleteProject(long projectId) throws PersistenceException {
        return projectPersistence.deleteProject(projectId);
    }

    /**
     * <p>
     * Retrieves all the projects.
     * </p>
     *
     * @return All the projects. The returned collection will not be null or contain nulls. Possibly empty.
     * @throws PersistenceException
     *             If a generic persistence error.
     */
    public List<Project> getAllProjects() throws PersistenceException {
        return projectPersistence.getAllProjects();
    }

    /**
     * <p>
     * Retrieves the project with the given project ID.
     * </p>
     *
     * @param projectId
     *            The ID of the project to retrieve.
     * @return The project with the given ID.
     * @throws PersistenceException
     *             If a generic persistence error.
     * @throws ProjectNotFoundException
     *             If no project with the given ID exists.
     */
    public Project getProject(long projectId) throws PersistenceException {
        return projectPersistence.getProject(projectId);
    }

    /**
     * <p>
     * Retrieves all the projects for the given user.
     * </p>
     *
     * @param userId
     *            The ID of the user whose projects are to be retrieved.
     * @return all the projects for the given user. The returned collection will not be null or contain nulls. Possibly
     *         empty.
     * @throws PersistenceException
     *             If a generic persistence error.
     */
    public List<Project> getProjectsForUser(long userId) throws PersistenceException {
        return projectPersistence.getProjectsForUser(userId);
    }

    /**
     * <p>
     * Updates the given project.
     * </p>
     *
     * @param project
     *            The project to update. Must not be null. The name,description must also not be empty. The ProjectId
     *            must not be null. The competitions set, if present, must not contain nulls or contain Competition
     *            elements whose project element is not the given arg. For all properties of the project which are null
     *            (apart from ProjectId), those properties will not be updated.
     * @throws PersistenceException
     *             If a generic persistence error.
     * @throws IllegalArgumentException
     *             If that the arg given was illegal.
     * @throws ProjectNotFoundException
     *             If no project with the given ID exists.
     */
    public void updateProject(Project project) throws PersistenceException {
        projectPersistence.updateProject(project);
    }

}
