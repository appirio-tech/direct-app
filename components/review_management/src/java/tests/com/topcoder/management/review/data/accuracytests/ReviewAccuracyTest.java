/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.data.accuracytests;

import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.CommentType;
import com.topcoder.management.review.data.Item;
import com.topcoder.management.review.data.Review;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.Serializable;

import java.util.Date;


/**
 * <p>
 * This accuracy tests addresses the functionality provided by the <code>Review</code> class. It
 * tests the Review(), Review(long), Review(long, long, long, long), Review(long, String),
 * Review(long, String, long, long, long) constructors;  addComment(Comment),
 * addComments(Comment[]), addItem(Item), addItems(Item[]), clearComments(), clearItems(),
 * getAllComments(), getAllItems(), getAuthor(), getComment(int), getCreationTimestamp(),
 * getCreationUser(), getId(), getItem(int), getModificationTimestamp(), getModificationUser(),
 * getNumberOfComments(), getNumberOfItems(), getScore(), getScorecard(), getSubmission(),
 * isCommitted(), removeComment(Comment), removeComment(long), removeComments(Comment[]),
 * removeItem(Item), removeItem(long), removeItems(Item[]), resetAuthor(), resetCommitted(),
 * resetId(), resetScore(), resetScorecard(), resetSubmission(), setAuthor(long),
 * setCommitted(boolean), setId(long), setScore(Float), setScorecard(long), setSubmission(long),
 * setCreationTimestamp(Date), setCreationUser(String), setModificationTimestamp(Date),
 * setModificationUser(String) methods.
 * </p>
 *
 * @author icyriver
 * @version 1.0
 */
public class ReviewAccuracyTest extends TestCase {
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
     * The modificationUser of <code>Review</code> instance for test.
     * </p>
     */
    private String modificationUser = "modificationReviewer";

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
    private Review review = null;

    /**
     * <p>
     * Test suite of <code>ReviewAccuracyTest</code>.
     * </p>
     *
     * @return Test suite of <code>ReviewAccuracyTest</code>.
     */
    public static Test suite() {
        return new TestSuite(ReviewAccuracyTest.class);
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
    }

    /**
     * <p>
     * Accuracy test of <code>Review()</code> constructor.
     * </p>
     */
    public void testReviewCtor1() {
        review = new Review();

        // check null here.
        assertNotNull("Create Review failed.", review);

        // check the type here.
        assertTrue("Review should extend Serializable.", review instanceof Serializable);

        // check the fields here.
        assertEquals("The id should be: -1.", -1, review.getId());
        assertEquals("The author should be: -1.", -1, review.getAuthor());
        assertEquals("The submission should be: -1.", -1, review.getSubmission());
        assertEquals("The scorecard should be: -1.", -1, review.getScorecard());
        assertEquals("The comments' size should be: 0.", 0, review.getNumberOfComments());
        assertEquals("The items' size should be: 0.", 0, review.getNumberOfItems());
        assertFalse("The isCommitted should be false.", review.isCommitted());
        assertNull("The score should be null.", review.getScore());
        assertNull("The creation user should be: null.", review.getCreationUser());
        assertNull("The modification user should be: null.", review.getModificationUser());
        assertNull("The creation time stamp should be: null.", review.getCreationTimestamp());
        assertNull("The modification time stamp should be: null.", review.getModificationTimestamp());
    }

    /**
     * <p>
     * Accuracy test of <code>Review(long)</code> constructor.
     * </p>
     */
    public void testReviewCtor2() {
        review = new Review(id);

        // check null here.
        assertNotNull("Create Review failed.", review);

        // check the type here.
        assertTrue("Review should extend Serializable.", review instanceof Serializable);

        // check the fields here.
        assertEquals("The id should be: " + id + ".", id, review.getId());
        assertEquals("The author should be: -1.", -1, review.getAuthor());
        assertEquals("The submission should be: -1.", -1, review.getSubmission());
        assertEquals("The scorecard should be: -1.", -1, review.getScorecard());
        assertEquals("The comments' size should be: 0.", 0, review.getNumberOfComments());
        assertEquals("The items' size should be: 0.", 0, review.getNumberOfItems());
        assertFalse("The isCommitted should be false.", review.isCommitted());
        assertNull("The score should be null.", review.getScore());
        assertNull("The creation user should be: null.", review.getCreationUser());
        assertNull("The modification user should be: null.", review.getModificationUser());
        assertNull("The creation time stamp should be: null.", review.getCreationTimestamp());
        assertNull("The modification time stamp should be: null.", review.getModificationTimestamp());
    }

