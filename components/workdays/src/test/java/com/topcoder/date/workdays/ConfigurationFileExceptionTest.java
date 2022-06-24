/*
 * Copyright (c) 2004, TopCoder, Inc. All rights reserved
 */
package com.topcoder.date.workdays;

import junit.framework.TestCase;


/**
 * This class is used to check the behavior of the <code>ConfigurationFileException</code> class, including tests of
 * Constructing a ConfigurationFileException instance.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ConfigurationFileExceptionTest extends TestCase {
    /**
     * construct a ConstructConfiguration instance normally, and see it's behavior.
     */
    public void testConstructConfigurationFileExceptionNormal() {
        String message = "message";
        String exceptionMessage = "just a test null pointer exception";
        ConfigurationFileException cfe = new ConfigurationFileException(message,
                new NullPointerException(exceptionMessage));
        assertEquals("Test message of ConfigurationFileException", message + ", caused by " + exceptionMessage,
            cfe.getLocalizedMessage());
        assertEquals("Test cause exception of ConfigurationFileException", NullPointerException.class,
            cfe.getCause().getClass());
    }

    /**
     * construct a ConstructConfiguration instance without cause exception, and see it's behavior.
     */
    public void testConstructConfigurationFileExceptionWithNullArgument() {
        ConfigurationFileException cfe = new ConfigurationFileException("message", null);
        assertEquals("Test cause exception of ConfigurationFileException", null, cfe.getCause());
    }
}
