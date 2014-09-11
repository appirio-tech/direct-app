/*
 * Copyright (c) 2004, TopCoder, Inc. All rights reserved
 */
package com.topcoder.date.workdays;

/**
 * <p>
 * This is the interface that every Workdays factory should implement.
 * An instance of this interface creates a Workdays instance.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public interface WorkdaysFactory {
    /**
     * <p>
     * Creates an instance of Workdays.
     * </p>
     *
     * @return a Workdays instance
     */
    Workdays createWorkdaysInstance();
}
