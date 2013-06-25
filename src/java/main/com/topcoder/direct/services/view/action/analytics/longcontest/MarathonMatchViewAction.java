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
import com.topcoder.direct.services.view.dto.contest.ContestStatsDTO;
import com.topcoder.direct.services.view.dto.contest.ProjectPhaseDTO;
import com.topcoder.direct.services.view.dto.contest.ProjectPhaseType;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.util.DashboardHelper;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.marathonmatch.service.dto.MMCommonInfoDTO;
import com.topcoder.marathonmatch.service.dto.MMInfoDTO;
import com.topcoder.marathonmatch.service.dto.RegistrantInfo;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.tc.rest.longcontest.resources.CompetitorResource;
import com.topcoder.web.tc.rest.longcontest.resources.MarathonMatchDetailsResource;
import com.topcoder.web.tc.rest.longcontest.resources.SearchResult;
import org.apache.struts2.interceptor.SessionAware;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This action will be used to display the marathon match information with the Registrants And Submissions tab
 * and its child tabs
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is only used in thread-safe manner by Struts2 framework.
 * </p>
 *
 * @author Ghost_141
 * @since 1.0 (PoC Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress)
 * @version 1.0
 */
public class MarathonMatchViewAction extends AbstractAction implements ViewAction<MMInfoDTO>, SessionAware {

    /**
     * This field represents the qualified name of this class.
     */
    private static final String CLASS_NAME = MarathonMatchViewAction.class.getName();

    /**
     * Represent the dummy access token. This is just for test purpose. The access token itself means nothing.
     */
    private static final String ACCESS_TOKEN = "testAccessToken";

    /**
     * Represent the dummy group type. This is just for test purpose. The group type itself means nothing.
     */
    private static final String GROUP_TYPE = "hour";

    /**
     * Log instance.
     */
    private Log logger = LogManager.getLog(CLASS_NAME);

    /**
     * The View data.
     */
    private MMInfoDTO viewData;
    /**
     * The Timeline interval.
     */
    private int timelineInterval;
    /**
     * The Submission history interval.
     */
    private int submissionHistoryInterval;
    /**
     * The Marathon match analytics service.
     */
    private MarathonMatchAnalyticsService marathonMatchAnalyticsService;

    /**
     * Represent the project id.
     */
    private long projectId;

    /**
     * Represent the contest service facade instance.
     */
    private ContestServiceFacade contestServiceFacade;

    /**
     * Represent the contest.
     */
    private SoftwareCompetition softwareCompetition;

    /**
     * Represent the total count of the registrants.
     */
    private int registrantsCount;

    /**
     * Represent the contest has round id or not.
     */
    private boolean hasRoundId = false;

    /**
     * Represent the all available rating color in direct system.
     */
    private static final String[] RATING_COLORS =  {"Orange", "Gray", "Green", "Blue", "Yellow", "Red", "Unrated"};

    /**
     * Represent the all available rating color's stype in direct system.
     */
    private static final String[] RATING_COLOR_STYLE =
            {"#FF9900", "#999999", "#00A900", "#6666FF", "#DDCC00", "#EE0000", "#000000"};

    /**
     * Create a new instance of <code>MarathonMatchViewAction</code>.
     */
    public MarathonMatchViewAction() {
        //Empty
    }

    /**
     * Get the view data.
     *
     * @return the view data.
     */
    public MMInfoDTO getViewData() {
        return viewData;
    }

