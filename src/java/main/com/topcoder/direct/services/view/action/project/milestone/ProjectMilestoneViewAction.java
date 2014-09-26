/*
 * Copyright (C) 2012 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project.milestone;

import com.topcoder.direct.services.project.milestone.model.Milestone;
import com.topcoder.direct.services.project.milestone.model.MilestoneStatus;
import com.topcoder.direct.services.project.milestone.model.ResponsiblePerson;
import com.topcoder.direct.services.project.milestone.model.SortOrder;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.ProjectMilestoneViewDTO;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.dto.project.milestone.MilestoneContestDTO;
import com.topcoder.direct.services.view.dto.project.milestone.ProjectMilestoneDTO;
import com.topcoder.direct.services.view.form.ProjectMilestoneViewForm;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.service.permission.Permission;
import org.codehaus.jackson.map.ObjectMapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * This action class handles all the views for project milestone management: project milestone list view, project milestone
 * calendar view and multiple project milestones batch creation view.
 * </p>
 *
 * <p>
 * Version 1.1 (Module Assembly - TC Cockpit Contest Milestone Association Milestone Page Update)
 * <ul>
 *     <li>Updated {@link #executeAction()} to remove the codes to get milestone list view data, it's changed to get through ajax request</li>
 *     <li>Added method {@link #getProjectMilestoneListData()} to get the project milestone list data</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.2 (TopCoder Direct - Quick Bug Fixes 09.23)
 * <ul>
 *     <li>Updated {@link #getProjectResponsiblePerson()} to only retrieve for positive (valid) project ID</li>
 * </ul>
 * </p>
 *
 * @author GreatKevin, Veve
 * @version 1.2
 */
public class ProjectMilestoneViewAction extends BaseDirectStrutsAction implements FormAction<ProjectMilestoneViewForm> {

    /**
     * The default user handle color to display for the responsible person of the milestone.
     */
    private static final String DEFAULT_USER_HANDLE_CLASS = "coderTextOrange";

    /**
     * The default user handle link for the responsible person of the milestone.
     */
    private static final String DEFAULT_USER_HANDLE_URL = "javascript:;";

    /**
     * List of all the available milestone status.
     */
    private static final List ALL_MILESTONE_STATUS = Arrays.asList(MilestoneStatus.values());

    /**
     * The date string format of milestone due date and completion date.
     */
    private final DateFormat CALENDAR_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * The form data.
     */
    private ProjectMilestoneViewForm formData = new ProjectMilestoneViewForm();

    /**
     * The view data.
     */
    private ProjectMilestoneViewDTO viewData = new ProjectMilestoneViewDTO();

    /**
     * Redirects to different view by checking the view type.
     *
     * @return the result code.
     * @throws Exception if there is error.
     */
    @Override
    public String execute() throws Exception {
        String resultCode = super.execute();
        if (SUCCESS.equals(resultCode)) {
            if (formData.getViewType().equals(ProjectMilestoneViewForm.LIST_VIEW)) {
                return ProjectMilestoneViewForm.LIST_VIEW;
            }
            if (formData.getViewType().equals(ProjectMilestoneViewForm.CALENDAR_VIEW)) {
                return ProjectMilestoneViewForm.CALENDAR_VIEW;
            }
            if (formData.getViewType().equals(ProjectMilestoneViewForm.MULTIPLE_CREATION_VIEW)) {
                return ProjectMilestoneViewForm.MULTIPLE_CREATION_VIEW;
            }
        }

        return resultCode;
    }

    /**
     * Main execution for the action, prepare different set of data according to the view type.
     *
     * @throws Exception if there is any error.
     */
    @Override
    protected void executeAction() throws Exception {

        // set responsible person data
        final List<ResponsiblePerson> allResponsiblePeople = getMilestoneResponsiblePersonService().getAllResponsiblePeople(getFormData().getProjectId());
        getViewData().setResponsiblePersons(allResponsiblePeople);

        // right side bar data
        List<ProjectBriefDTO> projects
                = DataProvider.getUserProjects(getSessionData().getCurrentUserId());
        List<TypedContestBriefDTO> contests = DataProvider.getProjectTypedContests(getSessionData().getCurrentUserId(), formData.getProjectId());
        UserProjectsDTO userProjectsDTO = new UserProjectsDTO();
        userProjectsDTO.setProjects(projects);

        viewData.setUserProjects(userProjectsDTO);

        // put project into the session
        if (contests.size() > 0) {
            getSessionData().setCurrentProjectContext(contests.get(0).getProject());
        } else {
            for (ProjectBriefDTO p : projects) {
                if (p.getId() == getFormData().getProjectId()) {
                    getSessionData().setCurrentProjectContext(p);
                    break;
                }
            }

        }
        getSessionData().setCurrentSelectDirectProjectID(
                getFormData().getProjectId());

        // set project contests
        getSessionData().setCurrentProjectContests(contests);
    }

