/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard.calendar;

import com.topcoder.direct.services.project.milestone.model.Milestone;
import com.topcoder.direct.services.view.dto.CommonDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The DTO for action <code>DashboardMilestoneCalendarAction</code>.
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Release Assembly - TC Cockpit Enterprise Calendar Revamp)
 */
public class DashboardMilestoneCalendarDTO extends CommonDTO {

    /**
     * The list of milestones to show on the enterprise calendar.
     */
    private List<Milestone> milestones;

    /**
     * The responsible person ids.
     */
    private Set<Long> responsiblePersonIds;

    /**
     * The projects.
     */
    private Map<Long, String> projects;

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
     * Gets the responsible person ids.
     *
     * @return the responsible person ids.
     */
    public Set<Long> getResponsiblePersonIds() {
        return responsiblePersonIds;
    }

    /**
     * Sets the responsible person ids.
     *
     * @param responsiblePersonIds the responsible person ids.
     */
    public void setResponsiblePersonIds(Set<Long> responsiblePersonIds) {
        this.responsiblePersonIds = responsiblePersonIds;
    }

    /**
     * Gets the projects.
     *
     * @return the projects.
     */
    public Map<Long, String> getProjects() {
        return projects;
    }

    /**
     * Sets the projects.
     *
     * @param projects the projects.
     */
    public void setProjects(Map<Long, String> projects) {
        this.projects = projects;
    }
}
