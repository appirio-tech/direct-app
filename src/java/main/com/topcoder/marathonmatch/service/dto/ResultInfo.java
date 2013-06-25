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
 * @author sampath01, zhu_tao
 * @version 1.0
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

}
