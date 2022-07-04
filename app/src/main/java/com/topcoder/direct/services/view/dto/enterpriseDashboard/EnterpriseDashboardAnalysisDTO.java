/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.enterpriseDashboard;

import com.topcoder.direct.services.view.form.enterpriseDashboard.AnalysisGroupType;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>
 * The DTO used by new Enterprise Dashboard analysis tab.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - TC Cockpit Enterprise Dashboard Analysis 1)
 */
public class EnterpriseDashboardAnalysisDTO implements Serializable {

    /**
     * The client options.
     */
    private Map<Long, String> clients;

    /**
     * The billing account options.
     */
    private Map<Long, String> billingAccounts;

    /**
     * The projects options.
     */
    private Map<Long, String> projects;

    /**
     * The contest types options.
     */
    private Map<Long, String> contestTypes;

    /**
     * The group type options.
     */
    private AnalysisGroupType[] groupTypes;

    /**
     * Gets the clients.
     *
     * @return the clients.
     */
    public Map<Long, String> getClients() {
        return clients;
    }

    /**
     * Sets the clients.
     *
     * @param clients the clients.
     */
    public void setClients(Map<Long, String> clients) {
        this.clients = clients;
    }

    /**
     * Gets the billing accounts.
     *
     * @return the billing accounts.
     */
    public Map<Long, String> getBillingAccounts() {
        return billingAccounts;
    }

    /**
     * Sets the billing accounts.
     *
     * @param billingAccounts the billing accounts.
     */
    public void setBillingAccounts(Map<Long, String> billingAccounts) {
        this.billingAccounts = billingAccounts;
    }

    /**
     * Gets the projects.
     *
     * @return the projects.
     */
    public Map<Long, String> getProjects() {
        return projects;
    }

    /**
     * Sets the projects.
     *
     * @param projects the projects.
     */
    public void setProjects(Map<Long, String> projects) {
        this.projects = projects;
    }

    /**
     * Gets the contest types.
     *
     * @return the contest types.
     */
    public Map<Long, String> getContestTypes() {
        return contestTypes;
    }

    /**
     * Sets the contest types.
     *
     * @param contestTypes the contest types.
     */
    public void setContestTypes(Map<Long, String> contestTypes) {
        this.contestTypes = contestTypes;
    }

    /**
     * Gets the group types.
     *
     * @return the group types.
     */
    public AnalysisGroupType[] getGroupTypes() {
        return groupTypes;
    }

    /**
     * Sets the group types.
     *
     * @param groupTypes the group types.
     */
    public void setGroupTypes(AnalysisGroupType[] groupTypes) {
        this.groupTypes = groupTypes;
    }
}
