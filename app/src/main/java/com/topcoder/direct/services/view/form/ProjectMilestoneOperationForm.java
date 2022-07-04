/*
 * Copyright (C) 2012 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.form;

import com.topcoder.direct.services.project.milestone.model.Milestone;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * The request form for all the project milestone operations.
 * </p>
 *
 * <p>
 * Version 1.1 (Module Assembly - TC Cockpit Contest Milestone Association Milestone Page Update)
 * <ul>
 *     <li>Added property {@link #contestId} and its getter and setter</li>
 * </ul>
 * </p>
 *
 * @author GreatKevin
 * @version 1.1
 * @Since 1.0 (Module Assembly - TC Cockpit Project Milestones Management Front End)
 */
public class ProjectMilestoneOperationForm extends ProjectIdForm {

    /**
     * The milestone id to process.
     */
    private long milestoneId;

    /**
     * The single milestone instance to process.
     */
    private Milestone milestone;

    /**
     * A list of project milestones to process.
     */
    private List<Milestone> milestones;

    /**
     * The completion  date of the milestone.
     */
	private Date completionDate;

    /**
     * The contest id.
     *
     * @since 1.1
     */
    private long contestId;

    /**
     * Gets the milestone id.
     *
     * @return the milestone id.
     */
    public long getMilestoneId() {
        return milestoneId;
    }

    /**
     * Sets the milestone id.
     *
     * @param milestoneId the milestone id.
     */
    public void setMilestoneId(long milestoneId) {
        this.milestoneId = milestoneId;
    }

    /**
     * Gets the milestone.
     *
     * @return the milestone.
     */
    public Milestone getMilestone() {
        return milestone;
    }

    /**
     * Sets the milestone.
     *
     * @param milestone the milestone to set.
     */
    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    /**
     * Gets the list of milestones.
     *
     * @return the list of milestones.
     */
    public List<Milestone> getMilestones() {
        return milestones;
    }

    /**
     * Sets the list of milestones.
     *
     * @param milestones the list of milestones.
     */
    public void setMilestones(List<Milestone> milestones) {
        this.milestones = milestones;
    }

    /**
     * Gets the compeltion date of the milestone.
     * 
     * @return the completion date of the milestone.
     */
    public Date getCompletionDate() {
        return completionDate;
    }

    /**
     *Sets the completion date of the milestone.
     *
     *@param completionDate the completion date of the milestone.
     */
    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    /**
     * Gets the contest id.
     *
     * @return the contest id.
     * @since 1.1
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * Sets the contest id.
     *
     * @param contestId the contest id.
     * @since 1.1
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }
}
