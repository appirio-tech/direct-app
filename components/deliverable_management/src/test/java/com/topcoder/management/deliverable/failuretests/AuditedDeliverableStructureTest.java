/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.failuretests;

import junit.framework.TestCase;

/**
 * Failure tests for class <code>AuditedDeliverableStructure</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class AuditedDeliverableStructureTest extends TestCase {

    /**
     * Represents the {@link AuditedDeliverableStructure} to test.
     */
    private MockAuditedDeliverableStructure stru;


    /**
     * Set up the environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        stru = new MockAuditedDeliverableStructure();
    }

    /**
     * Test method for AuditedDeliverableStructure(long).
     * In this case, the id is zero.
     * Expected exception : {@link IllegalArgumentException}.
     */
    public void testAuditedDeliverableStructureZeroId() {
        try {
            new MockAuditedDeliverableStructure(0);
            fail("IllegalArgumentException e");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for AuditedDeliverableStructure(long).
     * In this case, the id is negative.
     * Expected exception : {@link IllegalArgumentException}.
     */
    public void testAuditedDeliverableStructureNegativeId() {
        try {
            new MockAuditedDeliverableStructure(-1);
            fail("IllegalArgumentException e");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for setId(long).
     * In this case, the id is zero.
     * Expected exception : {@link IllegalArgumentException}.
     */
    public void testSetIdZeroId() {
        try {
            stru.setId(0);
            fail("IllegalArgumentException e");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for setId(long).
     * In this case, the id is negative.
     * Expected exception : {@link IllegalArgumentException}.
     */
    public void testSetIdNegativeId() {
        try {
            stru.setId(-1);
            fail("IllegalArgumentException e");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for setCreationUser(java.lang.String).
     * Nothing should be thrown.
     */
    public void testSetCreationUser() {
        try {
            stru.setCreationUser(null);
        } catch (Exception e) {
            fail("Nothing should be thrown.");
        }
    }

    /**
     * Test method for setCreationTimestamp(java.util.Date).
     * Nothing should be thrown.
     */
    public void testSetCreationTimestamp() {
        try {
            stru.setCreationTimestamp(null);
        } catch (Exception e) {
            fail("Nothing should be thrown.");
        }
    }

    /**
     * Test method for setModificationUser(java.lang.String).
     * Nothing should be thrown.
     */
    public void testSetModificationUser() {
        try {
            stru.setModificationUser(null);
        } catch (Exception e) {
            fail("Nothing should be thrown.");
        }
    }

    /**
     * Test method for setModificationTimestamp(java.util.Date).
     * Nothing should be thrown.
     */
    public void testSetModificationTimestamp() {
        try {
            stru.setModificationTimestamp(null);
        } catch (Exception e) {
            fail("Nothing should be thrown.");
        }
    }

}
