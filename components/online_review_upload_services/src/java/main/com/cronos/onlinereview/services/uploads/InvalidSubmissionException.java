/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.onlinereview.services.uploads;

/**
 * <p>
 * This exception is thrown when a submission does not exist.
 * </p>
 *
 * <p>
 * Thread safety: This class is thread safe since it is immutable.
 * </p>
 *
 * @author fabrizyo, cyberjag
 * @version 1.0
 */
public class InvalidSubmissionException extends UploadServicesException {

    /**
     * <p>
     * Represents the id of the submission not valid. The valid values are all, it's not mutable, defined in
     * constructor and is returned by the getter method.
     * </p>
     */
    private final long submissionId;

    /**
     * <p>
     * Constructs the exception with error message and <code>submissionId</code>.
     * </p>
     *
     * @param message      the error message
     * @param submissionId the id of invalid submission
     */
    public InvalidSubmissionException(String message, long submissionId) {
        super(message);
        this.submissionId = submissionId;
    }

    /**
     * <p>
     * Constructs the exception with error message, inner cause and <code>submissionId</code>.
     * </p>
     *
     * @param message      the error message
     * @param cause        the cause of this exception
     * @param submissionId the id of invalid submission
     */
    public InvalidSubmissionException(String message, Throwable cause, long submissionId) {
        super(message, cause);
        this.submissionId = submissionId;
    }

    /**
     * <p>
     * Return the invalid submission id. The <code>submissionId</code> is the unique id which represents the
     * submission.
     * </p>
     *
     * @return the invalid submission id
     */
    public long getSubmissionId() {
        return submissionId;
    }
}
