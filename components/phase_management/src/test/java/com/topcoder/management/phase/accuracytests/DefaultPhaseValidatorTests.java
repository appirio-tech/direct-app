/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.accuracytests;

import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.date.workdays.DefaultWorkdaysFactory;
import com.topcoder.management.phase.PhaseValidator;
import com.topcoder.management.phase.validation.DefaultPhaseValidator;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;

/**
 * <p>
 * Accuracy test fixture for DefaultPhaseValidator class.
 * </p>
 * @author Thinfox
 * @version 1.0
 */
public class DefaultPhaseValidatorTests extends TestCase {
    /**
     * The default DefaultPhaseValidator instance on which to perform tests.
     */
    private DefaultPhaseValidator validator = null;

    /**
     * Tests the empty constructor.
     */
    public void testCtor() {
        validator = new DefaultPhaseValidator();
        assertNotNull("Creation Failed.", validator);
        assertTrue("Should implement the PhaseValidator interface.", validator instanceof PhaseValidator);
    }

    /**
     * Tests successful method invocation.
     * @exception Exception if an unexpected exception occurs
     */
    public void testValidateSuccess() throws Exception {
        Project project = new Project(new Date(), new DefaultWorkdaysFactory().createWorkdaysInstance());
        project.setId(1);
        Phase phase = new Phase(project, 1);
        phase.setId(1);
        phase.setPhaseType(new PhaseType(1, "type1"));
        phase.setPhaseStatus(PhaseStatus.OPEN);
        phase.setScheduledEndDate(new Date());
        phase.setScheduledStartDate(new Date());

        validator = new DefaultPhaseValidator();
        validator.validate(phase);
    }
}