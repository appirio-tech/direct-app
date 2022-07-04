/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review;

import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.CommentType;
import com.topcoder.management.review.data.Item;
import com.topcoder.management.review.data.Review;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanFilter;
import com.topcoder.search.builder.filter.LessThanFilter;

import com.topcoder.util.config.ConfigManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This <code>DemoTest</code> will show all functionalities provided by this component. It stands
 * on the user side, and tests the overall behavior of this component.
 * </p>
 *
 * @author icyriver
 * @version 1.0
 */
public class DemoTest extends TestCase {
    /**
     * <p>
     * The default namespace of <code>DefaultReviewManager</code> for test.
     * </p>
     */
    private static final String NAMESPACE = "com.topcoder.management.review.DefaultReviewManager";

    /**
     * <p>
     * The config file path of <code>DefaultReviewManager</code> for test.
     * </p>
     */
    private static final String CONFIG_FILE = "DemoTestConfig.xml";

    /**
     * <p>
     * The instance of <code>EqualToFilter</code> for unit test.
     * </p>
     */
    private Filter equalFilter = null;

    /**
     * <p>
     * The instance of <code>LessThanFilter</code> for unit test.
     * </p>
     */
    private Filter lessThanFilter = null;

    /**
     * <p>
     * The instance of <code>GreaterThanFilter</code> for unit test.
     * </p>
     */
    private Filter greaterThanFilter = null;

    /**
     * <p>
     * The instance of <code>ReviewPersistence</code> for unit test.
     * </p>
     */
    private ReviewPersistence persistence = null;

    /**
     * <p>
     * The instance of <code>Review</code> for unit test.
     * </p>
     */
    private Review review = null;

    /**
     * <p>
     * The instance of <code>DefaultReviewManager</code> for unit test.
     * </p>
     */
    private ReviewManager manager = null;

    /**
     * <p>
     * Returns the test suite of <code>DemoTest</code>.
     * </p>
     *
     * @return the test suite of <code>DemoTest</code>.
     */
    public static Test suite() {
        return new TestSuite(DemoTest.class);
    }

    /**
     * <p>
     * Set up the demo test envionment here.
     * </p>
     *
     * @throws Exception if any configuration error occurred.
     */
    protected void setUp() throws Exception {
        // clear all namespaces here.
        TestHelper.clearNamespace();

        // load config xml file.
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(CONFIG_FILE);

        // new the persistence instance.
        persistence = new MockReviewPersistence();

        // create some filters for test.
        lessThanFilter = new LessThanFilter("reviewer", new Long(10000));
        greaterThanFilter = new GreaterThanFilter("project", new Long(10001));
        equalFilter = new EqualToFilter("committed", new Integer(1));

        // create a review instance here.
        review = new Review(1);

        Item item = new Item(2);
        item.addComment(new Comment(2, 10000, "ok"));
        review.addItem(new Item(1));
        review.addItem(item);
        review.addComment(new Comment(1, 10000, "good"));
        review.addComment(new Comment(2, 10000, "good"));
    }

    /**
     * <p>
     * Clean the demo test environment.
     * </p>
     *
     * @throws Exception if configuration could not be clear.
     */
    protected void tearDown() throws Exception {
        // clear all namespaces.
        TestHelper.clearNamespace();
    }

    /**
     * <p>
     * Test the demo program. It tests the functionality provided by this  component.
     * </p>
     *
     * @throws Exception if any exception occurred.
     */
    public void testDemo() throws Exception {
        // create a manager instance from default namespace.
        manager = new DefaultReviewManager();

        // create a manager instance from specified namespace.
        manager = new DefaultReviewManager(NAMESPACE);

        // create a manager instance from given persistence.
        manager = new DefaultReviewManager(persistence);

        // create a review into the manager.
        manager.createReview(review, "createReviewer");

        // create another review into the manager.
        manager.createReview(new Review(2), "createReviewer");

        // update the review with id = 2 in the manager.
        Review updatedReview = new Review(2);
        updatedReview.addItem(new Item(1));
        manager.updateReview(updatedReview, "updateReviewer");

        // update this review again in the manager.
        updatedReview.addItem(new Item(2));
        manager.updateReview(updatedReview, "updateReviewer");

        // get the review from manager with its id.
        Review getReview = manager.getReview(1);

        // get all comment types from manager.
        CommentType[] commentTypes = manager.getAllCommentTypes();

        // add comment for review in manager.
        Comment reviewComment = new Comment(1, 10001, "good");
        manager.addReviewComment(review.getId(), reviewComment, "someReviewer");

        // add comment for item in manager.
        Comment itemComment = new Comment(1, 10001, "ok");
        manager.addReviewComment(1, itemComment, "someReviewer");

        // search the review from manager with simple filter.

        // search for the reviews which have been committed.
        Review[] reviews = manager.searchReviews(equalFilter, true);

        // search the review from manager with 'chain' filter.

        // search for the reviews which have been committed and
        // project is greater than 10001, or reviewer id is less than 10000.
        ChainFilter cf = new ChainFilter(equalFilter);
        cf = cf.and(greaterThanFilter).or(lessThanFilter);

        reviews = manager.searchReviews(cf.getFilter(), true);
    }
}
