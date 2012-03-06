/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project.milestone;

import com.topcoder.direct.services.project.milestone.model.Milestone;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.form.ProjectMilestoneOperationForm;
import com.topcoder.direct.services.view.util.AuthorizationProvider;
import com.topcoder.direct.services.view.util.DirectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This action class handles all the operation ajax requests for project milestone management.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - TC Cockpit Project Milestones Management Front End)
 */
public class ProjectMilestoneOperationAction extends BaseDirectStrutsAction {

    /**
     * The form data.
     */
    private ProjectMilestoneOperationForm formData = new ProjectMilestoneOperationForm();

    /**
     * Gets the form data.
     *
     * @return the form data.
     */
    public ProjectMilestoneOperationForm getFormData() {
        return formData;
    }

    /**
     * Sets the form data.
     *
     * @param formData the form data.
     */
    public void setFormData(ProjectMilestoneOperationForm formData) {
        this.formData = formData;
    }

    /**
     * Do nothing here
     *
     * @throws Exception if there is error.
     */
    @Override
    protected void executeAction() throws Exception {
        // do nothing
    }

    /**
     * Handles add single project milestone ajax request. It checks whether the user has permission on the project
     * the milestone adds to before adding the milestone.
     *
     * @return result code.
     */
    public String addProjectMilestone() {

        try {

            if (getFormData().getMilestone() == null) {
                throw new IllegalArgumentException("Request does not contain any milestone data");
            }

            long projectId = getFormData().getMilestone().getProjectId();

            if (projectId <= 0) {
                throw new IllegalArgumentException("Milestone to create does not have project id set");
            }

            // check whether the user has access to the project
            if (!AuthorizationProvider.isUserGrantedAccessToProject(DirectUtils.getTCSubjectFromSession(), projectId)) {
                throw new IllegalArgumentException("You don't have permission to create milestone for the project");
            }

            final long newMilestoneId = getMilestoneService().add(getFormData().getMilestone());

            Milestone milestone = getMilestoneService().get(newMilestoneId);

            final Map result = milestone.getMapRepresentation();

            result.put("operation", "add");

            setResult(result);

        } catch (Throwable e) {
            // set the error message into the ajax response
            if (getModel() != null) {
                setResult(e);
            }
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * Handles the request to change the milestone completion status. It checks whether the user has write permission on
     * the milestone to set before updating the milestone.
     *
     * @return result code.
     */
    public String changeMilestoneStatus() {
        try {

            if (getFormData().getMilestoneId() <= 0) {
                throw new IllegalArgumentException("Request does not contain any valid milestone id");
            }


            // check whether the user has access to the milestone
            if (!AuthorizationProvider.isUserGrantedToModifyMilestone(DirectUtils.getTCSubjectFromSession(), getFormData().getMilestoneId())) {
                throw new IllegalArgumentException("You don't have permission to modify this milestone");
            }

            Milestone m = getMilestoneService().get(getFormData().getMilestoneId());

            if (m == null) {
                throw new IllegalArgumentException("The requested milestone does not exist");
            }

            // reverse the current completion status
            m.setCompleted(!m.isCompleted());

            // update
            getMilestoneService().update(m);

            // get the updated one
            m = getMilestoneService().get(m.getId());

            Map result = m.getMapRepresentation();

            result.put("operation", "changeStatus");

            setResult(result);

        } catch (Throwable e) {
            // set the error message into the ajax response
            if (getModel() != null) {
                setResult(e);
            }
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * Handles the request to remove the milestone. It checks whether the user has write permission on
     * the milestone to remove before removing the milestone.
     *
     * @return result code.
     */
    public String removeMilestone() {
        Map<String, String> result = new HashMap<String, String>();

        try {

            if (getFormData().getMilestoneId() <= 0) {
                throw new IllegalArgumentException("Request does not contain any milestone data");
            }

            // check whether the user has access to the milestone
            if (!AuthorizationProvider.isUserGrantedToModifyMilestone(DirectUtils.getTCSubjectFromSession(), getFormData().getMilestoneId())) {
                throw new IllegalArgumentException("You don't have permission to remove this milestone");
            }

            getMilestoneService().delete(getFormData().getMilestoneId());

            result.put("operation", "remove");
            result.put("milestoneId", String.valueOf(getFormData().getMilestoneId()));

            setResult(result);

        } catch (Throwable e) {
            // set the error message into the ajax response
            if (getModel() != null) {
                setResult(e);
            }
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * Handles the request to update the milestone. It checks whether the user has write permission on
     * the milestone to update before updating the milestone.
     *
     * @return result code.
     */
    public String updateMilestone() {
        try {

            if (getFormData().getMilestone() == null || getFormData().getMilestone().getId() <= 0) {
                throw new IllegalArgumentException("Request does not contain any valid milestone id");
            }

            // check whether the user has access to the milestone
            if (!AuthorizationProvider.isUserGrantedToModifyMilestone(DirectUtils.getTCSubjectFromSession(), getFormData().getMilestone().getId())) {
                throw new IllegalArgumentException("You don't have permission to modify this milestone");
            }

            getMilestoneService().update(getFormData().getMilestone());

            Milestone m = getMilestoneService().get(getFormData().getMilestone().getId());

            Map result = m.getMapRepresentation();

            result.put("operation", "update");

            setResult(result);

        } catch (Throwable e) {
            // set the error message into the ajax response
            if (getModel() != null) {
                setResult(e);
            }
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * Handles the request to add multiple milestones in batch. It checks whether the user has write permission on the project
     * the milestones to add to before adding the milestones.
     *
     * @return result code.
     */
    public String addProjectMilestones() {
        Map<String, String> result = new HashMap<String, String>();

        try {

            if (getFormData().getMilestones() == null || getFormData().getMilestones().size() == 0) {
                throw new IllegalArgumentException("Request does not contain any milestone data");
            }

            long projectId = getFormData().getMilestones().get(0).getProjectId();

            if (projectId <= 0) {
                throw new IllegalArgumentException("Milestones to create does not have a project id set");
            }

            // check whether the user has access to the project
            if (!AuthorizationProvider.isUserGrantedAccessToProject(DirectUtils.getTCSubjectFromSession(), projectId)) {
                throw new IllegalArgumentException("You don't have permission to create milestones for the project");
            }

            getMilestoneService().add(getFormData().getMilestones());

            result.put("operation", "addAll");

            setResult(result);

        } catch (Throwable e) {
            // set the error message into the ajax response
            if (getModel() != null) {
                setResult(e);
            }
            return ERROR;
        }
        return SUCCESS;
    }
}
