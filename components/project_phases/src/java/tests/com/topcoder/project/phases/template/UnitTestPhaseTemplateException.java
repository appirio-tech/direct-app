/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests on class <code>PhaseTemplateException</code>.
 * </p>
 * @author flying2hk
 * @version 1.1
 * @since 1.0
 */
public class UnitTestPhaseTemplateException extends TestCase {
    /**
     * <p>
     * Test constructor <code>PhaseTemplateException(String message)</code>
     * for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create PhaseTemplateException.", new PhaseTemplateException("message"));
    }

    /**
     * <p>
     * Test constructor <code>PhaseTemplateException(String message, Throwable cause)</code>
     * for accuracy.
     * </p>
     */
    public void testCtor2() {
        assertNotNull("Failed to create PhaseTemplateException.",
                new PhaseTemplateException("message", new Exception()));
    }
}
