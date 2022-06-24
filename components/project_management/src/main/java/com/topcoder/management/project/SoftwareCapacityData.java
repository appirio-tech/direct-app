/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;

/**
 * <p>
 * Simple DTO class for capacity retrieval
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author pulky
 * @version 1.0 (Cockpit Pipeline Release Assembly 2 - Capacity)
 * @since 1.0
 */
public class SoftwareCapacityData implements Serializable {

    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = 600295947647235401L;

    /**
     * Represents the date
     */
    private Date date;

    /**
     * Represents the number of scheduled contests
     */
    private int numScheduledContests;

    /**
     * Represents contest ids
     */
    private Set contests = new HashSet();

    /**
     * Default constructor.
     */
    public SoftwareCapacityData() {
    }

    /**
     * Constructor using fields
     *
     * @param date the date to set
     * @param numScheduledContests the number of scheduled contests to set
     */
    public SoftwareCapacityData(Date date, int numScheduledContests) {
        super();
        this.date = date;
        this.numScheduledContests = numScheduledContests;
    }

    /**
     * Returns the date.
     *
     * @return the date.
     */
    public Date getDate() {
        return date;
    }

     /**
     * Updates the date with the specified value.
     *
     * @param date the date to set.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Returns the number of scheduled contests.
     *
     * @return the number of scheduled contests.
     */
    public int getNumScheduledContests() {
        return numScheduledContests;
    }

     /**
     * Updates the number of scheduled contests with the specified value.
     *
     * @param numScheduledContests the number of scheduled contests to set.
     */
    public void setNumScheduledContests(int numScheduledContests) {
        this.numScheduledContests = numScheduledContests;
    }

    /**
     * Returns contest ids.
     *
     * @return contest ids.
     */
    public Set getContests() {
        return contests;
    }

    /**
     * set contests
     *
     * @param contests the contests to set.
     */
    public void setContests(Set contests) {
        if (contests != null)
        {
            this.contests = contests;
        }
    }
}
