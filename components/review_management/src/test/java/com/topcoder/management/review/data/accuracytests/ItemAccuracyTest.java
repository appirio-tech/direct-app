/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.data.accuracytests;

import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.CommentType;
import com.topcoder.management.review.data.Item;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.Serializable;


/**
 * <p>
 * This accuracy tests addresses the functionality provided by the <code>Item</code> class. It
 * tests the Item(), Item(long), Item(long, long), Item(long, long, Serializable), constructors;
 * getId(), setId(long), resetId(), addComment(Comment), addComments(Comment[]), clearComments(),
 * getAllComments(), getAnswer(), getComment(int), getDocument(), getNumberOfComments(),
 * getQuestion(), removeComment(Comment), removeComment(long), removeComments(Comment[]),
 * resetAnswer(), resetDocument(), resetQuestion(), setAnswer(Serializable), setDocument(Long),
 * setQuestion(long) methods.
 * </p>
 *
 * @author icyriver
 * @version 1.0
 */
public class ItemAccuracyTest extends TestCase {
    /**
     * <p>
     * The id of <code>Item</code> instance for test.
     * </p>
     */
    private long id = 1;

    /**
     * <p>
     * The question id of <code>Item</code> instance for test.
     * </p>
     */
    private long question = 46;

    /**
     * <p>
     * The <code>Serializable</code> answer used for test.
     * </p>
     */
    private Serializable answer = new String("Great way to solve this problem!");

    /**
     * <p>
     * The document id of <code>Item</code> instance for test.
     * </p>
     */
    private Long document = new Long(14566);

    /**
     * <p>
     * The comment type of <code>Comment</code> instance for test.
     * </p>
     */
    private CommentType type = new CommentType(2, "Recommend");

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
    private Comment comm3 = new Comment(2, 576, type, "This is a great item.");

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
    private Item item = null;

    /**
     * <p>
     * Test suite of <code>ItemAccuracyTest</code>.
     * </p>
     *
     * @return Test suite of <code>ItemAccuracyTest</code>.
     */
    public static Test suite() {
        return new TestSuite(ItemAccuracyTest.class);
    }

    /**
     * <p>
     * Set up the accuracy testing envionment.
     * </p>
     */
    protected void setUp() {
        // new the comment array here.
        comments = new Comment[] {comm1, comm2, comm3};
    }

    /**
     * <p>
     * Accuracy test of <code>Item()</code> constructor.
     * </p>
     */
    public void testItemCtor1() {
        item = new Item();

        // check null here.
        assertNotNull("Create Item failed.", item);

        // check the type here.
        assertTrue("Item should extend Serializable.", item instanceof Serializable);

        // check the fields here.
        assertEquals("The id should be: -1.", -1, item.getId());
        assertEquals("The question should be: -1.", -1, item.getQuestion());
        assertEquals("The comments' size should be: 0.", 0, item.getNumberOfComments());
        assertNull("The answer should be: null.", item.getAnswer());
        assertNull("The document should be: null.", item.getDocument());
    }

    /**
     * <p>
     * Accuracy test of <code>Item(long)</code> constructor.
     * </p>
     */
    public void testItemCtor2() {
        item = new Item(id);

        // check null here.
        assertNotNull("Create Item failed.", item);

        // check the type here.
        assertTrue("Item should extend Serializable.", item instanceof Serializable);

        // check the fields here.
        assertEquals("The id should be: " + id + ".", id, item.getId());
        assertEquals("The question should be: -1.", -1, item.getQuestion());
        assertEquals("The comments' size should be: 0.", 0, item.getNumberOfComments());
        assertNull("The answer should be: null.", item.getAnswer());
        assertNull("The document should be: null.", item.getDocument());
    }

    /**
     * <p>
     * Accuracy test of <code>Item(long, long)</code> constructor.
     * </p>
     */
    public void testItemCtor3() {
        item = new Item(id, question);

        // check null here.
        assertNotNull("Create Item failed.", item);

        // check the type here.
        assertTrue("Item should extend Serializable.", item instanceof Serializable);

        // check the fields here.
        assertEquals("The id should be: " + id + ".", id, item.getId());
        assertEquals("The question should be: " + question + ".", question, item.getQuestion());
        assertEquals("The comments' size should be: 0.", 0, item.getNumberOfComments());
        assertNull("The answer should be: null.", item.getAnswer());
        assertNull("The document should be: null.", item.getDocument());
    }

