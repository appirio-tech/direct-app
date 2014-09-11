/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.persistence;

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.review.ConfigurationException;
import com.topcoder.management.review.DuplicateReviewEntityException;
import com.topcoder.management.review.ReviewEntityNotFoundException;
import com.topcoder.management.review.ReviewPersistence;
import com.topcoder.management.review.ReviewPersistenceException;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.CommentType;
import com.topcoder.management.review.data.Item;
import com.topcoder.management.review.data.Review;
import com.topcoder.management.review.persistence.Helper.DataType;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.util.config.ConfigManager;

/**
 * Accuracy and failure tests for <code>InformixReviewPersistence</code>.
 * @author urtks
 * @version 1.0
 */
public class InformixReviewPersistenceTest extends TestCase {
    /**
     * Aggregates all tests in this class.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(InformixReviewPersistenceTest.class);
    }

    /**
     * Get a sample review object to test.
     * @return a sample review object
     */
    private Review getSampleReview() {
        CommentType commentType = new CommentType();
        commentType.setId(1);
        commentType.setName("type 1");

        Review review = new Review();
        review.setAuthor(1);
        review.setSubmission(2);
        review.setScorecard(3);
        review.setScore(new Float("98.5"));
        review.setCommitted(true);

        // add review comments
        Comment comment = new Comment();
        comment.setAuthor(1);
        comment.setCommentType(commentType);
        comment.setComment("comment 1");
        review.addComment(comment);

        comment = new Comment();
        comment.setAuthor(2);
        comment.setCommentType(commentType);
        comment.setComment("comment 2");
        comment.setExtraInfo("extra info");
        review.addComment(comment);

        // add items
        Item item = new Item();
        item.setQuestion(1);
        item.setAnswer("answer 1");

        comment = new Comment();
        comment.setAuthor(3);
        comment.setCommentType(commentType);
        comment.setComment("comment 3");
        item.addComment(comment);

        comment = new Comment();
        comment.setAuthor(4);
        comment.setCommentType(commentType);
        comment.setComment("comment 4");
        comment.setExtraInfo("extra info");
        item.addComment(comment);

        review.addItem(item);

        // add items
        item = new Item();
        item.setQuestion(2);
        item.setAnswer("answer 2");

        comment = new Comment();
        comment.setAuthor(5);
        comment.setCommentType(commentType);
        comment.setComment("comment 5");
        item.addComment(comment);

        comment = new Comment();
        comment.setAuthor(6);
        comment.setCommentType(commentType);
        comment.setComment("comment 6");
        comment.setExtraInfo("extra info");
        item.addComment(comment);

        review.addItem(item);

        return review;
    }

    /**
     * Sets up the test environment. The configuration will be loaded.
     * @throws Exception
     *             throw any exception to JUnit
     */
    protected void setUp() throws Exception {
        tearDown();

        ConfigManager cm = ConfigManager.getInstance();

        // load the configurations for db connection factory
        cm.add("dbfactory.xml");

        // load the configurations for search bundle manager
        cm.add("search_bundle_manager.xml");

        // load the configurations for InformixReviewPersistence
        cm.add("informix_persistence.xml");

        // create the connection
        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();

        // insert data into comment_type_lu table
        Helper.doDMLQuery(conn, "INSERT INTO comment_type_lu"
            + " (comment_type_id, name, description, create_user,"
            + " create_date, modify_user, modify_date) values"
            + "(1, 'type 1', 'comment type 1', 'topcoder', CURRENT, 'topcoder', CURRENT)",
            new Object[] {});
        Helper.doDMLQuery(conn, "INSERT INTO comment_type_lu"
            + " (comment_type_id, name, description, create_user,"
            + " create_date, modify_user, modify_date) values"
            + "(2, 'type 2', 'comment type 2', 'topcoder', CURRENT, 'topcoder', CURRENT)",
            new Object[] {});
        Helper.doDMLQuery(conn, "INSERT INTO comment_type_lu"
            + " (comment_type_id, name, description, create_user,"
            + " create_date, modify_user, modify_date) values"
            + "(3, 'type 3', 'comment type 3', 'topcoder', CURRENT, 'topcoder', CURRENT)",
            new Object[] {});

        // insert data into resource table
        Helper.doDMLQuery(conn, "INSERT INTO resource (resource_id, project_id) values (1,1)",
            new Object[] {});
        Helper.doDMLQuery(conn, "INSERT INTO resource (resource_id, project_id) values (2,1)",
            new Object[] {});
        Helper.doDMLQuery(conn, "INSERT INTO resource (resource_id, project_id) values (3,1)",
            new Object[] {});
        Helper.doDMLQuery(conn, "INSERT INTO resource (resource_id) values (4)", new Object[] {});
        Helper.doDMLQuery(conn, "INSERT INTO resource (resource_id) values (5)", new Object[] {});
        Helper.doDMLQuery(conn, "INSERT INTO resource (resource_id) values (6)", new Object[] {});

        // insert data into submission table
        Helper.doDMLQuery(conn, "INSERT INTO submission (submission_id) values (1)",
            new Object[] {});
        Helper.doDMLQuery(conn, "INSERT INTO submission (submission_id) values (2)",
            new Object[] {});
        Helper.doDMLQuery(conn, "INSERT INTO submission (submission_id) values (3)",
            new Object[] {});

        // insert data into upload table
        Helper.doDMLQuery(conn, "INSERT INTO upload (upload_id) values (1)", new Object[] {});
        Helper.doDMLQuery(conn, "INSERT INTO upload (upload_id) values (2)", new Object[] {});
        Helper.doDMLQuery(conn, "INSERT INTO upload (upload_id) values (3)", new Object[] {});

        // insert data into scorecard_question table
        Helper.doDMLQuery(conn,
            "INSERT INTO scorecard_question (scorecard_question_id) values (1)", new Object[] {});
        Helper.doDMLQuery(conn,
            "INSERT INTO scorecard_question (scorecard_question_id) values (2)", new Object[] {});
        Helper.doDMLQuery(conn,
            "INSERT INTO scorecard_question (scorecard_question_id) values (3)", new Object[] {});

        // insert data into scorecard_type_lu table
        Helper.doDMLQuery(conn, "INSERT INTO scorecard_type_lu (scorecard_type_id) values (1)",
            new Object[] {});
        Helper.doDMLQuery(conn, "INSERT INTO scorecard_type_lu (scorecard_type_id) values (2)",
            new Object[] {});
        Helper.doDMLQuery(conn, "INSERT INTO scorecard_type_lu (scorecard_type_id) values (3)",
            new Object[] {});

        // insert data into scorecard table
        Helper.doDMLQuery(conn,
            "INSERT INTO scorecard (scorecard_id, scorecard_type_id) values (1, 1)",
            new Object[] {});
        Helper.doDMLQuery(conn,
            "INSERT INTO scorecard (scorecard_id, scorecard_type_id) values (2, 2)",
            new Object[] {});
        Helper.doDMLQuery(conn,
            "INSERT INTO scorecard (scorecard_id, scorecard_type_id) values (3, 1)",
            new Object[] {});
        Helper.doDMLQuery(conn,
            "INSERT INTO scorecard (scorecard_id, scorecard_type_id) values (4, 2)",
            new Object[] {});

        conn.close();
    }

    /**
     * Clean up the test environment. The configuration will be unloaded.
     * @throws Exception
     *             throw any exception to JUnit
     */
    protected void tearDown() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        Iterator it = cm.getAllNamespaces();
        while (it.hasNext()) {
            cm.removeNamespace((String) it.next());
        }

        // load the configurations for db connection factory
        cm.add("dbfactory.xml");

        // create the connection
        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();

        // clear the tables
        Helper.doDMLQuery(conn, "DELETE FROM review_item_comment", new Object[] {});
        Helper.doDMLQuery(conn, "DELETE FROM review_item", new Object[] {});
        Helper.doDMLQuery(conn, "DELETE FROM review_comment", new Object[] {});
        Helper.doDMLQuery(conn, "DELETE FROM review", new Object[] {});
        Helper.doDMLQuery(conn, "DELETE FROM comment_type_lu", new Object[] {});
        Helper.doDMLQuery(conn, "DELETE FROM resource", new Object[] {});
        Helper.doDMLQuery(conn, "DELETE FROM submission", new Object[] {});
        Helper.doDMLQuery(conn, "DELETE FROM upload", new Object[] {});
        Helper.doDMLQuery(conn, "DELETE FROM scorecard", new Object[] {});
        Helper.doDMLQuery(conn, "DELETE FROM scorecard_type_lu", new Object[] {});
        Helper.doDMLQuery(conn, "DELETE FROM scorecard_question", new Object[] {});
        Helper.doDMLQuery(conn, "DELETE FROM id_sequences", new Object[] {});

        // insert data into id_sequences table
        Helper.doDMLQuery(conn, "INSERT INTO id_sequences(name, next_block_start, "
            + "block_size, exhausted) VALUES('review_id_seq', 1, 1, 0)", new Object[] {});
        Helper.doDMLQuery(conn, "INSERT INTO id_sequences(name, next_block_start, "
            + "block_size, exhausted) VALUES('review_item_id_seq', 1, 1, 0);", new Object[] {});
        Helper.doDMLQuery(conn, "INSERT INTO id_sequences(name, next_block_start, "
            + "block_size, exhausted) VALUES('review_comment_id_seq', 1, 1, 0);", new Object[] {});
        Helper.doDMLQuery(conn, "INSERT INTO id_sequences(name, next_block_start, "
            + "block_size, exhausted) VALUES('review_item_comment_id_seq', 1, 1, 0); ",
            new Object[] {});

        conn.close();

