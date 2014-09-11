/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.data.accuracytests;

import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.CommentType;
import com.topcoder.management.review.data.Item;
import com.topcoder.management.review.data.Review;
import com.topcoder.management.review.data.ReviewEditor;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.Serializable;

import java.util.Date;


/**
 * <p>
 * This accuracy tests addresses the functionality provided by the <code>ReviewEditor</code> class.
 * It tests the ReviewEditor(Review, String), ReviewEditor(String) constructors;
 * addComment(Comment), addComments(Comment[]), addItem(Item), addItems(Item[]) clearComments(),
 * clearItems(), getReview(), getUser(), removeComment(Comment), removeComments(Comment[]),
 * removeItem(Item), removeItems(Item[]), resetAuthor(), resetCommitted(), resetId(),
 * resetScore(), resetScorecard(), resetSubmission(), setAuthor(long), setCommitted(boolean),
 * setId(long), setScore(Float), setScorecard(long), setSubmission(long) methods.
 * </p>
 *
 * @author icyriver
 * @version 1.0
 */
public class ReviewEditorAccuracyTest extends TestCase {
    /**
     * <p>
     * The id of <code>Review</code> instance for test.
     * </p>
     */
    private long id = 1;

    /**
     * <p>
     * The author of <code>Review</code> instance for test.
     * </p>
     */
    private long author = 472;

    /**
     * <p>
     * The submission of <code>Review</code> instance for test.
     * </p>
     */
    private long submission = 12;

    /**
     * <p>
     * The scorecard of <code>Review</code> instance for test.
     * </p>
     */
    private long scorecard = 7;

    /**
     * <p>
     * The creationUser of <code>Review</code> instance for test.
     * </p>
     */
    private String creationUser = "creationReviewer";

    /**
     * <p>
     * The  editor user of <code>Review</code> instance for test.
     * </p>
     */
    private String user = "editorReviewer";

    /**
     * <p>
     * The comment type of <code>Review</code> instance for test.
     * </p>
     */
    private CommentType type1 = new CommentType(1, "Comment");

    /**
     * <p>
     * The comment type of <code>Review</code> instance for test.
     * </p>
     */
    private CommentType type2 = new CommentType(2, "Recommend");

    /**
     * <p>
     * The comment type of <code>Review</code> instance for test.
     * </p>
     */
    private CommentType type3 = new CommentType(3, "Required");

    /**
     * <p>
     * The instance of <code>Comment</code> for test.
     * </p>
     */
    private Comment comm1 = new Comment();

    /**
     * <p>
     * The instance of <code>Comment</code> for test.
     * </p>
     */
    private Comment comm2 = new Comment(1, 472);

    /**
     * <p>
     * The instance of <code>Comment</code> for test.
     * </p>
     */
    private Comment comm3 = new Comment(2, 576, type1, "Nice Review!");

    /**
     * <p>
     * The array of <code>Comment</code> for test.
     * </p>
     */
    private Comment[] comments = null;

    /**
     * <p>
     * The instance of <code>Item</code> for test.
     * </p>
     */
    private Item item1 = null;

    /**
     * <p>
     * The instance of <code>Item</code> for test.
     * </p>
     */
    private Item item2 = null;

    /**
     * <p>
     * The array of <code>Item</code> for test.
     * </p>
     */
    private Item[] items = null;

    /**
     * <p>
     * The instance of <code>Review</code> for test.
     * </p>
     */
    private Review review1 = null;

    /**
     * <p>
     * The instance of <code>Date</code> for test.
     * </p>
     */
    private Date nowTime = new Date();

    /**
     * <p>
     * The instance of <code>ReviewEditor</code> for test.
     * </p>
     */
    private ReviewEditor reviewEditor = null;

    /**
     * <p>
     * Test suite of <code>ReviewEditorAccuracyTest</code>.
     * </p>
     *
     * @return Test suite of <code>ReviewEditorAccuracyTest</code>.
     */
    public static Test suite() {
        return new TestSuite(ReviewEditorAccuracyTest.class);
    }

