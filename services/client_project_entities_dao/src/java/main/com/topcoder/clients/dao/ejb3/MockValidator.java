/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.ejb3;

import com.topcoder.util.datavalidator.AbstractObjectValidator;


/**
 * <p>
 * This is the Mock for test.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MockValidator extends AbstractObjectValidator {
    /**
     * <p>
     * Default Constructor.
     * </p>
     */
    public MockValidator() {
    }

    /**
     * <p>
     * Always returns null, since this validator considers any object to be valid.
     * </p>
     *
     * @return null always.
     * @param obj
     *            The object to validate (this parameter is ignored)
     */
    public String getMessage(Object obj) {
        return null;
    }

    /**
     * <p>
     * Always returns true, since this validator considers any object to be valid.
     * </p>
     *
     * @return True always.
     * @param obj
     *            The object to validate (this parameter is ignored)
     */
    public boolean valid(Object obj) {
        return true;
    }
}
