/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.scorecard.data;

import java.io.IOException;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Contains the unit tests for the {@link Section} class.
 *
 * @author      UFP2161
 * @copyright   Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * @version     1.0
 */
public class SectionTest extends WeightedScorecardStructureTest {

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

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Static Methods

    /**
     * Creates a test suite for the tests in this test case.
     *
     * @return  A TestSuite for this test case.
     */
    public static Test suite() {
        return new TestSuite(SectionTest.class);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Overridden Abstract Methods

    /**
     * Creates a new Section instance using the zero argument constructor.
     *
     * @return  A Section instance created using the zero argument constructor.
     */
    protected NamedScorecardStructure createInstance() {
        return new Section();
    }

    /**
     * Creates a new Section instance using the one argument constructor.
     *
     * @param   id
     *          The identifier to initialize with.
     *
     * @return  A Section instance created using the one argument constructor.
     */
    protected NamedScorecardStructure createInstance(long id) {
        return new Section(id);
    }

    /**
     * Creates a new Section instance using the two argument constructor.
     *
     * @param   id
     *          The identifier to initialize with.
     * @param   name
     *          The name to initialize with.
     *
     * @return  A Section instance created using the two argument constructor.
     */
    protected NamedScorecardStructure createInstance(long id, String name) {
        return new Section(id, name);
    }

    /**
     * Creates a new Section instance using the two argument constructor that takes a weight.
     *
     * @param   id
     *          The identifier to initialize with.
     * @param   weight
     *          The weight to initialize with.
     *
     * @return  A Section instance created using the two argument constructor that takes a weight.
     */
    protected WeightedScorecardStructure createInstance(long id, float weight) {
        return new Section(id, weight);
    }

    /**
     * Creates a new Section instance using the three argument constructor.
     *
     * @param   id
     *          The identifier to initialize with.
     * @param   name
     *          The name to initialize with.
     * @param   weight
     *          The weight to initialize with.
     *
     * @return  A Section instance created using the three argument constructor.
     */
    protected WeightedScorecardStructure createInstance(long id, String name, float weight) {
        return new Section(id, name, weight);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Field Checkers

    /**
     * Helper method to check the questions field.
     *
     * @param   section
     *          The Section instance to check (will be down casted).
     * @param   expected
     *          The expected value of the questions field.
     * @param   message
     *          The error message if the field values don't match.
     */
    private void checkQuestions(NamedScorecardStructure section, Question[] expected, String message) {
        TestUtil.assertArraysAreEqual(message, expected, ((Section) section).getAllQuestions());
    }

    /**
     * Helper method to check the default value of questions field, which is an empty list.
     *
     * @param   section
     *          The Section instance to check (will be down casted).
     */
    private void checkDefaultQuestions(NamedScorecardStructure section) {
        checkQuestions(section, new Question[0], "The questions should be an empty list.");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor Tests

    /**
     * Ensures that the zero argument constructor properly sets the questions field, by checking that the
     * getAllQuestions method returns an empty list.
     */
    public void test0CtorSetsQuestions() {
        checkDefaultQuestions(createInstance());
    }

    /**
     * Ensures that the one argument constructor properly sets the questions field, by checking that the
     * getAllQuestions method returns an empty list.
     */
    public void test1CtorSetsQuestions() {
        checkDefaultQuestions(createInstance1());
    }

    /**
     * Ensures that the two argument constructor properly sets the questions field, by checking that the
     * getAllQuestions method returns an empty list.
     */
    public void test2CtorSetsQuestions() {
        checkDefaultQuestions(createInstance2());
    }

    /**
     * Ensures that the two argument constructor that takes a weight properly sets the questions field,
     * by checking that the getAllQuestions method returns an empty list.
     */
    public void test2WeightCtorSetsQuestions() {
        checkDefaultQuestions(createInstance2Weight());
    }

    /**
     * Ensures that the three argument constructor properly sets the questions field, by checking that the
     * getAllQuestions method returns an empty list.
     */
    public void test3CtorSetsQuestions() {
        checkDefaultQuestions(createInstance3());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Helper Methods

    /**
     * Down casts the NamedScorecardStructure back to a Section.
     *
     * @return  The Section instance created in the setUp method.
     */
    private Section getSectionInstance() {
        return (Section) getInstance();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // addQuestion(Question) Method Tests

    /**
     * Ensures that the addQuestion method throws an IllegalArgumentException when given a
     * null argument.
     */
    public void testAddQuestionThrowsOnNull() {
        try {
            getSectionInstance().addQuestion(null);
            fail("An IllegalArgumentException is expected when addQuestion(null) is called.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the addQuestion method works properly when adding both duplicate and non-duplicate
     * questions.
     */
    public void testAddQuestionWorks() {
        getSectionInstance().addQuestion(QUESTION1);
        getSectionInstance().addQuestion(QUESTION2);
        getSectionInstance().addQuestion(QUESTION1);

        checkQuestions(getInstance(), new Question[] {QUESTION1, QUESTION2}, "The questions are not correct.");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // addQuestions(Question[]) Method Tests

    /**
     * Ensures that the addQuestions method throws an IllegalArgumentException when given a
     * null argument.
     */
    public void testAddQuestionsThrowsOnIsNull() {
        try {
            getSectionInstance().addQuestions(null);
            fail("An IllegalArgumentException is expected when addQuestions(null) is called.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the addQuestions method throws an IllegalArgumentException when given a
     * argument with a null.
     */
    public void testAddQuestionsThrowsOnHasNull() {
        try {
            getSectionInstance().addQuestions(new Question[] {null});
            fail("An IllegalArgumentException is expected when addQuestions(new Question[]{null}) is called.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the addQuestions method works properly when adding both duplicate and non-duplicate
     * questions.
     */
    public void testAddQuestionsWorks() {
        getSectionInstance().addQuestions(
                new Question[] {QUESTION1, QUESTION2, QUESTION1, QUESTION3, QUESTION2});

        checkQuestions(
                getInstance(), new Question[] {QUESTION1, QUESTION2, QUESTION3},
                "The questions are not correct.");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // insertQuestion(Question, int) Method Tests

    /**
     * Ensures that the insertQuestion method throws an IllegalArgumentException when given a
     * null question.
     */
    public void testInsertQuestionThrowsOnNull() {
        try {
            getSectionInstance().insertQuestion(null, 0);
            fail("An IllegalArgumentException is expected when insertQuestion(null, 0) is called.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the insertQuestion method throws an IndexOutOfBoundsException when given an
     * index that is too small.
     */
    public void testInsertQuestionThrowsOnSmallIndex() {
        try {
            getSectionInstance().insertQuestion(QUESTION1, -1);
            fail("An IndexOutOfBoundsException is expected when insertQuestion(QUESTION1, -1) is called.");
        } catch (IndexOutOfBoundsException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the insertQuestion method throws an IndexOutOfBoundsException when given an
     * index that is too large.
     */
    public void testInsertQuestionThrowsOnLargeIndex() {
        try {
            getSectionInstance().insertQuestion(QUESTION1, 1);
            fail("An IndexOutOfBoundsException is expected when insertQuestion(QUESTION1, 1) is called.");
        } catch (IndexOutOfBoundsException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the insertQuestions method works properly when inserting both duplicate and
     * non-duplicate questions.
     */
    public void testInsertQuestionWorks() {
        getSectionInstance().insertQuestion(QUESTION1, 0);
        getSectionInstance().insertQuestion(QUESTION2, 0);
        getSectionInstance().insertQuestion(QUESTION1, 0);

        checkQuestions(getInstance(), new Question[] {QUESTION2, QUESTION1}, "The questions are not correct.");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // removeQuestion(Question) Method Tests

    /**
     * Ensures that the removeQuestion(Question) method throws an IllegalArgumentException when
     * given a null argument.
     */
    public void testRemoveQuestionThrowsOnNull() {
        try {
            getSectionInstance().removeQuestion(null);
            fail("An IllegalArgumentException is expected when removeQuestion(null) is called.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the removeQuestion(Question) method works properly.
     */
    public void testRemoveQuestionWorks() {
        getSectionInstance().addQuestion(QUESTION1);
        getSectionInstance().addQuestion(QUESTION2);

        getSectionInstance().removeQuestion(QUESTION1);
        getSectionInstance().removeQuestion(QUESTION3);

        checkQuestions(getInstance(), new Question[] {QUESTION2}, "The questions are not correct.");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // removeQuestion(int) Method Tests

    /**
     * Ensures that the removeQuestion(int) method throws an IndexOutOfBoundsException when given an
     * index that is too small.
     */
    public void testRemoveQuestionIntThrowsOnSmallIndex() {
        try {
            getSectionInstance().removeQuestion(-1);
            fail("An IndexOutOfBoundsException is expected when removeQuestion(-1) is called.");
        } catch (IndexOutOfBoundsException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the removeQuestion(int) method throws an IndexOutOfBoundsException when given an
     * index that is too large.
     */
    public void testRemoveQuestionIntThrowsOnLargeIndex() {
        getSectionInstance().addQuestion(QUESTION1);

        try {
            getSectionInstance().removeQuestion(1);
            fail("An IndexOutOfBoundsException is expected when removeQuestion(1) is called.");
        } catch (IndexOutOfBoundsException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the removeQuestion(int) method works properly.
     */
    public void testRemoveQuestionIntWorks() {
        getSectionInstance().addQuestion(QUESTION1);
        getSectionInstance().removeQuestion(0);

        checkQuestions(getInstance(), new Question[0], "The questions list should be emptied.");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // removeQuestions(Question[]) Method Tests

    /**
     * Ensures that the removeQuestions method throws an IllegalArgumentException when given a
     * null argument.
     */
    public void testRemoveQuestionsThrowsOnIsNull() {
        try {
            getSectionInstance().removeQuestions(null);
            fail("An IllegalArgumentException is expected when removeQuestions(null) is called.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the removeQuestions method throws an IllegalArgumentException when given a
     * argument with a null.
     */
    public void testRemoveQuestionsThrowsOnHasNull() {
        try {
            getSectionInstance().removeQuestions(new Question[] {null});
            fail("An IllegalArgumentException is expected when removeQuestions(new Question[]{null}) is called.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the removeQuestions method works properly.
     */
    public void testRemoveQuestionsWorks() {
        getSectionInstance().addQuestions(new Question[] {QUESTION1, QUESTION2});
        getSectionInstance().removeQuestions(new Question[] {QUESTION3, QUESTION1});

        checkQuestions(getInstance(), new Question[] {QUESTION2}, "The questions are not correct.");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // clearQuestions Method Tests

    /**
     * Ensures that the clearQuestions method works properly.
     */
    public void testClearQuestionsWorks() {
        getSectionInstance().addQuestion(QUESTION1);
        getSectionInstance().addQuestion(QUESTION2);
        getSectionInstance().addQuestion(QUESTION3);
        getSectionInstance().clearQuestions();

        checkQuestions(getInstance(), new Question[0], "The questions list should be emptied.");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // getQuestion Method Tests

    /**
     * Ensures that the getQuestion method throws an IndexOutOfBoundsException when given an
     * index that is too small.
     */
    public void testGetQuestionThrowsOnSmallIndex() {
        try {
            getSectionInstance().getQuestion(-1);
            fail("An IndexOutOfBoundsException is expected when getQuestion(-1) is called.");
        } catch (IndexOutOfBoundsException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the getQuestion method throws an IndexOutOfBoundsException when given an
     * index that is too large.
     */
    public void testGetQuestionThrowsOnLargeIndex() {
        getSectionInstance().addQuestion(QUESTION1);

        try {
            getSectionInstance().getQuestion(1);
            fail("An IndexOutOfBoundsException is expected when getQuestion(1) is called.");
        } catch (IndexOutOfBoundsException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the getQuestion method works properly.
     */
    public void testGetQuestionWorks() {
        getSectionInstance().addQuestion(QUESTION1);

        assertEquals(
                "The questions do not match.",
                QUESTION1, getSectionInstance().getQuestion(0));
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // getNumberOfQuestions() Method Tests

    /**
     * Ensures that the getNumberOfQuestions method works properly.
     */
    public void testGetNumberOfQuestionsWorks() {
        getSectionInstance().addQuestion(QUESTION1);
        getSectionInstance().addQuestion(QUESTION2);
        getSectionInstance().addQuestion(QUESTION3);

        assertEquals(
                "The number of questions do not match.",
                3, getSectionInstance().getNumberOfQuestions());
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
            getSectionInstance().checkWeights(tolerance);
            fail("An IllegalArgumentException is expected when " + message);
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the checkWeights method returns true when the weights add up to 100.
     */
    public void testCheckWeightsReturnsTrue() {
        getSectionInstance().addQuestion(QUESTION1);
        getSectionInstance().addQuestion(QUESTION2);
        getSectionInstance().addQuestion(QUESTION3);

        assertTrue(
                "The weights add up to 100, so true should be returned.",
                getSectionInstance().checkWeights(1e-9f));
    }

    /**
     * Ensures that the checkWeights method returns true when the weights add up to 100 (plus or
     * minus a large tolerance).
     */
    public void testCheckWeightsReturnsTrueHighTolerance() {
        getSectionInstance().addQuestion(QUESTION1);
        getSectionInstance().addQuestion(QUESTION2);

        assertTrue(
                "The weights add up to 100 +/- 50, so true should be returned.",
                getSectionInstance().checkWeights(50f));
    }

    /**
     * Ensures that the checkWeights method returns false when the weights don't add up to 100.
     */
    public void testCheckWeightsReturnsFalse() {
        getSectionInstance().addQuestion(QUESTION1);
        getSectionInstance().addQuestion(QUESTION3);

        assertFalse(
                "The weights don't add up to 100, so false should be returned.",
                getSectionInstance().checkWeights(1e-9f));
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Serialization Tests

    /**
     * Ensures that serialization of the questions field works properly.
     *
     * @throws  ClassNotFoundException
     *          Couldn't deserialize properly.
     * @throws  IOException
     *          Couldn't serialize or deserialize properly.
     */
    public void testSerializeQuestionsWorks() throws ClassNotFoundException, IOException {
        getSectionInstance().addQuestion(QUESTION1);
        Section copy = (Section) serializeAndDeserialize();

        assertEquals(
                "The number of questions did not match.",
                1, copy.getNumberOfQuestions());

        assertEquals(
                "The question identifiers did not match.",
                QUESTION1.getId(), copy.getQuestion(0).getId());
    }
}
