/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.form;

import java.io.Serializable;

/**
 * The action form the the action <code>DashboardEnterpriseCalendarAction</code>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Release Assembly - TC Cockpit Enterprise Calendar Revamp)
 */
public class DashboardMilestoneCalendarForm implements Serializable {

    /**
     * The id of the customer.
     */
    private long customerId;

    /**
     * The id of the project filter.
     */
    private long projectFilterId;

    /**
     * The project filter value.
     */
    private String projectFilterValue;

    /**
     * Gets the id of the customer.
     *
     * @return the id of the customer.
     */
    public long getCustomerId() {
        return customerId;
    }

    /**
     * Sets the id of the customer.
     *
     * @param customerId the id of the customer.
     */
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets the id of the project filter.
     *
     * @return the id of the project filter.
     */
    public long getProjectFilterId() {
        return projectFilterId;
    }

    /**
     * Sets the id of the project filter.
     *
     * @param projectFilterId the id of the project filter.
     */
    public void setProjectFilterId(long projectFilterId) {
        this.projectFilterId = projectFilterId;
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
     * @param projectFilterValue the value of the project filter.
     */
    public void setProjectFilterValue(String projectFilterValue) {
        this.projectFilterValue = projectFilterValue;
    }
}
