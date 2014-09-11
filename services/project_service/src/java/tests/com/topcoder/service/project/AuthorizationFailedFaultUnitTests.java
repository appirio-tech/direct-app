/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link AuthorizationFailedFault}</code> class.
 * </p>
 *
 * <p>
 * Version 1.1 adds a test case to ensure the inheritance.
 * </p>
 *
 * @author FireIce
 * @author TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public class AuthorizationFailedFaultUnitTests extends TestCase {

    /**
     * Represents a string with a detail message.
     */
    private static final String DETAIL_MESSAGE = "detail";

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(AuthorizationFailedFaultUnitTests.class);
    }

    /**
     * <p>
     * <code>{@link AuthorizationFailedFault}</code> should be subclass of <code>ProjectServiceFault</code>.
     * </p>
     */
    public void testInheritance() {
        assertTrue("AuthorizationFailedFault should be subclass of ProjectServiceFault",
            AuthorizationFailedFault.class.getSuperclass() == ProjectServiceFault.class);
    }

    /**
     * Tests accuracy of <code>AuthorizationFailedFault(String)</code> constructor. The detail error message should be
     * correct.
     */
    public void testAuthorizationFailedFaultStringAccuracy() {
        // Construct AuthorizationFailedFault with a detail message
        AuthorizationFailedFault exception = new AuthorizationFailedFault(DETAIL_MESSAGE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());
    }
}