    /**
     * <p>
     * Set up the accuracy testing envionment.
     * </p>
     */
    protected void setUp() {
        // new the comment array here.
        comments = new Comment[] {comm1, comm2, comm3};

        // new some items here.
        item1 = new Item(5, 8, "See comment");
        item2 = new Item(445, 46, "Great way to solve this problem!");
        item2.setDocument(new Long(14566));
        item2.addComment(new Comment(11, 10001, type2, "This is a great item."));
        items = new Item[] {item1, item2};

        review1 = new Review(id, creationUser, author, submission, scorecard);
    }

    /**
     * <p>
     * Accuracy test of <code>ReviewEditor(String)</code> constructor.
     * </p>
     */
    public void testReviewEditorCtor1() {
        reviewEditor = new ReviewEditor(user);

        // check null here.
        assertNotNull("Create ReviewEditor failed.", reviewEditor);

        // check the type here.
        assertTrue("ReviewEditor should extend Serializable.", reviewEditor instanceof Serializable);

        // check the fields here.
        Review review = reviewEditor.getReview();
        assertEquals("The id should be: -1.", -1, review.getId());
        assertEquals("The author should be: -1.", -1, review.getAuthor());
        assertEquals("The submission should be: -1.", -1, review.getSubmission());
        assertEquals("The scorecard should be: -1.", -1, review.getScorecard());
        assertEquals("The comments' size should be: 0.", 0, review.getNumberOfComments());
        assertEquals("The items' size should be: 0.", 0, review.getNumberOfItems());
        assertFalse("The isCommitted should be false.", review.isCommitted());
        assertEquals("The user should be: " + user + ".", user, reviewEditor.getUser());
        assertNull("The score should be null.", review.getScore());
        assertEquals("The creation user should be: " + user + ".", user, review.getCreationUser());
        assertEquals("The modification user should be: " + user + ".", user,
            review.getModificationUser());
        assertTrue("The creation time stamp is incorrect.",
            compareTime(nowTime, review.getCreationTimestamp()));
        assertTrue("The modification time stamp is incorrect.",
            compareTime(nowTime, review.getModificationTimestamp()));
    }

    /**
     * <p>
     * Accuracy test of <code>ReviewEditor(Review, String)</code> constructor.
     * </p>
     */
    public void testReviewEditorCtor2() {
        reviewEditor = new ReviewEditor(review1, user);

        // check null here.
        assertNotNull("Create ReviewEditor failed.", reviewEditor);

        // check the type here.
        assertTrue("ReviewEditor should extend Serializable.", reviewEditor instanceof Serializable);

        // check the fields here.
        Review review = reviewEditor.getReview();
        assertEquals("The user should be: " + user + ".", user, reviewEditor.getUser());
        assertEquals("The id should be: " + id + ".", id, review.getId());
        assertEquals("The author should be: " + author + ".", author, review.getAuthor());
        assertEquals("The submission should be: " + submission + ".", submission,
            review.getSubmission());
        assertEquals("The scorecard should be: " + scorecard + ".", scorecard, review.getScorecard());
        assertEquals("The comments' size should be: 0.", 0, review.getNumberOfComments());
        assertEquals("The items' size should be: 0.", 0, review.getNumberOfItems());
        assertFalse("The isCommitted should be false.", review.isCommitted());
        assertNull("The score should be null.", review.getScore());
        assertEquals("The creation user is incorrect.", creationUser, review.getCreationUser());
        assertNull("The modification user should be: null.", review.getModificationUser());
        assertNotNull("The creation time stamp should not be null.", review.getCreationTimestamp());
        assertNull("The modification time stamp should be: null.", review.getModificationTimestamp());
    }

    /**
     * <p>
     * Accuracy test of the <code>getReview()</code> method.
     * </p>
     */
    public void testMethod_getReview() {
        reviewEditor = new ReviewEditor(user);

        // check the review here.
        Review review = reviewEditor.getReview();
        assertEquals("The id should be: -1.", -1, review.getId());

        reviewEditor = new ReviewEditor(review1, user);

        // check the review here.
        review = reviewEditor.getReview();
        assertEquals("The id should be: " + id + ".", id, review.getId());
    }

    /**
     * <p>
     * Accuracy test of the <code>getUser()</code> method.
     * </p>
     */
    public void testMethod_getUser() {
        reviewEditor = new ReviewEditor(user);

        // check the user here.
        assertEquals("The user should be: " + user + ".", user, reviewEditor.getUser());

        reviewEditor = new ReviewEditor(review1, user);

        // check the user here.
        assertEquals("The user should be: " + user + ".", user, reviewEditor.getUser());
    }

