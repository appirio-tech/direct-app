/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>A <code>DTO</code> providing details for single project phase.</p>
 *
 * <p>Version 1.6 (TC Direct Contest Dashboard Update Assembly) change notes:
 * - add phase type and corresponding get/set methods.
 * - add phase status and corresponding get/set methods.
 * </p>
 *
 * @author isv, TCSASSEMBLER
 * @version 1.1 (Direct Contest Dashboard assembly)
 */
public class ProjectPhaseDTO implements Serializable {

    /**
     * <p>A <code>String</code> providing the name for the phase.</p>
     */
    private String phaseName;

    /**
     * <p>A <code>Date</code> providing the start time for the phase.</p>
     */
    private Date startTime;

    /**
     * <p>A <code>Date</code> providing the end time for the phase.</p>
     */
    private Date endTime;
    
    /**
     * The phase type.
     * 
     * @since 1.1
     */
    private ProjectPhaseType phaseType;
    
    /**
     * The phase status
     * 
     * @since 1.1
     */
    private ProjectPhaseStatus phaseStatus;
    
    /**
     * The num.
     * 
     * @since 1.1
     */
    private int num;

    /**
     * <p>Constructs new <code>ProjectPhaseDTO</code> instance. This implementation does nothing.</p>
     */
    public ProjectPhaseDTO() {
    }

    /**
     * <p>Gets the end time for the phase.</p>
     *
     * @return a <code>Date</code> providing the end time for the phase.
     */
    public Date getEndTime() {
        return this.endTime;
    }

    /**
     * <p>Sets the end time for the phase.</p>
     *
     * @param endTime a <code>Date</code> providing the end time for the phase.
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * <p>Gets the start time for the phase.</p>
     *
     * @return a <code>Date</code> providing the start time for the phase.
     */
    public Date getStartTime() {
        return this.startTime;
    }

    /**
     * <p>Sets the start time for the phase.</p>
     *
     * @param startTime a <code>Date</code> providing the start time for the phase.
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * <p>Gets the name for the phase.</p>
     *
     * @return a <code>String</code> providing the name for the phase.
     */
    public String getPhaseName() {
        return this.phaseName;
    }

    /**
     * <p>Sets the name for the phase.</p>
     *
     * @param phaseName a <code>String</code> providing the name for the phase.
     */
    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }

    /**
     * Gets the phaseType field.
     * 
     * @return the phaseType
     * @since 1.1
     */
    public ProjectPhaseType getPhaseType() {
        return phaseType;
    }

    /**
     * Sets the phaseType field.
     *
     * @param phaseType the phaseType to set
     * @since 1.1
     */
    public void setPhaseType(ProjectPhaseType phaseType) {
        this.phaseType = phaseType;
    }

    /**
     * Gets the phaseStatus field.
     * 
     * @return the phaseStatus
     * @since 1.1
     */
    public ProjectPhaseStatus getPhaseStatus() {
        return phaseStatus;
    }

    /**
     * Sets the phaseStatus field.
     *
     * @param phaseStatus the phaseStatus to set
     * @since 1.1
     */
    public void setPhaseStatus(ProjectPhaseStatus phaseStatus) {
        this.phaseStatus = phaseStatus;
    }

    /**
     * Get the num field.
     *
     * @return the num
     */
    public int getNum() {
        return num;
    }

    /**
     * Set the num field.
     *
     * @param num the num to set
     */
    public void setNum(int num) {
        this.num = num;
    }
    
    /**
     * num plus one.
     */
    public void increaseNum() {
        this.num++;
    }
}
