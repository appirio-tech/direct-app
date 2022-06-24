/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.accuracytests;

import com.topcoder.project.phases.PhaseStatus;

import junit.framework.TestCase;

/**
 * Accuracy tests for <code>PhaseStatus</code> class.
 *
 * @author mayi
 * @version 2.0
 */
public class PhaseStatusAccuracyTest extends TestCase {
    /**
     * Represents the name of the phaseStatus instance.
     */
    private static final String NAME = "Aggregation Review";

    /**
     * Represents the id of the phaseStatus instance.
     */
    private static final long ID = 34999999987L;

    /**
     * A <code>PhaseStatus</code> instance to test against.
     */
    private PhaseStatus phaseStatus = null;

    /**
     * Create the <code>phaseStatus</code> instance to test.
     */
    protected void setUp() {
        phaseStatus = new PhaseStatus(ID, NAME);
    }

    /**
     * Test the const fields <code>SCHEDULED</code>.
     */
    public void testConstFields_SCHEDULED() {
        assertEquals("Invalid SCHEDULED id.", 1, PhaseStatus.SCHEDULED.getId());
        assertEquals("Invalid SCHEDULED name.", "Scheduled", PhaseStatus.SCHEDULED.getName());
    }

    /**
     * Test the const fields <code>OPEN</code>.
     */
    public void testConstFields_OPEN() {
        assertEquals("Invalid OPEN id.", 2, PhaseStatus.OPEN.getId());
        assertEquals("Invalid OPEN name.", "Open", PhaseStatus.OPEN.getName());
    }

    /**
     * Test the const fields <code>CLOSED</code>.
     */
    public void testConstFields_CLOSED() {
        assertEquals("Invalid CLOSED id.", 3, PhaseStatus.CLOSED.getId());
        assertEquals("Invalid CLOSED name.", "Closed", PhaseStatus.CLOSED.getName());
    }

    /**
     * Test constructor with empty name.
     * <p>Empty name should be allowed.</p>
     */
    public void testConstructor_EmptyName() {
        phaseStatus = new PhaseStatus(ID, "  ");
        assertEquals("Cannot construct with empty name.",
                "  ", phaseStatus.getName());
    }

    /**
     * Test constructor with id equals zero.
     * <p>Zero should be allowed.</p>
     */
    public void testConstructor_ZeroId() {
        phaseStatus = new PhaseStatus(0, NAME);
        assertEquals("Cannot construct with zero id.",
                0, phaseStatus.getId());
    }

    /**
     * Test <code>getId</code> after constrution.
     * <p>Id value should be returned.</p>
     */
    public void testGetId() {
        assertEquals("Cannot get id.", ID, phaseStatus.getId());
    }

    /**
     * Test <code>setId</code> with normal value.
     * <p>It can be retrieved by setId.</p>
     */
    public void testSetId_Normal() {
        phaseStatus.setId(567246);
        assertEquals("Cannot setId.", 567246, phaseStatus.getId());
    }

    /**
     * Test <code>setId</code> with value which is 0.
     * <p>It can be retrieved by setId.</p>
     */
    public void testSetId_Zero() {
        phaseStatus.setId(0);
        assertEquals("Cannot setId as 0.", 0, phaseStatus.getId());
    }

    /**
     * Test <code>getName</code> after constrution.
     * <p>Name value passed to constructor should be returned.</p>
     */
    public void testGetName() {
        assertEquals("Cannot get id.", NAME, phaseStatus.getName());
    }

    /**
     * Test <code>setName</code> with normal value.
     * <p>It can be retrieved by getName.</p>
     */
    public void testSetName_Normal() {
        phaseStatus.setName("foo");
        assertEquals("Cannot setName.", "foo", phaseStatus.getName());
    }

    /**
     * Test <code>setName</code> with value which is empty.
     * <p>It can be retrieved by getName.</p>
     */
    public void testSetName_Empty() {
        phaseStatus.setName("\t\n");
        assertEquals("Cannot setName as empty.", "\t\n", phaseStatus.getName());
    }
}
