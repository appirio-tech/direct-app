/*
 * Copyright (C) 2007 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service;

import com.topcoder.management.project.DesignComponents;
import com.topcoder.management.project.FileType;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectPlatform;
import com.topcoder.management.project.SimplePipelineData;
import com.topcoder.management.project.SimpleProjectContestData;
import com.topcoder.management.project.SimpleProjectPermissionData;
import com.topcoder.management.project.SoftwareCapacityData;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.review.ReviewManagementException;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.Review;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.project.phases.Phase;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.security.TCSubject;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * This represents the interface that defines all business methods for project data retrieval
 * services. It will provide streamlined access to project information. It will allow for a simple
 * search for full or basic project information, or to use custom search criteria to locate
 * projects, either in its full or basic form. The basic form involves getting the
 * <code>Project</code> object (from <b>Project Management</b>), and the full form involves the
 * <code>FullProjectData</code> object, which not only provides information as the basic form, but
 * also provides project phase information, all resources participating in the project, and all
 * teams currently existing in it. Furthermore, it provides data about the technologies involved in
 * this project (such as Java, C#).
 * </p>
 *
 * <p>
 * Modifications in version 1.1:
 * <ul>
 * <li>In version 1.0, it has one implementation: <code>ProjectServicesImpl</code>. In version
 * 1.1, it is added an EJB layer to wrap all calls of the ProjectServices interface, so it has two
 * implementations: <code>ProjectServicesImpl</code> and <code>ProjectServicesBean</code> since
 * version 1.1.</li>
 * <li>In version 1.1, <code>createProject</code> and <code>updateProject</code> methods are
 * added</li>
 * </ul>
 * </p>
 *
 * <p>
 * Module Contest Service Software Contest Sales Assembly change: new methods added to support creating/updating/query contest
 * sale for software contest.
 * </p>
 *
 * <p>
 * Updated for Cockpit Project Admin Release Assembly v1.0: new methods added to support retrieval of project and their permissions.
 * </p>
 *
 * <p>
 * Version 1.1.1 (Cockpit Pipeline Release Assembly 1 v1.0) Change Notes:
 *  - Introduced method to retrieve SimplePipelineData for given date range.
 * </p>
 * <p>
 * Version 1.2 (Cockpit Pipeline Release Assembly 2 - Capacity) changelog:
 *     - added service that retrieves a list of capacity data (date, number of scheduled contests) starting from
 *       tomorrow for a given contest type
 * </p>
 * <p>
 * Changes in v1.2.1 - Cockpit Release Assembly 11
 * Add method getDesignComponents to get design components.
 * </p>
 *
 * <p>
 * Version 1.2.2 (Cockpit Contest Eligibility) changelog:
 *     - added a method for create private contest's roles
 * </p>
 * <p>
 * Version 1.3 (Cockpit Spec Review Backend Service Update v1.0) changelog:
 *     - Added project link, scorecard and review managers creation.
 *     - Added method to create specification review project for an existing project.
 *     - Added method to get scorecard and review information for a specific project.
 *     - Added method to get the corresponding specification review project id for a given project id.
 *     - Added method to get open phases names for a given project id.
 *     - Added method to add comments to an existing review.
 * </p>
 * <p>
 * Version 1.3.1 (BR3074) changelog:
 *     - Added method to link the design and development contests.
 * </p>
 * <p>
 * Changes in v1.4 (Cockpit Release Assembly - Contest Repost and New Version v1.0):
 * - Added method to re open failed software contest.
 * - Added method to create new version for development or design contest.
 * </p>
 * <p>
 * Changes in v1.4.1(Cockpit Security Facade V1.0)
 *  - findAllTcDirectProjects, findAllTcDirectProjectsForUser,
 *    getSimplePipelineData, getDesignComponents these methods add paremeter TCSubject in order to replacing
 *    the current permission checking security info.
 * </p>
 * <p>
 * Changes in v1.4.2 (BUGR - 3706)
 *  - add method getActiveContestsForUser(TCSubject subject, long userId)
 *  - add method getNotificationsForUser(long userId, long notificationType, long[] projectIds)
 *  - add method addNotifications(long userId, long[] projectIds, String operator)
 *  - add method removeNotifications(long userId, long[] projectIds, String operator)
 * </p>
 *
 * <p>
 * Version 1.4.3 (Manage Copilot Postings Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #getScorecardAndReviews(long, long)} method.</li>
 *     <li>Added {@link #createReview(Review)} method.</li>
 *   </ol>
 * </p>
 * <p>
 * Changes in v1.4.4 (TC Direct Release Assembly 7)
 *  - add method updateContestSale for updating contest sale.
 * </p>
 * 
 * <p>
 * Version 1.4.4 (TC Direct Replatforming Release 1) Change notes:
 * <ul>
 * <li>Add {@link #updateProject(Project, String, com.topcoder.project.phases.Project, Resource[], Date, String)} method.</li>
 * <li>Add {@link #createProjectWithTemplate(Project, com.topcoder.project.phases.Project, Resource[], Date, String)} method.</li>
 * <li>Add {@link #getAllFileTypes()} method.</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Version 1.4.5 (TC Direct Replatforming Release 3) Change notes:
 * <ul>
 * <li>Add {@link #getScorecardAndCheckpointReviews(long, long)} method.</li>
 * <li>Add {@link #updateReview(Review)} method to update the review board.</li>
 * </ul>
 * </p>
 *
 *  <p>
 * Version 1.4.6 (Release Assembly - TopCoder Bug Hunt Assembly Integration 2) change log:
 * <ul>
 *   <li>
 *     Add method {@link #linkBugHuntToOtherContest(long)} to link bug hunt contest with its parent contest (contest
 *     which shares the same asset setting for it)
 *   </li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.4.7 (Module Assembly - Adding Contest Approval Feature in Direct Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #getScorecard(long)} method.</li>
 *     <li>Added {@link #getReviews(long, long)} method.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.4.8 (Release Assembly - TC Direct Issue Tracking Tab Update Assembly 2 v1.0) change notes:
 *   <ol>
 *     <li>Added method {@link #getPhasesByType(long, String)} to get the phases of a project by phase type.</li>
 *   </ol>
 * </p>
 * 
 * <p>
 * Version 1.4.9 (Release Assembly - TopCoder Cockpit Software Checkpoint Management) change notes:
 * <ol><li>
 *     - Added method {@link #saveSoftwareCheckpointSubmissionsGeneralFeedback(long, String)},
 *     {@link #getSoftwareCheckpointSubmissionsGeneralFeedback(long)} to support
 *     software checkpoint management.
 * </li></ol>
 * </p>
 *
 * <p>
 * Version 1.4.10 (Module Assembly - TC Cockpit - Studio - Final Fixes Integration Part One Assembly) Change notes:
 *   <ol>
 *     <li>Added {@link #getReviewsByPhase(long, long)} method.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.5 (Module Assembly - TC Cockpit Contest Milestone Association 1) change notes:
 *  <ul>
 *      <li> Add method {@link #createProjectMilestoneRelation(long, long, String)}  to add contest milestone association</li>
 *      <li> Add method {@link #updateProjectMilestoneRelation(long, long, String)}  to update contest milestone association</li>
 *      <li> Add method {@link #deleteProjectMilestoneRelation(long, String)}   to remove contest milestone association</li>
 *      <li> Add method {@link #getProjectMilestoneRelation(long)}  to retrieve contest milestone association</li>
 * </ul>
 * <p>
 *
 *
 * <p>
 * Version 1.6 (Module Assembly - TC Cockpit Contest Milestone Association Milestone Page Update)
 * <ul>
 *     <li>Add method {@link #deleteMilestoneProjectRelations(long, String)}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.7 (Module Assembly - TC Cockpit Launch F2F contest)
 * <ul>
 *     <li>Added method {@link #getAllProjectPlatforms()}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.8 (Module Assembly - TC Direct Studio Design First2Finish Challenge Type)
 * <ul>
 *     <li>Added method {@link #getPhases(long)}</li>
 * </ul>
 * </p>
 * 
 * <p>
 * <strong>Thread Safety:</strong> Implementations must be thread-safe from the point of view of
 * their use. Implementations can assume that passed objects will be operated on by just one thread.
 * </p>
 *
 *
 * @author argolite, moonli, pulky
 * @author fabrizyo, znyyddf, murphydog, waits, hohosky, isv, GreatKevin
 * @version 1.8
 */
