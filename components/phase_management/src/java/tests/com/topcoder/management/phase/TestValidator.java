/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase;

import com.topcoder.project.phases.Phase;

/**
 * A simple validator that does nothing (i.e. returns valid for all phases.
 *
 * @author RachaelLCook
 * @version 1.0
 */

public class TestValidator implements PhaseValidator {
    /**
     * Does nothing.
     *
     * @param phase the phase to validate
     */
    public void validate(Phase phase) { }
}
