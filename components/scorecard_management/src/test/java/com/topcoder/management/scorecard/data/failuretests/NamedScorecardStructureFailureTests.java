/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.data.failuretests;

import com.topcoder.management.scorecard.data.NamedScorecardStructure;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Failure test cases for NamedScorecardStructure.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class NamedScorecardStructureFailureTests extends TestCase {
    /**
     * <p>
     * This constant represents the id value for testing.
     * </p>
     */
    private static final long ID = 100;

    /**
     * <p>
     * This constant represents the name value for testing.
     * </p>
     */
    private static final String NAME = "NamedScorecardStructure";

    /**
     * <p>
     * NamedScorecardStructure instance for testing.
     * </p>
     */
    private DummyNamedScorecardStructure structure;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        structure = new DummyNamedScorecardStructure();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
        structure = null;
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(NamedScorecardStructureFailureTests.class);
    }

    /**
     * <p>
     * Tests ctor NamedScorecardStructure#NamedScorecardStructure(long) for failure.
     * It tests the case that when id is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor1_NegativeId() {
        try {
            new DummyNamedScorecardStructure(-6);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor NamedScorecardStructure#NamedScorecardStructure(long) for failure.
     * It tests the case that when id is zero and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor1_ZeroId() {
        try {
            new DummyNamedScorecardStructure(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor NamedScorecardStructure#NamedScorecardStructure(long,String) for failure.
     * It tests the case that when id is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor2_NegativeId() {
        try {
            new DummyNamedScorecardStructure(-63, NAME);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor NamedScorecardStructure#NamedScorecardStructure(long,String) for failure.
     * It tests the case that when id is zero and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor2_ZeroId() {
        try {
            new DummyNamedScorecardStructure(0, NAME);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor NamedScorecardStructure#NamedScorecardStructure(long,String) for failure.
     * It tests the case that when name is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor2_NullName() {
        try {
            new DummyNamedScorecardStructure(ID, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests NamedScorecardStructure#setId(long) for failure.
     * It tests the case that when id is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testSetId_NegativeId() {
        try {
            structure.setId(-85);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests NamedScorecardStructure#setId(long) for failure.
     * It tests the case that when id is zero and expects IllegalArgumentException.
     * </p>
     */
    public void testSetId_ZeroId() {
        try {
            structure.setId(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * This class extends NamedScorecardStructure and is used for testing.
     * </p>
     *
     * @author biotrail
     * @version 1.0
     */
    private static final class DummyNamedScorecardStructure extends NamedScorecardStructure {
        /**
         * <p>
         * Creates a new DummyNamedScorecardStructure instance.
         * </p>
         */
        public DummyNamedScorecardStructure() {
            super();
        }

        /**
         * <p>
         * Creates a new DummyNamedScorecardStructure instance.
         * </p>
         *
         * @param id id
         * @param name name
         */
        public DummyNamedScorecardStructure(long id, String name) {
            super(id, name);
        }

        /**
         * <p>
         * Creates a new DummyNamedScorecardStructure instance.
         * </p>
         *
         * @param id id
         */
        public DummyNamedScorecardStructure(long id) {
            super(id);
        }

    }
}