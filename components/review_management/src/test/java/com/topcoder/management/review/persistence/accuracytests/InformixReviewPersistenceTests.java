/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.persistence.accuracytests;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.CommentType;
import com.topcoder.management.review.data.Item;
import com.topcoder.management.review.data.Review;
import com.topcoder.management.review.persistence.InformixReviewPersistence;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.util.config.ConfigManager;

/**
 * Accuracy test fixture for InformixReviewPersistence class.
 * @author Thinfox
 * @version 1.0
 */
public class InformixReviewPersistenceTests extends TestCase {
    /**
     * Represents the id used for tests.
     */
    private long id;

    /**
     * The IndormixReviewPersistence instance on which to perform tests.
     */
    private InformixReviewPersistence persistence;

    /**
     * Prepares a Review instance used for tests.
     * @return the created Review instance
     */
    private Review createReview() {
        Review review = new Review();
        review.setAuthor(1);
        review.setScorecard(1);
        review.setSubmission(1);
        review.setCommitted(true);
        review.setScore(new Float(90.0));

        CommentType commentType = new CommentType(1, "name1");

        Comment comment = new Comment();
        comment.setAuthor(2);
        comment.setExtraInfo("extraInfo");
        comment.setCommentType(commentType);
        comment.setComment("comment");
        review.addComment(comment);

        Item item = new Item();
        item.setQuestion(1);
        item.setAnswer("answer");

        comment = new Comment();
        comment.setAuthor(3);
        comment.setExtraInfo("extraInfo");
        comment.setCommentType(commentType);
        comment.setComment("comment");
        review.addComment(comment);
        item.addComment(comment);
        review.addItem(item);

        return review;
    }

    /**
     * Validates the equality of two Review instances.
     * @param expected the expected review
     * @param actual the actual review
     */
    private void validateReview(Review expected, Review actual) {
        assertEquals("Incorrect review id.", expected.getId(), actual.getId());
        assertEquals("Incorrect author.", expected.getAuthor(), actual.getAuthor());
        assertEquals("Incorrect submission.", expected.getSubmission(), actual.getSubmission());
        assertEquals("Incorrect scorecard.", expected.getScorecard(), actual.getScorecard());
        assertEquals("Incorrect committed flag.", expected.isCommitted(), actual.isCommitted());
        assertEquals("Incorrect score.", expected.getScore(), actual.getScore());
        assertEquals("Incorrect count of comments.", expected.getNumberOfComments(), actual
            .getNumberOfComments());
        assertEquals("Incorrect count of items.", expected.getNumberOfItems(), actual.getNumberOfItems());
        Map commentMap = new HashMap();
        for (int i = 0; i < expected.getNumberOfComments(); i++) {
            Comment comment = expected.getComment(i);
            commentMap.put(new Long(comment.getId()), comment);
        }
        for (int i = 0; i < expected.getNumberOfComments(); i++) {
            Comment comment = actual.getComment(i);
            Comment expectedComment = (Comment) commentMap.get(new Long(comment.getId()));
            assertNotNull("Unexpected comment.", expectedComment);
            validateComment(expectedComment, comment);
        }
        Map itemMap = new HashMap();
        for (int i = 0; i < expected.getNumberOfItems(); i++) {
            Item item = expected.getItem(i);
            itemMap.put(new Long(item.getId()), item);
        }
        for (int i = 0; i < expected.getNumberOfItems(); i++) {
            Item item = actual.getItem(i);
            Item expectedItem = (Item) itemMap.get(new Long(item.getId()));
            assertNotNull("Unexpected item.", expectedItem);
            validateItem(expectedItem, item);
        }
    }

    /**
     * Validates the equality of two Item instances.
     * @param expected the expected item
     * @param actual the actual item
     */
    private void validateItem(Item expected, Item actual) {
        assertEquals("Incorrect item id.", expected.getId(), actual.getId());
        assertEquals("Incorrect question.", expected.getQuestion(), actual.getQuestion());
        assertEquals("Incorrect answer.", expected.getAnswer(), actual.getAnswer());
        assertEquals("Incorrect document.", expected.getDocument(), actual.getDocument());
        assertEquals("Incorrect count of comments.", expected.getNumberOfComments(), actual
            .getNumberOfComments());
        Map commentMap = new HashMap();
        for (int i = 0; i < expected.getNumberOfComments(); i++) {
            Comment comment = expected.getComment(i);
            commentMap.put(new Long(comment.getId()), comment);
        }
        for (int i = 0; i < expected.getNumberOfComments(); i++) {
            Comment comment = actual.getComment(i);
            Comment expectedComment = (Comment) commentMap.get(new Long(comment.getId()));
            assertNotNull("Unexpected comment.", expectedComment);
            validateComment(expectedComment, comment);
        }
    }

