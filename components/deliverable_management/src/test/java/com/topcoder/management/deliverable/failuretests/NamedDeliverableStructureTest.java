/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.failuretests;

import junit.framework.TestCase;

import com.topcoder.management.deliverable.NamedDeliverableStructure;

/**
 * Failure tests for class <code>Submission</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class NamedDeliverableStructureTest extends TestCase {

    /**
     * Represents the {@link NamedDeliverableStructure} to test.
     */
    private MockNamedDeliverableStructure stru;

    /**
     * Set up the environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        stru = new MockNamedDeliverableStructure();
    }

    /**
     * Test the constructor with id.
     * The id is zero.
     * Expected : {@link IllegalArgumentException}
     *
     */
    public void testNamedDeliverableStructureLongZeroId() {
        try {
            new MockNamedDeliverableStructure(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test the constructor with id.
     * The id is negative.
     * Expected : {@link IllegalArgumentException}
     *
     */
    public void testNamedDeliverableStructureLongNegativeId() {
        try {
            new MockNamedDeliverableStructure(-1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test the constructor with id and name.
     * The id is zero.
     * Expected : {@link IllegalArgumentException}
     *
     */
    public void testNamedDeliverableStructureLongStringZeroId() {
        try {
            new MockNamedDeliverableStructure(0, "name");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test the constructor with id and name.
     * The id is zero.
     * Expected : {@link IllegalArgumentException}
     *
     */
    public void testNamedDeliverableStructureLongStringNegativeId() {
        try {
            new MockNamedDeliverableStructure(-1, "name");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test the method setName.
     * The name is null.
     * Expected : nothing
     *
     */
    public void testSetName() {
        try {
            stru.setName(null);
        } catch (Exception e) {
            fail("Nothing should be thrown.");
        }
    }

    /**
     * Test the method setDescription.
     * The description is null.
     * Expected : nothing
     *
     */
    public void testSetDescription() {
        try {
            stru.setDescription(null);
        } catch (Exception e) {
            fail("Nothing should be thrown.");
        }
    }

}
