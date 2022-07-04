/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.copilot.model;

import java.util.Date;

/**
 * <p>This class is a container for information about a single planned contest. It is a simple JavaBean (POJO) that
 * provides getters and setters for all private attributes and performs no argument validation in the setters.</p>
 *
 * <p><strong>Thread safety:</strong> This class is mutable and not thread safe.</p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class PlannedContest extends IdentifiableEntity {

    /**
     * <p>The name of the planned contest. Can be any value. Has getter and setter.</p>
     */
    private String name;

    /**
     * <p>The description of the contest. Can be any value. Has getter and setter.</p>
     */
    private String description;

    /**
     * <p>The ID of the project category. Can be any value. Has getter and setter.</p>
     */
    private long projectCategoryId;

    /**
     * <p>The ID of the copilot project plan. Can be any value. Has getter and setter.</p>
     */
    private long copilotProjectPlanId;

    /**
     * <p>The start date of the contest. Can be any value. Has getter and setter.</p>
     */
    private Date startDate;

    /**
     * <p>The end date of the contest. Can be any value. Has getter and setter.</p>
     */
    private Date endDate;

    /**
     * <p>Creates new instance of <code>{@link PlannedContest}</code> class.</p>
     */
    public PlannedContest() {
        // empty constructor
    }

    /**
     * <p>Retrieves the name of the planned contest.</p>
     *
     * @return the name of the planned contest
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Sets the name of the planned contest.</p>
     *
     * @param name the name of the planned contest
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>Retrieves the description of the contest.</p>
     *
     * @return the description of the contest
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>Sets the description of the contest.</p>
     *
     * @param description the description of the contest
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * <p>Retrieves the ID of the project category.</p>
     *
     * @return the ID of the project category
     */
    public long getProjectCategoryId() {
        return projectCategoryId;
    }

    /**
     * <p>Sets the ID of the project category.</p>
     *
     * @param projectCategoryId the ID of the project category
     */
    public void setProjectCategoryId(long projectCategoryId) {
        this.projectCategoryId = projectCategoryId;
    }

    /**
     * <p>Retrieves the ID of the copilot project plan.</p>
     *
     * @return the ID of the copilot project plan
     */
    public long getCopilotProjectPlanId() {
        return copilotProjectPlanId;
    }

    /**
     * <p>Sets the ID of the copilot project plan.</p>
     *
     * @param copilotProjectPlanId the ID of the copilot project plan
     */
    public void setCopilotProjectPlanId(long copilotProjectPlanId) {
        this.copilotProjectPlanId = copilotProjectPlanId;
    }

    /**
     * <p>Retrieves the start date of the contest.</p>
     *
     * @return the start date of the contest
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * <p>Sets the start date of the contest.</p>
     *
     * @param startDate the start date of the contest
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * <p>Retrieves the end date of the contest.</p>
     *
     * @return the end date of the contest
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * <p>Sets the end date of the contest.</p>
     *
     * @param endDate the end date of the contest
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}

