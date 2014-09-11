/*
 * Copyright (C) 2006-2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.data;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * The <code>CommentType</code> class supports the <code>Comment</code> class, and allows a comment to be tagged as
 * being of a certain type or style. Unlike other classes in this component, which are likely to be created dynamically
 * and frequently, only a few <code>CommentType</code> instances are likely to be used in any application. For this
 * component, this consideration really has no impact on the design or development of this class. Like other classes in
 * this component, it consists of simple data fields, each of which has a three method (get/set/reset) combination for
 * manipulating that field.
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
 * Version 1.1 (Module Assembly - TC Cockpit - Studio - Final Fixes Integration Part One Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #COMMENT_TYPE_APPROVAL_REVIEW} constant.</li>
 *     <li>Added {@link #COMMENT_TYPE_FINAL_REVIEW} constant.</li>
 *     <li>Added {@link #COMMENT_TYPE_REQUIRED} constant.</li>
 *   </ol>
 * </p>
 * <p>
 * Thread-safe: This class is highly mutable, all fields can be changed, it is not thread safe.
 * </p>
 *
 * @author aubergineanode, vividmxx, isv
 * @version 1.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "commentType", propOrder = {"id", "name"})
public class CommentType implements Serializable {


    public static final CommentType COMMENT_TYPE_COMMENT = new CommentType(1, "Comment");

    /**
     * <p>A <code>CommentType</code> referencing the <code>Approval Review Comment</code> comment type.</p>
     * 
     * @since 1.1
     */
    public static final CommentType COMMENT_TYPE_APPROVAL_REVIEW = new CommentType(12, "Approval Review Comment");

    /**
     * <p>A <code>CommentType</code> referencing the <code>Final Review Comment</code> comment type.</p>
     *
     * @since 1.1
     */
    public static final CommentType COMMENT_TYPE_FINAL_REVIEW = new CommentType(10, "Final Review Comment");

    /**
     * <p>A <code>CommentType</code> referencing the <code>Required</code> comment type.</p>
     *
     * @since 1.1
     */
    public static final CommentType COMMENT_TYPE_REQUIRED = new CommentType(3, "Required");


    /**
     * <p>
     * The identifier of the <code>CommentType</code>.
     * </p>
     * <p>
     * The default (unassigned value) for this field is -1, which indicates that the id of the <code>CommentType</code>
     * has not yet been set through the constructor or a call to <code>setId</code> (or has been set back to this
     * value by a call to <code>resetId</code>). Positive values indicate that the client using the component has
     * assigned an id (in the constructor or through the <code>setId</code> method). It is expected that all
     * <code>CommentType</code> instances will have unique ids. This is up to the client using the component to
     * enforce, this component will not enforce this condition.
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
     * The name for the <code>CommentType</code>.
     * </p>
     * <p>
     * The default (unassigned value) for this field is null, which indicates that the name of the
     * <code>CommentType</code> has not yet been set through the constructor or a call to <code>setName</code> (or
     * has been set back to this value by a call to <code>resetName</code>). Non-null values indicate that the client
     * using the component has assigned a name (in the constructor or through the <code>setName</code> method).
     * </p>
     * <p>
     * This field is default initialized to null (default value), and also can be initialized in the constructor. It is
     * mutable, and can any <code>String</code> instance (empty string, all whitespace, etc) or null (default value).
     * This field can be set using the <code>setName</code> and <code>resetName</code> methods and retrieved using
     * the <code>getName</code> method.
     * </p>
     */
    private String name = null;



    /**
     * <p>
     * Creates a new <code>CommentType</code> instance, leaving all fields as their default unassigned values (-1 for
     * id, null for name).
     * </p>
     */
    public CommentType() {
        // nothing needed
    }

    /**
     * <p>
     * Creates a new <code>CommentType</code> instance, setting id to the given value and leaving name as its default
     * unassigned value (null).
     * </p>
     *
     * @param id
     *            The identifier for the <code>CommentType</code>
     * @throws IllegalArgumentException
     *             if <code>id</code> is not positive
     */
    public CommentType(long id) {
        // just delegate to method setId
        setId(id);
    }

    /**
     * <p>
     * Creates a new <code>CommentType</code> instance, setting id and name to the given values.
     * </p>
     *
     * @param id
     *            The identifier for the <code>CommentType</code>
     * @param name
     *            The name for the <code>CommentType</code>
     * @throws IllegalArgumentException
     *             if <code>id</code> is not positive, or <code>name</code> is null
     */
    public CommentType(long id, String name) {
        this(id);
        // check name
        if (name == null) {
            throw new IllegalArgumentException("name should not be null.");
        }
        this.name = name;
    }

    /**
     * <p>
     * Sets the unique identifier of the <code>CommentType</code>.
     * </p>
     * <p>
     * This method does not allowing setting of the id to the unassigned value (use the <code>resetId</code> method),
     * but it does allow the id to be changed if it has already been set.
     * </p>
     *
     * @param id
     *            The identifier to assign to the <code>CommentType</code>
     * @throws IllegalArgumentException
     *             if <code>id</code> is not positive
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * <p>
     * Retrieves the unique identifier of the <code>CommentType</code>.
     * </p>
     *
     * @return The unique identifier of the <code>CommentType</code>
     */
    public long getId() {
        return id;
    }

    /**
     * <p>
     * Resets the unique identifier of the <code>CommentType</code> back to its undefined value, which is -1.
     * </p>
     */
    public void resetId() {
        id = -1;
    }

    /**
     * <p>
     * Sets the name of the <code>CommentType</code>.
     * </p>
     * <p>
     * The <code>name</code> argument is permitted to be null or any other string (empty string, all whitespace, etc).
     * </p>
     *
     * @param name
     *            The name to assign to the <code>CommentType</code>
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>
     * Retrieves the name of the <code>CommentType</code>.
     * </p>
     * <p>
     * This method may return null, indicating that no name is associated with the <code>CommentType</code>.
     * </p>
     *
     * @return The name of the <code>CommentType</code>
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Sets the name of the <code>CommentType</code> back to its undefined value, which is null.
     * </p>
     */
    public void resetName() {
        name = null;
    }
}
