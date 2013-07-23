/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.analytics.longcontest;

import com.topcoder.direct.services.view.action.analytics.longcontest.services.MarathonMatchAnalyticsService;
import com.topcoder.direct.services.view.action.analytics.longcontest.services.MarathonMatchAnalyticsServiceException;
import com.topcoder.direct.services.view.dto.contest.MarathonMatchCommonDTO;
import com.topcoder.marathonmatch.service.dto.CompetitorInfoDTO;
import com.topcoder.marathonmatch.service.dto.MMCommonInfoDTO;
import com.topcoder.web.tc.rest.longcontest.resources.CompetitorResource;
import com.topcoder.web.tc.rest.longcontest.resources.MarathonMatchDetailsResource;
import com.topcoder.web.tc.rest.longcontest.resources.MarathonMatchItemResource;
import com.topcoder.web.tc.rest.longcontest.resources.MatchResultResource;
import com.topcoder.web.tc.rest.longcontest.resources.ProgressResource;
import com.topcoder.web.tc.rest.longcontest.resources.SearchResult;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TimeZone;

/**
 * This class contain the common method for dashboard part in contest page.
 *
 * <p>
 * <strong>Thread Safety:</strong> This class is mutable and not thread-safe.
 * </p>
 *
 * @author Ghost_141
 * @version 1.0
 * @since 1.0 (Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - 
 *             Dashboard and Submissions Tab)
 */
public final class MarathonMatchHelper {

    /**
     * Represent the dummy access token. This is just for test purpose. The access token itself means nothing.
     */
    public static final String ACCESS_TOKEN = "testAccessToken";

    /**
     * Represent the hour group type.
     */
    public static final String GROUP_TYPE_HOUR = "hour";

    /**
     * Represent the day group type.
     * @since 1.2
     */
    public static final String GROUP_TYPE_DAY = "day";