    /**
     * <p>
     * Accuracy test of <code>Item(long, long)</code> constructor.
     * </p>
     */
    public void testItemCtor4() {
        item = new Item(id, question, answer);

        // check null here.
        assertNotNull("Create Item failed.", item);

        // check the type here.
        assertTrue("Item should extend Serializable.", item instanceof Serializable);

        // check the fields here.
        assertEquals("The id should be: " + id + ".", id, item.getId());
        assertEquals("The question should be: " + question + ".", question, item.getQuestion());
        assertEquals("The comments' size should be: 0.", 0, item.getNumberOfComments());
        assertEquals("The answer is incorrect.", answer, item.getAnswer());
        assertNull("The document should be: null.", item.getDocument());
    }

    /**
     * <p>
     * Accuracy test of the <code>getId()</code> method.
     * </p>
     */
    public void testMethod_getId() {
        item = new Item();

        // check the id here.
        assertEquals("The id should be: -1.", -1, item.getId());

        // set the id and then check it.
        item.setId(id);
        assertEquals("The id should be: " + id + ".", id, item.getId());

        // reset the id and then check it.
        item.resetId();
        assertEquals("The id should be: -1.", -1, item.getId());
    }

    /**
     * <p>
     * Accuracy test of the <code>setId()</code> method.
     * </p>
     */
    public void testMethod_setId() {
        item = new Item();

        // set the id and then check it.
        item.setId(id);
        assertEquals("The id should be: " + id + ".", id, item.getId());

        // reset the id and then check it.
        item.resetId();
        item.setId(100);
        assertEquals("The id should be: 100.", 100, item.getId());
    }

    /**
     * <p>
     * Accuracy test of the <code>resetId()</code> method.
     * </p>
     */
    public void testMethod_resetId() {
        item = new Item(id);

        // reset the id and then check it.
        item.resetId();
        assertEquals("The id should be: -1.", -1, item.getId());

        // reset the id and then check it.
        item.setId(id);
        item.resetId();
        assertEquals("The id should be: -1.", -1, item.getId());
    }

    /**
     * <p>
     * Accuracy test of the <code>getAnswer()</code> method.
     * </p>
     */
    public void testMethod_getAnswer() {
        item = new Item();

        // check the answer here.
        assertNull("The answer should be:null.", item.getAnswer());

        // set the answer and then check it.
        item.setAnswer(answer);
        assertEquals("The answer is incorrect.", answer, item.getAnswer());

        // reset the answer and then check it.
        item.resetAnswer();
        assertNull("The answer should be:null.", item.getAnswer());
    }

    /**
     * <p>
     * Accuracy test of the <code>setAnswer()</code> method.
     * </p>
     */
    public void testMethod_setAnswer() {
        item = new Item();

        // set the answer and then check it.
        item.setAnswer(answer);
        assertEquals("The answer is incorrect.", answer, item.getAnswer());

        // set the answer again and then check it.
        item.setAnswer("new answer");
        assertEquals("The answer is incorrect.", "new answer", item.getAnswer());
    }

    /**
     * <p>
     * Accuracy test of the <code>resetAnswer()</code> method.
     * </p>
     */
    public void testMethod_resetAnswer() {
        item = new Item(id, question, answer);

        // reset the answer and then check it.
        item.resetAnswer();
        assertNull("The answer should be:null.", item.getAnswer());

        // set the answer and then check it.
        item.setAnswer(answer);
        item.resetAnswer();
        assertNull("The answer should be:null.", item.getAnswer());
    }

    /**
     * <p>
     * Accuracy test of the <code>getQuestion()</code> method.
     * </p>
     */
    public void testMethod_getQuestion() {
        item = new Item();

        // check the question here.
        assertEquals("The question should be: -1.", -1, item.getQuestion());

        // set the question and then check it.
        item.setQuestion(question);
        assertEquals("The question should be: " + question + ".", question, item.getQuestion());

        // reset the question and then check it.
        item.resetQuestion();
        assertEquals("The question should be: -1.", -1, item.getQuestion());
    }

