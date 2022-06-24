/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.dto;

import java.util.Map;

/**
 * <p>
 * This class is a container for copilot profile details. It is a simple JavaBean (POJO) that provides getters
 * and setters for all private attributes and performs no argument validation in the setters.
 * </p>
 * <p>
 * <b>Thread Safety:</b> This class is mutable and not thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class CopilotProfileDTO extends CopilotPoolMember {
    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = -6256332270409355557L;

    /**
     * The statistics for the copilot by contest types. Keys are contest category names, values - stats for
     * each category. Can be any value. Has getter and setter.
     */
    private Map<String, ContestTypeStat> contestTypeStats;

    /**
     * The earnings of the copilot (for copilot activity only). Can be any value. Has getter and setter.
     */
    private double earnings;

    /**
     * Creates an instance of CopilotProfileDTO.
     */
    public CopilotProfileDTO() {
        // Do nothing
    }

    /**
     * Retrieves the statistics for the copilot by contest types.
     *
     * @return the statistics for the copilot by contest types
     */
    public Map<String, ContestTypeStat> getContestTypeStats() {
        return contestTypeStats;
    }

    /**
     * Sets the statistics for the copilot by contest types.
     *
     * @param contestTypeStats the statistics for the copilot by contest types
     */
    public void setContestTypeStats(Map<String, ContestTypeStat> contestTypeStats) {
        this.contestTypeStats = contestTypeStats;
    }

    /**
     * Retrieves the earnings of the copilot (for copilot activity only).
     *
     * @return the earnings of the copilot (for copilot activity only)
     */
    public double getEarnings() {
        return earnings;
    }

    /**
     * Sets the earnings of the copilot (for copilot activity only).
     *
     * @param earnings the earnings of the copilot (for copilot activity only)
     */
    public void setEarnings(double earnings) {
        this.earnings = earnings;
    }
}
