/*
 * Copyright (C) 2006 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.impl;

import com.cronos.onlinereview.external.ProjectRetrieval;
import com.topcoder.management.phase.PhaseManagementException;
import com.topcoder.management.phase.PhaseManager;
import com.topcoder.management.project.*;
import com.topcoder.management.project.link.ProjectLink;
import com.topcoder.management.project.link.ProjectLinkManager;
import com.topcoder.management.project.link.ProjectLinkType;
import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceManager;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.management.resource.search.ResourceFilterBuilder;
import com.topcoder.management.review.ReviewManagementException;
import com.topcoder.management.review.ReviewManager;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.Review;
import com.topcoder.management.scorecard.ScorecardManager;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.team.TeamManager;
import com.topcoder.management.team.TeamPersistenceException;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.template.PhaseTemplate;
import com.topcoder.project.phases.template.PhaseTemplateException;
import com.topcoder.project.service.ConfigurationException;
import com.topcoder.project.service.ContestSaleData;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.project.service.ProjectDoesNotExistException;
import com.topcoder.project.service.ProjectServices;
import com.topcoder.project.service.ProjectServicesException;
import com.topcoder.project.service.ScorecardReviewData;
import com.topcoder.project.service.Util;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.security.TCSubject;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.errorhandling.ExceptionUtils;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.*;

/**
 * <p>
 * Full implementation of the <code>ProjectServices</code> interface. This implementation makes
 * use of a large array of components to accomplish its task of retrieving project data: <b>User
 * Data Store Persistence</b> component to retrieve external project information, the <b>Project
 * Phases</b> and <b>Phase and Resource Management</b> to get project header and phase
 * information, <b>Resource Manager</b> to get resource information, and <b>Team Manager</b> to
 * get team information.
 * </p>
 * <p>
 * To provide a good view as the steps are progressing in each method, the <b>Logging Wrapper</b>
 * component is used in each method. To configure this component, the <b>ConfigManager</b> and
 * <b>ObjectFactory</b> components are used.
 * </p>
 * <p>
 * Also provided are configuration-backed and programmatic constructors. This allows the user to
 * either create all internal supporting objects from configuration, or to simply pass the instances
 * directly.
 * </p>
 *
 * <p>
 * Modifications in version 1.1: <code>createProject</code> and <code>updateProject</code>
 * methods are added.
 * </p>
 *
 * <p>
 * Here is the sample configuration for this class
 *
 * <pre>
 *  &lt;CMConfig&gt;
 *  &lt;!-- Configuration for ProjectServicesImpl --&gt;
 *  &lt;Config name=&quot;com.topcoder.project.service.impl.ProjectServicesImpl&quot;&gt;
 *  &lt;Property name=&quot;specNamespace&quot;&gt;
 *  &lt;Value&gt;com.topcoder.util.objectfactory&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;projectRetrievalKey&quot;&gt;
 *  &lt;Value&gt;projectRetrievalKey&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;resourceManagerKey&quot;&gt;
 *  &lt;Value&gt;resourceManagerKey&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;phaseManagerKey&quot;&gt;
 *  &lt;Value&gt;phaseManagerKey&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;projectManagerKey&quot;&gt;
 *  &lt;Value&gt;projectManagerKey&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;projectLinkManagerKey&quot;&gt;
 *  &lt;Value&gt;projectLinkManagerKey&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;scorecardManagerKey&quot;&gt;
 *  &lt;Value&gt;scorecardManagerKey&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;reviewManagerKey&quot;&gt;
 *  &lt;Value&gt;reviewManagerKey&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;teamManagerKey&quot;&gt;
 *  &lt;Value&gt;teamManagerKey&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;loggerName&quot;&gt;
 *  &lt;Value&gt;SystemLog&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;activeProjectStatusId&quot;&gt;
 *  &lt;Value&gt;1&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Config&gt;
 *  &lt;!-- Configuration for ObjectFactory --&gt;
 *  &lt;Config name=&quot;com.topcoder.util.objectfactory&quot;&gt;
 *  &lt;Property name=&quot;projectRetrievalKey&quot;&gt;
 *  &lt;Property name=&quot;type&quot;&gt;
 *  &lt;Value&gt;com.topcoder.project.service.impl.MockProjectRetrieval&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;resourceManagerKey&quot;&gt;
 *  &lt;Property name=&quot;type&quot;&gt;
 *  &lt;Value&gt;com.topcoder.project.service.impl.MockResourceManager&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;phaseManagerKey&quot;&gt;
 *  &lt;Property name=&quot;type&quot;&gt;
 *  &lt;Value&gt;com.topcoder.project.service.impl.MockPhaseManager&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;projectManagerKey&quot;&gt;
 *  &lt;Property name=&quot;type&quot;&gt;
 *  &lt;Value&gt;com.topcoder.project.service.impl.MockProjectManager&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;projectLinkManagerKey&quot;&gt;
 *  &lt;Property name=&quot;type&quot;&gt;
 *  &lt;Value&gt;com.topcoder.management.project.persistence.link.ProjectLinkManagerImpl&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;params&quot;&gt;
 *  &lt;Property name=&quot;param1&quot;&gt;
 *  &lt;Property name=&quot;name&quot;&gt;
 *  &lt;Value&gt;projectManagerKey&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;scorecardManagerKey&quot;&gt;
 *  &lt;Property name=&quot;type&quot;&gt;
 *  &lt;Value&gt;com.topcoder.management.scorecard.ScorecardManagerImpl&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;reviewManagerKey&quot;&gt;
 *  &lt;Property name=&quot;type&quot;&gt;
 *  &lt;Value&gt;com.topcoder.management.review.DefaultReviewManager&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;teamManagerKey&quot;&gt;
 *  &lt;Property name=&quot;type&quot;&gt;
 *  &lt;Value&gt;com.topcoder.project.service.impl.MockTeamManager&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;/Config&gt;
 *  &lt;/CMConfig&gt;
 * </pre>
 *
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
 * <li>Update {@link #updateProject(Project, String, com.topcoder.project.phases.Project, Resource[], String)} method.</li>
 * <li>Update {@link #createProjectWithTemplate(Project, com.topcoder.project.phases.Project, Resource[], String)} method.</li>
 * <li>Add {@link #getAllFileTypes()} method.</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Version 1.4.5 (TC Direct Replatforming Release 2) Change notes:
 * <ul>
 * <li>Added {@link #getPhaseTemplateName(Project)} method.</li>
 * <li>Added {@link #getLeftOutPhaseIds(Project, boolean, boolean, boolean)} method.</li>
 * <li>Added {@link #adjustPhaseForEndDate(com.topcoder.project.phases.PhaseType, com.topcoder.project.phases.Project, java.util.Date)} (com.topcoder.project.phases.Project, Date)} method.</li>
 * <li>Added {@link #setNewPhasesProperties(com.topcoder.management.project.Project, com.topcoder.project.phases.Project, boolean, boolean)} (Project, com.topcoder.project.phases.Project)} method.</li>
 * <li>Updated {@link #updateProject(Project, String, com.topcoder.project.phases.Project, Resource[], Date, String)} method to allow
 * change the number of project rounds. When the number of rounds changes, the project phases need to reset.</li>
 * <li>Updated {@link #createProjectWithTemplate(Project, com.topcoder.project.phases.Project, Resource[], Date, String) method to remove
 * the duplicated code with {@link #updateProject(Project, String, com.topcoder.project.phases.Project, Resource[], Date, String)} method.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.4.6 (TC Direct Replatforming Release 2) Change notes:
 * <ul>
 * <li>Added {@link #getScorecardAndCheckpointReviews(long, long)} method.</li>
 * <li>Added {@link #updateReview(Review)} method.</li>
 * <li>Updated {@link #getScorecardAndReviews(long, long)} method.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.4.7 (TCCC-3270) Change notes:
 * <ul>
 * <li>Updated {@link #setNewPhasesProperties(Project, Project, boolean)} method</li>
 * </ul>
 * </p>
 * <p>
 *  Version 1.4.8 (Release Assembly - TopCoder Bug Hunt Assembly Integration 2) change note:
 *  <ul>
 *    <li>
 *        Add method {@link #getContestId(long)}
 *    </li>
 *    <li>
 *        Add method {@link #linkBugHuntToOtherContest(long)}
 *    </li>
 *  </ul>
 * </p>
 * <p>
 * Version 1.4.9 (Module Assembly - Adding Contest Approval Feature in Direct Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #getScorecard(long)} method.</li>
 *     <li>Added {@link #getReviews(long, long)} method.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.4.10 (Release Assembly - TopCoder Cockpit Software Checkpoint Management) change notes:
 * <ol><li>
 *     - Added method {@link #saveSoftwareCheckpointSubmissionsGeneralFeedback(long, String)},
 *     {@link #getSoftwareCheckpointSubmissionsGeneralFeedback(long)} to support
 *     software checkpoint management.
 * </li></ol>
 * </p>
 * 
 * <p>
 * Version 1.4.10 (Release Assembly - TC Direct Issue Tracking Tab Update Assembly 2 v1.0) change notes:
 *   <ol>
 *     <li>Added method {@link #getPhasesByType(long, String)} to get the phases of a project by phase type.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.4.11 (Module Assembly - TC Cockpit - Studio - Final Fixes Integration Part One Assembly) Change notes:
 *   <ol>
 *     <li>Added {@link #getReviewsByPhase(long, long)} method.</li>
 *     <li>Updated {@link #updateProject(Project, String, com.topcoder.project.phases.Project, Resource[], Date, String)} 
 *     and {@link #createProjectWithTemplate(Project, com.topcoder.project.phases.Project, Resource[], String)} methods
 *     to properly maintain the Approval phase for Studio contests.</li>
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
 * Version 1.7 (Module Assembly - TC Cockpit Launch Code Contest)
 * <ul>
 *     <li>Updated method {@link #createProjectWithTemplate(com.topcoder.management.project.Project,
 *     com.topcoder.project.phases.Project, com.topcoder.management.resource.Resource[],
 *     java.util.Date, java.util.Date, String)} to hard code properties for Code Contest type</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.8 (Module Assembly - TC Cockpit Launch F2F contest)
 * <ul>
 *     <li>Added method {@link #getAllProjectPlatforms()}</li>
 *     <li>Updated method {@link #createProjectWithTemplate(com.topcoder.management.project.Project,
 *     com.topcoder.project.phases.Project, com.topcoder.management.resource.Resource[],
 *     java.util.Date, java.util.Date, String)} to hard code properties for F2F Contest type</li>
 *     <li>Updated method {@link #updateProject(com.topcoder.management.project.Project, String,
 *     com.topcoder.project.phases.Project, com.topcoder.management.resource.Resource[],
 *     java.util.Date, java.util.Date, String)} to hard code properties for F2F Contest Type</li>
 *     <li>Updated method {@link #setNewPhasesProperties(com.topcoder.management.project.Project,
 *     com.topcoder.project.phases.Project, boolean, boolean)} to set iterative review scorecard
 *     id for F2F contest and set reviewer number of F2F to 1</li>
 *
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.9 (TC Cockpit Software Challenge Checkpoint End Date and Final End Date)
 * <ul>
 *      <li>Update {@link #updateProject(com.topcoder.management.project.Project,
 *      String, com.topcoder.project.phases.Project, com.topcoder.management.resource.Resource[],
 *      java.util.Date, java.util.Date, String)} to correct the submission phase / checkpoint phase length calculation</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 2.0 (TC Cockpit Auto Assign Reviewer Update)
 * <ul>
 *     <li>updated {@link #updateProject(com.topcoder.management.project.Project,
 *     String, com.topcoder.project.phases.Project, com.topcoder.management.resource.Resource[],
 *     java.util.Date, java.util.Date, String)} to adjust the reviewer number of review phase of Code contest
 *     if a reviewer is auto assigned when updating contest</li>
 *     <li>Updated {@link #setNewPhasesProperties(com.topcoder.management.project.Project,
 *     com.topcoder.project.phases.Project, boolean, boolean)} to set reviewer number to 1 for code contest with
 *     auto assigned reviewer</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 2.1 (TC Direct - Fix setting end date issue)
 * <ul>
 *     <li>Updated {@link #createProjectWithTemplate(com.topcoder.management.project.Project,
 *     com.topcoder.project.phases.Project, com.topcoder.management.resource.Resource[],
 *     java.util.Date, java.util.Date, String)} to handle the case creating a challenge with
 *     submission duration less than 48 hours. In this case, need to make the registration phase
 *     the same length of submission phase</li>
 *     <li>Updated {@link #updateProject(com.topcoder.management.project.Project, String, String)} to
 *     handle the case of updating challenge submission duration to less than 48 hours. In this case,
 *     the registration phase should be updated to be the same as submission phase</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 2.2 (Module Assembly - TC Direct Studio Design First2Finish Challenge Type)
 * <ul>
 *     <li>Updated {@link #updateProject(com.topcoder.management.project.Project, String, String)} to
 *     always set reliability, DR to false for Design First2Finish</li>
 *     <li>Added {@link #getPhases(long)} to get the phases of the contest</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Thread Safety:</strong> This class is immutable but operates on non thread safe objects,
 * thus making it potentially non thread safe.
 * </p>
 *
 * @author argolite, moonli, pulky
 * @author fabrizyo, znyyddf, murphydog, waits, hohosky, isv, lmmortal, GreatKevin
 * @version 2.2
 * @since 1.0
 */
public class ProjectServicesImpl implements ProjectServices {

    /**
     * <p>
     * Represents the default namespace used by the default constructor to access configuration info
     * in the construction.
     * </p>
     */
    public static final String DEFAULT_NAMESPACE = "com.topcoder.project.service.impl.ProjectServicesImpl";

    /**
     * <p>
     * Represents the <b>specNamespace</b> property key.
     * </p>
     */
    private static final String SPEC_NAMESPACE = "specNamespace";

    /**
     * <p>
     * Represents the <b>userRetrieval</b> property key.
     * </p>
     */
    private static final String PROJECT_RETRIEVAL_KEY = "projectRetrievalKey";

    /**
     * <p>
     * Represents the <b>ResourceManager</b> property key.
     * </p>
     */
    private static final String RESOURCE_MANAGER_KEY = "resourceManagerKey";

    /**
     * <p>
     * Represents the <b>phaseManagerKey</b> property key.
     * </p>
     */
    private static final String PHASE_MANAGER_KEY = "phaseManagerKey";

    /**
     * <p>
     * Represents the <b>projectManagerKey</b> property key.
     * </p>
     */
    private static final String PROJECT_MANAGER_KEY = "projectManagerKey";

    /**
     * <p>
     * Represents the <b>teamManagerKey</b> property key.
     * </p>
     */
    private static final String TEAM_MANAGER_KEY = "teamManagerKey";

    /**
     * <p>
     * Represents the <b>teamManagerKey</b> property key.
     * </p>
     */
    private static final String PROJECT_PHASE_TEMPLATE_KEY = "projectPhaseTemplateKey";

    /**
     * <p>
     * Represents the <b>loggerName</b> property key.
     * </p>
     */
    private static final String LOGGER_NAME = "loggerName";

    /**
     * <p>
     * Represents the <b>activeProjectStatusId</b> property key.
     * </p>
     */
    private static final String ACTIVE_PROJECT_STATUS_ID = "activeProjectStatusId";

    /**
     * <p>
     * Represents the <b>activeProjectStatusId</b> property key.
     * </p>
     */
    private static final String ACTIVE_PROJECT_CATEGORY_ID = "activeProjectCategoryId";

    /**
     * <p>
     * Represents the <b>externalReferenceID</b> property key.
     * </p>
     */
    private static final String EXTERNAL_REFERENCE_ID = "External Reference ID";

    /**
     * <p>
     * Represents the "Submitter" resource role id.
     * </p>
     *
     * @since 1.3
     */
    private static final int SUBMITTER_ROLE_ID = 1;

    /**
     * <p>
     * Represents the <b>projectLinkManagerKey</b> property key.
     * </p>
     *
     * @since 1.3
     */
    private static final String PROJECT_LINK_MANAGER_KEY = "projectLinkManagerKey";

    /**
     * <p>
     * Represents the <b>scorecardManagerKey</b> property key.
     * </p>
     *
     * @since 1.3
     */
    private static final String SCORECARD_MANAGER_KEY = "scorecardManagerKey";

    /**
     * <p>
     * Represents the <b>reviewManagerKey</b> property key.
     * </p>
     *
     * @since 1.3
     */
    private static final String REVIEW_MANAGER_KEY = "reviewManagerKey";



    /**
     * <p>
     * Represents the scorecard id phase attribute key
     * </p>
     *
     * @since 1.3
     */
    private static final String SCORECARD_ID_PHASE_ATTRIBUTE_KEY = "Scorecard ID";

     /**
     * <p>
     * Represents the resource reviewer property name
     * </p>
     *
     * @since 1.3
     */
    private static final String RESOURCE_REVIEWER_PROPERTY = "reviewer";



    /**
     * <p>
     * Represents on value for the autopilot option project property
     * </p>
     *
     * @since 1.3
     */
    private static final String AUTOPILOT_OPTION_PROJECT_PROPERTY_VALUE_ON = "On";

    /**
     * <p>
     * Represents the "billing project" project property key
     * </p>
     *
     * @since 1.3
     */
    private static final String BILLING_PROJECT_PROJECT_PROPERTY_KEY = "Billing Project";

    /**
     * <p>
     * Represents the "confidentiality type" project property key
     * </p>
     *
     * @since 1.3
     */
    private static final String CONFIDENTIALITY_TYPE_PROJECT_PROPERTY_KEY = "Confidentiality Type";

    /**
     * <p>
     * Represents the "version id" project property key
     * </p>
     *
     * @since 1.3
     */
    private static final String VERSION_ID_PROJECT_PROPERTY_KEY = "Version ID";

    /**
     * <p>
     * Represents the "component id" project property key
     * </p>
     *
     * @since 1.3
     */
    private static final String COMPONENT_ID_PROJECT_PROPERTY_KEY = "Component ID";

    /**
     * <p>
     * Represents the "external reference id" project property key
     * </p>
     *
     * @since 1.3
     */
    private static final String EXTERNAL_REFERENCE_ID_PROJECT_PROPERTY_KEY = "External Reference ID";

    /**
     * <p>
     * Represents the "external reference id" resource property key
     * </p>
     *
     * @since 1.3
     */
    private static final String EXTERNAL_REFERENCE_ID_RESOURCE_PROPERTY_KEY = "External Reference ID";

    /**
     * <p>
     * Represents the "project version" project property key
     * </p>
     *
     * @since 1.3
     */
    private static final String PROJECT_VERSION_PROJECT_PROPERTY_KEY = "Project Version";

    /**
     * <p>
     * Represents the "payments" project property key
     * </p>
     *
     * @since 1.3
     */
    private static final String PAYMENTS_PROJECT_PROPERTY_KEY = "Payments";



    /**
     * <p>
     * Represents the "Autopilot option" project property key
     * </p>
     *
     * @since 1.3
     */
    private static final String AUTOPILOT_OPTION_PROJECT_PROPERTY_KEY = "Autopilot Option";

    /**
     * <p>
     * Represents the "project name" project property key
     * </p>
     *
     * @since 1.3
     */
    private static final String PROJECT_NAME_PROJECT_PROPERTY_KEY = "Project Name";


    /**
     * <p>
     * Represents the "Developer Forum ID" project property key
     * </p>
     *
     * @since 1.3
     */
    private static final String FORUM_ID_PROJECT_PROPERTY_KEY = "Developer Forum ID";


    /**
     * <p>
     * Represents the "Root Catelog ID" project property key
     * </p>
     *
     * @since 1.3
     */
    private static final String ROOT_CATALOG_ID_PROJECT_PROPERTY_KEY = "Root Catalog ID";

    /**
     * <p>
     * Represents the application project type id
     * </p>
     *
     * @since 1.3
     */
    private static final int APPLICATION_PROJECT_TYPE_ID = 2;

    /**
     * <p>
     * Represents the application project type name
     * </p>
     *
     * @since 1.3
     */
    private static final String APPLICATION_PROJECT_TYPE = "Application";

    /**
     * <p>
     * Represents the specification review project category id
     * </p>
     *
     * @since 1.3
     */
    private static final int SPEC_REVIEW_PROJECT_CATEGORY_ID = 27;

    /**
     * <p>
     * Represents the specification review prject category name
     * </p>
     *
     * @since 1.3
     */
    private static final String SPEC_REVIEW_PROJECT_CATEGORY = "Spec Review";

    /**
     * <p>
     * Represents the "Autopilot option" project property key
     * </p>
     *
     * @since 1.3
     */
    private static final String NOTES_PROJECT_PROPERTY_KEY = "Notes";


     /**
     * Private constant specifying resource handle
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final String RESOURCE_INFO_HANDLE = "Handle";

     /**
     * Private constant specifying resource pay
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final String RESOURCE_INFO_PAYMENT_STATUS = "Payment Status";

    /**
     * Private constant specifying resource pay
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final String RESOURCE_INFO_PAYMENT_STATUS_NA = "N/A";
    

    /**
     * Represents the project category id for development contests.
     *
     */
    private static final int DEVELOPMENT_PROJECT_CATEGORY_ID = 2;

