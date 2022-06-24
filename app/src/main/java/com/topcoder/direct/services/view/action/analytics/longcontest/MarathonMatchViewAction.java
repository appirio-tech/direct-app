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
import com.topcoder.marathonmatch.service.dto.CompetitorInfoDTO;
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
import org.joda.time.DateTimeZone;
import org.joda.time.Minutes;

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
 * <p>
 *     Version 1.2 - Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress -
 *          Dashboard and Submissions Tab
 *     <ol>
 *         <li>Add static property {@link #SUBMISSIONS} and {@link #HANDLE_COLOR}.</li>
 *         <li>Add static property {@link #DEFAULT_SUBMISSION_SIZE} and {@link #DEFAULT_SUBMISSION_START}</li>
 *         <li>Add property and {@link #registrants} to help store the data and save the time.</li>
 *         <li>Add property {@link #active} to represent the contest is active or past contest.</li>
 *         <li>Update method {@link #getMMRegistrants(String)} to use String as input parameter.</li>
 *         <li>Remove method {@link #getMarathonMatchDetails(String)}.</li>
 *         <li>Update to use <code>MarathonMatchHelper</code> to get the marathon match details for dashboard part.</li>
 *     </ol>
 * </p>
 *
 * <p>
 *     Version 1.3 - Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Results Tab
 *     <ol>
 *         <li>Remove property active to fix a bug.</li>
 *         <li>Remove method getCommonData, instead use <code>MarathonMatchHelper</code> method getCommonData.</li>
 *     </ol>
 * </p>
 *
 * <p>
 *    Version 1.4 - Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Results Tab 2
 *    <ol>
 *        <li>Update to use viewData.roundId.</li>
 *    </ol>
 * </p>
 *
 * @author Ghost_141
 * @since 1.0 (PoC Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress)
 * @version 1.4
 */
public class MarathonMatchViewAction extends AbstractAction implements ViewAction<MMInfoDTO>, SessionAware {

    /**
     * This field represents the qualified name of this class.
     */
    private static final String CLASS_NAME = MarathonMatchViewAction.class.getName();

    /**
     * Represent the competitors tab.
     * @since 1.1
     */
    private static final String COMPETITORS = "competitors";

    /**
     * Represent the submissions tab.
     * @since 1.2
     */
    private static final String SUBMISSIONS = "submissions";

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
     * Represent the default submission start index.
     * @since 1.2
     */
    private static final Integer DEFAULT_SUBMISSION_START = 1;

    /**
     * Represent the default submission size.
     * @since 1.2
     */
    private static final Integer DEFAULT_SUBMISSION_SIZE = 5;

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
     * Represent the submission start index in submissions line graph.
     * @since 1.2
     */
    private Integer submissionStart;

    /**
     * Represent the submission size in submissions line graph.
     * @since 1.2
     */
    private Integer submissionSize;

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
     * Represent a list of registrant member of this marathon match contest.
     * @since 1.2
     */
    private SearchResult<CompetitorResource> registrants;

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
     * Represent the handle color used in submissions line graph.
     * @since 1.2
     */
    private static final String[] HANDLE_COLOR = {"#f6995c", "#0faff1", "#0ea4a2", "#373a97", "#824325", "#ff0000",
            "#000000", "#ea00ff", "#50a432", "#626b8b"};

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
                MarathonMatchHelper.getMarathonMatchDetails(roundId, marathonMatchAnalyticsService, timelineInterval,
                        viewData);
                viewData.setRoundId(Long.valueOf(roundId));
            }

            // Get the common data for contest page.
            MarathonMatchHelper.getCommonData(projectId, currentUser, softwareCompetition, viewData,
                    contestServiceFacade, getSessionData());

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
            } else if (tab.equals(SUBMISSIONS)) {
                // Submissions tab.
                // Set the default value to submissionStart and submissionSize since this is not a ajax call.
                submissionStart = DEFAULT_SUBMISSION_START;
                submissionSize = DEFAULT_SUBMISSION_SIZE;
                viewSubmissions();
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
     * This method will handle the request for submissions tab.
     *
     * @throws Exception if any error occurred.
     * @since 1.2
     */
    public String viewSubmissions() throws Exception {
        final String signature = CLASS_NAME + "#viewSubmissions()";
        try {
            LoggingWrapperUtility.logEntrance(logger, signature, new String[] {}, new Object[] {});

            if(submissionStart <= 0) {
                throw new IllegalArgumentException("The submissionStart should be large than 1");
            }

            if(softwareCompetition == null) {
                softwareCompetition = contestServiceFacade.getSoftwareContestByProjectId(DirectStrutsActionsHelper
                        .getTCSubjectFromSession(), projectId);
            }

            // Get the round id from the project info.
            String roundId = softwareCompetition.getProjectHeader().getProperty("Marathon Match Id");

            this.registrants = getMMRegistrants(roundId);

            List<CompetitorResource> competitors = new ArrayList<CompetitorResource>();
            for(CompetitorResource registrant : registrants.getItems()) {
                if(registrant.getProvisionalScore() != null) {
                    competitors.add(registrant);
                }
            }
            Collections.sort(competitors, new Comparator<CompetitorResource>() {
                public int compare(CompetitorResource competitorResource, CompetitorResource competitorResource2) {
                    if (competitorResource.getProvisionalScore() > competitorResource2.getProvisionalScore()) {
                        return 0;
                    }
                    if (competitorResource.getProvisionalScore().equals(competitorResource2.getProvisionalScore())) {
                        return String.CASE_INSENSITIVE_ORDER
                                .compare(competitorResource.getHandleName(), competitorResource2.getHandleName());
                    }
                    return 1;
                }
            });

            int pageStart = submissionStart - 1 > competitors.size()
                    ? competitors.size() - submissionSize : submissionStart - 1;
            int pageSize = pageStart + submissionSize > competitors.size()
                    ? competitors.size() : pageStart + submissionSize;

            calculateSubmissionLineData(roundId, competitors.subList(pageStart, pageSize));
            LoggingWrapperUtility.logExit(logger, signature, new String[]{"success"});
            return SUCCESS;
        } catch (Exception e) {
            setResult(e);
            LoggingWrapperUtility.logException(logger, signature, e);
            throw e;
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
                            handle, Integer.MAX_VALUE, 1, "desc", "submissionTime", MarathonMatchHelper.ACCESS_TOKEN);
            if(submissionsHistory.getItems().size() == 0) {
                throw new MarathonMatchAnalyticsServiceException("The user " + handle +
                        " is not a competitor of this contest.");
            }
            List<SubmissionResource> recentSubmissions = submissionsHistory.getItems();

            viewData.getCompetitorInfoDTO().setNoOfFullSubmissions(recentSubmissions.size());

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
            this.registrants = getMMRegistrants(roundId);

            // calculate the json string by given data.
            graphData = calculateGraphData(this.registrants);
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
            this.registrants = getMMRegistrants(roundId);
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

            for(CompetitorResource competitorResource : competitors.getItems()) {
                ObjectNode node = objectMapper.createObjectNode();
                String key = competitorResource.getRatingColor();
                node.put("handle", competitorResource.getHandleName());
                node.put("number", competitorResource.getRating());
                node.put("color", competitorResource.getRatingColorStyle()
                        .substring(7, competitorResource.getRatingColorStyle().length()));
                ratingBarData.add(node);

                pieMap.put(key, pieMap.get(key) + 1);
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
        DateFormat dateFormat = createDateFormat();
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
     * Calculate the submission line data for submission tab.
     *
     * @param roundId the round id.
     * @param competitors the competitors
     * @throws MarathonMatchAnalyticsServiceException if any error occurred.
     * @since 1.2
     */
    private void calculateSubmissionLineData(String roundId, List<CompetitorResource> competitors)
            throws MarathonMatchAnalyticsServiceException {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode submissionsNode = objectMapper.createArrayNode();
        DateTimeZone.setDefault(DateTimeZone.forID("US/Eastern"));
        for(CompetitorResource competitor : competitors) {
            SearchResult<SubmissionResource> submissionResources =
                    marathonMatchAnalyticsService.getCompetitorSubmissionsHistory(Long.valueOf(roundId),
                            competitor.getHandleName(), Integer.MAX_VALUE, 1, "asc", "submissionTime",
                            MarathonMatchHelper.ACCESS_TOKEN);
            ObjectNode submissions = objectMapper.createObjectNode();
            ArrayNode submissionHistory = objectMapper.createArrayNode();
            for(SubmissionResource submissionResource : submissionResources.getItems()) {
                ObjectNode node = objectMapper.createObjectNode();

                DateTime submissionTime = new DateTime(submissionResource.getSubmissionTime());
                DateFormat format = createDateFormat();
                node.put("year", submissionTime.getYear());
                node.put("month", submissionTime.getMonthOfYear() - 1);
                node.put("day", submissionTime.getDayOfMonth());
                node.put("hour", submissionTime.getHourOfDay());
                node.put("minutes", submissionTime.getMinuteOfHour());
                node.put("score", submissionResource.getScore());
                node.put("language", submissionResource.getLanguage());
                node.put("number", submissionResource.getSubmissionNumber());
                node.put("time", format.format(submissionResource.getSubmissionTime()));
                submissionHistory.add(node);
            }
            submissions.put("handle", competitor.getHandleName());
            submissions.put("changeLog", submissionHistory);
            submissions.put("color", HANDLE_COLOR[competitors.indexOf(competitor)]);
            submissionsNode.add(submissions);
        }
        ObjectNode submissionLineData = objectMapper.createObjectNode();
        submissionLineData.put("submissions", submissionsNode);

        ObjectNode startDate = objectMapper.createObjectNode();
        ObjectNode endDate = objectMapper.createObjectNode();
        DateTime sdt, edt;
        if(viewData.getCommonInfo() == null) {
            // This is a ajax call. We need get the start date and end date first.
            MarathonMatchDetailsResource result =
                    marathonMatchAnalyticsService.getMarathonMatchDetails(Long.valueOf(roundId),
                            MarathonMatchHelper.GROUP_TYPE_DAY, MarathonMatchHelper.ACCESS_TOKEN);
            sdt = new DateTime(result.getStartDate());
            edt = new DateTime(result.getEndDate());
        } else {
            sdt = new DateTime(viewData.getCommonInfo().getContestStart());
            edt = new DateTime(viewData.getCommonInfo().getContestEnd());
        }
        startDate.put("year", sdt.getYear());
        startDate.put("month", sdt.getMonthOfYear() - 1);
        startDate.put("day", sdt.getDayOfMonth());
        startDate.put("hour", sdt.getHourOfDay());
        endDate.put("year", edt.getYear());
        endDate.put("month", edt.getMonthOfYear() - 1);
        endDate.put("day", edt.getDayOfMonth());
        endDate.put("hour", edt.getHourOfDay());
        submissionLineData.put("startDate", startDate);
        submissionLineData.put("endDate", endDate);
        viewData.setSubmissionsLineGraphData(submissionLineData.toString());
        Map<String, String> jsonResult = new HashMap<String, String>();
        jsonResult.put("submissionLineData", submissionLineData.toString());
        setResult(jsonResult);
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
     * Create a date format instance.
     *
     * @return the date format.
     * @since 1.2
     */
    private DateFormat createDateFormat() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm z");
        dateFormat.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
        return dateFormat;
    }

    /**
     * Get the registrants of this marathon match.
     *
     * - Version 1.2 - Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress -
     *   Dashboard and Submissions Tab
     * - Change the round id to <code>String</code> type.
     * - Update to return the registrants directly if it's not null which means this method has been called before in
     *   this request.
     * - Update to separate the logic of get registrants and set registrants.
     *
     * @param roundId the round id
     * @return the result from service.
     * @throws MarathonMatchAnalyticsServiceException the marathon match analytics service exception
     */
    private SearchResult<CompetitorResource> getMMRegistrants(String roundId)
            throws MarathonMatchAnalyticsServiceException {
        if(this.registrants != null) {
            return this.registrants;
        }
        SearchResult<CompetitorResource> result =
                marathonMatchAnalyticsService.getRegistrants(Long.valueOf(roundId), MarathonMatchHelper.ACCESS_TOKEN);
        convertRegistrants(result);
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
                                registrant.getHandleName(), 1, 1, "desc", "submissionTime",
                                MarathonMatchHelper.ACCESS_TOKEN);

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
     * This method will convert <code>CompetitorResource</code> instance to <code>RegistrantInfo</code> instance and
     * store them in viewData.
     *
     * @param registrants The registrants
     * @since 1.2
     */
    private void convertRegistrants(SearchResult<CompetitorResource> registrants) {
        List<RegistrantInfo> registrantInfos = new ArrayList<RegistrantInfo>();
        for(CompetitorResource competitorResource : registrants.getItems()) {
            RegistrantInfo registrant = new RegistrantInfo();

            registrant.setHandle(competitorResource.getHandleName());
            registrant.setRating(competitorResource.getRating());
            registrant.setRegistrationTime(competitorResource.getRegistrationDate());
            registrant.setUserId(competitorResource.getHandleId());
            registrantInfos.add(registrant);
        }

        if(viewData == null) {
            viewData = new MMInfoDTO();
        }
        viewData.setRegistrants(registrantInfos);
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

    /**
     * Gets submission start.
     *
     * @return the submission start
     * @since 1.2
     */
    public int getSubmissionStart() {
        return submissionStart;
    }

    /**
     * Sets submission start.
     *
     * @param submissionStart the submission start
     * @since 1.2
     */
    public void setSubmissionStart(int submissionStart) {
        this.submissionStart = submissionStart;
    }

    /**
     * Gets submission size.
     *
     * @return the submission size
     * @since 1.2
     */
    public int getSubmissionSize() {
        return submissionSize;
    }

    /**
     * Sets submission size.
     *
     * @param submissionSize the submission size
     * @since 1.2
     */
    public void setSubmissionSize(int submissionSize) {
        this.submissionSize = submissionSize;
    }
}

