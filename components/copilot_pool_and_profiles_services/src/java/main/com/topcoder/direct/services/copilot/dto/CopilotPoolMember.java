/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.dto;

import java.io.Serializable;

import com.topcoder.direct.services.copilot.model.CopilotProfile;

/**
 * <p>
 * This class is a container for information about a single copilot pool member. It is a simple JavaBean
 * (POJO) that provides getters and setters for all private attributes and performs no argument validation in
 * the setters.
 * </p>
 * <p>
 * <b>Thread Safety:</b> This class is mutable and not thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class CopilotPoolMember implements Serializable {
    /**
     * The constant representing that specific statistics value is currently undefined.
     */
    public static final int UNDEFINED_STAT = -1;

    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = -6256332270409355558L;
    /**
     * The copilot profile. Can be any value. Has getter and setter.
     */
    private CopilotProfile copilotProfile;

    /**
     * The total number of projects supported by this copilot. Can be any value. Has getter and setter.
     */
    private int totalProjects;

    /**
     * The total number of contests supported by this copilot, excluding reposted ones. Can be any value. Has
     * getter and setter.
     */
    private int totalContests;

    /**
     * The total number of reposted contests supported by this copilot. Can be any value. Has getter and
     * setter.
     */
    private int totalRepostedContests;

    /**
     * The total number of failed contests supported by this copilot. Can be any value. Has getter and setter.
     */
    private int totalFailedContests;

    /**
     * The total number of bug races supported by this copilot. Can be any value. Has getter and setter.
     */
    private int totalBugRaces;

    /**
     * The number of currently running (active) projects supported by this copilot. Can be any value. Has
     * getter and setter.
     */
    private int currentProjects;

    /**
     * The number of currently running (active) contests supported by this copilot. Can be any value. Has
     * getter and setter.
     */
    private int currentContests;

    /**
     * Creates an instance of CopilotPoolMember.
     */
    public CopilotPoolMember() {
        // Do nothing
    }

    /**
     * Retrieves the copilot profile.
     *
     * @return the copilot profile
     */
    public CopilotProfile getCopilotProfile() {
        return copilotProfile;
    }

    /**
     * Sets the copilot profile.
     *
     * @param copilotProfile the copilot profile
     */
    public void setCopilotProfile(CopilotProfile copilotProfile) {
        this.copilotProfile = copilotProfile;
    }

    /**
     * Retrieves the total number of projects supported by this copilot.
     *
     * @return the total number of projects supported by this copilot
     */
    public int getTotalProjects() {
        return totalProjects;
    }

    /**
     * Sets the total number of projects supported by this copilot.
     *
     * @param totalProjects the total number of projects supported by this copilot
     */
    public void setTotalProjects(int totalProjects) {
        this.totalProjects = totalProjects;
    }

    /**
     * Retrieves the total number of contests supported by this copilot, excluding reposted ones.
     *
     * @return the total number of contests supported by this copilot, excluding reposted ones
     */
    public int getTotalContests() {
        return totalContests;
    }

    /**
     * Sets the total number of contests supported by this copilot, excluding reposted ones.
     *
     * @param totalContests the total number of contests supported by this copilot, excluding reposted ones
     */
    public void setTotalContests(int totalContests) {
        this.totalContests = totalContests;
    }

    /**
     * Retrieves the total number of reposted contests supported by this copilot.
     *
     * @return the total number of reposted contests supported by this copilot
     */
    public int getTotalRepostedContests() {
        return totalRepostedContests;
    }

    /**
     * Sets the total number of reposted contests supported by this copilot.
     *
     * @param totalRepostedContests the total number of reposted contests supported by this copilot
     */
    public void setTotalRepostedContests(int totalRepostedContests) {
        this.totalRepostedContests = totalRepostedContests;
    }

    /**
     * Retrieves the total number of failed contests supported by this copilot.
     *
     * @return the total number of failed contests supported by this copilot
     */
    public int getTotalFailedContests() {
        return totalFailedContests;
    }

    /**
     * Sets the total number of failed contests supported by this copilot.
     *
     * @param totalFailedContests the total number of failed contests supported by this copilot
     */
    public void setTotalFailedContests(int totalFailedContests) {
        this.totalFailedContests = totalFailedContests;
    }

    /**
     * Retrieves the total number of bug races supported by this copilot.
     *
     * @return the total number of bug races supported by this copilot
     */
    public int getTotalBugRaces() {
        return totalBugRaces;
    }

    /**
     * Sets the total number of bug races supported by this copilot.
     *
     * @param totalBugRaces the total number of bug races supported by this copilot
     */
    public void setTotalBugRaces(int totalBugRaces) {
        this.totalBugRaces = totalBugRaces;
    }

    /**
     * Retrieves the number of currently running (active) projects supported by this copilot.
     *
     * @return the number of currently running (active) projects supported by this copilot
     */
    public int getCurrentProjects() {
        return currentProjects;
    }

    /**
     * Sets the number of currently running (active) projects supported by this copilot.
     *
     * @param currentProjects the number of currently running (active) projects supported by this copilot
     */
    public void setCurrentProjects(int currentProjects) {
        this.currentProjects = currentProjects;
    }

    /**
     * Retrieves the number of currently running (active) contests supported by this copilot.
     *
     * @return the number of currently running (active) contests supported by this copilot
     */
    public int getCurrentContests() {
        return currentContests;
    }

    /**
     * Sets the number of currently running (active) contests supported by this copilot.
     *
     * @param currentContests the number of currently running (active) contests supported by this copilot
     */
    public void setCurrentContests(int currentContests) {
        this.currentContests = currentContests;
    }
}
