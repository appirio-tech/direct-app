/*
 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileLock;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.axis.encoding.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionContext;
import com.topcoder.catalog.entity.CompUploadedFile;
import com.topcoder.clients.invoices.model.InvoiceType;
import com.topcoder.clients.model.Project;
import com.topcoder.direct.services.project.metadata.DirectProjectMetadataService;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadata;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.action.specreview.ViewSpecificationReviewActionResultData;
import com.topcoder.direct.services.view.dto.contest.BaseContestCommonDTO;
import com.topcoder.direct.services.view.dto.contest.ContestBriefDTO;
import com.topcoder.direct.services.view.dto.contest.ContestDashboardDTO;
import com.topcoder.direct.services.view.dto.contest.ContestRoundType;
import com.topcoder.direct.services.view.dto.contest.ContestStatsDTO;
import com.topcoder.direct.services.view.dto.contest.ContestStatus;
import com.topcoder.direct.services.view.dto.contest.PhasedContestDTO;
import com.topcoder.direct.services.view.dto.contest.ProjectPhaseDTO;
import com.topcoder.direct.services.view.dto.contest.ProjectPhaseType;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.interceptor.SecurityGroupsAccessInterceptor;
import com.topcoder.direct.services.view.util.jira.JiraRpcServiceWrapper;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.persistence.UploadPersistenceException;
import com.topcoder.management.project.CopilotContestExtraInfo;
import com.topcoder.management.project.CopilotContestExtraInfoType;
import com.topcoder.management.project.Prize;
import com.topcoder.management.project.ProjectCopilotType;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.review.data.Review;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.service.ContestSaleData;
import com.topcoder.project.service.ProjectServices;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.security.RolePrincipal;
import com.topcoder.security.TCPrincipal;
import com.topcoder.security.TCSubject;
import com.topcoder.security.groups.model.GroupPermissionType;
import com.topcoder.security.groups.model.ResourceType;
import com.topcoder.security.groups.services.AuthorizationService;
import com.topcoder.service.facade.contest.ContestServiceException;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.facade.project.ProjectServiceFacade;
import com.topcoder.service.permission.Permission;
import com.topcoder.service.permission.PermissionServiceException;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.service.user.UserServiceException;
import com.topcoder.shared.common.TCContext;
import com.topcoder.shared.dataAccess.DataAccess;
import com.topcoder.shared.dataAccess.Request;
import com.topcoder.shared.dataAccess.resultSet.ResultSetContainer;
import com.topcoder.shared.util.DBMS;
import com.topcoder.shared.util.dwload.CacheClearer;
import com.topcoder.web.common.CachedDataAccess;
import com.topcoder.web.common.cache.MaxAge;

/**
 * <p>
 * Direct common utility class.
 * </p>
 * <p>
 * Version 1.1 (Direct Registrants List assembly) change notes:
 * <ul>
 * <li>Added {@link #getContestStats(TCSubject, long, boolean)} method.</li>
 * <li>Added {@link #getTCSubjectFromSession()} method.</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.2 - Direct Launch Software Contests Assembly Change Note
 * <ul>
 * <li>Adds the new util function to get xml date from util date.</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.3 - Edit Software Assembly Change Note
 * <ul>
 * <li>Adds the new util function to get date string.</li>
 * </ul>
 * <ul>
 * <li>Adds the new util function to get end date of software competition.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.4 (Submission Viewer Release 1 Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #getStudioContestSubmissions(long, ContestRoundType, TCSubject, ContestServiceFacade)} method.
 *     </li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.5 (Direct Submission Viewer Release 2) Change notes:
 * <ul>
 * <li>Update {@link #getStudioContestSubmissions(long, ContestRoundType, TCSubject, ContestServiceFacade)} method
 * to use getMilestoneSubmissionsForContest and getFinalSubmissionsForContest methods to retrieve submissions.
 * </li>
 * <li>Add {@link #getContestPrizeNumber(SoftwareCompetition, ContestRoundType)} method
 * to get the number of a contest's prizes.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.6 (Direct Submission Viewer Release 3) Change notes:
 * <ul>
 * <li>Added {@link #getSubmissionsCheckout(List, ContestRoundType)} method to check whether submissions have already been
 * checked out.</li>
 * <li>Added {@link #getAdditionalPrize(StudioCompetition)} method to get the additional prize.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.6.1 (Direct Submission Viewer Release 4) Change notes:
 * <ul>
 * <li>Added {@link #getServletResponse()} method.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.6.2 (Direct Manage Copilot Postings Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Updated {@link #getContestStats(TCSubject, long, boolean)} method to calculate stats for <code>Copilot
 *     Posting</code> contests differently.</li>
 *   </ol>
 * </p>
 * <p>
 * Version 1.6.3 (TC Direct Release Assembly 7)) Change notes:
 * <ul>
 * <li>Added {@link #ADMIN_ROLE} field.</li>
 * <li>Added {@link #hasWritePermission} method and {@link #isRole} method.</li>
 * <li>Updated {@link #isPhaseOpen} method.</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.6.4 (TC Cockpit Billing Cost Report Assembly) Change notes:
 * <ul>
 *      <li>Added method getBillingsForClient</li>
 *      <li>Added method getProjectsForClient</li>
 *      <li>Added method getProjectsForBilling</li>
 *      <li>Added method getAllClients</li>
 * </ul>
 * These methods are refactored from EnterpriseDashboardAction, DashboardCostReportAction and
 * DashboardBillingCostReport action for increasing the reuse.
 * </p>
 * <p>
 * Version 1.6.5 (TC Cockpit Bug Tracking R1 Contest Tracking assembly) change notes:
 * <ul>
 *      <li>Change method getContestStats to add the number of jira issues and bug races of the contest into contest
 *      statistics</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.6.6 (TC Cockpit Bug Tracking R1 Cockpit Project Tracking assembly) change notes:
 * <ul>
 *      <li>Change method getContestStats to add contest issues into contest stats</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.6.7 (System Assembly - Direct TopCoder Scorecard Tool Integration) change notes:
 * <ul>
 *      <li>Added Scorecard administrator role checking.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.6.7 (TC Direct Contest Dashboard Update Assembly) change notes:
 * <ul>
 *      <li>Add setDashboardData method to set dashboard data.</li>
 *      <li>Add getStudioPhases method to get all phases for studio contest.</li>
 *      <li>Add getPhase method to generate single phase.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.6.8 (Release Assembly - TC Cockpit Enterprise Dashboard Update Assembly 1) change notes:
 * <ul>
 *      <li>Add method daysBetween to add contest issues into contest stats</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.6.9 (Release Assembly - Direct Improvements Assembly Release 3) change notes:
 * <ul>
 *      <li>Added {@link #setSoftwareCompetitionDirectProjectName(com.topcoder.service.project.SoftwareCompetition, java.util.List)} method</li>
 *      <li>Added {@link #setStudioCompetitionDirectProjectName(com.topcoder.service.project.StudioCompetition, java.util.List)}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.7 (TC Direct Replatforming Release 1) Change notes:
 *   <ol>
 *     <li>Update {@link #getContestStats(TCSubject, long, boolean)} method to work with the new Studio contest type.</li>
 *     <li>Add {@link #isMultiRound(SoftwareCompetition)} method.</li>
 *     <li>Add {@link #getMultiRoundEndDate(SoftwareCompetition)} method.</li>
 *     <li>Add {@link #isStudio(SoftwareCompetition)} method.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.7.1 (TC Direct Replatforming Release 3) Change notes:
 *   <ol>
 *     <li>Add {@link #MILESTONE_PRIZE_TYPE_ID} and {@link #CONTEST_PRIZE_TYPE_ID} constants.</li>
 *     <li>Add {@link #isPhaseScheduled(SoftwareCompetition, PhaseType)} method.</li>
 *     <li>Update {@link #getStudioContestSubmissions(long, ContestRoundType, TCSubject, ContestServiceFacade)} and
 *     {@link #getContestPrizeNumber(SoftwareCompetition, ContestRoundType)} methods to work with the new studio contest type.</li>
 *     <li>Add {@link #getContestCheckout(SoftwareCompetition, ContestRoundType)} method.</li>
 *     <li>Add {@link #getStudioSubmissionsFeedback(TCSubject, ContestServiceFacade, List, long, PhaseType)} method.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.7.2 (TC Direct Replatforming Release 5) Change notes:
 *   <ol>
 *     <li>Fixed {@link #getStudioContestSubmissions(long, ContestRoundType, TCSubject, ContestServiceFacade)} method to work for Final Round.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.7.3 (TC Direct Replatforming Release 4) Change notes:
 *   <ol>
 *     <li>Update {@link #getContestStats} method to use the
 *      new direct_contest_stats_replatforming query.</li>
 *   </ol>
 * </p>
 *
 *
 * <p>
 * Version 1.7.4 (Direct Release 6 Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Updated <code>setDashboardData</code> methods to properly analyze the types of the contest.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.7.5 (Release Assembly - TopCoder Cockpit Project Status Management) Change notes:
 *   <ol>
 *     <li>Updated <code>setDashboardData</code> methods to properly analyze the types of the contest.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.7.6 (TopCoder Cockpit Project Overview R1 Project Forum Backend Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #hasProjectReadPermission(BaseDirectStrutsAction, TCSubject, long)} method.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.7.7 (TC Accounting Tracking Invoiced Payments) Change notes:
 *   <ol>
 *     <li>Added {@link #getInvoiceType(String, List)} to get invoice type by the name of invoice type.</li>
 *     <li>Added {@link #canPerformInvoiceRecords(TCSubject)} method.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.7.8 (Release Assembly - Project Contest Fee Management ) Change notes:
 *   <ol>
 *     <li>Added {@link #isTCAccounting(TCSubject)} to check the user is TC Accounting or not.</li>
 *     <li>Added {@link #isSuperAdmin(TCSubject)} to check the user is TC Admin or not.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.7.9 (Release Assembly - Project Contest Fee Management Update 1 Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Re-imported contes fee classes from new package com.topcoder.clients.model.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.8.0 (Release Assembly - TopCoder Cockpit Studio Submission Viewer Revamp v1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #getStudioSubmissionArtifacts(long)} to fetch image file names.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.8.1 (Module Assembly - TopCoder Cockpit Project Dashboard Edit Project version 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #getClientIdForProject(com.topcoder.security.TCSubject, long)} to fetch the client id of the direct project.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.8.2 (Release Assembly - TC Cockpit Create Project Refactoring Assembly Part One) Change notes:
 *   <ol>
 *     <li>change call of contest service facade #getPermissionsByProject to permission service facade #getPermissionsByProject</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.8.3 (Release Assembly - TC Direct Cockpit Release One) changes:
 * <ol>
 *     <li>Update {@link #getContestStats(com.topcoder.security.TCSubject, long)}</li> to add milestone submission number,
 *     final submission number and is multiple round flag.
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.8.4 (Module Assembly - Adding Contest Approval Feature in Direct Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #hasReview(ProjectServices, SoftwareCompetition, long, long, long)} method.</li>
 *     <li>Added {@link #getUserResourceByRole(long, SoftwareCompetition, long)} method.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * 
 * <p>
 * Version 1.8.5 (Release Assembly - TC Direct Issue Tracking Tab Update Assembly 2 v1.0) change notes:
 *   <ol>
 *     <li>Added constant {@link #FINAL_FIX_UPLOAD_TYPE_ID}.</li>
 *     <li>Added method {@link #getLastClosedFinalFixPhase(ProjectServices, long)} to get the last closed
 *     final fix phase of a project.</li>
 *     <li>Added method {@link #addAttachmentsToIssue(String, SessionFileStore, String[])} to upload attachments
 *     to a JIRA issue.</li>
 *     <li>Added method {@link #addFinalFixToIssue(String, Upload, String)} to upload final fix to a JIRA issue.</li>
 *     <li>Added method {@link #getFinalFixUpload(long, long, ContestServiceFacade)} to get the final fix upload of
 *     a specified final fix phase.</li>
 *   </ol>
 * </p>
 * 
 * <p>
 * Version 1.8.6 (Release Assembly - TopCoder Cockpit Software Milestone Management) Change notes:
 *   <ol>
 *     <li>Updated {@link #getContestStats(TCSubject, long, SoftwareCompetition)} method to
 *     add parameter softwareCompetition.</li>
 *   </ol>
 * </p>
 *
 * Version 1.8.7 (Module Assembly - TC Cockpit Project Contests Batch Edit) change notes:
 * <ol>
 *     <li>Add method {@link #getCurrentProjectBrief(com.topcoder.service.facade.project.ProjectServiceFacade, long)}</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.8.8 (Release Assembly - TC Direct Cockpit Release Five) change notes:
 * <li>
 *    Fix getContestCheckout(SoftwareCompetition competition, ContestRoundType roundType), it should check whether the
 *    milestone review/review phase is closed.
 * </li>
 * </p>
 * <p>
 * Version 1.8.9 (Release Assembly - TopCoder Cockpit Project Overview Performance Improvement)
 * <ol>
 *     <li>Add method {@link #createProjectBriefDTOFromProjectData(com.topcoder.service.project.ProjectData)}</li>
 * </ol>
 * </p>
 * <p>
 * Version 1.9.0 (Release Assembly - TC Direct Cockpit Release Seven version 1.0)
 * <p>
 *     <li>Updated method {@link #hasReview(com.topcoder.project.service.ProjectServices, com.topcoder.service.project.SoftwareCompetition, long, long, long)}</li>
 *     <li>Added method {@link #hasReview(com.topcoder.project.service.ProjectServices, com.topcoder.service.project.SoftwareCompetition, long, long, long, java.util.List)}</li>
 * </p>
 * </p>
 *
 * <p>
 * Version 1.9.1 (Release Assembly - TopCoder Direct Billing Project Links)
 * <ul>
 *     <li>Update getDashboardClientBillingProjectMappings to use the new method to retrieve the client, billing, project mappings.
 *     The new method gets the mappings from direct_project_account table</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.9.2 (Cockpit Customer Copilot Posting Process Revamp Copilot Posting Dashboard)
 * <p>
 *     <li>Added method {@link #setCopilotDashboardSpecificData(com.topcoder.project.service.ProjectServices, long, com.topcoder.direct.services.view.dto.contest.ContestDashboardDTO)}</li>
 * </p>
 * </p>
 *
 * <p>
 * Version 1.9.3 (Topcoder Security Groups Backend - Direct Permissions Propagation Assembly 1.0) 
 * Change notes:
 *   <ol>
 *     <li>Added {@link #hasPermissionBySecurityGroups(TCSubject, long, AuthorizationService, GroupPermissionType...)}
 *     method.</li>
 *     <li>Updated {@link #hasWritePermission(BaseDirectStrutsAction, TCSubject, long, boolean)}, 
 *     {@link #hasProjectReadPermission(BaseDirectStrutsAction, TCSubject, long)}, 
 *     {@link #hasProjectWritePermission(BaseDirectStrutsAction, TCSubject, long)} methods to check user permissions
 *     against security groups in case all other checks have returned <code>false</code>.</li>
 *     <li>Fixed bug in {@link #hasProjectReadPermission(BaseDirectStrutsAction, TCSubject, long)}, 
 *     {@link #hasProjectWritePermission(BaseDirectStrutsAction, TCSubject, long)} methods </li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.9.4 (Release Assembly - TopCoder Security Groups - Release 2) Change notes:
 *   <ol>
 *     <li>Added {@link #isSecurityGroupsUIAvailable()} method.</li>
 *   </ol>
 * </p>
 *
 *
 * <p>
 * Version 1.9.5 (Release Assembly - TC Direct Cockpit Release Eight)
 * <ul>
 *     <li>Add method {@link #isMilestoneSubmissionPhaseClosed(com.topcoder.service.project.SoftwareCompetition)}</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Version 1.9.6 (Release Assembly - TopCoder Cockpit - Launch Contest Update for Marathon Match)
 * <ul>
 *     <li>Add method {@link #isMM(com.topcoder.service.project.SoftwareCompetition)}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.9.7 (Release Assembly - TC Cockpit New Enterprise Dashboard Release 2)
 * <ul>
 *     <li>Adds method {@link #convertMapKeyToString(java.util.Map)}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.9.8 (Release Assembly - TopCoder Cockpit - Billing Management)
 * <ul>
 *     <li>Add method {@link #getUserClientId(long)}</li>
 *     <li>Add method {@link #isClientUser(long)}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.9.6 (TC-Studio - Wireframe Viewer Modal Window Direct integration assembly v1.0)
 * <ul>
 *     <li>Add method {@link #createFile(InputStream, String)}</li>
 *     <li>Add method {@link #storeZipStream(InputStream, String)}</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Version 1.9.7 (TC-Studio - Wireframe Viewer Modal Window Direct Updates assembly v1.0)
 * <ul>
 *     <li>Update method {@link #getContestStats(TCSubject, long, SoftwareCompetition)} to
 *     set the contest type id.</li>
 *     <li>Update method {@link #storeZipStream(InputStream, String)} to always set the first directory name
 *     to be lower case.</li>
 * </ul>
 * </p>
 * 
 * @author BeBetter, isv, flexme, Blues, Veve, GreatKevin, isv, minhu, VeVe, GreatKevin, TCSASSEMBLER
 * @version 1.9.8
 */
