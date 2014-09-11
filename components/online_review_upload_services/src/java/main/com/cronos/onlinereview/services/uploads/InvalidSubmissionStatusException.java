/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.onlinereview.services.uploads;

/**
 * <p>
 * This exception is thrown when a submission status does not exist.
 * </p>
 *
 * <p>
 * Thread safety: This class is thread safe since it is immutable.
 * </p>
 *
 * @author fabrizyo, cyberjag
 * @version 1.0
 */
public class InvalidSubmissionStatusException extends UploadServicesException {

    /**
     * <p>
     * Represents the id of the submission status not valid. The valid values are all, it's not mutable,
     * defined in constructor and is returned by the getter method.
     * </p>
     */
    private final long submissionStatusId;

    /**
     * <p>
     * Constructs the exception with error message and <code>submissionStatusId</code>.
     * </p>
     *
     * @param message            the error message
     * @param submissionStatusId the id of submission status not valid
     */
    public InvalidSubmissionStatusException(String message, long submissionStatusId) {
        super(message);
        this.submissionStatusId = submissionStatusId;
    }

    /**
     * <p>
     * Constructs the exception with error message, inner cause and <code>submissionStatusId</code>.
     * </p>
     *
     * @param message            the error message
     * @param cause              the cause of this exception
     * @param submissionStatusId the id of submission status not valid
     */
    public InvalidSubmissionStatusException(String message, Throwable cause, long submissionStatusId) {
        super(message, cause);
        this.submissionStatusId = submissionStatusId;
    }

    /**
     * <p>
     * Return the invalid submission status id. The <code>submissionStatusId</code> is the unique id which
     * represents the <code>SubmissitionStatus</code>.
     * </p>
     *
     * @return the invalid submission status id
     */
    public long getSubmissionStatusId() {
        return submissionStatusId;
    }
}
