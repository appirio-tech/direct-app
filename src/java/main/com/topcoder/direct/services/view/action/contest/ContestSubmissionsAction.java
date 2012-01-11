/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.topcoder.direct.services.view.action.contest.launch.StudioOrSoftwareContestAction;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import com.topcoder.direct.services.exception.DirectException;
import com.topcoder.direct.services.view.action.contest.launch.ContestAction;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.contest.ContestRoundType;
import com.topcoder.direct.services.view.dto.contest.ContestStatsDTO;
import com.topcoder.direct.services.view.dto.contest.StudioContestSubmissionsDTO;
import com.topcoder.direct.services.view.dto.contest.SubmissionViewerType;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.form.StudioContestSubmissionsForm;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.resource.Resource;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.project.SoftwareCompetition;

/**
 * <p>A <code>Struts</code> action to be used for handling requests for viewing a list of submissions for
 * <code>Studio</code> contest.</p>
 *
 * <p>
 * Version 1.1 (Direct Submission Viewer Release 2) change notes:
 * <ul>
 * <li>
 * Update {@link #executeAction()} method to set prize number to the view data.
 * </li>
 * </ul>
 * </p>
 *
 * <p>
 *   Version 1.2 (Direct Submission Viewer Release 4) change notes:
 *   <ul>
 *     <li>Updated {@link #executeAction()} method to set hasCheckout flag to the view data.</li>
 *     <li>Updated {@link #executeAction()} and {@link #execute()} methods to redirect request to <code>Milestone</code>
 *     submissions view in case contest has milestone round which is not confirmed by client yet.</li>
 *   </ul>   
 * </p>
 * 
 * <p>
 *   Version 1.3 (TC Direct Release Assembly 7) change notes:
 *   <ul>
 *     <li>Updated {@link #executeAction()} method to set hasContestWritePermission flag to the view data.</li>
 *   </ul>
 * </p>
 * 
 * <p>
 *   Version 1.4 (TC Direct Replatforming Release 3) change notes:
 *   <ul>
 *     <li>The {@link #executeAction()} method was totally updated to work for the new studio contest.</li>
 *   </ul>
 * </p>
 * 
 * <p>
 *   Version 1.5 (TC Direct Contest Dashboard Update Assembly) change notes:
 *   <ul>
 *     <li>Updated {@link #executeAction()} method to set contest dashboard data.</li>
 *   </ul>
 * </p>
 *
 * <p>
 *   Version 1.6 (TC Direct Replatforming Release 5) change notes:
 *   <ul>
 *     <li>Updated {@link #executeAction()} method to load the <code>Resource</code> data associated with the submissions.</li>
 *   </ul>
 * </p>
 * 
 * @author isv, flexme
 * @since Submission Viewer Release 1 assembly
 * @version 1.6
 */
public class ContestSubmissionsAction extends StudioOrSoftwareContestAction {

    /**
     * <p>A <code>StudioContestSubmissionsForm</code> providing the parameters of the incoming request.</p>
     */
    private StudioContestSubmissionsForm formData;

    /**
     * <p>A <code>SessionData</code> providing interface to current session.</p>
     */
    private SessionData sessionData;

    /**
     * <p>A <code>StudioContestSubmissionsDTO</code> providing the view data for displaying by <code>Studio Contest
     * Submissions</code> view.</p>
     */
    private StudioContestSubmissionsDTO viewData;

    /**
     * <p>A <code>boolean</code> flag indicating whether the request is to be redirected to <code>Milestone</code>
     * submissions view.</p>
     */
    private boolean redirectToMilestone;

    /**
     * <p>Constructs new <code>ContestSubmissionsAction</code> instance. This implementation does nothing.</p>
     */
    public ContestSubmissionsAction() {
        this.viewData = new StudioContestSubmissionsDTO();
        this.formData = new StudioContestSubmissionsForm(this.viewData);
    }

    /**
     * <p>Gets the form data.</p>
     *
     * @return an <code>StudioContestSubmissionsForm</code> providing the data for form submitted by user..
     */
    public StudioContestSubmissionsForm getFormData() {
        return this.formData;
    }

