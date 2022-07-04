/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.data.accuracytests;

import com.topcoder.management.scorecard.data.WeightedScorecardStructure;

import junit.framework.TestCase;


/**
 * Tests for WeightedScorecardStructure class.
 *
 * @author crackme
 * @version 1.0
 */
public class TestWeightedScorecardStructure extends TestCase {
    /** the id of this instance. */
    private final long id = new Long(1234567).longValue();

    /** the weight of this instance. */
    private final float weight = 0.1234567f;

    /** A WeightedScorecardStructure instance used to test. */
    private WeightedScorecardStructure scorecard = null;

    /**
     * Setup the test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        scorecard = new WeightedScorecardStructureImpl(id, "scorecard", weight);
    }

    /**
     * Setup the test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        scorecard = null;
    }

    /**
     * Test WeightedScorecardStructure() method with accuracy state.
     */
    public void testWeightedScorecardStructureAccuracy1() {
        WeightedScorecardStructure structure = new WeightedScorecardStructureImpl();
        assertNull("constructor is wrong.", structure.getName());
        assertEquals("constructor is wrong.", -1, structure.getId());
    }

    /**
     * Test WeightedScorecardStructure(long id) method with accuracy state.
     */
    public void testWeightedScorecardStructureAccuracy2() {
        WeightedScorecardStructure structure = new WeightedScorecardStructureImpl(id);
        assertNull("constructor is wrong.", structure.getName());
        assertEquals("constructor is wrong.", id, structure.getId());
    }

    /**
     * Test WeightedScorecardStructure(long id, String name) method with accuracy state.
     */
    public void testWeightedScorecardStructureAccuracy3() {
        WeightedScorecardStructure structure = new WeightedScorecardStructureImpl(id,
                "new name");
        assertEquals("constructor is wrong.", "new name", structure.getName());
        assertEquals("constructor is wrong.", id, structure.getId());
    }

    /**
     * Test WeightedScorecardStructure(long id, float weight) method with accuracy state.
     */
    public void testWeightedScorecardStructureAccuracy4() {
        WeightedScorecardStructure structure = new WeightedScorecardStructureImpl(id,
                weight);
        assertNull("constructor is wrong.", structure.getName());
        assertEquals("constructor is wrong.", id, structure.getId());
        assertEquals("constructor is wrong.", weight, structure.getWeight(), 0);
    }

    /**
     * Tests setWeight(float weight) method with accuracy state.
     */
    public void testSetWeightAccuracy1() {
        float newWeight = 56.87654321f;
        scorecard.setWeight(newWeight);
        assertEquals("setWeight is wrong.", newWeight, scorecard.getWeight(), 0);
    }

    /**
     * Tests setWeight(float weight) method with accuracy state. The new weight is zero.
     */
    public void testSetWeightAccuracy2() {
        scorecard.setWeight(0);
        assertEquals("setWeight is wrong.", 0, scorecard.getWeight(), 0);
    }

    /**
     * Tests setWeight(float weight) method with accuracy state. The new weight is 100.
     */
    public void testSetWeightAccuracy3() {
        float newWeight = 100.00f;
        scorecard.setWeight(newWeight);
        assertEquals("setWeight is wrong.", newWeight, scorecard.getWeight(), 0);
    }

    /**
     * Tests resetWeight() method with accuracy state.
     */
    public void testResetWeightAccuracy() {
        scorecard.resetWeight();
        assertEquals("resetWeight is wrong.", 0, scorecard.getWeight(), 0);
    }

    /**
     * Tests getWeight() method with accuracy state.
     */
    public void testGetWeightAccuracy() {
        assertEquals("getWeight is wrong.", weight, scorecard.getWeight(), 0);
    }

    /**
     * This class extends from WeightedScorecardStructure used to test.
     */
    final class WeightedScorecardStructureImpl extends WeightedScorecardStructure {
        /**
         * Create a new WeightedScorecardStructure.
         */
        protected WeightedScorecardStructureImpl() {
            super();
        }

        /**
         * Create a new WeightedScorecardStructure.
         *
         * @param id The id of the scorecard structure
         *
         * @throws IllegalArgumentException If id lower than 0
         */
        protected WeightedScorecardStructureImpl(long id) {
            super(id);
        }

        /**
         * Create a new WeightedScorecardStructure.
         *
         * @param id The id of the scorecard structure
         * @param name The name of the scorecard structure
         *
         * @throws IllegalArgumentException If id lower than 0
         * @throws IllegalArgumentException If name is null
         */
        protected WeightedScorecardStructureImpl(long id, String name) {
            super(id, name);
        }

        /**
         * Create a new WeightedScorecardStructure.
         *
         * @param id The id of the scorecard structure
         * @param weight The relative weight of the structure (relative to others in its
         *        container)
         *
         * @throws IllegalArgumentException If id lower than 0
         * @throws IllegalArgumentException If weight is equals or lower than 0 or bigger
         *         than 100 (or NaN)
         */
        protected WeightedScorecardStructureImpl(long id, float weight) {
            super(id, weight);
        }

        /**
         * Create a new WeightedScorecardStructure.
         *
         * @param id The id of the scorecard structure
         * @param name The name of the scorecard structure
         * @param weight The relative weight of the structure (relative to others in its
         *        container)
         *
         * @throws IllegalArgumentException If id lower than 0
         * @throws IllegalArgumentException If name is null
         * @throws IllegalArgumentException If weight lower than 0 or bigger 100 (or NaN)
         */
        protected WeightedScorecardStructureImpl(long id, String name, float weight) {
            super(id, name, weight);
        }
    }
}
