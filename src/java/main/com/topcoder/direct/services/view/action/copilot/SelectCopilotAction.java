/*
 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.copilot;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>A <code>Struts</code> action to be used for handling the requests for selecting copilot for contest.</p>
 *
 * <p>
 * Version 1.1 (Module Assembly - Cockpit Copilot Posting Skills Update and Submission Revamp)
 * <ul>
 *     <li>
 *         Adds the ajax method {@link #selectCopilotAjax()} to handle ajax request of selecting copilot / runner
 *         for copilot posting. It can also choose no copilot posting winner or no copilot posting runner-up.
 *     </li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.2 (Release Assembly - TopCoder Cockpit Copilot Selection Update and Other Fixes Assembly)
 * <ul>
 *     <li>Adds properties {@link #winnerProfileId}, {@link #winnerSubmissionId}, {@link #secondPlaceSubmissionId}</li>
 * </ul>
 * </p>
 *
 * @author GreatKevin
 * @version 1.2
 */
public class SelectCopilotAction extends BaseDirectStrutsAction {

    /**
     * <p>A <code>long</code> providing the ID of a project.</p>
     */
    private long tcDirectProjectId;

    /**
     * <p>A <code>long</code> providing the ID of a project.</p>
     */
    private long projectId;

    /**
     * The winner profile id.
     *
     * @since 1.2
     */
    private long winnerProfileId;

    /**
     * The winner submission id.
     *
     * @since 1.2
     */
    private long winnerSubmissionId;

    /**
     * The second place submission id.
     *
     * @since 1.2
     */
    private long secondPlaceSubmissionId;


    /**
     * <p>Constructs new <code>SelectCopilotAction</code> instance. This implementation does nothing.</p>
     */
    public SelectCopilotAction() {
    }

    /**
     * <p>
     * Handles the incoming request. Update copilot projects.
     * </p>
     *
     * @throws Exception
     *             if an unexpected error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        getContestServiceFacade().selectCopilot(getCurrentUser(), getTcDirectProjectId(), getProjectId(), getWinnerProfileId(),
                                                getWinnerSubmissionId(), getSecondPlaceSubmissionId());
    }

    /**
     * <p>
     * Handles the select copilot / runner-up operation in ajax.
     * </p>
     *
     * @return the result code.
     * @since 1.1
     */
    public String selectCopilotAjax() {
        try {

            Map<String, String> result = new HashMap<String, String>();

            getContestServiceFacade().selectCopilot(getCurrentUser(), getTcDirectProjectId(), getProjectId(), getWinnerProfileId(),
                                                    getWinnerSubmissionId(), getSecondPlaceSubmissionId());

            result.put("resultCode", "success");

            setResult(result);

        } catch (Throwable e) {
            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }

    /**
     * Gets the copilot profile id of the winner.
     *
     * @return the copilot profile id of the winner.
     * @since 1.2
     */
    public long getWinnerProfileId() {
        return winnerProfileId;
    }

    /**
     * Sets copilot profile id of the winner.
     *
     * @param winnerProfileId the copilot profile id of the winner.
     * @since 1.2
     */
    public void setWinnerProfileId(long winnerProfileId) {
        this.winnerProfileId = winnerProfileId;
    }

    /**
     * Gets the winner submission id.
     *
     * @return the winner submission id.
     * @since 1.2
     */
    public long getWinnerSubmissionId() {
        return winnerSubmissionId;
    }

    /**
     * Sets the winner submission id.
     *
     * @param winnerSubmissionId the winner submission id.
     * @since 1.2
     */
    public void setWinnerSubmissionId(long winnerSubmissionId) {
        this.winnerSubmissionId = winnerSubmissionId;
    }

    /**
     * Gets the second place submission id.
     *
     * @return the second place submission id.
     * @since 1.2
     */
    public long getSecondPlaceSubmissionId() {
        return secondPlaceSubmissionId;
    }

    /**
     * Sets the second place submission id.
     *
     * @param secondPlaceSubmissionId the second place submission id.
     * @since 1.2
     */
    public void setSecondPlaceSubmissionId(long secondPlaceSubmissionId) {
        this.secondPlaceSubmissionId = secondPlaceSubmissionId;
    }

    /**
     * <p>Gets the ID of a project.</p>
     *
     * @return a <code>long</code> providing the ID of a project.
     */
    public long getTcDirectProjectId() {
        return this.tcDirectProjectId;
    }

    /**
     * <p>Sets the ID of a project.</p>
     *
     * @param tcDirectProjectId a <code>long</code> providing the ID of a project.
     */
    public void setTcDirectProjectId(long tcDirectProjectId) {
        this.tcDirectProjectId = tcDirectProjectId;
    }

    /**
     * <p>Gets the ID of a project.</p>
     *
     * @return a <code>long</code> providing the ID of a project.
     */
    public long getProjectId() {
        return this.projectId;
    }

    /**
     * <p>Sets the ID of a project.</p>
     *
     * @param projectId a <code>long</code> providing the ID of a project.
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }
}
