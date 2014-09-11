/*
 * Copyright (C) 2008 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.project;

import java.util.List;
import java.util.Map;

import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.model.Project;
import com.topcoder.service.project.entities.ProjectQuestion;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.project.notification.DirectProjectNotification;
import com.topcoder.service.permission.ProjectPermission;
import com.topcoder.service.project.AuthorizationFailedFault;
import com.topcoder.service.project.IllegalArgumentFault;
import com.topcoder.service.project.InvalidBillingAccountForProjectFault;
import com.topcoder.service.project.PersistenceFault;
import com.topcoder.service.project.ProjectCategory;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.project.ProjectNotFoundFault;
import com.topcoder.service.project.ProjectService;
import com.topcoder.service.project.ProjectServiceFault;
import com.topcoder.service.project.ProjectType;
import com.topcoder.service.project.UserNotFoundFault;

/**
 * <p>An interface for the web service implementing unified <code>Facade</code> interface to various service components
 * provided by global <code>TopCoder</code> application.</p>
 *
 * <p>As of this version a facade to <code>Project Service</code> is provided only.</p>
 *
 * <p>
 * Changes in v1.0.2(Cockpit Security Facade V1.0):
 *  - It is not a web-service facade any more.
 *  - All the methods accepts a parameter TCSubject which contains all the security info for current user.
 *    The implementation EJB should use TCSubject and now get these info from the sessionContext.
 *  - Please use the new ProjectServiceFacadeWebService as the facade now. That interface will delegates all the methods
 *    to this interface.
 * </p>
 *
 * <p>
 *     Version 1.0.3 changes:
 *     - add method {@link #updateProject(com.topcoder.security.TCSubject, ProjectData)}
 * </p>
 * <p>
 * Version 1.0.4 (Release Assembly - TC Cockpit Create Project Refactoring Assembly Part Two) change notes:
 *   <ol>
 *       <li>Added {@link #createTopCoderDirectProjectForum(TCSubject, long, Long)} method.</li>
 *       <li>Added {@link #createTCDirectProject(TCSubject, ProjectData, List)} method.</li>
 *   </ol>
 * </p>
 *
 * <p>
 *     Version 2.0 (Module Assembly - TC Cockpit Direct Project Related Services Update and Integration)
 *     <ul>
 *         <li>
 *             Add method {@link #getAllProjectTypes()}
 *         </li>
 *         <li>
 *             Add method {@link #getProjectCategoriesByProjectType(long)}
 *         </li>
 *         <li>
 *             Add method {@link #getBillingAccountsByProject(long)}
 *         </li>
 *         <li>
 *             Add method {@link #getBillingAccountsByClient(long)}
 *         </li>
 *         <li>
 *             Add method {@link #getProjectsByBillingAccount(long)}
 *         </li>
 *         <li>
 *             Add method {@link #addBillingAccountToProject(long, long)}
 *         </li>
 *         <li>
 *             Add method {@link #removeBillingAccountFromProject(long, long)}
 *         </li>
 *         <li>
 *             Add method {@link #getProjectsByClient(long)}
 *         </li>
 *     </ul>
 * </p>
 *
 * <p>
 *     Version 2.1 (Release Assembly - TC Cockpit Project Forum Settings) changes:
 *     <ul>
 *         <li>Add method {@link #getProjectNotifications(com.topcoder.security.TCSubject, long)}</li>
 *         <li>Add method {@link #updateProjectNotifications(com.topcoder.security.TCSubject, long, java.util.List)}</li>
 *     </ul>
 * </p>
 *
 * <p>
 *     Version 2.2 (Release Assembly - TopCoder Cockpit Project Dashboard Project Type and Permission Notifications Integration) changes:
 *     <ul>
 *         <li>Add method {@link #getWatchedUserIdsForProjectForum(com.topcoder.security.TCSubject, java.util.List, long)}</li>
 *     </ul>
 * </p>
 *
 * <p>
 *     Version 2.3(TC Cockpit - Create New Project BackEnd 1.0) change log:
 *     <ul>
 *      <li>
 *      Add a new contract method {@link #getProjectQuestions()} to get all project questions available 
 *      in the persistence.
 *      </li>
 *      <li>
 *       <strong>Thread Safety:</strong> Thread safety of this interface is not changed by this component.
 *      </li>
 *     </ul>
 * </p>
 * <p>
 *     Version 2.4 (Release Assembly - TC Direct Project Forum Configuration Assembly) changes:
 *     <ul>
 *         <li>Changed {@link #createTopCoderDirectProjectForum(TCSubject, long, Long, Map)} and add a new argument to
 *         support project forum configuration.</li>
 *         <li>Changed {@link #createTCDirectProject(TCSubject, ProjectData, List, Map)} to add a new argument.</li>
 *         <li>Added {@link #createProject(TCSubject, ProjectData, Map)} to add a new argument.</li>
 *     </ul>
 * </p>
 * <p>
 *     Version 2.5 (Release Assembly - TC Direct Project Forum Configuration Assembly 2) changes:
 *     <ul>
 *         <li>Added {@link #addTopCoderDirectProjectForum(long, String, String)} to add a direct project forum.</li>
 *         <li>Added {@link #deleteTopCoderDirectProjectForum(long, long)} to delete direct project forums.</li>
 *     </ul>
 * </p>
 * @author isv, GreatKevin, TCSDEVELOPER, duxiaoyang
 * @version 2.5
 */
