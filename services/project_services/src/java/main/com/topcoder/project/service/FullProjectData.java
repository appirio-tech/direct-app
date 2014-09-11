/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.management.project.Project;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.team.TeamHeader;

/**
 * <p>
 * This class is a DTO assembled to transfer all the data regarding a single project. It contains
 * project header and phase information, as well as all the resources participating in the project,
 * all teams currently existing in it, and all technologies involved in completing it.
 * </p>
 * <p>
 * This entity follows java bean conventions for defining setters and getters for these properties.
 * Two constructors are also provided for convenient usage: One to match the super class
 * <code>Project</code>, and another to set all fields in this class plus the two required fields
 * for the super class.
 * </p>
 * 
 * <p>
 * Module Contest Service Software Contest Sales Assembly change: contest sale DTO is added.
 * </p>
 *
 * <p>
 * Thread Safety: This class is mutable and not thread safe.
 * </p>
 * @author argolite, moonli
 * @version 1.0
 */
@SuppressWarnings("serial")
public class FullProjectData extends com.topcoder.project.phases.Project implements Serializable {

    /**
     * <p>
     * Represents the resources available in this project. This includes ones not assigned to any
     * team.
     * </p>
     * <p>
     * This value can be set in the constructor or in the setter, and accessed with the getter.
     * </p>
     * <p>
     * This value will never be null, and may have zero to many entries. It will never have null
     * elements.
     * </p>
     */
    private Resource[] resources;

    /**
     * <p>
     * Represents the project header. It contains the basic information about the project.
     * </p>
     * <p>
     * This value can be set in the constructor or in the setter, and accessed with the getter.
     * </p>
     * <p>
     * Once set, this will not be null.
     * </p>
     */
    private Project projectHeader;

    /**
     * <p>
     * Represents the teams that are registered for this project.
     * </p>
     * <p>
     * This value can be set in the constructor or in the setter, and accessed with the getter.
     * </p>
     * <p>
     * This value will never be null, and may have zero to many entries. It will never have null
     * elements.
     * </p>
     */
    private TeamHeader[] teams;

    /**
     * <p>
     * Represents the technologies applicable in this project.
     * </p>
     * <p>
     * This value can be set in the constructor or in the setter, and accessed with the getter.
     * </p>
     * <p>
     * This value will never be null, and may have zero to many entries, although it is expected
     * that at least one technology will be involved. It will never have null/empty elements.
     * </p>
     */
    private String[] technologies;

    /**
     * <p>
     * Represents the contestSales applicable in this project.
     * </p>
     *
     * <p>
     * This value will can be null. It is set in the setter, and accessed with the getter.
     * </p>
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private List<ContestSaleData> contestSales;

    /**
     * <p>
     * Constructs an instance of this class with given startDate and workdays.
     * </p>
     * @param startDate
     *            the start date of the project
     * @param workdays
     *            the Workdays instance to calculate the end date for phases
     * @throws IllegalArgumentException
     *             If startDate or workdays is null
     */
    public FullProjectData(Date startDate, DefaultWorkdays workdays) {
        super(startDate, workdays);
        this.resources = new Resource[0];
        this.projectHeader = null;
        this.teams = new TeamHeader[0];
        this.technologies = new String[0];
    }


	/**
     * <p>
     * Constructs an instance of this class with given startDate and workdays.
     * </p>
     */
    public FullProjectData() {

    }

    /**
     * <p>
     * This is the full constructor of this class.
     * </p>
     * <p>
     * This convenience constructor allows for setting all values in one go.
     * </p>
     * @param resources
     *            the resources available in this project
     * @param projectHeader
     *            the project header. It contains the basic information about the project
     * @param teams
     *            the teams that are registered for this project
     * @param technologies
     *            the technologies applicable in this project
     * @param startDate
     *            the start date of the project
     * @param workdays
     *            the Workdays instance to calculate the end date for phases
     * @throws IllegalArgumentException
     *             If resources or teams contains null elements, or technologies contains null/empty
     *             elements, or projectHeader is null, or startDate or workdays is null
     */
    public FullProjectData(Resource[] resources, Project projectHeader, TeamHeader[] teams,
        String[] technologies, Date startDate, DefaultWorkdays workdays) {
        super(startDate, workdays);

        setResources(resources);
        setProjectHeader(projectHeader);
        setTeams(teams);
        setTechnologies(technologies);
    }

    /**
     * <p>
     * Gets the resources field value.
     * </p>
     * @return The resources field value
     */
    public Resource[] getResources() {
        return this.resources;
    }

    /**
     * <p>
     * Sets the resources field value.
     * </p>
     * @param resources
     *            The resources field value
     * @throws IllegalArgumentException
     *             If resources contains null elements
     */
    public void setResources(Resource[] resources) {
        if (resources == null) {
            this.resources = new Resource[0];
        } else {
            Util.checkArrrayHasNull(resources, "resources");
            this.resources = resources;
        }
    }

    /**
     * <p>
     * Gets the <code>projectHeader</code> field value.
     * </p>
     * @return The projectHeader field value
     */
    public Project getProjectHeader() {
        return this.projectHeader;
    }

    /**
     * <p>
     * Sets the <code>projectHeader</code> field value.
     * </p>
     * @param projectHeader
     *            The projectHeader field value
     * @throws IllegalArgumentException
     *             If projectHeader is null
     */
    public void setProjectHeader(Project projectHeader) {
        Util.checkObjNotNull(projectHeader, "projectHeader", null);
        this.projectHeader = projectHeader;
    }

    /**
     * <p>
     * Gets the teams field value.
     * </p>
     * @return The teams field value
     */
    public TeamHeader[] getTeams() {
        return this.teams;
    }

    /**
     * <p>
     * Sets the teams field value.
     * </p>
     * @param teams
     *            The teams field value
     * @throws IllegalArgumentException
     *             If teams contains null elements
     */
    public void setTeams(TeamHeader[] teams) {
        if (teams == null) {
            this.teams = new TeamHeader[0];
        } else {
            Util.checkArrrayHasNull(teams, "teams");
            this.teams = teams;
        }
    }

    /**
     * <p>
     * Gets the technologies field value.
     * </p>
     * @return The technologies field value
     */
    public String[] getTechnologies() {
        return this.technologies;
    }

    /**
     * <p>
     * Sets the technologies field value.
     * </p>
     * @param technologies
     *            The technologies field value
     * @throws IllegalArgumentException
     *             If technologies contains null/empty elements
     */
    public void setTechnologies(String[] technologies) {
        if (technologies == null) {
            this.technologies = new String[0];
        } else {
            Util.checkStringArrayHasNullOrEmptyEle(technologies, "technologies");
            this.technologies = technologies;
        }
    }

    /**
     * Gets the contestSales.
     * 
     * @return the contestSales.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    public List<ContestSaleData> getContestSales() {
        return contestSales;
    }

    /**
     * Sets the contestSales.
     * 
     * @param contestSales the contestSales to set.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    public void setContestSales(List<ContestSaleData> contestSales) {
        if (contestSales == null) {
            this.contestSales = new ArrayList<ContestSaleData>();
        } else {
            this.contestSales = contestSales;
        }
    }
}