public final class DirectUtils {
    /**
     * Constant for date format.
     */
    public static final String DATE_FORMAT = "MM/dd/yyyy";

    public static final String TIME_OLTP_DATABASE = "java:TimeDS";

    /**
     * Draft status list.
     */
    public final static List<String> DRAFT_STATUS = Arrays
        .asList("Draft", "Unactive - Not Yet Published", "Inactive");

    /**
     * Scheduled status list.
     */
    public final static List<String> SCHEDULED_STATUS = Arrays.asList("Scheduled", "Specification Submission", "Specification Review", "Passed Spec Review" );

    /**
     * Active status list.
     */
    public final static List<String> ACTIVE_STATUS = Arrays.asList("Active - Public", "Active", "Registration",
        "Submission", "Screening", "Review", "Appeals", "Appeals Response", "Aggregation", "Aggregation Review",
        "Final Fix", "Final Review", "Approval", "Action Required", "In Danger", "Extended");

    /**
     * Finished status list.
     */
    public final static List<String> FINISHED_STATUS = Arrays.asList("Completed", "No Winner Chosen",
        "Insufficient Submissions - ReRun Possible", "Insufficient Submissions", "Abandoned", "Inactive - Removed",
        "Cancelled - Failed Review", "Cancelled - Failed Screening", "Cancelled - Zero Submissions",
        "Cancelled - Winner Unresponsive", "Cancelled - Zero Registrations");

    /**
     * Cancelled status list.
     */
    public final static List<String> CANCELLED_STATUS = Arrays.asList("Cancelled - Client Request",
        "Cancelled - Requirement Infeasible");

    public final static String PROJECT_BILLING_MAPPING_RESULT_CACHE = "projectBillingMappingResultCache";

    public final static String PROJECT_BILLING_MAPPING_RECORD_CACHE = "projectBillingMappingRecordCache";

     /**
     * Represents the &quot;Specification Submission&quot; phase type.
     */
    private static final String SPECIFICATION_SUBMISSION = "Specification Submission";

    /**
     * Represents the &quot;Specification Review&quot; phase type.
     */
    private static final String SPECIFICATION_REVIEW = "Specification Review";

    /**
     * Private constant specifying administrator role.
     *
     * @since 1.6.2
     */
    private static final String ADMIN_ROLE = "Cockpit Administrator";

    /**
     * Private constant specifying TC operations role.
     */
    private static final String TC_OPERATIONS_ROLE = "TC Operations";

    /**
     * Private constant specifying TC operations role.
     */
    private static final String TC_STAFF_ROLE = "TC Staff";

    /**
     * The TC Accounting role.
     */
    private static final String TC_ACCOUNTING_ROLE = "TC Accounting";

    /**
     * The super Admin role.
     */
    private static final String SUPER_ADMIN_ROLE = "Admin Super Role";

    /**

     * Private constant specifying TC operations role.
     *
     *
     */
    private static final String SCORECARD_ADMIN_ROLE = "Scorecard Administrator";

    /**
     * The project name to use when the direct project name is not available, usually this will not happen.
     *
     * @since 1.6.7
     */
    private static final String DIRECT_PROJECT_NOT_AVAILABLE = "Project name not available";

    /**
     * Represents one hour in milliseconds.
     *
     * @since 1.6.7
     */
    private static final long ONE_HOUR = 60 * 60 * 1000L;

    /**
     * Represents the milestone submission type id.
     *
     * @since 1.7.2
     */
    private static final int MILESTONE_SUBMISSION_TYPE_ID = 3;

    /**
     * Represents the prize type id for milestone submission.
     *
     * @since 1.7.1
     */
    private static final long MILESTONE_PRIZE_TYPE_ID = 14L;

    /**
     * Represents the prize type if for contest submission.
     *
     * @since 1.7.1
     */
    private static final long CONTEST_PRIZE_TYPE_ID = 15L;

    /**
     * Represents the contest submission type id.
     *
     * @since 1.7.2
     */
    private static final int CONTEST_SUBMISSION_TYPE_ID = 1;

    /**
     * Represents the final fix upload type id.
     * 
     * @since 1.8.5
     */
    private static final int FINAL_FIX_UPLOAD_TYPE_ID = 3;

