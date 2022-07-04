/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.project;

import java.util.List;

import javax.jws.WebService;

import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.model.Project;
import com.topcoder.service.project.AuthorizationFailedFault;
import com.topcoder.service.project.IllegalArgumentFault;
import com.topcoder.service.project.PersistenceFault;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.project.ProjectNotFoundFault;
import com.topcoder.service.project.ProjectService;
import com.topcoder.service.project.UserNotFoundFault;

/**
 * <p>
 * An interface for the web service implementing unified <code>Facade</code> interface to various service components
 * provided by global <code>TopCoder</code> application.
 * </p>
 * <p>
 * This interface is a copy of the old ProjectServiceFacade interface. ProjectServiceFacade is no longer a web service
 * point. The security part is covered in this new web-service component. This bean's methods create this TCSubject
 * instance by do the login with LoginBean class and simply call the corresponding ContestServiceFacade method. This web
 * service must now be used instead of ProjectServiceFacade by web service clients.
 * </p>
 * <p>
 * So the old ProjectServiceFacade will accepts a more parameter: TCSubject from this new-facade.
 * AuthenticationException and GeneralSecurityException from login now will be directly propagated.
 * </p>
 * <p>
 * Thread-safe: the Implementation is required to be thread-safe.
 * </p>
 *
 * @author waits
 * @version 1.0
 * @since Cockpit Security Facade Assembly V1.0
 */
@WebService
public interface ProjectServiceFacadeWebService {

    /**
     * <p>
     * Creates a project with the given project data.
     * </p>
     * <p>
     * Note, any user can create project and the project will associate with him/her.
     * </p>
     *
     * @param projectData The project data to be created. Must not be null. The <code>ProjectData.name</code> must not
     *            be null/empty. The <code>ProjectData.projectId</code>, if any, is ignored.
     * @return The project as it was created, with the <code>ProjectData.projectId</code> and <code>ProjectData.userId
     *         </code> set. Will
     *         never be null.
     * @throws IllegalArgumentFault if the given <code>ProjectData</code> is illegal.
     * @throws PersistenceFault if a generic persistence error occurs.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     * @see ProjectService#createProject(ProjectData)
     */
    ProjectData createProject(ProjectData projectData) throws PersistenceFault, IllegalArgumentFault;

    /**
     * <p>
     * Gets the project data for the project with the given Id.
     * </p>
     * <p>
     * Notes, only associated user can retrieve the specified project, administrator can retrieve any projects.
     * </p>
     *
     * @param projectId the ID of the project to be retrieved.
     * @return The project data for the project with the given Id. Will never be null.
     * @throws PersistenceFault if a generic persistence error.
     * @throws ProjectNotFoundFault if no project with the given ID exists.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     * @throws AuthorizationFailedFault if the calling principal is not authorized to retrieve the project.
     * @see ProjectService#getProject(long)
     */
    ProjectData getProject(long projectId) throws PersistenceFault, ProjectNotFoundFault, AuthorizationFailedFault;

    /**
     * <p>
     * Gets the project data for all projects of the given user.
     * </p>
     * <p>
     * Notes, only administrator can do this.
     * </p>
     *
     * @param userId the ID of the user whose projects are to be retrieved.
     * @return The project data for all projects of the given user. The returned collection will not be null or contain
     *         nulls. Will never be empty.
     * @throws UserNotFoundFault if there are no projects linked to the given user.
     * @throws PersistenceFault if a generic persistence error occurs.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     * @throws AuthorizationFailedFault should not be thrown from version 1.1. It is here only to leave the web
     *             interface unchanged.
     * @see ProjectService#getProjectsForUser(long)
     */
    List<ProjectData> getProjectsForUser(long userId) throws PersistenceFault, UserNotFoundFault,
            AuthorizationFailedFault;

    /**
     * <p>
     * Gets the project data for all projects viewable from the calling principal.
     * </p>
     * <p>
     * Notes, for user, it will retrieve only the projects associated with him; for administrators, it will retrieve all
     * the existing projects.
     * </p>
     *
     * @return The project data for all projects viewable from the calling principal. The returned collection will not
     *         be null or contain nulls. Possibly empty.
     * @throws PersistenceFault if a generic persistence error occurs.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     * @throws AuthorizationFailedFault if errors occurs during authorization of the caller user.
     * @throws UserNotFoundFault if errors occurs during authorization of the caller user.
     * @see ProjectService#getAllProjects()
     */
    List<ProjectData> getAllProjects() throws PersistenceFault, AuthorizationFailedFault, UserNotFoundFault;

    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with projects with the given user id. If nothing is
     * found, return an empty list.
     * <p>
     *
     * @return List of Project, if nothing is found, return an empty string
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     * @throws DAOException if any error occurs while performing this operation.
     */
    public List<Project> getClientProjectsByUser() throws DAOFault;

}
