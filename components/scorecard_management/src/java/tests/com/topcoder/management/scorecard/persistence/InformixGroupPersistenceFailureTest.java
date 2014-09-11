/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.persistence;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.management.scorecard.data.Group;
import com.topcoder.management.scorecard.data.Question;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.ScorecardStatus;
import com.topcoder.management.scorecard.data.ScorecardType;
import com.topcoder.management.scorecard.data.Section;

import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

import java.io.File;

import java.sql.Connection;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * Tests for InformixGroupPersistence class.
 *
 * @author kr00tki
 * @version 1.0
 */
public class InformixGroupPersistenceFailureTest extends TestCase {
    /** InformixGroupPersistence instance used to test. */
    private InformixGroupPersistence persist = null;

    /** A ConfigManager instance used to test. */
    private ConfigManager cm = ConfigManager.getInstance();

    /** Group instance used to test. */
    private Group group = null;

    /** A Scorecard instance used to test. */
    private Scorecard scorecard = null;

    /**
     * Setup the test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        persist = new InformixGroupPersistence(createConn());

        Question question = new Question(999991, 100.0f);
        question.setName("Is elegant APIs?");
        question.setDescription("here is the first des.");
        question.setGuideline("here is the first guide");

        Section section = new Section(999992, "structure design party.", 100.0f);
        section.addQuestion(question);

        Group group = new Group(999993, "Method.", 100.0f);
        group.addSection(section);

        scorecard = new Scorecard(999990);
        scorecard.setName("validator test");
        scorecard.addGroup(group);
        scorecard.setCategory(999994);
        scorecard.setVersion("2.0");
        scorecard.setMinScore(0);
        scorecard.setMaxScore(100.0f);
        scorecard.setScorecardStatus(new ScorecardStatus(999995, "incompleted."));
        scorecard.setScorecardType(new ScorecardType(999996, "design"));
    }

    /**
     * Create the connection.
     *
     * @return the connection.
     *
     * @throws Exception to JUnit.
     */
    private Connection createConn() throws Exception {
        clearAllNamespaces();
        cm.add(new File("test_files/failure/failure.xml").getAbsolutePath());

        DBConnectionFactory factory = new DBConnectionFactoryImpl(
                "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");

        return factory.createConnection();
    }

