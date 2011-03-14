/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.transaction.UserTransaction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.contest.ContestRoundType;
import com.topcoder.direct.services.view.dto.contest.PhasedContestDTO;
import com.topcoder.direct.services.view.dto.dashboard.DashboardContestSearchResultDTO;
import com.topcoder.service.permission.PermissionServiceException;
import com.topcoder.service.project.CompetitionPrize;
import com.topcoder.service.studio.SubmissionData;
import org.apache.struts2.ServletActionContext;
import com.topcoder.direct.services.view.dto.contest.ContestStatus;

import com.opensymphony.xwork2.ActionContext;
import com.topcoder.direct.services.view.dto.contest.ContestBriefDTO;
import com.topcoder.direct.services.view.dto.contest.ContestStatsDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.service.ContestSaleData;
import com.topcoder.security.RolePrincipal;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.CommonProjectContestData;
import com.topcoder.service.facade.contest.ContestServiceException;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.service.project.StudioCompetition;
import com.topcoder.service.studio.PersistenceException;
import com.topcoder.service.studio.SubmissionData;
import com.topcoder.service.studio.ContestData;
import com.topcoder.service.studio.ContestNotFoundException;
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
 * <li>Add {@link #getContestPrizeNumber(StudioCompetition, ContestRoundType)} method
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
 *
 * @author BeBetter, isv, flexme, TCSDEVELOPER
 * @version 1.6.3
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

    private static final String TC_OPERATIONS_ROLE = "TC Operations";

    /**
     * <p>
     * Default Constructor.
     * </p>
     */
    private DirectUtils() {

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
     * @param currentUser a <code>TCSubject</code> representing the current user.
     * @param contestId a <code>long</code> providing the ID of a contest.
     * @param isStudio a flag indicates whether the contest to get is a studio contest.
     * @return a <code>ContestStatsDTO</code> providing the statistics for specified contest.
     * @throws Exception if an unexpected error occurs while accessing the persistent data store.
     * @since 1.1
     */
    public static ContestStatsDTO getContestStats(TCSubject currentUser, long contestId, boolean isStudio)
        throws Exception {

        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("direct_contest_stats");
        request.setProperty("ct", String.valueOf(contestId));
        request.setProperty("uid", String.valueOf(currentUser.getUserId()));

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("direct_contest_stats");
        final int recordNum = resultContainer.size();

        if (recordNum == 0) {
            // return null, if no record is found
            return null;
        }

        int recordIndex = 0;

        String contestType = null;
        if (recordNum > 1) {
            // there are two records, find out the correct one
            String[] types = new String[2];
            types[0] = resultContainer.getStringItem(0, "type").trim();
            types[1] = resultContainer.getStringItem(1, "type").trim();

            if (isStudio) {
                recordIndex = types[0].equals("Studio") ? 0 : 1;
            } else {
                recordIndex = types[0].equals("Studio") ? 1 : 0;
            }

        } else if (recordNum == 1) {

            // get the contest type first
            contestType = resultContainer.getStringItem(0, "type").trim();

            if (isStudio && (!contestType.equals("Studio"))) {
                // contest type is not studio when param indicates the studio, return null
                return null;
            }

            if (!isStudio && contestType.equals("Studio")) {
                // contest type is studio when param indicate sw, return null
                return null;
            }
        }

        ProjectBriefDTO project = new ProjectBriefDTO();
        project.setId(resultContainer.getLongItem(recordIndex, "project_id"));
        project.setName(resultContainer.getStringItem(recordIndex, "project_name"));

        ContestBriefDTO contest;
        if (isStudio) {
            contest = new ContestBriefDTO();
        } else {
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
        }
        contest.setId(resultContainer.getLongItem(recordIndex, "contest_id"));
        contest.setTitle(resultContainer.getStringItem(recordIndex, "contest_name"));
        contest.setProject(project);
        contest.setSoftware(!isStudio);

        ContestStatsDTO dto = new ContestStatsDTO();
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
     * @return true if any phase is open; false if none is open
     */
    public static boolean isPhaseOpen(SoftwareCompetition softwareCompetition) {
        if (softwareCompetition == null || softwareCompetition.getProjectPhases() == null) {
            return false;
        }

        for (Phase phase : softwareCompetition.getProjectPhases().getPhases()) {
            if (PhaseStatus.OPEN.getId() == phase.getPhaseStatus().getId() 
                && !SPECIFICATION_SUBMISSION.equals(phase.getPhaseType().getName())
                && !SPECIFICATION_REVIEW.equals(phase.getPhaseType().getName())) {
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
     * @param contestId a <code>long</code> providing the ID of a contest.
     * @param roundType a <code>ContestRoundType</code> providing the type of the contest round.
     * @param currentUser a <code>TCSubject</code> representing the current user.
     * @param contestServiceFacade a <code>ContestServiceFacade</code> to be used for accessing the service layer for
     *        data retrieval.
     * @return a <code>List</code> listing the submissions for specified round of specified <code>Studio</code> contest.
     * @throws PermissionServiceException if an unexpected error occurs.
     * @throws ContestServiceException if an unexpected error occurs.
     * @since 1.4
     */
    public static List<SubmissionData> getStudioContestSubmissions(ContestData contest, ContestRoundType roundType,
                                                                   TCSubject currentUser,
                                                                   ContestServiceFacade contestServiceFacade)
        throws PermissionServiceException, ContestServiceException,  PersistenceException, ContestNotFoundException  {

        List<SubmissionData> submissions = contestServiceFacade.retrieveSubmissionsForContest(currentUser, contest.getContestId());

        if (contest.getMultiRound()) {

            Date milestoneDate = getDate(contest.getMultiRoundData().getMilestoneDate());
            List<SubmissionData> filteredSubmissions = new ArrayList<SubmissionData>();

            for (SubmissionData sub : submissions)
            {
                Date subDate = getDate(sub.getSubmittedDate());

                if (roundType == ContestRoundType.MILESTONE) {
                    if (subDate.before(milestoneDate))
                    {
                        filteredSubmissions.add(sub);
                    }
                } else {
                    if (subDate.after(milestoneDate))
                    {
                        filteredSubmissions.add(sub);
                    }

                }
            }

            return filteredSubmissions;
             
        } else {
                return submissions;
        }
      
    }

    /**
     * <p>Gets the number of prizes for specified round of specified <code>Studio</code> contest.</p>
     *
     * @param studioCompetition a <code>StudioCompetition</code> providing the studio contest.
     * @param roundType a <code>ContestRoundType</code> providing the type of the contest round.
     * @return an <code>int</code> providing the number of the prize for specified round of specified <code>Studio</code> contest.
     * @since 1.5
     */
    public static int getContestPrizeNumber(StudioCompetition studioCompetition, ContestRoundType roundType) {
        if (roundType == ContestRoundType.MILESTONE) {
            return studioCompetition.getContestData().getMilestonePrizeData().getNumberOfSubmissions();
        } else {
            return studioCompetition.getPrizes().size();
        }
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
     * Checks if the login user is of given role
     * </p>
     * 
     * @param tcSubject
     *            TCSubject instance for login user
     * @return true if it is given role
     * @since 1.6.2
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
}
