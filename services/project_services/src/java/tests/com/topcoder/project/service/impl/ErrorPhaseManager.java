/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.impl;

import com.topcoder.management.phase.PhaseManagementException;
import com.topcoder.project.phases.Project;

/**
 * <p>
 * <code>ErrorPhaseManager</code> extends <code>MockPhaseManager</code>, used for test.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class ErrorPhaseManager extends MockPhaseManager {
    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public ErrorPhaseManager() {
    }

    /**
     * <p>
     * Throws PhaseManagementException for test.
     * </p>
     *
     * @param project
     *            project for which to update phases
     * @param operator
     *            the operator performing the action
     * @throws IllegalArgumentException
     *             if either argument is <code>null</code> or the empty string
     * @throws PhaseManagementException
     *             if a phase fails validation, or if an error occurs while persisting the updates
     *             or generating the IDs
     */
    public void updatePhases(Project project, String operator) throws PhaseManagementException {
        throw new PhaseManagementException("For test.");
    }
}
