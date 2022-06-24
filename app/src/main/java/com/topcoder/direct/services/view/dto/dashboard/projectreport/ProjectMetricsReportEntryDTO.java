/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard.projectreport;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * The DTO to store info the one entry of project metrics report data.
 * </p>
 * 
 * @author TCSASSEMBER
 * @version 1.0 (TC TC Cockpit Project Metrics Report)
 */
public class ProjectMetricsReportEntryDTO implements Serializable {
	private final static String INACTIVE = "Inactive";
	
	private final static String ARCHIVED = "Archived";
    /**
     * project id
     */
	private long projectId;
	
    /**
     * project name
     */
	private String projectName;
    /**
     * project type
     */
	private String projectType;
    /**
     * project status
     */
	private String projectStatus;
    /**
     * project fulfillment
     */
	private double projectFulfillment;
    /**
     * total budget
     */
	private String totalBudget;
    /**
     * actual cost
     */
	private double actualCost;
    /**
     * projected cost
     */
	private double projectedCost;
    /**
     * planned cost
     */
	private double plannedCost;
    /**
     * start date
     */
	private Date startDate;
    /**
     * completion date
     */
	private Date completionDate;
    /**
     * total contests
     */
	private Integer totalContests;
	
	private Integer numDraft;
	private double costDraft;
	private Integer numScheduled;
	private double costScheduled;
	private Integer numActive;
	private double costActive;
	private Integer numFinished;
	private double costFinished;
	private Integer numCanceled;
	private double costCanceled;

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getProjectStatus() {
		if(INACTIVE.equals(projectStatus.trim())){
			return ARCHIVED;
		}
		return projectStatus;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}

	public double getProjectFulfillment() {
		return projectFulfillment;
	}

	public void setProjectFulfillment(double projectFulfillment) {
		this.projectFulfillment = projectFulfillment;
	}

	public String getTotalBudget() {
		return totalBudget;
	}

	public void setTotalBudget(String totalBudget) {
		this.totalBudget = totalBudget;
	}

	public double getActualCost() {
		return actualCost;
	}

	public void setActualCost(double actualCost) {
		this.actualCost = actualCost;
	}

	public double getProjectedCost() {
		return projectedCost;
	}

	public void setProjectedCost(double projectedCost) {
		this.projectedCost = projectedCost;
	}

	public double getPlannedCost() {
		return plannedCost;
	}

	public void setPlannedCost(double plannedCost) {
		this.plannedCost = plannedCost;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	public Integer getTotalContests() {
		return totalContests;
	}

	public void setTotalContests(Integer totalContests) {
		this.totalContests = totalContests;
	}

	public Integer getNumDraft() {
		return numDraft;
	}

	public void setNumDraft(Integer numDraft) {
		this.numDraft = numDraft;
	}

	public double getCostDraft() {
		return costDraft;
	}

	public void setCostDraft(double costDraft) {
		this.costDraft = costDraft;
	}

	public Integer getNumScheduled() {
		return numScheduled;
	}

	public void setNumScheduled(Integer numScheduled) {
		this.numScheduled = numScheduled;
	}

	public double getCostScheduled() {
		return costScheduled;
	}

	public void setCostScheduled(double costScheduled) {
		this.costScheduled = costScheduled;
	}

	public Integer getNumActive() {
		return numActive;
	}

	public void setNumActive(Integer numActive) {
		this.numActive = numActive;
	}

	public double getCostActive() {
		return costActive;
	}

	public void setCostActive(double costActive) {
		this.costActive = costActive;
	}

	public Integer getNumFinished() {
		return numFinished;
	}

	public void setNumFinished(Integer numFinished) {
		this.numFinished = numFinished;
	}

	public double getCostFinished() {
		return costFinished;
	}

	public void setCostFinished(double costFinished) {
		this.costFinished = costFinished;
	}

	public Integer getNumCanceled() {
		return numCanceled;
	}

	public void setNumCanceled(Integer numCanceled) {
		this.numCanceled = numCanceled;
	}

	public double getCostCanceled() {
		return costCanceled;
	}

	public void setCostCanceled(double costCanceled) {
		this.costCanceled = costCanceled;
	}

	
}
