/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.onlinereview.services.uploads;

/**
 * <p>
 * This is thrown when a user does not exist or when some error occurs related to the user (for example:
 * she/he is not winner submitter)
 * </p>
 *
 * <p>
 * Thread safety: This class is thread safe since it is immutable.
 * </p>
 *
 * @author fabrizyo, cyberjag
 * @version 1.0
 */
public class InvalidUserException extends UploadServicesException {

    /**
     * <p>
     * Represents the id of the user not valid. The valid values are all, it's not mutable, defined in
     * constructor and is returned by the getter method.
     * </p>
     */
    private final long userId;

    /**
     * <p>
     * Constructs the exception with error message and <code>userId</code>.
     * </p>
     *
     * @param message the error message
     * @param userId  the user id
     */
    public InvalidUserException(String message, long userId) {
        super(message);
        this.userId = userId;
    }

    /**
     * <p>
     * Constructs the exception with error message, inner cause and <code>userId</code>.
     * </p>
     *
     * @param message the error message
     * @param cause   the cause of this exception
     * @param userId  the user id
     */
    public InvalidUserException(String message, Throwable cause, long userId) {
        super(message, cause);
        this.userId = userId;
    }

    /**
     * <p>
     * Return the invalid user id. The <code>userId</code> is the unique id which represents the user.
     * </p>
     *
     * @return the user id
     */
    public long getUserId() {
        return userId;
    }
}
