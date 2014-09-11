/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence;

import junit.framework.TestCase;

import com.topcoder.util.errorhandling.BaseException;

/**
 * Unit tests for the class: ResourcePersistenceException.
 *
 * @author kinfkong
 * @version 1.0
 */
public class ResourcePersistenceExceptionTest extends TestCase {
    /**
     * A reference to an instance of ResourcePersistenceException. This instance
     * will be constructed by the one-argument constructor.
     */
    private ResourcePersistenceException rpe1;

    /**
     * A reference to an instance of ResourcePersistenceException. This instance
     * will be constructed by the two-argument constructor.
     */
    private ResourcePersistenceException rpe2;

    /**
     * Sets the test environment. The instances of ResourcePersistenceException are
     * constructed.
     */
    protected void setUp() {
        rpe1 = new ResourcePersistenceException("dummy");
        rpe2 = new ResourcePersistenceException("dummy", new NullPointerException());
    }

    /**
     * Tests the definition of ResourcePersistenceException. It must extend the
     * BaseException. Test if the instances are successfully constructed.
     */
    public void testDefinitionAndConstructers() {
        assertTrue("The instance rpe1 cannot be constructed", rpe1 != null);
        assertTrue("The instance rpe2 cannot be constructed", rpe2 != null);
        assertTrue("The ResourcePersistenceException must extends BaseException",
            rpe1 instanceof BaseException);
        assertTrue("The ResourcePersistenceException must extends BaseException",
            rpe2 instanceof BaseException);
    }

    /**
     * Tests one-argument constructor with error message and the properties can
     * be retrieved correctly later.
     */
    public void testConstructor1() {
        assertTrue("The error message should match",
            rpe1.getMessage().indexOf("dummy") >= 0);
    }

    /**
     * Tests two-argument constructor with error message and the properties can
     * be retrieved correctly later.
     */
    public void testConstructor2() {
        assertTrue("The error message should match",
            rpe2.getMessage().indexOf("dummy") >= 0);
    }

    /**
     * Tests two-argument constructor non-null inner exception, the cause can be
     * retrieved correctly later.
     */
    public void testConstructor3() {
        assertTrue("The inner exception should match",
            rpe2.getCause() instanceof NullPointerException);
    }

    /**
     * Tests if ResourcePersistenceException works properly. Test the usage.
     */
    public void testUsage() {
        try {
            throw new ResourcePersistenceException("message");
        } catch (ResourcePersistenceException e) {
            // success
        }

        try {
            throw new ResourcePersistenceException("message", new Exception());
        } catch (ResourcePersistenceException e) {
            // success
        }
    }
}
