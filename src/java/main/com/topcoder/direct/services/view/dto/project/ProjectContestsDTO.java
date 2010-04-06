/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.project;

import com.topcoder.direct.services.view.dto.CommonDTO;

import java.io.Serializable;

/**
 * <p>A DTO class providing the data for <code>Project Contests</code> page view for single project.</p>
 *
 * @author isv
 * @version 1.0
 */
public class ProjectContestsDTO extends CommonDTO implements Serializable, ProjectStatsDTO.Aware,
                                                             ProjectContestsListDTO.Aware {

    /**
     * <p>An interface to be implemented by the parties interested in getting the details on contests associated with
     * requested project.</p>
     */
    public static interface Aware {

        /**
         * <p>Sets the details on user projects.</p>
         *
         * @param contestsDTO a <code>ProjectContestsDTO</code> providing details on user projects.
         */
        public void setProjectContests(ProjectContestsDTO contestsDTO);
    }

    /**
     * <p>A <code>ProjectStatsDTO</code> providing statistics on project.</p>
     */
    private ProjectStatsDTO projectStats;

    /**
     * <p>A <code>ProjectContestsListDTO</code> providing the details for the contests for requested project.</p>
     */
    private ProjectContestsListDTO projectContests;

    /**
     * <p>Constructs new <code>ProjectContestsDTO</code> instance. This implementation does nothing.</p>
     */
    public ProjectContestsDTO() {
    }

    /**
     * <p>Gets the statistics for requested project.</p>
     *
     * @return a <code>ProjectStatsDTO</code> providing statistics on project.
     */
    public ProjectStatsDTO getProjectStats() {
        return this.projectStats;
    }

    /**
     * <p>Sets the statistics for single project.</p>
     *
     * @param projectStats a <code>ProjectStatsDTO</code> providing statistics on project.
     */
    public void setProjectStats(ProjectStatsDTO projectStats) {
        this.projectStats = projectStats;
    }

    /**
     * <p>Gets the details for contests for the project.</p>
     *
     * @return a <code>ProjectContestsListDTO</code> providing the details for the contests for requested project.
     */
    public ProjectContestsListDTO getProjectContests() {
        return this.projectContests;
    }

    /**
     * <p>Sets the details for contests for the project.</p>
     *
     * @param projectContests a <code>ProjectContestsListDTO</code> providing the details for the contests for requested
     *        project.
     */
    public void setProjectContests(ProjectContestsListDTO projectContests) {
        this.projectContests = projectContests;
    }
}
