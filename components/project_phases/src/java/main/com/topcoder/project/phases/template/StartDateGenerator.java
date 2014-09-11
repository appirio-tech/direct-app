/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template;

import java.util.Date;

/**
 * <p>
 * <code>StartDateGenerator</code> interface defines the contract to generate a default start date for a
 * project according to a specific generation logic, so that the <code>{@link DefaultPhaseTemplate}</code> can
 * employ different start date generation logic without code changes.
 * </p>
 * <p>
 * It will be used in the <code>{@link DefaultPhaseTemplate}</code> as the default project start date
 * generation logic if no start date specified in the phase generation process.
 * </p>
 * <p>
 * If a concrete implementation need configuration, it must define a constructor with a string namespace
 * argument, otherwise a default constructor without any argument must be provided.
 * <code>{@link DefaultPhaseTemplate}</code> will try these two constructors.
 * </p>
 * <p>
 * Implementations of this interface should be thread safe.
 * </p>
 *
 * @author albertwang, TCSDEVELOPER
 * @author flying2hk, TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public interface StartDateGenerator {
    /**
     * <p>
     * Generate a start date according to a specific generation logic.
     * </p>
     * @return the generated start date
     * @throws StartDateGenerationException
     *             if any error occurs so that the
     *             start date can not be generated(not used in this initial
     *             version, just for future extension)
     */
    public Date generateStartDate() throws StartDateGenerationException;
}
