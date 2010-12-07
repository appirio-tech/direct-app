/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.copilot;

import com.topcoder.direct.services.view.dto.CommonDTO;
import com.topcoder.direct.services.view.dto.contest.PhasedContestDTO;

import java.io.Serializable;
import java.util.List;

/**
 * <p>A DTO providing the data for displaying by <code>Manage Copilot Postings</code> view.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0 (Direct Manage Copilot Postings assembly)
 */
public class CopilotPostingContestsListDTO extends CommonDTO implements Serializable {

    /**
     * <p>A <code>List</code> providing the Copilot Posting contests.</p>
     */
    private List<PhasedContestDTO> contests;

    /**
     * <p>Constructs new <code>CopilotPostingContestsListDTO</code> instance. This implementation does nothing.</p>
     */
    public CopilotPostingContestsListDTO() {
    }

    /**
     * <p>Gets the Copilot Posting contests.</p>
     *
     * @return a <code>List</code> providing the Copilot Posting contests.
     */
    public List<PhasedContestDTO> getContests() {
        return this.contests;
    }

    /**
     * <p>Sets the Copilot Posting contests.</p>
     *
     * @param contests a <code>List</code> providing the Copilot Posting contests.
     */
    public void setContests(List<PhasedContestDTO> contests) {
        this.contests = contests;
    }
}
