/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.project;

import java.io.Serializable;

/**
 * <p>
 *     DTO used to store the data of direct project copilot.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Release Assembly - TopCoder Cockpit Project Overview Update 1)
 */
public class ProjectCopilotStatDTO implements Serializable {

    /**
     * The copilot data the the project copilot.
     */
    private ProjectCopilotDTO copilotInfo;

    /**
     * The number of the draft contest number.
     */
    private int draftContestsNumber;

    /**
     * The number of the active contest number.
     */
    private int activeContestsNumber;

    /**
     * The number of the finished contest number.
     */
    private int finishedContestsNumber;

    /**
     * The number of the failure contest number.
     */
    private int failuresContestsNumber;

    /**
     * Gets the project copilot data.
     *
     * @return the project copilot  data.
     */
    public ProjectCopilotDTO getCopilotInfo() {
        return copilotInfo;
    }

    /**
     * Sets the project copilot data.
     *
     * @param copilotInfo the project copilot data.
     */
    public void setCopilotInfo(ProjectCopilotDTO copilotInfo) {
        this.copilotInfo = copilotInfo;
    }

    /**
     * Gets the draft contests number.
     *
     * @return the draft contests number.
     */
    public int getDraftContestsNumber() {
        return draftContestsNumber;
    }

    /**
     * Sets the the draft contests number.
     *
     * @param draftContestsNumber  the draft contests number.
     */
    public void setDraftContestsNumber(int draftContestsNumber) {
        this.draftContestsNumber = draftContestsNumber;
    }

    /**
     * Gets the active contests number.
     *
     * @return he active contests number.
     */
    public int getActiveContestsNumber() {
        return activeContestsNumber;
    }

    /**
     * Sets the active contests number.
     *
     * @param activeContestsNumber the active contests number.
     */
    public void setActiveContestsNumber(int activeContestsNumber) {
        this.activeContestsNumber = activeContestsNumber;
    }

    /**
     * Gets the finished contests number.
     *
     * @return the finished contests number.
     */
    public int getFinishedContestsNumber() {
        return finishedContestsNumber;
    }

    /**
     * Sets the finished contests number.
     *
     * @param finishedContestsNumber the finished contests number.
     */
    public void setFinishedContestsNumber(int finishedContestsNumber) {
        this.finishedContestsNumber = finishedContestsNumber;
    }

    /**
     * Gets the failure contests number.
     *
     * @return the failure contests number.
     */
    public int getFailuresContestsNumber() {
        return failuresContestsNumber;
    }

    /**
     * Sets the the failure contests number.
     *
     * @param failuresContestsNumber the failure contests number.
     */
    public void setFailuresContestsNumber(int failuresContestsNumber) {
        this.failuresContestsNumber = failuresContestsNumber;
    }

    /**
     * Gets the copilot fulfillment on the project.
     *
     * @return the copilot fulfillment
     */
    public double getFulfillment() {
        return  finishedContestsNumber * 1.0 / (finishedContestsNumber + failuresContestsNumber);
    }
}
