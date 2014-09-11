/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.data.accuracytests;

import com.topcoder.management.scorecard.data.Group;
import com.topcoder.management.scorecard.data.Question;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.ScorecardEditor;
import com.topcoder.management.scorecard.data.ScorecardStatus;
import com.topcoder.management.scorecard.data.ScorecardType;
import com.topcoder.management.scorecard.data.Section;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.List;


/**
 * Tests for ScorecardEditor class.
 *
 * @author crackme
 * @version 1.0
 */
public class TestScorecardEditor extends TestCase {
    /** Group instance used to test. */
    private Group group1 = null;

    /** Group instance used to test. */
    private Group group2 = null;

    /** Group[] instance used to test. */
    private Group[] groups = null;

    /** ScorecardStatus instance used to test. */
    private ScorecardStatus status = null;

    /** ScorecardType instance used to test. */
    private ScorecardType yype = null;

    /** Scorecard instance used to test. */
    private Scorecard card = null;

    /** ScorecardEditor instance used for test. */
    private ScorecardEditor editor = null;

    /**
     * Setup the test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        Question question1 = new Question(777771, "elegant api1", 100.00f);
        Question question2 = new Question(777772, "elegant api2", 100.00f);
        Section section1 = new Section(777771, "design document section", 100.00f);
        section1.addQuestion(question1);

        Section section2 = new Section(777772, "design method section", 100.00f);
        section2.addQuestion(question2);

        group1 = new Group(111110, "group", 10.123f);
        group1.addSection(section1);
        group2 = new Group(111110, "group", 89.877f);
        group2.addSection(section2);

        groups = new Group[] { group1, group2 };
        card = new Scorecard(999990, "dev review card");
        
        editor = new ScorecardEditor(card, "crackme");
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
     * Tests ScorecardEditor(String user) method with accuracy state.
     */
    public void testScorecardEditorAccuracy1() {
        ScorecardEditor e = new ScorecardEditor("crackme");
        assertEquals("constructor is wrong.", "crackme", e.getUser());
    }

    /**
     * Tests ScorecardEditor(Scorecard scorecard, String user) method with accuracy
     * state.
     */
    public void testScorecardEditorAccuracy2() {
        assertEquals("constructor is wrong.", "crackme", editor.getUser());
        assertEquals("constructor is wrong.", card, editor.getScorecard());
    }

    /**
     * Tests setId(long id) method with accuracy state.
     */
    public void testSetIdAccuracy() {
        editor.setId(1234);
        assertEquals("setId is wrong.", 1234, editor.getScorecard().getId());
    }

    /**
     * Tests resetId() method with accuracy state.
     */
    public void testResetIdAccuracy() {
        editor.setId(1234);
        assertEquals("setId is wrong.", 1234, editor.getScorecard().getId());

        editor.resetId();
        assertEquals("resetId is wrong.", -1, editor.getScorecard().getId());
    }

    /**
     * Tests setName(String name) method with accuracy state.
     */
    public void testSetNameAccuracy() {
        editor.setName("crack");
        assertEquals("setName is wrong.", "crack", editor.getScorecard().getName());
    }

    /**
     * Tests resetName() method with accuracy state.
     */
    public void testResetNameAccuracy() {
        editor.setName("crack");
        assertEquals("setName is wrong.", "crack", editor.getScorecard().getName());

        editor.resetName();
        assertNull("setName is wrong.", editor.getScorecard().getName());
    }

    /**
     * Tests setScorecardStatus(ScorecardStatus scorecardStatus) method with accuracy
     * state.
     */
    public void testSetScorecardStatusAccuracy() {
        assertNull("setScorecardStatus1 is wrong.", card.getScorecardStatus());

        ScorecardStatus newStatus = new ScorecardStatus(123);

        editor.setScorecardStatus(newStatus);
        assertEquals("setScorecardStatus2 is wrong.", newStatus,
            editor.getScorecard().getScorecardStatus());
    }

    /**
     * Tests resetScorecardStatus() method with accuracy state.
     */
    public void testResetScorecardStatusAccuracy() {
        assertNull("resetScorecardStatus1 is wrong.",
            editor.getScorecard().getScorecardStatus());

        ScorecardStatus newStatus = new ScorecardStatus(123);
        editor.setScorecardStatus(newStatus);
        assertEquals("resetScorecardStatus2 is wrong.", newStatus,
            editor.getScorecard().getScorecardStatus());

        editor.resetScorecardStatus();
        assertNull("resetScorecardStatus3 is wrong.",
            editor.getScorecard().getScorecardStatus());
    }

    /**
     * Tests getScorecardStatus() method with accuracy state.
     */
    public void testGetScorecardStatusAccuracy() {
        assertNull("getScorecardStatus is wrong.",
            editor.getScorecard().getScorecardStatus());
    }

