/*
 * Copyright (C) 2008 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import java.util.List;

import com.topcoder.clients.model.Client;
import com.topcoder.service.project.entities.ProjectQuestion;
import com.topcoder.security.TCSubject;

/**
 * <p>
 * This interface is annotated as a web service and defines the contract for the service methods which must be
 * implemented. The service methods provide CRUD functionality for the <code>ProjectData</code> entity.
 * </p>
 *
 * <p>
 * The contract lays down 6 methods - 4 simple CRUD methods and 2 additional retrieve methods that allow retrieval
 * by user, and retrieval of all projects.
 * </p>
 *
 * <p>
 * Implementations will provide the logic to implement the service methods. This may be done through a container
 * managed <code>EntityManager</code>.
 * </p>
 *
 * <p>
 *     <strong>Version History:</strong>
 *     <ul>
 *         <li>Introduced since version 1.0.</li>
 *         <li>Modified in version 1.1:</li>
 *             <ul>
 *               <li>The implementation of <code>getProjectsForUser()</code> method should no longer perform
 *               role-based authorization in code. It should rely instead (as always did) on EJB declarative
 *               security.</li>
 *             </ul>
 *         <li>Modified in version 1.1.1:</li>
 *             <ul>
 *               <li>It is not web-service any more.</li>
 *               <li>createProject, getProject, updateProject, deleteProject accepts a new parameter TCSubject
 *                   which is used to get the security info for current user.
 *               </li>
 *             </ul>
 *     </ul>
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
 *    Version 2.1 (TC Cockpit Create New Project Back End v1.0) Change log:
 *    <ul>
 *      <li>Add method {@link #getProjectQuestions()}</li>
 *    </ul>
 * </p>
 *
 * <p>
 *     Version 2.2 (TopCoder Direct - Add Group Permission Logic and project full permission checking)
 *     <ul>
 *         <li>Added method {@link #getClientByProject(long)}</li>
 *     </ul>
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: Implementations must be thread safe.
 * </p>
 *
 * @author humblefool, FireIce, ThinMan, leo_lol, GreatKevin
 * @version 2.2
 * @since 1.0
 */
public interface ProjectService {

    /**
     * <p>
     * Creates a project with the given project data.
     * </p>
     *
     * <p>
     * Note, any user can create project and the project will associate with him/her.
     * </p>
     * <p>
     * Update in v1.1.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectData
     *            The project data to be created. Must not be null.
     *            The <code>ProjectData.name</code> must not be null/empty.
     *            The <code>ProjectData.projectId</code>, if any, is ignored.
     *
     * @return The project as it was created, with the <code>ProjectData.projectId</code> and <code>ProjectData.userId
     *         </code> set. Will never be null.
     *
     * @throws IllegalArgumentFault
     *             If the given <code>ProjectData</code> is illegal.
     * @throws PersistenceFault
     *             If a generic persistence error occurs.
     *
     * @since 1.0
     */
    ProjectData createProject(TCSubject tcSubject, ProjectData projectData) throws PersistenceFault, IllegalArgumentFault;

    /**
     * <p>
     * Gets the project data for the project with the given Id.
     * </p>
     *
     * <p>
     * Notes, only associated user can retrieve the specified project, administrator can retrieve any projects.
     * </p>
     * <p>
     * Update in v1.1.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param projectId
     *            The ID of the project to be retrieved.
     *
     * @return The project data for the project with the given Id. Will never be null.
     *
     * @throws PersistenceFault
     *             If a generic persistence error.
     * @throws ProjectNotFoundFault
     *             If no project with the given ID exists.
     * @throws AuthorizationFailedFault
     *             If the calling principal is not authorized to retrieve the project.
     *
     * @since 1.0
     */
    ProjectData getProject(TCSubject tcSubject,long projectId) throws PersistenceFault, ProjectNotFoundFault,
        AuthorizationFailedFault;

    /**
     * <p>
     * Gets the project data for the project with the project name.
     * </p>
     *
     * @param projectName
     *            the name of the project to be retrieved.
     * @param userId
     *            The ID of the user whose project is to be retrieved.
     * @return
     *            The project data for the project with the given Id. Will never be null.
     * @throws PersistenceFault
     *            If a generic persistence error occurs.
     * @throws ProjectNotFoundFault
     *            If no project with the given name and user id exists.
     * @throws AuthorizationFailedFault
     *            If the calling principal is not authorized to retrieve the project.
     * @throws IllegalArgumentFault
     *            If the given <code>projectName</code> is null/empty, or <code>userId</code>
     *            is non-positive.
     * @since 1.1
     */
    ProjectData getProjectByName(String projectName, long userId) throws PersistenceFault,
        ProjectNotFoundFault, AuthorizationFailedFault, IllegalArgumentFault;

