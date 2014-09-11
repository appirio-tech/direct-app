/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application;

import com.topcoder.util.datavalidator.AbstractObjectValidator;

/**
 * <p>
 * A mock implementation of ObjectValidator. Used for testing.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MockValidator extends AbstractObjectValidator {
    /**
     * <p>
     * Creates an instance of MockValidator.
     * </p>
     */
    public MockValidator() {
        // Empty
    }

    /**
     * <p>
     * Always returns null, since this validator considers any object to be valid.
     * </p>
     *
     * @param obj
     *            The object to validate (this parameter is ignored)
     *
     * @return <code>null</code> always.
     */
    public String getMessage(Object obj) {
        return null;
    }

    /**
     * <p>
     * Always returns true, since this validator considers any object to be valid.
     * </p>
     *
     * @param obj
     *            The object to validate (this parameter is ignored)
     *
     * @return <code>true</code> always.
     */
    public boolean valid(Object obj) {
        return true;
    }
}
