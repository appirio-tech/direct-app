/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.project;

import java.util.List;

/**
 * @author isv
 * @version 1.0
 */
public class ProjectContestsListDTO {

    /**
     * <p>An interface to be implemented by the parties interested in getting the details on contests associated with
     * requested project.</p>
     */
    public static interface Aware {

        /**
         * <p>Sets the details on contests for the single project.</p>
         *
         * @param contestsDTO a <code>ProjectContestsListDTO</code> providing details on contests for single project.
         */
        public void setProjectContests(ProjectContestsListDTO contestsDTO);
    }

    /**
     * <p>A <code>List</code> providing the details for the requested contests.</p>
     */
    private List<ProjectContestDTO> contests;

    /**
     * <p>Constructs new <code>ProjectContestsListDTO</code> instance. This implementation does nothing.</p>
     */
    public ProjectContestsListDTO() {
    }

    /**
     * <p>Gets the details for the project contests.</p>
     *
     * @return a <code>List</code> providing the details for the requested contests.
     */
    public List<ProjectContestDTO> getContests() {
        return contests;
    }

    /**
     * <p>Sets the details for the project contests.</p>
     *
     * @param contests a <code>List</code> providing the details for the requested contests.
     */
    public void setContests(List<ProjectContestDTO> contests) {
        this.contests = contests;
    }
}
