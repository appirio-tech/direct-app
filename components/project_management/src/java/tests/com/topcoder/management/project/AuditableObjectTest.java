/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for AuditableObject class.
 *
 * @author iamajia
 * @version 1.0
 */
public class AuditableObjectTest extends TestCase {
    /**
     * this auditableObject is used in the test.
     */
    private AuditableObject auditableObject = new MockAuditableObject();

    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(AuditableObjectTest.class);
    }

    /**
     * This mock class extends from AuditableObject. it is used in the test.
     */
    private class MockAuditableObject extends AuditableObject {
        /**
         * Empty constructor.
         */
        public MockAuditableObject() {
            super();
        }
    }

    /**
     * Accuracy test of <code>AuditableObject()</code> constructor.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAuditableObject() throws Exception {
        new MockAuditableObject();
    }

    /**
     * Accuracy test of <code>setCreationUser(String creationUser)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetCreationUserAccuracy() throws Exception {
        auditableObject.setCreationUser("test");
        assertEquals("creationUser is incorrect.", "test", auditableObject.getCreationUser());
    }

    /**
     * Failure test of <code>setCreationUser(String creationUser)</code> method.
     * <p>
     * creationUser is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetCreationUserFailure1() throws Exception {
        try {
            auditableObject.setCreationUser(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>setCreationUser(String creationUser)</code> method.
     * <p>
     * creationUser is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetCreationUserFailure2() throws Exception {
        try {
            auditableObject.setCreationUser("  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>getCreationUser()</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetCreationUserAccuracy() throws Exception {
        auditableObject.setCreationUser("test");
        assertEquals("creationUser is incorrect.", "test", auditableObject.getCreationUser());

    }

    /**
     * Accuracy test of <code>setCreationTimestamp(Date creationTimestamp)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetCreationTimestampAccuracy() throws Exception {
        Date date = new Date();
        auditableObject.setCreationTimestamp(date);
        assertEquals("creationTimestamp is incorrect.", date, auditableObject.getCreationTimestamp());
    }

    /**
     * Failure test of <code>setCreationTimestamp(Date creationTimestamp)</code> method.
     * <p>
     * creationTimestamp is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetCreationTimestampFailure() throws Exception {
        try {
            auditableObject.setCreationTimestamp(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>getCreationTimestamp()</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetCreationTimestampAccuracy() throws Exception {
        Date date = new Date();
        auditableObject.setCreationTimestamp(date);
        assertEquals("creationTimestamp is incorrect.", date, auditableObject.getCreationTimestamp());
    }

    /**
     * Accuracy test of <code>setModificationUser(String modificationUser)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetModificationUserAccuracy() throws Exception {
        auditableObject.setModificationUser("test");
        assertEquals("modificationUser is incorrect.", "test", auditableObject.getModificationUser());
    }

    /**
     * Failure test of <code>setModificationUser(String modificationUser)</code> method.
     * <p>
     * modificationUser is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetModificationUserFailure1() throws Exception {
        try {
            auditableObject.setModificationUser(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>setModificationUser(String modificationUser)</code> method.
     * <p>
     * modificationUser is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetModificationUserFailure2() throws Exception {
        try {
            auditableObject.setModificationUser("  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>getModificationUser()</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetModificationUserAccuracy() throws Exception {
        auditableObject.setModificationUser("test");
        assertEquals("modificationUser is incorrect.", "test", auditableObject.getModificationUser());
    }

    /**
     * Accuracy test of <code>setModificationTimestamp(Date modificationTimestamp)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetModificationTimestampAccuracy() throws Exception {
        Date date = new Date();
        auditableObject.setModificationTimestamp(date);
        assertEquals("modificationTimestamp is incorrect.", date, auditableObject.getModificationTimestamp());
    }

    /**
     * Failure test of <code>setModificationTimestamp(Date modificationTimestamp)</code> method.
     * <p>
     * modificationTimestamp is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetModificationTimestampFailure() throws Exception {
        try {
            auditableObject.setModificationTimestamp(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>getModificationTimestamp()</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetModificationTimestampAccuracy() throws Exception {
        Date date = new Date();
        auditableObject.setModificationTimestamp(date);
        assertEquals("modificationTimestamp is incorrect.", date, auditableObject.getModificationTimestamp());

    }
}