public interface ProjectServices {
    /**
     * <p>
     * This method finds all active projects along with all known associated information. Returns
     * empty array if no projects found.
     * </p>
     *
     * @return FullProjectData array with full projects info, or empty array if none found
     * @throws ProjectServicesException
     *             If there is a system error while performing the search
     */
    public FullProjectData[] findActiveProjects();

    /**
     * <p>
     * This method finds all active projects. Returns empty array if no projects found.
     * </p>
     *
     * @return Project array with project info, or empty array if none found
     * @throws ProjectServicesException
     *             If there is a system error while performing the search
     */
    public Project[] findActiveProjectsHeaders();

    /**
     * <p>
     * This method finds all tc direct projects. Returns empty array if no projects found.
     * </p>
     * <p>
     * Update in v1.4.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @return Project array with project info, or empty array if none found
     */
    public Project[] findAllTcDirectProjects(TCSubject tcSubject);

    /**
     * <p>
     * This method finds all given user tc direct projects . Returns empty array if no projects found.
     * </p>
     * <p>
     * Update in v1.4.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param operator The user to search for projects
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @return   Project array with project info, or empty array if none found
     */
    public Project[] findAllTcDirectProjectsForUser(TCSubject tcSubject, String operator);

    /**
     * <p>
     * This method finds all projects along with all known associated information that match the
     * search criteria. Returns empty array if no projects found.
     * </p>
     *
     * @return FullProjectData array with full projects info, or empty array if none found
     * @param filter
     *            The search criteria to filter projects
     * @throws IllegalArgumentException
     *             If filter is null
     * @throws ProjectServicesException
     *             If there is a system error while performing the search
     */
    public FullProjectData[] findFullProjects(Filter filter);

    /**
     * <p>
     * This method finds all projects that match the search criteria. Returns empty array if no
     * projects found.
     * </p>
     *
     * @return Project array with project info, or empty array if none found
     * @param filter
     *            The search criteria to filter projects
     * @throws IllegalArgumentException
     *             If filter is null
     * @throws ProjectServicesException
     *             If there is a system error while performing the search
     */
    public Project[] findProjectsHeaders(Filter filter);

    /**
     * <p>
     * This method retrieves the project along with all known associated information. Returns null
     * if not found.
     * </p>
     *
     * <p>
     * Module Contest Service Software Contest Sales Assembly change: fetch the contest sale info.
     * </p>
     *
     * @return the project along with all known associated information
     * @param projectId
     *            The ID of the project to retrieve
     * @throws IllegalArgumentException
     *             If projectId is negative
     * @throws ProjectServicesException
     *             If there is a system error while performing the search
     */
    public FullProjectData getFullProjectData(long projectId);

    /**
     * <p>
     * Persist the project and all related data. All ids (of project header, project phases and
     * resources) will be assigned as new, for this reason there is no exception like 'project
     * already exists'.
     * </p>
     * <p>
     * First it persist the projectHeader a com.topcoder.management.project.Project instance. Its
     * properties and associating scorecards, the operator parameter is used as the
     * creation/modification user and the creation date and modification date will be the current
     * date time when the project is created. The id in Project will be ignored: a new id will be
     * created using ID Generator (see Project Management CS). This id will be set to Project
     * instance.
     * </p>
     * <p>
     * Then it persist the phases a com.topcoder.project.phases.Project instance. The id of project
     * header previous saved will be set to project Phases. The phases' ids will be set to 0 (id not
     * set) and then new ids will be created for each phase after persist operation.
     * </p>
     * <p>
     * At last it persist the resources, they can be empty.The id of project header previous saved
     * will be set to resources. The ids of resources' phases ids must be null. See &quot;id problem
     * with resources&quot; thread in design forum. The resources could be empty or null, null is
     * treated like empty: no resources are saved. The resources' ids will be set to UNSET_ID of
     * Resource class and therefore will be persisted as new resources's.
     * </p>
     *
     * <p>
     * Module Contest Service Software Contest Sales Assembly change: return the wrapped value for project header, phases, resources info.
     * </p>
     *
     * @param projectHeader
     *            the project's header, the main project's data
     * @param projectPhases
     *            the project's phases
     * @param projectResources
     *            the project's resources, can be null or empty, can't contain null values. Null is
     *            treated like empty.
     * @param operator
     *            the operator used to audit the operation, can be null but not empty
     * @return the created project.
     * @throws IllegalArgumentException
     *             if any case in the following occurs:
     *             <ul>
     *             <li>if projectHeader is null;</li>
     *             <li>if projectPhases is null or the phases of projectPhases are empty;</li>
     *             <li>if the project of phases (for each phase: phase.project) is not equal to
     *             projectPhases;</li>
     *             <li>if projectResources contains null entries;</li>
     *             <li>if for each resources: a required field of the resource is not set : if
     *             resource.getResourceRole() is null;</li>
     *             <li>if operator is null or empty;</li>
     *             </ul>
     * @throws ProjectServicesException
     *             if there is a system error while performing the create operation
     * @since 1.1
     */
    public FullProjectData createProject(Project projectHeader, com.topcoder.project.phases.Project projectPhases,
            Resource[] projectResources, String operator);