    /**
     * Represents the project category id for bug hunt contest.
     * @since 1.4.8
     */
    private static final int BUG_HUNT_PROJECT_CATEGORY_ID = 9;


    /**
     * <p>
     * Represents the project properties that need to be cloned when creating a specification review project
     * </p>
     *
     * @since 1.3
     */
    private static final String[] SPEC_REVIEW_PROJECT_PROPERTIES_TO_CLONE = new String[] {
        ProjectPropertyType.EXTERNAL_REFERENCE_ID_PROJECT_PROPERTY_KEY, ProjectPropertyType.COMPONENT_ID_PROJECT_PROPERTY_KEY,
        ProjectPropertyType.VERSION_ID_PROJECT_PROPERTY_KEY, ProjectPropertyType.CONFIDENTIALITY_TYPE_PROJECT_PROPERTY_KEY,
        ProjectPropertyType.BILLING_PROJECT_PROJECT_PROPERTY_KEY, ProjectPropertyType.PROJECT_VERSION_PROJECT_PROPERTY_KEY, 
        ProjectPropertyType.ROOT_CATALOG_ID_PROJECT_PROPERTY_KEY, ProjectPropertyType.DEVELOPER_FORUM_ID_PROJECT_PROPERTY_KEY};

    
    /**
     * <p>
     * Represents the project properties that need to be cloned when creating a new version for dev/design
     * </p>
     */
    private static final String[] NEW_VERSION_PROJECT_PROPERTIES_TO_CLONE = new String[] {
        ProjectPropertyType.EXTERNAL_REFERENCE_ID_PROJECT_PROPERTY_KEY, ProjectPropertyType.COMPONENT_ID_PROJECT_PROPERTY_KEY,
        ProjectPropertyType.VERSION_ID_PROJECT_PROPERTY_KEY, ProjectPropertyType.DEVELOPER_FORUM_ID_PROJECT_PROPERTY_KEY,
        ProjectPropertyType.ROOT_CATALOG_ID_PROJECT_PROPERTY_KEY, ProjectPropertyType.PROJECT_NAME_PROJECT_PROPERTY_KEY, 
        ProjectPropertyType.PROJECT_VERSION_PROJECT_PROPERTY_KEY, ProjectPropertyType.AUTOPILOT_OPTION_PROJECT_PROPERTY_KEY,
        ProjectPropertyType.STATUS_NOTIFICATION_PROJECT_PROPERTY_KEY, ProjectPropertyType.TIMELINE_NOTIFICATION_PROJECT_PROPERTY_KEY,
        ProjectPropertyType.PUBLIC_PROJECT_PROPERTY_KEY, ProjectPropertyType.RATED_PROJECT_PROPERTY_KEY,
        ProjectPropertyType.ELIGIBILITY_PROJECT_PROPERTY_KEY, ProjectPropertyType.NOTES_PROJECT_PROPERTY_KEY, 
        ProjectPropertyType.PAYMENTS_PROJECT_PROPERTY_KEY, ProjectPropertyType.DIGITAL_RRUN_FLAG_PROJECT_PROPERTY_KEY,
        ProjectPropertyType.ADMIN_FEE_PROJECT_PROPERTY_KEY, ProjectPropertyType.DR_POINTS_PROJECT_PROPERTY_KEY,
        ProjectPropertyType.CONFIDENTIALITY_TYPE_PROJECT_PROPERTY_KEY, ProjectPropertyType.SPEC_REVIEW_COSTS_PROJECT_PROPERTY_KEY,
        ProjectPropertyType.BILLING_PROJECT_PROJECT_PROPERTY_KEY, ProjectPropertyType.REVIEW_COSTS_PROJECT_PROPERTY_KEY,
        ProjectPropertyType.RELIABILITY_BONUS_COST_PROJECT_PROPERTY_KEY, ProjectPropertyType.CHECKPOINT_BONUS_COST_PROJECT_PROPERTY_KEY, 
        ProjectPropertyType.FIRST_PLACE_COST_PROJECT_PROPERTY_KEY, ProjectPropertyType.SECOND_PLACE_COST_PROJECT_PROPERTY_KEY, 
        ProjectPropertyType.COST_LEVEL_PROJECT_PROPERTY_KEY, ProjectPropertyType.SVN_MODULE_PROJECT_PROPERTY_KEY,
        ProjectPropertyType.APPROVAL_REQUIRED_PROJECT_PROPERTY_KEY, ProjectPropertyType.SEND_WINNDER_EMAILS_PROJECT_PROPERTY_KEY,
        ProjectPropertyType.POST_MORTEM_REQUIRED_PROJECT_PROPERTY_KEY, ProjectPropertyType.RELIABILITY_BONUS_ELIGIBLE_PROJECT_PROPERTY_KEY,
        ProjectPropertyType.MEMBER_PAYMENT_ELIGIBLE_PROJECT_PROPERTY_KEY, ProjectPropertyType.TRACK_LATE_DELIVERABLES_PROJECT_PROPERTY_KEY,
		ProjectPropertyType.REVIEW_FEEDBACK_FLAG_PROJECT_PROPERTY_KEY, ProjectPropertyType.CONTEST_FEE_PERCENTAGE_PROJECT_PROPERTY_KEY,
        ProjectPropertyType.ALLOW_MULTIPLE_SUBMISSIONS_PROPERTY_KEY, ProjectPropertyType.COPILOT_COST_PROJECT_PROPERTY_KEY,
        ProjectPropertyType.MAXIMUM_SUBMISSIONS_KEY, ProjectPropertyType.VIEWABLE_SUBMISSIONS_FLAG_KEY_STRING,
        ProjectPropertyType.ALLOW_STOCK_ART_KEY, ProjectPropertyType.FORUM_TYPE
    };

    /**
     * <p>
     * Represents the <code>ProjectRetrieval</code> instance that is used to retrieve the project
     * technologies information. It is set in the constructor to a non-null value, and will never
     * change.
     * </p>
     */
    private final ProjectRetrieval projectRetrieval;

    /**
     * <p>
     * Represents the <code>ResourceManager</code> instance that is used to retrieve resources. It
     * is set in the constructor to a non-null value, and will never change.
     * </p>
     */
    private final ResourceManager resourceManager;

    /**
     * <p>
     * Represents the <code>PhaseManager</code> instance that is used to obtain phase information
     * about a project. It is set in the constructor to a non-null value, and will never change.
     * </p>
     */
    private final PhaseManager phaseManager;

    /**
     * <p>
     * Represents the <code>TeamManager</code> instance that is used to retrieve teams. It is set
     * in the constructor to a non-null value, and will never change.
     * </p>
     */
    private final TeamManager teamManager;

    /**
     * <p>
     * Represents the <code>ProjectManager</code> instance that is used to retrieve projects. It
     * is set in the constructor to a non-null value, and will never change.
     * </p>
     */
    private final ProjectManager projectManager;

    /**
     * <p>
     * Represents the <code>ProjectLinkManager</code> instance that is used to manage project links. It
     * is set in the constructor to a non-null value, and will never change.
     * </p>
     *
     * @since 1.3
     */
    private final ProjectLinkManager projectLinkManager;

    /**
     * <p>
     * Represents the <code>ScorecardManager</code> instance that is used to manage scorecards. It
     * is set in the constructor to a non-null value, and will never change.
     * </p>
     *
     * @since 1.3
     */
    private final ScorecardManager scorecardManager;

    /**
     * <p>
     * Represents the <code>ReviewManager</code> instance that is used to manage reviews. It
     * is set in the constructor to a non-null value, and will never change.
     * </p>
     *
     * @since 1.3
     */
    private final ReviewManager reviewManager;

    /**
     * <p>
     * Used extensively in this class to log information. This will include logging method entry and
     * exit, errors, debug information for calls to other components, etc.
     * </p>
     * <p>
     * Note that logging is optional and can be null, in which case, no logging will take place. It
     * will be set in the constructor and will not change.
     * </p>
     */
    private final Log logger;

    /**
     * <p>
     * Represents the id of the active project status.
     * </p>
     * <p>
     * It is set in the constructor to a non-negative value, and will never change.
     * </p>
     */
    private final long activeProjectStatusId;

    /**
     * <p>
     * Represents the ids of the active project categories.
     * </p>
     * <p>
     * It is set in the constructor to non-negative values, and will never change.
     * </p>
     */
    private final List<Long> activeCategoriesList = new ArrayList<Long>();

    /**
     * <p>
     * Represents the <code>DefaultPhaseTemplate</code> instance that is used to generate project phases. It is set in
     * the constructor to a non-null value, and will never change.
     * </p>
     *
     * @since BUGR-1473
     */
    private final PhaseTemplate template;


    /**
     * <p>
     * Default constructor.
     * </p>
     *
     * @throws ConfigurationException
     *             If any configuration error occurs, such as unknown namespace, or missing required
     *             values, or errors while constructing the managers and services
     */
    public ProjectServicesImpl() {
        this(DEFAULT_NAMESPACE);
    }

    /**
     * <p>
     * Namespace constructor. Initializes this instance from configuration info from the <b>Config
     * Manager</b>. It will use the <b>Object Factory</b> to create instances of required objects.
     * </p>
     *
     * @param namespace
     *            The configuration namespace
     * @throws IllegalArgumentException
     *             If namespace is null/empty
     * @throws ConfigurationException
     *             If any configuration error occurs, such as unknown namespace, or missing required
     *             values, or errors while constructing the managers and services.
     */
    public ProjectServicesImpl(String namespace) {
        Util.checkStrNotNullOrEmpty(namespace, "namespace");

        ConfigManager cm = ConfigManager.getInstance();
        try {
            // gets namespace for ConfigManagerSpecificationFactory
            String specNamespace = cm.getString(namespace, SPEC_NAMESPACE);
            // creates an instance of ObjectFactory
            ObjectFactory objectFactory = new ObjectFactory(new ConfigManagerSpecificationFactory(specNamespace));

            // gets the value of userRetrievalKey and creates an instance by ObjectFactory
            this.projectRetrieval = (ProjectRetrieval) createObject(cm, objectFactory, namespace,
                    PROJECT_RETRIEVAL_KEY);
            // gets the value of resourceManagerKey and creates an instance by ObjectFactory
            this.resourceManager = (ResourceManager) createObject(cm, objectFactory, namespace, RESOURCE_MANAGER_KEY);
            // gets the value of phaseManagerKey and creates an instance by ObjectFactory
            this.phaseManager = (PhaseManager) createObject(cm, objectFactory, namespace, PHASE_MANAGER_KEY);
            // gets the value of projectManagerKey and creates an instance by ObjectFactory
            this.projectManager = (ProjectManager) createObject(cm, objectFactory, namespace, PROJECT_MANAGER_KEY);

            // gets the value of projectLinkManagerKey and creates an instance by ObjectFactory
            this.projectLinkManager = (ProjectLinkManager) createObject(cm, objectFactory, namespace,
                PROJECT_LINK_MANAGER_KEY);

            // gets the value of scorecardManagerKey and creates an instance by ObjectFactory
            this.scorecardManager = (ScorecardManager) createObject(cm, objectFactory, namespace,
                SCORECARD_MANAGER_KEY);

            // gets the value of reviewManagerKey and creates an instance by ObjectFactory
            this.reviewManager = (ReviewManager) createObject(cm, objectFactory, namespace, REVIEW_MANAGER_KEY);

            // gets the value of teamManagerKey and creates an instance by ObjectFactory
            this.teamManager = (TeamManager) createObject(cm, objectFactory, namespace, TEAM_MANAGER_KEY);

            // BUGR-1473
            this.template = (PhaseTemplate) createObject(cm, objectFactory, namespace, PROJECT_PHASE_TEMPLATE_KEY);

            // gets name of the log and gets the logger instance from LogManager if necessary
            String logName = cm.getString(namespace, LOGGER_NAME);
            this.logger = ((logName == null) ? null : LogManager.getLog(logName));

            // gets the value of activeProjectStatusId
            String activeStatusId = cm.getString(namespace, ACTIVE_PROJECT_STATUS_ID);
            // parses it to 'long' value, if error occurs or negative value returned, throw an
            // exception
            long theActiveProjectStatusId = Long.parseLong(activeStatusId);
            if (theActiveProjectStatusId < 0) {
                throw new ConfigurationException("Value of [activeProjectStatusId] should not be negative.");
            }
            this.activeProjectStatusId = theActiveProjectStatusId;

            log(Level.DEBUG, "Looking for activeCategoryIds");
            int categoryEntryNumber = 0;
            while (true) {
                // increase the innerValidatorNumber to get the key for the next innerValidator.
                categoryEntryNumber++;
                String keyPropertyName = ACTIVE_PROJECT_CATEGORY_ID + categoryEntryNumber;
                // Creates innerValidator using the ObjectFactory

                String categoryIdStr = cm.getString(namespace, keyPropertyName);
                if (categoryIdStr == null) {
                    break;
                } else {
                    long categoryId = Long.parseLong(categoryIdStr);
                    if (categoryId < 0) {
                        throw new ConfigurationException("Value of [" + ACTIVE_PROJECT_CATEGORY_ID
                                + categoryEntryNumber + "] should not be negative.");
                    }
                    log(Level.DEBUG, "Adding activeCategoryId: " + categoryId);
                    this.activeCategoriesList.add(categoryId);
                }
            }

        } catch (UnknownNamespaceException ex) {
            throw new ConfigurationException("Given namespace can't be recognized by ConfigManager.", ex);
        } catch (IllegalReferenceException ex) {
            throw new ConfigurationException("IllegalReferenceException occurred when initializing ObjectFactory.",
                    ex);
        } catch (SpecificationConfigurationException ex) {
            throw new ConfigurationException(
                    "SpecificationConfigurationException occurred when initializing ObjectFactory.", ex);
        } catch (InvalidClassSpecificationException ex) {
            throw new ConfigurationException("The configuration for ObjectFactory is invalid.", ex);
        } catch (NumberFormatException ex) {
            throw new ConfigurationException("Long value in configuration can not be converted to 'long' type.", ex);
        } catch (IllegalArgumentException ex) {
            throw new ConfigurationException("Some configuration value for ObjectFactory is null or empty.", ex);
        } catch (ClassCastException ex) {
            throw new ConfigurationException("Object created by ObjectFactory can not be casted to specific type.",
                    ex);
        }
    }

    /**
     * <p>
     * Parameter constructor.
     * </p>
     *
     * @param projectRetrieval
     *            the ProjectRetrieval instance that is used to retrieve the project technologies
     *            information
     * @param resourceManager
     *            the ResourceManager instance that is used to retrieve resources
     * @param phaseManager
     *            the PhaseManager instance that is used to obtain phase information about a project
     * @param teamManager
     *            the TeamManager instance that is used to retrieve teams
     * @param projectManager
     *            the ProjectManager instance that is used to retrieve projects
     * @param projectLinkManager
     *            the ProjectLinkManager instance that is used to manage project links
     * @param scorecardManager
     *            the ScorecardManager instance that is used to manage scorecards
     * @param reviewManager
     *            the ReviewManager instance that is used to manage reviews
     * @param logger
     *            used to log information
     * @param activeProjectStatusId
     *            the id of the active project status
     * @throws IllegalArgumentException
     *             if projectRetrieval or resourceManager or phaseManager or teamManager or
     *             projectManager is null, or activeProjectStatusId is negative
     */
    public ProjectServicesImpl(ProjectRetrieval projectRetrieval, ResourceManager resourceManager,
            PhaseManager phaseManager, TeamManager teamManager, ProjectManager projectManager, Log logger,
            long activeProjectStatusId, PhaseTemplate phaseTemplate, ProjectLinkManager projectLinkManager,
            ScorecardManager scorecardManager, ReviewManager reviewManager) {
        Util.checkObjNotNull(projectRetrieval, "projectRetrieval", null);
        Util.checkObjNotNull(resourceManager, "resourceManager", null);
        Util.checkObjNotNull(phaseManager, "phaseManager", null);
        Util.checkObjNotNull(teamManager, "teamManager", null);
        Util.checkObjNotNull(projectManager, "projectManager", null);
        Util.checkObjNotNull(projectLinkManager, "projectLinkManager", null);
        Util.checkObjNotNull(scorecardManager, "scorecardManager", null);
        Util.checkObjNotNull(reviewManager, "reviewManager", null);
        Util.checkIDNotNegative(activeProjectStatusId, "activeProjectStatusId", null);

        this.projectRetrieval = projectRetrieval;
        this.resourceManager = resourceManager;
        this.phaseManager = phaseManager;
        this.teamManager = teamManager;
        this.projectManager = projectManager;
        this.projectLinkManager = projectLinkManager;
        this.scorecardManager = scorecardManager;
        this.reviewManager = reviewManager;
        this.logger = logger;
        this.activeProjectStatusId = activeProjectStatusId;
        this.template = phaseTemplate;
    }


    /**
     * <p>
     * Creates new object by ObjectFactory.
     * </p>
     *
     * @param cm
     *            ConfigManager instance
     * @param objFactory
     *            ObjectFactory instance
     * @param namespace
     *            the namespace of configuration
     * @param propertyName
     *            the property name in configuration
     * @return the new created object
     * @throws IllegalArgumentException
     *             if any property value in configuration is null or empty
     * @throws InvalidClassSpecificationException
     *             if configuration for ObjectFactory is invalid
     * @throws UnknownNamespaceException
     *             if given namespace is unknown by ConfigManager
     */
    private Object createObject(ConfigManager cm, ObjectFactory objFactory, String namespace, String propertyName)
            throws InvalidClassSpecificationException, UnknownNamespaceException {
        // gets the property value by ConfigManager
        String propertyValue = cm.getString(namespace, propertyName);
        // creates a new object using ObjectFactory
        return objFactory.createObject(propertyValue);
    }

    /**
     * <p>
     * Logs messages if necessary.
     * </p>
     *
     * @param level
     *            the log level
     * @param msg
     *            the log message
     */
    private void log(Level level, String msg) {
        if (logger != null) {
            logger.log(level, msg);
        }
    }

    /**
     * <p>
     * Logs the exceptions.
     * </p>
     *
     * @param ex
     *            The exception to log.
     * @param msg
     *            The message
     * @since 1.1
     */
    private void logError(Exception ex, String msg) {
        if (logger != null) {
            logger.log(Level.ERROR, ex, msg);
        }
    }

    /**
     * <p>
     * Logs debug level message while calling external Topcoder classes.
     * </p>
     *
     * @param msg
     *            the logging message
     */
    private void logDebug(String msg) {
        log(Level.DEBUG, msg);
    }

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
    public FullProjectData[] findActiveProjects() {
        log(Level.INFO, "Enters ProjectServicesImpl#findActiveProjects method.");
        // finds the active projects
        Project[] projects = findActiveProjectsHeaders();
        // assembles the FullProjectDatas
        FullProjectData[] fullDatas = assembleFullProjectDatas(projects);

        log(Level.INFO, "Exits ProjectServicesImpl#findActiveProjects method.");
        return fullDatas;
    }

    /**
     * <p>
     * This method finds all active projects. Returns empty array if no projects found.
     * </p>
     *
     * @return Project array with project info, or empty array if none found
     * @throws ProjectServicesException
     *             If there is a system error while performing the search
     */
    public Project[] findActiveProjectsHeaders() {
        log(Level.INFO, "Enters ProjectServicesImpl#findActiveProjectsHeaders method.");

        // represents the active projects
        Project[] projects = null;
        try {
            // find all projects
            logDebug("Starts calling ProjectManager#searchProjects method.");

            Filter filter = ProjectFilterUtility.buildStatusIdEqualFilter(activeProjectStatusId);

            for (long categoryId : activeCategoriesList) {
                Filter filterForCategory = ProjectFilterUtility.buildCategoryIdEqualFilter(categoryId);
                filter = ProjectFilterUtility.buildAndFilter(filterForCategory, filter);
            }

            projects = projectManager.searchProjects(filter);

            logDebug("Finished calling ProjectManager#searchProjects method.");

        } catch (PersistenceException ex) {
            log(Level.ERROR,
                    "ProjectServicesException occurred in ProjectServicesImpl#findActiveProjectsHeaders method.");
            throw new ProjectServicesException("PersisteceException occurred when operating ProjectManager.", ex);
        }

        log(Level.INFO, "Exits ProjectServicesImpl#findActiveProjectsHeaders method.");
        return projects;
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
        log(Level.INFO, "Enters ProjectServicesImpl#findAllTcDirectProjects(tcSubject) method.");

        // represents the active projects
        Project[] projects = null;
        try {
            // find all projects which have tc direct project id
            logDebug("Starts calling ProjectManager#getAllTcDirectProjects(tcSubject) method.");

            projects = projectManager.getAllTcDirectProjects();

            logDebug("Finished calling ProjectManager#getAllTcDirectProjects(tcSubject) method.");

        } catch (PersistenceException ex) {
            log(Level.ERROR,
                    "ProjectServicesException occurred in ProjectServicesImpl#findAllTcDirectProjects(tcSubject) method.");
            throw new ProjectServicesException("PersisteceException occurred when operating ProjectManager.", ex);
        }

        log(Level.INFO, "Exits ProjectServicesImpl#findAllTcDirectProjects(tcSubject) method.");
        return projects;

    }

