/*
 * Copyright (C) 2006,2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

import java.util.Date;

import junit.framework.TestCase;

/**
 * Unit test for AuditedDeliverableStructure.
 * <p>
 * Changes in version 1.2:
 * <ul>
 * <li>Refactor out the test cases for IdentifiableDeliverableStructure super class.</li>
 * </ul>
 * </p>
 *
 * @author singlewood
 * @author TCSDEVELOPER
 * @version 1.2
 */
public class AuditedDeliverableStructureTests extends TestCase {

    /**
     * The test AuditedDeliverableStructure instance.
     */
    private AuditedDeliverableStructure auditedDeliverable = null;

    /**
     * The test Date instance.
     */
    private Date date = null;

    /**
     * Create the test instance.
     *
     * @throws Exception exception to JUnit.
     */
    public void setUp() throws Exception {
        auditedDeliverable = new AuditedDeliverableStructureExtends();
        date = new Date();
    }

    /**
     * Clean the config.
     *
     * @throws Exception exception to JUnit.
     */
    public void tearDown() throws Exception {
        auditedDeliverable = null;
        date = null;
    }

    /**
     * The default constructor should set id to UNSET_ID. So check if id is set properly. No
     * exception should be thrown.
     */
    public void testConstructor1_Accuracy() {
        assertEquals("the constructor doesn't set id properly", AuditedDeliverableStructure.UNSET_ID,
                auditedDeliverable.getId());
    }

