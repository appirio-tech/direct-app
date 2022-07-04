/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.dashboard;

import com.topcoder.direct.services.project.metadata.entities.dao.TcDirectProject;
import com.topcoder.direct.services.project.metadata.entities.dto.MetadataKeyIdValueFilter;
import com.topcoder.direct.services.project.metadata.entities.dto.MetadataValueOperator;
import com.topcoder.direct.services.project.milestone.model.Milestone;
import com.topcoder.direct.services.project.milestone.model.MilestoneStatus;
import com.topcoder.direct.services.project.milestone.model.SortOrder;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.dashboard.calendar.DashboardMilestoneCalendarDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.form.DashboardMilestoneCalendarForm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This action handles the request of viewing the enterprise milestone calendar.
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Release Assembly - TC Cockpit Enterprise Calendar Revamp)
 */
public class DashboardMilestoneCalendarAction extends BaseDirectStrutsAction
        implements FormAction<DashboardMilestoneCalendarForm>, ViewAction<DashboardMilestoneCalendarDTO> {

    /**
     * List of all the available milestone status.
     */
    private static final List ALL_MILESTONE_STATUS = Arrays.asList(MilestoneStatus.values());

    /**
     * Represent the project filter value for "none".
     */
    private static final String PROJECT_FILTER_VALUE_NONE = "none";

    /**
     * Represent the project filter value for "all"
     */
    private static final String PROJECT_FILTER_VALUE_ALL = "all";

    /**
     * Represent the customer filter value for "all customers" option
     */
    private static final long ALL_CUSTOMER_ID = -1L;

    /**
     * The form data of the action.
     */
    private DashboardMilestoneCalendarForm formData = new DashboardMilestoneCalendarForm();

    /**
     * The view data of the action.
     */
    private DashboardMilestoneCalendarDTO viewData = new DashboardMilestoneCalendarDTO();


    /**
     * The main logic of the action.
     *
     * @throws Exception if there is any error.
     */
    @Override
    protected void executeAction() throws Exception {

        // check if the dependent services initialized
        if (getMetadataService() == null) {
            throw new IllegalStateException("The direct project metadata service is not initialized.");
        }

        if (getMilestoneService() == null) {
            throw new IllegalStateException("The project milestone service is not initialized.");
        }

        // get the active projects the user has access to, it's already set the ActionPreProcessor
        final UserProjectsDTO userProjects = getViewData().getUserProjects();

        viewData.setProjects(new HashMap<Long, String>());

        // filter by customer id first
        for (ProjectBriefDTO p : userProjects.getProjects()) {
            if (getFormData().getCustomerId() == ALL_CUSTOMER_ID
                    || (getFormData().getCustomerId() != ALL_CUSTOMER_ID && p.getCustomerId() == getFormData().getCustomerId())) {
                // add the project, if all customers is selected
                // or the customer id selected matches (0 means no customer)
                viewData.getProjects().put(p.getId(), p.getName());
            }
        }

        if (getFormData().getCustomerId() != 0 && getFormData().getProjectFilterId() > 0
                && !getFormData().getProjectFilterValue().toLowerCase().equals(PROJECT_FILTER_VALUE_ALL)) {
            // filter by the project filter and project filter values
            if (getFormData().getProjectFilterValue().toLowerCase().equals(PROJECT_FILTER_VALUE_NONE)) {
                // exclude filter value
                MetadataKeyIdValueFilter idValueFilter = new MetadataKeyIdValueFilter();
                idValueFilter.setMetadataValue("");
                idValueFilter.setMetadataValueOperator(MetadataValueOperator.LIKE);
                idValueFilter.setProjectMetadataKeyId(getFormData().getProjectFilterId());
                final List<TcDirectProject> projectsToExclude = getMetadataService().searchProjects(idValueFilter);

                for (TcDirectProject projectToExclude : projectsToExclude) {
                    if (viewData.getProjects().containsKey(projectToExclude.getProjectId())) {
                        viewData.getProjects().remove(projectToExclude.getProjectId());
                    }
                }
            } else {
                // filter with the project filter value
                final Set<Long> includeProjectIds = getMetadataService().searchProjectIds(
                        getFormData().getProjectFilterId(),
                        Arrays.asList(new String[]{getFormData().getProjectFilterValue()}));

                // remove the project not in the set
                List<Long> idsToCheck = new ArrayList<Long>(getViewData().getProjects().keySet());
                for (Long projectIdToCheck : idsToCheck) {
                    if (!includeProjectIds.contains(projectIdToCheck)) {
                        // no in the set, remove
                        getViewData().getProjects().remove(projectIdToCheck);
                    }
                }
            }
        }

        // retrieve milestones for the left projects in getViewData().getProjects()
        final List<Milestone> allForProjects;

        if (viewData.getProjects().keySet().size() > 0) {

            allForProjects = getMilestoneService().getAllForProjects(new ArrayList<Long>(viewData.getProjects().keySet()), ALL_MILESTONE_STATUS, SortOrder.ASCENDING);
        } else {
            allForProjects = new ArrayList<Milestone>();
        }

        // extra all the responsible person user id from the milestone
        getViewData().setResponsiblePersonIds(new HashSet<Long>());

        for(Milestone m : allForProjects) {
            if (m.getOwners() != null && m.getOwners().size() > 0) {
                getViewData().getResponsiblePersonIds().add(m.getOwners().get(0).getUserId());
            }
        }

        // set the milestones
        viewData.setMilestones(allForProjects);

    }

    /**
     * Gets the form data of the action.
     *
     * @return the form data of the action.
     */
    public DashboardMilestoneCalendarForm getFormData() {
        return formData;
    }

    /**
     * Sets the form data of the action.
     *
     * @param formData the form data of the action.
     */
    public void setFormData(DashboardMilestoneCalendarForm formData) {
        this.formData = formData;
    }

    /**
     * Gets the view data of the action.
     *
     * @return the view data of the action.
     */
    public DashboardMilestoneCalendarDTO getViewData() {
        return viewData;
    }

    /**
     * Sets the view data of the action.
     *
     * @param viewData the view data of the action.
     */
    public void setViewData(DashboardMilestoneCalendarDTO viewData) {
        this.viewData = viewData;
    }
}