public interface ProjectServiceFacade {

    /**
     * <p>Creates a project with the given project data.</p>
     *
     * <p>Note, any user can create project and the project will associate with him/her.</p>
     * <p>
     * Update in v1.0.2: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectData
     *            The project data to be created. Must not be null.
     *            The <code>ProjectData.name</code> must not be null/empty.
     *            The <code>ProjectData.projectId</code>, if any, is ignored.
     * @return The project as it was created, with the <code>ProjectData.projectId</code> and <code>ProjectData.userId
     *         </code> set. Will never be null.
     * @throws IllegalArgumentFault if the given <code>ProjectData</code> is illegal.
     * @throws PersistenceFault if a generic persistence error occurs.
     * @see ProjectService#createProject(ProjectData)
     */
    ProjectData createProject(TCSubject tcSubject, ProjectData projectData) throws PersistenceFault, IllegalArgumentFault;
	
	/**
     * <p>Creates a project with the given project data and forum templates.</p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectData
     *            The project data to be created. Must not be null.
     *            The <code>ProjectData.name</code> must not be null/empty.
     *            The <code>ProjectData.projectId</code>, if any, is ignored.
     * @param forums
     *            The list of project forum template configurations.
     * @return The project as it was created, with the <code>ProjectData.projectId</code> and <code>ProjectData.userId
     *         </code> set. Will never be null.
     * @throws IllegalArgumentFault if the given <code>ProjectData</code> is illegal.
     * @throws PersistenceFault if a generic persistence error occurs.
     * @see ProjectService#createProject(ProjectData)
     * @since 2.4
     */
    ProjectData createProject(TCSubject tcSubject, ProjectData projectData, Map<String, String> forums)
            throws PersistenceFault, IllegalArgumentFault;
			
     /**
     * <p>Gets the project data for the project with the given Id.</p>
     *
     * <p>Notes, only associated user can retrieve the specified project, administrator can retrieve any projects.</p>
     * <p>
     * Update in v1.0.2: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectId the ID of the project to be retrieved.
     * @return The project data for the project with the given Id. Will never be null.
     * @throws PersistenceFault if a generic persistence error.
     * @throws ProjectNotFoundFault if no project with the given ID exists.
     * @throws AuthorizationFailedFault if the calling principal is not authorized to retrieve the project.
     * @see ProjectService#getProject(long)
     */
    ProjectData getProject(TCSubject tcSubject, long projectId) throws PersistenceFault, ProjectNotFoundFault, AuthorizationFailedFault;

    /**
     * <p>Gets the project data for all projects of the given user.</p>
     *
     * <p>Notes, only administrator can do this.</p>
     *<p>
     * Update in v1.0.2: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param userId the ID of the user whose projects are to be retrieved.
     * @return The project data for all projects of the given user. The returned collection will not be null or contain
     *         nulls. Will never be empty.
     * @throws UserNotFoundFault if there are no projects linked to the given user.
     * @throws PersistenceFault if a generic persistence error occurs.
     * @throws AuthorizationFailedFault should not be thrown from version 1.1. It is here only to leave the web
     *         interface unchanged.
     * @see ProjectService#getProjectsForUser(long)
     */
    List< ProjectData > getProjectsForUser(TCSubject tcSubject, long userId) throws PersistenceFault, UserNotFoundFault,
                                                               AuthorizationFailedFault;