    /**
     * Tests setScorecardType(ScorecardType scorecardType) method with accuracy state.
     */
    public void testSetScorecardTypeAccuracy() {
        assertNull("resetScorecardType1 is wrong.",
            editor.getScorecard().getScorecardType());

        ScorecardType newType = new ScorecardType(123);

        editor.setScorecardType(newType);
        assertEquals("resetScorecardType2 is wrong.", newType,
            editor.getScorecard().getScorecardType());
    }

    /**
     * Tests resetScorecardType() method with accuracy state.
     */
    public void testResetScorecardTypeAccuracy() {
        assertNull("resetScorecardType1 is wrong.",
            editor.getScorecard().getScorecardType());

        ScorecardType newType = new ScorecardType(123);
        editor.setScorecardType(newType);
        assertEquals("resetScorecardType2 is wrong.", newType,
            editor.getScorecard().getScorecardType());

        editor.resetScorecardType();
        assertNull("resetScorecardType3 is wrong.",
            editor.getScorecard().getScorecardType());
    }

    /**
     * Tests getScorecardType() method with accuracy state.
     */
    public void testGetScorecardTypeAccuracy() {
        assertNull("resetScorecardType1 is wrong.",
            editor.getScorecard().getScorecardType());
    }

    /**
     * Tests setCategory(long category) method with accuracy state.
     */
    public void testSetCategoryAccuracy() {
        editor.setCategory(12);
        assertEquals("setCategory is wrong.", 12, editor.getScorecard().getCategory());
    }

    /**
     * Tests resetCategory() method with accuracy state.
     */
    public void testResetCategoryAccuracy() {
        editor.setCategory(12);
        assertEquals("resetCategory1 is wrong.", 12, editor.getScorecard().getCategory());

        editor.resetCategory();

        try {
            editor.getScorecard().getCategory();
            fail("resetCategory2 is wrong.");
        } catch (IllegalStateException ise) {
            // ignore
        } catch (Exception e) {
            fail("resetCategory3 is wrong.");
        }
    }

    /**
     * Tests getCategory() method with accuracy state.
     */
    public void testGetCategoryAccuracy() {
        try {
            editor.getScorecard().getCategory();
            fail("getCategory1 is wrong.");
        } catch (IllegalStateException ise) {
            // ignore
        } catch (Exception e) {
            fail("getCategory2 is wrong.");
        }
    }

    /**
     * Tests setVersion(String version) method with accuracy state.
     */
    public void testSetVersionAccuracy() {
        editor.setVersion("1.12");
        assertEquals("setVersion is wrong.", "1.12", editor.getScorecard().getVersion());
    }

    /**
     * Tests resetVersion() method with accuracy state.
     */
    public void testResetVersionAccuracy() {
        editor.setVersion("1.12");
        assertEquals("resetVersion1 is wrong.", "1.12", editor.getScorecard().getVersion());

        editor.resetVersion();
        assertNull("resetVersion2 is wrong.", editor.getScorecard().getVersion());
    }

    /**
     * Tests getVersion() method with accuracy state.
     */
    public void testGetVersionAccuracy() {
        assertNull("getVersion is wrong.", editor.getScorecard().getVersion());
    }

    /**
     * Tests setMinScore(float minScore) method with accuracy state.
     */
    public void testSetMinScoreAccuracy() {
        editor.setMinScore(12.34f);
        assertEquals("setMinScore is wrong.", 12.34f,
            editor.getScorecard().getMinScore(), 0);
    }

    /**
     * Tests resetMinScore() method with accuracy state.
     */
    public void testResetMinScoreAccuracy() {
        editor.setMinScore(12.34f);
        assertEquals("resetMinScore1 is wrong.", 12.34f,
            editor.getScorecard().getMinScore(), 0);

        editor.resetMinScore();
        try {
            editor.getScorecard().getMinScore();
            fail("getMinScore is wrong.");
        } catch (IllegalStateException e) {
            // pass.
        }
    }

    /**
     * Tests getMinScore() method with accuracy state.
     */
    public void testGetMinScoreAccuracy() {
        try {
            editor.getScorecard().getMinScore();
            fail("getMinScore is wrong.");
        } catch (IllegalStateException e) {
            // pass.
        }
    }

    /**
     * Tests setMaxScore(float maxScore) method with accuracy state.
     */
    public void testSetMaxScoreAccuracy() {
        editor.setMaxScore(12.34f);
        assertEquals("setMinScore is wrong.", 12.34f,
            editor.getScorecard().getMaxScore(), 0);
    }

