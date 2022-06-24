/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.marathonmatch.service.dto;

import com.topcoder.direct.services.view.dto.contest.MarathonMatchCommonDTO;

import java.util.List;
import java.util.Map;

/**
 * This DTO holds as a container for the all the data to be shown on the MM results page.
 * <p>
 * <strong>Thread Safety:</strong> This class is mutable and not thread-safe.
 * </p>
 *
 * <p>
 *     Version 1.1 - Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Results Tab
 *     <ol>
 *         <li>Update this class to extend the <code>MarathonMatchCommonDTO</code>.</li>
 *     </ol>
 * </p>
 *
 * <p>
 *     Version 1.2 - Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Results Tab 2
 *     <ol>
 *         <li>Add property {@link #competitorsTestCases} to store the test cases info for competitors.</li>
 *         <li>Add property {@link #testCasesStartNumber} and {@link #testCasesEndNumber} to store the index of test
 *         cases.</li>
 *         <li>Add property {@link #codersStartNumber} and {@link #codersEndNumber} to store the index of coders.</li>
 *         <li>Add property {@link #codersCount} and {@link #testCasesCount} to store the number of coders and
 *         test cases.</li>
 *     </ol>
 * </p>
 * 
 * @author sampath01, zhu_tao, Ghost_141
 * @version 1.2
 * @since 1.0
 */
public class MMResultsInfoDTO extends MarathonMatchCommonDTO {

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
     * The test cases list for competitors.
     * @since 1.2
     */
    private List<TestCaseSubmissionInfo> competitorsTestCases;

    /**
     * The test case start number.
     * @since 1.2
     */
    private Integer testCasesStartNumber;

    /**
     * The test case end number.
     * @since 1.2
     */
    private Integer testCasesEndNumber;

    /**
     * The number of test cases.
     * @since 1.2
     */
    private Integer testCasesCount;

    /**
     * The coders start number.
     * @since 1.2
     */
    private Integer codersStartNumber;

    /**
     * The coders end number.
     * @since 1.2
     */
    private Integer codersEndNumber;

    /**
     * The number of coders.
     * @since 1.2
     */
    private Integer codersCount;

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
     * Gets competitors test cases.
     *
     * @return the competitors test cases
     * @since 1.2
     */
    public List<TestCaseSubmissionInfo> getCompetitorsTestCases() {
        return competitorsTestCases;
    }

    /**
     * Sets competitors test cases.
     *
     * @param competitorsTestCases the competitors test cases
     * @since 1.2
     */
    public void setCompetitorsTestCases(List<TestCaseSubmissionInfo> competitorsTestCases) {
        this.competitorsTestCases = competitorsTestCases;
    }

    /**
     * Gets test case start number.
     *
     * @return the test case start number
     * @since 1.2
     */
    public Integer getTestCasesStartNumber() {
        return testCasesStartNumber;
    }

    /**
     * Sets test case start number.
     *
     * @param testCasesStartNumber the test case start number
     * @since 1.2
     */
    public void setTestCasesStartNumber(Integer testCasesStartNumber) {
        this.testCasesStartNumber = testCasesStartNumber;
    }

    /**
     * Gets test case end number.
     *
     * @return the test case end number
     * @since 1.2
     */
    public Integer getTestCasesEndNumber() {
        return testCasesEndNumber;
    }

    /**
     * Sets test case end number.
     *
     * @param testCasesEndNumber the test case end number
     * @since 1.2
     */
    public void setTestCasesEndNumber(Integer testCasesEndNumber) {
        this.testCasesEndNumber = testCasesEndNumber;
    }

    /**
     * Gets coders start number.
     *
     * @return the coders start number
     * @since 1.2
     */
    public Integer getCodersStartNumber() {
        return codersStartNumber;
    }

    /**
     * Sets coders start number.
     *
     * @param codersStartNumber the coders start number
     * @since 1.2
     */
    public void setCodersStartNumber(Integer codersStartNumber) {
        this.codersStartNumber = codersStartNumber;
    }

    /**
     * Gets coders end number.
     *
     * @return the coders end number
     * @since 1.2
     */
    public Integer getCodersEndNumber() {
        return codersEndNumber;
    }

    /**
     * Sets coders end number.
     *
     * @param codersEndNumber the coders end number
     * @since 1.2
     */
    public void setCodersEndNumber(Integer codersEndNumber) {
        this.codersEndNumber = codersEndNumber;
    }

    /**
     * Gets test cases count.
     *
     * @return the test cases count
     * @since 1.2
     */
    public Integer getTestCasesCount() {
        return testCasesCount;
    }

    /**
     * Sets test cases count.
     *
     * @param testCasesCount the test cases count
     * @since 1.2
     */
    public void setTestCasesCount(Integer testCasesCount) {
        this.testCasesCount = testCasesCount;
    }

    /**
     * Gets coders count.
     *
     * @return the coders count
     * @since 1.2
     */
    public Integer getCodersCount() {
        return codersCount;
    }

    /**
     * Sets coders count.
     *
     * @param codersCount the coders count
     * @since 1.2
     */
    public void setCodersCount(Integer codersCount) {
        this.codersCount = codersCount;
    }
}
