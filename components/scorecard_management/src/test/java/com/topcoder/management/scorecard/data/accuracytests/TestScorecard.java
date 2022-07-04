/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.data.accuracytests;

import com.topcoder.management.scorecard.data.Group;
import com.topcoder.management.scorecard.data.Question;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.ScorecardStatus;
import com.topcoder.management.scorecard.data.ScorecardType;
import com.topcoder.management.scorecard.data.Section;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * Tests for Scorecard class.
 *
 * @author crackme
 * @version 1.0
 */
public class TestScorecard extends TestCase {
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
     * Tests Scorecard() method with accuracy state.
     */
    public void testScorecardAccuracy1() {
        Scorecard s = new Scorecard();
        assertNull("constructor1 is wrong.", s.getName());
        assertEquals("constructor is wrong.", -1, s.getId());
    }

    /**
     * Tests Scorecard(long id) method with accuracy state.
     */
    public void testScorecardAccuracy2() {
        Scorecard s = new Scorecard(111111);
        assertNull("constructor1 is wrong.", s.getName());
        assertEquals("constructor2 is wrong.", 111111, s.getId());
    }

    /**
     * Tests Scorecard(long id, String name) method with accuracy state.
     */
    public void testScorecardAccuracy3() {
        Scorecard s = new Scorecard(11111, "card");
        assertEquals("constructor1 is wrong.", "card", s.getName());
        assertEquals("constructor2 is wrong.", 11111, s.getId());
    }

    /**
     * Tests setScorecardStatus(ScorecardStatus scorecardStatus) method with accuracy
     * state.
     */
    public void testSetScorecardStatusAccuracy() {
        assertNull("setScorecardStatus1 is wrong.", card.getScorecardStatus());

        ScorecardStatus newStatus = new ScorecardStatus(123);

        card.setScorecardStatus(newStatus);
        assertEquals("setScorecardStatus2 is wrong.", newStatus, card.getScorecardStatus());
    }

    /**
     * Tests resetScorecardStatus() method with accuracy state.
     */
    public void testResetScorecardStatusAccuracy() {
        assertNull("resetScorecardStatus1 is wrong.", card.getScorecardStatus());

        ScorecardStatus newStatus = new ScorecardStatus(123);
        card.setScorecardStatus(newStatus);
        assertEquals("resetScorecardStatus2 is wrong.", newStatus,
            card.getScorecardStatus());

        card.resetScorecardStatus();
        assertNull("resetScorecardStatus3 is wrong.", card.getScorecardStatus());
    }

    /**
     * Tests getScorecardStatus() method with accuracy state.
     */
    public void testGetScorecardStatusAccuracy() {
        assertNull("getScorecardStatus is wrong.", card.getScorecardStatus());
    }

    /**
     * Tests setScorecardType(ScorecardType scorecardType) method with accuracy state.
     */
    public void testSetScorecardTypeAccuracy() {
        assertNull("resetScorecardType1 is wrong.", card.getScorecardType());

        ScorecardType newType = new ScorecardType(123);

        card.setScorecardType(newType);
        assertEquals("resetScorecardType2 is wrong.", newType, card.getScorecardType());
    }

    /**
     * Tests resetScorecardType() method with accuracy state.
     */
    public void testResetScorecardTypeAccuracy() {
        assertNull("resetScorecardType1 is wrong.", card.getScorecardType());

        ScorecardType newType = new ScorecardType(123);
        card.setScorecardType(newType);
        assertEquals("resetScorecardType2 is wrong.", newType, card.getScorecardType());

        card.resetScorecardType();
        assertNull("resetScorecardType3 is wrong.", card.getScorecardType());
    }

    /**
     * Tests getScorecardType() method with accuracy state.
     */
    public void testGetScorecardTypeAccuracy() {
        assertNull("resetScorecardType1 is wrong.", card.getScorecardType());
    }

    /**
     * Tests setCategory(long category) method with accuracy state.
     */
    public void testSetCategoryAccuracy() {
        card.setCategory(12);
        assertEquals("setCategory is wrong.", 12, card.getCategory());
    }

