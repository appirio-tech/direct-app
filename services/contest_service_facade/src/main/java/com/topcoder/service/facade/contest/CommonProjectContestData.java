/*
 * Copyright (C) 2009-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.contest;

import java.io.Serializable;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Represents the common entity class for contest info for
 * SimpleProjectContestData.
 * </p>
 * 
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 * -----update in version 1.1---- Add the comments for some methods based on the
 * UML file.
 *
 * Version 1.2
 * 	Add checkpointDate and submissionEndDate for Cockpit Release Assembly 10 - My Projects v1.0
 * Version 1.3
 *  Add specReviewProjectId for Cockpit Spec Review - Stage 2 v1.0.
 * Version 1.4
 *  Implements Serializable interface and adds the contest fee field - Direct Search Assembly
 *
 * @author will.xie, squarY, murphydog, BeBetter
 * @version 1.4
 */
public class CommonProjectContestData implements Serializable {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -6991488651979864256L;

    /**
     * Represents the project id.
     */
    private Long projectId;

    /**
     * Represents the phrase name.
     */
    private String pname;

    /**
     * Represents the contest name.
     */
    private String cname;

    /**
     * Represents the start date.
     */
    private XMLGregorianCalendar startDate;

    /**
     * Represents the end date.
     */
    private XMLGregorianCalendar endDate;

    /**
     * Represents the Registrants number.
     */
    private Integer num_reg;

    /**
     * Represents the submission number.
     */
    private Integer num_sub;

    /**
     * Represents the post number in forum.
     */
    private Integer num_for;

    /**
     * Represents the status name.
     */
    private String sname;

    /**
     * Represents the contest type.
     */
    private String type;

    /**
     * Represents the contest id.
     */
    private Long contestId;

    /**
     * Represents the forum id.
     */
    private Integer forumId;

    /**
     * Represents the description for project.
     */
    private String description;

    /**
     * Represents the create user of contest.
     * 
     * @since My Projects Overhaul Assembly.
     */
    private String createUser;

    /**
     * Represents the permission for contest.
     */
    private String cperm;

    /**
     * Represents the permissionfor project.
     */
    private String pperm;

	 /** 
     * Represents the status for spec reviews. 
     * 
     * @since Cockpit Launch Contest - Inline Spec Reviews part 2
     */
    private String specReviewStatus;

    /**
     * The date of checkpoint(if exists).
     * @since 1.2
     */
    private XMLGregorianCalendar checkpointDate;

    /**
     * The date of end of submission(only in software competitions).
     * @since 1.2
     */
    private XMLGregorianCalendar submissionEndDate;
    
    /**
     * Spec review project id.
     * @since 1.3
     */
    private Long specReviewProjectId;

    /**
     * The contest fee.
     * @since 1.4
     */
    private Double contestFee;

    /**
     * Returns the value of specReviewProjectId.
     * @return the specReviewProjectId
     */
    public Long getSpecReviewProjectId() {
        return specReviewProjectId;
    }

    /**
     * Set the value to  specReviewProjectId field.
     * @param specReviewProjectId the specReviewProjectId to set
     */
    public void setSpecReviewProjectId(Long specReviewProjectId) {
        this.specReviewProjectId = specReviewProjectId;
    }

    /**
     * Returns the contestId.
     * 
     * @return the contestId.
     */
    public Long getContestId() {
        return contestId;
    }

    /**
     * Updates the contestId with the specified value.
     * 
     * @param contestId
     *            the contestId to set.
     */
    public void setContestId(Long contestId) {
        this.contestId = contestId;
    }

    /**
     * Get cname
     * 
     * Impl note: Get the namesake variable.
     * 
     * @return the cname attribute of the CommonProjectContestData
     */
    public String getCname() {
        return cname;
    }

    /**
     * Set cname
     * 
     * Impl note: Set the namesake variable. This set method does not perform
     * any check on the argument.
     * 
     * @param cname
     *            the cname attribute of the CommonProjectContestData entity
     */
    public void setCname(String cname) {
        this.cname = cname;
    }

