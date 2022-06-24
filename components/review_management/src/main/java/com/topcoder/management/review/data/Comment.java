/*
 * Copyright (C) 2006-2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.data;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * The <code>Comment</code> class can be considered the third level in the review model hierarchy. Comments can be
 * associated to both reviews and items. There is no limit on the number of comments that can be associated to a review
 * or item. Since a review or item is not required to have any associated comments, the <code>Comment</code> class can
 * be seen as (slightly) auxiliary to the main review and item part of the model. As in the <code>Review</code> and
 * <code>Item</code> classes, each data field has a three method (get/set/reset) combination for manipulating that
 * data field.
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
 * <p>
 * Thread-safe: This class is highly mutable, all fields can be changed, it is not thread safe.
 * </p>
 *
 * <p>
 * Changes in v1.1 (Cockpit Spec Review Backend Service Update v1.0):
 * - removed extraInfo from propOrder
 * - added @XmlTransient to extraInfo
 * </p>
 *
 * @author aubergineanode, vividmxx, pulky
 * @version 1.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "comment", propOrder = {"author", "comment", "commentType", "id"})
public class Comment implements Serializable {

    /**
     * <p>
     * The identifier of the <code>Comment</code>.
     * </p>
     * <p>
     * The default (unassigned value) for this field is -1, which indicates that the id of the <code>Comment</code>
     * has not yet been set through the constructor or a call to <code>setId</code> (or has been set back to this
     * value by a call to <code>resetId</code>). Positive values indicate that the client using the component has
     * assigned an id (in the constructor or through the <code>setId</code> method). It is expected that all
     * <code>Comment</code> instances will have unique ids. This is up to the client using the component to enforce,
     * this component will not enforce this condition.
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
     * The identifier of the author of the <code>Comment</code>.
     * </p>
     * <p>
     * The author is only an identifier, this component does not define an abstract <code>Author</code> class, and it
     * is up to the application to determine how the author identifier is used.
     * </p>
     * <p>
     * The default (unassigned value) for this field is -1, which indicates that the author of the <code>Comment</code>
     * has not yet been set through the constructor or a call to <code>setAuthor</code> (or has been set back to this
     * value by a call to <code>resetAuthor</code>). Positive values indicate that the client using the component has
     * assigned an author (in the constructor or through the <code>setAuthor</code> method).
     * </p>
     * <p>
     * This field is initialized to -1 (default value), and also can be initialized in the constructor. It is mutable,
     * and can be positive or equal to -1 (default value). This field can be set using the <code>setAuthor</code> and
     * <code>resetAuthor</code> methods and retrieved using the <code>getAuthor</code> method.
     * </p>
     */
    private long author = -1;

    /**
     * <p>
     * The general type that this <code>Comment</code> belongs to.
     * </p>
     * <p>
     * The default (unassigned value) for this field is null, which indicates that the type of the <code>Comment</code>
     * has not yet been set through the constructor or a call to <code>setCommentType</code> (or has been set back to
     * this value by a call to <code>resetCommentType</code>). Non-null values indicate that the client using the
     * component has assigned a type (in the constructor or through the <code>setCommentType</code> method).
     * </p>
     * <p>
     * This field is initialized to null (default value), and also can be initialized in the constructor. It is mutable,
     * and can be any <code>CommentType</code> instance or null (default value). This field can be set using the
     * <code>setCommentType</code> and <code>resetCommentType</code> methods and retrieved using the
     * <code>getCommentType</code> method.
     * </p>
     */
    private CommentType commentType = null;

    /**
     * <p>
     * Any extra information that is desired to be associated with this <code>Comment</code>.
     * </p>
     * <p>
     * This field is intended to allow as generic a type as possible, and a <code>Serializable</code> enables this
     * class to guarantee that serialization will succeed.
     * </p>
     * <p>
     * The default (unassigned value) for this field is null, which indicates that the extra information of the
     * <code>Comment</code> has not yet been set through a call to <code>setExtraInfo</code> (or has been set back
     * to this value by a call to <code>resetExtraInfo</code>). Non-null values indicate that the client using the
     * component has assigned an extra information (through the <code>setExtraInfo</code> method).
     * </p>
     * <p>
     * This field is initialized to null (default value). It is mutable, and can be any <code>Serializable</code>
     * instance or null (default value). This field can be set using the <code>setExtraInfo</code> and
     * <code>resetExtraInfo</code> methods and retrieved using the <code>getExtraInfo</code> method.
     * </p>
     */
    @XmlTransient
    private Serializable extraInfo = null;

    /**
     * <p>
     * The actual text of the <code>Comment</code>.
     * </p>
     * <p>
     * The default (unassigned value) for this field is null, which indicates that the actual text of the
     * <code>Comment</code> has not yet been set through the constructor or a call to <code>setComment</code> (or
     * has been set back to this value by a call to <code>resetComment</code>). Non-null values indicate that the
     * client using the component has assigned the actual text (in the constructor or through the
     * <code>setComment</code> method).
     * </p>
     * <p>
     * This field is initialized to null (default value), and also can be initialized in the constructor. It is mutable,
     * and can be any string (empty string, all whitespace, etc) or null (default value). This field can be set using
     * the <code>setComment</code> and <code>resetComment</code> methods and retrieved using the
     * <code>getComment</code> method.
     * </p>
     */
    private String comment = null;

    /**
     * <p>
     * Creates a new <code>Comment</code> instance, leaving all fields as their default unassigned values (-1 for id
     * and author, null for commentType, comment and extraInfo).
     * </p>
     */
    public Comment() {
        // nothing needed
    }

    /**
     * <p>
     * Creates a new <code>Comment</code> instance, setting id to the given value and leaving all other fields as
     * their default unassigned values (-1 for author, null for commentType, comment and extraInfo).
     * </p>
     *
     * @param id
     *            The identifier for the <code>Comment</code>
     * @throws IllegalArgumentException
     *             if <code>id</code> is not positive
     */
    public Comment(long id) {
        // just delegate to method setId
        setId(id);
    }

    /**
     * <p>
     * Creates a new <code>Comment</code> instance, setting id and author to the given values and leaving all other
     * fields as their default unassigned values (null for commentType, comment and extraInfo).
     * </p>
     *
     * @param id
     *            The identifier for the <code>Comment</code>
     * @param author
     *            The identifier of the author of the <code>Comment</code>
     * @throws IllegalArgumentException
     *             if <code>id</code> or <code>author</code> is not positive
     */
    public Comment(long id, long author) {
        this(id);
        // delegate to method setAuthor
        setAuthor(author);
    }

    /**
     * <p>
     * Creates a new <code>Comment</code> instance, setting id, author and comment to the given values and leaving all
     * other fields as their default unassigned values (null for commentType and extraInfo).
     * </p>
     *
     * @param id
     *            The identifier for the <code>Comment</code>
     * @param author
     *            The identifier of the author of the <code>Comment</code>
     * @param comment
     *            The actual text of the <code>Comment</code>
     * @throws IllegalArgumentException
     *             if <code>id</code> or <code>author</code> is not positive, or <code>comment</code> is null
     */
    public Comment(long id, long author, String comment) {
        this(id, author);
        // check comment
        if (comment == null) {
            throw new IllegalArgumentException("comment should not be null.");
        }
        this.comment = comment;
    }

    /**
     * <p>
     * Creates a new <code>Comment</code> instance, setting id, author and commentType to the given values and leaving
     * all other fields as their default unassigned values (null for comment and extraInfo).
     * </p>
     *
     * @param id
     *            The identifier for the <code>Comment</code>
     * @param author
     *            The identifier of the author of the <code>Comment</code>
     * @param commentType
     *            The general type that this <code>Comment</code> belongs to
     * @throws IllegalArgumentException
     *             if <code>id</code> or <code>author</code> is not positive, or <code>commentType</code> is null
     */
    public Comment(long id, long author, CommentType commentType) {
        this(id, author);
        // check commentType
        if (commentType == null) {
            throw new IllegalArgumentException("commentType should not be null.");
        }
        this.commentType = commentType;
    }

    /**
     * <p>
     * Creates a new <code>Comment</code> instance, setting id, author, commentType and comment to the given values
     * and leaving extraInfo as its default unassigned values (null).
     * </p>
     *
     * @param id
     *            The identifier for the <code>Comment</code>
     * @param author
     *            The identifier of the author of the <code>Comment</code>
     * @param commentType
     *            The general type that this <code>Comment</code> belongs to
     * @param comment
     *            The actual text of the <code>Comment</code>
     * @throws IllegalArgumentException
     *             if <code>id</code> or <code>author</code> is not positive, or <code>commentType</code> or
     *             <code>comment</code> is null
     */
    public Comment(long id, long author, CommentType commentType, String comment) {
        this(id, author, commentType);
        // check comment
        if (comment == null) {
            throw new IllegalArgumentException("comment should not be null.");
        }
        this.comment = comment;
    }

    /**
     * <p>
     * Sets the unique identifier of the <code>Comment</code>.
     * </p>
     * <p>
     * This method does not allowing setting of the id to the unassigned value (use the <code>resetId</code> method),
     * but it does allow the id to be changed if it has already been set.
     * </p>
     *
     * @param id
     *            The identifier to assign to the <code>Comment</code>
     * @throws IllegalArgumentException
     *             if <code>id</code> is not positive
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * <p>
     * Retrieves the unique identifier of the <code>Comment</code>.
     * </p>
     *
     * @return The unique identifier of the <code>Comment</code>
     */
    public long getId() {
        return id;
    }

    /**
     * <p>
     * Resets the unique identifier of the <code>Comment</code> to its undefined value, which is -1.
     * </p>
     */
    public void resetId() {
        id = -1;
    }

    /**
     * <p>
     * Sets the identifier of the author of the <code>Comment</code>.
     * </p>
     * <p>
     * The <code>author</code> is only an identifier, this component does not define an abstract <code>Author</code>
     * class, and it is up to the application to determine how the author identifier is used.
     * </p>
     * <p>
     * This method does not allowing setting of the author to the unassigned value (use the <code>resetAuthor</code>
     * method), but it does allow the author to be changed if it has already been set.
     * </p>
     *
     * @param author
     *            The identifier of the author to assign to the <code>Comment</code>
     * @throws IllegalArgumentException
     *             if <code>author</code> is not positive
     */
    public void setAuthor(long author) {
        this.author = author;
    }

    /**
     * <p>
     * Retrieves the identifier of the author of the <code>Comment</code>.
     * </p>
     * <p>
     * The returned value is only an identifier, this component does not define an abstract <code>Author</code> class,
     * and it is up to the application to determine how the author identifier is used.
     * </p>
     * <p>
     * This method may return -1, indicating that the comment is not associated with an author.
     * </p>
     *
     * @return The identifier of the author of the <code>Comment</code>
     */
    public long getAuthor() {
        return author;
    }

    /**
     * <p>
     * Resets the author identifier of the <code>Comment</code> to its undefined value, which is -1.
     * </p>
     */
    public void resetAuthor() {
        author = -1;
    }

    /**
     * <p>
     * Sets the type of the <code>Comment</code>.
     * </p>
     * <p>
     * The <code>commentType</code> argument may be null.
     * </p>
     *
     * @param commentType
     *            The type of the <code>Comment</code>
     */
    public void setCommentType(CommentType commentType) {
        this.commentType = commentType;
    }

    /**
     * <p>
     * Retrieves the type of the <code>Comment</code>.
     * </p>
     * <p>
     * This method may return null, indicating that the comment is not associated with a type.
     * </p>
     *
     * @return The type of the <code>Comment</code>
     */
    public CommentType getCommentType() {
        return commentType;
    }

    /**
     * <p>
     * Resets the type of the <code>Comment</code> to its unassigned value, which is null.
     * </p>
     */
    public void resetCommentType() {
        commentType = null;
    }

    /**
     * <p>
     * Sets the actual text content of the <code>Comment</code>.
     * </p>
     * <p>
     * The <code>comment</code> argument may be null, indicating that there should be no text associated with the
     * comment. Any non-null value is allowed (can be the empty string, all whitespace, etc).
     * </p>
     *
     * @param comment
     *            The actual text content of the <code>Comment</code>
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * <p>
     * Retrieves the actual text of the <code>Comment</code>.
     * </p>
     * <p>
     * This method may return null, indicating that no actual text is associated with the comment.
     * </p>
     *
     * @return The actual text of the <code>Comment</code>
     */
    public String getComment() {
        return comment;
    }

    /**
     * <p>
     * Resets the actual text of the <code>Comment</code> to its unassigned value, which is null.
     * </p>
     */
    public void resetComment() {
        comment = null;
    }

    /**
     * <p>
     * Allows users to set any extra information needed for the <code>Comment</code>.
     * </p>
     * <p>
     * The <code>extraInfo</code> argument may be null, indicating that no extra information is associated with the
     * comment.
     * </p>
     *
     * @param extraInfo
     *            The extra information to associate with this <code>Comment</code>
     */
    public void setExtraInfo(Serializable extraInfo) {
        this.extraInfo = extraInfo;
    }

    /**
     * <p>
     * Retrieves the extra information associated with the <code>Comment</code>.
     * </p>
     * <p>
     * This method may return null, indicating that no extra information has been associated with the comment.
     * </p>
     *
     * @return The user specified extra information associated with the <code>Comment</code>.
     */
    public Serializable getExtraInfo() {
        return extraInfo;
    }

    /**
     * <p>
     * Resets the extra information associated with the <code>Comment</code> to its unassigned value, which is null.
     * </p>
     */
    public void resetExtraInfo() {
        extraInfo = null;
    }
}
