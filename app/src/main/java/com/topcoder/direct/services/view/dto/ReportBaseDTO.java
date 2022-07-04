/*
 * Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * <p>A <code>DTO</code> class providing the basic report data a report.</p>
 * 
 * <p>Version 1.1: (Module Assembly - TC Cockpit Project Metrics Report  ) change notes:
 * <ol>
 *   <li>Added {@link #projectStatus} and it's getter and setter.</li>
 * </ol>
 * </p>
 * 
 * @author TCSASSEMBER
 * @version 1.1 (TC Cockpit Project Metrics Report )
 */
public class ReportBaseDTO extends CommonDTO implements Serializable {

    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 4924706523653390195L;
    
    /**
     * <p>The map stores the direct project information.</p>
     */
    private Map<Long, String> projectsLookupMap;

    /**
     * <p>A <code>Map</code> providing the mapping from IDs to names of client billing projects.</p>
     */
    private Map<Long, String> clientBillingProjects;
    
    /**
     * <p>A <code>Map</code> providing the mapping from IDs to names of available client accounts.</p>
     */
    private Map<Long, String> clientAccounts;

    /**
     * <p>A <code>Map</code> providing the mapping from project category IDs to names.</p>
     */
    private Map<Long, String> projectCategories;
    
    /**
     * <p>A <code>Map</code> providing the mapping from status id to status names.</p>
     */
    private Map<Long, String> contestStatus;

    /**
     * <p>A <code>Map</code> providing the mapping from status id to project status names.</p>
     */
    private Map<Long, String> projectStatus;

    /**
     * <p>A <code>Map</code> provided the group by id and names for the chosen client if to user</p>
     *
     * @since 1.1
     */
    private Map<Long, String> groupKeys;

    /**
     * <p>
     * A <code>Set</code> provided the group values for the chosen group id to user.
     * </p>
     *
     * @since 1.1
     */
    private Set<String> groupValues;
    
    /**
     * <p>A <code>boolean</code> providing the flag indicating whether the participation metrics report data is
     * to be calculated and displayed.</p>
     */
    private boolean showJustForm;
    
    /**
     * <p>Construct a new <code>ReportBaseDTO</code> instance.</p>
     */
    public ReportBaseDTO() {
        super();
    }
    
    /**
     * <p>Gets the projects lookup map which stores the direct project information.</p>
     *
     * @return the projects lookup map.
     */
    public Map<Long, String> getProjectsLookupMap() {
        return projectsLookupMap;
    }

    /**
     * <p>Sets the project lookup map.</p>
     *
     * @param projectsLookupMap the projects lookup map to set.
     */
    public void setProjectsLookupMap(Map<Long, String> projectsLookupMap) {
        this.projectsLookupMap = projectsLookupMap;
    }

    /**
     * <p>Gets the client billing projects.</p>
     *
     * @return the client billing projects.
     */
    public Map<Long, String> getClientBillingProjects() {
        return clientBillingProjects;
    }

    /**
     * <p>Sets the client billing projects.</p>
     *
     * @param clientBillingProjects the client billing projects to set.
     */
    public void setClientBillingProjects(Map<Long, String> clientBillingProjects) {
        this.clientBillingProjects = clientBillingProjects;
    }

    /**
     * <p>Gets the client accounts.</p>
     *
     * @return the client accounts.
     */
    public Map<Long, String> getClientAccounts() {
        return clientAccounts;
    }

    /**
     * <p>Sets the client accounts.</p>
     *
     * @param clientAccounts the client accounts to set.
     */
    public void setClientAccounts(Map<Long, String> clientAccounts) {
        this.clientAccounts = clientAccounts;
    }

    /**
     * <p>Gets the project categories.</p>
     *
     * @return the project categories.
     */
    public Map<Long, String> getProjectCategories() {
        return projectCategories;
    }

    /**
     * <p>Sets the project categories.</p>
     *
     * @param projectCategories the project categories to set.
     */
    public void setProjectCategories(Map<Long, String> projectCategories) {
        this.projectCategories = projectCategories;
    }
    
    /**
     * <p>Gets the contest status.</p>
     *
     * @return the contest status.
     */
    public Map<Long, String> getContestStatus() {
        return contestStatus;
    }

    /**
     * <p>Sets the contest status.</p>
     *
     * @param contestStatus the contest status to set.
     */
    public void setContestStatus(Map<Long, String> contestStatus) {
        this.contestStatus = contestStatus;
    }
    
    /**
     * <p>Gets the flag to determine whether only show the form.</p>
     *
     * @return the flag to determine whether only show the form.
     */
    public boolean isShowJustForm() {
        return showJustForm;
    }

    /**
     * <p>Sets the flag of whether only show the form.</p>
     *
     * @param showJustForm flag to determine whether only show the form.
     */
    public void setShowJustForm(boolean showJustForm) {
        this.showJustForm = showJustForm;
    }

    /**
     * Gets the group keys.
     *
     * @return the group keys.
     * @since 1.1
     */
    public Map<Long, String> getGroupKeys() {
        return groupKeys;
    }

    /**
     * Sets the group keys.
     *
     * @param groupKeys the group keys.
     * @since 1.1
     */
    public void setGroupKeys(Map<Long, String> groupKeys) {
        this.groupKeys = groupKeys;
    }

    /**
     * Gets the group values.
     *
     * @return the group values.
     * @since 1.1
     */
    public Set<String> getGroupValues() {
        return groupValues;
    }

    /**
     * Sets the group values.
     *
     * @param groupValues the group values.
     * @since 1.1
     */
    public void setGroupValues(Set<String> groupValues) {
        this.groupValues = groupValues;
    }

    /**
     * <p>Gets the project status.</p>
     *
     * @return the project status.
     */
	public Map<Long, String> getProjectStatus() {
		return projectStatus;
	}

    /**
     * <p>Sets the project status.</p>
     *
     * @param projectStatus the project status to set.
     */
	public void setProjectStatus(Map<Long, String> projectStatus) {
		this.projectStatus = projectStatus;
	}
    
}
