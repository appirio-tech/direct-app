/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.fees.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Represents the Contest Fee Detail entity. It's a Hibernate entity.
 * 
 * Thread safety: The class is mutable and not thread safe.
 * 
 * @author winstips, TCSDEVELOPER
 * @version 1.0
 */
@Entity
@Table(name = "project_contest_fee")
@SequenceGenerator(name = "project_contest_fee_seq", sequenceName = "project_contest_fee_seq")
public class ContestFeeDetails {

    /**
     * Represents the ID of contest type. It is managed with a getter and setter. It may have any value. It is fully
     * mutable.
     */
    @Column(name = "contest_type_id")
    private long contestTypeId;
    /**
     * Represents the fee for a contest type. It is managed with a getter and setter. It may have any value. It is fully
     * mutable.
     */
    @Column(name = "contest_fee")
    private double fee;

    /**
     * Represents the project contest fee id. It is managed with a getter and setter. It may have any value. It is fully
     * mutable.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_contest_fee_seq")
    @Column(name = "project_contest_fee_id")
    private long projectContestFeeId;
    /**
     * Represents the project id. It is managed with a getter and setter. It may have any value. It is fully mutable.
     */
    @Column(name = "project_id")
    private long projectId;
    /**
     * Represents the studio flag. It is managed with a getter and setter. It may have any value. It is fully mutable.
     */
    @Column(name = "is_studio")
    private int isStudio;
    /**
     * Represents the deleted flag. It is managed with a getter and setter. It may have any value. It is fully mutable.
     */
    @Column(name = "is_deleted")
    private int isDelete;
    /**
     * Represents contest type description. It is managed with a getter and setter. It may have any value. It is fully
     * mutable.
     */
    @Transient
    private String contestTypeDescription;

    /**
     * Default Constructor.
     */
    public ContestFeeDetails() {
    }

    /**
     * Returns the contestTypeId field value.
     * 
     * @return contestTypeId field value.
     */
    public long getContestTypeId() {
        return contestTypeId;
    }

    /**
     * Sets the given value to contestTypeId field.
     * 
     * @param contestTypeId
     *            - the given value to set.
     */
    public void setContestTypeId(long contestTypeId) {
        this.contestTypeId = contestTypeId;
    }

    /**
     * Returns the fee field value.
     * 
     * @return fee field value.
     */
    public double getFee() {
        return fee;
    }

    /**
     * Sets the given value to fee field.
     * 
     * @param fee
     *            - the given value to set.
     */
    public void setFee(double fee) {
        this.fee = fee;
    }

    /**
     * Returns the projectContestFeeId field value.
     * 
     * @return projectContestFeeId field value.
     */
    public long getProjectContestFeeId() {
        return projectContestFeeId;
    }

    /**
     * Sets the given value to projectContestFeeId field.
     * 
     * @param projectContestFeeId
     *            - the given value to set.
     */
    public void setProjectContestFeeId(long projectContestFeeId) {
        this.projectContestFeeId = projectContestFeeId;
    }

    /**
     * Returns the projectId field value.
     * 
     * @return projectId field value.
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Sets the given value to projectId field.
     * 
     * @param projectId
     *            - the given value to set.
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * Returns the isStudio field value.
     * 
     * @return isStudio field value.
     */
    public int getIsStudio() {
        return isStudio;
    }

    /**
     * Sets the given value to isStudio field.
     * 
     * @param isStudio
     *            - the given value to set.
     */
    public void setIsStudio(int isStudio) {
        this.isStudio = isStudio;
    }

    /**
     * Returns the isDelete field value.
     * 
     * @return isDelete field value.
     */
    public int getIsDelete() {
        return isDelete;
    }

    /**
     * Sets the given value to isDelete field.
     * 
     * @param isDelete
     *            - the given value to set.
     */
    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * Returns the contestTypeDescription field value.
     * 
     * @return contestTypeDescription field value.
     */
    public String getContestTypeDescription() {
        return contestTypeDescription;
    }

    /**
     * Sets the given value to contestTypeDescription field.
     * 
     * @param contestTypeDescription
     *            - the given value to set.
     */
    public void setContestTypeDescription(String contestTypeDescription) {
        this.contestTypeDescription = contestTypeDescription;
    }

}