    /**
     * Tests resetCategory() method with accuracy state.
     */
    public void testResetCategoryAccuracy() {
        card.setCategory(12);
        assertEquals("resetCategory1 is wrong.", 12, card.getCategory());

        card.resetCategory();

        try {
            card.getCategory();
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
            card.getCategory();
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
        card.setVersion("1.12");
        assertEquals("setVersion is wrong.", "1.12", card.getVersion());
    }

    /**
     * Tests resetVersion() method with accuracy state.
     */
    public void testResetVersionAccuracy() {
        card.setVersion("1.12");
        assertEquals("resetVersion1 is wrong.", "1.12", card.getVersion());

        card.resetVersion();
        assertNull("resetVersion2 is wrong.", card.getVersion());
    }

    /**
     * Tests getVersion() method with accuracy state.
     */
    public void testGetVersionAccuracy() {
        assertNull("getVersion is wrong.", card.getVersion());
    }

    /**
     * Tests setMinScore(float minScore) method with accuracy state.
     */
    public void testSetMinScoreAccuracy() {
        card.setMinScore(12.34f);
        assertEquals("setMinScore is wrong.", 12.34f, card.getMinScore(), 0);
    }

    /**
     * Tests resetMinScore() method with accuracy state.
     */
    public void testResetMinScoreAccuracy() {
        card.setMinScore(12.34f);
        assertEquals("resetMinScore1 is wrong.", 12.34f, card.getMinScore(), 0);

        card.resetMinScore();
        try {
            card.getMinScore();
            fail("resetMinScore is wrong.");
        } catch (IllegalStateException e) {
            // pass.
        }
    }

    /**
     * Tests getMinScore() method with accuracy state.
     */
    public void testGetMinScoreAccuracy() {
        try {
            card.getMinScore();
            fail("getMinScore is wrong.");
        } catch (IllegalStateException e) {
            // pass.
        }
    }

    /**
     * Tests setMaxScore(float maxScore) method with accuracy state.
     */
    public void testSetMaxScoreAccuracy() {
        card.setMaxScore(12.34f);
        assertEquals("setMinScore is wrong.", 12.34f, card.getMaxScore(), 0);
    }

    /**
     * Tests resetMaxScore() method with accuracy state.
     */
    public void testResetMaxScoreAccuracy() {
        card.setMaxScore(12.34f);
        assertEquals("resetMinScore1 is wrong.", 12.34f, card.getMaxScore(), 0);

        card.resetMaxScore();
        try {
            card.getMaxScore();
            fail("resetMaxScore is wrong.");
        } catch (IllegalStateException e) {
            // pass.
        }
    }

    /**
     * Tests getMaxScore() method with accuracy state.
     */
    public void testGetMaxScoreAccuracy() {
        try {
            card.getMaxScore();
            fail("getMaxScore is wrong.");
        } catch (IllegalStateException e) {
            // pass.
        }
    }

    /**
     * Tests addGroup(Group group) method with accuracy state.
     */
    public void testAddGroupAccuracy1() {
        assertEquals("addSection1 is wrong.", 0, card.getNumberOfGroups());

        card.addGroup(group1);
        assertEquals("addSection2 is wrong.", 1, card.getNumberOfGroups());
        assertEquals("addSection3 is wrong.", group1, card.getGroup(0));
    }
    
    /**
     * Tests addGroup(Group group) method with accuracy state. The group exists,
     * so do nothing.
     */
    public void testAddGroupAccuracy2() {
        assertEquals("addSection1 is wrong.", 0, card.getNumberOfGroups());

        card.addGroup(group1);
        assertEquals("addSection2 is wrong.", 1, card.getNumberOfGroups());
        assertEquals("addSection3 is wrong.", group1, card.getGroup(0));
        
        card.addGroup(group1);
        assertEquals("addSection2 is wrong.", 1, card.getNumberOfGroups());
        assertEquals("addSection3 is wrong.", group1, card.getGroup(0));
    }

    /**
     * Tests addGroups(Group[] groups) method with accuracy state.
     */
    public void testAddGroupsAccuracy1() {
        assertEquals("addGroups1 is wrong.", 0, card.getNumberOfGroups());

        card.addGroups(groups);

        List ss = Arrays.asList(card.getAllGroups());
        assertTrue("addGroups2 is wrong.", ss.indexOf(group1) != -1);
        assertTrue("addGroups3 is wrong.", ss.indexOf(group2) != -1);
    }
    
    /**
     * Tests addGroups(Group[] groups) method with accuracy state. The groups exist,
     * so do nothing.
     */
    public void testAddGroupsAccuracy2() {
        assertEquals("addGroups1 is wrong.", 0, card.getNumberOfGroups());

        card.addGroups(groups);

        List ss = Arrays.asList(card.getAllGroups());
        assertTrue("addGroups2 is wrong.", ss.indexOf(group1) != -1);
        assertTrue("addGroups3 is wrong.", ss.indexOf(group2) != -1);
        
        card.addGroups(groups);

        List ss2 = Arrays.asList(card.getAllGroups());
        assertTrue("addGroups2 is wrong.", ss2.indexOf(group1) != -1);
        assertTrue("addGroups3 is wrong.", ss2.indexOf(group2) != -1);
    }

    /**
     * Tests removeGroup(Group group) method with accuracy state.
     */
    public void testRemoveGroupAccuracy1() {
        assertEquals("removeGroup1 is wrong.", 0, card.getNumberOfGroups());

        card.addGroups(groups);

        List ss = Arrays.asList(card.getAllGroups());
        assertTrue("removeGroup2 is wrong.", ss.indexOf(group1) != -1);
        assertTrue("removeGroup3 is wrong.", ss.indexOf(group2) != -1);

        card.removeGroup(group2);

        List ss2 = Arrays.asList(card.getAllGroups());
        assertEquals("removeGroup4 is wrong.", 1, ss2.size());
        assertTrue("removeGroup5 is wrong.", ss2.indexOf(group1) != -1);
        assertTrue("removeGroup6 is wrong.", ss2.indexOf(group2) == -1);
    }
    
    /**
     * Tests removeGroup(Group group) method with accuracy state. The group
     * does not exist, so do nothing.
     */
    public void testRemoveGroupAccuracy2() {
        assertEquals("removeGroup1 is wrong.", 0, card.getNumberOfGroups());

        card.addGroups(groups);

        List ss = Arrays.asList(card.getAllGroups());
        assertTrue("removeGroup2 is wrong.", ss.indexOf(group1) != -1);
        assertTrue("removeGroup3 is wrong.", ss.indexOf(group2) != -1);

        Group group3 = new Group(123);
        
        card.removeGroup(group3);

        List ss2 = Arrays.asList(card.getAllGroups());
        assertEquals("removeGroup4 is wrong.", 2, ss2.size());
        assertTrue("removeGroup5 is wrong.", ss2.indexOf(group1) != -1);
        assertTrue("removeGroup6 is wrong.", ss2.indexOf(group2) != -1);
    }

    /**
     * Tests removeGroups(Group[] groups) method with accuracy state.
     */
    public void testRemoveGroupsAccuracy() {
        assertEquals("removeGroup1 is wrong.", 0, card.getNumberOfGroups());

        card.addGroups(groups);

        List ss = Arrays.asList(card.getAllGroups());
        assertTrue("removeGroup2 is wrong.", ss.indexOf(group1) != -1);
        assertTrue("removeGroup3 is wrong.", ss.indexOf(group2) != -1);

        Group group3 = new Group(123);
        Group group4 = new Group(1235);
        card.removeGroups(new Group[] {group3, group4});

        List ss2 = Arrays.asList(card.getAllGroups());
        assertEquals("removeGroup4 is wrong.", 2, ss2.size());
        assertTrue("removeGroup5 is wrong.", ss2.indexOf(group1) != -1);
        assertTrue("removeGroup6 is wrong.", ss2.indexOf(group2) != -1);
    }

    /**
     * Tests clearGroups() method with accuracy state.
     */
    public void testClearGroupsAccuracy() {
        assertEquals("clearGroups1 is wrong.", 0, card.getNumberOfGroups());

        card.addGroups(groups);

        Group group3 = new Group(123);
        card.addGroup(group3);

        assertEquals("clearGroups2 is wrong.", 3, card.getNumberOfGroups());

        card.clearGroups();

        assertEquals("clearGroups3 is wrong.", 0, card.getNumberOfGroups());
    }

    /**
     * Tests getGroup(int groupIndex) method with accuracy state.
     */
    public void testGetGroupAccuracy() {
        assertEquals("getGroup1 is wrong.", 0, card.getNumberOfGroups());

        card.addGroups(groups);

        Group group3 = new Group(123);
        card.addGroup(group3);

        assertEquals("getGroup2 is wrong.", 3, card.getNumberOfGroups());

        assertEquals("getGroup3 is wrong.", group3, card.getGroup(2));
    }

    /**
     * Tests getAllGroups() method with accuracy state.
     */
    public void testGetAllGroupsAccuracy() {
        assertEquals("getAllGroups1 is wrong.", 0, card.getNumberOfGroups());

        card.addGroups(groups);

        Group group3 = new Group(123);
        card.addGroup(group3);

        Group[] ss = card.getAllGroups();
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
        assertEquals("getNumberOfGroups1 is wrong.", 0, card.getNumberOfGroups());

        card.addGroups(groups);

        assertEquals("getNumberOfGroups2 is wrong.", 2, card.getNumberOfGroups());

        Group group3 = new Group(123);
        card.addGroup(group3);

        assertEquals("getNumberOfGroups3 is wrong.", 3, card.getNumberOfGroups());
    }

    /**
     * Tests checkWeights(float tolerance) method with accuracy state.
     */
    public void testCheckWeightsAccuracy1() {
        card.addGroups(groups);
        assertTrue("checkWeights is wrong", card.checkWeights(0.01f));
    }

    /**
     * Tests checkWeights(float tolerance) method with accuracy state.
     */
    public void testCheckWeightsAccuracy2() {
        card.addGroups(groups);

        Group group3 = new Group(777779, "design method group2", 99.234f);
        card.addGroup(group3);
        assertFalse("checkWeights is wrong", card.checkWeights(0));
    }

    /**
     * Tests checkWeights(float tolerance) method with accuracy state.
     */
    public void testCheckWeightsAccuracy3() {
        card.addGroups(groups);
        assertTrue("checkWeights is wrong", card.checkWeights(0.08f));
    }

    /**
     * Tests removeGroup(int index) method with accuracy state.
     */
    public void testRemoveGroup2Accuracy() {
        assertEquals("getNumberOfGroups1 is wrong.", 0, card.getNumberOfGroups());

        card.addGroups(groups);

        Group group3 = new Group(123);
        card.addGroup(group3);

        assertEquals("getNumberOfGroups2 is wrong.", 3, card.getNumberOfGroups());

        card.removeGroup(1);

        assertEquals("getNumberOfGroups2 is wrong.", 2, card.getNumberOfGroups());

        List ss2 = Arrays.asList(card.getAllGroups());
        assertTrue("getAllGroups5 is wrong.", ss2.indexOf(group1) != -1);
        assertTrue("getAllGroups6 is wrong.", ss2.indexOf(group3) != -1);
    }

    /**
     * Tests insertGroup(Group group, int index) method with accuracy state.
     */
    public void testInsertGroupAccuracy() {
        assertEquals("insertGroup1 is wrong.", 0, card.getNumberOfGroups());

        card.addGroups(groups);
        assertEquals("insertGroup2 is wrong.", group2, card.getGroup(1));

        Group group3 = new Group(123);
        card.insertGroup(group3, 1);

        assertEquals("insertGroup3 is wrong.", 3, card.getNumberOfGroups());
        assertEquals("insertGroup4 is wrong.", group3, card.getGroup(1));
    }

    /**
     * Tests setInUse(boolean inUse) method with accuracy state.
     */
    public void testSetInUseAccuracy() {
        assertFalse("isInUse is wrong.", card.isInUse());
        card.setInUse(true);
        assertTrue("isInUse is wrong.", card.isInUse());
    }

    /**
     * Tests resetInUse() method with accuracy state.
     */
    public void testResetInUseAccuracy() {
        assertFalse("isInUse is wrong.", card.isInUse());
        card.setInUse(true);
        assertTrue("isInUse is wrong.", card.isInUse());

        card.resetInUse();
        assertFalse("isInUse is wrong.", card.isInUse());
    }

    /**
     * Tests isInUse() method with accuracy state.
     */
    public void testIsInUseAccuracy() {
        assertFalse("isInUse is wrong.", card.isInUse());
    }

    /**
     * Tests setCreationUser(String creationUser) method with accuracy state.
     */
    public void testSetCreationUserAccuracy() {
        assertNull("getCreationUser is wrong.", card.getCreationUser());

        card.setCreationUser("crackme");
        assertEquals("getCreationUser is wrong.", "crackme", card.getCreationUser());
    }

    /**
     * Tests getCreationUser() method with accuracy state.
     */
    public void testGetCreationUserAccuracy() {
        assertNull("getCreationUser is wrong.", card.getCreationUser());
    }

    /**
     * Tests setCreationTimestamp(Date creationTimestamp) method with accuracy state.
     */
    public void testSetCreationTimestampAccuracy() {
        assertNull("setCreationTimestamp1 is wrong.", card.getCreationTimestamp());

        Date time = new Date(1);
        card.setCreationTimestamp(time);
        assertEquals("setCreationTimestamp2 is wrong.", time, card.getCreationTimestamp());
    }

    /**
     * Tests getCreationTimestamp() method with accuracy state.
     */
    public void testGetCreationTimestampAccuracy() {
        assertNull("getCreationTimestamp is wrong.", card.getCreationTimestamp());
    }

    /**
     * Tests setModificationUser(String modificationUser) method with accuracy state.
     */
    public void testSetModificationUserAccuracy() {
        assertNull("setModificationUser1 is wrong.", card.getModificationUser());

        card.setModificationUser("crackme");
        assertEquals("setModificationUser2 is wrong.", "crackme",
            card.getModificationUser());
    }

    /**
     * Tests getModificationUser() method with accuracy state.
     */
    public void testGetModificationUserAccuracy() {
        assertNull("setModificationUser1 is wrong.", card.getModificationUser());
    }

    /**
     * Tests setModificationTimestamp(Date modificationTimestamp) method with accuracy
     * state.
     */
    public void testSetModificationTimestampAccuracy() {
        assertNull("setModificationTimestamp1 is wrong.", card.getModificationTimestamp());

        Date time = new Date(1);
        card.setModificationTimestamp(time);
        assertEquals("setModificationTimestamp2 is wrong.", time,
            card.getModificationTimestamp());
    }

    /**
     * Tests getModificationTimestamp() method with accuracy state.
     */
    public void testGetModificationTimestampAccuracy() {
        assertNull("getModificationTimestamp is wrong.", card.getModificationTimestamp());
    }
}
