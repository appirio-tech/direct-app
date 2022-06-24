/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.marathonmatch.service.dto;

/**
 * This DTO contains a coder's final result information.
 * <p>
 * <strong>Thread Safety:</strong> This class is mutable and not thread-safe.
 * </p>
 *
 * <p>
 *     Version 1.1 - Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Results Tab
 *     <ol>
 *         <li>Add property {@link #userId} and {@link #submissionNumber}</li>
 *     </ol>
 * </p>
 *
 * <p>
 *     Version 1.2 - Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Results Tab 2
 *     <ol>
 *         <li>Add property {@link #place}.</li>
 *     </ol>
 * </p>
 * 
 * @author sampath01, zhu_tao, Ghost_141
 * @version 1.2
 * @since 1.0
 */
public class ResultInfo {
    /**
     * The name of the coder
     */
    private String handle;

    /**
     * The final score of the coder
     */
    private Double finalScore;

    /**
     * The provisional score of the coder (before system testing)
     */
    private Double provisionalScore;

    /**
     * The provisional rank (before system testing)
     */
    private int provisionalRank;

    /**
     * The language used by coder
     */
    private String language;

    /**
     * The final rank of the coder
     */
    private int finalRank;

    /**
     * Represent the user id
     * @since 1.1
     */
    private Long userId;

    /**
     * Represent the place that this user in. This property is different with rank. The rank can be duplicate. The place
     * can't be duplicate. It will indicate the place of user in system tests list.
     * @since 1.2
     */
    private int place;

    /**
     * Represent the number of last submission for the competitor.
     * @since 1.1
     */
    private int submissionNumber;

    /**
     * Default constructor.
     */
    public ResultInfo() {
        super();
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
    public int getProvisionalRank() {
        return provisionalRank;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param provisionalRank
     *            the name-sake field to set
     */
    public void setProvisionalRank(int provisionalRank) {
        this.provisionalRank = provisionalRank;
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
    public int getFinalRank() {
        return finalRank;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param finalRank
     *            the name-sake field to set
     */
    public void setFinalRank(int finalRank) {
        this.finalRank = finalRank;
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

    /**
     * Gets submission number.
     *
     * @return the submission number
     * @since 1.1
     */
    public int getSubmissionNumber() {
        return submissionNumber;
    }

    /**
     * Sets submission number.
     *
     * @param submissionNumber the submission number
     * @since 1.1
     */
    public void setSubmissionNumber(int submissionNumber) {
        this.submissionNumber = submissionNumber;
    }

    /**
     * Gets place.
     *
     * @return the place
     * @since 1.2
     */
    public int getPlace() {
        return place;
    }

    /**
     * Sets place.
     *
     * @param place the place
     * @since 1.2
     */
    public void setPlace(int place) {
        this.place = place;
    }
}