    /**
     * <p>
     * Updates the project data.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectData The project data to be updated. Must not be null.
     * @throws PersistenceFault if a generic persistence error occurs.
     * @throws ProjectNotFoundFault if no project with the given ID exists.
     * @throws AuthorizationFailedFault if the calling principal is not authorized to retrieve the project.
     * @throws IllegalArgumentFault if the arguments are invalid.
     * @since 1.0.3
     */
    void updateProject(TCSubject tcSubject, ProjectData projectData) throws  PersistenceFault, ProjectNotFoundFault,
                                                                        AuthorizationFailedFault, IllegalArgumentFault;

    /**
     * <p>Gets the project data for all projects viewable from the calling principal.</p>
     *
     * <p>Notes, for user, it will retrieve only the projects associated with him; for administrators, it will retrieve
     * all the existing projects.</p>
     *
     * <p>
     * Updated for Cockpit Project Admin Release Assembly v1.0
     *      - Added check for admin user, if admin user then all projects are loaded else only for the user.
     * </p>
     * <p>
     * Update in v1.0.2: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @return The project data for all projects viewable from the calling principal. The returned collection will not
     *         be null or contain nulls. Possibly empty.
     * @throws PersistenceFault if a generic persistence error occurs.
     * @throws AuthorizationFailedFault if errors occurs during authorization of the caller user.
     * @throws UserNotFoundFault if errors occurs during authorization of the caller user.
     *
     * @see ProjectService#getAllProjects()
     */
    List < ProjectData > getAllProjects(TCSubject tcSubject) throws PersistenceFault, AuthorizationFailedFault, UserNotFoundFault;


    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with
     * projects with the given client from the persistence. If nothing is found,
     * return an empty list.
     * </p>
     *
     * @param client
     *                the given clients to retrieve it's projects. Should not be
     *                null.
     * @return the list of Projects for the given client found in the
     *         persistence. If nothing is found, return an empty list.
     * @throws IllegalArgumentException
     *                 if client is null.
     * @throws EntityNotFoundFault
     *                 if client is not found in the persistence.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    //List<Project> getClientProjectsForClient(Client client) throws DAOFault;

    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with
     * projects with the given user id. If nothing is found, return an empty list.
     * <p>
     * <p>
     * Update in v1.0.2: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @return List of Project, if nothing is found, return an empty string
     * @throws DAOException if any error occurs while performing this operation.
     */
    public List<Project> getClientProjectsByUser(TCSubject tcSubject) throws DAOFault;


    /**
     * <p>Creates the forum for the specified <code>TC Direct</code> project.</p>
     *
     * @param currentUser           a <code>TCSubject</code> representing the current user.
     * @param projectId             a <code>long</code> providing the ID of <code>TC Direct</code> project to create
     *                              forum for.
     * @param tcDirectProjectTypeId a <code>Long</code> referencing the type of <code>TC Direct</code> project. May be
     *                              <code>null</code>.
	 * @param forums                a list of project forum templates configuration.
     * @return a <code>long</code> providing the ID of created forum.
     * @throws ProjectServiceFault if an unexpected error occurs.
     * @since 1.0.4
     */
    public long createTopCoderDirectProjectForum(TCSubject currentUser, long projectId,
                                                 Long tcDirectProjectTypeId, Map<String, String> forums) throws ProjectServiceFault;

    /**
     * <p>Creates the specified <code>TC Direct</code> project with optional permissions granted to users.</p>
     *
     * @param currentUser a <code>TCSubject</code> representing the current user.
     * @param projectData a <code>ProjectData</code> providing the details for project to be created.
     * @param permissions a <code>List</code> providing the permissions for users to be set. May be <code>null</code>.
     * @return a <code>ProjectData</code> providing details for created project.
     * @throws ProjectServiceFault if an unexpected error occurs.
     * @since 1.0.4
     */
    public ProjectData createTCDirectProject(TCSubject currentUser, ProjectData projectData,
                                             List<ProjectPermission> permissions) throws ProjectServiceFault;

