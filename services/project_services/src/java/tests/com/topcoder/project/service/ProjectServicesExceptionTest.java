/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service;

import junit.framework.TestCase;

/**
 * <p>
 * This is a test case for <code>ProjectServicesException</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProjectServicesExceptionTest extends TestCase {

    /**
     * <p>
     * Test for constructor with message.
     * </p>
     * <p>
     * Tests it for accuracy.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithMsg() throws Exception {
        Exception ex = new ProjectServicesException("msg");
        assertNotNull("'ex' should not be null.", ex);
        assertEquals("Exception message mismatched.", "msg", ex.getMessage());
    }

    /**
     * <p>
     * Test for constructor with message and cause.
     * </p>
     * <p>
     * Tests it for accuracy.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithMsgAndCause() throws Exception {
        Exception e = new Exception("exception");
        Exception ex = new ProjectServicesException("message", e);
        assertNotNull("'ex' should not be null.", ex);
        assertEquals("Exception message mismatched.", "message", ex.getMessage());
    }
}
