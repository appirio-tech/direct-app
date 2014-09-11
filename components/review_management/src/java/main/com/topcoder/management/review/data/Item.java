/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * The <code>Item</code> class is the second level in the review model hierarchy. A review consists of responses to
 * individual items, each of which is represented by an instance of this class. There are two types of data fields for
 * this class: the simple data fields and the comments list. All data fields are mutable, and each data field has a
 * three method (get/set/reset) combination for that data field. The comments list is manipulated through nine methods
 * (two for adding, three for removing, one for clear, and three getters).
 * </p>
 * <p>
 * The only thing to note is the difference between the set/reset method pairs for longs and the set/reset method pairs
 * for non-primitive fields.
 * <ul>
 * <li>When the underlying field is a primitive long value, the set method will not permit the user to set the field to
 * its unassigned value (which is -1). The reset method must be used in this case.</li>
 * <li>When the underlying field is a reference type, the set method can be used both for a normal set and to set the
 * field to its unassigned value (which is null). The reset method is provided as a convenience in this case.</li>
 * </ul>
 * The difference in the set/reset behavior is based on the reasoning that when the field is a primitive long type, it
 * is expected to always be in a valid state once initialization of the object (by loading data from a database or
 * through another method) is complete. Resetting it is not considered a normal action and therefore warrants a special
 * method to enact it. On the other hand, for fields for which an "unassigned" (i.e. null) value is expected to occur in
 * normal use, the set method is allowed to set the field to the "unassigned" value, as this is an expected normal state
 * for these fields. The reset method is provided in these cases as a convenience and to maintain API consistency.
 * </p>
 *
 * <p>
 * Changes in v1.1 (Cockpit Spec Review Backend Service Update v1.0):
 * - replaced Serializable to String for "answer"
 * </p>
 * 
 * <p>
 * Thread-safe: This class is highly mutable, all fields can be changed, it is not thread safe.
 * </p>
 *
 * @author aubergineanode, vividmxx, pulky
 * @version 1.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "item", propOrder = {"answer", "comments", "document", "id", "question"})
public class Item implements Serializable {

    /**
     * <p>
     * The identifier of the <code>Item</code>.
     * </p>
     * <p>
     * The default (unassigned value) for this field is -1, which indicates that the id of the <code>Item</code> has
     * not yet been set through the constructor or a call to <code>setId</code> (or has been set back to this value by
     * a call to <code>resetId</code>). Positive values indicate that the client using the component has assigned an
     * id (in the constructor or through the <code>setId</code> method). It is expected that all <code>Item</code>
     * instances will have unique ids. This is up to the client using the component to enforce, this component will not
     * enforce this condition.
     * </p>
     * <p>
     * This field is initialized to -1 (default value), and also can be initialized in the constructor. It is mutable,
     * and can be positive or equal to -1 (default value). This field can be set using the <code>setId</code> and
     * <code>resetId</code> methods and retrieved using the <code>getId</code> method.
     * </p>
     */
    private long id = -1;

    /**
     * <p>
     * The answer associated with the <code>Item</code>.
     * </p>
     * <p>
     * This field is intended to allow as generic an answer type as possible, and a <code>Serializable</code> enables
     * this class to guarantee that serialization will succeed.
     * </p>
     * <p>
     * The default (unassigned value) for this field is null, which indicates that the answer of the <code>Item</code>
     * has not yet been set through the constructor or a call to <code>setAnswer</code> (or has been set back to this
     * value by a call to <code>resetAnswer</code>). Non-null values indicate that the client using the component has
     * assigned an answer (through the constructor or <code>setAnswer</code> method).
     * </p>
     * <p>
     * This field is initialized to null (default value). It is mutable, and can be any <code>Serializable</code>
     * instance or null (default value). This field can be set using the <code>setAnswer</code> and
     * <code>resetAnswer</code> methods and retrieved using the <code>getAnswer</code> method.
     * </p>
     */
    private String answer = null;

