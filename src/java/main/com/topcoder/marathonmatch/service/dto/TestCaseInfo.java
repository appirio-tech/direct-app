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
 * @author sampath01, zhu_tao
 * @version 1.0
 * @since 1.0
 */
public class TestCaseInfo {

    /**
     * The name of the test case (usually this is just Test Case 1, Test Case 2,...)
     */
    private String testCaseName;

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
    public String getTestCaseName() {
        return testCaseName;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param testCaseName
     *            the name-sake field to set
     */
    public void setTestCaseName(String testCaseName) {
        this.testCaseName = testCaseName;
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

}
