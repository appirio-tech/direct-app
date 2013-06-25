/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.marathonmatch.service.dto;

import java.util.List;
import java.util.Map;

/**
 * This DTO holds as a container for the all the data to be shown on the MM results page.
 * <p>
 * <strong>Thread Safety:</strong> This class is mutable and not thread-safe.
 * </p>
 * 
 * @author sampath01, zhu_tao
 * @version 1.0
 * @since 1.0
 */
public class MMResultsInfoDTO {

    /**
     * The common info DTO
     */
    private MMCommonInfoDTO commonInfo;

    /**
     * The list of registrants in descending order of registration
     */
    private List<ResultInfo> results;

    /**
     * The submissions by coder. The coder's handle is the key and the coder's submissions are the values.
     */
    private Map<String, List<SubmissionInfo>> submissionsByCoder;

    /**
     * The test cases by submission. The key is the submission for which we have the test cases. The value is the list
     * of test cases and their results.
     */
    private Map<TestCaseSubmissionInfo, List<TestCaseInfo>> testCasesBySubmission;

    /**
     * The JSON data used for showing the Final Score vs Ranking Chart See the front-end assembly spec for expected
     * format
     */
    private String finalScoreRankingData;

    /**
     * The JSON data used for showing the Final Score vs Provisional Score Chart See the front-end assembly spec for
     * expected format
     */
    private String finalVsProvisionalScoreData;

    /**
     * The JSON data used for showing the TimeLine Graph. See the front-end assembly spec for expected format.
     */
    private String timeLineGraphData;

    /**
     * Default constructor.
     */
    public MMResultsInfoDTO() {
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
    public List<ResultInfo> getResults() {
        return results;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param results
     *            the name-sake field to set
     */
    public void setResults(List<ResultInfo> results) {
        this.results = results;
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
    public Map<TestCaseSubmissionInfo, List<TestCaseInfo>> getTestCasesBySubmission() {
        return testCasesBySubmission;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param testCasesBySubmission
     *            the name-sake field to set
     */
    public void setTestCasesBySubmission(Map<TestCaseSubmissionInfo, List<TestCaseInfo>> testCasesBySubmission) {
        this.testCasesBySubmission = testCasesBySubmission;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public String getFinalScoreRankingData() {
        return finalScoreRankingData;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param finalScoreRankingData
     *            the name-sake field to set
     */
    public void setFinalScoreRankingData(String finalScoreRankingData) {
        this.finalScoreRankingData = finalScoreRankingData;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public String getFinalVsProvisionalScoreData() {
        return finalVsProvisionalScoreData;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param finalVsProvisionalScoreData
     *            the name-sake field to set
     */
    public void setFinalVsProvisionalScoreData(String finalVsProvisionalScoreData) {
        this.finalVsProvisionalScoreData = finalVsProvisionalScoreData;
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

}