    /**
     * <p>
     * Update the project and all related data. First it updates the projectHeader a
     * com.topcoder.management.project.Project instance. All related items will be updated. If items
     * are removed from the project, they will be deleted from the persistence. Likewise, if new
     * items are added, they will be created in the persistence. For the project, its properties and
     * associating scorecards, the operator parameter is used as the modification user and the
     * modification date will be the current date time when the project is updated. See the source
     * code of Project Management component, ProjectManager: there is a 'reason' parameter in
     * updateProject method.
     * </p>
     * <p>
     * Then it updates the phases a com.topcoder.project.phases.Project instance. The id of
     * projectHeader previous saved must be equal to projectPhases' id. The
     * projectPhases.phases.project's id must be equal to projectHeader's id. The phases of the
     * specified project are compared to the phases already in the database. If any new phases are
     * encountered, they are added to the persistent store. If any phases are missing from the
     * input, they are deleted. All other phases are updated.
     * </p>
     * <p>
     * At last it updates the resources, they can be empty. Any resources in the array with UNSET_ID
     * are assigned an id and updated in the persistence. Any resources with an id already assigned
     * are updated in the persistence. Any resources associated with the project in the persistence
     * store, but not appearing in the array are removed. The resource.project must be equal to
     * projectHeader's id. The resources which have a phase id assigned ( a resource could not have
     * the phase id assigned), must have the phase id contained in the projectPhases.phases' ids.
     * </p>
     *
     * @param projectHeader
     *            the project's header, the main project's data
     * @param projectHeaderReason
     *            the reason of projectHeader updating.
     * @param projectPhases
     *            the project's phases
     * @param projectResources
     *            the project's resources, can be null or empty, can't contain null values. Null is
     *            treated like empty.
     * @param operator
     *            the operator used to audit the operation, can be null but not empty
     * @throws IllegalArgumentException
     *             if any case in the following occurs:
     *             <ul>
     *             <li>if projectHeader is null or projectHeader.id is nonpositive;</li>
     *             <li>if projectHeaderReason is null or empty;</li>
     *             <li>if projectPhases is null, or if the phases of projectPhases are empty, or if
     *             the projectPhases.id is not equal to projectHeader.id, or for each phase: if the
     *             phase.object is not equal to projectPhases;</li>
     *             <li>if projectResources contains null entries;</li>
     *             <li>for each resource: if resource.getResourceRole() is null, or if the resource
     *             role is associated with a phase type but the resource is not associated with a
     *             phase, or if the resource.phase (id of phase) is set but it's not in
     *             projectPhases.phases' ids, or if the resource.project (project's id) is not equal
     *             to projectHeader's id;</li>
     *             <li>if operator is null or empty;</li>
     *             </ul>
     * @throws ProjectDoesNotExistException
     *             if the project doesn't exist in persistent store.
     * @throws ProjectServicesException
     *             if there is a system error while performing the update operation
     * @since 1.1
     */
    public FullProjectData updateProject(Project projectHeader, String projectHeaderReason,
            com.topcoder.project.phases.Project projectPhases, Resource[] projectResources, String operator);

    /**
     * <p>
     * Update the project and all related data. First it updates the projectHeader a
     * com.topcoder.management.project.Project instance. All related items will be updated. If items
     * are removed from the project, they will be deleted from the persistence. Likewise, if new
     * items are added, they will be created in the persistence. For the project, its properties and
     * associating scorecards, the operator parameter is used as the modification user and the
     * modification date will be the current date time when the project is updated. See the source
     * code of Project Management component, ProjectManager: there is a 'reason' parameter in
     * updateProject method.
     * </p>
     * <p>
     * Then it updates the phases a com.topcoder.project.phases.Project instance. The id of
     * projectHeader previous saved must be equal to projectPhases' id. The
     * projectPhases.phases.project's id must be equal to projectHeader's id. The phases of the
     * specified project are compared to the phases already in the database. If any new phases are
     * encountered, they are added to the persistent store. If any phases are missing from the
     * input, they are deleted. All other phases are updated.
     * </p>
     * <p>
     * At last it updates the resources, they can be empty. Any resources in the array with UNSET_ID
     * are assigned an id and updated in the persistence. Any resources with an id already assigned
     * are updated in the persistence. Any resources associated with the project in the persistence
     * store, but not appearing in the array are removed. The resource.project must be equal to
     * projectHeader's id. The resources which have a phase id assigned ( a resource could not have
     * the phase id assigned), must have the phase id contained in the projectPhases.phases' ids.
     * </p>
     *
     * @param projectHeader
     *            the project's header, the main project's data
     * @param projectHeaderReason
     *            the reason of projectHeader updating.
     * @param projectPhases
     *            the project's phases
     * @param projectResources
     *            the project's resources, can be null or empty, can't contain null values. Null is
     *            treated like empty.
     * @param multiRoundEndDate the end date for the multiround phase. No multiround if it's null.
     * @param operator
     *            the operator used to audit the operation, can be null but not empty
     * @throws IllegalArgumentException
     *             if any case in the following occurs:
     *             <ul>
     *             <li>if projectHeader is null or projectHeader.id is nonpositive;</li>
     *             <li>if projectHeaderReason is null or empty;</li>
     *             <li>if projectPhases is null, or if the phases of projectPhases are empty, or if
     *             the projectPhases.id is not equal to projectHeader.id, or for each phase: if the
     *             phase.object is not equal to projectPhases;</li>
     *             <li>if projectResources contains null entries;</li>
     *             <li>for each resource: if resource.getResourceRole() is null, or if the resource
     *             role is associated with a phase type but the resource is not associated with a
     *             phase, or if the resource.phase (id of phase) is set but it's not in
     *             projectPhases.phases' ids, or if the resource.project (project's id) is not equal
     *             to projectHeader's id;</li>
     *             <li>if operator is null or empty;</li>
     *             </ul>
     * @throws ProjectDoesNotExistException
     *             if the project doesn't exist in persistent store.
     * @throws ProjectServicesException
     *             if there is a system error while performing the update operation
     * @since 1.4.4
     */
    public FullProjectData updateProject(Project projectHeader, String projectHeaderReason,
            com.topcoder.project.phases.Project projectPhases, Resource[] projectResources, Date multiRoundEndDate, String operator);

