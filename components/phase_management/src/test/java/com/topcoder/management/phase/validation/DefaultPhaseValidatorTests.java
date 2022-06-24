/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.validation;

import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;

import com.topcoder.management.phase.PhaseManagementTestCase;
import com.topcoder.management.phase.PhaseValidationException;

import java.util.Date;

/**
 * Test suite for DefaultPhaseValidator.
 *
 * @author RachaelLCook
 * @version 1.0
 */

public class DefaultPhaseValidatorTests extends PhaseManagementTestCase {
    /**
     * Tests invalid arguments to validate.
     *
     * @exception Exception if an unexpected exception occurs
     */
    public void testValidateArg() throws Exception {
        try {
            new DefaultPhaseValidator().validate(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests successful method invocation.
     *
     * @exception Exception if an unexpected exception occurs
     */
    public void testValidateSuccess() throws Exception {
        Phase p = new Phase(PROJECT, 1);

        p.setPhaseType(PHASE_TYPE_ONE);
        p.setPhaseStatus(PhaseStatus.OPEN);
        p.setScheduledEndDate(new Date());
        p.setScheduledStartDate(new Date());

        new DefaultPhaseValidator().validate(p);
    }

    /**
     * Tests validation of a phase missing a phase type.
     */
    public void testValidateMissingPhaseType() {
        Phase p = new Phase(PROJECT, 1);

        p.setPhaseStatus(PhaseStatus.OPEN);
        p.setScheduledEndDate(new Date());
        p.setScheduledStartDate(new Date());

        try {
            new DefaultPhaseValidator().validate(p);
            fail("should have thrown PhaseValidationException");
        } catch (PhaseValidationException ex) {
            // pass
        }
    }

    /**
     * Tests validation of a phase missing a phase status.
     */
    public void testValidateMissingPhaseStatus() {
        Phase p = new Phase(PROJECT, 1);

        p.setPhaseType(PHASE_TYPE_ONE);
        p.setScheduledEndDate(new Date());
        p.setScheduledStartDate(new Date());

        try {
            new DefaultPhaseValidator().validate(p);
            fail("should have thrown PhaseValidationException");
        } catch (PhaseValidationException ex) {
            // pass
        }
    }

    /**
     * Tests validation of a phase missing a scheduled end date.
     */
    public void testValidateMissingScheduledEndDate() {
        Phase p = new Phase(PROJECT, 1);

        p.setPhaseType(PHASE_TYPE_ONE);
        p.setPhaseStatus(PhaseStatus.OPEN);
        p.setScheduledStartDate(new Date());

        try {
            new DefaultPhaseValidator().validate(p);
            fail("should have thrown PhaseValidationException");
        } catch (PhaseValidationException ex) {
            // pass
        }
    }

    /**
     * Tests validation of a phase missing a scheduled start date.
     */
    public void testValidateMissingScheduledStartDate() {
        Phase p = new Phase(PROJECT, 1);

        p.setPhaseType(PHASE_TYPE_ONE);
        p.setPhaseStatus(PhaseStatus.OPEN);
        p.setScheduledEndDate(new Date());

        try {
            new DefaultPhaseValidator().validate(p);
            fail("should have thrown PhaseValidationException");
        } catch (PhaseValidationException ex) {
            // pass
        }
    }
}