    /**
     * <p>
     * Gets the project data for all projects of the given user.
     * </p>
     *
     * <p>
     * Notes, only administrator can do this.
     * </p>
     *
     * <p>
     *     <strong>Version History:</strong>
     *     <ul>
     *         <li>Introduced since version 1.0.</li>
     *         <li>Modified in version 1.1:</li>
     *             <ul>
     *               <li>The implementation should no longer perform role-based authorization in code.
     *               It should rely instead (as always did) on EJB declarative security.</li>
     *             </ul>
     *     </ul>
     * </p>
     *
     * @param userId
     *            The ID of the user whose projects are to be retrieved.
     *
     * @return The project data for all projects of the given user. The returned collection will not
     *         be null or contain nulls. Will never be empty.
     *
     * @throws UserNotFoundFault
     *             If there are no projects linked to the given user.
     * @throws PersistenceFault
     *             If a generic persistence error occurs.
     * @throws AuthorizationFailedFault
     *             Should not be thrown from version1.1. It is here only to leave the web interface unchanged.
     *
     * @since 1.0
     */
    List < ProjectData > getProjectsForUser(long userId) throws PersistenceFault, UserNotFoundFault,
        AuthorizationFailedFault;

    /**
     * <p>
     * Gets the project data for all projects viewable from the calling principal.
     * </p>
     *
     * <p>
     * Notes, for user, it will retrieve only the projects associated with him;
     * For administrator, it will retrieve all the projects.
     * </p>
     *
     * @return The project data for all projects viewable from the calling principal. The returned collection will not
     *         be null or contain nulls. Possibly empty.
     *
     * @throws PersistenceFault
     *             If a generic persistence error occurs.
     *
     * @since 1.0
     */
    List < ProjectData > getAllProjects() throws PersistenceFault;

    /**
     * <p>
     * Updates the project data for the given project.
     * </p>
     *
     * <p>
     * Notes, only project-associated user can update that project, but administrator can update any project.
     * </p>
     * <p>
     * Update in v1.1.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param projectData
     *            The project data to be updated. Must not be null.
     *            The <code>ProjectData.name</code> must not be null/empty.
     *            The <code>ProjectData.projectId</code> must be non-null.
     *
     * @throws IllegalArgumentFault
     *             If the given <code>ProjectData</code> is illegal.
     * @throws ProjectNotFoundFault
     *             If no project with the given ID exists.
     * @throws AuthorizationFailedFault
     *             If the calling principal is not authorized to update the project.
     * @throws PersistenceFault
     *             If a generic persistence error.
     *
     * @since 1.0
     */
    void updateProject(TCSubject tcSubject, ProjectData projectData) throws PersistenceFault, ProjectNotFoundFault,
        AuthorizationFailedFault, IllegalArgumentFault;

    /**
     * <p>
     * Deletes the project data for the project with the given Id.
     * </p>
     *
     * <p>
     * Notes, only project-associated user can delete that project, but administrator can delete any project.
     * </p>
     * <p>
     * Update in v1.1.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param projectId
     *            The ID of the project be deleted.
     * @return Whether the project was found, and thus deleted.
     *
     * @throws AuthorizationFailedFault
     *             If the calling principal is not authorized to delete the project.
     * @throws ProjectHasCompetitionsFault
     *             If the project cannot be deleted since it has competitions associated with it.
     * @throws PersistenceFault
     *             If a generic persistence error.
     *
     * @since 1.0
     */
    boolean deleteProject(TCSubject tcSubject, long projectId) throws PersistenceFault, AuthorizationFailedFault,
        ProjectHasCompetitionsFault;

    /**
     * <p>
     * Gets the project data for the project with the project name.
     * </p>
     *
     * @param projectName
     *            the name of the project to be retrieved.
     * @return
     *            The project data for the project with the given Id. Will never be null.
     * @throws PersistenceFault
     *            If a generic persistence error occurs.
     * @throws ProjectNotFoundFault
     *            If no project with the given name and user id exists.
     * @throws AuthorizationFailedFault
     *            If the calling principal is not authorized to retrieve the project.
     * @throws IllegalArgumentFault
     *            If the given <code>projectName</code> is null/empty, or <code>userId</code>
     *            is non-positive.
     * @since 1.1
     */
    public List < ProjectData > getProjectsByName(String projectName) throws PersistenceFault,
        ProjectNotFoundFault, IllegalArgumentFault;

    /**
     * Gets all the project types.
     *
     * @return all the project types.
     * @throws PersistenceFault if a generic persistence error occurs.
     * @since 2.0
     */
    public List<ProjectType> getAllProjectTypes() throws  PersistenceFault;

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
     * Gets the client instance by the given direct project id.
     *
     * @param projectId the id of the direct project
     * @return the client this project belongs to, null if no client
     * @throws PersistenceFault if a generic persistence error occurs.
     * @since 1.2
     */
    public Client getClientByProject(long projectId) throws PersistenceFault;

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
     * @param projectId the id of the direct project
     * @param billingAccountId the id of the billing account
     * @throws InvalidBillingAccountForProjectFault if the billing account does not have same client as project's existing billing accounts
     * @throws PersistenceFault if a generic persistence error occurs.
     * @since 2.0
     */
    public void addBillingAccountToProject(long projectId, long billingAccountId) throws InvalidBillingAccountForProjectFault, PersistenceFault;

    /**
     * Removes the billing account from the project.
     *
     * @param projectId the id of the direct project
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
     * This contract method gets all ProjectQuestion instances available in the persistence.
     * This contract method is added by "TC Cockpit- Create New Project BackEnd 1.0" component.
     * @return A list of all the available ProjectQuestion instances in the persistence.
     * @throws PersistenceFault When some error occurred when accessing the persistence.
     * @since 2.1
     */
    public List<ProjectQuestion> getProjectQuestions() throws PersistenceFault;

}
