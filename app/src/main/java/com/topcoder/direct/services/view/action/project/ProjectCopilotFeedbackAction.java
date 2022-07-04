/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;

import com.topcoder.direct.services.copilot.dao.CopilotProjectDAO;
import com.topcoder.direct.services.copilot.model.CopilotProjectFeedback;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.form.ProjectCopilotFeedbackForm;
import com.topcoder.direct.services.view.util.DirectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * The action handles the copilot feedback feature in the project overview page.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - TopCoder Copilot Feedback Integration)
 */
public class ProjectCopilotFeedbackAction extends BaseDirectStrutsAction implements FormAction<ProjectCopilotFeedbackForm> {

    /**
     * The form data.
     */
    private ProjectCopilotFeedbackForm formData = new ProjectCopilotFeedbackForm();

    /**
     * The copilot project DAO.
     */
    private CopilotProjectDAO copilotProjectDAO;

    /**
     * Gets the form data.
     *
     * @return the form data.
     */
    public ProjectCopilotFeedbackForm getFormData() {
        return formData;
    }

    /**
     * Sets the form data.
     *
     * @param formData the form data to set.
     */
    public void setFormData(ProjectCopilotFeedbackForm formData) {
        this.formData = formData;
    }

    /**
     * Gets the copilot project dao.
     *
     * @return the copilot project dao.
     */
    public CopilotProjectDAO getCopilotProjectDAO() {
        return copilotProjectDAO;
    }

    /**
     * Sets the copilot project dao.
     *
     * @param copilotProjectDAO the copilot project dao.
     */
    public void setCopilotProjectDAO(CopilotProjectDAO copilotProjectDAO) {
        this.copilotProjectDAO = copilotProjectDAO;
    }

    /**
     * Empty execution
     *
     * @throws Exception if any error
     */
    @Override
    protected void executeAction() throws Exception {
        // do nothing
    }

    /**
     * Handles the create copilot project feedback ajax request.
     *
     * @return the result code
     */
    public String createCopilotProjectFeedback() {
        try {
            Map<String, String> result = new HashMap<String, String>();

            long currentUserId = DirectUtils.getTCSubjectFromSession().getUserId();

            getFormData().getFeedback().setAuthorId(currentUserId);

            // check if the copilot project feedback already exists
            final CopilotProjectFeedback feedback = getCopilotProjectDAO().getCopilotProjectFeedback(getFormData().getCopilotProjectId(), currentUserId);

            if(feedback != null) {
                throw new IllegalStateException("You have already created feedback for the copilot on this project");
            }

            getFormData().getFeedback().setAuthorId(currentUserId);
            getFormData().getFeedback().setUpdaterId(currentUserId);

            getCopilotProjectDAO().addCopilotProjectFeedback(getFormData().getFeedback(), getFormData().getCopilotProjectId());

            result.put("result", "success");

            setResult(result);
        } catch (Throwable e) {
            e.printStackTrace(System.err);
            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }

    /**
     * Handles the update copilot project feedback ajax request.
     *
     * @return the result code.
     */
    public String updateCopilotProjectFeedback() {
        try {
            Map<String, String> result = new HashMap<String, String>();

            long currentUserId = DirectUtils.getTCSubjectFromSession().getUserId();

            getFormData().getFeedback().setAuthorId(currentUserId);
            getFormData().getFeedback().setUpdaterId(currentUserId);

            // check author user id first
            if(currentUserId != getFormData().getFeedback().getAuthorId()) {
                throw new IllegalArgumentException("The author id of the copilot feedback is not current user");
            }

            // check updater user id
            if(currentUserId != getFormData().getFeedback().getUpdaterId()) {
                throw new IllegalArgumentException("The feedback updater is not current user");
            }

            // check if the feedback exists then
            final CopilotProjectFeedback feedback = getCopilotProjectDAO().getCopilotProjectFeedback(getFormData().getCopilotProjectId(), getFormData().getFeedback().getAuthorId());

            if (feedback == null) {
                throw new IllegalStateException("The feedback to update does not exist");
            } else if(!feedback.getStatus().equalsIgnoreCase("pending")) {
                // user cannot update the feedback approved/rejected
                throw new IllegalArgumentException("You cannot update the approved/rejected copilot feedback");
            }

            getFormData().getFeedback().setAuthorId(feedback.getAuthorId());
            getFormData().getFeedback().setUpdaterId(currentUserId);

            getCopilotProjectDAO().updateCopilotProjectFeedback(getFormData().getFeedback(), getFormData().getCopilotProjectId());

            result.put("result", "success");

            setResult(result);
        } catch (Throwable e) {
            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }


}
