/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.dto;

import java.io.Serializable;

/**
 * <p>
 * This class is a container for statistics associated with some specific contest type. It is a simple
 * JavaBean (POJO) that provides getters and setters for all private attributes and performs no argument
 * validation in the setters.
 * </p>
 * <p>
 * <b>Thread Safety:</b> This class is mutable and not thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class ContestTypeStat implements Serializable {
    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = -6256332270409355556L;
    /**
     * The ID of the project category. Can be any value. Has getter and setter.
     */
    private long projectCategoryId;

    /**
     * The project category name. Can be any value. Has getter and setter.
     */
    private String projectCategoryName;

    /**
     * The number of planned contests for this category. Can be any value. Has getter and setter.
     */
    private int plannedContests;

    /**
     * The number of real contests for this category. Can be any value. Has getter and setter.
     */
    private int realContests;

    /**
     * The number of reposted contests for this category. Can be any value. Has getter and setter.
     */
    private int repostedContests;

    /**
     * The number of failed contests for this category. Can be any value. Has getter and setter.
     */
    private int failedContests;

    /**
     * Creates an instance of ContestTypeStat.
     */
    public ContestTypeStat() {
        // Do nothing
    }

    /**
     * Retrieves the ID of the project category.
     *
     * @return the ID of the project category
     */
    public long getProjectCategoryId() {
        return projectCategoryId;
    }

    /**
     * Sets the ID of the project category.
     *
     * @param projectCategoryId the ID of the project category
     */
    public void setProjectCategoryId(long projectCategoryId) {
        this.projectCategoryId = projectCategoryId;
    }

    /**
     * Retrieves the project category name.
     *
     * @return the project category name
     */
    public String getProjectCategoryName() {
        return projectCategoryName;
    }

    /**
     * Sets the project category name.
     *
     * @param projectCategoryName the project category name
     */
    public void setProjectCategoryName(String projectCategoryName) {
        this.projectCategoryName = projectCategoryName;
    }

    /**
     * Retrieves the number of planned contests for this category.
     *
     * @return the number of planned contests for this category
     */
    public int getPlannedContests() {
        return plannedContests;
    }

    /**
     * Sets the number of planned contests for this category.
     *
     * @param plannedContests the number of planned contests for this category
     */
    public void setPlannedContests(int plannedContests) {
        this.plannedContests = plannedContests;
    }

    /**
     * Retrieves the number of real contests for this category.
     *
     * @return the number of real contests for this category
     */
    public int getRealContests() {
        return realContests;
    }

    /**
     * Sets the number of real contests for this category.
     *
     * @param realContests the number of real contests for this category
     */
    public void setRealContests(int realContests) {
        this.realContests = realContests;
    }

    /**
     * Retrieves the number of reposted contests for this category.
     *
     * @return the number of reposted contests for this category
     */
    public int getRepostedContests() {
        return repostedContests;
    }

    /**
     * Sets the number of reposted contests for this category.
     *
     * @param repostedContests the number of reposted contests for this category
     */
    public void setRepostedContests(int repostedContests) {
        this.repostedContests = repostedContests;
    }

    /**
     * Retrieves the number of failed contests for this category.
     *
     * @return the number of failed contests for this category
     */
    public int getFailedContests() {
        return failedContests;
    }

    /**
     * Sets the number of failed contests for this category.
     *
     * @param failedContests the number of failed contests for this category
     */
    public void setFailedContests(int failedContests) {
        this.failedContests = failedContests;
    }
}
