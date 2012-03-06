/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.form;

import com.topcoder.direct.services.project.milestone.model.Milestone;

import java.util.List;

/**
 * <p>
 * The request form for all the project milestone operations.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - TC Cockpit Project Milestones Management Front End)
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
}