    /**
     * <p>
     * Accuracy test of the <code>setQuestion()</code> method.
     * </p>
     */
    public void testMethod_setQuestion() {
        item = new Item();

        // set the question and then check it.
        item.setQuestion(question);
        assertEquals("The question should be: " + question + ".", question, item.getQuestion());

        // reset the question and then check it.
        item.resetQuestion();
        item.setQuestion(34);
        assertEquals("The question should be: 34.", 34, item.getQuestion());
    }

    /**
     * <p>
     * Accuracy test of the <code>resetQuestion()</code> method.
     * </p>
     */
    public void testMethod_resetQuestion() {
        item = new Item(id);

        // reset the question and then check it.
        item.resetQuestion();
        assertEquals("The question should be: -1.", -1, item.getQuestion());

        // set the question and then check it.
        item.setQuestion(question);
        item.resetQuestion();
        assertEquals("The question should be: -1.", -1, item.getQuestion());
    }

    /**
     * <p>
     * Accuracy test of the <code>getDocument()</code> method.
     * </p>
     */
    public void testMethod_getDocument() {
        item = new Item();

        // check the document here.
        assertNull("The document should be:null.", item.getDocument());

        // set the document and then check it.
        item.setDocument(document);
        assertEquals("The document is incorrect.", document, item.getDocument());

        // reset the document and then check it.
        item.resetDocument();
        assertNull("The document should be:null.", item.getDocument());
    }

    /**
     * <p>
     * Accuracy test of the <code>setDocument()</code> method.
     * </p>
     */
    public void testMethod_setDocument() {
        item = new Item(id, question);

        // set the document and then check it.
        item.setDocument(document);
        assertEquals("The document is incorrect.", document, item.getDocument());

        // set the document again and then check it.
        item.setDocument(new Long(13245));
        assertEquals("The document is incorrect.", new Long(13245), item.getDocument());
    }

    /**
     * <p>
     * Accuracy test of the <code>resetDocument()</code> method.
     * </p>
     */
    public void testMethod_resetDocument() {
        item = new Item(id, question);

        // set the document and then check it.
        item.setDocument(document);

        // reset the document and then check it.
        item.resetDocument();
        assertNull("The document should be:null.", item.getDocument());

        // reset the document and then check it.
        item.resetDocument();
        assertNull("The document should be:null.", item.getDocument());
    }

    /**
     * <p>
     * Accuracy test of the <code>addComment(Comment)</code> method.
     * </p>
     */
    public void testMethod_addComment() {
        item = new Item(id, question);

        // add each comment here.
        for (int i = 0, j = 1; i < comments.length; i++, j++) {
            item.addComment(comments[i]);
            assertEquals("The comments' size should be: " + j + "", j, item.getNumberOfComments());
            assertEquals("The comment does not add correctly.", comments[i], item.getComment(i));
        }

        // add each comment again.
        for (int i = 0; i < comments.length; i++) {
            item.addComment(comments[i]);
            assertEquals("The comments' size should be: 3", 3, item.getNumberOfComments());
            assertEquals("The comment does not add correctly.", comments[i], item.getComment(i));
        }
    }

    /**
     * <p>
     * Accuracy test of the <code>addComments(Comment[])</code> method.
     * </p>
     */
    public void testMethod_addComments() {
        item = new Item(id, question);

        // add comment array here.
        item.addComments(comments);

        // check the result.
        assertEquals("The comments' size should be: 3", 3, item.getNumberOfComments());

        for (int i = 0; i < comments.length; i++) {
            assertEquals("The comment does not add correctly.", comments[i], item.getComment(i));
        }

        // add the comment array again here.
        item.addComments(comments);

        // check the result.
        assertEquals("The comments' size should be: 3", 3, item.getNumberOfComments());

        for (int i = 0; i < comments.length; i++) {
            assertEquals("The comment does not add correctly.", comments[i], item.getComment(i));
        }
    }