        it = cm.getAllNamespaces();
        while (it.hasNext()) {
            cm.removeNamespace((String) it.next());
        }
    }

    /**
     * <p>
     * Accuracy test of the constructor
     * <code>InformixReviewPersistence(DBConnectionFactory dbFactory,
     * String connectionName, SearchBundle searchBundle)</code>.
     * </p>
     * <p>
     * An instance of InformixReviewPersistence can be created.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyConstructorA1() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence(
            new DBConnectionFactoryImpl(), "InformixConnection", new SearchBundle("bundle",
                new HashMap(), "context"));

        assertNotNull("Unable to create InformixReviewPersistence.", persistence);
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>InformixReviewPersistence(DBConnectionFactory dbFactory,
     * String connectionName, SearchBundle searchBundle)</code>.
     * </p>
     * <p>
     * dbFactory is null.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureConstructorA1() throws Exception {
        try {
            new InformixReviewPersistence(null, "InformixConnection", new SearchBundle("bundle",
                new HashMap(), "context"));
        } catch (IllegalArgumentException e) {
            assertEquals("dbFactory should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>InformixReviewPersistence(DBConnectionFactory dbFactory,
     * String connectionName, SearchBundle searchBundle)</code>.
     * </p>
     * <p>
     * connectionName is null.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureConstructorA2() throws Exception {
        try {
            new InformixReviewPersistence(new DBConnectionFactoryImpl(), null, new SearchBundle(
                "bundle", new HashMap(), "context"));
        } catch (IllegalArgumentException e) {
            assertEquals("connectionName should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>InformixReviewPersistence(DBConnectionFactory dbFactory,
     * String connectionName, SearchBundle searchBundle)</code>.
     * </p>
     * <p>
     * connectionName is empty (trimmed).
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureConstructorA3() throws Exception {
        try {
            new InformixReviewPersistence(new DBConnectionFactoryImpl(), "    ", new SearchBundle(
                "bundle", new HashMap(), "context"));
        } catch (IllegalArgumentException e) {
            assertEquals("connectionName should not be empty (trimmed).", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>InformixReviewPersistence(DBConnectionFactory dbFactory,
     * String connectionName, SearchBundle searchBundle)</code>.
     * </p>
     * <p>
     * searchBundle is null.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureConstructorA4() throws Exception {
        try {
            new InformixReviewPersistence(new DBConnectionFactoryImpl(), "conn", null);
        } catch (IllegalArgumentException e) {
            assertEquals("searchBundle should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the constructor <code>InformixReviewPersistence()</code>.
     * </p>
     * <p>
     * An instance of InformixReviewPersistence can be created.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyConstructorB1() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        assertNotNull("Unable to create InformixReviewPersistence.", persistence);
    }

    /**
     * <p>
     * Failure test of the constructor <code>InformixReviewPersistence()</code>.
     * </p>
     * <p>
     * the default namespace doesn't exist.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureConstructorB1() throws Exception {
        ConfigManager.getInstance().removeNamespace(InformixReviewPersistence.class.getName());
        try {
            new InformixReviewPersistence();
        } catch (ConfigurationException e) {
            assertEquals("Configuration namespace [com.topcoder.management.review."
                + "persistence.InformixReviewPersistence] does not exist.,"
                + " caused by com.topcoder.management.review.persistence.In"
                + "formixReviewPersistence is unknown", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the constructor
     * <code>InformixReviewPersistence(String namespace)</code>.
     * </p>
     * <p>
     * An instance of InformixReviewPersistence can be created.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyConstructorC1() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence(
            "InformixReviewPersistence.CustomNamespace");

        assertNotNull("Unable to create InformixReviewPersistence.", persistence);
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>InformixReviewPersistence(String namespace)</code>.
     * </p>
     * <p>
     * namespace is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureConstructorC1() throws Exception {
        try {
            new InformixReviewPersistence(null);
        } catch (IllegalArgumentException e) {
            assertEquals("namespace should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>InformixReviewPersistence(String namespace)</code>.
     * </p>
     * <p>
     * namespace is empty (trimmed). IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureConstructorC2() throws Exception {
        try {
            new InformixReviewPersistence("    ");
        } catch (IllegalArgumentException e) {
            assertEquals("namespace should not be empty (trimmed).", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>InformixReviewPersistence(String namespace)</code>.
     * </p>
     * <p>
     * namespace doesn't exist. ConfigurationException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureConstructorC3() throws Exception {
        try {
            new InformixReviewPersistence("do_not_exist");
        } catch (ConfigurationException e) {
            assertEquals("Configuration namespace [do_not_exist] does not exist.,"
                + " caused by do_not_exist is unknown", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>InformixReviewPersistence(String namespace)</code>.
     * </p>
     * <p>
     * connection name is not provided. ConfigurationException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureConstructorC4() throws Exception {
        try {
            new InformixReviewPersistence(
                "InformixReviewPersistence.CustomNamespace.NoConnectionName");
        } catch (ConfigurationException e) {
            assertEquals("Configuration parameter [connection.name] under"
                + " namespace [InformixReviewPersistence.CustomNamesp"
                + "ace.NoConnectionName] is not specified.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>InformixReviewPersistence(String namespace)</code>.
     * </p>
     * <p>
     * connection name is empty (trimmed). ConfigurationException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureConstructorC5() throws Exception {
        try {
            new InformixReviewPersistence(
                "InformixReviewPersistence.CustomNamespace.EmptyConnectionName");
        } catch (ConfigurationException e) {
            assertEquals("Configuration parameter [connection.name] under"
                + " namespace [InformixReviewPersistence.CustomNamesp"
                + "ace.EmptyConnectionName] is empty (trimmed).", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>InformixReviewPersistence(String namespace)</code>.
     * </p>
     * <p>
     * factory_namespace is not provided. ConfigurationException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureConstructorC6() throws Exception {
        try {
            new InformixReviewPersistence(
                "InformixReviewPersistence.CustomNamespace.NoFactoryNamespace");
        } catch (ConfigurationException e) {
            assertEquals("Configuration parameter [connection.factory_namespace] under"
                + " namespace [InformixReviewPersistence.CustomNamesp"
                + "ace.NoFactoryNamespace] is not specified.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>InformixReviewPersistence(String namespace)</code>.
     * </p>
     * <p>
     * factory_namespace is empty (trimmed). ConfigurationException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureConstructorC7() throws Exception {
        try {
            new InformixReviewPersistence(
                "InformixReviewPersistence.CustomNamespace.EmptyFactoryNamespace");
        } catch (ConfigurationException e) {
            assertEquals("Configuration parameter [connection.factory_namespace] under"
                + " namespace [InformixReviewPersistence.CustomNamesp"
                + "ace.EmptyFactoryNamespace] is empty (trimmed).", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>InformixReviewPersistence(String namespace)</code>.
     * </p>
     * <p>
     * factory_class is not provided. ConfigurationException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureConstructorC8() throws Exception {
        try {
            new InformixReviewPersistence(
                "InformixReviewPersistence.CustomNamespace.NoFactoryClass");
        } catch (ConfigurationException e) {
            assertEquals("Configuration parameter [connection.factory_class] under"
                + " namespace [InformixReviewPersistence.CustomNamesp"
                + "ace.NoFactoryClass] is not specified.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>InformixReviewPersistence(String namespace)</code>.
     * </p>
     * <p>
     * factory_class is empty (trimmed). ConfigurationException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureConstructorC9() throws Exception {
        try {
            new InformixReviewPersistence(
                "InformixReviewPersistence.CustomNamespace.EmptyFactoryClass");
        } catch (ConfigurationException e) {
            assertEquals("Configuration parameter [connection.factory_class] under"
                + " namespace [InformixReviewPersistence.CustomNamesp"
                + "ace.EmptyFactoryClass] is empty (trimmed).", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>InformixReviewPersistence(String namespace)</code>.
     * </p>
     * <p>
     * the namespace specified by factory_namespace doesn't exist.
     * ConfigurationException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureConstructorC10() throws Exception {
        try {
            new InformixReviewPersistence(
                "InformixReviewPersistence.CustomNamespace.NonExistFactoryNamespace");
        } catch (ConfigurationException e) {
            assertEquals("Error occurs when invoking the constructor of"
                + " class [com.topcoder.db.connectionfactory.DBCo"
                + "nnectionFactoryImpl]., caused by Error occurs "
                + "in the ConfigManager- , caused by The namespace"
                + " is not defined in the ConfigManager.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>InformixReviewPersistence(String namespace)</code>.
     * </p>
     * <p>
     * the class specified by factory_class doesn't exist.
     * ConfigurationException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureConstructorC11() throws Exception {
        try {
            new InformixReviewPersistence(
                "InformixReviewPersistence.CustomNamespace.NonExistFactoryClass");
        } catch (ConfigurationException e) {
            assertEquals("Unable to find class [do_not_exist]., caused by do_not_exist", e
                .getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>InformixReviewPersistence(String namespace)</code>.
     * </p>
     * <p>
     * the class specified by factory_class does not have a constructor with one
     * String parameter. ConfigurationException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureConstructorC13() throws Exception {
        try {
            new InformixReviewPersistence(
                "InformixReviewPersistence.CustomNamespace.InvalidFactoryClass1");
        } catch (ConfigurationException e) {
            assertEquals("Unable to find the constructor with one String "
                + "parameter of class [java.util.ArrayList]., caused "
                + "by java.util.ArrayList.<init>(java.lang.String)", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>InformixReviewPersistence(String namespace)</code>.
     * </p>
     * <p>
     * search_bundle_manager_namespace is not provided. ConfigurationException
     * is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureConstructorC14() throws Exception {
        try {
            new InformixReviewPersistence(
                "InformixReviewPersistence.CustomNamespace.NoBundleManagerNamespace");
        } catch (ConfigurationException e) {
            assertEquals("Configuration parameter [search_bundle_manager_namespace]"
                + " under namespace [InformixReviewPersistence.CustomNam"
                + "espace.NoBundleManagerNamespace] is not specified.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>InformixReviewPersistence(String namespace)</code>.
     * </p>
     * <p>
     * search_bundle_manager_namespace is empty (trimmed).
     * ConfigurationException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureConstructorC15() throws Exception {
        try {
            new InformixReviewPersistence(
                "InformixReviewPersistence.CustomNamespace.EmptyBundleManagerNamespace");
        } catch (ConfigurationException e) {
            assertEquals("Configuration parameter [search_bundle_manager_namespace]"
                + " under namespace [InformixReviewPersistence."
                + "CustomNamespace.EmptyBundleManagerNamespace]" + " is empty (trimmed).", e
                .getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>InformixReviewPersistence(String namespace)</code>.
     * </p>
     * <p>
     * the namespace specified by search_bundle_manager_namespace does not
     * exist. ConfigurationException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureConstructorC16() throws Exception {
        try {
            new InformixReviewPersistence(
                "InformixReviewPersistence.CustomNamespace.NonExistBundleManagerNamespace");
        } catch (ConfigurationException e) {
            assertEquals("Unable to create a new instance of SearchBundleManager"
                + " class from namespace [do_not_exist], caused by "
                + "The namespace 'do_not_exist' is missing in configuration., "
                + "caused by do_not_exist is unknown", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>InformixReviewPersistence(String namespace)</code>.
     * </p>
     * <p>
     * search_bundle_name is not provided. ConfigurationException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureConstructorC17() throws Exception {
        try {
            new InformixReviewPersistence("InformixReviewPersistence.CustomNamespace.NoBundleName");
        } catch (ConfigurationException e) {
            assertEquals("Configuration parameter [search_bundle_name]"
                + " under namespace [InformixReviewPersistence.CustomNam"
                + "espace.NoBundleName] is not specified.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>InformixReviewPersistence(String namespace)</code>.
     * </p>
     * <p>
     * search_bundle_name is empty (trimmed). ConfigurationException is
     * expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureConstructorC18() throws Exception {
        try {
            new InformixReviewPersistence(
                "InformixReviewPersistence.CustomNamespace.EmptyBundleName");
        } catch (ConfigurationException e) {
            assertEquals("Configuration parameter [search_bundle_name]"
                + " under namespace [InformixReviewPersistence."
                + "CustomNamespace.EmptyBundleName]" + " is empty (trimmed).", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>InformixReviewPersistence(String namespace)</code>.
     * </p>
     * <p>
     * the name specified by search_bundle_name does not exist.
     * ConfigurationException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureConstructorC19() throws Exception {
        try {
            new InformixReviewPersistence(
                "InformixReviewPersistence.CustomNamespace.NonExistBundleName");
        } catch (ConfigurationException e) {
            assertEquals("Unable to find the specified SearchBundle"
                + " [do_not_exist] from the SearchBundleManager"
                + " created from configuration namespace"
                + " [com.topcoder.search.builder.SearchBundleManager].", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>createReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * check if the review object is stored in the database correctly and its
     * properties are updated.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCreateReview1() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Review review = getSampleReview();

        persistence.createReview(review, "topcoder");

        // first check if the review's properties are updated.
        assertEquals("check review id", 1, review.getId());
        assertEquals("check review creation user", "topcoder", review.getCreationUser());
        assertTrue("check creation date is near current time", (new Date().getTime() - review
            .getCreationTimestamp().getTime()) <= 10 * 60 * 1000);
        assertEquals("check review modification user", "topcoder", review.getModificationUser());
        assertTrue("check modification date is near current time", (new Date().getTime() - review
            .getCreationTimestamp().getTime()) <= 10 * 60 * 1000);

        assertEquals("check review comment id", 1, review.getComment(0).getId());
        assertEquals("check review comment id", 2, review.getComment(1).getId());
        assertEquals("check item id", 1, review.getItem(0).getId());
        assertEquals("check item id", 2, review.getItem(1).getId());
        assertEquals("check item comment id", 1, review.getItem(0).getComment(0).getId());
        assertEquals("check item comment id", 2, review.getItem(0).getComment(1).getId());
        assertEquals("check item comment id", 3, review.getItem(1).getComment(0).getId());
        assertEquals("check item comment id", 4, review.getItem(1).getComment(1).getId());

        // then check if the review is stored in the database.
        // create the connection
        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();

        Object[][] rows = Helper.doQuery(conn, "SELECT * FROM review", new Object[] {},
            new DataType[] {Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE,
                Helper.LONG_TYPE, Helper.FLOAT_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
                Helper.STRING_TYPE, Helper.DATE_TYPE});
        assertEquals("only one result", 1, rows.length);
        Object[] row = rows[0];

        assertEquals("check review_id", review.getId(), ((Long) row[0]).longValue());
        assertEquals("check resource_id", review.getAuthor(), ((Long) row[1]).longValue());
        assertEquals("check submission_id", review.getSubmission(), ((Long) row[2]).longValue());
        assertEquals("check scorecard_id", review.getScorecard(), ((Long) row[3]).longValue());
        assertEquals("check committed", review.isCommitted(), ((Long) row[4]).longValue() != 0);
        assertEquals("check score", review.getScore(), row[5]);
        assertEquals("check create_user", review.getCreationUser(), row[6]);
        assertEquals("check create_date", review.getCreationTimestamp(), row[7]);
        assertEquals("check modify_user", review.getModificationUser(), row[8]);
        assertEquals("check modify_date", review.getModificationTimestamp(), row[9]);

        rows = Helper.doQuery(conn, "SELECT * FROM review_comment ORDER BY sort", new Object[] {},
            new DataType[] {Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE,
                Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE,
                Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE});
        assertEquals("two results", 2, rows.length);

        for (int i = 0; i < rows.length; ++i) {
            row = rows[i];

            assertEquals("check review_comment_id", review.getComment(i).getId(), ((Long) row[0])
                .longValue());
            assertEquals("check resource_id", review.getComment(i).getAuthor(), ((Long) row[1])
                .longValue());
            assertEquals("check review_id", review.getId(), ((Long) row[2]).longValue());
            assertEquals("check comment_type_id", review.getComment(i).getCommentType().getId(),
                ((Long) row[3]).longValue());
            assertEquals("check content", review.getComment(i).getComment(), row[4]);
            assertEquals("check extra info", review.getComment(i).getExtraInfo(), row[5]);
            assertEquals("check sort", i, ((Long) row[6]).longValue());
        }

        rows = Helper.doQuery(conn, "SELECT * FROM review_item ORDER BY sort", new Object[] {},
            new DataType[] {Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE,
                Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
                Helper.STRING_TYPE, Helper.DATE_TYPE});
        assertEquals("two results", 2, rows.length);

        for (int i = 0; i < rows.length; ++i) {
            row = rows[i];

            assertEquals("check review_item_id", review.getItem(i).getId(), ((Long) row[0])
                .longValue());
            assertEquals("check review_id", review.getId(), ((Long) row[1]).longValue());
            assertEquals("check scorecard_question_id", review.getItem(i).getQuestion(),
                ((Long) row[2]).longValue());
            assertEquals("check upload_id", review.getItem(i).getDocument(), row[3]);
            assertEquals("check answer", review.getItem(i).getAnswer(), row[4]);
            assertEquals("check sort", i, ((Long) row[5]).longValue());
        }

        rows = Helper.doQuery(conn,
            "SELECT * FROM review_item_comment ORDER BY review_item_id, sort", new Object[] {},
            new DataType[] {Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE,
                Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE,
                Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE});
        assertEquals("four results", 4, rows.length);

        for (int i = 0; i < review.getNumberOfItems(); ++i) {
            Item item = review.getItem(i);

            for (int j = 0; j < item.getNumberOfComments(); ++j) {
                Comment comment = item.getComment(j);

                row = rows[i * item.getNumberOfComments() + j];

                assertEquals("check review_comment_id", comment.getId(), ((Long) row[0])
                    .longValue());
                assertEquals("check resource_id", comment.getAuthor(), ((Long) row[1]).longValue());
                assertEquals("check review_id", item.getId(), ((Long) row[2]).longValue());
                assertEquals("check comment_type_id", comment.getCommentType().getId(),
                    ((Long) row[3]).longValue());
                assertEquals("check content", comment.getComment(), row[4]);
                assertEquals("check extra info", comment.getExtraInfo(), row[5]);
                assertEquals("check sort", j, ((Long) row[6]).longValue());
            }
        }

        conn.close();
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>createReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * check if the review object's properties are reset to their initial values
     * when any error occurs during the creation.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCreateReview2() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        // first try to insert some record to cause an exception when call
        // createReview.

        // create the connection
        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();
        Helper.doDMLQuery(conn, "INSERT INTO review (review_id,resource_id,"
            + "submission_id,scorecard_id,committed,score,"
            + "create_user,create_date,modify_user,modify_date)"
            + " VALUES (10, 1, 1, 1, 1, 1.0, 'user', CURRENT, 'user', CURRENT)", new Object[] {});
        Helper.doDMLQuery(conn, "INSERT INTO review_item (review_item_id,review_id,"
            + "scorecard_question_id,upload_id,answer,sort,"
            + "create_user,create_date,modify_user,modify_date)"
            + " VALUES (10, 10, 1, 1, 'answer', 0, 'user', CURRENT, 'user', CURRENT)",
            new Object[] {});

        Helper.doDMLQuery(conn,
            "INSERT INTO review_item_comment (review_item_comment_id,resource_id,"
                + "review_item_id,comment_type_id,content,extra_info,sort,"
                + "create_user,create_date,modify_user,modify_date)"
                + " VALUES (4, 1, 10, 1, 'content', 'extrainfo', 0,"
                + " 'user', CURRENT, 'user', CURRENT)", new Object[] {});
        conn.close();

        Review review = getSampleReview();

        try {
            persistence.createReview(review, "topcoder");
        } catch (ReviewPersistenceException e) {
            // ignore
        }

        // then check if the review's properties are restored.
        assertEquals("check review id", -1, review.getId());
        assertNull("check review creation user", review.getCreationUser());
        assertNull("check creation date is near current time", review.getCreationTimestamp());
        assertNull("check review modification user", review.getModificationUser());
        assertNull("check modification date is near current time", review.getCreationTimestamp());

        assertEquals("check review comment id", -1, review.getComment(0).getId());
        assertEquals("check review comment id", -1, review.getComment(1).getId());
        assertEquals("check item id", -1, review.getItem(0).getId());
        assertEquals("check item id", -1, review.getItem(1).getId());
        assertEquals("check item comment id", -1, review.getItem(0).getComment(0).getId());
        assertEquals("check item comment id", -1, review.getItem(0).getComment(1).getId());
        assertEquals("check item comment id", -1, review.getItem(1).getComment(0).getId());
        assertEquals("check item comment id", -1, review.getItem(1).getComment(1).getId());
    }

    /**
     * <p>
     * Failure test of the method
     * <code>createReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateReview1() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        try {
            persistence.createReview(null, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("review should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>createReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * operator is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateReview2() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Review review = new Review();
        review.setAuthor(1);
        review.setSubmission(1);
        review.setScorecard(1);

        try {
            persistence.createReview(review, null);
        } catch (IllegalArgumentException e) {
            assertEquals("operator should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>createReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * operator is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateReview3() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Review review = new Review();
        review.setAuthor(1);
        review.setSubmission(1);
        review.setScorecard(1);

        try {
            persistence.createReview(review, "   ");
        } catch (IllegalArgumentException e) {
            assertEquals("operator should not be empty (trimmed).", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>createReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. review author is -1. IllegalArgumentException is
     * expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateReview4() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Review review = getSampleReview();
        review.resetAuthor();

        try {
            persistence.createReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("author of review [-1] should be positive.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>createReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. review submission is -1. IllegalArgumentException is
     * expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateReview5() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Review review = getSampleReview();
        review.resetSubmission();

        try {
            persistence.createReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("submission of review [-1] should be positive.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>createReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. review scorecard is -1. IllegalArgumentException is
     * expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateReview6() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Review review = getSampleReview();
        review.resetScorecard();

        try {
            persistence.createReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("scorecard of review [-1] should be positive.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>createReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. item question is -1. IllegalArgumentException is
     * expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateReview7() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Review review = getSampleReview();
        review.getItem(0).resetQuestion();

        try {
            persistence.createReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("question of item [-1] should be positive.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>createReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. item answer is null. IllegalArgumentException is
     * expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateReview8() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Review review = getSampleReview();
        review.getItem(0).resetAnswer();

        try {
            persistence.createReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("answer of item is null or not of String type.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>createReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. item answer is not String. IllegalArgumentException is
     * expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateReview9() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Review review = getSampleReview();
        review.getItem(0).setAnswer(new ReviewPersistenceException("answer"));

        try {
            persistence.createReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("answer of item is null or not of String type.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>createReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. review comment author is -1. IllegalArgumentException
     * is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateReview10() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Review review = getSampleReview();
        review.getComment(0).resetAuthor();

        try {
            persistence.createReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("author of comment [-1] should be positive.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>createReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. review comment type is null. IllegalArgumentException
     * is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateReview11() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Review review = getSampleReview();
        review.getComment(0).resetCommentType();

        try {
            persistence.createReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("type of comment should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>createReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. review comment content is null.
     * IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateReview12() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Review review = getSampleReview();
        review.getComment(0).resetComment();

        try {
            persistence.createReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("content of comment should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>createReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. review comment extra info is not of String type.
     * IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateReview13() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Review review = getSampleReview();
        review.getComment(0).setExtraInfo(new ReviewPersistenceException("extra info"));

        try {
            persistence.createReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("extra info of comment is not of String type.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>createReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. item comment author is -1. IllegalArgumentException is
     * expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateReview14() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Review review = getSampleReview();
        review.getItem(0).getComment(0).resetAuthor();

        try {
            persistence.createReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("author of comment [-1] should be positive.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>createReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. item comment type is null. IllegalArgumentException is
     * expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateReview15() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Review review = getSampleReview();
        review.getItem(0).getComment(0).resetCommentType();

        try {
            persistence.createReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("type of comment should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>createReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. item comment content is null. IllegalArgumentException
     * is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateReview16() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Review review = getSampleReview();
        review.getItem(0).getComment(0).resetComment();

        try {
            persistence.createReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("content of comment should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>createReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. item comment extra info is not of String type.
     * IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateReview17() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Review review = getSampleReview();
        review.getItem(0).getComment(0).setExtraInfo(new ReviewPersistenceException("extra info"));

        try {
            persistence.createReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("extra info of comment is not of String type.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>createReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. comment type id is -1. IllegalArgumentException is
     * expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateReview18() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Review review = getSampleReview();
        review.getItem(0).getComment(0).getCommentType().resetId();

        try {
            persistence.createReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("id of comment type [-1] should be positive.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>createReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. comment type name is null. IllegalArgumentException is
     * expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateReview19() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Review review = getSampleReview();
        review.getComment(0).getCommentType().resetName();

        try {
            persistence.createReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("name of comment type should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>createReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. duplicate comment references in different items.
     * IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateReview20() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Review review = getSampleReview();
        review.getItem(0).addComment(review.getItem(1).getComment(0));

        try {
            persistence.createReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("Duplicate Comment references found.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>createReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. duplicate comment references in review comments and
     * item comments. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateReview21() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Review review = getSampleReview();
        review.addComment(review.getItem(0).getComment(0));

        try {
            persistence.createReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("Duplicate Comment references found.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>createReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review id already exists in database. DuplicateReviewEntityException is
     * expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateReview22() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        // create the connection
        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();

        // insert data
        Helper.doDMLQuery(conn, "INSERT INTO review (review_id,"
            + " resource_id, submission_id, scorecard_id, committed, "
            + "create_user, create_date, modify_user, modify_date) values "
            + "(5, 1, 1, 1, 0, 'topcoder', CURRENT, 'topcoder', CURRENT)", new Object[] {});
        conn.close();

        Review review = getSampleReview();
        review.setId(5);

        try {
            persistence.createReview(review, "topcoder");
        } catch (DuplicateReviewEntityException e) {
            assertEquals("The review with same id [5] already exists.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>createReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * unable to generate review id. ReviewPersistenceException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateReview23() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        // create the connection
        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();

        // delete review_id_seq record
        Helper.doDMLQuery(conn, "DELETE FROM id_sequences WHERE name='review_id_seq'",
            new Object[] {});

        conn.close();

        Review review = getSampleReview();

        try {
            persistence.createReview(review, "topcoder");
        } catch (ReviewPersistenceException e) {
            assertEquals("Unable to generate id for review., caused by "
                + "The specified IDName does not exist in the database.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>createReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * unable to generate review comment id. ReviewPersistenceException is
     * expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateReview24() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        // create the connection
        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();

        // delete review_comment_id_seq record
        Helper.doDMLQuery(conn, "DELETE FROM id_sequences WHERE name='review_comment_id_seq'",
            new Object[] {});

        conn.close();

        Review review = getSampleReview();

        try {
            persistence.createReview(review, "topcoder");
        } catch (ReviewPersistenceException e) {
            assertEquals("Unable to generate id for review comment.,"
                + " caused by The specified IDName does not exist in the database.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>createReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * unable to generate review item id. ReviewPersistenceException is
     * expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateReview25() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        // create the connection
        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();

        // delete review_item_id_seq record
        Helper.doDMLQuery(conn, "DELETE FROM id_sequences WHERE name='review_item_id_seq'",
            new Object[] {});

        conn.close();

        Review review = getSampleReview();

        try {
            persistence.createReview(review, "topcoder");
        } catch (ReviewPersistenceException e) {
            assertEquals("Unable to generate id for review item.,"
                + " caused by The specified IDName does not exist in the database.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>createReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * unable to generate review item comment id. ReviewPersistenceException is
     * expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateReview26() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        // create the connection
        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();

        // delete review_item_comment_id_seq record
        Helper.doDMLQuery(conn, "DELETE FROM id_sequences WHERE name='review_item_comment_id_seq'",
            new Object[] {});

        conn.close();

        Review review = getSampleReview();

        try {
            persistence.createReview(review, "topcoder");
        } catch (ReviewPersistenceException e) {
            assertEquals("Unable to generate id for review item comment.,"
                + " caused by The specified IDName does not exist in the database.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>updateReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * check if the review object is updated in the database correctly and its
     * properties are updated.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyUpdateReview1() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Review review = getSampleReview();

        persistence.createReview(review, "topcoder");
        Thread.sleep(500);

        // change the review object
        review.setAuthor(3);
        review.setSubmission(2);
        review.setSubmission(1);

        CommentType commentType = new CommentType();
        commentType.setId(2);
        commentType.setName("type 2");

        Comment[] comments = review.getAllComments();
        comments[0].setId(4);
        comments[0].setComment("comment 4");
        comments[1].setCommentType(commentType);

        Item[] items = review.getAllItems();
        items[0].setDocument(new Long(2));
        items[1].setId(4);

        items[0].removeComment(1);
        Comment newComment = new Comment();
        newComment.setAuthor(2);
        newComment.setComment("new comment");
        newComment.setExtraInfo("new extra info");
        newComment.setCommentType(commentType);
        items[1].addComment(newComment);

        persistence.updateReview(review, "topcoder1234");

        // first check if the review's properties are updated.
        assertEquals("check review id", 1, review.getId());
        assertEquals("check review modification user", "topcoder1234", review.getModificationUser());
        assertTrue("check modification date ", review.getModificationTimestamp().after(
            review.getCreationTimestamp()));

        assertEquals("check review comment id", 3, review.getComment(0).getId());
        assertEquals("check review comment id", 2, review.getComment(1).getId());
        assertEquals("check item id", 1, review.getItem(0).getId());
        assertEquals("check item id", 3, review.getItem(1).getId());
        assertEquals("check item comment id", 2, review.getItem(0).getComment(0).getId());
        assertEquals("check item comment id", 5, review.getItem(1).getComment(0).getId());
        assertEquals("check item comment id", 6, review.getItem(1).getComment(1).getId());
        assertEquals("check item comment id", 7, review.getItem(1).getComment(2).getId());

        // then check if the review is updated in the database.
        // create the connection
        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();

        Object[][] rows = Helper.doQuery(conn, "SELECT * FROM review", new Object[] {},
            new DataType[] {Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE,
                Helper.LONG_TYPE, Helper.FLOAT_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
                Helper.STRING_TYPE, Helper.DATE_TYPE});
        assertEquals("only one result", 1, rows.length);
        Object[] row = rows[0];

        assertEquals("check review_id", review.getId(), ((Long) row[0]).longValue());
        assertEquals("check resource_id", review.getAuthor(), ((Long) row[1]).longValue());
        assertEquals("check submission_id", review.getSubmission(), ((Long) row[2]).longValue());
        assertEquals("check scorecard_id", review.getScorecard(), ((Long) row[3]).longValue());
        assertEquals("check committed", review.isCommitted(), ((Long) row[4]).longValue() != 0);
        assertEquals("check score", review.getScore(), row[5]);
        assertEquals("check create_user", review.getCreationUser(), row[6]);
        assertEquals("check create_date", review.getCreationTimestamp(), row[7]);
        assertEquals("check modify_user", review.getModificationUser(), row[8]);
        assertEquals("check modify_date", review.getModificationTimestamp(), row[9]);

        rows = Helper.doQuery(conn, "SELECT * FROM review_comment ORDER BY sort", new Object[] {},
            new DataType[] {Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE,
                Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE,
                Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE});
        assertEquals("two results", 2, rows.length);

        for (int i = 0; i < rows.length; ++i) {
            row = rows[i];

            assertEquals("check review_comment_id", review.getComment(i).getId(), ((Long) row[0])
                .longValue());
            assertEquals("check resource_id", review.getComment(i).getAuthor(), ((Long) row[1])
                .longValue());
            assertEquals("check review_id", review.getId(), ((Long) row[2]).longValue());
            assertEquals("check comment_type_id", review.getComment(i).getCommentType().getId(),
                ((Long) row[3]).longValue());
            assertEquals("check content", review.getComment(i).getComment(), row[4]);
            assertEquals("check extra info", review.getComment(i).getExtraInfo(), row[5]);
            assertEquals("check sort", i, ((Long) row[6]).longValue());
        }

        rows = Helper.doQuery(conn, "SELECT * FROM review_item ORDER BY sort", new Object[] {},
            new DataType[] {Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE,
                Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
                Helper.STRING_TYPE, Helper.DATE_TYPE});
        assertEquals("two results", 2, rows.length);

        for (int i = 0; i < rows.length; ++i) {
            row = rows[i];

            assertEquals("check review_item_id", review.getItem(i).getId(), ((Long) row[0])
                .longValue());
            assertEquals("check review_id", review.getId(), ((Long) row[1]).longValue());
            assertEquals("check scorecard_question_id", review.getItem(i).getQuestion(),
                ((Long) row[2]).longValue());
            assertEquals("check upload_id", review.getItem(i).getDocument(), row[3]);
            assertEquals("check answer", review.getItem(i).getAnswer(), row[4]);
            assertEquals("check sort", i, ((Long) row[5]).longValue());
        }

        rows = Helper.doQuery(conn,
            "SELECT * FROM review_item_comment ORDER BY review_item_id, sort", new Object[] {},
            new DataType[] {Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE,
                Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE,
                Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE});
        assertEquals("four results", 4, rows.length);

        int k = 0;
        for (int i = 0; i < review.getNumberOfItems(); ++i) {
            Item item = review.getItem(i);

            for (int j = 0; j < item.getNumberOfComments(); ++j) {
                Comment comment = item.getComment(j);

                row = rows[k++];

                assertEquals("check review_comment_id", comment.getId(), ((Long) row[0])
                    .longValue());
                assertEquals("check resource_id", comment.getAuthor(), ((Long) row[1]).longValue());
                assertEquals("check review_id", item.getId(), ((Long) row[2]).longValue());
                assertEquals("check comment_type_id", comment.getCommentType().getId(),
                    ((Long) row[3]).longValue());
                assertEquals("check content", comment.getComment(), row[4]);
                assertEquals("check extra info", comment.getExtraInfo(), row[5]);
                assertEquals("check sort", j, ((Long) row[6]).longValue());
            }
        }

        conn.close();
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>updateReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * check if the review object's properties are reset to their initial values
     * when any error occurs during the update.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyUpdateReview2() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Review review = getSampleReview();

        persistence.createReview(review, "topcoder");

        // first try to cause an exception when call
        // updateReview.

        // create the connection
        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();
        // delete review_item_comment_id_seq record
        Helper.doDMLQuery(conn, "DELETE FROM id_sequences WHERE name='review_item_comment_id_seq'",
            new Object[] {});

        conn.close();

        Date date = review.getCreationTimestamp();

        review.getComment(0).resetId();
        review.getComment(1).setId(100);

        review.getItem(0).resetId();
        review.getItem(1).setId(100);

        review.getItem(0).getComment(0).resetId();
        review.getItem(0).getComment(1).setId(100);
        review.getItem(1).getComment(0).resetId();
        review.getItem(1).getComment(1).setId(100);

        try {
            persistence.updateReview(review, "topcoder");
        } catch (ReviewPersistenceException e) {
            // ignore
        }

        // then check if the review's properties are restored.
        assertEquals("check review id", 1, review.getId());
        assertEquals("check review creation user", "topcoder", review.getCreationUser());
        assertEquals("check creation date is near current time", date, review
            .getCreationTimestamp());
        assertEquals("check review modification user", "topcoder", review.getModificationUser());
        assertEquals("check modification date is near current time", date, review
            .getCreationTimestamp());

        assertEquals("check review comment id", -1, review.getComment(0).getId());
        assertEquals("check review comment id", 100, review.getComment(1).getId());
        assertEquals("check item id", -1, review.getItem(0).getId());
        assertEquals("check item id", 100, review.getItem(1).getId());
        assertEquals("check item comment id", -1, review.getItem(0).getComment(0).getId());
        assertEquals("check item comment id", 100, review.getItem(0).getComment(1).getId());
        assertEquals("check item comment id", -1, review.getItem(1).getComment(0).getId());
        assertEquals("check item comment id", 100, review.getItem(1).getComment(1).getId());
    }

    /**
     * <p>
     * Failure test of the method
     * <code>updateReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateReview1() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        try {
            persistence.updateReview(null, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("review should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>updateReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * operator is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateReview2() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Review review = new Review();
        review.setAuthor(1);
        review.setSubmission(1);
        review.setScorecard(1);

        try {
            persistence.updateReview(review, null);
        } catch (IllegalArgumentException e) {
            assertEquals("operator should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>udpateReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * operator is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateReview3() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Review review = new Review();
        review.setAuthor(1);
        review.setSubmission(1);
        review.setScorecard(1);

        try {
            persistence.updateReview(review, "   ");
        } catch (IllegalArgumentException e) {
            assertEquals("operator should not be empty (trimmed).", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>udpateReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. review id is -1. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateReview4() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Review review = new Review();
        review.resetId();
        review.setAuthor(1);
        review.setSubmission(1);
        review.setScorecard(1);

        try {
            persistence.updateReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("review id [-1] should be positive.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>updateReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. review author is -1. IllegalArgumentException is
     * expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateReview5() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();
        Helper.doDMLQuery(conn, "INSERT INTO review (review_id,resource_id,"
            + "submission_id,scorecard_id,committed,score,"
            + "create_user,create_date,modify_user,modify_date)"
            + " VALUES (1, 1, 1, 1, 1, 1.0, 'user', CURRENT, 'user', CURRENT)", new Object[] {});
        conn.close();

        Review review = getSampleReview();
        review.setId(1);
        review.resetAuthor();

        try {
            persistence.updateReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("author of review [-1] should be positive.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>updateReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. review submission is -1. IllegalArgumentException is
     * expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateReview6() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();
        Helper.doDMLQuery(conn, "INSERT INTO review (review_id,resource_id,"
            + "submission_id,scorecard_id,committed,score,"
            + "create_user,create_date,modify_user,modify_date)"
            + " VALUES (1, 1, 1, 1, 1, 1.0, 'user', CURRENT, 'user', CURRENT)", new Object[] {});
        conn.close();

        Review review = getSampleReview();
        review.setId(1);
        review.resetSubmission();

        try {
            persistence.updateReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("submission of review [-1] should be positive.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>updateReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. review scorecard is -1. IllegalArgumentException is
     * expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateReview7() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();
        Helper.doDMLQuery(conn, "INSERT INTO review (review_id,resource_id,"
            + "submission_id,scorecard_id,committed,score,"
            + "create_user,create_date,modify_user,modify_date)"
            + " VALUES (1, 1, 1, 1, 1, 1.0, 'user', CURRENT, 'user', CURRENT)", new Object[] {});
        conn.close();

        Review review = getSampleReview();
        review.setId(1);
        review.resetScorecard();

        try {
            persistence.updateReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("scorecard of review [-1] should be positive.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>updateReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. item question is -1. IllegalArgumentException is
     * expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateReview8() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();
        Helper.doDMLQuery(conn, "INSERT INTO review (review_id,resource_id,"
            + "submission_id,scorecard_id,committed,score,"
            + "create_user,create_date,modify_user,modify_date)"
            + " VALUES (1, 1, 1, 1, 1, 1.0, 'user', CURRENT, 'user', CURRENT)", new Object[] {});
        conn.close();

        Review review = getSampleReview();
        review.setId(1);
        review.getItem(0).resetQuestion();

        try {
            persistence.updateReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("question of item [-1] should be positive.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>updateReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. item answer is null. IllegalArgumentException is
     * expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateReview9() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();
        Helper.doDMLQuery(conn, "INSERT INTO review (review_id,resource_id,"
            + "submission_id,scorecard_id,committed,score,"
            + "create_user,create_date,modify_user,modify_date)"
            + " VALUES (1, 1, 1, 1, 1, 1.0, 'user', CURRENT, 'user', CURRENT)", new Object[] {});
        conn.close();

        Review review = getSampleReview();
        review.setId(1);
        review.getItem(0).resetAnswer();

        try {
            persistence.updateReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("answer of item is null or not of String type.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>updateReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. item answer is not String. IllegalArgumentException is
     * expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateReview10() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();
        Helper.doDMLQuery(conn, "INSERT INTO review (review_id,resource_id,"
            + "submission_id,scorecard_id,committed,score,"
            + "create_user,create_date,modify_user,modify_date)"
            + " VALUES (1, 1, 1, 1, 1, 1.0, 'user', CURRENT, 'user', CURRENT)", new Object[] {});
        conn.close();

        Review review = getSampleReview();
        review.setId(1);
        review.getItem(0).setAnswer(new ReviewPersistenceException("answer"));

        try {
            persistence.updateReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("answer of item is null or not of String type.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>updateReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. review comment author is -1. IllegalArgumentException
     * is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateReview11() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();
        Helper.doDMLQuery(conn, "INSERT INTO review (review_id,resource_id,"
            + "submission_id,scorecard_id,committed,score,"
            + "create_user,create_date,modify_user,modify_date)"
            + " VALUES (1, 1, 1, 1, 1, 1.0, 'user', CURRENT, 'user', CURRENT)", new Object[] {});
        conn.close();

        Review review = getSampleReview();
        review.setId(1);
        review.getComment(0).resetAuthor();

        try {
            persistence.updateReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("author of comment [-1] should be positive.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>updateReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. review comment type is null. IllegalArgumentException
     * is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateReview12() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();
        Helper.doDMLQuery(conn, "INSERT INTO review (review_id,resource_id,"
            + "submission_id,scorecard_id,committed,score,"
            + "create_user,create_date,modify_user,modify_date)"
            + " VALUES (1, 1, 1, 1, 1, 1.0, 'user', CURRENT, 'user', CURRENT)", new Object[] {});
        conn.close();

        Review review = getSampleReview();
        review.setId(1);
        review.getComment(0).resetCommentType();

        try {
            persistence.updateReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("type of comment should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>updateReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. review comment content is null.
     * IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateReview13() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();
        Helper.doDMLQuery(conn, "INSERT INTO review (review_id,resource_id,"
            + "submission_id,scorecard_id,committed,score,"
            + "create_user,create_date,modify_user,modify_date)"
            + " VALUES (1, 1, 1, 1, 1, 1.0, 'user', CURRENT, 'user', CURRENT)", new Object[] {});
        conn.close();

        Review review = getSampleReview();
        review.setId(1);
        review.getComment(0).resetComment();

        try {
            persistence.updateReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("content of comment should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>updateReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. review comment extra info is not of String type.
     * IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateReview14() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();
        Helper.doDMLQuery(conn, "INSERT INTO review (review_id,resource_id,"
            + "submission_id,scorecard_id,committed,score,"
            + "create_user,create_date,modify_user,modify_date)"
            + " VALUES (1, 1, 1, 1, 1, 1.0, 'user', CURRENT, 'user', CURRENT)", new Object[] {});
        conn.close();

        Review review = getSampleReview();
        review.setId(1);
        review.getComment(0).setExtraInfo(new ReviewPersistenceException("extra info"));

        try {
            persistence.updateReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("extra info of comment is not of String type.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>updateReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. item comment author is -1. IllegalArgumentException is
     * expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateReview15() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();
        Helper.doDMLQuery(conn, "INSERT INTO review (review_id,resource_id,"
            + "submission_id,scorecard_id,committed,score,"
            + "create_user,create_date,modify_user,modify_date)"
            + " VALUES (1, 1, 1, 1, 1, 1.0, 'user', CURRENT, 'user', CURRENT)", new Object[] {});
        conn.close();

        Review review = getSampleReview();
        review.setId(1);
        review.getItem(0).getComment(0).resetAuthor();

        try {
            persistence.updateReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("author of comment [-1] should be positive.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>updateReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. item comment type is null. IllegalArgumentException is
     * expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateReview16() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();
        Helper.doDMLQuery(conn, "INSERT INTO review (review_id,resource_id,"
            + "submission_id,scorecard_id,committed,score,"
            + "create_user,create_date,modify_user,modify_date)"
            + " VALUES (1, 1, 1, 1, 1, 1.0, 'user', CURRENT, 'user', CURRENT)", new Object[] {});
        conn.close();

        Review review = getSampleReview();
        review.setId(1);
        review.getItem(0).getComment(0).resetCommentType();

        try {
            persistence.updateReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("type of comment should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>updateReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. item comment content is null. IllegalArgumentException
     * is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateReview17() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();
        Helper.doDMLQuery(conn, "INSERT INTO review (review_id,resource_id,"
            + "submission_id,scorecard_id,committed,score,"
            + "create_user,create_date,modify_user,modify_date)"
            + " VALUES (1, 1, 1, 1, 1, 1.0, 'user', CURRENT, 'user', CURRENT)", new Object[] {});
        conn.close();

        Review review = getSampleReview();
        review.setId(1);
        review.getItem(0).getComment(0).resetComment();

        try {
            persistence.updateReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("content of comment should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>updateReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. item comment extra info is not of String type.
     * IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateReview18() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();
        Helper.doDMLQuery(conn, "INSERT INTO review (review_id,resource_id,"
            + "submission_id,scorecard_id,committed,score,"
            + "create_user,create_date,modify_user,modify_date)"
            + " VALUES (1, 1, 1, 1, 1, 1.0, 'user', CURRENT, 'user', CURRENT)", new Object[] {});
        conn.close();

        Review review = getSampleReview();
        review.setId(1);
        review.getItem(0).getComment(0).setExtraInfo(new ReviewPersistenceException("extra info"));

        try {
            persistence.updateReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("extra info of comment is not of String type.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>updateReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. comment type id is -1. IllegalArgumentException is
     * expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateReview19() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();
        Helper.doDMLQuery(conn, "INSERT INTO review (review_id,resource_id,"
            + "submission_id,scorecard_id,committed,score,"
            + "create_user,create_date,modify_user,modify_date)"
            + " VALUES (1, 1, 1, 1, 1, 1.0, 'user', CURRENT, 'user', CURRENT)", new Object[] {});
        conn.close();

        Review review = getSampleReview();
        review.setId(1);
        review.getItem(0).getComment(0).getCommentType().resetId();

        try {
            persistence.updateReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("id of comment type [-1] should be positive.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>updateReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. comment type name is null. IllegalArgumentException is
     * expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateReview20() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();
        Helper.doDMLQuery(conn, "INSERT INTO review (review_id,resource_id,"
            + "submission_id,scorecard_id,committed,score,"
            + "create_user,create_date,modify_user,modify_date)"
            + " VALUES (1, 1, 1, 1, 1, 1.0, 'user', CURRENT, 'user', CURRENT)", new Object[] {});
        conn.close();

        Review review = getSampleReview();
        review.setId(1);
        review.getComment(0).getCommentType().resetName();

        try {
            persistence.updateReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("name of comment type should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>updateReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. duplicate comment references in different items.
     * IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateReview21() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();
        Helper.doDMLQuery(conn, "INSERT INTO review (review_id,resource_id,"
            + "submission_id,scorecard_id,committed,score,"
            + "create_user,create_date,modify_user,modify_date)"
            + " VALUES (1, 1, 1, 1, 1, 1.0, 'user', CURRENT, 'user', CURRENT)", new Object[] {});
        conn.close();

        Review review = getSampleReview();
        review.setId(1);
        review.getItem(0).addComment(review.getItem(1).getComment(0));

        try {
            persistence.updateReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("Duplicate Comment references found.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>updateReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review is invalid. duplicate comment references in review comments and
     * item comments. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateReview22() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();
        Helper.doDMLQuery(conn, "INSERT INTO review (review_id,resource_id,"
            + "submission_id,scorecard_id,committed,score,"
            + "create_user,create_date,modify_user,modify_date)"
            + " VALUES (1, 1, 1, 1, 1, 1.0, 'user', CURRENT, 'user', CURRENT)", new Object[] {});
        conn.close();

        Review review = getSampleReview();
        review.setId(1);
        review.addComment(review.getItem(0).getComment(0));

        try {
            persistence.updateReview(review, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("Duplicate Comment references found.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>updateReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * review id does not exists in database. ReviewEntityNotFoundException is
     * expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateReview23() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Review review = getSampleReview();
        review.setId(1);

        try {
            persistence.updateReview(review, "topcoder");
        } catch (ReviewEntityNotFoundException e) {
            assertEquals("The review id [1] does not exist in the database.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>createReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * unable to generate review id. ReviewPersistenceException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateReview24() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        // create the connection
        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();

        // delete review_id_seq record
        Helper.doDMLQuery(conn, "DELETE FROM id_sequences WHERE name='review_id_seq'",
            new Object[] {});

        Helper.doDMLQuery(conn, "INSERT INTO review (review_id,resource_id,"
            + "submission_id,scorecard_id,committed,score,"
            + "create_user,create_date,modify_user,modify_date)"
            + " VALUES (1, 1, 1, 1, 1, 1.0, 'user', CURRENT, 'user', CURRENT)", new Object[] {});
        conn.close();

        Review review = getSampleReview();
        review.setId(1);

        try {
            persistence.updateReview(review, "topcoder");
        } catch (ReviewPersistenceException e) {
            assertEquals("Unable to generate id for review., caused by "
                + "The specified IDName does not exist in the database.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>updateReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * unable to generate review comment id. ReviewPersistenceException is
     * expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateReview25() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        // create the connection
        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();

        // delete review_comment_id_seq record
        Helper.doDMLQuery(conn, "DELETE FROM id_sequences WHERE name='review_comment_id_seq'",
            new Object[] {});

        Helper.doDMLQuery(conn, "INSERT INTO review (review_id,resource_id,"
            + "submission_id,scorecard_id,committed,score,"
            + "create_user,create_date,modify_user,modify_date)"
            + " VALUES (1, 1, 1, 1, 1, 1.0, 'user', CURRENT, 'user', CURRENT)", new Object[] {});
        conn.close();

        Review review = getSampleReview();
        review.setId(1);
        try {
            persistence.updateReview(review, "topcoder");
        } catch (ReviewPersistenceException e) {
            assertEquals("Unable to generate id for review comment.,"
                + " caused by The specified IDName does not exist in the database.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>udpateReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * unable to generate review item id. ReviewPersistenceException is
     * expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateReview26() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        // create the connection
        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();

        // delete review_item_id_seq record
        Helper.doDMLQuery(conn, "DELETE FROM id_sequences WHERE name='review_item_id_seq'",
            new Object[] {});

        Helper.doDMLQuery(conn, "INSERT INTO review (review_id,resource_id,"
            + "submission_id,scorecard_id,committed,score,"
            + "create_user,create_date,modify_user,modify_date)"
            + " VALUES (1, 1, 1, 1, 1, 1.0, 'user', CURRENT, 'user', CURRENT)", new Object[] {});
        conn.close();

        Review review = getSampleReview();
        review.setId(1);

        try {
            persistence.updateReview(review, "topcoder");
        } catch (ReviewPersistenceException e) {
            assertEquals("Unable to generate id for review item.,"
                + " caused by The specified IDName does not exist in the database.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>updateReview(Review review, String operator)</code>.
     * </p>
     * <p>
     * unable to generate review item comment id. ReviewPersistenceException is
     * expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateReview27() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        // create the connection
        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();

        // delete review_item_comment_id_seq record
        Helper.doDMLQuery(conn, "DELETE FROM id_sequences WHERE name='review_item_comment_id_seq'",
            new Object[] {});

        Helper.doDMLQuery(conn, "INSERT INTO review (review_id,resource_id,"
            + "submission_id,scorecard_id,committed,score,"
            + "create_user,create_date,modify_user,modify_date)"
            + " VALUES (1, 1, 1, 1, 1, 1.0, 'user', CURRENT, 'user', CURRENT)", new Object[] {});
        conn.close();

        Review review = getSampleReview();
        review.setId(1);
        try {
            persistence.updateReview(review, "topcoder");
        } catch (ReviewPersistenceException e) {
            assertEquals("Unable to generate id for review item comment.,"
                + " caused by The specified IDName does not exist in the database.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>getReview(Long id)</code>.
     * </p>
     * <p>
     * check if the review object is get correctly from the database.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyGetReview1() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Review review = getSampleReview();

        persistence.createReview(review, "topcoder");

        Review review1 = persistence.getReview(1);

        // first check if the review's properties are updated.
        assertEquals("check review id", review1.getId(), review.getId());
        assertEquals("check review creation user", review1.getCreationUser(), review
            .getCreationUser());
        assertEquals("check creation date ", review1.getCreationTimestamp(), review
            .getCreationTimestamp());
        assertEquals("check review modification user", review1.getModificationUser(), review
            .getModificationUser());
        assertEquals("check modification date ", review1.getModificationTimestamp(), review
            .getModificationTimestamp());

        // then check if the review is get correctly from the database.

        assertEquals("check review_id", review.getId(), review1.getId());
        assertEquals("check resource_id", review.getAuthor(), review1.getAuthor());
        assertEquals("check submission_id", review.getSubmission(), review1.getSubmission());
        assertEquals("check scorecard_id", review.getScorecard(), review1.getScorecard());
        assertEquals("check committed", review.isCommitted(), review1.isCommitted());
        assertEquals("check score", review.getScore(), review1.getScore());
        assertEquals("check create_user", review.getCreationUser(), review1.getCreationUser());
        assertEquals("check create_date", review.getCreationTimestamp(), review1
            .getCreationTimestamp());
        assertEquals("check modify_user", review.getModificationUser(), review1
            .getModificationUser());
        assertEquals("check modify_date", review.getModificationTimestamp(), review1
            .getModificationTimestamp());

        for (int i = 0; i < review.getNumberOfComments(); ++i) {
            assertEquals("check review_comment_id", review.getComment(i).getId(), review1
                .getComment(i).getId());
            assertEquals("check resource_id", review.getComment(i).getAuthor(), review1.getComment(
                i).getAuthor());
            assertEquals("check review_id", review.getId(), review1.getId());
            assertEquals("check comment_type_id", review.getComment(i).getCommentType().getId(),
                review1.getComment(i).getCommentType().getId());
            assertEquals("check content", review.getComment(i).getComment(), review1.getComment(i)
                .getComment());
            assertEquals("check extra info", review.getComment(i).getExtraInfo(), review1
                .getComment(i).getExtraInfo());
        }

        for (int i = 0; i < review.getNumberOfItems(); ++i) {
            assertEquals("check review_item_id", review.getItem(i).getId(), review1.getItem(i)
                .getId());
            assertEquals("check review_id", review.getId(), review1.getId());
            assertEquals("check scorecard_question_id", review.getItem(i).getQuestion(), review1
                .getItem(i).getQuestion());
            assertEquals("check upload_id", review.getItem(i).getDocument(), review1.getItem(i)
                .getDocument());
            assertEquals("check answer", review.getItem(i).getAnswer(), review1.getItem(i)
                .getAnswer());
        }

        for (int i = 0; i < review.getNumberOfItems(); ++i) {
            Item item = review.getItem(i);
            Item item1 = review1.getItem(i);

            for (int j = 0; j < item.getNumberOfComments(); ++j) {
                Comment comment = item.getComment(j);
                Comment comment1 = item1.getComment(j);

                assertEquals("check review_comment_id", comment.getId(), comment1.getId());
                assertEquals("check resource_id", comment.getAuthor(), comment1.getAuthor());
                assertEquals("check review_id", item.getId(), item1.getId());
                assertEquals("check comment_type_id", comment.getCommentType().getId(), comment1
                    .getCommentType().getId());
                assertEquals("check content", comment.getComment(), comment1.getComment());
                assertEquals("check extra info", comment.getExtraInfo(), comment1.getExtraInfo());
            }
        }
    }

    /**
     * <p>
     * Failure test of the method <code>getReview(long id)</code>.
     * </p>
     * <p>
     * id is -1. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureGetReview1() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        try {
            persistence.getReview(-1);
        } catch (IllegalArgumentException e) {
            assertEquals("id [-1] should be positive.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>getReview(long id)</code>.
     * </p>
     * <p>
     * thre review id does not exist. ReviewEntityNotFoundException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureGetReview2() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        try {
            persistence.getReview(1);
        } catch (ReviewEntityNotFoundException e) {
            assertEquals("The review id [1] does not exist in the database.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>getAllCommentTypes()</code>.
     * </p>
     * <p>
     * check if the review object is get correctly from the database.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyGetAllCommentTypes() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();
        CommentType[] types = persistence.getAllCommentTypes();

        assertEquals("# of types", 3, types.length);
        assertEquals("id of type", 1, types[0].getId());
        assertEquals("id of type", 2, types[1].getId());
        assertEquals("id of type", 3, types[2].getId());
        assertEquals("name of type", "type 1", types[0].getName());
        assertEquals("name of type", "type 2", types[1].getName());
        assertEquals("name of type", "type 3", types[2].getName());
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>addReviewComment(long reviewId, Comment comment, String operator)</code>.
     * </p>
     * <p>
     * check if the review comment is correctly added to the database.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyAddReviewComment1() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();
        Review review = getSampleReview();
        persistence.createReview(review, "topcoder");

        CommentType commentType = new CommentType();
        commentType.setId(1);
        commentType.setName("type 1");

        Comment comment = new Comment();
        comment.setAuthor(1);
        comment.setComment("add review comment");
        comment.setCommentType(commentType);

        persistence.addReviewComment(1, comment, "addReviewComment");

        assertEquals("check comment id", 3, comment.getId());

        // then check if the review is stored in the database.
        // create the connection
        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();

        Object[][] rows = Helper.doQuery(conn,
            "SELECT * FROM review_comment WHERE review_comment_id=3", new Object[] {},
            new DataType[] {Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE,
                Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE,
                Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE});
        Object[] row = rows[0];

        assertEquals("check resource_id", comment.getAuthor(), ((Long) row[1]).longValue());
        assertEquals("check review_id", 1, ((Long) row[2]).longValue());
        assertEquals("check comment_type_id", comment.getCommentType().getId(), ((Long) row[3])
            .longValue());
        assertEquals("check content", comment.getComment(), (String) row[4]);
        assertEquals("check extra_info", comment.getExtraInfo(), (String) row[5]);
        assertEquals("check sort", 2, ((Long) row[6]).longValue());
        assertEquals("check create_user", "addReviewComment", row[7]);
        assertEquals("check modify_user", "addReviewComment", row[9]);

        conn.close();
    }

    /**
     * <p>
     * Failure test of the method
     * <code>addReviewComment(long reviewId, Comment comment, String operator)</code>.
     * </p>
     * <p>
     * reviewId is -1. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureAddReviewComment1() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();
        Review review = getSampleReview();
        persistence.createReview(review, "topcoder");

        Comment comment = new Comment();
        CommentType commentType = new CommentType();
        commentType.setId(1);
        commentType.setName("type 1");
        comment.setAuthor(1);
        comment.setComment("comment");
        comment.setCommentType(commentType);

        try {
            persistence.addReviewComment(-1, comment, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("reviewId [-1] should be positive.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>addReviewComment(long reviewId, Comment comment, String operator)</code>.
     * </p>
     * <p>
     * comment is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureAddReviewComment2() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();
        Review review = getSampleReview();
        persistence.createReview(review, "topcoder");

        try {
            persistence.addReviewComment(1, null, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("comment should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>addReviewComment(long reviewId, Comment comment, String operator)</code>.
     * </p>
     * <p>
     * operator is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureAddReviewComment3() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();
        Review review = getSampleReview();
        persistence.createReview(review, "topcoder");

        Comment comment = new Comment();
        CommentType commentType = new CommentType();
        commentType.setId(1);
        commentType.setName("type 1");
        comment.setAuthor(1);
        comment.setComment("comment");
        comment.setCommentType(commentType);

        try {
            persistence.addReviewComment(1, comment, null);
        } catch (IllegalArgumentException e) {
            assertEquals("operator should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>addReviewComment(long reviewId, Comment comment, String operator)</code>.
     * </p>
     * <p>
     * operator is empty (trimmed). IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureAddReviewComment4() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();
        Review review = getSampleReview();
        persistence.createReview(review, "topcoder");

        Comment comment = new Comment();
        CommentType commentType = new CommentType();
        commentType.setId(1);
        commentType.setName("type 1");
        comment.setAuthor(1);
        comment.setComment("comment");
        comment.setCommentType(commentType);

        try {
            persistence.addReviewComment(1, comment, "    ");
        } catch (IllegalArgumentException e) {
            assertEquals("operator should not be empty (trimmed).", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>addReviewComment(long reviewId, Comment comment, String operator)</code>.
     * </p>
     * <p>
     * review id doesn't exist. ReviewEntityNotFoundException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureAddReviewComment5() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Comment comment = new Comment();
        CommentType commentType = new CommentType();
        commentType.setId(1);
        commentType.setName("type 1");
        comment.setAuthor(1);
        comment.setComment("comment");
        comment.setCommentType(commentType);

        try {
            persistence.addReviewComment(1, comment, "topcoder");
        } catch (ReviewEntityNotFoundException e) {
            assertEquals("The review id [1] does not exist in table [review].", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>addItemComment(long itemId, Comment comment, String operator)</code>.
     * </p>
     * <p>
     * check if the item comment is correctly added to the database.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyAddItemComment1() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();
        Review review = getSampleReview();
        persistence.createReview(review, "topcoder");

        CommentType commentType = new CommentType();
        commentType.setId(1);
        commentType.setName("type 1");

        Comment comment = new Comment();
        comment.setAuthor(1);
        comment.setComment("add review comment");
        comment.setCommentType(commentType);

        persistence.addItemComment(2, comment, "addItemComment");

        assertEquals("check comment id", 5, comment.getId());

        // then check if the review is stored in the database.
        // create the connection
        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName())
            .createConnection();

        Object[][] rows = Helper.doQuery(conn,
            "SELECT * FROM review_item_comment WHERE review_item_comment_id=5", new Object[] {},
            new DataType[] {Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE,
                Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE,
                Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE});
        Object[] row = rows[0];

        assertEquals("check resource_id", comment.getAuthor(), ((Long) row[1]).longValue());
        assertEquals("check review_id", 2, ((Long) row[2]).longValue());
        assertEquals("check comment_type_id", comment.getCommentType().getId(), ((Long) row[3])
            .longValue());
        assertEquals("check content", comment.getComment(), (String) row[4]);
        assertEquals("check extra_info", comment.getExtraInfo(), (String) row[5]);
        assertEquals("check sort", 2, ((Long) row[6]).longValue());
        assertEquals("check create_user", "addItemComment", row[7]);
        assertEquals("check modify_user", "addItemComment", row[9]);

        conn.close();
    }

    /**
     * <p>
     * Failure test of the method
     * <code>addItemComment(long itemId, Comment comment, String operator)</code>.
     * </p>
     * <p>
     * itemId is -1. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureAddItemComment1() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();
        Review review = getSampleReview();
        persistence.createReview(review, "topcoder");

        Comment comment = new Comment();
        CommentType commentType = new CommentType();
        commentType.setId(1);
        commentType.setName("type 1");
        comment.setAuthor(1);
        comment.setComment("comment");
        comment.setCommentType(commentType);

        try {
            persistence.addItemComment(-1, comment, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("itemId [-1] should be positive.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>addItemComment(long itemId, Comment comment, String operator)</code>.
     * </p>
     * <p>
     * comment is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureAddItemComment2() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();
        Review review = getSampleReview();
        persistence.createReview(review, "topcoder");

        try {
            persistence.addItemComment(1, null, "topcoder");
        } catch (IllegalArgumentException e) {
            assertEquals("comment should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>addItemComment(long itemId, Comment comment, String operator)</code>.
     * </p>
     * <p>
     * operator is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureAddItemComment3() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();
        Review review = getSampleReview();
        persistence.createReview(review, "topcoder");

        Comment comment = new Comment();
        CommentType commentType = new CommentType();
        commentType.setId(1);
        commentType.setName("type 1");
        comment.setAuthor(1);
        comment.setComment("comment");
        comment.setCommentType(commentType);

        try {
            persistence.addItemComment(1, comment, null);
        } catch (IllegalArgumentException e) {
            assertEquals("operator should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>addItemComment(long itemId, Comment comment, String operator)</code>.
     * </p>
     * <p>
     * operator is empty (trimmed). IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureAddItemComment4() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();
        Review review = getSampleReview();
        persistence.createReview(review, "topcoder");

        Comment comment = new Comment();
        CommentType commentType = new CommentType();
        commentType.setId(1);
        commentType.setName("type 1");
        comment.setAuthor(1);
        comment.setComment("comment");
        comment.setCommentType(commentType);

        try {
            persistence.addItemComment(1, comment, "    ");
        } catch (IllegalArgumentException e) {
            assertEquals("operator should not be empty (trimmed).", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>addItemComment(long itemId, Comment comment, String operator)</code>.
     * </p>
     * <p>
     * Item id doesn't exist. ReviewEntityNotFoundException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureAddItemComment5() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        Comment comment = new Comment();
        CommentType commentType = new CommentType();
        commentType.setId(1);
        commentType.setName("type 1");
        comment.setAuthor(1);
        comment.setComment("comment");
        comment.setCommentType(commentType);

        try {
            persistence.addItemComment(1, comment, "topcoder");
        } catch (ReviewEntityNotFoundException e) {
            assertEquals("The item id [1] does not exist in table [review_item].", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>searchReviews(Filter filter, boolean complete)</code>.
     * </p>
     * <p>
     * check if the search result is correct when querying scorecardType
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracySearchReviews1() throws Exception {

        ReviewPersistence persistence = new InformixReviewPersistence();

        Review review = getSampleReview();
        review.setAuthor(1);
        review.setSubmission(1);
        review.setCommitted(false);
        review.setScorecard(1);
        persistence.createReview(review, "topcoder1");

        review.setId(2);
        review.setAuthor(2);
        review.setSubmission(2);
        review.setCommitted(true);
        review.setScorecard(2);
        persistence.createReview(review, "topcoder2");

        Review[] reviews = persistence.searchReviews(
            new EqualToFilter("scorecardType", new Long(1)), false);
        assertEquals("# of reviews", 1, reviews.length);
        assertEquals("review id", 1, reviews[0].getId());
        // check only the review object is retrived.
        assertEquals("# of review comments", 0, reviews[0].getNumberOfComments());
        assertEquals("# of review items", 0, reviews[0].getNumberOfItems());

        reviews = persistence.searchReviews(new EqualToFilter("scorecardType", new Long(1)), true);
        assertEquals("# of reviews", 1, reviews.length);
        assertEquals("review id", 1, reviews[0].getId());
        // check only the review object is retrived.
        assertEquals("# of review comments", 2, reviews[0].getNumberOfComments());
        assertEquals("# of review items", 2, reviews[0].getNumberOfItems());

        reviews = persistence.searchReviews(new EqualToFilter("scorecardType", new Long(2)), false);
        assertEquals("# of reviews", 1, reviews.length);
        assertEquals("review id", 2, reviews[0].getId());
        // check only the review object is retrived.
        assertEquals("# of review comments", 0, reviews[0].getNumberOfComments());
        assertEquals("# of review items", 0, reviews[0].getNumberOfItems());

        reviews = persistence.searchReviews(new EqualToFilter("scorecardType", new Long(2)), true);
        assertEquals("# of reviews", 1, reviews.length);
        assertEquals("review id", 2, reviews[0].getId());
        // check only the review object is retrived.
        assertEquals("# of review comments", 2, reviews[0].getNumberOfComments());
        assertEquals("# of review items", 2, reviews[0].getNumberOfItems());

        // no records found
        reviews = persistence.searchReviews(new EqualToFilter("scorecardType", new Long(3)), false);
        assertEquals("# of reviews", 0, reviews.length);
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>searchReviews(Filter filter, boolean complete)</code>.
     * </p>
     * <p>
     * check if the search result is correct when querying submission
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracySearchReviews2() throws Exception {

        ReviewPersistence persistence = new InformixReviewPersistence();

        Review review = getSampleReview();
        review.setAuthor(1);
        review.setSubmission(1);
        review.setCommitted(false);
        review.setScorecard(1);
        persistence.createReview(review, "topcoder1");

        review.setId(2);
        review.setAuthor(2);
        review.setSubmission(2);
        review.setCommitted(true);
        review.setScorecard(2);
        persistence.createReview(review, "topcoder2");

        Review[] reviews = persistence.searchReviews(new EqualToFilter("submission", new Long(1)),
            false);
        assertEquals("# of reviews", 1, reviews.length);
        assertEquals("review id", 1, reviews[0].getId());
        // check only the review object is retrived.
        assertEquals("# of review comments", 0, reviews[0].getNumberOfComments());
        assertEquals("# of review items", 0, reviews[0].getNumberOfItems());

        reviews = persistence.searchReviews(new EqualToFilter("submission", new Long(1)), true);
        assertEquals("# of reviews", 1, reviews.length);
        assertEquals("review id", 1, reviews[0].getId());
        // check only the review object is retrived.
        assertEquals("# of review comments", 2, reviews[0].getNumberOfComments());
        assertEquals("# of review items", 2, reviews[0].getNumberOfItems());

        reviews = persistence.searchReviews(new EqualToFilter("submission", new Long(2)), false);
        assertEquals("# of reviews", 1, reviews.length);
        assertEquals("review id", 2, reviews[0].getId());
        // check only the review object is retrived.
        assertEquals("# of review comments", 0, reviews[0].getNumberOfComments());
        assertEquals("# of review items", 0, reviews[0].getNumberOfItems());

        reviews = persistence.searchReviews(new EqualToFilter("submission", new Long(2)), true);
        assertEquals("# of reviews", 1, reviews.length);
        assertEquals("review id", 2, reviews[0].getId());
        // check only the review object is retrived.
        assertEquals("# of review comments", 2, reviews[0].getNumberOfComments());
        assertEquals("# of review items", 2, reviews[0].getNumberOfItems());

        // no records found
        reviews = persistence.searchReviews(new EqualToFilter("submission", new Long(3)), false);
        assertEquals("# of reviews", 0, reviews.length);
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>searchReviews(Filter filter, boolean complete)</code>.
     * </p>
     * <p>
     * check if the search result is correct when querying reviewer
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracySearchReviews3() throws Exception {

        ReviewPersistence persistence = new InformixReviewPersistence();

        Review review = getSampleReview();
        review.setAuthor(1);
        review.setSubmission(1);
        review.setCommitted(false);
        review.setScorecard(1);
        persistence.createReview(review, "topcoder1");

        review.setId(2);
        review.setAuthor(2);
        review.setSubmission(2);
        review.setCommitted(true);
        review.setScorecard(2);
        persistence.createReview(review, "topcoder2");

        Review[] reviews = persistence.searchReviews(new EqualToFilter("reviewer", new Long(1)),
            false);
        assertEquals("# of reviews", 1, reviews.length);
        assertEquals("review id", 1, reviews[0].getId());
        // check only the review object is retrived.
        assertEquals("# of review comments", 0, reviews[0].getNumberOfComments());
        assertEquals("# of review items", 0, reviews[0].getNumberOfItems());

        reviews = persistence.searchReviews(new EqualToFilter("reviewer", new Long(1)), true);
        assertEquals("# of reviews", 1, reviews.length);
        assertEquals("review id", 1, reviews[0].getId());
        // check only the review object is retrived.
        assertEquals("# of review comments", 2, reviews[0].getNumberOfComments());
        assertEquals("# of review items", 2, reviews[0].getNumberOfItems());

        reviews = persistence.searchReviews(new EqualToFilter("reviewer", new Long(2)), false);
        assertEquals("# of reviews", 1, reviews.length);
        assertEquals("review id", 2, reviews[0].getId());
        // check only the review object is retrived.
        assertEquals("# of review comments", 0, reviews[0].getNumberOfComments());
        assertEquals("# of review items", 0, reviews[0].getNumberOfItems());

        reviews = persistence.searchReviews(new EqualToFilter("reviewer", new Long(2)), true);
        assertEquals("# of reviews", 1, reviews.length);
        assertEquals("review id", 2, reviews[0].getId());
        // check only the review object is retrived.
        assertEquals("# of review comments", 2, reviews[0].getNumberOfComments());
        assertEquals("# of review items", 2, reviews[0].getNumberOfItems());

        // no records found
        reviews = persistence.searchReviews(new EqualToFilter("reviewer", new Long(3)), false);
        assertEquals("# of reviews", 0, reviews.length);
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>searchReviews(Filter filter, boolean complete)</code>.
     * </p>
     * <p>
     * check if the search result is correct when querying project
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracySearchReviews4() throws Exception {

        ReviewPersistence persistence = new InformixReviewPersistence();

        Review review = getSampleReview();
        review.setAuthor(1);
        review.setSubmission(1);
        review.setCommitted(false);
        review.setScorecard(1);
        persistence.createReview(review, "topcoder1");

        review.setId(2);
        review.setAuthor(2);
        review.setSubmission(2);
        review.setCommitted(true);
        review.setScorecard(2);
        persistence.createReview(review, "topcoder2");

        Review[] reviews = persistence.searchReviews(new EqualToFilter("project", new Long(1)),
            false);
        assertEquals("# of reviews", 2, reviews.length);

        assertEquals("review id", 1, reviews[0].getId());
        // check only the review object is retrived.
        assertEquals("# of review comments", 0, reviews[0].getNumberOfComments());
        assertEquals("# of review items", 0, reviews[0].getNumberOfItems());

        assertEquals("review id", 2, reviews[1].getId());
        // check only the review object is retrived.
        assertEquals("# of review comments", 0, reviews[1].getNumberOfComments());
        assertEquals("# of review items", 0, reviews[1].getNumberOfItems());

        reviews = persistence.searchReviews(new EqualToFilter("project", new Long(1)), true);
        assertEquals("# of reviews", 2, reviews.length);

        assertEquals("review id", 1, reviews[0].getId());
        // check only the review object is retrived.
        assertEquals("# of review comments", 2, reviews[0].getNumberOfComments());
        assertEquals("# of review items", 2, reviews[0].getNumberOfItems());

        assertEquals("review id", 2, reviews[1].getId());
        // check only the review object is retrived.
        assertEquals("# of review comments", 2, reviews[1].getNumberOfComments());
        assertEquals("# of review items", 2, reviews[1].getNumberOfItems());

        // no records found
        reviews = persistence.searchReviews(new EqualToFilter("project", new Long(2)), false);
        assertEquals("# of reviews", 0, reviews.length);
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>searchReviews(Filter filter, boolean complete)</code>.
     * </p>
     * <p>
     * check if the search result is correct when querying committed
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracySearchReviews5() throws Exception {

        ReviewPersistence persistence = new InformixReviewPersistence();

        Review review = getSampleReview();
        review.setAuthor(1);
        review.setSubmission(1);
        review.setCommitted(false);
        review.setScorecard(1);
        persistence.createReview(review, "topcoder1");

        review.setId(2);
        review.setAuthor(2);
        review.setSubmission(2);
        review.setCommitted(true);
        review.setScorecard(2);
        persistence.createReview(review, "topcoder2");

        Review[] reviews = persistence.searchReviews(new EqualToFilter("committed", "0"), false);
        assertEquals("# of reviews", 1, reviews.length);
        assertEquals("review id", 1, reviews[0].getId());
        // check only the review object is retrived.
        assertEquals("# of review comments", 0, reviews[0].getNumberOfComments());
        assertEquals("# of review items", 0, reviews[0].getNumberOfItems());

        reviews = persistence.searchReviews(new EqualToFilter("committed", "0"), true);
        assertEquals("# of reviews", 1, reviews.length);
        assertEquals("review id", 1, reviews[0].getId());
        // check only the review object is retrived.
        assertEquals("# of review comments", 2, reviews[0].getNumberOfComments());
        assertEquals("# of review items", 2, reviews[0].getNumberOfItems());

        reviews = persistence.searchReviews(new EqualToFilter("committed", "1"), false);
        assertEquals("# of reviews", 1, reviews.length);
        assertEquals("review id", 2, reviews[0].getId());
        // check only the review object is retrived.
        assertEquals("# of review comments", 0, reviews[0].getNumberOfComments());
        assertEquals("# of review items", 0, reviews[0].getNumberOfItems());

        reviews = persistence.searchReviews(new EqualToFilter("committed", "1"), true);
        assertEquals("# of reviews", 1, reviews.length);
        assertEquals("review id", 2, reviews[0].getId());
        // check only the review object is retrived.
        assertEquals("# of review comments", 2, reviews[0].getNumberOfComments());
        assertEquals("# of review items", 2, reviews[0].getNumberOfItems());

        // no records found
        reviews = persistence.searchReviews(new EqualToFilter("committed", "3"), false);
        assertEquals("# of reviews", 0, reviews.length);
    }

    /**
     * <p>
     * Failure test of the method
     * <code>searchReviews(Filter filter, boolean complete)</code>.
     * </p>
     * <p>
     * filter should not be null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureSearchReviews1() throws Exception {
        ReviewPersistence persistence = new InformixReviewPersistence();

        try {
            persistence.searchReviews(null, false);
        } catch (IllegalArgumentException e) {
            assertEquals("filter should not be null.", e.getMessage());
        }
    }
}
