/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.enterpriseDashboard;

import java.io.Serializable;

/**
 * <p>
 *  The DTO represents the financial data of one project in the enterprise dashboard financial section.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0
 */
public class EnterpriseDashboardProjectFinancialDTO implements Serializable {
    /**
     * The direct project id.
     */
    private long projectId;

    /**
     * The direct project name.
     */
    private String projectName;

    /**
     * The direct project budget.
     */
    private long budget;

    /**
     * The actual cost of the project.
     */
    private long actualCost;

    /**
     * The planned cost of the project.
     */
    private long plannedCost;

    /**
     * Gets the direct project id.
     *
     * @return the direct project id.
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Sets the direct project id.
     *
     * @param projectId
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * Gets the project name.
     *
     * @return the project name.
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Sets the project name.
     *
     * @param projectName the project name.
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Gets the project budget.
     *
     * @return the project budget.
     */
    public long getBudget() {
        return budget;
    }

    /**
     * Sets the project budget.
     *
     * @param budget the project budget.
     */
    public void setBudget(long budget) {
        this.budget = budget;
    }

    /**
     * Gets the actual cost.
     *
     * @return the actual cost.
     */
    public long getActualCost() {
        return actualCost;
    }

    /**
     * Sets the actual cost.
     *
     * @param actualCost the actual cost.
     */
    public void setActualCost(long actualCost) {
        this.actualCost = actualCost;
    }

    /**
     * Gets the planned cost.
     *
     * @return the planned cost.
     */
    public long getPlannedCost() {
        return plannedCost;
    }

    /**
     * Sets the planned cost.
     *
     * @param plannedCost the planned cost.
     */
    public void setPlannedCost(long plannedCost) {
        this.plannedCost = plannedCost;
    }

    /**
     * Gets the projected cost.
     *
     * @return the projected cost.
     */
    public long getProjectedCost() {
        return this.actualCost + this.plannedCost;
    }
}
