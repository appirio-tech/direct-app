/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao;

import java.util.List;

import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;

/**
 * <p>
 * This interface represents the ProjectStatusDAO business interface.
 * </p>
 * <p>
 * This interface defines the specific methods available for the
 * ProjectStatusDAO business interface: get the projects with the corresponding
 * status.
 * </p>
 * <p>
 * See base interface for other available operations.
 * </p>
 * <p>
 * <strong>THREAD SAFETY:</strong> Implementations of this interface should be
 * thread safe.
 * </p>
 *
 * @author Mafy, TCSDEVELOPER
 * @version 1.0
 */
public interface ProjectStatusDAO extends GenericDAO<ProjectStatus, Long> {
    /**
     * <p>
     * This static final String field represents the 'BEAN_NAME' property of the
     * ProjectStatusDAO business interface. Represents the EJB session bean
     * name.
     * </p>
     * <p>
     * It is initialized to a default value: "ProjectStatusDAOBean" String
     * during runtime.
     * </p>
     */
    public static final String BEAN_NAME = "ProjectStatusDAOBean";

    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with
     * projects with the given status from the persistence. If nothing is found,
     * return an empty list.
     * </p>
     *
     * @param status
     *                the given project status to retrieve it's projects. Should
     *                not be null.
     * @return the list of Projects for the given project status found in the
     *         persistence. If nothing is found, return an empty list.
     * @throws IllegalArgumentException
     *                 if status is null.
     * @throws EntityNotFoundException
     *                 if status is not found in the persistence.
     * @throws DAOException
     *                 if any error occurs while performing this operation.
     */
    public List<Project> getProjectsWithStatus(ProjectStatus status)
        throws DAOException;
}