    /**
     * Get the marathon match details information of this marathon match contest.
     *
     * @param roundId the round id of this contest.
     * @throws MarathonMatchAnalyticsServiceException if any error occurred.
     */
    public static void getMarathonMatchDetails(String roundId,
            MarathonMatchAnalyticsService marathonMatchAnalyticsService, boolean active, int timelineInterval,
            MarathonMatchCommonDTO viewData) throws MarathonMatchAnalyticsServiceException {
        MarathonMatchDetailsResource result =
                marathonMatchAnalyticsService.getMarathonMatchDetails(Long.valueOf(roundId), GROUP_TYPE_DAY,
                        ACCESS_TOKEN);

        // TODO: This way isn't straightforward.
        SearchResult<MarathonMatchItemResource> matchList =
                marathonMatchAnalyticsService.getMarathonMatchListings("active", null, null, 50, 1, null, null,
                        ACCESS_TOKEN);
        for(MarathonMatchItemResource mmContest : matchList.getItems()) {
            if(mmContest.getRoundId() == Long.valueOf(roundId)) {
                active = true;
                break;
            }
        }

        // Get the latest progress, since the progress is sorted by date in asc.
        // So we just get the last one which means the latest one.
        List<ProgressResource> progress = result.getCurrentProgress().getProgressResources();
        ProgressResource latestProgress = progress.get(progress.size() - 1);

        viewData.setCommonInfo(new MMCommonInfoDTO());
        viewData.getCommonInfo().setNumCompetitors(result.getNoOfCompetitors());
        viewData.getCommonInfo().setNumRegistrants(result.getNoOfRegistrants());
        viewData.getCommonInfo().setNumSubmissions(result.getNoOfSubmissions());
        viewData.getCommonInfo().setSystemTestingStart(result.getSystemTestDate());
        viewData.getCommonInfo().setContestStart(result.getStartDate());
        viewData.getCommonInfo().setContestEnd(result.getEndDate());

        if(active) {
            viewData.getCommonInfo().setBestScoreHandle(latestProgress.getTopUserHandle());
            // TODO: The platform api didn't include user id for the user currently having best score.
            viewData.getCommonInfo().setBestScore(latestProgress.getCurrentTopProvisionalScore());

        } else {
            viewData.getCommonInfo().setBestScore(result.getWinnerScore());
            viewData.getCommonInfo().setBestScoreHandle(result.getWinnerHandle());
        }

        // Prepare the Current Standing.
        viewData.setTopCompetitors(new ArrayList<CompetitorInfoDTO>());
        if(active) {
            // The contest is still in progress.
            SearchResult<CompetitorResource> registrants =
                    marathonMatchAnalyticsService.getRegistrants(Long.valueOf(roundId), ACCESS_TOKEN);;
            List<CompetitorResource> registrantsItems = registrants.getItems();
            List<CompetitorResource> competitors = new ArrayList<CompetitorResource>();
            // Get the competitors.
            for(CompetitorResource registrant : registrantsItems) {
                if(registrant.getProvisionalScore() != null) {
                    competitors.add(registrant);
                }
            }
            Collections.sort(competitors, new Comparator<CompetitorResource>() {
                public int compare(CompetitorResource competitorResource, CompetitorResource competitorResource2) {
                    if (competitorResource.getProvisionalScore() > competitorResource2.getProvisionalScore()) {
                        return 0;
                    }
                    return 1;
                }
            });
            for(CompetitorResource competitor : competitors.subList(0,
                    competitors.size() > 3 ? 3 : competitors.size())) {
                CompetitorInfoDTO competitorInfo = new CompetitorInfoDTO();
                competitorInfo.setRating(competitor.getRating());
                competitorInfo.setHandle(competitor.getHandleName());
                competitorInfo.setUserId(competitor.getHandleId());
                competitorInfo.setProvisionalScore(competitor.getProvisionalScore());
                viewData.getTopCompetitors().add(competitorInfo);
            }
            // Set rank.
            for(int i = 0; i < viewData.getTopCompetitors().size(); i++) {
                viewData.getTopCompetitors().get(i).setRank(i + 1);
            }
        } else {
            // The contest is over.
            SearchResult<MatchResultResource> matchResults =
                    marathonMatchAnalyticsService.getMatchResults(Long.valueOf(roundId), 3, 1, "desc", "finalScore",
                            ACCESS_TOKEN);
            for(MatchResultResource matchResult : matchResults.getItems()) {
                CompetitorInfoDTO competitor = new CompetitorInfoDTO();
                competitor.setFinalScore(matchResult.getFinalScore());
                // TODO: The rating is not supported right now.
//                competitor.setRating(matchResult.ge);
                competitor.setHandle(matchResult.getHandleName());
                competitor.setUserId(matchResult.getCoderId());
                competitor.setRank(matchResult.getRank());
                viewData.getTopCompetitors().add(competitor);
            }
        }

        // Fill up the top 3 competitors list with null object if the top competitors are less than 3.
        CompetitorInfoDTO nullCompetitor = new CompetitorInfoDTO();
        while(viewData.getTopCompetitors().size() < 3) {
            viewData.getTopCompetitors().add(nullCompetitor);
        }

        calculateTimeLineData(progress, active, timelineInterval, viewData);
    }

