/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import java.util.List;

/**
 * <p>
 * This interface lays the contract for persisting <code>{@link Project}</code> entities. It consists of CRUD
 * operation methods for the <code>{@link Project}</code> entity. The 6 methods define correspond to the similar
 * methods defined in the <code>{@link ProjectService}</code> contract. Instances of this objects are likely to be
 * used to plug in persistence logic into <code>{@link ProjectService}</code> implementations.
 * </p>
 * <p>
 * Implementations will provide the logic to persist Project entities to a particular persistence medium - say databases
 * or XML files.
 * </p>
 * <p>
 * <b>Notes</b>, Implementations MUST provide for a no-args constructor so that instances can be constructed easily
 * through reflection.
 * </p>
 * <p>
 * <b>Thread Safety</b>: Implementations must be thread safe.
 * </p>
 *
 * @author humblefool, FireIce
 * @version 1.0
 */
public interface ProjectPersistence {
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
    Project createProject(Project project) throws PersistenceException;

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
    Project getProject(long projectId) throws PersistenceException;

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
    List<Project> getProjectsForUser(long userId) throws PersistenceException;

    /**
     * <p>
     * Retrieves all the projects.
     * </p>
     *
     * @return All the projects. The returned collection will not be null or contain nulls. Possibly empty.
     * @throws PersistenceException
     *             If a generic persistence error.
     */
    List<Project> getAllProjects() throws PersistenceException;

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
    void updateProject(Project project) throws PersistenceException;

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
    boolean deleteProject(long projectId) throws PersistenceException;
}
