/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.form;

import com.topcoder.direct.services.copilot.model.CopilotProjectFeedback;

/**
 * <p>
 * The form for the copilot project feedback.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - TopCoder Copilot Feedback Integration)
 */
public class ProjectCopilotFeedbackForm extends ProjectIdForm {

    /**
     * The copilot feedback.
     */
    private CopilotProjectFeedback feedback;

    /**
     * The copilot project id.
     */
    private long copilotProjectId;

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
}