    /**
     * This method is responsible for retrieving MM, list of RegistrantInfo and list of SubmissionInfo given round id,
     * pageSize, pageNumber, sortingOrder, sortingField
     *
     * @return a <code>String</code> that represent the state of execute result.
     */
    public String execute() throws Exception {
        final String signature = CLASS_NAME + "#execute()";
        try {
            if(projectId <= 0) {
                throw new DirectException("project less than 0 or not defined.");
            }

            TCSubject currentUser = DirectUtils.getTCSubjectFromSession();
            softwareCompetition = contestServiceFacade.getSoftwareContestByProjectId(DirectStrutsActionsHelper
                    .getTCSubjectFromSession(), projectId);

            // Get the round id from the project info.
            String roundId = softwareCompetition.getProjectHeader().getProperty("Marathon Match Id");

            hasRoundId = (roundId != null);

            if(!isMarathon()) {
                throw new DirectException("contest is not a marathon contest.");
            }

            viewData = new MMInfoDTO();

            if(hasRoundId) {
                // There is round id hooked with the contest.
                // call the service to get the registrants data.
                SearchResult<CompetitorResource> result = getMMRegistrants(Long.valueOf(roundId));
                // call the service to get the detail information.
                getMarathonMatchDetails(Long.valueOf(roundId));

                // calculate the json string by given data.
                calculateRegistrantsGraphData(result);

                // Set the registrants count.
                this.registrantsCount = result.getTotalCount();
            } else {
                // There is no round id hooked with contest.
                // calculate the json string by null.
                calculateRegistrantsGraphData(null);
            }

            // Set contest stats
            ContestStatsDTO contestStats = DirectUtils.getContestStats(currentUser, projectId, softwareCompetition);
            viewData.setContestStats(contestStats);

            viewData.setDashboard(
                    DataProvider.getContestDashboardData(projectId, DirectUtils.isStudio(softwareCompetition), false));
            DirectUtils.setDashboardData(currentUser, projectId, viewData, getContestServiceFacade(), !DirectUtils
                    .isStudio(softwareCompetition));

            // calculate the contest issues tracking health
            getViewData().getDashboard().setUnresolvedIssuesNumber(
                    getViewData().getContestStats().getIssues().getUnresolvedIssuesNumber());
            DashboardHelper.setContestStatusColor(getViewData().getDashboard());

            getViewData().getDashboard().setAllPhases(sortContestPhases(getViewData().getDashboard().getAllPhases()));

            // Get current session
            HttpServletRequest request = DirectUtils.getServletRequest();
            SessionData sessionData = new SessionData(request.getSession());
            // Set current project contests
            List<TypedContestBriefDTO> contests = DataProvider
                    .getProjectTypedContests(currentUser.getUserId(),
                            contestStats.getContest().getProject().getId());
            sessionData.setCurrentProjectContests(contests);

            // Set current project context based on selected contest
            sessionData.setCurrentProjectContext(contestStats.getContest().getProject());
            sessionData.setCurrentSelectDirectProjectID(contestStats.getContest().getProject().getId());
            setSessionData(sessionData);

            return SUCCESS;
        } catch (Exception e) {
            LoggingWrapperUtility.logException(logger, signature, e);
            throw new Exception("Error when executing action : " + getAction() + " : " + e.getMessage());
        }
    }

    /**
     * Calculate the registrants bar and pie data by given data.
     *
     * @param result the result
     */
    private void calculateRegistrantsGraphData(SearchResult<CompetitorResource> result) {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode registrantsRatingBarData = objectMapper.createArrayNode();
        ArrayNode registrantsRatingPieData = objectMapper.createArrayNode();
        Map<String, Integer> pieMap = new HashMap<String, Integer>();
        Map<String, String> colorMap = new HashMap<String, String>();

        // initialize the pie map with all available rating color.
        for(int i = 0; i < RATING_COLORS.length; i++) {
            colorMap.put(RATING_COLORS[i], RATING_COLOR_STYLE[i]);
            pieMap.put(RATING_COLORS[i], 0);
        }

        if(result != null) {
            //The result is not null mean there is round id hooked with contest.
            String maxNumberKey = RATING_COLORS[0];
            int maxNumber = 0;

            for(CompetitorResource competitorResource : result.getItems()) {
                ObjectNode node = objectMapper.createObjectNode();
                node.put("handle", competitorResource.getHandleName());
                node.put("number", competitorResource.getRating());
                node.put("color", competitorResource.getRatingColorStyle()
                        .substring(7, competitorResource.getRatingColorStyle().length()));
                registrantsRatingBarData.add(node);

                pieMap.put(competitorResource.getRatingColor(), pieMap.get(competitorResource.getRatingColor()) + 1);

                if(!maxNumberKey.equalsIgnoreCase(competitorResource.getRatingColor())
                        && maxNumber <= pieMap.get(competitorResource.getRatingColor())) {
                    maxNumberKey = competitorResource.getRatingColor();
                    maxNumber = pieMap.get(competitorResource.getRatingColor());
                }
            }

            for(String key : pieMap.keySet()) {
                ObjectNode node = objectMapper.createObjectNode();
                node.put("handle", key);
                node.put("number", pieMap.get(key));
                node.put("color", colorMap.get(key));
                if(key.equalsIgnoreCase("unrated")) {
                    node.put("textColor", "#666666");
                } else {
                    node.put("textColor", "#ffffff");
                }
                if(key.equalsIgnoreCase(maxNumberKey)) {
                    node.put("sliced", 1);
                } else {
                    node.put("sliced", 0);
                }
                registrantsRatingPieData.add(node);
            }
        } else {
            // There is no round id hooked with contest.
            for(String key : pieMap.keySet()) {
                ObjectNode node = objectMapper.createObjectNode();
                node.put("handle", key);
                node.put("number", 0);
                node.put("color", colorMap.get(key));
                node.put("textColor", "#666666");
                node.put("sliced", 0);
                registrantsRatingPieData.add(node);
            }
        }
        ObjectNode barData = objectMapper.createObjectNode();
        barData.put("rating", registrantsRatingBarData);

        ObjectNode pieData = objectMapper.createObjectNode();
        pieData.put("rating", registrantsRatingPieData);

        viewData.setRegistrantsRatingBarData(barData.toString());
        viewData.setRegistrantsRatingPieData(pieData.toString());
    }

    /**
     * <p>
     * Determines if the contest is marathon contest or not.
     * For this class it should be always true otherwise there is error occured.
     * </p>
     *
     * @return true if it's marathon contest, false otherwise.
     */
    public boolean isMarathon() {
        return DirectUtils.isMM(softwareCompetition);
    }