    /**
     * <p>
     * Update the project and all related data. First it updates the projectHeader a
     * com.topcoder.management.project.Project instance. All related items will be updated. If items
     * are removed from the project, they will be deleted from the persistence. Likewise, if new
     * items are added, they will be created in the persistence. For the project, its properties and
     * associating scorecards, the operator parameter is used as the modification user and the
     * modification date will be the current date time when the project is updated. See the source
     * code of Project Management component, ProjectManager: there is a 'reason' parameter in
     * updateProject method.
     * </p>
     * <p>
     * Then it updates the phases a com.topcoder.project.phases.Project instance. The id of
     * projectHeader previous saved must be equal to projectPhases' id. The
     * projectPhases.phases.project's id must be equal to projectHeader's id. The phases of the
     * specified project are compared to the phases already in the database. If any new phases are
     * encountered, they are added to the persistent store. If any phases are missing from the
     * input, they are deleted. All other phases are updated.
     * </p>
     * <p>
     * At last it updates the resources, they can be empty. Any resources in the array with UNSET_ID
     * are assigned an id and updated in the persistence. Any resources with an id already assigned
     * are updated in the persistence. Any resources associated with the project in the persistence
     * store, but not appearing in the array are removed. The resource.project must be equal to
     * projectHeader's id. The resources which have a phase id assigned ( a resource could not have
     * the phase id assigned), must have the phase id contained in the projectPhases.phases' ids.
     * </p>
     *
     * @param projectHeader
     *            the project's header, the main project's data
     * @param projectHeaderReason
     *            the reason of projectHeader updating.
     * @param projectPhases
     *            the project's phases
     * @param projectResources
     *            the project's resources, can be null or empty, can't contain null values. Null is
     *            treated like empty.
     * @param multiRoundEndDate the end date for the multiround phase. No multiround if it's null.
     * @param endDate the end date for submission phase.
     * @param operator
     *            the operator used to audit the operation, can be null but not empty
     * @throws IllegalArgumentException
     *             if any case in the following occurs:
     *             <ul>
     *             <li>if projectHeader is null or projectHeader.id is nonpositive;</li>
     *             <li>if projectHeaderReason is null or empty;</li>
     *             <li>if projectPhases is null, or if the phases of projectPhases are empty, or if
     *             the projectPhases.id is not equal to projectHeader.id, or for each phase: if the
     *             phase.object is not equal to projectPhases;</li>
     *             <li>if projectResources contains null entries;</li>
     *             <li>for each resource: if resource.getResourceRole() is null, or if the resource
     *             role is associated with a phase type but the resource is not associated with a
     *             phase, or if the resource.phase (id of phase) is set but it's not in
     *             projectPhases.phases' ids, or if the resource.project (project's id) is not equal
     *             to projectHeader's id;</li>
     *             <li>if operator is null or empty;</li>
     *             </ul>
     * @throws ProjectDoesNotExistException
     *             if the project doesn't exist in persistent store.
     * @throws ProjectServicesException
     *             if there is a system error while performing the update operation
     * @since 1.4.5
     */
    public FullProjectData updateProject(Project projectHeader, String projectHeaderReason,
            com.topcoder.project.phases.Project projectPhases, Resource[] projectResources, Date multiRoundEndDate, Date endDate, String operator);
    
    /**
     * <p>
     * Persist the project and all related data. All ids (of project header, project phases and
     * resources) will be assigned as new, for this reason there is no exception like 'project
     * already exists'.
     * </p>
     * <p>
     * First it persist the projectHeader a com.topcoder.management.project.Project instance. Its
     * properties and associating scorecards, the operator parameter is used as the
     * creation/modification user and the creation date and modification date will be the current
     * date time when the project is created. The id in Project will be ignored: a new id will be
     * created using ID Generator (see Project Management CS). This id will be set to Project
     * instance.
     * </p>
     * <p>
     * Then it persist the phases a com.topcoder.project.phases.Project instance. The id of project
     * header previous saved will be set to project Phases. The phases' ids will be set to 0 (id not
     * set) and then new ids will be created for each phase after persist operation.
     * </p>
     * <p>
     * At last it persist the resources, they can be empty.The id of project header previous saved
     * will be set to resources. The ids of resources' phases ids must be null. See &quot;id problem
     * with resources&quot; thread in design forum. The resources could be empty or null, null is
     * treated like empty: no resources are saved. The resources' ids will be set to UNSET_ID of
     * Resource class and therefore will be persisted as new resources's.
     * </p>
     *
     * @param projectHeader
     *            the project's header, the main project's data
     * @param projectPhases
     *            the project's phases
     * @param projectResources
     *            the project's resources, can be null or empty, can't contain null values. Null is
     *            treated like empty.
     * @param operator
     *            the operator used to audit the operation, can be null but not empty
     * @throws IllegalArgumentException
     *             if any case in the following occurs:
     *             <ul>
     *             <li>if projectHeader is null;</li>
     *             <li>if projectPhases is null;</li>
     *             <li>if the project of phases (for each phase: phase.project) is not equal to
     *             projectPhases;</li>
     *             <li>if projectResources contains null entries;</li>
     *             <li>if for each resources: a required field of the resource is not set : if
     *             resource.getResourceRole() is null;</li>
     *             <li>if operator is null or empty;</li>
     *             </ul>
     * @throws ProjectServicesException
     *             if there is a system error while performing the create operation
     * @since BUGR-1473
     */
    public FullProjectData createProjectWithTemplate(Project projectHeader, com.topcoder.project.phases.Project projectPhases,
            Resource[] projectResources, String operator);

    /**
     * <p>
     * Persist the project and all related data. All ids (of project header, project phases and
     * resources) will be assigned as new, for this reason there is no exception like 'project
     * already exists'.
     * </p>
     * <p>
     * First it persist the projectHeader a com.topcoder.management.project.Project instance. Its
     * properties and associating scorecards, the operator parameter is used as the
     * creation/modification user and the creation date and modification date will be the current
     * date time when the project is created. The id in Project will be ignored: a new id will be
     * created using ID Generator (see Project Management CS). This id will be set to Project
     * instance.
     * </p>
     * <p>
     * Then it persist the phases a com.topcoder.project.phases.Project instance. The id of project
     * header previous saved will be set to project Phases. The phases' ids will be set to 0 (id not
     * set) and then new ids will be created for each phase after persist operation.
     * </p>
     * <p>
     * At last it persist the resources, they can be empty.The id of project header previous saved
     * will be set to resources. The ids of resources' phases ids must be null. See &quot;id problem
     * with resources&quot; thread in design forum. The resources could be empty or null, null is
     * treated like empty: no resources are saved. The resources' ids will be set to UNSET_ID of
     * Resource class and therefore will be persisted as new resources's.
     * </p>
     *
     * @param projectHeader
     *            the project's header, the main project's data
     * @param projectPhases
     *            the project's phases
     * @param projectResources
     *            the project's resources, can be null or empty, can't contain null values. Null is
     *            treated like empty.
     * @param multiRoundEndDate the end date for the multiround phase. No multiround if it's null.
     * @param operator
     *            the operator used to audit the operation, can be null but not empty
     * @throws IllegalArgumentException
     *             if any case in the following occurs:
     *             <ul>
     *             <li>if projectHeader is null;</li>
     *             <li>if projectPhases is null;</li>
     *             <li>if the project of phases (for each phase: phase.project) is not equal to
     *             projectPhases;</li>
     *             <li>if projectResources contains null entries;</li>
     *             <li>if for each resources: a required field of the resource is not set : if
     *             resource.getResourceRole() is null;</li>
     *             <li>if operator is null or empty;</li>
     *             </ul>
     * @throws ProjectServicesException
     *             if there is a system error while performing the create operation
     * @since 1.4.4
     */
    public FullProjectData createProjectWithTemplate(Project projectHeader, com.topcoder.project.phases.Project projectPhases,
            Resource[] projectResources, Date multiRoundEndDate, String operator);

