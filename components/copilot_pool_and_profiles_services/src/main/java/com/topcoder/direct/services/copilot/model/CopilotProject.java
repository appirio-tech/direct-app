/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.model;

import java.util.Date;
import java.util.Set;

/**
 * <p>This class is a container for information about a single copilot project. It is a simple JavaBean (POJO) that
 * provides getters and setters for all private attributes and performs no argument validation in the setters.</p>
 *
 * <p><strong>Thread safety:</strong> This class is mutable and not thread safe.</p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class CopilotProject extends IdentifiableEntity {

    /**
     * <p>The ID of the copilot profile associated with this project. Can be any value. Has getter and setter.</p>
     */
    private long copilotProfileId;

    /**
     * <p>The name of the copilot project. Can be any value. Has getter and setter.</p>
     */
    private String name;

    /**
     * <p>The ID of the TC direct project associated with this copilot project. Can be any value. Has getter and
     * setter.</p>
     */
    private long tcDirectProjectId;

    /**
     * <p>The copilot type. Can be any value. Has getter and setter.</p>
     */
    private CopilotType copilotType;

    /**
     * <p>The copilot project status. Can be any value. Has getter and setter.</p>
     */
    private CopilotProjectStatus status;

    /**
     * <p>The customer feedback for the copilot project. Can be any value. Has getter and setter.</p>
     */
    private String customerFeedback;

    /**
     * <p>The customer rating for the copilot project. Can be any value. Has getter and setter.</p>
     */
    private Float customerRating;

    /**
     * <p>The feedback of the PM for the copilot project. Can be any value. Has getter and setter.</p>
     */
    private String pmFeedback;

    /**
     * <p>The rating of the PM for the copilot project. Can be any value. Has getter and setter.</p>
     */
    private Float pmRating;

    /**
     * <p>The flag indicating whether the copilot project is a private one. Can be any value. Has getter and
     * setter.</p>
     */
    private boolean privateProject;

    /**
     * <p>The additional project details. Can be any value. Has getter and setter.</p>
     */
    private Set<CopilotProjectInfo> projectInfos;

    /**
     * <p>The completion date of the copilot project. Can be any value. Has getter and setter.</p>
     */
    private Date completionDate;

    /**
     * <p>Creates new instance of <code>{@link CopilotProject}</code> class.</p>
     */
    public CopilotProject() {
        // empty constructor
    }

    /**
     * <p>Retrieves the ID of the copilot profile associated with this project.</p>
     *
     * @return the ID of the copilot profile associated with this project
     */
    public long getCopilotProfileId() {
        return copilotProfileId;
    }

    /**
     * <p>Sets the ID of the copilot profile associated with this project.</p>
     *
     * @param copilotProfileId the ID of the copilot profile associated with this project
     */
    public void setCopilotProfileId(long copilotProfileId) {
        this.copilotProfileId = copilotProfileId;
    }

    /**
     * <p>Retrieves the name of the copilot project.</p>
     *
     * @return the name of the copilot project
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Sets the name of the copilot project.</p>
     *
     * @param name the name of the copilot project
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>Retrieves the ID of the TC direct project associated with this copilot project.</p>
     *
     * @return the ID of the TC direct project associated with this copilot project
     */
    public long getTcDirectProjectId() {
        return tcDirectProjectId;
    }

    /**
     * <p>Sets the ID of the TC direct project associated with this copilot project.</p>
     *
     * @param tcDirectProjectId the ID of the TC direct project associated with this copilot project
     */
    public void setTcDirectProjectId(long tcDirectProjectId) {
        this.tcDirectProjectId = tcDirectProjectId;
    }

    /**
     * <p>Retrieves the copilot type.</p>
     *
     * @return the copilot type
     */
    public CopilotType getCopilotType() {
        return copilotType;
    }

    /**
     * <p>Sets the copilot type.</p>
     *
     * @param copilotType the copilot type
     */
    public void setCopilotType(CopilotType copilotType) {
        this.copilotType = copilotType;
    }

    /**
     * <p>Retrieves the copilot project status.</p>
     *
     * @return the copilot project status
     */
    public CopilotProjectStatus getStatus() {
        return status;
    }

    /**
     * <p>Sets the copilot project status.</p>
     *
     * @param status the copilot project status
     */
    public void setStatus(CopilotProjectStatus status) {
        this.status = status;
    }

    /**
     * <p>Retrieves the customer feedback for the copilot project.</p>
     *
     * @return the customer feedback for the copilot project
     */
    public String getCustomerFeedback() {
        return customerFeedback;
    }

    /**
     * <p>Sets the customer feedback for the copilot project.</p>
     *
     * @param customerFeedback the customer feedback for the copilot project
     */
    public void setCustomerFeedback(String customerFeedback) {
        this.customerFeedback = customerFeedback;
    }

    /**
     * <p>Retrieves the customer rating for the copilot project.</p>
     *
     * @return the customer rating for the copilot project
     */
    public Float getCustomerRating() {
        return customerRating;
    }

    /**
     * <p>Sets the customer rating for the copilot project.</p>
     *
     * @param customerRating the customer rating for the copilot project
     */
    public void setCustomerRating(Float customerRating) {
        this.customerRating = customerRating;
    }

    /**
     * <p>Retrieves the feedback of the PM for the copilot project.</p>
     *
     * @return the feedback of the PM for the copilot project
     */
    public String getPmFeedback() {
        return pmFeedback;
    }

    /**
     * <p>Sets the feedback of the PM for the copilot project.</p>
     *
     * @param pmFeedback the feedback of the PM for the copilot project
     */
    public void setPmFeedback(String pmFeedback) {
        this.pmFeedback = pmFeedback;
    }

    /**
     * <p>Retrieves the rating of the PM for the copilot project.</p>
     *
     * @return the rating of the PM for the copilot project
     */
    public Float getPmRating() {
        return pmRating;
    }

    /**
     * <p>Sets the rating of the PM for the copilot project.</p>
     *
     * @param pmRating the rating of the PM for the copilot project
     */
    public void setPmRating(Float pmRating) {
        this.pmRating = pmRating;
    }

    /**
     * <p>Retrieves the flag indicating whether the copilot project is a private one.</p>
     *
     * @return the flag indicating whether the copilot project is a private one
     */
    public boolean isPrivateProject() {
        return privateProject;
    }

    /**
     * <p>Sets the flag indicating whether the copilot project is a private one.</p>
     *
     * @param privateProject the flag indicating whether the copilot project is a private one
     */
    public void setPrivateProject(boolean privateProject) {
        this.privateProject = privateProject;
    }

    /**
     * <p>Retrieves the additional project details.</p>
     *
     * @return the additional project details
     */
    public Set<CopilotProjectInfo> getProjectInfos() {
        return projectInfos;
    }

    /**
     * <p>Sets the additional project details.</p>
     *
     * @param projectInfos the additional project details
     */
    public void setProjectInfos(Set<CopilotProjectInfo> projectInfos) {
        this.projectInfos = projectInfos;
    }

    /**
     * <p>Retrieves the completion date of the copilot project.</p>
     *
     * @return the completion date of the copilot project
     */
    public Date getCompletionDate() {
        return completionDate;
    }

    /**
     * <p>Sets the completion date of the copilot project.</p>
     *
     * @param completionDate the completion date of the copilot project
     */
    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }
}