    /**
     * Get end date
     * 
     * Impl note: Get the namesake variable.
     * 
     * @return the end date attribute of the CommonProjectContestData entity
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Get num_for
     * 
     * Impl note: Get the namesake variable.
     * 
     * @param endDate
     *            the num_for attribute of the CommonProjectContestData entity
     */
    public void setEndDate(XMLGregorianCalendar endDate) {
        this.endDate = endDate;
    }

    /**
     * Get num_for
     * 
     * Impl note: Get the namesake variable.
     * 
     * @return the num_for attribute to set to the CommonProjectContestData
     *         entity
     */
    public Integer getNum_for() {
        return num_for;
    }

    /**
     * Set num_for
     * 
     * Impl note: Set the namesake variable. This set method does not perform
     * any check on the argument.
     * 
     * @param num_for
     *            the num_for attribute to set to the CommonProjectContestData
     *            entity
     */
    public void setNum_for(Integer num_for) {
        this.num_for = num_for;
    }

    /**
     * Get num_reg
     * 
     * Impl note: Get the namesake variable.
     * 
     * @return the num_for attribute to set to the CommonProjectContestData
     *         entity
     */
    public Integer getNum_reg() {
        return num_reg;
    }

    /**
     * Set num_reg
     * 
     * Impl note: Set the namesake variable. This set method does not perform
     * any check on the argument.
     * 
     * @param num_reg
     *            the num_reg attribute to set to the CommonProjectContestData
     *            entity
     */
    public void setNum_reg(Integer num_reg) {
        this.num_reg = num_reg;
    }

    /**
     * Get num_sub
     * 
     * Impl note: Get the namesake variable.
     * 
     * @return the num_sub attribute of the CommonProjectContestData entity
     */
    public Integer getNum_sub() {
        return num_sub;
    }

    /**
     * Set num_sub
     * 
     * Impl note: Set the namesake variable. This set method does not perform
     * any check on the argument.
     * 
     * @param num_sub
     *            the num_sub attribute to set to the CommonProjectContestData
     *            entity
     */
    public void setNum_sub(Integer num_sub) {
        this.num_sub = num_sub;
    }

    /**
     * Get pname
     * 
     * Impl note: Get the namesake variable.
     * 
     * @return the pname attribute of the CommonProjectContestData entity
     */
    public String getPname() {
        return pname;
    }

    /**
     * Set pname
     * 
     * Impl note: Set the namesake variable. This set method does not perform
     * any check on the argument.
     * 
     * @param pname
     *            the pname attribute to set to the CommonProjectContestData
     *            entity
     */
    public void setPname(String pname) {
        this.pname = pname;
    }

    /**
     * Get project id
     * 
     * Impl note: Get the namesake variable.
     * 
     * @return the project id attribute of the CommonProjectContestData entity
     */
    public Long getProjectId() {
        return projectId;
    }

    /**
     * Set project id
     * 
     * Impl note: Set the namesake variable. This set method does not perform
     * any check on the argument.
     * 
     * @param projectId
     *            the project id attribute of the CommonProjectContestData
     *            entity
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    /**
     * Get sname
     * 
     * Impl note: Get the namesake variable.
     * 
     * @return the sname attribute of the CommonProjectContestData entity
     */
    public String getSname() {
        return sname;
    }

    /**
     * Set sname
     * 
     * Impl note: Set the namesake variable. This set method does not perform
     * any check on the argument.
     * 
     * @param sname
     *            the sname attribute of the CommonProjectContestData entity
     */
    public void setSname(String sname) {
        this.sname = sname;
    }

    /**
     * Get start date
     * 
     * Impl note: Get the namesake variable.
     * 
     * @return the start date attribute of the CommonProjectContestData entity
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * Set start date
     * 
     * Impl note: Set the namesake variable. This set method does not perform
     * any check on the argument.
     * 
     * @param startDate
     *            the start date attribute to set to the
     *            CommonProjectContestData entity
     */
    public void setStartDate(XMLGregorianCalendar startDate) {
        this.startDate = startDate;
    }

    /**
     * Get type
     * 
     * Impl note: Get the namesake variable.
     * 
     * @return the type attribute of the CommonProjectContestData entity
     */
    public String getType() {
        return type;
    }

