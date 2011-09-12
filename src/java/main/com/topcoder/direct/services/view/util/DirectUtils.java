/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.topcoder.clients.model.Project;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.direct.services.view.action.specreview.ViewSpecificationReviewActionResultData;
import com.topcoder.direct.services.view.dto.contest.BaseContestCommonDTO;
import com.topcoder.direct.services.view.dto.contest.ContestDashboardDTO;
import com.topcoder.direct.services.view.dto.contest.ContestRoundType;
import com.topcoder.direct.services.view.dto.contest.PhasedContestDTO;
import com.topcoder.direct.services.view.dto.contest.ProjectPhaseDTO;
import com.topcoder.direct.services.view.dto.contest.ProjectPhaseStatus;
import com.topcoder.direct.services.view.dto.contest.ProjectPhaseType;
import com.topcoder.service.permission.PermissionServiceException;
import com.topcoder.service.project.CompetitionPrize;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.studio.SubmissionData;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.topcoder.direct.services.view.dto.contest.*;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.persistence.UploadPersistenceException;
import com.topcoder.management.project.Prize;
import com.topcoder.management.project.ProjectType;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.service.ContestSaleData;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.security.RolePrincipal;
import com.topcoder.security.TCPrincipal;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceException;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.service.project.StudioCompetition;
import com.topcoder.service.studio.PersistenceException;
import com.topcoder.shared.common.TCContext;
import com.topcoder.shared.dataAccess.DataAccess;
import com.topcoder.shared.dataAccess.Request;
import com.topcoder.shared.dataAccess.resultSet.ResultSetContainer;
import com.topcoder.shared.util.DBMS;

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
 * </p
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
 * @author BeBetter, isv, flexme, Blues, Veve, TCSDEVELOPER
 * @version 1.7.4
 */
public final class DirectUtils {
    /**
     * Constant for date format.
     */
    public static final String DATE_FORMAT = "MM/dd/yyyy";

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
     * </p>
     *
     * @param currentUser a <code>TCSubject</code> representing the current user.
     * @param contestId a <code>long</code> providing the ID of a contest.
     * @param isStudio a flag indicates whether the contest to get is a studio contest.
     * @return a <code>ContestStatsDTO</code> providing the statistics for specified contest.
     * @throws Exception if an unexpected error occurs while accessing the persistent data store.
     * @since 1.1
     */
    public static ContestStatsDTO getContestStats(TCSubject currentUser, long contestId)
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

