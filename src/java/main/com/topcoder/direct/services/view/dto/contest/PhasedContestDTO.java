/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import java.io.Serializable;
import java.util.List;

/**
 * <p>A DTO providing the details for a single contest including the specification of contest type and status and
 * current phase.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0 (Direct Manage Copilot Postings assembly)
 */
public class PhasedContestDTO extends TypedContestBriefDTO implements Serializable {

    /**
     * <p>A <code>List</code> providing the details on current phases for this contest.</p>
     */
    private List<ProjectPhaseDTO> currentPhases;

    /**
     * <p>Constructs new <code>PhasedContestDTO</code> instance. This implementation does nothing.</p>
     */
    public PhasedContestDTO() {
    }

    /**
     * <p>Gets the details on current phases for this contest.</p>
     *
     * @return a <code>List</code> providing the details on current phases for this contest.
     */
    public List<ProjectPhaseDTO> getCurrentPhases() {
        return this.currentPhases;
    }

    /**
     * <p>Sets the details on current phases for this contest.</p>
     *
     * @param currentPhases a <code>List</code> providing the details on current phases for this contest.
     */
    public void setCurrentPhases(List<ProjectPhaseDTO> currentPhases) {
        this.currentPhases = currentPhases;
    }

}