    /**
     * Calculate the time line graph data by given progress resources.
     *
     * @param progress the progress resource.
     */
    public static void calculateTimeLineData(List<ProgressResource> progress, boolean active, int timelineInterval,
                                             MarathonMatchCommonDTO viewData) {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode provisionalScoreDetail = objectMapper.createArrayNode();
        ArrayNode competitorsScoreDetail = objectMapper.createArrayNode();
        ArrayNode fullSubmissionsScoreDetail = objectMapper.createArrayNode();

        DateFormat dateFormat = createDateFormat();

        // TODO: The result date is not supported now. So just use system Test date as the end date of contest.
        DateTime submissionPhase = new DateTime(viewData.getCommonInfo().getContestStart());
        DateTime systemTesting = new DateTime(viewData.getCommonInfo().getSystemTestingStart());

        // Because based on hours will make the time line graph messed. So based on days for now.
        int xLength = Days.daysBetween(submissionPhase, systemTesting).getDays() / timelineInterval;

        int days = Days.daysBetween(submissionPhase, new DateTime()).getDays();
        int currentInterval = days > xLength ? -1 : days;

        int maxSubNum = 0;
        int maxComNum = 0;
        double maxScore = 0.0;

        for(ProgressResource progressResource : progress) {
            ObjectNode competitorsNum = objectMapper.createObjectNode();
            ObjectNode submissionsNum = objectMapper.createObjectNode();
            ObjectNode provisionalScore = objectMapper.createObjectNode();

            if(progress.indexOf(progressResource) == progress.size() - 1) {
                competitorsNum.put("enabled", 1);
                submissionsNum.put("enabled", 1);
                provisionalScore.put("enabled", 1);
            }

            competitorsNum.put("score", progressResource.getCurrentNoOfcompetitors());
            if(progressResource.getCurrentNoOfcompetitors() > maxComNum) {
                competitorsNum.put("enabled", 1);
                maxComNum = progressResource.getCurrentNoOfcompetitors();
            }
            competitorsScoreDetail.add(competitorsNum);

            submissionsNum.put("score", progressResource.getCurrentNoOfSubmissions());
            if(progressResource.getCurrentNoOfSubmissions() > maxSubNum) {
                submissionsNum.put("enabled", 1);
                maxSubNum = progressResource.getCurrentNoOfSubmissions();
            }
            fullSubmissionsScoreDetail.add(submissionsNum);

            provisionalScore.put("score", progressResource.getCurrentTopProvisionalScore());
            if(progressResource.getCurrentTopProvisionalScore() > maxScore) {
                provisionalScore.put("enabled", 1);
                maxScore = progressResource.getCurrentTopProvisionalScore();
            }
            provisionalScoreDetail.add(provisionalScore);
        }

        // Fill up with null node.
        ObjectNode nullNode = objectMapper.createObjectNode();
        for(int i = progress.size(); i < xLength; i++) {
            provisionalScoreDetail.add(nullNode);
            fullSubmissionsScoreDetail.add(nullNode);
            competitorsScoreDetail.add(nullNode);
        }

        ObjectNode provisionalScoreNode = objectMapper.createObjectNode();
        ObjectNode fullSubmissionsNode = objectMapper.createObjectNode();
        ObjectNode competitorsNode = objectMapper.createObjectNode();

        if(active) {
            provisionalScoreNode.put("name", "Best Provisional Score");
        } else {
            provisionalScoreNode.put("name", "Best Final Score");
        }

        provisionalScoreNode.put("scoreDetail", provisionalScoreDetail);

        fullSubmissionsNode.put("name", "Number of Full Submissions");
        fullSubmissionsNode.put("scoreDetail", fullSubmissionsScoreDetail);

        competitorsNode.put("name", "Number of Competitors");
        competitorsNode.put("scoreDetail", competitorsScoreDetail);

        ArrayNode score = objectMapper.createArrayNode();
        score.add(provisionalScoreNode);
        score.add(fullSubmissionsNode);
        score.add(competitorsNode);

        ObjectNode timeLineGraphData = objectMapper.createObjectNode();

        timeLineGraphData.put("score", score);
        timeLineGraphData.put("xLength", xLength);
        if(currentInterval >= 0) {
            timeLineGraphData.put("currentInterval", currentInterval);
        }
        timeLineGraphData.put("submissionPhase", dateFormat.format(submissionPhase.toDate()));
        timeLineGraphData.put("systemTesting", dateFormat.format(systemTesting.toDate()));
        timeLineGraphData.put("active", active);

        viewData.setTimeLineGraphData(timeLineGraphData.toString());
    }

    /**
     * Create a date format instance.
     *
     * @return the date format.
     */
    public static DateFormat createDateFormat() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm z");
        dateFormat.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
        return dateFormat;
    }
}
