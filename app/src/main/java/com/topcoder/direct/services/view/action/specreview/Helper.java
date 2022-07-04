/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.specreview;

import com.opensymphony.xwork2.Action;

/**
 * <p>
 * Helper which contains utility code used by this component.
 * </p>
 *
 * <p>
 * <b>Thread safety:</b> This class is thread safe by being immutable.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
final class Helper {

    /**
     * Private constructor to prevent class instantiation.
     */
    private Helper() {
    }

    /**
     * Stores the exception in the action and returns <code>Action.ERROR</code>.
     *
     * @param action the action in which to store the exception
     * @param exception the exception to store in the action
     * @return always returns <code>Action.ERROR</code>
     */
    static String handleActionError(SpecificationReviewAction action,
        SpecificationReviewActionException exception) {

        // store exception in model and return Action.ERROR indicating action failed
        action.setException(exception);
        return Action.ERROR;
    }
}
