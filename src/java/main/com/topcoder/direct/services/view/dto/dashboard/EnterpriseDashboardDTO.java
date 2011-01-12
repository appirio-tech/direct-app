/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard;

import com.topcoder.direct.services.view.dto.CommonDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>A DTO class providing the data to be displayed on <code>Enterprise Dashboard</code> page view.</p>
 *
 * <p>
 * Version 1.0.1 (Cockpit - Enterprise Dashboard 2 Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #clientBillingProjects} property with respective accessor/mutator methods.</li>
 *     <li>Added {@link #clientAccounts} property with respective accessor/mutator methods.</li>
 *     <li>Added {@link #averageCost} property with respective accessor/mutator methods.</li>
 *     <li>Added {@link #averageDuration} property with respective accessor/mutator methods.</li>
 *     <li>Added {@link #averageFulfillment} property with respective accessor/mutator methods.</li>
 *   </ol>
 * </p>
 *
 * @author isv
 * @version 1.0.1 (Direct Enterprise Dashboard Assembly 1.0)
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
     * <p>A <code>Map</code> providing the mapping from IDs to names of client billing projects.</p>
     * 
     * @since 1.0.1
     */
    private Map<Long, String> clientBillingProjects;

    /**
     * <p>A <code>Map</code> providing the mapping from IDs to names of available client accounts.</p>
     * 
     * @since 1.0.1
     */
    private Map<Long, String> clientAccounts;

    /**
     * <p>A <code>double</code> providing the average cost for entire stats for customer.</p>
     * 
     * @since 1.0.1
     */
    private double averageCost;

    /**
     * <p>A <code>double</code> providing the average contest duration for entire stats for customer.</p>
     * 
     * @since 1.0.1
     */
    private double averageDuration;

    /**
     * <p>A <code>double</code> providing the average project fulfillment for entire stats for customer.</p>
     * 
     * @since 1.0.1
     */
    private double averageFulfillment;

    private Map<Long, String> projectsLookupMap;

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
        return this.projectsLookupMap;
            }

    public void setProjectsLookupMap(Map<Long, String> value) {
        this.projectsLookupMap = value;
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
    public Map<String, List<EnterpriseDashboardAggregatedStatDTO>> getFulfillmentStats() {
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

    /**
     * <p>Gets the list of client billing projects.</p>
     *
     * @return a a <code>Map</code> providing the mapping from IDs to names of client billing projects.
     * @since 1.0.1
     */
    public Map<Long, String> getClientBillingProjects() {
        return this.clientBillingProjects;
    }

    /**
     * <p>Sets the list of client billing projects.</p>
     *
     * @param clientBillingProjects a a <code>Map</code> providing the mapping from IDs to names of client billing 
     *        projects.
     * @since 1.0.1
     */
    public void setClientBillingProjects(Map<Long, String> clientBillingProjects) {
        this.clientBillingProjects = clientBillingProjects;
    }

    /**
     * <p>Gets the list of available client accounts.</p>
     *
     * @return a a <code>Map</code> providing the mapping from IDs to names of available client accounts.
     * @since 1.0.1
     */
    public Map<Long, String> getClientAccounts() {
        return this.clientAccounts;
    }

    /**
     * <p>Sets the list of available client accounts.</p>
     *
     * @param clientAccounts a <code>Map</code> providing the mapping from IDs to names of available client accounts.
     * @since 1.0.1
     */
    public void setClientAccounts(Map<Long, String> clientAccounts) {
        this.clientAccounts = clientAccounts;
    }

    /**
     * <p>Gets the average project fulfillment for entire stats for customer.</p>
     *
     * @return a <code>double</code> providing the average project fulfillment for entire stats for customer.
     * @since 1.0.1
     */
    public double getAverageFulfillment() {
        return this.averageFulfillment;
    }

    /**
     * <p>Sets the average project fulfillment for entire stats for customer.</p>
     *
     * @param averageFulfillment a <code>double</code> providing the average project fulfillment for entire stats for
     *                          customer.
     * @since 1.0.1
     */
    public void setAverageFulfillment(double averageFulfillment) {
        this.averageFulfillment = averageFulfillment;
    }

    /**
     * <p>Gets the average contest duration for entire stats for customer.</p>
     *
     * @return a <code>double</code> providing the average contest duration for entire stats for customer.
     * @since 1.0.1
     */
    public double getAverageDuration() {
        return this.averageDuration;
    }

    /**
     * <p>Sets the average contest duration for entire stats for customer.</p>
     *
     * @param averageDuration a <code>double</code> providing the average contest duration for entire stats for
     *                        customer.
     * @since 1.0.1
     */
    public void setAverageDuration(double averageDuration) {
        this.averageDuration = averageDuration;
    }

    /**
     * <p>Gets the average cost for entire stats for customer.</p>
     *
     * @return a <code>double</code> providing the average cost for entire stats for customer.
     * @since 1.0.1
     */
    public double getAverageCost() {
        return this.averageCost;
    }

    /**
     * <p>Sets the average cost for entire stats for customer.</p>
     *
     * @param averageCost a <code>double</code> providing the average cost for entire stats for customer.
     * @since 1.0.1
     */
    public void setAverageCost(double averageCost) {
        this.averageCost = averageCost;
    }
}
