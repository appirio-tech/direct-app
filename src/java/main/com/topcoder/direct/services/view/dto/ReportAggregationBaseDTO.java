/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto;

import java.util.List;

/**
 * <p>A <code>DTO</code> class providing the basic report data and aggregation data for
 * aggregation report.</p>
 * 
 * @author TCSASSEMBER
 * @version 1.0 (TC Cockpit Permission and Report Update One)
 * @param T the type of the aggregation data
 */
public class ReportAggregationBaseDTO<T> extends ReportBaseDTO {
    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 4924705523653390195L;
    
    /**
     * The project aggregation report data.
     */
    private List<T> projectAggregation;

    /**
     * The contest type aggregation report data.
     */
    private List<T> contestTypeAggregation;

    /**
     * The status aggregation report data.
     */
    private List<T> statusAggregation;
    
    /**
     * The billing account report data.
     */
    private List<T> billingAggregation;

    /**
     * Construct a new <code>ReportAggregationBaseDTO</code> instance.
     */
    public ReportAggregationBaseDTO() {
        super();
    }
    
    /**
     * Gets the project aggregation report data.
     * 
     * @return the project aggregation report data.
     */
    public List<T> getProjectAggregation() {
        return projectAggregation;
    }

    /**
     * Sets the project aggregation report data.
     * 
     * @param projectAggregation the project aggregation report data.
     */
    public void setProjectAggregation(List<T> projectAggregation) {
        this.projectAggregation = projectAggregation;
    }

    /**
     * Gets the contest type aggregation report data.
     * 
     * @return the contest type aggregation report data.
     */
    public List<T> getContestTypeAggregation() {
        return contestTypeAggregation;
    }

    /**
     * Sets the contest type aggregation report data.
     * 
     * @param contestTypeAggregation the contest type aggregation report data.
     */
    public void setContestTypeAggregation(List<T> contestTypeAggregation) {
        this.contestTypeAggregation = contestTypeAggregation;
    }

    /**
     * Gets the status aggregation report data.
     * 
     * @return the status aggregation report data.
     */
    public List<T> getStatusAggregation() {
        return statusAggregation;
    }

    /**
     * Sets the status aggregation report data.
     * 
     * @param statusAggregation the status aggregation report data.
     */
    public void setStatusAggregation(List<T> statusAggregation) {
        this.statusAggregation = statusAggregation;
    }

    /**
     * Gets the billing aggregation report data.
     * 
     * @return the billing aggregation report data.
     */
    public List<T> getBillingAggregation() {
        return billingAggregation;
    }

    /**
     * Sets the billing aggregation report data.
     * 
     * @param billingAggregation the billing aggregation report data.
     */
    public void setBillingAggregation(List<T> billingAggregation) {
        this.billingAggregation = billingAggregation;
    }
}