    /**
     * <p>
     * Persist the project and all related data. All ids (of project header, project phases and
     * resources) will be assigned as new, for this reason there is no exception like 'project
     * already exists'.
     * </p>
     * <p>
     * First it persist the projectHeader a com.topcoder.management.project.Project instance. Its
     * properties and associating scorecards, the operator parameter is used as the
     * creation/modification user and the creation date and modification date will be the current
     * date time when the project is created. The id in Project will be ignored: a new id will be
     * created using ID Generator (see Project Management CS). This id will be set to Project
     * instance.
     * </p>
     * <p>
     * Then it persist the phases a com.topcoder.project.phases.Project instance. The id of project
     * header previous saved will be set to project Phases. The phases' ids will be set to 0 (id not
     * set) and then new ids will be created for each phase after persist operation.
     * </p>
     * <p>
     * At last it persist the resources, they can be empty.The id of project header previous saved
     * will be set to resources. The ids of resources' phases ids must be null. See &quot;id problem
     * with resources&quot; thread in design forum. The resources could be empty or null, null is
     * treated like empty: no resources are saved. The resources' ids will be set to UNSET_ID of
     * Resource class and therefore will be persisted as new resources's.
     * </p>
     *
     * @param projectHeader
     *            the project's header, the main project's data
     * @param projectPhases
     *            the project's phases
     * @param projectResources
     *            the project's resources, can be null or empty, can't contain null values. Null is
     *            treated like empty.
     * @param multiRoundEndDate the end date for the multiround phase. No multiround if it's null.
     * @param endDate the end date for submission phase.
     * @param operator
     *            the operator used to audit the operation, can be null but not empty
     * @throws IllegalArgumentException
     *             if any case in the following occurs:
     *             <ul>
     *             <li>if projectHeader is null;</li>
     *             <li>if projectPhases is null;</li>
     *             <li>if the project of phases (for each phase: phase.project) is not equal to
     *             projectPhases;</li>
     *             <li>if projectResources contains null entries;</li>
     *             <li>if for each resources: a required field of the resource is not set : if
     *             resource.getResourceRole() is null;</li>
     *             <li>if operator is null or empty;</li>
     *             </ul>
     * @throws ProjectServicesException
     *             if there is a system error while performing the create operation
     * @since 1.4.4
     */
    public FullProjectData createProjectWithTemplate(Project projectHeader, com.topcoder.project.phases.Project projectPhases,
            Resource[] projectResources, Date multiRoundEndDate, Date endDate, String operator);
    
    /**
     * <p>
     * Creates a new contest sale and returns the created contest sale.
     * </p>
     *
     * @param contestSaleData the contest sale to create
     *
     * @return the created contest sale.
     *
     * @throws IllegalArgumentException if the arg is null.
     * @throws ProjectServicesException if any other error occurs.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    public ContestSaleData createContestSale(ContestSaleData contestSaleData) throws ProjectServicesException;

    /**
     * <p>
     * Updates a contest sale.
     * </p>
     *
     * @param contestSaleData the contest sale to update
     *
     * @throws IllegalArgumentException if the arg is null.
     * @throws ProjectServicesException if any other error occurs.
     *
     * @since TC Direct Release Assembly 7
     */
    public void updateContestSale(ContestSaleData contestSaleData) throws ProjectServicesException;

    /**
     * <p>
     * Gets contest sale by id, and return the retrieved contest sale. If
     * the contest sale doesn't exist, null is returned.
     * </p>
     *
     * @param contestSaleId the contest sale id
     *
     * @return the retrieved contest sale, or null if id doesn't exist
     *
     * @throws ProjectServicesException if any other error occurs.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    public ContestSaleData getContestSale(long contestSaleId) throws ProjectServicesException;

    /**
     * <p>
     * Gets contest sales by contest id, and return the retrieved contest sales.
     * </p>
     *
     * @param contestId the contest id of the contest sale
     *
     * @return the retrieved contest sales, or empty if none exists
     *
     * @throws ProjectServicesException if any other error occurs.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    public List<ContestSaleData> getContestSales(long contestId) throws ProjectServicesException;

    /**
     * <p>
     * Updates the contest sale data.
     * </p>
     *
     * @param contestSaleData the contest sale to update
     *
     * @throws IllegalArgumentException if the arg is null.
     * @throws ProjectServicesException if any other error occurs.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    public void editContestSale(ContestSaleData contestSaleData) throws ProjectServicesException;

    /**
     * <p>
     * Removes contest sale, return true if the contest sale exists and
     * removed successfully, return false if it doesn't exist.
     * </p>
     *
     * @param contestSaleId the contest sale id
     *
     * @return true if the contest sale exists and removed successfully,
     *         return false if it doesn't exist
     *
     * @throws ProjectServicesException if any other error occurs.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    public boolean removeContestSale(long contestSaleId) throws ProjectServicesException;

    /**
     * <p>
     * Get SimpleProjectContestData for all projects.
     * </p>
     * <p>
     * Update in v1.4.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @return List of SimpleProjectContestData
     * @throws ProjectServicesException if any error occurs
     */
    public List<SimpleProjectContestData> getSimpleProjectContestData(TCSubject tcSubject)
            throws ProjectServicesException;

    public List<SimpleProjectContestData> getSimpleProjectContestData(long pid)
            throws ProjectServicesException;

    public List<SimpleProjectContestData> getSimpleProjectContestDataByUser(String user) throws ProjectServicesException;

    /**
     * <p>
     * Gets the list of project their read/write/full permissions.
     * </p>
     * <p>
     * Update in v1.4.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param createdUser
     *            the specified user for which to get the permission
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @return the list of project their read/write/full permissions.
     *
     * @throws ProjectServicesException exception if error during retrieval from persistence.
     *
     * @since Cockpit Project Admin Release Assembly v1.0
     */
    List<SimpleProjectPermissionData> getSimpleProjectPermissionDataForUser(TCSubject tcSubject, long createdUser)
        throws ProjectServicesException;

    /**
     * Gets the list of simple pipeline data in between specified start and end date.
     * <p>
     * Update in v1.4.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param startDate
     *            the start of date range within which pipeline data for contests need to be fetched.
     * @param endDate
     *            the end of date range within which pipeline data for contests need to be fetched.
     * @param overdueContests
     *            whether to include overdue contests or not.
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @return the list of simple pipeline data for specified user id and between specified start and end date.
     * @throws ProjectServicesException
     *             if error during retrieval from database.
     * @since 1.1.1
     */
    public List<SimplePipelineData> getSimplePipelineData(TCSubject tcSubject, Date startDate, Date endDate, boolean overdueContests)
            throws ProjectServicesException;

    /**
     * Gets the list of simple pipeline data for specified user id and between specified start and end date.
     *
     * @param userId
     *            the user id.
     * @param startDate
     *            the start of date range within which pipeline data for contests need to be fetched.
     * @param endDate
     *            the end of date range within which pipeline data for contests need to be fetched.
     * @param overdueContests
     *            whether to include overdue contests or not.
     * @return the list of simple pipeline data for specified user id and between specified start and end date.
     * @throws ProjectServicesException
     *             if error during retrieval from database.
     * @since 1.1.1
     */
    public List<SimplePipelineData> getSimplePipelineData(long userId, Date startDate, Date endDate, boolean overdueContests)
            throws ProjectServicesException;


