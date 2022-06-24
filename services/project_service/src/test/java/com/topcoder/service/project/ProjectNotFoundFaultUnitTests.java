/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link ProjectNotFoundFault}</code> class.
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
public class ProjectNotFoundFaultUnitTests extends TestCase {

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
        return new TestSuite(ProjectNotFoundFaultUnitTests.class);
    }

    /**
     * <p>
     * <code>{@link ProjectNotFoundFault}</code> should be subclass of <code>ProjectServiceFault</code>.
     * </p>
     */
    public void testInheritance() {
        assertTrue("ProjectNotFoundFault should be subclass of ProjectServiceFault",
            ProjectNotFoundFault.class.getSuperclass() == ProjectServiceFault.class);
    }

    /**
     * Tests accuracy of <code>ProjectNotFoundFault(String)</code> constructor. The detail error message should be
     * correct.
     */
    public void testProjectNotFoundFaultStringAccuracy() {
        // Construct ProjectNotFoundFault with a detail message
        ProjectNotFoundFault exception = new ProjectNotFoundFault(DETAIL_MESSAGE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());
    }
}
