/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.validation;

import com.topcoder.management.phase.PhaseValidationException;
import com.topcoder.management.phase.PhaseValidator;

import com.topcoder.project.phases.Phase;

/**
 * <p>A simple validator for phases that ensures that all the required fields are actually present. This is a
 * sanity check validation, which ensures that all the required fields have been initialized with some values.  The
 * fields correspond to the non-Nullable fields for the phase table in the database.</p>
 *
 * <p>DefaultPhaseValidation is a thread safe utility class with no state.</p>
 *
 * @author AleaActaEst, RachaelLCook
 * @version 1.0
 */
public class DefaultPhaseValidator implements PhaseValidator {

    /**
     * Default constructor.
     */
    public  DefaultPhaseValidator() {
    }

    /**
     * Validates the specified phase. The entails checking to ensure that the following fields are
     * non-<code>null</code>.
     *
     * <ul>
     *   <li>{@link Phase#getProject Project}</li>
     *   <li>{@link Phase#getPhaseType PhaseType}</li>
     *   <li>{@link Phase#getPhaseStatus PhaseStatus}</li>
     *   <li>{@link Phase#getScheduledStartDate ScheduledStartDate}<li>
     *   <li>{@link Phase#getScheduledEndDate ScheduledEndDate}<li>
     * </ul>
     *
     * @throws PhaseValidationException if any of the above fields are <code>null</code>
     * @throws IllegalArgumentException if <code>phase</code> is <code>null</code>
     *
     * @param phase the phase to validate
     */
    public void validate(Phase phase) throws PhaseValidationException {
        if (phase == null) {
            throw new IllegalArgumentException("argument to DefaultPhaseValidator#validate must be non-null");
        }

        if (phase.getProject() == null) {
            throw new PhaseValidationException("phase has null project");
        }

        if (phase.getPhaseType() == null) {
            throw new PhaseValidationException("phase has null phase type");
        }

        if (phase.getPhaseStatus() == null) {
            throw new PhaseValidationException("phase has null phase status");
        }

        if (phase.getScheduledEndDate() == null) {
            throw new PhaseValidationException("phase has null scheduled end date");
        }

        if (phase.getScheduledStartDate() == null) {
            throw new PhaseValidationException("phase has null scheduled start date");
        }
    }
}