    /**
     * Set type
     * 
     * Impl note: Set the namesake variable. This set method does not perform
     * any check on the argument.
     * 
     * @param type
     *            the type attribute to set to the CommonProjectContestData
     *            entity
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns the description.
     * 
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Updates the description with the specified value.
     * 
     * @param description
     *            the description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the forum id.
     * 
     * @return the forum id.
     */
    public Integer getForumId() {
        return forumId;
    }

    /**
     * Updates the forumId with the specified value.
     * 
     * @param forumId
     *            the forumId to set.
     */
    public void setForumId(Integer forumId) {
        this.forumId = forumId;
    }

    /**
     * <p>
     * Gets the create user.
     * </p>
     * 
     * @return the create user
     * @since My Projects Overhaul Assembly
     */
    public String getCreateUser() {
        return this.createUser;
    }

    /**
     * <p>
     * Sets the create user.
     * </p>
     * 
     * @param createUser
     *            the create user to set
     * @since My Projects Overhaul Assembly.
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * Get cperm
     * 
     * Impl note: Get the namesake variable.
     * 
     * @return the cperm attribute of the CommonProjectContestData entity
     */
    public String getCperm() {
        return cperm;
    }

    /**
     * Set cperm
     * 
     * Impl note: Set the namesake variable. This set method does not perform
     * any check on the argument.
     * 
     * @param cperm
     *            the cperm attribute to set to the CommonProjectContestData
     *            entity
     */
    public void setCperm(String cperm) {
        this.cperm = cperm;
    }

    /**
     * Get pperm
     * 
     * Impl note: Get the namesake variable.
     * 
     * @return the pperm attribute of the CommonProjectContestData entity
     */
    public String getPperm() {
        return pperm;
    }

    /**
     * Set pperm
     * 
     * Impl note: Set the namesake variable. This set method does not perform
     * any check on the argument.
     * 
     * @param pperm
     *            the pperm attribute to set to the CommonProjectContestData
     *            entity
     */
    public void setPperm(String pperm) {
        this.pperm = pperm;
    }

	/**
     * Gets the spec review status.
     * 
     * @return the spec review status
     * 
     * @since Cockpit Launch Contest - Inline Spec Reviews part 2
     */
    public String getSpecReviewStatus() {
        return specReviewStatus;
    }

    /**
     * Sets the spec review status.
     * 
     * @param specReviewStatus
     *            the new spec review status
     * 
     * @since Cockpit Launch Contest - Inline Spec Reviews part 2
     */
    public void setSpecReviewStatus(String specReviewStatus) {
        this.specReviewStatus = specReviewStatus;
    }


    /**
     * Gets the checkpoint date.
     * 
     * @return the checkpoint date
     * 
     * @since 1.2
     */
    public XMLGregorianCalendar getCheckpointDate() {
        return checkpointDate;
    }

    /**
     * Sets the checkpoint date.
     * 
     * @param checkpointDate
     *            the new checkpoint dates
     * 
     * @since 1.2
     */
    public void setCheckpointDate(XMLGregorianCalendar checkpointDate) {
        this.checkpointDate = checkpointDate;
    }

    /**
     * Gets the submission end date.
     * 
     * @return submission end date
     * 
     * @since 1.2
     */
    public XMLGregorianCalendar getSubmissionEndDate() {
        return submissionEndDate;
    }

    /**
     * Sets the submission end date.
     * 
     * @param submissionEndDate
     *            the new submission end date
     * 
     * @since 1.2
     */
    public void setSubmissionEndDate(XMLGregorianCalendar submissionEndDate) {
        this.submissionEndDate = submissionEndDate;
    }

    /**
     * Gets the contest fee.
     *
     * @return the contest fee.
     * @since 1.4
     */
    public Double getContestFee() {
        return contestFee;
    }

    /**
     * Sets the contest fee.
     *
     * @param contestFee the contest fee
     * @since 1.4
     */
    public void setContestFee(Double contestFee) {
        this.contestFee = contestFee;
    }
}
