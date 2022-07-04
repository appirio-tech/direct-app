/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.data;

import java.io.Serializable;

import junit.framework.TestCase;

/**
 * Unit tests for <code>Comment</code> class.
 *
 * @author vividmxx
 * @version 1.0
 */
public class CommentUnitTests extends TestCase {
    /**
     * The <code>Comment</code> instance used to test against.
     */
    private Comment comment = null;

    /**
     * The <code>CommentType</code> instance used for test.
     */
    private CommentType type = new CommentType(2006, "CommentType");

    /**
     * The <code>Serializable</code> instance used for test.
     */
    private Serializable serializable = new String("I am a Serializable");

    /**
     * Set up the test environment.
     */
    protected void setUp() {
        comment = new Comment(20060624, 13579, type, "It's a comment");
        comment.setExtraInfo(serializable);
    }

    /**
     * Test whether <code>Comment</code> class implements the <code>Serializable</code> interface.
     */
    public void testImplements() {
        assertTrue("Comment should implement Serializable interface", comment instanceof Serializable);
    }

    /**
     * Test the constructor <code>Comment()</code>, all fields should have their default unassigned values.
     */
    public void testConstructorWithNoArgu() {
        Comment cm = new Comment();
        assertNotNull("Comment instance should be created", cm);
        assertTrue("id field should be -1", cm.getId() == -1);
        assertTrue("author field should be -1", cm.getAuthor() == -1);
        assertNull("commentType field should be null", cm.getCommentType());
        assertNull("comment field should be null", cm.getComment());
        assertNull("extraInfo field should be null", cm.getExtraInfo());
    }

