/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>A DTO class providing the statistical data for a single contest to be displayed in <code>Table View</code>
 * area on <code>Enterprise Dashboard</code> page view.</p>
 *
 * @author xjtufreeman
 * @version 1.0
 */
public class EnterpriseDashboardContestStatDTO implements Serializable {
    /**
     * <p>A <code>Date</code> providing the completion date for specified contest.</p>
     */
    private Date date;

    /**
     * <p>A <code>Date</code> providing the posting date for specified contest.</p>
     */
    private Date postingDate;

    /**
     * <p>A <code>String</code> providing the customer name for specified contest.</p>
     */
    private String customerName;

    /**
     * <p>A <code>String</code> providing the project name for specified contest.</p>
     */
    private String projectName;

    /**
     * <p>A <code>String</code> providing the contest name for specified contest.</p>
     */
    private String contestName;

    /**
     * <p>A <code>String</code> providing the contest type for specified contest.</p>
     */
    private String contestType;

    /**
     * <p>A <code>int</code> providing the project  id for specified contest.</p>
     */
    private int projectId;

    /**
     * <p>A <code>int</code> providing the direct project id for specified contest.</p>
     */
    private int directProjectId;

    /**
     * <p>A <code>int</code> providing the project category id for specified contest.</p>
     */
    private int projectCategoryId;

    /**
     * <p>A <code>Double</code> providing the fullfilment for specified contest.</p>
     */
    private Double contestFullfilment;

    /**
     * <p>A <code>Double</code> providing the average market fulfillment in the specified duration for the contest type of this contest.</p>
     */
    private Double marketAvgFullfilment;

    /**
     * <p>A <code>Double</code> providing the cost for specified contest.</p>
     */
    private Double contestCost;

    /**
     * <p>A <code>Double</code> providing the average market contest cost in the specified duration for the contest type of this contest.</p>
     */
    private Double marketAvgCost;

     /**
     * <p>A <code>Double</code> providing the duration for specified contest.</p>
     */
    private Double contestDuration;

    /**
     * <p>A <code>Double</code> providing the average market contest duration in the specified duration for the contest type of this contest.</p>
     */
    private Double marketAvgDuration;


    /**
     * <p>Constructs new <code>EnterpriseDashboardContestStatDTO</code> instance. This implementation does nothing.</p>
     */
    public EnterpriseDashboardContestStatDTO() {
    }

    /**
     * <p>Gets the completion date for contest.</p>
     *
     * @return a <code>Date</code> providing the completion date for specified contest.
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * <p>Sets the completion date for contest.</p>
     *
     * @param date a <code>Date</code> providing the completion date for specified contest.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * <p>Gets the posting date for contest.</p>
     *
     * @return a <code>Date</code> providing the posting date for specified contest.
     */
    public Date getPostingDate() {
        return this.postingDate;
    }

    /**
     * <p>Sets the posting date for contest.</p>
     *
     * @param postingDate a <code>Date</code> providing the posting date for specified contest.
     */
    public void setPostingDate(Date postingDate) {
        this.postingDate = postingDate;
    }

    /**
     * <p>Gets the customer name for specified contest.</p>
     *
     * @return a <code>String</code> providing the customer name for specified contest.
     */
    public String getCustomerName() {
        return this.customerName;
    }

    /**
     * <p>Sets the customer name for specified contest.</p>
     *
     * @param customerName a <code>String</code> providing the customer name for specified contest.
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    /**
     * <p>Gets the project name for specified contest.</p>
     *
     * @return a <code>String</code> providing the project name for specified contest.
     */
    public String getProjectName() {
        return this.projectName;
    }

    /**
     * <p>Sets the project name for specified contest.</p>
     *
     * @param projectName a <code>String</code> providing the project name for specified contest.
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * <p>Gets the contest name for specified contest.</p>
     *
     * @return a <code>String</code> providing the contest name for specified contest.
     */
    public String getContestName() {
        return this.contestName;
    }

    /**
     * <p>Sets the contest name for specified contest.</p>
     *
     * @param contestName a <code>String</code> providing the contest name for specified contest.
     */
    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    /**
     * <p>Gets the contest type for specified contest.</p>
     *
     * @return a <code>String</code> providing the contest type for specified contest.
     */
    public String getContestType() {
        return this.contestType;
    }

    /**
     * <p>Sets the contest type for specified contest.</p>
     *
     * @param contestType a <code>String</code> providing the contest type for specified contest.
     */
    public void setContestType(String contestType) {
        this.contestType = contestType;
    }

