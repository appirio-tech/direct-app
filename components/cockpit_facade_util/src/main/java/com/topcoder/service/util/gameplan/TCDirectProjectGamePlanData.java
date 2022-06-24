/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.util.gameplan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>This class is a container for game plan data associated with a single TC Direct project.</p>
 *
 * <p>It is a simple JavaBean (POJO) that provides getters and setters for all private fields and performs no argument
 * validation in the setters.</p>
 *
 * <p><b>Thread Safety</b>: This class is mutable and not thread safe.</p>
 *
 * @author saarixx, FireIce
 * @version 1.0
 */
public class TCDirectProjectGamePlanData implements Serializable {
    /**
     * The ID of the TC Direct project. Can be any value. Has getter and setter.
     */
    private long tcDirectProjectId;
    
    /**
     * The name of the TC Direct project. Can not be empty. Has getter and setter.
     */
    private String tcDirectProjectName;

    /**
     * The list of software projects for this TC Direct project. Is initialized in the constructor with an empty list.
     * Can be any value, can contain any values. Has getter and setter.
     */
    private List<SoftwareProjectData> softwareProjects;

    /**
     * The list of studio projects for this TC Direct project. Is initialized in the constructor with an empty list. Can
     * be any value, can contain any values. Has getter and setter.
     */
    private List<StudioProjectData> studioProjects;


    /**
     * Creates a <code>TCDirectProjectGamePlanData</code> instance.
     */
    public TCDirectProjectGamePlanData() {
        softwareProjects = new ArrayList<SoftwareProjectData>();
        studioProjects = new ArrayList<StudioProjectData>();
    }

    /**
     * Retrieves the ID of the TC Direct project.
     *
     * @return the ID of the TC Direct project
     */
    public long getTcDirectProjectId() {
        return tcDirectProjectId;
    }

    /**
     * Sets the ID of the TC Direct project.
     *
     * @param tcDirectProjectId the ID of the TC Direct project
     */
    public void setTcDirectProjectId(long tcDirectProjectId) {
        this.tcDirectProjectId = tcDirectProjectId;
    }
    
    /**
     * Retrieves the name of the TC Direct project.
     *
     * @return the name of the TC Direct project
     */
    public String getTcDirectProjectName() {
        return tcDirectProjectName;
    }

    /**
     * Sets the name of the TC Direct project.
     *
     * @param tcDirectProjectName the name of the TC Direct project
     */
    public void setTcDirectProjectName(String tcDirectProjectName) {
        this.tcDirectProjectName = tcDirectProjectName;
    }

    /**
     * Retrieves the list of software projects for this TC Direct project.
     *
     * @return the list of software projects for this TC Direct project
     */
    public List<SoftwareProjectData> getSoftwareProjects() {
        return softwareProjects;
    }

    /**
     * Sets the list of software projects for this TC Direct project.
     *
     * @param softwareProjects the list of software projects for this TC Direct project
     */
    public void setSoftwareProjects(List<SoftwareProjectData> softwareProjects) {
        this.softwareProjects = softwareProjects;
    }

    /**
     * Retrieves the list of studio projects for this TC Direct project.
     *
     * @return the list of studio projects for this TC Direct project
     */
    public List<StudioProjectData> getStudioProjects() {
        return studioProjects;
    }

    /**
     * Sets the list of studio projects for this TC Direct project.
     *
     * @param studioProjects the list of studio projects for this TC Direct project
     */
    public void setStudioProjects(List<StudioProjectData> studioProjects) {
        this.studioProjects = studioProjects;
    }
}

