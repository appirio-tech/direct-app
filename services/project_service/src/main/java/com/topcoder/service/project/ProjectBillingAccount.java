/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import java.io.Serializable;

/**
 * This entity presents the mapping between the direct project and billing account.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 * @since 1.0 (Module Assembly - TC Cockpit Direct Project Related Services Update and Integration)
 */
public class ProjectBillingAccount implements Serializable {

    /**
     * the id.
     */
    private long id;

    /**
     * The id of the direct project.
     */
    private long projectId;

    /**
     * The id of the billing account.
     */
    private long billingAccountId;

    /**
     * Gets the id
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the id of the direct project.
     *
     * @return the id of the direct project.
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Sets the id of the direct project.
     *
     * @param projectId the id of the direct project.
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * Gets the id of the billing account.
     *
     * @return the id of the billing account.
     */
    public long getBillingAccountId() {
        return billingAccountId;
    }

    /**
     * Sets the id of the billing account.
     *
     * @param billingAccountId the id of the billing account.
     */
    public void setBillingAccountId(long billingAccountId) {
        this.billingAccountId = billingAccountId;
    }
}