    /**
     * <p>
     * Determines if the contest has round id or not.
     * </p>
     *
     * @return true if the marathon contest has round id, false otherwise.
     */
    public boolean isHasRoundId() {
        return hasRoundId;
    }

    /**
     * Get the registrants of this marathon match.
     *
     * @return the result from service.
     * @throws MarathonMatchAnalyticsServiceException the marathon match analytics service exception
     */
    private SearchResult<CompetitorResource> getMMRegistrants(long roundId)
            throws MarathonMatchAnalyticsServiceException {
        SearchResult<CompetitorResource> result = marathonMatchAnalyticsService.getRegistrants(roundId, ACCESS_TOKEN);
        List<RegistrantInfo> registrantInfos = new ArrayList<RegistrantInfo>();
        for(CompetitorResource competitorResource : result.getItems()) {
            RegistrantInfo registrant = new RegistrantInfo();

            registrant.setHandle(competitorResource.getHandleName());
            registrant.setRating(competitorResource.getRating());
            registrant.setRegistrationTime(competitorResource.getRegistrationDate());
            registrantInfos.add(registrant);
        }

        viewData.setRegistrants(registrantInfos);
        return result;
    }

    /**
     * Get the marathon match details information of this marathon match contest.
     *
     * @param roundId the round id of this contest.
     * @throws MarathonMatchAnalyticsServiceException if any error occurred.
     */
    private void getMarathonMatchDetails(long roundId) throws MarathonMatchAnalyticsServiceException {
        MarathonMatchDetailsResource result =
                marathonMatchAnalyticsService.getMarathonMatchDetails(roundId, GROUP_TYPE, ACCESS_TOKEN);
        MMCommonInfoDTO commonInfoDTO = new MMCommonInfoDTO();
        viewData.setCommonInfo(commonInfoDTO);
        viewData.getCommonInfo().setNumCompetitors(result.getNoOfCompetitors());
        viewData.getCommonInfo().setNumRegistrants(result.getNoOfRegistrants());
        viewData.getCommonInfo().setNumSubmissions(result.getNoOfSubmissions());
    }

    /**
     * Sort contest phases.
     *
     * @param phases the phases
     * @return the list
     */
    private static List<ProjectPhaseDTO> sortContestPhases(List<ProjectPhaseDTO> phases) {
        List<ProjectPhaseDTO> specPart = new ArrayList<ProjectPhaseDTO>();
        List<ProjectPhaseDTO> reviewPart = new ArrayList<ProjectPhaseDTO>();
        List<ProjectPhaseDTO> finalPart = new ArrayList<ProjectPhaseDTO>();

        for(ProjectPhaseDTO p : phases) {
            if(p.getPhaseType().getOrder() <= ProjectPhaseType.SPECIFICATION_REVIEW.getOrder()) {
                specPart.add(p);
            } else if (p.getPhaseType().getOrder() >= ProjectPhaseType.FINAL_FIX.getOrder()) {
                finalPart.add(p);
            } else {
                reviewPart.add(p);
            }
        }

        StartDateComparator sc = new StartDateComparator();
        PhaseOrderComparator pc = new PhaseOrderComparator();
        Collections.sort(specPart, sc);
        Collections.sort(finalPart, sc);
        Collections.sort(reviewPart, pc);

        specPart.addAll(reviewPart);
        specPart.addAll(finalPart);

        return specPart;

    }

    /**
     * The type Start date comparator.
     */
    private static class StartDateComparator implements Comparator<ProjectPhaseDTO> {

        public int compare(ProjectPhaseDTO o1, ProjectPhaseDTO o2) {
            return o1.getStartTime().compareTo(o2.getStartTime());
        }
    }

    /**
     * The type Phase order comparator.
     */
    private static class PhaseOrderComparator implements Comparator<ProjectPhaseDTO>{

        public int compare(ProjectPhaseDTO p1, ProjectPhaseDTO p2) {
            int o1 = p1.getPhaseType().getOrder();
            int o2 = p2.getPhaseType().getOrder();
            return (o1>o2 ? 1 : (o1==o2 ? 0 : -1));
        }
    }

    /**
     * Setter of the session.
     *
     * @param session the session.
     */
    public void setSession(Map<String, Object> session) {

    }

    /**
     * Sets view data.
     *
     * @param viewData the view data
     */
    public void setViewData(MMInfoDTO viewData) {
        this.viewData = viewData;
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
     * Gets submission history interval.
     *
     * @return the submission history interval
     */
    public int getSubmissionHistoryInterval() {
        return submissionHistoryInterval;
    }

    /**
     * Sets submission history interval.
     *
     * @param submissionHistoryInterval the submission history interval
     */
    public void setSubmissionHistoryInterval(int submissionHistoryInterval) {
        this.submissionHistoryInterval = submissionHistoryInterval;
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
     * Sets project id.
     *
     * @param projectId the project id
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
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
     * Gets registrants count.
     *
     * @return the registrants count
     */
    public int getRegistrantsCount() {
        return registrantsCount;
    }
}

