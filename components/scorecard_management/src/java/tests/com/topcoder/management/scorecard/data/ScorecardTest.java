/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.scorecard.data;

import java.io.IOException;
import java.util.Date;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Contains the unit tests for the {@link Scorecard} class.
 *
 * @author      UFP2161
 * @copyright   Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * @version     1.0
 */
public class ScorecardTest extends NamedScorecardStructureTest {

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Static Fields

    /**
     * A question to be used for unit testing.
     */
    private static final Question QUESTION1 = new Question(1, 30f);

    /**
     * A question to be used for unit testing.
     */
    private static final Question QUESTION2 = new Question(2, 30f);

    /**
     * A question to be used for unit testing.
     */
    private static final Question QUESTION3 = new Question(3, 40f);

    /**
     * A section to be used for unit testing.
     */
    private static final Section SECTION1;

    /**
     * A section to be used for unit testing.
     */
    private static final Section SECTION2;

    /**
     * A section to be used for unit testing.
     */
    private static final Section SECTION3;

    /**
     * A group to be used for unit testing.
     */
    private static final Group GROUP1;

    /**
     * A group to be used for unit testing.
     */
    private static final Group GROUP2;

    /**
     * A group to be used for unit testing.
     */
    private static final Group GROUP3;

    /**
     * A scorecard status to be used for unit testing.
     */
    private static final ScorecardStatus SCORECARD_STATUS = new ScorecardStatus(1, "ScorecardStatus");

    /**
     * A scorecard type to be used for unit testing.
     */
    private static final ScorecardType SCORECARD_TYPE = new ScorecardType(2, "ScorecardType");

    /**
     * A category to be used for unit testing.
     */
    private static final long CATEGORY = 1;

    /**
     * A version to be used for unit testing.
     */
    private static final String VERSION = "1.0";

    /**
     * A minimum score to be used for unit testing.
     */
    private static final float MIN_SCORE = 1.0f;

    /**
     * A maximum score to be used for unit testing.
     */
    private static final float MAX_SCORE = 4.0f;

    /**
     * A creation user to be used for unit testing.
     */
    private static final String CREATION_USER = "Creation User";

    /**
     * A creation timestamp to be used for unit testing.
     */
    private static final Date CREATION_TIMESTAMP = new Date();

    /**
     * A modification user to be used for unit testing.
     */
    private static final String MODIFICATION_USER = "Modification User";

    /**
     * A modification timestamp to be used for unit testing.
     */
    private static final Date MODIFICATION_TIMESTAMP = new Date();

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Static Initializers

