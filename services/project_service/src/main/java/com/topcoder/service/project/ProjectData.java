/*
 * Copyright (C) 2008 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.topcoder.service.project.entities.ProjectAnswer;

/**
 * <p>
 * This data object serves to communicate between project data between the bean and its client. It also serves as a base
 * class for the Project class.
 * </p>
 * <p>
 * We simply define three properties and getter/setters for these properties. Note that this is a dumb DTO - no
 * validation is done.
 * </p>
 *
 * <p>
 *     Version 1.1 - Release Assembly - TopCoder Cockpit Project Status Management changes:
 *     <li>Add property <code>projectStatusId</code> and sql result set mapping for it.</li>
 * </p>
 * <p>
 *     Version 1.2 - add the property project forum category id.
 * </p>
 * <p>
 *     Version 1.3 - Release Assembly - TopCoder Cockpit DataTables Filter Panel and Search Bar changes:
 *     - add the property {@link #creationDate} to represent the project creation date.
 * </p>
 *
 * <p>
 *     Version 1.4 - Module Assembly - TC Cockpit Direct Project Related Services Update and Integration changes:
 *     - add the properties {@link #completionDate}m {@link #projectType} and {@link #projectCategory}
 * </p>
 * 
 * <p>
 *    Version 1.5(TC Cockpit Create New Project Back End v1.0) change log:
 *    <ul>
 *      <li>Add field {@link #projectAnswers}</li>
 *    </ul>
 * </p>
 *
 * <p>
 *    Version 1.6 (Release Assembly - TC Cockpit Start Project Flow Billing Account Integration)
 *    - Add project billing account id property
 * </p>
 *
 * <p>
 *    Version 1.7 (Release Assembly - TC Cockpit Bug Race Cost and Fees Part 1) change log:
 *    <ul>
 *      <li>Add fields {@link #fixedBugContestFee} and {@link #percentageBugContestFee}. Also
 *      the setters/getters were added.</li>
 *    </ul>
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: This class is not thread safe as it has mutable state.
 * </p>
 *
 * @author humblefool, FireIce, GreatKevin, TCSASSEMBLER
 * @version 1.7
 */
@Entity
public class ProjectData implements Serializable {
    /**
     * <p>
     * Represents the serial version unique id.
     * </p>
     */
    private static final long serialVersionUID = 9053041638081689882L;

    /**
     * <p>
     * Represents the ID of this project.
     * </p>
     * <p>
     * It uses <code>Long</code> type instead of <code>long</code> type to allow for null values to be set before
     * entity creation in persistence. This variable is mutable and is retrieved by the {@link #getProjectId()} method
     * and set by the {@link #setProjectId(Long)} method. It is initialized to null, and may be set to ANY value.
     * </p>
     */
    @Id
    private Long projectId;

    /**
     * <p>
     * Represents the name of this project.
     * </p>
     * <p>
     * This variable is mutable and is retrieved by the {@link #getName()} method and set by the
     * {@link #setName(String)} method. It is initialized to null, and may be set to ANY value, including null/empty
     * string.
     * </p>
     */
    private String name;

    /**
     * <p>
     * Represents the description of this project.
     * </p>
     * <p>
     * This variable is mutable and is retrieved by the getDescription method and set by the setDescription method. It
     * is initialized to null, and may be set to ANY value, including null/empty string.
     * </p>
     */
    private String description;

     /**
     * <p>
     * Represents project status id
     * </p>
     * <p>
     * It uses <code>Long</code> type instead of <code>long</code> type to allow for null values to be set before
     * entity creation in persistence.
     * </p>
      *@since 1.1
     */
    private Long projectStatusId;


    /**
     * The project forum category id.
     * @since 1.2
     */
    private String forumCategoryId;

    /**
     * The creation date of the project.
     * @since 1.3
     */
    private Date creationDate;

    /**
     * The completion date of the project.
     * @since 1.4
     */
    private Date completionDate;

    /**
     * The project type of the project.
     * @since 1.4
     */
    private ProjectType projectType;

    /**
     * The project category of the project.
     * @since 1.4
     */
    private ProjectCategory projectCategory;

    /**
     * The fixed bug race contest fee.
     * @since 1.7
     */
    private Double fixedBugContestFee;

    /**
     * The percentage bug race contest fee.
     * @since 1.7
     */
    private Double percentageBugContestFee;

    /**
     * The project billing account id.
     *
     * @since 1.6
     */
    private long projectBillingAccountId;
    
    /**
     * <p>
     * The associated project answers.
     * </p>
     * @since 1.5
     */
    @OneToMany
    private List<ProjectAnswer> projectAnswers = new ArrayList<ProjectAnswer>();
    

    /**
     * <p>
     * Gets the project status id
     * </p>
     *
     * @return The status id of the project.
     * @since 1.1
     */
    public Long getProjectStatusId() {
        return projectStatusId;
    }

