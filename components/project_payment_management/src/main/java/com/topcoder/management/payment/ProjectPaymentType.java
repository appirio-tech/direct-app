/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment;

/**
 * <p>
 * This class is a container for information about a single project payment type. It is a simple JavaBean (POJO) that
 * provides getters and setters for all private attributes and performs no argument validation in the setters.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable and not thread safe.
 * </p>
 *
 * @author maksimilian, sparemax
 * @version 1.0.1
 */
public class ProjectPaymentType {
    /**
     * The ID of the project payment type. Can be any value. Has getter and setter.
     */
    private Long projectPaymentTypeId;

    /**
     * The name. Can be any value. Has getter and setter.
     */
    private String name;

    /**
     * The flag indicating if type is mergeable. Can be any value. Has getter and setter.
     */
    private boolean mergeable;

    /**
     * The ID of the project payment type in PACTS. Can be any value. Has getter and setter.
     */
    private long pactsPaymentTypeId;

    /**
     * Creates an instance of ProjectPaymentType.
     */
    public ProjectPaymentType() {
        // Empty
    }

    /**
     * Gets the ID of the project payment type.
     *
     * @return the ID of the project payment type.
     */
    public Long getProjectPaymentTypeId() {
        return projectPaymentTypeId;
    }

    /**
     * Sets the ID of the project payment type.
     *
     * @param projectPaymentTypeId
     *            the ID of the project payment type.
     */
    public void setProjectPaymentTypeId(Long projectPaymentTypeId) {
        this.projectPaymentTypeId = projectPaymentTypeId;
    }

    /**
     * Gets the name.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name
     *            the name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the flag indicating if type is mergeable.
     *
     * @return the flag indicating if type is mergeable.
     */
    public boolean isMergeable() {
        return mergeable;
    }

    /**
     * Sets the flag indicating if type is mergeable.
     *
     * @param mergeable
     *            the flag indicating if type is mergeable.
     */
    public void setMergeable(boolean mergeable) {
        this.mergeable = mergeable;
    }

    /**
     * Gets the ID of the project payment type in PACTS.
     *
     * @return the ID of the project payment type in PACTS.
     */
    public Long getPactsPaymentTypeId() {
        return pactsPaymentTypeId;
    }

    /**
     * Sets the ID of the project payment type in PACTS.
     *
     * @param pactsPaymentTypeId
     *            the ID of the project payment type in PACTS.
     */
    public void setPactsPaymentTypeId(long pactsPaymentTypeId) {
        this.pactsPaymentTypeId = pactsPaymentTypeId;
    }
}
