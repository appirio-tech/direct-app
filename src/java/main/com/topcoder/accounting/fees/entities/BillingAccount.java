/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.fees.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.topcoder.direct.services.view.dto.contest.SoftwareContestSubmissionsDTO;

/**
 * Represents the Billing Account entity. It's a DTO object.
 * 
 * Thread safety: The class is mutable and not thread safe.
 * 
 * <p>
 * Version 1.1 (Release Assembly - Project Contest Fee Management ) Change notes:
 *   <ol>
 *     <li>Added a new clientName instance variable.</li>
 *   </ol>
 * </p>
 * 
 * @author winstips, TCSDEVELOPER
 * @version 1.1
 */
public class BillingAccount implements Serializable {
    /**
     * Represents the billing account id. It is managed with a getter and setter. It may have any value. It is fully
     * mutable.
     */
    private long projectId;

    /**
     * Represents the company id. It is managed with a getter and setter. It may have any value. It is fully mutable.
     */
    private long companyId;
    /**
     * Represents the billing account name. It is managed with a getter and setter. It may have any value. It is fully
     * mutable.
     */
    private String name;
    /**
     * Represents the billing account status. It is managed with a getter and setter. It may have any value. It is fully
     * mutable.
     */
    private boolean active;
    /**
     * Represents the list of contest fees associated with the billing account. It is managed with a getter and setter.
     * It may have any value. It is fully mutable.
     */
    private List<ContestFeeDetails> contestFees;
    /**
     * Represents the start time of the project. It is managed with a getter and setter. It may have any value. It is
     * fully mutable.
     */
    private Date startDate;
    /**
     * Represents the billing account description. It is managed with a getter and setter. It may have any value. It is
     * fully mutable.
     */
    private String description;
    
    /**
     * The client name.
     */
    private String clientName;

    /**
     * Default Constructor.
     */
    public BillingAccount() {
        super();
    }

    /**
     * Returns the projectId field value.
     * 
     * @return projectId field value.
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Sets the given value to projectId field.
     * 
     * @param projectId
     *            - the given value to set.
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * Returns the companyId field value.
     * 
     * @return companyId field value.
     */
    public long getCompanyId() {
        return companyId;
    }

    /**
     * Sets the given value to companyId field.
     * 
     * @param companyId
     *            - the given value to set.
     */
    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    /**
     * Returns the name field value.
     * 
     * @return name field value.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the given value to name field.
     * 
     * @param name
     *            - the given value to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the active field value.
     * 
     * @return active field value.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the given value to active field.
     * 
     * @param active
     *            - the given value to set.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Returns the contestFees field value.
     * 
     * @return contestFees field value.
     */
    public List<ContestFeeDetails> getContestFees() {
        return contestFees;
    }

    /**
     * Sets the given value to contestFees field.
     * 
     * @param contestFees
     *            - the given value to set.
     */
    public void setContestFees(List<ContestFeeDetails> contestFees) {
        this.contestFees = contestFees;
    }

    /**
     * Returns the startDate field value.
     * 
     * @return startDate field value.
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the given value to startDate field.
     * 
     * @param startDate
     *            - the given value to set.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Returns the description field value.
     * 
     * @return description field value.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the given value to description field.
     * 
     * @param description
     *            - the given value to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for the client name.
     * 
     * @return the client name.
     */
	public String getClientName() {
		return clientName;
	}

	/**
	 * Setter for the client name.
	 * 
	 * @param clientName the client name.
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
    
    
}