    /**
     * Validates the equality of two Comment instances.
     * @param expected the expected comment
     * @param actual the actual comment
     */
    private void validateComment(Comment expected, Comment actual) {
        assertEquals("Incorrect comment id.", expected.getId(), actual.getId());
        assertEquals("Incorrect author.", expected.getAuthor(), actual.getAuthor());
        assertEquals("Incorrect comment type.", expected.getCommentType().getId(), actual.getCommentType()
            .getId());
        assertEquals("Incorrect comment text.", expected.getComment(), actual.getComment());
        assertEquals("Incorrect extra info.", expected.getExtraInfo(), actual.getExtraInfo());
    }

    /**
     * Gets a valid id.
     * @return a valid id.
     */
    private long getId() {
        return id++;
    }

    /**
     * Gets a valid review instance.
     * @param commentNum the count of comments to create
     * @param itemNum the count of items to create
     * @param itemCommentNum the count of comments to create for each item.
     * @return a review instance
     */
    private Review getReview(int commentNum, int itemNum, int itemCommentNum) {
        long reviewId = getId();

        Review review = new Review(reviewId);
        review.setAuthor(reviewId);
        review.setScorecard(reviewId);
        review.setSubmission(reviewId);
        review.setCommitted((reviewId & 1) > 0);
        review.setScore(new Float(reviewId % 100));

        for (int i = 0; i < commentNum; i++) {
            review.addComment(getComment());
        }

        for (int i = 0; i < itemNum; i++) {
            review.addItem(getItem(itemCommentNum));
        }

        return review;
    }

    /**
     * Gets a valid item instance.
     * @param commentNum the count of comments to create.
     * @return an item instance
     */
    private Item getItem(int commentNum) {
        long itemId = getId();
        Item item = new Item(itemId);
        item.setQuestion(itemId);
        item.setAnswer("answer" + itemId);

        item.addComment(getComment());

        return item;
    }

    /**
     * Gets a comment instance.
     * @return a comment instance
     */
    private Comment getComment() {
        long commentId = getId();
        Comment comment = new Comment(commentId);
        comment.setAuthor(commentId);
        comment.setExtraInfo("extraInfo" + commentId);
        comment.setCommentType(new CommentType(1, "name1"));
        comment.setComment("content" + commentId);
        return comment;
    }

    /**
     * Sets up the test environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.clearConfiguration();
        ConfigManager cm = ConfigManager.getInstance();
        cm.add("accuracy/DBConnectionFactory.xml");
        cm.add("accuracy/SearchBundleManager.xml");
        cm.add("accuracy/InformixPersistence.xml");

        AccuracyTestHelper.initTables();
        persistence = new InformixReviewPersistence();
        id = 1;
    }

    /**
     * Cleans up the test environment.
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        AccuracyTestHelper.clearTables();
        AccuracyTestHelper.clearConfiguration();
    }

    /**
     * Tests the InformixReviewPersistence() constructor.
     * @throws Exception to JUnit
     */
    public void testCtor1() throws Exception {
        assertNotNull("Creation failed.", new InformixReviewPersistence());
    }

    /**
     * Tests the InformixReviewPersistence(String namespace) constructor.
     * @throws Exception to JUnit
     */
    public void testCtor2() throws Exception {
        assertNotNull("Unable to create InformixReviewPersistence.", new InformixReviewPersistence(
            "com.topcoder.management.review.persistence.InformixReviewPersistence"));
    }

    /**
     * Tests the InformixReviewPersistence(DBConnectionFactory dbFactory, String connectionName, SearchBundle
     * searchBundle) constructor.
     * @throws Exception to JUnit
     */
    public void testCtor3() throws Exception {
        assertNotNull("Creation failed.", new InformixReviewPersistence(new DBConnectionFactoryImpl(),
            "InformixConnection", new SearchBundle("bundle", new HashMap(), "context")));
    }

