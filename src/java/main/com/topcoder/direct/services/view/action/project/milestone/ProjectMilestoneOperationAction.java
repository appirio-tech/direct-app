/*
 * Copyright (C) 2012 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project.milestone;

import com.topcoder.direct.services.project.milestone.model.Milestone;
import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.project.milestone.MilestoneContestDTO;
import com.topcoder.direct.services.view.dto.project.milestone.ProjectMilestoneDTO;
import com.topcoder.direct.services.view.form.ProjectMilestoneOperationForm;
import com.topcoder.direct.services.view.util.AuthorizationProvider;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import org.codehaus.jackson.map.ObjectMapper;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * This action class handles all the operation ajax requests for project milestone management.
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TopCoder Security Groups Release 5 v1.0) Change notes:
 *   <ol>
 *     <li>Updated {@link #addProjectMilestone()} and {@link #addProjectMilestones()} to check the write permission
 *     instead of the read permission.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.2 (Module Assembly - TC Cockpit Contest Milestone Association Milestone Page Update)
 * <ul>
 *     <li>Updated method {@link #changeMilestoneStatus()} to check if the milestone has uncompleted contests before marking it as completed</li>
 *     <li>Updated method {@link #removeMilestone()} to remove all the contest milestone associations first before removing the milestone</li>
 *     <li>Updated method {@link #updateMilestone()} to return the contest associations data along with milestone data</li>
 *     <li>Added method {@link #deleteContestFromMilestone()}</li>
 *     <li>Added method {@link #moveContestToMilestone()}</li>
 * </ul>
 * </p>
 *
 * @author GreatKevin
 * @version 1.2
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
            if (!AuthorizationProvider.isUserGrantedWriteAccessToProject(DirectUtils.getTCSubjectFromSession(), projectId)) {
                throw new IllegalArgumentException("You don't have permission to create milestone for the project");
            }

            Milestone milestone = getFormData().getMilestone();
            milestone.setName(StringEscapeUtils.escapeHtml4(milestone.getName()));
            milestone.setDescription(StringEscapeUtils.escapeHtml4(milestone.getDescription()));

            final long newMilestoneId = getMilestoneService().add(milestone);

            milestone = getMilestoneService().get(newMilestoneId);

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

            if(m.isCompleted()) {
                if(getFormData().getCompletionDate() != null) {
                    m.setCompletionDate(getFormData().getCompletionDate());
                }

                // check if the milestone has all the associated contests completed.
                List<MilestoneContestDTO> contests = DataProvider.getMilestoneContestAssociations(
                        m.getProjectId(), m.getId(),
                        DirectUtils.getTCSubjectFromSession().getUserId());

                if (contests != null) {
                    for (MilestoneContestDTO contest : contests) {
                        if (contest.getContestStatus().toLowerCase().equals("active")
                                || contest.getContestStatus().toLowerCase().equals("draft")) {
                            throw new IllegalArgumentException(
                                    "There are uncompleted contests associated with your milestone," +
                                            " please either move the contest(s) to future milestone or delete/cancel the contests first");
                        }
                    }
                }
            }

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

            getProjectServices().deleteMilestoneProjectRelations(getFormData().getMilestoneId(),
                    String.valueOf(DirectUtils.getTCSubjectFromSession().getUserId()));
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

            Milestone m = getFormData().getMilestone();
            m.setName(StringEscapeUtils.escapeHtml4(m.getName()));
            m.setDescription(StringEscapeUtils.escapeHtml4(m.getDescription()));

            getMilestoneService().update(m);

            ProjectMilestoneDTO milestoneDTO = new ProjectMilestoneDTO();
            m = getMilestoneService().get(getFormData().getMilestone().getId());
            milestoneDTO.setMilestone(m);
            milestoneDTO.setContests(
                    DataProvider.getMilestoneContestAssociations(getFormData().getMilestone().getProjectId(),
                            getFormData().getMilestone().getId(), DirectUtils.getTCSubjectFromSession().getUserId()));

            ObjectMapper mapper = new ObjectMapper();

            setResult(mapper.convertValue(milestoneDTO, Map.class));

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
            if (!AuthorizationProvider.isUserGrantedWriteAccessToProject(DirectUtils.getTCSubjectFromSession(), projectId)) {
                throw new IllegalArgumentException("You don't have permission to create milestones for the project");
            }

            List<Milestone> milestones = getFormData().getMilestones();

            for (Milestone m : milestones) {
                m.setName(StringEscapeUtils.escapeHtml4(m.getName()));
                m.setDescription(StringEscapeUtils.escapeHtml4(m.getDescription()));
            }
            getMilestoneService().add(milestones);

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

    /**
     * Handles the request to delete a contest from associating to a milestone.
     *
     * @return result code.
     * @since 1.2
     */
    public String deleteContestFromMilestone() {
        Map<String, String> result = new HashMap<String, String>();

        try {

            if (getFormData().getMilestoneId() <= 0) {
                throw new IllegalArgumentException("Request does not contain any milestone data");
            }

            if (getFormData().getContestId() <= 0) {
                throw new IllegalArgumentException("Request does not contain any contest data");
            }

            // check whether the user has access to the milestone
            if (!AuthorizationProvider.isUserGrantedToModifyMilestone(DirectUtils.getTCSubjectFromSession(), getFormData().getMilestoneId())) {
                throw new IllegalArgumentException("You don't have permission to modify this milestone");
            }

            getProjectServices().deleteProjectMilestoneRelation(getFormData().getContestId(),
                    String.valueOf(DirectUtils.getTCSubjectFromSession().getUserId()));

            result.put("operation", "removeContest");
            result.put("milestoneId", String.valueOf(getFormData().getMilestoneId()));
            result.put("contestId", String.valueOf(getFormData().getContestId()));

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
     * Handles the request to move a contest to associate with another milestone.
     *
     * @return result code.
     * @since 1.2
     */
    public String moveContestToMilestone() {
        Map<String, String> result = new HashMap<String, String>();

        try {

            if (getFormData().getMilestoneId() <= 0) {
                throw new IllegalArgumentException("Request does not contain any milestone data");
            }

            if (getFormData().getContestId() <= 0) {
                throw new IllegalArgumentException("Request does not contain any contest data");
            }

            // check whether the user has access to the milestone
            if (!AuthorizationProvider.isUserGrantedToModifyMilestone(DirectUtils.getTCSubjectFromSession(), getFormData().getMilestoneId())) {
                throw new IllegalArgumentException("You don't have permission to modify this milestone");
            }

            getProjectServices().updateProjectMilestoneRelation(getFormData().getContestId(),
                    getFormData().getMilestoneId(),
                    String.valueOf(DirectUtils.getTCSubjectFromSession().getUserId()));

            result.put("operation", "moveContest");
            result.put("milestoneId", String.valueOf(getFormData().getMilestoneId()));
            result.put("contestId", String.valueOf(getFormData().getContestId()));

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
