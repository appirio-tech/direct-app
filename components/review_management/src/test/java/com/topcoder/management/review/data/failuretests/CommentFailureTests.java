/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 */
package com.topcoder.management.review.data.failuretests;

import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.CommentType;

import junit.framework.TestCase;
/**
 * <p>
 * The failure tests for Comment.
 * </p>
 *
 * @author waits
 * @version 1.0
 */
public class CommentFailureTests extends TestCase {
    /**
     * <p>The Comment instance used to test.</p>
     */
    private Comment comment;

    /**
     * <p>Create an instance of <code>Comment</code>.</p>
     */
    protected void setUp() {
        comment = new Comment();
    }
    /**
     * The failure test of constructor with id <= 0.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testCtorWithInvalidArgument1() {
        try {
            new Comment(0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of constructor with id <= 0.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testCtorWithInvalidArgument2() {
        try {
            new Comment(0, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of constructor with author <= 0.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testCtorWithInvalidArgument3() {
        try {
            new Comment(1, 0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of constructor with id <= 0.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testCtorWithInvalidArgument4() {
        try {
            new Comment(0, 1, "comment");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of constructor with author <= 0.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testCtorWithInvalidArgument5() {
        try {
            new Comment(1, 0, "comment");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of constructor with comment is null.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testCtorWithInvalidArgument6() {
        try {
            new Comment(1, 1, (String)null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of constructor with id <= 0.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testCtorWithInvalidArgument7() {
        try {
            new Comment(0, 1, new CommentType(1, "type"));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of constructor with author <= 0.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testCtorWithInvalidArgument8() {
        try {
            new Comment(1, 0, new CommentType(1, "type"));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of constructor with CommentType is null.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testCtorWithInvalidArgument9() {
        try {
            new Comment(1, 1, (CommentType)null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of constructor with id <= 0.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testCtorWithInvalidArgument10() {
        try {
            new Comment(0, 1, new CommentType(1, "type"), "comment");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of constructor with author <= 0.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testCtorWithInvalidArgument11() {
        try {
            new Comment(1, 0, new CommentType(1, "type"), "comment");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of constructor with CommentType is null.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testCtorWithInvalidArgument12() {
        try {
            new Comment(1, 1, null, "comment");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of constructor with CommentType is null.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testCtorWithInvalidArgument13() {
        try {
            new Comment(1, 1, new CommentType(1, "type"), null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
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
            comment.setId(0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
    /**
     * The failure test of setAuthor with author <= 0.
     * IllegalArgumentException should be thrown.
     *
     */
    public void testsetAuthorWithInvalidauthor() {
        try {
            comment.setAuthor(0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            //pass
        }
    }
}
