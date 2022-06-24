/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import junit.framework.TestCase;

/**
 * Unit tests for <code>Review</code> class.
 *
 * @author vividmxx
 * @version 1.0
 */
public class ReviewUnitTests extends TestCase {
    /**
     * The <code>Review</code> instance used to test against.
     */
    private Review review = null;

    /**
     * The <code>Float</code> instance used for test.
     */
    private Float score = new Float("20.06");

    /**
     * The <code>Comment</code> instance used for test.
     */
    private Comment comm1 = new Comment();

    /**
     * The <code>Comment</code> instance used for test.
     */
    private Comment comm2 = new Comment(100);

    /**
     * The <code>Item</code> instance used for test.
     */
    private Item item1 = new Item();

    /**
     * The <code>Item</code> instance used for test.
     */
    private Item item2 = new Item(100);

    /**
     * The <code>Date</code> instance used for test.
     */
    private Date date = new Date();

    /**
     * Set up the test environment.
     */
    protected void setUp() {
        review = new Review(20060624, 1, 2, 3);
        review.setScore(score);
        review.addItem(item1);
        review.addItem(item2);
        review.addComment(comm1);
        review.addComment(comm2);
        review.setCreationUser("Creation");
        review.setCreationTimestamp(date);
        review.setModificationUser("Modification");
        review.setModificationTimestamp(date);
    }

    /**
     * Test whether <code>Review</code> class implements the <code>Serializable</code> interface.
     */
    public void testImplements() {
        assertTrue("Review should implement Serializable interface", review instanceof Serializable);
    }

    /**
     * Test the constructor <code>Review()</code>, all fields should have their default unassigned values.
     */
    public void testConstructorWithNoArgu() {
        Review rv = new Review();
        assertNotNull("Review instance should be created", rv);
        assertTrue("id field should be -1", rv.getId() == -1);
        assertTrue("author field should be -1", rv.getAuthor() == -1);
        assertTrue("submission field should be -1", rv.getSubmission() == -1);
        assertTrue("scorecard field should be -1", rv.getScorecard() == -1);
        assertFalse("committed field should be false", rv.isCommitted());
        assertNull("score field should be null", rv.getScore());
        assertTrue("items field should be empty", rv.getNumberOfItems() == 0);
        assertTrue("comments field should be empty", rv.getNumberOfComments() == 0);
        assertNull("creationUser field should be null", rv.getCreationUser());
        assertNull("creationTimestamp field should be null", rv.getCreationTimestamp());
        assertNull("modificationUser field should be null", rv.getModificationUser());
        assertNull("modificationTimestamp field should be null", rv.getModificationTimestamp());
    }

