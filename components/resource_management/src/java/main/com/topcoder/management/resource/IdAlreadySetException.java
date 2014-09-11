/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

/**
 * <p>
 * The IdAlreadySetException is used to signal that the id of one of the
 * resource modeling classes has already been set. This is used to prevent the
 * id being changed once it has been set.
 * </p>
 *
 * <p>
 * This exception is initially thrown in the 3 setId methods of the resource
 * modeling classes.
 * </p>
 *
 * @author aubergineanode, kinfkong
 * @version 1.0
 *
 */
public class IdAlreadySetException extends IllegalStateException {

    /**
     * <p>
     * Creates a new IdAlreadySetException.
     * </p>
     *
     * @param message Explanation of error of the IdAlreadySetException
     */
    public IdAlreadySetException(String message) {
        super(message);
    }
}
