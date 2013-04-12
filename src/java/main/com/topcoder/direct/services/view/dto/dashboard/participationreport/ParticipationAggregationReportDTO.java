/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard.participationreport;

import java.io.Serializable;

/**
 * <p>
 * The DTO stores the aggregation participation metrics report data.
 * </p>
 *
 * <p>
 * Version 1.1 (TC Cockpit Permission and Report Update One) change log:
 * <ol>
 *   <li>Added {@link #checkpointWinners} field and getter/setter for it.</li>
 *   <li>Added {@link #finalWinners} field and getter/setter for it.</li>
 *   <li>Added {@link #totalUniqueWinners} field and getter/setter for it.</li>
 * </ol>
 * </p>
 * 
 * <p>
 * Version 1.2 (TC Cockpit - Member Participation Metrics Report Upgrade) change log:
 * <ol> 
 *   <li>Removed {@link #totalSubmitters} field and getter/setter for it.</li>
 *   <li>Added {@link #totalSubmissions} field and getter/setter for it.</li>
 *   <li>Added {@link #checkpointSubmissions} field and getter/setter for it.</li>
 *   <li>Added {@link #finalSubmissions} field and getter/setter for it.</li>
 * </ol>
 * </p>
 * 
 * @author TCSASSEMBER
 * @version  1.2 (TC Cockpit - Member Participation Metrics Report Upgrade)
 */
public class ParticipationAggregationReportDTO implements Serializable {

    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = -3985093109381162149L;

    /**
     * Represents the name of aggregation group.
     */
    private String groupName;
    
    /**
     * Represents the number of total registrants of the aggregation group.
     */
    private int totalRegistrants;
    
    /**
     * Represents the number of unique registrants of the aggregation group.
     */
    private int uniqueRegistrants;
    
    /**
     * Represents the number of registrant countries of the aggregation group.
     */
    private int registrantCountries;
    
    /**
     * Represents the number of total submissions of the aggregation group.
     * 
     * @since 1.2
     */
    private int totalSubmissions;
    
    /**
     * Represents the number of checkpoint submissions of the aggregation group.
     * 
     * @since 1.2
     */
    private int checkpointSubmissions;
    
    /**
     * Represents the number of final submissions of the aggregation group.
     * 
     * @since 1.2
     */
    private int finalSubmissions;
    
    /**
     * Represents the number of unique submitters of the aggregation group.
     */
    private int uniqueSubmitters;
    
    /**
     * Represents the number of submitter countries of the aggregation group.
     */
    private int submitterContries;
    
    /**
     * Represents the number of checkpoint winners (Repeatable) of the aggregation group.
     *
     * @since 1.1
     */
    private int checkpointWinners;

    /**
     * Represents the number of final winners (Repeatable) of the aggregation group.
     *
     * @since 1.1
     */
    private int finalWinners;

    /**
     * Represents the number of total winners of the aggregation group.
     */
    private int totalWinners;
    
    /**
     * Represents the unique winners of the aggregation group.
     *
     * @since 1.1
     */
    private int totalUniqueWinners;

    /**
     * Represents the number of winner countries of the aggregation group.
     */
    private int winnerCountries;
    
    /**
     * Empty constructor.
     */
    public ParticipationAggregationReportDTO() {
        
    }

    /**
     * Gets the name of aggregation group.
     * 
     * @return the name of aggregation group.
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Sets the name of aggregation group.
     * 
     * @param groupName the name of aggregation group.
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * Gets the number of total registrants of the aggregation group.
     * 
     * @return the number of total registrants of the aggregation group.
     */
    public int getTotalRegistrants() {
        return totalRegistrants;
    }

    /**
     * Sets the number of total registrants of the aggregation group.
     * 
     * @param totalRegistrants the number of total registrants of the aggregation group.
     */
    public void setTotalRegistrants(int totalRegistrants) {
        this.totalRegistrants = totalRegistrants;
    }

    /**
     * Gets the number of unique registrants of the aggregation group.
     * @return the number of unique registrants of the aggregation group.
     */
    public int getUniqueRegistrants() {
        return uniqueRegistrants;
    }

    /**
     * Sets the number of unique registrants of the aggregation group.
     * 
     * @param uniqueRegistrants the number of unique registrants of the aggregation group.
     */
    public void setUniqueRegistrants(int uniqueRegistrants) {
        this.uniqueRegistrants = uniqueRegistrants;
    }

    /**
     * Gets the number of registrant countries of the aggregation group.
     * 
     * @return the number of registrant countries of the aggregation group.
     */
    public int getRegistrantCountries() {
        return registrantCountries;
    }

    /**
     * Sets the number of registrant countries of the aggregation group.
     * 
     * @param registrantCountries the number of registrant countries of the aggregation group.
     */
    public void setRegistrantCountries(int registrantCountries) {
        this.registrantCountries = registrantCountries;
    }

    /**
     * Gets the number of total submissions of the aggregation group.
     * 
     * @return the number of total submissions of the aggregation group.
     * @since 1.2
     */    
    public int getTotalSubmissions() {
        return totalSubmissions;
    }

