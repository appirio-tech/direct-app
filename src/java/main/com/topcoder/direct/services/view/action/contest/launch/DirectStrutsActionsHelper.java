/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;

import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.security.TCSubject;

/**
 * <p>
 * Helper class for the component. It provides useful common methods for all the classes in this component.
 * </p>
 * <p>
 * <b>Thread Safety: </b> This class has no state, and thus it is thread safe.
 * </p>
 *
 * <p>As of version 1.1 the class is declared public.</p>
 *
 * @author FireIce, isv
 * @version 1.1
 */
public final class DirectStrutsActionsHelper {
    /**
     * <p>
     * Prevents to create a new instance.
     * </p>
     */
    private DirectStrutsActionsHelper() {
        // empty
    }

    /**
     * <p>
     * Validates the value of a variable. The value can not be <code>null</code>.
     * </p>
     *
     * @param value
     *            the value of the variable to be validated.
     * @param name
     *            the name of the variable to be validated.
     * @throws IllegalArgumentException
     *             if the value of the variable is <code>null</code>.
     */
    public static void checkNull(Object value, String name) {
        if (value == null) {
            throw new IllegalArgumentException(MessageFormat.format("The [{0}] argument should not be null.", name));
        }
    }

    /**
     * <p>
     * Gets the TCSubject instance from session.
     * </p>
     *
     * @return the TCSubject instance from session
     */
    public static TCSubject getTCSubjectFromSession() {
        HttpServletRequest request = DirectUtils.getServletRequest();
        if(request == null) {
            return null;
        }
        return new SessionData(request.getSession()).getCurrentUser();
    }
}
