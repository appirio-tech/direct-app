/*
 * Copyright (C) 2009 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.util.Date;

/**
 * <p>
 * Represents the entity class for contest info for myproject widget.
 * </p>
 * 
 * <p>
 * Updated for Cockpit Launch Contest - Inline Spec Reviews part 2
 *      - Added specReviewStatus field.
 * </p>
 * 
 * <p>
 * Version 1.1
 *  Add submissionEndDate for Cockpit Release Assembly 10 - My Projects v1.0
 * </p>
 *
 * <p>
 * Update for Cockpit Spec Review - Stage 2 v1.0.
 *      - Add the specReviewProjectId field. for version 1.2
 * </p>
 *
 * <p>
 * Version 1.2 Update for Direct Search Assembly
 *    - Add the contestFee field and associated setter/getter
 * </p>
 *
 * <p>
 * Version 1.3 (Release Assembly - Port Design Challenge Forum to use Dev Forum)
 *    - Add the forumType field and associated setter/getter
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author will.xie, murphydog, GreatKevin
 * @version 1.3
 */

public class SimpleProjectContestData {

	/**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -6991488651979864256L;


    /**
     * Represents the project id, tc_direct_project_id
     */
    private Long projectId;
	/*
	 * contest id - OR project id
	 */
	private Long contestId;

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
    private Date startDate;

    /**
     * Represents the end date.
     */
    private Date endDate;

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
     * Represents the description for project.
     */
    private String description;

	 /**
     * Represents the forum id.
     */
    private Integer forumId;

	 /**
     * Represents the create user of contest.
     * 
     * @since My Projects Overhaul Assembly.
     */
    private String createUser;
    
    /** 
     * Represents the status for spec reviews. 
     * 
     * @since Cockpit Release Assembly 3 [RS: 1.1.1]
     */
    private String cperm;

    /**
     * Represents the permission for project.
     * 
     * @since Cockpit Release Assembly 3 [RS: 1.1.1]
     */
    private String pperm;

	 /** 
     * Represents the status for spec reviews. 
     * 
     * @since Cockpit Launch Contest - Inline Spec Reviews part 2
     */
    private String specReviewStatus = "";

    /**
     * The date of end of submission(only in software competitions).
     * @since 1.1
     */
    private Date submissionEndDate;

    /**
     * The project id of spec review project linked to this project.
     * @since Cockpit Spec Review - Stage 2 v1.0
     */
    private Long specReviewProjectId;

    /**
     * The contest fee.
     * @since Direct Search Assembly
     */
    private Double contestFee;

    /**
     * is studio type
     */
    private Boolean isStudio;

    /**
     * Contest forum type.
     *
     * @since 1.3
     */
    private String forumType;

    /**
     * @return return the specReviewProjectId
     */
    public Long getSpecReviewProjectId() {
        return specReviewProjectId;
    }

    /**
     * @param specReviewProjectId the specReviewProjectId to set
     */
    public void setSpecReviewProjectId(Long specReviewProjectId) {
        this.specReviewProjectId = specReviewProjectId;
    }

    public String getCname() {
        return cname;
    }

	public void setCname(String cname) {
		this.cname = cname;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getNum_for() {
		return num_for;
	}

	public void setNum_for(Integer num_for) {
		this.num_for = num_for;
	}

	public Integer getNum_reg() {
		return num_reg;
	}

	public void setNum_reg(Integer num_reg) {
		this.num_reg = num_reg;
	}

	public Integer getNum_sub() {
		return num_sub;
	}

	public void setNum_sub(Integer num_sub) {
		this.num_sub = num_sub;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getContestId() {
		return contestId;
	}

	public void setContestId(Long contestId) {
		this.contestId = contestId;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getType() {
		return type;
	}

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
     * <p>
     * Gets the contest permission.
     * </p>
     * 
     * @return the contest permission.
     * @since Cockpit Release Assembly 3 [RS:1.1.1]
     */
    public String getCperm() {
        return cperm;
    }

    /**
     * <p>
     * Sets the contest permission.
     * </p>
     * 
     * @param cperm
     *            the contest permission.
     * @since Cockpit Release Assembly 3 [RS:1.1.1]
     */
    public void setCperm(String cperm) {
        this.cperm = cperm;
    }

    /**
     * <p>
     * Gets the project permission.
     * </p>
     * 
     * @return the project permission.
     * @since Cockpit Release Assembly 3 [RS:1.1.1]
     */
    public String getPperm() {
        return pperm;
    }

    /**
     * <p>
     * Sets the project permission.
     * </p>
     * 
     * @param pperm
     *            the project permission.
     * @since Cockpit Release Assembly 3 [RS:1.1.1]
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
     * Gets the submission end date.
     * 
     * @return submission end date
     * 
     * @since 1.1
     */
    public Date getSubmissionEndDate() {
        return submissionEndDate;
    }

    /**
     * Sets the submission end date.
     * 
     * @param submissionEndDate
     *            the new submission end date
     * 
     * @since 1.1
     */
    public void setSubmissionEndDate(Date submissionEndDate) {
        this.submissionEndDate = submissionEndDate;
    }

    /**
     * Gets the contest fee.
     *
     * @return the contest fee.
     * @since Direct Search Assembly
     */
    public Double getContestFee() {
        return contestFee;
    }

    /**
     * Sets the contest fee.
     *
     * @param contestFee the contest fee
     * @since Direct Search Assembly
     */
    public void setContestFee(Double contestFee) {
        this.contestFee = contestFee;
    }

    /**
     * Gets isStudio
     *
     * @return isStudio.
     */
    public Boolean isStudio() {
        return isStudio;
    }

    /**
     * Sets isStudio
     *
     * @param isStudio isStudio
     */
    public void setIsStudio(Boolean isStudio) {
        this.isStudio = isStudio;
    }

    public String getForumType() {
        return forumType;
    }

    public void setForumType(String forumType) {
        this.forumType = forumType;
    }
}