    /**
     * Retrieves a list of capacity data (date, number of scheduled contests) for the given contest type starting
     * from tomorrow.
     *
     * @param contestType the contest type
     *
     * @return the list of capacity data
     *
     * @throws ProjectServicesException if any error occurs during retrieval of information.
     *
     * @since 1.2
     */
    public List<SoftwareCapacityData> getCapacity(int contestType) throws ProjectServicesException;

    /**
     * Get all design components.
     * <p>
     * Update in v1.4.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param userId The dummy user id
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @throws ProjectServicesException if any other error occurs
     */
    public List<DesignComponents> getDesignComponents(TCSubject tcSubject, long userId) throws ProjectServicesException;

    /**
     * Get corresponding development contest's id for the design contest.
     *
     * @param contestId
     *            The contest id
     * @throws ProjectServicesException
     *             if any other error occurs
     * @since 1.2.1
     */
    public long getDevelopmentContestId(long contestId) throws ProjectServicesException;

     /**
     * check contest permission, check if a user has permission (read or write) on a contest
     *
     * @param contestId the contest id
     * @param readonly check read or write permission
     * @param userId user id
     *
     * @return true/false
     * @throws  PersistenceException
     *
     */
    public boolean checkContestPermission(long contestId, boolean readonly, long userId)  throws ProjectServicesException;

     /**
     * check contest permission, check if a user has permission (read or write) on a project
     *
     * @param tcprojectId the tc direct project id
     * @param readonly check read or write permission
     * @param userId user id
     *
     * @return true/false
     * @throws  PersistenceException
     *
     */
    public boolean checkProjectPermission(long tcprojectId, boolean readonly, long userId) throws ProjectServicesException;


     /**
     * <p>
     * get project ids by tc direct id
     * </p>
     *
     * @param tcprojectId tc direct project id
     *
     * @return list of project ids
     *
     * @throws PersistenceException if any other error occurs.
     *
     */
    public List<Long> getProjectIdByTcDirectProject(long tcprojectId) throws ProjectServicesException;


    /**
     * <p>
     * Searches the resources in the persistence store using the given filter.
     * The filter can be formed using the field names and utility methods in ResourceFilterBuilder.
     * The return will always be a non-null (possibly 0 item) array.
     * </p>
     *
     * <p>
     * In order to invoke this method correctly, one should properly set the resourceSearchBundle.
     * <pre>
     * A sample of the context of the search bundle is:
     *                  SELECT resource.resource_id
     *                  FROM resource
     *                  LEFT OUTER JOIN resource_submission
     *                  ON resource.resource_id = resource_submission.resource_id
     *                  LEFT OUTER JOIN resource_info
     *                  ON resource.resource_id = resource_info.resource_id
     *                  LEFT OUTER JOIN resource_info_type_lu
     *                  ON resource_info.resource_info_type_id =
     *                  resource_info_type_lu.resource_info_type_id
     *                  WHERE
     * </pre>
     *
     * Note, make sure the selected column is only one column and of the type: long in the configuration.
     *
     * </p>
     *
     * @param filter the filter to use
     *
     * @return The loaded resources
     *
     * @throws ProjectServicesException if there is an error executing the filter
     */
    public Resource[] searchResources(Filter filter) throws ProjectServicesException;

    /**
     * <p>
     * Updates the given resource in the persistence store.
     * </p>
     *
     * <p>
     * If the resource is new (id is UNSET_ID), then an id should be assigned and
     * the resource added to the persistence store. Otherwise the resource data
     * in the persistence store would be updated.
     * </p>
     *
     *
     * @param resource the resource to update
     * @param operator the operator making the update
     *
     * @throws IllegalArgumentException if a required field of the resource is not set (if resource.getResourceRole()
     *         is null), or if the resource role is associated with a phase type and the resource is not associated
     *         with a phase, or if resource or operator is null
     * @throws ResourcePersistenceException if there is an error updating the resource
     */
    public Resource updateResource(Resource resource, String operator) throws ProjectServicesException;


    /**
     * <p>
     * get forum id by project id
     * </p>
     *
     * @param projectId project id
     *
     * @return forum id
     *
     * @throws PersistenceException if any other error occurs.
     *
     */
    public long getForumId(long projectId) throws ProjectServicesException;



    /**
     * check if user has contest permission, it checks contest permission only (not project permission)
     *
     * @param contestId the contest id
     * @param userId user id
     *
     * @return true/false
     * @throws  PersistenceException
     *
     */
    public boolean hasContestPermission(long contestId, long userId) throws ProjectServicesException;

     /**
     * <p>
     * Removes the given resource in the persistence store (by id).
     * If the id does not exist in the persistence, nothing would be removed.
     * </p>
     *
     * @param resource the resource to remove
     * @param operator the operator making the update
     *
     * @throws IllegalArgumentException if the id of the resource is UNSET_ID, or the resource or operator is null
     * @throws ResourcePersistenceException if there is an error updating the persistence store
     */
    public void removeResource(Resource resource, String operator) throws ProjectServicesException;


     /**
     * Search resources by project id and role id
     *
     * @param projectId project id
     * @param roleId role id
     *
     * @return array of resoureces
     *
     * @throws ResourcePersistenceException if there is an error reading the persistence store.
     */
    public Resource[] searchResources(long projectId, long roleId) throws ProjectServicesException;

     /**
     * <p>
     * get tc direct project id by project id
     * </p>
     *
     * @param projectId project id
     *
     * @return tc direct project id
     *
     * @throws PersistenceException if any other error occurs.
     *
     */
    public long getTcDirectProject(long projectId) throws ProjectServicesException;

    /**
     * This method creates a Specification Review project associated to a project determined by parameter
     *
     * @param projectId the project id to create a Specification Review for
     * @param specReviewPrize the prize to set for the Specification Review project
     * @param operator the operator used to audit the operation, cannot be null or empty
     *
     * @return the created project
     *
     * @throws ProjectServicesException if any error occurs in the underlying services or if the specification
     * review already exists
     * @throws IllegalArgumentException if operator is null or empty or prize is negative.
     *
     * @since 1.3
     */
    public FullProjectData createSpecReview(long projectId, double specReviewPrize, String userId, String handle)
        throws ProjectServicesException;

    /**
     * This method retrieves scorecard and review information associated to a project determined by parameter.
     * Note: a single reviewer / review is assumed.
     *
     * @param projectId the project id to search for
     * @return the aggregated scorecard and review data
     *
     * @throws ProjectServicesException if any unexpected error occurs in the underlying services, if an invalid
     * number of reviewers or reviews are found or if the code fails to retrieve scorecard id.
     *
     * @since 1.3
     */
    public ScorecardReviewData getScorecardAndReview(long projectId) throws ProjectServicesException;

    /**
     * This method retrieves the corresponding specification review project id of a given project.
     * The code will rely on the project links to retrieve the specification project id.
     *
     * @param destProjectId the project id to search for
     *
     * @throws ProjectServicesException if any unexpected error occurs in the underlying services.
     *
     * @return the associated specification review project id, or -1 if it was not found.
     *
     * @since 1.3
     */
    public long getSpecReviewProjectId(long destProjectId) throws ProjectServicesException;

