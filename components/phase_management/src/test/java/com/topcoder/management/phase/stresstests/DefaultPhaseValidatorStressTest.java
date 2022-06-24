/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.stresstests;

import com.topcoder.management.phase.validation.DefaultPhaseValidator;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Stress tests for DefaultPhaseValidator.
 * </p>
 *
 * @author still
 * @version 1.0
 */
public class DefaultPhaseValidatorStressTest extends TestCase {
    /** The number of times each method will be run. */
    public static final int RUN_TIMES = 100000;
    /** The DefaultPhaseValidator used in this test. */
    private DefaultPhaseValidator validator;

    /**
     * Test suite of DefaultPhaseValidatorStressTest.
     *
     * @return Test suite of DefaultPhaseValidatorStressTest.
     */
    public static Test suite() {
        return new TestSuite(DefaultPhaseValidatorStressTest.class);
    }

    /**
     * Initialization for all tests here.
     * @throws Exception to Junit.
     */
    protected void setUp() throws Exception {
        validator = new DefaultPhaseValidator();
    }

    /**
     * <p>Stress test for DefaultPhaseValidator#DefaultPhaseValidator().</p>
     *
     */
    public void testCtor() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < RUN_TIMES; i++) {
            assertNotNull("Failed to create DefaultPhaseValidator.",
                    new DefaultPhaseValidator());
        }
        long end = System.currentTimeMillis();
        System.out.println("Testing DefaultPhaseValidator() for " + RUN_TIMES + " times costs "
                + (end - start) + "ms");
    }
    /**
     * <p>Stress test for DefaultPhaseValidator#validate(Phase).</p>
     *
     * @throws Exception to junit.
     */
    public void testValidate() throws Exception {
        long start = System.currentTimeMillis();
        Phase phase = new StressMockPhase();
        phase.setPhaseStatus(PhaseStatus.SCHEDULED);
        phase.setPhaseType(new PhaseType(101, "Type1"));
        for (int i = 0; i < RUN_TIMES; i++) {
            validator.validate(phase);
        }
        long end = System.currentTimeMillis();
        System.out.println("Testing validate(Phase) for " + RUN_TIMES + " times costs "
                + (end - start) + "ms");
    }
}