    /**
     * <p>
     * Sets the project status id
     * </p>
     *
     * @param projectStatusId The project status ID of this project.
     * @since 1.1
     */
    public void setProjectStatusId(Long projectStatusId) {
        this.projectStatusId = projectStatusId;
    }
  

    /**
     * <p>
     * Constructs a <code>ProjectData</code> instance.
     * </p>
     */
    public ProjectData() {
    }

    /**
     * <p>
     * Gets the ID of the project.
     * </p>
     *
     * @return The ID of the project.
     */
    public Long getProjectId() {
        return projectId;
    }

    /**
     * <p>
     * Sets the ID of this project.
     * </p>
     *
     * @param projectId
     *            The desired ID of this project. ANY value.
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    /**
     * <p>
     * Gets the name of the project.
     * </p>
     *
     * @return The name of the project.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Sets the name of this project.
     * </p>
     *
     * @param name
     *            The desired name of this project. ANY value.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>
     * Gets the description of the project.
     * </p>
     *
     * @return The description of the project.
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>
     * Sets the description of this project.
     * </p>
     *
     * @param description
     *            The desired description of this project. ANY value.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the project forum category id.
     *
     * @return the project forum category id.
     * @since 1.2
     */
    public String getForumCategoryId() {
        return forumCategoryId;
    }

    /**
     * Sets the project forum category id
     *
     * @param forumCategoryId the project forum category id
     * @since 1.2
     */
    public void setForumCategoryId(String forumCategoryId) {
        this.forumCategoryId = forumCategoryId;
    }

    /**
     * Gets the project creation date.
     *
     * @return the project creation date.
     * @since 1.3
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the project creation date.
     *
     * @param creationDate the project creation date to set.
     * @since 1.3
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Gets the completion date of the project.
     *
     * @return the completion date of the project.
     * @since 1.4
     */
    public Date getCompletionDate() {
        return completionDate;
    }

    /**
     * Sets the completion date of the project.
     *
     * @param completionDate the completion date of the project.
     * @since 1.4
     */
    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    /**
     * Gets the project type of the project.
     *
     * @return the project type of the project.
     * @since 1.4
     */
    public ProjectType getProjectType() {
        return projectType;
    }

    /**
     * Sets the project type of the project.
     *
     * @param projectType the project type of the project.
     * @since 1.4
     */
    public void setProjectType(ProjectType projectType) {
        this.projectType = projectType;
    }

    /**
     * Gets the project category of the project.
     *
     * @return the project category of the project.
     * @since 1.4
     */
    public ProjectCategory getProjectCategory() {
        return projectCategory;
    }

    /**
     * Sets the project category of the project.
     *
     * @param projectCategory the project category of the project.
     * @since 1.4
     */
    public void setProjectCategory(ProjectCategory projectCategory) {
        this.projectCategory = projectCategory;
    }

    /**
     * <p>
     * Getter of projectAnswers field.
     * </p>
     * @return the projectAnswers
     * @since 1.5
     */
    public List<ProjectAnswer> getProjectAnswers() {
        return projectAnswers;
    }

    /**
     * <p>
     * Setter of projectAnswers field.
     * </p>
     * 
     * @param projectAnswers the projectAnswers to set
     * @since 1.5
     */
    public void setProjectAnswers(List<ProjectAnswer> projectAnswers) {
        this.projectAnswers = projectAnswers;
    }

    /**
     * Gets the project billing account id.
     *
     * @return the project billing account id.
     * @since 1.6
     */
    public long getProjectBillingAccountId() {
        return projectBillingAccountId;
    }

    /**
     * Sets the project billing id.
     *
     * @param projectBillingAccountId the project billing account id.
     * @since 1.6
     */
    public void setProjectBillingAccountId(long projectBillingAccountId) {
        this.projectBillingAccountId = projectBillingAccountId;
    }

    /**
     * Gets the fixed bug race contest fee.
     *
     * @return the fixed bug race contest fee.
     * @since 1.7
     */
    public Double getFixedBugContestFee() {
        return fixedBugContestFee;
    }

    /**
     * Sets the fixed bug race contest fee.
     *
     * @param fixedBugContestFee the fixed bug race contest fee.
     * @since 1.7
     */
    public void setFixedBugContestFee(Double fixedBugContestFee) {
        this.fixedBugContestFee = fixedBugContestFee;
    }

    /**
     * Gets the percentage bug race contest fee.
     *
     * @return the percentage bug race contest fee.
     * @since 1.7
     */
    public Double getPercentageBugContestFee() {
        return percentageBugContestFee;
    }

    /**
     * Sets the percentage bug race contest fee.
     *
     * @param percentageBugContestFee the percentage bug race contest fee.
     * @since 1.7
     */
    public void setPercentageBugContestFee(Double percentageBugContestFee) {
        this.percentageBugContestFee = percentageBugContestFee;
    }
}
