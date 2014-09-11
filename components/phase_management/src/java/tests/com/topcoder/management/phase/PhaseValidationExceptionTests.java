/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase;

import junit.framework.TestCase;

/**
 * Tests the PhaseValidationException class.
 *
 * @author RachaelLCook
 * @version 1.0
 */

public class PhaseValidationExceptionTests extends TestCase {
    /**
     * Tests that the message is set correctly.
     */
    public void testMessage() {
        assertEquals("invalid return from getMessage()",
                     new PhaseValidationException("message").getMessage(), "message");
    }
}
