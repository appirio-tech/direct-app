/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard.participationreport;

import java.io.Serializable;

import com.topcoder.direct.services.view.dto.IdNamePair;

/**
 * <p>
 * The DTO stores the participation metrics contest detail data.
 * </p>
 *
 * <p>
 * Version 1.1 (TC Cockpit Permission and Report Update One) change log:
 * <ol>
 *   <li>Remove field <code>hasWin</code> and getter/setter for it.</li>
 *   <li>Added {@link #hasWinFinal} field and getter/setter for it.</li>
 *   <li>Added {@link #hasWinMilestone} field and getter/setter for it.</li>
 * </ol>
 * </p>
 * 
 * @author TCSASSEMBER
 * @version  1.1 (TC Cockpit Participation Metrics Report Part One Assembly 1)
 */
public class ParticipationContestDetailDTO implements Serializable {

    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = -2871220976543113229L;

    /**
     * Represents the client data.
     */
    private IdNamePair client;

    /**
     * Represents the billing data.
     */
    private IdNamePair billing;

    /**
     * Represents the direct project data.
     */
    private IdNamePair project;

    /**
     * Represents the contest data.
     */
    private IdNamePair contest;
    
    /**
     * Represents the contest type/category data.
     */
    private IdNamePair contestType;
    
    /**
     * Represents the user id of the registrant.
     */
    private long registrant;
    
    /**
     * Represents the country of the registrant.
     */
    private String country;
    
    /**
     * A flag indicates whether the registrant is a submitter.
     */
    private boolean hasSubmit;
    
    /**
     * A flag indicates whether the registrant has win in the final round.
     * 
     * @since 1.1
     */
    private boolean hasWinFinal;

    /**
     * A flag indicates whether the registrant has win in the milestone round.
     * 
     * @since 1.1
     */
    private boolean hasWinMilestone;

    /**
     * Represents the contest status.
     */
    private String status;
    
    /**
     * Empty constructor.
     */
    public ParticipationContestDetailDTO() {
        
    }

    /**
     * Gets the client/customer of the contest.
     *
     * @return the client of the contest.
     */
    public IdNamePair getClient() {
        return client;
    }

    /**
     * Sets the the client of the contest.
     *
     * @param client the client to set
     */
    public void setClient(IdNamePair client) {
        this.client = client;
    }

    /**
     * Gets the billing of the contest.
     *
     * @return the billing of the contest.
     */
    public IdNamePair getBilling() {
        return billing;
    }

    /**
     * Sets the billing of the contest.
     *
     * @param billing the billing to set.
     */
    public void setBilling(IdNamePair billing) {
        this.billing = billing;
    }

    /**
     * Gets the project of the contest.
     *
     * @return the project of the contest.
     */
    public IdNamePair getProject() {
        return project;
    }

    /**
     * Sets the project of the contest.
     *
     * @param project the project of the contest.
     */
    public void setProject(IdNamePair project) {
        this.project = project;
    }

    /**
     * Gets the contest.
     *
     * @return the contest.
     */
    public IdNamePair getContest() {
        return contest;
    }

    /**
     * Sets the contest.
     *
     * @param contest the contest to set.
     */
    public void setContest(IdNamePair contest) {
        this.contest = contest;
    }

    /**
     * Gets the contest type.
     *
     * @return the contest type.
     */
    public IdNamePair getContestType() {
        return contestType;
    }

    /**
     * Sets the contest type.
     *
     * @param contestType the contest type to set.
     */
    public void setContestType(IdNamePair contestType) {
        this.contestType = contestType;
    }

    /**
     * Gets the user id of the registrant.
     * 
     * @return the user id of the registrant.
     */
    public long getRegistrant() {
        return registrant;
    }

    /**
     * Sets the user id of the registrant.
     * 
     * @param registrant the user id of the registrant.
     */
    public void setRegistrant(long registrant) {
        this.registrant = registrant;
    }

    /**
     * Gets the country of the registrant.
     * 
     * @return the country of the registrant.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country of the registrant.
     * 
     * @param country the country of the registrant.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gets the flag indicates whether the registrant is a submitter.
     * 
     * @return true if the registrant is a submitter, false otherwise.
     */
    public boolean isHasSubmit() {
        return hasSubmit;
    }

    /**
     * Sets the flag indicates whether the registrant is a submitter.
     * 
     * @param hasSubmit true if the registrant is a submitter, false otherwise.
     */
    public void setHasSubmit(boolean hasSubmit) {
        this.hasSubmit = hasSubmit;
    }

    /**
     * Gets the flag indicates whether the registrant has win in the final round.
     *
     * @return true if the registrant has win in the final round, false otherwise.
     * @since 1.1
     */
    public boolean isHasWinFinal() {
        return hasWinFinal;
    }

    /**
     * Sets the flag indicates whether the registrant has win in the final round.
     *
     * @param hasWin true if the registrant has win in the final round, false otherwise.
     * @since 1.1
     */
    public void setHasWinFinal(boolean hasWinFinal) {
        this.hasWinFinal = hasWinFinal;
    }

    /**
     * Gets the flag indicates whether the registrant has win in the milestone round.
     *
     * @return true if the registrant has win in the milestone round, false otherwise.
     * @since 1.1
     */
    public boolean isHasWinMilestone() {
        return hasWinMilestone;
    }

    /**
     * Sets the flag indicates whether the registrant has win in the milestone round.
     *
     * @param hasWin true if the registrant has win in the milestone round, false otherwise.
     * @since 1.1
     */
    public void setHasWinMilestone(boolean hasWinMilestone) {
        this.hasWinMilestone = hasWinMilestone;
    }

    /**
     * Gets the contest status.
     * 
     * @return the contest status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the contest status.
     * 
     * @param status the contest status.
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
