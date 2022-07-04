/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.stresstests;

import java.util.Date;


import com.topcoder.date.workdays.DefaultWorkdaysFactory;
import com.topcoder.date.workdays.Workdays;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.Project;

/**
 * <p>
 * Mock class for Project. Here we need this mock class because Project Phases 2.0 is not available at now.
 * </p>
 *
 * @author still
 * @version 1.0
 */
final class StressMockProject extends Project {

    /**
     * Constructor.
     */
    public StressMockProject() {
        super(null, new DefaultWorkdaysFactory().createWorkdaysInstance());
    }

    /** the number of phases when getting all phases. */
    private static final int DATA_COUNT = 100;
    /**
     * Sets the project id.
     *
     * @param id the project id
     *
     */
    public void setId(long id) {
    }
    /**
     * Gets the project id.
     * @return the project id.
     */
    public long getId() {
        return 10;
    }
    /**
     * Empty mock method not used.
     * @param phase not used.
     */
    public void addPhase(Phase phase) {
    }
    /**
     * Empty mock method not used.
     * @param phase not used.
     */
    public void removePhase(Phase phase) {
    }
    /**
     * Mock method constructors a phase array of size DATA_COUNT.
     * @return all phases.
     */
    public Phase[] getAllPhases() {
        Phase[] phases = new StressMockPhase[DATA_COUNT];
        int i;
        for (i = 0; i < DATA_COUNT / 2; i++) {
            phases[i] = new StressMockPhase();
        }
        for (; i < DATA_COUNT; i++) {
            phases[i] = new StressMockPhase(i);
        }
        return phases;
    }
    /**
     * Empty mock method not used.
     * @param startDate not used.
     */
    public void setStartDate(Date startDate) {
    }
    /**
     * Mock method always returns the current date.
     * @return the current date.
     */
    public Date getStartDate() {
        return new Date();
    }
}