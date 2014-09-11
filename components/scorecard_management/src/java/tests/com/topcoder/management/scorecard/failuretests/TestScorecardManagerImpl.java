/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.failuretests;

import com.topcoder.management.scorecard.ConfigurationException;
import com.topcoder.management.scorecard.ScorecardManagerImpl;
import com.topcoder.management.scorecard.data.Group;
import com.topcoder.management.scorecard.data.Question;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.ScorecardStatus;
import com.topcoder.management.scorecard.data.ScorecardType;
import com.topcoder.management.scorecard.data.Section;

import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

import java.io.File;

import java.util.Iterator;


/**
 * Tests for ScorecardManagerImpl class.
 *
 * @author qiucx0161
 * @version 1.0
 */
public class TestScorecardManagerImpl extends TestCase {
    /** A ConfigManager used for test. */
    private ConfigManager cm = ConfigManager.getInstance();

    /** A ScorecardManagerImpl used for test. */
    private ScorecardManagerImpl manager = null;

    /** A Scorecard used for test. */
    private Scorecard scorecard = null;

    /**
     * Setup the test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        clearNamespaces();
        cm.add(new File("test_files/failure/failure.xml").getAbsolutePath());
        manager = new ScorecardManagerImpl("manager.failuretest");

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
        scorecard.setScorecardStatus(new ScorecardStatus(999995, "incompleted."));
        scorecard.setScorecardType(new ScorecardType(999996, "design"));
    }

    /**
     * Setup the test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        clearNamespaces();
    }

    /**
     * Clear the namespaces used in this component
     */
    private void clearNamespaces() {
        try {
            for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
                cm.removeNamespace((String) iter.next());
            }
        } catch (Exception e) {
            // ignore.
        }
    }

    /**
     * Tests ScorecardManagerImpl(String ns) method with null String ns,
     * IllegalArgumentException should be thrown.
     */
    public void testScorecardManagerImpl_NullNs() {
        try {
            new ScorecardManagerImpl(null);
            fail("testScorecardManagerImpl_NullNs is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testScorecardManagerImpl_NullNs.");
        }
    }

    /**
     * Tests ScorecardManagerImpl(String ns) method with empty String ns,
     * IllegalArgumentException should be thrown.
     */
    public void testScorecardManagerImpl_EmptyNs() {
        try {
            new ScorecardManagerImpl(" ");
            fail("testScorecardManagerImpl_EmptyNs is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testScorecardManagerImpl_EmptyNs.");
        }
    }

    /**
     * Tests ScorecardManagerImpl(String ns) method with missing SearchBuilderNamespace,
     * ConfigurationException should be thrown.
     */
    public void testScorecardManagerImpl_MissingSearchBuilderNamespace() {
        try {
            new ScorecardManagerImpl("manager.failuretest.missingSearchBuilderNamespace");
            fail("testScorecardManagerImpl_MissingSearchBuilderNamespace is failure.");
        } catch (ConfigurationException ce) {
            // pass
        } catch (Exception e) {
            fail(
                "Unknow exception occurs in testScorecardManagerImpl_MissingSearchBuilderNamespace.");
        }
    }

    /**
     * Tests ScorecardManagerImpl(String ns) method with invalid SearchBuilderNamespace,
     * ConfigurationException should be thrown.
     */
    public void testScorecardManagerImpl_InvalidSearchBuilderNamespace() {
        try {
            new ScorecardManagerImpl("manager.failuretest.InvalidSearchBuilderNamespace");
            fail("testScorecardManagerImpl_InvalidSearchBuilderNamespace is failure.");
        } catch (ConfigurationException ce) {
            // pass
        } catch (Exception e) {
            fail(
                "Unknow exception occurs in testScorecardManagerImpl_InvalidSearchBuilderNamespace.");
        }
    }

    /**
     * Tests ScorecardManagerImpl(String ns) method with missing PersistenceClass,
     * ConfigurationException should be thrown.
     */
    public void testScorecardManagerImpl_MissingPersistenceClass() {
        try {
            new ScorecardManagerImpl("manager.failuretest.missingPersistenceClass");
            fail("testScorecardManagerImpl_MissingPersistenceClass is failure.");
        } catch (ConfigurationException ce) {
            // pass
        } catch (Exception e) {
            fail(
                "Unknow exception occurs in testScorecardManagerImpl_MissingPersistenceClass.");
        }
    }

    /**
     * Tests ScorecardManagerImpl(String ns) method with missing ValidatorClass,
     * ConfigurationException should be thrown.
     */
    public void testScorecardManagerImpl_MissingValidatorClass() {
        try {
            new ScorecardManagerImpl("manager.failuretest.missingValidatorClass");
            fail("testScorecardManagerImpl_MissingValidatorClass is failure.");
        } catch (ConfigurationException ce) {
            // pass
        } catch (Exception e) {
            fail(
                "Unknow exception occurs in testScorecardManagerImpl_MissingValidatorClass.");
        }
    }

    /**
     * Tests createScorecard(Scorecard scorecard, String operator) method with null
     * Scorecard scorecard, IllegalArgumentException should be thrown.
     */
    public void testCreateScorecard_NullScorecard() {
        try {
            manager.createScorecard(null, "qiucx0161");
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
            manager.createScorecard(scorecard, null);
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
            manager.createScorecard(scorecard, " ");
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
            manager.updateScorecard(null, "qiucx0161");
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
            manager.updateScorecard(scorecard, null);
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
            manager.updateScorecard(scorecard, " ");
            fail("testUpdateScorecard_EmptyOperator is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testUpdateScorecard_EmptyOperator.");
        }
    }

    /**
     * Tests getScorecard(long id) method with zero long id, IllegalArgumentException
     * should be thrown.
     */
    public void testGetScorecard_ZeroId() {
        try {
            manager.getScorecard(0);
            fail("testGetScorecard_ZeroId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testGetScorecard_ZeroId.");
        }
    }

    /**
     * Tests getScorecard(long id) method with negative long id, IllegalArgumentException
     * should be thrown.
     */
    public void testGetScorecard_NegativeId() {
        try {
            manager.getScorecard(0);
            fail("testGetScorecard_NegativeId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testGetScorecard_NegativeId.");
        }
    }

    /**
     * Tests searchScorecards(Filter filter, boolean complete) method with null Filter
     * filter, IllegalArgumentException should be thrown.
     */
    public void testSearchScorecards_NullFilter() {
        try {
            manager.searchScorecards(null, false);
            fail("testSearchScorecards_NullFilter is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testSearchScorecards_NullFilter.");
        }
    }

    /**
     * Tests getScorecards(long[] ids, boolean complete) method with null long[] ids,
     * IllegalArgumentException should be thrown.
     */
    public void testGetScorecards_NullIds() {
        try {
            manager.getScorecards(null, false);
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
            manager.getScorecards(new long[] {  }, false);
            fail("testGetScorecards_EmptyIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testGetScorecards_EmptyIds.");
        }
    }

    /**
     * Tests getScorecards(long[] ids, boolean complete) method with zero element in
     * long[] ids, IllegalArgumentException should be thrown.
     */
    public void testGetScorecards_ZeroElementInIds() {
        try {
            manager.getScorecards(new long[] { 0 }, false);
            fail("testGetScorecards_ZeroElementInIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testGetScorecards_ZeroElementInIds.");
        }
    }

    /**
     * Tests getScorecards(long[] ids, boolean complete) method with negaive element in
     * long[] ids, IllegalArgumentException should be thrown.
     */
    public void testGetScorecards_NegativeElementInIds() {
        try {
            manager.getScorecards(new long[] { 0 }, false);
            fail("testGetScorecards_NegativeElementInIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testGetScorecards_NegativeElementInIds.");
        }
    }
}
