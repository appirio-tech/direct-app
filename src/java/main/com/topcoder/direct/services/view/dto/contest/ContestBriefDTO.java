/*
 * Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;

import java.io.Serializable;

/**
 * <p>A DTO providing the brief details for a single contest. </p>
 *
 * <p>Version 1.1 (TC Cockpit Bug Tracking R1 Cockpit Project Tracking version 1.0) change notes:
 * - Add methods equals and hashCode.
 * </p>
 * <p>Version 1.2 (Release Assembly - Cockpit Customer Right Sidebar and Active Contests Coordination version 1.0) change notes:
 * - Add property customerId to store the id of the customer.
 * </p>
 * <p>
 *     Version 1.3 (Module Assembly - TC Cockpit Project Contests Batch Edit) changes:
 *     - Add the field {@link #billingAccountId}
 * </p>
 *
 * @author isv, GreatKevin
 * @version 1.3
 */
public class ContestBriefDTO implements Serializable {

    /**
     * <p>A <code>long</code> providing the ID of this contest.</p>
     */
    private long id;

    /**
     * <p>A <code>String</code> providing the title of this contest.</p>
     */
    private String title;

    /**
     * <p>Indicates if it is software or not.</p>
     */
    private boolean software;

    /**
     * <p>A <code>ProjectBriefDTO</code> providing details on project which this contest belongs to.</p>
     */
    private ProjectBriefDTO project;

    /**
     * <p>A <code>String</code> providing the contest type.</p>
     */
    private String contestTypeName;

    /**
     * The billing account id of the contest.
     *
     * @since 1.3
     */
    private long billingAccountId;

    /**
     * <p>The id of the customer</p>
     *
     * @since 1.2
     */
    private long customerId;

    /**
     * <p>Constructs new <code>ContestBriefDTO</code> instance. This implementation does nothing.</p>
     */
    public ContestBriefDTO() {
    }

    /**
     * <p>Gets the ID of this contest.</p>
     *
     * @return a <code>long</code> providing the ID of this contest.
     */
    public long getId() {
        return id;
    }

    /**
     * <p>Sets the ID of this contest.</p>
     *
     * @param id a <code>long</code> providing the ID of this contest.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * <p>Return the software flag.</p>
     *
     * @return the software
     */
    public boolean isSoftware() {
        return software;
    }

    /**
     * <p>Sets the software flag.</p>
     *
     * @param software the software to set
     */
    public void setSoftware(boolean software) {
        this.software = software;
    }

    /**
     * <p>Gets the title of this contest.</p>
     *
     * @return a <code>String</code> providing the title of this contest.
     */
    public String getTitle() {
        return title;
    }

    /**
     * <p>Sets the title of this contest.</p>
     *
     * @param title a <code>String</code> providing the title of this contest.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * <p>Gets the details on project this contest belongs to.</p>
     *
     * @return a <code>ProjectBriefDTO</code> providing details on project which this contest belongs to.
     */
    public ProjectBriefDTO getProject() {
        return project;
    }

    /**
     * <p>Sets the details on project this contest belongs to.</p>
     *
     * @param project a <code>ProjectBriefDTO</code> providing details on project which this contest belongs to.
     */
    public void setProject(ProjectBriefDTO project) {
        this.project = project;
    }


    /**
     * <p>Gets the contest type of this contest.</p>
     *
     * @return a <code>String</code> providing the contest type.
     */
    public String getContestTypeName() {
        return contestTypeName;
    }

    /**
     * <p>Sets the contest type of this contest.</p>
     *
     * @param contestTypeName a <code>String</code> providing the contest type.
     */
    public void setContestTypeName(String contestTypeName) {
        this.contestTypeName = contestTypeName;
    }

    /**
     * Gets the id of the customer the contest is under.
     *
     * @return the id of the customer.
     * @since 1.2
     */
    public long getCustomerId() {
        return customerId;
    }

    /**
     * Sets the id of the customer the contest is under.
     *
     * @param customerId the id of the customer
     * @since 1.2
     */
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets the billing account id.
     *
     * @return the billing account id.
     * @since 1.3
     */
    public long getBillingAccountId() {
        return billingAccountId;
    }

    /**
     * Sets the billing account id.
     *
     * @param billingAccountId the billing account id.
     * @since 1.3
     */
    public void setBillingAccountId(long billingAccountId) {
        this.billingAccountId = billingAccountId;
    }

    /**
     * <p>Compares the ContestBriefDTO by contest id.</p>
     *
     * @param aThat an object to compare.
     * @return true if the object is of type ContestBriefDTO and has the same contest id, otherwise false.
     * @since 1.1
     */
    @Override
    public boolean equals(Object aThat) {
        if (this == aThat) return true;
        if (!(aThat instanceof ContestBriefDTO)) return false;

        ContestBriefDTO that = (ContestBriefDTO) aThat;

        return this.getId() == that.getId();
    }

    /**
     * <p>Gets the hash code of the ContestBriefDTO</p>
     *
     * @return the hash code of the ContestBriefDTO.
     * @since 1.1
     */
    @Override
    public int hashCode() {
        return (int) this.getId();
    }
}
