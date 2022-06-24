/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase;

import junit.framework.TestCase;

/**
 * Tests the PhasePersistenceException class.
 *
 * @author RachaelLCook
 * @version 1.0
 */

public class PhasePersistenceExceptionTests extends TestCase {
    /**
     * Tests that the mssage was set correctly.
     */
    public void testMessage() {
        assertEquals("invalid return from getMessage()",
                     new PhasePersistenceException("message").getMessage(), "message");
    }
}
