/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.admin;

import com.topcoder.direct.services.copilot.dao.CopilotProjectDAO;
import com.topcoder.direct.services.copilot.model.CopilotProjectFeedback;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.admin.CopilotFeedbackAdminDTO;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.service.permission.PermissionServiceException;
import com.topcoder.web.common.PermissionException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * This action handles all the requests from copilot feedback management admin page.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0
 */
public class ManageCopilotFeedbackAction extends BaseDirectStrutsAction {

    /**
     * The list of copilot feedback.
     */
    private List<CopilotFeedbackAdminDTO> copilotsFeedback;

    /**
     * The copilot project DAO.
     */
    private CopilotProjectDAO copilotProjectDAO;

    /**
     * The copilot project id.
     */
    private long copilotProjectId;

    /**
     * The feedback author id.
     */
    private long feedbackAuthorId;

    /**
     * The feedback status.
     */
    private String status;

    /**
     * The feedback to update.
     */
    private CopilotProjectFeedback feedback;


    /**
     * Gets the copilots feedback.
     *
     * @return the copilots feedback.
     */
    public List<CopilotFeedbackAdminDTO> getCopilotsFeedback() {
        return copilotsFeedback;
    }

    /**
     * Sets the copilots feedback.
     *
     * @param copilotsFeedback the copilots feedback.
     */
    public void setCopilotsFeedback(List<CopilotFeedbackAdminDTO> copilotsFeedback) {
        this.copilotsFeedback = copilotsFeedback;
    }

    /**
     * Gets the copilot project DAO.
     *
     * @return the copilot project DAO.
     */
    public CopilotProjectDAO getCopilotProjectDAO() {
        return copilotProjectDAO;
    }

    /**
     * Sets the copilot project DAO.
     *
     * @param copilotProjectDAO the copilot project DAO.
     */
    public void setCopilotProjectDAO(CopilotProjectDAO copilotProjectDAO) {
        this.copilotProjectDAO = copilotProjectDAO;
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
     * Gets the feedback author id.
     *
     * @return the feedback author id.
     */
    public long getFeedbackAuthorId() {
        return feedbackAuthorId;
    }

    /**
     * Sets the feedback author id.
     *
     * @param feedbackAuthorId the feedback author id.
     */
    public void setFeedbackAuthorId(long feedbackAuthorId) {
        this.feedbackAuthorId = feedbackAuthorId;
    }

    /**
     * Gets the feedback status.
     *
     * @return the feedback status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the feedback status.
     *
     * @param status the feedback status.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the feedback to update.
     *
     * @return the feedback to update.
     */
    public CopilotProjectFeedback getFeedback() {
        return feedback;
    }

    /**
     * Sets the feedback.
     *
     * @param feedback the feedback to update.
     */
    public void setFeedback(CopilotProjectFeedback feedback) {
        this.feedback = feedback;
    }

    /**
     * Gets all the copilot feedback.
     *
     * @throws Exception if there is any error.
     */
    @Override
    protected void executeAction() throws Exception {

        checkPermission();

        copilotsFeedback = DataProvider.getAllCopilotFeedback();
    }

    private void checkPermission() throws PermissionServiceException {
        if(!DirectUtils.isTcStaff(DirectUtils.getTCSubjectFromSession())) {
            throw new PermissionServiceException("You don't have permission to manage copilot feedback.");
        }
    }

    /**
     * The method handles the ajax request for the admin to change the feedback status.
     *
     * @return the result code.
     */
    public String changeFeedbackStatus() {
        try {

            checkPermission();

            Map<String, String> result = new HashMap<String, String>();

            long currentUserId = DirectUtils.getTCSubjectFromSession().getUserId();

            final CopilotProjectFeedback feedback = getCopilotProjectDAO().getCopilotProjectFeedback(getCopilotProjectId(), getFeedbackAuthorId());

            if(feedback == null) {
                throw new IllegalArgumentException("The feedback to update the status does not exist");
            }

            CopilotProjectFeedback feedbackToUpdate = new CopilotProjectFeedback();
            feedbackToUpdate.setStatus(getStatus());
            feedbackToUpdate.setUpdaterId(currentUserId);
            feedbackToUpdate.setAnswer(feedback.isAnswer());
            feedbackToUpdate.setText(feedback.getText());
            feedbackToUpdate.setAuthorId(feedback.getAuthorId());
            feedbackToUpdate.setSubmitDate(feedback.getSubmitDate());

            getCopilotProjectDAO().updateCopilotProjectFeedback(feedbackToUpdate, getCopilotProjectId());

            result.put("result", "success");

            setResult(result);
        } catch (Throwable e) {
            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }

    /**
     * Updates the feedback in administrator role.
     *
     * @return the result code
     */
    public String updateFeedbackAdmin() {
        try {

            checkPermission();

            Map<String, String> result = new HashMap<String, String>();

            long currentUserId = DirectUtils.getTCSubjectFromSession().getUserId();

            final CopilotProjectFeedback feedback = getCopilotProjectDAO().getCopilotProjectFeedback(getCopilotProjectId(), getFeedbackAuthorId());

            if(feedback == null) {
                throw new IllegalArgumentException("The feedback to update does not exist");
            }

            CopilotProjectFeedback feedbackToUpdate = new CopilotProjectFeedback();
            feedbackToUpdate.setStatus(feedback.getStatus());
            feedbackToUpdate.setUpdaterId(currentUserId);
            feedbackToUpdate.setAuthorId(feedback.getAuthorId());
            feedbackToUpdate.setSubmitDate(feedback.getSubmitDate());

            // update
            feedbackToUpdate.setAnswer(getFeedback().isAnswer());
            feedbackToUpdate.setText(getFeedback().getText());

            getCopilotProjectDAO().updateCopilotProjectFeedback(feedbackToUpdate, getCopilotProjectId());

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