    /**
     * Test the constructor <code>Review(long)</code> with negative id, <code>IllegalArgumentException</code> should
     * be thrown.
     */
    public void testConstructorWithNegativeId1() {
        try {
            new Review(-20060624);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Review(long)</code> with zero id, <code>IllegalArgumentException</code> should be
     * thrown.
     */
    public void testConstructorWithZeroId1() {
        try {
            new Review(0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Review(long)</code> with positive id, instance of <code>Review</code> should be
     * created.
     */
    public void testConstructorWithPositiveId() {
        Review rv = new Review(2006);
        assertNotNull("Review instance should be created", rv);
        assertTrue("id field should be 2006", rv.getId() == 2006);
        assertTrue("author field should be -1", rv.getAuthor() == -1);
        assertTrue("submission field should be -1", rv.getSubmission() == -1);
        assertTrue("scorecard field should be -1", rv.getScorecard() == -1);
        assertFalse("committed field should be false", rv.isCommitted());
        assertNull("score field should be null", rv.getScore());
        assertTrue("items field should be empty", rv.getNumberOfItems() == 0);
        assertTrue("comments field should be empty", rv.getNumberOfComments() == 0);
        assertNull("creationUser field should be null", rv.getCreationUser());
        assertNull("creationTimestamp field should be null", rv.getCreationTimestamp());
        assertNull("modificationUser field should be null", rv.getModificationUser());
        assertNull("modificationTimestamp field should be null", rv.getModificationTimestamp());
    }

    /**
     * Test the constructor <code>Review(long, String)</code> with negative id, <code>IllegalArgumentException</code>
     * should be thrown.
     */
    public void testConstructorWithNegativeIdAndNonNullUser() {
        try {
            new Review(-20060624, "userA");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Review(long, String)</code> with zero id, <code>IllegalArgumentException</code>
     * should be thrown.
     */
    public void testConstructorWithZeroIdAndNonNullUser() {
        try {
            new Review(0, "userA");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Review(long, String)</code> with null creationUser,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithPositiveIdAndNullUser() {
        try {
            new Review(2006, null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Review(long, String)</code> with positive id and non-null creationUser, instance of
     * <code>Review</code> should be created.
     */
    public void testConstructorWithPositiveIdAndNonNullUser() {
        Review rv = new Review(2006, "userA");
        assertNotNull("Review instance should be created", rv);
        assertTrue("id field should be 2006", rv.getId() == 2006);
        assertTrue("author field should be -1", rv.getAuthor() == -1);
        assertTrue("submission field should be -1", rv.getSubmission() == -1);
        assertTrue("scorecard field should be -1", rv.getScorecard() == -1);
        assertFalse("committed field should be false", rv.isCommitted());
        assertNull("score field should be null", rv.getScore());
        assertTrue("items field should be empty", rv.getNumberOfItems() == 0);
        assertTrue("comments field should be empty", rv.getNumberOfComments() == 0);
        assertTrue("creationUser field should be 'userA'", rv.getCreationUser().equals("userA"));
        assertNotNull("creationTimestamp field should not be null", rv.getCreationTimestamp());
        assertNull("modificationUser field should be null", rv.getModificationUser());
        assertNull("modificationTimestamp field should be null", rv.getModificationTimestamp());

        // empty creationUser is acceptable
        Review rv1 = new Review(2006, "");
        assertNotNull("Review instance should be created", rv1);
        assertTrue("creationUser field should be ''", rv1.getCreationUser().equals(""));
        assertNotNull("creationTimestamp field should not be null", rv1.getCreationTimestamp());

        // all whitespace creationUser is acceptable
        Review rv2 = new Review(2006, "   ");
        assertNotNull("Review instance should be created", rv2);
        assertTrue("creationUser field should be '   '", rv2.getCreationUser().equals("   "));
        assertNotNull("creationTimestamp field should not be null", rv2.getCreationTimestamp());
    }

    /**
     * Test the constructor <code>Review(long, long, long, long)</code> with negative id,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithNegativeId2() {
        try {
            new Review(-20060624, 1, 2, 3);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Review(long, long, long, long)</code> with zero id,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithZeroId2() {
        try {
            new Review(0, 1, 2, 3);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Review(long, long, long, long)</code> with negative author,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithNegativeAuthor1() {
        try {
            new Review(2006, -1, 2, 3);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Review(long, long, long, long)</code> with zero author,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithZeroAuthor1() {
        try {
            new Review(2006, 0, 2, 3);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Review(long, long, long, long)</code> with negative submission,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithNegativeSubmission1() {
        try {
            new Review(2006, 1, -2, 3);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Review(long, long, long, long)</code> with zero submission,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithZeroSubmission1() {
        try {
            new Review(2006, 1, 0, 3);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Review(long, long, long, long)</code> with negative scorecard,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithNegativeScorecard1() {
        try {
            new Review(2006, 1, 2, -3);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Review(long, long, long, long)</code> with zero scorecard,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithZeroScorecard1() {
        try {
            new Review(2006, 1, 2, 0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Review(long, long, long, long)</code> with valid arguments, instance of
     * <code>Review</code> should be created.
     */
    public void testConstructorWithPositiveLongs() {
        Review rv = new Review(2006, 1, 2, 3);
        assertNotNull("Review instance should be created", rv);
        assertTrue("id field should be 2006", rv.getId() == 2006);
        assertTrue("author field should be 1", rv.getAuthor() == 1);
        assertTrue("submission field should be 2", rv.getSubmission() == 2);
        assertTrue("scorecard field should be 3", rv.getScorecard() == 3);
        assertFalse("committed field should be false", rv.isCommitted());
        assertNull("score field should be null", rv.getScore());
        assertTrue("items field should be empty", rv.getNumberOfItems() == 0);
        assertTrue("comments field should be empty", rv.getNumberOfComments() == 0);
        assertNull("creationUser field should be null", rv.getCreationUser());
        assertNull("creationTimestamp field should be null", rv.getCreationTimestamp());
        assertNull("modificationUser field should be null", rv.getModificationUser());
        assertNull("modificationTimestamp field should be null", rv.getModificationTimestamp());
    }

    /**
     * Test the constructor <code>Review(long, String, long, long, long)</code> with negative id,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithNegativeId3() {
        try {
            new Review(-20060624, "userA", 1, 2, 3);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Review(long, String, long, long, long)</code> with zero id,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithZeroId3() {
        try {
            new Review(0, "userA", 1, 2, 3);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Review(long, String, long, long, long)</code> with negative author,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithNegativeAuthor2() {
        try {
            new Review(2006, "userA", -1, 2, 3);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Review(long, String, long, long, long)</code> with zero author,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithZeroAuthor2() {
        try {
            new Review(2006, "userA", 0, 2, 3);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Review(long, String, long, long, long)</code> with negative submission,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithNegativeSubmission2() {
        try {
            new Review(2006, "userA", 1, -2, 3);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Review(long, String, long, long, long)</code> with zero submission,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithZeroSubmission2() {
        try {
            new Review(2006, "userA", 1, 0, 3);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Review(long, String, long, long, long)</code> with negative scorecard,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithNegativeScorecard2() {
        try {
            new Review(2006, "userA", 1, 2, -3);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Review(long, String, long, long, long)</code> with zero scorecard,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithZeroScorecard2() {
        try {
            new Review(2006, "userA", 1, 2, 0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Review(long, String, long, long, long)</code> with null creationUser,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithNullUser() {
        try {
            new Review(2006, null, 1, 2, 3);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Review(long, String, long, long, long)</code> with valid arguments, instance of
     * <code>Review</code> should be created.
     */
    public void testConstructorWithPositiveLongsAndNonNullUser() {
        Review rv = new Review(2006, "userA", 1, 2, 3);
        assertNotNull("Review instance should be created", rv);
        assertTrue("id field should be 2006", rv.getId() == 2006);
        assertTrue("author field should be 1", rv.getAuthor() == 1);
        assertTrue("submission field should be 2", rv.getSubmission() == 2);
        assertTrue("scorecard field should be 3", rv.getScorecard() == 3);
        assertFalse("committed field should be false", rv.isCommitted());
        assertNull("score field should be null", rv.getScore());
        assertTrue("items field should be empty", rv.getNumberOfItems() == 0);
        assertTrue("comments field should be empty", rv.getNumberOfComments() == 0);
        assertTrue("creationUser field should be 'userA'", rv.getCreationUser().equals("userA"));
        assertNotNull("creationTimestamp field should not be null", rv.getCreationTimestamp());
        assertNull("modificationUser field should be null", rv.getModificationUser());
        assertNull("modificationTimestamp field should be null", rv.getModificationTimestamp());

        // empty creationUser is acceptable
        Review rv1 = new Review(2006, "", 1, 2, 3);
        assertNotNull("Review instance should be created", rv1);
        assertTrue("creationUser field should be ''", rv1.getCreationUser().equals(""));
        assertNotNull("creationTimestamp field should not be null", rv1.getCreationTimestamp());

        // all whitespace creationUser is acceptable
        Review rv2 = new Review(2006, "   ", 1, 2, 3);
        assertNotNull("Review instance should be created", rv2);
        assertTrue("creationUser field should be '   '", rv2.getCreationUser().equals("   "));
        assertNotNull("creationTimestamp field should not be null", rv2.getCreationTimestamp());
    }

    /**
     * Test the method <code>setId(long)</code> with negative id, <code>IllegalArgumentException</code> should be
     * thrown.
     */
    public void testSetIdWithNegativeId() {
        try {
            review.setId(-2006);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setId(long)</code> with zero id, <code>IllegalArgumentException</code> should be
     * thrown.
     */
    public void testSetIdWithZeroId() {
        try {
            review.setId(0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setId(long)</code> with positive id, the id field should be set successfully.
     */
    public void testSetIdWithPositiveId() {
        review.setId(2006);
        assertTrue("id field should be 2006", review.getId() == 2006);
    }

    /**
     * Test the method <code>getId()</code>, the id value should be returned successfully.
     */
    public void testGetId() {
        assertTrue("getId method should return 20060624", review.getId() == 20060624);
    }

    /**
     * Test the method <code>resetId()</code>, the id field should be set to -1.
     */
    public void testResetId() {
        review.resetId();
        assertTrue("id field should be -1", review.getId() == -1);
    }

    /**
     * Test the method <code>setAuthor(long)</code> with negative author, <code>IllegalArgumentException</code>
     * should be thrown.
     */
    public void testSetAuthorWithNegativeAuthor() {
        try {
            review.setAuthor(-1);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setAuthor(long)</code> with zero author, <code>IllegalArgumentException</code> should
     * be thrown.
     */
    public void testSetAuthorWithZeroAuthor() {
        try {
            review.setAuthor(0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setAuthor(long)</code> with positive author, the author field should be set successfully.
     */
    public void testSetAuthorWithPositiveAuthor() {
        review.setAuthor(100);
        assertTrue("author field should be 100", review.getAuthor() == 100);
    }

    /**
     * Test the method <code>getAuthor()</code>, the author value should be returned successfully.
     */
    public void testGetAuthor() {
        assertTrue("getAuthor method should return 1", review.getAuthor() == 1);
    }

    /**
     * Test the method <code>resetAuthor()</code>, the author field should be set to -1.
     */
    public void testResetAuthor() {
        review.resetAuthor();
        assertTrue("author field should be -1", review.getAuthor() == -1);
    }

    /**
     * Test the method <code>setSubmission(long)</code> with negative submission,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testSetSubmissionWithNegativeSubmission() {
        try {
            review.setSubmission(-2);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setSubmission(long)</code> with zero submission, <code>IllegalArgumentException</code>
     * should be thrown.
     */
    public void testSetSubmissionWithZeroSubmission() {
        try {
            review.setSubmission(0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setSubmission(long)</code> with positive submission, the submission field should be set
     * successfully.
     */
    public void testSetSubmissionWithPositiveSubmission() {
        review.setSubmission(100);
        assertTrue("submission field should be 100", review.getSubmission() == 100);
    }

    /**
     * Test the method <code>getSubmission()</code>, the submission value should be returned successfully.
     */
    public void testGetSubmission() {
        assertTrue("getSubmission method should return 2", review.getSubmission() == 2);
    }

    /**
     * Test the method <code>resetSubmission()</code>, the submission field should be set to -1.
     */
    public void testResetSubmission() {
        review.resetSubmission();
        assertTrue("submission field should be -1", review.getSubmission() == -1);
    }

    /**
     * Test the method <code>setScorecard(long)</code> with negative scorecard, <code>IllegalArgumentException</code>
     * should be thrown.
     */
    public void testSetScorecardWithNegativeScorecard() {
        try {
            review.setScorecard(-3);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setScorecard(long)</code> with zero scorecard, <code>IllegalArgumentException</code>
     * should be thrown.
     */
    public void testSetScorecardWithZeroScorecard() {
        try {
            review.setScorecard(0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setScorecard(long)</code> with positive scorecard, the scorecard field should be set
     * successfully.
     */
    public void testSetScorecardWithPositiveScorecard() {
        review.setScorecard(100);
        assertTrue("scorecard field should be 100", review.getScorecard() == 100);
    }

    /**
     * Test the method <code>getScorecard()</code>, the scorecard value should be returned successfully.
     */
    public void testGetScorecard() {
        assertTrue("getScorecard method should return 3", review.getScorecard() == 3);
    }

    /**
     * Test the method <code>resetScorecard()</code>, the scorecard field should be set to -1.
     */
    public void testResetScorecard() {
        review.resetScorecard();
        assertTrue("scorecard field should be -1", review.getScorecard() == -1);
    }

    /**
     * Test the method <code>setCommitted(boolean)</code>, the committed field should be set successfully.
     */
    public void testSetCommitted() {
        review.setCommitted(true);
        assertTrue("committed field should be true", review.isCommitted());
        review.setCommitted(false);
        assertFalse("committed field should be false", review.isCommitted());
    }

    /**
     * Test the method <code>isCommitted()</code>, the committed value should be returned successfully.
     */
    public void testIsCommitted() {
        assertFalse("isCommitted method should return false", review.isCommitted());
    }

    /**
     * Test the method <code>resetCommitted()</code>, the committed field should be set to false.
     */
    public void testResetCommitted() {
        review.setCommitted(true);
        assertTrue("committed field should be true", review.isCommitted());
        review.resetCommitted();
        assertFalse("committed field should be false", review.isCommitted());
    }

    /**
     * Test the method <code>setScore(Float)</code> with negative score, <code>IllegalArgumentException</code>
     * should be thrown.
     */
    public void testSetScoreWithNegativeScore() {
        try {
            review.setScore(new Float(-1));
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setScore(Float)</code> with POSITIVE_INFINITY score,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testSetScoreWithInfinityScore() {
        try {
            review.setScore(new Float(Float.POSITIVE_INFINITY));
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setScore(Float)</code> with NaN score, <code>IllegalArgumentException</code> should be
     * thrown.
     */
    public void testSetScoreWithNaNScore() {
        try {
            review.setScore(new Float(Float.NaN));
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setScore(Float)</code> with valid score, the score field should be set successfully.
     */
    public void testSetScoreWithValidScore() {
        review.setScore(new Float(1));
        assertTrue("score field should be 1", review.getScore().floatValue() == 1);

        review.setScore(new Float(0));
        assertTrue("score field should be 0", review.getScore().floatValue() == 0);

        // null is acceptable
        review.setScore(null);
        assertNull("score field should be null", review.getScore());
    }

    /**
     * Test the method <code>getScore()</code>, the score value should be returned successfully.
     */
    public void testGetScore() {
        assertTrue("getScore method should return 20.06", review.getScore().equals(score));
    }

    /**
     * Test the method <code>resetScore()</code>, the score field should be set to null.
     */
    public void testResetScore() {
        review.resetScore();
        assertNull("score field should be null", review.getScore());
    }

    /**
     * Test the method <code>addComment(Comment)</code> with null comment, <code>IllegalArgumentException</code>
     * should be thrown.
     */
    public void testAddCommentWithNullComment() {
        try {
            review.addComment(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>addComment(Comment)</code> with existent comment, the comment should not be added.
     */
    public void testAddCommentWithExistentComment() {
        int count = review.getNumberOfComments();
        review.addComment(comm2);
        assertTrue("the comment should not be added", review.getNumberOfComments() == count);
    }

    /**
     * Test the method <code>addComment(Comment)</code> with inexistent comment, the comment should be added.
     */
    public void testAddCommentWithInexistentComment() {
        int count = review.getNumberOfComments();
        review.addComment(new Comment());
        assertTrue("the comment should be added", review.getNumberOfComments() == count + 1);
    }

    /**
     * Test the method <code>addComments(Comment[])</code> with null comments, <code>IllegalArgumentException</code>
     * should be thrown.
     */
    public void testAddCommentsWithNullComments() {
        try {
            review.addComments(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>addComments(Comment[])</code> with invalid comments which contains null,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testAddCommentsWithInvalidComments() {
        try {
            review.addComments(new Comment[] {new Comment(), null});
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>addComments(Comment[])</code> with valid comments, only inexistent comments should be
     * added.
     */
    public void testAddCommentsWithValidComments() {
        int count = review.getNumberOfComments();
        review.addComments(new Comment[] {new Comment(), comm1, comm2});
        assertTrue("one inexistent comment should be added", review.getNumberOfComments() == count + 1);
    }

    /**
     * Test the method <code>removeComment(Comment)</code> with null comment, <code>IllegalArgumentException</code>
     * should be thrown.
     */
    public void testRemoveCommentWithNullComment() {
        try {
            review.removeComment(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>removeComment(Comment)</code> with inexistent comment, the comment should not be removed.
     */
    public void testRemoveCommentWithInexistentComment() {
        int count = review.getNumberOfComments();
        review.removeComment(new Comment());
        assertTrue("the comment should not be removed", review.getNumberOfComments() == count);
    }

    /**
     * Test the method <code>removeComment(Comment)</code> with existent comment, the comment should be removed.
     */
    public void testRemoveCommentWithExistentComment() {
        int count = review.getNumberOfComments();
        review.removeComment(comm1);
        assertTrue("the comment should be removed", review.getNumberOfComments() == count - 1);
    }

    /**
     * Test the method <code>removeComments(Comment[])</code> with null comments,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testRemoveCommentsWithNullComments() {
        try {
            review.removeComments(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>removeComments(Comment[])</code> with invalid comments which contains null,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testRemoveCommentsWithInvalidComments() {
        try {
            review.removeComments(new Comment[] {comm1, null});
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>removeComments(Comment[])</code> with valid comments, only existent comments should be
     * removed.
     */
    public void testRemoveCommentsWithValidComments() {
        int count = review.getNumberOfComments();
        review.removeComments(new Comment[] {new Comment(), comm1});
        assertTrue("one existent comment should be removed", review.getNumberOfComments() == count - 1);
    }

    /**
     * Test the method <code>removeComment(long)</code> with zero id, <code>IllegalArgumentException</code> should
     * be thrown.
     */
    public void testRemoveCommentWithZeroId() {
        try {
            review.removeComment(0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>removeComment(long)</code> with negative id (and not equal to -1),
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testRemoveCommentWithNegativeId() {
        try {
            review.removeComment(-2);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>removeComment(long)</code> with existent id, the comment should be removed.
     */
    public void testRemoveCommentWithExistentId() {
        int count = review.getNumberOfComments();
        review.removeComment(-1);
        assertTrue("one comment should be removed", review.getNumberOfComments() == count - 1);
        review.removeComment(100);
        assertTrue("one comment should be removed", review.getNumberOfComments() == count - 2);
    }

    /**
     * Test the method <code>removeComment(long)</code> with inexistent id, no comment should be removed.
     */
    public void testRemoveCommentWithInexistentId() {
        int count = review.getNumberOfComments();
        review.removeComment(1);
        assertTrue("no comment should be removed", review.getNumberOfComments() == count);
    }

    /**
     * Test the method <code>clearComments()</code>, all comments should be removed.
     */
    public void testClearComments() {
        assertTrue("comments list should not be empty", review.getNumberOfComments() != 0);
        review.clearComments();
        assertTrue("comments list should be empty", review.getNumberOfComments() == 0);
    }

    /**
     * Test the method <code>getComment(int)</code> with negative commentIndex, <code>IndexOutOfBoundsException</code>
     * should be thrown.
     */
    public void testGetCommentWithNegativeIndex() {
        try {
            review.getComment(-1);
            fail("IndexOutOfBoundsException should be thrown");
        } catch (IndexOutOfBoundsException e) {
            // success
        }
    }

    /**
     * Test the method <code>getComment(int)</code> with too big commentIndex, <code>IndexOutOfBoundsException</code>
     * should be thrown.
     */
    public void testGetCommentWithTooBigIndex() {
        try {
            review.getComment(2);
            fail("IndexOutOfBoundsException should be thrown");
        } catch (IndexOutOfBoundsException e) {
            // success
        }
    }

    /**
     * Test the method <code>getComment(int)</code> with valid commentIndex, the corresponding comment should be
     * retrieved.
     */
    public void testGetCommentWithValidIndex() {
        assertTrue("the corresponding comment should be retrieved", review.getComment(0).equals(comm1));
        assertTrue("the corresponding comment should be retrieved", review.getComment(1).equals(comm2));
    }

    /**
     * Test the method <code>getAllComments()</code>, the array contains all the comments should be returned.
     */
    public void testGetAllComments() {
        Comment[] expected = new Comment[] {comm1, comm2};
        assertTrue("the array contains all the comments should be returned",
                Arrays.equals(expected, review.getAllComments()));
    }

    /**
     * Test the method <code>getNumberOfComments()</code>, the size of the comments list should be returned.
     */
    public void testGetNumberOfComments() {
        assertTrue("the size of the comments list should be returned", review.getNumberOfComments() == 2);
    }

    /**
     * Test the method <code>addItem(Item)</code> with null item, <code>IllegalArgumentException</code> should be
     * thrown.
     */
    public void testAddItemWithNullItem() {
        try {
            review.addItem(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>addItem(Item)</code> with existent item, the item should not be added.
     */
    public void testAddItemWithExistentItem() {
        int count = review.getNumberOfItems();
        review.addItem(item2);
        assertTrue("the item should not be added", review.getNumberOfItems() == count);
    }

    /**
     * Test the method <code>addItem(Item)</code> with inexistent item, the item should be added.
     */
    public void testAddItemWithInexistentItem() {
        int count = review.getNumberOfItems();
        review.addItem(new Item());
        assertTrue("the item should be added", review.getNumberOfItems() == count + 1);
    }

    /**
     * Test the method <code>addItems(Item[])</code> with null items, <code>IllegalArgumentException</code> should
     * be thrown.
     */
    public void testAddItemsWithNullItems() {
        try {
            review.addItems(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>addItems(Item[])</code> with invalid items which contains null,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testAddItemsWithInvalidItems() {
        try {
            review.addItems(new Item[] {new Item(), null});
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>addItems(Item[])</code> with valid items, only inexistent items should be added.
     */
    public void testAddItemsWithValidItems() {
        int count = review.getNumberOfItems();
        review.addItems(new Item[] {new Item(), item1, item2});
        assertTrue("one inexistent item should be added", review.getNumberOfItems() == count + 1);
    }

    /**
     * Test the method <code>removeItem(Item)</code> with null item, <code>IllegalArgumentException</code> should be
     * thrown.
     */
    public void testRemoveItemWithNullItem() {
        try {
            review.removeItem(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>removeItem(Item)</code> with inexistent item, the item should not be removed.
     */
    public void testRemoveItemWithInexistentItem() {
        int count = review.getNumberOfItems();
        review.removeItem(new Item());
        assertTrue("the item should not be removed", review.getNumberOfItems() == count);
    }

    /**
     * Test the method <code>removeItem(Item)</code> with existent item, the item should be removed.
     */
    public void testRemoveItemWithExistentItem() {
        int count = review.getNumberOfItems();
        review.removeItem(item1);
        assertTrue("the item should be removed", review.getNumberOfItems() == count - 1);
    }

    /**
     * Test the method <code>removeItems(Item[])</code> with null items, <code>IllegalArgumentException</code>
     * should be thrown.
     */
    public void testRemoveItemsWithNullItems() {
        try {
            review.removeItems(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>removeItems(Item[])</code> with invalid items which contains null,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testRemoveItemsWithInvalidItems() {
        try {
            review.removeItems(new Item[] {item1, null});
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>removeItems(Item[])</code> with valid items, only existent items should be removed.
     */
    public void testRemoveItemsWithValidItems() {
        int count = review.getNumberOfItems();
        review.removeItems(new Item[] {new Item(), item1});
        assertTrue("one existent item should be removed", review.getNumberOfItems() == count - 1);
    }

    /**
     * Test the method <code>removeItem(long)</code> with zero id, <code>IllegalArgumentException</code> should be
     * thrown.
     */
    public void testRemoveItemWithZeroId() {
        try {
            review.removeItem(0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>removeItem(long)</code> with negative id (and not equal to -1),
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testRemoveItemWithNegativeId() {
        try {
            review.removeItem(-2);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>removeItem(long)</code> with existent id, the item should be removed.
     */
    public void testRemoveItemWithExistentId() {
        int count = review.getNumberOfItems();
        review.removeItem(-1);
        assertTrue("one item should be removed", review.getNumberOfItems() == count - 1);
        review.removeItem(100);
        assertTrue("one item should be removed", review.getNumberOfItems() == count - 2);
    }

    /**
     * Test the method <code>removeItem(long)</code> with inexistent id, no item should be removed.
     */
    public void testRemoveItemWithInexistentId() {
        int count = review.getNumberOfItems();
        review.removeItem(1);
        assertTrue("no item should be removed", review.getNumberOfItems() == count);
    }

    /**
     * Test the method <code>clearItems()</code>, all items should be removed.
     */
    public void testClearItems() {
        assertTrue("items list should not be empty", review.getNumberOfItems() != 0);
        review.clearItems();
        assertTrue("items list should be empty", review.getNumberOfItems() == 0);
    }

    /**
     * Test the method <code>getItem(int)</code> with negative itemIndex, <code>IndexOutOfBoundsException</code>
     * should be thrown.
     */
    public void testGetItemWithNegativeIndex() {
        try {
            review.getItem(-1);
            fail("IndexOutOfBoundsException should be thrown");
        } catch (IndexOutOfBoundsException e) {
            // success
        }
    }

    /**
     * Test the method <code>getItem(int)</code> with too big itemIndex, <code>IndexOutOfBoundsException</code>
     * should be thrown.
     */
    public void testGetItemWithTooBigIndex() {
        try {
            review.getItem(2);
            fail("IndexOutOfBoundsException should be thrown");
        } catch (IndexOutOfBoundsException e) {
            // success
        }
    }

    /**
     * Test the method <code>getItem(int)</code> with valid itemIndex, the corresponding item should be retrieved.
     */
    public void testGetItemWithValidIndex() {
        assertTrue("the corresponding item should be retrieved", review.getItem(0).equals(item1));
        assertTrue("the corresponding item should be retrieved", review.getItem(1).equals(item2));
    }

    /**
     * Test the method <code>getAllItems()</code>, the array contains all the items should be returned.
     */
    public void testGetAllItems() {
        Item[] expected = new Item[] {item1, item2};
        assertTrue("the array contains all the items should be returned",
                Arrays.equals(expected, review.getAllItems()));
    }

    /**
     * Test the method <code>getNumberOfItems()</code>, the size of the items list should be returned.
     */
    public void testGetNumberOfItems() {
        assertTrue("the size of the items list should be returned", review.getNumberOfItems() == 2);
    }

    /**
     * Test the method <code>setCreationUser(String)</code>, the creationUser field should be set successfully.
     */
    public void testSetCreationUser() {
        review.setCreationUser("UserC");
        assertTrue("creationUser field should be 'UserC'", review.getCreationUser().equals("UserC"));

        // null is acceptable
        review.setCreationUser(null);
        assertNull("creationUser field should be null", review.getCreationUser());

        // empty string is acceptable
        review.setCreationUser("");
        assertTrue("creationUser field should be ''", review.getCreationUser().equals(""));

        // all whitespace is acceptable
        review.setCreationUser("   ");
        assertTrue("creationUser field should be '   '", review.getCreationUser().equals("   "));
    }

    /**
     * Test the method <code>getCreationUser()</code>, the creationUser value should be returned correctly.
     */
    public void testGetCreationUser() {
        assertTrue("getCreationUser method should return 'Creation'", review.getCreationUser().equals("Creation"));
    }

    /**
     * Test the method <code>setCreationTimestamp(Date)</code>, the creationTimestamp field should be set successfully.
     */
    public void testSetCreationTimestamp() {
        Date newDate = new Date();
        review.setCreationTimestamp(newDate);
        assertTrue("creationTimestamp field should be set", review.getCreationTimestamp().equals(newDate));

        // null is acceptable
        review.setCreationTimestamp(null);
        assertNull("creationTimestamp field should be null", review.getCreationTimestamp());
    }

    /**
     * Test the method <code>getCreationTimestamp()</code>, the creationTimestamp value should be returned correctly.
     */
    public void testGetCreationTimestamp() {
        assertTrue("getCreationTimestamp method should return correctly", review.getCreationTimestamp().equals(date));
    }

    /**
     * Test the method <code>setModificationUser(String)</code>, the modificationUser field should be set
     * successfully.
     */
    public void testSetModificationUser() {
        review.setModificationUser("UserM");
        assertTrue("modificationUser field should be 'UserM'", review.getModificationUser().equals("UserM"));

        // null is acceptable
        review.setModificationUser(null);
        assertNull("modificationUser field should be null", review.getModificationUser());

        // empty string is acceptable
        review.setModificationUser("");
        assertTrue("modificationUser field should be ''", review.getModificationUser().equals(""));

        // all whitespace is acceptable
        review.setModificationUser("   ");
        assertTrue("modificationUser field should be '   '", review.getModificationUser().equals("   "));
    }

    /**
     * Test the method <code>getModificationUser()</code>, the modificationUser value should be returned correctly.
     */
    public void testGetModificationUser() {
        assertTrue("getModificationUser method should return 'Modification'",
                review.getModificationUser().equals("Modification"));
    }

    /**
     * Test the method <code>setModificationTimestamp(Date)</code>, the modificationTimestamp field should be set
     * successfully.
     */
    public void testSetModificationTimestamp() {
        Date newDate = new Date();
        review.setModificationTimestamp(newDate);
        assertTrue("modificationTimestamp field should be set", review.getModificationTimestamp().equals(newDate));

        // null is acceptable
        review.setModificationTimestamp(null);
        assertNull("modificationTimestamp field should be null", review.getModificationTimestamp());
    }

    /**
     * Test the method <code>getModificationTimestamp()</code>, the modificationTimestamp value should be returned
     * correctly.
     */
    public void testGetModificationTimestamp() {
        assertTrue("getModificationTimestamp method should return correctly",
                review.getModificationTimestamp().equals(date));
    }
}