    /**
     * <p>
     * Accuracy test of <code>Review(long, long, long, long)</code> constructor.
     * </p>
     */
    public void testReviewCtor3() {
        review = new Review(id, author, submission, scorecard);

        // check null here.
        assertNotNull("Create Review failed.", review);

        // check the type here.
        assertTrue("Review should extend Serializable.", review instanceof Serializable);

        // check the fields here.
        assertEquals("The id should be: " + id + ".", id, review.getId());
        assertEquals("The author should be: " + author + ".", author, review.getAuthor());
        assertEquals("The submission should be: " + submission + ".", submission,
            review.getSubmission());
        assertEquals("The scorecard should be: " + scorecard + ".", scorecard, review.getScorecard());
        assertEquals("The comments' size should be: 0.", 0, review.getNumberOfComments());
        assertEquals("The items' size should be: 0.", 0, review.getNumberOfItems());
        assertFalse("The isCommitted should be false.", review.isCommitted());
        assertNull("The score should be null.", review.getScore());
        assertNull("The creation user should be: null.", review.getCreationUser());
        assertNull("The modification user should be: null.", review.getModificationUser());
        assertNull("The creation time stamp should be: null.", review.getCreationTimestamp());
        assertNull("The modification time stamp should be: null.", review.getModificationTimestamp());
    }

