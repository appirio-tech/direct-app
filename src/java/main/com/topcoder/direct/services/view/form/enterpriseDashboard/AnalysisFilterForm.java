/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.form.enterpriseDashboard;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * The form data of new Enterprise Dashboard analysis tab.
 * </p>
 *
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - TC Cockpit Enterprise Dashboard Analysis 1)
 */
public class AnalysisFilterForm implements Serializable {

    /**
     * The customer id.
     */
    private long customerId;

    /**
     * The billing account id.
     */
    private long billingAccountId;

    /**
     * The project id.
     */
    private long projectId;

    /**
     * The group type.
     */
    private AnalysisGroupType groupType;

    /**
     * An array of category ids.
     */
    private long[] categoryIds;

    /**
     * The start date used to filter.
     */
    private Date startDate;

    /**
     * The end date used to filter.
     */
    private Date endDate;

    /**
     * Gets the customer id.
     *
     * @return the customer id.
     */
    public long getCustomerId() {
        return customerId;
    }

    /**
     * Sets the customer id.
     *
     * @param customerId the customer id.
     */
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets the billing account id.
     *
     * @return the billing account id.
     */
    public long getBillingAccountId() {
        return billingAccountId;
    }

    /**
     * Sets the billing account id.
     *
     * @param billingAccountId the billing account id.
     */
    public void setBillingAccountId(long billingAccountId) {
        this.billingAccountId = billingAccountId;
    }

    /**
     * Gets the project id.
     *
     * @return
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Sets the project id.
     *
     * @param projectId the project id.
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * Gets the group type.
     *
     * @return the group type.
     */
    public AnalysisGroupType getGroupType() {
        return groupType;
    }

    /**
     * sets the group type.
     *
     * @param groupType the group type.
     */
    public void setGroupType(AnalysisGroupType groupType) {
        this.groupType = groupType;
    }

    /**
     * Gets the category ids.
     *
     * @return the category ids.
     */
    public long[] getCategoryIds() {
        return categoryIds;
    }

    /**
     * Sets the category ids.
     *
     * @param categoryIds the category ids.
     */
    public void setCategoryIds(long[] categoryIds) {
        this.categoryIds = categoryIds;
    }

    /**
     * Gets the start date.
     *
     * @return the start date.
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date.
     *
     * @param startDate the start date.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date.
     *
     * @return the end date.
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date.
     *
     * @param endDate the end date.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
