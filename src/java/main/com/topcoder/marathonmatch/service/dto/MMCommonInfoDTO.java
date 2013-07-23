/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.marathonmatch.service.dto;

import java.util.Date;

/**
 * <p>
 * This class contains common information about the MM.
 * </p>
 *
 * <p>
 *     Version 1.1 - Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Dashboard and Submissions Tab
 *     <ol>
 *         <li>Add property {@link #bestScoreUserId}.</li>
 *     </ol>
 * </p>
 *
 * <p>
 * <strong>Thread Safety:</strong> This class is fully mutable and not thread-safe.
 * </p>
 * 
 * @author sampath01, zhu_tao, Ghost_141
 * @version 1.1
 * @since 1.0
 */
public class MMCommonInfoDTO {
    /**
     * The handle of user currently having the best score
     */
    private String bestScoreHandle;

    /**
     * The user id of user currently having the best score.
     * @since 1.1
     */
    private Long bestScoreUserId;

    /**
     * The current best score of the MM
     */
    private Double bestScore;

    /**
     * The start date-time of the MM contest
     */
    private Date contestStart;

    /**
     * The submission end and system test starting date-time of the MM contest
     */
    private Date systemTestingStart;

    /**
     * The system test end date-time of the MM contest
     */
    private Date contestEnd;

    /**
     * The number of registrants of the MM
     */
    private int numRegistrants;

    /**
     * The number of competitors of the MM i.e. number of registrants who have submitted at least once for the MM
     */
    private int numCompetitors;

    /**
     * The total number of submissions so far for the MM
     */
    private int numSubmissions;

    /**
     * The name of the marathon match
     */
    private String name;

    /**
     * Default constructor.
     */
    public MMCommonInfoDTO() {
        super();
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public String getBestScoreHandle() {
        return bestScoreHandle;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param bestScoreHandle
     *            the name-sake field to set
     */
    public void setBestScoreHandle(String bestScoreHandle) {
        this.bestScoreHandle = bestScoreHandle;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public Double getBestScore() {
        return bestScore;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param bestScore
     *            the name-sake field to set
     */
    public void setBestScore(Double bestScore) {
        this.bestScore = bestScore;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public Date getContestStart() {
        return contestStart;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param contestStart
     *            the name-sake field to set
     */
    public void setContestStart(Date contestStart) {
        this.contestStart = contestStart;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public Date getSystemTestingStart() {
        return systemTestingStart;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param systemTestingStart
     *            the name-sake field to set
     */
    public void setSystemTestingStart(Date systemTestingStart) {
        this.systemTestingStart = systemTestingStart;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public Date getContestEnd() {
        return contestEnd;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param contestEnd
     *            the name-sake field to set
     */
    public void setContestEnd(Date contestEnd) {
        this.contestEnd = contestEnd;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public int getNumRegistrants() {
        return numRegistrants;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param numRegistrants
     *            the name-sake field to set
     */
    public void setNumRegistrants(int numRegistrants) {
        this.numRegistrants = numRegistrants;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public int getNumCompetitors() {
        return numCompetitors;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param numCompetitors
     *            the name-sake field to set
     */
    public void setNumCompetitors(int numCompetitors) {
        this.numCompetitors = numCompetitors;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public int getNumSubmissions() {
        return numSubmissions;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param numSubmissions
     *            the name-sake field to set
     */
    public void setNumSubmissions(int numSubmissions) {
        this.numSubmissions = numSubmissions;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param name
     *            the name-sake field to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets best score user id.
     *
     * @return the best score user id
     * @since 1.1
     */
    public Long getBestScoreUserId() {
        return bestScoreUserId;
    }

    /**
     * Sets best score user id.
     *
     * @param bestScoreUserId the best score user id
     * @since 1.1
     */
    public void setBestScoreUserId(Long bestScoreUserId) {
        this.bestScoreUserId = bestScoreUserId;
    }
}
