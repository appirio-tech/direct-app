/*
 * Copyright (C) 2006 - 2017 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.topcoder.management.project.FileType;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectPlatform;
import com.topcoder.management.project.SimplePipelineData;
import com.topcoder.management.project.SimpleProjectContestData;
import com.topcoder.management.project.SimpleProjectPermissionData;
import com.topcoder.management.project.SoftwareCapacityData;
import com.topcoder.management.project.DesignComponents;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;

import com.topcoder.management.review.ReviewManagementException;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.Review;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.service.ConfigurationException;
import com.topcoder.project.service.ContestSaleData;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.project.service.ProjectDoesNotExistException;
import com.topcoder.project.service.ProjectServices;
import com.topcoder.project.service.ProjectServicesException;
import com.topcoder.project.service.ProjectServicesFactory;
import com.topcoder.project.service.ScorecardReviewData;
import com.topcoder.project.service.Util;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.security.RolePrincipal;
import com.topcoder.security.TCSubject;
import com.topcoder.service.user.UserService;
import com.topcoder.util.errorhandling.ExceptionUtils;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * This is the Stateless Enterprise bean which wraps all methods of <code>ProjectServices</code> interface. It delegates
 * the business logic to a ProjectServices instance. This instance is retrieved from <code>ProjectServicesFactory</code>
 * , using or not the namespace (it is optional). It has the <b>@Stateless</b> annotation. Stateless refers to
 * javax.ejb.Stateless. It implements both Local and Remote interface. It has also the
 * <b>@TransactionAttribute(TransactionAttributeType.REQUIRED)</b> annotation, to define the required
 * <code>transactions.TransactionAttribute</code> refers to TransactionAttributeType enum. It's sufficient to define
 * this annotation to entire class, it will be applied for default to every method.
 * </p>
 * <p>
 * It needs not to use the <b>@Local</b> and <b>@Remote</b> annotations in this case. It is not recommended to use this
 * approach unless you have to, as implementing the business interfaces directly enforces the contract between the bean
 * class and these interfaces.
 * </p>
 * <p>
 * It will provide streamlined access to project information. It will allow for a simple search for full or basic
 * project information, or to use custom search criteria to locate projects, either in its full or basic form. The basic
 * form involves getting the <code>Project</code> object (from <b>Project Management</b>), and the full form involves
 * the <code>FullProjectData</code> object, which not only provides information as the basic form, but also provides
 * project phase information, all resources participating in the project, and all teams currently existing in it.
 * Furthermore, it provides data about the technologies involved in this project (such as Java, C#).
 * </p>
 * <p>
 * Here is the sample XML deployment descriptor of this stateless bean:
 *
 * <pre>
 * &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
 *  &lt;ejb-jar xmlns=&quot;http://java.sun.com/xml/ns/javaee&quot;
 *  xmlns:xsi=&quot;http://www.w3.org/2001/XMLSchema-instance&quot;
 *  xsi:schemaLocation=&quot;http://java.sun.com/xml/ns/javaee
 *  http://java.sun.com/xml/ns/javaee/ejb-jar_3_0.xsd&quot;
 *  version=&quot;3.0&quot;&gt;
 *  &lt;enterprise-beans&gt;
 *  &lt;session&gt;
 *  &lt;ejb-name&gt;ProjectServicesBean&lt;/ejb-name&gt;
 *  &lt;env-entry&gt;
 *  &lt;env-entry-name&gt;projectServicesFactoryNamespace&lt;/env-entry-name&gt;
 *  &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *  &lt;env-entry-value&gt;ProjectServicesFactory&lt;/env-entry-value&gt;
 *  &lt;/env-entry&gt;
 *  &lt;env-entry&gt;
 *  &lt;env-entry-name&gt;loggerName&lt;/env-entry-name&gt;
 *  &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *  &lt;env-entry-value&gt;defaultLogger&lt;/env-entry-value&gt;
 *  &lt;/env-entry&gt;
 *  &lt;/session&gt;
 *  &lt;/enterprise-beans&gt;
 *  &lt;/ejb-jar&gt;
 * </pre>
 *
 * </p>
 * <p>
 * Module Contest Service Software Contest Sales Assembly change: new methods added to support creating/updating/query
 * contest sale for software contest.
 * </p>
 * <p>
 * Updated for Cockpit Project Admin Release Assembly v1.0: new methods added to support retrieval of project and their
 * permissions.
 * </p>
 * <p>
 * Version 1.1.1 (Cockpit Pipeline Release Assembly 1 v1.0) Change Notes: - Introduced method to retrieve
 * SimplePipelineData for given date range.
 * </p>
 * <p>
 * Version 1.2 (Cockpit Pipeline Release Assembly 2 - Capacity) changelog: - added service that retrieves a list of
 * capacity data (date, number of scheduled contests) starting from tomorrow for a given contest type
 * </p>
 * <p>
 * Changes in v1.2.1 - Cockpit Release Assembly 11 Add method getDesignComponents to get design components.
 * </p>
 * <p>
 * Version 1.2.2 (Cockpit Contest Eligibility) changelog: - added a method for create private contest's roles
 * </p>
 * <p>
 * Version 1.3 (Cockpit Spec Review Backend Service Update v1.0) changelog: - Added project link, scorecard and review
 * managers creation. - Added method to create specification review project for an existing project. - Added method to
 * get scorecard and review information for a specific project. - Added method to get the corresponding specification
 * review project id for a given project id. - Added method to get open phases names for a given project id. - Added
 * method to add comments to an existing review.
 * </p>
 * <p>
 * Changes in v1.4 (Cockpit Release Assembly - Contest Repost and New Version v1.0): - Added method to re open failed
 * software contest. - Added method to create new version for development or design contest.
 * </p>
 * <p>
 * Changes in v1.4.1(Cockpit Security Facade V1.0)
 *  - findAllTcDirectProjects, findAllTcDirectProjectsForUser,
 *    getSimplePipelineData, getDesignComponents these methods add paremeter TCSubject in order to replacing
 *    the current permission checking security info.
 * </p>
 *  <p>
 * Changes in BUGR-3706
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
 * <p>
 * Version 1.4.6 (Release Assembly - TopCoder Bug Hunt Assembly Integration 2) change log:
 * <ul>
 *   <li>
 *     Add method {@link #linkBugHuntToOtherContest(long)} to link bug hunt contest with its parent contest (contest
 *     which shares the same asset setting for it)
 *   </li>  
 * </ul>
 * </p>
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
 * Version 1.4.8 (Release Assembly - TopCoder Cockpit Software Checkpoint Management) change notes:
 * <ol><li>
 *     - Added method {@link #saveSoftwareCheckpointSubmissionsGeneralFeedback(long, String)},
 *     {@link #getSoftwareCheckpointSubmissionsGeneralFeedback(long)} to support
 *     software checkpoint management.
 * </li></ol>
 * </p>
 *
 * <p>
 * Version 1.4.9 (Module Assembly - TC Cockpit - Studio - Final Fixes Integration Part One Assembly) Change notes:
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
 * Version 1.9 (Topcoder - Ability To Set End Date For Registration Phase and Submission Phase)
 * <ul>
 *     <li>Updated createProjectWithTemplate and updateProject methods to take an extra regEndDate argument</li>
 * </ul>
 * </p>
 *
 * @author fabrizyo, znyyddf, pulky, murphydog, waits, hohosky, isv, GreatKevin, TCSCODER
 * @version 1.9
 * @since 1.0
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ProjectServicesBean implements ProjectServicesLocal, ProjectServicesRemote {

    /**
     * Private constant specifying user role.
     */
    //private static final String USER_ROLE = "Cockpit User";

    /**
     * Private constant specifying administrator role.
     */
    private static final String ADMIN_ROLE = "Cockpit Administrator";

    /**
     * <p>
     * Represents the <code>SessionContext</code> injected by the <b>EJB</b> container automatically and is never
     * changed afterwards.
     * </p>
     * <p>
     * It is injected using <b>@Resource</b> annotation, and it can be accessed in the getter method.
     * </p>
     */
    @javax.annotation.Resource
    private SessionContext sessionContext;

    /**
     * <p>
     * Represents the projectServicesFactory namespace used to load the projectServices delegating instance. It is
     * optional and can be null, but can't be empty.
     * </p>
     * <p>
     * It is injected using <b>@Resource(name=&quot;projectServicesFactoryNamespace&quot;)</b> annotation, the env-entry
     * in deployment descriptor will have the &quot;projectServicesFactoryNamespace&quot; name.
     * </p>
     */
    @javax.annotation.Resource(name = "projectServicesFactoryNamespace")
    private String projectServicesFactoryNamespace;

    /**
     * <p>
     * Represents the logger name used to load the Log instance. It is optional and can be null, but can't be empty.
     * </p>
     * <p>
     * It is injected using <b>@Resource(name=&quot;loggerName&quot;)</b> annotation, the env-entry in deployment
     * descriptor will have the &quot;loggerName&quot; name.
     * </p>
     * <p>
     * If it is not present then the logging is not performed. If it is present then the Log instance is created using
     * LogManager.getLog(loggerName). It is retrieved for each method because the bean is stateless.
     * </p>
     */
    @javax.annotation.Resource(name = "loggerName")
    private String logName;
    /**
     * <p>
     * A <code>UserService</code> providing access to available <code>User Service EJB</code>.
     * </p>
     */
    @EJB(name = "ejb/UserService")
    private UserService userService = null;
    /**
     * <p>
     * Used extensively in this class to log information. This will include logging method entry and exit, errors, debug
     * information for calls to other components, etc.
     * </p>
     * <p>
     * Note that logging is optional and can be null, in which case, no logging will take place. It will be set in the
     * constructor and will not change.
     * </p>
     */
    private Log logger;

    /**
     * <p>
     * This is the default constructor.
     * </p>
     */
    public ProjectServicesBean() {
        // does nothing
    }

    /**
     * <p>
     * This method is called after this bean is constructed by the EJB container.
     * </p>
     * <p>
     * It assigns the logger field using logName property value, which is assigned by J2EE container.
     * </p>
     */
    @SuppressWarnings("unused")
    @PostConstruct
    private void initialize() {
        logger = logName != null ? LogManager.getLog(logName) : null;
    }

    /**
     * <p>
     * This method finds all active projects along with all known associated information. Returns empty array if no
     * projects found.
     * </p>
     *
     * @return FullProjectData array with full projects info, or empty array if none found
     * @throws ProjectServicesException If there is a system error while performing the search
     */
    public FullProjectData[] findActiveProjects() {
        String method = "ProjectServicesBean#findActiveProjects() method.";

        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            return getProjectServices().findActiveProjects();
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * <p>
     * This method finds all active projects. Returns empty array if no projects found.
     * </p>
     *
     * @return Project array with project info, or empty array if none found
     * @throws ProjectServicesException If there is a system error while performing the search
     */
    public Project[] findActiveProjectsHeaders() {
        String method = "ProjectServicesBean#findActiveProjectsHeaders() method.";

        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            return getProjectServices().findActiveProjectsHeaders();
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * <p>
     * Checks if the login user is of given role
     * </p>
     *
     * @param tcSubject TCSubject instance for login user
     * @return true if it is given role
     */
    private static boolean isRole(TCSubject tcSubject, String roleName) {
        Set<RolePrincipal> roles = tcSubject.getPrincipals();
        if (roles != null) {
            for (RolePrincipal role : roles) {
                if (role.getName().equalsIgnoreCase(roleName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * <p>
     * Get the user-name for current login user represented by tcSubject.
     * </p>
     * @param tcSubject TCSubject instance for login user
     * @return user name
     * @throws ProjectServicesException fail to retrieve user-name
     */
    private String getUserName(TCSubject tcSubject) throws ProjectServicesException {
        try {
            return this.userService.getUserHandle(tcSubject.getUserId());
        } catch (Exception e) {
            throw new ProjectServicesException("Fail to get the user-name by user-id" + tcSubject.getUserId(), e);
        }
    }

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
    public Project[] findAllTcDirectProjects(TCSubject tcSubject) {
        String method = "ProjectServicesBean#findAllTcDirectProjects(TCSubject) method.";

        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            String userName = getUserName(tcSubject);
            if (isRole(tcSubject, ADMIN_ROLE)) {
                Util.log(logger, Level.INFO, "User is admin.");
                return getProjectServices().findAllTcDirectProjects(tcSubject);
            } else  {
                Util.log(logger, Level.INFO, "User is non-admin.");
                return getProjectServices().findAllTcDirectProjectsForUser(tcSubject, userName);
            }


        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }

    }

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
    public Project[] findAllTcDirectProjectsForUser(TCSubject tcSubject, String user) {
        String method = "ProjectServicesBean#findAllTcDirectProjectsForUser(TCSubject, user) method.";

        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            if (isRole(tcSubject, ADMIN_ROLE)) {
                Util.log(logger, Level.INFO, "User is admin.");
                return getProjectServices().findAllTcDirectProjectsForUser(tcSubject, user);
            }
            String userName = getUserName(tcSubject);

            Util.log(logger, Level.INFO, "User " + userName + " is non-admin.");
            if (userName.equals(user)) {
                return getProjectServices().findAllTcDirectProjectsForUser(tcSubject, user);
            } else {
                return new Project[0];
            }

        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * <p>
     * This method finds all projects along with all known associated information that match the search criteria.
     * Returns empty array if no projects found.
     * </p>
     *
     * @return FullProjectData array with full projects info, or empty array if none found
     * @param filter The search criteria to filter projects
     * @throws IllegalArgumentException If filter is null
     * @throws ProjectServicesException If there is a system error while performing the search
     */
    public FullProjectData[] findFullProjects(Filter filter) {
        String method = "ProjectServicesBean#findFullProjects(Filter filter) method.";

        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            return getProjectServices().findFullProjects(filter);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * <p>
     * This method finds all projects that match the search criteria. Returns empty array if no projects found.
     * </p>
     *
     * @return Project array with project info, or empty array if none found
     * @param filter The search criteria to filter projects
     * @throws IllegalArgumentException If filter is null
     * @throws ProjectServicesException If there is a system error while performing the search
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Project[] findProjectsHeaders(Filter filter) {
        String method = "ProjectServicesBean#findProjectsHeaders(Filter filter) method.";

        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            return getProjectServices().findProjectsHeaders(filter);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * <p>
     * This method retrieves the project along with all known associated information. Returns null if not found.
     * </p>
     * <p>
     * Module Contest Service Software Contest Sales Assembly change: fetch the contest sale info.
     * </p>
     *
     * @return the project along with all known associated information
     * @param projectId The ID of the project to retrieve
     * @throws IllegalArgumentException If projectId is negative
     * @throws ProjectServicesException If there is a system error while performing the search
     */
    public FullProjectData getFullProjectData(long projectId) {
        String method = "ProjectServicesBean#getFullProjectData(long projectId) method.";

        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            return getProjectServices().getFullProjectData(projectId);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * <p>
     * Persist the project and all related data. All ids (of project header, project phases and resources) will be
     * assigned as new, for this reason there is no exception like 'project already exists'.
     * </p>
     * <p>
     * First it persist the projectHeader a com.topcoder.management.project.Project instance. Its properties and
     * associating scorecards, the operator parameter is used as the creation/modification user and the creation date
     * and modification date will be the current date time when the project is created. The id in Project will be
     * ignored: a new id will be created using ID Generator (see Project Management CS). This id will be set to Project
     * instance.
     * </p>
     * <p>
     * Then it persist the phases a com.topcoder.project.phases.Project instance. The id of project header previous
     * saved will be set to project Phases. The phases' ids will be set to 0 (id not set) and then new ids will be
     * created for each phase after persist operation.
     * </p>
     * <p>
     * At last it persist the resources, they can be empty.The id of project header previous saved will be set to
     * resources. The ids of resources' phases ids must be null. See &quot;id problem with resources&quot; thread in
     * design forum. The resources could be empty or null, null is treated like empty: no resources are saved. The
     * resources' ids will be set to UNSET_ID of Resource class and therefore will be persisted as new resources'.
     * </p>
     * <p>
     * Module Contest Service Software Contest Sales Assembly change: return the wrapped value for project header,
     * phases, resources info.
     * </p>
     *
     * @param projectHeader the project's header, the main project's data
     * @param projectPhases the project's phases
     * @param projectResources the project's resources, can be null or empty, can't contain null values. Null is treated
     *            like empty.
     * @param operator the operator used to audit the operation, can be null but not empty
     * @return the created project.
     * @throws IllegalArgumentException if any case in the following occurs:
     *             <ul>
     *             <li>if projectHeader is null;</li>
     *             <li>if projectPhases is null or the phases of projectPhases are empty;</li>
     *             <li>if the project of phases (for each phase: phase.project) is not equal to projectPhases;</li>
     *             <li>if projectResources contains null entries;</li>
     *             <li>if for each resources: a required field of the resource is not set : if
     *             resource.getResourceRole() is null;</li>
     *             <li>if operator is null or empty;</li>
     *             </ul>
     * @throws ProjectServicesException if there is a system error while performing the create operation
     */
    public FullProjectData createProject(Project projectHeader,
            com.topcoder.project.phases.Project projectPhases, Resource[] projectResources, String operator) {
        String method = "ProjectServicesBean#createProject(Project projectHeader, com.topcoder.project.phases.Project"
                + " projectPhases, Resource[] projectResources, String operator) method.";

        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            return getProjectServices().createProject(projectHeader, projectPhases, projectResources, operator);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * <p>
     * Update the project and all related data. First it updates the projectHeader a
     * com.topcoder.management.project.Project instance. All related items will be updated. If items are removed from
     * the project, they will be deleted from the persistence. Likewise, if new items are added, they will be created in
     * the persistence. For the project, its properties and associating scorecards, the operator parameter is used as
     * the modification user and the modification date will be the current date time when the project is updated. See
     * the source code of Project Management component, ProjectManager: there is a 'reason' parameter in updateProject
     * method.
     * </p>
     * <p>
     * Then it updates the phases a com.topcoder.project.phases.Project instance. The id of projectHeader previous saved
     * must be equal to projectPhases' id. The projectPhases.phases.project's id must be equal to projectHeader's id.
     * The phases of the specified project are compared to the phases already in the database. If any new phases are
     * encountered, they are added to the persistent store. If any phases are missing from the input, they are deleted.
     * All other phases are updated.
     * </p>
     * <p>
     * At last it updates the resources, they can be empty. Any resources in the array with UNSET_ID are assigned an id
     * and updated in the persistence. Any resources with an id already assigned are updated in the persistence. Any
     * resources associated with the project in the persistence store, but not appearing in the array are removed. The
     * resource.project must be equal to projectHeader's id. The resources which have a phase id assigned ( a resource
     * could not have the phase id assigned), must have the phase id contained in the projectPhases.phases' ids.
     * </p>
     *
     * @param projectHeader the project's header, the main project's data
     * @param projectHeaderReason the reason of projectHeader updating.
     * @param projectPhases the project's phases
     * @param projectResources the project's resources, can be null or empty, can't contain null values. Null is treated
     *            like empty.
     * @param operator the operator used to audit the operation, can be null but not empty
     * @throws IllegalArgumentException if any case in the following occurs:
     *             <ul>
     *             <li>if projectHeader is null or projectHeader.id is nonpositive;</li>
     *             <li>if projectHeaderReason is null or empty;</li>
     *             <li>if projectPhases is null, or if the phases of projectPhases are empty, or if the projectPhases.id
     *             is not equal to projectHeader.id, or for each phase: if the phase.object is not equal to
     *             projectPhases;</li>
     *             <li>if projectResources contains null entries;</li>
     *             <li>for each resource: if resource.getResourceRole() is null, or if the resource role is associated
     *             with a phase type but the resource is not associated with a phase, or if the resource.phase (id of
     *             phase) is set but it's not in projectPhases.phases' ids, or if the resource.project (project's id) is
     *             not equal to projectHeader's id;</li>
     *             <li>if operator is null or empty;</li>
     *             </ul>
     * @throws ProjectDoesNotExistException if the project doesn't exist in persistent store.
     * @throws ProjectServicesException if there is a system error while performing the update operation
     */
    public FullProjectData updateProject(Project projectHeader, String projectHeaderReason,
            com.topcoder.project.phases.Project projectPhases, Resource[] projectResources, String operator) {
        String method = "ProjectServicesBean#updateProject(Project projectHeader,"
                + " String projectHeaderReason, com.topcoder.project.phases.Project projectPhases,"
                + " Resource[] projectResources, String operator) method.";

        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            return getProjectServices().updateProject(projectHeader, projectHeaderReason, projectPhases,
                    projectResources, operator);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

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
            com.topcoder.project.phases.Project projectPhases, Resource[] projectResources, Date multiRoundEndDate, String operator) {
        String method = "ProjectServicesBean#updateProject(Project projectHeader,"
            + " String projectHeaderReason, com.topcoder.project.phases.Project projectPhases,"
            + " Resource[] projectResources, Date multiRoundEndDate, String operator) method.";

        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            return getProjectServices().updateProject(projectHeader, projectHeaderReason, projectPhases,
                    projectResources, multiRoundEndDate, operator);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

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
	 * @param regEndDate the registration end date
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
            com.topcoder.project.phases.Project projectPhases, Resource[] projectResources, Date regEndDate, Date multiRoundEndDate, Date endDate, String operator) {
        String method = "ProjectServicesBean#updateProject(Project projectHeader,"
            + " String projectHeaderReason, com.topcoder.project.phases.Project projectPhases,"
            + " Resource[] projectResources, Date regEndDate, Date multiRoundEndDate, Date endDate, String operator) method.";

        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            return getProjectServices().updateProject(projectHeader, projectHeaderReason, projectPhases,
                    projectResources, regEndDate, multiRoundEndDate, endDate, operator);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }
    
    /**
     * <p>
     * Creates a new contest sale and returns the created contest sale.
     * </p>
     *
     * @param contestSaleData the contest sale to create
     * @return the created contest sale.
     * @throws IllegalArgumentException if the arg is null.
     * @throws ProjectServicesException if any other error occurs.
     * @since Module Contest Service Software Contest Sales Assembly
     */
    public ContestSaleData createContestSale(ContestSaleData contestSaleData)
            throws ProjectServicesException {
        String method = "ProjectServicesBean#createContestSale(ContestSaleData contestSaleData) method.";

        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            return getProjectServices().createContestSale(contestSaleData);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * <p>
     * Updates a contest sale.
     * </p>
     *
     * @param contestSaleData the contest sale to update
     * @throws IllegalArgumentException if the arg is null.
     * @throws ProjectServicesException if any other error occurs.
     * @since TC Direct Release Assembly 7
     */
    public void updateContestSale(ContestSaleData contestSaleData)
            throws ProjectServicesException {
        String method = "ProjectServicesBean#updateContestSale(ContestSaleData contestSaleData) method.";

        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            getProjectServices().updateContestSale(contestSaleData);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }
    
    /**
     * <p>
     * Gets contest sale by id, and return the retrieved contest sale. If the contest sale doesn't exist, null is
     * returned.
     * </p>
     *
     * @param contestSaleId the contest sale id
     * @return the retrieved contest sale, or null if id doesn't exist
     * @throws ProjectServicesException if any other error occurs.
     * @since Module Contest Service Software Contest Sales Assembly
     */
    public ContestSaleData getContestSale(long contestSaleId) throws ProjectServicesException {
        String method = "ProjectServicesBean#getContestSale(long contestSaleId) method.";

        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            return getProjectServices().getContestSale(contestSaleId);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * <p>
     * Gets contest sales by contest id, and return the retrieved contest sales.
     * </p>
     *
     * @param contestId the contest id of the contest sale
     * @return the retrieved contest sales, or empty if none exists
     * @throws ProjectServicesException if any other error occurs.
     * @since Module Contest Service Software Contest Sales Assembly
     */
    public List<ContestSaleData> getContestSales(long contestId) throws ProjectServicesException {
        String method = "ProjectServicesBean#getContestSales(long contestId) method.";

        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            return getProjectServices().getContestSales(contestId);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * <p>
     * Updates the contest sale data.
     * </p>
     *
     * @param contestSaleData the contest sale to update
     * @throws IllegalArgumentException if the arg is null.
     * @throws ProjectServicesException if any other error occurs.
     * @since Module Contest Service Software Contest Sales Assembly
     */
    public void editContestSale(ContestSaleData contestSaleData) throws ProjectServicesException {
        String method = "ProjectServicesBean#editContestSale(ContestSaleData contestSale) method.";

        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            getProjectServices().editContestSale(contestSaleData);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * <p>
     * Removes contest sale, return true if the contest sale exists and removed successfully, return false if it doesn't
     * exist.
     * </p>
     *
     * @param contestSaleId the contest sale id
     * @return true if the contest sale exists and removed successfully, return false if it doesn't exist
     * @throws ProjectServicesException if any other error occurs.
     * @since Module Contest Service Software Contest Sales Assembly
     */
    public boolean removeContestSale(long contestSaleId) throws ProjectServicesException {
        String method = "ProjectServicesBean#removeContestSale(long contestSaleId) method.";

        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            return getProjectServices().removeContestSale(contestSaleId);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * <p>
     * Return the session context of this bean.
     * </p>
     *
     * @return the session context of this bean.
     */
    protected SessionContext getSessionContext() {
        String method = "ProjectServicesBean#getSessionContext() method.";
        Util.log(logger, Level.INFO, "Enters " + method);
        Util.log(logger, Level.INFO, "Exits " + method);
        return sessionContext;
    }

    /**
     * <p>
     * Gets the projectServices from <code>ProjectServicesFactory</code>.
     * </p>
     *
     * @return the projectServices
     * @throws ProjectServicesException if any configuration error occurs
     */
    private ProjectServices getProjectServices() {
        ProjectServices projectServices;

        try {
            if (projectServicesFactoryNamespace != null) {
                // If the projectServicesFactoryNamespace is not null then get the projectServices
                // instance from ProjectServicesFactory using namespace
                projectServices = ProjectServicesFactory.getProjectServices(projectServicesFactoryNamespace);
            } else {
                // otherwise get the projectServices instance calling the ProjectServicesFactory's
                // method without namespace
                projectServices = ProjectServicesFactory.getProjectServices();
            }
        } catch (ConfigurationException e) {
            throw new ProjectServicesException(
                    "ConfigurationException occurred while getting ProjectServices from ProjectServicesFactory.", e);
        }

        return projectServices;
    }

    /**
     * <p>
     * Persist the project and all related data. All ids (of project header, project phases and resources) will be
     * assigned as new, for this reason there is no exception like 'project already exists'.
     * </p>
     * <p>
     * First it persist the projectHeader a com.topcoder.management.project.Project instance. Its properties and
     * associating scorecards, the operator parameter is used as the creation/modification user and the creation date
     * and modification date will be the current date time when the project is created. The id in Project will be
     * ignored: a new id will be created using ID Generator (see Project Management CS). This id will be set to Project
     * instance.
     * </p>
     * <p>
     * Then it persist the phases a com.topcoder.project.phases.Project instance. The id of project header previous
     * saved will be set to project Phases. The phases' ids will be set to 0 (id not set) and then new ids will be
     * created for each phase after persist operation.
     * </p>
     * <p>
     * At last it persist the resources, they can be empty.The id of project header previous saved will be set to
     * resources. The ids of resources' phases ids must be null. See &quot;id problem with resources&quot; thread in
     * design forum. The resources could be empty or null, null is treated like empty: no resources are saved. The
     * resources' ids will be set to UNSET_ID of Resource class and therefore will be persisted as new resources's.
     * </p>
     *
     * @param projectHeader the project's header, the main project's data
     * @param projectPhases the project's phases
     * @param projectResources the project's resources, can be null or empty, can't contain null values. Null is treated
     *            like empty.
     * @param operator the operator used to audit the operation, can be null but not empty
     * @throws IllegalArgumentException if any case in the following occurs:
     *             <ul>
     *             <li>if projectHeader is null;</li>
     *             <li>if projectPhases is null;</li>
     *             <li>if the project of phases (for each phase: phase.project) is not equal to projectPhases;</li>
     *             <li>if projectResources contains null entries;</li>
     *             <li>if for each resources: a required field of the resource is not set : if
     *             resource.getResourceRole() is null;</li>
     *             <li>if operator is null or empty;</li>
     *             </ul>
     * @throws ProjectServicesException if there is a system error while performing the create operation
     * @since BUGR-1473
     */
    public FullProjectData createProjectWithTemplate(Project projectHeader,
            com.topcoder.project.phases.Project projectPhases, Resource[] projectResources, String operator) {
        String method = "ProjectServicesBean#createProjectWithTemplate(Project projectHeader, com.topcoder.project.phases.Project"
                + " projectPhases, Resource[] projectResources, String operator) method.";

        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            return getProjectServices().createProjectWithTemplate(projectHeader, projectPhases, projectResources,
                    operator);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }
    
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
            Resource[] projectResources, Date multiRoundEndDate, String operator) {
        String method = "ProjectServicesBean#createProjectWithTemplate(Project projectHeader, com.topcoder.project.phases.Project"
            + " projectPhases, Resource[] projectResources, Date multiRoundEndDate, String operator) method.";

        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            return getProjectServices().createProjectWithTemplate(projectHeader, projectPhases, projectResources,
                    multiRoundEndDate, operator);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }
    
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
	 * @param regEndDate the registration end date
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
     * @since 1.4.5
     */
    public FullProjectData createProjectWithTemplate(Project projectHeader, com.topcoder.project.phases.Project projectPhases,
            Resource[] projectResources, Date regEndDate, Date multiRoundEndDate, Date endDate, String operator) {
        String method = "ProjectServicesBean#createProjectWithTemplate(Project projectHeader, com.topcoder.project.phases.Project"
            + " projectPhases, Resource[] projectResources, Date regEndDate, Date multiRoundEndDate, Date endDate, String operator) method.";

        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            return getProjectServices().createProjectWithTemplate(projectHeader, projectPhases, projectResources,
                    regEndDate, multiRoundEndDate, endDate, operator);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }
    
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
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SimpleProjectContestData> getSimpleProjectContestData(TCSubject tcSubject)
            throws ProjectServicesException {

        String method = "ProjectServicesBean#getSimpleProjectContestData(TCSubject) method.";

        Util.log(logger, Level.INFO, "Enters " + method);

        try {

            if (isRole(tcSubject, ADMIN_ROLE)) {
                return getProjectServices().getSimpleProjectContestData(tcSubject);
            } else {
                return getProjectServices().getSimpleProjectContestDataByUser(String.valueOf(tcSubject.getUserId()));
            }

        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }

    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SimpleProjectContestData> getSimpleProjectContestData(long pid)
            throws ProjectServicesException {
        String method = "ProjectServicesBean#getSimpleProjectContestData(pid) method.";

        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            return getProjectServices().getSimpleProjectContestData(pid);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SimpleProjectContestData> getSimpleProjectContestDataByUser(String user)
            throws ProjectServicesException {
        String method = "ProjectServicesBean#getSimpleProjectContestDataByUser(user) method.";

        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            return getProjectServices().getSimpleProjectContestDataByUser(user);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

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
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SimpleProjectPermissionData> getSimpleProjectPermissionDataForUser(TCSubject tcSubject, long createdUser)
            throws ProjectServicesException {
        String method = "ProjectServicesBean#getSimpleProjectPermissionDataForUser(TCSubject, createdUser) method.";

        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            List<SimpleProjectPermissionData> contests = null;

            if (createdUser < 0) {
                // retrieve data for current user
                if (isRole(tcSubject, ADMIN_ROLE)) {
                    Util.log(logger, Level.DEBUG, "User is admin.");
                    contests = getProjectServices().getSimpleProjectPermissionDataForUser(tcSubject,-1);

                } else {
                    Util.log(logger, Level.DEBUG, "User  is non-admin.");
                    contests = getProjectServices().getSimpleProjectPermissionDataForUser(tcSubject, tcSubject.getUserId());
                }

            } else {
                contests = getProjectServices().getSimpleProjectPermissionDataForUser(tcSubject, createdUser);
            }

            if (contests == null)
                contests = new ArrayList<SimpleProjectPermissionData>();

            return contests;

        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * Gets the list of simple pipeline data in between specified start and end date.
     *
     * @param startDate the start of date range within which pipeline data for contests need to be fetched.
     * @param endDate the end of date range within which pipeline data for contests need to be fetched.
     * @param overdueContests whether to include overdue contests or not.
     * @return the list of simple pipeline data for specified user id and between specified start and end date.
     * @throws ProjectServicesException if error during retrieval from database.
     * @since 1.1.1
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SimplePipelineData> getSimplePipelineData(TCSubject tcSubject, Date startDate, Date endDate,
            boolean overdueContests) throws ProjectServicesException {
        String method = "ProjectServicesBean#getSimplePipelineData(TCSubject, startDate,endDate,overdueContests) method.";

        Util.log(logger, Level.INFO, "Enters " + method);

        try {

            return getProjectServices().getSimplePipelineData(tcSubject.getUserId(), startDate, endDate, overdueContests);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * Gets the list of simple pipeline data for specified user id and between specified start and end date.
     *
     * @param userId the user id.
     * @param startDate the start of date range within which pipeline data for contests need to be fetched.
     * @param endDate the end of date range within which pipeline data for contests need to be fetched.
     * @param overdueContests whether to include overdue contests or not.
     * @return the list of simple pipeline data for specified user id and between specified start and end date.
     * @throws ProjectServicesException if error during retrieval from database.
     * @since 1.1.1
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SimplePipelineData> getSimplePipelineData(long userId, Date startDate,
            Date endDate, boolean overdueContests) throws ProjectServicesException {
        String method = "ProjectServicesBean#getSimplePipelineData(userId, startDate,endDate,overdueContests) method.";

        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            return getProjectServices().getSimplePipelineData(userId, startDate, endDate, overdueContests);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * Retrieves a list of capacity data (date, number of scheduled contests) for the given contest type starting from
     * tomorrow.
     *
     * @param contestType the contest type
     * @return the list of capacity data
     * @throws ProjectServicesException if any error occurs during retrieval of information.
     * @since 1.2
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SoftwareCapacityData> getCapacity(int contestType) throws ProjectServicesException {
        String method = "ProjectServicesBean#getCapacity(" + contestType + ") method.";

        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            return getProjectServices().getCapacity(contestType);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * Get all design components.
     * <p>
     * Update in v1.4.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param userId The dummy user id
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @throws ProjectServicesException if any other error occurs
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<DesignComponents> getDesignComponents(TCSubject tcSubject, long userId) throws ProjectServicesException {
        String method = "ProjectServicesBean#getDesignComponents(TCSubject, userId) method.";

        Util.log(logger, Level.INFO, "Enters " + method);

        try {

            List<DesignComponents> ret = getProjectServices().getDesignComponents(tcSubject,tcSubject.getUserId());
            if (isRole(tcSubject, ADMIN_ROLE)) {
                return ret;
            } else {
                for (int index = ret.size() - 1; index >= 0; index--) {
                    DesignComponents designComponents = (DesignComponents) ret.get(index);
                    if (designComponents.getCperm() == null && designComponents.getPperm() == null) {
                        ret.remove(index);
                    }
                }
                return ret;
            }
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * Get corresponding development contest's id for the design contest.
     *
     * @param contestId The contest id
     * @throws ProjectServicesException if any other error occurs
     * @since 1.2.1
     */
    public long getDevelopmentContestId(long contestId) throws ProjectServicesException {
        String method = "ProjectServicesBean#getDesignComponents() method.";

        Util.log(logger, Level.INFO, "Enters " + method);

        try {

            return getProjectServices().getDevelopmentContestId(contestId);

        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * check contest permission, check if a user has permission (read or write) on a contest
     *
     * @param contestId the contest id
     * @param readonly check read or write permission
     * @param userId user id
     * @return true/false
     * @throws PersistenceException
     */
    public boolean checkContestPermission(long contestId, boolean readonly, long userId)
            throws ProjectServicesException {
        try {
            return getProjectServices().checkContestPermission(contestId, readonly, userId);
        } catch (ProjectServicesException e) {
            throw e;
        }
    }

    /**
     * check contest permission, check if a user has permission (read or write) on a project
     *
     * @param tcprojectId the tc direct project id
     * @param readonly check read or write permission
     * @param userId user id
     * @return true/false
     * @throws PersistenceException
     */
    public boolean checkProjectPermission(long tcprojectId, boolean readonly, long userId)
            throws ProjectServicesException {
        try {
            return getProjectServices().checkProjectPermission(tcprojectId, readonly, userId);
        } catch (ProjectServicesException e) {
            throw e;
        }
    }

    /**
     * <p>
     * get project ids by tc direct id
     * </p>
     *
     * @param tcprojectId tc direct project id
     * @return list of project ids
     * @throws PersistenceException if any other error occurs.
     */
    public List<Long> getProjectIdByTcDirectProject(long tcprojectId)
            throws ProjectServicesException {
        try {
            return getProjectServices().getProjectIdByTcDirectProject(tcprojectId);
        } catch (ProjectServicesException e) {
            throw e;
        }

    }

    /**
     * <p>
     * Searches the resources in the persistence store using the given filter. The filter can be formed using the field
     * names and utility methods in ResourceFilterBuilder. The return will always be a non-null (possibly 0 item) array.
     * </p>
     * <p>
     * In order to invoke this method correctly, one should properly set the resourceSearchBundle.
     *
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
     * </p>
     *
     * @param filter the filter to use
     * @return The loaded resources
     * @throws ProjectServicesException if there is an error executing the filter
     */
    public Resource[] searchResources(Filter filter) throws ProjectServicesException {
        try {
            return getProjectServices().searchResources(filter);
        } catch (ProjectServicesException e) {
            throw e;
        }
    }

    /**
     * <p>
     * Updates the given resource in the persistence store.
     * </p>
     * <p>
     * If the resource is new (id is UNSET_ID), then an id should be assigned and the resource added to the persistence
     * store. Otherwise the resource data in the persistence store would be updated.
     * </p>
     *
     * @param resource the resource to update
     * @param operator the operator making the update
     * @throws IllegalArgumentException if a required field of the resource is not set (if resource.getResourceRole() is
     *             null), or if the resource role is associated with a phase type and the resource is not associated
     *             with a phase, or if resource or operator is null
     * @throws ResourcePersistenceException if there is an error updating the resource
     */
    public Resource updateResource(Resource resource, String operator)
            throws ProjectServicesException {
        try {
            return getProjectServices().updateResource(resource, operator);
        } catch (ProjectServicesException e) {
            throw e;
        }
    }

    /**
     * <p>
     * get forum id by project id
     * </p>
     *
     * @param projectId project id
     * @return forum id
     * @throws PersistenceException if any other error occurs.
     */
    public long getForumId(long projectId) throws ProjectServicesException {
        try {
            return getProjectServices().getForumId(projectId);
        } catch (ProjectServicesException e) {
            throw e;
        }
    }

    /**
     * check if user has contest permission, it checks contest permission only (not project permission)
     *
     * @param contestId the contest id
     * @param userId user id
     * @return true/false
     * @throws PersistenceException
     */
    public boolean hasContestPermission(long contestId, long userId)
            throws ProjectServicesException {
        try {
            return getProjectServices().hasContestPermission(contestId, userId);
        } catch (ProjectServicesException e) {
            throw e;
        }
    }

    /**
     * <p>
     * Removes the given resource in the persistence store (by id). If the id does not exist in the persistence, nothing
     * would be removed.
     * </p>
     *
     * @param resource the resource to remove
     * @param operator the operator making the update
     * @throws IllegalArgumentException if the id of the resource is UNSET_ID, or the resource or operator is null
     * @throws ResourcePersistenceException if there is an error updating the persistence store
     */
    public void removeResource(Resource resource, String operator) throws ProjectServicesException {
        try {
            getProjectServices().removeResource(resource, operator);
        } catch (ProjectServicesException e) {
            throw e;
        }
    }

    /**
     * Search resources by project id and role id
     *
     * @param projectId project id
     * @param roleId role id
     * @return array of resoureces
     * @throws ResourcePersistenceException if there is an error reading the persistence store.
     */
    public Resource[] searchResources(long projectId, long roleId) throws ProjectServicesException {
        try {
            return getProjectServices().searchResources(projectId, roleId);
        } catch (ProjectServicesException e) {
            throw e;
        }
    }

    /**
     * <p>
     * get tc direct project id by project id
     * </p>
     *
     * @param projectId project id
     * @return tc direct project id
     * @throws PersistenceException if any other error occurs.
     */
    public long getTcDirectProject(long projectId) throws ProjectServicesException {
        try {
            return getProjectServices().getTcDirectProject(projectId);
        } catch (ProjectServicesException e) {
            throw e;
        }
    }

    /**
     * This method creates a Specification Review project associated to a project determined by parameter
     *
     * @param projectId the project id to create a Specification Review for
     * @param specReviewPrize the prize to set for the Specification Review project
     * @param operator the operator used to audit the operation, cannot be null or empty
     * @return the created project
     * @throws ProjectServicesException if any error occurs in the underlying services or if the specification review
     *             already exists
     * @throws IllegalArgumentException if operator is null or empty or prize is negative.
     * @since 1.3
     */
    public FullProjectData createSpecReview(long projectId, double specReviewPrize, String userId, String handle)
            throws ProjectServicesException {

        // check operator
        ExceptionUtils.checkNullOrEmpty(userId, null, null, "The parameter[userId] should not be null or empty.");

        String method = "ProjectServicesBean#createSpecReview(" + projectId + ", " + specReviewPrize + ", " + userId
                + ") method.";
        Util.log(logger, Level.INFO, "Enters " + method);

        FullProjectData specReview = null;
        try {
            specReview = getProjectServices().createSpecReview(projectId, specReviewPrize, userId, handle);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }

        return specReview;
    }

    /**
     * This method retrieves scorecard and review information associated to a project determined by parameter. Note: a
     * single reviewer / review is assumed.
     *
     * @param projectId the project id to search for
     * @return the aggregated scorecard and review data
     * @throws ProjectServicesException if any unexpected error occurs in the underlying services, if an invalid number
     *             of reviewers or reviews are found or if the code fails to retrieve scorecard id.
     * @since 1.3
     */
    public ScorecardReviewData getScorecardAndReview(long projectId)
            throws ProjectServicesException {
        String method = "ProjectServicesBean#getScorecardAndReview(" + projectId + ") method.";
        Util.log(logger, Level.INFO, "Enters " + method);

        ScorecardReviewData scorecardReviewData = null;
        try {
            scorecardReviewData = getProjectServices().getScorecardAndReview(projectId);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
        return scorecardReviewData;
    }

    /**
     * This method retrieves the corresponding specification review project id of a given project. The code will rely on
     * the project links to retrieve the specification project id.
     *
     * @param projectId the project id to search for
     * @throws ProjectServicesException if any unexpected error occurs in the underlying services.
     * @return the associated specification review project id, or -1 if it was not found.
     * @since 1.3
     */
    public long getSpecReviewProjectId(long projectId) throws ProjectServicesException {
        String method = "ProjectServicesBean#getSpecReviewProjectId(" + projectId + ") method.";

        Util.log(logger, Level.INFO, "Enters " + method);
        long specReviewProjectId = -1;
        try {
            specReviewProjectId = getProjectServices().getSpecReviewProjectId(projectId);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }

        return specReviewProjectId;
    }

    /**
     * This method retrieves open phases names for a given project id
     *
     * @param projectId the project id to search for
     * @return a set with open phases names
     * @throws ProjectServicesException if any error occurs during retrieval of information.
     * @since 1.3
     */
    public Set<String> getOpenPhases(long projectId) throws ProjectServicesException {
        String method = "ProjectServicesBean#getOpenPhases(" + projectId + ") method.";

        Util.log(logger, Level.INFO, "Enters " + method);
        Set<String> openPhases = null;
        try {
            openPhases = getProjectServices().getOpenPhases(projectId);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }

        return openPhases;
    }

    /**
     * This method retrieves the phases by phase type for a given project id.
     * 
     * @param projectId the project id to search for
     * @param phaseTypeName the phase type to search for
     * @return the retrieved phases
     * @throws ProjectServicesException if any error occurs during retrieval of information.
     * @since 1.4.8
     */
    public List<Phase> getPhasesByType(long projectId, String phaseTypeName) throws ProjectServicesException {
        String method = "ProjectServicesBean#getPhasesByType(" + projectId + "," + phaseTypeName + ") method.";

        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            return getProjectServices().getPhasesByType(projectId, phaseTypeName);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }
    
    /**
     * This method adds a review comment to a review. It simply delegates all logic to underlying services.
     *
     * @param reviewId the review id to add the comment to
     * @param comment the review comment to add
     * @throws ProjectServicesException if any unexpected error occurs in the underlying services.
     * @throws IllegalArgumentException if comment is null or operator is null or empty
     * @since 1.3
     */
    public void addReviewComment(long reviewId, Comment comment, String operator)
            throws ProjectServicesException {
        // check comment
        ExceptionUtils.checkNull(comment, null, null, "The parameter[comment] should not be null.");

        // check operator
        ExceptionUtils.checkNullOrEmpty(operator, null, null, "The parameter[operator] should not be null or empty.");

        String method = "ProjectServicesBean#addReviewComment(" + reviewId + ", " + comment + ", " + operator
                + ") method.";
        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            getProjectServices().addReviewComment(reviewId, comment, operator);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * <p>
     * update phases
     * </p>
     *
     * @param project project
     * @param operator operator
     * @throws PersistenceException if any other error occurs.
     */
    public void updatePhases(com.topcoder.project.phases.Project project, String operator)
            throws ProjectServicesException {

        String method = "ProjectServicesBean#updatePhases(" + project.getId() + ") method.";
        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            getProjectServices().updatePhases(project, operator);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }


    /**
     * Gets the <code>com.topcoder.project.phases.Project</code> phases by the give project id.
     *
     *
     * @param projectId the id of the challenge
     * @return the <code>com.topcoder.project.phases.Project</code>
     * @throws ProjectServicesException if any other error occurs.
     * @since 1.8
     */
    public com.topcoder.project.phases.Project getPhases(long projectId)
            throws ProjectServicesException {

        String method = "ProjectServicesBean#getPhases(" + projectId + ") method.";
        Util.log(logger, Level.INFO, "Enters " + method);

        try {
           return getProjectServices().getPhases(projectId);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * Update the given project
     *
     * @param project The project instance to be updated into the database.
     * @param reason The update reason. It will be stored in the persistence for future references.
     * @param operator The modification user of this project.
     * @throws IllegalArgumentException if any input is null or the operator is empty string.
     * @throws PersistenceException if error occurred while accessing the database.
     * @throws ValidationException if error occurred while validating the project instance.
     */
    public void updateProject(Project project, String reason, String operator)
            throws ProjectServicesException {
        String method = "ProjectServicesBean#updateProject(" + project.getId() + ") method.";
        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            getProjectServices().updateProject(project, reason, operator);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * <p>
     * check if it is dev only
     * </p>
     *
     * @param projectId project id
     * @return boolean
     * @throws PersistenceException if any other error occurs.
     */
    public boolean isDevOnly(long projectId) throws ProjectServicesException {
        String method = "ProjectServicesBean#isDevOnly(" + projectId + ") method.";
        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            return getProjectServices().isDevOnly(projectId);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * This method links the design contest to its development contest.
     *
     * @param developmentContestId the development contest id
     * @throws ProjectServicesException if any unexpected error occurs in the underlying services.
     * @since 1.3.1
     */
    public void linkDevelopmentToDesignContest(long developmentContestId)
            throws ProjectServicesException {
        String method = "ProjectServicesImpl#linkDevelopmentToDesignContest(" + developmentContestId + ") method.";
        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            getProjectServices().linkDevelopmentToDesignContest(developmentContestId);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "PersistenceException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * Links the parent contest with the bug hunt contest.
     *
     * @param bugHuntContestId the id of the bug hunt contest.
     * @throws ProjectServicesException if any unexpected error occurs in the underlying services.
     * @since 1.4.6
     */
    public void linkBugHuntToOtherContest(long bugHuntContestId) throws ProjectServicesException {
        String method = "ProjectServicesImpl#linkBugHuntToOtherContest(" + bugHuntContestId + ") method.";
        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            getProjectServices().linkBugHuntToOtherContest(bugHuntContestId);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "PersistenceException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * Get corresponding development contest's id for the design contest.
     *
     * @param contestId The contest id
     * @throws ProjectServicesException if any other error occurs
     * @since 1.2.1
     */
    public long getDesignContestId(long contestId) throws ProjectServicesException {
        String method = "ProjectServicesBean#getDesignContestId() method.";

        Util.log(logger, Level.INFO, "Enters " + method);

        try {

            return getProjectServices().getDesignContestId(contestId);

        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * Get corresponding contest id matches the same asset setting for the given contest.
     *
     * @param contestId The contest id
     * @throws ProjectServicesException if any other error occurs
     * @since 1.4.6
     */
    public long getContestId(long contestId) throws ProjectServicesException {
        String method = "ProjectServicesBean#getContestId() method.";

        Util.log(logger, Level.INFO, "Enters " + method);

        try {

            return getProjectServices().getContestId(contestId);

        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }


    /**
     * Creates re-open contest for the given contest. Since version 1.4.
     *
     * @param contest the contest to repost
     * @param operator the operator
     * @return new contest for the repost one
     * @throws ProjectServicesException if any error occurs
     */
    public FullProjectData createReOpenContest(FullProjectData contest, String operator)
            throws ProjectServicesException {
        // check operator
        ExceptionUtils.checkNullOrEmpty(operator, null, null, "The parameter[operator] should not be null or empty.");

        String method = "ProjectServicesBean#createReOpenContest(" + contest + ", " + operator + ") method.";
        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            return getProjectServices().createReOpenContest(contest, operator);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * Creates new version for development and design contest for the given contest. Since version 1.4.
     *
     * @param contest the contest to create new version
     * @param operator the operator
     * @return new contest for the repost one
     * @throws ProjectServicesException if any error occurs
     */
    public FullProjectData createNewVersionContest(FullProjectData contest, String operator)
            throws ProjectServicesException {
        // check operator
        ExceptionUtils.checkNullOrEmpty(operator, null, null, "The parameter[operator] should not be null or empty.");

        String method = "ProjectServicesBean#createNewVersionContest(" + contest + ", " + operator + ") method.";
        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            return getProjectServices().createNewVersionContest(contest, operator);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }


   /**
     *  Get project only (not phase or resources)
     */
    public Project getProject(long projectId) throws ProjectServicesException
    {


        String method = "ProjectServicesBean#getProject(" + projectId + ") method.";
        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            return getProjectServices().getProject(projectId);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

     /**
     * Gets all resource roles in the persistence store.
     *
     * @return All resource roles in the persistence store
     * @throws ProjectServicesException
     *             If there is an error reading the persistence store.
     */
    public ResourceRole[] getAllResourceRoles() throws ProjectServicesException
    {
         String method = "ProjectServicesBean#getAllResourceRoles() method.";
        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            return getProjectServices().getAllResourceRoles();
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }

    }

     /**
     * Add notifications of the given projects IDs to given user.
     * 
     *  @param userId the id of the user.
     *  @param projectIds the array of project IDs.
     *  @param operator the operator.
     *  @throws ProjectServicesException if any error occurs.
     *  @since 1.4.2 BUGR-3706
     */
    public void addNotifications(long userId, long[] projectIds, String operator) throws ProjectServicesException {
        ExceptionUtils.checkNullOrEmpty(operator, null, null, "The parameter[operator] should not be null or empty.");
        
        String method = "ProjectServicesBean#addNotifications method.";
        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            getProjectServices().addNotifications(userId, projectIds, operator);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }

    }

    /**
     * Get active contests for the given user, the contest data is stored in
     * SimpleProjectContestData and the result is returned as a list of SimpleProjectContestData.
     * 
     * @param subject the TCSubject instance.
     * @param userId the id of the user.
     * @throws ProjectServicesException is any error occrus.
     * 
     * @since 1.4.2 BUGR-3706
     */
    public List<SimpleProjectContestData> getActiveContestsForUser(TCSubject subject, long userId)
            throws ProjectServicesException {
        
        String method = "ProjectServicesBean#getActiveContestsForUser method.";
        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            return getProjectServices().getActiveContestsForUser(subject, userId);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }
	
	/**
     * Get active/draft contests for the given user, the contest data is stored in
     * SimpleProjectContestData and the result is returned as a list of SimpleProjectContestData.
     * 
     * @param subject the TCSubject instance.
     * @param userId the id of the user.
     * @throws ProjectServicesException is any error occrus.
     * 
     * @since 1.4.2 BUGR-3706
     */
    public List<SimpleProjectContestData> getActiveDraftContestsForUser(TCSubject subject, long userId)
            throws ProjectServicesException {
        
        String method = "ProjectServicesBean#getActiveDraftContestsForUser method.";
        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            return getProjectServices().getActiveDraftContestsForUser(subject, userId);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * Get project notifications of the given notification type for the given user.
     * 
     * @param userId the id of the user.
     * @param the id of the notification type.
     * @param the array of project ids to check.
     * 
     * @throws ProjectServicesException if any error occurs.
     * 
     * @since 1.4.2 BUGR-3706
     */
    public long[] getNotificationsForUser(long userId, long notificationType, long[] projectIds)
            throws ProjectServicesException {

        String method = "ProjectServicesBean#getNotificationsForUser method.";
        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            return getProjectServices().getNotificationsForUser(userId, notificationType, projectIds);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * Removes the notifications of the given project IDs for the given userId.
     * 
     * @param userId the id of the user.
     * @param projectIds the array of project IDs.
     * @param operator the operator.
     * @throws ProjectServicesException if any error occurs.
     * @since 1.4.2 BUGR-3706
     */
    public void removeNotifications(long userId, long[] projectIds, String operator) throws ProjectServicesException {
        ExceptionUtils.checkNullOrEmpty(operator, null, null, "The parameter[operator] should not be null or empty.");

        String method = "ProjectServicesBean#removeNotifications method.";
        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            getProjectServices().removeNotifications(userId, projectIds, operator);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }


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
    public boolean resourceExists(long projectId, long roleId, long userId) throws ProjectServicesException {

        String method = "ProjectServicesBean#resourceExists method.";
        Util.log(logger, Level.INFO, "Enters " + method);

        try {
            return getProjectServices().resourceExists(projectId, roleId, userId);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * This method retrieves scorecard and review information associated to a project determined by parameter. Note: a
     * single reviewer / review is assumed.
     *
     * @param projectId  the project id to search for.
     * @param reviewerId the reviewer resource ID.
     * @return the aggregated scorecard and review data.
     * @throws ProjectServicesException if any unexpected error occurs in the underlying services, if an invalid number of
     * reviewers or reviews are found or if the code fails to retrieve scorecard id.
     * @since 1.4.3
     */
    public List<ScorecardReviewData> getScorecardAndReviews(long projectId, long reviewerResourceId)
        throws ProjectServicesException {
        String method = "ProjectServicesBean#getScorecardAndReviews method.";
        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            return getProjectServices().getScorecardAndReviews(projectId, reviewerResourceId);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }


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
        throws ProjectServicesException {
        String method = "ProjectServicesBean#getScorecardAndCheckpointReviews method.";
        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            return getProjectServices().getScorecardAndCheckpointReviews(projectId, reviewerResourceId);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }
    
    /**
     * This method retrieves scorecard and screening information associated to a project determined by parameter. Note: a
     * single primary screener / screening is assumed.
     *
     * @param projectId  the project id to search for.
     * @param screenerId the screener ID.
     * @return the aggregated scorecard and review data.
     * @throws ProjectServicesException if any unexpected error occurs in the underlying services, if an invalid number of
     * reviewers or reviews are found or if the code fails to retrieve scorecard id.
     * @since 1.4.3
     */
    public List<ScorecardReviewData> getScorecardAndScreening(long projectId, long screenerId)
        throws ProjectServicesException {
        String method = "ProjectServicesBean#getScorecardAndScreening method.";
        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            return getProjectServices().getScorecardAndScreening(projectId, screenerId);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * <p>Creates specified review for software project.</p>
     *
     * @param review a <code>Review</code> providing the details for review to be created.
     * @throws ReviewManagementException if an unexpected error occurs.
     * @since 1.4.3
     */
    public void createReview(Review review) throws ReviewManagementException {
        String method = "ProjectServicesBean#createReview method.";
        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            getProjectServices().createReview(review);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }
    
    /**
     * <p>Updates specified review for software project.</p>
     *
     * @param review a <code>Review</code> providing the details for review to be created.
     * @throws ReviewManagementException if an unexpected error occurs.
     * @since 1.4.5
     */
    public void updateReview(Review review) throws ReviewManagementException {
        String method = "ProjectServicesBean#updateReview method.";
        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            getProjectServices().updateReview(review);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }
    
    /**
     * Gets all FileType entities.
     *
     * @return the found FileType entities, return empty if cannot find any.
     * @throws ProjectServicesException
     *             if there are any exceptions.
     * @since 1.4.4
     */
    public FileType[] getAllFileTypes() throws ProjectServicesException {
        String method = "ProjectServicesBean#getAllFileTypes emthod.";
        Util.log(logger, Level.INFO, "Enters" + method);
        try {
            return getProjectServices().getAllFileTypes();
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * Gets all project platforms.
     *
     * @return all project platforms.
     * @throws ProjectServicesException if any error.
     * @since 1.7
     */
    public ProjectPlatform[] getAllProjectPlatforms() throws ProjectServicesException {
        String method = "ProjectServicesBean#getAllProjectPlatforms method.";
        Util.log(logger, Level.INFO, "Enters" + method);
        try {
            return getProjectServices().getAllProjectPlatforms();
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * <p>Gets the scorecard matching the specified ID.</p>
     *
     * @param scorecardId a <code>long</code> providing the ID of a scorecard.
     * @return a <code>Scorecard</code> providing the details for scorecard.
     * @throws ProjectServicesException if an unexpected error occurs.
     * @since 1.4.7
     */
    public Scorecard getScorecard(long scorecardId) throws ProjectServicesException {
        String method = "ProjectServicesBean#getScorecard emthod.";
        Util.log(logger, Level.INFO, "Enters" + method);
        try {
            return getProjectServices().getScorecard(scorecardId);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * <p>Gets the reviews of specified type for specified contest.</p>
     *
     * @param contestId       a <code>long</code> providing the ID for the contest.
     * @param scorecardTypeId a <code>long</code> providing the ID for the scorecard type.
     * @return a <code>Review</code> array providing the details for found reviews.
     * @throws ProjectServicesException if an unexpected error occurs.
     * @since 1.4.7
     */
    public Review[] getReviews(long contestId, long scorecardTypeId) throws ProjectServicesException {
        String method = "ProjectServicesBean#getReviews emthod.";
        Util.log(logger, Level.INFO, "Enters" + method);
        try {
            return getProjectServices().getReviews(contestId, scorecardTypeId);
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }
    
    /**
     * Gets the general feedback for software checkpoint submissions.
     * 
     * @param contestId the contest id
     * @return the general feedback, or null if there's no matching record in DB
     * @throws IllegalArgumentException if the argument is non-positive
     * @throws ProjectServicesException if any other error occurs
     * @since 1.4.8
     */
    public String getSoftwareCheckpointSubmissionsGeneralFeedback(long contestId) throws ProjectServicesException {
        String method = "ProjectServicesBean#getSoftwareCheckpointSubmissionsGeneralFeedback method.";
        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            return getProjectServices().getSoftwareCheckpointSubmissionsGeneralFeedback(contestId);
        } catch (IllegalArgumentException e) {
            Util.log(logger, Level.ERROR, "IllegalArgumentException occurred in " + method);
            throw e;
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }
    
    /**
     * Saves the general feedback for software checkpoint submissions. If the feedback don't exist it will create
     * a new record, otherwise update it.
     * 
     * @param contestId the contest id
     * @param feedback the general feedback
     * @throws IllegalArgumentException if the argument contestId is non-positive
     * @throws ProjectServicesException if any other error occurs
     * @since 1.4.8
     */
    public void saveSoftwareCheckpointSubmissionsGeneralFeedback(long contestId, String feedback)
        throws ProjectServicesException {
        String method = "ProjectServicesBean#saveSoftwareCheckpointSubmissionsGeneralFeedback method.";
        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            getProjectServices().saveSoftwareCheckpointSubmissionsGeneralFeedback(contestId, feedback);
        } catch (IllegalArgumentException e) {
            Util.log(logger, Level.ERROR, "IllegalArgumentException occurred in " + method);
            throw e;
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * <p>Gets the reviews for specified phase of specified contest.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest to get review for.
     * @param phaseId a <code>long</code> providing the ID of a contest phase to get reviews for,
     * @return a <code>Review</code> array listing the reviews for the specified phase of specified contest.
     * @throws ProjectServicesException if an unexpected error occurs.
     * @since 1.4.9
     */
    public Review[] getReviewsByPhase(long contestId, long phaseId) throws ProjectServicesException {
        String method = "ProjectServicesBean#getReviewsByPhase method.";
        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            return getProjectServices().getReviewsByPhase(contestId, phaseId);
        } catch (IllegalArgumentException e) {
            Util.log(logger, Level.ERROR, "IllegalArgumentException occurred in " + method);
            throw e;
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * Creates the contest milestone relationship.
     *
     * @param projectId the id of the contest
     * @param milestoneId the id of the direct project milestone
     * @param operator the operator
     * @throws ProjectServicesException if any error occurs
     * @since 1.5
     */
    public void createProjectMilestoneRelation(long projectId, long milestoneId, String operator)
            throws ProjectServicesException {
        String method = "ProjectServicesBean#createProjectMilestoneRelation method.";
        Util.log(logger, Level.INFO, "Enters " + method);
        try {
          getProjectServices().createProjectMilestoneRelation(projectId, milestoneId, operator);
        } catch (IllegalArgumentException e) {
            Util.log(logger, Level.ERROR, "IllegalArgumentException occurred in " + method);
            throw e;
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * Updates the contest milestone relationship.
     *
     * @param projectId the id of the contest
     * @param milestoneId the id of the direct project milestone
     * @param operator the operator
     * @throws ProjectServicesException if any error occurs
     * @since 1.5
     */
    public void updateProjectMilestoneRelation(long projectId, long milestoneId, String operator)
            throws ProjectServicesException {
        String method = "ProjectServicesBean#updateProjectMilestoneRelation method.";
        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            getProjectServices().updateProjectMilestoneRelation(projectId, milestoneId, operator);
        } catch (IllegalArgumentException e) {
            Util.log(logger, Level.ERROR, "IllegalArgumentException occurred in " + method);
            throw e;
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * Deletes the contest milestone relationship.
     *
     * @param projectId the id of the contest
     * @param operator the operator
     * @throws ProjectServicesException if any error occurs
     * @since 1.5
     */
    public void deleteProjectMilestoneRelation(long projectId, String operator) throws ProjectServicesException {
        String method = "ProjectServicesBean#deleteProjectMilestoneRelation method.";
        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            getProjectServices().deleteProjectMilestoneRelation(projectId, operator);
        } catch (IllegalArgumentException e) {
            Util.log(logger, Level.ERROR, "IllegalArgumentException occurred in " + method);
            throw e;
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * Deletes all the contests associations to the specified milestone.
     *
     * @param milestoneId the id of the milestone.
     * @param operator the operator.
     * @throws ProjectServicesException if any error occurs.
     * @since 1.6
     */
    public void deleteMilestoneProjectRelations(long milestoneId, String operator) throws ProjectServicesException {
        String method = "ProjectServicesBean#deleteMilestoneProjectRelations method.";
        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            getProjectServices().deleteMilestoneProjectRelations(milestoneId, operator);
        } catch (IllegalArgumentException e) {
            Util.log(logger, Level.ERROR, "IllegalArgumentException occurred in " + method);
            throw e;
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * Gets the contest milestone relationship.
     *
     * @param projectId the id of the contest
     * @return the project milestone id
     * @throws ProjectServicesException if any error occurs
     * @since 1.5
     */
    public long getProjectMilestoneRelation(long projectId) throws ProjectServicesException {
        String method = "ProjectServicesBean#getProjectMilestoneRelation method.";
        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            return getProjectServices().getProjectMilestoneRelation(projectId);
        } catch (IllegalArgumentException e) {
            Util.log(logger, Level.ERROR, "IllegalArgumentException occurred in " + method);
            throw e;
        } catch (ProjectServicesException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }
}
