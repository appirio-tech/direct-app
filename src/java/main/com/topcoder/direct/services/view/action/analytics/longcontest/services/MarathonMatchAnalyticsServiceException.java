/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.analytics.longcontest.services;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception is thrown while error occurs when process marathonMatchAnalyticsService.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> Exceptions are not thread safe and they are
 * not expected to be used concurrently.
 * </p>
 *
 * @author Ghost_141
 * @version 1.0 (PoC Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress)
 * @since 1.0
 */
public class MarathonMatchAnalyticsServiceException extends BaseException {

    /**
     * Constructor with error message.
     *
     * @param message
     *            error message
     */
    public MarathonMatchAnalyticsServiceException(String message) {
        super(message);
    }

    /**
     * Constructor with error message and cause.
     *
     * @param message
     *            error message
     * @param cause
     *            exception cause
     */
    public MarathonMatchAnalyticsServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
