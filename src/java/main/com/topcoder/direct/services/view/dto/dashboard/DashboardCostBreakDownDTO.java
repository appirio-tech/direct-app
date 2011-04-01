/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard;

import java.io.Serializable;

/**
 * <p>A DTO class providing the cost break down data for contests or market.</p>
 * 
 * @author TCSASSEMBER
 * @version 1.0 (TC Cockpit Enterprise Dashboard Update Cost Breakdown Assembly)
 */
public class DashboardCostBreakDownDTO implements Serializable {
    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = -978594336377127076L;

    /**
     * Represents the id of the contest or the project category.
     */
    private long id;
    
    /**
     * Represents the contest fee cost.
     */
    private double contestFee;
    
    /**
     * Represents the prizes cost. 
     */
    private double prizes;
    
    /**
     * Represents the specification review cost.
     */
    private double specReview;
    
    /**
     * Represents the review cost.
     */
    private double review;
    
    /**
     * Represents the reliability cost.
     */
    private double reliability;
    
    /**
     * Represents the digital run cost.
     */
    private double digitalRun;
    
    /**
     * Represents the copilot cost.
     */
    private double copilot;
    
    /**
     * Represents the build cost.
     */
    private double build;
    
    /**
     * Represents the bugs cost.
     */
    private double bugs;
    
    /**
     * Represents the misc cost.
     */
    private double misc;

    /**
     * Empty constructor.
     */
    public DashboardCostBreakDownDTO() {
        
    }

    /**
     * Gets the id of the contest or the project category.
     * 
     * @return the id of the contest or the project category.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id of the contest or the project category.
     * 
     * @param id the id of the contest or the project category.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the contest fee cost.
     * 
     * @return the contest fee cost.
     */
    public double getContestFee() {
        return contestFee;
    }

    /**
     * Sets the contest fee cost.
     * 
     * @param contestFee the contest fee cost.
     */
    public void setContestFee(double contestFee) {
        this.contestFee = contestFee;
    }

    /**
     * Gets the prizes cost.
     * 
     * @return the prizes cost.
     */
    public double getPrizes() {
        return prizes;
    }

    /**
     * Sets the prizes cost.
     * 
     * @param prizes the prizes cost.
     */
    public void setPrizes(double prizes) {
        this.prizes = prizes;
    }

    /**
     * Gets the specification review cost.
     * 
     * @return the specification review cost.
     */
    public double getSpecReview() {
        return specReview;
    }

    /**
     * Sets the specification review cost.
     * 
     * @param specReview the specification review cost.
     */
    public void setSpecReview(double specReview) {
        this.specReview = specReview;
    }

    /**
     * Gets the review cost.
     * 
     * @return the review cost.
     */
    public double getReview() {
        return review;
    }

    /**
     * Sets the review cost.
     * 
     * @param review the review cost.
     */
    public void setReview(double review) {
        this.review = review;
    }

    /**
     * Gets the reliability cost.
     * 
     * @return the reliability cost.
     */
    public double getReliability() {
        return reliability;
    }

    /**
     * Sets the reliability cost.
     * 
     * @param reliability the reliability cost.
     */
    public void setReliability(double reliability) {
        this.reliability = reliability;
    }

    /**
     * Gets the digital run cost.
     * 
     * @return the digital run cost.
     */
    public double getDigitalRun() {
        return digitalRun;
    }

    /**
     * Sets the digital run cost.
     * 
     * @param digitalRun the digital run cost.
     */
    public void setDigitalRun(double digitalRun) {
        this.digitalRun = digitalRun;
    }

    /**
     * Gets the copilot cost.
     * 
     * @return the copilot cost.
     */
    public double getCopilot() {
        return copilot;
    }

    /**
     * Sets the copilot cost.
     * 
     * @param copilot the copilot cost.
     */
    public void setCopilot(double copilot) {
        this.copilot = copilot;
    }

    /**
     * Gets the build cost.
     * 
     * @return the build cost.
     */
    public double getBuild() {
        return build;
    }

    /**
     * Sets the build cost.
     * 
     * @param build the build cost.
     */
    public void setBuild(double build) {
        this.build = build;
    }

    /**
     * Gets the bugs cost.
     * 
     * @return the bugs cost.
     */
    public double getBugs() {
        return bugs;
    }

    /**
     * Sets the bugs cost.
     * 
     * @param bugs the bugs cost.
     */
    public void setBugs(double bugs) {
        this.bugs = bugs;
    }

    /**
     * Gets the misc cost.
     * 
     * @return the misc cost.
     */
    public double getMisc() {
        return misc;
    }

    /**
     * Sets the misc cost.
     * 
     * @param misc the misc cost.
     */
    public void setMisc(double misc) {
        this.misc = misc;
    }
}
