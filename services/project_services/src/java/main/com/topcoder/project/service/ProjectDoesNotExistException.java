/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service;

/**
 * <p>
 * This exception is thrown when the update method is called with a specified Project but this
 * project does not exist in persistence store.
 * </p>
 *
 * <p>
 * <strong>Thread Safety:</strong> This class is not thread safe since the parent is not thread safe,
 * but the thread safety is not necessary for the exceptions.
 * </p>
 *
 * @author argolite, moonli
 * @author fabrizyo, znyyddf
 * @version 1.1
 * @since 1.1
 */
@SuppressWarnings("serial")
public class ProjectDoesNotExistException extends ProjectServicesException {
    /**
     * <p>
     * Represents the project id of the non existent project. It is initialized in constructor and
     * cannot be modified after then. It can be accessed in the getter method. The value should
     * greater or equal than 0.
     * </p>
     */
    private final long projectId;

    /**
     * <p>
     * Creates a new exception instance with this error message and the project id.
     * </p>
     *
     * @param msg
     *            the error message
     * @param projectId
     *            the project's id
     */
    public ProjectDoesNotExistException(String msg, long projectId) {
        super(msg);
        this.projectId = projectId;
    }

    /**
     * <p>
     * Creates a new exception instance with this error message and cause of error and the project
     * id.
     * </p>
     *
     * @param msg
     *            the error message
     * @param cause
     *            cause of error
     * @param projectId
     *            the project's id
     */
    public ProjectDoesNotExistException(String msg, Throwable cause, long projectId) {
        super(msg, cause);
        this.projectId = projectId;
    }

    /**
     * <p>
     * Return the project id.
     * </p>
     *
     * @return the project id
     */
    public long getProjectId() {
        return this.projectId;
    }
}