        ContestStatsDTO dto = new ContestStatsDTO();
        dto.setCurrentStatus(resultContainer.getStringItem(recordIndex, "status"));
        dto.setEndTime(resultContainer.getTimestampItem(recordIndex, "end_date"));
        dto.setStartTime(resultContainer.getTimestampItem(recordIndex, "start_date"));
        dto.setSubmissionsNumber(resultContainer.getIntItem(recordIndex, "number_of_submission"));
        dto.setRegistrantsNumber(resultContainer.getIntItem(recordIndex, "number_of_registration"));
        dto.setForumPostsNumber(resultContainer.getIntItem(recordIndex, "number_of_forum"));
        dto.setSvn(resultContainer.getStringItem(recordIndex, "svn"));
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
                    new Date(getRegistrationPhase(softwareCompetition).getFixedStartDate().getTime() + phase.getLength());
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
                    new Date(getRegistrationPhase(softwareCompetition).getFixedStartDate().getTime() + phase.getLength());
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
     * Sets the direct project name of the given StudioCompetition
     *
     * @param studioCompetition the studio competition to set.
     * @param projects all the direct projects the user has.
     * @since 1.6.7
     */
    public static void setStudioCompetitionDirectProjectName(StudioCompetition studioCompetition, List<ProjectData> projects) {
        studioCompetition.getContestData().setTcDirectProjectName(getDirectProjectName(studioCompetition.getContestData().getTcDirectProjectId(), projects));
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
     * <p>Gets a flag indicating whether the submissions have already been checked out.</p>
     *
     * @param submissions the submissions to check.
     * @param roundType a <code>ContestRoundType</code> providing the type of the contest round.
     * @return a flag indicating whether the submissions have already been checked out.
     * @since 1.6
     */
    public static boolean getSubmissionsCheckout(List<SubmissionData> submissions, ContestRoundType roundType) {
        for (SubmissionData submission : submissions) {
            if (roundType == ContestRoundType.MILESTONE) {
                if (submission.isAwardMilestonePrize() != null && submission.isAwardMilestonePrize()) {
                    return true;
                }
            } else {
                if (submission.getUserRank() > 0) {
                    return true;
                }
            }
        }
        return false;
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
                    return !(phase.getPhaseStatus().getId() == PhaseStatus.OPEN.getId());
                }
            } else if (roundType == ContestRoundType.FINAL) {
                if (phase.getPhaseType().getId() == PhaseType.REVIEW_PHASE.getId()) {
                    return !(phase.getPhaseStatus().getId() == PhaseStatus.OPEN.getId());
                }
            }
        }
        return false;
    }

    /**
     * <p>Gets the additional prize for a studio competition.</p>
     *
     * @param studioCompetition the studio competition
     * @return the additional prize for the studio competition
     * @since 1.6
     */
    public static double getAdditionalPrize(StudioCompetition studioCompetition) {
        List<CompetitionPrize> prizes = studioCompetition.getPrizes();
        if (prizes.size() == 0) {
            return 0.0;
        }
        double prize = prizes.get(0).getAmount();
        for (int i = 1; i < prizes.size(); i++) {
            prize = Math.min(prize, prizes.get(i).getAmount());
        }
        return prize;
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
     * Check whether user has permission to a specify contest.
     *
     * @param action
     *            the action
     * @param tcSubject
     *            the user to check
     * @param contestId
     *            the contest id
     * @param isStudio
     *            whether is studio contest
     * @return has permission or not
     * @throws PersistenceException
     * @since 1.6.2
     */
    public static boolean hasWritePermission(BaseDirectStrutsAction action,
            TCSubject tcSubject, long contestId, boolean isStudio)
            throws PersistenceException {
        if (!isRole(tcSubject, ADMIN_ROLE)) {
            if (isStudio) {
                return action.getStudioService().checkContestPermission(
                        contestId, false, tcSubject.getUserId());
            } else {
                return action.getProjectServices().checkContestPermission(
                        contestId, false, tcSubject.getUserId());
            }
        } else {
            return true;
        }

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
     * @param tcSubject the tcSubject
     * @return the mapping of client, billing and projects
     * @throws Exception if any error occurs.
     * @since 1.6.4
     */
    private static Map<String, Object> getDashboardClientBillingProjectMappings(TCSubject tcSubject) throws Exception {
        Map<String, Object> result;
        HttpServletRequest request = DirectUtils.getServletRequest();
        Object value = request.getSession().getAttribute("clientBillingProjectMappings");

        if (value == null) {
            result = DataProvider.getDashboardClientBillingProjectMappings(tcSubject);
            request.getSession().setAttribute("clientBillingProjectMappings", result);
        } else {
            result = (Map<String, Object>) value;
        }

        return result;
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
     * Gets all the clients of current user.
     *
     * @param tcSubject the tcSubject instance.
     * @return all the clients.
     * @throws Exception if any error occurs.
     * @since 1.6.4
     */
    public static Map<Long, String> getAllClients(TCSubject tcSubject) throws Exception {
        return  sortByValue((Map<Long, String>) getDashboardClientBillingProjectMappings(tcSubject).get("clients"));
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
            dto.setContestStats(DirectUtils.getContestStats(currentUser, contestId));
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
            dto.setContestStats(DirectUtils.getContestStats(currentUser, contestId));
        }
        dto.setDashboard(DataProvider.getContestDashboardData(contestId, !software, false));
        
        dto.setDashboard(adjustPhases(dto.getDashboard()));
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
}