    /**
     * Initializes the section static fields.
     */
    static {
        SECTION1 = new Section(1, 50f);
        SECTION2 = new Section(2, 50f);
        SECTION3 = new Section(3, 50f);

        SECTION1.addQuestion(QUESTION1);
        SECTION1.addQuestion(QUESTION2);
        SECTION1.addQuestion(QUESTION3);

        SECTION2.addQuestion(QUESTION1);
        SECTION2.addQuestion(QUESTION2);

        SECTION3.addQuestion(QUESTION1);
        SECTION3.addQuestion(QUESTION2);
        SECTION3.addQuestion(QUESTION3);

        GROUP1 = new Group(1, 50f);
        GROUP2 = new Group(2, 50f);
        GROUP3 = new Group(3, 50f);

        GROUP1.addSection(SECTION1);
        GROUP1.addSection(SECTION3);

        GROUP2.addSection(SECTION1);
        GROUP2.addSection(SECTION2);

        GROUP3.addSection(SECTION1);
        GROUP3.addSection(SECTION3);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Static Methods

    /**
     * Creates a test suite for the tests in this test case.
     *
     * @return  A TestSuite for this test case.
     */
    public static Test suite() {
        return new TestSuite(ScorecardTest.class);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Overridden Abstract Methods

    /**
     * Creates a new Scorecard instance using the zero argument constructor.
     *
     * @return  A Scorecard instance created using the zero argument constructor.
     */
    protected NamedScorecardStructure createInstance() {
        return new Scorecard();
    }

    /**
     * Creates a new Scorecard instance using the one argument constructor.
     *
     * @param   id
     *          The identifier to initialize with.
     *
     * @return  A Scorecard instance created using the one argument constructor.
     */
    protected NamedScorecardStructure createInstance(long id) {
        return new Scorecard(id);
    }

    /**
     * Creates a new Scorecard instance using the two argument constructor.
     *
     * @param   id
     *          The identifier to initialize with.
     * @param   name
     *          The name to initialize with.
     *
     * @return  A Scorecard instance created using the two argument constructor.
     */
    protected NamedScorecardStructure createInstance(long id, String name) {
        return new Scorecard(id, name);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Field Checkers

    /**
     * Helper method to check the scorecardStatus field.
     *
     * @param   scorecard
     *          The Scorecard instance to check (will be down casted).
     * @param   expected
     *          The expected value of the scorecardStatus field.
     * @param   message
     *          The error message if the field values don't match.
     */
    private void checkScorecardStatus(NamedScorecardStructure scorecard, ScorecardStatus expected, String message) {
        assertEquals(message, expected, ((Scorecard) scorecard).getScorecardStatus());
    }

    /**
     * Helper method to check the scorecardType field.
     *
     * @param   scorecard
     *          The Scorecard instance to check (will be down casted).
     * @param   expected
     *          The expected value of the scorecardType field.
     * @param   message
     *          The error message if the field values don't match.
     */
    private void checkScorecardType(NamedScorecardStructure scorecard, ScorecardType expected, String message) {
        assertEquals(message, expected, ((Scorecard) scorecard).getScorecardType());
    }

    /**
     * Helper method to check the category field.
     *
     * @param   scorecard
     *          The Scorecard instance to check (will be down casted).
     * @param   expected
     *          The expected value of the category field.
     * @param   message
     *          The error message if the field values don't match.
     */
    private void checkCategory(NamedScorecardStructure scorecard, long expected, String message) {
        assertEquals(message, expected, ((Scorecard) scorecard).getCategory());
    }

    /**
     * Helper method to check the version field.
     *
     * @param   scorecard
     *          The Scorecard instance to check (will be down casted).
     * @param   expected
     *          The expected value of the version field.
     * @param   message
     *          The error message if the field values don't match.
     */
    private void checkVersion(NamedScorecardStructure scorecard, String expected, String message) {
        assertEquals(message, expected, ((Scorecard) scorecard).getVersion());
    }

    /**
     * Helper method to check the minScore field.
     *
     * @param   scorecard
     *          The Scorecard instance to check (will be down casted).
     * @param   expected
     *          The expected value of the minScore field.
     * @param   message
     *          The error message if the field values don't match.
     */
    private void checkMinScore(NamedScorecardStructure scorecard, float expected, String message) {
        assertEquals(message, expected, ((Scorecard) scorecard).getMinScore(), 1e-9f);
    }

    /**
     * Helper method to check the maxScore field.
     *
     * @param   scorecard
     *          The Scorecard instance to check (will be down casted).
     * @param   expected
     *          The expected value of the maxScore field.
     * @param   message
     *          The error message if the field values don't match.
     */
    private void checkMaxScore(NamedScorecardStructure scorecard, float expected, String message) {
        assertEquals(message, expected, ((Scorecard) scorecard).getMaxScore(), 1e-9f);
    }

    /**
     * Helper method to check the groups field.
     *
     * @param   scorecard
     *          The Scorecard instance to check (will be down casted).
     * @param   expected
     *          The expected value of the groups field.
     * @param   message
     *          The error message if the field values don't match.
     */
    private void checkGroups(NamedScorecardStructure scorecard, Group[] expected, String message) {
        TestUtil.assertArraysAreEqual(message, expected, ((Scorecard) scorecard).getAllGroups());
    }

    /**
     * Helper method to check the inUse field.
     *
     * @param   scorecard
     *          The Scorecard instance to check (will be down casted).
     * @param   expected
     *          The expected value of the inUse field.
     * @param   message
     *          The error message if the field values don't match.
     */
    private void checkInUse(NamedScorecardStructure scorecard, boolean expected, String message) {
        assertEquals(message, expected, ((Scorecard) scorecard).isInUse());
    }

    /**
     * Helper method to check the creationUser field.
     *
     * @param   scorecard
     *          The Scorecard instance to check (will be down casted).
     * @param   expected
     *          The expected value of the creationUser field.
     * @param   message
     *          The error message if the field values don't match.
     */
    private void checkCreationUser(NamedScorecardStructure scorecard, String expected, String message) {
        assertEquals(message, expected, ((Scorecard) scorecard).getCreationUser());
    }

    /**
     * Helper method to check the creationTimestamp field.
     *
     * @param   scorecard
     *          The Scorecard instance to check (will be down casted).
     * @param   expected
     *          The expected value of the creationTimestamp field.
     * @param   message
     *          The error message if the field values don't match.
     */
    private void checkCreationTimestamp(NamedScorecardStructure scorecard, Date expected, String message) {
        assertEquals(message, expected, ((Scorecard) scorecard).getCreationTimestamp());
    }

    /**
     * Helper method to check the modificationUser field.
     *
     * @param   scorecard
     *          The Scorecard instance to check (will be down casted).
     * @param   expected
     *          The expected value of the modificationUser field.
     * @param   message
     *          The error message if the field values don't match.
     */
    private void checkModificationUser(NamedScorecardStructure scorecard, String expected, String message) {
        assertEquals(message, expected, ((Scorecard) scorecard).getModificationUser());
    }

    /**
     * Helper method to check the modificationTimestamp field.
     *
     * @param   scorecard
     *          The Scorecard instance to check (will be down casted).
     * @param   expected
     *          The expected value of the modificationTimestamp field.
     * @param   message
     *          The error message if the field values don't match.
     */
    private void checkModificationTimestamp(NamedScorecardStructure scorecard, Date expected, String message) {
        assertEquals(message, expected, ((Scorecard) scorecard).getModificationTimestamp());
    }

    /**
     * Helper method to check the default value of scorecardStatus field, which is null.
     *
     * @param   scorecard
     *          The Scorecard instance to check (will be down casted).
     */
    private void checkDefaultScorecardStatus(NamedScorecardStructure scorecard) {
        checkScorecardStatus(scorecard, null, "The scorecardStatus should be null.");
    }

    /**
     * Helper method to check the default value of scorecardTyoe field, which is null.
     *
     * @param   scorecard
     *          The Scorecard instance to check (will be down casted).
     */
    private void checkDefaultScorecardType(NamedScorecardStructure scorecard) {
        checkScorecardType(scorecard, null, "The scorecardType should be null.");
    }

    /**
     * Helper method to check the default value of category field, which would cause an
     * IllegalStateException to be thrown by the getCategory method.
     *
     * @param   scorecard
     *          The Scorecard instance to check (will be down casted).
     */
    private void checkDefaultCategory(NamedScorecardStructure scorecard) {
        try {
            ((Scorecard) scorecard).getCategory();
            fail("An IllegalStateException should be thrown when getCategory is called if the category is not set.");
        } catch (IllegalStateException ex) {
            // Good!
        }
    }

    /**
     * Helper method to check the default value of version field, which is null.
     *
     * @param   scorecard
     *          The Scorecard instance to check (will be down casted).
     */
    private void checkDefaultVersion(NamedScorecardStructure scorecard) {
        checkVersion(scorecard, null, "The version should be null.");
    }

    /**
     * Helper method to check the default value of minScore field, which is Float.NaN, which results in an
     * IllegalStateException when getMinScore is called.
     *
     * @param   scorecard
     *          The Scorecard instance to check (will be down casted).
     */
    private void checkDefaultMinScore(NamedScorecardStructure scorecard) {
        try {
            ((Scorecard) scorecard).getMinScore();
            fail("An IllegalStateException is expected since the minimum score has not yet been set.");
        } catch (IllegalStateException ex) {
            // Good!
        }
    }

    /**
     * Helper method to check the default value of maxScore field, which is Float.NaN, which results in an
     * IllegalStateException when getMaxScore is called.
     *
     * @param   scorecard
     *          The Scorecard instance to check (will be down casted).
     */
    private void checkDefaultMaxScore(NamedScorecardStructure scorecard) {
        try {
            ((Scorecard) scorecard).getMaxScore();
            fail("An IllegalStateException is expected since the maximum score has not yet been set.");
        } catch (IllegalStateException ex) {
            // Good!
        }
    }

    /**
     * Helper method to check the default value of group field, which is an empty list.
     *
     * @param   scorecard
     *          The Scorecard instance to check (will be down casted).
     */
    private void checkDefaultGroups(NamedScorecardStructure scorecard) {
        checkGroups(scorecard, new Group[0], "The groups should be an empty list.");
    }

    /**
     * Helper method to check the default value of inUse field, which is false.
     *
     * @param   scorecard
     *          The Scorecard instance to check (will be down casted).
     */
    private void checkDefaultInUse(NamedScorecardStructure scorecard) {
        checkInUse(scorecard, false, "The inUse should be false.");
    }

    /**
     * Helper method to check the default value of creationUser field, which is null.
     *
     * @param   scorecard
     *          The Scorecard instance to check (will be down casted).
     */
    private void checkDefaultCreationUser(NamedScorecardStructure scorecard) {
        checkCreationUser(scorecard, null, "The creationUser should be null.");
    }

    /**
     * Helper method to check the default value of creationTimestamp field, which is null.
     *
     * @param   scorecard
     *          The Scorecard instance to check (will be down casted).
     */
    private void checkDefaultCreationTimestamp(NamedScorecardStructure scorecard) {
        checkCreationTimestamp(scorecard, null, "The creationTimestamp should be null.");
    }

    /**
     * Helper method to check the default value of modificationUser field, which is null.
     *
     * @param   scorecard
     *          The Scorecard instance to check (will be down casted).
     */
    private void checkDefaultModificationUser(NamedScorecardStructure scorecard) {
        checkCreationUser(scorecard, null, "The modificationUser should be null.");
    }

    /**
     * Helper method to check the default value of modificationTimestamp field, which is null.
     *
     * @param   scorecard
     *          The Scorecard instance to check (will be down casted).
     */
    private void checkDefaultModificationTimestamp(NamedScorecardStructure scorecard) {
        checkModificationTimestamp(scorecard, null, "The modificationTimestamp should be null.");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor() Tests

    /**
     * Ensures that the zero argument constructor properly sets the scorecardStatus field, by checking that
     * the getScorecardStatus method returns a null.
     */
    public void test0CtorSetsScorecardStatus() {
        checkDefaultScorecardStatus(createInstance());
    }

    /**
     * Ensures that the zero argument constructor properly sets the scorecardType field, by checking that
     * the getScorecardType method returns a null.
     */
    public void test0CtorSetsScorecardType() {
        checkDefaultScorecardType(createInstance());
    }

    /**
     * Ensures that the zero argument constructor properly sets the category field, by checking that the
     * getCategory method throws an IllegalStateException.
     */
    public void test0CtorSetsCategory() {
        checkDefaultCategory(createInstance());
    }

    /**
     * Ensures that the zero argument constructor properly sets the version field, by checking that the
     * getVersion method returns a null.
     */
    public void test0CtorSetsVersion() {
        checkDefaultVersion(createInstance());
    }

    /**
     * Ensures that the zero argument constructor properly sets the minScore field, by checking that
     * the getMinScore method returns a Float.NaN.
     */
    public void test0CtorSetsMinScore() {
        checkDefaultMinScore(createInstance());
    }

    /**
     * Ensures that the zero argument constructor properly sets the maxScore field, by checking that
     * the getMaxScore method returns a Float.NaN.
     */
    public void test0CtorSetsMaxScore() {
        checkDefaultMaxScore(createInstance());
    }

    /**
     * Ensures that the zero argument constructor properly sets the groups field, by checking that the
     * getAllGroups method returns an empty list.
     */
    public void test0CtorSetsGroups() {
        checkDefaultGroups(createInstance());
    }

    /**
     * Ensures that the zero argument constructor properly sets the inUse field, by checking that the
     * isInUse method returns false.
     */
    public void test0CtorSetsInUse() {
        checkDefaultInUse(createInstance());
    }

    /**
     * Ensures that the zero argument constructor properly sets the creationUser field, by checking that
     * the getCreationUser method returns a null.
     */
    public void test0CtorSetsCreationUser() {
        checkDefaultCreationUser(createInstance());
    }

    /**
     * Ensures that the zero argument constructor properly sets the creationTimestamp field, by checking
     * that the getCreationTimestamp method returns a null.
     */
    public void test0CtorSetsCreationTimestamp() {
        checkDefaultCreationTimestamp(createInstance());
    }

    /**
     * Ensures that the zero argument constructor properly sets the modificationUser field, by checking
     * that the getModificationUser method returns a null.
     */
    public void test0CtorSetsModificationUser() {
        checkDefaultModificationUser(createInstance());
    }

    /**
     * Ensures that the zero argument constructor properly sets the modificationTimestamp field, by
     * checking that the getModificationTimestamp method returns a null.
     */
    public void test0CtorSetsModificationTimestamp() {
        checkDefaultModificationTimestamp(createInstance());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor(long) Tests

    /**
     * Ensures that the one argument constructor properly sets the scorecardStatus field, by checking that
     * the getScorecardStatus method returns a null.
     */
    public void test1CtorSetsScorecardStatus() {
        checkDefaultScorecardStatus(createInstance1());
    }

    /**
     * Ensures that the one argument constructor properly sets the scorecardType field, by checking that
     * the getScorecardType method returns a null.
     */
    public void test1CtorSetsScorecardType() {
        checkDefaultScorecardType(createInstance1());
    }

    /**
     * Ensures that the one argument constructor properly sets the category field, by checking that the
     * getCategory method throws an IllegalStateException.
     */
    public void test1CtorSetsCategory() {
        checkDefaultCategory(createInstance1());
    }

    /**
     * Ensures that the one argument constructor properly sets the version field, by checking that the
     * getVersion method returns a null.
     */
    public void test1CtorSetsVersion() {
        checkDefaultVersion(createInstance1());
    }

    /**
     * Ensures that the one argument constructor properly sets the minScore field, by checking that
     * the getMinScore method returns a Float.NaN.
     */
    public void test1CtorSetsMinScore() {
        checkDefaultMinScore(createInstance1());
    }

    /**
     * Ensures that the one argument constructor properly sets the maxScore field, by checking that
     * the getMaxScore method returns a Float.NaN.
     */
    public void test1CtorSetsMaxScore() {
        checkDefaultMaxScore(createInstance1());
    }

    /**
     * Ensures that the one argument constructor properly sets the groups field, by checking that the
     * getAllGroups method returns an empty list.
     */
    public void test1CtorSetsGroups() {
        checkDefaultGroups(createInstance1());
    }

    /**
     * Ensures that the one argument constructor properly sets the inUse field, by checking that the
     * isInUse method returns false.
     */
    public void test1CtorSetsInUse() {
        checkDefaultInUse(createInstance1());
    }

    /**
     * Ensures that the one argument constructor properly sets the creationUser field, by checking that
     * the getCreationUser method returns a null.
     */
    public void test1CtorSetsCreationUser() {
        checkDefaultCreationUser(createInstance1());
    }

    /**
     * Ensures that the one argument constructor properly sets the creationTimestamp field, by checking
     * that the getCreationTimestamp method returns a null.
     */
    public void test1CtorSetsCreationTimestamp() {
        checkDefaultCreationTimestamp(createInstance1());
    }

    /**
     * Ensures that the one argument constructor properly sets the modificationUser field, by checking
     * that the getModificationUser method returns a null.
     */
    public void test1CtorSetsModificationUser() {
        checkDefaultModificationUser(createInstance1());
    }

    /**
     * Ensures that the one argument constructor properly sets the modificationTimestamp field, by
     * checking that the getModificationTimestamp method returns a null.
     */
    public void test1CtorSetsModificationTimestamp() {
        checkDefaultModificationTimestamp(createInstance1());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor(long, String) Tests

    /**
     * Ensures that the two argument constructor properly sets the scorecardStatus field, by checking that
     * the getScorecardStatus method returns a null.
     */
    public void test2CtorSetsScorecardStatus() {
        checkDefaultScorecardStatus(createInstance2());
    }

    /**
     * Ensures that the two argument constructor properly sets the scorecardType field, by checking that
     * the getScorecardType method returns a null.
     */
    public void test2CtorSetsScorecardType() {
        checkDefaultScorecardType(createInstance2());
    }

    /**
     * Ensures that the two argument constructor properly sets the category field, by checking that the
     * getCategory method throws an IllegalStateException.
     */
    public void test2CtorSetsCategory() {
        checkDefaultCategory(createInstance2());
    }

    /**
     * Ensures that the two argument constructor properly sets the version field, by checking that the
     * getVersion method returns a null.
     */
    public void test2CtorSetsVersion() {
        checkDefaultVersion(createInstance2());
    }

    /**
     * Ensures that the two argument constructor properly sets the minScore field, by checking that
     * the getMinScore method returns a Float.NaN.
     */
    public void test2CtorSetsMinScore() {
        checkDefaultMinScore(createInstance2());
    }

    /**
     * Ensures that the two argument constructor properly sets the maxScore field, by checking that
     * the getMaxScore method returns a Float.NaN.
     */
    public void test2CtorSetsMaxScore() {
        checkDefaultMaxScore(createInstance2());
    }

    /**
     * Ensures that the two argument constructor properly sets the groups field, by checking that the
     * getAllGroups method returns an empty list.
     */
    public void test2CtorSetsGroups() {
        checkDefaultGroups(createInstance2());
    }

    /**
     * Ensures that the two argument constructor properly sets the inUse field, by checking that the
     * isInUse method returns false.
     */
    public void test2CtorSetsInUse() {
        checkDefaultInUse(createInstance2());
    }

    /**
     * Ensures that the two argument constructor properly sets the creationUser field, by checking that
     * the getCreationUser method returns a null.
     */
    public void test2CtorSetsCreationUser() {
        checkDefaultCreationUser(createInstance2());
    }

    /**
     * Ensures that the two argument constructor properly sets the creationTimestamp field, by checking
     * that the getCreationTimestamp method returns a null.
     */
    public void test2CtorSetsCreationTimestamp() {
        checkDefaultCreationTimestamp(createInstance2());
    }

    /**
     * Ensures that the two argument constructor properly sets the modificationUser field, by checking
     * that the getModificationUser method returns a null.
     */
    public void test2CtorSetsModificationUser() {
        checkDefaultModificationUser(createInstance2());
    }

    /**
     * Ensures that the two argument constructor properly sets the modificationTimestamp field, by
     * checking that the getModificationTimestamp method returns a null.
     */
    public void test2CtorSetsModificationTimestamp() {
        checkDefaultModificationTimestamp(createInstance2());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Helper Methods

    /**
     * Down casts the NamedScorecardStructure back to a Scorecard.
     *
     * @return  The Scorecard instance created in the setUp method.
     */
    private Scorecard getScorecardInstance() {
        return (Scorecard) getInstance();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // scorecardStatus Mutator Tests

    /**
     * Ensures that the setScorecardStatus method sets the scorecard status properly, by checking that the
     * getScorecardStatus method returns the original scorecard status.
     */
    public void testSetScorecardStatusWorks() {
        getScorecardInstance().setScorecardStatus(SCORECARD_STATUS);
        checkScorecardStatus(getInstance(), SCORECARD_STATUS, "The scorecard statuses do not match.");
    }

    /**
     * Ensures that the resetScorecardStatus method resets the scorecard status properly, by checking that
     * the getScorecardStatus method returns a null.
     */
    public void testResetScorecardStatusWorks() {
        getScorecardInstance().setScorecardStatus(SCORECARD_STATUS);
        getScorecardInstance().resetScorecardStatus();
        checkDefaultScorecardStatus(getInstance());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // scorecardType Mutator Tests

    /**
     * Ensures that the setScorecardType method sets the scorecard type properly, by checking that the
     * getScorecardType method returns the original scorecard type.
     */
    public void testSetScorecardTypeWorks() {
        getScorecardInstance().setScorecardType(SCORECARD_TYPE);
        checkScorecardType(getInstance(), SCORECARD_TYPE, "The scorecard types do not match.");
    }

    /**
     * Ensures that the resetScorecardType method resets the scorecard type properly, by checking that
     * the getScorecardType method returns a null.
     */
    public void testResetScorecardTypeWorks() {
        getScorecardInstance().setScorecardType(SCORECARD_TYPE);
        getScorecardInstance().resetScorecardType();
        checkDefaultScorecardType(getInstance());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // category Mutator Tests

    /**
     * Ensures that the setCategory method throws an IllegalArgumentException when given a negative
     * category identifier.
     */
    public void testSetCategoryThrowsOnNegativeCategory() {
        try {
            getScorecardInstance().setCategory(-1);
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
            getScorecardInstance().setCategory(0);
            fail("An IllegalArgumentException is expected when setting the category to 0.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the setCategory method sets the category properly, by checking that the
     * getCategory method returns the original category.
     */
    public void testSetCategoryWorks() {
        getScorecardInstance().setCategory(CATEGORY);
        checkCategory(getInstance(), CATEGORY, "The categories do not match.");
    }

    /**
     * Ensures that the resetCategory method resets the scorecard type properly, by checking that
     * the getCategory method throws an IllegalStateException.
     */
    public void testResetCategoryWorks() {
        getScorecardInstance().setCategory(CATEGORY);
        getScorecardInstance().resetCategory();
        checkDefaultCategory(getInstance());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // version Mutator Tests

    /**
     * Ensures that the setVersion method sets the version properly, by checking that the getVersion
     * method returns the original version.
     */
    public void testSetVersionWorks() {
        getScorecardInstance().setVersion(VERSION);
        checkVersion(getInstance(), VERSION, "The versions do not match.");
    }

    /**
     * Ensures that the resetVersion method resets the version properly, by checking that the
     * getVersion method returns a null.
     */
    public void testResetVersionWorks() {
        getScorecardInstance().setVersion(VERSION);
        getScorecardInstance().resetVersion();
        checkDefaultVersion(getInstance());
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
            getScorecardInstance().setMinScore(minScore);
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
        getScorecardInstance().setMaxScore(MAX_SCORE);

        try {
            getScorecardInstance().setMinScore(MAX_SCORE + 0.01f);
            fail("An IllegalStateException is expected when the minScore is greater than the maxScore.");
        } catch (IllegalStateException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the setMinScore method sets the minimum score properly, by checking that the
     * getMinScore method returns the original minimum score.
     */
    public void testSetMinScoreWorks() {
        getScorecardInstance().setMaxScore(MAX_SCORE);
        getScorecardInstance().setMinScore(MIN_SCORE);
        checkMinScore(getInstance(), MIN_SCORE, "The minimum scores do not match.");
    }

    /**
     * Ensures that the resetMinScore method resets the minimum score properly, by checking that the
     * getMinScore method returns a Float.NaN.
     */
    public void testResetMinScoreWorks() {
        getScorecardInstance().setMinScore(MIN_SCORE);
        getScorecardInstance().resetMinScore();
        checkDefaultMinScore(getInstance());
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
            getScorecardInstance().setMaxScore(maxScore);
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
        getScorecardInstance().setMinScore(MIN_SCORE);

        try {
            getScorecardInstance().setMaxScore(MIN_SCORE - 0.01f);
            fail("An IllegalStateException is expected when the maxScore is less than the minScore.");
        } catch (IllegalStateException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the setMaxScore method sets the maximum score properly, by checking that the
     * getMaxScore method returns the original maximum score.
     */
    public void testSetMaxScoreWorks() {
        getScorecardInstance().setMinScore(MIN_SCORE);
        getScorecardInstance().setMaxScore(MAX_SCORE);
        checkMaxScore(getInstance(), MAX_SCORE, "The maximum scores do not match.");
    }

    /**
     * Ensures that the resetMaxScore method resets the maximum score properly, by checking that the
     * getMaxScore method returns a Float.NaN.
     */
    public void testResetMaxScoreWorks() {
        getScorecardInstance().setMaxScore(MAX_SCORE);
        getScorecardInstance().resetMaxScore();
        checkDefaultMaxScore(getInstance());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // addGroup(Group) Method Tests

    /**
     * Ensures that the addGroup method throws an IllegalArgumentException when given a
     * null argument.
     */
    public void testAddGroupThrowsOnNull() {
        try {
            getScorecardInstance().addGroup(null);
            fail("An IllegalArgumentException is expected when addGroup(null) is called.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the addGroup method works properly when adding both duplicate and non-duplicate
     * groups.
     */
    public void testAddGroupWorks() {
        getScorecardInstance().addGroup(GROUP1);
        getScorecardInstance().addGroup(GROUP2);
        getScorecardInstance().addGroup(GROUP1);

        checkGroups(getInstance(), new Group[] {GROUP1, GROUP2}, "The groups are not correct.");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // addGroup(Group[]) Method Tests

    /**
     * Ensures that the addGroups method throws an IllegalArgumentException when given a
     * null argument.
     */
    public void testAddGroupsThrowsOnIsNull() {
        try {
            getScorecardInstance().addGroups(null);
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
            getScorecardInstance().addGroups(new Group[] {null});
            fail("An IllegalArgumentException is expected when addGroups(new Group[]{null}) is called.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the addGroups method works properly when adding both duplicate and non-duplicate
     * groups.
     */
    public void testAddGroupsWorks() {
        getScorecardInstance().addGroups(
                new Group[] {GROUP1, GROUP2, GROUP1, GROUP3, GROUP2});

        checkGroups(
                getInstance(), new Group[] {GROUP1, GROUP2, GROUP3},
                "The groups are not correct.");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // insertGroup(Group, int) Method Tests

    /**
     * Ensures that the insertGroup method throws an IllegalArgumentException when given a
     * null question.
     */
    public void testInsertGroupThrowsOnNull() {
        try {
            getScorecardInstance().insertGroup(null, 0);
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
            getScorecardInstance().insertGroup(GROUP1, -1);
            fail("An IndexOutOfBoundsException is expected when insertGroup(GROUP1, -1) is called.");
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
            getScorecardInstance().insertGroup(GROUP1, 1);
            fail("An IndexOutOfBoundsException is expected when insertGroup(GROUP1, 1) is called.");
        } catch (IndexOutOfBoundsException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the insertGroup method works properly when inserting both duplicate and
     * non-duplicate groups.
     */
    public void testInsertGroupWorks() {
        getScorecardInstance().insertGroup(GROUP1, 0);
        getScorecardInstance().insertGroup(GROUP2, 0);
        getScorecardInstance().insertGroup(GROUP1, 0);

        checkGroups(getInstance(), new Group[] {GROUP2, GROUP1}, "The groups are not correct.");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // removeGroup(Group) Method Tests

    /**
     * Ensures that the removeGroup(Group) method throws an IllegalArgumentException when
     * given a null argument.
     */
    public void testRemoveGroupThrowsOnNull() {
        try {
            getScorecardInstance().removeGroup(null);
            fail("An IllegalArgumentException is expected when removeGroup(null) is called.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the removeGroup(Group) method works properly.
     */
    public void testRemoveGroupWorks() {
        getScorecardInstance().addGroup(GROUP1);
        getScorecardInstance().addGroup(GROUP2);

        getScorecardInstance().removeGroup(GROUP1);
        getScorecardInstance().removeGroup(GROUP3);

        checkGroups(getInstance(), new Group[] {GROUP2}, "The groups are not correct.");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // removeGroup(int) Method Tests

    /**
     * Ensures that the removeGroup(int) method throws an IndexOutOfBoundsException when given an
     * index that is too small.
     */
    public void testRemoveGroupIntThrowsOnSmallIndex() {
        try {
            getScorecardInstance().removeGroup(-1);
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
        getScorecardInstance().addGroup(GROUP1);

        try {
            getScorecardInstance().removeGroup(1);
            fail("An IndexOutOfBoundsException is expected when removeGroup(1) is called.");
        } catch (IndexOutOfBoundsException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the removeGroup(int) method works properly.
     */
    public void testRemoveGroupIntWorks() {
        getScorecardInstance().addGroup(GROUP1);
        getScorecardInstance().removeGroup(0);

        checkGroups(getInstance(), new Group[0], "The groups list should be emptied.");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // removeGroups(Group[]) Method Tests

    /**
     * Ensures that the removeGroups method throws an IllegalArgumentException when given a
     * null argument.
     */
    public void testRemoveGroupsThrowsOnIsNull() {
        try {
            getScorecardInstance().removeGroups(null);
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
            getScorecardInstance().removeGroups(new Group[] {null});
            fail("An IllegalArgumentException is expected when removeGroups(new Group[]{null}) is called.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the removeGroups method works properly.
     */
    public void testRemoveGroupsWorks() {
        getScorecardInstance().addGroups(new Group[] {GROUP1, GROUP2});
        getScorecardInstance().removeGroups(new Group[] {GROUP3, GROUP1});

        checkGroups(getInstance(), new Group[] {GROUP2}, "The groups are not correct.");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // clearGroups Method Tests

    /**
     * Ensures that the clearGroups method works properly.
     */
    public void testClearGroupsWorks() {
        getScorecardInstance().addGroup(GROUP1);
        getScorecardInstance().addGroup(GROUP2);
        getScorecardInstance().addGroup(GROUP3);
        getScorecardInstance().clearGroups();

        checkGroups(getInstance(), new Group[0], "The groups list should be emptied.");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // getGroup Method Tests

    /**
     * Ensures that the getGroup method throws an IndexOutOfBoundsException when given an
     * index that is too small.
     */
    public void testGetGroupThrowsOnSmallIndex() {
        try {
            getScorecardInstance().getGroup(-1);
            fail("An IndexOutOfBoundsException is expected when getGroup(-1) is called.");
        } catch (IndexOutOfBoundsException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the getGroup method throws an IndexOutOfBoundsException when given an
     * index that is too large.
     */
    public void testGetGroupThrowsOnLargeIndex() {
        getScorecardInstance().addGroup(GROUP1);

        try {
            getScorecardInstance().getGroup(1);
            fail("An IndexOutOfBoundsException is expected when getGroup(1) is called.");
        } catch (IndexOutOfBoundsException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the getGroup method works properly.
     */
    public void testGetGroupWorks() {
        getScorecardInstance().addGroup(GROUP1);

        assertEquals(
                "The groups do not match.",
                GROUP1, getScorecardInstance().getGroup(0));
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // getNumberOfGroups() Method Tests

    /**
     * Ensures that the getNumberOfGroups method works properly.
     */
    public void testGetNumberOfGroupsWorks() {
        getScorecardInstance().addGroup(GROUP1);
        getScorecardInstance().addGroup(GROUP2);
        getScorecardInstance().addGroup(GROUP3);

        assertEquals(
                "The number of groups do not match.",
                3, getScorecardInstance().getNumberOfGroups());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // checkWeights(float) Method Tests

    /**
     * Ensures that the checkWeights method throws an IllegalArgumentException when given a
     * negative real number.
     */
    public void testCheckWeightsThrowsOnNegative() {
        checkCheckWeightsThrowsIAE(-0.01f, " the tolerance is negative.");
    }

    /**
     * Ensures that the checkWeights method throws an IllegalArgumentException when given
     * negative infinity (Float.NEGATIVE_INFINITY).
     */
    public void testCheckWeightsThrowsOnNegativeInfinity() {
        checkCheckWeightsThrowsIAE(Float.NEGATIVE_INFINITY, " the tolerance is negative infinity.");
    }

    /**
     * Ensures that the checkWeights method throws an IllegalArgumentException when given
     * positive infinity (Float.POSITIVE_INFINITY).
     */
    public void testCheckWeightsThrowsOnPositiveInfinity() {
        checkCheckWeightsThrowsIAE(Float.POSITIVE_INFINITY, " the tolerance is positive infinity.");
    }

    /**
     * Ensures that the checkWeights method throws an IllegalArgumentException when given
     * not a number (Float.NaN).
     */
    public void testCheckWeightsThrowsOnNaN() {
        checkCheckWeightsThrowsIAE(Float.NaN, " the tolerance is not a number (NaN).");
    }

    /**
     * Ensures that the checkWeights method throws an IllegalArgumentException when given the
     * specified argument.
     *
     * @param   tolerance
     *          The tolerance that should generate an IAE being thrown.
     * @param   message
     *          The message to use in the error message.
     */
    private void checkCheckWeightsThrowsIAE(float tolerance, String message) {
        try {
            getScorecardInstance().checkWeights(tolerance);
            fail("An IllegalArgumentException is expected when " + message);
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the checkWeights method returns true when the weights add up to 100, and each
     * section's checkWeights method returns true.
     */
    public void testCheckWeightsReturnsTrue() {
        getScorecardInstance().addGroup(GROUP1);
        getScorecardInstance().addGroup(GROUP3);

        assertTrue(
                "All weights add up to 100, so true should be returned.",
                getScorecardInstance().checkWeights());
    }

    /**
     * Ensures that the checkWeights method returns true when the weights add up to 100 (plus or
     * minus a large tolerance).
     */
    public void testCheckWeightsReturnsTrueHighTolerance() {
        getScorecardInstance().addGroup(GROUP2);

        assertTrue(
                "All weights add up to 100 +/- 60, so true should be returned.",
                getScorecardInstance().checkWeights(60f));
    }

    /**
     * Ensures that the checkWeights method returns false when the weights of the groups list
     * don't add up to 100.
     */
    public void testCheckWeightsReturnsFalseForGroupsList() {
        getScorecardInstance().addGroup(GROUP1);

        assertFalse(
                "The weights don't add up to 100, so false should be returned.",
                getScorecardInstance().checkWeights());
    }

    /**
     * Ensures that the checkWeights method returns false when the weights of a group don't
     * add up to 100.
     */
    public void testCheckWeightsReturnsFalseForBadGroup() {
        getScorecardInstance().addGroup(GROUP1);
        getScorecardInstance().addGroup(GROUP2);

        assertFalse(
                "One of the groups don't weigh up to 100, so false should be returned.",
                getScorecardInstance().checkWeights(1e-9f));
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // inUse Mutator Tests

    /**
     * Ensures that the setInUse method sets the in use switch properly, by checking that the isInUse
     * method returns the original in use switch.
     */
    public void testSetInUseWorks() {
        getScorecardInstance().setInUse(true);
        checkInUse(getInstance(), true, "The in use switches do not match.");
    }

    /**
     * Ensures that the resetInUse method resets the in use switch properly, by checking that the
     * isInUse method returns false.
     */
    public void testResetInUseWorks() {
        getScorecardInstance().setInUse(true);
        getScorecardInstance().resetInUse();
        checkDefaultInUse(getInstance());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // creationUser/Timestamp + modificationUser/Timestamp Mutator Tests

    /**
     * Ensures that the setCreationUser method sets the creation user properly, by checking that the
     * getCreationUser method returns the original creation user.
     */
    public void testSetCreationUserWorks() {
        getScorecardInstance().setCreationUser(CREATION_USER);
        checkCreationUser(getInstance(), CREATION_USER, "The creation users do not match.");
    }

    /**
     * Ensures that the setCreationTimestamp method sets the creation timestamp properly, by checking
     * that the getCreationTimestamp method returns the original creation timestamp.
     */
    public void testSetCreationTimestampWorks() {
        getScorecardInstance().setCreationTimestamp(CREATION_TIMESTAMP);
        checkCreationTimestamp(getInstance(), CREATION_TIMESTAMP, "The creation timestamps do not match.");
    }

    /**
     * Ensures that the setModificationUser method sets the modification user properly, by checking
     * that the getModificationUser method returns the original modification user.
     */
    public void testSetModificationUserWorks() {
        getScorecardInstance().setModificationUser(MODIFICATION_USER);
        checkModificationUser(getInstance(), MODIFICATION_USER, "The modification users do not match.");
    }

    /**
     * Ensures that the setModificationTimestamp method sets the modification timestamp properly, by
     * checking that the getModificationTimestamp method returns the original modificiation timestamp.
     */
    public void testSetModificationTimestampWorks() {
        getScorecardInstance().setModificationTimestamp(MODIFICATION_TIMESTAMP);
        checkModificationTimestamp(getInstance(), MODIFICATION_TIMESTAMP, "The modification timestamps do not match.");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Serialization Tests

    /**
     * Ensures that serialization of the scorecardStatus field works properly.
     *
     * @throws  ClassNotFoundException
     *          Couldn't deserialize properly.
     * @throws  IOException
     *          Couldn't serialize or deserialize properly.
     */
    public void testSerializeScorecardStatusWorks() throws ClassNotFoundException, IOException {
        getScorecardInstance().setScorecardStatus(SCORECARD_STATUS);
        Scorecard copy = (Scorecard) serializeAndDeserialize();

        assertEquals(
                "The scorecard status identifiers did not match.",
                SCORECARD_STATUS.getId(), copy.getScorecardStatus().getId());

        assertEquals(
                "The scorecard status names did not match.",
                SCORECARD_STATUS.getName(), copy.getScorecardStatus().getName());
    }

    /**
     * Ensures that serialization of the scorecardType field works properly.
     *
     * @throws  ClassNotFoundException
     *          Couldn't deserialize properly.
     * @throws  IOException
     *          Couldn't serialize or deserialize properly.
     */
    public void testSerializeScorecardTypeWorks() throws ClassNotFoundException, IOException {
        getScorecardInstance().setScorecardType(SCORECARD_TYPE);
        Scorecard copy = (Scorecard) serializeAndDeserialize();

        assertEquals(
                "The scorecard type identifiers did not match.",
                SCORECARD_TYPE.getId(), copy.getScorecardType().getId());

        assertEquals(
                "The scorecard type names did not match.",
                SCORECARD_TYPE.getName(), copy.getScorecardType().getName());
    }

    /**
     * Ensures that serialization of the category field works properly.
     *
     * @throws  ClassNotFoundException
     *          Couldn't deserialize properly.
     * @throws  IOException
     *          Couldn't serialize or deserialize properly.
     */
    public void testSerializeCategoryWorks() throws ClassNotFoundException, IOException {
        getScorecardInstance().setCategory(CATEGORY);
        Scorecard copy = (Scorecard) serializeAndDeserialize();
        checkCategory(copy, CATEGORY, "The categories did not match.");
    }

    /**
     * Ensures that serialization of the version field works properly.
     *
     * @throws  ClassNotFoundException
     *          Couldn't deserialize properly.
     * @throws  IOException
     *          Couldn't serialize or deserialize properly.
     */
    public void testSerializeVersionWorks() throws ClassNotFoundException, IOException {
        getScorecardInstance().setVersion(VERSION);
        Scorecard copy = (Scorecard) serializeAndDeserialize();
        checkVersion(copy, VERSION, "The versions did not match.");
    }

    /**
     * Ensures that serialization of the minScore field works properly.
     *
     * @throws  ClassNotFoundException
     *          Couldn't deserialize properly.
     * @throws  IOException
     *          Couldn't serialize or deserialize properly.
     */
    public void testSerializeMinScoreWorks() throws ClassNotFoundException, IOException {
        getScorecardInstance().setMinScore(MIN_SCORE);
        Scorecard copy = (Scorecard) serializeAndDeserialize();
        checkMinScore(copy, MIN_SCORE, "The minimum scores did not match.");
    }

    /**
     * Ensures that serialization of the maxScore field works properly.
     *
     * @throws  ClassNotFoundException
     *          Couldn't deserialize properly.
     * @throws  IOException
     *          Couldn't serialize or deserialize properly.
     */
    public void testSerializeMaxScoreWorks() throws ClassNotFoundException, IOException {
        getScorecardInstance().setMaxScore(MAX_SCORE);
        Scorecard copy = (Scorecard) serializeAndDeserialize();
        checkMaxScore(copy, MAX_SCORE, "The maximum score did not match.");
    }

    /**
     * Ensures that serialization of the groups field works properly.
     *
     * @throws  ClassNotFoundException
     *          Couldn't deserialize properly.
     * @throws  IOException
     *          Couldn't serialize or deserialize properly.
     */
    public void testSerializeGroupsWorks() throws ClassNotFoundException, IOException {
        getScorecardInstance().addGroup(GROUP1);
        Scorecard copy = (Scorecard) serializeAndDeserialize();

        assertEquals(
                "The number of groups did not match.",
                1, copy.getNumberOfGroups());

        assertEquals(
                "The group identifiers did not match.",
                GROUP1.getId(), copy.getGroup(0).getId());
    }

    /**
     * Ensures that serialization of the inUse field works properly.
     *
     * @throws  ClassNotFoundException
     *          Couldn't deserialize properly.
     * @throws  IOException
     *          Couldn't serialize or deserialize properly.
     */
    public void testSerializeInUseWorks() throws ClassNotFoundException, IOException {
        getScorecardInstance().setInUse(true);
        Scorecard copy = (Scorecard) serializeAndDeserialize();
        checkInUse(copy, true, "The in use switches did not match.");
    }

    /**
     * Ensures that serialization of the creationUser field works properly.
     *
     * @throws  ClassNotFoundException
     *          Couldn't deserialize properly.
     * @throws  IOException
     *          Couldn't serialize or deserialize properly.
     */
    public void testSerializeCreationUserWorks() throws ClassNotFoundException, IOException {
        getScorecardInstance().setCreationUser(CREATION_USER);
        Scorecard copy = (Scorecard) serializeAndDeserialize();
        checkCreationUser(copy, CREATION_USER, "The creation users did not match.");
    }

    /**
     * Ensures that serialization of the creationTimestamp field works properly.
     *
     * @throws  ClassNotFoundException
     *          Couldn't deserialize properly.
     * @throws  IOException
     *          Couldn't serialize or deserialize properly.
     */
    public void testSerializeCreationTimestampWorks() throws ClassNotFoundException, IOException {
        getScorecardInstance().setCreationTimestamp(CREATION_TIMESTAMP);
        Scorecard copy = (Scorecard) serializeAndDeserialize();
        checkCreationTimestamp(copy, CREATION_TIMESTAMP, "The creation timestamps did not match.");
    }

    /**
     * Ensures that serialization of the modificationUser field works properly.
     *
     * @throws  ClassNotFoundException
     *          Couldn't deserialize properly.
     * @throws  IOException
     *          Couldn't serialize or deserialize properly.
     */
    public void testSerializeModificationUserWorks() throws ClassNotFoundException, IOException {
        getScorecardInstance().setModificationUser(MODIFICATION_USER);
        Scorecard copy = (Scorecard) serializeAndDeserialize();
        checkModificationUser(copy, MODIFICATION_USER, "The in use switches did not match.");
    }

    /**
     * Ensures that serialization of the modificationTimestamp field works properly.
     *
     * @throws  ClassNotFoundException
     *          Couldn't deserialize properly.
     * @throws  IOException
     *          Couldn't serialize or deserialize properly.
     */
    public void testSerializeModificationTimestampWorks() throws ClassNotFoundException, IOException {
        getScorecardInstance().setModificationTimestamp(MODIFICATION_TIMESTAMP);
        Scorecard copy = (Scorecard) serializeAndDeserialize();
        checkModificationTimestamp(copy, MODIFICATION_TIMESTAMP, "The modification timestamps did not match.");
    }
}
