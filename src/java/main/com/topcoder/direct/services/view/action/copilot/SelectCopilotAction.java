/*
 * Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
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
 * @author GreatKevin
 * @version 1.1
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
     * <p>A <code>long</code> providing the ID for copilot profile.</p>
     */
    private long profileId;

    /**
     * <p>A <code>int</code> providing the placement for copilot.</p>
     */
    private int placement;

    /**
     * <p>A <code>long</code> providing the submission ID.</p>
     */
    private long submissionId;

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
        getContestServiceFacade().selectCopilot(getCurrentUser(), getTcDirectProjectId(), getProfileId(), 
                                                getSubmissionId(), getPlacement(), getProjectId());
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

            getContestServiceFacade().selectCopilot(getCurrentUser(), getTcDirectProjectId(), getProfileId(),
                    getSubmissionId(), getPlacement(), getProjectId());

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
     * <p>Gets the placement for copilot.</p>
     *
     * @return a <code>int</code> providing the placement for copilot.
     */
    public int getPlacement() {
        return this.placement;
    }

    /**
     * <p>Sets the placement for copilot.</p>
     *
     * @param placement a <code>int</code> providing the placement for copilot.
     */
    public void setPlacement(int placement) {
        this.placement = placement;
    }

    /**
     * <p>Gets the ID for copilot profile.</p>
     *
     * @return a <code>long</code> providing the ID for copilot profile.
     */
    public long getProfileId() {
        return this.profileId;
    }

    /**
     * <p>Sets the ID for copilot profile.</p>
     *
     * @param profileId a <code>long</code> providing the ID for copilot profile.
     */
    public void setProfileId(long profileId) {
        this.profileId = profileId;
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
     * <p>Gets the submission ID.</p>
     *
     * @return a <code>long</code> providing the submission ID.
     */
    public long getSubmissionId() {
        return this.submissionId;
    }

    /**
     * <p>Sets the submission ID.</p>
     *
     * @param submissionId a <code>long</code> providing the submission ID.
     */
    public void setSubmissionId(long submissionId) {
        this.submissionId = submissionId;
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
