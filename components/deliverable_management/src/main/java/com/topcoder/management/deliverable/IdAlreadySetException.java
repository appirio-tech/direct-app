/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

/**
 * <p>
 * The IdAlreadySetException is used to signal that the id of one of the modeling classes has
 * already been set. This is used to prevent the id being changed once it has been set.
 * </p>
 *
 * @author aubergineanode, singlewood
 * @version 1.0.2
 */
public class IdAlreadySetException extends IllegalStateException {
    /**
     * <p>
     * The serial version id.
     * </p>
     */
    private static final long serialVersionUID = 6496859705557700467L;

    /**
     * Creates a new IdAlreadySetException.
     *
     * @param message Explanation of error
     */
    public IdAlreadySetException(String message) {
        super(message);
    }
}
