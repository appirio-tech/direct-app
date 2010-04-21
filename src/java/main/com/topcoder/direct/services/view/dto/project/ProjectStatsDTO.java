/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.project;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>A DTO providing the statistics on single project.</p>
 *
 * @author isv
 * @version 1.0
 */
public class ProjectStatsDTO implements Serializable {
    
    public double getFeesDraft() {
        return feesDraft;
    }

    public void setFeesDraft(double feesDraft) {
        this.feesDraft = feesDraft;
    }

    public double getFeesScheduled() {
        return feesScheduled;
    }

    public void setFeesScheduled(double feesScheduled) {
        this.feesScheduled = feesScheduled;
    }

   

    /**
     * <p>An interface to be implemented by the parties interested in getting the stats on single requested project.</p>
     */
    public static interface Aware {

        /**
         * <p>Sets the statistics for single project.</p>
         *
         * @param projectStats a <code>ProjectStatsDTO</code> providing statistics on project.
         */
        public void setProjectStats(ProjectStatsDTO projectStats);
    }

    /**
     * <p>A <code>ProjectBriefDTO</code> providing basic details for project.</p>
     */
    private ProjectBriefDTO project;

    /**
     * <p>An <code>int</code> providing the number of draft contests for project.</p>
     */
    private int draftContestsNumber;

    /**
     * <p>An <code>int</code> providing the number of currently running contests for project.</p>
     */
    private int runningContestsNumber;

    /**
     * <p>An <code>int</code> providing the number of contests in pipeline for project.</p>
     */
    private int pipelineContestsNumber;

    /**
     * <p>An <code>int</code> providing the number of finished contests for project.</p>
     */
    private int finishedContestsNumber;

    /**
     * <p>An <code>int</code> providing the number of tasked contests for project.</p>
     */
    private int taskedContestsNumber;

    /**
     * <p>A <code>Date</code> providing the start date for project.</p>
     */
    private Date startDate;

    /**
     * <p>A <code>double</code> providing the total fees for running contests for project.</p>
     */
    private double feesRunning;

    /**
     * <p>A <code>double</code> providing the total fees finalized for project.</p>
     */
    private double feesFinalized;

    /**
     * Draft contest fee
     */
    private double feesDraft;

    /**
     * Scheduled contest fee
     */
    private double feesScheduled;

  

    /**
     * <p>Constructs new <code>ProjectStatsDTO</code> instance. This implementation does nothing.</p>
     */
    public ProjectStatsDTO() {
    }

    /**
     * <p>Gets the details on project.</p>
     *
     * @return a <code>ProjectBriefDTO</code> providing basic details for project.
     */
    public ProjectBriefDTO getProject() {
        return project;
    }

    /**
     * <p>Sets the details on project.</p>
     *
     * @param project a <code>ProjectBriefDTO</code> providing basic details for project.
     */
    public void setProject(ProjectBriefDTO project) {
        this.project = project;
    }

    /**
     * <p>Gets the number of draft contests for project.</p>
     *
     * @return an <code>int</code> providing the number of draft contests for project.
     */
    public int getDraftContestsNumber() {
        return draftContestsNumber;
    }

    /**
     * <p>Sets the number of draft contests for project.</p>
     *
     * @param draftContestsNumber an <code>int</code> providing the number of draft contests for project.
     */
    public void setDraftContestsNumber(int draftContestsNumber) {
        this.draftContestsNumber = draftContestsNumber;
    }

    /**
     * <p>Gets the number of running contests for project.</p>
     *
     * @return an <code>int</code> providing the number of running contests for project.
     */
    public int getRunningContestsNumber() {
        return runningContestsNumber;
    }

    /**
     * <p>Sets the number of running contests for project.</p>
     *
     * @param runningContestsNumber an <code>int</code> providing the number of running contests for project.
     */
    public void setRunningContestsNumber(int runningContestsNumber) {
        this.runningContestsNumber = runningContestsNumber;
    }

    /**
     * <p>Gets the number of contests in pipeline for project.</p>
     *
     * @return an <code>int</code> providing the number of contests in pipeline for project.
     */
    public int getPipelineContestsNumber() {
        return pipelineContestsNumber;
    }

    /**
     * <p>Sets the number of contests in pipeline for project.</p>
     *
     * @param pipelineContestsNumber an <code>int</code> providing the number of contests in pipeline for project.
     */
    public void setPipelineContestsNumber(int pipelineContestsNumber) {
        this.pipelineContestsNumber = pipelineContestsNumber;
    }

    /**
     * <p>Gets the number of finished contests for project.</p>
     *
     * @return an <code>int</code> providing the number of finished contests for project.
     */
    public int getFinishedContestsNumber() {
        return finishedContestsNumber;
    }

    /**
     * <p>Sets the number of finished contests for project.</p>
     *
     * @param finishedContestsNumber an <code>int</code> providing the number of finished contests for project.
     */
    public void setFinishedContestsNumber(int finishedContestsNumber) {
        this.finishedContestsNumber = finishedContestsNumber;
    }

    /**
     * <p>Gets the number of tasked contests for project.</p>
     *
     * @return an <code>int</code> providing the number of tasked contests for project.
     */
    public int getTaskedContestsNumber() {
        return taskedContestsNumber;
    }

    /**
     * <p>Sets the number of tasked contests for project.</p>
     *
     * @param taskedContestsNumber an <code>int</code> providing the number of tasked contests for project.
     */
    public void setTaskedContestsNumber(int taskedContestsNumber) {
        this.taskedContestsNumber = taskedContestsNumber;
    }

    /**
     * <p>Gets the total fees for running contests for project.</p>
     *
     * @return a <code>double</code> providing the total fees for running contests for project.
     */
    public double getFeesRunning() {
        return feesRunning;
    }

    /**
     * <p>Sets the total fees for running contests for project.</p>
     *
     * @param feesRunning a <code>double</code> providing the total fees for running contests for project.
     */
    public void setFeesRunning(double feesRunning) {
        this.feesRunning = feesRunning;
    }

    /**
     * <p>Gets the total fees for finalized contests for project.</p>
     *
     * @return a <code>double</code> providing the total fees for finalized contests for project.
     */
    public double getFeesFinalized() {
        return feesFinalized;
    }

    /**
     * <p>Sets the total fees for finalized contests for project.</p>
     *
     * @param feesFinalized a <code>double</code> providing the total fees for finalized contests for project.
     */
    public void setFeesFinalized(double feesFinalized) {
        this.feesFinalized = feesFinalized;
    }

    /**
     * <p>Gets the start date for project.</p>
     *
     * @return a <code>Date</code> providing the start date for project.
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * <p>Sets the start date for project.</p>
     *
     * @param startDate a <code>Date</code> providing the start date for project.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
