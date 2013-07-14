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
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.contest.ContestStatsDTO;
import com.topcoder.direct.services.view.dto.contest.ProjectPhaseDTO;
import com.topcoder.direct.services.view.dto.contest.ProjectPhaseType;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.util.DashboardHelper;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.marathonmatch.service.dto.CompetitorInfoDTO;
import com.topcoder.marathonmatch.service.dto.MMCommonInfoDTO;
import com.topcoder.marathonmatch.service.dto.MMInfoDTO;
import com.topcoder.marathonmatch.service.dto.RegistrantInfo;
import com.topcoder.marathonmatch.service.dto.SubmissionInfo;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.tc.rest.longcontest.resources.CompetitorResource;
import com.topcoder.web.tc.rest.longcontest.resources.MarathonMatchDetailsResource;
import com.topcoder.web.tc.rest.longcontest.resources.SearchResult;
import com.topcoder.web.tc.rest.longcontest.resources.SubmissionResource;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * This action will be used to display the marathon match information with the Registrants And Submissions tab
 * and its child tabs. The child tabs include registrants tab, competitors tab and submissions tab.
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is only used in thread-safe manner by Struts2 framework.
 * </p>
 *
 * <p>
 *     Version 1.1 - Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Competitors Tab
 *     <ol>
 *         <li>Update method {@link #execute()}.</li>
 *         <li>Remove property userService.</li>
 *         <li>Add property {@link #view}.</li>
 *         <li>Add property {@link #tab}.</li>
 *         <li>Add property {@link #handle}.</li>
 *         <li>Add static property {@link #BAR_GRAPH}, {@link #PIE_GRAPH}, {@link #LIST_VIEW} and
 *         {@link #GRID_VIEW}</li>
 *     </ol>
 * </p>
 *
 * @author Ghost_141
 * @since 1.0 (PoC Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress)
 * @version 1.1
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
     * Represent the dummy group type. This is just for test purpose.
     */
    private static final String GROUP_TYPE = "hour";

    /**
     * Represent the competitors tab.
     * @since 1.1
     */
    private static final String COMPETITORS = "competitors";

    /**
     * Represent the bar data type in page.
     * @since 1.1
     */
    private static final String BAR_GRAPH = "bar";

    /**
     * Represent the pie data type in page.
     * @since 1.1
     */
    private static final String PIE_GRAPH = "pie";

    /**
     * Represent the competitor tab list view page.
     * @since 1.1
     */
    private static final String LIST_VIEW = "list";

    /**
     * Represent the competitor tab grid view page.
     * @since 1.1
     */
    private static final String GRID_VIEW = "grid";

    /**
     * Log instance.
     */
    private Log logger = LogManager.getLog(CLASS_NAME);

    /**
     * The Session map.
     * @since 1.1
     */
    private Map<String, Object> session;

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
     * Represent the tab.
     * @since 1.1
     */
    private String tab;

    /**
     * Represent the handle.
     * @since 1.1
     */
    private String handle;

    /**
     * Represent the view model.
     * @since 1.1
     */
    private String view;

    /**
     * Represent the contest service facade instance.
     */
    private ContestServiceFacade contestServiceFacade;

    /**
     * Represent the contest.
     */
    private SoftwareCompetition softwareCompetition;

    /**
     * Represent the contest has round id or not.
     */
    private boolean hasRoundId = false;

    /**
     * Represent the all available rating color in direct system.
     */
    private static final String[] RATING_COLORS =  {"Orange", "Gray", "Green", "Blue", "Yellow", "Red", "Unrated"};

    /**
     * Represent the all available rating color's type in direct system.
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

            hasRoundId = !(roundId == null);

            if(!isMarathon()) {
                throw new DirectException("contest is not a marathon contest.");
            }

            viewData = new MMInfoDTO();

            // Do the execution for request.

            // call the service to get the detail information for the dashboard part.
            if(hasRoundId) {
                getMarathonMatchDetails(Long.valueOf(roundId));
            }

            // Get the common data for contest page.
            getCommonData(currentUser);

            boolean isFailed = false;
            if(tab == null) {
                // Registrants tab
                viewRegistrants(roundId);
            } else if (tab.equals(COMPETITORS)) {
                // Competitors tab
                if(view == null || view.equals(LIST_VIEW) || view.equals(GRID_VIEW)) {
                    viewCompetitors(roundId);
                } else {
                    isFailed = true;
                }
                // Competitor History page.
                if(handle != null) {
                    viewCompetitorSubmissionHistory(roundId);
                }
            } else {
                isFailed = true;
            }
            if(isFailed) {
                ServletActionContext.getRequest().setAttribute("errorPageMessage", "The path parameter is invalid.");
                throw new IllegalArgumentException("The path parameter is invalid.");
            }

            return SUCCESS;
        } catch (Exception e) {
            LoggingWrapperUtility.logException(logger, signature, e);
            throw new Exception("Error when executing action : " + getAction() + " : " + e.getMessage());
        }
    }

    /**
     * This method will handle the request for competitor submission history.
     *
     * @param roundId the round id.
     * @throws MarathonMatchAnalyticsServiceException if any error occurred.
     * @since 1.1
     */
    private void viewCompetitorSubmissionHistory(String roundId) throws MarathonMatchAnalyticsServiceException {
        if(hasRoundId) {
            // Get submissions and sort it by submission date.
            SearchResult<SubmissionResource> submissionsHistory =
                    marathonMatchAnalyticsService.getCompetitorSubmissionsHistory(Long.parseLong(roundId),
                            handle, Integer.MAX_VALUE, 1, "desc", "submissionTime", ACCESS_TOKEN);
            if(submissionsHistory.getItems().size() == 0) {
                throw new MarathonMatchAnalyticsServiceException("The user " + handle +
                        " is not a competitor of this contest.");
            }
            List<SubmissionResource> recentSubmissions = submissionsHistory.getItems();

            viewData.getCompetitorInfoDTO().setNoOfFullSubmissions(recentSubmissions.size());

            viewData.setRecentSubmissions(new ArrayList<SubmissionInfo>());
            for(SubmissionResource submission :
                    recentSubmissions.subList(0, recentSubmissions.size() >= 2 ? 2 : recentSubmissions.size())) {
                SubmissionInfo submissionInfo = new SubmissionInfo();
                submissionInfo.setProvisionalScore(submission.getScore());
                submissionInfo.setLanguage(submission.getLanguage());
                submissionInfo.setSubmissionNumber(submission.getSubmissionNumber());
                viewData.getRecentSubmissions().add(submissionInfo);
            }

            calculateSubmissionHistoryData(recentSubmissions);
        }
    }

    /**
     * This method will handle the request for registrants tab.
     *
     * @param roundId the round id of contest.
     * @throws MarathonMatchAnalyticsServiceException if any error occurred when calling MarathonMatchAnalyticsService.
     */
    private void viewRegistrants(String roundId) throws MarathonMatchAnalyticsServiceException {
        Map<String, String> graphData;
        if(hasRoundId) {
            // There is round id hooked with the contest.
            // call the service to get the registrants data.
            SearchResult<CompetitorResource> result = getMMRegistrants(Long.valueOf(roundId));

            // calculate the json string by given data.
            graphData = calculateGraphData(result);
        } else {
            // There is no round id hooked with contest.
            // calculate the json string by null.
            graphData = calculateGraphData(null);
        }

        viewData.setRegistrantsRatingBarData(graphData.get(BAR_GRAPH));
        viewData.setRegistrantsRatingPieData(graphData.get(PIE_GRAPH));
    }

    /**
     * This method will handle the request for competitors tab.
     *
     * @param roundId the round id.
     * @throws MarathonMatchAnalyticsServiceException if any error occurred when calling MarathonMatchAnalyticsService.
     * @since 1.1
     */
    private void viewCompetitors(String roundId) throws MarathonMatchAnalyticsServiceException {
        Map<String, String> graphData;
        if(hasRoundId) {
            // Get the registrant from rest api.
            SearchResult<CompetitorResource> registrants = getMMRegistrants(Long.parseLong(roundId));
            // Get the competitors from the registrants.
            SearchResult<CompetitorResource> competitors = getCompetitors(roundId, registrants);

            // Calculate graph data.
            graphData = calculateGraphData(competitors);
        } else {
            // There is no round id hooked with contest.
            // calculate the json string by null.
            graphData = calculateGraphData(null);
        }
        viewData.setSubmittersRatingBarData(graphData.get(BAR_GRAPH));
        viewData.setSubmittersRatingPieData(graphData.get(PIE_GRAPH));
    }

    /**
     * Calculate the bar and pie data by given data and return the result.
     *
     * <p>
     *     Version 1.1 - Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Competitors Tab
     *     - Rename method to calculateGraphData.
     * </p>
     *
     * @param competitors the competitors.
     * @return the result of calculation.
     */
    private Map<String, String> calculateGraphData(SearchResult<CompetitorResource> competitors) {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode ratingBarData = objectMapper.createArrayNode();
        ArrayNode ratingPieData = objectMapper.createArrayNode();
        Map<String, Integer> pieMap = new HashMap<String, Integer>();
        Map<String, String> colorMap = new HashMap<String, String>();

        // initialize the pie map with all available rating color.
        for(int i = 0; i < RATING_COLORS.length; i++) {
            colorMap.put(RATING_COLORS[i], RATING_COLOR_STYLE[i]);
            pieMap.put(RATING_COLORS[i], 0);
        }

        if(competitors != null) {
            //The result is not null mean there is round id hooked with contest.
            String maxNumberKey = RATING_COLORS[0];
            int maxNumber = 0;

            for(CompetitorResource competitorResource : competitors.getItems()) {
                ObjectNode node = objectMapper.createObjectNode();
                String key = competitorResource.getRatingColor();
                node.put("handle", competitorResource.getHandleName());
                node.put("number", competitorResource.getRating());
                node.put("color", competitorResource.getRatingColorStyle()
                        .substring(7, competitorResource.getRatingColorStyle().length()));
                ratingBarData.add(node);


                pieMap.put(key, pieMap.get(key) + 1);

                if(!maxNumberKey.equalsIgnoreCase(key)
                        && maxNumber <= pieMap.get(key)) {
                    maxNumberKey = key;
                    maxNumber = pieMap.get(key);
                } else if(maxNumberKey.equalsIgnoreCase(key)) {
                    maxNumber++;
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
                ratingPieData.add(node);
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
                ratingPieData.add(node);
            }
        }
        ObjectNode barData = objectMapper.createObjectNode();
        barData.put("rating", ratingBarData);

        ObjectNode pieData = objectMapper.createObjectNode();
        pieData.put("rating", ratingPieData);

        Map<String, String> result = new HashMap<String, String>();

        result.put(BAR_GRAPH, barData.toString());
        result.put(PIE_GRAPH, pieData.toString());

        return result;
    }

    /**
     * Calculate the competitor's submission history data base on given full submissions.
     *
     * @param recentSubmissions the full submissions that competitor submit in this marathon match.
     * @since 1.1
     */
    private void calculateSubmissionHistoryData(List<SubmissionResource> recentSubmissions) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm z");
        dateFormat.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
        DateTime startDate = new DateTime(viewData.getCommonInfo().getContestStart());
        DateTime endDate = new DateTime(viewData.getCommonInfo().getSystemTestingStart());

        int timeLine = Minutes.minutesBetween(startDate, endDate).getMinutes();

        int xLength = timeLine / submissionHistoryInterval;

        // sort the submissions by submission time in asc.
        Collections.sort(recentSubmissions, new Comparator<SubmissionResource>() {
            public int compare(SubmissionResource submissionResource, SubmissionResource submissionResource2) {
                DateTime date1 = new DateTime(submissionResource.getSubmissionTime());
                DateTime date2 = new DateTime(submissionResource2.getSubmissionTime());
                if(date1.compareTo(date2) > 0) {
                    return 1;
                }
                return 0;
            }
        });

        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode personalScore = objectMapper.createArrayNode();
        for(SubmissionResource s : recentSubmissions) {
            ObjectNode node = objectMapper.createObjectNode();
            node.put("score", s.getScore());
            int xPosition =
                    Minutes.minutesBetween(startDate, new DateTime(s.getSubmissionTime())).getMinutes()
                            / submissionHistoryInterval;
            node.put("x", xPosition);
            node.put("date", dateFormat.format(s.getSubmissionTime()));
            personalScore.add(node);
        }
        ObjectNode submissionHistory = objectMapper.createObjectNode();
        submissionHistory.put("personScore", personalScore);
        submissionHistory.put("x", xLength);
        submissionHistory.put("submissionStartTime", dateFormat.format(startDate.toDate()));
        submissionHistory.put("submissionEndTime", dateFormat.format(endDate.toDate()));
        viewData.setSubmissionHistoryData(submissionHistory.toString());
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
     * @param roundId the round id
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
            registrant.setUserId(competitorResource.getHandleId());
            registrantInfos.add(registrant);
        }

        viewData.setRegistrants(registrantInfos);
        return result;
    }

    /**
     * Get the competitors of this marathon match.
     *
     * @param roundId the round id.
     * @param registrants the registrants of this marathon match.
     * @return the competitors.
     * @throws MarathonMatchAnalyticsServiceException if any error occurred.
     * @since 1.1
     */
    private SearchResult<CompetitorResource> getCompetitors(String roundId,
             SearchResult<CompetitorResource> registrants) throws MarathonMatchAnalyticsServiceException {
        // Get the competitors from the registrants.
        List<CompetitorInfoDTO> competitors = new ArrayList<CompetitorInfoDTO>();
        SearchResult<CompetitorResource> result = new SearchResult<CompetitorResource>();
        result.setItems(new ArrayList<CompetitorResource>());

        for(CompetitorResource registrant : registrants.getItems()) {
            // the registrant is a valid competitor.
            CompetitorInfoDTO competitorInfo = new CompetitorInfoDTO();
            if(registrant.getProvisionalScore() != null) {
                // get the submissions and sort the result by submissionDate.
                SearchResult<SubmissionResource> submissionResources =
                        marathonMatchAnalyticsService.getCompetitorSubmissionsHistory(Long.valueOf(roundId),
                                registrant.getHandleName(), 1, 1, "desc", "submissionTime", ACCESS_TOKEN);

                SubmissionResource latestSubmission = submissionResources.getItems().get(0);

                competitorInfo.setHandle(registrant.getHandleName());
                competitorInfo.setRating(registrant.getRating());
                competitorInfo.setProvisionalScore(latestSubmission.getScore());
                competitorInfo.setLastSubmissionTime(latestSubmission.getSubmissionTime());
                competitorInfo.setRank(latestSubmission.getSubmissionRank());
                competitorInfo.setLastSubmissionNumber(latestSubmission.getSubmissionNumber());
                competitorInfo.setLanguage(registrant.getLanguage());
                competitors.add(competitorInfo);
                if(handle != null && handle.equals(competitorInfo.getHandle())) {
                    viewData.setCompetitorInfoDTO(competitorInfo);
                }
                result.getItems().add(registrant);
            }
        }
        // Sort the competitors and set the rank.
        Collections.sort(competitors, new Comparator<CompetitorInfoDTO>() {
            public int compare(CompetitorInfoDTO competitorInfoDTO, CompetitorInfoDTO competitorInfoDTO2) {
                if (competitorInfoDTO.getProvisionalScore() < competitorInfoDTO2.getProvisionalScore()) {
                    return 1;
                }
                return 0;
            }
        });
        for(CompetitorInfoDTO c : competitors) {
            c.setRank(competitors.indexOf(c) + 1);
        }
        viewData.setCompetitors(competitors);
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
        viewData.getCommonInfo().setSystemTestingStart(result.getSystemTestDate());
        viewData.getCommonInfo().setContestStart(result.getStartDate());
        viewData.getCommonInfo().setContestEnd(result.getEndDate());
    }

    /**
     * Get the common data for contest page.
     *
     * @param currentUser the current user instance.
     * @throws Exception if any error occurred.
     */
    private void getCommonData(TCSubject currentUser) throws Exception {
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

        List<ProjectBriefDTO> projects = DataProvider.getUserProjects(sessionData.getCurrentUserId());

        UserProjectsDTO userProjectsDTO = new UserProjectsDTO();
        userProjectsDTO.setProjects(projects);
        viewData.setUserProjects(userProjectsDTO);
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
        this.session = session;
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
     * Sets tab.
     *
     * @param tab the tab
     * @since 1.1
     */
    public void setTab(String tab) {
        this.tab = tab;
    }

    /**
     * Sets handle.
     *
     * @param handle the handle
     * @since 1.1
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }

    /**
     * Gets tab.
     *
     * @return the tab
     * @since 1.1
     */
    public String getTab() {
        return tab;
    }

    /**
     * Gets handle.
     *
     * @return the handle
     * @since 1.1
     */
    public String getHandle() {
        return handle;
    }

    /**
     * Gets view.
     *
     * @return the view
     * @since 1.1
     */
    public String getView() {
        return view;
    }

    /**
     * Sets view.
     *
     * @param view the view
     * @since 1.1
     */
    public void setView(String view) {
        this.view = view;
    }
}