    /**
     * <p>Gets the current session associated with the incoming request from client.</p>
     *
     * @return a <code>SessionData</code> providing access to current session.
     */
    public SessionData getSessionData() {
        return this.sessionData;
    }

    /**
     * <p>Gets the data to be displayed by view mapped to this action.</p>
     *
     * @return a <code>StudioContestSubmissionsDTO</code> providing the collector for data to be rendered by the view
     *         mapped to this action.
     */
    public StudioContestSubmissionsDTO getViewData() {
        return this.viewData;
    }

    /**
     * <p>Handles the incoming request. If action is executed successfully then changes the current project context to
     * project for contest requested for this action.</p>
     *
     * @return <code>SUCCESS</code> if request is to be forwarded to respective view or <code>download</code> if
     *         response is to be written to client directly.
     */
    @Override
    public String execute() throws Exception {
        String result = super.execute();
        if (SUCCESS.equals(result)) {
            if (this.redirectToMilestone) {
                return "milestone";
            } else {
                SubmissionViewerType submissionViewerType = getFormData().getViewType();
                if (submissionViewerType == SubmissionViewerType.GRID) {
                    return "grid";
                } else if (submissionViewerType == SubmissionViewerType.LIST) {
                    return "list";
                }
            }
        }
        return result;
    }

