/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.persistence;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.review.ReviewPersistence;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.CommentType;
import com.topcoder.management.review.data.Item;
import com.topcoder.management.review.data.Review;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.config.ConfigManager;

/**
 * This TestCase demonstrates the usage of this component.
 * @author urtks
 * @version 1.0
 */
public class DemoTest extends TestCase {
    /**
     * Aggregates all tests in this class.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(DemoTest.class);
    }

    /**
     * Get a sample comment object to test.
     * @return a sample comment object
     */
    private Comment getSampleComment() {
        CommentType type = new CommentType();
        type.setId(1);
        type.setName("type 1");

        Comment comment = new Comment();
        comment.setAuthor(1);
        comment.setComment("comment 1");
        comment.setCommentType(type);
        return comment;
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
     *             to JUnit
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
     * This method demonstrates the methods to create a
     * InformixReviewPersistence. We have 3 methods:
     * <ol>
     * <li>Using a namespace in configuration manager.</li>
     * <li>Using the default namespace in configuration manager.</li>
     * <li>Using a db factory instance and a connection name and a search
     * bundle.</li>
     * </ol>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testCreation() throws Exception {
        // create the instance with the given namespace
        new InformixReviewPersistence("InformixReviewPersistence.CustomNamespace");

        // create the instance with the default namespace;
        new InformixReviewPersistence();

        // create the instance from db factory and connection name and search
        // bundle

        // create a db factory first
        DBConnectionFactory factory = new DBConnectionFactoryImpl(
            "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        // then create a search bundle
        SearchBundle searchBundle = new SearchBundle("empty_search_bundle", new HashMap(), "context");
        // finally create the InformixReviewPersistence
        new InformixReviewPersistence(factory, "informix_connection", searchBundle);
    }

    /**
     * This method demonstrates the management of reviews.
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testCreateReivew() throws Exception {
        // first create an instance of InformixReviewPersistence class
        ReviewPersistence persistence = new InformixReviewPersistence();

        // get all comment types
        CommentType[] commentTypes = persistence.getAllCommentTypes();

        // create the review in the database
        // first initialize a review object
        Review review = getSampleReview();
        persistence.createReview(review, "someReviewer");

        // add comment for review
        // first initialize a comment object
        Comment reviewComment = getSampleComment();
        persistence.addReviewComment(1, reviewComment, "admin");

        // add comment for item
        // first initializ a comment object
        Comment itemComment = getSampleComment();
        persistence.addItemComment(1, itemComment, "admin");

        // update the review
        review.setCommitted(true);
        persistence.updateReview(review, "someReviewer");

        // get the review
        review = persistence.getReview(1);

        // search reviews
        // search for the reviews which have been committed
        Filter filter = new EqualToFilter("committed", new Integer(1));
        Review[] reviews = persistence.searchReviews(filter, true);
    }
}
