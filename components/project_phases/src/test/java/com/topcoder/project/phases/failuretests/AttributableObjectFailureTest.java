/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.failuretests;

import com.topcoder.project.phases.AttributableObject;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test for AttributableObject class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 2.0
 */
public class AttributableObjectFailureTest extends TestCase {
    /**
     * <p>
     * An instance of mock implementation of <code>AttributableObject</code>.
     * </p>
     */
    AttributableObject tester = null;

    /**
     * <p>
     * Initializes <code>tester</code> for test.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        tester = new AttributableObjectTester();
    }

    /**
     * <p>
     * Test getAttribute(Serializable key),
     * when key is null, IllegalArgumentException is expected.
     * </p>
     */
    public void testGetAttribute_KeyIsNull() {
        try {
            tester.getAttribute(null);
            fail("when key is null, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test setAttribute(Serializable key, Serializable value),
     * when key is null, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetAttribute_KeyIsNull() {
        try {
            tester.setAttribute(null, "value");
            fail("when key is null, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test setAttribute(Serializable key, Serializable value),
     * when value is null, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetAttribute_ValueIsNull() {
        try {
            tester.setAttribute("key", null);
            fail("when value is null, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test removeAttribute(Serializable key),
     * when key is null, IllegalArgumentException is expected.
     * </p>
     */
    public void testRemoveAttribute_KeyIsNull() {
        try {
            tester.removeAttribute(null);
            fail("when key is null, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * An mock implementation of <code>AttributableObject</code>.
     * </p>
     *
     * @author TCSDEVELOPER
     * @version 2.0
     */
    class AttributableObjectTester extends AttributableObject {
    }
}
