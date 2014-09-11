/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.project.service.ProjectServicesException;
import com.topcoder.util.errorhandling.BaseRuntimeException;

/**
 * <p>
 * Tests the functionality of <code>ProjectServicesExceptionTest</code> class for accuracy.
 * </p>
 *
 * @author kshatriyan
 * @version 1.0
 */
public class ProjectServicesExceptionTest extends TestCase {
    /**
     * <p>
     * Instance of <code>ProjectServicesException</code> for test.
     * </p>
     */
    private ProjectServicesException projectServicesException;

    /**
     * <p>
     * Integrates all tests in this class.
     * </p>
     *
     * @return Test suite of all tests of <code>ProjectServicesExceptionTest</code>.
     */
    public static Test suite() {
        return new TestSuite(ProjectServicesExceptionTest.class);
    }

    /**
     * <p>
     * Sets up the environment before each TestCase.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    protected void setUp() throws Exception {
        projectServicesException = new ProjectServicesException("message");
    }

    /**
     * <p>
     * Tears down the environment after execution of each TestCase.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    protected void tearDown() throws Exception {
        projectServicesException = null;
    }

    /**
     * <p>
     * Accuracy test of <code>ProjectServicesException(String msg)</code> constructor. Creates an instance and get
     * its attributes for test.
     * </p>
     *
     * <p>
     * Tests for instantiation and inheritance and proper exception message propagation.
     * </p>
     *
     */
    public void testProjectServicesException_accuracy1() {
        assertNotNull("Unable to create the instance properly", projectServicesException);
        assertTrue("ProjectServicesException should extend BaseRuntimeException",
                projectServicesException instanceof BaseRuntimeException);
        assertEquals("Unable to get the message properly", "message", projectServicesException.getMessage());
    }

    /**
     * <p>
     * Accuracy test of <code>ProjectServicesException(String msg, Throwable cause)</code> constructor. Creates an
     * instance and get its attributes for test.
     * </p>
     *
     * <p>
     * Tests for instantiation and inheritance and proper exception message and cause propagation.
     * </p>
     *
     */
    public void testProjectServicesException_accuracy2() {
        Exception cause = new Exception();
        projectServicesException = new ProjectServicesException("message", cause);
        assertNotNull("Unable to create the instance properly", projectServicesException);
        assertTrue("ProjectServicesException should extend BaseRuntimeException",
                projectServicesException instanceof BaseRuntimeException);
        assertTrue("Unable to get the message properly", projectServicesException.getMessage().startsWith("message"));
        assertEquals("Unable to get the cause properly", cause, projectServicesException.getCause());
    }
}
