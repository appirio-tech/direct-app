/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.stresstests;

import java.util.Date;

import com.topcoder.project.phases.Dependency;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;

/**
 * <p>
 * Mock class for Phase. Here we need this mock class because Project Phases 2.0 is not available at now.
 * </p>
 * @author still
 * @version 1.0
 */
final class StressMockPhase extends Phase {
    /**
     * Represents the phase id.
     */
    private long id = 0;
    /**
     * Represents the phase type.
     */
    private PhaseType phaseType;
    /**
     * Represents the phase status.
     */
    private PhaseStatus phaseStatus;

    /**
     * Empty constructor.
     */
    public StressMockPhase() {
        super(new StressMockProject(), 0);
    }

    /**
     * Constructor for id.
     *
     * @param id the id to be set.
     */
    public StressMockPhase(int id) {
        super(new StressMockProject(), id);
        this.id = id;
    }

    /**
     * Sets the phase id.
     *
     * @param id the phase id
     *
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the phase id.
     *
     * @return the phase id
     */
    public long getId() {
        return id;
    }
    /**
     * Return the project this phase belong to.
     *
     * @return the project this phase belongs to
     */
    public Project getProject() {
        return new StressMockProject();
    }

    /**
     * Sets the phase type.
     *
     * @param phaseType the phase type
     */
    public void setPhaseType(PhaseType phaseType) {
        this.phaseType = phaseType;
    }
    /**
     * Gets the phase type.
     *
     * @return the phase type
     */
    public PhaseType getPhaseType() {
        return phaseType;
    }

    /**
     * Sets the phase status.
     *
     * @param phaseStatus the phase status
     */
    public void setPhaseStatus(PhaseStatus phaseStatus) {
        this.phaseStatus = phaseStatus;
    }
    /**
     * Gets the phase status.
     *
     * @return the phase status
     */
    public PhaseStatus getPhaseStatus() {
        return phaseStatus;
    }

    /**
     * Empty mock method not used.
     * @param fixedStartDate not used.
     */
    public void setFixedStartDate(Date fixedStartDate) {
    }
    /**
     * Mock method always returns the current date.
     * @return the current date.
     */
    public Date getFixedStartDate() {
        return new Date();
    }
    /**
     * Empty mock method not used.
     * @param scheduledStartDate not used.
     */
    public void setScheduledStartDate(Date scheduledStartDate) {
    }
    /**
     * Mock method always returns the current date.
     * @return the current date.
     */
    public Date getScheduledStartDate() {
        return new Date();
    }
    /**
     * Empty mock method not used.
     * @param scheduledEndDate not used.
     */
    public void setScheduledEndDate(Date scheduledEndDate) {
    }
    /**
     * Mock method always returns the current date.
     * @return the current date.
     */
    public Date getScheduledEndDate() {
        return new Date();
    }
    /**
     * Empty mock method not used.
     * @param length not used.
     */
    public void setLength(long length) {
    }
    /**
     * Mock method always returns 0.
     * @return 0.
     */
    public long getLength() {
        return 0;
    }
    /**
     * Empty mock method not used.
     * @param actualStartDate not used.
     */
    public void setActualStartDate(Date actualStartDate) {
    }
    /**
     * Mock method always returns the current date.
     * @return the current date.
     */
    public Date getActualStartDate() {
        return new Date();
    }
    /**
     * Empty mock method not used.
     * @param actualEndDate not used.
     */
    public void setActualEndDate(Date actualEndDate) {
    }
    /**
     * Mock method always returns the current date.
     * @return the current date.
     */
    public Date getActualEndDate() {
        return new Date();
    }
    /**
     * Empty mock method not used.
     * @param dependency not used.
     */
    public void addDependency(Dependency dependency) {
    }
    /**
     * Empty mock method not used.
     * @param dependency not used.
     */
    public void removeDependency(Dependency dependency) {
    }
    /**
     * Empty mock method not used.
     * @return empty Dependency array.
     */
    public Dependency[] getAllDependencies() {
        return new Dependency[0];
    }
    /**
     * Mock method always returns the current date.
     * @return the current date.
     */
    public Date calcStartDate() {
        return new Date();
    }
    /**
     * Mock method always returns the current date.
     * @return the current date.
     */
    public Date calcEndDate() {
        return new Date();
    }
}
