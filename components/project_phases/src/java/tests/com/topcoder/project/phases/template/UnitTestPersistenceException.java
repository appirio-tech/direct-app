/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests on class <code>PersistenceException</code>.
 * </p>
 * @author flying2hk
 * @version 1.1
 * @since 1.0
 */
public class UnitTestPersistenceException extends TestCase {
    /**
     * <p>
     * Test constructor <code>PersistenceException(String message)</code>
     * for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create PersistenceException.", new PersistenceException("message"));
    }

    /**
     * <p>
     * Test constructor <code>PersistenceException(String message, Throwable cause)</code>
     * for accuracy.
     * </p>
     */
    public void testCtor2() {
        assertNotNull("Failed to create PersistenceException.",
                new PersistenceException("message", new Exception()));
    }
}
