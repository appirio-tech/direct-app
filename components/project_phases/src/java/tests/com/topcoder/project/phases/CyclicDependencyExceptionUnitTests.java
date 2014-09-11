/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases;

import junit.framework.TestCase;

/**
 * Unit test case for <code>CyclicDependencyException</code> exception.
 *
 * @author littlebull
 * @version 2.0
 */
public class CyclicDependencyExceptionUnitTests extends TestCase {
    /**
     * Test the constructor <code>CyclicDependencyException(String)</code>.
     */
    public void testConstructorWithString() {
        CyclicDependencyException excp = new CyclicDependencyException("Failed");
        assertNotNull("The instance should be created.", excp);
        assertTrue("The message field should be set correctly.", excp.getMessage().startsWith("Failed"));
    }
}
