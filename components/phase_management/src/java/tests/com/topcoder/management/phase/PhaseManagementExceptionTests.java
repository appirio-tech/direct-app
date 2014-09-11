/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase;

import junit.framework.TestCase;

/**
 * Tests the PhaseManagementException class.
 *
 * @author RachaelLCook
 * @version 1.0
 */

public class PhaseManagementExceptionTests extends TestCase {
    /**
     * Tests that the message and wrapped exception are set correctly by the constructor.
     */
    public void testMessage() {
        assertEquals("invalid return from getMessage()",
                     new PhaseManagementException("message").getMessage(), "message");

        RuntimeException rex = new RuntimeException("rte");
        PhaseManagementException ex = new PhaseManagementException("message2", rex);

        assertEquals("invalid return from getCause()", ex.getCause(), rex);
    }
}
