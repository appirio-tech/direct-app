/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review;

/**
 * This exception will be thrown by ReviewInformixPersistence if application
 * tries to get or update a non-existing review entity
 * @author woodjhon, urtks
 * @version 1.0
 */
public class ReviewEntityNotFoundException extends ReviewPersistenceException {

    /**
     * Represents the entity id that is not found in the persistence. It's set
     * in the constructor. Default value is -1. the id can be any long value.
     */
    private long id = -1;

    /**
     * Create the instance with given error message and the id that is not found
     * in the persistence.
     * @param msg
     *            the error message
     * @param id
     *            the entity id that is not found in the persistence.
     */
    public ReviewEntityNotFoundException(String msg, long id) {
        super(msg);
        this.id = id;
    }

    /**
     * Get the entity id that is not found in the persistence.
     * @return the entity id that is not found in the persistence.
     */
    public long getId() {
        return id;
    }
}
