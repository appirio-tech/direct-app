/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.marathonmatch.service.dto;

import com.topcoder.direct.services.view.dto.contest.BaseContestCommonDTO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * This DTO holds as a container for the all the data to be shown on the active MM view page.
 * </p>
 * <p>
 * <strong>Thread Safety:</strong> This class is mutable and not thread-safe.
 * </p>
 *
 * <p>
 * Version 1.1 - TopCoder Cockpit - Tracking Marathon Matches Progress
 * <ol>
 *     <li>Change the class to extend <code>BaseContestCommonDTO</code> so it will be more easy to prepare data for
 *         common data in contest page.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.2 - Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Competitors Tab
 * <ol>
 *     <li>Add property {@link #competitors}.</li>
 *     <li>Add property {@link #recentSubmissions}.</li>
 * </ol>
 * </p>
 *
 * @author sampath01, zhu_tao, Ghost_141
 * @version 1.2
 * @since 1.0
 */
public class MMInfoDTO extends BaseContestCommonDTO {

    /**
     * The common info DTO
     */
    private MMCommonInfoDTO commonInfo;

    /**
     * The list of registrants in descending order of registration
     */
    private List<RegistrantInfo> registrants;

    /**
     * The list of competitors.
     * @since 1.2
     */
    private List<CompetitorInfoDTO> competitors;

    /**
     * The list of submissions.
     * @since 1.2
     */
    private List<SubmissionInfo> recentSubmissions;

    /**
     * The competitor in competitor submission history page.
     * @since 1.2
     */
    private CompetitorInfoDTO competitorInfoDTO;

    /**
     * The submissions by coder. The coder's handle is the key and the coder's submissions are the values.
     */
    private Map<String, List<SubmissionInfo>> submissionsByCoder;

    /**
     * The submissions by provisional rank. The highest scoring submissions come first in the list.
     */
    private List<SubmissionInfo> submissionsByProvisionalRank;

    /**
     * The JSON data used for showing the Registrants Rating Pie Chart See the front-end assembly spec for expected
     * format
     */
    private String registrantsRatingPieData;

    /**
     * The JSON data used for showing the Registrants Rating Bar Chart See the front-end assembly spec for expected
     * format
     */
    private String registrantsRatingBarData;

    /**
     * The JSON data used for showing the Submitters Rating Pie Chart See the front-end assembly spec for expected
     * format
     */
    private String submittersRatingPieData;

    /**
     * The JSON data used for showing the Submitters Rating Bar Chart See the front-end assembly spec for expected
     * format
     */
    private String submittersRatingBarData;

    /**
     * The JSON data used for showing the Submissions Line Graph See the front-end assembly spec for expected format
     */
    private String submissionsLineGraphData;

    /**
     * The JSON data used for showing the TimeLine Graph See the front-end assembly spec for expected format
     */
    private String timeLineGraphData;

    /**
     * The JSON data used for showing the Submissions History Graph See the front-end assembly spec for expected format
     */
    private String submissionHistoryData;

    /**
     * Default constructor.
     */
    public MMInfoDTO() {
        super();
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public MMCommonInfoDTO getCommonInfo() {
        return commonInfo;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param commonInfo
     *            the name-sake field to set
     */
    public void setCommonInfo(MMCommonInfoDTO commonInfo) {
        this.commonInfo = commonInfo;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public List<RegistrantInfo> getRegistrants() {
        return registrants;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param registrants
     *            the name-sake field to set
     */
    public void setRegistrants(List<RegistrantInfo> registrants) {
        this.registrants = registrants;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public Map<String, List<SubmissionInfo>> getSubmissionsByCoder() {
        return submissionsByCoder;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param submissionsByCoder
     *            the name-sake field to set
     */
    public void setSubmissionsByCoder(Map<String, List<SubmissionInfo>> submissionsByCoder) {
        this.submissionsByCoder = submissionsByCoder;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public List<SubmissionInfo> getSubmissionsByProvisionalRank() {
        return submissionsByProvisionalRank;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param submissionsByProvisionalRank
     *            the name-sake field to set
     */
    public void setSubmissionsByProvisionalRank(List<SubmissionInfo> submissionsByProvisionalRank) {
        this.submissionsByProvisionalRank = submissionsByProvisionalRank;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public String getRegistrantsRatingPieData() {
        return registrantsRatingPieData;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param registrantsRatingPieData
     *            the name-sake field to set
     */
    public void setRegistrantsRatingPieData(String registrantsRatingPieData) {
        this.registrantsRatingPieData = registrantsRatingPieData;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public String getRegistrantsRatingBarData() {
        return registrantsRatingBarData;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param registrantsRatingBarData
     *            the name-sake field to set
     */
    public void setRegistrantsRatingBarData(String registrantsRatingBarData) {
        this.registrantsRatingBarData = registrantsRatingBarData;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public String getSubmittersRatingPieData() {
        return submittersRatingPieData;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param submittersRatingPieData
     *            the name-sake field to set
     */
    public void setSubmittersRatingPieData(String submittersRatingPieData) {
        this.submittersRatingPieData = submittersRatingPieData;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public String getSubmittersRatingBarData() {
        return submittersRatingBarData;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param submittersRatingBarData
     *            the name-sake field to set
     */
    public void setSubmittersRatingBarData(String submittersRatingBarData) {
        this.submittersRatingBarData = submittersRatingBarData;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public String getSubmissionsLineGraphData() {
        return submissionsLineGraphData;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param submissionsLineGraphData
     *            the name-sake field to set
     */
    public void setSubmissionsLineGraphData(String submissionsLineGraphData) {
        this.submissionsLineGraphData = submissionsLineGraphData;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public String getTimeLineGraphData() {
        return timeLineGraphData;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param timeLineGraphData
     *            the name-sake field to set
     */
    public void setTimeLineGraphData(String timeLineGraphData) {
        this.timeLineGraphData = timeLineGraphData;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public String getSubmissionHistoryData() {
        return submissionHistoryData;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param submissionHistoryData
     *            the name-sake field to set
     */
    public void setSubmissionHistoryData(String submissionHistoryData) {
        this.submissionHistoryData = submissionHistoryData;
    }

    /**
     * Gets competitors.
     *
     * @return the competitors
     * @since 1.2
     */
    public List<CompetitorInfoDTO> getCompetitors() {
        return competitors;
    }

    /**
     * Sets competitors.
     *
     * @param competitors the competitors
     * @since 1.2
     */
    public void setCompetitors(List<CompetitorInfoDTO> competitors) {
        this.competitors = competitors;
    }

    /**
     * Gets recent submissions.
     *
     * @return the recent submissions
     */
    public List<SubmissionInfo> getRecentSubmissions() {
        return recentSubmissions;
    }

    /**
     * Sets recent submissions.
     *
     * @param recentSubmissions the recent submissions
     */
    public void setRecentSubmissions(List<SubmissionInfo> recentSubmissions) {
        this.recentSubmissions = recentSubmissions;
    }

    /**
     * Gets competitor info dTO.
     *
     * @return the competitor info dTO
     */
    public CompetitorInfoDTO getCompetitorInfoDTO() {
        return competitorInfoDTO;
    }

    /**
     * Sets competitor info dTO.
     *
     * @param competitorInfoDTO the competitor info dTO
     */
    public void setCompetitorInfoDTO(CompetitorInfoDTO competitorInfoDTO) {
        this.competitorInfoDTO = competitorInfoDTO;
    }
}
