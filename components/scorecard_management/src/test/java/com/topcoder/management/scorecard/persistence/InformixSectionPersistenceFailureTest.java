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
 * Tests for InformixSectionPersistence class.
 *
 * @author kr00tki
 * @version 1.0
 */
public class InformixSectionPersistenceFailureTest extends TestCase {
    /** Section instance used to test. */
    private Section section = null;

    /** A Scorecard instance used to test. */
    private Scorecard scorecard = null;

    /** InformixSectionPersistence instance used to test. */
    private InformixSectionPersistence persist = null;

    /** A ConfigManager instance used to test. */
    private ConfigManager cm = ConfigManager.getInstance();

    /**
     * Setup the test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        Question question = new Question(999991, 100.0f);
        question.setName("Is elegant APIs?");
        question.setDescription("here is the first des.");
        question.setGuideline("here is the first guide");

        section = new Section(999992, "structure design party.", 100.0f);
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

        persist = new InformixSectionPersistence(createConn());
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
     * Tests InformixSectionPersistence(Connection connection) method with null
     * Connection connection, IllegalArgumentException should be thrown.
     */
    public void testInformixSectionPersistence_NullConnection() {
        try {
            new InformixSectionPersistence(null);
            fail("testInformixSectionPersistence_NullConnection is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail(
                "Unknow exception occurs in testInformixSectionPersistence_NullConnection.");
        }
    }

    /**
     * Tests createSection(Section section, int order, String operator, long parentId)
     * method with null Section section, IllegalArgumentException should be thrown.
     */
    public void testCreateSection_NullSection() {
        try {
            persist.createSection(null, 1, "+", 123450);
            fail("testCreateSection_NullSection is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testCreateSection_NullSection.");
        }
    }

    /**
     * Tests createSection(Section section, int order, String operator, long parentId)
     * method with null String operator, IllegalArgumentException should be thrown.
     */
    public void testCreateSection_NullOperator() {
        try {
            persist.createSection(section, 1, null, 123450);
            fail("testCreateSection_NullOperator is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testCreateSection_NullOperator.");
        }
    }

    /**
     * Tests createSection(Section section, int order, String operator, long parentId)
     * method with empty String operator, IllegalArgumentException should be thrown.
     */
    public void testCreateSection_EmptyOperator() {
        try {
            persist.createSection(section, 1, " ", 123450);
            fail("testCreateSection_EmptyOperator is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testCreateSection_EmptyOperator.");
        }
    }

    /**
     * Tests updateSection(Section section, int order, String operator, long parentId,
     * Scorecard oldScorecard,List deletedQuestionIds) method with null Section section,
     * IllegalArgumentException should be thrown.
     */
    public void testUpdateSection_NullSection() {
        try {
            persist.updateSection(null, 1, "+", 123450, scorecard, new ArrayList());
            fail("testUpdateSection_NullSection is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testUpdateSection_NullSection.");
        }
    }

    /**
     * Tests updateSection(Section section, int order, String operator, long parentId,
     * Scorecard oldScorecard,List deletedQuestionIds) method with null String operator,
     * IllegalArgumentException should be thrown.
     */
    public void testUpdateSection_NullOperator() {
        try {
            persist.updateSection(section, 1, null, 123450, scorecard, new ArrayList());
            fail("testUpdateSection_NullOperator is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testUpdateSection_NullOperator.");
        }
    }

    /**
     * Tests updateSection(Section section, int order, String operator, long parentId,
     * Scorecard oldScorecard,List deletedQuestionIds) method with empty String
     * operator, IllegalArgumentException should be thrown.
     */
    public void testUpdateSection_EmptyOperator() {
        try {
            persist.updateSection(section, 1, " ", 123450, scorecard, new ArrayList());
            fail("testUpdateSection_EmptyOperator is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testUpdateSection_EmptyOperator.");
        }
    }

    /**
     * Tests updateSection(Section section, int order, String operator, long parentId,
     * Scorecard oldScorecard,List deletedQuestionIds) method with null Scorecard
     * oldScorecard, IllegalArgumentException should be thrown.
     */
    public void testUpdateSection_NullOldScorecard() {
        try {
            persist.updateSection(section, 1, "+", 123450, null, new ArrayList());
            fail("testUpdateSection_NullOldScorecard is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testUpdateSection_NullOldScorecard.");
        }
    }

    /**
     * Tests updateSection(Section section, int order, String operator, long parentId,
     * Scorecard oldScorecard,List deletedQuestionIds) method with null List
     * deletedQuestionIds, IllegalArgumentException should be thrown.
     */
    public void testUpdateSection_NullDeletedQuestionIds() {
        try {
            persist.updateSection(section, 1, "+", 123450, scorecard, null);
            fail("testUpdateSection_NullDeletedQuestionIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testUpdateSection_NullDeletedQuestionIds.");
        }
    }

    /**
     * Tests deleteSections(long[] ids) method with null long[] ids,
     * IllegalArgumentException should be thrown.
     */
    public void testDeleteSections_NullIds() {
        try {
            persist.deleteSections(null);
            fail("testDeleteSections_NullIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testDeleteSections_NullIds.");
        }
    }

    /**
     * Tests deleteSections(long[] ids) method with empty long[] ids,
     * IllegalArgumentException should be thrown.
     */
    public void testDeleteSections_EmptyIds() {
        try {
            persist.deleteSections(new long[] {  });
            fail("testDeleteSections_EmptyIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testDeleteSections_EmptyIds.");
        }
    }

    /**
     * Tests deleteSections(long[] ids) method with zero long[] ids,
     * IllegalArgumentException should be thrown.
     */
    public void testDeleteSections_ZeroIds() {
        try {
            persist.deleteSections(new long[] { 0 });
            fail("testDeleteSections_ZeroIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testDeleteSections_ZeroIds.");
        }
    }

    /**
     * Tests deleteSections(long[] ids) method with negative long[] ids,
     * IllegalArgumentException should be thrown.
     */
    public void testDeleteSections_NegativeIds() {
        try {
            persist.deleteSections(new long[] {  });
            fail("testDeleteSections_NegativeIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testDeleteSections_NegativeIds.");
        }
    }

    /**
     * Tests getSection(long id) method with zero long id, IllegalArgumentException
     * should be thrown.
     */
    public void testGetSection_ZeroId() {
        try {
            persist.getSection(0);
            fail("testGetSection_ZeroId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testGetSection_ZeroId.");
        }
    }

    /**
     * Tests getSection(long id) method with negative long id, IllegalArgumentException
     * should be thrown.
     */
    public void testGetSection_NegativeId() {
        try {
            persist.getSection(-1);
            fail("testGetSection_NegativeId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testGetSection_NegativeId.");
        }
    }
}
