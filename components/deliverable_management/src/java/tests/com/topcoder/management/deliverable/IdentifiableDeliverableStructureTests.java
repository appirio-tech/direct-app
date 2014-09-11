/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

import junit.framework.TestCase;

/**
 * Unit test for IdentifiableDeliverableStructure.
 *
 * @author TCSDEVELOPER
 * @version 1.2
 * @since 1.2
 */
public class IdentifiableDeliverableStructureTests extends TestCase {

    /**
     * The test IdentifiableDeliverableStructure instance.
     */
    private IdentifiableDeliverableStructure identifiableDeliverable = null;

    /**
     * Create the test instance.
     *
     * @throws Exception
     *             exception to JUnit.
     */
    public void setUp() throws Exception {
        identifiableDeliverable = new DummyIdentifiableDeliverableStructure();
    }

    /**
     * Clean the config.
     *
     * @throws Exception
     *             exception to JUnit.
     */
    public void tearDown() throws Exception {
        identifiableDeliverable = null;
    }

    /**
     * The default constructor should set id to UNSET_ID. So check if id is set properly. No exception should be thrown.
     */
    public void testConstructor1_Accuracy() {
        assertEquals("the constructor doesn't set id properly", AuditedDeliverableStructure.UNSET_ID,
                identifiableDeliverable.getId());
    }

    /**
     * Test constructor2 with invalid parameters. The argument will be set to 0. IllegalArgumentException should be
     * thrown.
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
     * Test constructor2 with invalid parameters. The argument will be set to -2. IllegalArgumentException should be
     * thrown.
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
     * Test constructor2 with valid parameter. Check if the constructor set the id fields properly. No exception should
     * be thrown.
     */
    public void testConstructor2_Accuracy() {
        identifiableDeliverable = new AuditedDeliverableStructureExtends(123);
        assertEquals("constructor doesn't work properly.", 123, identifiableDeliverable.getId());
    }

    /**
     * Test the behavior of setId. Set the id field with 0. IllegalArgumentException should be thrown.
     */
    public void testSetId_Invalid1() {
        try {
            identifiableDeliverable.setId(0);
            fail("IllegalArgumentException should be thrown because of the invalid parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Test the behavior of setId. Set the id field when it was already set. IdAlreadySetException should be thrown.
     */
    public void testSetId_Invalid2() {
        try {
            identifiableDeliverable.setId(1);

            // set again
            identifiableDeliverable.setId(1);
            fail("IdAlreadySetException should be thrown because id was set ");
        } catch (IdAlreadySetException ise) {
            // expected.
        }
    }

    /**
     * Test the behavior of setId. Set the id field, see if the getId method can get the correct value. No exception
     * should be thrown.
     */
    public void testSetId_Accuracy() {
        identifiableDeliverable.setId(111);
        assertEquals("id is not set properly.", 111, identifiableDeliverable.getId());
    }

    /**
     * Test the behavior of getId. Set the id field, see if the getId method can get the correct value. No exception
     * should be thrown.
     */
    public void testGetId_Accuracy() {
        identifiableDeliverable.setId(222);
        assertEquals("getId doesn't work properly.", 222, identifiableDeliverable.getId());
    }
}
