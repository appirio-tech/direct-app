/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.data.accuracytests;

import com.topcoder.management.scorecard.data.Group;
import com.topcoder.management.scorecard.data.Question;
import com.topcoder.management.scorecard.data.Section;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.List;


/**
 * Tests for Group class.
 *
 * @author crackme
 * @version 1.0
 */
public class TestGroup extends TestCase {
    /** Group instance used to test. */
    private Group group = null;

    /** Section instance used to test. */
    private Section section1 = null;

    /** Section instance used to test. */
    private Section section2 = null;
    private Question question = null;

    /** Section[] instance used to test. */
    private Section[] sections = null;

    /**
     * Setup the test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        section1 = new Section(777771, "design document section", 20.123f);
        section2 = new Section(777772, "design method section", 30.234f);

        question = new Question(88888, "document", 100.0f);
        section1.addQuestion(question);
        section2.addQuestion(question);
        group = new Group(111110, "group", 50.357f);

        sections = new Section[] { section1, section2 };
    }

    /**
     * Setup the test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Tests Group() method with accuracy state.
     */
    public void testGroupAccuracy1() {
        Group g = new Group();
        assertNull("constructor is wrong.", g.getName());
        assertEquals("constructor is wrong.", -1, g.getId());
    }

    /**
     * Tests Group(long id) method with accuracy state.
     */
    public void testGroupAccuracy2() {
        Group g = new Group(111111);
        assertNull("constructor1 is wrong.", g.getName());
        assertEquals("constructor2 is wrong.", 111111, g.getId());
    }

    /**
     * Tests Group(long id, String name) method with accuracy state.
     */
    public void testGroupAccuracy3() {
        Group g = new Group(111111, "group2");
        assertEquals("constructor1 is wrong.", "group2", g.getName());
        assertEquals("constructor2 is wrong.", 111111, g.getId());
    }

    /**
     * Tests Group(long id, float weight) method with accuracy state.
     */
    public void testGroupAccuracy4() {
        Group g = new Group(111111, 50.456f);
        assertNull("constructor1 is wrong.", g.getName());
        assertEquals("constructor2 is wrong.", 111111, g.getId());
        assertEquals("constructor is wrong.", 50.456f, g.getWeight(), 0);
    }

    /**
     * Tests Group(long id, String name, float weight) method with accuracy state.
     */
    public void testGroupAccuracy5() {
        Group g = new Group(111111, "group2", 10.123f);
        assertEquals("constructor1 is wrong.", "group2", g.getName());
        assertEquals("constructor2 is wrong.", 111111, g.getId());
        assertEquals("constructor is wrong.", 10.123f, g.getWeight(), 0);
    }

    /**
     * Tests addSection(Section section) method with accuracy state.
     */
    public void testAddSectionAccuracy1() {
        assertEquals("addSection1 is wrong.", 0, group.getNumberOfSections());

        group.addSection(section1);
        assertEquals("addSection2 is wrong.", 1, group.getNumberOfSections());
        assertEquals("addSection3 is wrong.", section1, group.getSection(0));
    }
    
    /**
     * Tests addSection(Section section) method with accuracy state.
     */
    public void testAddSectionAccuracy2() {
        assertEquals("addSection1 is wrong.", 0, group.getNumberOfSections());

        group.addSection(section1);
        assertEquals("addSection2 is wrong.", 1, group.getNumberOfSections());
        assertEquals("addSection3 is wrong.", section1, group.getSection(0));
        
        group.addSection(section1);
        assertEquals("addSection2 is wrong.", 1, group.getNumberOfSections());
        assertEquals("addSection3 is wrong.", section1, group.getSection(0));
    }

    /**
     * Tests addSections(Section[] sections) method with accuracy state.
     */
    public void testAddSectionsAccuracy1() {
        assertEquals("addSections1 is wrong.", 0, group.getNumberOfSections());

        group.addSections(sections);

        List ss = Arrays.asList(group.getAllSections());
        assertTrue("addSections2 is wrong.", ss.indexOf(section1) != -1);
        assertTrue("addSections3 is wrong.", ss.indexOf(section2) != -1);
    }
    
