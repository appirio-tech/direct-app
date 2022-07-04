/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.model;

import java.util.Set;

/**
 * <p>This class is a container for information about a single copilot project plan. It is a simple JavaBean (POJO) that
 * provides getters and setters for all private attributes and performs no argument validation in the setters.</p>
 *
 * <p><strong>Thread safety:</strong> This class is mutable and not thread safe.</p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class CopilotProjectPlan extends IdentifiableEntity {

    /**
     * <p>The planned duration of the copilot project in days. Can be any value. Has getter and setter.</p>
     */
    private int plannedDuration;

    /**
     * <p>The planned number of bug races. Can be any value. Has getter and setter.</p>
     */
    private int plannedBugRaces;

    /**
     * <p>The ID of the copilot project. Can be any value. Has getter and setter.</p>
     */
    private long copilotProjectId;

    /**
     * <p>The planned contests for the copilot project. Can be any value. Has getter and setter.</p>
     */
    private Set<PlannedContest> plannedContests;

    /**
     * <p>Creates new instance of <code>{@link CopilotProjectPlan}</code> class.</p>
     */
    public CopilotProjectPlan() {
        // empty constructor
    }

    /**
     * <p>Retrieves the planned duration of the copilot project in days.</p>
     *
     * @return the planned duration of the copilot project in days
     */
    public int getPlannedDuration() {
        return plannedDuration;
    }

    /**
     * <p>Sets the planned duration of the copilot project in days.</p>
     *
     * @param plannedDuration the planned duration of the copilot project in days
     */
    public void setPlannedDuration(int plannedDuration) {
        this.plannedDuration = plannedDuration;
    }

    /**
     * <p>Retrieves the planned number of bug races.</p>
     *
     * @return the planned number of bug races
     */
    public int getPlannedBugRaces() {
        return plannedBugRaces;
    }

    /**
     * <p>Sets the planned number of bug races.</p>
     *
     * @param plannedBugRaces the planned number of bug races
     */
    public void setPlannedBugRaces(int plannedBugRaces) {
        this.plannedBugRaces = plannedBugRaces;
    }

    /**
     * <p>Retrieves the ID of the copilot project.</p>
     *
     * @return the ID of the copilot project
     */
    public long getCopilotProjectId() {
        return copilotProjectId;
    }

    /**
     * <p>Sets the ID of the copilot project.</p>
     *
     * @param copilotProjectId the ID of the copilot project
     */
    public void setCopilotProjectId(long copilotProjectId) {
        this.copilotProjectId = copilotProjectId;
    }

    /**
     * <p>Retrieves the planned contests for the copilot project.</p>
     *
     * @return the planned contests for the copilot project
     */
    public Set<PlannedContest> getPlannedContests() {
        return plannedContests;
    }

    /**
     * <p>Sets the planned contests for the copilot project.</p>
     *
     * @param plannedContests the planned contests for the copilot project
     */
    public void setPlannedContests(Set<PlannedContest> plannedContests) {
        this.plannedContests = plannedContests;
    }
}