    private static final String IS_CLIENT_USER_SQL = "SELECT client_id FROM client_user_xref cux, user_account ua, common_oltp:user u"
     + " WHERE cux.user_id = ua.user_account_id AND UPPER(ua.user_name) = UPPER(u.handle) AND u.user_id = ?";
    
    /**
     * <p>
     * Default Constructor.
     * </p>
     */
    private DirectUtils() {

    }

    /**
     * Calculates the number of days between two dates.
     *
     * @param d1 the early date.
     * @param d2 the later date.
     * @return the number of days between d2 and d1.
     * @since 1.6.8
     */
    public static long daysBetween(Date d1, Date d2) {
        return ((d2.getTime() - d1.getTime() + ONE_HOUR) /
                (ONE_HOUR * 24));
    }

    /**
     * Gets date from date string.
     *
     * @param dateString the date string. see <code>DATE_FORMAT</code> for the format.
     * @return the <code>Date</code> object. it might be null.
     */
    public static Date getDate(String dateString) {
        if (dateString == null || dateString.trim().length() == 0) {
            return null;
        }

        try {
            return new SimpleDateFormat(DATE_FORMAT).parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Gets date from the <code>XMLGregorianCalendar</code> object.
     *
     * @param calendarDate <code>XMLGregorianCalendar</code> object.
     * @return the <code>Date</code> object
     */
    public static Date getDate(XMLGregorianCalendar calendarDate) {
        if (calendarDate == null) {
            return null;
        }

        return calendarDate.toGregorianCalendar().getTime();
    }

    /**
     * Gets date without time portion.
     *
     * @param date the original date
     * @return the date without time information
     */
    public static Date getDateWithoutTime(Date date) {
        DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        try {
            return formatter.parse(formatter.format(date));
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Gets <code>ContestServiceFacade</code> service bean.
     *
     * @return the <code>ContestServiceFacade</code> service bean
     * @throws NamingException if any naming exception occurs
     */
    public static ContestServiceFacade getContestServiceFacade() throws NamingException {
        Context context = TCContext.getContext(DirectProperties.CONTEST_SERVICE_FACADE_CONTEXT_FACTORY,
            DirectProperties.CONTEST_SERVICE_FACADE_PROVIDER_URL);
        return (ContestServiceFacade) context.lookup(DirectProperties.CONTEST_SERVICE_FACADE_JNDI_NAME);
    }

    /**
     * <p>
     * Gets the servlet request.
     * </p>
     *
     * @return the servlet request
     */
    public static HttpServletRequest getServletRequest() {
        return (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
    }

    public static Map<String, Object> getApplicationContext() {
        return ActionContext.getContext().getApplication();
    }

    /**
     * <p>
     * Gets the statistics for the specified contest.
     * </p>
     *
     * <p>
     * version 1.6.5 changes:
     *  - add the total number of jira issues and bug races into contest statistics.
     *
     * version 1.6.6 changes:
     * - add issues of the contest into contest stats.
     *
     * version 1.8.5 changes:
     * - add parameter softwareCompetition.
     * </p>
     *
     * @param currentUser a <code>TCSubject</code> representing the current user.
     * @param contestId a <code>long</code> providing the ID of a contest.
     * @param softwareCompetition the contest providing additional information, might be null
     * @return a <code>ContestStatsDTO</code> providing the statistics for specified contest.
     * @throws Exception if an unexpected error occurs while accessing the persistent data store.
     * @since 1.1
     */
    public static ContestStatsDTO getContestStats(TCSubject currentUser, long contestId,
            SoftwareCompetition softwareCompetition)
        throws Exception {

        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("direct_contest_stats_replatforming");
        request.setProperty("pj", String.valueOf(contestId));
        request.setProperty("uid", String.valueOf(currentUser.getUserId()));

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("direct_contest_stats_replatforming");
        final int recordNum = resultContainer.size();

        if (recordNum == 0) {
            // return null, if no record is found
            return null;
        }

        int recordIndex = 0;
        boolean isStudio = "Studio".equalsIgnoreCase(resultContainer.getStringItem(recordIndex, "type").trim());
        ProjectBriefDTO project = new ProjectBriefDTO();
        project.setId(resultContainer.getLongItem(recordIndex, "project_id"));
        project.setName(resultContainer.getStringItem(recordIndex, "project_name"));

        ContestBriefDTO contest;

            PhasedContestDTO phasedContest = new PhasedContestDTO();
            phasedContest.setCurrentPhases(DataProvider.getCurrentPhases(contestId));
            phasedContest.setStatus(ContestStatus.forName(resultContainer.getStringItem(recordIndex, "status")));
            /*List<DashboardContestSearchResultDTO> contests =
                DataProvider.searchUserContests(currentUser, null, null, null);
            for (DashboardContestSearchResultDTO c : contests) {
                if (c.getContest().getId() == contestId) {
                    phasedContest.setStatus(c.getStatus());
                    break;
                }
            }*/

        contest = phasedContest;

        contest.setId(resultContainer.getLongItem(recordIndex, "contest_id"));
        contest.setTitle(resultContainer.getStringItem(recordIndex, "contest_name"));
        contest.setProject(project);
        contest.setSoftware(!isStudio);
        contest.setContestTypeName(resultContainer.getStringItem(recordIndex, "type"));
        contest.setTypeId((int) softwareCompetition.getProjectHeader().getProjectCategory().getId());

        ContestStatsDTO dto = new ContestStatsDTO();
        dto.setCurrentStatus(resultContainer.getStringItem(recordIndex, "status"));
        dto.setEndTime(resultContainer.getTimestampItem(recordIndex, "end_date"));
        dto.setStartTime(resultContainer.getTimestampItem(recordIndex, "start_date"));
        dto.setSubmissionsNumber(resultContainer.getIntItem(recordIndex, "number_of_submission"));
        dto.setRegistrantsNumber(resultContainer.getIntItem(recordIndex, "number_of_registration"));
        dto.setForumPostsNumber(resultContainer.getIntItem(recordIndex, "number_of_forum"));
        dto.setSvn(resultContainer.getStringItem(recordIndex, "svn"));
        dto.setShowSpecReview(resultContainer.getBooleanItem(recordIndex, "has_spec_review"));
        dto.setMilestoneSubmissionNumber(resultContainer.getIntItem(recordIndex, "number_of_milestone_submission"));
        dto.setFinalSubmissionNumber(resultContainer.getIntItem(recordIndex, "number_of_final_submission"));
        dto.setMultipleRound(resultContainer.getBooleanItem(recordIndex, "is_multiple_round"));
        long forumId = -1;
        try {
            if (resultContainer.getStringItem(recordIndex, "forum_id") != null
                && !resultContainer.getStringItem(recordIndex, "forum_id").equals(""))
                forumId = Long.parseLong(resultContainer.getStringItem(recordIndex, "forum_id"));
            dto.setForumId(forumId);
        } catch (NumberFormatException ne) {
            // ignore
        }

        dto.setContest(contest);
        dto.setIsStudio(isStudio);

        // sets the issues of contests
        dto.setIssues(DataProvider.getContestIssues(contestId));

        // gets the number of issues and bug races for contest
        dto.setTotalJiraIssuesNumber(dto.getIssues().getIssuesNumber() + dto.getIssues().getBugRacesNumber());

        // set additional properties here
        if (softwareCompetition != null) {
            dto.setInMilestoneSubmissionOrMilestoneReview(isPhaseOpen(
                softwareCompetition, PhaseType.MILESTONE_SUBMISSION_PHASE) || isPhaseOpen(
                softwareCompetition, PhaseType.MILESTONE_REVIEW_PHASE));            
        }
        return dto;
    }

    /**
     * <p>
     * Gets the TCSubject instance from session.
     * </p>
     *
     * @return the TCSubject instance from session.
     * @since 1.1
     */
    public static TCSubject getTCSubjectFromSession() {
        HttpServletRequest request = getServletRequest();
        if (request == null) {
            return null;
        }
        return new SessionData(request.getSession()).getCurrentUser();
    }

    /**
     * Gets user roles from the persistence.
     *
     * @return a set of RolePrincipal which represents user roles
     * @throws Exception if any error occurs.
     */
    public static Set<TCPrincipal> getUserRoles(long userId) throws Exception {
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("security_roles");
        request.setProperty("uid", String.valueOf(userId));

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("security_roles");

        final int recordNum = resultContainer.size();

        Set<TCPrincipal> principals = new HashSet<TCPrincipal>();
        for (int i = 0; i < recordNum; i++) {
            long roleId = resultContainer.getLongItem(i, "role_id");
            String description = resultContainer.getStringItem(i, "description");

            principals.add(new RolePrincipal(description, roleId));
        }

        return principals;
    }


    /**
     * Get studio submission artifacts which contain file names.
     *
     * @return the artifacts which contain file names
     * @throws Exception if any error occurs.
     * @since 1.8.0
     */
    public static List<String> getStudioSubmissionArtifacts(long submissionId) throws Exception {
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("studio_submission_artifacts");
        request.setProperty("subid", String.valueOf(submissionId));

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("studio_submission_artifacts");

        final long recordNum = resultContainer.size();

        List<String> names = new ArrayList<String>();
        for (int i = 0; i < recordNum; i++) {
            names.add(resultContainer.getStringItem(i, "file_name"));
        }

        return names;
    }


    /**
     * Get studio submission artifact count.
     *
     * @return the number of artifacts
     * @throws Exception if any error occurs.
     */
    public static long getStudioSubmissionArtifactCount(long submissionId) throws Exception {
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("studio_submission_artifact_count");
        request.setProperty("subid", String.valueOf(submissionId));

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("studio_submission_artifact_count");

        final long recordNum = resultContainer.size();

        if (recordNum == 0) {
            // return null, if no record is found
            return 0;
        }

        long count = resultContainer.getLongItem(0, "artifact_count");

        return count;

    }

    /**
     * <p>
     * Creates the <code>XMLGregorianCalendar</code> from the given date.
     * </p>
     *
     * @param date the date
     * @return the created XMLGregorianCalendar instance
     * @throws DatatypeConfigurationException if fail to create the XMLGregorianCalendar instance
     */
    public static XMLGregorianCalendar newXMLGregorianCalendar(Date date) throws DatatypeConfigurationException {
        if (date == null) {
            date = new Date();
        }
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();

        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);

        return datatypeFactory.newXMLGregorianCalendar(gc);
    }

    /**
     * <p>Checks whether a <code>SoftwareCompetition</code> is multi round or not.</p>
     *
     * @param softwareCompetition the <code>SoftwareCompetition</code> to check.
     * @return true if the contest is multi round, false otherwise.
     * @since 1.7
     */
    public static boolean isMultiRound(SoftwareCompetition softwareCompetition) {
        if (softwareCompetition == null || softwareCompetition.getProjectPhases() == null) {
            return false;
        }
        for (Phase phase : softwareCompetition.getProjectPhases().getPhases()) {
            if (phase.getPhaseType().getId() == PhaseType.MILESTONE_SUBMISSION_PHASE.getId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>Gets the milestone submission phase end date for a multiround contest.
     *
     * @param softwareCompetition the multiround contest
     * @return the milestone submission phase end date
     * @since 1.7
     */
    public static Date getMultiRoundEndDate(SoftwareCompetition softwareCompetition) {
        if (softwareCompetition == null || softwareCompetition.getProjectPhases() == null) {
            return null;
        }

        for (Phase phase : softwareCompetition.getProjectPhases().getPhases()) {
            if (phase.getPhaseType().getId() == PhaseType.MILESTONE_SUBMISSION_PHASE.getId()) {
                return phase.getActualEndDate() != null ? phase.getActualEndDate() :
                    phase.getScheduledEndDate();
            }
        }
        return null;
    }

    /**
     * <p>Gets the submission phase end date for a contest.
     *
     * @param softwareCompetition the contest
     * @return the submission phase end date
     */
    public static Date getSubmissionEndDate(SoftwareCompetition softwareCompetition) {
        if (softwareCompetition == null || softwareCompetition.getProjectPhases() == null) {
            return null;
        }

        for (Phase phase : softwareCompetition.getProjectPhases().getPhases()) {
            if (phase.getPhaseType().getId() == PhaseType.SUBMISSION_PHASE.getId()) {
                return phase.getActualEndDate() != null ? phase.getActualEndDate() :
                    phase.getScheduledEndDate();
            }
        }
        return null;
    }
	
	/**
     * <p>Gets the submission phase end date for a contest.
     *
     * @param softwareCompetition the contest
     * @return the submission phase end date
     */
    public static Date getRegistrationStartDate(SoftwareCompetition softwareCompetition) {
        if (softwareCompetition == null || softwareCompetition.getProjectPhases() == null) {
            return null;
        }

        for (Phase phase : softwareCompetition.getProjectPhases().getPhases()) {
            if (phase.getPhaseType().getId() == PhaseType.REGISTRATION_PHASE.getId()) {
                return phase.getActualStartDate() != null ? phase.getActualStartDate() :
                    phase.getScheduledStartDate();
            }
        }
        return null;
    }

    /**
     * <p>Gets the submission phase of the competition.
     *
     * @param softwareCompetition the contest
     * @return the submission phase of the contest. null if not found.
     */
    public static Phase getRegistrationPhase(SoftwareCompetition softwareCompetition) {
        for (Phase phase : softwareCompetition.getProjectPhases().getPhases()) {
            if (phase.getPhaseType().getId() == PhaseType.REGISTRATION_PHASE.getId()) {
                return phase;
            }
        }
        return null;
    }

    /**
     * <p>
     * Gets the end date of software competition.
     * </p>
     *
     * @param softwareCompetition the software competition
     * @return the end date
     */
    public static Date getEndDate(SoftwareCompetition softwareCompetition) {
        if (softwareCompetition == null || softwareCompetition.getProjectPhases() == null) {
            return null;
        }

        Date endDate = null;
        for (Phase phase : softwareCompetition.getProjectPhases().getPhases()) {
            Date phaseEnd = (phase.getActualEndDate() != null) ? phase.getActualEndDate() : phase
                .getScheduledEndDate();
            if (endDate == null || phaseEnd.after(endDate)) {
                endDate = phaseEnd;
            }
        }
        return endDate;
    }

    /**
     * <p>
     * Determines if any of the phase (ignore spec review/submission) is open or not.
     * </p>
     *
     * @param softwareCompetition the software competition
     * @return true if any phase is open or closed; false if none is open or closed.
     */
    public static boolean isPhaseOpen(SoftwareCompetition softwareCompetition) {
        if (softwareCompetition == null || softwareCompetition.getProjectPhases() == null) {
            return false;
        }

        for (Phase phase : softwareCompetition.getProjectPhases().getPhases()) {
            if ((PhaseStatus.OPEN.getId() == phase.getPhaseStatus().getId()
                || PhaseStatus.CLOSED.getId() == phase.getPhaseStatus().getId())
                && !SPECIFICATION_SUBMISSION.equals(phase.getPhaseType().getName())
                && !SPECIFICATION_REVIEW.equals(phase.getPhaseType().getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether a phase of a specified contest is in Open status.
     *
     * @param softwareCompetition the specified contest
     * @param phaseType the phase
     * @return true if the phase of the specified contest is in open status, false otherwise.
     * @since 1.7.1
     */
    public static boolean isPhaseOpen(SoftwareCompetition softwareCompetition, PhaseType phaseType) {
        if (softwareCompetition == null || softwareCompetition.getProjectPhases() == null) {
            return false;
        }

        for (Phase phase : softwareCompetition.getProjectPhases().getPhases()) {
            if (phase.getPhaseType().getId() == phaseType.getId() && phase.getPhaseStatus().getId() == PhaseStatus.OPEN.getId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether a phase of a specified contest is in scheduled status.
     *
     * @param softwareCompetition the specified contest
     * @param phaseType the phase
     * @return true if the phase of the specified contest is in scheduled status, false otherwise.
     * @since 1.7.1
     */
    public static boolean isPhaseScheduled(SoftwareCompetition softwareCompetition, PhaseType phaseType) {
        if (softwareCompetition == null || softwareCompetition.getProjectPhases() == null) {
            return false;
        }

        for (Phase phase : softwareCompetition.getProjectPhases().getPhases()) {
            if (phase.getPhaseType().getId() == phaseType.getId() && phase.getPhaseStatus().getId() == PhaseStatus.SCHEDULED.getId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>
     * Determines if any of the phase (ignore spec review/submission) is open or not.
     * </p>
     *
     * @param softwareCompetition the software competition
     * @return true if any phase is open; false if none is open
     */
    public static boolean isSpecReviewStarted(SoftwareCompetition softwareCompetition) {
        if (softwareCompetition == null || softwareCompetition.getProjectPhases() == null) {
            return false;
        }

        Phase specificationReviewPhase = null;
        Phase specificationSubmissionPhase = null;

        Set<Phase> allPhases = softwareCompetition.getProjectPhases().getPhases();
        for (Phase phase : allPhases) {
            PhaseType phaseType = phase.getPhaseType();
            if (SPECIFICATION_SUBMISSION.equals(phaseType.getName())) {
                specificationSubmissionPhase = phase;
                break;
            }
        }
        for (Phase phase : allPhases) {
            PhaseType phaseType = phase.getPhaseType();
            if (SPECIFICATION_REVIEW.equals(phaseType.getName())) {
                specificationReviewPhase = phase;
                break;
            }
        }

        if (specificationReviewPhase != null
             && (PhaseStatus.OPEN.getName().equals(specificationReviewPhase.getPhaseStatus().getName())
                  || PhaseStatus.CLOSED.getName().equals(specificationReviewPhase.getPhaseStatus().getName())))
        {
            return true;
        }

        if (specificationSubmissionPhase != null
             && (PhaseStatus.OPEN.getName().equals(specificationSubmissionPhase.getPhaseStatus().getName())
                  || PhaseStatus.CLOSED.getName().equals(specificationSubmissionPhase.getPhaseStatus().getName())))
        {
            return true;
        }

        return false;
    }


    /**
     * <p>
     * Determines if any of the phase (ignore spec review/submission) is open or not.
     * </p>
     *
     * @param softwareCompetition the software competition
     * @return true if any phase is open; false if none is open
     */
    public static boolean hasSpecReview(SoftwareCompetition softwareCompetition) {
        if (softwareCompetition == null || softwareCompetition.getProjectPhases() == null) {
            return false;
        }

        Set<Phase> allPhases = softwareCompetition.getProjectPhases().getPhases();
        for (Phase phase : allPhases) {
            PhaseType phaseType = phase.getPhaseType();
            if (SPECIFICATION_SUBMISSION.equals(phaseType.getName())) {
                return true;
            }
            if (SPECIFICATION_REVIEW.equals(phaseType.getName())) {
                return true;
            }
        }

        return false;
    }

    /**
     * <p>
     * Gets the paid fee.
     * </p>
     *
     * @param softwareCompetition the software competition
     * @return the paid fee
     */
    public static double getPaidFee(SoftwareCompetition softwareCompetition) {
        if (softwareCompetition == null || softwareCompetition.getProjectData() == null) {
            return 0;
        }

        double pastPayment = 0;

        List<ContestSaleData> sales = softwareCompetition.getProjectData().getContestSales();
        if (sales != null) {
            for (ContestSaleData sale : sales) {
                pastPayment += sale.getPrice();
            }
        }

        return pastPayment;
    }

    /**
     * <p>
     * Gets date string from xml date.
     * </p>
     *
     * @param date the date object
     * @return the date string
     */
    public static String getDateString(Date date) {
        if (date == null) {
            return null;
        }
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        return dateFormat.format(date);
    }

    /**
     * <p>Gets the submissions for specified round of specified <code>Studio</code> contest.</p>
     *
     * @param projectId represents the contest.
     * @param roundType a <code>ContestRoundType</code> providing the type of the contest round.
     * @param currentUser a <code>TCSubject</code> representing the current user.
     * @param contestServiceFacade a <code>ContestServiceFacade</code> to be used for accessing the service layer for
     *        data retrieval.
     * @return a <code>List</code> listing the submissions for specified round of specified <code>Studio</code> contest.
     * @throws PermissionServiceException if an unexpected error occurs.
     * @throws ContestServiceException if an unexpected error occurs.
     * @since 1.4
     */
    public static List<Submission> getStudioContestSubmissions(long projectId, ContestRoundType roundType,
                                                                   TCSubject currentUser,
                                                                   ContestServiceFacade contestServiceFacade)
        throws UploadPersistenceException, SearchBuilderException {
        return Arrays.asList(contestServiceFacade.getSoftwareActiveSubmissions(projectId,
                roundType == ContestRoundType.MILESTONE ? MILESTONE_SUBMISSION_TYPE_ID : CONTEST_SUBMISSION_TYPE_ID));
    }

    /**
     * <p>Gets the number of prizes for specified round of specified <code>Studio</code> contest.</p>
     *
     * @param competition a <code>StudioCompetition</code> providing the studio contest.
     * @param roundType a <code>ContestRoundType</code> providing the type of the contest round.
     * @return an <code>int</code> providing the number of the prize for specified round of specified <code>Studio</code> contest.
     * @since 1.5
     */
    public static int getContestPrizeNumber(SoftwareCompetition competition, ContestRoundType roundType) {
        int tot1 = 0;
        int tot2 = 0;
        for (Prize prize : competition.getProjectHeader().getPrizes()) {
            if (prize.getPrizeType().getId() == MILESTONE_PRIZE_TYPE_ID) {
                tot1 += prize.getNumberOfSubmissions();
            } else if (prize.getPrizeType().getId() == CONTEST_PRIZE_TYPE_ID) {
                tot2 += prize.getNumberOfSubmissions();
            }
        }
        if (roundType == ContestRoundType.MILESTONE) {
            return tot1;
        } else {
            return tot2;
        }
    }

    /**
     * Sets the direct project name of the given SoftwareCompetition
     *
     * @param softwareCompetition the software competition to set.
     * @param projects all the direct projects the user has.
     * @since 1.6.7
     */
    public static void setSoftwareCompetitionDirectProjectName(SoftwareCompetition softwareCompetition, List<ProjectData> projects) {
        softwareCompetition.getProjectHeader().setTcDirectProjectName(getDirectProjectName(softwareCompetition.getProjectHeader().getTcDirectProjectId(), projects));
    }

    /**
     * Gets the name of the direct project by comparing the direct project id.
     *
     * @param directProjectId the direct project id
     * @param projects all the direct projects the user has
     * @return the found direct project name.
     * @since 1.6.7
     */
    private static String getDirectProjectName(long directProjectId, List<ProjectData> projects) {

        for(ProjectData project : projects) {
            if(project.getProjectId() == directProjectId) {
                return project.getName();
            }
        }

        return DIRECT_PROJECT_NOT_AVAILABLE;
    }


    /**
     * Checks whether a specified round type of a contest is checked out. If the corresponding phase is closed,
     * then the contest is checked out.
     *
     * @param competition the contest to check
     * @param roundType the specified round type
     * @return true if the specified round type of the contest is checked out, false otherwise.
     * @since 1.7.1
     */
    public static boolean getContestCheckout(SoftwareCompetition competition, ContestRoundType roundType) {
        for (Phase phase : competition.getProjectPhases().getPhases()) {
            if (roundType == ContestRoundType.MILESTONE) {
                if (phase.getPhaseType().getId() == PhaseType.MILESTONE_REVIEW_PHASE.getId()) {
                    return phase.getPhaseStatus().getId() == PhaseStatus.CLOSED.getId();
                }
            } else if (roundType == ContestRoundType.FINAL) {
                if (phase.getPhaseType().getId() == PhaseType.REVIEW_PHASE.getId()) {
                    return phase.getPhaseStatus().getId() == PhaseStatus.CLOSED.getId();
                }
            }
        }
        return false;
    }

    /**
     * Checks whether the milestone submission phase is closed for a multiple round software contest.
     *
     * @param competition the software contest to check
     * @return whether the milestone submission phase is closed for a multiple round software contest.
     *
     * @since 1.9.5
     */
    public static boolean isMilestoneSubmissionPhaseClosed(SoftwareCompetition competition) {
        for (Phase phase : competition.getProjectPhases().getPhases()) {
            if (phase.getPhaseType().getId() == PhaseType.MILESTONE_SUBMISSION_PHASE.getId()) {
                return phase.getPhaseStatus().getId() == PhaseStatus.CLOSED.getId();
            }
        }
        return false;
    }

     /**
     * Gets <code>UserTransaction</code> bean.
     *
     * @return the <code>UserTransaction</code> bean
     * @throws NamingException if any naming exception occurs
     */
    public static UserTransaction getUserTransaction() throws NamingException {
        Context context = TCContext.getContext(DirectProperties.CONTEST_SERVICE_FACADE_CONTEXT_FACTORY,
            DirectProperties.CONTEST_SERVICE_FACADE_PROVIDER_URL);
        return (UserTransaction) context.lookup("UserTransaction");
    }

    /**
     * <p>Gets the servlet response.</p>
     *
     * @return the servlet response.
     * @since 1.6.1
     */
    public static HttpServletResponse getServletResponse() {
        return (HttpServletResponse) ActionContext.getContext().get(ServletActionContext.HTTP_RESPONSE);
    }

    /**
     * <p>Checks whether a <code>SoftwareCompetition</code> is a studio contest or not.</p>
     *
     * @param competition the <code>SoftwareCompetition</code> to check
     * @return true if the contest is a studio contest, false otherwise.
     * @since 1.7
     */
    public static boolean isStudio(SoftwareCompetition competition) {
        return competition.getProjectHeader().getProjectCategory().getProjectType().getId() == ProjectType.STUDIO.getId();
    }
    
    /**
     * <p>Checks whether a <code>SoftwareCompetition</code> is a marathon match contest or not.</p>
     *
     * @param competition the <code>SoftwareCompetition</code> to check
     * @return true if the contest is a marathon match contest, false otherwise.
     * @since 1.9.6
     */
    public static boolean isMM(SoftwareCompetition competition) {
        return competition.getProjectHeader().getProjectCategory().getId() == 37;
    }

    /**
     * Check whether user has permission to a specify contest.
     *
     * @param action the action
     * @param tcSubject the user to check
     * @param contestId the contest id
     * @param isStudio whether is studio contest
     * @return has permission or not
     * @throws PermissionServiceException if an unexpected error occurs.
     * @throws ContestServiceException if an unexpected error occurs.
     * @since 1.6.2
     */
    public static boolean hasWritePermission(BaseDirectStrutsAction action, TCSubject tcSubject, long contestId,
                                             boolean isStudio)
        throws PermissionServiceException, ContestServiceException {
        if (!isRole(tcSubject, ADMIN_ROLE)) {
            boolean hasContestPermission = action.getProjectServices().checkContestPermission(contestId, false,
                                                                                              tcSubject.getUserId());
            if (hasContestPermission) {
                return true;
            } else {
                long tcDirectProjectId = action.getProjectServices().getTcDirectProject(contestId);
                return hasPermissionBySecurityGroups(tcSubject, tcDirectProjectId, action.getAuthorizationService(),
                                                     GroupPermissionType.WRITE, GroupPermissionType.FULL);
            }
        } else {
            return true;
        }

    }

    /**
     * Check if the user is TC Accounting
     *
     * @param tcSubject the user.
     * @return true if the user is TC Accounting and false otherwise.
     *
     * @since 1.7.7
     */
    public static boolean isTCAccounting(TCSubject tcSubject) {
        return isRole(tcSubject, TC_ACCOUNTING_ROLE);
    }

    /**
     * Check if the user is super admin
     *
     * @param tcSubject the user.
     * @return true if the user is super admin and false otherwise.
     *
     * @since 1.7.7
     */
    public static boolean isSuperAdmin(TCSubject tcSubject) {
        return isRole(tcSubject, SUPER_ADMIN_ROLE);
    }

    /**
     * <p>
     * Checks if the user is cockpit administrator.
     * </p>
     *
     * @param tcSubject  TCSubject instance for login user
     * @return true if the user is cockpit administrator and false otherwise.
     */
    public static boolean isCockpitAdmin(TCSubject tcSubject) {
        return isRole(tcSubject, ADMIN_ROLE);
    }

    /**
     * <p>
     * Checks if the user is cockpit administrator.
     * </p>
     *
     * @param tcSubject  TCSubject instance for login user
     * @return true if the user is cockpit administrator and false otherwise.
     */
    public static boolean isTcOperations(TCSubject tcSubject) {
        return isRole(tcSubject, TC_OPERATIONS_ROLE);
    }

    /**
     * <p>
     * Checks if the user is cockpit administrator.
     * </p>
     *
     * @param tcSubject  TCSubject instance for login user
     * @return true if the user is cockpit administrator and false otherwise.
     */
    public static boolean isTcStaff(TCSubject tcSubject) {
        return isRole(tcSubject, TC_STAFF_ROLE);
    }

    /**

     * <p>
     * Checks if the user is scorecard administrator.
     * </p>
     *
     * @return true if the user is scorcard administrator and false otherwise.
     * @since 1.6.7
     */
    public static boolean isScorecardAdmin() {
        return isRole(getTCSubjectFromSession(), SCORECARD_ADMIN_ROLE);
    }

    /**

     * <p>
     * Checks if the user can view internal stats.
     * </p>
     *
     * @return true if the user can view internal stats and false otherwise.
     * @since 1.6.7
     */
    public static boolean canViewInternalStats() {
        return isRole(getTCSubjectFromSession(), TC_OPERATIONS_ROLE);
    }

    /**
     * Gets the client feedback for submissions.
     *
     * @param currentUser TCSubject instance for login user
     * @param contestServiceFacade the <code>ContestServiceFacade</code> service bean
     * @param submissions a <code>List</code> providing the submissions
     * @param projectId the project id which the submissions belong to
     * @param phaseType the phase type which the submissions belong to
     * @return a <code>Map</code> providing the client feedback for the submissions.
     * @throws Exception if any error occurs
     * @since 1.7.1
     */
    public static Map<Long, String> getStudioSubmissionsFeedback(TCSubject currentUser, ContestServiceFacade contestServiceFacade,
            List<Submission> submissions, long projectId, PhaseType phaseType) throws Exception {
        Map<Long, String> feedback = new HashMap<Long, String>();
        for (Submission submission : submissions) {
            feedback.put(submission.getId(),
                    contestServiceFacade.getStudioSubmissionFeedback(currentUser, projectId, submission.getId(), phaseType));
        }
        return feedback;
    }
    /**
     * Gets the mappings of client, billing and projects.
     *
     * <p>
     *     Update in version 1.9.1:
     *     - use getDashboardClientBillingProjectMappingsV2 to get mappings
     * </p>
     *
     * @param tcSubject the tcSubject
     * @return the mapping of client, billing and projects
     * @throws Exception if any error occurs.
     * @since 1.6.4
     */
    private static Map<String, Object> getDashboardClientBillingProjectMappings(TCSubject tcSubject) throws Exception {
        return DataProvider.getDashboardClientBillingProjectMappingsV2(tcSubject);
    }

    /**
     * Gets the billing accounts of the given client.
     *
     * @param tcSubject the tcSubject instance.
     * @param clientId the id of the client.
     * @return the billing accounts of the client.
     * @throws Exception if any error occurs.
     * @since 1.6.4
     */
    public static Map<Long, String> getBillingsForClient(TCSubject tcSubject, long clientId) throws Exception {
        Map<Long, Map<Long, String>> data = (Map<Long, Map<Long, String>>) getDashboardClientBillingProjectMappings(tcSubject).get("client.billing");
        Map<Long, String> result =  data.get(clientId);
        if (result == null) {
            return new HashMap<Long, String>();
        } else {
            return new HashMap<Long, String>(result);
        }
    }

    /**
     * Gets the billing account id of the given project.
     *
     * @param tcSubject the tcSubject instance.
     * @param projectId the id of the project.
     * @return the billing account id, or -1 if not found.
     * @throws Exception if any error occurs.
     */
    public static Long getBillingIdForProject(TCSubject tcSubject, long projectId) throws Exception {
        Map<Long, Map<Long, String>> data = (Map<Long, Map<Long, String>>) getDashboardClientBillingProjectMappings(tcSubject).get("billing.project");

        for (Map.Entry<Long, Map<Long, String>> entry : data.entrySet()) {
            Map<Long, String> projects = entry.getValue();

            if (projects.containsKey(projectId)) {
                return entry.getKey();
            }
        }

        return -1l;
    }

    /**
     * Gets the billing accounts.
     *
     * @param tcSubject the tcSubject instance.
     * @return the billing accounts, never null, possible entry
     * @throws Exception if any error occurs.
     */
    public static List<Project> getBillingAccounts(TCSubject tcSubject) throws Exception {
        Map<Long, Map<Long, String>> data = (Map<Long, Map<Long, String>>) getDashboardClientBillingProjectMappings(tcSubject).get("client.billing");

        List<Project> billingAccounts = new ArrayList<Project>();
        for (Map<Long, String> billingData : data.values()) {

            for (Map.Entry<Long, String> entry : billingData.entrySet()) {
                Project project = new Project();
                project.setId(entry.getKey());
                project.setName(entry.getValue());

                billingAccounts.add(project);
            }
        }

        return billingAccounts;
    }

    /**
     * Gets the projects of the given client.
     *
     * @param tcSubject the tcSubject instance.
     * @param clientId the client id.
     * @return the projects of the client.
     * @throws Exception if any error occurs.
     * @since 1.6.4
     */
    public static Map<Long, String> getProjectsForClient(TCSubject tcSubject, long clientId) throws Exception {
        Map<Long, Map<Long, String>> data = (Map<Long, Map<Long, String>>) getDashboardClientBillingProjectMappings(tcSubject).get("client.project");
        Map<Long, String> result =  data.get(clientId);
        if (result == null) {
            return new HashMap<Long, String>();
        } else {

            return new HashMap<Long, String>(result);
        }
    }

    /**
     * Gets the projects of the given billing.
     *
     * @param tcSubject the tcSubject instance.
     * @param billingId the billing account id.
     * @return the billing accounts of the project.
     * @throws Exception if any error occurs.
     * @since 1.6.4
     */
    public static Map<Long, String> getProjectsForBilling(TCSubject tcSubject, long billingId) throws Exception {
        Map<Long, Map<Long, String>> data = (Map<Long, Map<Long, String>>) getDashboardClientBillingProjectMappings(tcSubject).get("billing.project");
        Map<Long, String> result =  data.get(billingId);
        if (result == null) {
            return new HashMap<Long, String>();
        } else {
            return new HashMap<Long, String>(result);
        }
    }

    /**
     * Gets the client id for the specified project id.
     *
     * @param tcSubject the current user.
     * @param directProjectId the id of the direct project.
     * @return the client id
     * @throws Exception if any error.
     * @since 1.8.1
     */
    public static Long getClientIdForProject(TCSubject tcSubject, long directProjectId) throws Exception {
        Map<Long, Long> projectClientMap = (Map<Long, Long>) getDashboardClientBillingProjectMappings(tcSubject).get("project.client");

        return projectClientMap.get(directProjectId);
    }

    /**
     * Gets all the clients of current user.
     *
     * @param tcSubject the tcSubject instance.
     * @return all the clients.
     * @throws Exception if any error occurs.
     * @since 1.6.4
     */
    public static Map<Long, String> getAllClients(TCSubject tcSubject) throws Exception {
        Map<Long, String> result = sortByValue((Map<Long, String>) getDashboardClientBillingProjectMappings(tcSubject).get("clients"));
        return new LinkedHashMap<Long, String>(result);
    }

    /**
     * Utility method to sort the map and returned a ordered one (backend with a LinkedHashMap).
     *
     * @param map the map to sort.
     * @return the sorted map.
     * @since 1.6.4
     */
    private static Map<Long, String> sortByValue(Map<Long, String> map) {
        List list = new LinkedList<Map.Entry<Long, String>>(map.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });

        Map<Long, String> result = new LinkedHashMap<Long, String>();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry<Long, String> entry = (Map.Entry<Long, String>) it.next();
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    /**
     * Utility method to covert a List<Long> to an array of long and return.
     *
     * @param list the list to convert.
     * @return the converted array.
     * @since 1.6.4
     */
    public static long[] covertLongListToArray(List<Long> list) {
        long[] result = new long[list.size()];
        int index = 0;
        for (Long item : list) {
            result[index++] = item.longValue();
        }
        return result;
    }


    /**
     * <p>
     * Checks if the login user is of given role
     * </p>
     *
     * @param tcSubject
     *            TCSubject instance for login user
     * @return true if it is given role
     * @since 1.6.2
     */
    public static boolean isRole(TCSubject tcSubject, String roleName) {
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
     * <p>Gets the additional prize for a studio competition.</p>
     *
     * @param softwareCompetition the studio competition
     * @return the additional prize for the studio competition
     */
    public static double getAdditionalPrize(SoftwareCompetition softwareCompetition) {
        List<Prize> prizes = softwareCompetition.getProjectHeader().getPrizes();
        if (prizes.size() == 0) {
            return 0.0;
}

        double prize = Double.MAX_VALUE;
        for (int i = 0; i < prizes.size(); i++) {
            // only check contest prize
            if (prizes.get(i).getPrizeType().getId() == 15) {
                prize = Math.min(prize, prizes.get(i).getPrizeAmount());
            }
        }

        if (prize == Double.MAX_VALUE) {
            // since there are always contest prize, no such case.
            prize = 0.0;
        }
        return prize;
    }

    /**
     * <p>Gets the contest prizes.</p>
     *
     * @param softwareCompetition the studio competition
     * @return the list of contest prize
     */
    public static List<Prize> getContestPrizes(SoftwareCompetition softwareCompetition) {
        List<Prize> prizes = new ArrayList<Prize>(softwareCompetition.getProjectHeader().getPrizes());
        for (Iterator<Prize> iter = prizes.iterator(); iter.hasNext();) {
            Prize prize = iter.next();
            // only left contest prize
            if (prize.getPrizeType().getId() != 15) {
                iter.remove();
            }
        }

        return prizes;
    }

    /**
     * <p>Gets the milestone prize.</p>
     *
     * @param softwareCompetition the studio competition
     * @return the milestone prize or null
     */
    public static Prize getMilestonePrize(SoftwareCompetition softwareCompetition) {
        List<Prize> prizes = softwareCompetition.getProjectHeader().getPrizes();
        for (Iterator<Prize> iter = prizes.iterator(); iter.hasNext();) {
            Prize prize = iter.next();
            // only left contest prize
            if (prize.getPrizeType().getId() == 14) {
                return prize;
            }
        }

        return null;
    }

    /**
     * Set dashboard data.
     *
     * @param currentUser current user
     * @param contestId the contest id
     * @param dto the contest common DTO
     * @param facade the contest service facade
     * @param software whether it is a software contest
     * @throws Exception if any exception occurs
     * @since 1.6.7
     */
    public static void setDashboardData(TCSubject currentUser, long contestId, BaseContestCommonDTO dto, ContestServiceFacade facade, boolean software) throws Exception {
        if (dto.getContestStats() == null) {
            dto.setContestStats(DirectUtils.getContestStats(currentUser, contestId,
                facade.getSoftwareContestByProjectId(currentUser, contestId)));
        }
        dto.setDashboard(DataProvider.getContestDashboardData(contestId, !software, false));

        dto.setDashboard(adjustPhases(dto.getDashboard()));
    }

    /**
     * Set dashboard data.
     *
     * @param currentUser current user
     * @param contestId the contest id
     * @param dto the specification review dto
     * @param facade the contest service facade
     * @param software whether it is a software contest
     * @throws Exception if any exception occurs
     * @since 1.6.7
     */
    public static void setDashboardData(TCSubject currentUser, long contestId, ViewSpecificationReviewActionResultData dto, ContestServiceFacade facade, boolean software) throws Exception {
        if (dto.getContestStats() == null) {
            dto.setContestStats(DirectUtils.getContestStats(currentUser, contestId,
                facade.getSoftwareContestByProjectId(currentUser, contestId)));
        }
        dto.setDashboard(DataProvider.getContestDashboardData(contestId, !software, false));

        dto.setDashboard(adjustPhases(dto.getDashboard()));
    }

    /**
     * Sets the copilot dashboard data.
     *
     * @param contestService the contest service.
     * @param contestId the contest id.
     * @param dashboardDTO the dashboard DTO
     * @since 1.9.1
     */
    public static void setCopilotDashboardSpecificData(ProjectServices contestService,
                            ProjectServiceFacade projectServiceFacade,
                            DirectProjectMetadataService metadataService, long contestId,
                            long directProjectId, ContestDashboardDTO dashboardDTO) throws Exception {

        final TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

        // set project
        com.topcoder.management.project.Project project = contestService.getProject(contestId);
        Set<Long> copilotProjectTypes = new HashSet<Long>();

        if (project.getProjectCopilotTypes() != null && project.getProjectCopilotTypes().size() > 0) {
            for (ProjectCopilotType type : project.getProjectCopilotTypes()) {
                copilotProjectTypes.add(type.getId());
            }
        }

        dashboardDTO.setCopilotProjectTypes(copilotProjectTypes);

        String budget = null;
        String otherManagingExperienceString = null;
        if (project.getCopilotContestExtraInfos() != null && project.getCopilotContestExtraInfos().size() > 0) {
            for (CopilotContestExtraInfo extraInfo : project.getCopilotContestExtraInfos()) {
                if (extraInfo.getType().getId() == CopilotContestExtraInfoType.BUDGET.getId()) {
                    budget = extraInfo.getValue();
                }
                if (extraInfo.getType().getId() == CopilotContestExtraInfoType.OTHER_MANAGING_EXPERIENCE.getId()) {
                    otherManagingExperienceString = extraInfo.getValue();
                }
            }
        }

        dashboardDTO.setBudget(budget);
        dashboardDTO.setOtherManagingExperienceString(otherManagingExperienceString);

        // set project type
        final ProjectData tp = projectServiceFacade.getProject(currentUser, directProjectId);

        String projectType = null;
        long projectTypeId = 0;

        if (tp.getProjectType() != null) {
            projectType = tp.getProjectType().getName();
            projectTypeId = tp.getProjectType().getProjectTypeId();

            if(tp.getProjectCategory() != null) {
                projectType = projectType + " - " + tp.getProjectCategory().getName();
            }
        }

        dashboardDTO.setDirectProjectType(projectType);
        dashboardDTO.setDirectProjectTypeId(projectTypeId);

        // set project duration
        List<DirectProjectMetadata> metadata = metadataService.getProjectMetadataByProject(directProjectId);

        for (DirectProjectMetadata data : metadata) {
            long keyId = data.getProjectMetadataKey().getId();
            String value = data.getMetadataValue();
            if (value == null || value.trim().length() == 0) {
                // value does not exist, continue
                continue;
            }

            if (keyId == 6L) {
                // duration
                dashboardDTO.setDirectProjectDuration(value);
            }
        }
    }

    /**
     * Checks whether the given user has write permission to the given project.
     *
     * @param action the action invokes this method.
     * @param tcSubject the TCSubject instance which represents an user
     * @param directProjectId the id of the direct project
     * @return true if has write permission, false otherwise
     * @throws PermissionServiceException
     * @since 1.7.1
     */
    public static boolean hasProjectWritePermission(BaseDirectStrutsAction action,
                                                    TCSubject tcSubject, long directProjectId) throws PermissionServiceException {
        if (!isRole(tcSubject, ADMIN_ROLE)) {

            List<Permission> permissions = action.getPermissionServiceFacade().getPermissionsByProject(tcSubject, directProjectId);

            for (Permission p : permissions) {

                if (p.getUserId() == tcSubject.getUserId()) {
                    if (p.getPermissionType().getPermissionTypeId() == 2L 
                        || p.getPermissionType().getPermissionTypeId() == 3L) {
                        return true;
                    }
                 }
            }

            return hasPermissionBySecurityGroups(tcSubject, directProjectId, action.getAuthorizationService(),
                                                 GroupPermissionType.WRITE, GroupPermissionType.FULL);

        } else {
            return true;
        }
    }

    /**
     * Checks whether the given user has write permission to the given project.
     *
     * @param action the action invokes this method.
     * @param tcSubject the TCSubject instance which represents an user
     * @param directProjectId the id of the direct project
     * @return true if has write permission, false otherwise
     * @throws PermissionServiceException if an unexpected error occurs.
     * @since 1.7.6
     */
    public static boolean hasProjectReadPermission(BaseDirectStrutsAction action,
                                                   TCSubject tcSubject, long directProjectId) throws PermissionServiceException {
        if (!isRole(tcSubject, ADMIN_ROLE)) {
            List<Permission> permissions = action.getPermissionServiceFacade().getPermissionsByProject(tcSubject, directProjectId);
            for (Permission p : permissions) {
                if (p.getUserId() == tcSubject.getUserId()) {
                    if (p.getPermissionType().getPermissionTypeId() == 1L
                        || p.getPermissionType().getPermissionTypeId() == 2L
                        || p.getPermissionType().getPermissionTypeId() == 3L) {
                        return true;
                    }
                }
            }

            return hasPermissionBySecurityGroups(tcSubject, directProjectId, action.getAuthorizationService(),
                                                 GroupPermissionType.READ, GroupPermissionType.FULL);

        } else {
            return true;
        }
    }

    /**
     * Sets the Date instance to the midnight, so it does not have the time portion.
     *
     * @param date the date instance to transfer.
     * @return the transferred date.
     */
    public static Date setTimeToMidnight(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }


    public static ContestDashboardDTO adjustPhases(ContestDashboardDTO dashboard) {
        List<ProjectPhaseDTO> phases = dashboard.getAllPhases();
        List<ProjectPhaseDTO> adjustedPhases = new ArrayList<ProjectPhaseDTO>();
        Map<ProjectPhaseType, Integer> phaseIndex = new HashMap<ProjectPhaseType, Integer>();

        for (ProjectPhaseDTO phase : phases) {
            if (!phaseIndex.containsKey(phase.getPhaseType())) {
                adjustedPhases.add(phase);
                phaseIndex.put(phase.getPhaseType(), adjustedPhases.size() - 1);
            }

            ProjectPhaseDTO tmpPhase = adjustedPhases.get(phaseIndex.get(phase.getPhaseType()));

            tmpPhase.increaseNum();
            tmpPhase.setStartTime(phase.getStartTime());
            tmpPhase.setEndTime(phase.getEndTime());
        }

        dashboard.setAllPhases(adjustedPhases);
        return dashboard;
    }

    /**
     * <p>
     * Helper method to clean registrants query results from the cache to
     * reflect the latest addition.
     * </p>
     */
    public static void refreshCache(String commandToRefresh) {
        try {
            if (commandToRefresh != null && !commandToRefresh.equals(""))
            {
                CacheClearer.removelike(commandToRefresh);
            }

        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
    }

    /**
     * Gets the <code>InvoiceType</code> instance by the name of invoice type.
     *
     * @param invoiceTypeName the name of the invoice type.
     * @param invoiceTypes all the invoice types.
     * @return the <code>InvoiceType</code> with the specified invoice type name. Null if not found.
     * @since 1.7.7
     */
    public static InvoiceType getInvoiceType(String invoiceTypeName, List<InvoiceType> invoiceTypes) {
        for (InvoiceType invoiceType : invoiceTypes) {
            if (invoiceType.getName().equals(invoiceTypeName)) {
                return invoiceType;
            }
        }
        return null;
    }

    /**
     * Checks whether the user can perform operations on invoice records.
     *
     * @param tcSubject the user.
     * @return true if the user can perform operations on invoice records, false otherwise.
     * @since 1.7.7
     */
    public static boolean canPerformInvoiceRecords(TCSubject tcSubject) {
        return DirectUtils.isCockpitAdmin(tcSubject) || DirectUtils.isTCAccounting(tcSubject);
    }

    /**
     * Get all contest types in database.
     *
     * @return all contest types.
     * @throws Exception
     *             if there is any exception.
     */
    public static Map<String, com.topcoder.clients.model.ContestType> getContesetTypes() throws Exception {

        CachedDataAccess dataAccess = new CachedDataAccess(MaxAge.QUARTER_HOUR, DBMS.TCS_OLTP_DATASOURCE_NAME);

        Request request = new Request();
        request.setContentHandle("project_categories_replatforming");
        Map<String, com.topcoder.clients.model.ContestType> contestTypes =
            new LinkedHashMap<String, com.topcoder.clients.model.ContestType>();

        ResultSetContainer container = dataAccess.getData(request)
                .get("project_categories_replatforming");
        int recordNum = container.size();
        for (int i = 0; i < recordNum; i++) {
            com.topcoder.clients.model.ContestType type = new com.topcoder.clients.model.ContestType();
            type.setTypeId(container.getIntItem(i, "project_category_id"));
            if (container.getIntItem(i, "project_type_id") == 3) {
                type.setStudio(true);
            } else {
                type.setStudio(false);
            }
            type.setDescription(container.getStringItem(i, "name"));

            contestTypes.put(Integer.toString(type.getTypeId()), type);
        }
        return contestTypes;
    }

    /**
     * <p>Gets the resource of specified role for specified user for specified contest.</p>
     * 
     * @param userId a <code>long</code> providing the ID of a user.
     * @param contest a <code>SoftwareCompetition</code> providing the details for contest. 
     * @param resourceRoleId a <code>long</code> providing the ID for desired resource role.
     * @return a <code>Resource</code> of specified role for existing user or <code>null</code> if such resource is not
     *         found.
     * @since 1.8.4
     */
    public static Resource getUserResourceByRole(long userId, SoftwareCompetition contest, long resourceRoleId) {
        Resource[] resources = contest.getResources();
        for (Resource resource : resources) {
            if (resource.getResourceRole().getId() == resourceRoleId) {
                String resourceUserId = resource.getProperty("External Reference ID");
                if (userId == Long.parseLong(resourceUserId)) {
                    return resource;
                }
            }
        }
        return null;
    }

    /**
     * <p>Checks if there is a review of specified type by specified reviewer for specified project and specified phase
     * type. This method is intended for phases which require just a single review by single resource (like Approval 
     * or Final Review).</p>
     *
     * @param projectServices    a <code>ProjectServices</code> providing interface to project services.
     * @param contest            a <code>SoftwareCompetition</code> providing the contest details.
     * @param phaseTypeId        a <code>long</code> providing the ID for phase type.
     * @param scorecardTypeId    a <code>long</code> providing the ID for scorecard type.
     * @param reviewerResourceId a <code>long</code> providing the ID for reviewer resource.
     * @return <code>true</code> if ; <code>false</code> otherwise.
     * @since 1.8.4
     */
    public static boolean hasReview(ProjectServices projectServices, SoftwareCompetition contest, long phaseTypeId, 
                                    long scorecardTypeId, long reviewerResourceId) {
        return hasReview(projectServices, contest, phaseTypeId, scorecardTypeId, reviewerResourceId, null);
    }

    /**
     * <p>Checks if there is a review of specified type by specified reviewer for specified project and specified phase
     * type. This method is intended for phases which require just a single review by single resource (like Approval
     * or Final Review).</p>
     *
     * @param projectServices    a <code>ProjectServices</code> providing interface to project services.
     * @param contest            a <code>SoftwareCompetition</code> providing the contest details.
     * @param phaseTypeId        a <code>long</code> providing the ID for phase type.
     * @param scorecardTypeId    a <code>long</code> providing the ID for scorecard type.
     * @param reviewerResourceId a <code>long</code> providing the ID for reviewer resource.
     * @param userReviews the list to store all the user reviews
     * @return <code>true</code> if ; <code>false</code> otherwise.
     * @since 1.9.0
     */
    public static boolean hasReview(ProjectServices projectServices, SoftwareCompetition contest, long phaseTypeId,
                                    long scorecardTypeId, long reviewerResourceId, List<Review> userReviews) {
        int phasesCnt = 0;

        Set<Phase> phases = contest.getProjectPhases().getPhases();
        for (Phase phase : phases) {
            if (phase.getPhaseType().getId() == phaseTypeId) {
                phasesCnt++;
            }
        }

        int reviewsCnt = 0;
        Review[] reviews = projectServices.getReviews(contest.getId(), scorecardTypeId);
        for (Review review : reviews) {

            long reviewResourceId = review.getAuthor();
            if (reviewResourceId == reviewerResourceId) {
                reviewsCnt++;
                if(userReviews != null) {
                    userReviews.add(review);
                }
            }
        }

        return (reviewsCnt == phasesCnt);
    }


    
    /**
     * <p>Gets the last closed final fix phase of a specified project.</p>
     * 
     * @param projectServices the project services instance.
     * @param projectId the id of the specified project.
     * @return the last closed final fix phase of the specified project.
     * @since 1.8.5
     */
    public static Phase getLastClosedFinalFixPhase(ProjectServices projectServices, long projectId) {
        List<Phase> finalFixPhases = projectServices.getPhasesByType(projectId, "Final Fix");
        Phase lastClosedFinalFixPhase = null;
        for (Phase phase : finalFixPhases) {
            if (phase.getPhaseStatus().getId() == PhaseStatus.CLOSED.getId()) {
                lastClosedFinalFixPhase = phase;
            }
        }
        return lastClosedFinalFixPhase;
    }
    
    /**
     * <p>Upload attachments from <code>SessionFileStore</code> to an issue.</p>
     * 
     * @param issueKey the issue key to upload
     * @param fileStore the instance of <code>SessionFileStore</code>
     * @param docIds the IDs of the attachments to upload
     * @throws Exception if any error occurs
     * @since 1.8.5
     */
    public static void addAttachmentsToIssue(String issueKey, SessionFileStore fileStore, String[] docIds)
        throws Exception {
        if (docIds.length > 0) {
            List<Long> docIds2 = new ArrayList<Long>();
            for (String id : docIds) {
                if (id.trim().length() > 0) {
                    docIds2.add(Long.parseLong(id));
                }
            }
            String[] fileNames = new String[docIds2.size()];
            String[] fileData = new String[docIds2.size()];
            for (int i = 0; i < docIds2.size(); i++) {
                CompUploadedFile file = fileStore.getFile(docIds2.get(i));
                fileNames[i] = file.getUploadedFileName();
                fileData[i] = Base64.encode(file.getFileData());
            }
            JiraRpcServiceWrapper.addAttachments(issueKey, fileNames, fileData);
        }
    }
    
    /**
     * <p>Upload final fix as attachment to an issue.</p>
     * 
     * @param issueKey the issue key to upload
     * @param upload the final fix
     * @param fileLocation the directory location where the final fix submission stored
     * @throws Exception if any error occurs
     */
    public static void addFinalFixToIssue(String issueKey, Upload upload, String fileLocation) throws Exception {
        int i = upload.getParameter().lastIndexOf('.');
        String fileName = "Final_Fix_" + upload.getProjectPhase();
        if (i > 0 && i < upload.getParameter().length() - 1) {
            fileName = fileName + upload.getParameter().substring(i);
        }
        FileInputStream ins = new FileInputStream(fileLocation + File.separator + upload.getParameter());
        String base64data;
        try {
            base64data = Base64.encode(IOUtils.toByteArray(ins));
        } finally {
            try {
                ins.close();
            } catch (IOException e) {
                
            }
        }
        
        JiraRpcServiceWrapper.addAttachments(issueKey, new String[] {fileName}, new String[] {base64data});
    }
    
    /**
     * <p>Gets the final fix upload associated with a specified final fix phase.</p>
     * 
     * @param projectId the if of the project which the final fix upload belongs to
     * @param phaseId the id of the specified final fix phase
     * @param facade the contest service facade
     * @return the final fix upload associated with the specified final fix phase
     * @throws UploadPersistenceException if any error occurs when retrieving data from database
     * @throws SearchBuilderException if any error occurs when searching the uploads
     * @since 1.8.5
     */
    public static Upload getFinalFixUpload(long projectId, long phaseId, ContestServiceFacade facade)
        throws UploadPersistenceException, SearchBuilderException {
        Upload[] uploads = facade.getActiveUploads(projectId, FINAL_FIX_UPLOAD_TYPE_ID);
        for (Upload upload : uploads) {
            if (upload.getProjectPhase() != null && upload.getProjectPhase().longValue() == phaseId) {
                return upload;
            }
        }
        return null;
    }

    /**
     * Gets the project brief DTO of the specified project id.
     *
     * @param projectServiceFacade the project service facade
     * @param directProjectId the id of the direct project.
     * @return the <code>ProjectBriefDTO</code> instance.
     * @throws Exception if any error occurs.
     * @since 1.8.7
     */
    public static ProjectBriefDTO getCurrentProjectBrief(ProjectServiceFacade projectServiceFacade, long directProjectId)
            throws Exception {
        TCSubject currentUser = getTCSubjectFromSession();
        final ProjectData project = projectServiceFacade.getProject(currentUser, directProjectId);

        return createProjectBriefDTOFromProjectData(project);
    }

    /**
     * Creates the <code>ProjectBriefDTO</code> from the <code>ProjectData</code> instance.
     *
     * @param project the <code>ProjectData</code> instance.
     * @return the created <code>ProjectBriefDTO</code> instance
     * @since 1.8.9
     */
    public static ProjectBriefDTO createProjectBriefDTOFromProjectData(ProjectData project) {
        ProjectBriefDTO projectBriefDTO = new ProjectBriefDTO();

        projectBriefDTO.setId(project.getProjectId());
        projectBriefDTO.setName(project.getName());
        projectBriefDTO.setProjectForumCategoryId(project.getForumCategoryId());

        return projectBriefDTO;
    }

    /**
     * <p>Checks if specified user is granted one of the specified permissions for accessing the specified TC Direct
     * project based on the security groups policies.</p>
     *
     * @param tcSubject              a <code>TCSubject</code> representing the desired user.
     * @param tcDirectProjectId      a <code>long</code> providing the ID of a TC Direct project to check permission
     *                               for.
     * @param authorizationService   an <code>AuthorizationService</code> providing interface to authorization service.
     * @param allowedPermissionTypes a list of permission types which should allow the user to access the project.
     * @return <code>true</code> if user is granted permission for accessing the project; <code>false</code> otherwise.
     * @throws PermissionServiceException if an unexpected error occurs.
     * @since 1.9.3
     */
    public static boolean hasPermissionBySecurityGroups(TCSubject tcSubject, long tcDirectProjectId,
                                                        AuthorizationService authorizationService,
                                                        GroupPermissionType... allowedPermissionTypes)
        throws PermissionServiceException {
        try {
            // Check if user is administrator for client account
            Long clientId = getClientIdForProject(tcSubject, tcDirectProjectId);
            long userId = tcSubject.getUserId();
            boolean isCustomerAdministrator = false;
            if (clientId != null) {
                isCustomerAdministrator = authorizationService.isCustomerAdministrator(userId, clientId);
            }
            if (isCustomerAdministrator) {
                return true;
            } else {
                // If not then check if user is granted desired permission to access the project based on 
                // security groups which user is member of
                GroupPermissionType groupPermissionType =
                    authorizationService.checkAuthorization(userId, tcDirectProjectId, ResourceType.PROJECT);
                if (groupPermissionType == null) {
                    return false;
                } else {
                    for (GroupPermissionType allowedPermissionType : allowedPermissionTypes) {
                        if (allowedPermissionType == groupPermissionType) {
                            return true;
                        }
                    }
                }
                return false;
            }
        } catch (Exception e) {
            throw new PermissionServiceException("Failed to authorize user against security groups", e);
        }
    }

    /**
     * <p>Checks whether the current user is allowed to access Security Groups UI or not.</p>
     *
     * @return <code>true</code> if current user can access Security Groups UI; <code>false</code> otherwise.
     * @throws UserServiceException if an unexpected error occurs.
     * @since 1.9.4
     */
    public static boolean isSecurityGroupsUIAvailable() throws UserServiceException {
        HttpServletRequest servletRequest = getServletRequest();
        ServletContext ctx = servletRequest.getSession().getServletContext();
        WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(ctx);
        SecurityGroupsAccessInterceptor securityGroupsAccessInterceptor
            = (SecurityGroupsAccessInterceptor) applicationContext.getBean("securityGroupsAccessInterceptor");
        return securityGroupsAccessInterceptor.isSecurityGroupsUIAvailable();
    }

    /**
     * Helper method to convert the key of Map<Long, String> to String, returns a Map<String, String>.
     *
     * @param toConvert the map to convert.
     * @return the converted Map<String, String> instance.
     * @since 1.9.6
     */
    public static Map<String, String> convertMapKeyToString(Map<Long, String> toConvert) {
        Map<String, String> result = new HashMap<String, String>();
        for(Map.Entry<Long, String> e : toConvert.entrySet()) {
            result.put(String.valueOf(e.getKey()), e.getValue());
        }
        return  result;
    }

    /**
     * Gets the client id of the given user id. If the user is not a client, 0 is returned.
     *
     * @param userId the id of the user to check.
     * @return the client id of the given user, if the user is not a client, 0 is returned.
     * @throws Exception if there is any error.
     * @since 1.9.8
     */
    public static long getUserClientId(long userId) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseUtils.getDatabaseConnection(TIME_OLTP_DATABASE);

            statement = connection.prepareStatement(IS_CLIENT_USER_SQL);

            statement.setLong(1, userId);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getLong("client_id");
            } else {
                return 0;
            }
        } finally {
            DatabaseUtils.close(resultSet);
            DatabaseUtils.close(statement);
            DatabaseUtils.close(connection);
        }
    }

    /**
     * Checks whether the user is a client user.
     *
     * @param userId the user id ot check.
     * @return whether the user is a client user.
     * @throws Exception if any error.
     * @since 1.9.8
     */
    public static boolean isClientUser(long userId) throws Exception {
        return getUserClientId(userId) > 0;
    }

    /**
     * create file using the input InputStream object, FileLock is used to ensure the file is only created once. 
     * 
     * @param is the InputStream object
     * @param absoluteFileName the absolute file name
     * @throws IOException if IOException occurs.
     * @since 1.9.6
     */
    public static void createFile(InputStream is, String absoluteFileName) throws IOException {
        File f = new File(absoluteFileName);

        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }

        FileOutputStream out = new FileOutputStream(absoluteFileName);
        FileLock lock = out.getChannel().lock();

        try {
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = is.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } finally {
            if (lock != null) {
                lock.release();
            }
            out.close();
        }
    }
    
    /**
     * store the zip inputstream into the specified directory.
     * 
     * @param inputStream the InputStream object
     * @param dir the destination directory
     * @return number of files Extracted
     * @throws IOException if IOException occurs.
     * @since 1.9.6
     */
    public static int storeZipStream(InputStream inputStream, String dir) throws IOException {

        ZipInputStream zis = new ZipInputStream(inputStream);
        try {
            ZipEntry entry = null;
            int countEntry = 0;
            if (!dir.endsWith(File.separator)){
                dir += File.separator;
            }
                
            // check inputStream is ZIP or not
            if ((entry = zis.getNextEntry()) != null) {

                do {
                    String entryName = entry.getName();
                    String firstDir = entryName.split("/")[0];
                    entryName = firstDir.toLowerCase() + entryName.substring(firstDir.length());
                    // Directory Entry should end with FileSeparator
                    if (!entry.isDirectory()) {
                        // Directory will be created while creating file with in it.
                        String fileName = dir + entryName;
                        createFile(zis, fileName);
                        countEntry++;
                    }
                } while ((entry = zis.getNextEntry()) != null);

                return countEntry;
            } else {
                throw new IOException("Given file is not a Compressed one");
            }
        } finally {
            try {
                zis.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }
}
