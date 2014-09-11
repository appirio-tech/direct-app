package com.topcoder.management.phase.failuretests;

import com.topcoder.management.phase.validation.DefaultPhaseValidator;

/**
 * Failure tests for <code>DefaultPhaseValidator</code>.
 *
 * @author assistant
 * @version 1.0
 *
 */
public class DefaultPhaseValidatorTest extends FailureTestBase {

    /**
     * Test method for validate(com.topcoder.project.phases.Phase).
     * The phase is null.
     * Expected : {@link IllegalArgumentException}
     * @throws Exception to JUnit
     */
    public void testValidate() throws Exception {
        DefaultPhaseValidator validator = new DefaultPhaseValidator();
        try {
            validator.validate(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

}
