/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 */
package com.topcoder.management.review.data.failuretests;

import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.Item;
import com.topcoder.management.review.data.Review;

import junit.framework.TestCase;
/**
 * <p>
 * The failure test for Review.
 * </p>
 *
 * @author waits
 * @version 1.0
 */
public class ReviewFailureTests extends TestCase {
    /**
     * The Review instance used to test.</p>
     */
    private Review review;

    /**
     * The setUp.
     */
    protected void setUp() {
        review = new Review();
    }
    /**
     * The failure test of the constructor with id <=0,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testctorWithInvalidParam1() {
        try {
            new Review(0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the constructor with id <=0,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testctorWithInvalidParam2() {
        try {
            new Review(0, "createuser");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the constructor with create user null,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testctorWithInvalidParam3() {
        try {
            new Review(1, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the constructor with id <=0,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testctorWithInvalidParam4() {
        try {
            new Review(0, 1, 1, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the constructor with author <=0,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testctorWithInvalidParam5() {
        try {
            new Review(1, 0, 1, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the constructor with submission <=0,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testctorWithInvalidParam6() {
        try {
            new Review(1, 1, 0, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the constructor with scorecard <=0,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testctorWithInvalidParam7() {
        try {
            new Review(1, 1, 1, 0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the constructor with id <=0,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testctorWithInvalidParam8() {
        try {
            new Review(0, "createuser", 1, 1, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the constructor with author <=0,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testctorWithInvalidParam9() {
        try {
            new Review(1, "createuser", 0, 1, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the constructor with submission <=0,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testctorWithInvalidParam10() {
        try {
            new Review(1, "createuser", 1, 0, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the constructor with scorecard <=0,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testctorWithInvalidParam11() {
        try {
            new Review(1, "createuser", 1, 1, 0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the constructor with create user null,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testctorWithInvalidParam12() {
        try {
            new Review(1, null, 1, 1, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of setId with id <= 0.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testsetIdWithInvalidId() {
        try {
            review.setId(0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of setAuthor with Author <= 0.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testsetAuthorWithInvalidAuthor() {
        try {
            review.setAuthor(0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of setSubmission with Submission <= 0.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testsetSubmissionWithInvalidSubmission() {
        try {
            review.setSubmission(0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of setScorecard with Scorecard <= 0.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testsetScorecardWithInvalidScorecard() {
        try {
            review.setScorecard(0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of the setScore with score <0,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testsetScoreForInvalidFloat1() {
        try {
            review.setScore(new Float(-0.5));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the setScore with score is NaN,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testsetScoreForInvalidFloat2() {
        try {
            review.setScore(new Float("112..1"));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the setScore with score is POSITIVE_INFINITY,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testsetScoreForInvalidFloat3() {
        try {
            review.setScore(new Float(Float.POSITIVE_INFINITY));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of addItem with item is null.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testaddItemWithNull() {
        try {
            review.addItem(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of addItems with item is null.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testaddItemsWithNull() {
        try {
            review.addItems(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of addItems with contains null.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testaddItemsWithcontainsNull() {
        try {
            review.addItems(new Item[] {null});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of removeItem with item is null.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testremoveItemWithNull() {
        try {
            review.removeItem(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of removeItems with item is null.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testremoveItemsWithNull() {
        try {
            review.removeItems(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of removeItems with contains null.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testremoveItemsWithcontainsNull() {
        try {
            review.removeItems(new Item[] {null});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of removeItems with id <= 0 null.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testremoveItemWithInvalidId() {
        try {
            review.removeItem(0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of getItem with invalid index.
     * IndexOutOfBoundsException should be thrown.
     *
     */
    public void testremoveItemWithInvalidIndex1() {
        try {
            review.getItem(100);
            fail("IndexOutOfBoundsException should be thrown.");
        } catch (IndexOutOfBoundsException ex) {
            //pass
        }
    }
    /**
     * The failure test of getItem with invalid index.
     * IndexOutOfBoundsException should be thrown.
     *
     */
    public void testremoveItemWithInvalidIndex2() {
        try {
            review.getItem(-1);
            fail("IndexOutOfBoundsException should be thrown.");
        } catch (IndexOutOfBoundsException ex) {
            //pass
        }
    }
    /**
     * The failure test of addComment with Comment is null.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testaddCommentWithNull() {
        try {
            review.addComment(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of addComments with Comment is null.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testaddCommentsWithNull() {
        try {
            review.addComments(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of addComments with contains null.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testaddCommentsWithcontainsNull() {
        try {
            review.addComments(new Comment[] {null});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of removeComment with Comment is null.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testremoveCommentWithNull() {
        try {
            review.removeComment(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of removeComments with Comment is null.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testremoveCommentsWithNull() {
        try {
            review.removeComments(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of removeComments with contains null.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testremoveCommentsWithcontainsNull() {
        try {
            review.removeComments(new Comment[] {null});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of removeComments with id <= 0 null.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testremoveCommentWithInvalidId() {
        try {
            review.removeComment(0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of getComment with invalid index.
     * IndexOutOfBoundsException should be thrown.
     *
     */
    public void testremoveCommentWithInvalidIndex1() {
        try {
            review.getComment(100);
            fail("IndexOutOfBoundsException should be thrown.");
        } catch (IndexOutOfBoundsException ex) {
            //pass
        }
    }
    /**
     * The failure test of getComment with invalid index.
     * IndexOutOfBoundsException should be thrown.
     *
     */
    public void testremoveCommentWithInvalidIndex2() {
        try {
            review.getComment(-1);
            fail("IndexOutOfBoundsException should be thrown.");
        } catch (IndexOutOfBoundsException ex) {
            //pass
        }
    }
}
