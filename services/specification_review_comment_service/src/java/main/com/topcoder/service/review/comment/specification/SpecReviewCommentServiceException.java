/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.comment.specification;

/**
 * <p>
 * This exception is thrown from the SpecReviewCommentService interface and its
 * implementations if any error occurs.
 * </p>
 * 
 * <strong>Thread-safety:</strong>
 * <p>
 * Implementation needs to be thread-safe.
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0
 */
public class SpecReviewCommentServiceException extends Exception {
    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = -3068148737074754049L;

    /**
     * Constructor with error message.
     * 
     * @param message
     *            - the error message.
     */
    public SpecReviewCommentServiceException(String message) {
        super(message);
    }

    /**
     * Constructor with error message and inner cause.
     * 
     * @param message
     *            - the error message.
     * @param cause
     *            - the inner cause.
     */
    public SpecReviewCommentServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
