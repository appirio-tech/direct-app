/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase;

import com.topcoder.project.phases.Phase;

/**
 * <p>This is a validation contract for phase objects. Implementations will apply some validation criteria and will
 * signal validation issues by throwing <code>PhaseValidationException</code>.</p>
 *
 * <p>Implementations should strive to ensure thread safety. A validator should act as a stateless utility.</p>
 *
 * @author AleaActaEst, RachaelLCook
 * @version 1.0
 */

public interface PhaseValidator {
    /**
     * Validates the specified phase. Implementations will typically test if the phase data is consistent with some
     * specific rules (for example when certain fields can not be empty). A non-exceptional return indicates that
     * the phase is valid.
     *
     * @throws PhaseValidationException if validation fails
     * @throws IllegalArgumentException if <code>phase</code> is <code>null</code>
     * @param phase phase to validate
     */
    public void validate(Phase phase) throws PhaseValidationException;
}


