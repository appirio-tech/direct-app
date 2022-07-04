/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.accuracytests;

import com.topcoder.management.deliverable.AuditedDeliverableStructure;

import junit.framework.TestCase;

import java.util.Date;


/**
 * <p>
 * Accuracy test cases for <code>AuditedDeliverableStructure</code>.
 * </p>
 *
 * @author skatou
 * @version 1.0
 */
public class AuditedDeliverableStructureAccuracyTests extends TestCase {
    /** The <code>AuditedDeliverableStructure</code> instance to be test. */
    private AuditedDeliverableStructure structure = null;

    /**
     * Sets up the test environment. New <code>AuditedDeliverableStructure</code> instance is created with default id.
     */
    protected void setUp() {
        structure = new EmptyDeliverableStructure();
    }

    /**
     * Verifies UNSET_ID is set correctly.
     */
    public void testUnsetIdValue() {
        assertEquals("UNSET_ID not set correctly", -1, AuditedDeliverableStructure.UNSET_ID);
    }

    /**
     * Tests default constructor.
     */
    public void testDefaultConstructor() {
        structure = new EmptyDeliverableStructure();
        assertNotNull("Unable to instantiate AuditedDeliverableStructure", structure);
        assertEquals("Id not set correctly", AuditedDeliverableStructure.UNSET_ID, structure.getId());
        assertEquals("Creation user not set correctly", null, structure.getCreationUser());
        assertEquals("Creation timestamp not set correctly", null, structure.getCreationTimestamp());
        assertEquals("Modification user not set correctly", null, structure.getModificationUser());
        assertEquals("Modification timestamp not set correctly", null, structure.getModificationTimestamp());
    }

    /**
     * Tests constructor(long).
     */
    public void testConstructorWithId() {
        long id = 4096;
        structure = new EmptyDeliverableStructure(id);
        assertNotNull("Unable to instantiate AuditedDeliverableStructure", structure);
        assertEquals("Id not set correctly", id, structure.getId());
        assertEquals("Creation user not set correctly", null, structure.getCreationUser());
        assertEquals("Creation timestamp not set correctly", null, structure.getCreationTimestamp());
        assertEquals("Modification user not set correctly", null, structure.getModificationUser());
        assertEquals("Modification timestamp not set correctly", null, structure.getModificationTimestamp());
    }

    /**
     * Tests setId and getId methods.
     */
    public void testSetAndGetId() {
        long id = 64;
        structure.setId(id);
        assertEquals("SetId or getId not functions correctly", id, structure.getId());
    }

    /**
     * Tests setCreationUser and getCreationUser methods.
     */
    public void testSetAndGetCreationUser() {
        String user = "admin";
        structure.setCreationUser(user);
        assertEquals("SetCreationUser or getCreationUser not functions correctly", user, structure.getCreationUser());
        structure.setCreationUser(null);
        assertEquals("SetCreationUser or getCreationUser not functions correctly", null, structure.getCreationUser());
        structure.setCreationUser("");
        assertEquals("SetCreationUser or getCreationUser not functions correctly", "", structure.getCreationUser());
    }

    /**
     * Tests setCreationTimestamp and getCreationTimestamp methods.
     */
    public void testSetAndGetCreationTimestamp() {
        Date now = new Date();
        structure.setCreationTimestamp(now);
        assertEquals("SetCreationTimestamp or getCreationTimestamp not functions correctly", now,
            structure.getCreationTimestamp());
        structure.setCreationTimestamp(null);
        assertEquals("SetCreationTimestamp or getCreationTimestamp not functions correctly", null,
            structure.getCreationTimestamp());
    }

    /**
     * Tests setModificationUser and getModificationUser methods.
     */
    public void testSetAndGetModificationUser() {
        String user = "modification admin";
        structure.setModificationUser(user);
        assertEquals("SetModificationUser or getModificationUser not functions correctly", user,
            structure.getModificationUser());
        structure.setModificationUser(null);
        assertEquals("SetModificationUser or getModificationUser not functions correctly", null,
            structure.getModificationUser());
        structure.setModificationUser("");
        assertEquals("SetModificationUser or getModificationUser not functions correctly", "",
            structure.getModificationUser());
    }

