/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * <p>
 * This class is a container for information about a project payment. It is a simple JavaBean (POJO) that provides
 * getters and setters for all private attributes and performs no argument validation in the setters.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable and not thread safe.
 * </p>
 *
 * @author maksimilian, sparemax
 * @version 1.0.1
 */
public class ProjectPayment {
    /**
     * The ID of the project payment. Can be any value. Has getter and setter.
     */
    private Long projectPaymentId;

    /**
     * The project payment type. Can be any value. Has getter and setter.
     */
    private ProjectPaymentType projectPaymentType;

    /**
     * The ID of the resource. Can be any value. Has getter and setter.
     */
    private Long resourceId;

    /**
     * The ID of the submission. Can be any value. Has getter and setter.
     */
    private Long submissionId;

    /**
     * The amount. Can be any value. In setter it is formatted using setScale(2, RoundingMode.HALF_UP). Has getter and
     * setter.
     */
    private BigDecimal amount;

    /**
     * The ID of the PACTS payment. Can be any value. Has getter and setter.
     */
    private Long pactsPaymentId;

    /**
     * Represents the creation user of the payment instance. Can be any value. Has getter and setter.
     */
    private String createUser;

    /**
     * The creation date. Can be any value. Has getter and setter.
     */
    private Date createDate;

    /**
     * Represents the modification user of the payment instance. Can be any value. Has getter and setter.
     */
    private String modifyUser;

    /**
     * The modification date. Can be any value. Has getter and setter.
     */
    private Date modifyDate;


    /**
     * Creates an instance of ProjectPayment.
     */
    public ProjectPayment() {
        // Empty
    }

    /**
     * Gets the ID of the project payment.
     *
     * @return the ID of the project payment.
     */
    public Long getProjectPaymentId() {
        return projectPaymentId;
    }

    /**
     * Sets the ID of the project payment.
     *
     * @param projectPaymentId
     *            the ID of the project payment.
     */
    public void setProjectPaymentId(Long projectPaymentId) {
        this.projectPaymentId = projectPaymentId;
    }

    /**
     * Gets the project payment type.
     *
     * @return the project payment type.
     */
    public ProjectPaymentType getProjectPaymentType() {
        return projectPaymentType;
    }

    /**
     * Sets the project payment type.
     *
     * @param projectPaymentType
     *            the project payment type.
     */
    public void setProjectPaymentType(ProjectPaymentType projectPaymentType) {
        this.projectPaymentType = projectPaymentType;
    }

    /**
     * Gets the ID of the resource.
     *
     * @return the ID of the resource.
     */
    public Long getResourceId() {
        return resourceId;
    }

    /**
     * Sets the ID of the resource.
     *
     * @param resourceId
     *            the ID of the resource.
     */
    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    /**
     * Gets the ID of the submission.
     *
     * @return the ID of the submission.
     */
    public Long getSubmissionId() {
        return submissionId;
    }

    /**
     * Sets the ID of the submission.
     *
     * @param submissionId
     *            the ID of the submission.
     */
    public void setSubmissionId(Long submissionId) {
        this.submissionId = submissionId;
    }

    /**
     * Gets the amount.
     *
     * @return the amount.
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets the amount.
     *
     * @param amount
     *            the amount.
     */
    public void setAmount(BigDecimal amount) {
        this.amount = (amount == null) ? null : amount.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Gets the ID of the PACTS payment.
     *
     * @return the ID of the PACTS payment.
     */
    public Long getPactsPaymentId() {
        return pactsPaymentId;
    }

    /**
     * Sets the ID of the PACTS payment.
     *
     * @param pactsPaymentId
     *            the ID of the PACTS payment.
     */
    public void setPactsPaymentId(Long pactsPaymentId) {
        this.pactsPaymentId = pactsPaymentId;
    }

    /**
     * Gets the creation user.
     *
     * @return the creation user.
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * Sets the creation user.
     *
     * @param createUser
     *            the creation user.
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * Gets the create date.
     *
     * @return the create date.
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Sets the create date.
     *
     * @param createDate
     *            the create date.
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets the modification user.
     *
     * @return the modification user.
     */
    public String getModifyUser() {
        return modifyUser;
    }

    /**
     * Sets the modification user.
     *
     * @param modifyUser
     *            the modification user.
     */
    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    /**
     * Gets the modification date.
     *
     * @return the modification date.
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * Sets the modification date.
     *
     * @param modifyDate
     *            the modification date.
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}