    /**
     * Gets the responsible person list for the project.
     *
     * @return the result code.
     * @throws Exception if there is any error.
     */
    public String getProjectResponsiblePerson() throws Exception {
        List<Map<String, Object>> result = new LinkedList<Map<String, Object>>();

        try {

            if (getFormData().getProjectId() > 0) {
                List<Permission> permissionsByProject = getPermissionServiceFacade().getPermissionsByProject(
                        DirectUtils.getTCSubjectFromSession(), getFormData().getProjectId());


                for (Permission p : permissionsByProject) {
                    Map<String, Object> data = new HashMap<String, Object>();
                    data.put("userId", p.getUserId());
                    data.put("name", p.getUserHandle());
                    result.add(data);
                }
            }

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
     * Gets the project milestones calendar dat via ajax.
     *
     * @return the result code.
     * @throws Exception if there is any error.
     */
    public String getProjectMilestoneCalendarData() throws Exception {
        List<Map<String, Object>> result = new LinkedList<Map<String, Object>>();

        try {
            final List<Milestone> allMilestones =
                    this.getMilestoneService().getAll(getFormData().getProjectId(), ALL_MILESTONE_STATUS, SortOrder.ASCENDING);

            for (Milestone m : allMilestones) {
                Map<String, Object> data = new HashMap<String, Object>();

                data.put("title", m.getName());
                data.put("status", m.getStatus().toString().toLowerCase());
                if (m.isCompleted()) {
                    data.put("start", CALENDAR_DATE_FORMAT.format(m.getCompletionDate()));
                } else {
                    data.put("start", CALENDAR_DATE_FORMAT.format(m.getDueDate()));
                }

                data.put("description", m.getDescription());

                if (m.getOwners() != null && m.getOwners().size() > 0) {
                    ResponsiblePerson rp = m.getOwners().get(0);

                    if (rp != null) {
                        Map<String, Object> person = new HashMap<String, Object>();
                        person.put("name", rp.getName());
                        person.put("color", DEFAULT_USER_HANDLE_CLASS);
                        person.put("url", DEFAULT_USER_HANDLE_URL);

                        data.put("person", person);
                    }

                }

                result.add(data);
            }
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
     * Gets the project milestone list view data with ajax.
     *
     * @return the result code.
     * @throws Exception if there is any error.
     * @since 1.1
     */
    public String getProjectMilestoneListData() throws Exception {
        try {

            List<Milestone> overdueMilestones = getMilestoneService().getAll(formData.getProjectId(),
                    Arrays.asList(new MilestoneStatus[]{MilestoneStatus.OVERDUE}), SortOrder.ASCENDING);
            List<Milestone> upcomingMilestones = getMilestoneService().getAll(formData.getProjectId(),
                    Arrays.asList(new MilestoneStatus[]{MilestoneStatus.UPCOMING}), SortOrder.ASCENDING);
            List<Milestone> completedMilestones = getMilestoneService().getAll(formData.getProjectId(),
                    Arrays.asList(new MilestoneStatus[]{MilestoneStatus.COMPLETED}), SortOrder.DESCENDING);


            List<MilestoneContestDTO> milestoneContestAssociations = DataProvider.getMilestoneContestAssociations(
                    formData.getProjectId(), 0,
                    DirectUtils.getTCSubjectFromSession().getUserId());

            Map<Long, List<MilestoneContestDTO>> tempMapping = new HashMap<Long, List<MilestoneContestDTO>>();

            for(MilestoneContestDTO mcd : milestoneContestAssociations) {
                if(tempMapping.get(mcd.getMilestoneId()) == null) {
                    tempMapping.put(mcd.getMilestoneId(), new ArrayList<MilestoneContestDTO>());
                }

                tempMapping.get(mcd.getMilestoneId()).add(mcd);
            }

            Map<String, List<ProjectMilestoneDTO>> result = new HashMap<String, List<ProjectMilestoneDTO>>();

            result.put("overdue", new ArrayList<ProjectMilestoneDTO>());
            result.put("upcoming", new ArrayList<ProjectMilestoneDTO>());
            result.put("completed", new ArrayList<ProjectMilestoneDTO>());

            for(Milestone m : overdueMilestones) {
                ProjectMilestoneDTO pmd = new ProjectMilestoneDTO();
                pmd.setMilestone(m);
                pmd.setContests(tempMapping.get(m.getId()));
                result.get("overdue").add(pmd);
            }

            for(Milestone m : upcomingMilestones) {
                ProjectMilestoneDTO pmd = new ProjectMilestoneDTO();
                pmd.setMilestone(m);
                pmd.setContests(tempMapping.get(m.getId()));
                result.get("upcoming").add(pmd);

            }

            for(Milestone m : completedMilestones) {
                ProjectMilestoneDTO pmd = new ProjectMilestoneDTO();
                pmd.setMilestone(m);
                pmd.setContests(tempMapping.get(m.getId()));
                result.get("completed").add(pmd);

            }

            ObjectMapper m = new ObjectMapper();

            setResult(m.convertValue(result, Map.class));


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
     * Gets the form data.
     *
     * @return the form data.
     */
    public ProjectMilestoneViewForm getFormData() {
        return formData;
    }

    /**
     * Sets the form data.
     *
     * @param formData the form data to set.
     */
    public void setFormData(ProjectMilestoneViewForm formData) {
        this.formData = formData;
    }

    /**
     * Gets the view data.
     *
     * @return the view data.
     */
    public ProjectMilestoneViewDTO getViewData() {
        return viewData;
    }

    /**
     * Sets the view data.
     *
     * @param viewData the view data to set.
     */
    public void setViewData(ProjectMilestoneViewDTO viewData) {
        this.viewData = viewData;
    }
}
