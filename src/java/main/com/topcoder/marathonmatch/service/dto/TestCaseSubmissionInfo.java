/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.marathonmatch.service.dto;

/**
 * This DTO contains the information for a submission that has been tested.
 * <p>
 * <strong>Thread Safety:</strong> This class is mutable and not thread-safe.
 * </p>
 * 
 * @author sampath01, zhu_tao
 * @version 1.0
 * @since 1.0
 */
public class TestCaseSubmissionInfo {

    /**
     * The coder of the submission
     */
    private String handle;

    /**
     * The final score of the submission
     */
    private Double finalScore;
    
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
}
