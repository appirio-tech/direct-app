/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.data.failuretests;

import com.topcoder.management.scorecard.data.WeightedScorecardStructure;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Failure test cases for WeightedScorecardStructure.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class WeightedScorecardStructureFailureTests extends TestCase {
    /**
     * <p>
     * This constant represents the id for testing.
     * </p>
     */
    private static final long ID = 100;

    /**
     * <p>
     * This constant represents the name for testing.
     * </p>
     */
    private static final String NAME = "WeightedScorecardStructure";

    /**
     * <p>
     * This constant represents the weight for testing.
     * </p>
     */
    private static final float WEIGHT = 100f;

    /**
     * <p>
     * WeightedScorecardStructure instance for testing.
     * </p>
     */
    private WeightedScorecardStructure structure;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        structure = new DummyWeightedScorecardStructure();
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
        return new TestSuite(WeightedScorecardStructureFailureTests.class);
    }

    /**
     * <p>
     * Tests ctor WeightedScorecardStructure#WeightedScorecardStructure(long) for failure.
     * It tests the case that when id is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor1_NegativeId() {
        try {
            new DummyWeightedScorecardStructure(-45);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor WeightedScorecardStructure#WeightedScorecardStructure(long) for failure.
     * It tests the case that when id is zero and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor1_ZeroId() {
        try {
            new DummyWeightedScorecardStructure(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor WeightedScorecardStructure#WeightedScorecardStructure(long,String) for failure.
     * It tests the case that when id is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor2_NegativeId() {
        try {
            new DummyWeightedScorecardStructure(-45, NAME);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor WeightedScorecardStructure#WeightedScorecardStructure(long,String) for failure.
     * It tests the case that when id is zero and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor2_ZeroId() {
        try {
            new DummyWeightedScorecardStructure(0, NAME);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor WeightedScorecardStructure#WeightedScorecardStructure(long,String) for failure.
     * It tests the case that when name is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor2_NullName() {
        try {
            new DummyWeightedScorecardStructure(ID, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor WeightedScorecardStructure#WeightedScorecardStructure(long,float) for failure.
     * It tests the case that when id is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor3_NegativeId() {
        try {
            new DummyWeightedScorecardStructure(-45, WEIGHT);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor WeightedScorecardStructure#WeightedScorecardStructure(long,float) for failure.
     * It tests the case that when id is zero and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor3_ZeroId() {
        try {
            new DummyWeightedScorecardStructure(0, WEIGHT);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor WeightedScorecardStructure#WeightedScorecardStructure(long,float) for failure.
     * It tests the case that when weight is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor3_NegativeWeight() {
        try {
            new DummyWeightedScorecardStructure(ID, -65);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor WeightedScorecardStructure#WeightedScorecardStructure(long,float) for failure.
     * It tests the case that when weight is over 100 and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor3_TooLargeWeight() {
        try {
            new DummyWeightedScorecardStructure(ID, 154);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor WeightedScorecardStructure#WeightedScorecardStructure(long,float) for failure.
     * It tests the case that when weight is NaN and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor3_NaNWeight() {
        try {
            new DummyWeightedScorecardStructure(ID, Float.NaN);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor WeightedScorecardStructure#WeightedScorecardStructure(long,String,float) for failure.
     * It tests the case that when id is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor4_NegativeId() {
        try {
            new DummyWeightedScorecardStructure(-45, NAME, WEIGHT);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor WeightedScorecardStructure#WeightedScorecardStructure(long,String,float) for failure.
     * It tests the case that when id is zero and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor4_ZeroId() {
        try {
            new DummyWeightedScorecardStructure(0, NAME, WEIGHT);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor WeightedScorecardStructure#WeightedScorecardStructure(long,String,float) for failure.
     * It tests the case that when name is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor4_NullName() {
        try {
            new DummyWeightedScorecardStructure(ID, null, WEIGHT);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor WeightedScorecardStructure#WeightedScorecardStructure(long,String,float) for failure.
     * It tests the case that when weight is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor4_NegativeWeight() {
        try {
            new DummyWeightedScorecardStructure(ID, NAME, -65);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor WeightedScorecardStructure#WeightedScorecardStructure(long,String,float) for failure.
     * It tests the case that when weight is over 100 and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor4_TooLargeWeight() {
        try {
            new DummyWeightedScorecardStructure(ID, NAME, 154);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor WeightedScorecardStructure#WeightedScorecardStructure(long,String,float) for failure.
     * It tests the case that when weight is NaN and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor4_NaNWeight() {
        try {
            new DummyWeightedScorecardStructure(ID, NAME, Float.NaN);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor WeightedScorecardStructure#setWeight(float) for failure.
     * It tests the case that when weight is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testSetWeight_NegativeWeight() {
        try {
            structure.setWeight(-98f);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor WeightedScorecardStructure#setWeight(float) for failure.
     * It tests the case that when weight is too large and expects IllegalArgumentException.
     * </p>
     */
    public void testSetWeight_TooLargeWeight() {
        try {
            structure.setWeight(457f);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor WeightedScorecardStructure#setWeight(float) for failure.
     * It tests the case that when weight is NaN and expects IllegalArgumentException.
     * </p>
     */
    public void testSetWeight_NaNWeight() {
        try {
            structure.setWeight(Float.NaN);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * This class extends WeightedScorecardStructure and is used for testing.
     * </p>
     *
     * @author biotrail
     * @version 1.0
     */
    private static final class DummyWeightedScorecardStructure extends WeightedScorecardStructure {
        /**
         * <p>
         * Creates a new DummyWeightedScorecardStructure instance.
         * </p>
         */
        public DummyWeightedScorecardStructure() {
            super();
        }

        /**
         * <p>
         * Creates a new DummyWeightedScorecardStructure instance.
         * </p>
         *
         * @param id id
         * @param weight weight
         */
        public DummyWeightedScorecardStructure(long id, float weight) {
            super(id, weight);
        }

        /**
         * <p>
         * Creates a new DummyWeightedScorecardStructure instance.
         * </p>
         *
         * @param id id
         * @param name name
         * @param weight weight
         */
        public DummyWeightedScorecardStructure(long id, String name, float weight) {
            super(id, name, weight);
        }

        /**
         * <p>
         * Creates a new DummyWeightedScorecardStructure instance.
         * </p>
         *
         * @param id id
         * @param name name
         */
        public DummyWeightedScorecardStructure(long id, String name) {
            super(id, name);
        }

        /**
         * <p>
         * Creates a new DummyWeightedScorecardStructure instance.
         * </p>
         *
         * @param id id
         */
        public DummyWeightedScorecardStructure(long id) {
            super(id);
        }

    }
}