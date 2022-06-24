/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review;

/**
 * This exception will be thrown by ReviewInformixPersistence if application
 * tries to create a duplicated review entity.
 * @author woodjhon, urtks
 * @version 1.0
 */
public class DuplicateReviewEntityException extends ReviewPersistenceException {

    /**
     * Represents the duplicate entity id. It's set in the constructor. Default
     * value is -1. the id can be any long value.
     */
    private long id = -1;

    /**
     * Create the instance with given error message and dulicpate entity id.
     * @param msg
     *            error message
     * @param id
     *            the duplicate entity id
     */
    public DuplicateReviewEntityException(String msg, long id) {
        super(msg);
        this.id = id;
    }

    /**
     * Get the duplicate entity id.
     * @return the duplicate entity id
     */
    public long getId() {
        return id;
    }
}