    /**
     * <p>
     * Accuracy test of the <code>setId()</code> method.
     * </p>
     */
    public void testMethod_setId() {
        // test with constructor1.
        reviewEditor = new ReviewEditor(user);

        // set the id here.
        reviewEditor.setId(100);

        Review review = reviewEditor.getReview();
        assertEquals("The id should be: 100.", 100, review.getId());
        assertTrue("The modification time stamp is incorrect.",
            compareTime(nowTime, review.getModificationTimestamp()));
        assertEquals("The modification user should be: " + user + ".", user,
            review.getModificationUser());

        // test with constructor2.
        reviewEditor = new ReviewEditor(review1, user);
        reviewEditor.setId(101);
        review = reviewEditor.getReview();
        assertEquals("The id should be: 101.", 101, review.getId());
        assertTrue("The modification time stamp is incorrect.",
            compareTime(nowTime, review.getModificationTimestamp()));
        assertEquals("The creation user is incorrect.", creationUser, review.getCreationUser());
        assertEquals("The modification user should be: " + user + ".", user,
            review.getModificationUser());
    }

    /**
     * <p>
     * Accuracy test of the <code>resetId()</code> method.
     * </p>
     */
    public void testMethod_resetId() {
        // test with constructor1.
        reviewEditor = new ReviewEditor(user);

        // set the id here.
        reviewEditor.resetId();

        Review review = reviewEditor.getReview();
        assertEquals("The id should be: -1.", -1, review.getId());
        assertTrue("The modification time stamp is incorrect.",
            compareTime(nowTime, review.getModificationTimestamp()));
        assertEquals("The modification user should be: " + user + ".", user,
            review.getModificationUser());

        // test with constructor2.
        reviewEditor = new ReviewEditor(review1, user);
        reviewEditor.resetId();
        review = reviewEditor.getReview();
        assertEquals("The id should be: -1.", -1, review.getId());
        assertTrue("The modification time stamp is incorrect.",
            compareTime(nowTime, review.getModificationTimestamp()));
        assertEquals("The creation user is incorrect.", creationUser, review.getCreationUser());
        assertEquals("The modification user should be: " + user + ".", user,
            review.getModificationUser());
    }

    /**
     * <p>
     * Accuracy test of the <code>setAuthor(long)</code> method.
     * </p>
     */
    public void testMethod_setAuthor() {
        // test with constructor1.
        reviewEditor = new ReviewEditor(user);
        reviewEditor.setAuthor(100001);

        // check the autho here.
        Review review = reviewEditor.getReview();
        assertEquals("The author should be: 100001.", 100001, review.getAuthor());
        assertTrue("The modification time stamp is incorrect.",
            compareTime(nowTime, review.getModificationTimestamp()));
        assertEquals("The modification user should be: " + user + ".", user,
            review.getModificationUser());

        // test with constructor2.
        reviewEditor = new ReviewEditor(review1, user);
        reviewEditor.setAuthor(100002);

        // check the autho here.
        review = reviewEditor.getReview();
        assertEquals("The author should be: 100002.", 100002, review.getAuthor());
        assertTrue("The modification time stamp is incorrect.",
            compareTime(nowTime, review.getModificationTimestamp()));
        assertEquals("The creation user is incorrect.", creationUser, review.getCreationUser());
        assertEquals("The modification user should be: " + user + ".", user,
            review.getModificationUser());
    }

    /**
     * <p>
     * Accuracy test of the <code>resetAuthor()</code> method.
     * </p>
     */
    public void testMethod_resetAuthor() {
        // test with constructor1.
        reviewEditor = new ReviewEditor(user);

        // set the id here.
        reviewEditor.resetAuthor();

        // check the autho here.
        Review review = reviewEditor.getReview();
        assertEquals("The author should be: -1.", -1, review.getAuthor());
        assertTrue("The modification time stamp is incorrect.",
            compareTime(nowTime, review.getModificationTimestamp()));
        assertEquals("The modification user should be: " + user + ".", user,
            review.getModificationUser());

        // test with constructor2.
        reviewEditor = new ReviewEditor(review1, user);
        reviewEditor.resetAuthor();

        // check the autho here.
        review = reviewEditor.getReview();
        assertEquals("The author should be: -1.", -1, review.getAuthor());
        assertTrue("The modification time stamp is incorrect.",
            compareTime(nowTime, review.getModificationTimestamp()));
        assertEquals("The creation user is incorrect.", creationUser, review.getCreationUser());
        assertEquals("The modification user should be: " + user + ".", user,
            review.getModificationUser());
    }