    /**
     * <p>
     * The identifier of the question this <code>Item</code> is about.
     * </p>
     * <p>
     * The question is only an identifier, this component does not define an abstract <code>Question</code> class, and
     * it is up to the application to determine how the question identifier is used.
     * </p>
     * <p>
     * The default (unassigned value) for this field is -1, which indicates that the question of the <code>Item</code>
     * has not yet been set through the constructor or a call to <code>setQuestion</code> (or has been set back to
     * this value by a call to <code>resetQuestion</code>). Positive values indicate that the client using the
     * component has assigned a question (in the constructor or through the <code>setQuestion</code> method).
     * </p>
     * <p>
     * This field is initialized to -1 (default value), and also can be initialized in the constructor. It is mutable,
     * and can be positive or equal to -1 (default value). This field can be set using the <code>setQuestion</code>
     * and <code>resetQuestion</code> methods and retrieved using the <code>getQuestion</code> method.
     * </p>
     */
    private long question = -1;

    /**
     * <p>
     * The identifier of an optional attachment associated with this <code>Item</code>.
     * </p>
     * <p>
     * The default (unassigned value) for this field is null, which indicates that the optional attachment of the
     * <code>Item</code> has not yet been set through a call to <code>setDocument</code> (or has been set back to
     * this value by a call to <code>resetDocument</code>). Non-null values indicate that the client using the
     * component has assigned an optional attachment (through the <code>setDocument</code> method).
     * </p>
     * <p>
     * This field is initialized to null (default value). It is mutable, and can be null or a wrapper of a positive
     * value. This field can be set using the <code>setDocument</code> and <code>resetDocument</code> methods and
     * retrieved using the <code>getDocument</code> method.
     * </p>
     */
    private Long document = null;

    /**
     * <p>
     * The list of <code>Comment</code> instances that are associated with this <code>Item</code>.
     * </p>
     * <p>
     * This would normally seem like the situation to use a set, but the requirements specify that the ordering of
     * comments is important, so ordering must be preserved. Hence the use of a list storage structure.
     * </p>
     * <p>
     * This field is default initialized to an empty list, and is immutable, although its contents may be changed (use
     * <code>addComment</code>, <code>removeComment</code>, <code>addComments</code>,
     * <code>removeComments</code> and <code>clearComments</code> methods). All items in the list are required to be
     * non-null <code>Comment</code> instances, and no duplicates (by reference equality) are allowed. Comments are
     * retrieved through the <code>getComment</code> and <code>getAllComments</code> methods.
     * </p>
     */
    private final List comments = new ArrayList();

    /**
     * <p>
     * Creates a new <code>Item</code> instance, leaving all fields as their default unassigned values (-1 for id and
     * question, null for answer and document).
     * </p>
     */
    public Item() {
        // nothing needed
    }

    /**
     * <p>
     * Creates a new <code>Item</code> instance, setting id to the given value and leaving all other fields as their
     * default unassigned values (-1 for question, null for answer and document).
     * </p>
     *
     * @param id
     *            The identifier for the <code>Item</code>
     * @throws IllegalArgumentException
     *             if <code>id</code> is not positive
     */
    public Item(long id) {
        // just delegate to method setId
        setId(id);
    }

    /**
     * <p>
     * Creates a new <code>Item</code> instance, setting id and question to the given values and leaving all other
     * fields as their default unassigned values (null for answer and document).
     * </p>
     *
     * @param id
     *            The identifier for the <code>Item</code>
     * @param question
     *            The identifier of the question this <code>Item</code> is about
     * @throws IllegalArgumentException
     *             If <code>id</code> or <code>question</code> is not positive
     */
    public Item(long id, long question) {
        this(id);
        // delegate to method setQuestion
        setQuestion(question);
    }

    /**
     * <p>
     * Creates a new <code>Item</code> instance, setting id, question and answer to the given values and leaving
     * document field as its default unassigned values (null).
     * </p>
     *
     * @param id
     *            The identifier for the <code>Item</code>
     * @param question
     *            The identifier of the question this <code>Item</code> is about
     * @param answer
     *            The answer associated with the <code>Item</code>
     * @throws IllegalArgumentException
     *             If <code>id</code> or <code>question</code> is not positive, or <code>answer</code> is null
     */
    public Item(long id, long question, String answer) {
        this(id, question);
        // check answer
        if (answer == null) {
            throw new IllegalArgumentException("answer should not be null.");
        }
        this.answer = answer;
    }

    /**
     * <p>
     * Sets the unique identifier of the <code>Item</code>.
     * </p>
     * <p>
     * This method does not allowing setting of the id to the unassigned value (use the <code>resetId</code> method),
     * but it does allow the id to be changed if it has already been set.
     * </p>
     *
     * @param id
     *            The identifier to assign to the <code>Item</code>
     * @throws IllegalArgumentException
     *             if <code>id</code> is not positive
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * <p>
     * Retrieves the unique identifier of the <code>Item</code>.
     * </p>
     *
     * @return The unique identifier of the <code>Item</code>
     */
    public long getId() {
        return id;
    }

