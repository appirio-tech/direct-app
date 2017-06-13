/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment;

import java.math.BigDecimal;

/**
 * <p>
 * This class is a container for information about a single project payment adjustment. It is a simple JavaBean (POJO)
 * that provides getters and setters for all private attributes and performs no argument validation in the setters.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable and not thread safe.
 * </p>
 *
 * @author maksimilian, sparemax
 * @version 1.0
 */
public class ProjectPaymentAdjustment {
    /**
     * The ID of the project. Can be any value. Has getter and setter.
     */
    private Long projectId;

    /**
     * The ID of the resource role. Can be any value. Has getter and setter.
     */
    private Long resourceRoleId;

    /**
     * The fixed amount. Can be any value. Has getter and setter.
     */
    private BigDecimal fixedAmount;

    /**
     * The multiplier. Can be any value. Has getter and setter.
     */
    private Double multiplier;

    /**
     * Creates an instance of ProjectPaymentAdjustment.
     */
    public ProjectPaymentAdjustment() {
        // Empty
    }

    /**
     * Gets the ID of the project.
     *
     * @return the ID of the project.
     */
    public Long getProjectId() {
        return projectId;
    }

    /**
     * Sets the ID of the project.
     *
     * @param projectId
     *            the ID of the project.
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    /**
     * Gets the ID of the resource role.
     *
     * @return the ID of the resource role.
     */
    public Long getResourceRoleId() {
        return resourceRoleId;
    }

    /**
     * Sets the ID of the resource role.
     *
     * @param resourceRoleId
     *            the ID of the resource role.
     */
    public void setResourceRoleId(Long resourceRoleId) {
        this.resourceRoleId = resourceRoleId;
    }

    /**
     * Gets the fixed amount.
     *
     * @return the fixed amount.
     */
    public BigDecimal getFixedAmount() {
        return fixedAmount;
    }

    /**
     * Sets the fixed amount.
     *
     * @param fixedAmount
     *            the fixed amount.
     */
    public void setFixedAmount(BigDecimal fixedAmount) {
        this.fixedAmount = fixedAmount;
    }

    /**
     * Gets the multiplier.
     *
     * @return the multiplier.
     */
    public Double getMultiplier() {
        return multiplier;
    }

    /**
     * Sets the multiplier.
     *
     * @param multiplier
     *            the multiplier.
     */
    public void setMultiplier(Double multiplier) {
        this.multiplier = multiplier;
    }
}