    /**
     * Clear all the namespaces in this component
     */
    private void clearAllNamespaces() {
        try {
            for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
                cm.removeNamespace((String) iter.next());
            }
        } catch (Exception e) {
            // ignore.
        }
    }

    /**
     * Setup the test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        clearAllNamespaces();
    }

    /**
     * Tests InformixGroupPersistence(Connection connection) method with null Connection
     * connection, IllegalArgumentException should be thrown.
     */
    public void testInformixGroupPersistence_NullConnection() {
        try {
            new InformixGroupPersistence(null);
            fail("testInformixGroupPersistence_NullConnection is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail(
                "Unknow exception occurs in testInformixGroupPersistence_NullConnection.");
        }
    }

    /**
     * Tests createGroup(Group group, int order, String operator, long parentId) method
     * with null Group group, IllegalArgumentException should be thrown.
     */
    public void testCreateGroup_NullGroup() {
        try {
            persist.createGroup(null, 1, "+", 123450);
            fail("testCreateGroup_NullGroup is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testCreateGroup_NullGroup.");
        }
    }

    /**
     * Tests createGroup(Group group, int order, String operator, long parentId) method
     * with null String operator, IllegalArgumentException should be thrown.
     */
    public void testCreateGroup_NullOperator() {
        try {
            persist.createGroup(group, 1, null, 123450);
            fail("testCreateGroup_NullOperator is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testCreateGroup_NullOperator.");
        }
    }

    /**
     * Tests createGroup(Group group, int order, String operator, long parentId) method
     * with empty String operator, IllegalArgumentException should be thrown.
     */
    public void testCreateGroup_EmptyOperator() {
        try {
            persist.createGroup(group, 1, " ", 123450);
            fail("testCreateGroup_EmptyOperator is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testCreateGroup_EmptyOperator.");
        }
    }

    /**
     * Tests updateGroup(Group group, int order, String operator, long parentId,
     * Scorecard oldScorecard,List deletedSectionIds, List deletedQuestionIds) method
     * with null Group group, IllegalArgumentException should be thrown.
     */
    public void testUpdateGroup_NullGroup() {
        try {
            persist.updateGroup(null, 1, "+", 123450, scorecard, new ArrayList(),
                new ArrayList());
            fail("testUpdateGroup_NullGroup is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testUpdateGroup_NullGroup.");
        }
    }

    /**
     * Tests updateGroup(Group group, int order, String operator, long parentId,
     * Scorecard oldScorecard,List deletedSectionIds, List deletedQuestionIds) method
     * with null String operator, IllegalArgumentException should be thrown.
     */
    public void testUpdateGroup_NullOperator() {
        try {
            persist.updateGroup(group, 1, null, 123450, scorecard, new ArrayList(),
                new ArrayList());
            fail("testUpdateGroup_NullOperator is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testUpdateGroup_NullOperator.");
        }
    }

    /**
     * Tests updateGroup(Group group, int order, String operator, long parentId,
     * Scorecard oldScorecard,List deletedSectionIds, List deletedQuestionIds) method
     * with empty String operator, IllegalArgumentException should be thrown.
     */
    public void testUpdateGroup_EmptyOperator() {
        try {
            persist.updateGroup(group, 1, " ", 123450, scorecard, new ArrayList(),
                new ArrayList());
            fail("testUpdateGroup_EmptyOperator is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testUpdateGroup_EmptyOperator.");
        }
    }

    /**
     * Tests updateGroup(Group group, int order, String operator, long parentId,
     * Scorecard oldScorecard,List deletedSectionIds, List deletedQuestionIds) method
     * with null Scorecard oldScorecard, IllegalArgumentException should be thrown.
     */
    public void testUpdateGroup_NullOldScorecard() {
        try {
            persist.updateGroup(group, 1, "+", 123450, null, new ArrayList(),
                new ArrayList());
            fail("testUpdateGroup_NullOldScorecard is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testUpdateGroup_NullOldScorecard.");
        }
    }

    /**
     * Tests updateGroup(Group group, int order, String operator, long parentId,
     * Scorecard oldScorecard,List deletedSectionIds, List deletedQuestionIds) method
     * with null List deletedSectionIds, IllegalArgumentException should be thrown.
     */
    public void testUpdateGroup_NullDeletedSectionIds() {
        try {
            persist.updateGroup(group, 1, "+", 123450, scorecard, null, new ArrayList());
            fail("testUpdateGroup_NullDeletedSectionIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testUpdateGroup_NullDeletedSectionIds.");
        }
    }

    /**
     * Tests updateGroup(Group group, int order, String operator, long parentId,
     * Scorecard oldScorecard,List deletedSectionIds, List deletedQuestionIds) method
     * with null List deletedQuestionIds, IllegalArgumentException should be thrown.
     */
    public void testUpdateGroup_NullDeletedQuestionIds() {
        try {
            persist.updateGroup(group, 1, "+", 123450, scorecard, new ArrayList(), null);
            fail("testUpdateGroup_NullDeletedQuestionIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testUpdateGroup_NullDeletedQuestionIds.");
        }
    }

    /**
     * Tests deleteGroups(long[] ids) method with null long[] ids,
     * IllegalArgumentException should be thrown.
     */
    public void testDeleteGroups_NullIds() {
        try {
            persist.deleteGroups(null);
            fail("testDeleteGroups_NullIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testDeleteGroups_NullIds.");
        }
    }

    /**
     * Tests deleteGroups(long[] ids) method with empty long[] ids,
     * IllegalArgumentException should be thrown.
     */
    public void testDeleteGroups_EmptyIds() {
        try {
            persist.deleteGroups(new long[] {  });
            fail("testDeleteGroups_EmptyIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testDeleteGroups_EmptyIds.");
        }
    }

    /**
     * Tests deleteGroups(long[] ids) method with zero long[] ids,
     * IllegalArgumentException should be thrown.
     */
    public void testDeleteGroups_ZeroIds() {
        try {
            persist.deleteGroups(new long[] { 0 });
            fail("testDeleteGroups_ZeroIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testDeleteGroups_ZeroIds.");
        }
    }

    /**
     * Tests deleteGroups(long[] ids) method with negative long[] ids,
     * IllegalArgumentException should be thrown.
     */
    public void testDeleteGroups_NegativeIds() {
        try {
            persist.deleteGroups(new long[] { 0 });
            fail("testDeleteGroups_NegativeIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testDeleteGroups_NegativeIds.");
        }
    }

    /**
     * Tests getGroup(long id) method with zero long id, IllegalArgumentException should
     * be thrown.
     */
    public void testGetGroup_ZeroId() {
        try {
            persist.getGroup(0);
            fail("testGetGroup_ZeroId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testGetGroup_ZeroId.");
        }
    }

    /**
     * Tests getGroup(long id) method with negative long id, IllegalArgumentException
     * should be thrown.
     */
    public void testGetGroup_NegativeId() {
        try {
            persist.getGroup(-1);
            fail("testGetGroup_NegativeId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testGetGroup_NegativeId.");
        }
    }
}
