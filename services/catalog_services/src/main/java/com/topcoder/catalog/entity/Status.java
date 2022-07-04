/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity;

/**
 * <p>This class represents an enumeration of the possible status of a <code>Component</code>,
 * a <code>Category</code>, a <code>Technology</code> or a <code>CompVersionDates</code>.</p>
 * <p>Each status has a different status id.</p>
 * <p><strong>Thread safety: </strong></p> <p>This class is immutable and thread safe.</p>
 *
 * @author caru, Retunsky
 * @version 1.0
 */
public enum Status {
    /**
     * <p>No active status.</p>
     */
    DELETED(0),
    /**
     * <p>Represents the status of an active item.</p>
     */
    ACTIVE(1),
    /**
     * <p>Represents the status of a requested item.</p>
     */
    REQUESTED(101),
    /**
     * <p>Represents the status of an approved item.</p>
     */
    APPROVED(102),
    /**
     * <p>Represents the status of a duplicate item.</p>
     */
    DUPLICATE(103),
    /**
     * <p>Represents the status of a declined item.</p>
     */
    DECLINED(104),
    /**
     * <p>Represents the status of an item pending for activation.</p>
     */
    PENDING_ACTIVATION(201),
    /**
     * <p>Represents the status of a new post.</p>
     */
    NEW_POST(301),
    /**
     * <p>Represents the status of a repost.</p>
     */
    REPOST(302),
    /**
     * <p>Represents the status of a tournament.</p>
     */
    TOURNAMENT(303);
    /**
     * <p>Represents the id of the status. It's initialized in the constructor and being never changed.</p>
     * <p>It's accessed by getter.</p>
     */
    private final int statusId;

    /**
     * <p>Instantiates an enum item with <code>statusId</code>.</p>
     * @param statusId the status id of the <code>Status</code> instance.
     */
    private Status(int statusId) {
        this.statusId = statusId;
    }

    /**
     * <p>Retrieves the id of the status.</p>
     *
     * @return {@link #statusId} property's value.
     */
    public int getStatusId() {
        return statusId;
    }

    /**
     * <p>Returns Status instance by given <code>statusId</code>.</p>
     * @param statusId status id by which a Status is being retrieved
     * @return Status instance with statusId equal to the given one, or null if there is no one
     */
    public static Status valueOf(int statusId) {
        for (Status status : values()) {
            if (status.getStatusId() == statusId) {
                return status;
            }
        }
        return null;
    }
}