    /**
     * <p>
     * Accuracy test of the <code>clearComments()</code> method.
     * </p>
     */
    public void testMethod_clearComments() {
        item = new Item(id, question);

        // add a comment here.
        item.addComment(comm1);
        item.clearComments();
        assertEquals("The comments' size should be: 0", 0, item.getNumberOfComments());

        // add comment array here.
        item.addComments(comments);
        item.clearComments();
        assertEquals("The comments' size should be: 0", 0, item.getNumberOfComments());
    }

    /**
     * <p>
     * Accuracy test of the <code>getAllComments()</code> method.
     * </p>
     */
    public void testMethod_getAllComments() {
        item = new Item(id, question);

        // test for empty comment array here.
        Comment[] allComments = item.getAllComments();
        assertEquals("The comments' size should be: 0", 0, allComments.length);

        // test for one comment in array here.
        item.addComment(comm1);
        allComments = item.getAllComments();
        assertEquals("The comments' size should be: 1", 1, allComments.length);

        // test for some comments in array here.
        item.addComments(comments);
        allComments = item.getAllComments();
        assertEquals("The comments' size should be: 3", 3, allComments.length);
    }

    /**
     * <p>
     * Accuracy test of the <code>getComment(int)</code> method.
     * </p>
     */
    public void testMethod_getComment() {
        item = new Item(id, question);

        // add one comment here.
        item.addComment(comments[1]);
        assertEquals("The comment does not add correctly.", comments[1], item.getComment(0));

        // add comment array here.
        item.addComments(comments);

        // check the result.
        assertEquals("The comment does not add correctly.", comments[1], item.getComment(0));
        assertEquals("The comment does not add correctly.", comments[0], item.getComment(1));
        assertEquals("The comment does not add correctly.", comments[2], item.getComment(2));
    }

    /**
     * <p>
     * Accuracy test of the <code>removeComment(long)</code> method.
     * </p>
     */
    public void testMethod_removeComment1() {
        item = new Item(id, question);

        // add comment array here.
        item.addComments(comments);

        // remove each comment.
        item.removeComment(comments[1].getId());
        assertEquals("The comments' size should be: 2", 2, item.getNumberOfComments());
        item.removeComment(comments[2].getId());
        assertEquals("The comments' size should be: 2", 1, item.getNumberOfComments());

        // remove inexistent comment here.
        for (int i = 1; i < 10; i++) {
            item.removeComment(i);
            assertEquals("The comments' size should be: 1", 1, item.getNumberOfComments());
        }

        // remove inexistent comment here.
        item.removeComment(Long.MAX_VALUE);
    }

    /**
     * <p>
     * Accuracy test of the <code>removeComment(Comment)</code> method.
     * </p>
     */
    public void testMethod_removeComment2() {
        item = new Item(id, question);

        // add comment array here.
        item.addComments(comments);

        // remove each comment.
        for (int i = 0, j = 2; i < comments.length; i++, j--) {
            item.removeComment(comments[i]);
            assertEquals("The comments' size should be: " + j + ".", j, item.getNumberOfComments());
        }

        // remove inexistent comment here.
        for (int i = 1; i < comments.length; i++) {
            item.removeComment(comments[i]);
            assertEquals("The comments' size should be: 0", 0, item.getNumberOfComments());
        }
    }

    /**
     * <p>
     * Accuracy test of the <code>removeComment(Comment[])</code> method.
     * </p>
     */
    public void testMethod_removeComments() {
        item = new Item(id, question);

        // add comment array here.
        item.addComments(comments);

        // remove one comment.
        item.removeComments(new Comment[] {comm1});
        assertEquals("The comments' size should be: 2", 2, item.getNumberOfComments());

        // remove all comments.
        item.removeComments(comments);
        assertEquals("The comments' size should be: 0", 0, item.getNumberOfComments());
    }

    /**
     * <p>
     * Accuracy test of the <code>getNumberOfComments()</code> method.
     * </p>
     */
    public void testMethod_getNumberOfComments() {
        item = new Item(id, question);
        assertEquals("The comments' size should be: 0", 0, item.getNumberOfComments());

        // add some comments here.
        item.addComments(comments);
        assertEquals("The comments' size should be: 3", 3, item.getNumberOfComments());

        // clear all comments and then test it.
        item.clearComments();
        assertEquals("The comments' size should be: 0", 0, item.getNumberOfComments());
    }
}
