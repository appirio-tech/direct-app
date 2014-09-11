/*
 * Copyright (C) 2008-2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import com.topcoder.security.TCSubject;
import com.topcoder.service.project.AuthorizationFailedFault;
import com.topcoder.service.project.IllegalArgumentFault;
import com.topcoder.service.project.PersistenceFault;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.project.ProjectHasCompetitionsFault;
import com.topcoder.service.project.ProjectNotFoundFault;
import com.topcoder.service.project.ProjectServiceLocal;
import com.topcoder.service.project.UserNotFoundFault;

/**
 * <p>
 * The bridge used to expose the <code>ProjectServiceLocal</code>.
 * </p>
 *
 * <p>
 * Itself is a SLSB and implements <code>ProjectServiceLocalBridge</code>.
 * </p>
 *
 * <p>
 * It has a container injected <code>ProjectServiceLocal</code>, and will delegates all methods to it.
 * </p>
 *
 * <p>
 * Version 1.2 (Release Assembly - TC Cockpit Create Project Refactoring Assembly Part Two Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Updated class to make it compilable.</li>
 *   </ol>
 * </p>
 *
 * @author TCSDEVELOPER, isv
 * @version 1.2
 * @since 1.1
 */
@Stateless
@Remote(ProjectServiceLocalBridge.class)
public class ProjectServiceLocalBridgeBean implements ProjectServiceLocalBridge {

    /**
     * <p>A <code>String</code> providing the ID for user granted <code>Cockpit User</code> role.</p>
     *
     * @since 1.2
     */
    public static final long USER_ID = 132456L;

    /**
     * <p>A <code>String</code> providing the ID for user granted <code>Cockpit Administrator</code> role.</p>
     *
     * @since 1.2
     */
    public static final long ADMIN_ID = 132458L;

    /**
     * <p>A <code>TCSubject</code> for user account.</p>
     *
     * @since 1.2
     */
    private TCSubject user = new TCSubject(USER_ID);

    /**
     * <p>A <code>TCSubject</code> for admin account.</p>
     *
     * @since 1.2
     */
    private TCSubject admin = new TCSubject(ADMIN_ID);

    /**
     * <p>
     * The container injected <code>ProjectServiceLocal</code>.
     * </p>
     */
    @EJB(beanName = "ProjectServiceBean") private ProjectServiceLocal projectService;

    /**
     * <p>
     * Creates a project with the given project data.
     * </p>
     *
     * <p>
     * Note, any user can create project and the project will associate with him/her.
     * </p>
     *
     * <p>
     * Simply delegates to the injected <code>ProjectServiceLocal</code>.
     * </p>
     *
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
     * @since 1.1
     */
    public ProjectData createProject(ProjectData projectData)
        throws PersistenceFault, IllegalArgumentFault {
        return projectService.createProject(user, projectData);
    }

    /**
     * <p>
     * Deletes the project data for the project with the given Id.
     * </p>
     *
     * <p>
     * Notes, only project-associated user can delete that project, but administrator can delete any project.
     * </p>
     *
     * <p>
     * Simply delegates to the injected <code>ProjectServiceLocal</code>.
     * </p>
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
     * @since 1.1
     */
    public boolean deleteProject(long projectId)
        throws PersistenceFault, AuthorizationFailedFault,
            ProjectHasCompetitionsFault {
        return projectService.deleteProject(admin, projectId);
    }

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
     * <p>
     * Simply delegates to the injected <code>ProjectServiceLocal</code>.
     * </p>
     *
     * @return The project data for all projects viewable from the calling principal. The returned collection will not
     *         be null or contain nulls. Possibly empty.
     *
     * @throws PersistenceFault
     *             If a generic persistence error occurs.
     * @since 1.1
     */
    public List < ProjectData > getAllProjects() throws PersistenceFault {
        return projectService.getAllProjects();
    }

    /**
     * <p>
     * Gets the project data for the project with the given Id.
     * </p>
     *
     * <p>
     * Notes, only associated user can retrieve the specified project, administrator can retrieve any projects.
     * </p>
     *
     * <p>
     * Simply delegates to the injected <code>ProjectServiceLocal</code>.
     * </p>
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
     * @since 1.1
     */
    public ProjectData getProject(long projectId)
        throws PersistenceFault, ProjectNotFoundFault, AuthorizationFailedFault {
        return projectService.getProject(user, projectId);
    }

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
     * Simply delegates to the injected <code>ProjectServiceLocal</code>.
     * </p>
     *
     * <p>
     *     <strong>Version History:</strong>
     *     <ul>
     *         <li>Introduced since version 1.0.</li>
     *         <li>Modified in version 1.1:</li>
     *             <ul>
     *               <li>This method now does not throw <code>AuthorizationFailedFault</code>. Because the
     *               implementation should no longer perform role-based authorization in code. It should
     *               rely instead (as always did) on EJB declarative security.</li>
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
     * @since 1.1
     */
    public List < ProjectData > getProjectsForUser(long userId)
        throws PersistenceFault, UserNotFoundFault, AuthorizationFailedFault {
        return projectService.getProjectsForUser(userId);
    }

