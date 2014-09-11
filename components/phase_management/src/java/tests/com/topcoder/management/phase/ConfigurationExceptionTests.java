/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase;

import junit.framework.TestCase;

/**
 * Tests the ConfigurationException class.
 *
 * @author RachaelLCook
 * @version 1.0
 */

public class ConfigurationExceptionTests extends TestCase {
    /**
     * Tests that the message and wrapped exception are set correctly.
     */
    public void testMessage() {
        assertEquals("invalid return from getMessage()",
                     new ConfigurationException("message").getMessage(), "message");

        RuntimeException rex = new RuntimeException("rte");
        ConfigurationException ex = new ConfigurationException("message2", rex);

        assertEquals("invalid return from getCause()", ex.getCause(), rex);
    }
}