    /**
     * <p>Gets the project category id for specified contest.</p>
     *
     * @return a <code>int</code> providing the project category id for specified contest.
     */
    public int getProjectCategoryId() {
        return this.projectCategoryId;
    }

    /**
     * <p>Sets the project category id for specified contest.</p>
     *
     * @param projectCategoryId a <code>int</code> providing the project category id for specified contest.
     */
    public void setProjectCategoryId(int projectCategoryId) {
        this.projectCategoryId = projectCategoryId;
    }

    /**
     * <p>Gets the project id for specified contest.</p>
     *
     * @return a <code>int</code> providing the project id for specified contest.
     */
    public int getProjectId() {
        return this.projectId;
    }

    /**
     * <p>Sets the project id for specified contest.</p>
     *
     * @param projectId a <code>int</code> providing the project id for specified contest.
     */
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

     /**
     * <p>Gets the direct project id for specified contest.</p>
     *
     * @return a <code>int</code> providing the direct project id for specified contest.
     */
    public int getDirectProjectId() {
        return this.directProjectId;
    }

    /**
     * <p>Sets the direct project id for specified contest.</p>
     *
     * @param directProjectId a <code>int</code> providing the direct project id for specified contest.
     */
    public void setDirectProjectId(int directProjectId) {
        this.directProjectId = directProjectId;
    }

    /**
     * <p>Gets the fullfilment for specified contest.</p>
     *
     * @return a <code>Double</code> providing the fullfilment for specified contest.
     */
    public Double getContestFullfilment() {
        return this.contestFullfilment;
    }

    /**
     * <p>Sets the fullfilment for specified contest.</p>
     *
     * @param fullfilment a <code>String</code> providing the fullfilment for specified contest.
     */
    public void setContestFullfilment(Double fullfilment) {
        this.contestFullfilment = fullfilment;
    }

    /**
     * <p>Gets the average market fulfillment in the specified duration for the contest type of this contest.</p>
     *
     * @return a <code>Double</code> providing the average market fulfillment in the specified duration for the contest type of this contest
     */
    public Double getMarketAvgFullfilment() {
        return this.marketAvgFullfilment;
    }

    /**
     * <p>Sets the average market fulfillment in the specified duration for the contest type of this contest.</p>
     *
     * @param marketAvgFullfilment a <code>String</code> providing the average market fulfillment in the specified duration for the contest type of this contest.
     */
    public void setMarketAvgFullfilment(Double marketAvgFullfilment) {
        this.marketAvgFullfilment = marketAvgFullfilment;
    }


    /**
     * <p>Gets the cost for specified contest.</p>
     *
     * @return a <code>Double</code> providing the cost for specified contest.
     */
    public Double getContestCost() {
        return this.contestCost;
    }

    /**
     * <p>Sets the cost for specified contest.</p>
     *
     * @param contestCost a <code>String</code> providing the cost for specified contest.
     */
    public void setContestCost(Double contestCost) {
        this.contestCost = contestCost;
    }

    /**
     * <p>Gets the average market contest cost in the specified duration for the contest type of this contest.</p>
     *
     * @return a <code>Double</code> providing the average market contest cost in the specified duration for the contest type of this contest.
     */
    public Double getMarketAvgCost() {
        return this.marketAvgCost;
    }

    /**
     * <p>Sets the average market contest cost in the specified duration for the contest type of this contest.</p>
     *
     * @param marketAvgCost a <code>String</code> providing the average market contest cost in the specified duration for the contest type of this contest.
     */
    public void setMarketAvgCost(Double marketAvgCost) {
        this.marketAvgCost = marketAvgCost;
    }


    /**
     * <p>Gets the duration for specified contest.</p>
     *
     * @return a <code>Double</code> providing the duration for specified contest.
     */
    public Double getContestDuration() {
        return this.contestDuration;
    }

    /**
     * <p>Sets the duration for specified contest.</p>
     *
     * @param contestDuration a <code>String</code> providing the duration for specified contest.
     */
    public void setContestDuration(Double contestDuration) {
        this.contestDuration = contestDuration;
    }


    /**
     * <p>Gets the average market contest duration in the specified duration for the contest type of this contest.</p>
     *
     * @return a <code>Double</code> providing the average market contest duration in the specified duration for the contest type of this contest.
     */
    public Double getMarketAvgDuration() {
        return this.marketAvgDuration;
    }

    /**
     * <p>Sets the average market contest duration in the specified duration for the contest type of this contest.</p>
     *
     * @param marketAvgDuration a <code>String</code> providing the average market contest duration in the specified duration for the contest type of this contest.
     */
    public void setMarketAvgDuration(Double marketAvgDuration) {
        this.marketAvgDuration = marketAvgDuration;
    }

}
