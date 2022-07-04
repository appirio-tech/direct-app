/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline.entities;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * <p>
 * Represents the competition change history. - it tracks the change in value for a specific type of change in the
 * competition.
 * </p>
 * 
 * <p>
 * For Pipeline Conversion Cockpit Integration Assembly 1 v1.0
 *      - Added contestId parameter.
 *      - Added @XmlSee definition
 *      - Removed it being abstract class. It's complete dto in itself.
 * </p>     
 *
 * @author TCSASSEMBLER
 * @since Pipeline Conversion Service Layer Assembly v1.0
 */
@MappedSuperclass
@XmlSeeAlso ({SoftwareCompetitionChangeHistory.class})
public abstract class CompetitionChangeHistory implements Serializable {
    /** serial version UID. */
    private static final long serialVersionUID = -569637477874600887L;

    /** The change history id. */
    @Id
    @Column(name = "id")
    private long id;

    /** The manager. */
    @Column(name = "manager", length=45)
    private String manager;

    /** The client of the competition. */
    @Column(name = "client", length=45)
    private String client;

    /** Type of the competition. */
    @Column(name = "type", length=45)
    private String type;

    /** New data for the change. */
    @Column(name = "new_data", length=45)
    private String newData;

    /** Old data for the change. */
    @Column(name = "old_data", length=45)
    private String oldData;

    /** Initial data for the value. */
    @Column(name = "initial_data", length=45)
    private String initialData;

    /** The time of change. */
    @Column(name = "change_time")
    private Date changeTime;

    /** The type of change. */
    @Column(name = "change_type", length=45)
    @Enumerated(EnumType.STRING)
    private CompetitionChangeType changeType;

    /** The pipeline info id. Column name mapping is defined by the sub class. */
    @Column(name="pipelineInfoId")
    private long pipelineInfoId;
    
    /**
     * Represents the contest id for the change history
     * 
     * @since Pipeline Conversion Cockpit Integration Assembly 1 v1.0
     */
    @Transient
    private long contestId;

    /**
     * Empty default constructor.
     */
    public CompetitionChangeHistory() {
    }

    /**
     * <p>
     * Gets the id
     * </p>
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * <p>
     * Sets the id
     * </p>
     *
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * <p>
     * Gets the manager
     * </p>
     *
     * @return the manager
     */
    public String getManager() {
        return manager;
    }

    /**
     * <p>
     * Sets the manager
     * </p>
     *
     * @param manager the manager to set
     */
    public void setManager(String manager) {
        this.manager = manager;
    }

    /**
     * <p>
     * Gets the client
     * </p>
     *
     * @return the client
     */
    public String getClient() {
        return client;
    }

    /**
     * <p>
     * Sets the client
     * </p>
     *
     * @param client the client to set
     */
    public void setClient(String client) {
        this.client = client;
    }

    /**
     * <p>
     * Gets the type
     * </p>
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * <p>
     * Sets the type
     * </p>
     *
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * <p>
     * Gets the newData
     * </p>
     *
     * @return the newData
     */
    public String getNewData() {
        return newData;
    }

    /**
     * <p>
     * Sets the newData
     * </p>
     *
     * @param newData the newData to set
     */
    public void setNewData(String newData) {
        this.newData = newData;
    }

    /**
     * <p>
     * Gets the oldData
     * </p>
     *
     * @return the oldData
     */
    public String getOldData() {
        return oldData;
    }

    /**
     * <p>
     * Sets the oldData
     * </p>
     *
     * @param oldData the oldData to set
     */
    public void setOldData(String oldData) {
        this.oldData = oldData;
    }

    /**
     * <p>
     * Gets the initialData
     * </p>
     *
     * @return the initialData
     */
    public String getInitialData() {
        return initialData;
    }

    /**
     * <p>
     * Sets the initialData
     * </p>
     *
     * @param initialData the initialData to set
     */
    public void setInitialData(String initialData) {
        this.initialData = initialData;
    }

    /**
     * <p>
     * Gets the changeTime
     * </p>
     *
     * @return the changeTime
     */
    public Date getChangeTime() {
        return changeTime;
    }

    /**
     * <p>
     * Sets the changeTime
     * </p>
     *
     * @param changeTime the changeTime to set
     */
    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    /**
     * <p>
     * Gets the changeType
     * </p>
     *
     * @return the changeType
     */
    public CompetitionChangeType getChangeType() {
        return changeType;
    }

    /**
     * <p>
     * Sets the changeType
     * </p>
     *
     * @param changeType the changeType to set
     */
    public void setChangeType(CompetitionChangeType changeType) {
        this.changeType = changeType;
    }

    /**
     * <p>
     * Gets the pipelineInfoId
     * </p>
     *
     * @return the pipelineInfoId
     */
    public long getPipelineInfoId() {
        return pipelineInfoId;
    }

    /**
     * <p>
     * Sets the pipelineInfoId
     * </p>
     *
     * @param pipelineInfoId the pipelineInfoId to set
     */
    public void setPipelineInfoId(long pipelineInfoId) {
        this.pipelineInfoId = pipelineInfoId;
    }

    /**
     * Gets the contest id for this change history.
     * 
     * @return the contest id for this change history.
     * 
     * @since Pipeline Conversion Cockpit Integration Assembly 1 v1.0
     */
    public long getContestId() {
        return this.contestId;
    }

    /**
     * Sets the contest id for this change history.
     * 
     * @param contestId the contest id for this change history
     * 
     * @since Pipeline Conversion Cockpit Integration Assembly 1 v1.0
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }
}