    /**
     * <p>Handles the incoming request. Retrieves the list of submissions for requested contest and binds it to view
     * data along with other necessary details.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    public void executeAction() throws Exception {
        getFormData().setContestId(getProjectId());
        long projectId = getProjectId();
        if (projectId <= 0) {
            throw new DirectException("projectId less than 0 or not defined.");
        }
        
        ContestServiceFacade contestServiceFacade = getContestServiceFacade();
        TCSubject currentUser = DirectStrutsActionsHelper.getTCSubjectFromSession();
        SoftwareCompetition softwareCompetition = contestServiceFacade.getSoftwareContestByProjectId(currentUser, projectId);
        
        // only works for studio contest
        if (DirectUtils.isStudio(softwareCompetition)) {
            // Get current session
            HttpServletRequest request = DirectUtils.getServletRequest();
            this.sessionData = new SessionData(request.getSession());
            
            boolean hasMilestoneRound = DirectUtils.isMultiRound(softwareCompetition);
            getViewData().setHasMilestoneRound(hasMilestoneRound);
            
            // if Final round was requested and if milestone round is present then check if milestone round
            // if confirmed. If Milestone round is not confirmed yet then cause the request to be redirected to
            // Milestone submissions view
            ContestRoundType roundType = getFormData().getRoundType();
            if (hasMilestoneRound) {
                if (roundType == ContestRoundType.FINAL) {
                    // if the milestone is not confirmed
                    boolean isMilestoneRoundConfirmed = DirectUtils.getContestCheckout(softwareCompetition, ContestRoundType.MILESTONE);
                    if (!isMilestoneRoundConfirmed) {
                        this.redirectToMilestone = true;
                        return;
                    }
                }
            }
            
            PhaseType reviewPhaseType;
            if (roundType == ContestRoundType.FINAL) {
                reviewPhaseType = PhaseType.REVIEW_PHASE;
            } else {
                reviewPhaseType = PhaseType.MILESTONE_REVIEW_PHASE;
            }
            // set submission data
            List<Submission> submissions = DirectUtils.getStudioContestSubmissions(projectId, roundType, currentUser, contestServiceFacade);
            if (DirectUtils.isPhaseScheduled(softwareCompetition, reviewPhaseType)) {
                viewData.setPhaseOpen(false);
            } else {
                viewData.setPhaseOpen(true);
            }
            
            boolean hasCheckout = DirectUtils.getContestCheckout(softwareCompetition, roundType);
            getViewData().setHasCheckout(hasCheckout);
            if (hasCheckout && viewData.isPhaseOpen()) {
                Collections.sort(submissions, new SubmissionsRankComparator());
                getViewData().setContestSubmissions(submissions);
            } else {
                getViewData().setContestSubmissions(submissions);
            }
            
            // If contest submissions are confirmed then set the request cookie for contest with the details on
            // prized submissions
            if (hasCheckout && viewData.isPhaseOpen()) {
                // Collect resources for submissions
                Map<Long, Resource> submissionResources = new HashMap<Long, Resource>();
                Map<Long, Resource> resources = new HashMap<Long, Resource>();
                for (Resource resource : softwareCompetition.getResources()) {
                    resources.put(resource.getId(), resource);
                }
                for (Submission submission : submissions) {
                    submissionResources.put(submission.getId(), resources.get(submission.getUpload().getOwner()));
                }
                getViewData().setSubmissionResources(submissionResources);
                
                String contestIdString = String.valueOf(projectId);
                JSONObject bankSelectionJSON = null;
                JSONObject contestSelectionJSON = null;
                JSONObject contestRoundSelectionJSON = null;
                
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if ("bankSelection".equals(cookie.getName())) {
                            bankSelectionJSON = toJSON(cookie);
                            if (bankSelectionJSON.has(contestIdString)) {
                                contestSelectionJSON = bankSelectionJSON.getJSONObject(contestIdString);
                                contestRoundSelectionJSON = contestSelectionJSON.getJSONObject(roundType.toString());
                                bankSelectionJSON.remove(contestIdString);
                            }
                            cookie.setMaxAge(0);
                            break;
                        }
                    }
                }

                if (bankSelectionJSON == null) {
                    bankSelectionJSON = new JSONObject();
                }
                if (contestSelectionJSON == null) {
                    contestSelectionJSON = new JSONObject();
                }
                if (contestRoundSelectionJSON == null) {
                    contestRoundSelectionJSON = new JSONObject();
                }

                // Remove prized submissions as provided by request cookie
                String[] prizeSlotNames = {"firstPrize", "secondPrize", "thirdPrize", "fourthPrize", "fifthPrize", "sixthPrize", "seventhPrize", "eighthPrize", "ninthPrize", "tenthPrize"};
                if (!contestRoundSelectionJSON.isNullObject()) {
                    for (String prizeSlotName : prizeSlotNames) {
                        if (contestRoundSelectionJSON.has(prizeSlotName)) {
                            contestRoundSelectionJSON.remove(prizeSlotName);
                        }
                    }
                } else {
                    contestRoundSelectionJSON = (JSONObject) JSONSerializer.toJSON("{}");
                }

                // Set real prized submissions as is for confirmed contest
                int prizeIndex = 0;
                for (Submission submission : submissions) {
                    if (!submission.isExtra() && submission.getFinalScore() != null && submission.getFinalScore() > 10.0) {
                        // milestone prize
                        contestRoundSelectionJSON.put(prizeSlotNames[prizeIndex++], 
                                String.valueOf(submission.getId()));
                    }
                }

                // Remove real prized submissions from list of liked, disliked, additional purchased submissions
                // as provided by request cookie
                removeSubmissionFromList(contestRoundSelectionJSON, "listLikes");
                removeSubmissionFromList(contestRoundSelectionJSON, "listDislikes");
                removeSubmissionFromList(contestRoundSelectionJSON, "listExtra");

                // set extra purchases
                JSONArray listExtra = new JSONArray();
                for (Submission submission : submissions) {
                    if (submission.isExtra()) {
                        listExtra.add(submission.getId());
                    }
                }
                contestRoundSelectionJSON.put("listExtra", listExtra);
                
                contestSelectionJSON.put(roundType.toString(), contestRoundSelectionJSON);
                bankSelectionJSON.put(contestIdString, contestSelectionJSON);

                HttpServletResponse response = DirectUtils.getServletResponse();
                response.addCookie(toBankSelectionCookie(bankSelectionJSON));
            }
            
            // For normal request flow prepare various data to be displayed to user

            // Set contest stats
            //ContestStatsDTO contestStats = DirectUtils.getContestStats(contestServiceFacade, currentUser, contestId);
            ContestStatsDTO contestStats = DirectUtils.getContestStats(currentUser, projectId);
            getViewData().setContestStats(contestStats);

            // set the number of prizes
            int prizeNumber = DirectUtils.getContestPrizeNumber(softwareCompetition, roundType);
            getViewData().setPrizeNumber(prizeNumber);

            // Set projects data
            List<ProjectBriefDTO> projects = DataProvider.getUserProjects(currentUser.getUserId());
            UserProjectsDTO userProjectsDTO = new UserProjectsDTO();
            userProjectsDTO.setProjects(projects);
            getViewData().setUserProjects(userProjectsDTO);

            // Set current project contests
            List<TypedContestBriefDTO> contests = DataProvider.getProjectTypedContests(
                    currentUser.getUserId(), contestStats.getContest().getProject().getId());
            getSessionData().setCurrentProjectContests(contests);

            // Set current project context based on selected contest
            getSessionData().setCurrentProjectContext(contestStats.getContest().getProject());
            getSessionData().setCurrentSelectDirectProjectID(contestStats.getContest().getProject().getId());
            
            // set contest permission
            viewData.setHasContestWritePermission(DirectUtils
                    .hasWritePermission(this, currentUser, projectId, false));
            
            DirectUtils.setDashboardData(currentUser, projectId, viewData, getContestServiceFacade(), false);
        }
    }

    /**
     * <p>Removes prized submissions from the specified list of user's bank selection.</p>
     *
     * @param contestRoundSelectionJSON a <code>JSONObject</code> providing the data for contest round selection.
     * @param listName a <code>String</code> providing the name of the list to be processed.
     */
    private void removeSubmissionFromList(JSONObject contestRoundSelectionJSON, String listName) {
        if (contestRoundSelectionJSON.has(listName)) {
            JSONArray list = contestRoundSelectionJSON.getJSONArray(listName);
            list.clear();
        }
    }

