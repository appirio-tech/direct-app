/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.management.scorecard.data.stresstests;

import com.topcoder.management.scorecard.data.Group;
import com.topcoder.management.scorecard.data.Section;


/**
 * Stress test cases for Group class.
 *
 * @author Wendell
 * @version 1.0
 */
public class GroupStressTest extends AbstractTest {
    /** The id of the group. */
    private final long ID = 1;

    /** The name of the group. */
    private final String NAME = "group";

    /** The weight of the group. */
    private final float WEIGHT = 20f;

    /** The Group instance to test against. */
    private Group group;

    /**
     * Creates the group instance to be tested.
     */
    protected void setUp() {
        Section section = new Section();

        group = new Group();
        group.addSection(section);
    }

    /**
     * Tests the Group() constructor.
     */
    public void testGroup1() {
        begin();

        for (int i = 0; i < TIMES; i++) {
            Group group = new Group();
            assertNotNull("failed to instantiate Group", group);
        }

        end("Group()");
    }

    /**
     * Tests the Group(long) constructor.
     */
    public void testGroup2() {
        begin();

        for (int i = 0; i < TIMES; i++) {
            Group group = new Group(ID);
            assertNotNull("failed to instantiate Group", group);
            assertEquals("id incorrect", ID, group.getId());
        }

        end("Group(long)");
    }

    /**
     * Tests the Group(long, String) constructor.
     */
    public void testGroup3() {
        begin();

        for (int i = 0; i < TIMES; i++) {
            Group group = new Group(ID, NAME);
            assertNotNull("failed to instantiate Group", group);
            assertEquals("id incorrect", ID, group.getId());
            assertEquals("name incorrect", NAME, group.getName());
        }

        end("Group(long, String)");
    }

    /**
     * Tests Group(long, float) constructor.
     */
    public void testGroup4() {
        begin();

        for (int i = 0; i < TIMES; i++) {
            Group group = new Group(ID, WEIGHT);
            assertNotNull("failed to instantiate Group", group);
            assertEquals("id incorrect", ID, group.getId());
            assertEquals("weight incorrect", WEIGHT, group.getWeight(), DELTA);
        }

        end("Group(long, float)");
    }

    /**
     * Tests Group(long, String, float) constructor.
     */
    public void testGroup5() {
        begin();

        for (int i = 0; i < TIMES; i++) {
            Group group = new Group(ID, NAME, WEIGHT);
            assertEquals("id incorrect", ID, group.getId());
            assertEquals("name incorrect", NAME, group.getName());
            assertEquals("weight incorrect", WEIGHT, group.getWeight(), DELTA);
        }

        end("Group(long, String, float)");
    }

    /**
     * Tests addSection(Section) method.
     */
    public void testAddSection() {
        group.clearSections();
        begin();

        for (int i = 0; i < TIMES; i++) {
            group.addSection(new Section());
        }

        assertEquals("section count incorrect", TIMES, group.getAllSections().length);

        end("addSection(Section)");
    }

    /**
     * Tests addSections(Section[]) method.
     */
    public void testAddSections() {
        group.clearSections();
        begin();

        for (int i = 0; i < TIMES; i++) {
            group.addSections(new Section[] {new Section()});
        }

        assertEquals("section count incorrect", TIMES, group.getAllSections().length);

        end("addSections(Section[])");
    }

    /**
     * Tests removeSection(Section) method.
     */
    public void testRemoveSection1() {
        group.clearSections();

        Section[] sections = new Section[TIMES];

        for (int i = 0; i < TIMES; i++) {
            sections[i] = new Section();
            group.addSection(sections[i]);
        }

        begin();

        for (int i = 0; i < TIMES; i++) {
            group.removeSection(sections[i]);
        }

        assertEquals("section count incorrect", 0, group.getAllSections().length);
        end("removeSection(Section)");
    }

    /**
     * Tests removeSection(int) method.
     */
    public void testRemoveSection2() {
        group.clearSections();

        Section[] sections = new Section[TIMES];

        for (int i = 0; i < TIMES; i++) {
            sections[i] = new Section();
            group.addSection(sections[i]);
        }

        begin();

        for (int i = 0; i < TIMES; i++) {
            group.removeSection(0);
        }

        assertEquals("section count incorrect", 0, group.getAllSections().length);
        end("removeSection(int)");
    }

    /**
     * Tests removeSections(Section[]) method.
     */
    public void testRemoveSections() {
        group.clearSections();

        Section[] sections = new Section[TIMES];

        for (int i = 0; i < TIMES; i++) {
            sections[i] = new Section();
        }

        begin();

        for (int i = 0; i < TIMES; i++) {
            group.addSections(sections);
            group.removeSections(sections);
            assertEquals("section count incorrect", 0, group.getAllSections().length);
        }

        end("removeSections(Section[])");
    }

    /**
     * Tests insertSection(Section, int) method.
     */
    public void testInsertSection() {
        group.clearSections();

        Section[] sections = new Section[TIMES];

        for (int i = 0; i < TIMES; i++) {
            sections[i] = new Section();
        }

        begin();

        for (int i = 0; i < TIMES; i++) {
            group.insertSection(sections[i], 0);
        }

        assertEquals("section count incorrect", TIMES, group.getAllSections().length);
        end("insertSection(Section, int)");
    }

    /**
     * Tests checkWeights(float) method.
     */
    public void testCheckWeights() {
        begin();

        for (int i = 0; i < TIMES; i++) {
            group.checkWeights(DELTA);
        }

        end("checkWeights(float)");
    }
}