    /**
     * <p>
     * Accuracy test of <code>Review(long, String)</code> constructor.
     * </p>
     */
    public void testReviewCtor4() {
        review = new Review(id, creationUser);

        // check null here.
        assertNotNull("Create Review failed.", review);

        // check the type here.
        assertTrue("Review should extend Serializable.", review instanceof Serializable);

        // check the fields here.
        assertEquals("The id should be: " + id + ".", id, review.getId());
        assertEquals("The author should be: -1.", -1, review.getAuthor());
        assertEquals("The submission should be: -1.", -1, review.getSubmission());
        assertEquals("The scorecard should be: -1.", -1, review.getScorecard());
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
     * Accuracy test of <code>Review(long, String, long, long, long)</code> constructor.
     * </p>
     */
    public void testReviewCtor5() {
        review = new Review(id, creationUser, author, submission, scorecard);

        // check null here.
        assertNotNull("Create Review failed.", review);

        // check the type here.
        assertTrue("Review should extend Serializable.", review instanceof Serializable);

        // check the fields here.
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
     * Accuracy test of the <code>getId()</code> method.
     * </p>
     */
    public void testMethod_getId() {
        review = new Review();

        // check the fields here.
        assertEquals("The id should be: -1.", -1, review.getId());

        // set the id and then check it.
        review.setId(id);
        assertEquals("The id should be: " + id + ".", id, review.getId());

        // reset the id and then check it.
        review.resetId();
        assertEquals("The id should be: -1.", -1, review.getId());
    }

    /**
     * <p>
     * Accuracy test of the <code>setId()</code> method.
     * </p>
     */
    public void testMethod_setId() {
        review = new Review();

        // set the id and then check it.
        review.setId(id);
        assertEquals("The id should be: " + id + ".", id, review.getId());

        // set the id and then check it.
        review.setId(100);
        assertEquals("The id should be: 100.", 100, review.getId());
    }

    /**
     * <p>
     * Accuracy test of the <code>resetId()</code> method.
     * </p>
     */
    public void testMethod_resetId() {
        review = new Review(id);

        // reset the id and then check it.
        review.resetId();
        assertEquals("The id should be: -1.", -1, review.getId());

        // set the id and then check it.
        review.setId(id);
        review.resetId();
        assertEquals("The id should be: -1.", -1, review.getId());
    }

    /**
     * <p>
     * Accuracy test of the <code>getAuthor()</code> method.
     * </p>
     */
    public void testMethod_getAuthor() {
        review = new Review();

        // check the author here.
        assertEquals("The author should be: -1.", -1, review.getAuthor());

        // set the author and then check it.
        review.setAuthor(author);
        assertEquals("The author should be: " + author + ".", author, review.getAuthor());

        // reset the author and then check it.
        review.resetAuthor();
        assertEquals("The author should be: -1.", -1, review.getAuthor());
    }

    /**
     * <p>
     * Accuracy test of the <code>setAuthor()</code> method.
     * </p>
     */
    public void testMethod_setAuthor() {
        review = new Review();

        // set the author and then check it.
        review.setAuthor(author);
        assertEquals("The author should be: " + author + ".", author, review.getAuthor());

        // set the author and then check it.
        review.setAuthor(10001);
        assertEquals("The author should be: 10001.", 10001, review.getAuthor());
    }

    /**
     * <p>
     * Accuracy test of the <code>resetAuthor()</code> method.
     * </p>
     */
    public void testMethod_resetAuthor() {
        review = new Review(id, author, submission, scorecard);

        // reset the author and then check it.
        review.resetAuthor();
        assertEquals("The author should be: -1.", -1, review.getAuthor());

        // set the author and then check it.
        review.setAuthor(author);
        review.resetAuthor();
        assertEquals("The author should be: -1.", -1, review.getAuthor());
    }

    /**
     * <p>
     * Accuracy test of the <code>getCreationTimestamp()</code> method.
     * </p>
     */
    public void testMethod_getCreationTimestamp() {
        review = new Review();

        // check the time here.
        assertNull("The creation time stamp should be: null.", review.getCreationTimestamp());

        review = new Review(id, creationUser);

        // check the time here.
        assertNotNull("The creation time stamp should not be null.", review.getCreationTimestamp());

        // set the time and then check it.
        Date now = new Date();
        review.setCreationTimestamp(now);
        assertEquals("The creation time stamp is incorrect.", now, review.getCreationTimestamp());
    }

    /**
     * <p>
     * Accuracy test of the <code>setCreationTimestamp()</code> method.
     * </p>
     */
    public void testMethod_setCreationTimestamp() {
        Date now = new Date();

        review = new Review();

        // check the time here.
        review.setCreationTimestamp(now);
        assertEquals("The creation time stamp is incorrect.", now, review.getCreationTimestamp());

        // check the time again.
        review = new Review(id, creationUser);
        review.setCreationTimestamp(now);
        assertEquals("The creation time stamp is incorrect.", now, review.getCreationTimestamp());

        // set the time to null.
        review.setCreationTimestamp(null);
        assertNull("The creation time stamp should be null.", review.getCreationTimestamp());
    }

    /**
     * <p>
     * Accuracy test of the <code>getModificationTimestamp()</code> method.
     * </p>
     */
    public void testMethod_getModificationTimestamp() {
        review = new Review();

        // check the time here.
        assertNull("The modification time stamp should be: null.", review.getModificationTimestamp());

        review = new Review(id, creationUser);

        // check the time here.
        assertNull("The modification time stamp should not be null.",
            review.getModificationTimestamp());

        // set the time and then check it.
        Date now = new Date();
        review.setModificationTimestamp(now);
        assertEquals("The modification time stamp is incorrect.", now,
            review.getModificationTimestamp());
    }

    /**
     * <p>
     * Accuracy test of the <code>setModificationTimestamp()</code> method.
     * </p>
     */
    public void testMethod_setModificationTimestamp() {
        review = new Review();

        // set the time and then check it.
        Date now = new Date();
        review.setModificationTimestamp(now);
        assertEquals("The modification time stamp is incorrect.", now,
            review.getModificationTimestamp());

        // set the time to null.
        review.setModificationTimestamp(null);
        assertNull("The modification time stamp should be null.", review.getModificationTimestamp());
    }

    /**
     * <p>
     * Accuracy test of the <code>isCommitted()</code> method.
     * </p>
     */
    public void testMethod_isCommitted() {
        review = new Review();

        assertFalse("The isCommitted should be false.", review.isCommitted());

        // set to true and then check it.
        review.setCommitted(true);
        assertTrue("The isCommitted should be true.", review.isCommitted());

        // reset to false and then check it.
        review.resetCommitted();
        assertFalse("The isCommitted should be false.", review.isCommitted());
    }

    /**
     * <p>
     * Accuracy test of the <code>setCommitted()</code> method.
     * </p>
     */
    public void testMethod_setCommitted() {
        review = new Review();

        // set to true and then check it.
        review.setCommitted(true);
        assertTrue("The isCommitted should be true.", review.isCommitted());

        // set to false and then check it.
        review.setCommitted(false);
        assertFalse("The isCommitted should be false.", review.isCommitted());
    }

    /**
     * <p>
     * Accuracy test of the <code>resetCommitted()</code> method.
     * </p>
     */
    public void testMethod_resetCommitted() {
        review = new Review();

        // set to true and then check it.
        review.resetCommitted();
        assertFalse("The isCommitted should be false.", review.isCommitted());

        // set to false and then check it.
        review.setCommitted(true);
        review.resetCommitted();
        assertFalse("The isCommitted should be false.", review.isCommitted());
    }

    /**
     * <p>
     * Accuracy test of the <code>getScore()</code> method.
     * </p>
     */
    public void testMethod_getScore() {
        review = new Review(id, creationUser, author, submission, scorecard);

        // check the score here.
        assertNull("The score should be null.", review.getScore());

        // set the score and then check it.
        Float score = new Float(94.58);
        review.setScore(score);
        assertEquals("The score should be: " + score + ".", score, review.getScore());

        // reset the score and then check it.
        review.resetScore();
        assertNull("The score should be null.", review.getScore());
    }

    /**
     * <p>
     * Accuracy test of the <code>setScore()</code> method.
     * </p>
     */
    public void testMethod_setScore() {
        review = new Review(id, creationUser);

        // set the score and then check it.
        Float score = new Float(94.58);
        review.setScore(score);
        assertEquals("The score should be: " + score + ".", score, review.getScore());

        // set the score to null and then check it.
        review.setScore(null);
        assertNull("The score should be null.", review.getScore());
    }

    /**
     * <p>
     * Accuracy test of the <code>resetScore()</code> method.
     * </p>
     */
    public void testMethod_resetScore() {
        review = new Review(id);

        // reset the score to null and then check it.
        review.resetScore();
        assertNull("The score should be null.", review.getScore());

        // set the score and then check it.
        Float score = new Float(94.58);
        review.setScore(score);
        assertEquals("The score should be: " + score + ".", score, review.getScore());

        review.resetScore();
        assertNull("The score should be null.", review.getScore());
    }

    /**
     * <p>
     * Accuracy test of the <code>getScorecard()</code> method.
     * </p>
     */
    public void testMethod_getScorecard() {
        review = new Review(id, creationUser, author, submission, scorecard);

        // check the scorecard here.
        assertEquals("The scorecard should be: " + scorecard + ".", scorecard, review.getScorecard());

        // set the scorecard and then check it.
        review.setScorecard(23);
        assertEquals("The score should be: 23.", 23, review.getScorecard());

        // reset the scorecard and then check it.
        review.resetScorecard();
        assertEquals("The scorecard should be: -1.", -1, review.getScorecard());
    }

    /**
     * <p>
     * Accuracy test of the <code>setScorecard()</code> method.
     * </p>
     */
    public void testMethod_setScorecard() {
        review = new Review(id);

        // check the scorecard here.
        review.setScorecard(scorecard);
        assertEquals("The scorecard should be: " + scorecard + ".", scorecard, review.getScorecard());

        // set the scorecard and then check it.
        review.setScorecard(23);
        assertEquals("The score should be: 23.", 23, review.getScorecard());
    }

    /**
     * <p>
     * Accuracy test of the <code>resetScorecard()</code> method.
     * </p>
     */
    public void testMethod_resetScorecard() {
        review = new Review(id);

        // check the scorecard here.
        review.resetScorecard();
        assertEquals("The scorecard should be: -1.", -1, review.getScorecard());

        // set the scorecard and then check it.
        review.setScorecard(scorecard);
        assertEquals("The scorecard should be: " + scorecard + ".", scorecard, review.getScorecard());

        review.resetScorecard();
        assertEquals("The scorecard should be: -1.", -1, review.getScorecard());
    }

    /**
     * <p>
     * Accuracy test of the <code>getSubmission()</code> method.
     * </p>
     */
    public void testMethod_getSubmission() {
        review = new Review(id, creationUser, author, submission, scorecard);

        // check the submission here.
        assertEquals("The submission should be: " + submission + ".", submission,
            review.getSubmission());

        // set the submission and then check it.
        review.setSubmission(14);
        assertEquals("The submission should be: 14.", 14, review.getSubmission());

        // reset the submission and then check it.
        review.resetSubmission();
        assertEquals("The submission should be: -1.", -1, review.getSubmission());
    }

    /**
     * <p>
     * Accuracy test of the <code>setSubmission()</code> method.
     * </p>
     */
    public void testMethod_setSubmission() {
        review = new Review(id);

        // check the submission here.
        review.setSubmission(submission);
        assertEquals("The submission should be: " + submission + ".", submission,
            review.getSubmission());

        // set the submission and then check it.
        review.setSubmission(14);
        assertEquals("The submission should be: 14.", 14, review.getSubmission());
    }

    /**
     * <p>
     * Accuracy test of the <code>resetSubmission()</code> method.
     * </p>
     */
    public void testMethod_resetSubmission() {
        review = new Review(id, creationUser, author, submission, scorecard);

        // reset the submission and then check it.
        review.resetSubmission();
        assertEquals("The submission should be: -1.", -1, review.getSubmission());

        // set the submission here.
        review.setSubmission(submission);
        assertEquals("The submission should be: " + submission + ".", submission,
            review.getSubmission());

        // reset the submission and then check it.
        review.resetSubmission();
        assertEquals("The submission should be: -1.", -1, review.getSubmission());
    }

    /**
     * <p>
     * Accuracy test of the <code>getCreationUser()</code> method.
     * </p>
     */
    public void testMethod_getCreationUser() {
        review = new Review(id, creationUser, author, submission, scorecard);
        assertEquals("The creation user is incorrect.", creationUser, review.getCreationUser());

        // set the creation user here.
        review.setCreationUser("newuser");
        assertEquals("The creation user is incorrect.", "newuser", review.getCreationUser());
    }

    /**
     * <p>
     * Accuracy test of the <code>setCreationUser()</code> method.
     * </p>
     */
    public void testMethod_setCreationUser() {
        review = new Review(id);
        review.setCreationUser(creationUser);
        assertEquals("The creation user is incorrect.", creationUser, review.getCreationUser());

        // set the creation user to null here.
        review.setCreationUser(null);
        assertNull("The creation user should be null.", review.getCreationUser());

        // set the creation user here.
        review.setCreationUser("newuser");
        assertEquals("The creation user is incorrect.", "newuser", review.getCreationUser());
    }

    /**
     * <p>
     * Accuracy test of the <code>getModificationUser()</code> method.
     * </p>
     */
    public void testMethod_getModificationUser() {
        review = new Review(id, creationUser, author, submission, scorecard);
        assertNull("The modification user should be null.", review.getModificationUser());

        // set the creation user here.
        review.setModificationUser("newuser");
        assertEquals("The modification user is incorrect.", "newuser", review.getModificationUser());
    }

    /**
     * <p>
     * Accuracy test of the <code>setModificationUser()</code> method.
     * </p>
     */
    public void testMethod_setModificationUser() {
        review = new Review(id, creationUser);

        // set the creation user here.
        review.setModificationUser("newuser");
        assertEquals("The modification user is incorrect.", "newuser", review.getModificationUser());

        // set the modification user to null here.
        review.setModificationUser(null);
        assertNull("The modification user should be null.", review.getModificationUser());
    }

    /**
     * <p>
     * Accuracy test of the <code>addComment(Comment)</code> method.
     * </p>
     */
    public void testMethod_addComment() {
        review = new Review(id, creationUser);

        // add each comment here.
        for (int i = 0, j = 1; i < comments.length; i++, j++) {
            review.addComment(comments[i]);
            assertEquals("The number of comments is incorrect.", j, review.getNumberOfComments());
            assertEquals("The comments[" + i + "] is incorrect.", comments[i], review.getComment(i));
        }

        // add those comment again here.
        for (int i = 0; i < comments.length; i++) {
            review.addComment(comments[i]);
            assertEquals("The number of comments is incorrect.", 3, review.getNumberOfComments());
            assertEquals("The comments[" + i + "] is incorrect.", comments[i], review.getComment(i));
        }
    }

    /**
     * <p>
     * Accuracy test of the <code>addComments(Comment[])</code> method.
     * </p>
     */
    public void testMethod_addComments() {
        review = new Review(id, creationUser);

        // add one comment here.
        review.addComment(comments[1]);
        assertEquals("The number of comments is incorrect.", 1, review.getNumberOfComments());

        // add a comment array here.
        review.addComments(comments);
        assertEquals("The number of comments is incorrect.", 3, review.getNumberOfComments());
        assertEquals("The comments[0] is incorrect.", comments[1], review.getComment(0));
        assertEquals("The comments[1] is incorrect.", comments[0], review.getComment(1));
        assertEquals("The comments[2] is incorrect.", comments[2], review.getComment(2));

        // add those comment again here.
        review.addComments(new Comment[] {comm1, comm2});
        assertEquals("The number of comments is incorrect.", 3, review.getNumberOfComments());
        assertEquals("The comments[0] is incorrect.", comments[1], review.getComment(0));
        assertEquals("The comments[1] is incorrect.", comments[0], review.getComment(1));
        assertEquals("The comments[2] is incorrect.", comments[2], review.getComment(2));
    }

    /**
     * <p>
     * Accuracy test of the <code>removeComment(Comment)</code> method.
     * </p>
     */
    public void testMethod_removeComment1() {
        review = new Review(id, creationUser);
        review.addComments(comments);

        // remove each comment here.
        for (int i = 0, j = 2; i < comments.length; i++, j--) {
            review.removeComment(comments[i]);
            assertEquals("The number of comments is incorrect.", j, review.getNumberOfComments());
        }

        // remove those comment again.
        for (int i = 0; i < comments.length; i++) {
            review.removeComment(comments[i]);
            assertEquals("The number of comments is incorrect.", 0, review.getNumberOfComments());
        }
    }

    /**
     * <p>
     * Accuracy test of the <code>removeComment(long)</code> method.
     * </p>
     */
    public void testMethod_removeComment2() {
        review = new Review(id, creationUser);
        review.addComment(new Comment());
        review.addComments(comments);

        // remove each comment here.
        for (int i = 0, j = 3; i < comments.length; i++, j--) {
            review.removeComment(comments[i].getId());
            assertEquals("The number of comments is incorrect.", j, review.getNumberOfComments());
        }

        review.removeComment(-1);
        assertEquals("The number of comments is incorrect.", 0, review.getNumberOfComments());

        // remove those comment again.
        for (int i = 0; i < comments.length; i++) {
            review.removeComment(comments[i].getId());
            assertEquals("The number of comments is incorrect.", 0, review.getNumberOfComments());
        }

        // remove inexistent comment here.
        review.removeComment(-1);
        review.removeComment(Long.MAX_VALUE);
    }

    /**
     * <p>
     * Accuracy test of the <code>removeComment(Comment[])</code> method.
     * </p>
     */
    public void testMethod_removeComments() {
        review = new Review(id, creationUser);
        review.addComments(comments);

        // remove one comment.
        review.removeComments(new Comment[] {comm2});
        assertEquals("The number of comments is incorrect.", 2, review.getNumberOfComments());

        // add some comments again.
        review.addComments(comments);
        assertEquals("The number of comments is incorrect.", 3, review.getNumberOfComments());

        // remove some comments here.
        review.removeComments(new Comment[] {comm1, comm2});
        assertEquals("The number of comments is incorrect.", 1, review.getNumberOfComments());
        assertEquals("The element of comments is incorrect.", comments[2], review.getComment(0));
    }

    /**
     * <p>
     * Accuracy test of the <code>clearComments()</code> method.
     * </p>
     */
    public void testMethod_clearComments() {
        review = new Review(id, creationUser);

        // clear all comments here.
        review.clearComments();
        assertEquals("The number of comments is incorrect.", 0, review.getNumberOfComments());

        // add one comment here.
        review.addComment(comments[1]);
        review.clearComments();
        assertEquals("The number of comments is incorrect.", 0, review.getNumberOfComments());

        // add a comment array here.
        review.addComments(comments);
        review.clearComments();
        assertEquals("The number of comments is incorrect.", 0, review.getNumberOfComments());
    }

    /**
     * <p>
     * Accuracy test of the <code>getAllComments()</code> method.
     * </p>
     */
    public void testMethod_getAllComments() {
        review = new Review(id, creationUser);

        // check the empty comment array here.
        Comment[] allComments = review.getAllComments();
        assertEquals("The number of comments is incorrect.", 0, allComments.length);

        // add one comment here.
        review.addComment(comments[1]);
        allComments = review.getAllComments();
        assertEquals("The number of comments is incorrect.", 1, allComments.length);
        assertEquals("The comments[0] is incorrect.", comments[1], allComments[0]);

        // add a comment array here.
        review.addComments(comments);
        allComments = review.getAllComments();
        assertEquals("The number of comments is incorrect.", 3, allComments.length);
        assertEquals("The comments[0] is incorrect.", comments[1], review.getComment(0));
        assertEquals("The comments[1] is incorrect.", comments[0], review.getComment(1));
        assertEquals("The comments[2] is incorrect.", comments[2], review.getComment(2));
    }

    /**
     * <p>
     * Accuracy test of the <code>getNumberOfComments()</code> method.
     * </p>
     */
    public void testMethod_getNumberOfComments() {
        review = new Review(id, creationUser);

        assertEquals("The number of comments is incorrect.", 0, review.getNumberOfComments());

        // add one comment here.
        review.addComment(comments[1]);
        assertEquals("The number of comments is incorrect.", 1, review.getNumberOfComments());

        // add a comment array here.
        review.addComments(comments);
        assertEquals("The number of comments is incorrect.", 3, review.getNumberOfComments());
    }

    /**
     * <p>
     * Accuracy test of the <code>addItem(Item)</code> method.
     * </p>
     */
    public void testMethod_addItem() {
        review = new Review(id, creationUser);

        // add each item here.
        for (int i = 0, j = 1; i < items.length; i++, j++) {
            review.addItem(items[i]);
            assertEquals("The number of items is incorrect.", j, review.getNumberOfItems());
            assertEquals("The items[" + i + "] is incorrect.", items[i], review.getItem(i));
        }

        // add those items here.
        for (int i = 0, j = 1; i < items.length; i++, j++) {
            review.addItem(items[i]);
            assertEquals("The number of items is incorrect.", 2, review.getNumberOfItems());
            assertEquals("The items[" + i + "] is incorrect.", items[i], review.getItem(i));
        }
    }

    /**
     * <p>
     * Accuracy test of the <code>addItems(Item[])</code> method.
     * </p>
     */
    public void testMethod_addItems() {
        review = new Review(id, creationUser);

        // add one item here.
        review.addItem(items[1]);
        assertEquals("The number of items is incorrect.", 1, review.getNumberOfItems());

        // add item array here.
        review.addItems(items);
        assertEquals("The number of items is incorrect.", 2, review.getNumberOfItems());
        assertEquals("The items[0] is incorrect.", items[1], review.getItem(0));
        assertEquals("The items[1] is incorrect.", items[0], review.getItem(1));

        // add item array again here.
        review.addItems(new Item[] {item2});
        assertEquals("The number of items is incorrect.", 2, review.getNumberOfItems());
        assertEquals("The items[0] is incorrect.", items[1], review.getItem(0));
        assertEquals("The items[1] is incorrect.", items[0], review.getItem(1));
    }

    /**
     * <p>
     * Accuracy test of the <code>removeItem(Item)</code> method.
     * </p>
     */
    public void testMethod_removeItem1() {
        review = new Review(id, creationUser);
        review.addItems(items);

        // remove each item here.
        review.removeItem(items[1]);
        assertEquals("The number of items is incorrect.", 1, review.getNumberOfItems());
        review.removeItem(items[0]);
        assertEquals("The number of items is incorrect.", 0, review.getNumberOfItems());

        // remove those items again here.
        review.removeItem(items[1]);
        assertEquals("The number of items is incorrect.", 0, review.getNumberOfItems());
    }

    /**
     * <p>
     * Accuracy test of the <code>removeItem(long)</code> method.
     * </p>
     */
    public void testMethod_removeItem2() {
        review = new Review(id, creationUser);
        review.addItems(items);
        review.addItem(new Item());
        review.addItem(new Item());
        review.addItem(new Item(5));

        // remove each item with its id here.
        review.removeItem(items[1].getId());
        assertEquals("The number of items is incorrect.", 4, review.getNumberOfItems());
        review.removeItem(items[0].getId());
        assertEquals("The number of items is incorrect.", 3, review.getNumberOfItems());
        review.removeItem(items[0].getId());
        assertEquals("The number of items is incorrect.", 2, review.getNumberOfItems());
        review.removeItem(-1);
        assertEquals("The number of items is incorrect.", 1, review.getNumberOfItems());
        review.removeItem(items[1].getId());
        review.removeItem(Long.MAX_VALUE);
        review.removeItem(-1);
        assertEquals("The number of items is incorrect.", 0, review.getNumberOfItems());
    }

    /**
     * <p>
     * Accuracy test of the <code>removeItems(Item[])</code> method.
     * </p>
     */
    public void testMethod_removeItems() {
        review = new Review(id, creationUser);
        review.addItems(items);

        // remove one item here.
        review.removeItems(new Item[] {item2});
        assertEquals("The number of items is incorrect.", 1, review.getNumberOfItems());

        // remove some items here.
        review.addItems(items);
        review.removeItems(items);
        assertEquals("The number of items is incorrect.", 0, review.getNumberOfItems());
    }

    /**
     * <p>
     * Accuracy test of the <code>clearItems()</code> method.
     * </p>
     */
    public void testMethod_clearItems() {
        review = new Review(id);

        // add one item here.
        review.addItem(items[1]);
        review.clearItems();
        assertEquals("The number of items is incorrect.", 0, review.getNumberOfItems());

        // add item array here.
        review.addItems(items);
        review.clearItems();
        assertEquals("The number of items is incorrect.", 0, review.getNumberOfItems());
    }

    /**
     * <p>
     * Accuracy test of the <code>getAllItems()</code> method.
     * </p>
     */
    public void testMethod_getAllItems() {
        review = new Review(id);

        // check empty item array here.
        Item[] allItems = review.getAllItems();
        assertEquals("The number of items is incorrect.", 0, review.getNumberOfItems());

        // add one item here.
        review.addItem(items[1]);
        allItems = review.getAllItems();
        assertEquals("The number of items is incorrect.", 1, review.getNumberOfItems());
        assertEquals("The allItems[0] is incorrect.", items[1], allItems[0]);

        // add item array here.
        review.addItems(items);
        allItems = review.getAllItems();
        assertEquals("The number of items is incorrect.", 2, allItems.length);
        assertEquals("The allItems[0] is incorrect.", items[1], allItems[0]);
        assertEquals("The allItems[1] is incorrect.", items[0], allItems[1]);
    }

    /**
     * <p>
     * Accuracy test of the <code>getNumberOfItems()</code> method.
     * </p>
     */
    public void testMethod_getNumberOfItems() {
        review = new Review(id);
        assertEquals("The number of items is incorrect.", 0, review.getNumberOfItems());

        // add one item here.
        review.addItem(items[1]);
        assertEquals("The number of items is incorrect.", 1, review.getNumberOfItems());

        // add item array here.
        review.addItems(items);
        assertEquals("The number of items is incorrect.", 2, review.getNumberOfItems());
    }
}
