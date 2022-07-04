/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.data.failuretests;

import com.topcoder.management.scorecard.data.Group;
import com.topcoder.management.scorecard.data.Section;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Failure test cases for Group.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class GroupFailureTests extends TestCase {
    /**
     * <p>
     * This constant represents the group id for testing.
     * </p>
     */
    private static final long ID = 100;

    /**
     * <p>
     * This constant represents the group name for testing.
     * </p>
     */
    private static final String NAME = "GroupOne";

    /**
     * <p>
     * This constant represents the group weight for testing.
     * </p>
     */
    private static final float WEIGHT = 100f;

    /**
     * <p>
     * Group instance for testing.
     * </p>
     */
    private Group group;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        group = new Group(ID, NAME, WEIGHT);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
        group = null;
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(GroupFailureTests.class);
    }

    /**
     * <p>
     * Tests ctor Group#Group(long) for failure.
     * It tests the case that when id is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor1_NegativeId() {
        try {
            new Group(-45);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Group#Group(long) for failure.
     * It tests the case that when id is zero and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor1_ZeroId() {
        try {
            new Group(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Group#Group(long,String) for failure.
     * It tests the case that when id is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor2_NegativeId() {
        try {
            new Group(-45, NAME);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Group#Group(long,String) for failure.
     * It tests the case that when id is zero and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor2_ZeroId() {
        try {
            new Group(0, NAME);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Group#Group(long,String) for failure.
     * It tests the case that when name is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor2_NullName() {
        try {
            new Group(ID, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Group#Group(long,float) for failure.
     * It tests the case that when id is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor3_NegativeId() {
        try {
            new Group(-45, WEIGHT);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Group#Group(long,float) for failure.
     * It tests the case that when id is zero and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor3_ZeroId() {
        try {
            new Group(0, WEIGHT);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Group#Group(long,float) for failure.
     * It tests the case that when weight is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor3_NegativeWeight() {
        try {
            new Group(ID, -65);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Group#Group(long,float) for failure.
     * It tests the case that when weight is over 100 and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor3_TooLargeWeight() {
        try {
            new Group(ID, 154);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Group#Group(long,float) for failure.
     * It tests the case that when weight is NaN and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor3_NaNWeight() {
        try {
            new Group(ID, Float.NaN);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Group#Group(long,String,float) for failure.
     * It tests the case that when id is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor4_NegativeId() {
        try {
            new Group(-45, NAME, WEIGHT);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Group#Group(long,String,float) for failure.
     * It tests the case that when id is zero and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor4_ZeroId() {
        try {
            new Group(0, NAME, WEIGHT);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Group#Group(long,String,float) for failure.
     * It tests the case that when name is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor4_NullName() {
        try {
            new Group(ID, null, WEIGHT);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Group#Group(long,String,float) for failure.
     * It tests the case that when weight is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor4_NegativeWeight() {
        try {
            new Group(ID, NAME, -65);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Group#Group(long,String,float) for failure.
     * It tests the case that when weight is over 100 and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor4_TooLargeWeight() {
        try {
            new Group(ID, NAME, 154);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Group#Group(long,String,float) for failure.
     * It tests the case that when weight is NaN and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor4_NaNWeight() {
        try {
            new Group(ID, NAME, Float.NaN);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Group#addSection(Section) for failure.
     * It tests the case that when section is null and expects IllegalArgumentException.
     * </p>
     */
    public void testAddSection_NullSection() {
        try {
            group.addSection(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Group#addSections(Section[]) for failure.
     * It tests the case that when sections is null and expects IllegalArgumentException.
     * </p>
     */
    public void testAddSections_NullSections() {
        try {
            group.addSections(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Group#addSections(Section[]) for failure.
     * It tests the case that when sections contains null section and expects IllegalArgumentException.
     * </p>
     */
    public void testAddSections_NullSectionInSections() {
        try {
            group.addSections(new Section[] {new Section(ID), null});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Group#removeSection(Section) for failure.
     * It tests the case that when section is null and expects IllegalArgumentException.
     * </p>
     */
    public void testRemoveSection_NullSection() {
        try {
            group.removeSection(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Group#removeSection(int) for failure.
     * It tests the case that when index is negative and expects IndexOutOfBoundsException.
     * </p>
     */
    public void testRemoveSection_NegativeIndex() {
        try {
            group.removeSection(-4);
            fail("IndexOutOfBoundsException expected.");
        } catch (IndexOutOfBoundsException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests Group#removeSection(int) for failure.
     * It tests the case that when index is too large and expects IndexOutOfBoundsException.
     * </p>
     */
    public void testRemoveSection_TooLargeIndex() {
        try {
            group.removeSection(44);
            fail("IndexOutOfBoundsException expected.");
        } catch (IndexOutOfBoundsException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests Group#getSection(int) for failure.
     * It tests the case that when index is negative and expects IndexOutOfBoundsException.
     * </p>
     */
    public void testGetSection_NegativeIndex() {
        try {
            group.getSection(-4);
            fail("IndexOutOfBoundsException expected.");
        } catch (IndexOutOfBoundsException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests Group#getSection(int) for failure.
     * It tests the case that when index is too large and expects IndexOutOfBoundsException.
     * </p>
     */
    public void testGetSection_TooLargeIndex() {
        try {
            group.getSection(44);
            fail("IndexOutOfBoundsException expected.");
        } catch (IndexOutOfBoundsException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests Group#removeSections(Section[]) for failure.
     * It tests the case that when sections is null and expects IllegalArgumentException.
     * </p>
     */
    public void testRemoveSections_NullSections() {
        try {
            group.removeSections(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Group#removeSections(Section[]) for failure.
     * It tests the case that when sections contains null section and expects IllegalArgumentException.
     * </p>
     */
    public void testRemoveSections_NullSectionInSections() {
        try {
            group.removeSections(new Section[] {new Section(ID), null});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Group#insertSection(Section,int) for failure.
     * It tests the case that when section is null and expects IllegalArgumentException.
     * </p>
     */
    public void testInsertSection_NullSection() {
        try {
            group.insertSection(null, 0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Group#insertSection(Section,int) for failure.
     * It tests the case that when index is negative and expects IndexOutOfBoundsException.
     * </p>
     */
    public void testInsertSection_NegativeIndex() {
        try {
            group.insertSection(new Section(ID), -6);
            fail("IndexOutOfBoundsException expected.");
        } catch (IndexOutOfBoundsException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests Group#insertSection(Section,int) for failure.
     * It tests the case that when index is too large and expects IndexOutOfBoundsException.
     * </p>
     */
    public void testInsertSection_TooLargeIndex() {
        try {
            group.insertSection(new Section(ID), 34);
            fail("IndexOutOfBoundsException expected.");
        } catch (IndexOutOfBoundsException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests Group#checkWeights(float) for failure.
     * It tests the case that when tolerance is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCheckWeights_NegativeTolerance() {
        try {
            group.checkWeights(-9f);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Group#checkWeights(float) for failure.
     * It tests the case that when tolerance is positive infinite and expects IllegalArgumentException.
     * </p>
     */
    public void testCheckWeights_PositiveInfiniteTolerance() {
        try {
            group.checkWeights(Float.POSITIVE_INFINITY);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Group#checkWeights(float) for failure.
     * It tests the case that when tolerance is NaN and expects IllegalArgumentException.
     * </p>
     */
    public void testCheckWeights_NaNTolerance() {
        try {
            group.checkWeights(Float.NaN);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }
}