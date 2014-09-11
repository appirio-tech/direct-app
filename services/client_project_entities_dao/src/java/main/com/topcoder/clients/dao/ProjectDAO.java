/*
 * Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao;

import java.util.List;

import javax.ejb.Remote;

import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectContestFee;

/**
 * <p>
 * This interface represents the ProjectDAO business interface.
 * </p>
 * <p>
 * This interface defines the specific methods available for the ProjectDAO
 * business interface: retrieve project by id and retrieve all projects.
 * </p>
 * <p>
 * Change note for Configurable Contest Fees v1.0 Assembly: Adds contest fee related functions:
 * <ul>
 *   <li>searchProjectsByClientName</li>
 *   <li>searchProjectsByProjectName</li>
 *   <li>saveContestFees</li>
 *   <li>getContestFeesByProject</li>
 * </ul>
 * </p>
 * <p>
 * See base interface for other available operations.
 * </p>
 *
 * <p>
 * Version 1.2 (Module Assembly - TC Cockpit Direct Project Related Services Update and Integration)
 * - Add method {@link #getProjectsByIds(java.util.List)}
 * </p>
 * <p>
 * <strong>THREAD SAFETY:</strong> Implementations of this interface should be
 * thread safe.
 * </p>
 *
 * @author Mafy, flying2hk, TCSDEVELOPER
 * @version 1.2
 */
@Remote
public interface ProjectDAO extends GenericDAO<Project, Long> {
    /**
     * <p>
     * This static final String field represents the 'BEAN_NAME' property of the
     * ProjectDAO business interface. Represents the EJB session bean name.
     * </p>
     * <p>
     * It is initialized to a default value: "ProjectDAOBean" String during
     * runtime.
     * </p>
     */
    public static final String BEAN_NAME = "ProjectDAOBean";

    /**
     * <p>
     * Defines the operation that performs the retrieval of a project using the
     * given id from the persistence. If include children is true return the
     * Project.childProjects list too; otherwise the list should not be
     * returned.
     * </p>
     *
     * @param id
     *                the identifier of the Project that should be retrieved.
     *                Should be positive and not null.
     * @param includeChildren
     *                the flag that mention if the Project.childrenProjects list
     *                should be returned or not.
     * @return the Project with the given id retrieved from the persistence.
     * @throws IllegalArgumentException
     *                 if id <= 0 or id is null.
     * @throws EntityNotFoundException
     *                 if id is not found in the persistence.
     * @throws DAOException
     *                 if any error occurs while performing this operation.
     */
    public Project retrieveById(Long id, boolean includeChildren)
        throws EntityNotFoundException, DAOException;

    /**
     * <p>
     * Defines the operation that performs the retrieval of all projects from
     * the persistence. If include children is true return the
     * Project.childProjects list too; otherwise the list should not be
     * returned. If nothing is found, return an empty list.
     * </p>
     *
     * @param includeChildren
     *                the flag that mention if the Project.childrenProjects list
     *                should be returned or not.
     * @return the list of Projects found in the persistence. If nothing is
     *         found, return an empty list.
     * @throws DAOException
     *                 if any error occurs while performing this operation.
     */
    public List<Project> retrieveAll(boolean includeChildren)
        throws DAOException;

    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with
     * projects with the given user id. If nothing is found, return an empty list.
     * <p>
     * @param username the user name
     * @return List of Project, if nothing is found, return an empty string
     * @throws DAOException if any error occurs while performing this operation.
     */
    public List<Project> getProjectsByUser(String username) throws DAOException;


     /**
     * <p>
     * Defines the operation that performs the retrieval of all projects from
     * the persistence. If include children is true return the
     * Project.childProjects list too; otherwise the list should not be
     * returned. If nothing is found, return an empty list.
     * </p>
     *
     * @return the list of Projects found in the persistence. If nothing is
     *         found, return an empty list.
     * @throws DAOException
     *                 if any error occurs while performing this operation.
     */
    public List<Project> retrieveAllProjectsOnly() throws DAOException;

    /**
     * Gets all contest fees by project id.
     *
     * @param projectId
     *            the project id
     * @return the list of project contest fees for the given project id
     *
     * @throws DAOException
     *             if any persistence or other error occurs
     *
     * @since Configurable Contest Fees v1.0 Assembly
     */
    public List<ProjectContestFee> getContestFeesByProject(long projectId)
            throws DAOException;

    /**
     * Saves contest fees. It will refresh contest fees for the given project.
     *
     * @param contestFees
     *            the contest fees
     * @param projectId
     *            the project id
     *
     * @throws IllegalArgumentException
     *             if the contestFees is null or the associated project id is
     *             not equal to the one given
     * @throws DAOException
     *             if any persistence or other error occurs
     *
     * @since Configurable Contest Fees v1.0 Assembly
     */
    public void saveContestFees(List<ProjectContestFee> contestFees, long projectId) throws DAOException;

