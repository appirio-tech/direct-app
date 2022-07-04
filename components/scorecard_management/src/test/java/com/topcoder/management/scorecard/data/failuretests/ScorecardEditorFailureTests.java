/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.data.failuretests;

import com.topcoder.management.scorecard.data.Group;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.ScorecardEditor;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Failure test cases for ScorecardEditor.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class ScorecardEditorFailureTests extends TestCase {
    /**
     * <p>
     * ScorecardEditor instance for testing.
     * </p>
     */
    private ScorecardEditor editor;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        editor = new ScorecardEditor("ivern");
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
        editor = null;
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ScorecardEditorFailureTests.class);
    }

    /**
     * <p>
     * Tests ctor ScorecardEditor#ScorecardEditor(Scorecard,String) for failure.
     * It tests the case that when scorecard is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor1_NullScorecard() {
        try {
            new ScorecardEditor(null, "ivern");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor ScorecardEditor#ScorecardEditor(Scorecard,String) for failure.
     * It tests the case that when user is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor1_NullUser() {
        try {
            new ScorecardEditor(new Scorecard(), null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor ScorecardEditor#ScorecardEditor(String) for failure.
     * It tests the case that when user is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor2_NullUser() {
        try {
            new ScorecardEditor(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ScorecardEditor#setId(long) for failure.
     * It tests the case that when id is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testSetId_NegativeId() {
        try {
            editor.setId(-54);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ScorecardEditor#setId(long) for failure.
     * It tests the case that when id is zero and expects IllegalArgumentException.
     * </p>
     */
    public void testSetId_ZeroId() {
        try {
            editor.setId(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ScorecardEditor#setCategory(long) for failure.
     * It tests the case that when category is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testSetCategory_NegativeCategory() {
        try {
            editor.setCategory(-54);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ScorecardEditor#setCategory(long) for failure.
     * It tests the case that when category is zero and expects IllegalArgumentException.
     * </p>
     */
    public void testSetCategory_ZeroCategory() {
        try {
            editor.setCategory(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ScorecardEditor#addGroup(Group) for failure.
     * It tests the case that when group is null and expects IllegalArgumentException.
     * </p>
     */
    public void testAddGroup_NullGroup() {
        try {
            editor.addGroup(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ScorecardEditor#addGroups(Group[]) for failure.
     * It tests the case that when groups is null and expects IllegalArgumentException.
     * </p>
     */
    public void testAddGroups_NullGroups() {
        try {
            editor.addGroups(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ScorecardEditor#addGroups(Group[]) for failure.
     * It tests the case that when groups is null and expects IllegalArgumentException.
     * </p>
     */
    public void testAddGroups_NullGroupInGroups() {
        try {
            editor.addGroups(new Group[] {new Group(), null});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ScorecardEditor#removeGroup(Group) for failure.
     * It tests the case that when group is null and expects IllegalArgumentException.
     * </p>
     */
    public void testRemoveGroup_NullGroup() {
        try {
            editor.removeGroup(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ScorecardEditor#removeGroups(Group[]) for failure.
     * It tests the case that when groups is null and expects IllegalArgumentException.
     * </p>
     */
    public void testRemoveGroups_NullGroups() {
        try {
            editor.removeGroups(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ScorecardEditor#removeGroups(Group[]) for failure.
     * It tests the case that when groups is null and expects IllegalArgumentException.
     * </p>
     */
    public void testRemoveGroups_NullGroupInGroups() {
        try {
            editor.removeGroups(new Group[] {new Group(), null});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ScorecardEditor#setMinScore(float) for failure.
     * It tests the case that when minScore is NaN and expects IllegalArgumentException.
     * </p>
     */
    public void testSetMinScore_NaNMinScore() {
        try {
            editor.setMinScore(Float.NaN);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ScorecardEditor#setMinScore(float) for failure.
     * It tests the case that when minScore is positive infinite and expects IllegalArgumentException.
     * </p>
     */
    public void testSetMinScore_PositeInfiniteMinScore() {
        try {
            editor.setMinScore(Float.POSITIVE_INFINITY);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ScorecardEditor#setMinScore(float) for failure.
     * It tests the case that when minScore is negative infinite and expects IllegalArgumentException.
     * </p>
     */
    public void testSetMinScore_NegativeInfiniteMinScore() {
        try {
            editor.setMinScore(Float.NEGATIVE_INFINITY);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ScorecardEditor#setMinScore(float) for failure.
     * It tests the case that when minScore is large than maxScore infinite and expects IllegalStateException.
     * </p>
     */
    public void testSetMinScore_LargeThanMaxScore() {
        editor.setMaxScore(85f);
        try {
            editor.setMinScore(95f);
            fail("IllegalStateException expected.");
        } catch (IllegalStateException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ScorecardEditor#setMaxScore(float) for failure.
     * It tests the case that when maxScore is NaN and expects IllegalArgumentException.
     * </p>
     */
    public void testSetMaxScore_NaNMaxScore() {
        try {
            editor.setMaxScore(Float.NaN);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ScorecardEditor#setMaxScore(float) for failure.
     * It tests the case that when maxScore is positive infinite and expects IllegalArgumentException.
     * </p>
     */
    public void testSetMaxScore_PositeInfiniteMaxScore() {
        try {
            editor.setMaxScore(Float.POSITIVE_INFINITY);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ScorecardEditor#setMaxScore(float) for failure.
     * It tests the case that when maxScore is negative infinite and expects IllegalArgumentException.
     * </p>
     */
    public void testSetMaxScore_NegativeInfiniteMaxScore() {
        try {
            editor.setMaxScore(Float.NEGATIVE_INFINITY);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ScorecardEditor#setMaxScore(float) for failure.
     * It tests the case that when maxScore is less than minScore and expects IllegalStateException.
     * </p>
     */
    public void testSetMaxScore_LessThanMinScore() {
        editor.setMaxScore(85f);
        editor.setMinScore(55f);
        try {
            editor.setMaxScore(25f);
            fail("IllegalStateException expected.");
        } catch (IllegalStateException e) {
            //good
        }
    }
}