    /**
     * <p>
     * Resets the unique identifier of the <code>Item</code> to its undefined value, which is -1.
     * </p>
     */
    public void resetId() {
        id = -1;
    }

    /**
     * <p>
     * Sets the answer associated with the <code>Item</code>.
     * </p>
     * <p>
     * The <code>answer</code> argument can be null, indicating that no answer is associated with the
     * <code>Item</code>.
     * </p>
     *
     * @param answer
     *            The answer associated with the <code>Item</code>
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * <p>
     * Retrieves the answer associated with the <code>Item</code>.
     * </p>
     * <p>
     * The return value can be null, indicating that no answer is associated with the <code>Item</code>.
     * </p>
     *
     * @return The answer associated with the <code>Item</code>
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * <p>
     * Sets the answer associated with this <code>Item</code> back to its unassigned value, which is null.
     * </p>
     */
    public void resetAnswer() {
        answer = null;
    }

    /**
     * <p>
     * Sets the identifier of the question this <code>Item</code> is about.
     * </p>
     * <p>
     * The <code>question</code> is only an identifier, this component does not define an abstract
     * <code>Question</code> class, and it is up to the application to determine how the question identifier is used.
     * </p>
     * <p>
     * This method does not allowing setting of the question to the unassigned value (use the <code>resetQuestion</code>
     * method), but it does allow the id to be changed if it has already been set.
     * </p>
     *
     * @param question
     *            The identifier of the question this <code>Item</code> is about
     * @throws IllegalArgumentException
     *             if <code>question</code> is not positive
     */
    public void setQuestion(long question) {
        this.question = question;
    }

    /**
     * <p>
     * Retrieves the identifier of the question this <code>Item</code> is about.
     * </p>
     * <p>
     * The returned value is only an identifier, this component does not define an abstract <code>Question</code>
     * class, and it is up to the application to determine how the question identifier is used.
     * </p>
     * <p>
     * This method may return -1, indicating that the comment is not associated with a question.
     * </p>
     *
     * @return The identifier of the question this <code>Item</code> is about
     */
    public long getQuestion() {
        return question;
    }

    /**
     * <p>
     * Resets the identifier of the question this <code>Item</code> is about back to its undefined value, which is -1.
     * </p>
     */
    public void resetQuestion() {
        question = -1;
    }

    /**
     * <p>
     * Sets the identifier of the document that is attached to the <code>Item</code>.
     * </p>
     * <p>
     * The <code>document</code> argument may be null, indicating that no document is associated with the
     * <code>Item</code>. If it is non-null, the wrapped value must be positive.
     * </p>
     *
     * @param document
     *            The identifier for the attached document
     * @throws IllegalArgumentException
     *             If <code>document</code> wraps a non-positive value
     */
    public void setDocument(Long document) {
        this.document = document;
    }

    /**
     * <p>
     * Retrieves the identifier of the document that is attached to the <code>Item</code>.
     * </p>
     * <p>
     * The return value may be null, indicating that no document is associated with the <code>Item</code>. If it is
     * non-null, the wrapped value will be positive.
     * </p>
     *
     * @return The identifier for the attached document
     */
    public Long getDocument() {
        return document;
    }

    /**
     * <p>
     * Resets the identifier of the document that is attached to the <code>Item</code> back to its undefined value,
     * which is null.
     * </p>
     */
    public void resetDocument() {
        document = null;
    }

    /**
     * <p>
     * Adds a <code>Comment</code> to the comments list of this <code>Item</code>.
     * </p>
     * <p>
     * This method will adhere to the no-duplicates restriction of the comments list.
     * </p>
     *
     * @param comment
     *            The comment to add
     * @throws IllegalArgumentException
     *             If <code>comment</code> is null
     */
    public void addComment(Comment comment) {
        if (comment == null) {
            throw new IllegalArgumentException("comment should not be null");
        }
        if (!comments.contains(comment)) {
            comments.add(comment);
        }
    }

