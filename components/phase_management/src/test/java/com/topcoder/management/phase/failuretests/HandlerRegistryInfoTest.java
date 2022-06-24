/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 */
package com.topcoder.management.phase.failuretests;

import com.topcoder.management.phase.HandlerRegistryInfo;
import com.topcoder.management.phase.PhaseOperationEnum;
import com.topcoder.project.phases.PhaseType;

/**
 * Failure tests for <code>HandlerRegistryInfo</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class HandlerRegistryInfoTest extends FailureTestBase {

    /**
     * The phase type used in test.
     */
    private static final PhaseType PHASE_TYPE = new PhaseType(1, "test");

    /**
     * Test method for HandlerRegistryInfo(PhaseType, PhaseOperationEnum).
     * Type is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testHandlerRegistryInfoNullType() {
        try {
            new HandlerRegistryInfo(null, PhaseOperationEnum.START);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for HandlerRegistryInfo(PhaseType, PhaseOperationEnum).
     * Operation is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testHandlerRegistryInfoNullOP() {
        try {
            new HandlerRegistryInfo(PHASE_TYPE, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

}
