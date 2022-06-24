/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.scorecard.data;

import java.io.IOException;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Contains the unit tests for the {@link Question} class.
 *
 * @author      UFP2161
 * @copyright   Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * @version     1.0
 */
public class QuestionTest extends WeightedScorecardStructureTest {

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Static Fields

    /**
     * The question type to be used in unit testing.
     */
    private static final QuestionType QUESTION_TYPE = new QuestionType(1, "Question type.");

    /**
     * The description to be used in unit testing.
     */
    private static final String DESCRIPTION = "Some description.";

    /**
     * The guideline to be used in unit testing.
     */
    private static final String GUIDELINE = "Some guideline.";

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Static Methods

    /**
     * Creates a test suite for the tests in this test case.
     *
     * @return  A TestSuite for this test case.
     */
    public static Test suite() {
        return new TestSuite(QuestionTest.class);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Overridden Abstract Methods

    /**
     * Creates a new Question instance using the zero argument constructor.
     *
     * @return  A Question instance created using the zero argument constructor.
     */
    protected NamedScorecardStructure createInstance() {
        return new Question();
    }

    /**
     * Creates a new Question instance using the one argument constructor.
     *
     * @param   id
     *          The identifier to initialize with.
     *
     * @return  A Question instance created using the one argument constructor.
     */
    protected NamedScorecardStructure createInstance(long id) {
        return new Question(id);
    }

    /**
     * Creates a new Question instance using the two argument constructor.
     *
     * @param   id
     *          The identifier to initialize with.
     * @param   name
     *          The name to initialize with.
     *
     * @return  A Question instance created using the two argument constructor.
     */
    protected NamedScorecardStructure createInstance(long id, String name) {
        return new Question(id, name);
    }

    /**
     * Creates a new Question instance using the two argument constructor that takes a weight.
     *
     * @param   id
     *          The identifier to initialize with.
     * @param   weight
     *          The weight to initialize with.
     *
     * @return  A Question instance created using the two argument constructor that takes a weight.
     */
    protected WeightedScorecardStructure createInstance(long id, float weight) {
        return new Question(id, weight);
    }

    /**
     * Creates a new Question instance using the three argument constructor.
     *
     * @param   id
     *          The identifier to initialize with.
     * @param   name
     *          The name to initialize with.
     * @param   weight
     *          The weight to initialize with.
     *
     * @return  A Question instance created using the three argument constructor.
     */
    protected WeightedScorecardStructure createInstance(long id, String name, float weight) {
        return new Question(id, name, weight);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Field Checkers

    /**
     * Helper method to check the questionType field.
     *
     * @param   question
     *          The Question instance to check (will be down casted).
     * @param   expected
     *          The expected value of the questionType field.
     * @param   message
     *          The error message if the field values don't match.
     */
    private void checkQuestionType(NamedScorecardStructure question, QuestionType expected, String message) {
        assertEquals(message, expected, ((Question) question).getQuestionType());
    }

    /**
     * Helper method to check the description field.
     *
     * @param   question
     *          The Question instance to check (will be down casted).
     * @param   expected
     *          The expected value of the description field.
     * @param   message
     *          The error message if the field values don't match.
     */
    private void checkDescription(NamedScorecardStructure question, String expected, String message) {
        assertEquals(message, expected, ((Question) question).getDescription());
    }

    /**
     * Helper method to check the guideline field.
     *
     * @param   question
     *          The Question instance to check (will be down casted).
     * @param   expected
     *          The expected value of the guideline field.
     * @param   message
     *          The error message if the field values don't match.
     */
    private void checkGuideline(NamedScorecardStructure question, String expected, String message) {
        assertEquals(message, expected, ((Question) question).getGuideline());
    }

    /**
     * Helper method to check the uploadDocument field.
     *
     * @param   question
     *          The Question instance to check (will be down casted).
     * @param   expected
     *          The expected value of the uploadDocument field.
     * @param   message
     *          The error message if the field values don't match.
     */
    private void checkUploadDocument(NamedScorecardStructure question, boolean expected, String message) {
        assertEquals(message, expected, ((Question) question).isUploadDocument());
    }

    /**
     * Helper method to check the uploadRequired field.
     *
     * @param   question
     *          The Question instance to check (will be down casted).
     * @param   expected
     *          The expected value of the uploadRequired field.
     * @param   message
     *          The error message if the field values don't match.
     */
    private void checkUploadRequired(NamedScorecardStructure question, boolean expected, String message) {
        assertEquals(message, expected, ((Question) question).isUploadRequired());
    }

    /**
     * Helper method to check the default value of questionType field, which is null.
     *
     * @param   question
     *          The Question instance to check (will be down casted).
     */
    private void checkDefaultQuestionType(NamedScorecardStructure question) {
        checkQuestionType(question, null, "The questionType should be null.");
    }

    /**
     * Helper method to check the default value of description field, which is null.
     *
     * @param   question
     *          The Question instance to check (will be down casted).
     */
    private void checkDefaultDescription(NamedScorecardStructure question) {
        checkDescription(question, null, "The description should be null.");
    }

    /**
     * Helper method to check the default value of guideline field, which is null.
     *
     * @param   question
     *          The Question instance to check (will be down casted).
     */
    private void checkDefaultGuideline(NamedScorecardStructure question) {
        checkGuideline(question, null, "The guideline should be null.");
    }

    /**
     * Helper method to check the default value of uploadDocument field, which is false.
     *
     * @param   question
     *          The Question instance to check (will be down casted).
     */
    private void checkDefaultUploadDocument(NamedScorecardStructure question) {
        checkUploadDocument(question, false, "The uploadDocument should be false.");
    }

    /**
     * Helper method to check the default value of uploadRequired field, which is false.
     *
     * @param   question
     *          The Question instance to check (will be down casted).
     */
    private void checkDefaultUploadRequired(NamedScorecardStructure question) {
        checkUploadRequired(question, false, "The uploadRequired should be false.");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor() Tests

    /**
     * Ensures that the zero argument constructor properly sets the questionType, by checking that the
     * getQuestionType method returns a null.
     */
    public void test0CtorSetsQuestionType() {
        checkDefaultQuestionType(createInstance());
    }

    /**
     * Ensures that the zero argument constructor properly sets the description, by checking that the
     * getDescription method returns a null.
     */
    public void test0CtorSetsDescription() {
        checkDefaultDescription(createInstance());
    }

    /**
     * Ensures that the zero argument constructor properly sets the guideline, by checking that the
     * getGuideline method returns a null.
     */
    public void test0CtorSetsGuideline() {
        checkDefaultGuideline(createInstance());
    }

    /**
     * Ensures that the zero argument constructor properly sets the uploadDocument, by checking that the
     * isUploadDocument method returns a false.
     */
    public void test0CtorSetsUploadDocument() {
        checkDefaultUploadDocument(createInstance());
    }

    /**
     * Ensures that the zero argument constructor properly sets the uploadRequired, by checking that the
     * isUploadRequired method returns a false.
     */
    public void test0CtorSetsUploadRequired() {
        checkDefaultUploadRequired(createInstance());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor(long) Tests

    /**
     * Ensures that the one argument constructor properly sets the questionType, by checking that the
     * getQuestionType method returns a null.
     */
    public void test1CtorSetsQuestionType() {
        checkDefaultQuestionType(createInstance1());
    }

    /**
     * Ensures that the one argument constructor properly sets the description, by checking that the
     * getDescription method returns a null.
     */
    public void test1CtorSetsDescription() {
        checkDefaultDescription(createInstance1());
    }

    /**
     * Ensures that the one argument constructor properly sets the guideline, by checking that the
     * getGuideline method returns a null.
     */
    public void test1CtorSetsGuideline() {
        checkDefaultGuideline(createInstance1());
    }

    /**
     * Ensures that the one argument constructor properly sets the uploadDocument, by checking that the
     * isUploadDocument method returns a false.
     */
    public void test1CtorSetsUploadDocument() {
        checkDefaultUploadDocument(createInstance1());
    }

    /**
     * Ensures that the one argument constructor properly sets the uploadRequired, by checking that the
     * isUploadRequired method returns a false.
     */
    public void test1CtorSetsUploadRequired() {
        checkDefaultUploadRequired(createInstance1());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor(long, String) Tests

    /**
     * Ensures that the two argument constructor properly sets the questionType, by checking that the
     * getQuestionType method returns a null.
     */
    public void test2CtorSetsQuestionType() {
        checkDefaultQuestionType(createInstance2());
    }

    /**
     * Ensures that the two argument constructor properly sets the description, by checking that the
     * getDescription method returns a null.
     */
    public void test2CtorSetsDescription() {
        checkDefaultDescription(createInstance2());
    }

    /**
     * Ensures that the two argument constructor properly sets the guideline, by checking that the
     * getGuideline method returns a null.
     */
    public void test2CtorSetsGuideline() {
        checkDefaultGuideline(createInstance2());
    }

    /**
     * Ensures that the two argument constructor properly sets the uploadDocument, by checking that the
     * isUploadDocument method returns a false.
     */
    public void test2CtorSetsUploadDocument() {
        checkDefaultUploadDocument(createInstance2());
    }

    /**
     * Ensures that the two argument constructor properly sets the uploadRequired, by checking that the
     * isUploadRequired method returns a false.
     */
    public void test2CtorSetsUploadRequired() {
        checkDefaultUploadRequired(createInstance2());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor(long, float) Tests

    /**
     * Ensures that the two argument constructor that takes a weight properly sets the questionType, by
     * checking that the getQuestionType method returns a null.
     */
    public void test2WeightCtorSetsQuestionType() {
        checkDefaultQuestionType(createInstance2Weight());
    }

    /**
     * Ensures that the two argument constructor that takes a weight properly sets the description, by
     * checking that the getDescription method returns a null.
     */
    public void test2WeightCtorSetsDescription() {
        checkDefaultDescription(createInstance2Weight());
    }

    /**
     * Ensures that the two argument constructor that takes a weight properly sets the guideline, by
     * checking that the getGuideline method returns a null.
     */
    public void test2WeightCtorSetsGuideline() {
        checkDefaultGuideline(createInstance2Weight());
    }

    /**
     * Ensures that the two argument constructor that takes a weight properly sets the uploadDocument, by
     * checking that the isUploadDocument method returns a false.
     */
    public void test2WeightCtorSetsUploadDocument() {
        checkDefaultUploadDocument(createInstance2Weight());
    }

    /**
     * Ensures that the two argument constructor that takes a weight properly sets the uploadRequired, by
     * checking that the isUploadRequired method returns a false.
     */
    public void test2WeightCtorSetsUploadRequired() {
        checkDefaultUploadRequired(createInstance2Weight());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor(long, String, float) Tests

    /**
     * Ensures that the three argument constructor properly sets the questionType, by checking that the
     * getQuestionType method returns a null.
     */
    public void test3CtorSetsQuestionType() {
        checkDefaultQuestionType(createInstance3());
    }

    /**
     * Ensures that the three argument constructor properly sets the description, by checking that the
     * getDescription method returns a null.
     */
    public void test3CtorSetsDescription() {
        checkDefaultDescription(createInstance3());
    }

    /**
     * Ensures that the three argument constructor properly sets the guideline, by checking that the
     * getGuideline method returns a null.
     */
    public void test3CtorSetsGuideline() {
        checkDefaultGuideline(createInstance3());
    }

    /**
     * Ensures that the three argument constructor properly sets the uploadDocument, by checking that the
     * isUploadDocument method returns a false.
     */
    public void test3CtorSetsUploadDocument() {
        checkDefaultUploadDocument(createInstance3());
    }

    /**
     * Ensures that the three argument constructor properly sets the uploadRequired, by checking that the
     * isUploadRequired method returns a false.
     */
    public void test3CtorSetsUploadRequired() {
        checkDefaultUploadRequired(createInstance3());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Helper Methods

    /**
     * Down casts the NamedScorecardStructure back to a Question.
     *
     * @return  The Question instance created in the setUp method.
     */
    private Question getQuestionInstance() {
        return (Question) getInstance();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // questionType Mutator Tests

    /**
     * Ensures that the setQuestionType method sets the question type properly, by checking that the
     * getQuestionType method returns the original question type.
     */
    public void testSetQuestionTypeWorks() {
        getQuestionInstance().setQuestionType(QUESTION_TYPE);
        checkQuestionType(getInstance(), QUESTION_TYPE, "The question types do not match.");
    }

    /**
     * Ensures that the resetQuestionType method resets the question type properly, by checking that the
     * getQuestionType method returns a null.
     */
    public void testResetQuestionTypeWorks() {
        getQuestionInstance().setQuestionType(QUESTION_TYPE);
        getQuestionInstance().resetQuestionType();
        checkDefaultQuestionType(getInstance());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // description Mutator Tests

    /**
     * Ensures that the setDescription method sets the description properly, by checking that the
     * getDescription method returns the original description.
     */
    public void testSetDescriptionWorks() {
        getQuestionInstance().setDescription(DESCRIPTION);
        checkDescription(getInstance(), DESCRIPTION, "The descriptions do not match.");
    }

    /**
     * Ensures that the resetDescription method resets the description properly, by checking that the
     * getDescription method returns a null.
     */
    public void testResetDescriptionWorks() {
        getQuestionInstance().setDescription(DESCRIPTION);
        getQuestionInstance().resetDescription();
        checkDefaultDescription(getInstance());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // guideline Mutator Tests

    /**
     * Ensures that the setGuideline method sets the guideline properly, by checking that the
     * getGuideline method returns the original guideline.
     */
    public void testSetGuidelineWorks() {
        getQuestionInstance().setGuideline(GUIDELINE);
        checkGuideline(getInstance(), GUIDELINE, "The guidelines do not match.");
    }

    /**
     * Ensures that the resetGuideline method resets the guideline properly, by checking that the
     * getGuideline method returns a null.
     */
    public void testResetGuidelineWorks() {
        getQuestionInstance().setGuideline(GUIDELINE);
        getQuestionInstance().resetGuideline();
        checkDefaultGuideline(getInstance());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // uploadDocument Mutator Tests

    /**
     * Ensures that the setUploadDocument method throws an IllegalStateException when given a
     * false when the uploadRequired field is true.
     */
    public void testSetUploadDocumentFalseWhenRequiredTrueThrows() {
        getQuestionInstance().setUploadRequired(true);

        try {
            getQuestionInstance().setUploadDocument(false);
            fail("The upload document should not be able to be set to false.");
        } catch (IllegalStateException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the setUploadDocument method sets the uploadDocument field properly when given
     * a true when the uploadRequired field is true.
     */
    public void testSetUploadDocumentTrueWhenRequiredTrueWorks() {
        getQuestionInstance().setUploadRequired(true);
        getQuestionInstance().setUploadDocument(true);
        checkUploadDocument(getInstance(), true, "The upload document switches do not match.");
    }

    /**
     * Ensures that the setUploadDocument method sets the uploadDocument field properly when given
     * a true when the uploadRequired field is false.
     */
    public void testSetUploadDocumentTrueWhenRequiredFalseWorks() {
        getQuestionInstance().setUploadDocument(true);
        checkUploadDocument(getInstance(), true, "The upload document switches do not match.");
    }

    /**
     * Ensures that the setUploadDocument method sets the uploadDocument field properly when given
     * a false when the uploadRequired field is true.
     */
    public void testSetUploadDocumentFalseWhenRequiredFalseWorks() {
        getQuestionInstance().setUploadDocument(false);
        checkUploadDocument(getInstance(), false, "The upload document switches do not match.");
    }

    /**
     * Ensures that the resetUploadDocument method throws an IllegalStateException when the
     * uploadRequired field is true.
     */
    public void testResetUploadDocumentWhenRequiredTrueThrows() {
        getQuestionInstance().setUploadRequired(true);

        try {
            getQuestionInstance().resetUploadDocument();
            fail("The upload document should not be able to be reset to false.");
        } catch (IllegalStateException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the resetUploadDocument method resets the uploadDocument field properly when
     * the uploadRequired field is false.
     */
    public void testResetUploadDocumentWhenRequiredFalseWorks() {
        getQuestionInstance().setUploadDocument(true);
        getQuestionInstance().resetUploadDocument();
        checkDefaultUploadDocument(getInstance());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // uploadRequired Mutator Tests

    /**
     * Ensures that the setUploadRequired method sets the uploadRequired field properly when given
     * a false when the uploadDocument field is false.
     */
    public void testSetUploadRequiredFalseWhenDocumentFalseWorks() {
        getQuestionInstance().setUploadRequired(false);
        checkUploadRequired(getInstance(), false, "The upload required switches do not match.");
    }

    /**
     * Ensures that the setUploadRequired method sets the uploadRequired field properly when given
     * a true when the uploadDocument field is false.
     */
    public void testSetUploadRequiredTrueWhenDocumentFalseWorks() {
        getQuestionInstance().setUploadRequired(true);
        checkUploadRequired(getInstance(), true, "The upload required switches do not match.");
        checkUploadDocument(getInstance(), true, "The upload document switches do not match.");
    }

    /**
     * Ensures that the setUploadRequired method sets the uploadRequired field properly when given
     * a false when the uploadDocument field is true.
     */
    public void testSetUploadRequiredFalseWhenDocumentTrueWorks() {
        getQuestionInstance().setUploadDocument(true);
        getQuestionInstance().setUploadRequired(false);
        checkUploadRequired(getInstance(), false, "The upload required switches do not match.");
    }

    /**
     * Ensures that the setUploadRequired method sets the uploadRequired field properly when given
     * a true when the uploadDocument field is true.
     */
    public void testSetUploadRequiredTrueWhenDocumentTrueWorks() {
        getQuestionInstance().setUploadDocument(true);
        getQuestionInstance().setUploadRequired(true);
        checkUploadRequired(getInstance(), true, "The upload required switches do not match.");
        checkUploadDocument(getInstance(), true, "The upload document switches do not match.");
    }

    /**
     * Ensures that the resetUploadRequired method resets the uploadRequired field properly.
     */
    public void testResetUploadRequiredWorks() {
        getQuestionInstance().setUploadRequired(true);
        getQuestionInstance().resetUploadRequired();
        checkDefaultUploadRequired(getInstance());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Serialization Tests

    /**
     * Ensures that serialization of the questionType field works properly.
     *
     * @throws  ClassNotFoundException
     *          Couldn't deserialize properly.
     * @throws  IOException
     *          Couldn't serialize or deserialize properly.
     */
    public void testSerializeQuestionTypeWorks() throws ClassNotFoundException, IOException {
        getQuestionInstance().setQuestionType(QUESTION_TYPE);
        Question copy = (Question) serializeAndDeserialize();

        assertEquals(
                "The question type identifies do not match.",
                QUESTION_TYPE.getId(), copy.getQuestionType().getId());

        assertEquals(
                "The question type names do not match.",
                QUESTION_TYPE.getName(), copy.getQuestionType().getName());
    }

    /**
     * Ensures that serialization of the description field works properly.
     *
     * @throws  ClassNotFoundException
     *          Couldn't deserialize properly.
     * @throws  IOException
     *          Couldn't serialize or deserialize properly.
     */
    public void testSerializeDescriptionWorks() throws ClassNotFoundException, IOException {
        getQuestionInstance().setDescription(DESCRIPTION);
        Question copy = (Question) serializeAndDeserialize();
        checkDescription(copy, DESCRIPTION, "The descriptions did not match.");
    }

    /**
     * Ensures that serialization of the guideline field works properly.
     *
     * @throws  ClassNotFoundException
     *          Couldn't deserialize properly.
     * @throws  IOException
     *          Couldn't serialize or deserialize properly.
     */
    public void testSerializeGuidelineWorks() throws ClassNotFoundException, IOException {
        getQuestionInstance().setGuideline(GUIDELINE);
        Question copy = (Question) serializeAndDeserialize();
        checkGuideline(copy, GUIDELINE, "The guidelines did not match.");
    }

    /**
     * Ensures that serialization of the uploadDocument field works properly.
     *
     * @throws  ClassNotFoundException
     *          Couldn't deserialize properly.
     * @throws  IOException
     *          Couldn't serialize or deserialize properly.
     */
    public void testSerializeUploadDocumentWorks() throws ClassNotFoundException, IOException {
        getQuestionInstance().setUploadDocument(true);
        Question copy = (Question) serializeAndDeserialize();
        checkUploadDocument(copy, true, "The upload document switches did not match.");
    }

    /**
     * Ensures that serialization of the uploadRequired field works properly.
     *
     * @throws  ClassNotFoundException
     *          Couldn't deserialize properly.
     * @throws  IOException
     *          Couldn't serialize or deserialize properly.
     */
    public void testSerializeUploadRequiredWorks() throws ClassNotFoundException, IOException {
        getQuestionInstance().setUploadRequired(true);
        Question copy = (Question) serializeAndDeserialize();
        checkUploadRequired(copy, true, "The upload required switches did not match.");
    }
}
