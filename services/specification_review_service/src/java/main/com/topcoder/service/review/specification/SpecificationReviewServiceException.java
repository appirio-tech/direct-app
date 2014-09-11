/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.specification;

import javax.ejb.ApplicationException;

import com.topcoder.util.errorhandling.BaseCriticalException;

/**
 * <p>
 * This exception is thrown by implementations of <code>SpecificationReviewService</code>
 * when some error occurs in the business methods.
 * </p>
 * <p>
 * Thread Safety: This class is not thread safe because its base class is not thread safe.
 * </p>
 *
 * @author saarixx, myxgyy
 * @version 1.0
 */
@ApplicationException(rollback = true)
public class SpecificationReviewServiceException extends BaseCriticalException {
    /**
     * The generated serial version UID.
     */
    private static final long serialVersionUID = -8028469795740439544L;

    /**
     * Creates a new instance of this exception.
     */
    public SpecificationReviewServiceException() {
    }

    /**
     * Creates a new instance of this exception with the given message.
     *
     * @param message
     *            the detailed error message of this exception.
     */
    public SpecificationReviewServiceException(String message) {
        super(message);
    }

    /**
     * Creates a new instance of this exception with the given cause.
     *
     * @param cause
     *            the inner cause of this exception.
     */
    public SpecificationReviewServiceException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates a new instance of this exception with the given message and cause.
     *
     * @param message
     *            the detailed error message of this exception.
     * @param cause
     *            the inner cause of this exception.
     */
    public SpecificationReviewServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
