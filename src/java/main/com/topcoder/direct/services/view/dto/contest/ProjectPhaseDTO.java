/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>A <code>DTO</code> providing details for single project phase.</p>
 *
 * @author isv
 * @version 1.0 (Direct Contest Dashboard assembly)
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
}
