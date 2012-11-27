/*
 * Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.groups;

/**
 * <p>
 * SecurityGroupsActionConfigurationException is thrown by the action methods if any configuration error is caught.
 * </p>
 * <p>
 * <strong>Thread Safety: </strong>
 * This class is not thread safe because its base class is not thread safe.
 * </p>
 * <p>
 * Version 1.1 (TopCoder Security Groups Frontend - Create Update Group) change notes:
 * <ol>
 *   <li>Integrated this class into Direct.</li>
 * </ol>
 * </p>
 * @author woodjhon, hanshuai, flexme
 * @version 1.1
 */
@SuppressWarnings("serial")
public class SecurityGroupsActionConfigurationException extends RuntimeException {
    /**
     * <p>
     * Create the exception, call corresponding parent constructor.
     * </p>
     * @param message The error message to set.
     */
    public SecurityGroupsActionConfigurationException(String message) {
        super(message);
    }

    /**
     * <p>
     * Create the exception, call corresponding parent constructor.
     * </p>
     * @param message The error message to set.
     * @param cause The cause error to set.
     */
    public SecurityGroupsActionConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