    /**
     * <p>
     * Accuracy test of the <code>setCommitted(boolean)</code> method.
     * </p>
     */
    public void testMethod_setCommitted() {
        // test with constructor1.
        reviewEditor = new ReviewEditor(user);
        reviewEditor.setCommitted(true);

        // check the isCommitted here.
        Review review = reviewEditor.getReview();
        assertTrue("The isCommitted should be true.", review.isCommitted());
        assertTrue("The modification time stamp is incorrect.",
            compareTime(nowTime, review.getModificationTimestamp()));
        assertEquals("The modification user should be: " + user + ".", user,
            review.getModificationUser());

        // test with constructor2.
        review1.setCommitted(true);
        reviewEditor = new ReviewEditor(review1, user);
        reviewEditor.setCommitted(false);

        // check the isCommitted here.
        review = reviewEditor.getReview();
        assertFalse("The isCommitted should be false.", review.isCommitted());
        assertTrue("The modification time stamp is incorrect.",
            compareTime(nowTime, review.getModificationTimestamp()));
        assertEquals("The creation user is incorrect.", creationUser, review.getCreationUser());
        assertEquals("The modification user should be: " + user + ".", user,
            review.getModificationUser());
    }

    /**
     * <p>
     * Accuracy test of the <code>resetCommitted()</code> method.
     * </p>
     */
    public void testMethod_resetCommitted() {
        // test with constructor1.
        reviewEditor = new ReviewEditor(user);
        reviewEditor.setCommitted(true);
        reviewEditor.resetCommitted();

        // check the isCommitted here.
        Review review = reviewEditor.getReview();
        assertFalse("The isCommitted should be false.", review.isCommitted());
        assertTrue("The modification time stamp is incorrect.",
            compareTime(nowTime, review.getModificationTimestamp()));
        assertEquals("The modification user should be: " + user + ".", user,
            review.getModificationUser());

        // test with constructor2.
        review1.setCommitted(true);
        reviewEditor = new ReviewEditor(review1, user);
        reviewEditor.resetCommitted();

        // check the isCommitted here.
        review = reviewEditor.getReview();
        assertFalse("The isCommitted should be false.", review.isCommitted());
        assertTrue("The modification time stamp is incorrect.",
            compareTime(nowTime, review.getModificationTimestamp()));
        assertEquals("The creation user is incorrect.", creationUser, review.getCreationUser());
        assertEquals("The modification user should be: " + user + ".", user,
            review.getModificationUser());
    }

    /**
     * <p>
     * Accuracy test of the <code>setScore(Float)</code> method.
     * </p>
     */
    public void testMethod_setScore() {
        // test with constructor2.
        review1.setScore(new Float(89.90));
        reviewEditor = new ReviewEditor(review1, user);

        Float score = new Float(92.43);
        reviewEditor.setScore(score);

        // check the score here.
        Review review = reviewEditor.getReview();
        assertEquals("The score is incorrect.", score, review.getScore());
        assertTrue("The modification time stamp is incorrect.",
            compareTime(nowTime, review.getModificationTimestamp()));
        assertEquals("The modification user should be: " + user + ".", user,
            review.getModificationUser());

        // set the score to null.
        reviewEditor.setScore(null);
        review = reviewEditor.getReview();
        assertNull("The score should be null.", review.getScore());
        assertTrue("The modification time stamp is incorrect.",
            compareTime(nowTime, review.getModificationTimestamp()));
        assertEquals("The creation user is incorrect.", creationUser, review.getCreationUser());
        assertEquals("The modification user should be: " + user + ".", user,
            review.getModificationUser());
    }

