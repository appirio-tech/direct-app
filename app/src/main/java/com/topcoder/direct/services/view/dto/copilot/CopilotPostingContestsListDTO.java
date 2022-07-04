/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.copilot;

import com.topcoder.direct.services.view.dto.CommonDTO;
import com.topcoder.direct.services.view.dto.project.ProjectContestDTO;

import java.io.Serializable;
import java.util.List;

/**
 * <p>A DTO providing the data for displaying by <code>Manage Copilot Postings</code> view.</p>
 * 
 * <p>
 * Version 1.1 (TC Direct - Page Layout Update Assembly) Change notes:
 *   <ol>
 *     <li>Updated contests field to a list of ProjectContestDTO and corresponding get/set methods.</li>
 *   </ol>
 * </p>
 * 
 * @author TCSDEVELOPER, tangzx
 * @version 1.1 (Direct Manage Copilot Postings assembly)
 */
public class CopilotPostingContestsListDTO extends CommonDTO implements Serializable {

    /**
     * <p>A <code>List</code> providing the Copilot Posting contests.</p>
     */
    private List<ProjectContestDTO> contests;

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
    public List<ProjectContestDTO> getContests() {
        return this.contests;
    }

    /**
     * <p>Sets the Copilot Posting contests.</p>
     *
     * @param contests a <code>List</code> providing the Copilot Posting contests.
     */
    public void setContests(List<ProjectContestDTO> contests) {
        this.contests = contests;
    }
}
