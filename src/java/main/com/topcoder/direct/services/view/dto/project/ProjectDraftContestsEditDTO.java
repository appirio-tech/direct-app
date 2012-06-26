/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.project;

import com.topcoder.direct.services.view.dto.CommonDTO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * The DTO to store the data for the project draft contests edit page.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0
 */
public class ProjectDraftContestsEditDTO extends CommonDTO {
    /**
     * The list of draft <code>ProjectContestDTO</code>.
     */
    private List<ProjectContestDTO> draftContests;

    /**
     * The studio contest types.
     */
    private Map<Long, String> studioContestTypes;

    /**
     * The software contest types.
     */
    private Map<Long, String> softwareContestTypes;

    /**
     * Gets the draft contests.
     *
     * @return the draft contests.
     */
    public List<ProjectContestDTO> getDraftContests() {
        return draftContests;
    }

    /**
     * Sets the draft contests.
     *
     * @param draftContests the draft contests.
     */
    public void setDraftContests(List<ProjectContestDTO> draftContests) {
        this.draftContests = draftContests;
    }

    /**
     * Gets the studio contest types.
     *
     * @return the studio contest types.
     */
    public Map<Long, String> getStudioContestTypes() {
        return studioContestTypes;
    }

    /**
     * Sets the studio contest types.
     *
     * @param studioContestTypes the studio contest types.
     */
    public void setStudioContestTypes(Map<Long, String> studioContestTypes) {
        this.studioContestTypes = studioContestTypes;
    }

    /**
     * Gets the software contest types.
     *
     * @return the software contest types.
     */
    public Map<Long, String> getSoftwareContestTypes() {
        return softwareContestTypes;
    }

    /**
     * Sets the software contest types.
     *
     * @param softwareContestTypes the software contest types.
     */
    public void setSoftwareContestTypes(Map<Long, String> softwareContestTypes) {
        this.softwareContestTypes = softwareContestTypes;
    }
}
