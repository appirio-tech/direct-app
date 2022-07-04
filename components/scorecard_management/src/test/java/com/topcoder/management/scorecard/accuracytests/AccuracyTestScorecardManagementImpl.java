/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.accuracytests;

import com.topcoder.management.scorecard.ScorecardManager;
import com.topcoder.management.scorecard.ScorecardManagerImpl;
import com.topcoder.management.scorecard.ScorecardSearchBundle;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.ScorecardStatus;
import com.topcoder.management.scorecard.data.ScorecardType;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.util.config.ConfigManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Iterator;

/**
 * Unit test for <code>ScorecardManagementImpl</code>.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestScorecardManagementImpl extends TestCase {
    /** The name of the operator used for accuracy test. */
    private static final String USER_NAME = "foo";

    /** The namespace used for accuracy test. */
    private static final String NAMESPACE = "com.topcoder.management.scorecard.accuracytest";

    /** The values of the parameters used to validate the calling process. */
    private static DummyParameters parameters = null;

    /** The instance of ScorecardManagerImpl used for test. */
    private ScorecardManager manager = null;

    /** The instance of Scorecard used for test. */
    private Scorecard card = null;

    /**
     * Assign the parameters.
     * @param parameters
     *            the parameters to assign.
     */
    public static void setParameters(DummyParameters parameters) {
        AccuracyTestScorecardManagementImpl.parameters = parameters;
    }

    /**
     * See javadoc for junit.framework.TestCase#setUp().
     * @throws Exception
     *             exception that occurs during the setup process.
     * @see junit.framework.TestCase#tearDown()
     */
    public void setUp() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        Iterator iter = cm.getAllNamespaces();

        while (iter.hasNext()) {
            cm.removeNamespace((String) iter.next());
        }

        cm.add("accuracytests/config.xml");

        AccuracyTestHelper.clearAllTables();

        manager = new ScorecardManagerImpl();
        card = AccuracyTestHelper.createScorecard(new ScorecardStatus(1), new ScorecardType(1), "test card", "1.0",
            1, 0.0f, 100.0f);

        parameters = null;
    }

    /**
     * See javadoc for junit.framework.TestCase#tearDown().
     * @throws Exception
     *             exception that occurs during the tear down process.
     * @see junit.framework.TestCase#setUp()
     */
    public void tearDown() throws Exception {
        card = null;

        manager = null;

        AccuracyTestHelper.clearAllTables();

        ConfigManager cm = ConfigManager.getInstance();

        Iterator iter = cm.getAllNamespaces();

        while (iter.hasNext()) {
            cm.removeNamespace((String) iter.next());
        }
    }

    /**
     * Returns the suit to run the test.
     * @return suite to run the test
     */
    public static Test suite() {
        return new TestSuite(AccuracyTestScorecardManagementImpl.class);
    }

    /**
     * Tests ScorecardManagerImpl().
     * @throws Exception
     *             to JUnit.
     */
    public void testConstructor1() throws Exception {
        assertNotNull("invalid ScorecardManagerImpl instance", manager);
    }

    /**
     * Tests ScorecardManagerImpl(String ns).
     * @throws Exception
     *             to JUnit.
     */
    public void testConstructor2() throws Exception {
        manager = new ScorecardManagerImpl(NAMESPACE);

        assertNotNull("invalid ScorecardManagerImpl instanc", manager);
    }

    /**
     * Tests createScorecard(Scorecard card, String operator).
     * @throws Exception
     *             to JUnit.
     */
    public void testCreateScorecard() throws Exception {
        manager.createScorecard(card, USER_NAME);

        assertTrue("invalid card", parameters.getScorecard().equals(card));
        assertTrue("invalid operator", parameters.getOperator().equals(USER_NAME));
        assertTrue("invalid method name", parameters.getMethodName().equals("createScorecard"));
    }

    /**
     * Tests updateScorecard(Scorecard card, String operator).
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateScorecard() throws Exception {
        manager.createScorecard(card, USER_NAME);

        assertTrue("invalid card", parameters.getScorecard().equals(card));
        assertTrue("invalid operator", parameters.getOperator().equals(USER_NAME));
        assertTrue("invalid method name", parameters.getMethodName().equals("createScorecard"));
    }

    /**
     * Tests getScorecard(long id).
     * @throws Exception
     *             to JUnit.
     */
    public void testGetScorecard() throws Exception {
        manager.getScorecard(1000);

        assertTrue("invalid id", parameters.getId() == 1000);
        assertTrue("invalid method name", parameters.getMethodName().equals("getScorecard"));
    }

    /**
     * Tests searchScorecards(Filter filter, boolean complete) with false complete flag.
     * @throws Exception
     *             to JUnit.
     */
    public void testSearchScorecards1() throws Exception {
        Scorecard card1 = AccuracyTestHelper.createScorecard(new ScorecardStatus(3, "status1"), new ScorecardType(1,
            "type1"), "test card 1", "1.0", 1, 0.0f, 100.0f);
        Scorecard card2 = AccuracyTestHelper.createScorecard(new ScorecardStatus(2, "status2"), new ScorecardType(2,
            "type2"), "test card 2", "1.0", 2, 0.0f, 100.0f);
        Scorecard card3 = AccuracyTestHelper.createScorecard(new ScorecardStatus(1, "status3"), new ScorecardType(3,
            "type3"), "test card 3", "1.0", 3, 0.0f, 100.0f);

        card1.setId(1);
        card2.setId(2);
        card3.setId(3);

        AccuracyTestHelper.insertScorecard(card1, USER_NAME);
        AccuracyTestHelper.insertScorecard(card2, USER_NAME);
        AccuracyTestHelper.insertScorecard(card3, USER_NAME);

        Filter filter = ScorecardSearchBundle.buildStatusIdEqualFilter(1);

        manager.searchScorecards(filter, true);
    }

    /**
     * Tests searchScorecards(Filter filter, boolean complete) with true complete flag.
     * @throws Exception
     *             to JUnit.
     */
    public void testSearchScorecards2() throws Exception {
        Scorecard card1 = AccuracyTestHelper.createScorecard(new ScorecardStatus(3, "status1"), new ScorecardType(1,
            "type1"), "test card 1", "1.0", 1, 0.0f, 100.0f);
        Scorecard card2 = AccuracyTestHelper.createScorecard(new ScorecardStatus(2, "status2"), new ScorecardType(2,
            "type2"), "test card 2", "1.0", 2, 0.0f, 100.0f);
        Scorecard card3 = AccuracyTestHelper.createScorecard(new ScorecardStatus(1, "status3"), new ScorecardType(3,
            "type3"), "test card 3", "1.0", 3, 0.0f, 100.0f);

        Filter filter = ScorecardSearchBundle.buildNameEqualFilter("test card 2");

        card1.setId(1);
        card2.setId(2);
        card3.setId(3);

        AccuracyTestHelper.insertScorecard(card1, USER_NAME);
        AccuracyTestHelper.insertScorecard(card2, USER_NAME);
        AccuracyTestHelper.insertScorecard(card3, USER_NAME);

        manager.searchScorecards(filter, false);
    }

    /**
     * Tests searchScorecards(Filter filter, boolean complete).
     * @throws Exception
     *             to JUnit.
     */
    public void testSearchScorecards3() throws Exception {
        Scorecard card1 = AccuracyTestHelper.createScorecard(new ScorecardStatus(3, "status1"), new ScorecardType(1,
            "type1"), "test card 1", "1.0", 1, 0.0f, 100.0f);
        Scorecard card2 = AccuracyTestHelper.createScorecard(new ScorecardStatus(2, "status2"), new ScorecardType(2,
            "type2"), "test card 2", "1.0", 2, 0.0f, 100.0f);
        Scorecard card3 = AccuracyTestHelper.createScorecard(new ScorecardStatus(1, "status3"), new ScorecardType(3,
            "type3"), "test card 3", "1.0", 3, 0.0f, 100.0f);

        Filter filter = ScorecardSearchBundle.buildNameEqualFilter("test card 4");

        card1.setId(1);
        card2.setId(2);
        card3.setId(3);

        AccuracyTestHelper.insertScorecard(card1, USER_NAME);
        AccuracyTestHelper.insertScorecard(card2, USER_NAME);
        AccuracyTestHelper.insertScorecard(card3, USER_NAME);

        Scorecard[] cards = manager.searchScorecards(filter, false);

        assertEquals("No scorecard should be retrieved", 0, cards.length);
    }

    /**
     * Tests getAllScorecardTypes().
     * @throws Exception
     *             to JUnit.
     */
    public void testGetAllScorecardTypes() throws Exception {
        manager.getAllScorecardTypes();

        assertTrue("invalid method name", parameters.getMethodName().equals("getAllScorecardTypes"));
    }

    /**
     * Tests getAllQuestionTypes().
     * @throws Exception
     *             DOCUMENT ME!
     */
    public void testGetAllQuestionTypes() throws Exception {
        manager.getAllQuestionTypes();

        assertTrue("invalid method name", parameters.getMethodName().equals("getAllQuestionTypes"));
    }

    /**
     * Tests getAllScorecardStatuses().
     * @throws Exception
     *             to JUnit.
     */
    public void testGetAllScorecardStatuses() throws Exception {
        manager.getAllScorecardStatuses();

        assertTrue("invalid method name", parameters.getMethodName().equals("getAllScorecardStatuses"));
    }

    /**
     * Tests getScorecards(long[] ids, boolean complete).
     * @throws Exception
     *             to JUnit.
     */
    public void testGetScorecards1() throws Exception {
        long[] ids = new long[] {1000, 2000};

        manager.getScorecards(ids, false);

        assertTrue("invalid id array", parameters.getIds().equals(ids));
        assertFalse("invalid operator", parameters.isComplete());
        assertTrue("invalid method name", parameters.getMethodName().equals("getScorecards"));
    }

    /**
     * Tests getScorecards(long[] ids, boolean complete).
     * @throws Exception
     *             to JUnit.
     */
    public void testGetScorecards2() throws Exception {
        long[] ids = new long[] {1000, 2000};

        manager.getScorecards(ids, true);

        assertTrue("invalid id array", parameters.getIds().equals(ids));
        assertTrue("invalid operator", parameters.isComplete());
        assertTrue("invalid method name", parameters.getMethodName().equals("getScorecards"));
    }
}
