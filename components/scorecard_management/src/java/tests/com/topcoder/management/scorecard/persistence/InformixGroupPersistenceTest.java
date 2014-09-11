/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.management.scorecard.persistence;

import java.util.ArrayList;

import com.topcoder.management.scorecard.data.Group;
import com.topcoder.management.scorecard.data.Scorecard;

/**
 * <p>
 * Unit tests for {@link com.topcoder.management.scorecard.persistence.InformixScorecardPersistence} class.
 * </p>
 *
 * @author kr00tki
 * @version 1.0
 */
public class InformixGroupPersistenceTest extends DbTestCase {
    /**
     * The scorecard persistence used to create test data.
     */
    private InformixScorecardPersistence scorecardPers = null;

    /**
     * InformixGroupPersistence instance to test on.
     */
    private InformixGroupPersistence persistence = null;

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
        persistence = new InformixGroupPersistence(getConnection());
        scorecard = createScorecard(0, 1);
        scorecardPers.createScorecard(scorecard, "tester");
    }

    /**
     * Tests the {@link InformixGroupPersistence#InformixGroupPersistence(java.sql.Connection)} constructor
     * failure. Checks if exception is thrown on null argument.
     */
    public void testInformixGroupPersistence_Null() {
        try {
            new InformixGroupPersistence(null);
            fail("Null connection , IAE expected");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixGroupPersistence#createGroup(Group, int, String, long)} method accuracy. Checks if
     * group is properly created in the database.
     *
     * @throws Exception to Junit.
     */
    public void testCreateGroupGroupIntStringLong() throws Exception {
        Group group = createGroup(0, 1);
        group.setName("special");
        long parentId = scorecard.getId();

        persistence.createGroup(group, 987, "TCSDEVELOPER", parentId);
        assertTrue("Id not assigned.", group.getId() > 0);

        // check persistence
        assertGroup(group, parentId, 987);
    }

    /**
     * Tests the {@link InformixGroupPersistence#createGroup(Group, int, String, long)} method failure. Checks if
     * exception is thrown when the group is null.
     *
     * @throws Exception to Junit.
     */
    public void testCreateGroup_NullGroup() throws Exception {
        try {
            persistence.createGroup(null, 1, "OP", 1);
            fail("Null group, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixGroupPersistence#createGroup(Group, int, String, long)} method failure. Checks if
     * exception is thrown when the operator is null.
     *
     * @throws Exception to Junit.
     */
    public void testCreateGroup_NullOperator() throws Exception {
        try {
            persistence.createGroup(createGroup(1, 1), 1, null, 1);
            fail("Null operator, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixGroupPersistence#createGroup(Group, int, String, long)} method failure. Checks if
     * exception is thrown when the operator is empty.
     *
     * @throws Exception to Junit.
     */
    public void testCreateGroup_EmptyOperator() throws Exception {
        try {
            persistence.createGroup(createGroup(1, 1), 1, " ", 1);
            fail("Empty operator, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixGroupPersistence#createGroups(Group[], String, long)} method accuracy. Checks if
     * the groups are properly created in the database.
     *
     * @throws Exception to Junit.
     */
    public void testCreateGroupGroupArrayStringLong() throws Exception {
        Group group = createGroup(0, 1);
        group.setName("special");
        long parentId = scorecard.getId();

        persistence.createGroups(new Group[] {group}, "TCSDEVELOPER", parentId);
        assertTrue("Id not assigned.", group.getId() > 0);

        // check persistence
        assertGroup(group, parentId, 0);
    }

    /**
     * Tests the {@link InformixGroupPersistence#updateGroup(Group, int, String, long, Scorecard, List, List)}
     * method accuracy. Checks if the group data are properly updated in the database.
     *
     * @throws Exception to Junit.
     */
    public void testUpdateGroup() throws Exception {
        Group group = createGroup(0, 1);
        group.setName("special");
        long parentId = scorecard.getId();

        persistence.createGroup(group, 2, "TCSDEVELOPER", parentId);
        assertTrue("Id not assigned.", group.getId() > 0);

        // change the question
        group.setName("new description");
        group.setWeight(88);

        // update
        persistence.updateGroup(group, 4, "update", parentId, scorecard, new ArrayList(), new ArrayList());

        // check persistence
        assertGroup(group, parentId, 4);
    }

    /**
     * Tests the {@link InformixGroupPersistence#updateGroup(Group, int, String, long, Scorecard, List, List)}
     * method failure. Checks if exception is thrown when group is null.
     *
     * @throws Exception to Junit.
     */
    public void testUpdateGroup_NullGroup() throws Exception {
        try {
            persistence.updateGroup(null, 1, "OP", 1, createScorecard(1, 1), new ArrayList(), new ArrayList());
            fail("Null group, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixGroupPersistence#updateGroup(Group, int, String, long, Scorecard, List, List)}
     * method failure. Checks if exception is thrown when operator is null.
     *
     * @throws Exception to Junit.
     */
    public void testUpdateGroup_NullOperator() throws Exception {
        try {
            persistence.updateGroup(createGroup(1, 1), 1, null, 1, createScorecard(1, 1), new ArrayList(),
                    new ArrayList());
            fail("Null operator, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixGroupPersistence#updateGroup(Group, int, String, long, Scorecard, List, List)}
     * method failure. Checks if exception is thrown when operator is empty.
     *
     * @throws Exception to Junit.
     */
    public void testUpdateGroup_EmptyOperator() throws Exception {
        try {
            persistence.updateGroup(createGroup(1, 1), 1, "", 1, createScorecard(1, 1), new ArrayList(),
                    new ArrayList());
            fail("Empty operator, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixGroupPersistence#updateGroup(Group, int, String, long, Scorecard, List, List)}
     * method failure. Checks if exception is thrown when sections list is null.
     *
     * @throws Exception to Junit.
     */
    public void testUpdateGroup_NullSectionsList() throws Exception {
        try {
            persistence.updateGroup(createGroup(1, 1), 1, "x", 1, createScorecard(1, 1), null, new ArrayList());
            fail("Null sections list, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixGroupPersistence#updateGroup(Group, int, String, long, Scorecard, List, List)}
     * method failure. Checks if exception is thrown when questions list is null.
     *
     * @throws Exception to Junit.
     */
    public void testUpdateGroup_NullQuestionsList() throws Exception {
        try {
            persistence.updateGroup(createGroup(1, 1), 1, "x", 1, createScorecard(1, 1), new ArrayList(), null);
            fail("Null questions list, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests {@link InformixGroupPersistence#deleteGroups(long[])} method accuracy. Checks if the groups are
     * removed from database.
     *
     * @throws Exception to Junit.
     */
    public void testDeleteGroups() throws Exception {
        Group group = scorecard.getGroup(0);
        // check if exists
        assertExists("Question should be in persistence.", group);

        // delete question
        persistence.deleteGroups(new long[] {group.getId()});

        // check if removed
        assertNotExists("Question should be removed.", group);
    }

    /**
     * Tests {@link InformixGroupPersistence#deleteGroups(long[])} method failure. Checks if exception is thrown
     * when the array is null.
     *
     * @throws Exception to Junit.
     */
    public void testDeleteGroups_NullArray() throws Exception {
        try {
            persistence.deleteGroups(null);
            fail("Null groups array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests {@link InformixGroupPersistence#deleteGroups(long[])} method failure. Checks if exception is thrown
     * when the array is empty.
     *
     * @throws Exception to Junit.
     */
    public void testDeleteGroups_EmptyArray() throws Exception {
        try {
            persistence.deleteGroups(new long[0]);
            fail("Empty groups array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests {@link InformixGroupPersistence#deleteGroups(long[])} method failure. Checks if exception is thrown
     * when the array is contains negative element.
     *
     * @throws Exception to Junit.
     */
    public void testDeleteGroups_ArrayWithNegative() throws Exception {
        try {
            persistence.deleteGroups(new long[] {-1});
            fail("groups array contains negative value, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixGroupPersistence#getGroup(long)} method accuracy. Checks if proper group is
     * retrieved from database.
     *
     * @throws Exception to Junit.
     */
    public void testGetGroup() throws Exception {
        Group old = scorecard.getGroup(0);
        Group newGroup = persistence.getGroup(old.getId());

        assertGroup(old, newGroup);
    }

    /**
     * Tests the {@link InformixGroupPersistence#getGroup(long)} method failure. Checks if the exceptions is thrown
     * when the id is negative.
     *
     * @throws Exception to Junit.
     */
    public void testGetGroup_Negative() throws Exception {
        try {
            persistence.getGroup(-1);
            fail("Negative id, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixGroupPersistence#getGroups(long)} method accuracy. Checks if proper groups are
     * retrieved for the given parent.
     *
     * @throws Exception to Junit.
     */
    public void testGetGroups() throws Exception {
        long parentId = scorecard.getId();
        Group old = scorecard.getGroup(0);
        Group[] newGroup = persistence.getGroups(parentId);

        assertEquals("Should have only one question.", 1, newGroup.length);
        assertGroup(old, newGroup[0]);
    }

}