    /**
     * <p>
     * This method finds all given user tc direct projects . Returns empty array if no projects found.
     * </p>
     * <p>
     * Update in v1.4.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param user The user to search for projects
     * @return   Project array with project info, or empty array if none found
     */
    public Project[] findAllTcDirectProjectsForUser(TCSubject tcSubject, String user) {
        log(Level.INFO, "Enters ProjectServicesImpl#findAllTcDirectProjectsForUser(tcSubject, user) method.");

        // represents the active projects
        Project[] projects = null;
        try {
            // find all projects which have tc direct project id
            logDebug("Starts calling ProjectManager#getAllTcDirectProjects(tcSubject, user) method.");

            projects = projectManager.getAllTcDirectProjects(user);

            logDebug("Finished calling ProjectManager#getAllTcDirectProjects(tcSubject, user) method.");

        } catch (PersistenceException ex) {
            log(Level.ERROR,
                    "ProjectServicesException occurred in ProjectServicesImpl#findAllTcDirectProjectsForUser(tcSubject, user) method.");
            throw new ProjectServicesException("PersisteceException occurred when operating ProjectManager.", ex);
        }

        log(Level.INFO, "Exits ProjectServicesImpl#findAllTcDirectProjectsForUser(tcSubject, user) method.");
        return projects;

    }

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
    public FullProjectData[] findFullProjects(Filter filter) {
        log(Level.INFO, "Enters ProjectServicesImpl#findFullProjects method.");
        Util.checkObjNotNull(filter, "filter", logger);

        // filter ProjectHeaders
        Project[] projects = findProjectsHeaders(filter);
        // assembles FullProjectDatas
        FullProjectData[] fullProjects = assembleFullProjectDatas( projects);

        log(Level.INFO, "Exits ProjectServicesImpl#findFullProjects method.");
        return fullProjects;
    }

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
    public Project[] findProjectsHeaders(Filter filter) {
        log(Level.INFO, "Enters ProjectServicesImpl#findProjectsHeaders method.");
        Util.checkObjNotNull(filter, "filter", logger);

        Project[] projects = null;
        try {
            // searches projects using given filter
            logDebug("Starts calling ProjectManager#searchProjects method.");
            projects = projectManager.searchProjects(filter);
            logDebug("Finished calling ProjectManager#searchProjects method.");

        } catch (PersistenceException ex) {
            log(Level.ERROR, "ProjectServicesException occurred in ProjectServicesImpl#findProjectsHeaders method.");
            throw new ProjectServicesException("PersistenceException occurred when searching projects.", ex);
        }

        log(Level.INFO, "Exits ProjectServicesImpl#findProjectsHeaders method.");
        return projects;
    }

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
     *            the ID of the project to retrieve
     * @throws IllegalArgumentException
     *             if projectId is negative
     * @throws ProjectServicesException
     *             if there is a system error while performing the search
     * @throws TeamPersistenceException
     *             if error occurred when searching teams
     */
    public FullProjectData getFullProjectData(long projectId) {
        log(Level.INFO, "Enters ProjectServicesImpl#getFullProjectData method.");
        Util.checkIDNotNegative(projectId, "projectId", logger);

        FullProjectData fullProjectData = null;
        try {
            // gets the phase project
            logDebug("Starts calling PhaseManager#getPhases method.");
            com.topcoder.project.phases.Project phaseProject = phaseManager.getPhases(projectId);
            logDebug("Finished calling PhaseManager#getPhases method.");
            if (phaseProject == null) {
                log(Level.INFO, "Exits ProjectServicesImpl#getFullProjectData method.");
                return null;
            }

            // creates an instance of FullProjectData with phaseProject
            fullProjectData = new FullProjectData(phaseProject.getStartDate(), phaseProject.getWorkdays());
            Phase[] allPhases = phaseProject.getAllPhases();
            for (int i = 0; i < allPhases.length; i++) {
                fullProjectData.addPhase(allPhases[i]);
            }

            // gets the project header
            logDebug("Starts calling ProjectManager#getProject method.");
            Project projectHeader = projectManager.getProject(projectId);
            logDebug("Finished calling ProjectManager#getProject method.");
            // if not found, return null
            if (projectHeader == null) {
                log(Level.INFO, "Exits ProjectServicesImpl#getFullProjectData method.");
                return null;
            }
            // sets the project header
            fullProjectData.setProjectHeader(projectHeader);

            // searches the resources associated with give project
            logDebug("Starts calling ResourceManager#searchResources method.");
            Resource[] resources = resourceManager.searchResources(ResourceFilterBuilder
                    .createProjectIdFilter(projectId));
            logDebug("Finished calling ResourceManager#searchResources method.");
            // sets the resources to fullProjectData
            fullProjectData.setResources(resources);

            // searches the teams associated with given project
            /*logDebug("Starts calling TeamManager#findTeams method.");
            TeamHeader[] teams = teamManager.findTeams(projectId);
            logDebug("Finished calling TeamManager#findTeams method.");
            // sets the teams to fullProjectData
            fullProjectData.setTeams(teams); */

            // get the contest sale
            fullProjectData.setContestSales(this.getContestSales(projectId));
        } catch (NumberFormatException ex) {
            log(Level.ERROR, "ProjectServicesException occurred in ProjectServicesImpl#getFullProjectData method.");
            throw new ProjectServicesException("PhaseManagementException occurred when retrieving project phases.",
                    ex);
        } catch (PhaseManagementException ex) {
            log(Level.ERROR, "ProjectServicesException occurred in ProjectServicesImpl#getFullProjectData method.");
            throw new ProjectServicesException("PhaseManagementException occurred when retrieving project phases.",
                    ex);
        } catch (PersistenceException ex) {
            log(Level.ERROR, "ProjectServicesException occurred in ProjectServicesImpl#getFullProjectData method.");
            throw new ProjectServicesException("PersistenceException occurred when retrieving project.", ex);
        } catch (SearchBuilderException ex) {
            log(Level.ERROR, "ProjectServicesException occurred in ProjectServicesImpl#getFullProjectData method.");
            throw new ProjectServicesException("SearchBuilderException occurred when searching resources.", ex);
        } catch (ResourcePersistenceException ex) {
            log(Level.ERROR, "ProjectServicesException occurred in ProjectServicesImpl#getFullProjectData method.");
            throw new ProjectServicesException("ResourcePersistenceException occurred when searching resources.", ex);
        }

        log(Level.INFO, "Exits ProjectServicesImpl#getFullProjectData method.");
        return fullProjectData;
    }

