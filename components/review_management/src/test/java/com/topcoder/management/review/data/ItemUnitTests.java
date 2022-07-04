/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.data;

import java.io.Serializable;
import java.util.Arrays;

import junit.framework.TestCase;

/**
 * Unit tests for <code>Item</code> class.
 *
 * @author vividmxx
 * @version 1.0
 */
public class ItemUnitTests extends TestCase {
    /**
     * The <code>Item</code> instance used to test against.
     */
    private Item item = null;

    /**
     * The <code>Long</code> instance used for test.
     */
    private Long document = new Long(999);

    /**
     * The <code>Comment</code> instance used for test.
     */
    private Comment comm1 = new Comment();

    /**
     * The <code>Comment</code> instance used for test.
     */
    private Comment comm2 = new Comment(100);

    /**
     * The <code>Serializable</code> instance used for test.
     */
    private Serializable answer = new String("I am an answer");

    /**
     * Set up the test environment.
     */
    protected void setUp() {
        item = new Item(20060624, 13579, answer);
        item.setDocument(document);
        item.addComment(comm1);
        item.addComment(comm2);
    }

    /**
     * Test whether <code>Item</code> class implements the <code>Serializable</code> interface.
     */
    public void testImplements() {
        assertTrue("Item should implement Serializable interface", item instanceof Serializable);
    }

    /**
     * Test the constructor <code>Item()</code>, all fields should have their default unassigned values.
     */
    public void testConstructorWithNoArgu() {
        Item it = new Item();
        assertNotNull("Item instance should be created", it);
        assertTrue("id field should be -1", it.getId() == -1);
        assertTrue("question field should be -1", it.getQuestion() == -1);
        assertNull("answer field should be null", it.getAnswer());
        assertNull("document field should be null", it.getDocument());
        assertTrue("comments field should be empty", it.getNumberOfComments() == 0);
    }

