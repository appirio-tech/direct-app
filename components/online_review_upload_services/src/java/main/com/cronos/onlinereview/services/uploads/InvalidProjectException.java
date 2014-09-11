/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.onlinereview.services.uploads;

/**
 * <p>
 * This exception is thrown when a project does not exist.
 * </p>
 *
 * <p>
 * Thread safety: This class is thread safe since it is immutable.
 * </p>
 *
 * @author fabrizyo, cyberjag
 * @version 1.0
 */
public class InvalidProjectException extends UploadServicesException {

    /**
     * <p>
     * Represents the project id of the project not valid. The valid values are all, it's not mutable, defined
     * in constructor and is returned by the getter method.
     * </p>
     */
    private final long projectId;

    /**
     * <p>
     * Constructs the exception with error message and <code>projectId</code>.
     * </p>
     *
     * @param message   the error message
     * @param projectId the id of invalid project
     */
    public InvalidProjectException(String message, long projectId) {
        super(message);
        this.projectId = projectId;
    }

    /**
     * <p>
     * Constructs the exception with error message, inner cause and <code>projectId</code>.
     * </p>
     *
     * @param message   the error message
     * @param cause     the cause of this exception
     * @param projectId the id of invalid project
     */
    public InvalidProjectException(String message, Throwable cause, long projectId) {
        super(message, cause);
        this.projectId = projectId;
    }

    /**
     * <p>
     * Return the invalid project id. The <code>projectId</code> is the unique id which represents the
     * <code>Project</code>.
     * </p>
     *
     * @return the project id
     */
    public long getProjectId() {
        return projectId;
    }
}