    /**
     * <p>
     * Adds all comments in the array to the comments list of this <code>Item</code>.
     * </p>
     * <p>
     * This method will adhere to the no-duplicates restriction of the comments list.
     * </p>
     *
     * @param comments
     *            The array of comments to add to the <code>Item</code>, it may be a 0-length array
     * @throws IllegalArgumentException
     *             If <code>comments</code> is null or has null entries
     */
    public void addComments(Comment[] comments) {
        if (comments == null) {
            throw new IllegalArgumentException("comments should not be null");
        }
        // checks the array for null entries before adding them to the comments list
        // this will guarantee no comment is added if the array has null entries
        for (int i = 0; i < comments.length; i++) {
            if (comments[i] == null) {
                throw new IllegalArgumentException("comments should not have null entries");
            }
        }
        for (int i = 0; i < comments.length; i++) {
            if (!this.comments.contains(comments[i])) {
                this.comments.add(comments[i]);
            }
        }
    }

    /**
     * <p>
     * Removes a <code>Comment</code> from the comments list of this <code>Item</code>.
     * </p>
     * <p>
     * If the given <code>Comment</code> dose not exist in the comments list, nothing is done.
     * </p>
     *
     * @param comment
     *            The comment to remove
     * @throws IllegalArgumentException
     *             If <code>comment</code> is null
     */
    public void removeComment(Comment comment) {
        if (comment == null) {
            throw new IllegalArgumentException("comment should not be null");
        }
        comments.remove(comment);
    }

    /**
     * <p>
     * Removes all comments in the array from the comments list of this <code>Item</code>.
     * </p>
     * <p>
     * Only those comments in the array which exist in the comments list of this <code>Item</code> will be removed.
     * </p>
     *
     * @param comments
     *            The array of comments to remove from the <code>Item</code>, it may be a 0-length array
     * @throws IllegalArgumentException
     *             If <code>comments</code> is null or has null entries
     */
    public void removeComments(Comment[] comments) {
        if (comments == null) {
            throw new IllegalArgumentException("comments should not be null");
        }
        // checks the array for null entries before removing them from the comments list
        // this will guarantee no comment is removed if the array has null entries
        for (int i = 0; i < comments.length; i++) {
            if (comments[i] == null) {
                throw new IllegalArgumentException("comments should not have null entries");
            }
        }
        for (int i = 0; i < comments.length; i++) {
            this.comments.remove(comments[i]);
        }
    }

    /**
     * <p>
     * Removes the first <code>Comment</code> with the given id from the comments list of this <code>Item</code>.
     * </p>
     * <p>
     * If no <code>Comment</code> has the given id, then no <code>Comment</code> will be removed.
     * </p>
     *
     * @param id
     *            The identifier of the <code>Comment</code> to remove
     * @throws IllegalArgumentException
     *             If <code>id</code> is non-positive and not equals to -1
     */
    public void removeComment(long id) {
        if (id <= 0 && id != -1) {
            throw new IllegalArgumentException("id should be positive or -1");
        }
        for (Iterator itr = comments.iterator(); itr.hasNext();) {
            Comment comm = (Comment) itr.next();
            if (comm.getId() == id) {
                itr.remove();
                break;
            }
        }
    }

    /**
     * <p>
     * Clears all comments associated with this <code>Item</code>.
     * </p>
     */
    public void clearComments() {
        comments.clear();
    }

    /**
     * <p>
     * Gets the <code>Comment</code> associated with this <code>Item</code> with the given index.
     * </p>
     *
     * @param commentIndex
     *            The index of the comment to retrieve
     * @return The comment in the commentIndex position
     * @throws IndexOutOfBoundsException
     *             If <code>commentIndex</code> is negative or greater-equal than the length of the comments list
     */
    public Comment getComment(int commentIndex) {
        return (Comment) comments.get(commentIndex);
    }
    
	/**
     * <p>
     * Gets all the comments associated with this <code>Item</code>.
     * </p>
     *
     * @return All the comments associated with this item, it may be a 0-length array
     */
	public List getComments() {
		return comments;
	}

	/**
     * <p>
     * Gets all the comments associated with this <code>Item</code>.
     * </p>
     *
     * @return All the comments associated with this item, it may be a 0-length array
     */
    public Comment[] getAllComments() {
        return (Comment[]) comments.toArray(new Comment[comments.size()]);
    }

    /**
     * <p>
     * Gets the number of comments associated with this <code>Item</code>.
     * </p>
     *
     * @return The number of comments associated with this <code>Item</code>
     */
    public int getNumberOfComments() {
        return comments.size();
    }
}
