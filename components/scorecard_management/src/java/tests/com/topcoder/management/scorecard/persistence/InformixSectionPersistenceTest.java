/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.management.scorecard.persistence;

import java.util.ArrayList;

import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.Section;

/**
 * Unit tests for the {@link com.topcoder.management.scorecard.persistence.InformixScorecardPersistence} class.
 *
 * @author kr00tki
 * @version 1.0
 */
public class InformixSectionPersistenceTest extends DbTestCase {
    /**
     * The scorecard persistence used to create test data.
     */
    private InformixScorecardPersistence scorecardPers = null;

    /**
     * InformixSectionPersistence instance to test on.
     */
    private InformixSectionPersistence persistence = null;

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
        persistence = new InformixSectionPersistence(getConnection());
        scorecard = createScorecard(0, 1);
        scorecardPers.createScorecard(scorecard, "tester");
    }

    /**
     * Tests the {@link InformixSectionPersistence#InformixSectionPersistence(java.sql.Connection)} constructor
     * failure. Checks if exception is thrown on null argument.
     */
    public void testInformixSectionPersistence_Null() {
        try {
            new InformixSectionPersistence(null);
            fail("Null connection , IAE expected");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixSectionPersistence#createSection(Section, int, String, long)} method accuracy.
     * Checks if the section is properly created in the database.
     *
     * @throws Exception to Junit.
     */
    public void testCreateSection() throws Exception {
        Section section = createSection(0, 1);
        section.setName("special");
        long parentId = scorecard.getGroup(0).getId();

        persistence.createSection(section, 987, "TCSDEVELOPER", parentId);
        assertTrue("Id not assigned.", section.getId() > 0);

        // check persistence
        assertSection(section, parentId, 987);
    }

    /**
     * Tests the {@link InformixSectionPersistence#createSection(Section, int, String, long)} method accuracy.
     * Checks if exception is throw when the section is null.
     *
     * @throws Exception to Junit.
     */
    public void testCreateSection_NullGroup() throws Exception {
        try {
            persistence.createSection(null, 1, "OP", 1);
            fail("Null section, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixSectionPersistence#createSection(Section, int, String, long)} method accuracy.
     * Checks if exception is throw when the operator is null.
     *
     * @throws Exception to Junit.
     */
    public void testCreateSection_NullOperator() throws Exception {
        try {
            persistence.createSection(createSection(1, 1), 1, null, 1);
            fail("Null operator, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixSectionPersistence#createSection(Section, int, String, long)} method accuracy.
     * Checks if exception is throw when the operator is empty.
     *
     * @throws Exception to Junit.
     */
    public void testCreateSection_EmptyOperator() throws Exception {
        try {
            persistence.createSection(createSection(1, 1), 1, " ", 1);
            fail("Empty operator, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixSectionPersistence#createSections(Section[], String, long)} method accuracy. Checks
     * if the section is created in the database.
     *
     * @throws Exception to Junit.
     */
    public void testCreateSections() throws Exception {
        Section section = createSection(0, 1);
        section.setName("special");
        long parentId = scorecard.getGroup(0).getId();

        persistence.createSections(new Section[] {section}, "TCSDEVELOPER", parentId);
        assertTrue("Id not assigned.", section.getId() > 0);

        // check persistence
        assertSection(section, parentId, 0);
    }

    /**
     * Tests the {@link InformixSectionPersistence#updateSection(Section, int, String, long, Scorecard, List)}
     * method accuracy. Checks if the sections are updated in the persistence.
     *
     * @throws Exception to Junit.
     */
    public void testUpdateSection() throws Exception {
        Section section = createSection(0, 1);
        section.setName("special");
        long parentId = scorecard.getGroup(0).getId();

        persistence.createSection(section, 2, "TCSDEVELOPER", parentId);
        assertTrue("Id not assigned.", section.getId() > 0);

        // change the question
        section.setName("new description");
        section.setWeight(88);

        // update
        persistence.updateSection(section, 4, "update", parentId, scorecard, new ArrayList());

        // check persistence
        assertSection(section, parentId, 4);
    }

    /**
     * Tests the {@link InformixSectionPersistence#updateSection(Section, int, String, long, Scorecard, List)}
     * method accuracy. Checks if the exception is thrown when the section is null.
     *
     * @throws Exception to Junit.
     */
    public void testUpdateSection_NullSection() throws Exception {
        try {
            persistence.updateSection(null, 1, "OP", 1, createScorecard(1, 1), new ArrayList());
            fail("Null section, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixSectionPersistence#updateSection(Section, int, String, long, Scorecard, List)}
     * method accuracy. Checks if the exception is thrown when the operator is null.
     *
     * @throws Exception to Junit.
     */
    public void testUpdateSection_NullOperator() throws Exception {
        try {
            persistence.updateSection(createSection(1, 1), 1, null, 1, createScorecard(1, 1), new ArrayList());
            fail("Null operator, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixSectionPersistence#updateSection(Section, int, String, long, Scorecard, List)}
     * method accuracy. Checks if the exception is thrown when the operator is empty.
     *
     * @throws Exception to Junit.
     */
    public void testUpdateSection_EmptyOperator() throws Exception {
        try {
            persistence.updateSection(createSection(1, 1), 1, "", 1, createScorecard(1, 1), new ArrayList());
            fail("Empty operator, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixSectionPersistence#updateSection(Section, int, String, long, Scorecard, List)}
     * method accuracy. Checks if the exception is thrown when the questions list is null.
     *
     * @throws Exception to Junit.
     */
    public void testUpdateGroup_NullQuestionsList() throws Exception {
        try {
            persistence.updateSection(createSection(1, 1), 1, "x", 1, createScorecard(1, 1), null);
            fail("Null questions list, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixSectionPersistence#deleteSections(long[])} method accuracy. Checks if the section
     * is removed from the persistence.
     *
     * @throws Exception to Junit.
     */
    public void testDeleteSections() throws Exception {
        Section section = createSection(0, 1);
        section.setName("special");
        long parentId = scorecard.getGroup(0).getId();

        persistence.createSection(section, 2, "TCSDEVELOPER", parentId);
        assertTrue("Id not assigned.", section.getId() > 0);

        // update
        persistence.deleteSections(new long[] {section.getId()});

        // check persistence
        assertNotExists("Should be removed.", section);
    }

    /**
     * Tests the {@link InformixSectionPersistence#deleteSections(long[])} method failure. Checks if the exception
     * is thrown when the array is null.
     *
     * @throws Exception to Junit.
     */
    public void testDeleteSections_NullArray() throws Exception {
        try {
            persistence.deleteSections(null);
            fail("Null sections array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixSectionPersistence#deleteSections(long[])} method failure. Checks if the exception
     * is thrown when the array is empty.
     *
     * @throws Exception to Junit.
     */
    public void testDeleteSections_EmptyArray() throws Exception {
        try {
            persistence.deleteSections(new long[0]);
            fail("Empty sections array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixSectionPersistence#deleteSections(long[])} method failure. Checks if the exception
     * is thrown when the array contains negative value.
     *
     * @throws Exception to Junit.
     */
    public void testDeleteSections_ArrayWithNegative() throws Exception {
        try {
            persistence.deleteSections(new long[] {-1});
            fail("sections array contains negative value, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixSectionPersistence#getSection(long)} method accuracy. Checks if proper section is
     * returned.
     *
     * @throws Exception to Junit.
     */
    public void testGetSection() throws Exception {
        Section old = scorecard.getGroup(0).getSection(0);
        Section newGroup = persistence.getSection(old.getId());

        assertSection(old, newGroup);
    }

    /**
     * Tests the {@link InformixSectionPersistence#getSection(long)} method failure. Checks if exception is thrown
     * when the id is negative.
     *
     * @throws Exception to Junit.
     */
    public void testGetSections_Negative() throws Exception {
        try {
            persistence.getSection(-1);
            fail("Negative id, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixSectionPersistence#getSections(long)} method accuracy. Checks if proper sections
     * are returned for the parent id.
     *
     * @throws Exception to Junit.
     */
    public void testGetSections() throws Exception {
        long parentId = scorecard.getGroup(0).getId();
        Section old = scorecard.getGroup(0).getSection(0);
        Section[] newSection = persistence.getSections(parentId);

        assertEquals("Should have only one question.", 1, newSection.length);
        assertSection(old, newSection[0]);
    }

}
