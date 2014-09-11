/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.impl;

/**
 * <p>
 * This class is a container for statistics information about a list of specific contests. It is a simple JavaBean
 * (POJO) that provides getters and setters for all private attributes and performs no argument validation in the
 * setters.
 * </p>
 * <p>
 * <b>Thread Safety:</b> This class is mutable and not thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
class ContestsStat {
    /**
     * The total number of contests, excluding reposts. Can be any value. Has getter and setter.
     */
    private int totalContests;

    /**
     * The total number of reposted contests. Can be any value. Has getter and setter.
     */
    private int totalRepostedContests;

    /**
     * The total number of failed contests. Can be any value. Has getter and setter.
     */
    private int totalFailedContests;

    /**
     * The total number of bug races. Can be any value. Has getter and setter.
     */
    private int totalBugRaces;

    /**
     * The number of currently running (active) contests. Can be any value. Has getter and setter.
     */
    private int currentContests;

    /**
     * Creates an instance of ContestsStat.
     */
    public ContestsStat() {
        // Do nothing
    }

    /**
     * Retrieves the total number of contests, excluding reposts.
     *
     * @return the total number of contests, excluding reposts
     */
    public int getTotalContests() {
        return totalContests;
    }

    /**
     * Sets the total number of contests, excluding reposts.
     *
     * @param totalContests the total number of contests, excluding reposts
     */
    public void setTotalContests(int totalContests) {
        this.totalContests = totalContests;
    }

    /**
     * Retrieves the total number of reposted contests.
     *
     * @return the total number of reposted contests
     */
    public int getTotalRepostedContests() {
        return totalRepostedContests;
    }

    /**
     * Sets the total number of reposted contests.
     *
     * @param totalRepostedContests the total number of reposted contests
     */
    public void setTotalRepostedContests(int totalRepostedContests) {
        this.totalRepostedContests = totalRepostedContests;
    }

    /**
     * Retrieves the total number of failed contests.
     *
     * @return the total number of failed contests
     */
    public int getTotalFailedContests() {
        return totalFailedContests;
    }

    /**
     * Sets the total number of failed contests.
     *
     * @param totalFailedContests the total number of failed contests
     */
    public void setTotalFailedContests(int totalFailedContests) {
        this.totalFailedContests = totalFailedContests;
    }

    /**
     * Retrieves the total number of bug races.
     *
     * @return the total number of bug races
     */
    public int getTotalBugRaces() {
        return totalBugRaces;
    }

    /**
     * Sets the total number of bug races.
     *
     * @param totalBugRaces the total number of bug races
     */
    public void setTotalBugRaces(int totalBugRaces) {
        this.totalBugRaces = totalBugRaces;
    }

    /**
     * Retrieves the number of currently running (active) contests.
     *
     * @return the number of currently running (active) contests
     */
    public int getCurrentContests() {
        return currentContests;
    }

    /**
     * Sets the number of currently running (active) contests.
     *
     * @param currentContests the number of currently running (active) contests
     */
    public void setCurrentContests(int currentContests) {
        this.currentContests = currentContests;
    }
}