    /**
     * <p>
     * Accuracy test of the <code>resetScore()</code> method.
     * </p>
     */
    public void testMethod_resetScore() {
        review1.setScore(new Float(89.90));
        reviewEditor = new ReviewEditor(review1, user);
        reviewEditor.resetScore();

        // set the score to null.
        Review review = reviewEditor.getReview();
        assertNull("The score should be null.", review.getScore());
        assertTrue("The modification time stamp is incorrect.",
            compareTime(nowTime, review.getModificationTimestamp()));
        assertEquals("The creation user is incorrect.", creationUser, review.getCreationUser());
        assertEquals("The modification user should be: " + user + ".", user,
            review.getModificationUser());
    }

    /**
     * <p>
     * Accuracy test of the <code>setScorecard(long)</code> method.
     * </p>
     */
    public void testMethod_setScorecard() {
        // test with constructor1.
        reviewEditor = new ReviewEditor(user);
        reviewEditor.setScorecard(200001);

        // check the scorecard here.
        Review review = reviewEditor.getReview();
        assertEquals("The scorecard should be: 200001.", 200001, review.getScorecard());
        assertTrue("The modification time stamp is incorrect.",
            compareTime(nowTime, review.getModificationTimestamp()));
        assertEquals("The modification user should be: " + user + ".", user,
            review.getModificationUser());

        // test with constructor2.
        reviewEditor = new ReviewEditor(review1, user);
        reviewEditor.setScorecard(200002);

        // check the scorecard here.
        review = reviewEditor.getReview();
        assertEquals("The scorecard should be: 200002.", 200002, review.getScorecard());
        assertTrue("The modification time stamp is incorrect.",
            compareTime(nowTime, review.getModificationTimestamp()));
        assertEquals("The creation user is incorrect.", creationUser, review.getCreationUser());
        assertEquals("The modification user should be: " + user + ".", user,
            review.getModificationUser());
    }

    /**
     * <p>
     * Accuracy test of the <code>resetScorecard()</code> method.
     * </p>
     */
    public void testMethod_resetScorecard() {
        // test with constructor1.
        reviewEditor = new ReviewEditor(user);
        reviewEditor.setScorecard(200001);
        reviewEditor.resetScorecard();

        // check the scorecard here.
        Review review = reviewEditor.getReview();
        assertEquals("The scorecard should be: -1.", -1, review.getScorecard());
        assertTrue("The modification time stamp is incorrect.",
            compareTime(nowTime, review.getModificationTimestamp()));
        assertEquals("The modification user should be: " + user + ".", user,
            review.getModificationUser());

        // test with constructor2.
        reviewEditor = new ReviewEditor(review1, user);
        reviewEditor.resetScorecard();

        // check the scorecard here.
        review = reviewEditor.getReview();
        assertEquals("The scorecard should be: -1.", -1, review.getScorecard());
        assertTrue("The modification time stamp is incorrect.",
            compareTime(nowTime, review.getModificationTimestamp()));
        assertEquals("The creation user is incorrect.", creationUser, review.getCreationUser());
        assertEquals("The modification user should be: " + user + ".", user,
            review.getModificationUser());
    }

    /**
     * <p>
     * Accuracy test of the <code>setSubmission(long)</code> method.
     * </p>
     */
    public void testMethod_setSubmission() {
        // test with constructor1.
        reviewEditor = new ReviewEditor(user);
        reviewEditor.setSubmission(300001);

        // check the submission here.
        Review review = reviewEditor.getReview();
        assertEquals("The submission should be: 300001.", 300001, review.getSubmission());
        assertTrue("The modification time stamp is incorrect.",
            compareTime(nowTime, review.getModificationTimestamp()));
        assertEquals("The modification user should be: " + user + ".", user,
            review.getModificationUser());

        // test with constructor2.
        reviewEditor = new ReviewEditor(review1, user);
        reviewEditor.setSubmission(300002);

        // check the submission here.
        review = reviewEditor.getReview();
        assertEquals("The submission should be: 300002.", 300002, review.getSubmission());
        assertTrue("The modification time stamp is incorrect.",
            compareTime(nowTime, review.getModificationTimestamp()));
        assertEquals("The creation user is incorrect.", creationUser, review.getCreationUser());
        assertEquals("The modification user should be: " + user + ".", user,
            review.getModificationUser());
    }

