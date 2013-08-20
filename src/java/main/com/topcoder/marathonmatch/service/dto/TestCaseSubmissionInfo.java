/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.marathonmatch.service.dto;

import java.util.List;

/**
 * This DTO contains the information for a submission that has been tested.
 * <p>
 * <strong>Thread Safety:</strong> This class is mutable and not thread-safe.
 * </p>
 *
 * <p>
 *     Version 1.1 - Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Results Tab 2
 *     <ol>
 *         <li>Add property {@link #testCases} to store the test cases info.</li>
 *         <li>Add property {@link #userId} to store the user id.</li>
 *     </ol>
 * </p>
 * 
 * @author sampath01, zhu_tao, Ghost_141
 * @version 1.1
 * @since 1.0
 */
public class TestCaseSubmissionInfo {

    /**
     * The coder of the submission
     */
    private String handle;

    /**
     * The coder id of this competitor.
     * @since 1.1
     */
    private Long userId;

    /**
     * The final score of the submission
     */
    private Double finalScore;

    /**
     * The test cases of this competitor.
     */
    private List<TestCaseInfo> testCases;
    
    /**
     * Default constructor.
     */
    public TestCaseSubmissionInfo() {
        super();
    }

    /**
     * This method provides a custom hash code calculation approach.
     * 
     * @return Hash code of this instance.
     */
    public int hashCode() {
        int result = 17;
        if (null != handle) {
            result = 37 * result + handle.hashCode();
        }
        long toLong = Double.doubleToLongBits(finalScore);
        result = 37 * result + (int) (toLong ^ (toLong >>> 32));
        return result;
    }

    /**
     * This method tests if the given instance is identical to current one.
     * 
     * @param obj
     *            A second instance to compare.
     * @return true if identical; false otherwise.
     */
    public boolean equals(Object obj) {
        if (null == obj || !(obj instanceof TestCaseSubmissionInfo)) {
            return false;
        } else {
            TestCaseSubmissionInfo info = (TestCaseSubmissionInfo) obj;
            // compare final score.
            if ((null == finalScore && null != info.getFinalScore())
                    || (null != finalScore && (!finalScore.equals(info.getFinalScore())))) {
                return false;
            }
            // compare handle.
            if ((null == handle && null != info.getHandle()) 
                    || (null != handle && (!handle.equals(info.getHandle())))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public String getHandle() {
        return handle;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param handle
     *            the name-sake field to set
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public Double getFinalScore() {
        return finalScore;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param finalScore
     *            the name-sake field to set
     */
    public void setFinalScore(Double finalScore) {
        this.finalScore = finalScore;
    }

    /**
     * Gets test cases.
     *
     * @return the test cases
     * @since 1.1
     */
    public List<TestCaseInfo> getTestCases() {
        return testCases;
    }

    /**
     * Sets test cases.
     *
     * @param testCases the test cases
     * @since 1.1
     */
    public void setTestCases(List<TestCaseInfo> testCases) {
        this.testCases = testCases;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     * @since 1.1
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     * @since 1.1
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
