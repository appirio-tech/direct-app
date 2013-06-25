/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.marathonmatch.service.dto;

import java.util.Date;

/**
 * This DTO holds a single submission information.
 * <p>
 * <strong>Thread Safety:</strong> This class is mutable and not thread-safe.
 * </p>
 * 
 * @author sampath01, zhu_tao
 * @version 1.0
 * @since 1.0
 */
public class SubmissionInfo {
    /**
     * The submission number of the current submission. <strong>NOTE:</strong>This is unique only for the given
     * submitter of the given problem of the given MM. It is NOT globally unique.
     */
    private Integer submissionNumber;

    /**
     * The date-time at which the submission was made
     */
    private Date submissionTime;

    /**
     * The language of teh submission
     */
    private String language;

    /**
     * The provisional score of the submission.
     */
    private Double provisionalScore;

    /**
     * The coder whose submission this is.
     */
    private String handle;
    
    /**
     * Default constructor.
     */
    public SubmissionInfo() {
        super();
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public Integer getSubmissionNumber() {
        return submissionNumber;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param submissionNumber
     *            the name-sake field to set
     */
    public void setSubmissionNumber(Integer submissionNumber) {
        this.submissionNumber = submissionNumber;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public Date getSubmissionTime() {
        return submissionTime;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param submissionTime
     *            the name-sake field to set
     */
    public void setSubmissionTime(Date submissionTime) {
        this.submissionTime = submissionTime;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param language
     *            the name-sake field to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public Double getProvisionalScore() {
        return provisionalScore;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param provisionalScore
     *            the name-sake field to set
     */
    public void setProvisionalScore(Double provisionalScore) {
        this.provisionalScore = provisionalScore;
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

}
