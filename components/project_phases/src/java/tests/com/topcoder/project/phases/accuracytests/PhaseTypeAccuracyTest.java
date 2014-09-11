/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.accuracytests;

import com.topcoder.project.phases.PhaseType;

import junit.framework.TestCase;

/**
 * Accuracy tests for <code>PhaseType</code> class.
 *
 * @author mayi
 * @version 2.0
 */
public class PhaseTypeAccuracyTest extends TestCase {
    /**
     * Represents the name of the phaseType instance.
     */
    private static final String NAME = "Aggregation";

    /**
     * Represents the id of the phaseType instance.
     */
    private static final long ID = 34987;

    /**
     * A <code>PhaseType</code> instance to test against.
     */
    private PhaseType phaseType = null;

    /**
     * Create the <code>phaseType</code> instance to test.
     */
    protected void setUp() {
        phaseType = new PhaseType(ID, NAME);
    }

    /**
     * Test constructor with empty name.
     * <p>Empty name should be allowed.</p>
     */
    public void testConstructor_EmptyName() {
        phaseType = new PhaseType(ID, "  ");
        assertEquals("Cannot construct with empty name.",
                "  ", phaseType.getName());
    }

    /**
     * Test constructor with id equals zero.
     * <p>Zero should be allowed.</p>
     */
    public void testConstructor_ZeroId() {
        phaseType = new PhaseType(0, NAME);
        assertEquals("Cannot construct with zero id.",
                0, phaseType.getId());
    }

    /**
     * Test <code>getId</code> after constrution.
     * <p>Id value should be returned.</p>
     */
    public void testGetId() {
        assertEquals("Cannot get id.", ID, phaseType.getId());
    }

    /**
     * Test <code>setId</code> with normal value.
     * <p>It can be retrieved by setId.</p>
     */
    public void testSetId_Normal() {
        phaseType.setId(567246);
        assertEquals("Cannot setId.", 567246, phaseType.getId());
    }

    /**
     * Test <code>setId</code> with value which is 0.
     * <p>It can be retrieved by setId.</p>
     */
    public void testSetId_Zero() {
        phaseType.setId(0);
        assertEquals("Cannot setId as 0.", 0, phaseType.getId());
    }

    /**
     * Test <code>getName</code> after constrution.
     * <p>Name value passed to constructor should be returned.</p>
     */
    public void testGetName() {
        assertEquals("Cannot get id.", NAME, phaseType.getName());
    }

    /**
     * Test <code>setName</code> with normal value.
     * <p>It can be retrieved by getName.</p>
     */
    public void testSetName_Normal() {
        phaseType.setName("foo");
        assertEquals("Cannot setName.", "foo", phaseType.getName());
    }

    /**
     * Test <code>setName</code> with value which is empty.
     * <p>It can be retrieved by getName.</p>
     */
    public void testSetName_Empty() {
        phaseType.setName("\t\n");
        assertEquals("Cannot setName as empty.", "\t\n", phaseType.getName());
    }
}
