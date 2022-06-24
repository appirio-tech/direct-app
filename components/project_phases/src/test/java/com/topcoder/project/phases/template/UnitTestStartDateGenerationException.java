/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests on class <code>StartDateGenerationException</code>.
 * </p>
 * @author flying2hk
 * @version 1.0
 */
public class UnitTestStartDateGenerationException extends TestCase {
    /**
     * <p>
     * Test constructor <code>StartDateGenerationException(String message)</code>
     * for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create StartDateGenerationException.", new StartDateGenerationException("message"));
    }

    /**
     * <p>
     * Test constructor <code>StartDateGenerationException(String message, Throwable cause)</code>
     * for accuracy.
     * </p>
     */
    public void testCtor2() {
        assertNotNull("Failed to create StartDateGenerationException.",
                new StartDateGenerationException("message", new Exception()));
    }
}
