/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

import java.io.Serializable;
import java.util.Date;

import junit.framework.TestCase;

/**
 * Unit tests for the class: AuditableResourceStructure.
 *
 * @author kinfkong
 * @version 1.0
 */
public class AuditableResourceStructureTest extends TestCase {

    /**
     * An instance of AuditableResourceStructure for tests.
     */
    private AuditableResourceStructure ars = null;

    /**
     * Sets up the environments.
     *
     * Creates the instances.
     */
    protected void setUp() {
        ars = new AuditableResourceStructure() { };
    }

    /**
     * Tests constructor AuditableResourceStructure().
     *
     * Checks if the instance has been created successfully.
     */
    public void testAuditableResourceStructure() {
        assertNotNull("The instance cannot be created.", ars);
    }

    /**
     * Tests constructor AuditableResourceStructure().
     *
     * Checks if it implements Serializable.
     */
    public void testAuditableResourceStructure_Definition() {
        assertTrue("AuditableResourcestructure should implements Serializable.", ars instanceof Serializable);
    }

    /**
     * Tests method: setCreationUser(String).
     *
     * Checks if it works properly.
     */
    public void testSetCreationUser() {
        ars.setCreationUser("kinfkong");
        assertEquals("This method does not work properly.", "kinfkong", ars.getCreationUser());
    }

    /**
     * Tests method: setCreationUser(String).
     *
     * Checks if it works properly with null.
     */
    public void testSetCreationUser_Null() {
        ars.setCreationUser(null);
        assertNull("This method does not work properly.", ars.getCreationUser());
    }

    /**
     * Tests method: getCreationUser(String).
     *
     * Checks if it works properly.
     */
    public void testGetCreationUser() {
        ars.setCreationUser("kinfkong");
        assertEquals("This method does not work properly.", "kinfkong", ars.getCreationUser());
    }

    /**
     * Tests method: getCreationUser(String).
     *
     * Checks if it works properly with null.
     */
    public void testGetCreationUser_Null() {
        assertNull("This method does not work properly.", ars.getCreationUser());
    }

    /**
     * Tests method: setCreationTimestamp(Date).
     *
     * With current date.
     */
    public void testSetCreationTimestamp() {
        Date current = new Date();
        ars.setCreationTimestamp(current);
        assertEquals("This method does not work properly.",
                current.getTime(), ars.getCreationTimestamp().getTime());
    }

    /**
     * Tests method: setCreationTimestamp(Date).
     *
     * With a specific date.
     */
    public void testSetCreationTimestamp_someDate() {
        Date date = new Date(12345678L);
        ars.setCreationTimestamp(date);
        assertEquals("This method does not work properly.",
                date.getTime(), ars.getCreationTimestamp().getTime());
    }

    /**
     * Tests method: getCreationTimestamp(Date).
     *
     * With current date.
     */
    public void testGetCreationTimestamp() {
        Date current = new Date();
        ars.setCreationTimestamp(current);
        assertEquals("This method does not work properly.",
                current.getTime(), ars.getCreationTimestamp().getTime());
    }

    /**
     * Tests method: getCreationTimestamp(Date).
     *
     * With a specific date.
     */
    public void testGetCreationTimestamp_someDate() {
        Date date = new Date(12345678L);
        ars.setCreationTimestamp(date);
        assertEquals("This method does not work properly.",
                date.getTime(), ars.getCreationTimestamp().getTime());
    }


    /**
     * Tests method: setModificationUser(String).
     *
     * Checks if it works properly.
     */
    public void testSetModificationUser() {
        ars.setModificationUser("kinfkong");
        assertEquals("This method does not work properly.", "kinfkong", ars.getModificationUser());
    }

    /**
     * Tests method: setModificationUser(String).
     *
     * Checks if it works properly with null.
     */
    public void testSetModificationUser_Null() {
        ars.setModificationUser(null);
        assertNull("This method does not work properly.", ars.getModificationUser());
    }

    /**
     * Tests method: getModificationUser(String).
     *
     * Checks if it works properly.
     */
    public void testGetModificationUser() {
        ars.setModificationUser("kinfkong");
        assertEquals("This method does not work properly.", "kinfkong", ars.getModificationUser());
    }

    /**
     * Tests method: getModificationUser(String).
     *
     * Checks if it works properly with null.
     */
    public void testGetModificationUser_Null() {
        assertNull("This method does not work properly.", ars.getModificationUser());
    }

    /**
     * Tests method: setModificationTimestamp(Date).
     *
     * With current date.
     */
    public void testSetModificationTimestamp() {
        Date current = new Date();
        ars.setModificationTimestamp(current);
        assertEquals("This method does not work properly.",
                current.getTime(), ars.getModificationTimestamp().getTime());
    }

    /**
     * Tests method: setModificationTimestamp(Date).
     *
     * With a specific date.
     */
    public void testSetModificationTimestamp_someDate() {
        Date date = new Date(12345678L);
        ars.setModificationTimestamp(date);
        assertEquals("This method does not work properly.",
                date.getTime(), ars.getModificationTimestamp().getTime());
    }

    /**
     * Tests method: getModificationTimestamp(Date).
     *
     * With current date.
     */
    public void testGetModificationTimestamp() {
        Date current = new Date();
        ars.setModificationTimestamp(current);
        assertEquals("This method does not work properly.",
                current.getTime(), ars.getModificationTimestamp().getTime());
    }

    /**
     * Tests method: getModificationTimestamp(Date).
     *
     * With a specific date.
     */
    public void testGetModificationTimestamp_someDate() {
        Date date = new Date(12345678L);
        ars.setModificationTimestamp(date);
        assertEquals("This method does not work properly.",
                date.getTime(), ars.getModificationTimestamp().getTime());
    }

}
