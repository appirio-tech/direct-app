/**
 * Copyright (c) 2004, TopCoder, Inc. All rights reserved.
 *
 * TCS Project Phases 1.0 (Unit Test)
 *
 * @ CyclicDependencyExceptionTests.java
 */
package com.topcoder.project.phases;

import junit.framework.TestCase;

import com.topcoder.util.errorhandling.BaseRuntimeException;

/**
 * <p>Unit test cases for CyclicDependencyException.</p>
 *
 * <p>This class is pretty simple. The test cases simply verifies the exception can be instantiated with the
 * error message properly propagated, and that it comes with correct inheritance.</p>
 *
 * @author TCSDEVELOPER
 *
 * @version 1.0
 */
public class CyclicDependencyExceptionTests extends TestCase {

    /**
     * <p>The error message used for testing.</p>
     */
    private static final String ERROR_MESSAGE = "test error message";

    /**
     * <p>Creation test.</p>
     *
     * <p>Verifies the error message is properly propagated.</p>
     */
    public void testCreateCyclicDependencyException() {
        CyclicDependencyException cde = new CyclicDependencyException(CyclicDependencyExceptionTests.ERROR_MESSAGE);

        assertNotNull("Unable to instantiate CyclicDependencyException.",
                cde);
        assertEquals("Error message is not properly propagated to super class.",
                CyclicDependencyExceptionTests.ERROR_MESSAGE,
                cde.getMessage());
    }

    /**
     * <p>Inheritance test.</p>
     *
     * <p>Verifies CyclicDependencyException subclasses BaseRuntimeException.</p>
     */
    public void testCyclicDependencyExceptionInheritance() {
        assertTrue("CyclicDependencyException does not subclass BaseRuntimeException.",
                new CyclicDependencyException(CyclicDependencyExceptionTests.ERROR_MESSAGE)
                        instanceof BaseRuntimeException);
    }

}