    /**
     * Tests the createReview(Review review, String operator) method.
     * @throws Exception to JUnit
     */
    public void testCreateReview() throws Exception {

        Review review = getReview(2, 2, 2);
        persistence.createReview(review, "user1");

        Review retrieved = persistence.getReview(review.getId());

        assertEquals("Incorrect creation user.", "user1", review.getCreationUser());
        assertTrue("Incorrect creation date.", (new Date().getTime() - review.getCreationTimestamp()
            .getTime()) <= 10 * 60 * 1000);
        assertEquals("Incorrect modification user.", "user1", review.getModificationUser());
        assertTrue("Incorrect modification date.", (new Date().getTime() - review.getModificationTimestamp()
            .getTime()) <= 10 * 60 * 1000);

        validateReview(review, retrieved);
    }

    /**
     * Tests the updateReview(Review review, String operator) method.
     * @throws Exception to JUnit
     */
    public void testUpdateReview() throws Exception {

        Review review = getReview(2, 2, 2);
        persistence.createReview(review, "user1");
        review.addComment(getComment());
        review.clearItems();
        persistence.updateReview(review, "user2");
        Review retrieved = persistence.getReview(review.getId());

        assertEquals("Incorrect creation user.", "user1", review.getCreationUser());
        assertTrue("Incorrect creation date.", (new Date().getTime() - review.getCreationTimestamp()
            .getTime()) <= 10 * 60 * 1000);
        assertEquals("Incorrect modification user.", "user2", review.getModificationUser());
        assertTrue("Incorrect modification date.", (new Date().getTime() - review.getModificationTimestamp()
            .getTime()) <= 10 * 60 * 1000);

        validateReview(review, retrieved);
    }

    /**
     * Tests the createReview(long id) method.
     * @throws Exception to JUnit
     */
    public void testGetReview() throws Exception {
        Review review = getReview(2, 2, 2);
        persistence.createReview(review, "topcoder");

        Review retrieved = persistence.getReview(review.getId());

        assertEquals("Incorrect creation user.", "topcoder", review.getCreationUser());
        assertTrue("Incorrect creation date.", (new Date().getTime() - review.getCreationTimestamp()
            .getTime()) <= 10 * 60 * 1000);
        assertEquals("Incorrect modification user.", "topcoder", review.getModificationUser());
        assertTrue("Incorrect modification date.", (new Date().getTime() - review.getModificationTimestamp()
            .getTime()) <= 10 * 60 * 1000);

        validateReview(review, retrieved);
    }

    /**
     * Tests the getAllCommentTypes() method.
     * @throws Exception to JUnit
     */
    public void testAccuracyGetAllCommentTypes1() throws Exception {
        CommentType[] types = persistence.getAllCommentTypes();

        assertEquals("Incorrect count of types.", 10, types.length);
        for (int i = 0; i < types.length; i++) {
            assertEquals("Incorrect type id.", i + 1, types[i].getId());
            assertEquals("Incorrect type name.", "type" + (i + 1), types[i].getName());
        }
    }

    /**
     * Tests the getAllCommentTypes() method.
     * @throws Exception to JUnit
     */
    public void testAccuracyGetAllCommentTypes2() throws Exception {
        AccuracyTestHelper.clearTables();
        CommentType[] types = persistence.getAllCommentTypes();
        assertEquals("Incorrect count of types.", 0, types.length);
    }

    /**
     * Tests the addReviewComment(long reviewId, Comment comment, String operator) method.
     * @throws Exception to JUnit
     */
    public void testAddReviewComment() throws Exception {
        Review review = getReview(2, 2, 2);
        persistence.createReview(review, "topcoder");

        Comment comment = getComment();
        review.addComment(comment);
        persistence.addReviewComment(review.getId(), comment, "addReviewComment");
        Review retrieved = persistence.getReview(review.getId());

        validateReview(review, retrieved);
    }

    /**
     * Tests the addItemComment(long itemId, Comment comment, String operator) method.
     * @throws Exception to JUnit
     */
    public void testAddItemComment() throws Exception {
        Review review = getReview(2, 2, 2);
        persistence.createReview(review, "topcoder");

        Comment comment = getComment();
        Item item = review.getItem(0);
        item.addComment(comment);
        persistence.addItemComment(item.getId(), comment, "addItemComment");
        Review retrieved = persistence.getReview(review.getId());

        validateReview(review, retrieved);
    }

