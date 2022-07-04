/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto;

import java.io.Serializable;

/**
 * <p>A DTO providing the statistics on available co-piloting projects and available co-pilots.</p>
 *
 * @author isv
 * @version 1.0
 */
public class CoPilotStatsDTO implements Serializable {

    /**
     * <p>An interface to be implemented by the parties interested in getting the stats on co-piloting projects and
     * available co-pilots.</p>
     */
    public static interface Aware {

        /**
         * <p>Sets the statistics for co-piloting projects and co-pilots.</p>
         *
         * @param coPilotStats a <code>CoPilotStatsDTO</code> providing statistics about co-piloting projects.
         */
        public void setCoPilotStats(CoPilotStatsDTO coPilotStats);
    }

    /**
     * <p>An <code>int</code> providing the number of available co-pilots.</p>
     */
    private int availableCopilots;

    /**
     * <p>An <code>int</code> providing the number of projects available to co-pilots.</p>
     */
    private int availableCopilotProjects;

    /**
     * <p>Constructs new <code>CoPilotStatsDTO</code> instance. This implementation does nothing.</p>
     */
    public CoPilotStatsDTO() {
    }

    /**
     * <p>Gets the number of available co-pilots.</p>
     *
     * @return an <code>int</code> providing the number of available co-pilots.
     */
    public int getAvailableCopilots() {
        return this.availableCopilots;
    }

    /**
     * <p>Sets the number of available co-pilots.</p>
     *
     * @param availableCopilots an <code>int</code> providing the number of available co-pilots.
     */
    public void setAvailableCopilots(int availableCopilots) {
        this.availableCopilots = availableCopilots;
    }

    /**
     * <p>Gets the number of available projects for co-pilots.</p>
     *
     * @return an <code>int</code> providing the number of projects available to co-pilots.
     */
    public int getAvailableCopilotProjects() {
        return this.availableCopilotProjects;
    }

    /**
     * <p>Sets the number of available projects for co-pilots.</p>
     *
     * @param availableCopilotProjects an <code>int</code> providing the number of projects available to co-pilots.
     */
    public void setAvailableCopilotProjects(int availableCopilotProjects) {
        this.availableCopilotProjects = availableCopilotProjects;
    }
}