    /**
     * Tests resetMaxScore() method with accuracy state.
     */
    public void testResetMaxScoreAccuracy() {
        editor.setMaxScore(12.34f);
        assertEquals("resetMinScore1 is wrong.", 12.34f,
            editor.getScorecard().getMaxScore(), 0);

        editor.resetMaxScore();
        try {
            editor.getScorecard().getMaxScore();
            fail("getMaxScore is wrong.");
        } catch (IllegalStateException e) {
            // pass.
        }
    }

    /**
     * Tests getMaxScore() method with accuracy state.
     */
    public void testGetMaxScoreAccuracy() {
        try {
            editor.getScorecard().getMaxScore();
            fail("getMaxScore is wrong.");
        } catch (IllegalStateException e) {
            // pass.
        }
    }

    /**
     * Tests addGroup(Group group) method with accuracy state.
     */
    public void testAddGroupAccuracy() {
        assertEquals("addSection1 is wrong.", 0, editor.getScorecard().getNumberOfGroups());

        editor.addGroup(group1);
        assertEquals("addSection2 is wrong.", 1, editor.getScorecard().getNumberOfGroups());
        assertEquals("addSection3 is wrong.", group1, editor.getScorecard().getGroup(0));
    }

    /**
     * Tests addGroups(Group[] groups) method with accuracy state.
     */
    public void testAddGroupsAccuracy() {
        assertEquals("addGroups1 is wrong.", 0, editor.getScorecard().getNumberOfGroups());

        editor.addGroups(groups);

        List ss = Arrays.asList(editor.getScorecard().getAllGroups());
        assertTrue("addGroups2 is wrong.", ss.indexOf(group1) != -1);
        assertTrue("addGroups3 is wrong.", ss.indexOf(group2) != -1);
    }

    /**
     * Tests removeGroup(Group group) method with accuracy state.
     */
    public void testRemoveGroupAccuracy() {
        assertEquals("removeGroup1 is wrong.", 0,
            editor.getScorecard().getNumberOfGroups());

        editor.addGroups(groups);

        List ss = Arrays.asList(editor.getScorecard().getAllGroups());
        assertTrue("removeGroup2 is wrong.", ss.indexOf(group1) != -1);
        assertTrue("removeGroup3 is wrong.", ss.indexOf(group2) != -1);

        editor.removeGroup(group2);

        List ss2 = Arrays.asList(editor.getScorecard().getAllGroups());
        assertEquals("removeGroup4 is wrong.", 1, ss2.size());
        assertTrue("removeGroup5 is wrong.", ss2.indexOf(group1) != -1);
        assertTrue("removeGroup6 is wrong.", ss2.indexOf(group2) == -1);
    }

    /**
     * Tests removeGroups(Group[] groups) method with accuracy state.
     */
    public void testRemoveGroupsAccuracy() {
        assertEquals("removeGroups1 is wrong.", 0,
            editor.getScorecard().getNumberOfGroups());

        editor.addGroups(groups);

        Group group3 = new Group(123);
        editor.addGroup(group3);

        assertEquals("removeGroups2 is wrong.", 3,
            editor.getScorecard().getNumberOfGroups());

        editor.removeGroups(new Group[] { group1, group3 });

        assertEquals("removeGroups3 is wrong.", 1,
            editor.getScorecard().getNumberOfGroups());

        assertEquals("removeGroups4 is wrong.", group2, editor.getScorecard().getGroup(0));
    }

    /**
     * Tests clearGroups() method with accuracy state.
     */
    public void testClearGroupsAccuracy() {
        assertEquals("clearGroups1 is wrong.", 0,
            editor.getScorecard().getNumberOfGroups());

        editor.addGroups(groups);

        Group group3 = new Group(123);
        editor.addGroup(group3);

        assertEquals("clearGroups2 is wrong.", 3,
            editor.getScorecard().getNumberOfGroups());

        editor.clearGroups();

        assertEquals("clearGroups3 is wrong.", 0,
            editor.getScorecard().getNumberOfGroups());
    }

    /**
     * Tests getGroup(int groupIndex) method with accuracy state.
     */
    public void testGetGroupAccuracy() {
        assertEquals("getGroup1 is wrong.", 0, editor.getScorecard().getNumberOfGroups());

        editor.addGroups(groups);

        Group group3 = new Group(123);
        editor.addGroup(group3);

        assertEquals("getGroup2 is wrong.", 3, editor.getScorecard().getNumberOfGroups());

        assertEquals("getGroup3 is wrong.", group3, editor.getScorecard().getGroup(2));
    }

    /**
     * Tests getAllGroups() method with accuracy state.
     */
    public void testGetAllGroupsAccuracy() {
        assertEquals("getAllGroups1 is wrong.", 0,
            editor.getScorecard().getNumberOfGroups());

        editor.addGroups(groups);

        Group group3 = new Group(123);
        editor.addGroup(group3);

        Group[] ss = editor.getScorecard().getAllGroups();
        assertEquals("getAllGroups1 is wrong.", 3, ss.length);

        List ss2 = Arrays.asList(ss);

        assertTrue("getAllGroups5 is wrong.", ss2.indexOf(group1) != -1);
        assertTrue("getAllGroups6 is wrong.", ss2.indexOf(group2) != -1);
        assertTrue("getAllGroups6 is wrong.", ss2.indexOf(group3) != -1);
    }