    /**
     * Test the constructor <code>Item(long)</code> with negative id, <code>IllegalArgumentException</code> should
     * be thrown.
     */
    public void testConstructorWithNegativeId() {
        try {
            new Item(-20060624);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Item(long)</code> with zero id, <code>IllegalArgumentException</code> should be
     * thrown.
     */
    public void testConstructorWithZeroId() {
        try {
            new Item(0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Item(long)</code> with positive id, instance of <code>Item</code> should be
     * created.
     */
    public void testConstructorWithPositiveId() {
        Item it = new Item(2006);
        assertNotNull("Item instance should be created", it);
        assertTrue("id field should be 2006", it.getId() == 2006);
        assertTrue("question field should be -1", it.getQuestion() == -1);
        assertNull("answer field should be null", it.getAnswer());
        assertNull("document field should be null", it.getDocument());
        assertTrue("comments field should be empty", it.getNumberOfComments() == 0);
    }

    /**
     * Test the constructor <code>Item(long, long)</code> with negative id, <code>IllegalArgumentException</code>
     * should be thrown.
     */
    public void testConstructorWithNegativeIdAndPositiveQuestion() {
        try {
            new Item(-20060624, 13579);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Item(long, long)</code> with zero id, <code>IllegalArgumentException</code> should
     * be thrown.
     */
    public void testConstructorWithZeroIdAndPositiveQuestion() {
        try {
            new Item(0, 13579);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Item(long, long)</code> with negative question,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithNegativeQuestionAndPositiveId() {
        try {
            new Item(2006, -13579);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Item(long, long)</code> with zero question, <code>IllegalArgumentException</code>
     * should be thrown.
     */
    public void testConstructorWithZeroQuestionAndPositiveId() {
        try {
            new Item(2006, 0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Item(long, long)</code> with positive id and question, instance of <code>Item</code>
     * should be created.
     */
    public void testConstructorWithPositiveQuestionAndId() {
        Item it = new Item(2006, 13579);
        assertNotNull("Item instance should be created", it);
        assertTrue("id field should be 2006", it.getId() == 2006);
        assertTrue("question field should be 13579", it.getQuestion() == 13579);
        assertNull("answer field should be null", it.getAnswer());
        assertNull("document field should be null", it.getDocument());
        assertTrue("comments field should be empty", it.getNumberOfComments() == 0);
    }

    /**
     * Test the constructor <code>Item(long, long, Serializable)</code> with negative id,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithNegativeIdAndNonNullAnswer() {
        try {
            new Item(-20060624, 13579, answer);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Item(long, long, Serializable)</code> with zero id,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithZeroIdAndNonNullAnswer() {
        try {
            new Item(0, 13579, answer);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Item(long, long, Serializable)</code> with negative question,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithNegativeQuestionAndNonNullAnswer() {
        try {
            new Item(2006, -13579, answer);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Item(long, long, Serializable)</code> with zero question,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithZeroQuestionAndNonNullAnswer() {
        try {
            new Item(2006, 0, answer);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Item(long, long, Serializable)</code> with null answer,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithNullAnswer() {
        try {
            new Item(2006, 13579, null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Item(long, long)</code> with positive id and question and non-null answer, instance
     * of <code>Item</code> should be created.
     */
    public void testConstructorWithPositiveQuestionIdAndNonNullAnswer() {
        Item it = new Item(2006, 13579, answer);
        assertNotNull("Item instance should be created", it);
        assertTrue("id field should be 2006", it.getId() == 2006);
        assertTrue("question field should be 13579", it.getQuestion() == 13579);
        assertTrue("answer field should not be null", it.getAnswer().equals(answer));
        assertNull("document field should be null", it.getDocument());
        assertTrue("comments field should be empty", it.getNumberOfComments() == 0);
    }

    /**
     * Test the method <code>setId(long)</code> with negative id, <code>IllegalArgumentException</code> should be
     * thrown.
     */
    public void testSetIdWithNegativeId() {
        try {
            item.setId(-2006);
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
            item.setId(0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setId(long)</code> with positive id, the id field should be set successfully.
     */
    public void testSetIdWithPositiveId() {
        item.setId(2006);
        assertTrue("id field should be 2006", item.getId() == 2006);
    }

    /**
     * Test the method <code>getId()</code>, the id value should be returned successfully.
     */
    public void testGetId() {
        assertTrue("getId method should return 20060624", item.getId() == 20060624);
    }

    /**
     * Test the method <code>resetId()</code>, the id field should be set to -1.
     */
    public void testResetId() {
        item.resetId();
        assertTrue("id field should be -1", item.getId() == -1);
    }

    /**
     * Test the method <code>setQuestion(long)</code> with negative question, <code>IllegalArgumentException</code>
     * should be thrown.
     */
    public void testSetQuestionWithNegativeQuestion() {
        try {
            item.setQuestion(-24680);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setQuestion(long)</code> with zero question, <code>IllegalArgumentException</code>
     * should be thrown.
     */
    public void testSetQuestionWithZeroQuestion() {
        try {
            item.setQuestion(0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setQuestion(long)</code> with positive question, the id field should be set successfully.
     */
    public void testSetQuestionWithPositiveQuestion() {
        item.setQuestion(24680);
        assertTrue("question field should be 24680", item.getQuestion() == 24680);
    }

    /**
     * Test the method <code>getQuestion()</code>, the question value should be returned successfully.
     */
    public void testGetQuestion() {
        assertTrue("getQuestion method should return 13579", item.getQuestion() == 13579);
    }

    /**
     * Test the method <code>resetQuestion()</code>, the question field should be set to -1.
     */
    public void testResetQuestion() {
        item.resetQuestion();
        assertTrue("question field should be -1", item.getQuestion() == -1);
    }

    /**
     * Test the method <code>setAnswer(Serializable)</code>, the answer field should be set successfully.
     */
    public void testSetAnswer() {
        Serializable other = new String("other answer");
        item.setAnswer(other);
        assertTrue("answer field should be set", item.getAnswer().equals(other));

        // null is acceptable
        item.setAnswer(null);
        assertNull("answer field should be null", item.getAnswer());
    }

    /**
     * Test the method <code>getAnswer()</code>, the answer value should be returned successfully.
     */
    public void testGetAnswer() {
        assertTrue("getAnswer method should return correctly", item.getAnswer().equals(answer));
    }

    /**
     * Test the method <code>resetAnswer()</code>, the answer field should be set to null.
     */
    public void testResetAnswer() {
        item.resetAnswer();
        assertNull("answer field should be null", item.getAnswer());
    }

    /**
     * Test the method <code>setDocument(Long)</code> with the invalid Long which wraps a negative value,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testSetDocumentWithNegativeDocument() {
        Long doc = new Long(-1);
        try {
            item.setDocument(doc);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setDocument(Long)</code> with the invalid Long which wraps a zero value,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testSetDocumentWithZeroDocument() {
        Long doc = new Long(0);
        try {
            item.setDocument(doc);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setDocument(Long)</code> with the valid argument, the document field should be set
     * successfully.
     */
    public void testSetDocumentWithValidArgu() {
        Long doc = new Long(1);
        item.setDocument(doc);
        assertTrue("document should be set", item.getDocument().equals(doc));

        // null is acceptable
        item.setDocument(null);
        assertNull("document should be null", item.getDocument());
    }

    /**
     * Test the method <code>getDocument()</code>, the document value should be returned successfully.
     */
    public void testGetDocument() {
        assertTrue("getDocument method should return correctly", item.getDocument().equals(document));
    }

    /**
     * Test the method <code>resetDocument()</code>, the document field should be set to null.
     */
    public void testResetDocument() {
        item.resetDocument();
        assertNull("document field should be null", item.getDocument());
    }

    /**
     * Test the method <code>addComment(Comment)</code> with null comment, <code>IllegalArgumentException</code>
     * should be thrown.
     */
    public void testAddCommentWithNullComment() {
        try {
            item.addComment(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>addComment(Comment)</code> with existent comment, the comment should not be added.
     */
    public void testAddCommentWithExistentComment() {
        int count = item.getNumberOfComments();
        item.addComment(comm2);
        assertTrue("the comment should not be added", item.getNumberOfComments() == count);
    }

    /**
     * Test the method <code>addComment(Comment)</code> with inexistent comment, the comment should be added.
     */
    public void testAddCommentWithInexistentComment() {
        int count = item.getNumberOfComments();
        item.addComment(new Comment());
        assertTrue("the comment should be added", item.getNumberOfComments() == count + 1);
    }

    /**
     * Test the method <code>addComments(Comment[])</code> with null comments, <code>IllegalArgumentException</code>
     * should be thrown.
     */
    public void testAddCommentsWithNullComments() {
        try {
            item.addComments(null);
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
            item.addComments(new Comment[] {new Comment(), null});
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
        int count = item.getNumberOfComments();
        item.addComments(new Comment[] {new Comment(), comm1, comm2});
        assertTrue("one inexistent comment should be added", item.getNumberOfComments() == count + 1);
    }

    /**
     * Test the method <code>removeComment(Comment)</code> with null comment, <code>IllegalArgumentException</code>
     * should be thrown.
     */
    public void testRemoveCommentWithNullComment() {
        try {
            item.removeComment(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>removeComment(Comment)</code> with inexistent comment, the comment should not be removed.
     */
    public void testRemoveCommentWithInexistentComment() {
        int count = item.getNumberOfComments();
        item.removeComment(new Comment());
        assertTrue("the comment should not be removed", item.getNumberOfComments() == count);
    }

    /**
     * Test the method <code>removeComment(Comment)</code> with existent comment, the comment should be removed.
     */
    public void testRemoveCommentWithExistentComment() {
        int count = item.getNumberOfComments();
        item.removeComment(comm1);
        assertTrue("the comment should be removed", item.getNumberOfComments() == count - 1);
    }

    /**
     * Test the method <code>removeComments(Comment[])</code> with null comments,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testRemoveCommentsWithNullComments() {
        try {
            item.removeComments(null);
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
            item.removeComments(new Comment[] {comm1, null});
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>removeComments(Comment[])</code> with valid comments, only existent comments should be
     * added.
     */
    public void testRemoveCommentsWithValidComments() {
        int count = item.getNumberOfComments();
        item.removeComments(new Comment[] {new Comment(), comm1});
        assertTrue("one existent comment should be removed", item.getNumberOfComments() == count - 1);
    }

    /**
     * Test the method <code>removeComment(long)</code> with zero id, <code>IllegalArgumentException</code> should
     * be thrown.
     */
    public void testRemoveCommentWithZeroId() {
        try {
            item.removeComment(0);
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
            item.removeComment(-2);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>removeComment(long)</code> with existent id, the comment should be removed.
     */
    public void testRemoveCommentWithExistentId() {
        int count = item.getNumberOfComments();
        item.removeComment(-1);
        assertTrue("one comment should be removed", item.getNumberOfComments() == count - 1);
        item.removeComment(100);
        assertTrue("one comment should be removed", item.getNumberOfComments() == count - 2);
    }

    /**
     * Test the method <code>removeComment(long)</code> with inexistent id, no comment should be removed.
     */
    public void testRemoveCommentWithInexistentId() {
        int count = item.getNumberOfComments();
        item.removeComment(1);
        assertTrue("no comment should be removed", item.getNumberOfComments() == count);
    }

    /**
     * Test the method <code>clearComments()</code>, all comments should be removed.
     */
    public void testClearComments() {
        assertTrue("comments list should not be empty", item.getNumberOfComments() != 0);
        item.clearComments();
        assertTrue("comments list should be empty", item.getNumberOfComments() == 0);
    }

    /**
     * Test the method <code>getComment(int)</code> with negative commentIndex, <code>IndexOutOfBoundsException</code>
     * should be thrown.
     */
    public void testGetCommentWithNegativeIndex() {
        try {
            item.getComment(-1);
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
            item.getComment(2);
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
        assertTrue("the corresponding comment should be retrieved", item.getComment(0).equals(comm1));
        assertTrue("the corresponding comment should be retrieved", item.getComment(1).equals(comm2));
    }

    /**
     * Test the method <code>getAllComments()</code>, the array contains all the comments should be returned.
     */
    public void testGetAllComments() {
        Comment[] expected = new Comment[] {comm1, comm2};
        assertTrue("the array contains all the comments should be returned",
                Arrays.equals(expected, item.getAllComments()));
    }

    /**
     * Test the method <code>getNumberOfComments()</code>, the size of the comments list should be returned.
     */
    public void testGetNumberOfComments() {
        assertTrue("the size of the comments list should be returned", item.getNumberOfComments() == 2);
    }
}
