/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard;

import com.topcoder.direct.services.view.dto.CommonDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>A DTO class providing the data to be displayed on <code>Enterprise Dashboard</code> page view.</p>
 *
 * @author isv
 * @version 1.0 (Direct Enterprise Dashboard Assembly 1.0)
 */
public class EnterpriseDashboardDTO extends CommonDTO implements Serializable {

    /**
     * <p>A <code>List</code> providing the statistics for projects associated to current user.</p>
     */
    private List<EnterpriseDashboardProjectStatDTO> projects;

    /**
     * <p>A <code>Map</code> providing the mapping from project category IDs to names.</p>
     */
    private Map<Long, String> projectCategories;

    /**
     * <p>A <code>Map</code> providing the statistics for average contest duration over time for requested
     * project.</p>
     */
    private Map<String, List<EnterpriseDashboardAggregatedStatDTO>> durationStats;

    /**
     * <p>A <code>Map</code> providing the statistics for average contest costs over time for specified project.</p>
     */
    private Map<String, List<EnterpriseDashboardAggregatedStatDTO>> costStats;

    /**
     * <p>A <code>Map</code> providing the statistics for average contest fulfillment over time for specified
     * project.</p>
     */
    private Map<String, List<EnterpriseDashboardAggregatedStatDTO>> fulfillmentStats;

    /**
     * <p>Constructs new <code>EnterpriseDashboardDTO</code> instance. This implementation does nothing.</p>
     */
    public EnterpriseDashboardDTO() {
    }

    /**
     * <p>Gets the statistics for projects associated to current user.</p>
     *
     * @return a <code>List</code> providing the statistics for projects associated to current user.
     */
    public List<EnterpriseDashboardProjectStatDTO> getProjects() {
        return this.projects;
    }

    /**
     * <p>Sets the statistics for projects associated to current user.</p>
     *
     * @param projects a <code>List</code> providing the statistics for projects associated to current user.
     */
    public void setProjects(List<EnterpriseDashboardProjectStatDTO> projects) {
        this.projects = projects;
    }

    /**
     * <p>Gets the map for looking up the projects by IDs. This is a read-only property.</p>
     *
     * @return a <code>Map</code> mapping the project IDs to project names.
     */
    public Map<Long, String> getProjectsLookupMap() {
        Map<Long, String> map = new HashMap<Long, String>();
        List<EnterpriseDashboardProjectStatDTO> projectStats = getProjects();
        if (projectStats != null) {
            for (EnterpriseDashboardProjectStatDTO projectStat : projectStats) {
                ProjectBriefDTO project = projectStat.getProject();
                map.put(project.getId(), project.getName());
            }
        }
        return map;
    }

    /**
     * <p>Gets the mapping from project category IDs to names.</p>
     *
     * @return a <code>Map</code> providing the mapping from project category IDs to names.
     */
    public Map<Long, String> getProjectCategories() {
        return this.projectCategories;
    }

    /**
     * <p>Sets the mapping from project category IDs to names.</p>
     *
     * @param projectCategories a <code>Map</code> providing the mapping from project category IDs to names.
     */
    public void setProjectCategories(Map<Long, String> projectCategories) {
        this.projectCategories = projectCategories;
    }

    /**
     * <p>Gets the statistics for average contest fulfillment over time for specified project.</p>
     *
     * @return a <code>Map</code> providing the statistics for average contest fulfillment over time for specified
     *         project.
     */
    public Map getFulfillmentStats() {
        return this.fulfillmentStats;
    }

    /**
     * <p>Sets the statistics for average contest fulfillment over time for specified project.</p>
     *
     * @param fulfillmentStats a <code>Map</code> providing the statistics for average contest fulfillment over time
     *        for specified project.
     */
    public void setFulfillmentStats(Map<String, List<EnterpriseDashboardAggregatedStatDTO>> fulfillmentStats) {
        this.fulfillmentStats = fulfillmentStats;
    }

    /**
     * <p>Gets the statistics for average contest costs over time for specified project.</p>
     *
     * @return a <code>Map</code> providing the statistics for average contest costs over time for specified
     *         project.
     */
    public Map<String, List<EnterpriseDashboardAggregatedStatDTO>> getCostStats() {
        return this.costStats;
    }

    /**
     * <p>Sets the statistics for average contest costs over time for specified project.</p>
     *
     * @param costStats a <code>Map</code> providing the statistics for average contest costs over time for specified
     *                  project.
     */
    public void setCostStats(Map<String, List<EnterpriseDashboardAggregatedStatDTO>> costStats) {
        this.costStats = costStats;
    }

    /**
     * <p>Gets the statistics for average contest duration over time for requested project.</p>
     *
     * @return a <code>Map</code> providing the statistics for average contest duration over time for requested
     *         project.
     */
    public Map<String, List<EnterpriseDashboardAggregatedStatDTO>> getDurationStats() {
        return this.durationStats;
    }

    /**
     * <p>Sets the statistics for average contest duration over time for requested project.</p>
     *
     * @param durationStats a <code>Map</code> providing the statistics for average contest duration over time for
     *        requested project.
     */
    public void setDurationStats(Map<String, List<EnterpriseDashboardAggregatedStatDTO>> durationStats) {
        this.durationStats = durationStats;
    }
}