    /**
     * <p>Creates the specified <code>TC Direct</code> project with optional permissions granted to users and forum
     * tempaltes.</p>
     * @param currentUser a <code>TCSubject</code> representing the current user.
     * @param projectData a <code>ProjectData</code> providing the details for project to be created.
     * @param permissions a <code>List</code> providing the permissions for users to be set. May be <code>null</code>.
     * @param forums a list of project forum templates configuration.
     * @return a <code>ProjectData</code> providing details for created project.
     * @throws ProjectServiceFault if an unexpected error occurs.
     * @since 2.4
     */
    public ProjectData createTCDirectProject(TCSubject currentUser, ProjectData projectData,
                                             List<ProjectPermission> permissions, Map<String, String> forums) throws ProjectServiceFault;
											 
    /**
     * <p>Updates the project forum watch.</p>
     *
     * @param currentUser a <code>TCSubject</code> representing the current user.
     * @param tcDirectProjectId the project id.
     * @param watching to indicate if you want to set watch or unwatch.
     * @throws ProjectServiceFault if an unexpected error occurs.
     */
    public void updateProjectForumWatch(TCSubject currentUser, long tcDirectProjectId, boolean watching) throws ProjectServiceFault;

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
     * @throws EntityNotFoundFault
     *                 if id is not found in the persistence.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    //Project retrieveClientProjectById(Long id, boolean includeChildren) throws EntityNotFoundFault, DAOFault;

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
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    //List<Project> retrieveClientProjects(boolean includeChildren) throws DAOFault;
    /**
     * <p>
     * Defines the operation that performs the retrieval of an entity using the
     * given id from the persistence.
     * </p>
     *
     * @param id
     *                the identifier of the entity that should be retrieved.
     *                Should be positive and not null.
     * @return the entity with the given id retrieved from the persistence.
     * @throws IllegalArgumentException
     *                 if id <= 0 or id is null.
     * @throws EntityNotFoundFault
     *                 if an entity for the given id is not found in the
     *                 persistence.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    //Project retrieveClientProjectByProjectId(Long id) throws EntityNotFoundFault, DAOFault;

    /**
     * <p>
     * Defines the operation that performs the retrieval of all entities from
     * the persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @return the list of entities found in the persistence. If nothing is
     *         found, return an empty list.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    //List<Project> retrieveAllClientProjects() throws DAOFault;

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
     * @throws EntityNotFoundFault
     *                 if status is not found in the persistence.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    //List<Project> getClientProjectsWithStatus(ProjectStatus status) throws DAOFault;
    /**
     * <p>
     * Defines the operation that performs the retrieval of an entity using the
     * given id from the persistence.
     * </p>
     *
     * @param id
     *                the identifier of the entity that should be retrieved.
     *                Should be positive and not null.
     * @return the entity with the given id retrieved from the persistence.
     * @throws IllegalArgumentException
     *                 if id <= 0 or id is null.
     * @throws EntityNotFoundFault
     *                 if an entity for the given id is not found in the
     *                 persistence.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    //ProjectStatus retrieveProjectStatusById(Long id) throws EntityNotFoundFault, DAOFault;


    /**
     * Gets all the project types.
     *
     * @return all the project types.
     * @throws PersistenceFault if a generic persistence error occurs.
     * @since 2.0
     */
    public List<ProjectType> getAllProjectTypes() throws PersistenceFault;

    /**
     * Gets the project categories by the given project type id.
     *
     * @param projectTypeId the id of the project type.
     * @return a list pf project categories
     * @throws PersistenceFault if a generic persistence error occurs.
     * @since 2.0
     */
    public List<ProjectCategory> getProjectCategoriesByProjectType(long projectTypeId) throws PersistenceFault;

    /**
     * Gets the billing accounts of the given direct project id
     *
     * @param projectId the id of the direct project
     * @return a list of billing accounts of the direct project.
     * @throws PersistenceFault if a generic persistence error occurs.
     * @since 2.0
     */
    public List<com.topcoder.clients.model.Project> getBillingAccountsByProject(long projectId) throws PersistenceFault;

    /**
     * Gets the projects by the billing account id.
     *
     * @param billingAccountId the id of the billing account.
     * @return a list of <code>ProjectData</code>
     * @throws PersistenceFault if a generic persistence error occurs.
     * @since 2.0
     */
    public List<ProjectData> getProjectsByBillingAccount(long billingAccountId) throws PersistenceFault;