    /**
     * <p>Converts specified <code>JSON</code> presentation to cookie.</p>
     *
     * @param banksSelectionJSON a <code>JSONObject</code> providing the cookie value.
     * @return a <code>Cookie</code> constructed from the value provided by JSON object.
     * @throws UnsupportedEncodingException if an unexpected error occurs.
     */
    private Cookie toBankSelectionCookie(JSONObject banksSelectionJSON) throws UnsupportedEncodingException {
        String jsonString = banksSelectionJSON.toString();
        Cookie cookie = new Cookie("bankSelection", URLEncoder.encode(jsonString, "UTF-8"));
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 3600);
        return cookie;
    }

    /**
     * <p>Converts specified cookie to <code>JSON</code> presentation.</p>
     *
     * @param cookie a <code>Cookie</code> providing the cookie value.
     * @return a <code>JSONObject</code> constructed from the value provided by cookie.
     * @throws UnsupportedEncodingException if an unexpected error occurs.
     */
    private JSONObject toJSON(Cookie cookie) throws UnsupportedEncodingException {
        String value = cookie.getValue();
        return (JSONObject) JSONSerializer.toJSON(URLDecoder.decode(value, "UTF-8"));
    }

    /**
     * <p>Comparator for submissions based on ranks assigned by user.</p>
     *
     * <p>Change notes:</p>
     * <p>
     *  Version 1.1: check the argument null in method compare.
     * </p>
     * 
     * @author isv, minhu
     * @version 1.1
     */
    private static class SubmissionsRankComparator implements Comparator<Submission> {

        /**
         * <p>Constructs new <code>ContestSubmissionsAction$SubmissionsRankComparator</code> instance. This
         * implementation does nothing.</p>
         */
        private SubmissionsRankComparator() {
        }

        /**
         * <p>Compares two submissions based on rank assigned by user. If both submissions do not have ranks then they
         * are "equal"; if one of submissions has no rank then it's "greater" than the opponent.</p>
         *
         * @param s1 a <code>SubmissionData</code> to compare.
         * @param s2 a <code>SubmissionData</code> to compare.
         */
        public int compare(Submission s1, Submission s2) {
            if (s2.getPlacement() == null) {
                return 1;
            }
            if (s1.getPlacement() == null) {
                return -1;
            }
            long rank1 = s1.getPlacement();
            long rank2 = s2.getPlacement();

            if (rank1 > 0) {
                if (rank2 <= 0) {
                    return -1;
                } else {
                    return (int) (rank1 - rank2);
                }
            } else {
                if (rank2 > 0) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }
}