    /**
     * This method retrieves open phases names for a given project id
     *
     * @param projectId the project id to search for
     * @return a set with open phases names
     *
     * @throws ProjectServicesException if any error occurs during retrieval of information.
     *
     * @since 1.3
     */
    public Set<String> getOpenPhases(long projectId) throws ProjectServicesException;

    /**
     * This method retrieves the phases by phase type for a given project id.
     * 
     * @param projectId the project id to search for
     * @param phaseTypeName the phase type to search for
     * @return the retrieved phases
     * @throws ProjectServicesException if any error occurs during retrieval of information.
     * @since 1.4.8
     */
    public List<Phase> getPhasesByType(long projectId, String phaseTypeName) throws ProjectServicesException;
    
    /**
     * This method adds a review comment to a review. It simply delegates all logic to underlying services.
     *
     * @param reviewId the review id to add the comment to
     * @param comment the review comment to add
     *
     * @throws ProjectServicesException if any unexpected error occurs in the underlying services.
     * @throws IllegalArgumentException if comment is null or operator is null or empty
     *
     * @since 1.3
     */
    public void addReviewComment(long reviewId, Comment comment, String operator)
        throws ProjectServicesException;


    /**
     * <p>
     * update phases
     * </p>
     *
     * @param project project
     * @oaran operator operator
     *
     *
     * @throws PersistenceException if any other error occurs.
     *
     */
    public void updatePhases(com.topcoder.project.phases.Project project, String operator) throws ProjectServicesException;


    /**
     * Update the given project
     *
     * @param project
     *            The project instance to be updated into the database.
     * @param reason
     *            The update reason. It will be stored in the persistence for
     *            future references.
     * @param operator
     *            The modification user of this project.
     * @throws IllegalArgumentException
     *             if any input is null or the operator is empty string.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     * @throws ValidationException
     *             if error occurred while validating the project instance.
     */
    public void updateProject(Project project, String reason, String operator) throws ProjectServicesException;

     /**
     * <p>
     * check if it is dev only
     * </p>
     *
     * @param projectId  project id
     *
     * @return boolean
     *
     * @throws PersistenceException if any other error occurs.
     *
     */
    public boolean isDevOnly(long projectId) throws ProjectServicesException;

    /**
     * This method links the development contest to its design contest. It simply call a method in project link manager.
     *
     * @param developmentContestId the development contest id
     *
     * @throws ProjectServicesException if any unexpected error occurs in the underlying services.
     *
     * @since 1.3.1
     */
    public void linkDevelopmentToDesignContest(long developmentContestId) throws ProjectServicesException;


    /**
     * Links the parent contest with the bug hunt contest.
     *
     * @param bugHuntContestId the id of the bug hunt contest.
     * @throws ProjectServicesException if any unexpected error occurs in the underlying services.
     * @since 1.4.6
     */
    public void linkBugHuntToOtherContest(long bugHuntContestId) throws ProjectServicesException;

    /**
     * Get corresponding development contest's id for the design contest.
     *
     * @param contestId
     *            The design contestId
     * @throws ProjectServicesException
     *             if any other error occurs
     * @since 1.2.1
     */
    public long getDesignContestId(long contestId) throws ProjectServicesException;

    /**
     * Get corresponding contest id matches the same asset setting for the given contest.
     *
     * @param contestId The contest id
     * @throws ProjectServicesException if any other error occurs
     * @since 1.4.6
     */
    public long getContestId(long contestId) throws ProjectServicesException;

     /**
     * Creates re-open contest for the given contest.
     * since version 1.4.
     *
     * @param contest the contest to repost
     * @param operator the operator
     * @return new contest for the repost one
     * @throws ProjectServicesException if any error occurs
     */
    FullProjectData createReOpenContest(FullProjectData contest, String operator) throws ProjectServicesException;
    
    
    /**
     * Creates new version for development and design contest for the given contest.
     * since version 1.4.
     *
     * @param contest the contest to create new version
     * @param operator the operator
     * @return new contest for the repost one
     * @throws ProjectServicesException if any error occurs
     */
    FullProjectData createNewVersionContest(FullProjectData contest, String operator) throws ProjectServicesException;

    /**
     *  Get project only (not phase or resources)
     */
    public Project getProject(long projectId) throws ProjectServicesException;


    /**
     * Gets all resource roles in the persistence store.
     *
     * @return All resource roles in the persistence store
     * @throws ProjectServicesException
     *             If there is an error reading the persistence store.
     */
    public ResourceRole[] getAllResourceRoles() throws ProjectServicesException;

    /**
     * Gets all project platforms.
     *
     * @return all project platforms.
     * @throws ProjectServicesException if any error.
     * @since 1.7
     */
    public ProjectPlatform[] getAllProjectPlatforms() throws ProjectServicesException;

        
    /**
     * Get active contests for the given user, the contest data is stored in
     * SimpleProjectContestData and the result is returned as a list of SimpleProjectContestData.
     * 
     * @param subject the TCSubject instance.
     * @param userId the id of the user.
     * @throws ProjectServicesException is any error occrus.
     * 
     * @since 1.4.2
     */
    public List<SimpleProjectContestData> getActiveContestsForUser(TCSubject subject, long userId) throws ProjectServicesException;
	
	    /**
     * Get active/draft contests for the given user, the contest data is stored in
     * SimpleProjectContestData and the result is returned as a list of SimpleProjectContestData.
     * 
     * @param subject the TCSubject instance.
     * @param userId the id of the user.
     * @throws ProjectServicesException is any error occrus.
     * 
     * @since 1.4.2
     */
    public List<SimpleProjectContestData> getActiveDraftContestsForUser(TCSubject subject, long userId) throws ProjectServicesException;
    
    /**
     * Get project notifications of the given notification type for the given user.
     * 
     * @param userId the id of the user.
     * @param the id of the notification type.
     * @param the array of project ids to check.
     * 
     * @throws ProjectServicesException if any error occurs.
     * 
     * @since 1.4.2
     */
    public long[] getNotificationsForUser(long userId, long notificationType, long[] projectIds) throws ProjectServicesException;
    
    /**
     * Add notifications of the given projects IDs to given user.
     * 
     *  @param userId the id of the user.
     *  @param projectIds the array of project IDs.
     *  @param operator the operator.
     *  @throws ProjectServicesException if any error occurs.
     *  @since 1.4.2
     */
    public void addNotifications(long userId, long[] projectIds, String operator) throws ProjectServicesException;
    
    /**
     * Removes the notifications of the given project IDs for the given userId.
     * 
     * @param userId the id of the user.
     * @param projectIds the array of project IDs.
     * @param operator the operator.
     * @throws ProjectServicesException if any error occurs.
     * @since 1.4.2
     */
    public void removeNotifications(long userId, long[] projectIds, String operator) throws ProjectServicesException;


      /**
     * cehck if resource exists
     *
     * @param projectId project id
     * @param roleId role 
     * @param userId user id
     *
     * @return boolean
     *
     * @throws ResourcePersistenceException if there is an error reading the persistence store.
     */
    public boolean resourceExists(long projectId, long roleId, long userId) throws ProjectServicesException;


