/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

import java.util.Date;

import junit.framework.TestCase;

/**
 * Unit test for NamedDeliverableStructure.
 *
 * @author singlewood
 * @version 1.0
 */
public class NamedDeliverableStructureTests extends TestCase {

    /**
     * The test NamedDeliverableStructure instance.
     */
    private NamedDeliverableStructure namedDeliverable = null;

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
        namedDeliverable = new NamedDeliverableStructureExtends();
        date = new Date();

        // set all the field of AuditedDeliverableStructure
        namedDeliverable.setCreationUser("CreationUser");
        namedDeliverable.setCreationTimestamp(date);
        namedDeliverable.setModificationUser("ModificationUser");
        namedDeliverable.setModificationTimestamp(date);
    }

    /**
     * Clean the config.
     *
     * @throws Exception exception to JUnit.
     */
    public void tearDown() throws Exception {
        namedDeliverable = null;
        date = null;
    }

    /**
     * The default constructor should set id to UNSET_ID. So check if id is set properly. No
     * exception should be thrown.
     */
    public void testConstructor1_Accuracy1() {
        assertEquals("the constructor doesn't set id properly", NamedDeliverableStructureExtends.UNSET_ID,
                namedDeliverable.getId());
    }

    /**
     * The default constructor should set name to null. So check if name is set properly. No
     * exception should be thrown.
     */
    public void testConstructor1_Accuracy2() {
        assertEquals("the constructor doesn't set name properly", null, namedDeliverable.getName());
    }

    /**
     * Test constructor2 with invalid parameters. The argument will be set to 0.
     * IllegalArgumentException should be thrown.
     */
    public void testConstructor2_InvalidLong1() {
        try {
            new NamedDeliverableStructureExtends(0);
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
            new NamedDeliverableStructureExtends(-2);
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
        namedDeliverable = new NamedDeliverableStructureExtends(123);
        assertEquals("constructor doesn't work properly.", 123, namedDeliverable.getId());
    }

    /**
     * Test constructor3 with invalid parameters. The first argument will be set to 0.
     * IllegalArgumentException should be thrown.
     */
    public void testConstructor3_InvalidLong1() {
        try {
            new NamedDeliverableStructureExtends(0, null);
            fail("IllegalArgumentException should be thrown because of the null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Test constructor3 with invalid parameters. The first argument will be set to -3.
     * IllegalArgumentException should be thrown.
     */
    public void testConstructor3_InvalidLong2() {
        try {
            new NamedDeliverableStructureExtends(-3, null);
            fail("IllegalArgumentException should be thrown because of invalid parameters.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Test constructor3 with valid parameter. Check if the constructor set the name fields properly.
     * No exception should be thrown.
     */
    public void testConstructor3_Accuracy1() {
        namedDeliverable = new NamedDeliverableStructureExtends(133, "name");
        assertEquals("constructor doesn't work properly.", "name", namedDeliverable.getName());
    }

    /**
     * Test the behavior of setName. Set the name field, see if the getName
     * method can get the correct value. No exception should be thrown.
     */
    public void testSetName_Accuracy1() {
        namedDeliverable.setName("user1");
        assertEquals("name is not set properly.", "user1", namedDeliverable.getName());
    }

    /**
     * Test the behavior of getName. Set the name field, see if the getName
     * method can get the correct value. No exception should be thrown.
     */
    public void testGetName_Accuracy1() {
        namedDeliverable.setName("user2");
        assertEquals("getName doesn't work properly.", "user2", namedDeliverable.getName());
    }

    /**
     * Test the behavior of setDescription. Set the description field, see if the
     * getDescription method can get the correct value. No exception should be thrown.
     */
    public void testSetDescription_Accuracy1() {
        namedDeliverable.setDescription("user1");
        assertEquals("description is not set properly.", "user1", namedDeliverable
                .getDescription());
    }

    /**
     * Test the behavior of getDescription. Set the description field, see if the
     * getDescription method can get the correct value. No exception should be thrown.
     */
    public void testGetDescription_Accuracy1() {
        namedDeliverable.setDescription("user2");
        assertEquals("getDescription doesn't work properly.", "user2", namedDeliverable
                .getDescription());
    }

    /**
     * Test the behavior of isValidToPersist. Set the name field to null, see if the
     * isValidToPersist returns false. No exception should be thrown.
     */
    public void testIsValidToPersist_Accuracy1() {
        namedDeliverable.setName(null);
        namedDeliverable.setDescription("Description");
        namedDeliverable.setId(1);
        assertEquals("isValidToPersist doesn't work properly.", false, namedDeliverable.isValidToPersist());
    }

    /**
     * Test the behavior of isValidToPersist. Set the description field to null, see if the
     * isValidToPersist returns false. No exception should be thrown.
     */
    public void testIsValidToPersist_Accuracy2() {
        namedDeliverable.setName("name");
        namedDeliverable.setDescription(null);
        namedDeliverable.setId(1);
        assertEquals("isValidToPersist doesn't work properly.", false, namedDeliverable.isValidToPersist());
    }

    /**
     * Test the behavior of isValidToPersist. Do not set id field, see if the
     * isValidToPersist returns false. No exception should be thrown.
     */
    public void testIsValidToPersist_Accuracy3() {
        namedDeliverable.setName("name");
        namedDeliverable.setDescription("Description");
        assertEquals("isValidToPersist doesn't work properly.", false, namedDeliverable.isValidToPersist());
    }

    /**
     * Test the behavior of isValidToPersist. Set super.isValidToPersist() to false, see if the
     * isValidToPersist returns false. No exception should be thrown.
     */
    public void testIsValidToPersist_Accuracy4() {
        namedDeliverable.setName("name");
        namedDeliverable.setDescription("Description");
        namedDeliverable.setId(1);
        namedDeliverable.setCreationUser(null);
        assertEquals("isValidToPersist doesn't work properly.", false, namedDeliverable.isValidToPersist());
    }

    /**
     * Test the behavior of isValidToPersist. Set all the field with non-null values, see if the
     * isValidToPersist returns true. No exception should be thrown.
     */
    public void testIsValidToPersist_Accuracy5() {
        namedDeliverable.setName("name");
        namedDeliverable.setDescription("Description");
        namedDeliverable.setId(1);
        assertEquals("isValidToPersist doesn't work properly.", true, namedDeliverable.isValidToPersist());
    }

}
