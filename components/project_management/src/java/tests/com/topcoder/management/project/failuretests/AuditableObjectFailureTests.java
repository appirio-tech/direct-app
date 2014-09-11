/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.failuretests;

import com.topcoder.management.project.AuditableObject;

import junit.framework.TestCase;

/**
 * Failure tests for <code>AuditableObject</code> class.
 *
 * @author vividmxx
 * @version 1.0
 */
public class AuditableObjectFailureTests extends TestCase {

    /**
     * The mock class which extends from <code>AuditableObject</code> class.
     */
    private class MockAuditableObject extends AuditableObject {

        /**
         * The default constructor which dose nothing.
         */
        public MockAuditableObject() {
            super();
        }
    }

    /**
     * The <code>MockAuditableObject</code> instance used for test.
     */
    private MockAuditableObject ao = new MockAuditableObject();

    /**
     * Test the method <code>setCreationUser(String)</code> with null creationUser. IllegalArgumentException should be
     * thrown.
     */
    public void testSetCreationUserWithNullCreationUser() {
        try {
            ao.setCreationUser(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setCreationUser(String)</code> with empty creationUser. IllegalArgumentException should
     * be thrown.
     */
    public void testSetCreationUserWithEmptyCreationUser() {
        try {
            ao.setCreationUser(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setModificationUser(String)</code> with null modificationUser. IllegalArgumentException
     * should be thrown.
     */
    public void testSetModificationUserWithNullModificationUser() {
        try {
            ao.setModificationUser(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setModificationUser(String)</code> with empty modificationUser. IllegalArgumentException
     * should be thrown.
     */
    public void testSetModificationUserWithEmptyModificationUser() {
        try {
            ao.setModificationUser(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setCreationTimestamp(Date)</code> with null creationTimestamp. IllegalArgumentException
     * should be thrown.
     */
    public void testSetCreationTimestampWithNullCreationTimestamp() {
        try {
            ao.setCreationTimestamp(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setModificationTimestamp(Date)</code> with null modificationTimestamp.
     * IllegalArgumentException should be thrown.
     */
    public void testSetModificationTimestampWithNullModificationTimestamp() {
        try {
            ao.setModificationTimestamp(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }
}
