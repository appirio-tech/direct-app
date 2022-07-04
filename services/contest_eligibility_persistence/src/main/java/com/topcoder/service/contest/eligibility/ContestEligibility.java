/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibility;

import java.io.Serializable;

/**
 * <p>
 * Represents the bass entity class for contest eligibility.
 * </p>
 * <p>
 * <strong>Thread Safety</strong>: This class is not thread safe since it is mutable.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public abstract class ContestEligibility implements Serializable {

    /**
     * <p>
     * The serial version UID.
     * </p>
     */
    private static final long serialVersionUID = -7716443412438952646L;

    /**
     * <p>
     * Represents the id of the entity.
     * </p>
     */
    private long id;

    /**
     * <p>
     * Represents the id of the contest.
     * </p>
     */
    private long contestId;

    /**
     * <p>
     * Represents the flag to indicate whether it is used for studio.
     * </p>
     */
    private boolean studio;

    /**
     * <p>
     * It is a non-persistent flag to indicate a 'to be deleted' eligibility.
     * </p>
     */
    private boolean deleted;

    /**
     * Default constructor.
     */
    public ContestEligibility() {
        // empty.
    }

    /**
     * Returns the id.
     *
     * @return the id.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id with the specified value.
     *
     * @param id
     *            the id to set.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns the contestId.
     *
     * @return the contestId.
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * Sets the contestId with the specified value.
     *
     * @param contestId
     *            the contestId to set.
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * Returns the studio.
     *
     * @return the studio.
     */
    public boolean isStudio() {
        return studio;
    }

    /**
     * Sets the studio flag with the specified value.
     *
     * @param studio
     *            the studio flag to set.
     */
    public void setStudio(boolean studio) {
        this.studio = studio;
    }

    /**
     * Returns the deleted.
     *
     * @return the deleted.
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * Sets the delete flag with the specified value.
     *
     * @param deleted
     *            the delete flag to set.
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