    /**
     * Test constructor2 with invalid parameters. The argument will be set to 0.
     * IllegalArgumentException should be thrown.
     */
    public void testConstructor2_InvalidLong1() {
        try {
            new AuditedDeliverableStructureExtends(0);
            fail("IllegalArgumentException should be thrown because of the invalid parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Test constructor2 with invalid parameters. The argument will be set to -2.
     * IllegalArgumentException should be thrown.
     */
    public void testConstructor2_InvalidLong2() {
        try {
            new AuditedDeliverableStructureExtends(-2);
            fail("IllegalArgumentException should be thrown because of invalid parameters.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Test constructor2 with valid parameter. Check if the constructor set the id fields properly.
     * No exception should be thrown.
     */
    public void testConstructor2_Accuracy1() {
        auditedDeliverable = new AuditedDeliverableStructureExtends(123);
        assertEquals("constructor doesn't work properly.", 123, auditedDeliverable.getId());
    }

    /**
     * Test the behavior of setCreationUser. Set the creationUser field, see if the getCreationUser
     * method can get the correct value. No exception should be thrown.
     */
    public void testSetCreationUser_Accuracy1() {
        auditedDeliverable.setCreationUser("user1");
        assertEquals("creationUser is not set properly.", "user1", auditedDeliverable.getCreationUser());
    }

    /**
     * Test the behavior of getCreationUser. Set the creationUser field, see if the getCreationUser
     * method can get the correct value. No exception should be thrown.
     */
    public void testGetCreationUser_Accuracy1() {
        auditedDeliverable.setCreationUser("user2");
        assertEquals("getCreationUser doesn't work properly.", "user2", auditedDeliverable.getCreationUser());
    }

    /**
     * Test the behavior of setCreationTimestamp. Set the creationTimestamp field, see if the
     * getCreationTimestamp method can get the correct value. No exception should be thrown.
     */
    public void testSetCreationTimestamp_Accuracy1() {
        auditedDeliverable.setCreationTimestamp(date);
        assertEquals("creationTimestamp is not set properly.", date, auditedDeliverable
                .getCreationTimestamp());
    }

    /**
     * Test the behavior of getCreationTimestamp. Set the creationTimestamp field, see if the
     * getCreationTimestamp method can get the correct value. No exception should be thrown.
     */
    public void testGetCreationTimestamp_Accuracy1() {
        auditedDeliverable.setCreationTimestamp(date);
        assertEquals("getCreationTimestamp doesn't work properly.", date, auditedDeliverable
                .getCreationTimestamp());
    }

    /**
     * Test the behavior of setModificationUser. Set the modificationUser field, see if the
     * getModificationUser method can get the correct value. No exception should be thrown.
     */
    public void testSetModificationUser_Accuracy1() {
        auditedDeliverable.setModificationUser("user1");
        assertEquals("modificationUser is not set properly.", "user1", auditedDeliverable
                .getModificationUser());
    }

    /**
     * Test the behavior of getModificationUser. Set the modificationUser field, see if the
     * getModificationUser method can get the correct value. No exception should be thrown.
     */
    public void testGetModificationUser_Accuracy1() {
        auditedDeliverable.setModificationUser("user2");
        assertEquals("getModificationUser doesn't work properly.", "user2", auditedDeliverable
                .getModificationUser());
    }

    /**
     * Test the behavior of setModificationTimestamp. Set the modificationTimestamp field, see if
     * the getModificationTimestamp method can get the correct value. No exception should be thrown.
     */
    public void testSetModificationTimestamp_Accuracy1() {
        auditedDeliverable.setModificationTimestamp(date);
        assertEquals("modificationTimestamp is not set properly.", date, auditedDeliverable
                .getModificationTimestamp());
    }

    /**
     * Test the behavior of getModificationTimestamp. Set the modificationTimestamp field, see if
     * the getModificationTimestamp method can get the correct value. No exception should be thrown.
     */
    public void testGetModificationTimestamp_Accuracy1() {
        auditedDeliverable.setModificationTimestamp(date);
        assertEquals("getModificationTimestamp doesn't work properly.", date, auditedDeliverable
                .getModificationTimestamp());
    }

    /**
     * Test the behavior of isValidToPersist. Set the creationUser field to null, see if the
     * isValidToPersist returns false. No exception should be thrown.
     */
    public void testIsValidToPersist_Accuracy1() {
        auditedDeliverable.setCreationUser(null);
        auditedDeliverable.setCreationTimestamp(date);
        auditedDeliverable.setModificationUser("ModificationUser");
        auditedDeliverable.setModificationTimestamp(date);
        assertEquals("isValidToPersist doesn't work properly.", false, auditedDeliverable.isValidToPersist());
    }

    /**
     * Test the behavior of isValidToPersist. Set the creationTimestamp field to null, see if the
     * isValidToPersist returns false. No exception should be thrown.
     */
    public void testIsValidToPersist_Accuracy2() {
        auditedDeliverable.setCreationUser("CreationUser");
        auditedDeliverable.setCreationTimestamp(null);
        auditedDeliverable.setModificationUser("ModificationUser");
        auditedDeliverable.setModificationTimestamp(date);
        assertEquals("isValidToPersist doesn't work properly.", false, auditedDeliverable.isValidToPersist());
    }

    /**
     * Test the behavior of isValidToPersist. Set the modificationUser field to null, see if the
     * isValidToPersist returns false. No exception should be thrown.
     */
    public void testIsValidToPersist_Accuracy3() {
        auditedDeliverable.setCreationUser("CreationUser");
        auditedDeliverable.setCreationTimestamp(date);
        auditedDeliverable.setModificationUser(null);
        auditedDeliverable.setModificationTimestamp(date);
        assertEquals("isValidToPersist doesn't work properly.", false, auditedDeliverable.isValidToPersist());
    }

    /**
     * Test the behavior of isValidToPersist. Set the modificationTimestamp field to null, see if
     * the isValidToPersist returns false. No exception should be thrown.
     */
    public void testIsValidToPersist_Accuracy4() {
        auditedDeliverable.setCreationUser("CreationUser");
        auditedDeliverable.setCreationTimestamp(date);
        auditedDeliverable.setModificationUser("ModificationUser");
        auditedDeliverable.setModificationTimestamp(null);
        assertEquals("isValidToPersist doesn't work properly.", false, auditedDeliverable.isValidToPersist());
    }

    /**
     * Test the behavior of isValidToPersist. Set the modificationTimestamp <= creationTimestamp, see if
     * the isValidToPersist returns false. No exception should be thrown.
     */
    public void testIsValidToPersist_Accuracy5() {
        auditedDeliverable.setCreationUser("CreationUser");
        auditedDeliverable.setCreationTimestamp(new Date(2000));
        auditedDeliverable.setModificationUser("ModificationUser");
        auditedDeliverable.setModificationTimestamp(new Date(1000));
        assertEquals("isValidToPersist doesn't work properly.", false, auditedDeliverable.isValidToPersist());
    }

    /**
     * Test the behavior of isValidToPersist. Set all the field with non-null values, see if the
     * isValidToPersist returns true. No exception should be thrown.
     */
    public void testIsValidToPersist_Accuracy6() {
        auditedDeliverable.setCreationUser("CreationUser");
        auditedDeliverable.setCreationTimestamp(date);
        auditedDeliverable.setModificationUser("ModificationUser");
        auditedDeliverable.setModificationTimestamp(date);
        assertEquals("isValidToPersist doesn't work properly.", true, auditedDeliverable.isValidToPersist());
    }

}