    /**
     * Adds the billing account to the project.
     *
     * @param projectId        the id of the direct project
     * @param billingAccountId the id of the billing account
     * @throws InvalidBillingAccountForProjectFault
     *                          if the billing account does not have same client as project's existing billing accounts
     * @throws PersistenceFault if a generic persistence error occurs.
     * @since 2.0
     */
    public void addBillingAccountToProject(long projectId, long billingAccountId) throws InvalidBillingAccountForProjectFault, PersistenceFault;

    /**
     * Removes the billing account from the project.
     *
     * @param projectId        the id of the direct project
     * @param billingAccountId the id of the billing account
     * @throws PersistenceFault if a generic persistence error occurs.
     * @since 2.0
     */
    public void removeBillingAccountFromProject(long projectId, long billingAccountId) throws PersistenceFault;

    /**
     * Gets all the billing accounts by the id of the client
     *
     * @param clientId the id of the client
     * @return a list of billing accounts
     * @throws PersistenceFault if a generic persistence error occurs.
     * @since 2.0
     */
    public List<com.topcoder.clients.model.Project> getBillingAccountsByClient(long clientId) throws PersistenceFault;

    /**
     * Gets the projects of the given client.
     *
     * @param clientId the id of the client.
     * @return a list of projects belong to the client.
     * @throws PersistenceFault if a generic persistence error occurs.
     * @since 2.0
     */
    public List<ProjectData> getProjectsByClient(long clientId) throws PersistenceFault;

    /**
     * Gets the projects notification settings
     *
     * @param tcSubject the TCSubject instance.
     * @param userId the user id.
     * @return the projects notification settings of the user.
     * @throws ProjectServiceFault if there is any error.
     * @since 2.1
     */
    public List<DirectProjectNotification> getProjectNotifications(TCSubject tcSubject, long userId) throws ProjectServiceFault;

    /**
     * Update the project notification settings of the user.
     *
     * @param subject the TCSubject instance.
     * @param userId the user id.
     * @param notifications a list of <code>DirectProjectNotification</code> instances.
     * @throws ProjectServiceFault if there is any error.
     * @since 2.1
     */
    public void updateProjectNotifications(TCSubject subject, long userId, List<DirectProjectNotification> notifications) throws ProjectServiceFault;

    /**
     * Gets the ids of the users who watch the project forum of the specified project id.
     *
     * @param tcSubject the current user performs the operation.
     * @param userIds the list of user ids to check.
     * @param projectId the id of the project.
     * @return the list of user ids who watch the project forum
     * @throws ProjectServiceFault if there is any error.
     * @since 2.2
     */
    public List<Long> getWatchedUserIdsForProjectForum(TCSubject tcSubject, List<Long> userIds, long projectId) throws ProjectServiceFault;
    
    /**
     * This contract method gets all ProjectQuestion instances available in the persistence.
     * @return A list of all the available ProjectQuestion instances in the persistence.
     * @throws PersistenceFault When some error occurred when accessing the persistence.
     * @since 2.3(TC Cockpit- Create New Project BackEnd 1.0)
     */
    public List<ProjectQuestion> getProjectQuestions() throws PersistenceFault;
	
	/**
     * <p>
     * Adds a forum to the existing TopCoder Direct project forum category.
     * </p>
     * @param forumCategoryId the TopCoder Direct project forum category id.
     * @param forumName the name of the forum to be added.
     * @param forumDescription the description of the forum to be added.
     * @return the id of the added forum.
     * @throws ProjectServiceFault if an unexpected error occurs.
     * @since 2.5
     */
    public long addTopCoderDirectProjectForum(long forumCategoryId, String forumName, String forumDescription)
            throws ProjectServiceFault;

    /**
     * <p>
     * Deletes an existing TopCoder Direct project forum.
     * </p>
     * @param forumCategoryId the id of the forum category
     * @param forumId the id of the forum to be deleted.
     * @throws ProjectServiceFault if an unexpected error occurs.
     * @since 2.5
     */
    public void deleteTopCoderDirectProjectForum(long forumCategoryId, long forumId) throws ProjectServiceFault;
}