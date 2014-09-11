/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.dto;

import java.io.Serializable;
import java.util.Map;

import com.topcoder.direct.services.copilot.model.CopilotProject;

/**
 * <p>
 * This class is a container for copilot project details. It is a simple JavaBean (POJO) that provides getters
 * and setters for all private attributes and performs no argument validation in the setters.
 * </p>
 * <p>
 * <b>Thread Safety:</b> This class is mutable and not thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class CopilotProjectDTO implements Serializable {
    /**
     * The constant representing that specific statistics value is currently undefined.
     */
    public static final int UNDEFINED_STAT = -1;
    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = -6256332270409355559L;
    /**
     * The copilot project. Can be any value. Has getter and setter.
     */
    private CopilotProject copilotProject;

    /**
     * The number of total planned contests for this copilot project. Can be any value. Has getter and setter.
     */
    private int totalPlannedContests;

    /**
     * The number of total real contests for this copilot project. Can be any value. Has getter and setter.
     */
    private int totalRealContests;

    /**
     * The number of total reposted contests for this copilot project. Can be any value. Has getter and
     * setter.
     */
    private int totalRepostedContests;

    /**
     * The number of total failed contests for this copilot project. Can be any value. Has getter and setter.
     */
    private int totalFailedContests;

    /**
     * The planned project duration in days. Can be any value. Has getter and setter.
     */
    private int plannedDuration;

    /**
     * The real project duration in days. Can be any value. Has getter and setter.
     */
    private int realDuration;

    /**
     * The number of planned bug races for this copilot project. Can be any value. Has getter and setter.
     */
    private int plannedBugRaces;

    /**
     * The total number of real bug races for this copilot project. Can be any value. Has getter and setter.
     */
    private int totalRealBugRaces;

    /**
     * The statistics for the copilot project by contest types. Keys are contest category names, values are
     * stats for each category. Can be any value. Has getter and setter.
     */
    private Map<String, ContestTypeStat> contestTypeStats;

    /**
     * Creates an instance of CopilotProjectDTO.
     */
    public CopilotProjectDTO() {
        // Do nothing
    }

    /**
     * Retrieves the copilot project.
     *
     * @return the copilot project
     */
    public CopilotProject getCopilotProject() {
        return copilotProject;
    }

    /**
     * Sets the copilot project.
     *
     * @param copilotProject the copilot project
     */
    public void setCopilotProject(CopilotProject copilotProject) {
        this.copilotProject = copilotProject;
    }

    /**
     * Retrieves the number of total planned contests for this copilot project.
     *
     * @return the number of total planned contests for this copilot project
     */
    public int getTotalPlannedContests() {
        return totalPlannedContests;
    }

    /**
     * Sets the number of total planned contests for this copilot project.
     *
     * @param totalPlannedContests the number of total planned contests for this copilot project
     */
    public void setTotalPlannedContests(int totalPlannedContests) {
        this.totalPlannedContests = totalPlannedContests;
    }

    /**
     * Retrieves the number of total real contests for this copilot project.
     *
     * @return the number of total real contests for this copilot project
     */
    public int getTotalRealContests() {
        return totalRealContests;
    }

    /**
     * Sets the number of total real contests for this copilot project.
     *
     * @param totalRealContests the number of total real contests for this copilot project
     */
    public void setTotalRealContests(int totalRealContests) {
        this.totalRealContests = totalRealContests;
    }

    /**
     * Retrieves the number of total reposted contests for this copilot project.
     *
     * @return the number of total reposted contests for this copilot project
     */
    public int getTotalRepostedContests() {
        return totalRepostedContests;
    }

    /**
     * Sets the number of total reposted contests for this copilot project.
     *
     * @param totalRepostedContests the number of total reposted contests for this copilot project
     */
    public void setTotalRepostedContests(int totalRepostedContests) {
        this.totalRepostedContests = totalRepostedContests;
    }

    /**
     * Retrieves the number of total failed contests for this copilot project.
     *
     * @return the number of total failed contests for this copilot project
     */
    public int getTotalFailedContests() {
        return totalFailedContests;
    }

    /**
     * Sets the number of total failed contests for this copilot project.
     *
     * @param totalFailedContests the number of total failed contests for this copilot project
     */
    public void setTotalFailedContests(int totalFailedContests) {
        this.totalFailedContests = totalFailedContests;
    }

    /**
     * Retrieves the planned project duration in days.
     *
     * @return the planned project duration in days
     */
    public int getPlannedDuration() {
        return plannedDuration;
    }

    /**
     * Sets the planned project duration in days.
     *
     * @param plannedDuration the planned project duration in days
     */
    public void setPlannedDuration(int plannedDuration) {
        this.plannedDuration = plannedDuration;
    }

    /**
     * Retrieves the real project duration in days.
     *
     * @return the real project duration in days
     */
    public int getRealDuration() {
        return realDuration;
    }

    /**
     * Sets the real project duration in days.
     *
     * @param realDuration the real project duration in days
     */
    public void setRealDuration(int realDuration) {
        this.realDuration = realDuration;
    }

    /**
     * Retrieves the number of planned bug races for this copilot project.
     *
     * @return the number of planned bug races for this copilot project
     */
    public int getPlannedBugRaces() {
        return plannedBugRaces;
    }

    /**
     * Sets the number of planned bug races for this copilot project.
     *
     * @param plannedBugRaces the number of planned bug races for this copilot project
     */
    public void setPlannedBugRaces(int plannedBugRaces) {
        this.plannedBugRaces = plannedBugRaces;
    }

    /**
     * Retrieves the total number of real bug races for this copilot project.
     *
     * @return the total number of real bug races for this copilot project
     */
    public int getTotalRealBugRaces() {
        return totalRealBugRaces;
    }

    /**
     * Sets the total number of real bug races for this copilot project.
     *
     * @param totalRealBugRaces the total number of real bug races for this copilot project
     */
    public void setTotalRealBugRaces(int totalRealBugRaces) {
        this.totalRealBugRaces = totalRealBugRaces;
    }

    /**
     * Retrieves the statistics for the copilot project by contest types.
     *
     * @return the statistics for the copilot project by contest types
     */
    public Map<String, ContestTypeStat> getContestTypeStats() {
        return contestTypeStats;
    }

    /**
     * Sets the statistics for the copilot project by contest types.
     *
     * @param contestTypeStats the statistics for the copilot project by contest types
     */
    public void setContestTypeStats(Map<String, ContestTypeStat> contestTypeStats) {
        this.contestTypeStats = contestTypeStats;
    }
}
