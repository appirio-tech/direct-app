/*
 * Copyright (C) 2010-2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard;

import com.topcoder.direct.services.view.dto.CommonDTO;

import java.io.Serializable;
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
 * <p>
 * Version 1.0.2 (Cockpit - Enterprise Dashboard 3 Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #contestStat} property with respective accessor/mutator methods.</li>
 *     <li>Added {@link #allContestStat} property with respective accessor/mutator methods.</li>
 *   </ol>
 * </p>
 * <p>
 * Version 1.0.3 (Release Assembly - TC Cockpit Enterprise Dashboard Update Assembly 1) Change notes:
 *   <ol>
 *     <li>Added {@link #customerSummary} property with respective set/get methods.</li>
 *     <li>Added {@link #marketSummary} property with respective set/get methods.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.0.4 (TC Cockpit Performance Improvement Enterprise Dashboard 1 Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Removed <code>projects</code> property.</li>
 *   </ol>
 * </p>
 *
 * @author isv xjtufreeman, Veve, isv
 * @version 1.0.4 (Release Assembly - TC Cockpit Enterprise Dashboard Update Assembly 1)
 */
public class EnterpriseDashboardDTO extends CommonDTO implements Serializable {

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


    private Map<Long, String> projectsLookupMap;

    /**
     * The customer summary data
     * @since 1.0.3
     */
    private EnterpriseDashboardSummary customerSummary;

    /**
     * The market summary data.
     * @since 1.0.3
     */
    private EnterpriseDashboardSummary marketSummary;

    /**
     * <p>A <code>List</code> providing the statistics for contests associated to current user.</p>
     *
     * @since 1.0.2
     */
    private List<EnterpriseDashboardContestStatDTO> contestStat;

    /**
     * <p>A <code>List</code> providing the statistics for all contests associated to current user.</p>
     *
     * @since 1.0.2
     */
    private List<EnterpriseDashboardContestStatDTO> allContestStat;

    /**
     * <p>Constructs new <code>EnterpriseDashboardDTO</code> instance. This implementation does nothing.</p>
     */
    public EnterpriseDashboardDTO() {
    }

    /**
     * Gets the customer summary.
     *
     * @return the customer summary.
     * @since 1.0.3
     */
    public EnterpriseDashboardSummary getCustomerSummary() {
        return customerSummary;
    }

    /**
     * Sets the customer summary.
     *
     * @param customerSummary the customer summary data.
     * @since 1.0.3
     */
    public void setCustomerSummary(EnterpriseDashboardSummary customerSummary) {
        this.customerSummary = customerSummary;
    }

    /**
     * Gets the market summary.
     *
     * @return the market summary.
     * @since 1.0.3
     */
    public EnterpriseDashboardSummary getMarketSummary() {
        return marketSummary;
    }

    /**
     * Sets the market summary.
     *
     * @param marketSummary the market summary data.
     * @since 1.0.3
     */
    public void setMarketSummary(EnterpriseDashboardSummary marketSummary) {
        this.marketSummary = marketSummary;
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
     * <p>Gets the statistics for contests associated to current user.</p>
     *
     * @return a <code>List</code> providing the statistics for contests associated to current user.
     * @since 1.0.2
     */
    public List<EnterpriseDashboardContestStatDTO> getContestStat() {
        return this.contestStat;
    }

    /**
     * <p>Sets the statistics for contests associated to current user.</p>
     *
     * @param contestStat a <code>List</code> providing the statistics for contests associated to current user.
     * @since 1.0.2
     */
    public void setContestStat(List<EnterpriseDashboardContestStatDTO> contestStat) {
        this.contestStat = contestStat;
    }

    /**
     * <p>Gets the statistics for all contests associated to current user.</p>
     *
     * @return a <code>List</code> providing the statistics for all contests associated to current user.
     * @since 1.0.2
     */
    public List<EnterpriseDashboardContestStatDTO> getAllContestStat() {
        return this.allContestStat;
    }

    /**
     * <p>Sets the statistics for all contests associated to current user.</p>
     *
     * @param contestStat a <code>List</code> providing the statistics for all contests associated to current user.
     * @since 1.0.2
     */
    public void setAllContestStat(List<EnterpriseDashboardContestStatDTO> contestStat) {
        this.allContestStat = contestStat;
    }
}
