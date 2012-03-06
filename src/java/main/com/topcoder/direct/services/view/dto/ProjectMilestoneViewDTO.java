/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto;

import com.topcoder.direct.services.project.milestone.model.Milestone;
import com.topcoder.direct.services.project.milestone.model.ResponsiblePerson;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * The DTO for different views of the project milestone management.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - TC Cockpit Project Milestones Management Front End)
 */
public class ProjectMilestoneViewDTO extends CommonDTO implements Serializable {
    /**
     * The list of overdue milestones.
     */
    private List<Milestone> overdueMilestones;

    /**
     * The list of upcoming milestones.
     */
    private List<Milestone> upcomingMilestones;

    /**
     * The list of completed milestones.
     */
    private List<Milestone> completedMilestones;

    /**
     * The list of responsible person of the project.
     */
    private List<ResponsiblePerson> responsiblePersons;

    /**
     * Gets the list of overdue milestones.
     *
     * @return the list of overdue milestones.
     */
    public List<Milestone> getOverdueMilestones() {
        return overdueMilestones;
    }

    /**
     * Sets the list of overdue milestones.
     *
     * @param overdueMilestones the list of overdue milestones.
     */
    public void setOverdueMilestones(List<Milestone> overdueMilestones) {
        this.overdueMilestones = overdueMilestones;
    }

    /**
     * Gets the list of upcoming milestones.
     *
     * @return the list of upcoming milestones.
     */
    public List<Milestone> getUpcomingMilestones() {
        return upcomingMilestones;
    }

    /**
     * Sets the list of upcoming milestones.
     *
     * @param upcomingMilestones the list of upcoming milestones.
     */
    public void setUpcomingMilestones(List<Milestone> upcomingMilestones) {
        this.upcomingMilestones = upcomingMilestones;
    }

    /**
     * Gets the list of upcoming milestones.
     *
     * @return the list of upcoming milestones.
     */
    public List<Milestone> getCompletedMilestones() {
        return completedMilestones;
    }

    /**
     * Gets the list of completed milestones.
     *
     * @param completedMilestones the list of completed milestones.
     */
    public void setCompletedMilestones(List<Milestone> completedMilestones) {
        this.completedMilestones = completedMilestones;
    }

    /**
     * Gets the list of responsible person.
     *
     * @return the list of responsible person.
     */
    public List<ResponsiblePerson> getResponsiblePersons() {
        return responsiblePersons;
    }

    /**
     * Sets the list of responsible person.
     *
     * @param responsiblePersons the list of responsible person.
     */
    public void setResponsiblePersons(List<ResponsiblePerson> responsiblePersons) {
        this.responsiblePersons = responsiblePersons;
    }
}