    /**
     * Tests getNumberOfGroups() method with accuracy state.
     */
    public void testGetNumberOfGroupsAccuracy() {
        assertEquals("getNumberOfGroups1 is wrong.", 0,
            editor.getScorecard().getNumberOfGroups());

        editor.addGroups(groups);

        assertEquals("getNumberOfGroups2 is wrong.", 2,
            editor.getScorecard().getNumberOfGroups());

        Group group3 = new Group(123);
        editor.addGroup(group3);

        assertEquals("getNumberOfGroups3 is wrong.", 3,
            editor.getScorecard().getNumberOfGroups());
    }

    /**
     * Tests checkWeights(float tolerance) method with accuracy state.
     */
    public void testCheckWeightsAccuracy1() {
        editor.addGroups(groups);
        assertTrue("checkWeights is wrong", editor.getScorecard().checkWeights(0.01f));
    }

    /**
     * Tests checkWeights(float tolerance) method with accuracy state.
     */
    public void testCheckWeightsAccuracy2() {
        editor.addGroups(groups);

        Group group3 = new Group(777779, "design method group2", 99.234f);
        editor.addGroup(group3);
        assertFalse("checkWeights is wrong", editor.getScorecard().checkWeights(0));
    }

    /**
     * Tests checkWeights(float tolerance) method with accuracy state.
     */
    public void testCheckWeightsAccuracy3() {
        editor.addGroups(groups);
        assertTrue("checkWeights is wrong", editor.getScorecard().checkWeights(0.08f));
    }

    /**
     * Tests removeGroup(int index) method with accuracy state.
     */
    public void testRemoveGroup2Accuracy() {
        assertEquals("getNumberOfGroups1 is wrong.", 0,
            editor.getScorecard().getNumberOfGroups());

        editor.addGroups(groups);

        Group group3 = new Group(123);
        editor.addGroup(group3);

        assertEquals("getNumberOfGroups2 is wrong.", 3,
            editor.getScorecard().getNumberOfGroups());

        editor.removeGroup(1);

        assertEquals("getNumberOfGroups2 is wrong.", 2,
            editor.getScorecard().getNumberOfGroups());

        List ss2 = Arrays.asList(editor.getScorecard().getAllGroups());
        assertTrue("getAllGroups5 is wrong.", ss2.indexOf(group1) != -1);
        assertTrue("getAllGroups6 is wrong.", ss2.indexOf(group3) != -1);
    }

    /**
     * Tests insertGroup(Group group, int index) method with accuracy state.
     */
    public void testInsertGroupAccuracy() {
        assertEquals("insertGroup1 is wrong.", 0,
            editor.getScorecard().getNumberOfGroups());

        editor.addGroups(groups);
        assertEquals("insertGroup2 is wrong.", group2, editor.getScorecard().getGroup(1));

        Group group3 = new Group(123);
        editor.insertGroup(group3, 1);

        assertEquals("insertGroup3 is wrong.", 3,
            editor.getScorecard().getNumberOfGroups());
        assertEquals("insertGroup4 is wrong.", group3, editor.getScorecard().getGroup(1));
    }

    /**
     * Tests setInUse(boolean inUse) method with accuracy state.
     */
    public void testSetInUseAccuracy() {
        assertFalse("isInUse is wrong.", editor.getScorecard().isInUse());
        editor.setInUse(true);
        assertTrue("isInUse is wrong.", editor.getScorecard().isInUse());
    }

    /**
     * Tests resetInUse() method with accuracy state.
     */
    public void testResetInUseAccuracy() {
        assertFalse("isInUse is wrong.", editor.getScorecard().isInUse());
        editor.setInUse(true);
        assertTrue("isInUse is wrong.", editor.getScorecard().isInUse());

        editor.resetInUse();
        assertFalse("isInUse is wrong.", editor.getScorecard().isInUse());
    }

    /**
     * Tests isInUse() method with accuracy state.
     */
    public void testIsInUseAccuracy() {
        assertFalse("isInUse is wrong.", editor.getScorecard().isInUse());
    }

    /**
     * Tests getScorecard() method with accuracy state.
     */
    public void testGetScorecardAccuracy() {
        assertEquals("getScorecard is wrong.", card, editor.getScorecard());
    }

    /**
     * Tests getUser() method with accuracy state.
     */
    public void testGetUserAccuracy() {
        assertEquals("getUser is wrong.", "crackme", editor.getUser());
    }
}
