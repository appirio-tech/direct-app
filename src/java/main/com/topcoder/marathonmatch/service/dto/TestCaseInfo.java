/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.marathonmatch.service.dto;

/**
 * This DTO consists of a particular DTO's test case information.
 * <p>
 * <strong>Thread Safety:</strong> This class is mutable and not thread-safe.
 * </p>
 *
 * <p>
 *     Version 1.1 - Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Results Tab 2
 *     <ol>
 *         <li>Update property testCaseName to testCaseId since testCaseName is not existed.</li>
 *     </ol>
 * </p>
 * 
 * @author sampath01, zhu_tao, Ghost_141
 * @version 1.1
 * @since 1.0
 */
public class TestCaseInfo {

    /**
     * The name of the test case (usually this is just Test Case 1, Test Case 2,...)
     */
    private Long testCaseId;

    /**
     * The score for this particular test case
     */
    private Double testCaseScore;

    /**
     * Default constructor.
     */
    public TestCaseInfo() {
        super();
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public Double getTestCaseScore() {
        return testCaseScore;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param testCaseScore
     *            the name-sake field to set
     */
    public void setTestCaseScore(Double testCaseScore) {
        this.testCaseScore = testCaseScore;
    }

    /**
     * Gets test case id.
     *
     * @return the test case id
     * @since 1.1
     */
    public Long getTestCaseId() {
        return testCaseId;
    }

    /**
     * Sets test case id.
     *
     * @param testCaseId the test case id
     * @since 1.1
     */
    public void setTestCaseId(Long testCaseId) {
        this.testCaseId = testCaseId;
    }
}
