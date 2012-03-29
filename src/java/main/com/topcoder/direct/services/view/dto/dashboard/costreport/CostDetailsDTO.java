/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard.costreport;

import com.topcoder.direct.services.view.dto.IdNamePair;

import java.io.Serializable;
import java.util.Date;

/**
 * The DTO stores the cost details report data.
 *
 * @author TCSDEVELOPER
 * @version  1.0 (TopCoder Cockpit - Cost Report Assembly)
 */
public class CostDetailsDTO implements Serializable {

    /**
     * The client data.
     */
    private IdNamePair client;

    /**
     * The billing data.
     */
    private IdNamePair billing;

    /**
     * The direct project data.
     */
    private IdNamePair project;

    /**
     * The contest data.
     */
    private IdNamePair contest;

    /**
     * The contest type/category data.
     */
    private IdNamePair contestType;

    /**
     * The status of the contest, could be 'Active', 'Scheduled' and 'Finished'
     */
    private String status;

    /**
     * The completion date of the contest.
     */
    private Date completionDate;

    /**
     * The contest fee of the contest.
     */
    private double contestFee;

    /**
     * The estimated cost of the contest.
     */
    private double estimatedCost;

    /**
     * The actual cost of the contest. This only applies to 'Finished' contest.
     */
    private double actualCost;

    /**
     * The total cost of the contest.
     */
    private double total;

    /**
     * Whether the contest is studio contest
     */
    private boolean isStudio;

    /**
     * The project filter value applied.
     */
    private String projectFilterValue;
    

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
     * Gets the status.
     *
     * @return the status of the contest.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param status the status of the contest to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the completion date of the contest.
     *
     * @return the completion date of the contest.
     */
    public Date getCompletionDate() {
        return completionDate;
    }

    /**
     * Sets the completion data of the contest.
     *
     * @param completionDate the completion date of the contest.
     */
    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    /**
     * Gets the contest fee.
     *
     * @return the contest fee.
     */
    public double getContestFee() {
        return contestFee;
    }

    /**
     * Sets the contest fee.
     *
     * @param contestFee the contest fee of the contest.
     */
    public void setContestFee(double contestFee) {
        this.contestFee = contestFee;
    }

    /**
     * Gets the estimated cost of the contest.
     *
     * @return the estimated cost of the contest.
     */
    public double getEstimatedCost() {
        return estimatedCost;
    }

    /**
     * Sets the estimated cost of the contest.
     *
     * @param estimatedCost the estimated cost of the contest.
     */
    public void setEstimatedCost(double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    /**
     * Gets the actual cost of the contest.
     *
     * @return the actual cost of the contest.
     */
    public double getActualCost() {
        return actualCost;
    }

    /**
     * Sets the actual cost of the contest.
     *
     * @param actualCost the actual cost of the contest.
     */
    public void setActualCost(double actualCost) {
        this.actualCost = actualCost;
    }

    /**
     * Gets the total cost of the contest.
     *
     * @return the total cost of the contest.
     */
    public double getTotal() {
        return total;
    }

    /**
     * Sets the total cost of the contest.
     *
     * @param total the total cost of the contest.
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * Gets is studio flag.
     *
     * @return is studio flag.
     */
    public boolean isStudio() {
        return isStudio;
    }

    /**
     * Sets is studio flag.
     *
     * @param studio is studio flag.
     */
    public void setStudio(boolean studio) {
        isStudio = studio;
    }

    /**
     * Gets the project filter value.
     *
     * @return the project filter value.
     */
    public String getProjectFilterValue() {
        return projectFilterValue;
    }

    /**
     * Sets the project filter value.
     *
     * @param projectFilterValue the project filter value.
     */
    public void setProjectFilterValue(String projectFilterValue) {
        this.projectFilterValue = projectFilterValue;
    }
}