    /**
     * Tests the searchReviews(Filter filter, boolean complete) method.
     * @throws Exception to JUnit
     */
    public void testAccuracySearchReviews1() throws Exception {
        Review review1 = getReview(1, 1, 1);
        Review review2 = getReview(1, 0, 0);
        persistence.createReview(review1, "topcoder");
        persistence.createReview(review2, "topcoder");

        Review[] reviews = persistence.searchReviews(new EqualToFilter("scorecardType", new Long(review1
            .getId())), true);
        assertEquals("Incorrect count of search results.", 1, reviews.length);
        validateReview(review1, reviews[0]);
    }

    /**
     * Tests the searchReviews(Filter filter, boolean complete) method.
     * @throws Exception to JUnit
     */
    public void testAccuracySearchReviews2() throws Exception {
        Review review1 = getReview(1, 1, 1);
        Review review2 = getReview(1, 0, 0);
        persistence.createReview(review1, "topcoder");
        persistence.createReview(review2, "topcoder");

        Review[] reviews = persistence.searchReviews(new EqualToFilter("scorecardType", new Long(review1
            .getId())), false);
        assertEquals("Incorrect count of search results.", 1, reviews.length);
        review1.clearComments();
        review1.clearItems();
        validateReview(review1, reviews[0]);
    }

    /**
     * Tests the searchReviews(Filter filter, boolean complete) method.
     * @throws Exception to JUnit
     */
    public void testAccuracySearchReviews3() throws Exception {
        Review review1 = getReview(1, 1, 1);
        Review review2 = getReview(1, 0, 0);
        persistence.createReview(review1, "topcoder");
        persistence.createReview(review2, "topcoder");

        Review[] reviews = persistence
            .searchReviews(new EqualToFilter("scorecardType", new Long(999)), false);
        assertEquals("Incorrect count of search results.", 0, reviews.length);
    }

    /**
     * Tests the searchReviews(Filter filter, boolean complete) method.
     * @throws Exception to JUnit
     */
    public void testAccuracySearchReviews4() throws Exception {
        Review review1 = getReview(1, 1, 1);
        Review review2 = getReview(1, 0, 0);
        persistence.createReview(review1, "topcoder");
        persistence.createReview(review2, "topcoder");

        Review[] reviews = persistence.searchReviews(new EqualToFilter("submission",
            new Long(review1.getId())), true);
        assertEquals("Incorrect count of search results.", 1, reviews.length);
        validateReview(review1, reviews[0]);
    }

    /**
     * Tests the searchReviews(Filter filter, boolean complete) method.
     * @throws Exception to JUnit
     */
    public void testAccuracySearchReviews5() throws Exception {
        Review review1 = getReview(1, 1, 1);
        Review review2 = getReview(1, 0, 0);
        persistence.createReview(review1, "topcoder");
        persistence.createReview(review2, "topcoder");

        Review[] reviews = persistence.searchReviews(new EqualToFilter("submission",
            new Long(review1.getId())), false);
        assertEquals("Incorrect count of search results.", 1, reviews.length);
        review1.clearComments();
        review1.clearItems();
        validateReview(review1, reviews[0]);
    }

    /**
     * Tests the searchReviews(Filter filter, boolean complete) method.
     * @throws Exception to JUnit
     */
    public void testAccuracySearchReviews6() throws Exception {
        Review review1 = getReview(1, 1, 1);
        Review review2 = getReview(1, 0, 0);
        persistence.createReview(review1, "topcoder");
        persistence.createReview(review2, "topcoder");

        Review[] reviews = persistence.searchReviews(new EqualToFilter("submission", new Long(999)), false);
        assertEquals("Incorrect count of search results.", 0, reviews.length);
    }

    /**
     * Tests the searchReviews(Filter filter, boolean complete) method.
     * @throws Exception to JUnit
     */
    public void testAccuracySearchReviews7() throws Exception {
        Review review1 = getReview(1, 1, 1);
        Review review2 = getReview(1, 0, 0);
        persistence.createReview(review1, "topcoder");
        persistence.createReview(review2, "topcoder");

        Review[] reviews = persistence.searchReviews(
            new EqualToFilter("reviewer", new Long(review1.getId())), true);
        assertEquals("Incorrect count of search results.", 1, reviews.length);
        validateReview(review1, reviews[0]);
    }