    /**
     * <p>
     * Helper method to obtains full project information for each of the passed projects. The
     * returned array will be of the same dimension as the passed array, and each
     * <code>Project</code> in projects will correspond to its <code>FullProjectData</code> at
     * the same index in the returned array. Since this is a helper method, no parameter checking is
     * done.
     * </p>
     *
     * @return FullProjectData array with the full project info.
     * @param projects
     *            The projects whose full associated information needs to be retrieved
     * @throws ProjectServicesException
     *             If there is a system error while performing the search
     */
    private FullProjectData[] assembleFullProjectDatas(Project[] projects) {
        log(Level.INFO, "Enters ProjectServicesImpl#assembleFullProjectDatas method.");

        FullProjectData[] fullProjects = new FullProjectData[projects.length];
        for (int i = 0; i < projects.length; i++) {
            fullProjects[i] = getFullProjectData(projects[i].getId());
        }

        log(Level.INFO, "Exits ProjectServicesImpl#assembleFullProjectDatas method.");
        return fullProjects;
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
     * Resource class and therefore will be persisted as new resources'.
     * </p>
     * <p>
     * The logging must performed in the same manner of other methods. Read the 1.4.1 section of
     * Component Specification for further details.
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
     *            the operator used to audit the operation, cannot be null or empty
     * @return the created project.
     * @throws IllegalArgumentException
     *             if any case in the following occurs:
     *             <ul>
     *             <li>if projectHeader is null;</li>
     *             <li>if projectPhases is null or the phases of projectPhases are empty;</li>
     *             <li>if the project of phases (for each phase: phase.project) is not equal to
     *             projectPhases;</li>
     *             <li>if projectResources contains null entries;</li>
     *             <li>if for each resources: resource.id != Resource.UNSET_ID or a required field
     *             of the resource is not set : if resource.getResourceRole() is null;</li>
     *             <li>if operator is null or empty;</li>
     *             </ul>
     * @throws ProjectServicesException
     *             if there is a system error while performing the create operation
     * @since 1.1
     */
    public FullProjectData createProject(Project projectHeader, com.topcoder.project.phases.Project projectPhases,
            Resource[] projectResources, String operator) {
        Util.log(logger, Level.INFO, "Enters ProjectServicesImpl#createProject method.");

        ExceptionUtils.checkNull(projectHeader, null, null, "The parameter[projectHeader] should not be null.");

        // check projectPhases
        ExceptionUtils.checkNull(projectPhases, null, null, "The parameter[projectPhases] should not be null.");
        if (projectPhases.getAllPhases().length == 0) {
            throw new IllegalArgumentException("The phases of projectPhases should not be empty.");
        }
        // if the project of phases (for each phase: phase.project)
        // is not equal to projectPhases, throws IAE
        for (Phase phase : projectPhases.getAllPhases()) {
            phase.setProject(projectPhases);
            /*if (!phase.getProject().equals(projectPhases)) {
                throw new IllegalArgumentException(
                        "The Project of phase in projectPhases should equal to the projectPhases.");
            }*/
        }

        // check projectResources
        if (projectResources != null) {
            Util.checkArrrayHasNull(projectResources, "projectResources");
            for (Resource resource : projectResources) {
                // check resource.id, if resource.id != Resource.UNSET_ID then throw IAE
                if (Resource.UNSET_ID != resource.getId()) {
                    throw new IllegalArgumentException("The resource.id should be UNSET_ID.");
                }
                ExceptionUtils.checkNull(resource.getResourceRole(), null, null,
                        "The ResourceRole of resource in projectResources should not be null.");
            }
        }

        ExceptionUtils.checkNullOrEmpty(operator, null, null, "The parameter[operator] should not be null or empty.");

        try {
            // call projectManager.createProject(projectHeader,operator)
            Util.log(logger, Level.DEBUG, "Starts calling ProjectManager#createProject method.");
            projectManager.createProject(projectHeader, operator);
            Util.log(logger, Level.DEBUG, "Finished calling ProjectManager#createProject method.");

            // at this point projectHeader will have the id set
            // set the projectPhases.id to projectHeader.id
            projectPhases.setId(projectHeader.getId());

            // for each phase in projectPhases.phases:
            for (Phase phase : projectPhases.getAllPhases()) {
                // set the phase.id to 0 (id not set)
                phase.setId(0);
            }

            // call phaseManager.updatePhases(projectPhases,operator)
            Util.log(logger, Level.DEBUG, "Starts calling ProjectManager#updatePhases method.");
            phaseManager.updatePhases(projectPhases, operator);
            Util.log(logger, Level.DEBUG, "Finished calling ProjectManager#updatePhases method.");

            // if projectResources are not null and not empty:
            if (projectResources != null && projectResources.length > 0) {
                // for each resource: set the project to projectHeader.id
                for (Resource resource : projectResources) {
                    resource.setProject(projectHeader.getId());
                }

                // call resourceManager.updateResources(projectResources, projectHeader.getId(),
                // operator);
                Util.log(logger, Level.DEBUG, "Starts calling ResourceManager#updateResources method.");
                projectResources = resourceManager.updateResources(projectResources, projectHeader.getId(), operator);
                Util.log(logger, Level.DEBUG, "Finished calling ResourceManager#updateResources method.");
            }

            // creates an instance of FullProjectData with phaseProject
            FullProjectData projectData = new FullProjectData(projectPhases.getStartDate(), projectPhases.getWorkdays());
            projectData.setId(projectPhases.getId());
            Phase[] allPhases = projectPhases.getAllPhases();
            for (int i = 0; i < allPhases.length; i++) {
                projectData.addPhase(allPhases[i]);
            }

            // sets the project header
            projectData.setProjectHeader(projectHeader);

            // sets the resources to fullProjectData
            projectData.setResources(projectResources);

            return projectData;
        } catch (PersistenceException e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "PersisteceException occurred in ProjectServicesImpl#createProject method : " + e.getMessage(), e);
            logError(e, pse.getMessage());
            throw pse;
        } catch (ValidationException e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "ValidationException occurred in ProjectServicesImpl#createProject method : " + e.getMessage(), e);
            logError(e, pse.getMessage());
            throw pse;
        } catch (PhaseManagementException e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "PhaseManagementException occurred in ProjectServicesImpl#createProject method : "
                            + e.getMessage(), e);
            logError(e, pse.getMessage());
            throw pse;
        } catch (ResourcePersistenceException e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "ResourcePersistenceException occurred in ProjectServicesImpl#createProject method : "
                            + e.getMessage(), e);
            logError(e, pse.getMessage());
            throw pse;
        }
         catch (Exception e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "Exception occurred in ProjectServicesImpl#createProject method : "
                            + e.getMessage(), e);
            logError(e, pse.getMessage());
            throw pse;
        } finally {
            Util.log(logger, Level.INFO, "Exits ProjectServicesImpl#createProject method.");
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
     * <p>
     * The logging must performed in the same manner of other methods. Read the 1.4.1 section of
     * Component Specification for further details.
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
            com.topcoder.project.phases.Project projectPhases, Resource[] projectResources, String operator) {
        return updateProject(projectHeader, projectHeaderReason, projectPhases, projectResources, null, operator);
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
        return updateProject(projectHeader, projectHeaderReason, projectPhases, projectResources, multiRoundEndDate, null, operator);
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
     * @since 1.4.7
     */
    public FullProjectData updateProject(Project projectHeader, String projectHeaderReason,
            com.topcoder.project.phases.Project projectPhases, Resource[] projectResources, Date multiRoundEndDate, Date endDate, String operator) {
        Util.log(logger, Level.INFO, "Enters ProjectServicesImpl#updateProject method.");

        // check projectHeader
        ExceptionUtils.checkNull(projectHeader, null, null, "The parameter[projectHeader] should not be null.");
        if (projectHeader.getId() <= 0) {
            throw new IllegalArgumentException("The projectHeader.id must be positive.");
        }

        // check projectHeaderReason
        ExceptionUtils.checkNullOrEmpty(projectHeaderReason, null, null,
                "The parameter[projectHeaderReason] should not be null or empty.");

        if (template != null)
        {
            projectPhases.setWorkdays(template.getWorkdays());
        }


        validateUpdatePhases(projectHeader, projectPhases);
        validateUpdateResources(projectHeader, projectPhases, projectResources);

        // check operator
        ExceptionUtils.checkNullOrEmpty(operator, null, null, "The parameter[operator] should not be null or empty.");

        try {
            boolean hasMultiRoundBefore = false;
            long fixedStart = projectPhases.getStartDate().getTime();
            
            for (Phase phase : projectPhases.getAllPhases()) {
                if (phase.getPhaseType().getId() == PhaseType.CHECKPOINT_SUBMISSION_PHASE.getId()) {
                    hasMultiRoundBefore = true;
                    break;
                }
            }
            // get the project calling projectManager.getProject(projectHeader.getId())
            Util.log(logger, Level.DEBUG, "Starts calling ProjectManager#createProject method.");
            Project project = projectManager.getProject(projectHeader.getId());
            Util.log(logger, Level.DEBUG, "Finished calling ProjectManager#createProject method.");
            // if the project does not exist then throw the ProjectDoesNotExistException
            if (project == null) {
                ProjectDoesNotExistException pde = new ProjectDoesNotExistException(
                        "The project with the specified id does not exist.", projectHeader.getId());
                Util.log(logger, Level.ERROR, pde.getMessage());
                throw pde;
            }

            String billingProject = projectHeader.getProperty(ProjectPropertyType.BILLING_PROJECT_PROJECT_PROPERTY_KEY);

            long billingProjectId = 0;

            if (billingProject != null && !billingProject.equals("") && !billingProject.equals("0")) {
                billingProjectId = Long.parseLong(billingProject);
            }

            // check whether billing project id requires approval phase
            boolean requireApproval = projectManager.requireApprovalPhase(billingProjectId);
            boolean isStudio = (projectHeader.getProjectCategory().getProjectType().getId() == ProjectType.STUDIO.getId());
            boolean isAlgorithm = (projectHeader.getProjectCategory().getId() ==
                    ProjectCategory.MARATHON_MATCH.getId());
            boolean isFirst2Finish = (projectHeader.getProjectCategory().getId() ==
                    ProjectCategory.FIRST2FINISH.getId());
            boolean isCode = (projectHeader.getProjectCategory().getId() ==
                    ProjectCategory.CODE.getId());

			if (!isStudio)
			{
				projectHeader.setProperty(ProjectPropertyType.POST_MORTEM_REQUIRED_PROJECT_PROPERTY_KEY, String
						.valueOf(getBooleanClientProjectConfig(billingProjectId, BillingProjectConfigType.POST_MORTEM_REQUIRED)));
			}
			else
			{
				projectHeader.setProperty(ProjectPropertyType.POST_MORTEM_REQUIRED_PROJECT_PROPERTY_KEY, "false");
			}

            projectHeader.setProperty(ProjectPropertyType.RELIABILITY_BONUS_ELIGIBLE_PROJECT_PROPERTY_KEY, String
                    .valueOf(getBooleanClientProjectConfig(billingProjectId, BillingProjectConfigType.RELIABILITY_BONUS_ELIGIBLE)));

            projectHeader.setProperty(ProjectPropertyType.MEMBER_PAYMENT_ELIGIBLE_PROJECT_PROPERTY_KEY, String
                    .valueOf(getBooleanClientProjectConfig(billingProjectId, BillingProjectConfigType.MEMBER_PAYMENT_ELIGIBLE)));

            projectHeader.setProperty(ProjectPropertyType.SEND_WINNDER_EMAILS_PROJECT_PROPERTY_KEY, String
                    .valueOf(getBooleanClientProjectConfig(billingProjectId, BillingProjectConfigType.SEND_WINNER_EMAILS)));

            if (isStudio) {
                // Studio contest has no approval phase to be created after Final Review; it will get created after
                // Review phase based on the template
                projectHeader.setProperty(ProjectPropertyType.APPROVAL_REQUIRED_PROJECT_PROPERTY_KEY, "false");
            } else {
                // For the rest of the contests the Approval phase will get created after Final Review phase if
                // is required by the configuration for respective client account
                projectHeader.setProperty(ProjectPropertyType.APPROVAL_REQUIRED_PROJECT_PROPERTY_KEY, String
                        .valueOf(requireApproval));
            }

            // hard code the flags for the First2Finish and CODE contest type
            if(projectHeader.getProjectCategory().getId() == ProjectCategory.FIRST2FINISH.getId()
                  || projectHeader.getProjectCategory().getId() == ProjectCategory.CODE.getId()
                  || projectHeader.getProjectCategory().getId() == ProjectCategory.DESIGN_FIRST2FINISH.getId()) {
                projectHeader.setProperty(ProjectPropertyType.RELIABILITY_BONUS_ELIGIBLE_PROJECT_PROPERTY_KEY, "false");
                projectHeader.setProperty(ProjectPropertyType.APPROVAL_REQUIRED_PROJECT_PROPERTY_KEY, "false");
                projectHeader.setProperty(ProjectPropertyType.POST_MORTEM_REQUIRED_PROJECT_PROPERTY_KEY, "false");
            }

            boolean requireSpecReview = getBooleanClientProjectConfig(billingProjectId, BillingProjectConfigType.SPEC_REVIEW_REQUIRED);
            
            boolean needResetPhases = hasMultiRoundBefore != (multiRoundEndDate != null); 
		
            if (needResetPhases) {
                // need to reset the project phases
                long[] leftOutPhaseIds;
                if (isStudio) {
                    // For Studio contests Approval phase must be included into timeline based on template
                    leftOutPhaseIds 
                        = getLeftOutPhaseIds(projectHeader, true, requireSpecReview, multiRoundEndDate != null);
                } else {
                    // For the rest of contests Approval phase must be included only if required by configuration
                    // for respective client account
                    leftOutPhaseIds = getLeftOutPhaseIds(projectHeader, requireApproval, requireSpecReview,
                                                         multiRoundEndDate != null);
                }
                String templateName = getPhaseTemplateName(projectHeader);
                if (templateName == null) {
                    throw new PhaseTemplateException("No template found for type " + projectHeader.getProjectCategory().getProjectType().getName()
                            + " or category " + projectHeader.getProjectCategory().getName());
                }
                com.topcoder.project.phases.Project newProjectPhases = template.applyTemplate(templateName, leftOutPhaseIds,
                        PhaseType.REGISTRATION_PHASE.getId(), PhaseType.REGISTRATION_PHASE.getId(), projectPhases.getStartDate(), projectPhases.getStartDate());
                if (multiRoundEndDate != null) {
                    adjustPhaseForEndDate(PhaseType.CHECKPOINT_SUBMISSION_PHASE, newProjectPhases, multiRoundEndDate);
                }
                if (endDate != null) {
                    adjustPhaseForEndDate(PhaseType.SUBMISSION_PHASE, newProjectPhases, endDate);
                }
                setNewPhasesProperties(projectHeader, newProjectPhases, (multiRoundEndDate != null), isStudio);
                newProjectPhases.setId(projectPhases.getId());
                for (Phase phase : newProjectPhases.getAllPhases()) {
                    phase.setProject(newProjectPhases);
                    phase.setId(0);
                }
                projectPhases = newProjectPhases;
            }
            
            // call projectManager.updateProject(projectHeader,projectHeaderReason,operator)
            Util.log(logger, Level.DEBUG, "Starts calling ProjectManager#updateProject method.");
            projectManager.updateProject(projectHeader, projectHeaderReason, operator);
            Util.log(logger, Level.DEBUG, "Finished calling ProjectManager#updateProject method.");

            if (!needResetPhases) {
                // recalcuate phase dates in case project start date changes
                Phase[] phases = projectPhases.getAllPhases();
                Map phasesMap = new HashMap();
                
                 for (Phase p : phases) {
                            phasesMap.put(new Long(p.getId()), p);
                            p.setScheduledStartDate(null);
                            p.setScheduledEndDate(null);
                            p.setFixedStartDate(null);
                 }
                 phaseManager.fillDependencies(phasesMap, new long[]{projectPhases.getId()});
                
    
                Phase subPhase = null;
                 for (Phase p : phases) {
                            p.setScheduledStartDate(p.calcStartDate());
                            p.setScheduledEndDate(p.calcEndDate());
                            // only set Reg with fixed dates
                            if (p.getPhaseType().getId() == PhaseType.REGISTRATION_PHASE.getId()
                                  || p.getPhaseType().getId() == PhaseType.SPECIFICATION_SUBMISSION_PHASE.getId())
                            {
                                p.setFixedStartDate(p.calcStartDate());
                            }
                            if (p.getPhaseType().getId() == PhaseType.SUBMISSION_PHASE.getId()) {
                            	subPhase = p;
                            }
                }
    
               
    
                long diff = 0;
                long subRegDiff = 0;
                for (Phase p : phases) {
                            phasesMap.put(new Long(p.getId()), p);
                            // check the diff between project start date and reg phase start date
                            if (p.getPhaseType().getId() == PhaseType.REGISTRATION_PHASE.getId()) {  
                                    diff = projectPhases.getStartDate().getTime() - p.calcStartDate().getTime();
                                    subRegDiff = subPhase.calcStartDate().getTime() - p.calcStartDate().getTime(); 
									// if reg already starts, then fixStart will use reg actual for calculation below
									if (p.getActualStartDate() != null)
									{
										fixedStart = p.getActualStartDate().getTime();
									}
                            }
                 }
    
    
                
                // adjust project start date so reg start date is the passed project start date
                projectPhases.setStartDate(new Date(projectPhases.getStartDate().getTime() + diff));
    
                for (Phase p : phases) {
                            phasesMap.put(new Long(p.getId()), p);
                            p.setScheduledStartDate(null);
                            p.setScheduledEndDate(null);
                            p.setFixedStartDate(null);
                }
                phaseManager.fillDependencies(phasesMap, new long[]{projectPhases.getId()});

                subRegDiff = isStudio ? 0 : -subRegDiff;

                if (multiRoundEndDate != null) {
                    // multiround phase duration
                    Util.log(logger, Level.INFO, "set duration for multi round phase");
                    Phase multiRoundPhase = null;
                    for (Phase phase : phases) {
                        if (phase.getPhaseType().getId() == PhaseType.CHECKPOINT_SUBMISSION_PHASE.getId()) {
                            multiRoundPhase = phase;
                            break;
                        }
                    }
                    if (multiRoundPhase != null) {
                        multiRoundPhase.setLength(multiRoundEndDate.getTime() - fixedStart + subRegDiff);
                    }
                }
                if (endDate != null) {       
                    // submission phase duration
                    Util.log(logger, Level.INFO, "set duration for submission phase");
                    Phase submissionPhase = null;
                    Phase registrationPhase = null;
                    for (Phase phase : phases) {
                        if (phase.getPhaseType().getId() == PhaseType.SUBMISSION_PHASE.getId()) {
                            submissionPhase = phase;
                            break;
                        }
                    }

                    for (Phase phase : phases) {
                        if (phase.getPhaseType().getId() == PhaseType.REGISTRATION_PHASE.getId()) {
                            registrationPhase = phase;
                            break;
                        }
                    }

                    if (submissionPhase != null) {
                        submissionPhase.setLength(endDate.getTime() - fixedStart + subRegDiff);
                    }

                    if (registrationPhase != null && (isStudio || isAlgorithm || isFirst2Finish || isCode || submissionPhase.getLength() <= (48 * 60 * 60 * 1000))) {
                        registrationPhase.setLength(endDate.getTime() - fixedStart);
                    }
                }
    
                for (Phase p : phases) {
                    p.setScheduledStartDate(p.calcStartDate());
                    p.setScheduledEndDate(p.calcEndDate());
                    // only set Reg amd Spec Sub with fixed dates
                    if (p.getPhaseType().getId() == PhaseType.SPECIFICATION_SUBMISSION_PHASE.getId()) {
                        p.setFixedStartDate(p.calcStartDate());
                    }
                    if (p.getPhaseType().getId() == PhaseType.REGISTRATION_PHASE.getId()) {
                        p.setFixedStartDate(new Date(fixedStart));
                    }

                    if (projectHeader.getProjectCategory().getId() == ProjectCategory.CODE.getId() &&
                            projectHeader.getAutoAssignReviewerId() > 0 && p.getPhaseType().getId() == PhaseType.REVIEW_PHASE.getId()) {
                        // code with auto assigned review only requires one reviewer.
                        p.setAttribute("Reviewer Number", "1");
                    } else if (projectHeader.getProjectCategory().getId() == ProjectCategory.CODE.getId() &&
                            projectHeader.getAutoAssignReviewerId() == 0 && p.getPhaseType().getId() == PhaseType.REVIEW_PHASE.getId()) {
                        // code with auto assigned review only requires one reviewer.
                        p.setAttribute("Reviewer Number", "2");
                    }
                }
            }

            // call phaseManager.updatePhases(projectPhases,operator)
            Util.log(logger, Level.DEBUG, "Starts calling PhaseManager#updatePhases method.");
            phaseManager.updatePhases(projectPhases, operator);
            Util.log(logger, Level.DEBUG, "Finished calling PhaseManager#updatePhases method.");

            // if projectResources are not null and not empty, call
            // resourceManager.updateResources(projectResources, projectHeader.getId(), operator);
            if (projectResources != null && projectResources.length > 0 && 
						(project.getProjectStatus().getId() == ProjectStatus.ACTIVE.getId() || 
							project.getProjectStatus().getId() == ProjectStatus.DRAFT.getId())) {
                Util.log(logger, Level.DEBUG, "Starts calling ResourceManager#updateResources method.");
                resourceManager.updateResources(projectResources, projectHeader.getId(), operator);
                Util.log(logger, Level.DEBUG, "Finished calling ResourceManager#updateResources method.");
            }


            // creates an instance of FullProjectData with phaseProject
            FullProjectData projectData = new FullProjectData(projectPhases.getStartDate(), projectPhases.getWorkdays());
            projectData.setId(projectPhases.getId());
            Phase[] allPhases = projectPhases.getAllPhases();
            for (int i = 0; i < allPhases.length; i++) {
                projectData.addPhase(allPhases[i]);
            }

            // sets the project header
            projectData.setProjectHeader(projectHeader);

            // sets the resources to fullProjectData
            projectData.setResources(projectResources);

            return projectData;
        } catch (PersistenceException e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "PersistenceException occurred in ProjectServicesImpl#updateProject method : " + e.getMessage(),
                    e);
            logError(e, pse.getMessage());
            throw pse;
        } catch (ValidationException e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "ValidationException occurred in ProjectServicesImpl#updateProject method : " + e.getMessage(), e);
            logError(e, pse.getMessage());
            throw pse;
        } catch (PhaseManagementException e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "PhaseManagementException occurred in ProjectServicesImpl#updateProject method : "
                            + e.getMessage(), e);
            logError(e, pse.getMessage());
            throw pse;
        } catch (ResourcePersistenceException e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "ResourcePersistenceException occurred in ProjectServicesImpl#updateProject method : "
                            + e.getMessage(), e);
            logError(e, pse.getMessage());
            throw pse;
        }
        catch (Exception e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "Exception occurred in ProjectServicesImpl#updateProject method : "
                            + e.getMessage(), e);
            logError(e, pse.getMessage());
            throw pse;
        }
        finally {
            Util.log(logger, Level.INFO, "Exits ProjectServicesImpl#updateProject method.");
        }
    }

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
    public ContestSaleData createContestSale(ContestSaleData contestSaleData) throws ProjectServicesException {
        Util.log(logger, Level.INFO, "Enters ProjectServicesImpl#createContestSale method.");

        ExceptionUtils.checkNull(contestSaleData, null, null, "The parameter[contestSaleData] should not be null.");

        try {
            ContestSale contestSale = convertContestSaleData(contestSaleData);

            contestSale = projectManager.createContestSale(contestSale);

            return convertContestSale(contestSale);
        } catch (PersistenceException e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "PersisteceException occurred in ProjectServicesImpl#createContestSale method : " + e.getMessage(),
                    e);
            logError(e, pse.getMessage());
            throw pse;
        } finally {
            Util.log(logger, Level.INFO, "Exits ProjectServicesImpl#createContestSale method.");
        }
    }

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
    public void updateContestSale(ContestSaleData contestSaleData) throws ProjectServicesException {
        Util.log(logger, Level.INFO, "Enters ProjectServicesImpl#updateContestSale method.");

        ExceptionUtils.checkNull(contestSaleData, null, null, "The parameter[contestSaleData] should not be null.");

        try {
            ContestSale contestSale = convertContestSaleData(contestSaleData);

            projectManager.updateContestSale(contestSale);
        } catch (PersistenceException e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "PersisteceException occurred in ProjectServicesImpl#updateContestSale method : " + e.getMessage(),
                    e);
            logError(e, pse.getMessage());
            throw pse;
        } finally {
            Util.log(logger, Level.INFO, "Exits ProjectServicesImpl#updateContestSale method.");
        }
    }
    
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
    public ContestSaleData getContestSale(long contestSaleId) throws ProjectServicesException {
        Util.log(logger, Level.INFO, "Enters ProjectServicesImpl#getContestSale method.");

        try {
            ContestSale contestSale = projectManager.getContestSale(contestSaleId);

            return convertContestSale(contestSale);
        } catch (PersistenceException e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "PersisteceException occurred in ProjectServicesImpl#getContestSale method : " + e.getMessage(),
                    e);
            logError(e, pse.getMessage());
            throw pse;
        } finally {
            Util.log(logger, Level.INFO, "Exits ProjectServicesImpl#getContestSale method.");
        }
    }

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
    public List<ContestSaleData> getContestSales(long contestId) throws ProjectServicesException {
        Util.log(logger, Level.INFO, "Enters ProjectServicesImpl#getContestSales method.");

        try {
            List<ContestSale> contestSales = projectManager.getContestSales(contestId);

            List<ContestSaleData> ret = new ArrayList<ContestSaleData>();

            for (ContestSale contestSale : contestSales) {
                ret.add(convertContestSale(contestSale));
            }

            return ret;
        } catch (PersistenceException e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "PersisteceException occurred in ProjectServicesImpl#getContestSales method : " + e.getMessage(),
                    e);
            logError(e, pse.getMessage());
            throw pse;
        } finally {
            Util.log(logger, Level.INFO, "Exits ProjectServicesImpl#getContestSales method.");
        }
    }

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
    public void editContestSale( ContestSaleData contestSaleData) throws ProjectServicesException {
        Util.log(logger, Level.INFO, "Enters ProjectServicesImpl#editContestSale method.");

        ExceptionUtils.checkNull(contestSaleData, null, null, "The parameter[contestSaleData] should not be null.");

        try {
            ContestSale contestSale = convertContestSaleData(contestSaleData);

            projectManager.editContestSale(contestSale);
        } catch (PersistenceException e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "PersisteceException occurred in ProjectServicesImpl#editContestSale method : " + e.getMessage(),
                    e);
            logError(e, pse.getMessage());
            throw pse;
        } finally {
            Util.log(logger, Level.INFO, "Exits ProjectServicesImpl#editContestSale method.");
        }
    }

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
    public boolean removeContestSale( long contestSaleId) throws ProjectServicesException {
        Util.log(logger, Level.INFO, "Enters ProjectServicesImpl#removeContestSale method.");

        try {
            return projectManager.removeContestSale(contestSaleId);
        } catch (PersistenceException e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "PersisteceException occurred in ProjectServicesImpl#removeContestSale method : " + e.getMessage(),
                    e);
            logError(e, pse.getMessage());
            throw pse;
        } finally {
            Util.log(logger, Level.INFO, "Exits ProjectServicesImpl#removeContestSale method.");
        }
    }

    /**
     * This method used to convert ContestSaleData object into ContestSale object.
     *
     * @param data ContestSaleData object to convert.
     *
     * @return converted ContestSale instance
     *
     * @throws PersistenceException when error reported by manager
     */
    private ContestSale convertContestSaleData(ContestSaleData data) throws PersistenceException {
        ContestSale result = new ContestSale();
        result.setContestSaleId(data.getContestSaleId());
        result.setContestId(data.getContestId());
        result.setPayPalOrderId(data.getPaypalOrderId());
        result.setPrice(data.getPrice());
        result.setCreateDate(data.getCreateDate());

        SaleStatus status = projectManager.getSaleStatus(data.getSaleStatusId());
        result.setStatus(status);
        result.setSaleReferenceId(data.getSaleReferenceId());

        SaleType saleType = projectManager.getSaleType(data.getSaleTypeId());
        result.setSaleType(saleType);

        return result;
    }

    /**
     * This method converts ContestSale object into ContestSaleData object.
     *
     * @param contestSale ContestSale instance to convert
     *
     * @return converted ContestSaleDate object
     */
    private ContestSaleData convertContestSale(ContestSale contestSale) {
        ContestSaleData contestSaleData = new ContestSaleData();
        contestSaleData.setContestSaleId(contestSale.getContestSaleId());
        contestSaleData.setContestId(contestSale.getContestId());
        contestSaleData.setPaypalOrderId(contestSale.getPayPalOrderId());
        contestSaleData.setPrice(contestSale.getPrice());
        contestSaleData.setCreateDate(contestSale.getCreateDate());
        contestSaleData.setSaleStatusId(contestSale.getStatus().getSaleStatusId());
        contestSaleData.setSaleReferenceId(contestSale.getSaleReferenceId());
        contestSaleData.setSaleTypeId(contestSale.getSaleType().getSaleTypeId());

        return contestSaleData;
    }

    /**
     * <p>
     * Validates the projectPhases in the updateProject method.
     * </p>
     *
     * @param projectHeader
     *            the project's header, the main project's data
     * @param projectPhases
     *            the project's phases
     * @throws IllegalArgumentException
     *             if any case in the following occurs:
     *             <ul>
     *             <li>if projectPhases is null</li>
     *             <li>if the phases of projectPhases are empty, </li>
     *             <li>if the projectPhases.id is not equal to projectHeader.id;</li>
     *             <li>for each phase: if the phase.object is not equal to projectPhases, </li>
     *             </ul>
     */
    private void validateUpdatePhases(Project projectHeader, com.topcoder.project.phases.Project projectPhases) {
        // if projectPhases is null or the phases of projectPhases are empty or the projectPhases.id
        // is not equal to projectHeader.id, throws IAE
        ExceptionUtils.checkNull(projectPhases, null, null, "The parameter[projectPhases] should not be null.");
        if (projectPhases.getAllPhases().length == 0) {
            throw new IllegalArgumentException("The phases of projectPhases should not be empty.");
        }
        if (projectPhases.getId() != projectHeader.getId()) {
            throw new IllegalArgumentException("The projectPhases.id should equal to projectHeader.id.");
        }
        // for each phase: if the phase.object is not equal to projectPhases or the
        // projectPhases.phases.project.id is not equal to projectHeader.id, throws IAE
        for (Phase phase : projectPhases.getAllPhases()) {
            phase.setProject(projectPhases);
            /*if (!phase.getProject().equals(projectPhases)) {
                throw new IllegalArgumentException(
                        "The Project of phase in projectPhases should equal to the projectPhases.");
            }*/
        }
    }

    /**
     * <p>
     * Validates the projectResources in the updateProject method.
     * </p>
     *
     * @param projectHeader
     *            the project's header, the main project's data
     * @param projectPhases
     *            the project's phases
     * @param projectResources
     *            the project's resources, can be null or empty, can't contain null values. Null is
     *            treated like empty.
     * @throws IllegalArgumentException
     *             if projectResources contains null entries or for each resource:
     *             <ul>
     *             <li>if resource.getResourceRole() is null</li>
     *             <li>if the resource role is associated with a phase type but the resource is not
     *             associated with a phase</li>
     *             <li>if the resource.phase (id of phase) is set but it's not in
     *             projectPhases.phases' ids</li>
     *             <li>if the resource.project (project's id) is not set or not equal to
     *             projectHeader.id;</li>
     *             </ul>
     */
    private void validateUpdateResources(Project projectHeader, com.topcoder.project.phases.Project projectPhases,
            Resource[] projectResources) {
        // check projectResources
        if (projectResources != null) {
            // if projectResources contains null entries;
            Util.checkArrrayHasNull(projectResources, "projectResources");
            // for each resource:
            for (Resource resource : projectResources) {
                ResourceRole resourceRole = resource.getResourceRole();
                // if resource.getResourceRole() is null, throws IAE
                ExceptionUtils.checkNull(resourceRole, null, null,
                        "The ResourceRole of resource in projectResources should not be null.");

                Long phaseType = resourceRole.getPhaseType();
                Long phaseId = resource.getPhase();
                // if the resourceRole#phaseType!=null then also resource#phase must be not null.
                if (phaseType != null && phaseId == null) {
                    throw new IllegalArgumentException("The resource role is associated with a phase type"
                            + " but the resource is not associated with a phase.");
                }

                // if the resource.phase (id of phase) is set but it's not in projectPhases.phases'
                // ids, throws IAE
                if (phaseId != null) {
                    // Represents the phaseId is in projectPhases.phases' ids or not
                    boolean inPhasesIds = false;
                    for (Phase phase : projectPhases.getAllPhases()) {
                        if (phaseId == phase.getId()) {
                            inPhasesIds = true;
                            break;
                        }
                    }
                    if (!inPhasesIds) {
                        throw new IllegalArgumentException(
                                "The resource.phase (id of phase) is set but it's not in projectPhases.phases' ids.");
                    }
                }

                // if the resource.project (project's id) is not set or not equal to
                // projectHeader.id, throws IAE
                Long projectId = resource.getProject();
                if (projectId == null) {
                    throw new IllegalArgumentException("The resource.project must be set.");
                } else if (projectHeader.getId() != projectId) {
                    throw new IllegalArgumentException("The resource.project must equal to prohectHeader.id.");
                }
            }
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
     * resources' ids will be set to UNSET_ID of Resource class and therefore will be persisted as new resources's.
     * </p>
     *
     * @param projectHeader
     *            the project's header, the main project's data
     * @param projectPhases
     *            the project's phases
     * @param projectResources
     *            the project's resources, can be null or empty, can't contain null values. Null is treated like empty.
     * @param operator
     *            the operator used to audit the operation, can be null but not empty
     * @throws IllegalArgumentException
     *             if any case in the following occurs:
     *             <ul>
     *             <li>if projectHeader is null;</li>
     *             <li>if projectPhases is null;</li>
     *             <li>if the project of phases (for each phase: phase.project) is not equal to projectPhases;</li>
     *             <li>if projectResources contains null entries;</li>
     *             <li>if for each resources: a required field of the resource is not set : if
     *             resource.getResourceRole() is null;</li>
     *             <li>if operator is null or empty;</li>
     *             </ul>
     * @throws ProjectServicesException
     *             if there is a system error while performing the create operation
     * @since BUGR-1473
     */
    public FullProjectData createProjectWithTemplate( Project projectHeader, com.topcoder.project.phases.Project projectPhases,
            Resource[] projectResources, String operator) {
        return createProjectWithTemplate(projectHeader, projectPhases, projectResources, null, operator);
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
        return createProjectWithTemplate(projectHeader, projectPhases, projectResources, multiRoundEndDate, null, operator);
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
     * @since 1.4.7
     */
    public FullProjectData createProjectWithTemplate(Project projectHeader, com.topcoder.project.phases.Project projectPhases,
            Resource[] projectResources, Date multiRoundEndDate, Date endDate, String operator) {
        Util.log(logger, Level.INFO, "Enters ProjectServicesImpl#createProjectWithTemplate method.");

        ExceptionUtils.checkNull(projectHeader, null, null, "The parameter[projectHeader] should not be null.");

        // check projectPhases
        ExceptionUtils.checkNull(projectPhases, null, null, "The parameter[projectPhases] should not be null.");
        try {
            String templateName = getPhaseTemplateName(projectHeader);
            boolean isStudio = (projectHeader.getProjectCategory().getProjectType().getId() == ProjectType.STUDIO.getId());
            boolean isAlgorithm = (projectHeader.getProjectCategory().getId() ==
                    ProjectCategory.MARATHON_MATCH.getId());
            boolean isFirst2Finish = (projectHeader.getProjectCategory().getId() ==
                    ProjectCategory.FIRST2FINISH.getId());
            boolean isCode = (projectHeader.getProjectCategory().getId() ==
                    ProjectCategory.CODE.getId());

             // Start BUGR-3616
            // get billing project id from the project information
            String billingProject = projectHeader.getProperty(ProjectPropertyType.BILLING_PROJECT_PROJECT_PROPERTY_KEY);

            long billingProjectId = 0;

            if (billingProject != null && !billingProject.equals("") && !billingProject.equals("0")) {
                billingProjectId = Long.parseLong(billingProject);
            }

            // check whether billing project id requires approval phase
            boolean requireApproval = projectManager.requireApprovalPhase(billingProjectId);

            if (!isStudio)
			{
				projectHeader.setProperty(ProjectPropertyType.POST_MORTEM_REQUIRED_PROJECT_PROPERTY_KEY, String
						.valueOf(getBooleanClientProjectConfig(billingProjectId, BillingProjectConfigType.POST_MORTEM_REQUIRED)));
			}
			else
			{
				projectHeader.setProperty(ProjectPropertyType.POST_MORTEM_REQUIRED_PROJECT_PROPERTY_KEY, "false");
			}

            projectHeader.setProperty(ProjectPropertyType.RELIABILITY_BONUS_ELIGIBLE_PROJECT_PROPERTY_KEY, String
                    .valueOf(getBooleanClientProjectConfig(billingProjectId, BillingProjectConfigType.RELIABILITY_BONUS_ELIGIBLE)));

            projectHeader.setProperty(ProjectPropertyType.MEMBER_PAYMENT_ELIGIBLE_PROJECT_PROPERTY_KEY, String
                    .valueOf(getBooleanClientProjectConfig(billingProjectId, BillingProjectConfigType.MEMBER_PAYMENT_ELIGIBLE)));

            projectHeader.setProperty(ProjectPropertyType.SEND_WINNDER_EMAILS_PROJECT_PROPERTY_KEY, String
                    .valueOf(getBooleanClientProjectConfig(billingProjectId, BillingProjectConfigType.SEND_WINNER_EMAILS)));

            if (!isStudio) {
            	projectHeader.setProperty(ProjectPropertyType.TRACK_LATE_DELIVERABLES_PROJECT_PROPERTY_KEY, String
                    .valueOf(getBooleanClientProjectConfig(billingProjectId, BillingProjectConfigType.TRACK_LATE_DELIVERABLES)));
            }

            if (isStudio) {
                // Studio contest has no approval phase to be created after Final Review; it will get created after
                // Review phase based on the template
                projectHeader.setProperty(ProjectPropertyType.APPROVAL_REQUIRED_PROJECT_PROPERTY_KEY, "false");
            } else {
                // For the rest of the contests the Approval phase will get created after Final Review phase if
                // is required by the configuration for respective client account
                projectHeader.setProperty(ProjectPropertyType.APPROVAL_REQUIRED_PROJECT_PROPERTY_KEY, String
                        .valueOf(requireApproval));
            }


            // hard code the flags for the First2Finish and CODE contest type
            if(projectHeader.getProjectCategory().getId() == ProjectCategory.FIRST2FINISH.getId() 
                || projectHeader.getProjectCategory().getId() == ProjectCategory.CODE.getId()) {
                projectHeader.setProperty(ProjectPropertyType.RELIABILITY_BONUS_ELIGIBLE_PROJECT_PROPERTY_KEY, "false");
                projectHeader.setProperty(ProjectPropertyType.APPROVAL_REQUIRED_PROJECT_PROPERTY_KEY, "false");
                projectHeader.setProperty(ProjectPropertyType.POST_MORTEM_REQUIRED_PROJECT_PROPERTY_KEY, "false");
            }


            boolean requireSpecReview = getBooleanClientProjectConfig(billingProjectId, BillingProjectConfigType.SPEC_REVIEW_REQUIRED);

            long[] leftOutPhaseIds;
            if (isStudio) {
                // For Studio contests Approval phase must be included into timeline based on template
                leftOutPhaseIds
                        = getLeftOutPhaseIds(projectHeader, true, requireSpecReview, multiRoundEndDate != null);
            } else {
                // For the rest of contests Approval phase must be included only if required by configuration
                // for respective client account
                leftOutPhaseIds = getLeftOutPhaseIds(projectHeader, requireApproval, requireSpecReview,
                        multiRoundEndDate != null);
            }

            if (templateName == null)
            {
                throw new PhaseTemplateException("No template found for type " + projectHeader.getProjectCategory().getProjectType().getName()
                        + " or category " + projectHeader.getProjectCategory().getName());
            }
            // apply a template with name category with a given start date
            com.topcoder.project.phases.Project newProjectPhases = template
                    .applyTemplate(templateName, leftOutPhaseIds, PhaseType.REGISTRATION_PHASE.getId(), PhaseType.REGISTRATION_PHASE.getId(), projectPhases.getStartDate(), projectPhases.getStartDate());

            if (multiRoundEndDate != null) {
                // multiround phase duration
                adjustPhaseForEndDate(PhaseType.CHECKPOINT_SUBMISSION_PHASE, newProjectPhases, multiRoundEndDate);
            }
            if (endDate != null) {
                // submission phase duration
                long submissionDuration = adjustPhaseForEndDate(PhaseType.SUBMISSION_PHASE, newProjectPhases, endDate);

                if(isStudio || isAlgorithm || isFirst2Finish || isCode || submissionDuration <= (48 * 60 * 60 * 1000)) {
                    adjustPhaseForEndDate(PhaseType.REGISTRATION_PHASE, newProjectPhases, endDate);
                }
            }

            setNewPhasesProperties(projectHeader, newProjectPhases, (multiRoundEndDate != null), isStudio);

            return this.createProject(projectHeader, newProjectPhases, projectResources, operator);

        } catch (PhaseTemplateException e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "PhaseTemplateException occurred in ProjectServicesImpl#createProjectWithTemplate method : " + e.getMessage(),
                    e);
            logError(e, pse.getMessage());
            throw pse;
        }
        catch (PersistenceException e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "PhaseTemplateException occurred in ProjectServicesImpl#createProjectWithTemplate method : " + e.getMessage(),
                    e);
            logError(e, pse.getMessage());
            throw pse;
        }
        catch (Exception e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "PhaseTemplateException occurred in ProjectServicesImpl#createProjectWithTemplate method : " + e.getMessage(),
                    e);
            logError(e, pse.getMessage());
            throw pse;
        }finally {
            Util.log(logger, Level.INFO, "Exits ProjectServicesImpl#createProjectWithTemplate method.");
        }
    }
    
    /**
     * Gets the general feedback for software checkpoint submissions.
     * 
     * @param contestId the contest id
     * @return the general feedback, or null if there's no matching record in DB
     * @throws IllegalArgumentException if the argument is non-positive
     * @throws ProjectServicesException if any other error occurs
     * @since 1.4.10
     */
    public String getSoftwareCheckpointSubmissionsGeneralFeedback(long contestId) throws ProjectServicesException {
        String method = "ProjectServicesImpl#getSoftwareCheckpointSubmissionsGeneralFeedback method.";
        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            return projectManager.getSoftwareCheckpointSubmissionsGeneralFeedback(contestId);
        } catch (PersistenceException e) {
            ProjectServicesException pse = new ProjectServicesException(
                "PersisteceException occurred in " + method + " Message: " + e.getMessage(), e);
            logError(e, pse.getMessage());
            throw pse;
        } catch (IllegalArgumentException e) {
            logError(e, "IllegalArgumentException occurred in " + method + " Message: " + e.getMessage());
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
     * @since 1.4.10
     */
    public void saveSoftwareCheckpointSubmissionsGeneralFeedback(long contestId, String feedback)
        throws ProjectServicesException {
        String method = "ProjectServicesImpl#saveSoftwareCheckpointSubmissionsGeneralFeedback method.";
        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            projectManager.saveSoftwareCheckpointSubmissionsGeneralFeedback(contestId, feedback);
        } catch (PersistenceException e) {
            ProjectServicesException pse = new ProjectServicesException(
                "PersisteceException occurred in " + method + " Message: " + e.getMessage(), e);
            logError(e, pse.getMessage());
            throw pse;
        } catch (IllegalArgumentException e) {
            logError(e, "IllegalArgumentException occurred in " + method + " Message: " + e.getMessage());
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
    public List<SimpleProjectContestData> getSimpleProjectContestData(TCSubject tcSubject)
            throws ProjectServicesException {
        log(Level.INFO,
                "Enters ProjectServicesImpl#getSimpleProjectContestData(tcSubject) method.");

        // represents the active projects
        List<SimpleProjectContestData> ret = null;
        try {
            // find all projects which have tc direct project id
            logDebug("Starts calling ProjectManager#getSimpleProjectContestData(tcSubject) method.");

            ret = projectManager.getSimpleProjectContestData();

            logDebug("Finished calling ProjectManager#getSimpleProjectContestData(tcSubject) method.");

        } catch (PersistenceException ex) {
            log(
                    Level.ERROR,
                    "ProjectServicesException occurred in ProjectServicesImpl#getSimpleProjectContestData(tcSubject) method.");
            throw new ProjectServicesException(
                    "PersistenceException occurred when operating ProjectManager.",
                    ex);
        }

        log(Level.INFO,
                "Exits ProjectServicesImpl#getSimpleProjectContestData(tcSubject) method.");
        return ret;
    }

    public List<SimpleProjectContestData> getSimpleProjectContestData( long pid)
            throws ProjectServicesException {
        log(Level.INFO,
                "Enters ProjectServicesImpl#getSimpleProjectContestData method.");

        // represents the active projects
        List<SimpleProjectContestData> ret = null;
        try {
            // find all projects which have tc direct project id
            logDebug("Starts calling ProjectManager#getSimpleProjectContestData method.");

            ret = projectManager.getSimpleProjectContestData(pid);

            logDebug("Finished calling ProjectManager#getSimpleProjectContestData method.");

        } catch (PersistenceException ex) {
            log(
                    Level.ERROR,
                    "ProjectServicesException occurred in ProjectServicesImpl#getSimpleProjectContestData method.");
            throw new ProjectServicesException(
                    "PersistenceException occurred when operating ProjectManager.",
                    ex);
        }

        log(Level.INFO,
                "Exits ProjectServicesImpl#getSimpleProjectContestData method.");
        return ret;
    }

    public List<SimpleProjectContestData> getSimpleProjectContestDataByUser(
            String user) throws ProjectServicesException {
        log(Level.INFO,
                "Enters ProjectServicesImpl#getSimpleProjectContestDataByUser method.");

        // represents the active projects
        List<SimpleProjectContestData> ret = null;
        try {
            // find all projects which have tc direct project id
            logDebug("Starts calling ProjectManager#getSimpleProjectContestDataByUser method.");

            ret = projectManager.getSimpleProjectContestDataByUser(user);

            logDebug("Finished calling ProjectManager#getSimpleProjectContestDataByUser method.");

        } catch (PersistenceException ex) {
            log(
                    Level.ERROR,
                    "ProjectServicesException occurred in ProjectServicesImpl#getSimpleProjectContestDataByUser method.");
            throw new ProjectServicesException(
                    "PersistenceException occurred when operating ProjectManager.",
                    ex);
        }

        log(Level.INFO,
                "Exits ProjectServicesImpl#getSimpleProjectContestData method.");
        return ret;
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
    public List<SimpleProjectPermissionData> getSimpleProjectPermissionDataForUser(TCSubject tcSubject, long createdUser)
            throws ProjectServicesException {
        String method = "ProjectServicesBean#getSimpleProjectPermissionDataForUser(tcSubject, createdUser) method.";

        log(Level.INFO, "Enters ProjectServicesImpl#getSimpleProjectPermissionDataForUser(tcSubject, createdUser)  method.");

        // represents the active projects
        List<SimpleProjectPermissionData> ret = null;
        try {
            logDebug("Starts calling ProjectManager#getSimpleProjectPermissionDataForUser(tcSubject, createdUser)  method.");

            ret = projectManager.getSimpleProjectPermissionDataForUser(createdUser);

            logDebug("Finished calling ProjectManager#getSimpleProjectPermissionDataForUser(tcSubject, createdUser)  method.");

        } catch (PersistenceException ex) {
            log(Level.ERROR,
                    "ProjectServicesException occurred in ProjectServicesImpl#getSimpleProjectPermissionDataForUser(tcSubject, createdUser)  method.");
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectManager.", ex);
        }

        log(Level.INFO, "Exits ProjectServicesImpl#getSimpleProjectPermissionDataForUser(tcSubject, createdUser)  method.");
        return ret;
    }

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
    public List<SimplePipelineData> getSimplePipelineData(TCSubject tcSubject, Date startDate, Date endDate,
            boolean overdueContests) throws ProjectServicesException {
        log(Level.INFO, "Enters ProjectServicesImpl#getSimplePipelineData method.");

        List<SimplePipelineData> ret = null;
        try {
            logDebug("Starts calling ProjectManager#getSimplePipelineData method.");

            ret = projectManager.getSimplePipelineData(-1, startDate, endDate, overdueContests);

            logDebug("Finished calling ProjectManager#getSimplePipelineData method.");

        } catch (PersistenceException ex) {
            log(Level.ERROR, "ProjectServicesException occurred in ProjectServicesImpl#getSimplePipelineData method.");
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectManager.", ex);
        }

        log(Level.INFO, "Exits ProjectServicesImpl#getSimplePipelineData method.");
        return ret;
    }

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
    public List<SimplePipelineData> getSimplePipelineData( long userId, Date startDate, Date endDate,
            boolean overdueContests) throws ProjectServicesException {
        log(Level.INFO, "Enters ProjectServicesImpl#getSimplePipelineData method.");

        List<SimplePipelineData> ret = null;
        try {
            logDebug("Starts calling ProjectManager#getSimplePipelineData method.");

            ret = projectManager.getSimplePipelineData(userId, startDate, endDate, overdueContests);

            logDebug("Finished calling ProjectManager#getSimplePipelineData method.");

        } catch (PersistenceException ex) {
            log(Level.ERROR, "ProjectServicesException occurred in ProjectServicesImpl#getSimplePipelineData method.");
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectManager.", ex);
        }

        log(Level.INFO, "Exits ProjectServicesImpl#getSimplePipelineData method.");
        return ret;
    }


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
    public List<SoftwareCapacityData> getCapacity( int contestType) throws ProjectServicesException {
        String method = "ProjectServicesImpl#getCapacity(" + contestType + ") method.";

        log(Level.INFO, "Enters " + method);
        try {
            return projectManager.getCapacity(contestType);
        } catch (PersistenceException ex) {
            log(Level.ERROR, "ProjectServicesException occurred in " + method + " method.");
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectManager.", ex);
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
    public List<DesignComponents> getDesignComponents(TCSubject tcSubject, long userId)
            throws ProjectServicesException {
        log(Level.INFO,
                "Enters ProjectServicesImpl#getDesignComponents method.");

        List<DesignComponents> ret = null;
        try {
            ret = projectManager.getDesignComponents(userId);
        } catch (PersistenceException ex) {
            log(Level.ERROR,
                    "ProjectServicesException occurred in ProjectServicesImpl#getDesignComponents method.");
            throw new ProjectServicesException(
                    "PersistenceException occurred when operating ProjectManager.",
                    ex);
        }
        log(Level.INFO,
                "Exits ProjectServicesImpl#getDesignComponents method.");
        return ret;
    }
     /**
     * Get corresponding development contest's id for the design contest.
     *
     * @param contestId
     *            The contest id
     * @throws ProjectServicesException
     *             if any other error occurs
     * @since 1.2.1
     */
    public long getDevelopmentContestId( long contestId)
        throws ProjectServicesException {
        log(Level.INFO,
                "Enters ProjectServicesImpl#getDevelopmentContestId method.");

        long ret;
        try {
            ret = projectManager.getDevelopmentContestId(contestId);
        } catch (PersistenceException ex) {
            log(
                    Level.ERROR,
                    "ProjectServicesException occurred in ProjectServicesImpl#getDevelopmentContestId method.");
            throw new ProjectServicesException(
                    "PersistenceException occurred when operating ProjectManager.",
                    ex);
        }
        log(Level.INFO,
                "Exits ProjectServicesImpl#getDevelopmentContestId method.");
        return ret;
    }


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
    public boolean checkContestPermission(long contestId, boolean readonly, long userId)  throws ProjectServicesException
    {
        String method = "checkContestPermission(" + contestId + ", " + readonly + ", " + userId + ")";

        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            return projectManager.checkContestPermission(contestId, readonly, userId);
        } catch (PersistenceException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectManager." ,e);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }

    }

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
    public boolean checkProjectPermission(long tcprojectId, boolean readonly, long userId) throws ProjectServicesException
    {
        String method = "checkContestPermission(" + tcprojectId + ", " + readonly + ", " + userId + ")";

        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            return projectManager.checkProjectPermission(tcprojectId, readonly, userId);
        } catch (PersistenceException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectManager." ,e);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }


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
    public List<Long> getProjectIdByTcDirectProject( long tcprojectId) throws ProjectServicesException
    {
        String method = "getProjectIdByTcDirectProject(" + tcprojectId + ")";

        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            return projectManager.getProjectIdByTcDirectProject(tcprojectId);
        } catch (PersistenceException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectManager." ,e);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }


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
    public Resource[] searchResources( Filter filter) throws ProjectServicesException
    {
        String method = "searchResources(" + filter + ")";

        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            return resourceManager.searchResources(filter);
        } catch (Exception e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectManager." ,e);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }


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
    public Resource updateResource( Resource resource, String operator) throws ProjectServicesException
    {

        String method = "updateResource(" + resource + ")";

        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            return resourceManager.updateResource(resource, operator);
        } catch (Exception e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectManager." ,e);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }


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
    public long getForumId( long projectId) throws ProjectServicesException
    {

        String method = "getForumId(" + projectId + ")";

        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            return projectManager.getForumId(projectId);
        } catch (Exception e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectManager." ,e);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }


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
    public boolean hasContestPermission( long contestId, long userId)  throws ProjectServicesException
     {

        String method = "hasContestPermission(" + contestId + "," + userId + ")";

        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            return projectManager.hasContestPermission(contestId, userId);
        } catch (Exception e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectManager." ,e);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

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
    public void removeResource( Resource resource, String operator) throws ProjectServicesException
    {
     String method = "removeResource(" + resource + ")";

        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            resourceManager.removeResource(resource, operator);
        } catch (Exception e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method + ": " + e);
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectManager." ,e);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }


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
    public Resource[] searchResources( long projectId, long roleId) throws ProjectServicesException
     {
        String method = "searchResources(" + projectId + "," + roleId + ")";

        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            return resourceManager.searchResources(projectId, roleId);
        } catch (Exception e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method + ": " + e);
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectManager." ,e);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }


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
    public long getTcDirectProject( long projectId) throws ProjectServicesException
    {

        String method = "getTcDirectProject(" + projectId + ")";

        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            return projectManager.getTcDirectProject(projectId);
        } catch (Exception e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectManager." ,e);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

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
    public FullProjectData createSpecReview( long projectId, double specReviewPrize, String userId, String handle)
        throws ProjectServicesException {

        // check operator
        ExceptionUtils.checkNullOrEmpty(userId, null, null, "The parameter[userId] should not be null or empty.");

        String method = "ProjectServicesImpl#createSpecReview(" + projectId + ", " + specReviewPrize + ", " +
            userId + ") method.";
        log(Level.INFO, "Enters " + method);

        // check non-negative specification review prize
        if (specReviewPrize < 0) {
            throw new IllegalArgumentException("The parameter[specReviewPrize] should not be negative.");
        }

        // check if there is already a spec review project linked
        long specReviewProjectId = getSpecReviewProjectId(projectId);
        if (specReviewProjectId > 0) {
            throw new ProjectServicesException("There is already an associated specification review project: " +
                specReviewProjectId);
        }

        Project specReview = null;
        FullProjectData projectData = null;
        try {
            // get original project data
            FullProjectData fullProjectData = getFullProjectData(projectId);
            if (fullProjectData == null) {
                throw new ProjectServicesException("Project was not found: " + projectId);
            }
            Project project = fullProjectData.getProjectHeader();

            // set project start date to current
            com.topcoder.project.phases.Project projectPhases = new com.topcoder.project.phases.Project();

            // spec review starts 48 hrs before project starts or now if 48 hrs earlier is past
            Duration fotyEightHours = DatatypeFactory.newInstance().newDurationDayTime(false, 0, 48, 0, 0);

            XMLGregorianCalendar fotyEightHoursEarlier = getXMLGregorianCalendar(fullProjectData.getStartDate());
            fotyEightHoursEarlier.add(fotyEightHours);

            projectPhases.setStartDate(getDate(fotyEightHoursEarlier));
            if (projectPhases.getStartDate().before(new Date()))
            {
                projectPhases.setStartDate(new Date());
            }

            // create project header
            Project projectHeader = new Project();
            ProjectType projectType = new ProjectType(APPLICATION_PROJECT_TYPE_ID, APPLICATION_PROJECT_TYPE,
                APPLICATION_PROJECT_TYPE);
            ProjectCategory projectCategory = new ProjectCategory(SPEC_REVIEW_PROJECT_CATEGORY_ID,
                SPEC_REVIEW_PROJECT_CATEGORY, SPEC_REVIEW_PROJECT_CATEGORY, projectType);

            projectHeader.setProjectCategory(projectCategory);
            projectHeader.setProjectStatus(project.getProjectStatus());

            // clone some original project properties
            for (String key : SPEC_REVIEW_PROJECT_PROPERTIES_TO_CLONE) {
                projectHeader.setProperty(key, project.getProperty(key));
            }

            // set new properties for the spec review
            projectHeader.setProperty(ProjectPropertyType.PROJECT_NAME_PROJECT_PROPERTY_KEY,
                project.getProperty(ProjectPropertyType.PROJECT_NAME_PROJECT_PROPERTY_KEY) + " " + SPEC_REVIEW_PROJECT_CATEGORY);
            // Dont turn on yet
            //projectHeader.setProperty(AUTOPILOT_OPTION_PROJECT_PROPERTY_KEY,
            //    AUTOPILOT_OPTION_PROJECT_PROPERTY_VALUE_ON);
            projectHeader.setProperty(ProjectPropertyType.REVIEW_COSTS_PROJECT_PROPERTY_KEY,
                          project.getProperty(ProjectPropertyType.SPEC_REVIEW_COSTS_PROJECT_PROPERTY_KEY));
            projectHeader.setProperty(ProjectPropertyType.PAYMENTS_PROJECT_PROPERTY_KEY, "0");
            

            projectHeader.setProperty(ProjectPropertyType.NOTES_PROJECT_PROPERTY_KEY, "Contest Detail: http://www.topcoder.com/tc?module=ProjectDetail&pj="+projectId);

            // create mock ProjectSpec object
            ProjectSpec projectSpec = new ProjectSpec();
            projectSpec.setDetailedRequirements(SPEC_REVIEW_PROJECT_CATEGORY);
            projectSpec.setSubmissionDeliverables(SPEC_REVIEW_PROJECT_CATEGORY);
            projectSpec.setEnvironmentSetupInstructions(SPEC_REVIEW_PROJECT_CATEGORY);
            projectSpec.setFinalSubmissionGuidelines(SPEC_REVIEW_PROJECT_CATEGORY);

            projectHeader.setProjectSpec(projectSpec);
            projectHeader.setTcDirectProjectId(project.getTcDirectProjectId());

            // clone resources (reset Id so that they are regenerated in persistence layer)
            com.topcoder.management.resource.Resource[] resources = fullProjectData.getResources();
            for (com.topcoder.management.resource.Resource resource : resources) {
                resource.setId(com.topcoder.management.resource.Resource.UNSET_ID);
            }

            // add operator as a submitter
            com.topcoder.management.resource.Resource[] extendedResources =
                new com.topcoder.management.resource.Resource[resources.length + 1];

            System.arraycopy(resources, 0, extendedResources, 0, resources.length);
            ResourceRole submitterRole = new ResourceRole(SUBMITTER_ROLE_ID);

            com.topcoder.management.resource.Resource submitter =
                new com.topcoder.management.resource.Resource(com.topcoder.management.resource.Resource.UNSET_ID,
                    submitterRole);

            submitter.setProperty(EXTERNAL_REFERENCE_ID_RESOURCE_PROPERTY_KEY, userId);
            submitter.setProperty(RESOURCE_INFO_HANDLE, handle);
            submitter.setProperty(RESOURCE_INFO_PAYMENT_STATUS, RESOURCE_INFO_PAYMENT_STATUS_NA);
            submitter.setUserId(Long.parseLong(userId));

            extendedResources[extendedResources.length - 1] = submitter;

            // create spec review project
            projectData = createProjectWithTemplate(projectHeader, projectPhases, extendedResources,
                userId);

            projectHeader.setProperty(ProjectPropertyType.APPROVAL_REQUIRED_PROJECT_PROPERTY_KEY, "false");
            projectHeader.setProperty(ProjectPropertyType.SEND_WINNDER_EMAILS_PROJECT_PROPERTY_KEY, "false");
            projectHeader.setProperty(ProjectPropertyType.POST_MORTEM_REQUIRED_PROJECT_PROPERTY_KEY, "false");
            projectHeader.setProperty(ProjectPropertyType.RELIABILITY_BONUS_ELIGIBLE_PROJECT_PROPERTY_KEY, "false");
            projectHeader.setProperty(ProjectPropertyType.MEMBER_PAYMENT_ELIGIBLE_PROJECT_PROPERTY_KEY, "true");

            // link it to the original project
            projectLinkManager.addProjectLink(projectId, projectData.getProjectHeader().getId(),
                                                    ProjectLinkType.REQUIRES_SPEC_REVIEW);

            specReview = projectData.getProjectHeader();
        } catch (PersistenceException ex) {
            log(Level.ERROR, "PersistenceException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectLinkManager.", ex);
        }
        catch (DatatypeConfigurationException ex) {
            log(Level.ERROR, "PersistenceException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectLinkManager.", ex);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }

        return projectData;
    }
    /**
     * Creates re-open contest for the given contest. since version 1.4.
     *
     * @param contest the contest to repost
     * @param operator the operator
     * @return new contest for the repost one
     * @throws ProjectServicesException if any error occurs
     */
    public FullProjectData createReOpenContest( FullProjectData contest, String operator) throws ProjectServicesException {
        // check operator
        ExceptionUtils.checkNullOrEmpty(operator, null, null, "The parameter[operator] should not be null or empty.");

        String method = "ProjectServicesImpl#createReOpenContest(" + contest + ", " + operator + ") method.";
        log(Level.INFO, "Enters " + method);

        try {
            //1. create the new re-open contest
            //1.1 copy the project header
            Project projectHeader = new Project();
            projectHeader.setProjectCategory(contest.getProjectHeader().getProjectCategory());
            projectHeader.setProjectStatus(ProjectStatus.DRAFT);

            if (contest.getProjectHeader().getProjectCategory().getProjectType().getId() ==
                    ProjectType.STUDIO.getId()) {
                ProjectStudioSpecification projectStudioSpecification = contest.getProjectHeader().getProjectStudioSpecification();
                projectStudioSpecification.setId(0);
                projectHeader.setProjectStudioSpecification(projectStudioSpecification);
                projectHeader.setProjectFileTypes(contest.getProjectHeader().getProjectFileTypes());
            } else {
                ProjectSpec spec = contest.getProjectHeader().getProjectSpec();
                spec.setProjectSpecId(-1);
                projectHeader.setProjectSpec(spec);
            }

            // clone some original project properties
            for (String key : NEW_VERSION_PROJECT_PROPERTIES_TO_CLONE) {
                if (contest.getProjectHeader().getProperty(key) != null) {
                    projectHeader.setProperty(key, contest.getProjectHeader().getProperty(key));
                }
            }

            projectHeader.setTcDirectProjectId(contest.getProjectHeader().getTcDirectProjectId());
            projectHeader.setTcDirectProjectName(contest.getProjectHeader().getTcDirectProjectName());

            if (contest.getProjectHeader().getPlatforms() != null &&
                    contest.getProjectHeader().getPlatforms().size() > 0) {
                projectHeader.setPlatforms(contest.getProjectHeader().getPlatforms());
            }

            if (contest.getProjectHeader().getProjectCategory().getId() == ProjectCategory.COPILOT_POSTING.getId()) {
                projectHeader.setCopilotContestExtraInfos(contest.getProjectHeader().getCopilotContestExtraInfos());
                projectHeader.setProjectCopilotTypes(contest.getProjectHeader().getProjectCopilotTypes());
            }


            List<Prize> prizes = contest.getProjectHeader().getPrizes();
            List<Prize> newPrizes = new ArrayList<Prize>();

            for (Prize p : prizes) {
                Prize newPrize = new Prize();
                newPrize.setPrizeType(p.getPrizeType());
                newPrize.setPlace(p.getPlace());
                newPrize.setPrizeAmount(p.getPrizeAmount());
                newPrize.setNumberOfSubmissions(p.getNumberOfSubmissions());
                newPrizes.add(newPrize);
            }

            projectHeader.setPrizes(newPrizes);

            //1.2 clone resources:Only observers, managers, client managers and copilots should be copied in resources.
            List<com.topcoder.management.resource.Resource> newResourcesList = new ArrayList<com.topcoder.management.resource.Resource>();
            com.topcoder.management.resource.Resource[] resources = contest.getResources();
            for (com.topcoder.management.resource.Resource resource : resources) {
                if (resource.getResourceRole().getId() == ResourceRole.RESOURCE_ROLE_CLIENT_MANAGER_ID
                        ||resource.getResourceRole().getId() == ResourceRole.RESOURCE_ROLE_COPILOT_ID
                        ||resource.getResourceRole().getId() == ResourceRole.RESOURCE_ROLE_MANAGER_ID
                        ||resource.getResourceRole().getId() == ResourceRole.RESOURCE_ROLE_OBSERVER_ID){
                    resource.setId(com.topcoder.management.resource.Resource.UNSET_ID);
                    newResourcesList.add(resource);
                }
            }

            //1.3 Set the start time to 1 day + current time.
            com.topcoder.project.phases.Project projectPhases = new com.topcoder.project.phases.Project();
            projectPhases.setStartDate(contest.getStartDate());

            Date multipleRoundEndDate = null;
            Phase[] allPhases = contest.getAllPhases();
            for(Phase ph : allPhases) {
                if(ph.getPhaseType().getId() == PhaseType.CHECKPOINT_SUBMISSION_PHASE.getId()) {
                    GregorianCalendar cal = new GregorianCalendar();
                    cal.setTime(contest.getStartDate());
                    cal.add(Calendar.HOUR, 24 * 3);
                    multipleRoundEndDate = cal.getTime();
                    break;
                }
            }


            com.topcoder.management.resource.Resource[] newResources =
                 (com.topcoder.management.resource.Resource[]) newResourcesList.toArray(new com.topcoder.management.resource.Resource[newResourcesList.size()]);

            //1.4 create the project here
            FullProjectData reOpendedProject =
                createProjectWithTemplate(projectHeader, projectPhases, newResources, multipleRoundEndDate, operator);

            //1.5 link the project to the original one
            projectLinkManager.addProjectLink(reOpendedProject.getProjectHeader().getId(),
                                                    contest.getProjectHeader().getId(), ProjectLinkType.REPOST_FOR);

            // update links
            // get all dependents
            ProjectLink[] dependents = projectLinkManager.getSourceProjectLinks(contest.getProjectHeader().getId());
            if (dependents != null && dependents.length > 0)
            {
                for (ProjectLink link : dependents)
                {
                    //ignore repost link
                    if (link.getType().getId() != ProjectLinkType.REPOST_FOR)
                    {
                        // delete existing link
                    projectLinkManager.removeProjectLink(link.getSourceProject().getId(),
                                                      link.getDestProject().getId(), link.getType().getId());

                    // depenpends link to reposted
                    projectLinkManager.addProjectLink(link.getSourceProject().getId(),
                                                      reOpendedProject.getProjectHeader().getId(), link.getType().getId());

                    // existing link changes to related_to
                    projectLinkManager.addProjectLink(link.getSourceProject().getId(),
                                                      contest.getProjectHeader().getId(), ProjectLinkType.IS_RELATED_TO);

                    }
                }
            }

            return reOpendedProject;

        } catch (PersistenceException e) {
            log(Level.ERROR, "PersistenceException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectLinkManager.", e);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }
    /**
     * Creates new version for development and design contest for the given contest.since version 1.4.
     *
     * @param contest the contest to create new version
     * @param operator the operator
     * @return new contest for the repost one
     * @throws ProjectServicesException if any error occurs
     */
    public FullProjectData createNewVersionContest( FullProjectData contest, String operator) throws ProjectServicesException {
        // check operator
        ExceptionUtils.checkNullOrEmpty(operator, null, null, "The parameter[operator] should not be null or empty.");

        String method = "ProjectServicesImpl#createNewVersionContest(" + contest + ", " + operator + ") method.";
        log(Level.INFO, "Enters " + method);

        try {
            //1.1 copy the project header
            Project projectHeader = new Project();
            projectHeader.setProjectCategory(contest.getProjectHeader().getProjectCategory());
            projectHeader.setProjectStatus(ProjectStatus.DRAFT);

            ProjectSpec spec = contest.getProjectHeader().getProjectSpec();
            spec.setProjectSpecId(-1);
            projectHeader.setProjectSpec(spec);
            // clone some original project properties
            for (String key : NEW_VERSION_PROJECT_PROPERTIES_TO_CLONE) {
                projectHeader.setProperty(key, contest.getProjectHeader().getProperty(key));
            }
            projectHeader.setTcDirectProjectId(contest.getProjectHeader().getTcDirectProjectId());
            projectHeader.setTcDirectProjectName(contest.getProjectHeader().getTcDirectProjectName());

            //1.2 clone resources:Only observers, managers, client managers and copilots should be copied in resources.
            List<com.topcoder.management.resource.Resource> newResourcesList = new ArrayList<com.topcoder.management.resource.Resource>();
            com.topcoder.management.resource.Resource[] resources = contest.getResources();
            for (com.topcoder.management.resource.Resource resource : resources) {
                if (resource.getResourceRole().getId() == ResourceRole.RESOURCE_ROLE_CLIENT_MANAGER_ID
                        ||resource.getResourceRole().getId() == ResourceRole.RESOURCE_ROLE_COPILOT_ID
                        ||resource.getResourceRole().getId() == ResourceRole.RESOURCE_ROLE_MANAGER_ID
                        ||resource.getResourceRole().getId() == ResourceRole.RESOURCE_ROLE_OBSERVER_ID){
                    resource.setId(com.topcoder.management.resource.Resource.UNSET_ID);
                    newResourcesList.add(resource);
                }
            }

            //1.3 Set the start time to 1 day + current time.
            com.topcoder.project.phases.Project projectPhases = new com.topcoder.project.phases.Project();
            projectPhases.setStartDate(contest.getStartDate());

            com.topcoder.management.resource.Resource[] newResources =
                 (com.topcoder.management.resource.Resource[]) newResourcesList.toArray(new com.topcoder.management.resource.Resource[newResourcesList.size()]);

            //1.4 create the project here
            FullProjectData reOpendedProject =
                createProjectWithTemplate(projectHeader, projectPhases, newResources, operator);

            return reOpendedProject;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

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
    public ScorecardReviewData getScorecardAndReview( long projectId) throws ProjectServicesException {
        String method = "ProjectServicesImpl#getScorecardAndReview(" + projectId + ") method.";

        ScorecardReviewData scorecardReviewData = new ScorecardReviewData();

        log(Level.INFO, "Enters " + method);
        try {
            // Build resources filter
            Filter filterProject = ResourceFilterBuilder.createProjectIdFilter(projectId);
            Filter filterRole = ResourceFilterBuilder.createResourceRoleIdFilter(ResourceRole.RESOURCE_ROLE_REVIEWER_ID);
            Filter filterRoles = new AndFilter(filterProject, filterRole);

            // Search for the reviewers
            Resource[] reviewers = resourceManager.searchResources(filterRoles);
            if (reviewers.length > 1) {
                throw new ProjectServicesException("Invalid number of reviewers found: " + reviewers.length);
            }

            Review review = null;
            if (reviewers.length == 1) {
                // build reviews filter
                Filter filterReviewer = new EqualToFilter(ResourceRole.RESOURCE_ROLE_REVIEWER_NAME, reviewers[0].getId());

                // Search for the reviews
                Review[] reviews = reviewManager.searchReviews(filterReviewer, true);
                if (reviews.length > 1) {
                    throw new ProjectServicesException("Invalid number of reviews found: " + reviewers.length);
                }

                if (reviews.length == 1) {
                    review = reviews[0];
                }
            }

            // if we have the review, we can get scorecard id from there, otherwise we need to search in review phase
            long scorecardId = -1;
            if (review != null) {
                // set review in DTO
                scorecardReviewData.setReview(review);
                scorecardId = review.getScorecard();
            } else {
                com.topcoder.project.phases.Project projectPhases = phaseManager.getPhases(projectId);

                if (projectPhases != null) {
                    Set<Phase> phases = projectPhases.getPhases();
                    Iterator<Phase> iter = phases.iterator();

                    while (iter.hasNext() && scorecardId < 0) {
                        Phase phase = iter.next();
                        if (phase.getPhaseType().getName().equals(PhaseType.REVIEW_PHASE.getName())) {
                            scorecardId = Long.parseLong(phase.getAttribute(SCORECARD_ID_PHASE_ATTRIBUTE_KEY).toString());
                        }
                    }
                }
            }

            if (scorecardId < 0) {
                throw new ProjectServicesException("Failed to find scorecard id");
            }

            // get scorecard and set it in DTO
            scorecardReviewData.setScorecard(scorecardManager.getScorecard(scorecardId));
        } catch (ReviewManagementException ex) {
            log(Level.ERROR, "ReviewManagementException occurred in " + method);
            throw new ProjectServicesException("ReviewManagementException occurred when operating Review Manager.", ex);
        } catch (com.topcoder.management.scorecard.PersistenceException ex) {
            log(Level.ERROR, "PersistenceException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating Scorecard Manager.", ex);
        } catch (ResourcePersistenceException ex) {
            log(Level.ERROR, "ResourcePersistenceException occurred in " + method);
            throw new ProjectServicesException("ResourcePersistenceException occurred when operating Resource Manager.",
                ex);
        } catch (PhaseManagementException ex) {
            log(Level.ERROR, "PhaseManagementException occurred in " + method);
            throw new ProjectServicesException("PhaseManagementException occurred when operating Phase Manager.", ex);
        } catch (SearchBuilderException ex) {
            log(Level.ERROR, "SearchBuilderException occurred in " + method);
            throw new ProjectServicesException("SearchBuilderException occurred when operating Search Builder.", ex);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }

        return scorecardReviewData;
    }

    /**
     * This method retrieves the corresponding specification review project id of a given project.
     * The code will rely on the project links to retrieve the specification project id.
     *
     * @param projectId the project id to search for
     *
     * @throws ProjectServicesException if any unexpected error occurs in the underlying services.
     *
     * @return the associated specification review project id, or -1 if it was not found.
     *
     * @since 1.3
     */
    public long getSpecReviewProjectId( long projectId) throws ProjectServicesException {
        String method = "ProjectServicesImpl#getSpecReviewProjectId(" + projectId + ") method.";

        log(Level.INFO, "Enters " + method);
        long specReviewProjectId = -1;
        try {
            ProjectLink[] projectLinks = projectLinkManager.getDestProjectLinks(projectId);
            for (int i = 0; i < projectLinks.length && specReviewProjectId < 0; i++) {
                if (projectLinks[i].getType().getId() == ProjectLinkType.REQUIRES_SPEC_REVIEW) {
                    specReviewProjectId = projectLinks[i].getDestProject().getId();
                }
            }
        } catch (PersistenceException ex) {
            log(Level.ERROR, "PersistenceException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectLinkManager.", ex);
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
     *
     * @throws ProjectServicesException if any error occurs during retrieval of information.
     *
     * @since 1.3
     */
    public Set<String> getOpenPhases( long projectId) throws ProjectServicesException {
        String method = "ProjectServicesImpl#getOpenPhases(" + projectId + ") method.";
        log(Level.INFO, "Enters " + method);

        Set<String> openPhases = new HashSet<String>();
        try {
            com.topcoder.project.phases.Project projectPhases = phaseManager.getPhases(projectId);

            for (Phase phase : projectPhases.getPhases()) {
                if (phase.getPhaseStatus().getName().equals(PhaseStatus.OPEN.getName())) {
                    openPhases.add(phase.getPhaseType().getName());
                }
            }
        } catch (PhaseManagementException ex) {
            log(Level.ERROR, "PhaseManagementException occurred in " + method);
            throw new ProjectServicesException("PhaseManagementException occurred when operating PhaseManager.", ex);
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
     * @since 1.4.10
     */
    public List<Phase> getPhasesByType(long projectId, String phaseTypeName) throws ProjectServicesException {
        String method = "ProjectServicesImpl#getPhasesByType(" + projectId + ", " + phaseTypeName + ") method.";
        log(Level.INFO, "Enters " + method);
        
        List<Phase> phases = new ArrayList<Phase>();
        try {
            com.topcoder.project.phases.Project projectPhases = phaseManager.getPhases(projectId);
            for (Phase phase : projectPhases.getPhases()) {
                if (phase.getPhaseType().getName().equals(phaseTypeName)) {
                    phases.add(phase);
                }
            }
        } catch (PhaseManagementException ex) {
            log(Level.ERROR, "PhaseManagementException occurred in " + method);
            throw new ProjectServicesException("PhaseManagementException occurred when operating PhaseManager.", ex);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
        return phases;
    }
    
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
    public void addReviewComment( long reviewId, Comment comment, String operator) throws ProjectServicesException {
        // check comment
        ExceptionUtils.checkNull(comment, null, null, "The parameter[comment] should not be null.");

        // check operator
        ExceptionUtils.checkNullOrEmpty(operator, null, null, "The parameter[operator] should not be null or empty.");

        String method = "ProjectServicesImpl#addReviewComment(" + reviewId + ", " + comment + ", " + operator +
            ") method.";

        log(Level.INFO, "Enters " + method);
        try {
            reviewManager.addReviewComment(reviewId, comment, operator);
        } catch (ReviewManagementException ex) {
            log(Level.ERROR, "ReviewManagementException occurred in " + method);
            throw new ProjectServicesException("ReviewManagementException occurred when operating Review Manager.", ex);
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
     *
     *
     * @throws PersistenceException if any other error occurs.
     *
     */
    public void updatePhases( com.topcoder.project.phases.Project project, String operator) throws ProjectServicesException
    {
        String method = "ProjectServicesImpl#updatePhases(" + project.getId() + ") method.";
        log(Level.INFO, "Enters " + method);


        try {
            phaseManager.updatePhases(project, operator);

        } catch (PhaseManagementException ex) {
            log(Level.ERROR, "PhaseManagementException occurred in " + method);
            throw new ProjectServicesException("PhaseManagementException occurred when operating PhaseManager.", ex);
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
     * @since 2.2
     */
    public com.topcoder.project.phases.Project getPhases(long projectId) throws ProjectServicesException
    {
        String method = "ProjectServicesImpl#getPhases(" + projectId + ") method.";
        log(Level.INFO, "Enters " + method);


        try {
            return phaseManager.getPhases(projectId);

        } catch (PhaseManagementException ex) {
            log(Level.ERROR, "PhaseManagementException occurred in " + method);
            throw new ProjectServicesException("PhaseManagementException occurred when operating PhaseManager.", ex);
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
        String method = "ProjectServicesImpl#createProjectMilestoneRelation(" + projectId + "," + milestoneId + ") method.";
        log(Level.INFO, "Enters " + method);


        try {
            projectManager.createProjectMilestoneRelation(projectId, milestoneId, operator);

        } catch (PersistenceException ex) {
            log(Level.ERROR, "PersistenceException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectManager.", ex);
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
        String method = "ProjectServicesImpl#updateProjectMilestoneRelation(" + projectId + "," + milestoneId + ") method.";
        log(Level.INFO, "Enters " + method);


        try {
            projectManager.updateProjectMilestoneRelation(projectId, milestoneId, operator);

        } catch (PersistenceException ex) {
            log(Level.ERROR, "PersistenceException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectManager.", ex);
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
        String method = "ProjectServicesImpl#deleteProjectMilestoneRelation(" + projectId + ") method.";
        log(Level.INFO, "Enters " + method);


        try {
            projectManager.deleteProjectMilestoneRelation(projectId, operator);

        } catch (PersistenceException ex) {
            log(Level.ERROR, "PersistenceException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectManager.", ex);
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
        String method = "ProjectServicesImpl#deleteMilestoneProjectRelations(" + milestoneId + ") method.";
        log(Level.INFO, "Enters " + method);


        try {
            projectManager.deleteMilestoneProjectRelations(milestoneId, operator);

        } catch (PersistenceException ex) {
            log(Level.ERROR, "PersistenceException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectManager.", ex);
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
        String method = "ProjectServicesImpl#getProjectMilestoneRelation(" + projectId + ") method.";
        log(Level.INFO, "Enters " + method);

        try {
            return projectManager.getProjectMilestoneRelation(projectId);

        } catch (PersistenceException ex) {
            log(Level.ERROR, "PersistenceException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectManager.", ex);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

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
    public void updateProject( Project project, String reason, String operator) throws ProjectServicesException
    {
        String method = "ProjectServicesImpl#updateProject(" + project.getId() + ") method.";
        log(Level.INFO, "Enters " + method);


        try {
            projectManager.updateProject(project, reason, operator);

        } catch (PersistenceException ex) {
            log(Level.ERROR, "PersistenceException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating PhaseManager.", ex);
        }
        catch (ValidationException ex) {
            log(Level.ERROR, "ValidationException occurred in " + method);
            throw new ProjectServicesException("ValidationException occurred when operating PhaseManager.", ex);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }


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
    public boolean isDevOnly( long projectId) throws ProjectServicesException
    {
        String method = "ProjectServicesImpl#isDevOnly(" + projectId + ") method.";
        log(Level.INFO, "Enters " + method);


        try {
            return projectManager.isDevOnly(projectId);

        } catch (PersistenceException ex) {
            log(Level.ERROR, "PersistenceException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating PhaseManager.", ex);
        }
       finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * This method links the development contest to its design contest. It simply call a method in project link manager.
     *
     * @param developmentContestId the development contest id
     *
     * @throws ProjectServicesException if any unexpected error occurs in the underlying services.
     *
     * @since 1.3.1
     */
    public void linkDevelopmentToDesignContest( long developmentContestId) throws ProjectServicesException {
        String method = "ProjectServicesImpl#linkDevelopmentToDesignContest(" + developmentContestId + ") method.";
        log(Level.INFO, "Enters " + method);
        log(Level.ERROR, "Enters1 " + developmentContestId);
        log(Level.ERROR, "Enters2 " + getDevelopmentContestId( developmentContestId));
        try {
            long designId = getDesignContestId(developmentContestId);
            if (designId != 0)
            {
                projectLinkManager.addProjectLink(developmentContestId, designId, ProjectLinkType.FOR_DESIGN);
            }

        } catch (PersistenceException ex) {
            log(Level.ERROR, "PersistenceException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating Rroject Link Manager.", ex);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * Links the parent contest with the bug hunt contest.
     *
     * @param bugHuntContestId the id of the bug hunt contest.
     * @throws ProjectServicesException if any unexpected error occurs in the underlying services.
     * @since 1.4.8
     */
    public void linkBugHuntToOtherContest( long bugHuntContestId) throws ProjectServicesException {
            String method = "ProjectServicesImpl#linkBugHuntToOtherContest(" + bugHuntContestId + ") method.";
            log(Level.INFO, "Enters " + method);
            log(Level.ERROR, "Enters1 " + bugHuntContestId);

            try {
                long linkContestId = getContestId(bugHuntContestId);

                if (linkContestId != 0)
                {
                    projectLinkManager.addProjectLink(bugHuntContestId, linkContestId, ProjectLinkType.BUG_HUNT_FOR);
                }

            } catch (PersistenceException ex) {
                log(Level.ERROR, "PersistenceException occurred in " + method);
                throw new ProjectServicesException("PersistenceException occurred when operating Rroject Link Manager.", ex);
            } finally {
                Util.log(logger, Level.INFO, "Exits " + method);
            }
        }


    /**
     * Get corresponding development contest's id for the design contest.
     *
     * @param contestId
     *            The contest id
     * @throws ProjectServicesException
     *             if any other error occurs
     * @since 1.2.1
     */
    public long getDesignContestId(long contestId)
        throws ProjectServicesException {
        log(Level.INFO,
                "Enters ProjectServicesImpl#getDesignContestId method.");

        long ret;
        try {
            ret = projectManager.getDesignContestId(contestId);
        } catch (PersistenceException ex) {
            log(
                    Level.ERROR,
                    "ProjectServicesException occurred in ProjectServicesImpl#getDesignContestId method.");
            throw new ProjectServicesException(
                    "PersistenceException occurred when operating ProjectManager.",
                    ex);
        }
        log(Level.INFO,
                "Exits ProjectServicesImpl#getDesignContestId method.");
        return ret;
    }

    /**
     * Gets the corresponding contest id which shars the same asset setting as the given contest id.
     *
     * @param contestId the contest id
     * @return the matched contest id
     * @throws ProjectServicesException if any other error occurs.
     * @since 1.4.8
     */
    public long getContestId(long contestId)
            throws ProjectServicesException {
        log(Level.INFO,
                "Enters ProjectServicesImpl#getContestId method.");

        long ret;
        try {
            ret = projectManager.getContestId(contestId);
        } catch (PersistenceException ex) {
            log(
                    Level.ERROR,
                    "ProjectServicesException occurred in ProjectServicesImpl#getContestId method.");
            throw new ProjectServicesException(
                    "PersistenceException occurred when operating ProjectManager.",
                    ex);
        }
        log(Level.INFO,
                "Exits ProjectServicesImpl#getContestId method.");
        return ret;
    }

    /**
     * This method retrieves scorecard and review information associated to a project determined by parameter.
     *
     * @param projectId the project id to search for.
     * @param reviewerResourceId the reviewer resource ID.
     * @param phaseType the phase type of the review to search for.
     * @return the aggregated scorecard and review data.
     * @throws ProjectServicesException if any unexpected error occurs in the underlying services.
     * @since 1.4.6
     */
    private List<ScorecardReviewData> getScorecardAndReviews(long projectId, long reviewerResourceId, PhaseType phaseType)
        throws ProjectServicesException {
        String method = "ProjectServicesImpl#getScorecardAndReviews method.";
        
        List<ScorecardReviewData> scorecardReviewData = new ArrayList<ScorecardReviewData>();

        log(Level.INFO, "Enters " + method);
        try {
            // Search for the checkpoint reviewers
            Resource reviewer = resourceManager.getResource(reviewerResourceId);
            if (reviewer == null) {
                throw new ProjectServicesException("Can not find the reviewer.");
            }
            Filter filterReviewer = new AndFilter(
                    new EqualToFilter(RESOURCE_REVIEWER_PROPERTY, reviewer.getId()),
                    new EqualToFilter("project", String.valueOf(projectId)));
            Review[] reviews = reviewManager.searchReviews(filterReviewer, true);
            for (int i = 0; i < reviews.length; i++) {
                Review review = reviews[i];
                ScorecardReviewData data = new ScorecardReviewData();
                data.setReview(review);
                data.setScorecard(scorecardManager.getScorecard(review.getScorecard()));
                scorecardReviewData.add(data);
            }
            
            if (scorecardReviewData.isEmpty()) {
                com.topcoder.project.phases.Project projectPhases = phaseManager.getPhases(projectId);
                long scorecardId = -1;
                if (projectPhases != null) {
                    Set<Phase> phases = projectPhases.getPhases();
                    Iterator<Phase> iter = phases.iterator();
                    while (iter.hasNext() && scorecardId < 0) {
                        Phase phase = iter.next();
                        if (phase.getPhaseType().getName().equals(phaseType.getName())) {
                            scorecardId = Long.parseLong(phase.getAttribute(SCORECARD_ID_PHASE_ATTRIBUTE_KEY).toString());
                        }
                    }
                }
                
                if (scorecardId < 0) {
                    throw new ProjectServicesException("Failed to find scorecard id");
                }
                ScorecardReviewData data = new ScorecardReviewData();
                data.setScorecard(scorecardManager.getScorecard(scorecardId));
                scorecardReviewData.add(data);
            }
        } catch (ReviewManagementException ex) {
            log(Level.ERROR, "ReviewManagementException occurred in " + method);
            throw new ProjectServicesException("ReviewManagementException occurred when operating Review Manager.", ex);
        } catch (com.topcoder.management.scorecard.PersistenceException ex) {
            log(Level.ERROR, "PersistenceException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating Scorecard Manager.", ex);
        } catch (ResourcePersistenceException ex) {
            log(Level.ERROR, "ResourcePersistenceException occurred in " + method);
            throw new ProjectServicesException("ResourcePersistenceException occurred when operating Resource Manager.",
                ex);
        } catch (PhaseManagementException ex) {
            log(Level.ERROR, "PhaseManagementException occurred in " + method);
            throw new ProjectServicesException("PhaseManagementException occurred when operating Phase Manager.", ex);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }

        return scorecardReviewData;
    }

    /**
     * This method retrieves checkpoint scorecard and checkpoint review information associated to a project determined by parameter.
     *
     * @param projectId the project id to search for.
     * @param reviewerResourceId the reviewer resource ID.
     * @return the aggregated scorecard and review data.
     * @throws ProjectServicesException if any unexpected error occurs in the underlying services.
     * @since 1.4.6
     */
    public List<ScorecardReviewData> getScorecardAndCheckpointReviews(long projectId, long reviewerResourceId)
        throws ProjectServicesException {
        return getScorecardAndReviews(projectId, reviewerResourceId, PhaseType.CHECKPOINT_REVIEW_PHASE);
    }
    
    /**
     * This method retrieves scorecard and review information associated to a project determined by parameter.
     * Note: a single reviewer / review is assumed.
     *
     * @param projectId the project id to search for.
     * @param reviewerResourceId the reviewer ID.
     * @return the aggregated scorecard and review data.
     * @throws ProjectServicesException if any unexpected error occurs in the underlying services, if an invalid
     * number of reviewers or reviews are found or if the code fails to retrieve scorecard id.
     * @since 1.4.3
     */
    public List<ScorecardReviewData> getScorecardAndReviews(long projectId, long reviewerResourceId) 
        throws ProjectServicesException {
        return getScorecardAndReviews(projectId, reviewerResourceId, PhaseType.REVIEW_PHASE);
    }

    /**
     * This method retrieves scorecard and review information associated to a project determined by parameter.
     * Note: a single primary screener / screening is assumed.
     *
     * @param projectId the project id to search for.
     * @param screenerId the screener ID.
     * @return the aggregated scorecard and review data.
     * @throws ProjectServicesException if any unexpected error occurs in the underlying services, if an invalid
     * number of reviewers or reviews are found or if the code fails to retrieve scorecard id.
     * @since 1.4.3
     */
    public List<ScorecardReviewData> getScorecardAndScreening(long projectId, long screenerId) 
        throws ProjectServicesException {
        String method = "ProjectServicesImpl#getScorecardAndScreening(" + projectId + ") method.";

        List<ScorecardReviewData> scorecardReviewData = new ArrayList<ScorecardReviewData>();

        log(Level.INFO, "Enters " + method);
        try {
            // Build resources filter
            Filter filterProject = ResourceFilterBuilder.createProjectIdFilter(projectId);
            Filter filterRole = ResourceFilterBuilder.createResourceRoleIdFilter(ResourceRole.RESOURCE_ROLE_PRIMARY_SCREENER_ID);
            Filter filterRoles = new AndFilter(Arrays.asList(filterProject, filterRole));

            // Search for the screeners
            Resource[] screeners = resourceManager.searchResources(filterRoles);
            if (screeners.length > 1) {
                throw new ProjectServicesException("Invalid number of primary screener found: " + screeners.length);
            }

            Filter filterScreener = new EqualToFilter(RESOURCE_REVIEWER_PROPERTY, screeners[0].getId());
            Review[] reviews = reviewManager.searchReviews(filterScreener, true);
            for (int i = 0; i < reviews.length; i++) {
                Review review = reviews[i];
                ScorecardReviewData data = new ScorecardReviewData();
                data.setReview(review);
                data.setScorecard(scorecardManager.getScorecard(review.getScorecard()));
                scorecardReviewData.add(data);
            }
            
            if (scorecardReviewData.isEmpty()) {
                com.topcoder.project.phases.Project projectPhases = phaseManager.getPhases(projectId);
                long scorecardId = -1;
                if (projectPhases != null) {
                    Set<Phase> phases = projectPhases.getPhases();
                    Iterator<Phase> iter = phases.iterator();
                    while (iter.hasNext() && scorecardId < 0) {
                        Phase phase = iter.next();
                        if (phase.getPhaseType().getName().equals(PhaseType.SCREENING_PHASE.getName())) {
                            scorecardId = Long.parseLong(phase.getAttribute(SCORECARD_ID_PHASE_ATTRIBUTE_KEY).toString());
                        }
                    }
                }
                
                if (scorecardId < 0) {
                    throw new ProjectServicesException("Failed to find scorecard id");
                }
                ScorecardReviewData data = new ScorecardReviewData();
                data.setScorecard(scorecardManager.getScorecard(scorecardId));
                scorecardReviewData.add(data);
            }

        } catch (ReviewManagementException ex) {
            log(Level.ERROR, "ReviewManagementException occurred in " + method);
            throw new ProjectServicesException("ReviewManagementException occurred when operating Review Manager.", ex);
        } catch (com.topcoder.management.scorecard.PersistenceException ex) {
            log(Level.ERROR, "PersistenceException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating Scorecard Manager.", ex);
        } catch (ResourcePersistenceException ex) {
            log(Level.ERROR, "ResourcePersistenceException occurred in " + method);
            throw new ProjectServicesException("ResourcePersistenceException occurred when operating Resource Manager.",
                ex);
        } catch (PhaseManagementException ex) {
            log(Level.ERROR, "PhaseManagementException occurred in " + method);
            throw new ProjectServicesException("PhaseManagementException occurred when operating Phase Manager.", ex);
        } catch (SearchBuilderException ex) {
            log(Level.ERROR, "SearchBuilderException occurred in " + method);
            throw new ProjectServicesException("SearchBuilderException occurred when operating Search Builder.", ex);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }

        return scorecardReviewData;
    }

    /**
     * <p>Creates specified review for software project.</p>
     *
     * @param review a <code>Review</code> providing the details for review to be created.
     * @throws ReviewManagementException if an unexpected error occurs.
     * @since 1.4.3
     */
    public void createReview(Review review) throws ReviewManagementException {
        if (review == null) {
            throw new IllegalArgumentException("The parameter [review] is NULL");
        }
        reviewManager.createReview(review, review.getCreationUser());
    }

    /**
     * <p>Updates specified review for software project.</p>
     *
     * @param review a <code>Review</code> providing the details for review to be updated.
     * @throws ReviewManagementException if an unexpected error occurs.
     * @since 1.4.6
     */
    public void updateReview(Review review) throws ReviewManagementException {
        if (review == null) {
            throw new IllegalArgumentException("The parameter [review] is NULL");
        }
        reviewManager.updateReview(review, review.getCreationUser());
    }

    /**
     * <p>
     * Converts specified <code>XMLGregorianCalendar</code> instance into
     * <code>Date</code> instance.
     * </p>
     *
     * @param calendar
     *            an <code>XMLGregorianCalendar</code> representing the date to
     *            be converted.
     * @return a <code>Date</code> providing the converted value of specified
     *         calendar or <code>null</code> if specified <code>calendar</code>
     *         is <code>null</code>.
     */
    private Date getDate(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return null;
        }

        return calendar.toGregorianCalendar().getTime();
    }

    /**
     * <p>
     * Converts specified <code>Date</code> instance into
     * <code>XMLGregorianCalendar</code> instance.
     * </p>
     *
     * @param date
     *            a <code>Date</code> representing the date to be converted.
     * @return a <code>XMLGregorianCalendar</code> providing the converted value
     *         of specified date or <code>null</code> if specified
     *         <code>date</code> is <code>null</code> or if it can't be
     *         converted to calendar.
     */
    private XMLGregorianCalendar getXMLGregorianCalendar(Date date) {
        if (date == null) {
            return null;
        }

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);

        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (DatatypeConfigurationException ex) {
            return null;
        }
    }

   

    /**
     * <p>Gets the last phase from specified list of project phase. Current implementation looks up for the <code>Final
     * Review</code> phase but this may change later.</p>
     *
     * @param phases a <code>Phase</code> array providing current project phases.
     * @return a <code>Phase</code> providing the last phase or <code>null</code> if there is no such phase found,
     */
    private Phase getLastPhase(Phase[] phases) {
        Phase lastPhase = null;
        for (int i = 0; i < phases.length; i++) {
            Phase phase = phases[i];
            PhaseType phaseType = phase.getPhaseType();
            if ((phaseType != null) && phaseType.getName().equalsIgnoreCase("Final Review")) {
                lastPhase = phase;
            }
        }
        return lastPhase;
    }


     /**
     *  Get project only (no phase or resources)
     */
    public Project getProject(long projectId) throws ProjectServicesException
    {

        log(Level.INFO,
                "Enters ProjectServicesImpl#getProject method.");

        Project project = null;
        try {
            project = projectManager.getProject(projectId);
        } catch (PersistenceException ex) {
            log(
                    Level.ERROR,
                    "ProjectServicesException occurred in ProjectServicesImpl#getProject method.");
            throw new ProjectServicesException(
                    "PersistenceException occurred when operating getProject.",
                    ex);
        } 
        log(Level.INFO,
                "Exits ProjectServicesImpl#getProject method.");
        return project;
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
        log(Level.INFO,
                "Enters ProjectServicesImpl#getAllResourceRoles method.");

        ResourceRole[] allroles = null;
        try {
            allroles = resourceManager.getAllResourceRoles();
        } catch (ResourcePersistenceException ex) {
            log(
                    Level.ERROR,
                    "ResourcePersistenceException occurred in ProjectServicesImpl#getAllResourceRoles method.");
            throw new ProjectServicesException(
                    "PersistenceException occurred when operating getAllResourceRoles.",
                    ex);
        } 
        log(Level.INFO,
                "Exits ProjectServicesImpl#getAllResourceRoles method.");
        return allroles;

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

        log(Level.INFO, "Enters ProjectServicesImpl#getActiveContestsForUser method.");

        List<SimpleProjectContestData> result = new ArrayList<SimpleProjectContestData>();

        try {
            result = projectManager.getActiveContestsForUser(userId);
            
            
        } catch (PersistenceException ex) {
            log(Level.ERROR,
                    "ProjectServicesException occurred in ProjectServicesImpl#getActiveContestsForUser method.");
            throw new ProjectServicesException(
                    "PersistenceException occurred when operating getSimpleProjectContestDataByUser.", ex);
        }
        log(Level.INFO, "Exits ProjectServicesImpl#getActiveContestsForUser method.");

        // an empty list will be returned if fail to get result
        return result;
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

        log(Level.INFO, "Enters ProjectServicesImpl#getActiveDraftContestsForUser method.");

        List<SimpleProjectContestData> result = new ArrayList<SimpleProjectContestData>();

        try {
            result = projectManager.getActiveDraftContestsForUser(userId);
            
            
        } catch (PersistenceException ex) {
            log(Level.ERROR,
                    "ProjectServicesException occurred in ProjectServicesImpl#getActiveDraftContestsForUser method.");
            throw new ProjectServicesException(
                    "PersistenceException occurred when operating getSimpleProjectContestDataByUser.", ex);
        }
        log(Level.INFO, "Exits ProjectServicesImpl#getActiveDraftContestsForUser method.");

        // an empty list will be returned if fail to get result
        return result;
    }

    /**
     * Get project notifications of the given notification type for the given user.
     * 
     * @param userId the id of the user.
     * @param notificationType id of the notification type.
     * @param projectIds array of project ids to check.
     * 
     * @throws ProjectServicesException if any error occurs.
     * 
     * @since 1.4.2 BUGR-3706
     */
    public long[] getNotificationsForUser(long userId, long notificationType, long[] projectIds)
            throws ProjectServicesException {

        log(Level.INFO, "Enters ProjectServicesImpl#getNotificationsForUser method.");

        // initialize the result to an empty array
        long[] result = new long[0];

        try {
            result = this.resourceManager.getNotificationsForUser(userId, 1, projectIds);
        } catch (ResourcePersistenceException ex) {
            log(Level.ERROR,
                    "ResourcePersistenceException occurred in ProjectServicesImpl#getNotificationsForUser method.");
            throw new ProjectServicesException(
                    "ResourcePersistenceException occurred when operating getNotificationsForUser of ResourceManager.",
                    ex);
        }
        log(Level.INFO, "Exits ProjectServicesImpl#getNotificationsForUser method.");

        // an empty array will be returned if fail to get result
        return result;
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
        log(Level.INFO, "Enters ProjectServicesImpl#addNotifications method.");


        try {
            this.resourceManager.addNotifications(userId, projectIds, NotificationType.TIMELINE_NOTIFICATION, operator);
        } catch (ResourcePersistenceException ex) {
            log(Level.ERROR,
                    "ResourcePersistenceException occurred in ProjectServicesImpl#addNotifications method.");
            throw new ProjectServicesException(
                    "ResourcePersistenceException occurred when operating addNotifications of ResourceManager.",
                    ex);
        }
        log(Level.INFO, "Exits ProjectServicesImpl#addNotifications method.");
        
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
        log(Level.INFO, "Enters ProjectServicesImpl#removeNotifications method.");


        try {
            this.resourceManager.removeNotifications(userId, projectIds, NotificationType.TIMELINE_NOTIFICATION, operator);
        } catch (ResourcePersistenceException ex) {
            log(Level.ERROR,
                    "ResourcePersistenceException occurred in ProjectServicesImpl#removeNotifications method.");
            throw new ProjectServicesException(
                    "ResourcePersistenceException occurred when operating removeNotifications of ResourceManager.",
                    ex);
        }
        log(Level.INFO, "Exits ProjectServicesImpl#removeNotifications method.");

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
        log(Level.INFO, "Enters ProjectServicesImpl#resourceExists method.");


        try {
            return this.resourceManager.resourceExists(projectId, roleId, userId);
        } catch (ResourcePersistenceException ex) {
            log(Level.ERROR,
                    "ResourcePersistenceException occurred in ProjectServicesImpl#resourceExists method." + ex);
            throw new ProjectServicesException(
                    "ResourcePersistenceException occurred when resourceExists",
                    ex);
        }

    }

    /**
     * get boolean value from client project config
     * 
     * @param billingProjectId billing project id
     * @return true if requires, false otherwise.
     * @throws PersistenceException if any other error occurs.
     */
    private boolean getBooleanClientProjectConfig(long billingProjectId, BillingProjectConfigType billingType) throws PersistenceException {
        
        BillingProjectConfiguration approvalConfig = projectManager.getBillingProjectConfig(billingProjectId, billingType);
        
        // if no billing project configuration for post-mortem phase, use TRUE by default
        if (approvalConfig == null) {
            return true;
        } else {
            String value = approvalConfig.getValue();
            
            // the value is not correctly set, use TRUE by default
            if (value == null || value.trim().length() == 0) {
                return true;
            } else {
                
                // parse the value to boolean and return the result
                return Boolean.valueOf(value);
            }
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
        log(Level.INFO, "Enters ProjectServicesImpl#getAllFileTypes method.");


        try {
            return projectManager.getAllFileTypes();
        } catch (PersistenceException ex) {
            log(Level.ERROR,
                    "PersistenceException occurred in ProjectServicesImpl#getAllFileTypes method." + ex);
            throw new ProjectServicesException(
                    "PersistenceException occurred when getAllFileTypes",
                    ex);
        }
    }

    /**
     * Gets all project platforms.
     *
     * @return all the project platforms.
     * @throws ProjectServicesException if any error.
     * @since 1.8
     */
    public ProjectPlatform[] getAllProjectPlatforms() throws ProjectServicesException {
        log(Level.INFO, "Enters ProjectServicesImpl#getAllProjectPlatforms method.");


        try {
            return projectManager.getAllProjectPlatforms();
        } catch (PersistenceException ex) {
            log(Level.ERROR,
                    "PersistenceException occurred in ProjectServicesImpl#getAllProjectPlatforms method." + ex);
            throw new ProjectServicesException(
                    "PersistenceException occurred when getAllProjectPlatforms",
                    ex);
        }
    }


    /**
     * <p>Gets the scorecard matching the specified ID.</p>
     *
     * @param scorecardId a <code>long</code> providing the ID of a scorecard.
     * @return a <code>Scorecard</code> providing the details for scorecard.
     * @throws ProjectServicesException if an unexpected error occurs.
     * @since 1.4.9
     */
    public Scorecard getScorecard(long scorecardId) throws ProjectServicesException {
        log(Level.INFO, "Enters ProjectServicesImpl#getScorecard method.");
        try {
            return this.scorecardManager.getScorecard(scorecardId);
        } catch (com.topcoder.management.scorecard.PersistenceException e) {
            log(Level.ERROR, "PersistenceException occurred in ProjectServicesImpl#getScorecard method." + e);
            throw new ProjectServicesException("PersistenceException occurred when getScorecard", e);
        }
    }

    /**
     * <p>Gets the reviews of specified type for specified contest.</p>
     *
     * @param contestId a <code>long</code> providing the ID for the contest.
     * @param scorecardTypeId a <code>long</code> providing the ID for the scorecard type.
     * @return a <code>Review</code> array providing the details for found reviews.
     * @throws ProjectServicesException if an unexpected error occurs.
     * @since 1.4.9
     */
    public Review[] getReviews(long contestId, long scorecardTypeId) throws ProjectServicesException {
        log(Level.INFO, "Enters ProjectServicesImpl#getReviews method.");
        try {
            Filter projectFilter = new EqualToFilter("project", contestId);
            Filter scorecardTypeFilter = new EqualToFilter("scorecardType", scorecardTypeId);

            return this.reviewManager.searchReviews(new AndFilter(projectFilter, scorecardTypeFilter), true);
        } catch (ReviewManagementException e) {
            log(Level.ERROR, "PersistenceException occurred in ProjectServicesImpl#getReviews method." + e);
            throw new ProjectServicesException("PersistenceException occurred when getScorecard", e);
        }
    }

    /**
     * <p>Gets the reviews for specified phase of specified contest.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest to get review for.
     * @param phaseId a <code>long</code> providing the ID of a contest phase to get reviews for,
     * @return a <code>Review</code> array listing the reviews for the specified phase of specified contest.
     * @throws ProjectServicesException if an unexpected error occurs.
     * @since 1.4.11
     */
    public Review[] getReviewsByPhase(long contestId, long phaseId) throws ProjectServicesException {
        log(Level.INFO, "Enters ProjectServicesImpl#getReviewsByPhase method.");
        try {
            Filter phaseFilter = new EqualToFilter("projectPhase", phaseId);
            Filter projectFilter = new EqualToFilter("project", String.valueOf(contestId));
            Filter filter = new AndFilter(phaseFilter, projectFilter);
            return reviewManager.searchReviews(filter, true);
        } catch (ReviewManagementException e) {
            log(Level.ERROR, "ReviewManagementException occurred in ProjectServicesImpl#getReviewsByPhase method." + e);
            throw new ProjectServicesException("ReviewManagementException occurred when calling getReviewsByPhase", e);
        }
    }

    /**
     * Gets the phase template name which should be applied to the given project.
     *
     * @param projectHeader the given project.
     * @return the phase template name which should be applied to the project.
     * @since 1.4.5
     */
    private String getPhaseTemplateName(Project projectHeader) {
        String category = projectHeader.getProjectCategory().getName();
        String type = projectHeader.getProjectCategory().getProjectType().getName();
        
        String[] templates = template.getAllTemplateNames();
        for (String t : templates) {
            if (category.equalsIgnoreCase(t)) {
                return t;
            }
        }
        for (String t : templates) {
            if (type.equalsIgnoreCase(t)) {
                return t;
            }
        }
        
        return null;
    }

    /**
     * Gets the phase ids which should be removed from the given project.
     * 
     * @param projectHeader the given project.
     * @param requireApproval is approval phase required.
     * @param requireSpecReview is specification review phase required.
     * @param requireMultiRound is checkpoint phases required.
     * @return the phase ids which should be removed from the given project.
     * @since 1.4.5
     */
    private static long[] getLeftOutPhaseIds(Project projectHeader, boolean requireApproval, boolean requireSpecReview, boolean requireMultiRound) {
        List<Long> leftoutphases = new ArrayList<Long>();

        if (!requireApproval) {
            leftoutphases.add(new Long(PhaseType.APPROVAL_PHASE.getId()));
        }
        
        

        if (!requireSpecReview || ((projectHeader.getProjectCategory().getId() == DEVELOPMENT_PROJECT_CATEGORY_ID && !projectHeader.isDevOnly()))
                || (projectHeader.getProjectCategory().getId() == BUG_HUNT_PROJECT_CATEGORY_ID && projectHeader.isAutoCreationBugHunt())) {
            leftoutphases.add(new Long(PhaseType.SPECIFICATION_SUBMISSION_PHASE.getId()));
            leftoutphases.add(new Long(PhaseType.SPECIFICATION_REVIEW_PHASE.getId()));
        }
        
        if (!requireMultiRound) {
            // no multiround phases
            leftoutphases.add(PhaseType.CHECKPOINT_SUBMISSION_PHASE.getId());
            leftoutphases.add(PhaseType.CHECKPOINT_SCREENING_PHASE.getId());
            leftoutphases.add(PhaseType.CHECKPOINT_REVIEW_PHASE.getId());
        }

        long[] leftOutPhaseIds = new long[leftoutphases.size()];

        if (leftoutphases.size() > 0) {
            int i = 0;
            for (Long phaseId : leftoutphases ) {
                leftOutPhaseIds[i++] = phaseId.longValue();
            }
        }
        return leftOutPhaseIds;
    }

    /**
     * Adjust the duration of the phase to meet the specified end date.
     *
     * @param phaseType the phase type to adjust
     * @param project the project phases
     * @param endDate the specified end date. 
     * @since 1.4.5
     */
    private static long adjustPhaseForEndDate(PhaseType phaseType, com.topcoder.project.phases.Project project, Date endDate) {
        Phase targetPhase = null;
        for (Phase phase : project.getAllPhases()) {
            if (phase.getPhaseType().getId() == phaseType.getId()) {
                targetPhase = phase;
                break;
            }
        }
        if (targetPhase != null) {
            Date scheduler = targetPhase.calcEndDate();
            long diff = endDate.getTime() - scheduler.getTime();
            targetPhase.setLength(targetPhase.getLength() + diff);
        }

        return targetPhase.getLength();
    }

    /**
     * Set the default properties for the given phases.
     *
     * @param projectHeader the given project which the phases belong to.
     * @param projectPhases the given phases.
     * @param multiRound flag representing where contest is multi-round contest.
     * @throws PersistenceException if any error occurs.
     * @since 1.4.5
     */
    private void setNewPhasesProperties(Project projectHeader, 
            com.topcoder.project.phases.Project projectPhases, boolean multiRound, boolean isStudio) throws PersistenceException {
        long screenTemplateId = 0L;
        long reviewTemplateId = 0L;
        long iterativeReviewTemplateId = 0L;
        long approvalTemplateId = 0L;
        long specReviewTemplateId = 0L;
        long checkpointScreenTemplateId = 0L;
        long checkpointReviewTemplateId = 0L;

        try
        {

            if (projectHeader.getProjectCategory().getId() != ProjectCategory.FIRST2FINISH.getId()) {


                if (projectHeader.getProjectCategory().getId() != ProjectCategory.BUG_HUNT.getId()
                	  && projectHeader.getProjectCategory().getId() != ProjectCategory.CODE.getId()) {
                    // bug hunt/code does not have these
                    screenTemplateId = projectManager.getScorecardId(projectHeader.getProjectCategory().getId(), 1);
                    approvalTemplateId = projectManager.getScorecardId(ProjectCategory.GENERIC_SCORECARDS.getId(), 3);
                }

                reviewTemplateId = projectManager.getScorecardId(projectHeader.getProjectCategory().getId(), 2);
				
				if (projectHeader.getProjectCategory().getId() != ProjectCategory.CODE.getId()) {
					specReviewTemplateId = projectManager.getScorecardId(projectHeader.getProjectCategory().getId(), 5);
				}

                if (multiRound) {
                    if (isStudio) {
                        checkpointScreenTemplateId = projectManager.getScorecardId(
                                projectHeader.getProjectCategory().getId(), 6);
                    }
                    checkpointReviewTemplateId = projectManager.getScorecardId(
                            projectHeader.getProjectCategory().getId(), 7);
                }
            } else {
                // first2finish does not have screen, review, approval
                iterativeReviewTemplateId = projectManager.getScorecardId((ProjectCategory.FIRST2FINISH.getId()), 8);
            }

        }
        catch (Exception e)
        {
            //TODO default to user spec (6) for now
            Util.log(logger, Level.INFO, "Default scorecard not found for project type " + projectHeader.getProjectCategory().getId() + ", used project type 6 as default");
            screenTemplateId = projectManager.getScorecardId(6, 1);
            reviewTemplateId = projectManager.getScorecardId(6, 2);
            approvalTemplateId = projectManager.getScorecardId(ProjectCategory.GENERIC_SCORECARDS.getId(), 3);
            iterativeReviewTemplateId = projectManager.getScorecardId(ProjectCategory.GENERIC_SCORECARDS.getId(), 8);
            specReviewTemplateId = projectManager.getScorecardId(6, 5);
            if (multiRound)
            {
                checkpointScreenTemplateId = projectManager.getScorecardId(6, 6);
                checkpointReviewTemplateId = projectManager.getScorecardId(6, 7);
            }
        }

        for (Phase p : projectPhases.getAllPhases()) {
            p.setPhaseStatus(PhaseStatus.SCHEDULED);
            p.setScheduledStartDate(p.calcStartDate());
            p.setScheduledEndDate(p.calcEndDate());
            if (p.getPhaseType().getId() == PhaseType.REGISTRATION_PHASE.getId() 
                || p.getPhaseType().getId() == PhaseType.SPECIFICATION_SUBMISSION_PHASE.getId())
            {
                p.setFixedStartDate(p.calcStartDate());
            }
            

            if (p.getPhaseType().getName().equals(PhaseType.REGISTRATION_PHASE.getName()))
            {
                p.setAttribute("Registration Number", "0");
            }
            else if (p.getPhaseType().getName().equals("Screening"))
            {
                p.setAttribute(SCORECARD_ID_PHASE_ATTRIBUTE_KEY, String.valueOf(screenTemplateId));
            }
            else if (p.getPhaseType().getName().equals("Checkpoint Screening")) 
            {
                p.setAttribute(SCORECARD_ID_PHASE_ATTRIBUTE_KEY, String.valueOf(checkpointScreenTemplateId));
            }
            else if (p.getPhaseType().getName().equals("Review"))
            {
                p.setAttribute(SCORECARD_ID_PHASE_ATTRIBUTE_KEY, String.valueOf(reviewTemplateId));
                if (projectHeader.getProjectCategory().getId() == ProjectCategory.COPILOT_POSTING.getId() ||
                        projectHeader.getProjectCategory().getId() == ProjectCategory.BUG_HUNT.getId() ||
                        projectHeader.getProjectCategory().getProjectType().getId() == ProjectType.STUDIO.getId() ||
                        (projectHeader.getProjectCategory().getId() == ProjectCategory.CODE.getId() &&
                                projectHeader.getAutoAssignReviewerId() > 0)
                        ) {
                    // 1) copilot posting 2) studio 3) bug hunt 4) code with auto assigned review only requires one reviewer.
                    p.setAttribute("Reviewer Number", "1");
                } else if((projectHeader.getProjectCategory().getId() == ProjectCategory.CODE.getId() && projectHeader.getAutoAssignReviewerId() == 0)
                        || projectHeader.getProjectCategory().getId() == ProjectCategory.ASSEMBLY.getId()) {
                    p.setAttribute("Reviewer Number", "2");
                } else {
                    p.setAttribute("Reviewer Number", "2");
                }
            }
			else if (p.getPhaseType().getName().equals("Secondary Reviewer Review"))
            {
                p.setAttribute(SCORECARD_ID_PHASE_ATTRIBUTE_KEY, String.valueOf(reviewTemplateId));
                p.setAttribute("Reviewer Number", "2");    
            }
			else if (p.getPhaseType().getName().equals("Primary Review Evaluation"))
            {
                p.setAttribute("Reviewer Number", "1");
            }
            else if (p.getPhaseType().getName().equals("Checkpoint Review")) 
            {
                p.setAttribute(SCORECARD_ID_PHASE_ATTRIBUTE_KEY, String.valueOf(checkpointReviewTemplateId));
            }
            else if (p.getPhaseType().getName().equals("Appeals"))
            {
                p.setAttribute("View Response During Appeals", "No");
            }
            else if (p.getPhaseType().getName().equals("Approval"))
            {
               p.setAttribute(SCORECARD_ID_PHASE_ATTRIBUTE_KEY, String.valueOf(approvalTemplateId));
               p.setAttribute("Reviewer Number", "1");
            }
            else if (p.getPhaseType().getName().equals("Specification Review"))
            {
               p.setAttribute(SCORECARD_ID_PHASE_ATTRIBUTE_KEY, String.valueOf(specReviewTemplateId));
               p.setAttribute("Reviewer Number", "1");
            }
            else if (p.getPhaseType().getName().equals("Iterative Review"))
            {
                p.setAttribute(SCORECARD_ID_PHASE_ATTRIBUTE_KEY, String.valueOf(iterativeReviewTemplateId));
                p.setAttribute("Reviewer Number", "1");
            }
        }
    }
}
