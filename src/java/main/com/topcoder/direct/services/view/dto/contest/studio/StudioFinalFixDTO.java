/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest.studio;

import com.topcoder.direct.services.view.dto.contest.BaseContestCommonDTO;
import com.topcoder.direct.services.view.dto.contest.ContestStatsDTO;
import com.topcoder.direct.services.view.form.ContestIdForm;
import com.topcoder.management.resource.Resource;

import java.util.List;

/**
 * <p>A DTO providing the details on the current state of <code>Final Fix</code> process for a single contest.</p>
 * 
 * @author isv
 * @version 1.0 (Module Assembly - TC Cockpit - Studio - Final Fixes Integration Part One)
 */
public class StudioFinalFixDTO extends BaseContestCommonDTO implements ContestStatsDTO.Aware, ContestIdForm.Aware {

    /**
     * <p>A <code>long</code> providing the ID of a contest.</p>
     */
    private long contestId;

    /**
     * <p>A <code>FinalFixStatus</code> providing the current status of the Final Fix process for contest.</p>
     */
    private FinalFixStatus finalFixStatus;

    /**
     * <p>A <code>List</code> providing the details for all Final Fix submissions for contest.</p>
     */
    private List<StudioFinalFix> finalFixes;

    /**
     * <p>A <code>Resource</code> providing the details for project resource associated with winner.</p>
     */
    private Resource winnerResource;

    /**
     * <p>A <code>boolean</code> providing the flag indicating whether data is unavailable or not.</p>
     */
    private boolean dataUnavailable;

    /**
     * <p>Constructs new <code>StudioFinalFixDTO</code> instance. This implementation does nothing.</p>
     */
    public StudioFinalFixDTO() {
    }

    /**
     * <p>Gets the details for all Final Fix submissions for contest.</p>
     *
     * @return a <code>List</code> providing the details for all Final Fix submissions for contest.
     */
    public List<StudioFinalFix> getFinalFixes() {
        return this.finalFixes;
    }

    /**
     * <p>Sets the details for all Final Fix submissions for contest.</p>
     *
     * @param finalFixes a <code>List</code> providing the details for all Final Fix submissions for contest.
     */
    public void setFinalFixes(List<StudioFinalFix> finalFixes) {
        this.finalFixes = finalFixes;
    }

    /**
     * <p>Gets the current status of the Final Fix process for contest.</p>
     *
     * @return a <code>FinalFixStatus</code> providing the current status of the Final Fix process for contest.
     */
    public FinalFixStatus getFinalFixStatus() {
        return this.finalFixStatus;
    }

    /**
     * <p>Sets the current status of the Final Fix process for contest.</p>
     *
     * @param finalFixStatus a <code>FinalFixStatus</code> providing the current status of the Final Fix process for
     * contest.
     */
    public void setFinalFixStatus(FinalFixStatus finalFixStatus) {
        this.finalFixStatus = finalFixStatus;
    }

    /**
     * <p>Gets the ID of a contest.</p>
     *
     * @return a <code>long</code> providing the ID of a contest.
     */
    public long getContestId() {
        return this.contestId;
    }

    /**
     * <p>Sets the ID of a contest.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest.
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * <p>Gets the details for project resource associated with winner.</p>
     *
     * @return a <code>Resource</code> providing the details for project resource associated with winner.
     */
    public Resource getWinnerResource() {
        return this.winnerResource;
    }

    /**
     * <p>Sets the details for project resource associated with winner.</p>
     *
     * @param winnerResource a <code>Resource</code> providing the details for project resource associated with winner.
     */
    public void setWinnerResource(Resource winnerResource) {
        this.winnerResource = winnerResource;
    }

    /**
     * <p>Gets the flag indicating whether data is unavailable or not.</p>
     *
     * @return a <code>boolean</code> providing the flag indicating whether data is unavailable or not.
     */
    public boolean getDataUnavailable() {
        return this.dataUnavailable;
    }

    /**
     * <p>Sets the flag indicating whether data is unavailable or not.</p>
     *
     * @param dataUnavailable a <code>boolean</code> providing the flag indicating whether data is unavailable or not.
     */
    public void setDataUnavailable(boolean dataUnavailable) {
        this.dataUnavailable = dataUnavailable;
    }
}
