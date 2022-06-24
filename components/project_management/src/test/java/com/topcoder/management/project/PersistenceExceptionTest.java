/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for PersistenceException class.
 *
 * @author iamajia
 * @version 1.0
 */
public class PersistenceExceptionTest extends TestCase {
    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(PersistenceExceptionTest.class);
    }

    /**
     * Accuracy test of <code>PersistenceException(String message)</code> constructor.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testPersistenceExceptionAccuracy1() throws Exception {
        PersistenceException pe = new PersistenceException("test");
        assertEquals("message is incorrect.", "test", pe.getMessage());
    }

    /**
     * Accuracy test of <code>PersistenceException(String message, Throwable cause)</code> constructor.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testPersistenceExceptionAccuracy2() throws Exception {
        Exception e = new Exception("error1");
        PersistenceException pe = new PersistenceException("error2", e);
        assertEquals("message is incorrect.", "error2", pe.getMessage());
        assertEquals("cause is incorrect.", e, pe.getCause());
    }
}