    /**
     * Test the constructor <code>Comment(long)</code> with negative id, <code>IllegalArgumentException</code>
     * should be thrown.
     */
    public void testConstructorWithNegativeId() {
        try {
            new Comment(-2006);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Comment(long)</code> with zero id, <code>IllegalArgumentException</code> should be
     * thrown.
     */
    public void testConstructorWithZeroId() {
        try {
            new Comment(0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Comment(long)</code> with positive id, instance of <code>Comment</code> should be
     * created.
     */
    public void testConstructorWithPositiveId() {
        Comment cm = new Comment(2006);
        assertNotNull("Comment instance should be created", cm);
        assertTrue("id field should be 2006", cm.getId() == 2006);
        assertTrue("author field should be -1", cm.getAuthor() == -1);
        assertNull("commentType field should be null", cm.getCommentType());
        assertNull("comment field should be null", cm.getComment());
        assertNull("extraInfo field should be null", cm.getExtraInfo());
    }

    /**
     * Test the constructor <code>Comment(long, long)</code> with negative id, <code>IllegalArgumentException</code>
     * should be thrown.
     */
    public void testConstructorWithNegativeIdAndPositiveAuthor() {
        try {
            new Comment(-2006, 24680);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Comment(long, long)</code> with zero id, <code>IllegalArgumentException</code>
     * should be thrown.
     */
    public void testConstructorWithZeroIdAndPositiveAuthor() {
        try {
            new Comment(0, 24680);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Comment(long, long)</code> with negative author,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithNegativeAuthorAndPositiveId() {
        try {
            new Comment(2006, -24680);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Comment(long, long)</code> with zero author, <code>IllegalArgumentException</code>
     * should be thrown.
     */
    public void testConstructorWithZeroAuthorAndPositiveId() {
        try {
            new Comment(2006, 0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Comment(long, long)</code> with positive id and author, instance of
     * <code>Comment</code> should be created.
     */
    public void testConstructorWithPositiveIdAndAuthor() {
        Comment cm = new Comment(2006, 24680);
        assertNotNull("Comment instance should be created", cm);
        assertTrue("id field should be 2006", cm.getId() == 2006);
        assertTrue("author field should be 24680", cm.getAuthor() == 24680);
        assertNull("commentType field should be null", cm.getCommentType());
        assertNull("comment field should be null", cm.getComment());
        assertNull("extraInfo field should be null", cm.getExtraInfo());
    }

    /**
     * Test the constructor <code>Comment(long, long, String)</code> with negative id,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithNegativeIdAndNonNullComment() {
        try {
            new Comment(-2006, 24680, "comment");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Comment(long, long, String)</code> with zero id,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithZeroIdAndNonNullComment() {
        try {
            new Comment(0, 24680, "comment");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Comment(long, long, String)</code> with negative author,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithNegativeAuthorAndNonNullComment() {
        try {
            new Comment(2006, -24680, "comment");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Comment(long, long, String)</code> with zero author,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithZeroAuthorAndNonNullComment() {
        try {
            new Comment(2006, 0, "comment");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Comment(long, long, String)</code> null comment,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithPositiveIdAuthorAndNullComment() {
        try {
            new Comment(2006, 24680, (String) null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Comment(long, long, String)</code> with positive id and author and non-null comment,
     * instance of <code>Comment</code> should be created.
     */
    public void testConstructorWithPositiveIdAuthorAndNonNullComment() {
        Comment cm = new Comment(2006, 24680, "comment");
        assertNotNull("Comment instance should be created", cm);
        assertTrue("id field should be 2006", cm.getId() == 2006);
        assertTrue("author field should be 24680", cm.getAuthor() == 24680);
        assertNull("commentType field should be null", cm.getCommentType());
        assertTrue("comment field should be 'comment'", cm.getComment().equals("comment"));
        assertNull("extraInfo field should be null", cm.getExtraInfo());

        // empty comment is acceptable
        Comment cm1 = new Comment(2006, 24680, "");
        assertNotNull("Comment instance should be created", cm1);
        assertTrue("id field should be 2006", cm1.getId() == 2006);
        assertTrue("author field should be 24680", cm1.getAuthor() == 24680);
        assertNull("commentType field should be null", cm1.getCommentType());
        assertTrue("comment field should be ''", cm1.getComment().equals(""));
        assertNull("extraInfo field should be null", cm1.getExtraInfo());

        // all whitespace comment is acceptable
        Comment cm2 = new Comment(2006, 24680, "   ");
        assertNotNull("Comment instance should be created", cm2);
        assertTrue("id field should be 2006", cm2.getId() == 2006);
        assertTrue("author field should be 24680", cm2.getAuthor() == 24680);
        assertNull("commentType field should be null", cm2.getCommentType());
        assertTrue("comment field should be '   '", cm2.getComment().equals("   "));
        assertNull("extraInfo field should be null", cm2.getExtraInfo());
    }

    /**
     * Test the constructor <code>Comment(long, long, CommentType)</code> with negative id,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithNegativeIdAndNonNullCommentType() {
        try {
            new Comment(-2006, 24680, type);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Comment(long, long, CommentType)</code> with zero id,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithZeroIdAndNonNullCommentType() {
        try {
            new Comment(0, 24680, type);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Comment(long, long, CommentType)</code> with negative author,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithNegativeAuthorAndNonNullCommentType() {
        try {
            new Comment(2006, -24680, type);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Comment(long, long, CommentType)</code> with zero author,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithZeroAuthorAndNonNullCommentType() {
        try {
            new Comment(2006, 0, type);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Comment(long, long, CommentType)</code> null commentType,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithPositiveIdAuthorAndNullCommentType() {
        try {
            new Comment(2006, 24680, (CommentType) null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Comment(long, long, CommentType)</code> with positive id and author and non-null
     * commentType, instance of <code>Comment</code> should be created.
     */
    public void testConstructorWithPositiveIdAuthorAndNonNullCommentType() {
        Comment cm = new Comment(2006, 24680, type);
        assertNotNull("Comment instance should be created", cm);
        assertTrue("id field should be 2006", cm.getId() == 2006);
        assertTrue("author field should be 24680", cm.getAuthor() == 24680);
        assertTrue("commentType field should not be null", cm.getCommentType().equals(type));
        assertNull("comment field should be null", cm.getComment());
        assertNull("extraInfo field should be null", cm.getExtraInfo());
    }

    /**
     * Test the constructor <code>Comment(long, long, CommentType, String)</code> with negative id,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithNegativeIdAndOtherValidArgus() {
        try {
            new Comment(-2006, 24680, type, "comment");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Comment(long, long, CommentType, String)</code> with zero id,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithZeroIdAndOtherValidArgus() {
        try {
            new Comment(0, 24680, type, "comment");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Comment(long, long, CommentType, String)</code> with negative author,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithNegativeAuthorAndOtherValidArgus() {
        try {
            new Comment(2006, -24680, type, "comment");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Comment(long, long, CommentType, String)</code> with zero author,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithZeroAuthorAndOtherValidArgus() {
        try {
            new Comment(2006, 0, type, "comment");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Comment(long, long, CommentType, String)</code> null comment,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithNullCommentAndOtherValidArgus() {
        try {
            new Comment(2006, 24680, type, null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Comment(long, long, CommentType, String)</code> null commentType,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithNullCommentTypeAndOtherValidArgus() {
        try {
            new Comment(2006, 24680, null, "comment");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Comment(long, long, CommentType, String)</code> with positive id and author and
     * non-null comment and commentType, instance of <code>Comment</code> should be created.
     */
    public void testConstructorWithValidArgus() {
        Comment cm = new Comment(2006, 24680, type, "comment");
        assertNotNull("Comment instance should be created", cm);
        assertTrue("id field should be 2006", cm.getId() == 2006);
        assertTrue("author field should be 24680", cm.getAuthor() == 24680);
        assertTrue("commentType field should not be null", cm.getCommentType().equals(type));
        assertTrue("comment field should be 'comment'", cm.getComment().equals("comment"));
        assertNull("extraInfo field should be null", cm.getExtraInfo());

        // empty comment is acceptable
        Comment cm1 = new Comment(2006, 24680, type, "");
        assertNotNull("Comment instance should be created", cm1);
        assertTrue("id field should be 2006", cm1.getId() == 2006);
        assertTrue("author field should be 24680", cm1.getAuthor() == 24680);
        assertTrue("commentType field should not be null", cm1.getCommentType().equals(type));
        assertTrue("comment field should be ''", cm1.getComment().equals(""));
        assertNull("extraInfo field should be null", cm1.getExtraInfo());

        // all whitespace comment is acceptable
        Comment cm2 = new Comment(2006, 24680, type, "   ");
        assertNotNull("Comment instance should be created", cm2);
        assertTrue("id field should be 2006", cm2.getId() == 2006);
        assertTrue("author field should be 24680", cm2.getAuthor() == 24680);
        assertTrue("commentType field should not be null", cm2.getCommentType().equals(type));
        assertTrue("comment field should be '   '", cm2.getComment().equals("   "));
        assertNull("extraInfo field should be null", cm2.getExtraInfo());
    }

    /**
     * Test the method <code>setId(long)</code> with negative id, <code>IllegalArgumentException</code> should be
     * thrown.
     */
    public void testSetIdWithNegativeId() {
        try {
            comment.setId(-2006);
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
            comment.setId(0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setId(long)</code> with positive id, the id field should be set successfully.
     */
    public void testSetIdWithPositiveId() {
        comment.setId(2006);
        assertTrue("id field should be 2006", comment.getId() == 2006);
    }

    /**
     * Test the method <code>getId()</code>, the id value should be returned successfully.
     */
    public void testGetId() {
        assertTrue("getId method should return 20060624", comment.getId() == 20060624);
    }

    /**
     * Test the method <code>resetId()</code>, the id field should be set to -1.
     */
    public void testResetId() {
        comment.resetId();
        assertTrue("id field should be -1", comment.getId() == -1);
    }

    /**
     * Test the method <code>setAuthor(long)</code> with negative author, <code>IllegalArgumentException</code>
     * should be thrown.
     */
    public void testSetAuthorWithNegativeAuthor() {
        try {
            comment.setAuthor(-2006);
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
            comment.setAuthor(0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setAuthor(long)</code> with positive author, the author field should be set successfully.
     */
    public void testSetAuthorWithPositiveAuthor() {
        comment.setAuthor(24680);
        assertTrue("author field should be 24680", comment.getAuthor() == 24680);
    }

    /**
     * Test the method <code>getAuthor()</code>, the author value should be returned successfully.
     */
    public void testGetAuthor() {
        assertTrue("getAuthor method should return 13579", comment.getAuthor() == 13579);
    }

    /**
     * Test the method <code>resetAuthor()</code>, the author field should be set to -1.
     */
    public void testResetAuthor() {
        comment.resetAuthor();
        assertTrue("author field should be -1", comment.getAuthor() == -1);
    }

    /**
     * Test the method <code>setCommentType(CommentType)</code>, the commentType field should be set successfully.
     */
    public void testSetCommentType() {
        CommentType other = new CommentType();
        comment.setCommentType(other);
        assertTrue("commentType field should be set", comment.getCommentType().equals(other));

        // null is acceptable
        comment.setCommentType(null);
        assertNull("commentType field should be null", comment.getCommentType());
    }

    /**
     * Test the method <code>getCommentType()</code>, the commentType value should be returned successfully.
     */
    public void testGetCommentType() {
        assertTrue("getCommentType method should return correctly", comment.getCommentType().equals(type));
    }

    /**
     * Test the method <code>resetCommentType()</code>, the commentType field should be set to null.
     */
    public void testResetCommentType() {
        comment.resetCommentType();
        assertNull("commentType field should be null", comment.getCommentType());
    }

    /**
     * Test the method <code>setComment(Comment)</code>, the comment field should be set successfully.
     */
    public void testSetComment() {
        comment.setComment("other comment");
        assertTrue("comment field should be 'other comment'", comment.getComment().equals("other comment"));

        // null comment is acceptable
        comment.setComment(null);
        assertNull("comment field should be null", comment.getComment());

        // empty comment is acceptable
        comment.setComment("");
        assertTrue("comment field should be ''", comment.getComment().equals(""));

        // all whitespace comment is acceptable
        comment.setComment("   ");
        assertTrue("comment field should be '   '", comment.getComment().equals("   "));
    }

    /**
     * Test the method <code>getComment()</code>, the comment value should be returned successfully.
     */
    public void testGetComment() {
        assertTrue("getComment method should return correctly", comment.getComment().equals("It's a comment"));
    }

    /**
     * Test the method <code>resetComment()</code>, the comment field should be set to null.
     */
    public void testResetComment() {
        comment.resetComment();
        assertNull("comment field should be null", comment.getComment());
    }

    /**
     * Test the method <code>setExtraInfo(Serializable)</code>, the extraInfo field should be set successfully.
     */
    public void testSetExtraInfo() {
        Serializable other = new String("other");
        comment.setExtraInfo(other);
        assertTrue("extraInfo field should be set", comment.getExtraInfo().equals(other));

        // null is acceptable
        comment.setExtraInfo(null);
        assertNull("extraInfo field should be null", comment.getExtraInfo());
    }

    /**
     * Test the method <code>getExtraInfo()</code>, the extraInfo value should be returned successfully.
     */
    public void testGetExtraInfo() {
        assertTrue("getExtraInfo method should return correctly", comment.getExtraInfo().equals(serializable));
    }

    /**
     * Test the method <code>resetExtraInfo()</code>, the extraInfo field should be set to null.
     */
    public void testResetExtraInfo() {
        comment.resetExtraInfo();
        assertNull("extraInfo field should be null", comment.getExtraInfo());
    }
}
