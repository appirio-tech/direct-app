/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.admin;

import com.topcoder.direct.services.copilot.model.CopilotProjectFeedback;

/**
 * <p>
 * The DTO represents the copilot feedback to the TopCoder Admin to manage.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0
 */
public class CopilotFeedbackAdminDTO {
    /**
     * The copilot feedback.
     */
    private CopilotProjectFeedback feedback;

    /**
     * The direct project id.
     */
    private long directProjectId;

    /**
     * The copilot user id.
     */
    private long copilotUserId;

    /**
     * The copilot project id.
     */
    private long copilotProjectId;

    /**
     * The direct project name.
     */
    private String directProjectName;

    /**
     * Gets the copilot feedback.
     *
     * @return the copilot feedback.
     */
    public CopilotProjectFeedback getFeedback() {
        return feedback;
    }

    /**
     * Sets the copilot feedback.
     *
     * @param feedback the copilot feedback.
     */
    public void setFeedback(CopilotProjectFeedback feedback) {
        this.feedback = feedback;
    }

    /**
     * Gets the direct project id.
     *
     * @return the direct project id.
     */
    public long getDirectProjectId() {
        return directProjectId;
    }

    /**
     * Sets the direct project id.
     *
     * @param directProjectId the direct project id.
     */
    public void setDirectProjectId(long directProjectId) {
        this.directProjectId = directProjectId;
    }

    /**
     * Gets the copilot user id.
     *
     * @return the copilot user id.
     */
    public long getCopilotUserId() {
        return copilotUserId;
    }

    /**
     * Sets the copilot user id.
     *
     * @param copilotUserId the copilot user id.
     */
    public void setCopilotUserId(long copilotUserId) {
        this.copilotUserId = copilotUserId;
    }

    /**
     * Gets the copilot project id.
     *
     * @return the copilot project id.
     */
    public long getCopilotProjectId() {
        return copilotProjectId;
    }

    /**
     * Sets the copilot project id.
     *
     * @param copilotProjectId the copilot project id.
     */
    public void setCopilotProjectId(long copilotProjectId) {
        this.copilotProjectId = copilotProjectId;
    }

    /**
     * Gets the direct project name.
     *
     * @return the direct project name.
     */
    public String getDirectProjectName() {
        return directProjectName;
    }

    /**
     * Sets the direct project name.
     *
     * @param directProjectName the direct project name.
     */
    public void setDirectProjectName(String directProjectName) {
        this.directProjectName = directProjectName;
    }
}
