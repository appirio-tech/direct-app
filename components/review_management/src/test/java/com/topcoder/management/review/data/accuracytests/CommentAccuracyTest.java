/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.data.accuracytests;

import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.CommentType;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.Serializable;


/**
 * <p>
 * This accuracy tests addresses the functionality provided by the <code>Comment</code> class. It
 * tests the Comment(), Comment(long), Comment(long, long), Comment(long, long, CommentType),
 * Comment(long, long, String), Comment(long, long, CommentType, String) constructors; getId(),
 * getAuthor(), getComment(), getCommentType(), getExtraInfo(), resetId(), resetAuthor(),
 * resetComment(), resetCommentType(), resetExtraInfo() setId(long), setAuthor(long),
 * setComment(String), setCommentType(CommentType) and setExtraInfo(Serializable) methods.
 * </p>
 *
 * @author icyriver
 * @version 1.0
 */
public class CommentAccuracyTest extends TestCase {
    /**
     * <p>
     * The id of <code>Comment</code> instance for test.
     * </p>
     */
    private long id = 1;

    /**
     * <p>
     * The author of <code>Comment</code> instance for test.
     * </p>
     */
    private long author = 10001;

    /**
     * <p>
     * The comment type of <code>Comment</code> instance for test.
     * </p>
     */
    private CommentType type = new CommentType(2, "Recommend");

    /**
     * <p>
     * The comment text of <code>Comment</code> instance for test.
     * </p>
     */
    private String commentText = "Comment";

    /**
     * <p>
     * The <code>Serializable</code> instance used for test.
     * </p>
     */
    private Serializable extraInfo = new String("Serializable Information");

    /**
     * <p>
     * The instance of <code>Comment</code> for test.
     * </p>
     */
    private Comment comment = null;

    /**
     * <p>
     * Test suite of <code>CommentAccuracyTest</code>.
     * </p>
     *
     * @return Test suite of <code>CommentAccuracyTest</code>.
     */
    public static Test suite() {
        return new TestSuite(CommentAccuracyTest.class);
    }

    /**
     * <p>
     * Accuracy test of <code>Comment()</code> constructor.
     * </p>
     */
    public void testCommentCtor1() {
        comment = new Comment();

        // check null here.
        assertNotNull("Create Comment failed.", comment);

        // check the type here.
        assertTrue("Comment should extend Serializable.", comment instanceof Serializable);

        // check the fields here.
        assertEquals("The id should be: -1.", -1, comment.getId());
        assertEquals("The author should be: -1.", -1, comment.getAuthor());
        assertNull("The comment text should be: null.", comment.getComment());
        assertNull("The comment type should be: null.", comment.getCommentType());
        assertNull("The extra info should be: null.", comment.getExtraInfo());
    }

    /**
     * <p>
     * Accuracy test of <code>Comment(long)</code> constructor.
     * </p>
     */
    public void testCommentCtor2() {
        comment = new Comment(id);

        // check null here.
        assertNotNull("Create Comment failed.", comment);

        // check the type here.
        assertTrue("Comment should extend Serializable.", comment instanceof Serializable);

        // check the fields here.
        assertEquals("The id should be: " + id + ".", id, comment.getId());
        assertEquals("The author should be: -1.", -1, comment.getAuthor());
        assertNull("The comment text should be: null.", comment.getComment());
        assertNull("The comment type should be: null.", comment.getCommentType());
        assertNull("The extra info should be: null.", comment.getExtraInfo());
    }

    /**
     * <p>
     * Accuracy test of <code>Comment(long, long)</code> constructor.
     * </p>
     */
    public void testCommentCtor3() {
        comment = new Comment(id, author);

        // check null here.
        assertNotNull("Create Comment failed.", comment);

        // check the type here.
        assertTrue("Comment should extend Serializable.", comment instanceof Serializable);

        // check the fields here.
        assertEquals("The id should be: " + id + ".", id, comment.getId());
        assertEquals("The author should be: " + author + ".", author, comment.getAuthor());
        assertNull("The comment text should be: null.", comment.getComment());
        assertNull("The comment type should be: null.", comment.getCommentType());
        assertNull("The extra info should be: null.", comment.getExtraInfo());
    }

    /**
     * <p>
     * Accuracy test of <code>Comment(long, long, CommentType)</code> constructor.
     * </p>
     */
    public void testCommentCtor4() {
        comment = new Comment(id, author, type);

        // check null here.
        assertNotNull("Create Comment failed.", comment);

        // check the type here.
        assertTrue("Comment should extend Serializable.", comment instanceof Serializable);

        // check the fields here.
        assertEquals("The id should be: " + id + ".", id, comment.getId());
        assertEquals("The author should be: " + author + ".", author, comment.getAuthor());
        assertEquals("The comment type is incorrect.", type, comment.getCommentType());
        assertNull("The comment text should be: null.", comment.getComment());
        assertNull("The extra info should be: null.", comment.getExtraInfo());
    }

