/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.persistence;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.management.scorecard.data.Question;

import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

import java.io.File;

import java.sql.Connection;

import java.util.Iterator;


/**
 * Tests for InformixQuestionPersistence class.
 *
 * @author kr00tki
 * @version 1.0
 */
public class InformixQuestionPersistenceFailureTest extends TestCase {
    /** Question instance used to test. */
    private Question question = null;

    /** InformixQuestionPersistence instance used to test. */
    private InformixQuestionPersistence persist = null;

    /** A ConfigManager instance used to test. */
    private ConfigManager cm = ConfigManager.getInstance();

    /**
     * Setup the test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        question = new Question(999991, 100.0f);
        question.setName("Is elegant APIs?");
        question.setDescription("here is the first des.");
        question.setGuideline("here is the first guide");

        persist = new InformixQuestionPersistence(createConn());
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
     * Tests InformixQuestionPersistence(Connection connection) method with null
     * Connection connection, IllegalArgumentException should be thrown.
     */
    public void testInformixQuestionPersistence_NullConnection() {
        try {
            new InformixQuestionPersistence(null);
            fail("testInformixQuestionPersistence_NullConnection is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail(
                "Unknow exception occurs in testInformixQuestionPersistence_NullConnection.");
        }
    }

    /**
     * Tests createQuestion(Question question, int order, String operator, long parentId)
     * method with null Question question, IllegalArgumentException should be thrown.
     */
    public void testCreateQuestion_NullQuestion() {
        try {
            persist.createQuestion(null, 1, "+", 123450);
            fail("testCreateQuestion_NullQuestion is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testCreateQuestion_NullQuestion.");
        }
    }

    /**
     * Tests createQuestion(Question question, int order, String operator, long parentId)
     * method with null String operator, IllegalArgumentException should be thrown.
     */
    public void testCreateQuestion_NullOperator() {
        try {
            persist.createQuestion(question, 1, null, 123450);
            fail("testCreateQuestion_NullOperator is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testCreateQuestion_NullOperator.");
        }
    }

    /**
     * Tests createQuestion(Question question, int order, String operator, long parentId)
     * method with empty String operator, IllegalArgumentException should be thrown.
     */
    public void testCreateQuestion_EmptyOperator() {
        try {
            persist.createQuestion(question, 1, " ", 123450);
            fail("testCreateQuestion_EmptyOperator is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testCreateQuestion_EmptyOperator.");
        }
    }

    /**
     * Tests updateQuestion(Question question, int order, String operator, long parentId)
     * method with null Question question, IllegalArgumentException should be thrown.
     */
    public void testUpdateQuestion_NullQuestion() {
        try {
            persist.updateQuestion(null, 1, "+", 123450);
            fail("testUpdateQuestion_NullQuestion is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testUpdateQuestion_NullQuestion.");
        }
    }

    /**
     * Tests updateQuestion(Question question, int order, String operator, long parentId)
     * method with null String operator, IllegalArgumentException should be thrown.
     */
    public void testUpdateQuestion_NullOperator() {
        try {
            persist.updateQuestion(question, 1, null, 123430);
            fail("testUpdateQuestion_NullOperator is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testUpdateQuestion_NullOperator.");
        }
    }

    /**
     * Tests updateQuestion(Question question, int order, String operator, long parentId)
     * method with empty String operator, IllegalArgumentException should be thrown.
     */
    public void testUpdateQuestion_EmptyOperator() {
        try {
            persist.updateQuestion(question, 1, " ", 123450);
            fail("testUpdateQuestion_EmptyOperator is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testUpdateQuestion_EmptyOperator.");
        }
    }

    /**
     * Tests deleteQuestions(long[] ids) method with null long[] ids,
     * IllegalArgumentException should be thrown.
     */
    public void testDeleteQuestions_NullIds() {
        try {
            persist.deleteQuestions(null);
            fail("testDeleteQuestions_NullIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testDeleteQuestions_NullIds.");
        }
    }

    /**
     * Tests deleteQuestions(long[] ids) method with empty long[] ids,
     * IllegalArgumentException should be thrown.
     */
    public void testDeleteQuestions_EmptyIds() {
        try {
            persist.deleteQuestions(new long[] {  });
            fail("testDeleteQuestions_EmptyIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testDeleteQuestions_EmptyIds.");
        }
    }

    /**
     * Tests deleteQuestions(long[] ids) method with zero long[] ids,
     * IllegalArgumentException should be thrown.
     */
    public void testDeleteQuestions_ZeroIds() {
        try {
            persist.deleteQuestions(new long[] { 0 });
            fail("testDeleteQuestions_ZeroIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testDeleteQuestions_ZeroIds.");
        }
    }

    /**
     * Tests deleteQuestions(long[] ids) method with negative long[] ids,
     * IllegalArgumentException should be thrown.
     */
    public void testDeleteQuestions_NegativeIds() {
        try {
            persist.deleteQuestions(new long[] { -1 });
            fail("testDeleteQuestions_NegativeIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testDeleteQuestions_NegativeIds.");
        }
    }

    /**
     * Tests getQuestion(long id) method with zero long id, IllegalArgumentException
     * should be thrown.
     */
    public void testGetQuestion_ZeroId() {
        try {
            persist.getQuestion(0);
            fail("testGetQuestion_ZeroId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testGetQuestion_ZeroId.");
        }
    }

    /**
     * Tests getQuestion(long id) method with negative long id, IllegalArgumentException
     * should be thrown.
     */
    public void testGetQuestion_NegativeId() {
        try {
            persist.getQuestion(-1);
            fail("testGetQuestion_NegativeId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testGetQuestion_NegativeId.");
        }
    }
}