    /**
     * <p>
     * Accuracy test of the <code>resetSubmission(long)</code> method.
     * </p>
     */
    public void testMethod_resetSubmission() {
        // test with constructor1.
        reviewEditor = new ReviewEditor(user);
        reviewEditor.setSubmission(300001);
        reviewEditor.resetSubmission();

        // check the submission here.
        Review review = reviewEditor.getReview();
        assertEquals("The submission should be: -1.", -1, review.getSubmission());
        assertTrue("The modification time stamp is incorrect.",
            compareTime(nowTime, review.getModificationTimestamp()));
        assertEquals("The modification user should be: " + user + ".", user,
            review.getModificationUser());

        // test with constructor2.
        reviewEditor = new ReviewEditor(review1, user);
        reviewEditor.resetSubmission();

        // check the submission here.
        review = reviewEditor.getReview();
        assertEquals("The submission should be: -1.", -1, review.getSubmission());
        assertTrue("The modification time stamp is incorrect.",
            compareTime(nowTime, review.getModificationTimestamp()));
        assertEquals("The creation user is incorrect.", creationUser, review.getCreationUser());
        assertEquals("The modification user should be: " + user + ".", user,
            review.getModificationUser());
    }

    /**
     * <p>
     * Accuracy test of the <code>addComment(Comment)</code> method.
     * </p>
     */
    public void testMethod_addComment() {
        // test with constructor1.
        reviewEditor = new ReviewEditor(user);

        // add each comment here.
        for (int i = 0, j = 1; i < comments.length; i++, j++) {
            reviewEditor.addComment(comments[i]);

            Review review = reviewEditor.getReview();
            assertEquals("The number of comments is incorrect.", j, review.getNumberOfComments());
            assertEquals("The comments[" + i + "] is incorrect.", comments[i], review.getComment(i));
        }

        // check the date here.
        Review review = reviewEditor.getReview();
        assertTrue("The modification time stamp is incorrect.",
            compareTime(nowTime, review.getModificationTimestamp()));
        assertEquals("The modification user should be: " + user + ".", user,
            review.getModificationUser());
    }

    /**
     * <p>
     * Accuracy test of the <code>addComments(Comment[])</code> method.
     * </p>
     */
    public void testMethod_addComments() {
        // test with constructor1.
        reviewEditor = new ReviewEditor(user);

        // add one comment here.
        reviewEditor.addComment(comments[1]);
        assertEquals("The number of comments is incorrect.", 1,
            reviewEditor.getReview().getNumberOfComments());

        // add a comment array here.
        reviewEditor.addComments(comments);

        Review review = reviewEditor.getReview();
        assertEquals("The number of comments is incorrect.", 3, review.getNumberOfComments());
        assertEquals("The comments[0] is incorrect.", comments[1], review.getComment(0));
        assertEquals("The comments[1] is incorrect.", comments[0], review.getComment(1));
        assertEquals("The comments[2] is incorrect.", comments[2], review.getComment(2));

        // check the date here.
        assertTrue("The modification time stamp is incorrect.",
            compareTime(nowTime, review.getModificationTimestamp()));
        assertEquals("The modification user should be: " + user + ".", user,
            review.getModificationUser());
    }

    /**
     * <p>
     * Accuracy test of the <code>removeComment(Comment)</code> method.
     * </p>
     */
    public void testMethod_removeComment() {
        reviewEditor = new ReviewEditor(user);
        reviewEditor.addComments(comments);

        // remove each comment here.
        for (int i = 0, j = 2; i < comments.length; i++, j--) {
            reviewEditor.removeComment(comments[i]);
            assertEquals("The number of comments is incorrect.", j,
                reviewEditor.getReview().getNumberOfComments());
        }

        // remove those comment again.
        for (int i = 0; i < comments.length; i++) {
            reviewEditor.removeComment(comments[i]);
            assertEquals("The number of comments is incorrect.", 0,
                reviewEditor.getReview().getNumberOfComments());
        }

        // check the date here.
        Review review = reviewEditor.getReview();
        assertTrue("The modification time stamp is incorrect.",
            compareTime(nowTime, review.getModificationTimestamp()));
        assertEquals("The modification user should be: " + user + ".", user,
            review.getModificationUser());
    }

