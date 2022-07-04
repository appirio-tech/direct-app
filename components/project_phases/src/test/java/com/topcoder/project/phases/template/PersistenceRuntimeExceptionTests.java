/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit tests on class <code>PersistenceRuntimeException</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class PersistenceRuntimeExceptionTests extends TestCase {
    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(PersistenceRuntimeExceptionTests.class);
    }
    /**
     * <p>
     * Test constructor <code>PhaseGenerationException(String message)</code>
     * for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create PersistenceRuntimeException.", new PersistenceRuntimeException("message"));
    }

    /**
     * <p>
     * Test constructor <code>PhaseGenerationException(String message, Throwable cause)</code>
     * for accuracy.
     * </p>
     */
    public void testCtor2() {
        assertNotNull("Failed to create PersistenceRuntimeException.",
                new PersistenceRuntimeException("message", new Exception()));
    }
}
