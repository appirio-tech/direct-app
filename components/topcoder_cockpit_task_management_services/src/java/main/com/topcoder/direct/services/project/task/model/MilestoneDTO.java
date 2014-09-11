/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.model;

import java.io.Serializable;

/**
 * <p>
 * This is the DTO for a project milestone, which holds the milestone ID and milestone name.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is not thread safe because it is mutable.
 * </p>
 *
 * <p>
 * Version 1.1 (TC - Cockpit Tasks Management Assembly 3)
 * <ul>
 *     <li>Overrides hashcode and equals so it  can be used in hashmap and hashset</li>
 * </ul>
 * </p>
 *
 * @author albertwang, sparemax, GreatKevin
 * @version 1.1
 */
public class MilestoneDTO implements Serializable {
    /**
     * The serial version ID.
     */
    private static final long serialVersionUID = 1681667751385868810L;
    /**
     * <p>
     * Represents the milestone id.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private long milestoneId;
    /**
     * <p>
     * Represents the milestone name.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private String milestoneName;

    /**
     * Creates an instance of MilestoneDTO.
     */
    public MilestoneDTO() {
        // Empty
    }

    /**
     * Gets the milestone id.
     *
     * @return the milestone id.
     */
    public long getMilestoneId() {
        return milestoneId;
    }

    /**
     * Sets the milestone id.
     *
     * @param milestoneId
     *            the milestone id.
     */
    public void setMilestoneId(long milestoneId) {
        this.milestoneId = milestoneId;
    }

    /**
     * Gets the milestone name.
     *
     * @return the milestone name.
     */
    public String getMilestoneName() {
        return milestoneName;
    }

    /**
     * Sets the milestone name.
     *
     * @param milestoneName
     *            the milestone name.
     */
    public void setMilestoneName(String milestoneName) {
        this.milestoneName = milestoneName;
    }

    /**
     * Gets the hash code.
     *
     * @return the hash code.
     * @since 1.1
     */
    @Override
    public int hashCode() {
        return (int) this.milestoneId;
    }

    /**
     * Compare if equals to the specified object.
     *
     * @param obj the object used to compare
     * @return true if equals, false otherwise.
     * @since 1.1
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MilestoneDTO)) {
            return false;
        }

        return this.milestoneId == ((MilestoneDTO) obj).getMilestoneId();
    }
}
