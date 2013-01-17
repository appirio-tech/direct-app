/*
 * Copyright (C) 2012 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.enterpriseDashboard;

import com.topcoder.direct.services.project.milestone.model.Milestone;
import com.topcoder.direct.services.project.milestone.model.MilestoneStatus;
import com.topcoder.direct.services.project.milestone.model.SortOrder;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.dashboard.calendar.DashboardMilestoneCalendarDTO;
import com.topcoder.direct.services.view.form.enterpriseDashboard.EnterpriseDashboardFilterForm;
import com.topcoder.direct.services.view.util.DataProvider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * The action handles the ajax requests for roadmap part of the enterprise dashboard.
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TC Cockpit New Enterprise Dashboard Release 1)
 * <ul>
 *     <li>Add method {@link #getRoadmapCalendar()} to get roadmap calendar data via ajax.</li>
 * </ul>
 * </p>
 *
 * @author GreatKevin
 * @version 1.1
 */
public class DashboardRoadmapAction extends BaseDirectStrutsAction implements FormAction<EnterpriseDashboardFilterForm> {

    /**
     * The date format to parse the date in the request.
     */
    private static final DateFormat SOURCE_DATE_FORMAT = new SimpleDateFormat("MMM''yy");

    /**
     * The date format to format the milestone due date and completion date in response.
     */
    private static final DateFormat MILESTONE_DATE_FORMAT = new SimpleDateFormat("MMMMM dd, yyyy");

    /**
     * The status and order filter used to filter and sort the project milestones.
     */
    private static final Map<MilestoneStatus, SortOrder> filters = new HashMap<MilestoneStatus, SortOrder>();

    /**
     * List of all the available milestone status.
     *
     * @since 1.1
     */
    private static final List ALL_MILESTONE_STATUS = Arrays.asList(MilestoneStatus.values());

    /**
     * Static constructor
     */
    static {
        // create the filters to send to milestone service
        filters.put(MilestoneStatus.OVERDUE, SortOrder.ASCENDING);
        filters.put(MilestoneStatus.UPCOMING, SortOrder.ASCENDING);
        filters.put(MilestoneStatus.COMPLETED, SortOrder.DESCENDING);
    }

    /**
     * The view data of the action.
     *
     * @since 1.1
     */
    private DashboardMilestoneCalendarDTO viewData = new DashboardMilestoneCalendarDTO();

    /**
     * The form data of the action.
     */
    private EnterpriseDashboardFilterForm formData = new EnterpriseDashboardFilterForm();

    /**
     * Gets the form data of the action.
     *
     * @return the form data of the action.
     */
    public EnterpriseDashboardFilterForm getFormData() {
        return this.formData;
    }

    /**
     * Sets the form data of the action.
     *
     * @param formData the form data of the action.
     */
    public void setFormData(EnterpriseDashboardFilterForm formData) {
        this.formData = formData;
    }

    /**
     * Gets view data for the roadmap calendar.
     *
     * @return the view data.
     * @since 1.1
     */
    public DashboardMilestoneCalendarDTO getViewData() {
        return viewData;
    }

    /**
     * Sets view data for the roadmap calendar
     *
     * @param viewData the view data.
     * @since 1.1
     */
    public void setViewData(DashboardMilestoneCalendarDTO viewData) {
        this.viewData = viewData;
    }

    /**
     * Empty, ajax requests are handled via action methods invocation.
     *
     * @throws Exception if there is any error.
     */
    @Override
    protected void executeAction() throws Exception {
        // do nothing
    }