    /**
     * <p>
     * Updates the project data for the given project.
     * </p>
     *
     * <p>
     * Notes, only project-associated user can update that project, but administrator can update any project.
     * </p>
     *
     * <p>
     * Simply delegates to the injected <code>ProjectServiceLocal</code>.
     * </p>
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
     * @since 1.1
     */
    public void updateProject(ProjectData projectData)
        throws PersistenceFault, ProjectNotFoundFault, AuthorizationFailedFault,
            IllegalArgumentFault {
        projectService.updateProject(admin, projectData);
    }
    
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
     * @since 1.2
     */
    public ProjectData getProjectByName(String projectName, long userId) throws PersistenceFault, ProjectNotFoundFault,
            AuthorizationFailedFault, IllegalArgumentFault {
        return projectService.getProjectByName(projectName, userId);
    }

    /**
     * <p> Creates a project with the given project data. </p>
     *
     * <p> Note, any user can create project and the project will associate with him/her. </p> <p> Update in v1.1.1: add
     * parameter TCSubject which contains the security info for current user. </p>
     *
     * @param tcSubject   TCSubject instance contains the login security info for the current user
     * @param projectData The project data to be created. Must not be null. The <code>ProjectData.name</code> must not be
     *                    null/empty. The <code>ProjectData.projectId</code>, if any, is ignored.
     * @return The project as it was created, with the <code>ProjectData.projectId</code> and <code>ProjectData.userId
     *         </code> set. Will never be null.
     * @throws IllegalArgumentFault If the given <code>ProjectData</code> is illegal.
     * @throws PersistenceFault If a generic persistence error occurs.
     * @since 1.0
     */
    public ProjectData createProject(TCSubject tcSubject, ProjectData projectData)
        throws PersistenceFault, IllegalArgumentFault {
        return projectService.createProject(tcSubject, projectData);
    }

    /**
     * <p> Gets the project data for the project with the given Id. </p>
     *
     * <p> Notes, only associated user can retrieve the specified project, administrator can retrieve any projects. </p>
     * <p> Update in v1.1.1: add parameter TCSubject which contains the security info for current user. </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectId The ID of the project to be retrieved.
     * @return The project data for the project with the given Id. Will never be null.
     * @throws PersistenceFault If a generic persistence error.
     * @throws ProjectNotFoundFault If no project with the given ID exists.
     * @throws AuthorizationFailedFault If the calling principal is not authorized to retrieve the project.
     * @since 1.0
     */
    public ProjectData getProject(TCSubject tcSubject, long projectId)
        throws PersistenceFault, ProjectNotFoundFault, AuthorizationFailedFault {
        return projectService.getProject(tcSubject, projectId);
    }

    /**
     * <p> Updates the project data for the given project. </p>
     *
     * <p> Notes, only project-associated user can update that project, but administrator can update any project. </p> <p>
     * Update in v1.1.1: add parameter TCSubject which contains the security info for current user. </p>
     *
     * @param tcSubject   TCSubject instance contains the login security info for the current user
     * @param projectData The project data to be updated. Must not be null. The <code>ProjectData.name</code> must not be
     *                    null/empty. The <code>ProjectData.projectId</code> must be non-null.
     * @throws IllegalArgumentFault If the given <code>ProjectData</code> is illegal.
     * @throws ProjectNotFoundFault If no project with the given ID exists.
     * @throws AuthorizationFailedFault If the calling principal is not authorized to update the project.
     * @throws PersistenceFault If a generic persistence error.
     * @since 1.0
     */
    public void updateProject(TCSubject tcSubject, ProjectData projectData)
        throws PersistenceFault, ProjectNotFoundFault, AuthorizationFailedFault, IllegalArgumentFault {
        projectService.updateProject(tcSubject, projectData);
    }

    /**
     * <p> Deletes the project data for the project with the given Id. </p>
     *
     * <p> Notes, only project-associated user can delete that project, but administrator can delete any project. </p> <p>
     * Update in v1.1.1: add parameter TCSubject which contains the security info for current user. </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectId The ID of the project be deleted.
     * @return Whether the project was found, and thus deleted.
     * @throws AuthorizationFailedFault If the calling principal is not authorized to delete the project.
     * @throws ProjectHasCompetitionsFault If the project cannot be deleted since it has competitions associated with it.
     * @throws PersistenceFault If a generic persistence error.
     * @since 1.0
     */
    public boolean deleteProject(TCSubject tcSubject, long projectId)
        throws PersistenceFault, AuthorizationFailedFault, ProjectHasCompetitionsFault {
        return projectService.deleteProject(tcSubject, projectId);
    }

    /**
     * <p> Gets the project data for the project with the project name. </p>
     *
     * @param projectName the name of the project to be retrieved.
     * @return The project data for the project with the given Id. Will never be null.
     * @throws PersistenceFault If a generic persistence error occurs.
     * @throws ProjectNotFoundFault If no project with the given name and user id exists.
     * @throws IllegalArgumentFault If the given <code>projectName</code> is null/empty, or <code>userId</code> is
     * non-positive.
     * @since 1.1
     */
    public List<ProjectData> getProjectsByName(String projectName)
        throws PersistenceFault, ProjectNotFoundFault, IllegalArgumentFault {
        return projectService.getProjectsByName(projectName);
    }
}
