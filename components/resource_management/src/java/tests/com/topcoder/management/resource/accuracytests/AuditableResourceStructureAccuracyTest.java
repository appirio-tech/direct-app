/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.accuracytests;

import com.topcoder.management.resource.AuditableResourceStructure;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.Serializable;

import java.util.Date;

/**
 * <p>
 * This accuracy tests addresses the functionality provided by the
 * <code>AuditableResourceStructure</code> class. It tests the
 * AuditableResourceStructure() constructor; getCreationTimestamp(),
 * getCreationUser(), getModificationTimestamp(), getModificationUser(),
 * setCreationTimestamp(Date), setCreationUser(String),
 * setModificationTimestamp(Date), setModificationUser(String) methods.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AuditableResourceStructureAccuracyTest extends TestCase {
    /**
     * <p>
     * The instance of <code>AuditableResourceStructure</code> for test.
     * </p>
     */
    private AuditableResourceStructure test = null;

    /**
     * <p>
     * Test suite of <code>AuditableResourceStructureAccuracyTest</code>.
     * </p>
     * @return Test suite of <code>AuditableResourceStructureAccuracyTest</code>.
     */
    public static Test suite() {
        return new TestSuite(AuditableResourceStructureAccuracyTest.class);
    }

    /**
     * <p>
     * Set up the accuracy testing envionment.
     * </p>
     */
    protected void setUp() {
        test = new AuditableResourceStructure() {
        };
    }

    /**
     * <p>
     * Basic test of <code>AuditableResourceStructure()</code> constructor.
     * </p>
     */
    public void testAuditableResourceStructureCtor() {
        // check null here.
        assertNotNull("Create AuditableResourceStructure failed.", test);

        // check the type here.
        assertTrue("AuditableResourceStructure should extend Serializable.", test instanceof Serializable);
    }

    /**
     * <p>
     * Accuracy test of the <code>getCreationTimestamp()</code> method.
     * </p>
     */
    public void testMethod_getCreationTimestamp() {
        assertNull("The creation timestamp should be null.", test.getCreationTimestamp());

        // test with now time.
        Date creationTime = new Date();
        test.setCreationTimestamp(creationTime);

        assertEquals("This creation timestamp is incorrect.", creationTime.getTime(), test
                .getCreationTimestamp().getTime());

        // test with a new time.
        creationTime = new Date(1234567890L);
        test.setCreationTimestamp(creationTime);

        assertEquals("The creation timestamp is incorrect.", creationTime.getTime(), test
                .getCreationTimestamp().getTime());

        // test with null.
        test.setCreationTimestamp(null);
        assertNull("The creation timestamp should be null.", test.getCreationTimestamp());
    }

    /**
     * <p>
     * Accuracy test of the <code>setCreationTimestamp(Date)</code> method.
     * </p>
     */
    public void testMethod_setCreationTimestamp() {
        // test with a new time.
        Date creationTime = new Date(1234567890L);
        test.setCreationTimestamp(creationTime);

        assertEquals("The creation timestamp is incorrect.", creationTime.getTime(), test
                .getCreationTimestamp().getTime());

        // test with null.
        test.setCreationTimestamp(null);
        assertNull("The creation timestamp should be null.", test.getCreationTimestamp());

        // test with now time.
        creationTime = new Date();
        test.setCreationTimestamp(creationTime);

        assertEquals("This creation timestamp is incorrect.", creationTime.getTime(), test
                .getCreationTimestamp().getTime());
    }

    /**
     * <p>
     * Accuracy test of the <code>getModificationTimestamp()</code> method.
     * </p>
     */
    public void testMethod_getModificationTimestamp() {
        assertNull("The modification timestamp should be null.", test.getModificationTimestamp());

        // test with now time.
        Date modificationTime = new Date();
        test.setModificationTimestamp(modificationTime);

        assertEquals("This modification timestamp is incorrect.", modificationTime.getTime(), test
                .getModificationTimestamp().getTime());

        // test with a new time.
        modificationTime = new Date(1234567890L);
        test.setModificationTimestamp(modificationTime);

        assertEquals("The modification timestamp is incorrect.", modificationTime.getTime(), test
                .getModificationTimestamp().getTime());

        // test with null.
        test.setModificationTimestamp(null);
        assertNull("The modification timestamp should be null.", test.getModificationTimestamp());
    }

    /**
     * <p>
     * Accuracy test of the <code>setModificationTimestamp(Date)</code>
     * method.
     * </p>
     */
    public void testMethod_setModificationTimestamp() {
        // test with a new time.
        Date modificationTime = new Date(1234567890L);
        test.setModificationTimestamp(modificationTime);

        assertEquals("The modification timestamp is incorrect.", modificationTime.getTime(), test
                .getModificationTimestamp().getTime());

        // test with null.
        test.setModificationTimestamp(null);
        assertNull("The modification timestamp should be null.", test.getModificationTimestamp());

        // test with now time.
        modificationTime = new Date();
        test.setModificationTimestamp(modificationTime);

        assertEquals("This modification timestamp is incorrect.", modificationTime.getTime(), test
                .getModificationTimestamp().getTime());
    }

    /**
     * <p>
     * Accuracy test of the <code>getCreationUser()</code> method.
     * </p>
     */
    public void testMethod_getCreationUser() {
        assertNull("This creation user should be null.", test.getCreationUser());

        // test with string.
        String creationUser = "tc";
        test.setCreationUser(creationUser);
        assertEquals("This creation user is incorrect.", creationUser, test.getCreationUser());

        // test with null.
        test.setCreationUser(null);
        assertNull("This creation user should be null.", test.getCreationUser());
    }

    /**
     * <p>
     * Accuracy test of the <code>setCreationUser(String)</code> method.
     * </p>
     */
    public void testMethod_setCreationUser() {
        // test with null.
        test.setCreationUser(null);
        assertNull("This creation user should be null.", test.getCreationUser());

        // test with string.
        String creationUser = "tc";
        test.setCreationUser(creationUser);
        assertEquals("This creation user is incorrect.", creationUser, test.getCreationUser());
    }

    /**
     * <p>
     * Accuracy test of the <code>getModificationUser()</code> method.
     * </p>
     */
    public void testMethod_getModificationUser() {
        assertNull("This modification user should be null.", test.getModificationUser());

        // test with string.
        String modificationUser = "tc";
        test.setModificationUser(modificationUser);
        assertEquals("This modification user is incorrect.", modificationUser, test.getModificationUser());

        // test with null.
        test.setModificationUser(null);
        assertNull("This modification user should be null.", test.getModificationUser());
    }

    /**
     * <p>
     * Accuracy test of the <code>setModificationUser()</code> method.
     * </p>
     */
    public void testMethod_setModificationUser() {
        // test with null.
        test.setModificationUser(null);
        assertNull("This modification user should be null.", test.getModificationUser());

        // test with string.
        String modificationUser = "tc";
        test.setModificationUser(modificationUser);
        assertEquals("This modification user is incorrect.", modificationUser, test.getModificationUser());
    }
}