    /**
     * Handles the ajax request to get the data for enterprise dashboard road map.
     *
     * @return the result code.
     */
    public String getProjectsMilestones() {
        try {
            Map<String, List<Map<String, String>>> result = new HashMap<String, List<Map<String, String>>>();

            final Map<Long, String> projects = DataProvider.getEnterpriseDashboardFilteredProjects(getFormData());

            if (projects == null || projects.size() == 0) {
                // if no projects, return empty result
                List<Map<String, String>> emptyList = new ArrayList<Map<String, String>>();
                result.put("overdue", emptyList);
                result.put("upcoming", emptyList);
                result.put("completed", emptyList);
                setResult(result);
                return SUCCESS;
            }

            if (getMilestoneService() == null) {
                throw new IllegalStateException("The project milestone service is not initialized");
            }

            Calendar calendar = Calendar.getInstance();
            // get start date
            calendar.setTime(SOURCE_DATE_FORMAT.parse(getFormData().getStartMonth()));
            Date startDate = calendar.getTime();
            // get end date
            calendar.setTime(SOURCE_DATE_FORMAT.parse(getFormData().getEndMonth()));
            calendar.add(Calendar.MONTH, 1);
            calendar.add(Calendar.SECOND, -1);
            Date endDate = calendar.getTime();

            // get the milestones with milestone service
            final Map<MilestoneStatus, List<Milestone>> allForProjectsGroupedByStatus =
                    getMilestoneService().getAllForProjectsGroupedByStatus(new ArrayList<Long>(projects.keySet()),
                            filters, startDate, endDate);

            // add overdue
            List<Map<String, String>> overdueList = new ArrayList<Map<String, String>>();
            if(allForProjectsGroupedByStatus.get(MilestoneStatus.OVERDUE) != null
                    && allForProjectsGroupedByStatus.get(MilestoneStatus.OVERDUE).size() > 0) {
                List<Milestone> overdueMilestones = allForProjectsGroupedByStatus.get(MilestoneStatus.OVERDUE);

                for(Milestone m : overdueMilestones) {
                    overdueList.add(getMilestoneJsonData(m, projects));
                }
            }
            result.put("overdue", overdueList);

            // add upcoming
            List<Map<String, String>> upcomingList = new ArrayList<Map<String, String>>();
            if(allForProjectsGroupedByStatus.get(MilestoneStatus.UPCOMING) != null
                    && allForProjectsGroupedByStatus.get(MilestoneStatus.UPCOMING).size() > 0) {
                List<Milestone> upcomingMilestones = allForProjectsGroupedByStatus.get(MilestoneStatus.UPCOMING);
                for(Milestone m : upcomingMilestones) {
                    upcomingList.add(getMilestoneJsonData(m, projects));
                }
            }
            result.put("upcoming", upcomingList);

            // add completed
            List<Map<String, String>> completedList = new ArrayList<Map<String, String>>();
            if(allForProjectsGroupedByStatus.get(MilestoneStatus.COMPLETED) != null
                    && allForProjectsGroupedByStatus.get(MilestoneStatus.COMPLETED).size() > 0) {
                List<Milestone> completedMilestones = allForProjectsGroupedByStatus.get(MilestoneStatus.COMPLETED);

                for(Milestone m : completedMilestones) {
                    completedList.add(getMilestoneJsonData(m, projects));
                }
            }
            result.put("completed", completedList);

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
     * Gets the roadmap calendar json data via ajax.
     *
     * @return the result code.
     * @throws Exception if there is any error.
     * @since 1.1
     */
    public String getRoadmapCalendar() throws Exception {
        Map<String, List<Map<String, String>>> result = new HashMap<String, List<Map<String, String>>>();

        final Map<Long, String> projects = DataProvider.getEnterpriseDashboardFilteredProjects(getFormData());

        viewData.setProjects(projects);

        getViewData().setResponsiblePersonIds(new HashSet<Long>());
        final List<Milestone> milestones;

        if (projects == null || projects.size() == 0) {
            milestones = new ArrayList<Milestone>();
        } else {
            milestones = getMilestoneService().getAllForProjects(new ArrayList<Long>(projects.keySet()), ALL_MILESTONE_STATUS, SortOrder.ASCENDING);
        }

        // extra all the responsible person user id from the milestone
        for (Milestone m : milestones) {
            if (m.getOwners() != null && m.getOwners().size() > 0) {
                getViewData().getResponsiblePersonIds().add(m.getOwners().get(0).getUserId());
            }
        }

        // set the milestones
        viewData.setMilestones(milestones);

        return SUCCESS;
    }

    /**
     * Gets the map to represent a single milestone.
     *
     * @param m the milestone
     * @param projects the projects cache
     * @return the map representing a single milestone.
     */
    private static Map<String, String> getMilestoneJsonData(Milestone m, Map<Long, String> projects) {
        Map<String, String> item = new HashMap<String, String>();
        item.put("title", m.getName());
        item.put("projectId", String.valueOf(m.getProjectId()));
        item.put("projectName", projects.get(m.getProjectId()));
        item.put("description", m.getDescription());
        item.put("date", MILESTONE_DATE_FORMAT.format(m.isCompleted() ? m.getCompletionDate() : m.getDueDate()));

        return item;
    }
}