    /**
     * Tests the searchReviews(Filter filter, boolean complete) method.
     * @throws Exception to JUnit
     */
    public void testAccuracySearchReviews8() throws Exception {
        Review review1 = getReview(1, 1, 1);
        Review review2 = getReview(1, 0, 0);
        persistence.createReview(review1, "topcoder");
        persistence.createReview(review2, "topcoder");

        Review[] reviews = persistence.searchReviews(
            new EqualToFilter("reviewer", new Long(review1.getId())), false);
        assertEquals("Incorrect count of search results.", 1, reviews.length);
        review1.clearComments();
        review1.clearItems();
        validateReview(review1, reviews[0]);
    }

    /**
     * Tests the searchReviews(Filter filter, boolean complete) method.
     * @throws Exception to JUnit
     */
    public void testAccuracySearchReviews9() throws Exception {
        Review review1 = getReview(1, 1, 1);
        Review review2 = getReview(1, 0, 0);
        persistence.createReview(review1, "topcoder");
        persistence.createReview(review2, "topcoder");

        Review[] reviews = persistence.searchReviews(new EqualToFilter("reviewer", new Long(999)), false);
        assertEquals("Incorrect count of search results.", 0, reviews.length);
    }

    /**
     * Tests the searchReviews(Filter filter, boolean complete) method.
     * @throws Exception to JUnit
     */
    public void testAccuracySearchReviews10() throws Exception {
        Review review1 = getReview(1, 1, 1);
        Review review2 = getReview(1, 0, 0);
        persistence.createReview(review1, "topcoder");
        persistence.createReview(review2, "topcoder");

        Review[] reviews = persistence.searchReviews(new EqualToFilter("project", new Long(review1.getId())),
            true);
        assertEquals("Incorrect count of search results.", 1, reviews.length);
        validateReview(review1, reviews[0]);
    }

    /**
     * Tests the searchReviews(Filter filter, boolean complete) method.
     * @throws Exception to JUnit
     */
    public void testAccuracySearchReviews11() throws Exception {
        Review review1 = getReview(1, 1, 1);
        Review review2 = getReview(1, 0, 0);
        persistence.createReview(review1, "topcoder");
        persistence.createReview(review2, "topcoder");

        Review[] reviews = persistence.searchReviews(new EqualToFilter("project", new Long(review1.getId())),
            false);
        assertEquals("Incorrect count of search results.", 1, reviews.length);
        review1.clearComments();
        review1.clearItems();
        validateReview(review1, reviews[0]);
    }

    /**
     * Tests the searchReviews(Filter filter, boolean complete) method.
     * @throws Exception to JUnit
     */
    public void testAccuracySearchReviews12() throws Exception {
        Review review1 = getReview(1, 1, 1);
        Review review2 = getReview(1, 0, 0);
        persistence.createReview(review1, "topcoder");
        persistence.createReview(review2, "topcoder");

        Review[] reviews = persistence.searchReviews(new EqualToFilter("project", new Long(999)), false);
        assertEquals("Incorrect count of search results.", 0, reviews.length);
    }

    /**
     * Tests the searchReviews(Filter filter, boolean complete) method.
     * @throws Exception to JUnit
     */
    public void testAccuracySearchReviews13() throws Exception {
        Review review1 = getReview(1, 1, 1);
        Review review2 = getReview(1, 0, 0);
        persistence.createReview(review1, "topcoder");
        persistence.createReview(review2, "topcoder");

        Review[] reviews = persistence.searchReviews(new EqualToFilter("committed", new Long(1)), true);
        assertEquals("Incorrect count of search results.", 2, reviews.length);
    }

    /**
     * Tests the searchReviews(Filter filter, boolean complete) method.
     * @throws Exception to JUnit
     */
    public void testAccuracySearchReviews14() throws Exception {
        Review review1 = getReview(1, 1, 1);
        Review review2 = getReview(1, 0, 0);
        persistence.createReview(review1, "topcoder");
        persistence.createReview(review2, "topcoder");

        Review[] reviews = persistence.searchReviews(new EqualToFilter("committed", new Long(1)), false);
        assertEquals("Incorrect count of search results.", 2, reviews.length);
    }

    /**
     * Tests the searchReviews(Filter filter, boolean complete) method.
     * @throws Exception to JUnit
     */
    public void testAccuracySearchReviews15() throws Exception {
        Review review1 = getReview(1, 1, 1);
        Review review2 = getReview(1, 0, 0);
        persistence.createReview(review1, "topcoder");
        persistence.createReview(review2, "topcoder");

        Review[] reviews = persistence.searchReviews(new EqualToFilter("committed", new Long(0)), false);
        assertEquals("Incorrect count of search results.", 0, reviews.length);
    }
}