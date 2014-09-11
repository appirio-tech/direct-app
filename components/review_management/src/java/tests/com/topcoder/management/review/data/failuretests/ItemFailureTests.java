/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 */
package com.topcoder.management.review.data.failuretests;

import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.Item;

import junit.framework.TestCase;
/**
 * <p>
 * The failure tests for Item.
 * </p>
 *
 * @author waits
 * @version 1.0
 */
public class ItemFailureTests extends TestCase {
    /**
     * The Item instance used to test.
     */
    private Item item;

    /**
     * The setUp.
     */
    protected void setUp() {
        item = new Item();
    }
    /**
     * The failure test of the constructor with id <=0,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testctorWithInvalidParam1() {
        try {
            new Item(0);
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
            new Item(0, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the constructor with question <=0,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testctorWithInvalidParam3() {
        try {
            new Item(1, 0);
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
            new Item(0, 1, "answer");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the constructor with question <=0,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testctorWithInvalidParam5() {
        try {
            new Item(1, 0, "answer");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the constructor with answer is null,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testctorWithInvalidParam6() {
        try {
            new Item(1, 1, null);
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
            item.setId(0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of setQuestion with id <= 0.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testsetQuestionWithInvalidId() {
        try {
            item.setQuestion(0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of setDocument with value <= 0.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testsetDocumentWithInvalidValue() {
        try {
            item.setDocument(new Long(0));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of addComment with comment is null.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testaddCommentWithInvalidParam() {
        try {
            item.addComment(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of addComments with comment is null.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testaddCommentsWithInvalidParam1() {
        try {
            item.addComments(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of addComments with comment is null.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testaddCommentsWithInvalidParam2() {
        try {
            item.addComments(new Comment[] {null});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of removeComment with comment is null.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testremoveCommentWithInvalidParam() {
        try {
            item.removeComment(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of removeComments with comment is null.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testremoveCommentsWithInvalidParam1() {
        try {
            item.removeComments(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of removeComments with comment is null.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testremoveCommentsWithInvalidParam2() {
        try {
            item.removeComments(new Comment[] {null});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of removeComment with id <= 0.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testremoveCommentWithInvalidId() {
        try {
            item.removeComment(0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of getComment with index invalid.
     * IndexOutOfBoundsException should be thrown.
     *
     */
    public void testgetCommentWithInvalidindex1() {
        try {
            item.getComment(100);
            fail("IndexOutOfBoundsException should be thrown.");
        } catch (IndexOutOfBoundsException ex) {
            //pass
        }
    }
    /**
     * The failure test of getComment with index invalid.
     * IndexOutOfBoundsException should be thrown.
     *
     */
    public void testgetCommentWithInvalidindex2() {
        try {
            item.getComment(-1);
            fail("IndexOutOfBoundsException should be thrown.");
        } catch (IndexOutOfBoundsException ex) {
            //pass
        }
    }
}
