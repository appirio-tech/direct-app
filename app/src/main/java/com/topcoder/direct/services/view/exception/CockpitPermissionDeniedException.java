/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.exception;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception is thrown by any action which wants to indicate a permission denied error.
 * </p>
 *
 * @author GreatKevin
 * @version 1.0 (Module Assembly - TopCoder Cockpit Project Planner)
 */
public class CockpitPermissionDeniedException extends BaseException {

    /**
     * Creates a new CockpitPermissionDeniedException
     *
     * @param message the error message.
     */
    public CockpitPermissionDeniedException(String message) {
        super(message);
    }

}
