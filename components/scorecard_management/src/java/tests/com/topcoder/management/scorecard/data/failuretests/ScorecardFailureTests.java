/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.data.failuretests;

import com.topcoder.management.scorecard.data.Group;
import com.topcoder.management.scorecard.data.Scorecard;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Failure test cases for Scorecard.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class ScorecardFailureTests extends TestCase {
    /**
     * <p>
     * Scorecard instance for testing.
     * </p>
     */
    private Scorecard scorecard;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        scorecard = new Scorecard();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
        scorecard = null;
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ScorecardFailureTests.class);
    }

    /**
     * <p>
     * Tests ctor Scorecard#Scorecard(long) for failure.
     * It tests the case that when id is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor1_NegativeId() {
        try {
            new Scorecard(-98);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Scorecard#Scorecard(long) for failure.
     * It tests the case that when id is zero and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor1_ZeroId() {
        try {
            new Scorecard(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Scorecard#Scorecard(long,String) for failure.
     * It tests the case that when id is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor2_NegativeId() {
        try {
            new Scorecard(-98, "GroupType");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Scorecard#Scorecard(long,String) for failure.
     * It tests the case that when id is zero and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor2_ZeroId() {
        try {
            new Scorecard(0, "GroupType");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Scorecard#Scorecard(long,String) for failure.
     * It tests the case that when name is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor2_NullName() {
        try {
            new Scorecard(100, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Scorecard#addGroup(Group) for failure.
     * It tests the case that when group is null and expects IllegalArgumentException.
     * </p>
     */
    public void testAddGroup_NullGroup() {
        try {
            scorecard.addGroup(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Scorecard#addGroups(Group[]) for failure.
     * It tests the case that when groups is null and expects IllegalArgumentException.
     * </p>
     */
    public void testAddGroups_NullGroups() {
        try {
            scorecard.addGroups(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Scorecard#addGroups(Group[]) for failure.
     * It tests the case that when groups contains null group and expects IllegalArgumentException.
     * </p>
     */
    public void testAddGroups_NullGroupInGroups() {
        try {
            scorecard.addGroups(new Group[] {new Group(), null});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Scorecard#removeGroup(Group) for failure.
     * It tests the case that when group is null and expects IllegalArgumentException.
     * </p>
     */
    public void testRemoveGroup_NullGroup() {
        try {
            scorecard.removeGroup(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Scorecard#removeGroups(Group[]) for failure.
     * It tests the case that when groups is null and expects IllegalArgumentException.
     * </p>
     */
    public void testRemoveGroups_NullGroups() {
        try {
            scorecard.removeGroups(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Scorecard#removeGroups(Group[]) for failure.
     * It tests the case that when groups contains null group and expects IllegalArgumentException.
     * </p>
     */
    public void testRemoveGroups_NullGroupInGroups() {
        try {
            scorecard.removeGroups(new Group[] {new Group(), null});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Scorecard#getGroup(int) for failure.
     * It tests the case that when index is negative and expects IndexOutOfBoundsException.
     * </p>
     */
    public void testGetGroups_NegativeIndex() {
        try {
            scorecard.getGroup(-98);
            fail("IndexOutOfBoundsException expected.");
        } catch (IndexOutOfBoundsException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests Scorecard#getGroup(int) for failure.
     * It tests the case that when index is too large and expects IndexOutOfBoundsException.
     * </p>
     */
    public void testGetGroups_TooLargeIndex() {
        try {
            scorecard.getGroup(8);
            fail("IndexOutOfBoundsException expected.");
        } catch (IndexOutOfBoundsException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests Scorecard#insertGroup(Group,int) for failure.
     * It tests the case that when group is null and expects IllegalArgumentException.
     * </p>
     */
    public void testInsertGroup_NullGroup() {
        try {
            scorecard.insertGroup(null, 0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Scorecard#insertGroup(Group,int) for failure.
     * It tests the case that when index is negative and expects IndexOutOfBoundsException.
     * </p>
     */
    public void testInsertGroup_NegativeIndex() {
        try {
            scorecard.insertGroup(new Group(), -87);
            fail("IndexOutOfBoundsException expected.");
        } catch (IndexOutOfBoundsException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests Scorecard#insertGroup(Group,int) for failure.
     * It tests the case that when index is too large and expects IllegalArgumentException.
     * </p>
     */
    public void testInsertGroup_TooLargeIndex() {
        try {
            scorecard.insertGroup(new Group(), 87);
            fail("IndexOutOfBoundsException expected.");
        } catch (IndexOutOfBoundsException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests Scorecard#removeGroup(int) for failure.
     * It tests the case that when index is negative and expects IndexOutOfBoundsException.
     * </p>
     */
    public void testRemoveGroup_NegativeIndex() {
        try {
            scorecard.removeGroup(-87);
            fail("IndexOutOfBoundsException expected.");
        } catch (IndexOutOfBoundsException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests Scorecard#removeGroup(int) for failure.
     * It tests the case that when index is too large and expects IllegalArgumentException.
     * </p>
     */
    public void testRemoveGroup_TooLargeIndex() {
        try {
            scorecard.removeGroup(87);
            fail("IndexOutOfBoundsException expected.");
        } catch (IndexOutOfBoundsException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests Scorecard#checkWeights(float) for failure.
     * It tests the case that when tolerance is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCheckWeights_NegativeTolerance() {
        try {
            scorecard.checkWeights(-9f);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Scorecard#checkWeights(float) for failure.
     * It tests the case that when tolerance is positive infinite and expects IllegalArgumentException.
     * </p>
     */
    public void testCheckWeights_PositiveInfiniteTolerance() {
        try {
            scorecard.checkWeights(Float.POSITIVE_INFINITY);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Scorecard#checkWeights(float) for failure.
     * It tests the case that when tolerance is NaN and expects IllegalArgumentException.
     * </p>
     */
    public void testCheckWeights_NaNTolerance() {
        try {
            scorecard.checkWeights(Float.NaN);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Scorecard#setCategory(long) for failure.
     * It tests the case that when category is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testSetCategory_NegativeCategory() {
        try {
            scorecard.setCategory(-65);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Scorecard#setCategory(long) for failure.
     * It tests the case that when category is zero and expects IllegalArgumentException.
     * </p>
     */
    public void testSetCategory_ZeroCategory() {
        try {
            scorecard.setCategory(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Scorecard#getCategory() for failure.
     * It tests the case that when category is not set and expects IllegalStateException.
     * </p>
     */
    public void testGetCategory_CategoryNotSet() {
        try {
            scorecard.getCategory();
            fail("IllegalStateException expected.");
        } catch (IllegalStateException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests Scorecard#setMinScore(float) for failure.
     * It tests the case that when minScore is NaN and expects IllegalArgumentException.
     * </p>
     */
    public void testSetMinScore_NaNMinScore() {
        try {
            scorecard.setMinScore(Float.NaN);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Scorecard#setMinScore(float) for failure.
     * It tests the case that when minScore is positive infinite and expects IllegalArgumentException.
     * </p>
     */
    public void testSetMinScore_PositeInfiniteMinScore() {
        try {
            scorecard.setMinScore(Float.POSITIVE_INFINITY);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Scorecard#setMinScore(float) for failure.
     * It tests the case that when minScore is negative infinite and expects IllegalArgumentException.
     * </p>
     */
    public void testSetMinScore_NegativeInfiniteMinScore() {
        try {
            scorecard.setMinScore(Float.NEGATIVE_INFINITY);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Scorecard#setMinScore(float) for failure.
     * It tests the case that when minScore is large than maxScore infinite and expects IllegalStateException.
     * </p>
     */
    public void testSetMinScore_LargeThanMaxScore() {
        scorecard.setMaxScore(85f);
        try {
            scorecard.setMinScore(95f);
            fail("IllegalStateException expected.");
        } catch (IllegalStateException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests Scorecard#getMinScore() for failure.
     * It tests the case that when minScore is not set and expects IllegalStateException.
     * </p>
     */
    public void testGetMinScore_MinScoreNotSet() {
        try {
            scorecard.getMinScore();
            fail("IllegalStateException expected.");
        } catch (IllegalStateException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests Scorecard#setMaxScore(float) for failure.
     * It tests the case that when maxScore is NaN and expects IllegalArgumentException.
     * </p>
     */
    public void testSetMaxScore_NaNMaxScore() {
        try {
            scorecard.setMaxScore(Float.NaN);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Scorecard#setMaxScore(float) for failure.
     * It tests the case that when maxScore is positive infinite and expects IllegalArgumentException.
     * </p>
     */
    public void testSetMaxScore_PositeInfiniteMaxScore() {
        try {
            scorecard.setMaxScore(Float.POSITIVE_INFINITY);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Scorecard#setMaxScore(float) for failure.
     * It tests the case that when maxScore is negative infinite and expects IllegalArgumentException.
     * </p>
     */
    public void testSetMaxScore_NegativeInfiniteMaxScore() {
        try {
            scorecard.setMaxScore(Float.NEGATIVE_INFINITY);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Scorecard#setMaxScore(float) for failure.
     * It tests the case that when maxScore is less than minScore and expects IllegalStateException.
     * </p>
     */
    public void testSetMaxScore_LessThanMinScore() {
        scorecard.setMaxScore(85f);
        scorecard.setMinScore(55f);
        try {
            scorecard.setMaxScore(25f);
            fail("IllegalStateException expected.");
        } catch (IllegalStateException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests Scorecard#getMaxScore() for failure.
     * It tests the case that when maxScore is not set and expects IllegalStateException.
     * </p>
     */
    public void testGetMaxScore_MaxScoreNotSet() {
        try {
            scorecard.getMaxScore();
            fail("IllegalStateException expected.");
        } catch (IllegalStateException e) {
            //good
        }
    }
}