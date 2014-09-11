/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import javax.security.auth.callback.NameCallback;

/**
 * <p>
 * Mock implementation of <code>NameCallback</code> used in Unit tests.
 * </p>
 *
 * <p>
 * It will cause <code>IOException</code> when handled by <code>MockCallBackHandler</code>.
 * </p>
 *
 * <p>
 * Used in UnitTests.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FailedNameCallback extends NameCallback {

    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    private static final long serialVersionUID = -2857326501433947909L;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public FailedNameCallback() {
        super("username");
    }
}