    /**
     * <p>
     * Accuracy test of <code>Comment(long, long, CommentType, String)</code> constructor.
     * </p>
     */
    public void testCommentCtor5() {
        comment = new Comment(id, author, type, commentText);

        // check null here.
        assertNotNull("Create Comment failed.", comment);

        // check the type here.
        assertTrue("Comment should extend Serializable.", comment instanceof Serializable);

        // check the fields here.
        assertEquals("The id should be: " + id + ".", id, comment.getId());
        assertEquals("The author should be: " + author + ".", author, comment.getAuthor());
        assertEquals("The comment text is incorrect.", commentText, comment.getComment());
        assertEquals("The comment type is incorrect.", type, comment.getCommentType());
        assertNull("The extra info should be: null.", comment.getExtraInfo());
    }

    /**
     * <p>
     * Accuracy test of <code>Comment(long, long, String)</code> constructor.
     * </p>
     */
    public void testCommentCtor6() {
        comment = new Comment(id, author, commentText);

        // check null here.
        assertNotNull("Create Comment failed.", comment);

        // check the type here.
        assertTrue("Comment should extend Serializable.", comment instanceof Serializable);

        // check the fields here.
        assertEquals("The id should be: " + id + ".", id, comment.getId());
        assertEquals("The author should be: " + author + ".", author, comment.getAuthor());
        assertEquals("The comment text is incorrect.", commentText, comment.getComment());
        assertNull("The comment type should be: null.", comment.getCommentType());
        assertNull("The extra info should be: null.", comment.getExtraInfo());
    }

    /**
     * <p>
     * Accuracy test of the <code>getId()</code> method.
     * </p>
     */
    public void testMethod_getId() {
        comment = new Comment(id, author, commentText);

        // check the id here.
        assertEquals("The id should be: " + id + ".", id, comment.getId());

        // set the id and then test it.
        comment.setId(100);
        assertEquals("The id should be: 100.", 100, comment.getId());

        // reset the id and then test it.
        comment.resetId();
        assertEquals("The id should be: -1.", -1, comment.getId());
    }

    /**
     * <p>
     * Accuracy test of the <code>setId()</code> method.
     * </p>
     */
    public void testMethod_setId() {
        comment = new Comment();

        // set the id and then test it.
        comment.setId(id);
        assertEquals("The id should be: " + id + ".", id, comment.getId());

        // set the id again and then test it.
        comment.setId(100);
        assertEquals("The id should be: 100.", 100, comment.getId());
    }

    /**
     * <p>
     * Accuracy test of the <code>resetId()</code> method.
     * </p>
     */
    public void testMethod_resetId() {
        comment = new Comment(id, author, commentText);

        // reset the id and then test it.
        comment.resetId();
        assertEquals("The id should be: -1.", -1, comment.getId());

        // set the id and then test it.
        comment.setId(id);
        comment.resetId();
        assertEquals("The id should be: -1.", -1, comment.getId());
    }

    /**
     * <p>
     * Accuracy test of the <code>getAuthor()</code> method.
     * </p>
     */
    public void testMethod_getAuthor() {
        comment = new Comment(id, author, commentText);

        // check the author here.
        assertEquals("The author should be: " + author + ".", author, comment.getAuthor());

        // set the author and then test it.
        comment.setAuthor(20001);
        assertEquals("The author should be: 100.", 20001, comment.getAuthor());

        // reset the author and then test it.
        comment.resetAuthor();
        assertEquals("The author should be: -1.", -1, comment.getAuthor());
    }

    /**
     * <p>
     * Accuracy test of the <code>setAuthor()</code> method.
     * </p>
     */
    public void testMethod_setAuthor() {
        comment = new Comment();

        // set the author and then test it.
        comment.setAuthor(author);
        assertEquals("The author should be: " + author + ".", author, comment.getAuthor());

        // set the author again and then test it.
        comment.setAuthor(20001);
        assertEquals("The author should be: 100.", 20001, comment.getAuthor());
    }

    /**
     * <p>
     * Accuracy test of the <code>resetAuthor()</code> method.
     * </p>
     */
    public void testMethod_resetAuthor() {
        comment = new Comment(id, author, commentText);

        // reset the author and then test it.
        comment.resetAuthor();
        assertEquals("The author should be: -1.", -1, comment.getAuthor());

        // set the author and then test it.
        comment.setAuthor(author);
        comment.resetAuthor();
        assertEquals("The author should be: -1.", -1, comment.getAuthor());
    }

