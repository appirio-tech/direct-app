/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases;

import com.topcoder.util.errorhandling.BaseRuntimeException;

/**
 * This exception will be thrown if cyclic dependency is detected in the project. It may be thrown when calculate the
 * start/end time of the project or phase, and remove phase from project.
 *
 * @author oldbig, littlebull
 * @version 2.0
 */
public class CyclicDependencyException extends BaseRuntimeException {
    /**
     * Create a new instance <code>CyclicDependencyException</code> with the given error message.
     * <p>
     * <b>Note:</b> There are a lot of discussion whether should check the message parameter of exception class in
     * TCS forum. This method will not check the message parameter.
     * </p>
     *
     * @param msg
     *            the error message of the exception
     */
    public CyclicDependencyException(String msg) {
        super(msg);
    }
}
