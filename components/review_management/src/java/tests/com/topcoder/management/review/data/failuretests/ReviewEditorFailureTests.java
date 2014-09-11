/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.data.failuretests;

import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.Item;
import com.topcoder.management.review.data.Review;
import com.topcoder.management.review.data.ReviewEditor;

import junit.framework.TestCase;
/**
 * <p>
 * The failure tests for ReviewEditor.
 * </p>
 *
 * @author waits
 * @version 1.0
 */
public class ReviewEditorFailureTests extends TestCase {
    /**
     * The ReviewEditor instance used to test.
     */
    private ReviewEditor editor;

    /**
     * The setUp of the unit test.
     */
    protected void setUp() {
        editor = new ReviewEditor("user");
    }
    /**
     * The failure test of the constructor with name null,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testctorForInvalidParam1() {
        try {
            new ReviewEditor(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the constructor with Review null,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testctorForInvalidParam2() {
        try {
            new ReviewEditor(null, "user");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the constructor with name null,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testctorForInvalidParam3() {
        try {
            new ReviewEditor(new Review(), null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the setId with id<=0,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testsetIdForInvalidId() {
        try {
            editor.setId(0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the setAuthor with id<=0,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testsetAuthorForInvalidId() {
        try {
            editor.setAuthor(0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the setSubmission with id<=0,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testsetSubmissionForInvalidId() {
        try {
            editor.setSubmission(0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the setScorecard with id<=0,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testsetScorecardForInvalidId() {
        try {
            editor.setScorecard(0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the setScore with score <0,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testsetScoreForInvalidId1() {
        try {
            editor.setScore(new Float(-0.5));
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
    public void testsetScoreForInvalidId2() {
        try {
            editor.setScore(new Float(Float.NaN));
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
    public void testsetScoreForInvalidId3() {
        try {
            editor.setScore(new Float(Float.POSITIVE_INFINITY));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the addItem with item is null,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testaddItemForNull() {
        try {
            editor.addItem(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the addItems with items is null,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testaddItemsFornull() {
        try {
            editor.addItems(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the addItems with items contains is null,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testaddItemsForInvalidContainsNull() {
        try {
            editor.addItems(new Item[] {null});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the removeItem with item is null,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testremoveItemForNull() {
        try {
            editor.removeItem(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the removeItems with items is null,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testremoveItemsFornull() {
        try {
            editor.removeItems(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the removeItems with items contains is null,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testremoveItemsForInvalidContainsNull() {
        try {
            editor.removeItems(new Item[] {null});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the addComment with item is null,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testaddCommentForNull() {
        try {
            editor.addComment(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the addComments with items is null,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testaddCommentsFornull() {
        try {
            editor.addComments(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the addComments with items contains is null,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testaddCommentsForInvalidContainsNull() {
        try {
            editor.addComments(new Comment[] {null});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the removeComment with item is null,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testremoveCommentForNull() {
        try {
            editor.removeComment(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the removeComments with items is null,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testremoveCommentsFornull() {
        try {
            editor.removeComments(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the removeComments with items contains is null,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testremoveCommentsForInvalidContainsNull() {
        try {
            editor.removeComments(new Comment[] {null});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
}
