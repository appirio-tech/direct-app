/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.model;

import java.io.Serializable;

/**
 * <p>
 * This is the DTO for a contest, which holds the contest ID and contest name.
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
public class ContestDTO implements Serializable {
    /**
     * The serial version ID.
     */
    private static final long serialVersionUID = -2063888071456209632L;
    /**
     * <p>
     * Represents the contest id.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private long contestId;
    /**
     * <p>
     * Represents the contest name.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private String contestName;

    /**
     * Creates an instance of ContestDTO.
     */
    public ContestDTO() {
        // Empty
    }

    /**
     * Gets the contest id.
     *
     * @return the contest id.
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * Sets the contest id.
     *
     * @param contestId
     *            the contest id.
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * Gets the contest name.
     *
     * @return the contest name.
     */
    public String getContestName() {
        return contestName;
    }

    /**
     * Sets the contest name.
     *
     * @param contestName
     *            the contest name.
     */
    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    /**
     * Gets the hashcode.
     *
     * @return the hash code.
     * @since 1.1
     */
    @Override
    public int hashCode() {
        return (int) this.contestId;
    }

    /**
     * Gets the equals method.
     *
     * @param obj the object to compare
     * @return true if equals, false otherwise.
     * @since 1.1
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ContestDTO)) {
            return false;
        }

        return this.contestId == ((ContestDTO) obj).getContestId();
    }
}