    /**
     * This method retrieves scorecard and review information associated to a project determined by parameter.
     * Note: a single reviewer / review is assumed.
     *
     * @param projectId the project id to search for.
     * @param reviewerResourceId the reviewer resource ID.
     * @return the aggregated scorecard and review data.
     * @throws ProjectServicesException if any unexpected error occurs in the underlying services, if an invalid
     * number of reviewers or reviews are found or if the code fails to retrieve scorecard id.
     * @since 1.4.3
     */
    public List<ScorecardReviewData> getScorecardAndReviews(long projectId, long reviewerResourceId) 
        throws ProjectServicesException;

    /**
     * This method retrieves checkpoint scorecard and checkpoint review information associated to a project determined by parameter.
     *
     * @param projectId the project id to search for.
     * @param reviewerResourceId the reviewer resource ID.
     * @return the aggregated scorecard and review data.
     * @throws ProjectServicesException if any unexpected error occurs in the underlying services.
     * @since 1.4.5
     */
    public List<ScorecardReviewData> getScorecardAndCheckpointReviews(long projectId, long reviewerResourceId)
        throws ProjectServicesException;
    
    /**
     * This method retrieves scorecard and review information associated to a project determined by parameter.
     * Note: a single primary screener / screen is assumed.
     *
     * @param projectId the project id to search for.
     * @param screenerId the screener ID.
     * @return the aggregated scorecard and review data.
     * @throws ProjectServicesException if any unexpected error occurs in the underlying services, if an invalid
     * number of reviewers or reviews are found or if the code fails to retrieve scorecard id.
     * @since 1.4.3
     */
    public List<ScorecardReviewData> getScorecardAndScreening(long projectId, long screenerId) 
        throws ProjectServicesException;
    
    /**
     * <p>Creates specified review for software project.</p>
     * 
     * @param review a <code>Review</code> providing the details for review to be created.
     * @throws ReviewManagementException if an unexpected error occurs.
     * @since 1.4.3 
     */
    void createReview(Review review) throws ReviewManagementException;

    /**
     * <p>Updates specified review for software project.</p>
     * 
     * @param review a <code>Review</code> providing the details for review to be updated.
     * @throws ReviewManagementException if an unexpected error occurs.
     * @since 1.4.5
     */
    void updateReview(Review review) throws ReviewManagementException;

    /**
     * Gets all FileType entities.
     *
     * @return the found FileType entities, return empty if cannot find any.
     * @throws ProjectServicesException
     *             if there are any exceptions.
     * @since 1.4.4
     */
    public FileType[] getAllFileTypes() throws ProjectServicesException;

    /**
     * <p>Gets the scorecard matching the specified ID.</p>
     * 
     * @param scorecardId a <code>long</code> providing the ID of a scorecard.
     * @return a <code>Scorecard</code> providing the details for scorecard. 
     * @throws ProjectServicesException if an unexpected error occurs.
     * @since 1.4.7
     */
    public Scorecard getScorecard(long scorecardId) throws ProjectServicesException;

    /**
     * <p>Gets the reviews of specified type for specified contest.</p>
     *
     * @param contestId a <code>long</code> providing the ID for the contest.
     * @param scorecardTypeId a <code>long</code> providing the ID for the scorecard type.
     * @return a <code>Review</code> array providing the details for found reviews.
     * @throws ProjectServicesException if an unexpected error occurs.
     * @since 1.4.7
     */
    public Review[] getReviews(long contestId, long scorecardTypeId) throws ProjectServicesException;
    
    /**
     * Gets the general feedback for software checkpoint submissions.
     * 
     * @param contestId the contest id
     * @return the general feedback, or null if there's no matching record in DB
     * @throws IllegalArgumentException if the argument is non-positive
     * @throws ProjectServicesException if any other error occurs
     * @since 1.4.9
     */
    public String getSoftwareCheckpointSubmissionsGeneralFeedback(long contestId) throws ProjectServicesException;
    
    /**
     * Saves the general feedback for software checkpoint submissions. If the feedback don't exist it will create
     * a new record, otherwise update it.
     * 
     * @param contestId the contest id
     * @param feedback the general feedback
     * @throws IllegalArgumentException if the argument contestId is non-positive
     * @throws ProjectServicesException if any other error occurs
     * @since 1.4.9
     */
    public void saveSoftwareCheckpointSubmissionsGeneralFeedback(long contestId, String feedback)
        throws ProjectServicesException;

    /**
     * <p>Gets the reviews for specified phase of specified contest.</p>
     * 
     * @param contestId a <code>long</code> providing the ID of a contest to get review for.
     * @param phaseId a <code>long</code> providing the ID of a contest phase to get reviews for,
     * @return a <code>Review</code> array listing the reviews for the specified phase of specified contest. 
     * @throws ProjectServicesException if an unexpected error occurs.
     * @since 1.4.10
     */
    public Review[] getReviewsByPhase(long contestId, long phaseId) throws ProjectServicesException;

    /**
     * Creates the contest milestone relationship.
     *
     * @param projectId the id of the contest
     * @param milestoneId the id of the direct project milestone
     * @param operator the operator
     * @throws ProjectServicesException if any error occurs
     * @since 1.5
     */
    public void createProjectMilestoneRelation(long projectId, long milestoneId, String operator) throws ProjectServicesException;

    /**
     * Updates the contest milestone relationship.
     *
     * @param projectId the id of the contest
     * @param milestoneId the id of the direct project milestone
     * @param operator the operator
     * @throws ProjectServicesException if any error occurs
     * @since 1.5
     */
    public void updateProjectMilestoneRelation(long projectId, long milestoneId, String operator) throws ProjectServicesException;

    /**
     * Deletes the contest milestone relationship.
     *
     * @param projectId the id of the contest
     * @param operator the operator
     * @throws ProjectServicesException if any error occurs
     * @since 1.5
     */
    public void deleteProjectMilestoneRelation(long projectId, String operator) throws ProjectServicesException;

    /**
     * Deletes all the contests associations to the specified milestone.
     *
     * @param milestoneId the id of the milestone.
     * @param operator the operator.
     * @throws ProjectServicesException if any error occurs.
     * @since 1.6
     */
    public void deleteMilestoneProjectRelations(long milestoneId, String operator) throws ProjectServicesException;

    /**
     * Gets the contest milestone relationship.
     *
     * @param projectId the id of the contest
     * @return the project milestone id
     * @throws ProjectServicesException if any error occurs
     * @since 1.5
     */
    public long getProjectMilestoneRelation(long projectId) throws ProjectServicesException;

    /**
     * Gets the <code>com.topcoder.project.phases.Project</code> phases by the give project id.
     *
     *
     * @param projectId the id of the challenge
     * @return the <code>com.topcoder.project.phases.Project</code>
     * @throws ProjectServicesException if any other error occurs.
     * @since 1.8
     */
    public com.topcoder.project.phases.Project getPhases(long projectId) throws ProjectServicesException;
}