    /**
     * <p>
     * Accuracy test of the <code>getComment()</code> method.
     * </p>
     */
    public void testMethod_getComment() {
        comment = new Comment(id, author, commentText);

        // check the comment text here.
        assertEquals("The comment text is incorrect.", commentText, comment.getComment());

        // set the comment text and then test it.
        comment.setComment("good");
        assertEquals("The comment text is incorrect.", "good", comment.getComment());

        // reset the comment text and then test it.
        comment.resetComment();
        assertNull("The comment text should be: null.", comment.getComment());
    }

    /**
     * <p>
     * Accuracy test of the <code>setComment()</code> method.
     * </p>
     */
    public void testMethod_setComment() {
        comment = new Comment(id, author);

        // check the comment text here.
        comment.setComment(commentText);
        assertEquals("The comment text is incorrect.", commentText, comment.getComment());

        // set the comment text and then test it.
        comment.setComment("good");
        assertEquals("The comment text is incorrect.", "good", comment.getComment());
    }

    /**
     * <p>
     * Accuracy test of the <code>resetComment()</code> method.
     * </p>
     */
    public void testMethod_resetComment() {
        comment = new Comment(id, author, type, commentText);

        // check the comment text here.
        comment.resetComment();
        assertNull("The comment text should be: null.", comment.getComment());

        // set the comment text and then test it.
        comment.setComment(commentText);
        comment.resetComment();
        assertNull("The comment text should be: null.", comment.getComment());
    }

    /**
     * <p>
     * Accuracy test of the <code>getCommentType()</code> method.
     * </p>
     */
    public void testMethod_getCommentType() {
        comment = new Comment(id, author, type, commentText);

        // check the comment type here.
        assertEquals("The comment type is incorrect.", type, comment.getCommentType());

        // set the comment type and then test it.
        CommentType newType = new CommentType(2, "Required");
        comment.setCommentType(newType);
        assertEquals("The comment type is incorrect.", newType, comment.getCommentType());

        // reset the comment text and then test it.
        comment.resetCommentType();
        assertNull("The comment type should be: null.", comment.getCommentType());
    }

    /**
     * <p>
     * Accuracy test of the <code>setCommentType()</code> method.
     * </p>
     */
    public void testMethod_setCommentType() {
        comment = new Comment(id, author, commentText);

        // check the comment type here.
        comment.setCommentType(type);
        assertEquals("The comment type is incorrect.", type, comment.getCommentType());

        // set the comment type and then test it.
        comment.resetCommentType();

        CommentType newType = new CommentType(2, "Required");
        comment.setCommentType(newType);
        assertEquals("The comment type is incorrect.", newType, comment.getCommentType());
    }

    /**
     * <p>
     * Accuracy test of the <code>resetCommentType()</code> method.
     * </p>
     */
    public void testMethod_resetCommentType() {
        comment = new Comment(id, author, type, commentText);

        // reset the comment text and then test it.
        comment.resetCommentType();
        assertNull("The comment type should be: null.", comment.getCommentType());

        // set the comment type and then test it.
        comment.setCommentType(type);
        comment.resetCommentType();
        assertNull("The comment type should be: null.", comment.getCommentType());
    }

    /**
     * <p>
     * Accuracy test of the <code>getExtraInfo()</code> method.
     * </p>
     */
    public void testMethod_getExtraInfo() {
        comment = new Comment(id, author, type, commentText);

        // check the extra info here.
        comment.setExtraInfo(extraInfo);
        assertEquals("The extra info is incorrect.", extraInfo, comment.getExtraInfo());

        // set the extra info and then test it.
        comment.setExtraInfo(null);
        assertNull("The extra info should be: null.", comment.getExtraInfo());

        // reset the extra info and then test it.
        comment.resetExtraInfo();
        assertNull("The extra info should be: null.", comment.getExtraInfo());
    }

    /**
     * <p>
     * Accuracy test of the <code>setExtraInfo()</code> method.
     * </p>
     */
    public void testMethod_setExtraInfo() {
        comment = new Comment(id, author, type, commentText);

        // check the extra info here.
        comment.setExtraInfo(extraInfo);
        assertEquals("The extra info is incorrect.", extraInfo, comment.getExtraInfo());

        // set the extra info and then test it.
        comment.setExtraInfo("info");
        assertEquals("The extra info is incorrect.", "info", comment.getExtraInfo());

        // set the extra info to null and then test it.
        comment.setExtraInfo(null);
        assertNull("The extra info should be: null.", comment.getExtraInfo());
    }

    /**
     * <p>
     * Accuracy test of the <code>resetExtraInfo()</code> method.
     * </p>
     */
    public void testMethod_resetExtraInfo() {
        comment = new Comment(id, author, type, commentText);

        comment.resetExtraInfo();
        assertNull("The extra info should be: null.", comment.getExtraInfo());

        // check the extra info here.
        comment.setExtraInfo(extraInfo);
        comment.resetExtraInfo();
        assertNull("The extra info should be: null.", comment.getExtraInfo());
    }
}
