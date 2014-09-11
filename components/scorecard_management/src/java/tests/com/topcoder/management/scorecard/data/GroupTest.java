/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.scorecard.data;

import java.io.IOException;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Contains the unit tests for the {@link Group} class.
 *
 * @author      UFP2161
 * @copyright   Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * @version     1.0
 */
public class GroupTest extends WeightedScorecardStructureTest {

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
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Static Methods

    /**
     * Creates a test suite for the tests in this test case.
     *
     * @return  A TestSuite for this test case.
     */
    public static Test suite() {
        return new TestSuite(GroupTest.class);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Overridden Abstract Methods

    /**
     * Creates a new Group instance using the zero argument constructor.
     *
     * @return  A Group instance created using the zero argument constructor.
     */
    protected NamedScorecardStructure createInstance() {
        return new Group();
    }

    /**
     * Creates a new Group instance using the one argument constructor.
     *
     * @param   id
     *          The identifier to initialize with.
     *
     * @return  A Group instance created using the one argument constructor.
     */
    protected NamedScorecardStructure createInstance(long id) {
        return new Group(id);
    }

    /**
     * Creates a new Group instance using the two argument constructor.
     *
     * @param   id
     *          The identifier to initialize with.
     * @param   name
     *          The name to initialize with.
     *
     * @return  A Group instance created using the two argument constructor.
     */
    protected NamedScorecardStructure createInstance(long id, String name) {
        return new Group(id, name);
    }

    /**
     * Creates a new Group instance using the two argument constructor that takes a weight.
     *
     * @param   id
     *          The identifier to initialize with.
     * @param   weight
     *          The weight to initialize with.
     *
     * @return  A Group instance created using the two argument constructor that takes a weight.
     */
    protected WeightedScorecardStructure createInstance(long id, float weight) {
        return new Group(id, weight);
    }

    /**
     * Creates a new Group instance using the three argument constructor.
     *
     * @param   id
     *          The identifier to initialize with.
     * @param   name
     *          The name to initialize with.
     * @param   weight
     *          The weight to initialize with.
     *
     * @return  A Group instance created using the three argument constructor.
     */
    protected WeightedScorecardStructure createInstance(long id, String name, float weight) {
        return new Group(id, name, weight);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Field Checkers

    /**
     * Helper method to check the sections field.
     *
     * @param   group
     *          The Group instance to check (will be down casted).
     * @param   expected
     *          The expected value of the sections field.
     * @param   message
     *          The error message if the field values don't match.
     */
    private void checkSections(NamedScorecardStructure group, Section[] expected, String message) {
        TestUtil.assertArraysAreEqual(message, expected, ((Group) group).getAllSections());
    }

    /**
     * Helper method to check the default value of sections field, which is an empty list.
     *
     * @param   group
     *          The Group instance to check (will be down casted).
     */
    private void checkDefaultSections(NamedScorecardStructure group) {
        checkSections(group, new Section[0], "The sections should be an empty list.");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor Tests

    /**
     * Ensures that the zero argument constructor properly sets the sections field, by checking that the
     * getAllSections method returns an empty list.
     */
    public void test0CtorSetsQuestions() {
        checkDefaultSections(createInstance());
    }

    /**
     * Ensures that the one argument constructor properly sets the sections field, by checking that the
     * getAllSections method returns an empty list.
     */
    public void test1CtorSetsQuestions() {
        checkDefaultSections(createInstance1());
    }

    /**
     * Ensures that the two argument constructor properly sets the sections field, by checking that the
     * getAllSections method returns an empty list.
     */
    public void test2CtorSetsQuestions() {
        checkDefaultSections(createInstance2());
    }

    /**
     * Ensures that the two argument constructor that takes a weight properly sets the sections field,
     * by checking that the getAllSections method returns an empty list.
     */
    public void test2WeightCtorSetsQuestions() {
        checkDefaultSections(createInstance2Weight());
    }

    /**
     * Ensures that the three argument constructor properly sets the sections field, by checking that the
     * getAllSections method returns an empty list.
     */
    public void test3CtorSetsQuestions() {
        checkDefaultSections(createInstance3());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Helper Methods

    /**
     * Down casts the NamedScorecardStructure back to a Group.
     *
     * @return  The Group instance created in the setUp method.
     */
    private Group getGroupInstance() {
        return (Group) getInstance();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // addSection(Section) Method Tests

    /**
     * Ensures that the addSection method throws an IllegalArgumentException when given a
     * null argument.
     */
    public void testAddSectionThrowsOnNull() {
        try {
            getGroupInstance().addSection(null);
            fail("An IllegalArgumentException is expected when addSection(null) is called.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the addSection method works properly when adding both duplicate and non-duplicate
     * sections.
     */
    public void testAddSectionWorks() {
        getGroupInstance().addSection(SECTION1);
        getGroupInstance().addSection(SECTION2);
        getGroupInstance().addSection(SECTION1);

        checkSections(getInstance(), new Section[] {SECTION1, SECTION2}, "The sections are not correct.");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // addSection(Section[]) Method Tests

    /**
     * Ensures that the addSections method throws an IllegalArgumentException when given a
     * null argument.
     */
    public void testAddSectionsThrowsOnIsNull() {
        try {
            getGroupInstance().addSections(null);
            fail("An IllegalArgumentException is expected when addSections(null) is called.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the addSections method throws an IllegalArgumentException when given a
     * argument with a null.
     */
    public void testAddSectionsThrowsOnHasNull() {
        try {
            getGroupInstance().addSections(new Section[] {null});
            fail("An IllegalArgumentException is expected when addSections(new Section[]{null}) is called.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the addSections method works properly when adding both duplicate and non-duplicate
     * sections.
     */
    public void testAddSectionsWorks() {
        getGroupInstance().addSections(
                new Section[] {SECTION1, SECTION2, SECTION1, SECTION3, SECTION2});

        checkSections(
                getInstance(), new Section[] {SECTION1, SECTION2, SECTION3},
                "The sections are not correct.");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // insertSection(Section, int) Method Tests

    /**
     * Ensures that the insertSection method throws an IllegalArgumentException when given a
     * null question.
     */
    public void testInsertSectionThrowsOnNull() {
        try {
            getGroupInstance().insertSection(null, 0);
            fail("An IllegalArgumentException is expected when insertSection(null, 0) is called.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the insertSection method throws an IndexOutOfBoundsException when given an
     * index that is too small.
     */
    public void testInsertSectionThrowsOnSmallIndex() {
        try {
            getGroupInstance().insertSection(SECTION1, -1);
            fail("An IndexOutOfBoundsException is expected when insertSection(SECTION1, -1) is called.");
        } catch (IndexOutOfBoundsException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the insertSection method throws an IndexOutOfBoundsException when given an
     * index that is too large.
     */
    public void testInsertSectionThrowsOnLargeIndex() {
        try {
            getGroupInstance().insertSection(SECTION1, 1);
            fail("An IndexOutOfBoundsException is expected when insertSection(SECTION1, 1) is called.");
        } catch (IndexOutOfBoundsException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the insertSections method works properly when inserting both duplicate and
     * non-duplicate sections.
     */
    public void testInsertSectionWorks() {
        getGroupInstance().insertSection(SECTION1, 0);
        getGroupInstance().insertSection(SECTION2, 0);
        getGroupInstance().insertSection(SECTION1, 0);

        checkSections(getInstance(), new Section[] {SECTION2, SECTION1}, "The sections are not correct.");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // removeSection(Section) Method Tests

    /**
     * Ensures that the removeSection(Section) method throws an IllegalArgumentException when
     * given a null argument.
     */
    public void testRemoveSectionThrowsOnNull() {
        try {
            getGroupInstance().removeSection(null);
            fail("An IllegalArgumentException is expected when removeSection(null) is called.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the removeSection(Section) method works properly.
     */
    public void testRemoveSectionWorks() {
        getGroupInstance().addSection(SECTION1);
        getGroupInstance().addSection(SECTION2);

        getGroupInstance().removeSection(SECTION1);
        getGroupInstance().removeSection(SECTION3);

        checkSections(getInstance(), new Section[] {SECTION2}, "The sections are not correct.");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // removeSection(int) Method Tests

    /**
     * Ensures that the removeSection(int) method throws an IndexOutOfBoundsException when given an
     * index that is too small.
     */
    public void testRemoveSectionIntThrowsOnSmallIndex() {
        try {
            getGroupInstance().removeSection(-1);
            fail("An IndexOutOfBoundsException is expected when removeSection(-1) is called.");
        } catch (IndexOutOfBoundsException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the removeSection(int) method throws an IndexOutOfBoundsException when given an
     * index that is too large.
     */
    public void testRemoveSectionIntThrowsOnLargeIndex() {
        getGroupInstance().addSection(SECTION1);

        try {
            getGroupInstance().removeSection(1);
            fail("An IndexOutOfBoundsException is expected when removeSection(1) is called.");
        } catch (IndexOutOfBoundsException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the removeSection(int) method works properly.
     */
    public void testRemoveSectionIntWorks() {
        getGroupInstance().addSection(SECTION1);
        getGroupInstance().removeSection(0);

        checkSections(getInstance(), new Section[0], "The sections list should be emptied.");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // removeSections(Section[]) Method Tests

    /**
     * Ensures that the removeSections method throws an IllegalArgumentException when given a
     * null argument.
     */
    public void testRemoveSectionsThrowsOnIsNull() {
        try {
            getGroupInstance().removeSections(null);
            fail("An IllegalArgumentException is expected when removeSections(null) is called.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the removeSections method throws an IllegalArgumentException when given a
     * argument with a null.
     */
    public void testRemoveSectionsThrowsOnHasNull() {
        try {
            getGroupInstance().removeSections(new Section[] {null});
            fail("An IllegalArgumentException is expected when removeSections(new Section[]{null}) is called.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the removeSections method works properly.
     */
    public void testRemoveSectionsWorks() {
        getGroupInstance().addSections(new Section[] {SECTION1, SECTION2});
        getGroupInstance().removeSections(new Section[] {SECTION3, SECTION1});

        checkSections(getInstance(), new Section[] {SECTION2}, "The sections are not correct.");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // clearSections Method Tests

    /**
     * Ensures that the clearSections method works properly.
     */
    public void testClearSectionsWorks() {
        getGroupInstance().addSection(SECTION1);
        getGroupInstance().addSection(SECTION2);
        getGroupInstance().addSection(SECTION3);
        getGroupInstance().clearSections();

        checkSections(getInstance(), new Section[0], "The sections list should be emptied.");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // getSection Method Tests

    /**
     * Ensures that the getSection method throws an IndexOutOfBoundsException when given an
     * index that is too small.
     */
    public void testGetSectionThrowsOnSmallIndex() {
        try {
            getGroupInstance().getSection(-1);
            fail("An IndexOutOfBoundsException is expected when getSection(-1) is called.");
        } catch (IndexOutOfBoundsException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the getSection method throws an IndexOutOfBoundsException when given an
     * index that is too large.
     */
    public void testGetSectionThrowsOnLargeIndex() {
        getGroupInstance().addSection(SECTION1);

        try {
            getGroupInstance().getSection(1);
            fail("An IndexOutOfBoundsException is expected when getSection(1) is called.");
        } catch (IndexOutOfBoundsException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the getSection method works properly.
     */
    public void testGetSectionWorks() {
        getGroupInstance().addSection(SECTION1);

        assertEquals(
                "The sections do not match.",
                SECTION1, getGroupInstance().getSection(0));
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // getNumberOfSections() Method Tests

    /**
     * Ensures that the getNumberOfSections method works properly.
     */
    public void testGetNumberOfSectionsWorks() {
        getGroupInstance().addSection(SECTION1);
        getGroupInstance().addSection(SECTION2);
        getGroupInstance().addSection(SECTION3);

        assertEquals(
                "The number of sections do not match.",
                3, getGroupInstance().getNumberOfSections());
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
            getGroupInstance().checkWeights(tolerance);
            fail("An IllegalArgumentException is expected when " + message);
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the checkWeights method returns true when the weights add up to 100, and each
     * section's weights add up to 100.
     */
    public void testCheckWeightsReturnsTrue() {
        getGroupInstance().addSection(SECTION1);
        getGroupInstance().addSection(SECTION3);

        assertTrue(
                "All weights add up to 100, so true should be returned.",
                getGroupInstance().checkWeights(1e-9f));
    }

    /**
     * Ensures that the checkWeights method returns true when the weights add up to 100 (plus or
     * minus a large tolerance).
     */
    public void testCheckWeightsReturnsTrueHighTolerance() {
        getGroupInstance().addSection(SECTION2);

        assertTrue(
                "All weights add up to 100 +/- 60, so true should be returned.",
                getGroupInstance().checkWeights(60f));
    }

    /**
     * Ensures that the checkWeights method returns false when the weights of the sections list
     * don't add up to 100.
     */
    public void testCheckWeightsReturnsFalseForSectionsList() {
        getGroupInstance().addSection(SECTION1);

        assertFalse(
                "The weights don't add up to 100, so false should be returned.",
                getGroupInstance().checkWeights(1e-9f));
    }

    /**
     * Ensures that the checkWeights method returns false when the weights of a section don't
     * add up to 100.
     */
    public void testCheckWeightsReturnsFalseForBadSection() {
        getGroupInstance().addSection(SECTION1);
        getGroupInstance().addSection(SECTION2);

        assertFalse(
                "One of the sections don't weigh up to 100, so false should be returned.",
                getGroupInstance().checkWeights(1e-9f));
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Serialization Tests

    /**
     * Ensures that serialization of the sections field works properly.
     *
     * @throws  ClassNotFoundException
     *          Couldn't deserialize properly.
     * @throws  IOException
     *          Couldn't serialize or deserialize properly.
     */
    public void testSerializeSectionsWorks() throws ClassNotFoundException, IOException {
        getGroupInstance().addSection(SECTION1);
        Group copy = (Group) serializeAndDeserialize();

        assertEquals(
                "The number of sections did not match.",
                1, copy.getNumberOfSections());

        assertEquals(
                "The section identifiers did not match.",
                SECTION1.getId(), copy.getSection(0).getId());
    }
}
