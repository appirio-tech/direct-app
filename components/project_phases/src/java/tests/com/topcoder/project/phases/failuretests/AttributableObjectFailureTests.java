/**
 * Copyright (c) 2004, TopCoder Inc. All rights reserved.
 */
package com.topcoder.project.phases.failuretests;

import com.topcoder.project.phases.AttributableObject;
import junit.framework.TestCase;


/**
 * Failure tests for AttributableObject.
 *
 * @author oldbig
 * @version 1.0
 */
public class AttributableObjectFailureTests extends TestCase {

    /**
     * An AttributableObject instance for test.
     */
    private AttributableObject attributableObject = null;

    /**
     * Set up.
     */
    protected void setUp() {
        this.attributableObject = new FailureTestAttributableObject();
    }

    /**
     * Tests getAttribute().
     */
    public void testGetAttribute() {
        try {
            this.attributableObject.getAttribute(null);
            fail("NullPointerException should be thrown.");
        } catch (NullPointerException npe) {
        }
    }


    /**
     * Tests setAttribute(key, value).
     */
    public void testSetAttribute() {
        try {
            this.attributableObject.setAttribute(null, "value");
            fail("NullPointerException should be thrown.");
        } catch (NullPointerException npe) {
        }

        try {
            this.attributableObject.setAttribute("key", null);
            fail("NullPointerException should be thrown.");
        } catch (NullPointerException npe) {
        }

    }

    /**
     * Tests removeAttribute().
     */
    public void testRemoveAttribute() {
        try {
            this.attributableObject.removeAttribute(null);
            fail("NullPointerException should be thrown.");
        } catch (NullPointerException npe) {
        }
    }

}

/**
 * An implementation of AttributableObject.
 * @author oldbig
 * @version 1.0
 */
class FailureTestAttributableObject extends AttributableObject {

}
