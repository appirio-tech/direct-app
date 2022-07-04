/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link ProjectServiceFault}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProjectServiceFaultUnitTests extends TestCase {

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
        return new TestSuite(ProjectServiceFaultUnitTests.class);
    }

    /**
     * Tests accuracy of <code>ProjectServiceFault(String)</code> constructor. The detail error message should be
     * correct.
     */
    public void testProjectServiceFaultStringAccuracy() {
        // Construct ProjectServiceFault with a detail message
        ProjectServiceFault exception = new ProjectServiceFault(DETAIL_MESSAGE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());
    }
}