    /**
     * Searches projects by project name. The name search is case insensitive
     * and also allows for partial name search. The name doesn't allow wildcard
     * characters: *, %. If it is null or empty, all projects will be returned.
     *
     * @param projectName
     *            the project name
     * @return projects matched with the project name
     *
     * @throws IllegalArgumentException
     *             if the project name contains wildcard character: *, %
     * @throws DAOException
     *             if any persistence or other error occurs
     *
     * @since Configurable Contest Fees v1.0 Assembly
     */
    public List<Project> searchProjectsByProjectName(String projectName) throws DAOException;

    /**
     * Searches projects by client name. The name search is case insensitive and
     * also allows for partial name search. The name doesn't allow wildcard
     * characters: *, %. If it is null or empty, all projects will be returned.
     *
     * @param clientName
     *            the client name
     * @return projects matched with the client name
     *
     * @throws IllegalArgumentException
     *             if the client name contains wildcard character: *, %
     * @throws DAOException
     *             if any persistence or other error occurs
     *
     * @since Configurable Contest Fees v1.0 Assembly
     */
    public List<Project> searchProjectsByClientName(String clientName) throws DAOException;

        /**
     * <p>
     * Check if user has permission on the client project.
     * <p>
     *
     * @param username  the user name
     * @param projectId client project id
     *
     * @return true/false
     * @throws DAOException
     *             if any error occurs while performing this operation.
     */
    public boolean checkClientProjectPermission(String username, long projectId) throws DAOException;

    /**
     * <p>
     * Check if user has permission on the po number.
     * <p>
     *
     * @param username  the user name
     * @param poNumber po number
     *
     * @return true/false
     * @throws DAOException
     *             if any error occurs while performing this operation.
     */
    public boolean checkPoNumberPermission(String username, String poNumber) throws DAOException;

    /**
     * <p>
     * Adds the user with given user name to the billing projects that are specified with project IDs.
     * </p>
     * <p>
     * Note that if the user doesn't exist, a new record will be created.
     * </p>
     *
     * @param userName
     *            the user name.
     * @param billingProjectIds
     *            the IDs of the projects to which the user is added
     * @throws IllegalArgumentException
     *             if userName is null/empty, or addUserToBillingProjects is null, or any project ID in
     *             addUserToBillingProjects array is &lt;= 0.
     * @throws DAOException
     *             if any error occurs while performing this operation.
     * @since 1.1
     */
    public void addUserToBillingProjects(String userName, long[] billingProjectIds) throws DAOException;

    /**
     * <p>
     * Removes the user with given user name from the billing projects that are specified with project IDs.
     * </p>
     * <p>
     * Note that if the user doesn't exist, nothing will happen.
     * </p>
     *
     * @param userName
     *            the user name.
     * @param billingProjectIds
     *            the IDs of the projects from which the user is removed
     * @throws DAOException
     * @throws IllegalArgumentException
     *             if userName is null/empty, or addUserToBillingProjects is null, or any project ID in
     *             addUserToBillingProjects array is &lt;= 0.
     * @throws DAOException
     *             if any error occurs while performing this operation.
     * @since 1.1
     */
    public void removeUserFromBillingProjects(String userName, long[] billingProjectIds) throws DAOException;


    /**
     * <p>
     * Get all projects associated with the given client id.
     * </p>
     *
     * @param clientId
     *            the client ID.
     * @return a list of projects which are associated with the given client id, if no project is found empty list will
     *         be returned.
     * @throws IllegalArgumentException
     *             if clientId is &lt;= 0.
     * @throws DAOException
     *             if any error occurs while performing this operation.
     * @since 1.1
     */
    public List<Project> getProjectsByClientId(long clientId) throws DAOException;

    /**
     * <p>
     * Get all project associated with the given id.
     * </p>
     *
     * @param projectId
     *            the project ID.
     * @return project which are associated with the given id, if no project is found null will
     *         be returned.
     * @throws IllegalArgumentException
     *             if clientId is &lt;= 0.
     * @throws DAOConfigurationException
     *             if the configured entityManager is invalid (invalid means null here).
     * @throws DAOException
     *             if any error occurs while performing this operation.
     * @since 1.1
     */
    @SuppressWarnings("unchecked")
    public Project getProjectById(long projectId) throws DAOException;

    /**
     * Gets the billing accounts by a list of billing account ids.
     *
     * @param projectIds a list of billing account ids.
     * @return the list <code>Project</code> instance retrieved.
     * @throws DAOException if there is any error.
     * @since 1.2
     */
    @SuppressWarnings("unchecked")
    public List<Project> getProjectsByIds(List<Long> projectIds) throws DAOException;
}
