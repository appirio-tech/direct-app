/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.data;

import java.io.Serializable;
import java.util.Date;

import junit.framework.TestCase;

/**
 * Unit tests for <code>ReviewEditor</code> class.
 *
 * @author vividmxx
 * @version 1.0
 */
public class ReviewEditorUnitTests extends TestCase {
    /**
     * The string constant used as the user for test.
     */
    private static final String USER = "TopCoder";

    /**
     * The <code>Review</code> instance used for test.
     */
    private Review review = null;

    /**
     * The <code>ReviewEditor</code> instance used to test against.
     */
    private ReviewEditor editor = null;

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

        editor = new ReviewEditor(review, USER);
    }

    /**
     * Test whether <code>ReviewEditor</code> class implements the <code>Serializable</code> interface.
     */
    public void testImplements() {
        assertTrue("ReviewEditor should implement Serializable interface", editor instanceof Serializable);
    }

    /**
     * Test the constructor <code>ReviewEditor(String)</code> with null user, <code>IllegalArgumentException</code>
     * should be thrown.
     */
    public void testConstructorWithNullUser() {
        try {
            new ReviewEditor(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>ReviewEditor(String)</code> with non-null user, instance of
     * <code>ReviewEditor</code> should be created.
     */
    public void testConstructorWithNonNullUser() {
        Date now = new Date();
        ReviewEditor edt = new ReviewEditor("New User");
        assertNotNull("ReviewEditor instance should be created", edt);
        assertTrue("user field should be 'New User'", edt.getUser().equals("New User"));
        assertNotNull("review field should not be null", edt.getReview());
        assertTrue("creationUser field of the review should be 'New User'",
                edt.getReview().getCreationUser().equals("New User"));
        assertTrue("creationTimestamp field of the review should be set",
                edt.getReview().getCreationTimestamp().equals(now));
        assertTrue("creationUser field of the review should be 'New User'",
                edt.getReview().getModificationUser().equals("New User"));
        assertTrue("creationTimestamp field of the review should be set",
                edt.getReview().getModificationTimestamp().equals(now));

        // empty user is acceptable
        now = new Date();
        ReviewEditor edt1 = new ReviewEditor("");
        assertNotNull("ReviewEditor instance should be created", edt1);
        assertTrue("user field should be ''", edt1.getUser().equals(""));
        assertNotNull("review field should not be null", edt1.getReview());
        assertTrue("creationUser field of the review should be ''",
                edt1.getReview().getCreationUser().equals(""));
        assertTrue("creationTimestamp field of the review should be set",
                edt1.getReview().getCreationTimestamp().equals(now));
        assertTrue("creationUser field of the review should be ''",
                edt1.getReview().getModificationUser().equals(""));
        assertTrue("creationTimestamp field of the review should be set",
                edt1.getReview().getModificationTimestamp().equals(now));

        // all whitespace user is acceptable
        now = new Date();
        ReviewEditor edt2 = new ReviewEditor("   ");
        assertNotNull("ReviewEditor instance should be created", edt2);
        assertTrue("user field should be '   '", edt2.getUser().equals("   "));
        assertNotNull("review field should not be null", edt2.getReview());
        assertTrue("creationUser field of the review should be '   '",
                edt2.getReview().getCreationUser().equals("   "));
        assertTrue("creationTimestamp field of the review should be set",
                edt2.getReview().getCreationTimestamp().equals(now));
        assertTrue("creationUser field of the review should be '   '",
                edt2.getReview().getModificationUser().equals("   "));
        assertTrue("creationTimestamp field of the review should be set",
                edt2.getReview().getModificationTimestamp().equals(now));
    }

    /**
     * Test the constructor <code>ReviewEditor(Review, String)</code> with null user,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithNullUserAndNonNullReview() {
        try {
            new ReviewEditor(new Review(), null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>ReviewEditor(Review, String)</code> with null review,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithNullReviewAndNonNullUser() {
        try {
            new ReviewEditor(null, "New User");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>ReviewEditor(Review, String)</code> with valid user and review, instance of
     * <code>ReviewEditor</code> should be created.
     */
    public void testConstructorWithValidUserAndReview() {
        Review rv = new Review();

        ReviewEditor edt = new ReviewEditor(rv, "New User");
        assertNotNull("ReviewEditor instance should be created", edt);
        assertTrue("user field should be 'New User'", edt.getUser().equals("New User"));
        assertTrue("review field should be set", edt.getReview().equals(rv));

        // empty user is acceptable
        ReviewEditor edt1 = new ReviewEditor(rv, "");
        assertNotNull("ReviewEditor instance should be created", edt1);
        assertTrue("user field should be ''", edt1.getUser().equals(""));
        assertTrue("review field should be set", edt1.getReview().equals(rv));

        // all whitespace user is acceptable
        ReviewEditor edt2 = new ReviewEditor(rv, "   ");
        assertNotNull("ReviewEditor instance should be created", edt2);
        assertTrue("user field should be '   '", edt2.getUser().equals("   "));
        assertTrue("review field should be set", edt2.getReview().equals(rv));
    }

    /**
     * Test the method <code>setId(long)</code> with negative id, <code>IllegalArgumentException</code> should be
     * thrown.
     */
    public void testSetIdWithNegativeId() {
        try {
            editor.setId(-2006);
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
            editor.setId(0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setId(long)</code> with positive id, the id field of the review should be set
     * successfully.
     */
    public void testSetIdWithPositiveId() {
        Date now = new Date();
        editor.setId(2006);
        assertTrue("id field should be 2006", review.getId() == 2006);
        assertTrue("modificationUser field of the review should be set",
                review.getModificationUser().equals(USER));
        assertTrue("modificationTimestamp field of the review should be set",
                review.getModificationTimestamp().equals(now));
    }

    /**
     * Test the method <code>resetId()</code>, the id field of the review should be set to -1.
     */
    public void testResetId() {
        Date now = new Date();
        editor.resetId();
        assertTrue("id field should be -1", review.getId() == -1);
        assertTrue("modificationUser field of the review should be set",
                review.getModificationUser().equals(USER));
        assertTrue("modificationTimestamp field of the review should be set",
                review.getModificationTimestamp().equals(now));
    }

    /**
     * Test the method <code>setAuthor(long)</code> with negative author, <code>IllegalArgumentException</code>
     * should be thrown.
     */
    public void testSetAuthorWithNegativeAuthor() {
        try {
            editor.setAuthor(-1);
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
            editor.setAuthor(0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setAuthor(long)</code> with positive author, the author field of the review should be set
     * successfully.
     */
    public void testSetAuthorWithPositiveAuthor() {
        Date now = new Date();
        editor.setAuthor(100);
        assertTrue("author field should be 100", review.getAuthor() == 100);
        assertTrue("modificationUser field of the review should be set",
                review.getModificationUser().equals(USER));
        assertTrue("modificationTimestamp field of the review should be set",
                review.getModificationTimestamp().equals(now));
    }

    /**
     * Test the method <code>resetAuthor()</code>, the author field of the review should be set to -1.
     */
    public void testResetAuthor() {
        Date now = new Date();
        editor.resetAuthor();
        assertTrue("author field should be -1", review.getAuthor() == -1);
        assertTrue("modificationUser field of the review should be set",
                review.getModificationUser().equals(USER));
        assertTrue("modificationTimestamp field of the review should be set",
                review.getModificationTimestamp().equals(now));
    }

    /**
     * Test the method <code>setSubmission(long)</code> with negative submission,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testSetSubmissionWithNegativeSubmission() {
        try {
            editor.setSubmission(-2);
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
            editor.setSubmission(0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setSubmission(long)</code> with positive submission, the submission field of the review
     * should be set successfully.
     */
    public void testSetSubmissionWithPositiveSubmission() {
        Date now = new Date();
        editor.setSubmission(100);
        assertTrue("submission field should be 100", review.getSubmission() == 100);
        assertTrue("modificationUser field of the review should be set",
                review.getModificationUser().equals(USER));
        assertTrue("modificationTimestamp field of the review should be set",
                review.getModificationTimestamp().equals(now));
    }

    /**
     * Test the method <code>resetSubmission()</code>, the submission field of the review should be set to -1.
     */
    public void testResetSubmission() {
        Date now = new Date();
        editor.resetSubmission();
        assertTrue("submission field should be -1", review.getSubmission() == -1);
        assertTrue("modificationUser field of the review should be set",
                review.getModificationUser().equals(USER));
        assertTrue("modificationTimestamp field of the review should be set",
                review.getModificationTimestamp().equals(now));
    }

    /**
     * Test the method <code>setScorecard(long)</code> with negative scorecard, <code>IllegalArgumentException</code>
     * should be thrown.
     */
    public void testSetScorecardWithNegativeScorecard() {
        try {
            editor.setScorecard(-3);
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
            editor.setScorecard(0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setScorecard(long)</code> with positive scorecard, the scorecard field of the review
     * should be set successfully.
     */
    public void testSetScorecardWithPositiveScorecard() {
        Date now = new Date();
        editor.setScorecard(100);
        assertTrue("scorecard field should be 100", review.getScorecard() == 100);
        assertTrue("modificationUser field of the review should be set",
                review.getModificationUser().equals(USER));
        assertTrue("modificationTimestamp field of the review should be set",
                review.getModificationTimestamp().equals(now));
    }

    /**
     * Test the method <code>resetScorecard()</code>, the scorecard field of the review should be set to -1.
     */
    public void testResetScorecard() {
        Date now = new Date();
        editor.resetScorecard();
        assertTrue("scorecard field should be -1", review.getScorecard() == -1);
        assertTrue("modificationUser field of the review should be set",
                review.getModificationUser().equals(USER));
        assertTrue("modificationTimestamp field of the review should be set",
                review.getModificationTimestamp().equals(now));
    }

    /**
     * Test the method <code>setCommitted(boolean)</code>, the committed field of the review should be set
     * successfully.
     */
    public void testSetCommitted() {
        Date now = new Date();
        editor.setCommitted(true);
        assertTrue("committed field should be true", review.isCommitted());
        assertTrue("modificationUser field of the review should be set",
                review.getModificationUser().equals(USER));
        assertTrue("modificationTimestamp field of the review should be set",
                review.getModificationTimestamp().equals(now));

        now = new Date();
        editor.setCommitted(false);
        assertFalse("committed field should be false", review.isCommitted());
        assertTrue("modificationUser field of the review should be set",
                review.getModificationUser().equals(USER));
        assertTrue("modificationTimestamp field of the review should be set",
                review.getModificationTimestamp().equals(now));
    }

    /**
     * Test the method <code>resetCommitted()</code>, the committed field of the review should be set to false.
     */
    public void testResetCommitted() {
        review.setCommitted(true);
        assertTrue("committed field should be true", review.isCommitted());

        Date now = new Date();
        editor.resetCommitted();
        assertFalse("committed field should be false", review.isCommitted());
        assertTrue("modificationUser field of the review should be set",
                review.getModificationUser().equals(USER));
        assertTrue("modificationTimestamp field of the review should be set",
                review.getModificationTimestamp().equals(now));
    }

    /**
     * Test the method <code>setScore(Float)</code> with negative score, <code>IllegalArgumentException</code>
     * should be thrown.
     */
    public void testSetScoreWithNegativeScore() {
        try {
            editor.setScore(new Float(-1));
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
            editor.setScore(new Float(Float.POSITIVE_INFINITY));
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
            editor.setScore(new Float(Float.NaN));
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setScore(Float)</code> with valid score, the score field of the review should be set
     * successfully.
     */
    public void testSetScoreWithValidScore() {
        Date now = new Date();
        editor.setScore(new Float(1));
        assertTrue("score field should be 1", review.getScore().floatValue() == 1);
        assertTrue("modificationUser field of the review should be set",
                review.getModificationUser().equals(USER));
        assertTrue("modificationTimestamp field of the review should be set",
                review.getModificationTimestamp().equals(now));

        now = new Date();
        editor.setScore(new Float(0));
        assertTrue("score field should be 0", review.getScore().floatValue() == 0);
        assertTrue("modificationUser field of the review should be set",
                review.getModificationUser().equals(USER));
        assertTrue("modificationTimestamp field of the review should be set",
                review.getModificationTimestamp().equals(now));

        // null is acceptable
        now = new Date();
        editor.setScore(null);
        assertNull("score field should be null", review.getScore());
        assertTrue("modificationUser field of the review should be set",
                review.getModificationUser().equals(USER));
        assertTrue("modificationTimestamp field of the review should be set",
                review.getModificationTimestamp().equals(now));
    }

    /**
     * Test the method <code>resetScore()</code>, the score field of the review should be set to null.
     */
    public void testResetScore() {
        Date now = new Date();
        editor.resetScore();
        assertNull("score field should be null", review.getScore());
        assertTrue("modificationUser field of the review should be set",
                review.getModificationUser().equals(USER));
        assertTrue("modificationTimestamp field of the review should be set",
                review.getModificationTimestamp().equals(now));
    }

    /**
     * Test the method <code>addComment(Comment)</code> with null comment, <code>IllegalArgumentException</code>
     * should be thrown.
     */
    public void testAddCommentWithNullComment() {
        try {
            editor.addComment(null);
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
        Date now = new Date();
        editor.addComment(comm2);
        assertTrue("the comment should not be added", review.getNumberOfComments() == count);
        assertTrue("modificationUser field of the review should be set",
                review.getModificationUser().equals(USER));
        assertTrue("modificationTimestamp field of the review should be set",
                review.getModificationTimestamp().equals(now));
    }

    /**
     * Test the method <code>addComment(Comment)</code> with inexistent comment, the comment should be added.
     */
    public void testAddCommentWithInexistentComment() {
        int count = review.getNumberOfComments();
        Date now = new Date();
        editor.addComment(new Comment());
        assertTrue("the comment should be added", review.getNumberOfComments() == count + 1);
        assertTrue("modificationUser field of the review should be set",
                review.getModificationUser().equals(USER));
        assertTrue("modificationTimestamp field of the review should be set",
                review.getModificationTimestamp().equals(now));
    }

    /**
     * Test the method <code>addComments(Comment[])</code> with null comments, <code>IllegalArgumentException</code>
     * should be thrown.
     */
    public void testAddCommentsWithNullComments() {
        try {
            editor.addComments(null);
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
            editor.addComments(new Comment[] {new Comment(), null});
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
        Date now = new Date();
        editor.addComments(new Comment[] {new Comment(), comm1, comm2});
        assertTrue("one inexistent comment should be added", review.getNumberOfComments() == count + 1);
        assertTrue("modificationUser field of the review should be set",
                review.getModificationUser().equals(USER));
        assertTrue("modificationTimestamp field of the review should be set",
                review.getModificationTimestamp().equals(now));
    }

    /**
     * Test the method <code>removeComment(Comment)</code> with null comment, <code>IllegalArgumentException</code>
     * should be thrown.
     */
    public void testRemoveCommentWithNullComment() {
        try {
            editor.removeComment(null);
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
        Date now = new Date();
        editor.removeComment(new Comment());
        assertTrue("the comment should not be removed", review.getNumberOfComments() == count);
        assertTrue("modificationUser field of the review should be set",
                review.getModificationUser().equals(USER));
        assertTrue("modificationTimestamp field of the review should be set",
                review.getModificationTimestamp().equals(now));
    }

    /**
     * Test the method <code>removeComment(Comment)</code> with existent comment, the comment should be removed.
     */
    public void testRemoveCommentWithExistentComment() {
        int count = review.getNumberOfComments();
        Date now = new Date();
        editor.removeComment(comm1);
        assertTrue("the comment should be removed", review.getNumberOfComments() == count - 1);
        assertTrue("modificationUser field of the review should be set",
                review.getModificationUser().equals(USER));
        assertTrue("modificationTimestamp field of the review should be set",
                review.getModificationTimestamp().equals(now));
    }

    /**
     * Test the method <code>removeComments(Comment[])</code> with null comments,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testRemoveCommentsWithNullComments() {
        try {
            editor.removeComments(null);
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
            editor.removeComments(new Comment[] {comm1, null});
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
        Date now = new Date();
        editor.removeComments(new Comment[] {new Comment(), comm1});
        assertTrue("one existent comment should be removed", review.getNumberOfComments() == count - 1);
        assertTrue("modificationUser field of the review should be set",
                review.getModificationUser().equals(USER));
        assertTrue("modificationTimestamp field of the review should be set",
                review.getModificationTimestamp().equals(now));
    }

    /**
     * Test the method <code>clearComments()</code>, all comments should be removed.
     */
    public void testClearComments() {
        assertTrue("comments list should not be empty", review.getNumberOfComments() != 0);
        Date now = new Date();
        editor.clearComments();
        assertTrue("comments list should be empty", review.getNumberOfComments() == 0);
        assertTrue("modificationUser field of the review should be set",
                review.getModificationUser().equals(USER));
        assertTrue("modificationTimestamp field of the review should be set",
                review.getModificationTimestamp().equals(now));
    }

    /**
     * Test the method <code>addItem(Item)</code> with null item, <code>IllegalArgumentException</code> should be
     * thrown.
     */
    public void testAddItemWithNullItem() {
        try {
            editor.addItem(null);
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
        Date now = new Date();
        editor.addItem(item2);
        assertTrue("the item should not be added", review.getNumberOfItems() == count);
        assertTrue("modificationUser field of the review should be set",
                review.getModificationUser().equals(USER));
        assertTrue("modificationTimestamp field of the review should be set",
                review.getModificationTimestamp().equals(now));
    }

    /**
     * Test the method <code>addItem(Item)</code> with inexistent item, the item should be added.
     */
    public void testAddItemWithInexistentItem() {
        int count = review.getNumberOfItems();
        Date now = new Date();
        editor.addItem(new Item());
        assertTrue("the item should be added", review.getNumberOfItems() == count + 1);
        assertTrue("modificationUser field of the review should be set",
                review.getModificationUser().equals(USER));
        assertTrue("modificationTimestamp field of the review should be set",
                review.getModificationTimestamp().equals(now));
    }

    /**
     * Test the method <code>addItems(Item[])</code> with null items, <code>IllegalArgumentException</code> should
     * be thrown.
     */
    public void testAddItemsWithNullItems() {
        try {
            editor.addItems(null);
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
            editor.addItems(new Item[] {new Item(), null});
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
        Date now = new Date();
        editor.addItems(new Item[] {new Item(), item1, item2});
        assertTrue("one inexistent item should be added", review.getNumberOfItems() == count + 1);
        assertTrue("modificationUser field of the review should be set",
                review.getModificationUser().equals(USER));
        assertTrue("modificationTimestamp field of the review should be set",
                review.getModificationTimestamp().equals(now));
    }

    /**
     * Test the method <code>removeItem(Item)</code> with null item, <code>IllegalArgumentException</code> should be
     * thrown.
     */
    public void testRemoveItemWithNullItem() {
        try {
            editor.removeItem(null);
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
        Date now = new Date();
        editor.removeItem(new Item());
        assertTrue("the item should not be removed", review.getNumberOfItems() == count);
        assertTrue("modificationUser field of the review should be set",
                review.getModificationUser().equals(USER));
        assertTrue("modificationTimestamp field of the review should be set",
                review.getModificationTimestamp().equals(now));
    }

    /**
     * Test the method <code>removeItem(Item)</code> with existent item, the item should be removed.
     */
    public void testRemoveItemWithExistentItem() {
        int count = review.getNumberOfItems();
        Date now = new Date();
        editor.removeItem(item1);
        assertTrue("the item should be removed", review.getNumberOfItems() == count - 1);
        assertTrue("modificationUser field of the review should be set",
                review.getModificationUser().equals(USER));
        assertTrue("modificationTimestamp field of the review should be set",
                review.getModificationTimestamp().equals(now));
    }

    /**
     * Test the method <code>removeItems(Item[])</code> with null items, <code>IllegalArgumentException</code>
     * should be thrown.
     */
    public void testRemoveItemsWithNullItems() {
        try {
            editor.removeItems(null);
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
            editor.removeItems(new Item[] {item1, null});
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
        Date now = new Date();
        editor.removeItems(new Item[] {new Item(), item1});
        assertTrue("one existent item should be removed", review.getNumberOfItems() == count - 1);
        assertTrue("modificationUser field of the review should be set",
                review.getModificationUser().equals(USER));
        assertTrue("modificationTimestamp field of the review should be set",
                review.getModificationTimestamp().equals(now));
    }

    /**
     * Test the method <code>clearItems()</code>, all items should be removed.
     */
    public void testClearItems() {
        assertTrue("items list should not be empty", review.getNumberOfItems() != 0);
        Date now = new Date();
        editor.clearItems();
        assertTrue("items list should be empty", review.getNumberOfItems() == 0);
        assertTrue("modificationUser field of the review should be set",
                review.getModificationUser().equals(USER));
        assertTrue("modificationTimestamp field of the review should be set",
                review.getModificationTimestamp().equals(now));
    }

    /**
     * Test the method <code>getReview()</code>, the review value should be returned correctly.
     */
    public void testGetReview() {
        assertTrue("getReview should return correctly", editor.getReview().equals(review));
    }

    /**
     * Test the method <code>getUser()</code>, the user value should be returned correctly.
     */
    public void testGetUser() {
        assertTrue("getUser should return correctly", editor.getUser().equals(USER));
    }
}
