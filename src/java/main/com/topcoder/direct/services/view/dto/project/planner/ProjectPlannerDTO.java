/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.project.planner;

import com.topcoder.clients.model.ContestType;
import com.topcoder.clients.model.Project;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * The DTO for the project planner page.
 * </p>
 *
 * @author GreatKevin
 * @version 1.0 (Module Assembly - TopCoder Cockpit Project Planner)
 */
public class ProjectPlannerDTO implements Serializable {

    /**
     * The billing accounts of the project.
     */
    private List<Project> billingAccounts;

    /**
     * Whether current user is registrant of the copilot posting of the project.
     */
    private boolean isCopilotPostingRegistrants;

    /**
     * All the contest types.
     */
    private Map<String, ContestType> contestTypes;

    /**
     * Gets the billing accounts.
     *
     * @return the billing accounts.
     */
    public List<Project> getBillingAccounts() {
        return billingAccounts;
    }

    /**
     * Sets the billing accounts.
     *
     * @param billingAccounts the billing accounts.
     */
    public void setBillingAccounts(List<Project> billingAccounts) {
        this.billingAccounts = billingAccounts;
    }

    /**
     * Check if the current user is registrant of the copilot posting of the project.
     *
     * @return true if yes, otherwise false.
     */
    public boolean isCopilotPostingRegistrants() {
        return isCopilotPostingRegistrants;
    }

    /**
     * Sets if the current user is registrant of the copilot posting of the project.
     *
     * @param copilotPostingRegistrants true if yes, otherwise false
     */
    public void setCopilotPostingRegistrants(boolean copilotPostingRegistrants) {
        isCopilotPostingRegistrants = copilotPostingRegistrants;
    }

    /**
     * Gets the contest types.
     *
     * @return the contest types.
     */
    public Map<String, ContestType> getContestTypes() {
        return contestTypes;
    }

    /**
     * Sets the contest types.
     *
     * @param contestTypes the contest types.
     */
    public void setContestTypes(Map<String, ContestType> contestTypes) {
        this.contestTypes = contestTypes;
    }
}
