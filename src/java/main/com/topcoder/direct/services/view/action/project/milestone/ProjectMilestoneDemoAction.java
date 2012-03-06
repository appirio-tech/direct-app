/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project.milestone;

import com.topcoder.direct.services.project.milestone.model.Milestone;
import com.topcoder.direct.services.project.milestone.model.MilestoneStatus;
import com.topcoder.direct.services.project.milestone.model.ResponsiblePerson;
import com.topcoder.direct.services.project.milestone.model.SortOrder;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.dto.dashboard.DashboardSearchResultsDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.security.TCSubject;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * This action demonstrates the usage of project milestone service and project responsible person service in Cockpit.
 * </p>
 *
 * <p>
 *  Version 1.1 (Module Assembly - TC Cockpit Project Milestones Management Front End) update:
 *  - Update {@link #updateProjectMilestone()} according to the new milestone service update.
 *  - Fix the error handling of ajax based operation methods.
 * </p>
 *
 * @author GreatKevin
 * @version 1.1
 */
public class ProjectMilestoneDemoAction extends BaseDirectStrutsAction {

    /**
     * All the avaible milestone status.
     */
    private static final List ALL_MILESTONE_STATUS = Arrays.asList(MilestoneStatus.values());

    /**
     * The date format used for display the milestone due date.
     */
    private static final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    /**
     * The view data which stores all the projects of the user.
     */
    private DashboardSearchResultsDTO viewData = new DashboardSearchResultsDTO();

    /**
     * The direct project id.
     */
    private long directProjectId;

    /**
     * The milestone id.
     */
    private long milestoneId;

    /**
     * The single milestone instance.
     */
    private Milestone milestone;

    /**
     * A list of project milestones.
     */
    private List<Milestone> milestones;

    /**
     * A list of milestone ids.
     */
    private List<Long> milestoneIds;

    /**
     * The main execution, get all the user's projects.
     *
     * @throws Exception if error.
     */
    @Override
    protected void executeAction() throws Exception {
        HttpServletRequest request = DirectUtils.getServletRequest();
        SessionData sessionData = new SessionData(request.getSession());
        TCSubject currentUser = getCurrentUser();

        // Set projects data
        List<ProjectBriefDTO> projects = DataProvider.getUserProjects(currentUser.getUserId());
        UserProjectsDTO userProjectsDTO = new UserProjectsDTO();
        userProjectsDTO.setProjects(projects);
        getViewData().setUserProjects(userProjectsDTO);

        // Set current project contests
        ProjectBriefDTO currentProject = sessionData.getCurrentProjectContext();
        if (currentProject != null) {
            List<TypedContestBriefDTO> contests
                    = DataProvider.getProjectTypedContests(currentUser.getUserId(), currentProject.getId());
            sessionData.setCurrentProjectContests(contests);
        }

        viewData.setProjects(DataProvider.searchUserProjects(currentUser, ""));
    }

    /**
     * Method to handle the ajax operation getProjectMilestones. Gets all the milestones of the project and return
     * as json.
     *
     * @return the result code.
     * @throws Exception if error.
     */
    public String getProjectMilestones() throws Exception {
        List<Map<String, Object>> result = new LinkedList<Map<String, Object>>();

        try {
            List<Milestone> all = getMilestoneService().getAll(getDirectProjectId(), ALL_MILESTONE_STATUS, SortOrder.DESCENDING);

            for (Milestone m : all) {

                Map<String, Object> milestoneData = new HashMap<String, Object>();
                milestoneData.put("id", m.getId());
                milestoneData.put("name", m.getName());
                milestoneData.put("description", m.getDescription());
                milestoneData.put("dueDate", dateFormat.format(m.getDueDate()));

                String ownerName = "none";
                Long ownerId = -1L;

                if (m.getOwners() != null && m.getOwners().size() > 0) {
                    ownerName = m.getOwners().get(0).getName();
                    ownerId = m.getOwners().get(0).getUserId();
                }


                milestoneData.put("ownerName", ownerName);
                milestoneData.put("ownerUserId", ownerId);
                milestoneData.put("notification", m.isSendNotifications());
                milestoneData.put("completed", m.isCompleted());
                milestoneData.put("status", m.getStatus().toString());

                result.add(milestoneData);
            }

            setResult(result);

        } catch (Throwable e) {
            // set the error message into the ajax response
            if (getModel() != null) {
                setResult(e.getMessage());
            }

            return ERROR;
        }

        return SUCCESS;
    }

    /**
     * Method to handle the ajax operation getProjectResponsiblePerson.
     * <p/>
     * Gets all the responsible person of the project and return as json.
     *
     * @return the result code.
     * @throws Exception if error.
     */
    public String getProjectResponsiblePerson() throws Exception {
        List<Map<String, Object>> result = new LinkedList<Map<String, Object>>();

        try {
            List<ResponsiblePerson> allResponsiblePeople = getMilestoneResponsiblePersonService().getAllResponsiblePeople(getDirectProjectId());

            for (ResponsiblePerson p : allResponsiblePeople) {

                Map<String, Object> data = new HashMap<String, Object>();
                data.put("userId", p.getUserId());
                data.put("name", p.getName());
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
     * Method to handle the ajax operation removeProjectMilestone.
     * <p/>
     * It removes the project mielstone specified by the milestone id.
     *
     * @return the result code.
     * @throws Exception if error.
     */
    public String removeProjectMilestone() throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();

        try {

            getMilestoneService().delete(getMilestoneId());

            result.put("operation", "remove");
            result.put("milestoneId", getMilestoneId());

            setResult(result);

        } catch (Throwable e) {
            // set the error message into the ajax response
            if (getModel() != null) {
                setResult(e.getMessage());
            }

            return ERROR;
        }

        return SUCCESS;
    }

    /**
     * Method to handle the ajax operation addProjectMilestone.
     * <p/>
     * It adds the new milestone specified by the Milestone object.
     *
     * @return the result code.
     * @throws Exception if error.
     */
    public String addProjectMilestone() throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();

        try {

            long newMilestoneId = getMilestoneService().add(getMilestone());

            result.put("operation", "add");
            result.put("milestoneId", newMilestoneId);

            setResult(result);

        } catch (Throwable e) {
            // set the error message into the ajax response
            if (getModel() != null) {
                setResult(e.getMessage());
            }

            return ERROR;
        }

        return SUCCESS;
    }

    /**
     * Method to handle the ajax operation updateProjectMilestone.
     * <p/>
     * It updates the existing milestone specified by the Milestone object.
     *
     * @return the result code.
     * @throws Exception if error.
     */
    public String updateProjectMilestone() throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            Milestone m = getMilestone();

            getMilestoneService().update(m);

            result.put("operation", "update");
            result.put("milestoneId", getMilestone().getId());

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
     * Method to handle the ajax operation addProjectMilestones.
     * <p/>
     * It adds a list of project milestones in one batch.
     *
     * @return the result code.
     * @throws Exception if error.
     */
    public String addProjectMilestones() throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            getMilestoneService().add(getMilestones());

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
     * Removes multiple project milestones at one time.
     *
     * @return the result code.
     * @throws Exception if error.
     */
    public String removeProjectMilestones() throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            getMilestoneService().delete(getMilestoneIds());

            result.put("operation", "deleteAll");

            setResult(result);

        } catch (Throwable e) {
            // set the error message into the ajax response
            if (getModel() != null) {
                setResult(e.getMessage());
            }

            return ERROR;
        }

        return SUCCESS;
    }

    /**
     * Gets the view data.
     *
     * @return the view data.
     */
    public DashboardSearchResultsDTO getViewData() {
        return viewData;
    }

    /**
     * Sets the view data.
     *
     * @param viewData the view data.
     */
    public void setViewData(DashboardSearchResultsDTO viewData) {
        this.viewData = viewData;
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
     * Gets the project milestone.
     *
     * @return the project milestone.
     */
    public Milestone getMilestone() {
        return milestone;
    }

    /**
     * Sets the project milestone.
     *
     * @param milestone the project milestone
     */
    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    /**
     * Gets the list of project milestones.
     *
     * @return the list of project milestones.
     */
    public List<Milestone> getMilestones() {
        return milestones;
    }

    /**
     * Sets the list of project milestones.
     *
     * @param milestones the list of project milestones.
     */
    public void setMilestones(List<Milestone> milestones) {
        this.milestones = milestones;
    }

    /**
     * Gets the id of the milestone.
     *
     * @return the id of the milestone.
     */
    public long getMilestoneId() {
        return milestoneId;
    }

    /**
     * Sets the id of the milestone.
     *
     * @param milestoneId the id of the milestone.
     */
    public void setMilestoneId(long milestoneId) {
        this.milestoneId = milestoneId;
    }

    /**
     * Gets the list of milestone ids.
     *
     * @return the list of milestone ids.
     */
    public List<Long> getMilestoneIds() {
        return milestoneIds;
    }

    /**
     * Sets the list of milestone ids.
     *
     * @param milestoneIds the list of milestone ids.
     */
    public void setMilestoneIds(List<Long> milestoneIds) {
        this.milestoneIds = milestoneIds;
    }
}
