/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.onlinereview.services.uploads;

/**
 * <p>
 * This is thrown when the project phase is invalid.
 * </p>
 *
 * <p>
 * Note: The Project phase is a <code>Project</code> class but in phase package, different from
 * <code>Project</code> class in project package.
 * </p>
 *
 * <p>
 * Thread safety: This class is thread safe since it is immutable.
 * </p>
 *
 * @author fabrizyo, cyberjag
 * @version 1.0
 */
public class InvalidProjectPhaseException extends UploadServicesException {

    /**
     * <p>
     * Represents the id of the project phase not valid. The valid values are all, it's not mutable, defined
     * in constructor and is returned by the getter method.
     * </p>
     */
    private final long projectPhaseId;

    /**
     * <p>
     * Constructs the exception with error message and <code>projectPhaseId</code>.
     * </p>
     *
     * @param message        the error message
     * @param projectPhaseId the id of invalid project
     */
    public InvalidProjectPhaseException(String message, long projectPhaseId) {
        super(message);
        this.projectPhaseId = projectPhaseId;
    }

    /**
     * <p>
     * Constructs the exception with error message, inner cause and <code>projectPhaseId</code>.
     * </p>
     *
     * @param message        the error message
     * @param cause          the cause of this exception
     * @param projectPhaseId the id of invalid project
     */
    public InvalidProjectPhaseException(String message, Throwable cause, long projectPhaseId) {
        super(message, cause);
        this.projectPhaseId = projectPhaseId;
    }

    /**
     * <p>
     * Return the invalid project phase id. The <code>projectPhaseId</code> is the unique id which
     * represents the <code>Project</code> which contains the <code>Phases</code>.
     * </p>
     *
     * @return the project id
     */
    public long getProjectPhaseId() {
        return projectPhaseId;
    }
}
