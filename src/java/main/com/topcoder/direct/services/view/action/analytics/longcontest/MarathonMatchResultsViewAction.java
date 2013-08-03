/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.analytics.longcontest;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.direct.services.exception.DirectException;
import com.topcoder.direct.services.view.action.AbstractAction;
import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.direct.services.view.action.analytics.longcontest.services.MarathonMatchAnalyticsService;
import com.topcoder.direct.services.view.action.analytics.longcontest.services.MarathonMatchAnalyticsServiceException;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.marathonmatch.service.dto.MMResultsInfoDTO;
import com.topcoder.marathonmatch.service.dto.ResultInfo;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.service.user.UserService;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.tc.rest.longcontest.resources.MatchResultResource;
import com.topcoder.web.tc.rest.longcontest.resources.SearchResult;
import com.topcoder.web.tc.rest.longcontest.resources.SubmissionResource;
import org.apache.struts2.interceptor.SessionAware;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * This action will be used to handle the request for view marathon match result. This action should not be called when
 * the marathon match contest is still active.
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is only used in thread-safe manner by Struts2 framework.
 * </p>
 *
 *
 * @author Ghost_141
 * @version 1.0
 * @since 1.0 (Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Results Tab)
 */
public class MarathonMatchResultsViewAction extends AbstractAction
        implements ViewAction<MMResultsInfoDTO>, SessionAware {

    /**
     * This field represents the qualified name of this class.
     */
    private static final String CLASS_NAME = MarathonMatchResultsViewAction.class.getName();

    /**
     * Log instance.
     */
    private Log logger = LogManager.getLog(CLASS_NAME);

    /**
     * Represent the session map.
     */
    private Map<String, Object> session;

    /**
     * The view data dto.
     */
    private MMResultsInfoDTO viewData;

    /**
     * Represent the project id.
     */
    private long projectId;

    /**
     * Represent the round id of this marathon match contest.
     */
    private Long roundId;

    /**
     * The Marathon match analytics service.
     */
    private MarathonMatchAnalyticsService marathonMatchAnalyticsService;

    /**
     * Represent the contest service facade instance.
     */
    private ContestServiceFacade contestServiceFacade;

    /**
     * Represent the user service instance.
     */
    private UserService userService;

    /**
     * Represent the contest.
     */
    private SoftwareCompetition softwareCompetition;

    /**
     * The Timeline interval.
     */
    private int timelineInterval;

    /**
     * Represent the contest has round id or not.
     */
    private boolean hasRoundId = false;

    /**
     * Create an instance of <code>MarathonMatchResultsViewAction</code>.
     */
    public MarathonMatchResultsViewAction() {
        //Empty
    }

    /**
     * This method is responsible for retrieving MM, list of RegistrantInfo and list of SubmissionInfo given round id,
     * pageSize, pageNumber, sortingOrder, sortingField
     *
     * @return a <code>String</code> that represent the state of execute result.
     * @throws Exception if any error occurred.
     */
    public String execute() throws Exception{
        final String signature = CLASS_NAME + "#execute()";
        try {
            if(projectId <= 0) {
                throw new DirectException("project less than 0 or not defined.");
            }

            TCSubject currentUser = DirectUtils.getTCSubjectFromSession();
            softwareCompetition = contestServiceFacade.getSoftwareContestByProjectId(DirectStrutsActionsHelper
                    .getTCSubjectFromSession(), projectId);

            // Get the round id from the project info.
            String roundIdStr = softwareCompetition.getProjectHeader().getProperty("Marathon Match Id");

            hasRoundId = !(roundIdStr == null);

            if(hasRoundId) {
                roundId = Long.valueOf(roundIdStr);
            }

            // If the contest don't have the round id or the contest is a active contest then throw an exception.
            if(!hasRoundId || MarathonMatchHelper.isMarathonMatchActive(roundId, marathonMatchAnalyticsService)) {
                throw new Exception("The contest is either don't have round id or is an active contest");
            }

            viewData = new MMResultsInfoDTO();

            MarathonMatchHelper.getMarathonMatchDetails(roundId.toString(), marathonMatchAnalyticsService, userService,
                    timelineInterval, viewData);

            // Get the common data for contest page.
            MarathonMatchHelper.getCommonData(projectId, currentUser, softwareCompetition, viewData,
                    contestServiceFacade, getSessionData());

            viewResults();

            return SUCCESS;
        } catch (Exception e) {
            LoggingWrapperUtility.logException(logger, signature, e);
            throw new Exception("Error when executing action : " + getAction() + " : " + e.getMessage());
        }
    }

    /**
     * This method will handle the request to results page.
     *
     * @throws MarathonMatchAnalyticsServiceException if any error occurred.
     */
    private void viewResults() throws MarathonMatchAnalyticsServiceException {
        SearchResult<MatchResultResource> matchResults =
                marathonMatchAnalyticsService.getMatchResults(roundId, Integer.MAX_VALUE, 1, "desc",
                        "finalScore", MarathonMatchHelper.ACCESS_TOKEN);

        List<ResultInfo> resultInfos = convertResults(matchResults);

        // Sort the results info by provisional score and set the provisional rank.
        Collections.sort(resultInfos, new Comparator<ResultInfo>() {
            public int compare(ResultInfo resultInfo, ResultInfo resultInfo2) {
                if (resultInfo.getProvisionalScore() != null && resultInfo2.getProvisionalScore() != null) {
                    if (resultInfo.getProvisionalScore() < resultInfo2.getProvisionalScore()) {
                        return 1;
                    }
                    if (resultInfo.getProvisionalScore().equals(resultInfo2.getProvisionalScore())) {
                        return String.CASE_INSENSITIVE_ORDER.compare(resultInfo.getHandle(), resultInfo2.getHandle());
                    }
                }
                return 0;
            }
        });
        for(ResultInfo r : resultInfos) {
            r.setProvisionalRank(resultInfos.indexOf(r) + 1);
        }
        viewData.setResults(resultInfos);

        // calculate the results graph data.
        calculateResultsGraphData(resultInfos);
    }

    /**
     * Calculate the results graph data. The graph includes final rank graph and final vs provisional score graph.
     *
     * @param resultInfos the results
     */
    private void calculateResultsGraphData(List<ResultInfo> resultInfos) {
        // Sort the results info by final score.
        Collections.sort(resultInfos, new Comparator<ResultInfo>() {
            public int compare(ResultInfo resultInfo, ResultInfo resultInfo2) {
                if (resultInfo.getFinalRank() < resultInfo2.getFinalRank()) {
                    return 0;
                }
                if (resultInfo.getFinalRank() == resultInfo2.getFinalRank()) {
                    return String.CASE_INSENSITIVE_ORDER.compare(resultInfo.getHandle(), resultInfo2.getHandle());
                }
                return 1;
            }
        });
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode finalScoreRankingData = objectMapper.createObjectNode();
        ObjectNode finalVsProvisionalScoreData = objectMapper.createObjectNode();
        ArrayNode rating = objectMapper.createArrayNode();
        ArrayNode score = objectMapper.createArrayNode();
        for(ResultInfo result : resultInfos) {
            ObjectNode finalScoreNode = objectMapper.createObjectNode();
            finalScoreNode.put("handle", result.getHandle());
            finalScoreNode.put("number", result.getFinalScore());
            finalScoreNode.put("rank", result.getFinalRank());
            rating.add(finalScoreNode);

            ObjectNode finalProvisionalScoreNode = objectMapper.createObjectNode();
            finalProvisionalScoreNode.put("handle", result.getHandle());
            finalProvisionalScoreNode.put("finalScore", result.getFinalScore());
            finalProvisionalScoreNode.put("provisionalScore", result.getProvisionalScore());
            score.add(finalProvisionalScoreNode);
        }
        finalScoreRankingData.put("rating", rating);
        finalVsProvisionalScoreData.put("score", score);

        viewData.setFinalScoreRankingData(finalScoreRankingData.toString());
        viewData.setFinalVsProvisionalScoreData(finalVsProvisionalScoreData.toString());
    }

    /**
     * This method will convert the MatchResultResource instance to ResultInfo instance and get the missing field by
     * calling service method.
     *
     *
     * @param matchResults the results.
     * @return the result infos.
     */
    private List<ResultInfo> convertResults(SearchResult<MatchResultResource> matchResults)
            throws MarathonMatchAnalyticsServiceException {
        List<ResultInfo> resultInfos = new ArrayList<ResultInfo>();
        for(MatchResultResource matchResultResource : matchResults.getItems()) {
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setHandle(matchResultResource.getHandleName());
            // TODO: This is a temporary test because the api only return full submissions of a competitor. So there
            // will be a out of bound exception for user who just submit example test. This test statement should be
            // removed in future.
            if(matchResultResource.getProvisionalScore() != null) {
                SearchResult<SubmissionResource> submissionHistory =
                        marathonMatchAnalyticsService.getCompetitorSubmissionsHistory(roundId,
                                matchResultResource.getHandleName(), 1, 1, "desc", "submissionNumber",
                                MarathonMatchHelper.ACCESS_TOKEN);
                resultInfo.setSubmissionNumber(submissionHistory.getItems().get(0).getSubmissionNumber());
            }
            resultInfo.setUserId(matchResultResource.getCoderId());
            resultInfo.setLanguage(matchResultResource.getLanguage());
            resultInfo.setFinalScore(matchResultResource.getFinalScore());
            resultInfo.setFinalRank(matchResultResource.getRank());
            resultInfo.setProvisionalScore(matchResultResource.getProvisionalScore());
            resultInfos.add(resultInfo);
        }
        return resultInfos;
    }

    /**
     * Setter of session.
     * @param sessionData the session data.
     */
    public void setSession(Map<String, Object> sessionData) {
        this.session = sessionData;
    }

    /**
     * Getter of view data.
     * @return the view data instance.
     */
    public MMResultsInfoDTO getViewData() {
        return viewData;
    }

    /**
     * Sets view data.
     *
     * @param viewData the view data
     */
    public void setViewData(MMResultsInfoDTO viewData) {
        this.viewData = viewData;
    }

    /**
     * Gets project id.
     *
     * @return the project id
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Sets project id.
     *
     * @param projectId the project id
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * Gets marathon match analytics service.
     *
     * @return the marathon match analytics service
     */
    public MarathonMatchAnalyticsService getMarathonMatchAnalyticsService() {
        return marathonMatchAnalyticsService;
    }

    /**
     * Sets marathon match analytics service.
     *
     * @param marathonMatchAnalyticsService the marathon match analytics service
     */
    public void setMarathonMatchAnalyticsService(MarathonMatchAnalyticsService marathonMatchAnalyticsService) {
        this.marathonMatchAnalyticsService = marathonMatchAnalyticsService;
    }

    /**
     * Gets contest service facade.
     *
     * @return the contest service facade
     */
    public ContestServiceFacade getContestServiceFacade() {
        return contestServiceFacade;
    }

    /**
     * Sets contest service facade.
     *
     * @param contestServiceFacade the contest service facade
     */
    public void setContestServiceFacade(ContestServiceFacade contestServiceFacade) {
        this.contestServiceFacade = contestServiceFacade;
    }

    /**
     * Gets software competition.
     *
     * @return the software competition
     */
    public SoftwareCompetition getSoftwareCompetition() {
        return softwareCompetition;
    }

    /**
     * Sets software competition.
     *
     * @param softwareCompetition the software competition
     */
    public void setSoftwareCompetition(SoftwareCompetition softwareCompetition) {
        this.softwareCompetition = softwareCompetition;
    }

    /**
     * Is has round id.
     *
     * @return the boolean
     */
    public boolean isHasRoundId() {
        return hasRoundId;
    }

    /**
     * Sets has round id.
     *
     * @param hasRoundId the has round id
     */
    public void setHasRoundId(boolean hasRoundId) {
        this.hasRoundId = hasRoundId;
    }

    /**
     * Gets timeline interval.
     *
     * @return the timeline interval
     */
    public int getTimelineInterval() {
        return timelineInterval;
    }

    /**
     * Sets timeline interval.
     *
     * @param timelineInterval the timeline interval
     */
    public void setTimelineInterval(int timelineInterval) {
        this.timelineInterval = timelineInterval;
    }

    /**
     * <p>
     * Determines if the contest is marathon contest or not.
     * For this class it should be always true otherwise there is error occurred.
     * </p>
     *
     * @return true if it's marathon contest, false otherwise.
     */
    public boolean isMarathon() {
        return DirectUtils.isMM(softwareCompetition);
    }

    /**
     * Getter of the round id.
     * @return the round id.
     */
    public Long getRoundId() {
        return roundId;
    }

    /**
     * Gets user service.
     *
     * @return the user service
     */
    public UserService getUserService() {
        return userService;
    }

    /**
     * Sets user service.
     *
     * @param userService the user service
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