    /**
     * Tests addSections(Section[] sections) method with accuracy state.
     */
    public void testAddSectionsAccuracy2() {
        assertEquals("addSections1 is wrong.", 0, group.getNumberOfSections());

        group.addSections(sections);

        List ss = Arrays.asList(group.getAllSections());
        assertTrue("addSections2 is wrong.", ss.indexOf(section1) != -1);
        assertTrue("addSections3 is wrong.", ss.indexOf(section2) != -1);
        
        group.addSections(sections);

        List ss2 = Arrays.asList(group.getAllSections());
        assertTrue("addSections2 is wrong.", ss2.indexOf(section1) != -1);
        assertTrue("addSections3 is wrong.", ss2.indexOf(section2) != -1);
    }

    /**
     * Tests removeSection(Section section) method with accuracy state.
     */
    public void testRemoveSection1Accuracy1() {
        assertEquals("removeSection1 is wrong.", 0, group.getNumberOfSections());

        group.addSections(sections);

        List ss = Arrays.asList(group.getAllSections());
        assertTrue("removeSection2 is wrong.", ss.indexOf(section1) != -1);
        assertTrue("removeSection3 is wrong.", ss.indexOf(section2) != -1);

        group.removeSection(section2);

        List ss2 = Arrays.asList(group.getAllSections());
        assertEquals("removeSection4 is wrong.", 1, ss2.size());
        assertTrue("removeSection5 is wrong.", ss2.indexOf(section1) != -1);
        assertTrue("removeSection6 is wrong.", ss2.indexOf(section2) == -1);
    }
    
    /**
     * Tests removeSection(Section section) method with accuracy state.
     */
    public void testRemoveSection1Accuracy2() {
        assertEquals("removeSection1 is wrong.", 0, group.getNumberOfSections());

        group.addSections(sections);

        List ss = Arrays.asList(group.getAllSections());
        assertTrue("removeSection2 is wrong.", ss.indexOf(section1) != -1);
        assertTrue("removeSection3 is wrong.", ss.indexOf(section2) != -1);

        Section newSection = new Section(4565);
        group.removeSection(newSection);

        List ss2 = Arrays.asList(group.getAllSections());
        assertTrue("removeSection2 is wrong.", ss2.indexOf(section1) != -1);
        assertTrue("removeSection3 is wrong.", ss2.indexOf(section2) != -1);
    }

    /**
     * Tests removeSections(Section[] sections) method with accuracy state.
     */
    public void testRemoveSectionsAccuracy() {
        assertEquals("removeSections1 is wrong.", 0, group.getNumberOfSections());

        group.addSections(sections);

        Section section3 = new Section(123);
        group.addSection(section3);

        assertEquals("removeSections2 is wrong.", 3, group.getNumberOfSections());

        group.removeSections(new Section[] { section1, section3 });

        assertEquals("removeSections3 is wrong.", 1, group.getNumberOfSections());

        assertEquals("removeSections4 is wrong.", section2, group.getSection(0));
    }

    /**
     * Tests clearSections() method with accuracy state.
     */
    public void testClearSectionsAccuracy() {
        assertEquals("clearSections1 is wrong.", 0, group.getNumberOfSections());

        group.addSections(sections);

        Section section3 = new Section(123);
        group.addSection(section3);

        assertEquals("clearSections2 is wrong.", 3, group.getNumberOfSections());

        group.clearSections();

        assertEquals("clearSections3 is wrong.", 0, group.getNumberOfSections());
    }

