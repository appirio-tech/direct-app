/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import javax.security.auth.callback.PasswordCallback;

/**
 * <p>
 * Mock implementation of <code>PasswordCallback</code> used in Unit tests.
 * </p>
 *
 * <p>
 * It will cause <code>UnsupportedCallbackException</code> when handled by <code>MockCallBackHandler</code>.
 * </p>
 *
 * <p>
 * Used in UnitTests.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FailedPasswordCallback extends PasswordCallback {

    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    static final long serialVersionUID = -7945448556198927734L;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public FailedPasswordCallback() {
        super("password", true);
    }
}
