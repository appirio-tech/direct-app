/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * This class represents project contest fee percentage.
 * </p>
 * <p>
 * <strong>THREAD SAFETY:</strong> This class contains only mutable fields so therefore it is not thread safe.
 * </p>
 * 
 * @author minhu
 * @version 1.0 (Module Assembly - Contest Fee Based on % of Member Cost Admin Part)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "projectContestFeePercentage")
@Entity
@Table(name = "project_contest_fee_percentage")
public class ProjectContestFeePercentage implements Serializable {
    /**
     * <p>
     * Generated serial id.
     * </p>
     */
    private static final long serialVersionUID = -3847165443467240319L;

    /**
     * <p>
     * The id.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_contest_fee_percentage_seq")
    @SequenceGenerator(name = "project_contest_fee_percentage_seq", sequenceName = "project_contest_fee_percentage_seq", allocationSize = 5)
    @Column(name = "project_contest_fee_percentage_id")
    private long id;

    /**
     * <p>
     * The project id.
     * </p>
     */
    @Column(name = "project_id")
    private long projectId;

    /**
     * <p>
     * The contest fee percentage.
     * </p>
     */
    @Column(name = "contest_fee_percentage")
    private double contestFeePercentage;

    /**
     * <p>
     * The active flag.
     * </p>
     */
    @Column(name = "active")
    private boolean active;

    /**
     * <p>
     * The create user name.
     * </p>
     */
    @Column(name = "creation_user")
    private String createUser;

    /**
     * <p>
     * The create date.
     * </p>
     */
    @Column(name = "creation_date")
    private Date createDate;

    /**
     * <p>
     * The modify user name.
     * </p>
     */
    @Column(name = "modification_user")
    private String modifyUser;

    /**
     * <p>
     * The modify date.
     * </p>
     */
    @Column(name = "modification_date")
    private Date modifyDate;

    /**
     * <p>
     * Sets the <code>id</code> field value.
     * </p>
     * 
     * @param id the value to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * <p>
     * Gets the <code>id</code> field value.
     * </p>
     * 
     * @return the <code>id</code> field value
     */
    public long getId() {
        return this.id;
    }

    /**
     * <p>
     * Sets the <code>projectId</code> field value.
     * </p>
     * 
     * @param projectId
     *        the value to set
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * <p>
     * Gets the <code>projectId</code> field value.
     * </p>
     * 
     * @return the <code>projectId</code> field value
     */
    public long getProjectId() {
        return this.projectId;
    }

    /**
     * <p>
     * Sets the <code>contestFeePercentage</code> field value.
     * </p>
     * 
     * @param contestFeePercentage
     *        the value to set
     */
    public void setContestFeePercentage(double contestFeePercentage) {
        this.contestFeePercentage = contestFeePercentage;
    }

    /**
     * <p>
     * Gets the <code>contestFeePercentage</code> field value.
     * </p>
     * 
     * @return the <code>contestFeePercentage</code> field value
     */
    public double getContestFeePercentage() {
        return this.contestFeePercentage;
    }

    /**
     * <p>
     * Gets the active flag.
     * </p>
     * 
     * @return the active flag.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * <p>
     * Sets the active flag.
     * </p>
     *
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * <p>
     * Gets the create user name.
     * </p>
     * 
     * @return the createUser
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * <p>
     * Sets the create user name.
     * </p>
     * 
     * @param createUser the createUser to set
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * <p>
     * Gets the create date.
     * </p>
     * 
     * @return the createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * <p>
     * Sets the create date.
     * </p>
     * 
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * <p>
     * Gets the modify user name.
     * </p>
     * 
     * @return the modifyUser
     */
    public String getModifyUser() {
        return modifyUser;
    }

    /**
     * <p>
     * Sets the modify user name.
     * </p>
     * 
     * @param modifyUser the modifyUser to set
     */
    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    /**
     * <p>
     * Gets the modify date.
     * </p>
     * 
     * @return the modifyDate
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * <p>
     * Sets the modify date.
     * </p>
     * 
     * @param modifyDate the modifyDate to set
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}