    /**
     * Tests getSection(int sectionIndex) method with accuracy state.
     */
    public void testGetSectionAccuracy() {
        assertEquals("getSection1 is wrong.", 0, group.getNumberOfSections());

        group.addSections(sections);

        Section section3 = new Section(123);
        group.addSection(section3);

        assertEquals("getSection2 is wrong.", 3, group.getNumberOfSections());

        assertEquals("getSection3 is wrong.", section3, group.getSection(2));
    }

    /**
     * Tests getAllSections() method with accuracy state.
     */
    public void testGetAllSectionsAccuracy() {
        assertEquals("getAllSections1 is wrong.", 0, group.getNumberOfSections());

        group.addSections(sections);

        Section section3 = new Section(123);
        group.addSection(section3);

        Section[] ss = group.getAllSections();
        assertEquals("getAllSections1 is wrong.", 3, ss.length);

        List ss2 = Arrays.asList(ss);

        assertTrue("getAllSections5 is wrong.", ss2.indexOf(section1) != -1);
        assertTrue("getAllSections6 is wrong.", ss2.indexOf(section2) != -1);
        assertTrue("getAllSections6 is wrong.", ss2.indexOf(section3) != -1);
    }

    /**
     * Tests getNumberOfSections() method with accuracy state.
     */
    public void testGetNumberOfSectionsAccuracy() {
        assertEquals("getNumberOfSections1 is wrong.", 0, group.getNumberOfSections());

        group.addSections(sections);

        assertEquals("getNumberOfSections2 is wrong.", 2, group.getNumberOfSections());

        Section section3 = new Section(123);
        group.addSection(section3);

        assertEquals("getNumberOfSections3 is wrong.", 3, group.getNumberOfSections());
    }

    /**
     * Tests checkWeights(float tolerance) method with accuracy state.
     */
    public void testCheckWeightsAccuracy1() {
        group.addSections(sections);

        // 50.357 is the sum.
        assertFalse("checkWeights is wrong", group.checkWeights(0));
    }

    /**
     * Tests checkWeights(float tolerance) method with accuracy state.
     */
    public void testCheckWeightsAccuracy2() {
        group.addSections(sections);

        // 149.591 is the sum.
        Section section3 = new Section(777779, "design method section2", 49.643f);
        section3.addQuestion(question);
        group.addSection(section3);
        assertTrue("checkWeights is wrong", group.checkWeights(1e-9f));
    }

    /**
     * Tests checkWeights(float tolerance) method with accuracy state. the another
     * section is not true.
     */
    public void testCheckWeightsAccuracy3() {
        group.addSections(sections);

        Section section3 = new Section(777779, "design method section2", 49.643f);
        group.addSection(section3);
        assertFalse("checkWeights is wrong", group.checkWeights(0.08f));
    }

    /**
     * Tests removeSection(int index) method with accuracy state.
     */
    public void testRemoveSection2Accuracy() {
        assertEquals("getNumberOfSections1 is wrong.", 0, group.getNumberOfSections());

        group.addSections(sections);

        Section section3 = new Section(123);
        group.addSection(section3);

        assertEquals("getNumberOfSections2 is wrong.", 3, group.getNumberOfSections());

        group.removeSection(1);

        assertEquals("getNumberOfSections2 is wrong.", 2, group.getNumberOfSections());

        List ss2 = Arrays.asList(group.getAllSections());
        assertTrue("getAllSections5 is wrong.", ss2.indexOf(section1) != -1);
        assertTrue("getAllSections6 is wrong.", ss2.indexOf(section3) != -1);
    }

    /**
     * Tests insertSection(Section section, int index) method with accuracy state.
     */
    public void testInsertSectionAccuracy() {
        assertEquals("insertSection1 is wrong.", 0, group.getNumberOfSections());

        group.addSections(sections);
        assertEquals("insertSection2 is wrong.", section2, group.getSection(1));

        Section section3 = new Section(123);
        group.insertSection(section3, 1);

        assertEquals("insertSection3 is wrong.", 3, group.getNumberOfSections());
        assertEquals("insertSection4 is wrong.", section3, group.getSection(1));
    }
}
