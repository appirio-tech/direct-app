/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest;

import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.direct.services.view.action.contest.launch.StudioOrSoftwareContestAction;
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
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.project.StudioCompetition;
import com.topcoder.service.studio.SubmissionData;
import com.topcoder.service.user.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
 *   Version 1.4 (TC Direct Contest Dashboard Update Assembly) change notes:
 *   <ul>
 *     <li>Updated {@link #executeAction()} method to set contest dashboard data.</li>
 *   </ul>
 * </p>
 *
 * @author isv, flexme, TCSDEVELOPER, TCSASSEMBLER
 * @since Submission Viewer Release 1 assembly
 * @version 1.4
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
        getFormData().setContestId(getContestId());
        if (isStudioCompetition()) {
            // Get current session
            HttpServletRequest request = DirectUtils.getServletRequest();
            this.sessionData = new SessionData(request.getSession());

            ContestServiceFacade contestServiceFacade = getContestServiceFacade();
            TCSubject currentUser = DirectStrutsActionsHelper.getTCSubjectFromSession();

            // Set flag indicating on milestone round presence
            long contestId = getContestId();
            StudioCompetition studioCompetition = contestServiceFacade.getContest(currentUser, contestId);
            getViewData().setHasMilestoneRound(studioCompetition.getContestData().getMultiRound());

            // if Final round was requested and if milestone round is present then check if milestone round
            // if confirmed. If Milestone round is not confirmed yet then cause the request to be redirected to
            // Milestone submissions view
            ContestRoundType roundType = getFormData().getRoundType();
            if (getViewData().getHasMilestoneRound()) {
                if (roundType == ContestRoundType.FINAL) {
                    List<SubmissionData> milestoneSubmissions
                        = DirectUtils.getStudioContestSubmissions(studioCompetition.getContestData(),
                                                                  ContestRoundType.MILESTONE, currentUser,
                                                                  contestServiceFacade);
                    boolean isMilestoneRoundConfirmed =
                        DirectUtils.getSubmissionsCheckout(milestoneSubmissions, ContestRoundType.MILESTONE);
                    if (!isMilestoneRoundConfirmed) {
                        this.redirectToMilestone = true;
                        return;
                    }
                }
            }

            // Set submissions data
            List<SubmissionData> submissions
                = DirectUtils.getStudioContestSubmissions(studioCompetition.getContestData(), roundType, currentUser,
                                                          contestServiceFacade);
            getViewData().setHasCheckout(DirectUtils.getSubmissionsCheckout(submissions, roundType));
            if (getViewData().getHasCheckout()) {
                Collections.sort(submissions, new SubmissionsRankComparator());
                getViewData().setContestSubmissions(submissions);
            } else {
                getViewData().setContestSubmissions(submissions);
            }


            // If contest submissions are confirmed then set the request cookie for contest with the details on
            // prized submissions
            if (getViewData().getHasCheckout()) {
                // Build submissions map for faster lookup and Collect handles for submitters
                UserService userService = getUserService();
                Map<String, SubmissionData> submissionsMap = new HashMap<String, SubmissionData>();
                Map<Long, String> submitterHandles = new HashMap<Long, String>();
                for (SubmissionData submission : submissions) {
                    submissionsMap.put(String.valueOf(submission.getSubmissionId()), submission);
                    submitterHandles.put(submission.getSubmitterId(),
                                         userService.getUserHandle(submission.getSubmitterId()));
                }
                getViewData().setSubmitterHandles(submitterHandles);
                
                String contestIdString = String.valueOf(contestId);
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
                String[] prizeSlotNames = {"firstPrize", "secondPrize", "thirdPrize", "fourthPrize", "fifthPrize"};
                if (!contestRoundSelectionJSON.isNullObject()) {
                    for (String prizeSlotName : prizeSlotNames) {
                        if (contestRoundSelectionJSON.has(prizeSlotName)) {
                            contestRoundSelectionJSON.remove(prizeSlotName);
                        }
                    }
                } else {
                    contestRoundSelectionJSON = (JSONObject) JSONSerializer.toJSON("{\"" + roundType + "\":{}}");
                }

                // Set real prized submissions as is for confirmed contest
                int prizeIndex = 0;
                for (SubmissionData submission : submissions) {
                    if (isCheckedOut(roundType, submission)) {
                        if (roundType == ContestRoundType.MILESTONE) {
                            contestRoundSelectionJSON.put(prizeSlotNames[prizeIndex++], 
                                                          String.valueOf(submission.getSubmissionId()));
                        } else {
                            contestRoundSelectionJSON.put(prizeSlotNames[submission.getUserRank() - 1],
                                                          String.valueOf(submission.getSubmissionId()));
                        }
                    }
                }

                // Remove real prized submissions from list of liked, disliked, additional purchased submissions
                // as provided by request cookie
                removeSubmissionFromList(contestRoundSelectionJSON, roundType, "listLikes", submissionsMap);
                removeSubmissionFromList(contestRoundSelectionJSON, roundType, "listDislikes", submissionsMap);
                removeSubmissionFromList(contestRoundSelectionJSON, roundType, "listExtra", submissionsMap);

                contestSelectionJSON.put(roundType.toString(), contestRoundSelectionJSON);
                bankSelectionJSON.put(contestIdString, contestSelectionJSON);

                HttpServletResponse response = DirectUtils.getServletResponse();
                response.addCookie(toBankSelectionCookie(bankSelectionJSON));
            }


            // For normal request flow prepare various data to be displayed to user

            // Set contest stats
            //ContestStatsDTO contestStats = DirectUtils.getContestStats(contestServiceFacade, currentUser, contestId);
            ContestStatsDTO contestStats = DirectUtils.getContestStats(currentUser, contestId, isStudioCompetition());
            getViewData().setContestStats(contestStats);

            // set the number of prizes
            int prizeNumber = DirectUtils.getContestPrizeNumber(studioCompetition, roundType);
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
                    .hasWritePermission(this, currentUser, contestId, true));
            
            DirectUtils.setDashboardData(currentUser, contestId, viewData,
                    getContestServiceFacade(), isSoftware());
        }
    }

    /**
     * <p>Removes prized submissions from the specified list of user's bank selection.</p>
     *
     * @param contestRoundSelectionJSON a <code>JSONObject</code> providing the data for contest round selection.
     * @param roundType a <code>ContestRoundType</code> referencing the round type.
     * @param listName a <code>String</code> providing the name of the list to be processed.
     * @param submissionsMap a <code>Map</code> mapping submission IDs to submission details.
     */
    private void removeSubmissionFromList(JSONObject contestRoundSelectionJSON, ContestRoundType roundType,
                                          String listName, Map<String, SubmissionData> submissionsMap) {
        if (contestRoundSelectionJSON.has(listName)) {
            JSONArray list = contestRoundSelectionJSON.getJSONArray(listName);
            Iterator listIterator = list.iterator();
            while (listIterator.hasNext()) {
                String submissionId = (String) listIterator.next();
                if (submissionsMap.containsKey(submissionId)) {
                    SubmissionData submission = submissionsMap.get(submissionId);
                    boolean isSubmissionCheckedOut = false;
                    isSubmissionCheckedOut = isCheckedOut(roundType, submission);
                    if (isSubmissionCheckedOut) {
                        listIterator.remove();
                    }
                }
            }
        }
    }

    /**
     * <p>Checks if specified submission is checked out.</p>
     *
     * @param roundType a <code>ContestRoundType</code> referencing the round type.
     * @param submission a <code>SubmissionData</code> providing details for submission.
     * @return <code>true</code> if submission is checked out; <code>false</code> otherwise.
     */
    private boolean isCheckedOut(ContestRoundType roundType, SubmissionData submission) {
        boolean submissionCheckedOut = false;
        if (roundType == ContestRoundType.MILESTONE) {
            if (submission.isAwardMilestonePrize() != null && submission.isAwardMilestonePrize()) {
                submissionCheckedOut = true;
            }
        } else {
            if (submission.getUserRank() > 0) {
                submissionCheckedOut = true;
            }
        }
        return submissionCheckedOut;
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
     * @author isv
     * @version 1.0
     */
    private static class SubmissionsRankComparator implements Comparator<SubmissionData> {

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
        public int compare(SubmissionData s1, SubmissionData s2) {
            int rank1 = s1.getUserRank();
            int rank2 = s2.getUserRank();

            if (rank1 > 0) {
                if (rank2 <= 0) {
                    return -1;
                } else {
                    return rank1 - rank2;
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
