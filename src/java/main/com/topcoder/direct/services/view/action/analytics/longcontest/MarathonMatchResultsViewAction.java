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
import com.topcoder.marathonmatch.service.dto.TestCaseInfo;
import com.topcoder.marathonmatch.service.dto.TestCaseSubmissionInfo;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.tc.rest.longcontest.resources.MatchResultResource;
import com.topcoder.web.tc.rest.longcontest.resources.SearchResult;
import com.topcoder.web.tc.rest.longcontest.resources.SystemTestResource;
import com.topcoder.web.tc.rest.longcontest.resources.SystemTestResourceWrapper;
import com.topcoder.web.tc.rest.longcontest.resources.TestCaseResource;
import org.apache.struts2.ServletActionContext;
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
 * <p>
 *     Version 1.1 - Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Results Tab 2
 *     <ol>
 *         <li>Remove property roundId instead use viewData.roundId.</li>
 *         <li>Add property {@link #type}, {@link #sr} and {@link #stc}.</li>
 *         <li>Add static property {@link #RESULT_DETAIL} and {@link #ALLOWABLE_SORT_ORDER}.</li>
 *     </ol>
 * </p>
 *
 * <p>
 *     Version 1.2 - BUGR -9794
 *     <ol>
 *         <li>Remove ALLOWABLE_SORT_ORDER</li>
 *         <li>Update method {@link #convertResults(SearchResult)} to remove the api call to competitorSubmissionHistory
 *         method. So the performance will be improved.</li>
 *     </ol>
 * </p>
 *
 *
 * @author Ghost_141
 * @version 1.1
 * @since 1.0 (Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Results Tab)
 */
public class MarathonMatchResultsViewAction extends AbstractAction
        implements ViewAction<MMResultsInfoDTO>, SessionAware {

    /**
     * This field represents the qualified name of this class.
     */
    private static final String CLASS_NAME = MarathonMatchResultsViewAction.class.getName();

    /**
     * The static field to indicate the result detail page.
     * @since 1.1
     */
    private static final String RESULT_DETAIL = "system";

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
     * Represent the results page type.
     * @since 1.1
     */
    private String type;

    /**
     * Represent the coder start number used in request parameter.
     * Default value is 1(Start with first coder).
     * @since 1.1
     */
    private Integer sr = 1;

    /**
     * Represent the test case start number used in request parameter.
     * Default value is 1(Start with first test case).
     * @since 1.1
     */
    private Integer stc = 1;

    /**
     * The Marathon match analytics service.
     */
    private MarathonMatchAnalyticsService marathonMatchAnalyticsService;

    /**
     * Represent the contest service facade instance.
     */
    private ContestServiceFacade contestServiceFacade;

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

            viewData = new MMResultsInfoDTO();

            if(hasRoundId) {
                viewData.setRoundId(Long.valueOf(roundIdStr));
            }

            // If the contest don't have the round id or the contest is a active contest then throw an exception.
            if(!hasRoundId ||
                    MarathonMatchHelper.isMarathonMatchActive(viewData.getRoundId(), marathonMatchAnalyticsService)) {
                throw new Exception("The contest is either don't have round id or is an active contest");
            }

            MarathonMatchHelper.getMarathonMatchDetails(viewData.getRoundId().toString(), marathonMatchAnalyticsService,
                    timelineInterval, viewData);

            // Get the common data for contest page.
            MarathonMatchHelper.getCommonData(projectId, currentUser, softwareCompetition, viewData,
                    contestServiceFacade, getSessionData());

            if(type == null) {
                // results page.
                viewResults();
            } else if(type.equals(RESULT_DETAIL)) {
                // result detail page.
                viewSystemTestResults();
            }

            return SUCCESS;
        } catch (Exception e) {
            LoggingWrapperUtility.logException(logger, signature, e);
            throw new Exception("Error when executing action : " + getAction() + " : " + e.getMessage());
        }
    }

    /**
     * Handle the view system test results request.
     *
     * @throws MarathonMatchAnalyticsServiceException if any error occurred.
     * @since 1.1
     */
    private void viewSystemTestResults() throws Exception {
        SystemTestResourceWrapper systemTestResourceWrapper =
                marathonMatchAnalyticsService.getSystemTests(viewData.getRoundId(), null, sr, stc, "score",
                        "desc", MarathonMatchHelper.ACCESS_TOKEN);
        if(systemTestResourceWrapper.getItems() != null && systemTestResourceWrapper.getItems().size() != 0) {
            viewData.setCompetitorsTestCases(new ArrayList<TestCaseSubmissionInfo>());
            for(SystemTestResource systemTestResource : systemTestResourceWrapper.getItems()) {
                TestCaseSubmissionInfo competitorTestCases = new TestCaseSubmissionInfo();
                competitorTestCases.setTestCases(new ArrayList<TestCaseInfo>());

                for(TestCaseResource testCase : systemTestResource.getTestCases()) {
                    TestCaseInfo testCaseInfo = new TestCaseInfo();
                    testCaseInfo.setTestCaseId(testCase.getTestCaseId());
                    testCaseInfo.setTestCaseScore(testCase.getScore());
                    competitorTestCases.getTestCases().add(testCaseInfo);
                }
                competitorTestCases.setHandle(systemTestResource.getHandleName());
                competitorTestCases.setUserId(Long.valueOf(systemTestResource.getHandleId()));
                competitorTestCases.setFinalScore(systemTestResource.getScore());
                viewData.getCompetitorsTestCases().add(competitorTestCases);
            }
            viewData.setTestCasesStartNumber(systemTestResourceWrapper.getTestCasesStartNumber());
            // TODO: Due to the API bug, this test statement have to be added.
            viewData.setTestCasesEndNumber(systemTestResourceWrapper.getTestCasesEndNumber()
                    - systemTestResourceWrapper.getTestCasesStartNumber() == 9
                    ? systemTestResourceWrapper.getTestCasesEndNumber() - 1
                    : systemTestResourceWrapper.getTestCasesEndNumber());
            viewData.setTestCasesCount(systemTestResourceWrapper.getTestCasesCount());
            viewData.setCodersStartNumber(systemTestResourceWrapper.getCodersStartNumber());
            // TODO: Due to the API bug, this test statement have to be added.
            viewData.setCodersEndNumber(systemTestResourceWrapper.getCodersEndNumber()
                    - systemTestResourceWrapper.getCodersStartNumber() == 39
                    ? systemTestResourceWrapper.getCodersEndNumber() - 1
                    : systemTestResourceWrapper.getCodersEndNumber());
            viewData.setCodersCount(systemTestResourceWrapper.getCodersCount());
        } else {
            ServletActionContext.getRequest().setAttribute("errorPageMessage", systemTestResourceWrapper.getMessage());
            throw new Exception("The system test cases result is empty");
        }
    }

    /**
     * This method will handle the request to results page.
     *
     * @throws MarathonMatchAnalyticsServiceException if any error occurred.
     */
    private void viewResults() throws MarathonMatchAnalyticsServiceException {
        SearchResult<MatchResultResource> matchResults =
                marathonMatchAnalyticsService.getMatchResults(viewData.getRoundId(), Integer.MAX_VALUE, 1, "desc",
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
        int place = 1;
        for(MatchResultResource matchResultResource : matchResults.getItems()) {
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setPlace(place++);
            resultInfo.setHandle(matchResultResource.getHandleName());
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
     * Gets type.
     *
     * @return the type
     * @since 1.1
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     * @since 1.1
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets sr.
     *
     * @return the sr
     * @since 1.1
     */
    public Integer getSr() {
        return sr;
    }

    /**
     * Sets sr.
     *
     * @param sr the sr
     * @since 1.1
     */
    public void setSr(Integer sr) {
        this.sr = sr;
    }

    /**
     * Gets stc.
     *
     * @return the stc
     * @since 1.1
     */
    public Integer getStc() {
        return stc;
    }

    /**
     * Sets stc.
     *
     * @param stc the stc
     * @since 1.1
     */
    public void setStc(Integer stc) {
        this.stc = stc;
    }
}
