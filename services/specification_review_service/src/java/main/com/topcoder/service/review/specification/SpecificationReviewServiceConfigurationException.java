/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.specification;

import com.topcoder.util.errorhandling.BaseRuntimeException;

/**
 * <p>
 * This exception is thrown by <code>SpecificationReviewServiceBean</code> when error occurs while
 * initializing this bean.
 * </p>
 * <p>
 * Thread Safety: This class is not thread safe because its base class is not thread safe.
 * </p>
 *
 * @author saarixx, myxgyy
 * @version 1.0
 */
public class SpecificationReviewServiceConfigurationException extends BaseRuntimeException {
    /**
     * The generated serial version UID.
     */
    private static final long serialVersionUID = 6150065556913895925L;

    /**
     * Creates a new instance of this exception.
     */
    public SpecificationReviewServiceConfigurationException() {
    }

    /**
     * Creates a new instance of this exception with the given message.
     *
     * @param message
     *            the detailed error message of this exception.
     */
    public SpecificationReviewServiceConfigurationException(String message) {
        super(message);
    }

    /**
     * Creates a new instance of this exception with the given cause.
     *
     * @param cause
     *            the inner cause of this exception.
     */
    public SpecificationReviewServiceConfigurationException(Throwable cause) {
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
    public SpecificationReviewServiceConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