    /**
     * <p>
     * Accuracy test of the <code>removeComment(Comment[])</code> method.
     * </p>
     */
    public void testMethod_removeComments() {
        reviewEditor = new ReviewEditor(user);
        reviewEditor.addComments(comments);

        // remove one comment.
        reviewEditor.removeComments(new Comment[] {comm2});
        assertEquals("The number of comments is incorrect.", 2,
            reviewEditor.getReview().getNumberOfComments());

        // add some comments again.
        reviewEditor.addComments(comments);
        assertEquals("The number of comments is incorrect.", 3,
            reviewEditor.getReview().getNumberOfComments());

        // remove some comments here.
        reviewEditor.removeComments(new Comment[] {comm1, comm2});
        assertEquals("The number of comments is incorrect.", 1,
            reviewEditor.getReview().getNumberOfComments());
        assertEquals("The element of comments is incorrect.", comments[2],
            reviewEditor.getReview().getComment(0));

        // check the date here.
        Review review = reviewEditor.getReview();
        assertTrue("The modification time stamp is incorrect.",
            compareTime(nowTime, review.getModificationTimestamp()));
        assertEquals("The modification user should be: " + user + ".", user,
            review.getModificationUser());
    }

    /**
     * <p>
     * Accuracy test of the <code>clearComments()</code> method.
     * </p>
     */
    public void testMethod_clearComments() {
        reviewEditor = new ReviewEditor(user);

        // clear all comments here.
        reviewEditor.clearComments();
        assertEquals("The number of comments is incorrect.", 0,
            reviewEditor.getReview().getNumberOfComments());

        // check the date here.
        Review review = reviewEditor.getReview();
        assertTrue("The modification time stamp is incorrect.",
            compareTime(nowTime, review.getModificationTimestamp()));
        assertEquals("The modification user should be: " + user + ".", user,
            review.getModificationUser());

        // add one comment here.
        reviewEditor.addComment(comments[1]);
        reviewEditor.clearComments();
        assertEquals("The number of comments is incorrect.", 0,
            reviewEditor.getReview().getNumberOfComments());

        // add a comment array here.
        reviewEditor.addComments(comments);
        reviewEditor.clearComments();
        assertEquals("The number of comments is incorrect.", 0,
            reviewEditor.getReview().getNumberOfComments());

        // check the date here.
        review = reviewEditor.getReview();
        assertTrue("The modification time stamp is incorrect.",
            compareTime(nowTime, review.getModificationTimestamp()));
        assertEquals("The modification user should be: " + user + ".", user,
            review.getModificationUser());
    }

    /**
     * <p>
     * Accuracy test of the <code>addItem(Item)</code> method.
     * </p>
     */
    public void testMethod_addItem() {
        reviewEditor = new ReviewEditor(user);

        // add each item here.
        for (int i = 0, j = 1; i < items.length; i++, j++) {
            reviewEditor.addItem(items[i]);
            assertEquals("The number of items is incorrect.", j,
                reviewEditor.getReview().getNumberOfItems());
            assertEquals("The items[" + i + "] is incorrect.", items[i],
                reviewEditor.getReview().getItem(i));
        }

        // add those items here.
        for (int i = 0, j = 1; i < items.length; i++, j++) {
            reviewEditor.addItem(items[i]);
            assertEquals("The number of items is incorrect.", 2,
                reviewEditor.getReview().getNumberOfItems());
            assertEquals("The items[" + i + "] is incorrect.", items[i],
                reviewEditor.getReview().getItem(i));
        }

        // check the date here.
        Review review = reviewEditor.getReview();
        assertTrue("The modification time stamp is incorrect.",
            compareTime(nowTime, review.getModificationTimestamp()));
        assertEquals("The modification user should be: " + user + ".", user,
            review.getModificationUser());
    }

