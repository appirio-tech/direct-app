/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.management.scorecard.data.stresstests;

import com.topcoder.management.scorecard.data.Group;
import com.topcoder.management.scorecard.data.Scorecard;


/**
 * Stress test cases for Scorecard class.
 *
 * @author Wendell
 * @version 1.0
 */
public class ScorecardStressTest extends AbstractTest {
    /** The id of the scorecard. */
    private final long ID = 1;

    /** The Scorecard instance to test against. */
    private Scorecard scorecard;

    /**
     * Creates the Scorecard instance for testing.
     */
    protected void setUp() {
        scorecard = new Scorecard(ID);
    }

    /**
     * Tests Scorecard() constructor.
     */
    public void testScorecard1() {
        begin();

        for (int i = 0; i < TIMES; i++) {
            Scorecard scorecard = new Scorecard();
            assertNotNull("fail to instantiate Scorecard", scorecard);
        }

        end("Scorecard()");
    }

    /**
     * Tests Scorecard(long) constructor.
     */
    public void testScorecard2() {
        begin();

        for (int i = 0; i < TIMES; i++) {
            Scorecard scorecard = new Scorecard(ID);
            assertNotNull("fail to instantiate Scorecard", scorecard);
            assertEquals("id incorrect", ID, scorecard.getId());
        }

        end("Scorecard(long)");
    }

    /**
     * Tests addGroup(Group) method.
     */
    public void testAddGroup() {
        begin();

        for (int i = 0; i < TIMES; i++) {
            this.scorecard.addGroup(new Group());
        }

        assertEquals("group count incorrect", TIMES, this.scorecard.getNumberOfGroups());
        end("addGroup(Group)");
    }

    /**
     * Tests addGroups(Group[]) method.
     */
    public void testAddGroups() {
        begin();

        for (int i = 0; i < TIMES; i++) {
            scorecard.addGroups(new Group[] {new Group()});
        }

        assertEquals("group count incorrect", TIMES, scorecard.getNumberOfGroups());
        end("addGroups(Group[])");
    }

    /**
     * Tests removeGroup(Group) method.
     */
    public void testRemoveGroup1() {
        Group[] groups = new Group[TIMES];

        for (int i = 0; i < TIMES; i++) {
            groups[i] = new Group();
        }

        begin();

        for (int i = 0; i < TIMES; i++) {
            scorecard.removeGroup(groups[i]);
        }

        assertEquals("group count incorrect", 0, scorecard.getNumberOfGroups());
        end("removeGroup(Group)");
    }

    /**
     * Tests removeGroup(int) method.
     */
    public void testRemoveGroup2() {
        Group[] groups = new Group[TIMES];

        for (int i = 0; i < TIMES; i++) {
            groups[i] = new Group();
            scorecard.addGroup(groups[i]);
        }

        begin();

        for (int i = 0; i < TIMES; i++) {
            scorecard.removeGroup(0);
        }

        assertEquals("group count incorrect", 0, scorecard.getNumberOfGroups());
        end("removeGroup(int)");
    }

    /**
     * Tests removeGroups(Group[]) method.
     */
    public void testRemoveGroups() {
        Group[] groups = new Group[TIMES];

        for (int i = 0; i < TIMES; i++) {
            groups[i] = new Group();
        }

        begin();

        for (int i = 0; i < TIMES; i++) {
            scorecard.removeGroups(new Group[] {groups[i]});
        }

        assertEquals("group count incorrect", 0, scorecard.getNumberOfGroups());
        end("removeGroups(Group[])");
    }

    /**
     * Tests insertGroup(Group, int) method.
     */
    public void testInsertGroup() {
        begin();

        for (int i = 0; i < TIMES; i++) {
            scorecard.insertGroup(new Group(), 0);
        }

        assertEquals("group count incorrect", TIMES, scorecard.getNumberOfGroups());
        end("insertGroup(Group, int)");
    }

    /**
     * Tests checkWeights(float) method.
     */
    public void testCheckWeights() {
        begin();

        for (int i = 0; i < TIMES; i++) {
            scorecard.checkWeights(DELTA);
        }

        end("checkWeights(float)");
    }
}
