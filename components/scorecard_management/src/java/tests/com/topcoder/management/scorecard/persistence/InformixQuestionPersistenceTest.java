/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.management.scorecard.persistence;

import com.topcoder.management.scorecard.data.Question;
import com.topcoder.management.scorecard.data.Scorecard;

/**
 * <p>
 * Unit tests for {@link com.topcoder.management.scorecard.persistence.InformixQuestionPersistence} class.
 * </p>
 *
 * @author kr00tki
 * @version 1.0
 */
public class InformixQuestionPersistenceTest extends DbTestCase {
    /**
     * The scorecard persistence used to create test data.
     */
    private InformixScorecardPersistence scorecardPers = null;

    /**
     * InformixQuestionPersistence instance to test on.
     */
    private InformixQuestionPersistence persistence = null;

    /**
     * The scorecard used in tests.
     */
    private Scorecard scorecard = null;

    /**
     * Sets up the test environment. It creates required objects and references.
     *
     * @throws Exception to Junit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        scorecardPers = new InformixScorecardPersistence(NAMESPACE);
        persistence = new InformixQuestionPersistence(getConnection());
        scorecard = createScorecard(0, 1);
        scorecardPers.createScorecard(scorecard, "tester");
    }

    /**
     * Tests the {@link InformixQuestionPersistence#InformixQuestionPersistence(java.sql.Connection)} constructor
     * failure. Checks if exception is thrown on null argument.
     */
    public void testInformixQuestionPersistence_Null() {
        try {
            new InformixQuestionPersistence(null);
            fail("Null connection , IAE expected");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixQuestionPersistence#createQuestion(Question, int, String, long)} method accuracy.
     * Checks if question is created in the database.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateQuestion() throws Exception {
        Question question = createQuestion(0);
        question.setDescription("special");
        long parentId = scorecard.getGroup(0).getSection(0).getId();

        persistence.createQuestion(question, 987, "TCSDEVELOPER", parentId);
        assertTrue("Id not assigned.", question.getId() > 0);

        // check persistence
        assertQuestion(question, parentId, 987);
    }

    /**
     * Tests the {@link InformixQuestionPersistence#createQuestion(Question, int, String, long)} method accuracy.
     * Checks if question is created in the database.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateQuestion_NullGuideline() throws Exception {
        Question question = createQuestion(0);
        question.setDescription("special");
        question.setGuideline(null);
        long parentId = scorecard.getGroup(0).getSection(0).getId();

        persistence.createQuestion(question, 987, "TCSDEVELOPER", parentId);
        assertTrue("Id not assigned.", question.getId() > 0);

        // check persistence
        assertQuestion(question, parentId, 987);
    }

    /**
     * Tests the {@link InformixQuestionPersistence#createQuestion(Question, int, String, long)} method failure.
     * Checks if exception is thrown when the question is null.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateQuestion_NullGroup() throws Exception {
        try {
            persistence.createQuestion(null, 1, "OP", 1);
            fail("Null question, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixQuestionPersistence#createQuestion(Question, int, String, long)} method failure.
     * Checks if exception is thrown when the operator is null.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateQuestion_NullOperator() throws Exception {
        try {
            persistence.createQuestion(createQuestion(1), 1, null, 1);
            fail("Null operator, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixQuestionPersistence#createQuestion(Question, int, String, long)} method failure.
     * Checks if exception is thrown when the operator is empty.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateQuestion_EmptyOperator() throws Exception {
        try {
            persistence.createQuestion(createQuestion(1), 1, " ", 1);
            fail("Empty operator, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests {@link InformixQuestionPersistence#createQuestions(Question[], String, long)} method accuracy. Checks
     * if all questions are created in the database.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateQuestions() throws Exception {
        Question question = createQuestion(0);
        question.setDescription("special");
        long parentId = scorecard.getGroup(0).getSection(0).getId();

        persistence.createQuestions(new Question[] {question}, "TCSDEVELOPER", parentId);
        assertTrue("Id not assigned.", question.getId() > 0);

        // check persistence
        assertQuestion(question, parentId, 0);
    }

    /**
     * Test the {@link InformixQuestionPersistence#updateQuestion(Question, int, String, long)} method accuracy.
     * Checks if question is properly updated in the database.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateQuestion() throws Exception {
        Question question = createQuestion(0);
        question.setDescription("special");
        long parentId = scorecard.getGroup(0).getSection(0).getId();

        persistence.createQuestions(new Question[] {question}, "TCSDEVELOPER", parentId);
        assertTrue("Id not assigned.", question.getId() > 0);

        // change the question
        question.setDescription("new description");
        question.setUploadDocument(true);
        question.setUploadRequired(true);
        question.setGuideline("new guidline");

        // update
        persistence.updateQuestion(question, 4, "update", parentId);

        // check persistence
        assertQuestion(question, parentId, 4);
    }

    /**
     * Test the {@link InformixQuestionPersistence#updateQuestion(Question, int, String, long)} method failure.
     * Checks if the exception is thrown when the question is null.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateQuestion_NullQuestion() throws Exception {
        try {
            persistence.updateQuestion(null, 1, "op", 1);
            fail("Null group, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Test the {@link InformixQuestionPersistence#updateQuestion(Question, int, String, long)} method failure.
     * Checks if the exception is thrown when the operator is null.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateQuestion_NullOperator() throws Exception {
        try {
            persistence.updateQuestion(createQuestion(1), 1, null, 1);
            fail("Null operator, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Test the {@link InformixQuestionPersistence#updateQuestion(Question, int, String, long)} method failure.
     * Checks if the exception is thrown when the operator is empty.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateQuestion_EmptyOperator() throws Exception {
        try {
            persistence.updateQuestion(createQuestion(1), 1, " ", 1);
            fail("Empty operator, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixQuestionPersistence#deleteQuestions(long[])} method accuracy. Checks if questions
     * are removed from database.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteQuestions() throws Exception {
        Question question = scorecard.getGroup(0).getSection(0).getQuestion(0);
        // check if exists
        assertExists("Question should be in persistence.", question);

        // delete question
        persistence.deleteQuestions(new long[] {question.getId()});

        // check if removed
        assertNotExists("Question should be removed.", question);
    }

    /**
     * Tests the {@link InformixQuestionPersistence#deleteQuestions(long[])} method failure. Checks if exception is
     * thrown when the array is null.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteQuestions_NullArray() throws Exception {
        try {
            persistence.deleteQuestions(null);
            fail("Null questions array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixQuestionPersistence#deleteQuestions(long[])} method failure. Checks if exception is
     * thrown when the array is empty.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteQuestions_EmptyArray() throws Exception {
        try {
            persistence.deleteQuestions(new long[0]);
            fail("Empty questions array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixQuestionPersistence#deleteQuestions(long[])} method failure. Checks if exception is
     * thrown when the array contains negative id.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteQuestions_ArrayWithNegative() throws Exception {
        try {
            persistence.deleteQuestions(new long[] {-1});
            fail("questions array contains negative value, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixQuestionPersistence#getQuestion(long)} method accuracy. Checks if proper question
     * is retrieved from the database.
     *
     * @throws Exception to JUnit.
     */
    public void testGetQuestion() throws Exception {
        Question old = scorecard.getGroup(0).getSection(0).getQuestion(0);
        Question newQuestion = persistence.getQuestion(old.getId());

        assertQuestion(old, newQuestion);
    }

    /**
     * Tests the {@link InformixQuestionPersistence#getQuestion(long)} method accuracy. Checks if null is returned
     * when there is no question with id.
     *
     * @throws Exception to JUnit.
     */
    public void testGetQuestion_NoQuestion() throws Exception {
        assertNull("Shoudl return null.", persistence.getQuestion(999999998));
    }

    /**
     * Tests the {@link InformixQuestionPersistence#getQuestion(long)} method failure. Checks if exception is
     * thrown when the id is negative.
     *
     * @throws Exception to JUnit.
     */
    public void testGetQuestion_Negative() throws Exception {
        try {
            persistence.getQuestion(-1);
            fail("Negative id, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixQuestionPersistence#getQuestions(long)} method accuracy. Checks if correct
     * questions are returned for parent id.
     *
     * @throws Exception to JUnit.
     */
    public void testGetQuestions() throws Exception {
        long parentId = scorecard.getGroup(0).getSection(0).getId();
        Question old = scorecard.getGroup(0).getSection(0).getQuestion(0);
        Question[] newQuestion = persistence.getQuestions(parentId);

        assertEquals("Should have only one question.", 1, newQuestion.length);
        assertQuestion(old, newQuestion[0]);
    }

}