    /**
     * <p>
     * Accuracy test of the <code>addItems(Item[])</code> method.
     * </p>
     */
    public void testMethod_addItems() {
        reviewEditor = new ReviewEditor(user);

        // add one item here.
        reviewEditor.addItem(items[1]);
        assertEquals("The number of items is incorrect.", 1,
            reviewEditor.getReview().getNumberOfItems());

        // add item array here.
        reviewEditor.addItems(items);
        assertEquals("The number of items is incorrect.", 2,
            reviewEditor.getReview().getNumberOfItems());
        assertEquals("The items[0] is incorrect.", items[1], reviewEditor.getReview().getItem(0));
        assertEquals("The items[1] is incorrect.", items[0], reviewEditor.getReview().getItem(1));

        // add item array again here.
        reviewEditor.addItems(new Item[] {item2});
        assertEquals("The number of items is incorrect.", 2,
            reviewEditor.getReview().getNumberOfItems());
        assertEquals("The items[0] is incorrect.", items[1], reviewEditor.getReview().getItem(0));
        assertEquals("The items[1] is incorrect.", items[0], reviewEditor.getReview().getItem(1));

        // check the date here.
        Review review = reviewEditor.getReview();
        assertTrue("The modification time stamp is incorrect.",
            compareTime(nowTime, review.getModificationTimestamp()));
        assertEquals("The modification user should be: " + user + ".", user,
            review.getModificationUser());
    }

    /**
     * <p>
     * Accuracy test of the <code>removeItem(Item)</code> method.
     * </p>
     */
    public void testMethod_removeItem() {
        reviewEditor = new ReviewEditor(user);
        reviewEditor.addItems(items);

        // remove each item here.
        reviewEditor.removeItem(items[1]);
        assertEquals("The number of items is incorrect.", 1,
            reviewEditor.getReview().getNumberOfItems());
        reviewEditor.removeItem(items[0]);
        assertEquals("The number of items is incorrect.", 0,
            reviewEditor.getReview().getNumberOfItems());

        // remove those items again here.
        reviewEditor.removeItem(items[1]);
        assertEquals("The number of items is incorrect.", 0,
            reviewEditor.getReview().getNumberOfItems());

        // check the date here.
        Review review = reviewEditor.getReview();
        assertTrue("The modification time stamp is incorrect.",
            compareTime(nowTime, review.getModificationTimestamp()));
        assertEquals("The modification user should be: " + user + ".", user,
            review.getModificationUser());
    }

    /**
     * <p>
     * Accuracy test of the <code>removeItems(Item[])</code> method.
     * </p>
     */
    public void testMethod_removeItems() {
        reviewEditor = new ReviewEditor(user);
        reviewEditor.addItems(items);

        // remove one item here.
        reviewEditor.removeItems(new Item[] {item2});
        assertEquals("The number of items is incorrect.", 1,
            reviewEditor.getReview().getNumberOfItems());

        // remove some items here.
        reviewEditor.addItems(items);
        reviewEditor.removeItems(items);
        assertEquals("The number of items is incorrect.", 0,
            reviewEditor.getReview().getNumberOfItems());

        // check the date here.
        Review review = reviewEditor.getReview();
        assertTrue("The modification time stamp is incorrect.",
            compareTime(nowTime, review.getModificationTimestamp()));
        assertEquals("The modification user should be: " + user + ".", user,
            review.getModificationUser());
    }

    /**
     * <p>
     * Accuracy test of the <code>clearItems()</code> method.
     * </p>
     */
    public void testMethod_clearItems() {
        reviewEditor = new ReviewEditor(user);

        // add one item here.
        reviewEditor.addItem(items[1]);
        reviewEditor.clearItems();
        assertEquals("The number of items is incorrect.", 0,
            reviewEditor.getReview().getNumberOfItems());

        // add item array here.
        reviewEditor.addItems(items);
        reviewEditor.clearItems();
        assertEquals("The number of items is incorrect.", 0,
            reviewEditor.getReview().getNumberOfItems());

        // check the date here.
        Review review = reviewEditor.getReview();
        assertTrue("The modification time stamp is incorrect.",
            compareTime(nowTime, review.getModificationTimestamp()));
        assertEquals("The modification user should be: " + user + ".", user,
            review.getModificationUser());
    }

    /**
     * <p>
     * Compare the passed two date.
     * </p>
     *
     * @param date1 the first date time.
     * @param date2 the second date time.
     *
     * @return true if date1 less than or equal to date2, otherwise false.
     */
    private boolean compareTime(Date date1, Date date2) {
        return date1.getTime() < date2.getTime();
    }
}
