/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.management.scorecard.persistence;

import com.topcoder.management.scorecard.PersistenceException;
import com.topcoder.management.scorecard.data.Group;
import com.topcoder.management.scorecard.data.Question;
import com.topcoder.management.scorecard.data.QuestionType;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.ScorecardStatus;
import com.topcoder.management.scorecard.data.ScorecardType;
import com.topcoder.management.scorecard.data.Section;

/**
 * The unit tests for the {@link com.topcoder.management.scorecard.persistence.InformixScorecardPersistence} class.
 *
 * @author kr00tki
 * @version 1.0
 */
public class InformixScorecardPersistenceTest extends DbTestCase {
    /**
     * The InformixScorecardPersistence intance to test on.
     */
    private InformixScorecardPersistence persistence = null;

    /**
     * Sets up the test environment. It creates required objects and references.
     *
     * @throws Exception to Junit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        persistence = new InformixScorecardPersistence(NAMESPACE);
    }

    /**
     * Tests the {@link InformixScorecardPersistence#InformixScorecardPersistence(String)} constructor failure.
     * Checks if exception is thrown when the namespace is null.
     *
     * @throws Exception to JUnit.
     */
    public void testInformixScorecardPersistence_Null() throws Exception {
        try {
            new InformixScorecardPersistence(null);
            fail("Null namespace, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixScorecardPersistence#InformixScorecardPersistence(String)} constructor failure.
     * Checks if exception is thrown when the namespace is empty.
     *
     * @throws Exception to JUnit.
     */
    public void testInformixScorecardPersistence_Empty() throws Exception {
        try {
            new InformixScorecardPersistence(" ");
            fail("Empty namespace, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixScorecardPersistence#createScorecard(Scorecard, String)} method accuracy.
     * Checks if the scorecard is properly created in the database.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateScorecard() throws Exception {
        Scorecard sc = createScorecard(0, 2);
        persistence.createScorecard(sc, "TCSDEVELOPER");
        assertScorecard(sc);
    }

    /**
     * Tests the {@link InformixScorecardPersistence#createScorecard(Scorecard, String)} method failure.
     * Checks if exception is thrown when the scorecard is null.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateScorecard_NullGroup() throws Exception {
        try {
            persistence.createScorecard(null, "OP");
            fail("Null scorecard, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixScorecardPersistence#createScorecard(Scorecard, String)} method failure.
     * Checks if exception is thrown when the operator is null.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateScorecard_NullOperator() throws Exception {
        try {
            persistence.createScorecard(createScorecard(1, 1), null);
            fail("Null operator, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixScorecardPersistence#createScorecard(Scorecard, String)} method failure.
     * Checks if exception is thrown when the operator is empty.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateScorecard_EmptyOperator() throws Exception {
        try {
            persistence.createScorecard(createScorecard(1, 1), " ");
            fail("Empty operator, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixScorecardPersistence#updateScorecard(Scorecard, String)} method accuracy.
     * Checks if the scorecard is properly updated in the database.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateScorecard() throws Exception {
        Scorecard sc = createScorecard(0, 2);
        persistence.createScorecard(sc, "TCSDEVELOPER");
        assertScorecard(sc);

        sc.setCategory(2);
        sc.setMaxScore((float) 999.23);

        persistence.updateScorecard(sc, "TCSDEVELOPER_UPDATE");
        assertEquals("The operator is not set", "TCSDEVELOPER_UPDATE", sc.getModificationUser());
        assertScorecard(sc);
    }

    /**
     * Tests the {@link InformixScorecardPersistence#updateScorecard(Scorecard, String)} method failure.
     * Checks if exception is thrown when the scorecard is null.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateScorecard_NullGroup() throws Exception {
        try {
            persistence.updateScorecard(null, "OP");
            fail("Null scorecard, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixScorecardPersistence#updateScorecard(Scorecard, String)} method failure.
     * Checks if exception is thrown when the operator is null.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateScorecard_NullOperator() throws Exception {
        try {
            persistence.updateScorecard(createScorecard(1, 1), null);
            fail("Null operator, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixScorecardPersistence#updateScorecard(Scorecard, String)} method failure.
     * Checks if exception is thrown when the operator is empty.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateScorecard_EmptyOperator() throws Exception {
        try {
            persistence.updateScorecard(createScorecard(1, 1), "");
            fail("Empty operator, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixScorecardPersistence#updateScorecard(Scorecard, String)} method failure.
     * The PersistenceExceptions hsould be thrown because the scorecard is in use.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateScorecard_InUse() throws Exception {
        Scorecard sc = createScorecard(0, 2);
        persistence.createScorecard(sc, "TCSDEVELOPER");
        assertScorecard(sc);
        setInUse(sc);

        try {
            persistence.updateScorecard(sc, "x");
            fail("Scorecard in use, exception expected.");
        } catch (PersistenceException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixScorecardPersistence#updateScorecard(Scorecard, String)} method accuracy.
     * Checks if the whole scorecard tree is updated. The new group should be added and question deleted.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateScorecard_DeepUpdate() throws Exception {
        Scorecard sc = createScorecard(0, 2);
        persistence.createScorecard(sc, "TCSDEVELOPER");
        assertScorecard(sc);

        sc.setCategory(2);
        sc.setMaxScore((float) 999.23);
        Group newGroup = createGroup(0, 3);
        sc.insertGroup(newGroup, 0);
        sc.getGroup(2).getSection(1).getQuestion(1).setDescription("new description");
        sc.getGroup(2).getSection(0).removeQuestion(1);

        persistence.updateScorecard(sc, "TCSDEVELOPER_UPDATE");
        assertEquals("The operator is not set", "TCSDEVELOPER_UPDATE", sc.getModificationUser());
        assertScorecard(sc);
    }

    /**
     * Tests the {@link InformixScorecardPersistence#updateScorecard(Scorecard, String)} method accuracy.
     * Checks if the update operation deletes the groups.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateScorecard_DeleteGroups() throws Exception {
        Scorecard sc = createScorecard(0, 2);
        persistence.createScorecard(sc, "TCSDEVELOPER");
        assertScorecard(sc);

        sc.setScorecardStatus(new ScorecardStatus(3, "deleted"));
        Group[] old = sc.getAllGroups();
        sc.clearGroups();

        persistence.updateScorecard(sc, "TCSDEVELOPER_UPDATE");
        assertEquals("The operator is not set", "TCSDEVELOPER_UPDATE", sc.getModificationUser());
        assertScorecard(sc);
        for (int i = 0; i < old.length; i++) {
            assertNotExists("Groups should be removed.", old[i]);
        }
    }

    /**
     * Tests the {@link InformixScorecardPersistence#updateScorecard(Scorecard, String)} method accuracy.
     * Checks if update operation deletes the sections.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateScorecard_DeleteSections() throws Exception {
        Scorecard sc = createScorecard(0, 2);
        persistence.createScorecard(sc, "TCSDEVELOPER");
        assertScorecard(sc);

        sc.setScorecardStatus(new ScorecardStatus(3, "deleted"));
        Section[] old = sc.getGroup(1).getAllSections();
        sc.getGroup(1).clearSections();

        persistence.updateScorecard(sc, "TCSDEVELOPER_UPDATE");
        assertEquals("The operator is not set", "TCSDEVELOPER_UPDATE", sc.getModificationUser());
        assertScorecard(sc);
        for (int i = 0; i < old.length; i++) {
            assertNotExists("Section should be removed.", old[i]);
        }
    }

    /**
     * Tests the {@link InformixScorecardPersistence#updateScorecard(Scorecard, String)} method accuracy.
     * Checks if the update operation removes the questions.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateScorecard_DeleteQuestion() throws Exception {
        Scorecard sc = createScorecard(0, 2);
        persistence.createScorecard(sc, "TCSDEVELOPER");
        assertScorecard(sc);

        sc.setScorecardStatus(new ScorecardStatus(3, "deleted"));
        Question[] old = sc.getGroup(1).getSection(0).getAllQuestions();
        sc.getGroup(1).getSection(0).clearQuestions();

        persistence.updateScorecard(sc, "TCSDEVELOPER_UPDATE");
        assertEquals("The operator is not set", "TCSDEVELOPER_UPDATE", sc.getModificationUser());
        assertScorecard(sc);
        for (int i = 0; i < old.length; i++) {
            assertNotExists("Section should be removed.", old[i]);
        }
    }

    /**
     * Tests the {@link InformixScorecardPersistence#getScorecard(long, boolean)} method accuracy.
     * Checks if the scorecard with all child elements is returned.
     *
     * @throws Exception to JUnit.
     */
    public void testGetScorecard() throws Exception {
        Scorecard old = createScorecard(45, 3);
        persistence.createScorecard(old, "TCSDEVELOPER");

        Scorecard newCard = persistence.getScorecard(old.getId(), true);
        assertScorecard(old, newCard, true);
    }

    /**
     * Tests the {@link InformixScorecardPersistence#getScorecard(long, boolean)} method accuracy.
     * Checks if the returned scorecard has the "isUse" flag set.
     *
     * @throws Exception to JUnit.
     */
    public void testGetScorecard_InUse() throws Exception {
        Scorecard old = createScorecard(44, 3);
        persistence.createScorecard(old, "TCSDEVELOPER");
        assertFalse(old.isInUse());
        setInUse(old);

        Scorecard newCard = persistence.getScorecard(old.getId(), true);
        assertScorecard(old, newCard, true);
        assertTrue(newCard.isInUse());
    }

    /**
     * Tests the {@link InformixScorecardPersistence#getScorecard(long, boolean)} method failure.
     * Checks if exception is thrown when the id is negative.
     *
     * @throws Exception to JUnit.
     */
    public void testGetScorecard_Negative() throws Exception {
        try {
            persistence.getScorecard(-1, true);
            fail("Negative id, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixScorecardPersistence#getAllScorecardTypes()} method accuracy.
     * Checks if all types preloaded to database are returned.
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllScorecardTypes() throws Exception {
        ScorecardType[] types = persistence.getAllScorecardTypes();
        assertEquals("Incorrect number of scorecards.", 3, types.length);
        for (int i = 0; i < types.length; i++) {
            assertEquals("Incorrect type id.", i + 1, types[i].getId());
            assertEquals("Incorrect type name.", "scorecard_type_" + (i + 1), types[i].getName());
        }
    }

    /**
     * Tests the {@link InformixScorecardPersistence#getAllQuestionTypes()} method accuracy.
     * Checks if all types preloaded to database are returned.
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllQuestionTypes() throws Exception {
        QuestionType[] types = persistence.getAllQuestionTypes();
        assertEquals("Incorrect number of question types.", 3, types.length);
        for (int i = 0; i < types.length; i++) {
            assertEquals("Incorrect type id.", i + 1, types[i].getId());
            assertEquals("Incorrect type name.", "question_type_" + (i + 1), types[i].getName());
        }
    }

    /**
     * Tests the {@link InformixScorecardPersistence#getAllScorecardStatuses()} method accuracy.
     * Checks if all statuses preloaded to database are returned.
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllScorecardStatuses() throws Exception {
        ScorecardStatus[] types = persistence.getAllScorecardStatuses();
        assertEquals("Incorrect number of question types.", 3, types.length);
        for (int i = 0; i < types.length; i++) {
            assertEquals("Incorrect type id.", i + 1, types[i].getId());
            assertEquals("Incorrect type name.", "scorecard_status_" + (i + 1), types[i].getName());
        }
    }

    /**
     * Tests the {@link InformixScorecardPersistence#getScorecards(long[], boolean)} method accuracy.
     * Checks if all the requested scorecards are returned.
     *
     * @throws Exception to JUnit.
     */
    public void testGetScorecards() throws Exception {
        Scorecard[] old = new Scorecard[5];
        long[] ids = new long[old.length];
        for (int i = 0; i < old.length; i++) {
            old[i] = createScorecard(1, 5);
            persistence.createScorecard(old[i], "TCSDEVELOPER");
            ids[i] = old[i].getId();
        }

        Scorecard[] newCards = persistence.getScorecards(ids, true);
        assertEquals("Incorrect array length.", old.length, newCards.length);
        for (int i = 0; i < old.length; i++) {
            assertScorecard(old[i], newCards[i], true);
        }
    }

    /**
     * Tests the {@link InformixScorecardPersistence#getScorecards(long[], boolean)} method failure.
     * Checks if the exception is throw when the array is null.
     *
     * @throws Exception to JUnit.
     */
    public void testGetScorecards_Null() throws Exception {
        try {
            persistence.getScorecards((long[]) null, true);
            fail("Null ids, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixScorecardPersistence#getScorecards(long[], boolean)} method failure.
     * Checks if the exception is throw when the array is empty.
     *
     * @throws Exception to JUnit.
     */
    public void testGetScorecards_EmptyArray() throws Exception {
        try {
            persistence.getScorecards((long[]) null, true);
            fail("Null ids, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixScorecardPersistence#getScorecards(long[], boolean)} method failure.
     * Checks if the exception is throw when the array contains negative value.
     *
     * @throws Exception to JUnit.
     */
    public void testGetScorecards_ArrayWithNegative() throws Exception {
        try {
            persistence.getScorecards(new long[] {-1}, true);
            fail("Ids with negative value, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

}
