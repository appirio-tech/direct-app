/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests on class <code>ConfigurationException</code>.
 * </p>
 * @author flying2hk
 * @version 1.1
 * @since 1.0
 */
public class UnitTestConfigurationException extends TestCase {
    /**
     * <p>
     * Test constructor <code>ConfigurationException(String message)</code>
     * for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create ConfigurationException.", new ConfigurationException("message"));
    }

    /**
     * <p>
     * Test constructor <code>ConfigurationException(String message, Throwable cause)</code>
     * for accuracy.
     * </p>
     */
    public void testCtor2() {
        assertNotNull("Failed to create ConfigurationException.",
                new ConfigurationException("message", new Exception()));
    }
}