    /**
     * Tests setModificationTimestamp and getModificationTimestamp methods.
     */
    public void testSetAndGetModificationTimestamp() {
        Date now = new Date();
        structure.setModificationTimestamp(now);
        assertEquals("SetModificationTimestamp or getModificationTimestamp not functions correctly", now,
            structure.getModificationTimestamp());
        structure.setModificationTimestamp(null);
        assertEquals("SetModificationTimestamp or getModificationTimestamp not functions correctly", null,
            structure.getModificationTimestamp());
    }

    /**
     * Tests isValidToPersist method with null creation user.
     */
    public void testIsValidToPersistNullCreationUser() {
        structure.setId(16);
        structure.setCreationTimestamp(new Date());
        structure.setModificationUser("modify");
        structure.setModificationTimestamp(new Date());
        assertEquals("IsValidToPersist not functions correctly, false should be returned.", false,
            ((EmptyDeliverableStructure) structure).isValidToPersist());
    }

    /**
     * Tests isValidToPersist method with null creation timestamp.
     */
    public void testIsValidToPersistNullCreationTimestamp() {
        structure.setId(16);
        structure.setCreationUser("create");
        structure.setModificationUser("modify");
        structure.setModificationTimestamp(new Date());
        assertEquals("IsValidToPersist not functions correctly, false should be returned.", false,
            ((EmptyDeliverableStructure) structure).isValidToPersist());
    }

    /**
     * Tests isValidToPersist method with null modification user.
     */
    public void testIsValidToPersistNullModificationUser() {
        structure.setId(16);
        structure.setCreationUser("create");
        structure.setCreationTimestamp(new Date());
        structure.setModificationTimestamp(new Date());
        assertEquals("IsValidToPersist not functions correctly, false should be returned.", false,
            ((EmptyDeliverableStructure) structure).isValidToPersist());
    }

    /**
     * Tests isValidToPersist method with null modification timestamp.
     */
    public void testIsValidToPersistNullModificationTimestamp() {
        structure.setId(16);
        structure.setCreationUser("create");
        structure.setCreationTimestamp(new Date());
        structure.setModificationUser("modify");
        assertEquals("IsValidToPersist not functions correctly, false should be returned.", false,
            ((EmptyDeliverableStructure) structure).isValidToPersist());
    }

    /**
     * Tests isValidToPersist method with modification timestamp before creation timestamp.
     *
     * @throws Exception pass to JUnit.
     */
    public void testIsValidToPersistModificationBeforeCreation()
        throws Exception {
        structure.setId(16);
        structure.setModificationUser("modify");
        structure.setModificationTimestamp(new Date());
        Thread.sleep(1000);
        structure.setCreationUser("create");
        structure.setCreationTimestamp(new Date());
        assertEquals("IsValidToPersist not functions correctly, false should be returned.", false,
            ((EmptyDeliverableStructure) structure).isValidToPersist());
    }

    /**
     * Tests isValidToPersist method when the structure is valid. True should be returned.
     */
    public void testIsValidToPersistValid() {
        structure.setId(16);
        structure.setCreationUser("create");
        structure.setCreationTimestamp(new Date());
        structure.setModificationUser("modify");
        structure.setModificationTimestamp(new Date());
        assertEquals("IsValidToPersist not functions correctly, true should be returned.", true,
            ((EmptyDeliverableStructure) structure).isValidToPersist());
    }

    /**
     * This class extends <code>AuditedDeliverableStructure</code>, making it possible to instantiate
     * <code>AuditedDeliverableStructure</code> and test it.
     */
    private class EmptyDeliverableStructure extends AuditedDeliverableStructure {
        /**
         * Default constructor. Simply call super().
         */
        public EmptyDeliverableStructure() {
            super();
        }

        /**
         * This constructor simply call super(id).
         *
         * @param id the id of this structure.
         */
        public EmptyDeliverableStructure(long id) {
            super(id);
        }

        /**
         * This method simply call super.isValidToPersist().
         *
         * @return true if the structure is valid to persist, false otherwise.
         */
        public boolean isValidToPersist() {
            return super.isValidToPersist();
        }
    }
}
