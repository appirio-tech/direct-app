/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.persistence.stresstests;

import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.CommentType;
import com.topcoder.management.review.data.Item;
import com.topcoder.management.review.data.Review;
import com.topcoder.management.review.persistence.InformixReviewPersistence;

import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

import java.util.Iterator;

/**
 * <p>
 * The stress test of InformixReviewPersistence.
 * </p>
 *
 * @author telly12
 * @version 1.0
 */
public class InformixReviewPersistenceStressTests extends TestCase {
    /**
     * The default namespace used to construct the test instance.
     */
    private static final String NAMESPACE = "com.topcoder.management.review.persistence.InformixReviewPersistence";

    /**
     * The current Review Id.
     */
    private long currentReviewId = 11;

    /**
     * The current Item Id.
     */
    private long currentItemId = 11;

    /**
     * The current Comment Id.
     */
    private long currentCommentId = 11;

    /**
     * The InformixReviewPersistence instance used to do the stress test.
     */
    private InformixReviewPersistence instance = null;

    /**
     * The setUp of the unit test.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        clearConfig();

        ConfigManager cm = ConfigManager.getInstance();
        cm.add("stress/config.xml");

        StressTestHelper.executeSqlFile("test_files/stress/clearTable.sql");
        StressTestHelper.executeSqlFile("test_files/stress/insert.sql");
        instance = new InformixReviewPersistence(NAMESPACE);
    }

    /**
     * The tearDown of the unit test.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        StressTestHelper.executeSqlFile("test_files/stress/clearTable.sql");
        clearConfig();
    }

    /**
     * Remove all the namespace.
     *
     * @throws Exception to JUnit
     */
    private void clearConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        Iterator it = cm.getAllNamespaces();

        while (it.hasNext()) {
            cm.removeNamespace((String) it.next());
        }
    }

    /**
     * The stress test of createReview,
     * Create 100 reviews at one time.
     *
     * @throws Exception to JUnit
     */
    public void testStresscreateReview() throws Exception {
        Review review = getReview();
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < StressTestHelper.LOAD; i++) {
            review.resetId();
            instance.createReview(review, "stressTestor");
        }

        long costTime = System.currentTimeMillis() - startTime;
        System.out.println("create 100 Review instance into informix database takes " + costTime + " ms.");
    }

    /**
     * The stress test of updateReview,
     * Update 100 times.
     *
     * @throws Exception to JUnit
     */
    public void testStressUpdateReview() throws Exception {
        Review review = getReview();
        instance.createReview(review, "stressTestor");

        long startTime = System.currentTimeMillis();

        Item item = null;
        item = getItem();

        for (int i = 0; i < StressTestHelper.LOAD; i++) {
            review.clearItems();
            review.addItem(item);
            instance.updateReview(review, "stressTestor");
        }

        long costTime = System.currentTimeMillis() - startTime;
        System.out.println("updateReview 100 Review instance into informix database takes " + costTime + " ms.");
    }

    /**
     * The stress test of getReview,
     * Review the review for 100 times.
     *
     * @throws Exception to JUnit
     */
    public void testStressgetReview() throws Exception {
        Review review = getReview();
        instance.createReview(review, "stressTestor");

        Review retrievedReview = null;
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < StressTestHelper.LOAD; i++) {
            retrievedReview = instance.getReview(review.getId());
        }

        long costTime = System.currentTimeMillis() - startTime;
        System.out.println("review Review 100 Review instance into informix database takes " + costTime + " ms.");

        assertEquals("The id should be same.", review.getId(), retrievedReview.getId());
        assertEquals("The resource id should be same.", review.getAuthor(), retrievedReview.getAuthor());
        assertEquals("The submission id should be same.", review.getSubmission(), retrievedReview.getSubmission());
        assertEquals("The scorecard id should be same.", review.getScorecard(), retrievedReview.getScorecard());
        assertEquals("The score should be same.", review.getScore(), retrievedReview.getScore());
        assertEquals("The committed state should be same.", review.isCommitted(), retrievedReview.isCommitted());
        assertEquals("The create user should be 'stressTestor'.", "stressTestor", retrievedReview.getCreationUser());
        assertEquals("The modify user should be 'stressTestor'.", "stressTestor", retrievedReview.getModificationUser());

        assertEquals("The number of items should be same.", review.getAllItems().length,
                retrievedReview.getAllItems().length);
        assertEquals("The number of comments should be same.", review.getAllComments().length, retrievedReview
                .getAllComments().length);

        Item item = review.getAllItems()[0];
        Item retrivedItem = retrievedReview.getAllItems()[0];

        // assert the retrieved item
        assertEquals("The id should be same.", item.getId(), retrivedItem.getId());
        assertEquals("The answer should be same.", item.getAnswer(), retrivedItem.getAnswer());
        assertNull("The upload_id should be null.", item.getDocument());
        assertEquals("The question should be same.", item.getQuestion(), retrivedItem.getQuestion());
        assertEquals("The question should be same.", item.getAllComments().length, retrivedItem.getAllComments().length);
    }

    /**
     * The stress test of searchReviews,
     * search for 100 times.
     *
     * @throws Exception to JUnit
     */
    public void testsearchReviews_completeFalse() throws Exception {
        Review review1 = getReview();
        Review review2 = getReview();
        Review review3 = getReview();
        review1.resetId();
        review2.resetId();
        review3.resetId();

        // insert the reviews
        instance.createReview(review1, "stressTestor");
        instance.createReview(review2, "stressTestor");
        instance.createReview(review3, "stressTestor");

        Filter filter = SearchBundle.buildEqualToFilter("committed", new Integer(1));

        Review[] reviews = new Review[0];
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < StressTestHelper.LOAD; i++) {
            reviews = instance.searchReviews(filter, true);
        }
        assertTrue("two review instances should be got from the database.", reviews.length == 2);
        long costTime = System.currentTimeMillis() - startTime;
        System.out.println("search Review for 100 times takes " + costTime + " ms.");
    }

    /**
     * Get a valid review instance.
     *
     * @return a review instance
     */
    private Review getReview() {
        // set all the long properties with currentReviewId
        Review review = new Review(currentReviewId);
        review.setAuthor(currentReviewId);
        review.setScorecard(currentReviewId);
        review.setSubmission(currentReviewId);
        review.setCommitted((currentReviewId & 1) == 1);
        review.setScore(new Float(currentReviewId * 5.0));

        // add comment
        review.addComment(getComment());

        // add one item
        review.addItem(getItem());
        currentReviewId++;

        return review;
    }

    /**
     * Get a valid item instance.
     *
     * @return an item instance
     */
    private Item getItem() {
        Item item = new Item(currentItemId);
        item.setQuestion(currentItemId);
        item.setAnswer("stress" + "answer " + currentItemId);

        item.addComment(getComment());
        currentItemId++;

        return item;
    }

    /**
     * Get a comment instance.
     *
     * @return a comment instance
     */
    private Comment getComment() {
        Comment comment = new Comment(currentCommentId);
        comment.setAuthor(currentCommentId);
        comment.setExtraInfo("stress" + "extraInfo" + currentCommentId);
        comment.setCommentType(new CommentType(currentCommentId, "name" + currentCommentId));
        comment.setComment("stress" + "content" + currentCommentId);

        currentCommentId++;

        return comment;
    }
}