    /**
     * Sets the number of total submissions of the aggregation group.
     * 
     * @param totalSubmissions the number of total submissions of the aggregation group.
     * @since 1.2
     */
    public void setTotalSubmissions(int totalSubmissions) {
        this.totalSubmissions = totalSubmissions;
    }

    /**
     * Gets the number of checkpoint submissions of the aggregation group.
     * 
     * @return the number of checkpoint submissions of the aggregation group.
     * @since 1.2
     */ 
    public int getCheckpointSubmissions() {
        return checkpointSubmissions;
    }

    /**
     * Sets the number of checkpoint submissions of the aggregation group.
     * 
     * @param checkpointSubmissions the number of checkpoint submissions of the aggregation group.
     * @since 1.2
     */
    public void setCheckpointSubmissions(int checkpointSubmissions) {
        this.checkpointSubmissions = checkpointSubmissions;
    }

    /**
     * Gets the number of final submissions of the aggregation group.
     * 
     * @return the number of final submissions of the aggregation group.
     * @since 1.2
     */ 
    public int getFinalSubmissions() {
        return finalSubmissions;
    }

    /**
     * Sets the number of final submissions of the aggregation group.
     * 
     * @param finalSubmissions the number of final submissions of the aggregation group.
     * @since 1.2
     */
    public void setFinalSubmissions(int finalSubmissions) {
        this.finalSubmissions = finalSubmissions;
    }

    /**
     * Gets the number of unique submitters of the aggregation group.
     * 
     * @return the number of unique submitters of the aggregation group.
     */
    public int getUniqueSubmitters() {
        return uniqueSubmitters;
    }

    /**
     * Sets the number of unique submitters of the aggregation group.
     * 
     * @param uniqueSubmitters the number of unique submitters of the aggregation group.
     */
    public void setUniqueSubmitters(int uniqueSubmitters) {
        this.uniqueSubmitters = uniqueSubmitters;
    }

    /**
     * Gets the number of submitter countries of the aggregation group.
     * 
     * @return the number of submitter countries of the aggregation group.
     */
    public int getSubmitterContries() {
        return submitterContries;
    }

    /**
     * Sets the number of submitter countries of the aggregation group.
     * 
     * @param submitterContries the number of submitter countries of the aggregation group.
     */
    public void setSubmitterContries(int submitterContries) {
        this.submitterContries = submitterContries;
    }

    /**
     * Gets the number of total winners of the aggregation group.
     * 
     * @return the number of total winners of the aggregation group.
     */
    public int getTotalWinners() {
        return totalWinners;
    }

    /**
     * Sets the number of total winners of the aggregation group.
     * 
     * @param totalWinners the number of total winners of the aggregation group.
     */
    public void setTotalWinners(int totalWinners) {
        this.totalWinners = totalWinners;
    }

    /**
     * Gets the number of winner countries of the aggregation group.
     * 
     * @return the number of winner countries of the aggregation group.
     */
    public int getWinnerCountries() {
        return winnerCountries;
    }

    /**
     * Sets the number of winner countries of the aggregation group.
     * 
     * @param winnerCountries the number of winner countries of the aggregation group.
     */
    public void setWinnerCountries(int winnerCountries) {
        this.winnerCountries = winnerCountries;
    }

    /**
     * Gets the number of checkpoint winners (Repeatable) of the aggregation group.
     *
     * @return the number of checkpoint winners (Repeatable) of the aggregation group.
     * @since 1.1
     */
    public int getCheckpointWinners() {
        return checkpointWinners;
    }

    /**
     * Sets the number of checkpoint winners (Repeatable) of the aggregation group.
     *
     * @param checkpointWinners the number of checkpoint winners (Repeatable) of the aggregation group.
     * @since 1.1
     */
    public void setCheckpointWinners(int checkpointWinners) {
        this.checkpointWinners = checkpointWinners;
    }

    /**
     * Gets the number of final winners (Repeatable) of the aggregation group.
     *
     * @return the number of final winners (Repeatable) of the aggregation group.
     * @since 1.1
     */
    public int getFinalWinners() {
        return finalWinners;
    }

    /**
     * Sets the number of final winners (Repeatable) of the aggregation group.
     *
     * @param finalWinners the number of final winners (Repeatable) of the aggregation group.
     * @since 1.1
     */
    public void setFinalWinners(int finalWinners) {
        this.finalWinners = finalWinners;
    }

    /**
     * Gets the unique winners of the aggregation group.
     *
     * @return the unique winners of the aggregation group.
     * @since 1.1
     */
    public int getTotalUniqueWinners() {
        return totalUniqueWinners;
    }

    /**
     * Sets the unique winners of the aggregation group.
     *
     * @param uniqueWinners the unique winners of the aggregation group.
     * @since 1.1
     */
    public void setTotalUniqueWinners(int totalUniqueWinners) {
        this.totalUniqueWinners = totalUniqueWinners;
    }
}
