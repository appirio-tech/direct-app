/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests on class <code>PhaseGenerationException</code>.
 * </p>
 * @author flying2hk
 * @version 1.1
 * @since 1.0
 */
public class UnitTestPhaseGenerationException extends TestCase {
    /**
     * <p>
     * Test constructor <code>PhaseGenerationException(String message)</code>
     * for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create PhaseGenerationException.", new PhaseGenerationException("message"));
    }

    /**
     * <p>
     * Test constructor <code>PhaseGenerationException(String message, Throwable cause)</code>
     * for accuracy.
     * </p>
     */
    public void testCtor2() {
        assertNotNull("Failed to create PhaseGenerationException.",
                new PhaseGenerationException("message", new Exception()));
    }
}
