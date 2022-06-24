/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.scorecard.data;

import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Contains the unit tests for the {@link ScorecardEditor} class.
 *
 * @author      UFP2161
 * @copyright   Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * @version     1.0
 */
public class ScorecardEditorTest extends TestCase {

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Static Fields

    /**
     * The user to be used in unit testing.
     */
    private static final String USER = "TopCoder";

    /**
     * A group to be used for unit testing.
     */
    private static final Group GROUP = new Group();

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Fields

    /**
     * The scorecard instance to be used in unit testing.
     */
    private Scorecard scorecard = null;

    /**
     * The editor instance to be used in unit testing.
     */
    private ScorecardEditor editor = null;

    /**
     * The current time when the unit test began.
     */
    private Date now = null;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Static Methods

    /**
     * Creates a test suite for the tests in this test case.
     *
     * @return  A TestSuite for this test case.
     */
    public static Test suite() {
        return new TestSuite(ScorecardEditorTest.class);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // SetUp

    /**
     * Recreates the scorecard instance before each unit test.
     */
    protected void setUp() {
        scorecard = new Scorecard();
        editor = new ScorecardEditor(scorecard, USER);
        now = new Date();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Helper Methods

    /**
     * Helper method to ensure that the modification user for the specified editor was set properly.
     *
     * @param   anEditor
     *          The scorecard editor to be checked.
     */
    private void checkModificationUser(ScorecardEditor anEditor) {
        assertEquals(
                "The modification users did not match.",
                USER, anEditor.getScorecard().getModificationUser());
    }

    /**
     * Helper method to ensure that the modification timestamp for the specified editor was set properly.
     *
     * @param   anEditor
     *          The scorecard editor to be checked.
     */
    private void checkModificationTimestamp(ScorecardEditor anEditor) {
        assertTrue(
                "The modification timestamps are too far apart.",
                (anEditor.getScorecard().getModificationTimestamp().getTime() - now.getTime()) < 1000);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor(String) Tests

    /**
     * Ensures that the one argument constructor throws an IllegalArgumentException when given a user that
     * is null.
     */
    public void test1CtorThrowsOnNullUser() {
        try {
            new ScorecardEditor(null);
            fail("An IllegalArgumentException is expected when given a null user.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the one argument constructor properly sets the user field, by checking that the
     * getUser returns the original user.
     */
    public void test1CtorSetsUser() {
        assertEquals(
                "The users did not match.",
                USER, new ScorecardEditor(USER).getUser());
    }

    /**
     * Ensures that the one argument constructor properly sets the scorecard field, by checking that the
     * getScorecard doesn't return a null.
     */
    public void test1CtorSetsScorecard() {
        assertNotNull(
                "The scorecard should not be null.",
                new ScorecardEditor(USER).getScorecard());
    }

    /**
     * Ensures that the one argument constructor properly sets the scorecard's creation user.
     */
    public void test1CtorSetsScorecardCreationUser() {
        assertEquals(
                "The creation users did not match.",
                USER, new ScorecardEditor(USER).getScorecard().getCreationUser());
    }

    /**
     * Ensures that the one argument constructor properly sets the scorecard's creation timestamp.
     */
    public void test1CtorSetsScorecardCreationTimestamp() {
        assertTrue(
                "The creation timestamps are too far apart.",
                (new ScorecardEditor(USER).getScorecard().getCreationTimestamp().getTime() - now.getTime()) < 1000);
    }

    /**
     * Ensures that the one argument constructor properly sets the scorecard's modification user.
     */
    public void test1CtorSetsScorecardModificationUser() {
        checkModificationUser(new ScorecardEditor(USER));
    }

    /**
     * Ensures that the one argument constructor properly sets the scorecard's modification timestamp.
     */
    public void test1CtorSetsScorecardModificationTimestamp() {
        checkModificationTimestamp(new ScorecardEditor(USER));
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor(Scorecard, String) Tests

    /**
     * Ensures that the two argument constructor throws an IllegalArgumentException when given a scorecard
     * that is null.
     */
    public void test2CtorThrowsOnNullScorecard() {
        try {
            new ScorecardEditor(null, USER);
            fail("An IllegalArgumentException is expected when given a null scorecard.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the two argument constructor throws an IllegalArgumentException when given a user that
     * is null.
     */
    public void test2CtorThrowsOnNullUser() {
        try {
            new ScorecardEditor(scorecard, null);
            fail("An IllegalArgumentException is expected when given a null user.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the two argument constructor properly sets the user field, by checking that the
     * getUser returns the original user.
     */
    public void test2CtorSetsUser() {
        assertEquals(
                "The users did not match.",
                USER, new ScorecardEditor(scorecard, USER).getUser());
    }

    /**
     * Ensures that the two argument constructor properly sets the scorecard field, by checking that
     * the getScorecard returns the original scorecard.
     */
    public void test2CtorSetsScorecard() {
        assertEquals(
                "The scorecard should not be null.",
                scorecard, new ScorecardEditor(scorecard, USER).getScorecard());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // id Mutator Tests

    /**
     * Ensures that the setId method throws an IllegalArgumentException when given a negative
     * identifier.
     */
    public void testSetIdThrowsOnNegativeId() {
        try {
            editor.setId(-1);
            fail("An IllegalArgumentException is expected when the id is negative.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the setId method throws an IllegalArgumentException when given a zero
     * identifier.
     */
    public void testSetIdThrowsOnZeroId() {
        try {
            editor.setId(0);
            fail("An IllegalArgumentException is expected when the id is zero.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the setId method sets the identifier properly.
     */
    public void testSetIdSetsId() {
        editor.setId(1);
        assertEquals("The identifiers did not match.", 1, scorecard.getId());
    }

    /**
     * Ensures that the setId method updates the modification user properly.
     */
    public void testSetIdSetsModificationUser() {
        editor.setId(1);
        checkModificationUser(editor);
    }

    /**
     * Ensures that the setId method updates the modification timestamp properly.
     */
    public void testSetIdSetsModificationTimestamp() {
        editor.setId(1);
        checkModificationTimestamp(editor);
    }

    /**
     * Ensures that the resetId method resets the identifier properly.
     */
    public void testResetIdResetsId() {
        editor.setId(1);
        editor.resetId();

        assertEquals("Id should be reset.", NamedScorecardStructure.SENTINEL_ID, scorecard.getId());
    }

    /**
     * Ensures that the resetId method updates the modification user properly.
     */
    public void testResetIdSetsModificationUser() {
        editor.resetId();
        checkModificationUser(editor);
    }

    /**
     * Ensures that the resetId method updates the modification timestamp properly.
     */
    public void testResetIdSetsModificationTimestamp() {
        editor.resetId();
        checkModificationTimestamp(editor);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // name Mutator Tests

    /**
     * Ensures that the setName method sets the name properly.
     */
    public void testSetNameSetsName() {
        editor.setName("name");
        assertEquals("The names did not match.", "name", scorecard.getName());
    }

    /**
     * Ensures that the setName method updates the modification user properly.
     */
    public void testSetNameSetsModificationUser() {
        editor.setName("name");
        checkModificationUser(editor);
    }

    /**
     * Ensures that the setName method updates the modification timestamp properly.
     */
    public void testSetNameSetsModificationTimestamp() {
        editor.setName("name");
        checkModificationTimestamp(editor);
    }

    /**
     * Ensures that the resetName method resets the name properly.
     */
    public void testResetNameResetsName() {
        editor.setName("name");
        editor.resetName();
        assertNull("The name should be null.", scorecard.getName());
    }

    /**
     * Ensures that the resetName method updates the modification user properly.
     */
    public void testResetNameSetsModificationUser() {
        editor.resetName();
        checkModificationUser(editor);
    }

    /**
     * Ensures that the resetName method updates the modification timestamp properly.
     */
    public void testResetNameSetsModificationTimestamp() {
        editor.resetName();
        checkModificationTimestamp(editor);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // scorecardStatus Mutator Tests

    /**
     * Ensures that the setScorecardStatus method sets the scorecard status properly.
     */
    public void testSetScorecardStatusSetsScorecardStatus() {
        ScorecardStatus status = new ScorecardStatus();
        editor.setScorecardStatus(status);
        assertEquals("The scorecard status did not match.", status, scorecard.getScorecardStatus());
    }

    /**
     * Ensures that the setScorecardStatus method updates the modification user properly.
     */
    public void testSetScorecardStatusSetsModificationUser() {
        editor.setScorecardStatus(new ScorecardStatus());
        checkModificationUser(editor);
    }

    /**
     * Ensures that the setScorecardStatus method updates the modification timestamp properly.
     */
    public void testSetScorecardStatusSetsModificationTimestamp() {
        editor.setScorecardStatus(new ScorecardStatus());
        checkModificationTimestamp(editor);
    }

    /**
     * Ensures that the resetScorecardStatus method resets the scorecard status properly.
     */
    public void testResetScorecardStatusResetsScorecardStatus() {
        editor.setScorecardStatus(new ScorecardStatus());
        editor.resetScorecardStatus();
        assertNull("The scorecard status should be null.", scorecard.getScorecardStatus());
    }

    /**
     * Ensures that the resetScorecardStatus method updates the modification user properly.
     */
    public void testResetScorecardStatusSetsModificationUser() {
        editor.resetScorecardStatus();
        checkModificationUser(editor);
    }

    /**
     * Ensures that the resetScorecardStatus method updates the modification timestamp properly.
     */
    public void testResetScorecardStatusSetsModificationTimestamp() {
        editor.resetScorecardStatus();
        checkModificationTimestamp(editor);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // scorecardType Mutator Tests

    /**
     * Ensures that the setScorecardType method sets the scorecard type properly.
     */
    public void testSetScorecardTypeSetsScorecardType() {
        ScorecardType type = new ScorecardType();
        editor.setScorecardType(type);
        assertEquals("The scorecard type did not match.", type, scorecard.getScorecardType());
    }

    /**
     * Ensures that the setScorecardType method updates the modification user properly.
     */
    public void testSetScorecardTypeSetsModificationUser() {
        editor.setScorecardType(new ScorecardType());
        checkModificationUser(editor);
    }

    /**
     * Ensures that the setScorecardType method updates the modification timestamp properly.
     */
    public void testSetScorecardTypeSetsModificationTimestamp() {
        editor.setScorecardType(new ScorecardType());
        checkModificationTimestamp(editor);
    }

    /**
     * Ensures that the resetScorecardType method resets the scorecard type properly.
     */
    public void testResetScorecardTypeResetsScorecardType() {
        editor.setScorecardType(new ScorecardType());
        editor.resetScorecardType();
        assertNull("The scorecard type should be null.", scorecard.getScorecardType());
    }

    /**
     * Ensures that the resetScorecardType method updates the modification user properly.
     */
    public void testResetScorecardTypeSetsModificationUser() {
        editor.resetScorecardType();
        checkModificationUser(editor);
    }

    /**
     * Ensures that the resetScorecardType method updates the modification timestamp properly.
     */
    public void testResetScorecardTypeSetsModificationTimestamp() {
        editor.resetScorecardStatus();
        checkModificationTimestamp(editor);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // category Mutator Tests

    /**
     * Ensures that the setCategory method throws an IllegalArgumentException when given a negative
     * category identifier.
     */
    public void testSetCategoryThrowsOnNegativeCategory() {
        try {
            editor.setCategory(-1);
            fail("An IllegalArgumentException is expected when setting the category to -1.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the setCategory method throws an IllegalArgumentException when given a zero
     * category identifier.
     */
    public void testSetCategoryThrowsOnZeroCategory() {
        try {
            editor.setCategory(0);
            fail("An IllegalArgumentException is expected when setting the category to 0.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the setCategory method sets the category properly.
     */
    public void testSetCategorySetsCategory() {
        editor.setCategory(1);
        assertEquals("The categories did not match.", 1, scorecard.getCategory());
    }

    /**
     * Ensures that the setCategory method updates the modification user properly.
     */
    public void testSetCategorySetsModificationUser() {
        editor.setCategory(1);
        checkModificationUser(editor);
    }

    /**
     * Ensures that the setCategory method updates the modification timestamp properly.
     */
    public void testSetCategorySetsModificationTimestamp() {
        editor.setCategory(1);
        checkModificationTimestamp(editor);
    }

    /**
     * Ensures that the resetCategory method resets the category properly.
     */
    public void testResetCategoryResetsVersion() {
        editor.setCategory(1);
        editor.resetCategory();

        try {
            scorecard.getCategory();
            fail("An IllegalStateException is expected when no category is set.");
        } catch (IllegalStateException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the resetCategory method updates the modification user properly.
     */
    public void testResetCategorySetsModificationUser() {
        editor.resetCategory();
        checkModificationUser(editor);
    }

    /**
     * Ensures that the resetCategory method updates the modification timestamp properly.
     */
    public void testResetCategorySetsModificationTimestamp() {
        editor.resetCategory();
        checkModificationTimestamp(editor);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // version Mutator Tests

    /**
     * Ensures that the setVersion method sets the version properly.
     */
    public void testSetVersionSetsVersion() {
        editor.setVersion("version");
        assertEquals("The version did not match.", "version", scorecard.getVersion());
    }

    /**
     * Ensures that the setVersion method updates the modification user properly.
     */
    public void testSetVersionSetsModificationUser() {
        editor.setVersion("version");
        checkModificationUser(editor);
    }

    /**
     * Ensures that the setVersion method updates the modification timestamp properly.
     */
    public void testSetVersionSetsModificationTimestamp() {
        editor.setVersion("version");
        checkModificationTimestamp(editor);
    }

    /**
     * Ensures that the resetVersion method resets the version properly.
     */
    public void testResetVersionResetsVersion() {
        editor.setVersion("version");
        editor.resetVersion();
        assertNull("The version should be null.", scorecard.getVersion());
    }

    /**
     * Ensures that the resetVersion method updates the modification user properly.
     */
    public void testResetVersionSetsModificationUser() {
        editor.resetVersion();
        checkModificationUser(editor);
    }

    /**
     * Ensures that the resetVersion method updates the modification timestamp properly.
     */
    public void testResetVersionSetsModificationTimestamp() {
        editor.resetVersion();
        checkModificationTimestamp(editor);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // minScore Mutator Tests

    /**
     * Ensures that the setMinScore method throws an IllegalArgumentException when given
     * Float.NaN.
     */
    public void testSetMinScoreThrowsOnNaN() {
        checkSetMinScoreThrowsIAE(Float.NaN, " when given not a number (Float.NaN).");
    }

    /**
     * Ensures that the setMinScore method throws an IllegalArgumentException when given
     * Float.POSITIVE_INFINITY.
     */
    public void testSetMinScoreThrowsOnPositiveInfinity() {
        checkSetMinScoreThrowsIAE(Float.POSITIVE_INFINITY, " when given positive infinity.");
    }

    /**
     * Ensures that the setMinScore method throws an IllegalArgumentException when given
     * Float.NEGATIVE_INFINITY.
     */
    public void testSetMinScoreThrowsOnNegativeInfinity() {
        checkSetMinScoreThrowsIAE(Float.NEGATIVE_INFINITY, " when given negative infinity.");
    }

    /**
     * Helper method to check that the setMinScore method throws an IllegalArgumentException
     * when given the specified argument.
     *
     * @param   minScore
     *          The minimum score that should generate an exception.
     * @param   message
     *          The message to use in the error message.
     */
    private void checkSetMinScoreThrowsIAE(float minScore, String message) {
        try {
            editor.setMinScore(minScore);
            fail("An IllegalArgumentException is expected when " + message);
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the setMinScore method throws an IllegalStateException when given a score
     * that is greater than the current maximum score.
     */
    public void testSetMinScoreThrowsOnTooLargeMinScore() {
        editor.setMaxScore(100.0f);

        try {
            editor.setMinScore(100.1f);
            fail("An IllegalStateException is expected when the minScore is greater than the maxScore.");
        } catch (IllegalStateException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the setMinScore method sets the minimum score properly.
     */
    public void testSetMinScoreSetsMinScore() {
        editor.setMinScore(0.0f);
        assertEquals("The minimum scores do not match.", 0.0f, scorecard.getMinScore(), 1e-9f);
    }

    /**
     * Ensures that the setMinScore method updates the modification user properly.
     */
    public void testSetMinScoreSetsModificationUser() {
        editor.setMinScore(0.0f);
        checkModificationUser(editor);
    }

    /**
     * Ensures that the setMinScore method updates the modification timestamp properly.
     */
    public void testSetMinScoreSetsModificationTimestamp() {
        editor.setMinScore(0.0f);
        checkModificationTimestamp(editor);
    }

    /**
     * Ensures that the resetMinScore method resets the minimum score properly.
     */
    public void testResetMinScoreResetsMinScore() {
        editor.setMinScore(0.0f);
        editor.resetMinScore();

        try {
            scorecard.getMinScore();
            fail("An IllegalStateException is expected when the minimum score is reset.");
        } catch (IllegalStateException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the resetMinScore method updates the modification user properly.
     */
    public void testResetMinScoreSetsModificationUser() {
        editor.resetMinScore();
        checkModificationUser(editor);
    }

    /**
     * Ensures that the resetMinScore method updates the modification timestamp properly.
     */
    public void testResetMinScoreSetsModificationTimestamp() {
        editor.resetMinScore();
        checkModificationTimestamp(editor);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // maxScore Mutator Tests

    /**
     * Ensures that the setMaxScore method throws an IllegalArgumentException when given
     * Float.NaN.
     */
    public void testSetMaxScoreThrowsOnNaN() {
        checkSetMaxScoreThrowsIAE(Float.NaN, " when given not a number (Float.NaN).");
    }

    /**
     * Ensures that the setMaxScore method throws an IllegalArgumentException when given
     * Float.POSITIVE_INFINITY.
     */
    public void testSetMaxScoreThrowsOnPositiveInfinity() {
        checkSetMaxScoreThrowsIAE(Float.POSITIVE_INFINITY, " when given positive infinity.");
    }

    /**
     * Ensures that the setMaxScore method throws an IllegalArgumentException when given
     * Float.NEGATIVE_INFINITY.
     */
    public void testSetMaxScoreThrowsOnNegativeInfinity() {
        checkSetMaxScoreThrowsIAE(Float.NEGATIVE_INFINITY, " when given negative infinity.");
    }

    /**
     * Helper method to check that the setMaxScore method throws an IllegalArgumentException
     * when given the specified argument.
     *
     * @param   maxScore
     *          The maximum score that should generate an exception.
     * @param   message
     *          The message to use in the error message.
     */
    private void checkSetMaxScoreThrowsIAE(float maxScore, String message) {
        try {
            editor.setMaxScore(maxScore);
            fail("An IllegalArgumentException is expected when " + message);
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the setMaxScore method throws an IllegalStateException when given a score
     * that is less than the current minimum score.
     */
    public void testSetMaxScoreThrowsOnTooSmallMaxScore() {
        editor.setMinScore(100.0f);

        try {
            editor.setMaxScore(99.9f);
            fail("An IllegalStateException is expected when the maxScore is less than the minScore.");
        } catch (IllegalStateException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the setMaxScore method sets the maximum score properly.
     */
    public void testSetMaxScoreSetsMaxScore() {
        editor.setMaxScore(0.0f);
        assertEquals("The maximum scores do not match.", 0.0f, scorecard.getMaxScore(), 1e-9f);
    }

    /**
     * Ensures that the setMaxScore method updates the modification user properly.
     */
    public void testSetMaxScoreSetsModificationUser() {
        editor.setMaxScore(0.0f);
        checkModificationUser(editor);
    }

    /**
     * Ensures that the setMaxScore method updates the modification timestamp properly.
     */
    public void testSetMaxScoreSetsModificationTimestamp() {
        editor.setMaxScore(0.0f);
        checkModificationTimestamp(editor);
    }

    /**
     * Ensures that the resetMaxScore method resets the maximum score properly.
     */
    public void testResetMaxScoreResetsMaxScore() {
        editor.setMaxScore(0.0f);
        editor.resetMaxScore();

        try {
            scorecard.getMaxScore();
            fail("An IllegalStateException is expected when the maximum score is reset.");
        } catch (IllegalStateException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the resetMaxScore method updates the modification user properly.
     */
    public void testResetMaxScoreSetsModificationUser() {
        editor.resetMaxScore();
        checkModificationUser(editor);
    }

    /**
     * Ensures that the resetMaxScore method updates the modification timestamp properly.
     */
    public void testResetMaxScoreSetsModificationTimestamp() {
        editor.resetMaxScore();
        checkModificationTimestamp(editor);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // addGroup(Group) Method Tests

    /**
     * Ensures that the addGroup method throws an IllegalArgumentException when given a
     * null argument.
     */
    public void testAddGroupThrowsOnNull() {
        try {
            editor.addGroup(null);
            fail("An IllegalArgumentException is expected when addGroup(null) is called.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the addGroup method works properly when adding a group.
     */
    public void testAddGroupAddsGroup() {
        editor.addGroup(GROUP);

        TestUtil.assertArraysAreEqual(
                "The groups are not correct.", new Group[] {GROUP}, scorecard.getAllGroups());
    }

    /**
     * Ensures that the addGroup method updates the modification user properly.
     */
    public void testAddGroupSetsModificationUser() {
        editor.addGroup(GROUP);
        checkModificationUser(editor);
    }

    /**
     * Ensures that the addGroup method updates the modification timestamp properly.
     */
    public void testAddGroupSetsModificationTimestamp() {
        editor.addGroup(GROUP);
        checkModificationTimestamp(editor);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // addGroup(Group[]) Method Tests

    /**
     * Ensures that the addGroups method throws an IllegalArgumentException when given a
     * null argument.
     */
    public void testAddGroupsThrowsOnIsNull() {
        try {
            editor.addGroups(null);
            fail("An IllegalArgumentException is expected when addGroups(null) is called.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the addGroups method throws an IllegalArgumentException when given a
     * argument with a null.
     */
    public void testAddGroupsThrowsOnHasNull() {
        try {
            editor.addGroups(new Group[] {null});
            fail("An IllegalArgumentException is expected when addGroups(new Group[]{null}) is called.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the addGroups method works properly when adding a groups list.
     */
    public void testAddGroupsAddsGroups() {
        editor.addGroups(new Group[] {GROUP});

        TestUtil.assertArraysAreEqual(
                "The groups are not correct.", new Group[] {GROUP}, scorecard.getAllGroups());
    }

    /**
     * Ensures that the addGroups method updates the modification user properly.
     */
    public void testAddGroupsSetsModificationUser() {
        editor.addGroups(new Group[0]);
        checkModificationUser(editor);
    }

    /**
     * Ensures that the addGroups method updates the modification timestamp properly.
     */
    public void testAddGroupsSetsModificationTimestamp() {
        editor.addGroups(new Group[0]);
        checkModificationTimestamp(editor);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // insertGroup(Group, int) Method Tests

    /**
     * Ensures that the insertGroup method throws an IllegalArgumentException when given a
     * null question.
     */
    public void testInsertGroupThrowsOnNull() {
        try {
            editor.insertGroup(null, 0);
            fail("An IllegalArgumentException is expected when insertGroup(null, 0) is called.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the insertGroup method throws an IndexOutOfBoundsException when given an
     * index that is too small.
     */
    public void testInsertGroupThrowsOnSmallIndex() {
        try {
            editor.insertGroup(GROUP, -1);
            fail("An IndexOutOfBoundsException is expected when insertGroup(GROUP, -1) is called.");
        } catch (IndexOutOfBoundsException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the insertGroup method throws an IndexOutOfBoundsException when given an
     * index that is too large.
     */
    public void testInsertGroupThrowsOnLargeIndex() {
        try {
            editor.insertGroup(GROUP, 1);
            fail("An IndexOutOfBoundsException is expected when insertGroup(GROUP, 1) is called.");
        } catch (IndexOutOfBoundsException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the insertGroup method works properly when inserting both duplicate and
     * non-duplicate groups.
     */
    public void testInsertGroupInsertsGroup() {
        editor.insertGroup(GROUP, 0);

        TestUtil.assertArraysAreEqual(
                "The groups are not correct.", new Group[] {GROUP}, scorecard.getAllGroups());
    }

    /**
     * Ensures that the insertGroup method updates the modification user properly.
     */
    public void testInsertGroupSetsModificationUser() {
        editor.insertGroup(GROUP, 0);
        checkModificationUser(editor);
    }

    /**
     * Ensures that the insertGroup method updates the modification timestamp properly.
     */
    public void testInsertGroupSetsModificationTimestamp() {
        editor.insertGroup(GROUP, 0);
        checkModificationTimestamp(editor);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // removeGroup(Group) Method Tests

    /**
     * Ensures that the removeGroup(Group) method throws an IllegalArgumentException when
     * given a null argument.
     */
    public void testRemoveGroupThrowsOnNull() {
        try {
            editor.removeGroup(null);
            fail("An IllegalArgumentException is expected when removeGroup(null) is called.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the removeGroup(Group) method works properly.
     */
    public void testRemoveGroupRemovesGroup() {
        editor.addGroup(GROUP);
        editor.removeGroup(GROUP);

        TestUtil.assertArraysAreEqual(
                "The groups are not correct.", new Group[0], scorecard.getAllGroups());
    }

    /**
     * Ensures that the removeGroup(Group) method updates the modification user properly.
     */
    public void testRemoveGroupSetsModificationUser() {
        editor.removeGroup(GROUP);
        checkModificationUser(editor);
    }

    /**
     * Ensures that the removeGroup(Group) method updates the modification timestamp properly.
     */
    public void testRemoveGroupSetsModificationTimestamp() {
        editor.removeGroup(GROUP);
        checkModificationTimestamp(editor);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // removeGroup(int) Method Tests

    /**
     * Ensures that the removeGroup(int) method throws an IndexOutOfBoundsException when given an
     * index that is too small.
     */
    public void testRemoveGroupIntThrowsOnSmallIndex() {
        try {
            editor.removeGroup(-1);
            fail("An IndexOutOfBoundsException is expected when removeGroup(-1) is called.");
        } catch (IndexOutOfBoundsException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the removeGroup(int) method throws an IndexOutOfBoundsException when given an
     * index that is too large.
     */
    public void testRemoveGroupIntThrowsOnLargeIndex() {
        editor.addGroup(GROUP);

        try {
            editor.removeGroup(1);
            fail("An IndexOutOfBoundsException is expected when removeGroup(1) is called.");
        } catch (IndexOutOfBoundsException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the removeGroup(int) method works properly.
     */
    public void testRemoveGroupIntRemovesGroup() {
        editor.addGroup(GROUP);
        editor.removeGroup(0);

        TestUtil.assertArraysAreEqual(
                "The groups are not correct.", new Group[0], scorecard.getAllGroups());
    }

    /**
     * Ensures that the removeGroup(int) method updates the modification user properly.
     */
    public void testRemoveGroupIntSetsModificationUser() {
        editor.addGroup(GROUP);
        editor.removeGroup(0);
        checkModificationUser(editor);
    }

    /**
     * Ensures that the removeGroup(int) method updates the modification timestamp properly.
     */
    public void testRemoveGroupIntSetsModificationTimestamp() {
        editor.addGroup(GROUP);
        editor.removeGroup(0);
        checkModificationTimestamp(editor);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // removeGroups(Group[]) Method Tests

    /**
     * Ensures that the removeGroups method throws an IllegalArgumentException when given a
     * null argument.
     */
    public void testRemoveGroupsThrowsOnIsNull() {
        try {
            editor.removeGroups(null);
            fail("An IllegalArgumentException is expected when removeGroups(null) is called.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the removeGroups method throws an IllegalArgumentException when given a
     * argument with a null.
     */
    public void testRemoveGroupsThrowsOnHasNull() {
        try {
            editor.removeGroups(new Group[] {null});
            fail("An IllegalArgumentException is expected when removeGroups(new Group[]{null}) is called.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the removeGroups method works properly.
     */
    public void testRemoveGroupsRemovesGroups() {
        editor.addGroup(GROUP);
        editor.removeGroups(new Group[] {GROUP});

        TestUtil.assertArraysAreEqual(
                "The groups are not correct.", new Group[0], scorecard.getAllGroups());
    }

    /**
     * Ensures that the removeGroups method updates the modification user properly.
     */
    public void testRemoveGroupsSetsModificationUser() {
        editor.removeGroups(new Group[0]);
        checkModificationUser(editor);
    }

    /**
     * Ensures that the removeGroups method updates the modification timestamp properly.
     */
    public void testRemoveGroupsSetsModificationTimestamp() {
        editor.removeGroups(new Group[0]);
        checkModificationTimestamp(editor);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // clearGroups Method Tests

    /**
     * Ensures that the clearGroups method works properly.
     */
    public void testClearGroupsClearsGroups() {
        editor.addGroup(GROUP);
        editor.clearGroups();

        TestUtil.assertArraysAreEqual(
                "The groups are not correct.", new Group[0], scorecard.getAllGroups());
    }

    /**
     * Ensures that the clearGroups method updates the modification user properly.
     */
    public void testClearGroupsSetsModificationUser() {
        editor.clearGroups();
        checkModificationUser(editor);
    }

    /**
     * Ensures that the clearGroups method updates the modification timestamp properly.
     */
    public void testClearGroupsSetsModificationTimestamp() {
        editor.clearGroups();
        checkModificationTimestamp(editor);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // inUse Mutator Tests

    /**
     * Ensures that the setInUse method sets the in use switch properly.
     */
    public void testSetInUseSetsInUse() {
        editor.setInUse(true);
        assertTrue("The in use switch should be true.", scorecard.isInUse());
    }

    /**
     * Ensures that the setInUse method updates the modification user properly.
     */
    public void testSetInUseSetsModificationUser() {
        editor.setInUse(true);
        checkModificationUser(editor);
    }

    /**
     * Ensures that the setInUse method updates the modification timestamp properly.
     */
    public void testSetInUseSetsModificationTimestamp() {
        editor.setInUse(true);
        checkModificationTimestamp(editor);
    }

    /**
     * Ensures that the resetInUse method resets the in use switch properly.
     */
    public void testResetInUseResetsInUse() {
        editor.setInUse(true);
        editor.resetInUse();
        assertFalse("The in use switch should be false.", scorecard.isInUse());
    }

    /**
     * Ensures that the resetInUse method updates the modification user properly.
     */
    public void testResetInUseSetsModificationUser() {
        editor.resetInUse();
        checkModificationUser(editor);
    }

    /**
     * Ensures that the resetInUse method updates the modification timestamp properly.
     */
    public void testResetInUseSetsModificationTimestamp() {
        editor.resetInUse();
        checkModificationTimestamp(editor);
    }
}
