/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.persistence.failuretests;

import com.topcoder.management.scorecard.data.Group;
import com.topcoder.management.scorecard.data.Question;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.Section;
import com.topcoder.management.scorecard.persistence.InformixScorecardPersistence;

import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

import java.io.File;

import java.util.Iterator;


/**
 * Tests for InformixScorecardPersistence class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class InformixScorecardPersistenceFailureTest extends TestCase {
    /** A ConfigManager instance used to test. */
    private ConfigManager cm = ConfigManager.getInstance();

    /** A Scorecard instance used to test. */
    private Scorecard scorecard = null;

    /** InformixScorecardPersistence instance used to test. */
    private InformixScorecardPersistence persist = null;

    /**
     * Setup the test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        clearAllNamespaces();
        cm.add(new File("test_files/failure/failure.xml").getAbsolutePath());
        persist = new InformixScorecardPersistence("persist.failuretest");

        scorecard = new Scorecard(999990, "validator test");

        Question question = new Question(999991, "Is elegant APIs?", 100.0f);
        question.setDescription("here is the first des.");
        question.setGuideline("here is the first guide");

        Section section = new Section(999992, "structure design party.", 100.0f);
        section.addQuestion(question);

        Group group = new Group(999993, "Method.", 100.0f);
        group.addSection(section);

        scorecard.addGroup(group);
        scorecard.setCategory(999994);
        scorecard.setVersion("2.0");
        scorecard.setMinScore(0);
        scorecard.setMaxScore(100.0f);
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
     * Tests InformixScorecardPersistence(String namespace) method with null String
     * namespace, IllegalArgumentException should be thrown.
     */
    public void testInformixScorecardPersistence_NullNamespace() {
        try {
            new InformixScorecardPersistence(null);
            fail("testInformixScorecardPersistence_NullNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail(
                "Unknow exception occurs in testInformixScorecardPersistence_NullNamespace.");
        }
    }

    /**
     * Tests InformixScorecardPersistence(String namespace) method with empty String
     * namespace, IllegalArgumentException should be thrown.
     */
    public void testInformixScorecardPersistence_EmptyNamespace() {
        try {
            new InformixScorecardPersistence(" ");
            fail("testInformixScorecardPersistence_EmptyNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail(
                "Unknow exception occurs in testInformixScorecardPersistence_EmptyNamespace.");
        }
    }

    /**
     * Tests createScorecard(Scorecard scorecard, String operator) method with null
     * Scorecard scorecard, IllegalArgumentException should be thrown.
     */
    public void testCreateScorecard_NullScorecard() {
        try {
            persist.createScorecard(null, "+");
            fail("testCreateScorecard_NullScorecard is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testCreateScorecard_NullScorecard.");
        }
    }

    /**
     * Tests createScorecard(Scorecard scorecard, String operator) method with null
     * String operator, IllegalArgumentException should be thrown.
     */
    public void testCreateScorecard_NullOperator() {
        try {
            persist.createScorecard(scorecard, null);
            fail("testCreateScorecard_NullOperator is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testCreateScorecard_NullOperator.");
        }
    }

    /**
     * Tests createScorecard(Scorecard scorecard, String operator) method with empty
     * String operator, IllegalArgumentException should be thrown.
     */
    public void testCreateScorecard_EmptyOperator() {
        try {
            persist.createScorecard(scorecard, " ");
            fail("testCreateScorecard_EmptyOperator is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testCreateScorecard_EmptyOperator.");
        }
    }

    /**
     * Tests updateScorecard(Scorecard scorecard, String operator) method with null
     * Scorecard scorecard, IllegalArgumentException should be thrown.
     */
    public void testUpdateScorecard_NullScorecard() {
        try {
            persist.updateScorecard(null, "+");
            fail("testUpdateScorecard_NullScorecard is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testUpdateScorecard_NullScorecard.");
        }
    }

    /**
     * Tests updateScorecard(Scorecard scorecard, String operator) method with null
     * String operator, IllegalArgumentException should be thrown.
     */
    public void testUpdateScorecard_NullOperator() {
        try {
            persist.updateScorecard(scorecard, null);
            fail("testUpdateScorecard_NullOperator is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testUpdateScorecard_NullOperator.");
        }
    }

    /**
     * Tests updateScorecard(Scorecard scorecard, String operator) method with empty
     * String operator, IllegalArgumentException should be thrown.
     */
    public void testUpdateScorecard_EmptyOperator() {
        try {
            persist.updateScorecard(scorecard, " ");
            fail("testUpdateScorecard_EmptyOperator is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testUpdateScorecard_EmptyOperator.");
        }
    }

    /**
     * Tests getScorecard(long id, boolean complete) method with zero long id,
     * IllegalArgumentException should be thrown.
     */
    public void testGetScorecard_ZeroId() {
        try {
            persist.getScorecard(0, true);
            fail("testGetScorecard_ZeroId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testGetScorecard_ZeroId.");
        }
    }

    /**
     * Tests getScorecard(long id, boolean complete) method with negative long id,
     * IllegalArgumentException should be thrown.
     */
    public void testGetScorecard_NegativeId() {
        try {
            persist.getScorecard(-1, true);
            fail("testGetScorecard_NegativeId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testGetScorecard_NegativeId.");
        }
    }

    /**
     * Tests getScorecards(long[] ids, boolean complete) method with null long[] ids,
     * IllegalArgumentException should be thrown.
     */
    public void testGetScorecards_NullIds() {
        try {
            persist.getScorecards((long[]) null, true);
            fail("testGetScorecards_NullIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testGetScorecards_NullIds.");
        }
    }

    /**
     * Tests getScorecards(long[] ids, boolean complete) method with empty long[] ids,
     * IllegalArgumentException should be thrown.
     */
    public void testGetScorecards_EmptyIds() {
        try {
            persist.getScorecards(new long[] {  }, true);
            fail("testGetScorecards_EmptyIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testGetScorecards_EmptyIds.");
        }
    }

    /**
     * Tests getScorecards(long[] ids, boolean complete) method with zero long id,
     * IllegalArgumentException should be thrown.
     */
    public void testGetScorecards_ZeroId() {
        try {
            persist.getScorecards(new long[] { 0 }, true);
            fail("testGetScorecards_ZeroId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testGetScorecards_ZeroId.");
        }
    }

    /**
     * Tests getScorecards(long[] ids, boolean complete) method with negative long id,
     * IllegalArgumentException should be thrown.
     */
    public void testGetScorecards_NegativeId() {
        try {
            persist.getScorecards(new long[] { -1 }, true);
            fail("testGetScorecards_NegativeId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testGetScorecards_NegativeId.");
        }
    }
}
