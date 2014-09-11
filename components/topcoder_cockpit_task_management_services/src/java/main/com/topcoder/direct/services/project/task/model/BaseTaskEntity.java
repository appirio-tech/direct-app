/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.model;

import java.util.List;

/**
 * <p>
 * This is a base class that holds name, notes and associated project milestones / contests.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is not thread safe because it is mutable.
 * </p>
 *
 * @author albertwang, sparemax
 * @version 1.0
 */
public abstract class BaseTaskEntity extends AuditableEntity {
    /**
     * The serial version ID.
     */
    private static final long serialVersionUID = -6856159602466730709L;
    /**
     * <p>
     * Represents the name.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private String name;
    /**
     * <p>
     * Represents the notes.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private String notes;
    /**
     * <p>
     * Represents the associated to project milestones.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private List<MilestoneDTO> associatedToProjectMilestones;
    /**
     * <p>
     * Represents the associated to contests.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private List<ContestDTO> associatedToContests;

    /**
     * Creates an instance of BaseTaskEntity.
     */
    protected BaseTaskEntity() {
        // Empty
    }

    /**
     * Gets the name.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name
     *            the name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the notes.
     *
     * @return the notes.
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the notes.
     *
     * @param notes
     *            the notes.
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Gets the associated to project milestones.
     *
     * @return the associated to project milestones.
     */
    public List<MilestoneDTO> getAssociatedToProjectMilestones() {
        return associatedToProjectMilestones;
    }

    /**
     * Sets the associated to project milestones.
     *
     * @param associatedToProjectMilestones
     *            the associated to project milestones.
     */
    public void setAssociatedToProjectMilestones(List<MilestoneDTO> associatedToProjectMilestones) {
        this.associatedToProjectMilestones = associatedToProjectMilestones;
    }

    /**
     * Gets the associated to contests.
     *
     * @return the associated to contests.
     */
    public List<ContestDTO> getAssociatedToContests() {
        return associatedToContests;
    }

    /**
     * Sets the associated to contests.
     *
     * @param associatedToContests
     *            the associated to contests.
     */
    public void setAssociatedToContests(List<ContestDTO> associatedToContests) {
        this.associatedToContests = associatedToContests;
    }
}