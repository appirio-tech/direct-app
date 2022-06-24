/*
 * Copyright (C) 2006-2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * The <code>Review</code> class is the class at the root of the review model hierarchy. It represents an entire
 * review, which is composed primarily of a list of items. Comments can also be associated with a review, and each
 * review also possesses a few simple data fields. The <code>Review</code> class is simply a container for these basic
 * data fields (along with the comment and item lists). All data fields are mutable, and each data field has a three
 * method (get/set/reset) combination for that data field. Each of the comment and item lists is manipulated through
 * nine methods (two for adding, three for removing, one for clear, and three getters).
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
 * - removed final keyword for comments and items
 * - added setter for comments and items collections and made them typed.
 * </p>
 * 
 * <p>
 * Thread-safe: This class is highly mutable, all fields can be changed, it is not thread safe.
 * </p>
 *
 * @author aubergineanode, vividmxx, George1, pulky
 * @version 1.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "review", propOrder = {"author", "comments","committed", "creationTimestamp", "creationUser", "id", 
		"initialScore", "items", "modificationTimestamp", "modificationUser", "score", "scorecard", "submission"})
public class Review implements Serializable {

    /**
     * <p>
     * The identifier of the <code>Review</code>.
     * </p>
     * <p>
     * The default (unassigned value) for this field is -1, which indicates that the id of the <code>Review</code>
     * has not yet been set through the constructor or a call to <code>setId</code> (or has been set back to this
     * value by a call to <code>resetId</code>). Positive values indicate that the client using the component has
     * assigned an id (in the constructor or through the <code>setId</code> method). It is expected that all
     * <code>Review</code> instances will have unique ids. This is up to the client using the component to enforce,
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
     * The identifier of the author of the <code>Review</code>.
     * </p>
     * <p>
     * The author is only an identifier, this component does not define an abstract <code>Author</code> class, and it
     * is up to the application to determine how the author identifier is used.
     * </p>
     * <p>
     * The default (unassigned value) for this field is -1, which indicates that the author of the <code>Review</code>
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
     * The identifier of the submission of the <code>Review</code>.
     * </p>
     * <p>
     * The submission is only an identifier, this component does not define an abstract <code>Submission</code> class,
     * and it is up to the application to determine how the submission identifier is used.
     * </p>
     * <p>
     * The default (unassigned value) for this field is -1, which indicates that the submission of the
     * <code>Review</code> has not yet been set through the constructor or a call to <code>setSubmission</code> (or
     * has been set back to this value by a call to <code>resetSubmission</code>). Positive values indicate that the
     * client using the component has assigned a submission (in the constructor or through the
     * <code>setSubmission</code> method).
     * </p>
     * <p>
     * This field is initialized to -1 (default value), and also can be initialized in the constructor. It is mutable,
     * and can be positive or equal to -1 (default value). This field can be set using the <code>setSubmission</code>
     * and <code>resetSubmission</code> methods and retrieved using the <code>getSubmission</code> method.
     * </p>
     */
    private long submission = -1;

    /**
     * <p>
     * The identifier of the project phase this <code>Review</code> is associated with.
     * </p>
     * <p>
     * The projectPhase is only an identifier, this component does not define an abstract <code>ProjectPhase</code>
     * class, and it is up to the application to determine how the projectPhase identifier is used.
     * </p>
     * <p>
     * The default (unassigned value) for this field is -1, which indicates that the project phase of the
     * <code>Review</code> has not yet been set through the constructor or a call to <code>setProjectPhase</code> (or
     * has been set back to this value by a call to <code>resetProjectPhase</code>). Positive values indicate that the
     * client using the component has assigned a project phase (in the constructor or through the
     * <code>setProjectPhase</code> method).
     * </p>
     * <p>
     * This field is initialized to -1 (default value), and also can be initialized in the constructor. It is mutable,
     * and can be positive or equal to -1 (default value). This field can be set using the <code>setProjectPhase</code>
     * and <code>resetProjectPhase</code> methods and retrieved using the <code>getProjectPhase</code> method.
     * </p>
     */
    private long projectPhase = -1;

    /**
     * <p>
     * The identifier of the scorecard of the <code>Review</code>.
     * </p>
     * <p>
     * The scorecard is only an identifier, this component does not define an abstract <code>Scorecard</code> class,
     * and it is up to the application to determine how the scorecard identifier is used.
     * </p>
     * <p>
     * The default (unassigned value) for this field is -1, which indicates that the scorecard of the
     * <code>Review</code> has not yet been set through the constructor or a call to <code>setScorecard</code> (or
     * has been set back to this value by a call to <code>resetScorecard</code>). Positive values indicate that the
     * client using the component has assigned a scorecard (in the constructor or through the <code>setScorecard</code>
     * method).
     * </p>
     * <p>
     * This field is initialized to -1 (default value), and also can be initialized in the constructor. It is mutable,
     * and can be positive or equal to -1 (default value). This field can be set using the <code>setScorecard</code>
     * and <code>resetScorecard</code> methods and retrieved using the <code>getScorecard</code> method.
     * </p>
     */
    private long scorecard = -1;

    /**
     * <p>
     * The flag indicating whether this <code>Review</code> has been committed or not.
     * </p>
     * <p>
     * The default (unassigned value) for this field is false, which indicates that the review is uncommitted. True
     * indicates that the review has been committed. It is up to the application to determine what it means for a
     * <code>Review</code> to be committed.
     * </p>
     * <p>
     * This field is initialized to false (default value), and it is mutable. This field can be set using the
     * <code>setCommitted</code> and <code>resetCommitted</code> methods and retrieved using the
     * <code>isCommitted</code> method.
     * </p>
     */
    private boolean committed = false;

    /**
     * <p>
     * The score associated with the <code>Review</code>.
     * </p>
     * <p>
     * The default (unassigned value) for this field is null, which indicates that the score of the <code>Review</code>
     * has not yet been set through a call to <code>setScore</code> (or has been set back to this value by a call to
     * <code>resetScore</code>). Non-null values indicate that the client using the component has assigned the score
     * (through the <code>setScore</code> method).
     * </p>
     * <p>
     * This field is initialized to null (default value), it is mutable. It can be null (default value) or wrap a
     * non-negative value (and not NaN or POSITIVE_INFINITY). This field can be set using the <code>setScore</code>
     * and <code>resetScore</code> methods and retrieved using the <code>getScore</code> method.
     * </p>
     */
    private Float score = null;

    /**
     * <p>
     * Initial score associated with the <code>Review</code>. The initial score is the value
     * before any appeals changes the score.
     * </p>
     * <p>
     * The default (unassigned value) for this field is <code>null</code>, which indicates that
     * the initial score of the <code>Review</code> has not yet been set through a call to
     * <code>setInitialScore</code> (or has been set back to this value by a call to
     * <code>resetInitialScore</code>). Non-null values indicate that the client using the
     * component has assigned the initial score (through the <code>setInitialScore</code> method).
     * </p>
     * <p>
     * This field is initialized to null (default value), it is mutable. It can be <code>null</code>
     * (default value) or wrap a non-negative value (and not NaN or POSITIVE_INFINITY). This field
     * can be set using the <code>setInitialScore</code> and <code>resetInitialScore</code>
     * methods and retrieved using the <code>getInitialScore</code> method.
     * </p>
     */
    private Float initialScore = null;

    /**
     * <p>
     * The list of <code>Item</code> instances that are associated with this <code>Review</code>.
     * </p>
     * <p>
     * This would normally seem like the situation to use a set, but the requirements specify that the ordering of
     * comments is important, so ordering must be preserved. Hence the use of a list storage structure.
     * </p>
     * <p>
     * This field is default initialized to an empty list, and is immutable, although its contents may be changed (use
     * <code>addItem</code>, <code>removeItem</code>, <code>addItems</code>, <code>removeItems</code> and
     * <code>clearItems</code> methods). All items in the list are required to be non-null <code>Item</code>
     * instances, and no duplicates (by reference equality) are allowed. Items are retrieved through the
     * <code>getItem</code> and <code>getAllItems</code> methods.
     * </p>
     */
    private List<Item> items = new ArrayList<Item>();

    /**
     * <p>
     * The list of <code>Comment</code> instances that are associated with this <code>Review</code>.
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
    private List<Comment> comments = new ArrayList<Comment>();

    /**
     * <p>
     * The name of the user that was responsible for creating the <code>Review</code>.
     * </p>
     * <p>
     * The default (unassigned value) for this field is null, which indicates that the creation user of the
     * <code>Review</code> has not yet been set in the constructor or through a call to <code>setCreationUser</code>.
     * Non-null values indicate that the client using the component has assigned the creation user (through the
     * constructor or a call to <code>setCreationUser</code> method).
     * </p>
     * <p>
     * This field is initialized to null (default value), it is mutable. It can be null (default value) or any string
     * value (empty string, all whitespace, etc). This field can be set using the <code>setCreationUser</code> method
     * and retrieved using the <code>getCreationUser</code> method.
     * </p>
     */
    private String creationUser = null;

    /**
     * <p>
     * The datetime that the <code>Review</code> was created.
     * </p>
     * <p>
     * The default (unassigned value) for this field is null, which indicates that the creation datetime of the
     * <code>Review</code> has not yet been set in the constructor or through a call to
     * <code>setCreationTimestamp</code>. Non-null values indicate that the client using the component has assigned
     * the creation datetime (through the constructor or a call to <code>setCreationTimestamp</code> method).
     * </p>
     * <p>
     * This field is initialized to null (default value), it is mutable. Although most applications will probably not
     * change the creation time stamp once it is set, this class does allow this field to be changed. It can be null
     * (default value) or any <code>Date</code> instance. This field can be set using the
     * <code>setCreationTimestamp</code> method and retrieved using the <code>getCreationTimestamp</code> method.
     * </p>
     */
    private Date creationTimestamp = null;

    /**
     * <p>
     * The name of the user that was responsible for the last modification to the <code>Review</code>.
     * </p>
     * <p>
     * The default (unassigned value) for this field is null, which indicates that the modification user of the
     * <code>Review</code> has not yet been set through a call to <code>setModificationUser</code>. Non-null values
     * indicate that the client using the component has assigned the modification user (through a call to
     * <code>setModificationUser</code> method).
     * </p>
     * <p>
     * This field is initialized to null (default value), it is mutable. It can be null (default value) or any string
     * value (empty string, all whitespace, etc). This field can be set using the <code>setModificationUser</code>
     * method and retrieved using the <code>getModificationUser</code> method.
     * </p>
     */
    private String modificationUser = null;

    /**
     * <p>
     * The datetime that the <code>Review</code> was last modified.
     * </p>
     * <p>
     * The default (unassigned value) for this field is null, which indicates that the modification datetime of the
     * <code>Review</code> has not yet been set through a call to <code>setModificationTimestamp</code>. Non-null
     * values indicate that the client using the component has assigned the modification datetime (through a call to
     * <code>setModificationTimestamp</code> method).
     * </p>
     * <p>
     * This field is initialized to null (default value), it is mutable. It can be null (default value) or any
     * <code>Date</code> instance. This field can be set using the <code>setModificationTimestamp</code> method and
     * retrieved using the <code>getModificationTimestamp</code> method.
     * </p>
     */
    private Date modificationTimestamp = null;

    /**
     * <p>
     * Creates a new <code>Review</code> instance, leaving all fields as their default unassigned values (-1 for id,
     * author, submission and scorecard, null for score, creationUser, creationTimestamp, modificationUser and
     * modificationTimestamp).
     * </p>
     */
    public Review() {
        // your code here
    }

    /**
     * <p>
     * Creates a new <code>Review</code> instance, setting id to the given value and leaving all other fields as their
     * default unassigned values (-1 for author, submission and scorecard, null for score, creationUser,
     * creationTimestamp, modificationUser and modificationTimestamp).
     * </p>
     *
     * @param id
     *            The identifier for the <code>Review</code>
     * @throws IllegalArgumentException
     *             if <code>id</code> is not positive
     */
    public Review(long id) {
        // just delegate to method setId
        setId(id);
    }

    /**
     * <p>
     * Creates a new <code>Review</code> instance, setting id and creationUser to the given value, setting
     * creationTimestamp to the current datetime and leaving all other fields as their default unassigned values (-1
     * for author, submission and scorecard, null for score, modificationUser and modificationTimestamp).
     * </p>
     *
     * @param id
     *            The identifier for the <code>Review</code>
     * @param creationUser
     *            The name of the user that was responsible for creating the <code>Review</code>
     * @throws IllegalArgumentException
     *             if <code>id</code> is not positive or <code>creationUser</code> is null
     */
    public Review(long id, String creationUser) {
        this(id);
        // check creationUser
        if (creationUser == null) {
            throw new IllegalArgumentException("creationUser should not be null.");
        }
        this.creationUser = creationUser;
        this.creationTimestamp = new Date();
    }

    /**
     * <p>
     * Creates a new <code>Review</code> instance, setting id, author, submission and scorecard to the given value and
     * leaving all other fields as their default unassigned values (null for score, creationUser, creationTimestamp,
     * modificationUser and modificationTimestamp).
     * </p>
     *
     * @param id
     *            The identifier for the <code>Review</code>
     * @param author
     *            The identifier for the author of the <code>Review</code>
     * @param submission
     *            The identifier for the submission the <code>Review</code> refers to
     * @param scorecard
     *            The identifier for the scorecard the <code>Review</code> uses
     * @throws IllegalArgumentException
     *             if <code>id</code>, <code>author</code>, <code>submission</code> or <code>scorecard</code>
     *             is not positive
     */
    public Review(long id, long author, long submission, long scorecard) {
        this(id);
        setAuthor(author);
        setSubmission(submission);
        setScorecard(scorecard);
    }

    /**
     * <p>
     * Creates a new <code>Review</code> instance, setting id, author, submission, scorecard and creationUser to the
     * given value, setting creationTimestamp to the current datetime and leaving all other fields as their default
     * unassigned values (null for score, modificationUser and modificationTimestamp).
     * </p>
     *
     * @param id
     *            The identifier for the <code>Review</code>
     * @param creationUser
     *            The name of the user that was responsible for creating the <code>Review</code>
     * @param author
     *            The identifier for the author of the <code>Review</code>
     * @param submission
     *            The identifier for the submission the <code>Review</code> refers to
     * @param scorecard
     *            The identifier for the scorecard the <code>Review</code> uses
     * @throws IllegalArgumentException
     *             if <code>id</code>, <code>author</code>, <code>submission</code> or <code>scorecard</code>
     *             is not positive, or <code>creationUser</code> is null
     */
    public Review(long id, String creationUser, long author, long submission, long scorecard) {
        this(id, author, submission, scorecard);
        // check creationUser
        if (creationUser == null) {
            throw new IllegalArgumentException("creationUser should not be null.");
        }
        this.creationUser = creationUser;
        this.creationTimestamp = new Date();
    }

    /**
     * <p>
     * Sets the unique identifier of the <code>Review</code>.
     * </p>
     * <p>
     * This method does not allowing setting of the id to the unassigned value (use the <code>resetId</code> method),
     * but it does allow the id to be changed if it has already been set.
     * </p>
     *
     * @param id
     *            The identifier to assign to the <code>Review</code>
     * @throws IllegalArgumentException
     *             if <code>id</code> is not positive
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * <p>
     * Retrieves the unique identifier of the <code>Review</code>.
     * </p>
     *
     * @return The unique identifier of the <code>Review</code>
     */
    public long getId() {
        return id;
    }

    /**
     * <p>
     * Resets the unique identifier of the <code>Review</code> to its undefined value, which is -1.
     * </p>
     */
    public void resetId() {
        id = -1;
    }

    /**
     * <p>
     * Sets the author of this <code>Review</code>.
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
     *            The identifier of the author to assign to the <code>Review</code>
     * @throws IllegalArgumentException
     *             if <code>author</code> is not positive
     */
    public void setAuthor(long author) {
        this.author = author;
    }

    /**
     * <p>
     * Retrieves the author for this <code>Review</code>.
     * </p>
     * <p>
     * The returned value is only an identifier, this component does not define an abstract <code>Author</code> class,
     * and it is up to the application to determine how the author identifier is used.
     * </p>
     * <p>
     * This method may return -1, indicating that the review is not associated with an author.
     * </p>
     *
     * @return The identifier of the author of the <code>Review</code>
     */
    public long getAuthor() {
        return author;
    }

    /**
     * <p>
     * Resets the author identifier of the <code>Review</code> to its undefined value, which is -1.
     * </p>
     */
    public void resetAuthor() {
        author = -1;
    }

    /**
     * <p>
     * Sets the submission that this <code>Review</code> applies to.
     * </p>
     * <p>
     * The <code>submission</code> is only an identifier, this component does not define an abstract
     * <code>Submission</code> class, and it is up to the application to determine how the submission identifier is
     * used.
     * </p>
     * <p>
     * This method does not allowing setting of the id to the unassigned value (use the <code>resetSubmission</code>
     * method), but it does allow the id to be changed if it has already been set.
     * </p>
     *
     * @param submission
     *            The submission that this <code>Review</code> applies to
     * @throws IllegalArgumentException
     *             if <code>submission</code> is not positive
     */
    public void setSubmission(long submission) {
        this.submission = submission;
    }

    /**
     * <p>
     * Retrieves the submission that this <code>Review</code> applies to.
     * </p>
     * <p>
     * The returned value is only an identifier, this component does not define an abstract <code>Submission</code>
     * class, and it is up to the application to determine how the submission identifier is used.
     * </p>
     * <p>
     * This method may return -1, indicating that the review is not associated with a submission.
     * </p>
     *
     * @return The submission that this <code>Review</code> applies to
     */
    public long getSubmission() {
        return submission;
    }

    /**
     * <p>
     * Resets the submission identifier of the <code>Review</code> to its undefined value, which is -1.
     * </p>
     */
    public void resetSubmission() {
        submission = -1;
    }

    /**
     * <p>
     * Sets the projectPhase that this <code>Review</code> is associated with.
     * </p>
     * <p>
     * The <code>projectPhase</code> is only an identifier, this component does not define an abstract
     * <code>ProjectPhase</code> class, and it is up to the application to determine how the projectPhase identifier is
     * used.
     * </p>
     * <p>
     * This method does not allowing setting of the id to the unassigned value (use the <code>resetProjectPhase</code>
     * method), but it does allow the id to be changed if it has already been set.
     * </p>
     *
     * @param projectPhase
     *            The projectPhase that this <code>Review</code> is associated with.
     * @throws IllegalArgumentException
     *             if <code>projectPhase</code> is not positive
     */
    public void setProjectPhase(long projectPhase) {
        if (projectPhase <= 0) {
            throw new IllegalArgumentException("projectPhase should be positive.");
        }
        this.projectPhase = projectPhase;
    }

    /**
     * <p>
     * Retrieves the projectPhase that this <code>Review</code> is associated with.
     * </p>
     * <p>
     * The returned value is only an identifier, this component does not define an abstract <code>ProjectPhase</code>
     * class, and it is up to the application to determine how the projectPhase identifier is used.
     * </p>
     * <p>
     * This method may return -1, indicating that the review is not associated with a project phase.
     * </p>
     *
     * @return The projectPhase that this <code>Review</code> is associated with.
     */
    public long getProjectPhase() {
        return projectPhase;
    }

    /**
     * <p>
     * Resets the projectPhase identifier of the <code>Review</code> to its undefined value, which is -1.
     * </p>
     */
    public void resetProjectPhase() {
        projectPhase = -1;
    }

    /**
     * <p>
     * Sets the scorecard that this <code>Review</code> applies to.
     * </p>
     * <p>
     * The <code>scorecard</code> is only an identifier, this component does not define an abstract
     * <code>Scorecard</code> class, and it is up to the application to determine how the scorecard identifier is
     * used.
     * </p>
     * <p>
     * This method does not allowing setting of the id to the unassigned value (use the <code>resetScorecard</code>
     * method), but it does allow the id to be changed if it has already been set.
     * </p>
     *
     * @param scorecard
     *            The scorecard that this <code>Review</code> applies to
     * @throws IllegalArgumentException
     *             if <code>scorecard</code> is not positive
     */
    public void setScorecard(long scorecard) {
        this.scorecard = scorecard;
    }

    /**
     * <p>
     * Retrieves the scorecard identifier that this <code>Review</code> relates to.
     * </p>
     * <p>
     * The returned value is only an identifier, this component does not define an abstract <code>Scorecard</code>
     * class, and it is up to the application to determine how the scorecard identifier is used.
     * </p>
     * <p>
     * This method may return -1, indicating that the review is not associated with a scorecard.
     * </p>
     *
     * @return The scorecard identifier that this <code>Review</code> relates to
     */
    public long getScorecard() {
        return scorecard;
    }

    /**
     * <p>
     * Resets the scorecard identifier of the <code>Review</code> to its undefined value, which is -1.
     * </p>
     */
    public void resetScorecard() {
        scorecard = -1;
    }

    /**
     * <p>
     * Sets the flag which indicates whether the <code>Review</code> has or has not been committed. It is up to the
     * application to define what "committed" means.
     * </p>
     * <p>
     * This method can be used both to "commit" and "uncommit" the <code>Review</code>.
     * </p>
     *
     * @param committed
     *            Whether the <code>Review</code> is to be considered committed or not
     */
    public void setCommitted(boolean committed) {
        this.committed = committed;
    }

    /**
     * <p>
     * Retrieves the flag which indicates whether the <code>Review</code> has or has not been committed. It is up to
     * the application to define what "committed" means.
     * </p>
     *
     * @return True if the <code>Review</code> is committed, otherwise false
     */
    public boolean isCommitted() {
        return committed;
    }

    /**
     * <p>
     * Resets the review back to its non-committed state (false).
     * </p>
     */
    public void resetCommitted() {
        committed = false;
    }

    /**
     * <p>
     * Sets the score associated with this <code>Review</code>.
     * </p>
     * <p>
     * The <code>score</code> may be null, indicating that no score is associated with the review. If the value is
     * non-null, the wrapped value must be non-negative (and not NaN or POSITIVE_INFINITY).
     * </p>
     *
     * @param score
     *            The score to associate with the <code>Review</code>
     * @throws IllegalArgumentException
     *             If <code>score</code> wraps a negative value, NaN or POSITIVE_INFINITY
     */
    public void setScore(Float score) {
        this.score = score;
    }

    /**
     * <p>
     * Gets the score associated with this <code>Review</code>.
     * </p>
     * <p>
     * This method may return null, indicating that there is no score associated with the review. If the value is
     * non-null, the wrapped value will be non-negative (and not NaN or POSITIVE_INFINITY).
     * </p>
     *
     * @return The score associated with the <code>Review</code>
     */
    public Float getScore() {
        return score;
    }

    /**
     * <p>
     * Resets the score associated with the <code>Review</code> to its undefined value, which is null.
     * </p>
     */
    public void resetScore() {
        score = null;
    }

    /**
     * <p>
     * Sets the initial score associated with this <code>Review</code>.
     * </p>
     * <p>
     * The <code>initialScore</code> may be <code>null</code>, indicating that no initial score
     * is associated with the review. If the value is non-null, the wrapped value must be
     * non-negative (and not NaN or POSITIVE_INFINITY).
     * </p>
     *
     * @param initialScore
     *            The initial score to associate with the <code>Review</code>
     * @throws IllegalArgumentException
     *             If <code>initialScore</code> wraps a negative value, NaN or POSITIVE_INFINITY
     */
    public void setInitialScore(Float initialScore) {
        this.initialScore = initialScore;
    }

    /**
     * <p>
     * Gets the initial score associated with this <code>Review</code>.
     * </p>
     * <p>
     * This method may return <code>null</code>, indicating that there is no initial score
     * associated with the review. If the value is non-null, the wrapped value will be non-negative
     * (and not NaN or POSITIVE_INFINITY).
     * </p>
     *
     * @return The initial score associated with the <code>Review</code>
     */
    public Float getInitialScore() {
        return initialScore;
    }

    /**
     * <p>
     * Resets the initial score associated with the <code>Review</code> to its undefined value,
     * which is <code>null</code>.
     * </p>
     */
    public void resetInitialScore() {
        initialScore = null;
    }

    /**
     * <p>
     * Adds an <code>Item</code> to the items list of this <code>Review</code>.
     * </p>
     * <p>
     * This method will adhere to the no-duplicates restriction of the items list.
     * </p>
     *
     * @param item
     *            The item to add
     * @throws IllegalArgumentException
     *             If <code>item</code> is null
     */
    public void addItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item should not be null.");
        }
        if (!items.contains(item)) {
            items.add(item);
        }
    }

    /**
     * <p>
     * Adds all items in the array to the items list of this <code>Review</code>.
     * </p>
     * <p>
     * This method will adhere to the no-duplicates restriction of the items list.
     * </p>
     *
     * @param items
     *            The array of items to add to the items list of the <code>Review</code>, it may be a 0-length array
     * @throws IllegalArgumentException
     *             If <code>items</code> is null or has null entries
     */
    public void addItems(Item[] items) {
        if (items == null) {
            throw new IllegalArgumentException("items should not be null");
        }
        // checks the array for null entries before adding them to the items list
        // this will guarantee no item is added if the array has null entries
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                throw new IllegalArgumentException("items should not have null entries.");
            }
        }
        for (int i = 0; i < items.length; i++) {
            if (!this.items.contains(items[i])) {
                this.items.add(items[i]);
            }
        }
    }

    /**
     * <p>
     * Removes an <code>Item</code> from the items list of this <code>Review</code>.
     * </p>
     * <p>
     * If the given <code>Item</code> dose not exist in the items list of the <code>Review</code>, nothing is done.
     * </p>
     *
     * @param item
     *            The item to remove
     * @throws IllegalArgumentException
     *             If <code>item</code> is null
     */
    public void removeItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item should not be null.");
        }
        items.remove(item);
    }

    /**
     * <p>
     * Removes all items in the array from the items list of this <code>Review</code>.
     * </p>
     *
     * @param items
     *            The array of comments to remove from the items list of the<code>Review</code>, it may be a
     *            0-length array
     * @throws IllegalArgumentException
     *             If <code>items</code> is null or has null entries
     */
    public void removeItems(Item[] items) {
        if (items == null) {
            throw new IllegalArgumentException("items should not be null.");
        }
        // checks the array for null entries before removing them from the items list
        // this will guarantee no item is removed if the array has null entries
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                throw new IllegalArgumentException("items should not have null entries.");
            }
        }
        for (int i = 0; i < items.length; i++) {
            this.items.remove(items[i]);
        }
    }

    /**
     * <p>
     * Removes the first <code>Item</code> with the given id from the items list.
     * </p>
     * <p>
     * If no <code>Item</code> has the given id, then no <code>Item</code> will be removed.
     * </p>
     *
     * @param id
     *            The identifier of the <code>Item</code> to remove
     * @throws IllegalArgumentException
     *             If <code>id</code> is non-positive and not equals to -1
     */
    public void removeItem(long id) {
        if (id <= 0 && id != -1) {
            throw new IllegalArgumentException("id should be positive or -1.");
        }
        for (Iterator itr = items.iterator(); itr.hasNext();) {
            Item it = (Item) itr.next();
            if (it.getId() == id) {
                itr.remove();
                break;
            }
        }
    }

    /**
     * <p>
     * Clears all the items associated with the <code>Review</code>.
     * </p>
     */
    public void clearItems() {
        items.clear();
    }

    /**
     * <p>
     * Gets the <code>Item</code> associated with this <code>Review</code> with the given index.
     * </p>
     *
     * @param itemIndex
     *            The index of the item to retrieve
     * @return The item in the <code>itemIndex</code> position
     * @throws IndexOutOfBoundsException
     *             If <code>itemIndex</code> is negative or greater-equal than the length of the items list
     */
    public Item getItem(int itemIndex) {
        return (Item) items.get(itemIndex);
    }

	/**
     * <p>
     * Gets all the items associated with this <code>Review</code>.
     * </p>
     *
     * @return All the items associated with this review, it may be a 0-length array
     */
	public List<Item> getItems() {
		return items;
	}

	/**
     * <p>
     * Sets all the items associated with this <code>Review</code>.
     * </p>
     *
     * @param items all the items to set
     * 
     * @since 1.1
     */
	public void setItems(List<Item> items) {
		this.items = items;
	}

	/**
     * <p>
     * Gets all the items associated with this <code>Review</code>.
     * </p>
     *
     * @return All the items associated with this review, it may be a 0-length array
     */
    public Item[] getAllItems() {
        return (Item[]) items.toArray(new Item[items.size()]);
    }

    /**
     * <p>
     * Gets the number of items associated with this <code>Review</code>.
     * </p>
     *
     * @return The number of items associated with this <code>Review</code>
     */
    public int getNumberOfItems() {
        return items.size();
    }

    /**
     * <p>
     * Adds a <code>Comment</code> to the comments list of this <code>Review</code>.
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
            throw new IllegalArgumentException("comment should not be null.");
        }
        if (!comments.contains(comment)) {
            comments.add(comment);
        }
    }

    /**
     * <p>
     * Adds all comments in the array to the comments list of this <code>Review</code>.
     * </p>
     * <p>
     * This method will adhere to the no-duplicates restriction of the comments list.
     * </p>
     *
     * @param comments
     *            The array of comments to add to the comments list of the <code>Review</code>, it may be a 0-length
     *            array
     * @throws IllegalArgumentException
     *             If <code>comments</code> is null or has null entries
     */
    public void addComments(Comment[] comments) {
        if (comments == null) {
            throw new IllegalArgumentException("comments should not be null.");
        }
        // checks the array for null entries before adding them to the comments list
        // this will guarantee no comment is added if the array has null entries
        for (int i = 0; i < comments.length; i++) {
            if (comments[i] == null) {
                throw new IllegalArgumentException("comments should not have null entries.");
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
     * Removes a <code>Comment</code> from the comments list of this <code>Review</code>.
     * </p>
     * <p>
     * If the given <code>Comment</code> dose not exist in this <code>Review</code>, nothing is done.
     * </p>
     *
     * @param comment
     *            The comment to remove
     * @throws IllegalArgumentException
     *             If <code>comment</code> is null
     */
    public void removeComment(Comment comment) {
        if (comment == null) {
            throw new IllegalArgumentException("comment should not be null.");
        }
        comments.remove(comment);
    }

    /**
     * <p>
     * Removes all comments in the array from the comments list of this <code>Review</code>.
     * </p>
     *
     * @param comments
     *            The array of comments to remove from the comments list of the <code>Review</code>, it may be a
     *            0-length array
     * @throws IllegalArgumentException
     *             If <code>comments</code> is null or has null entries
     */
    public void removeComments(Comment[] comments) {
        if (comments == null) {
            throw new IllegalArgumentException("comments should not be null.");
        }
        // checks the array for null entries before removing them from the comments list
        // this will guarantee no comment is removed if the array has null entries
        for (int i = 0; i < comments.length; i++) {
            if (comments[i] == null) {
                throw new IllegalArgumentException("comments should not have null entries.");
            }
        }
        for (int i = 0; i < comments.length; i++) {
            this.comments.remove(comments[i]);
        }
    }

    /**
     * <p>
     * Removes the first <code>Comment</code> with the given id from the comments list.
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
            throw new IllegalArgumentException("id should be positive or -1.");
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
     * Clears all comments associated with this <code>Review</code>.
     * </p>
     */
    public void clearComments() {
        comments.clear();
    }

    /**
     * <p>
     * Gets the <code>Comment</code> associated with this <code>Review</code> with the given index.
     * </p>
     *
     * @param commentIndex
     *            The index of the comment to retrieve
     * @return The comment in the <code>commentIndex</code> position
     * @throws IndexOutOfBoundsException
     *             If <code>commentIndex</code> is negative or greater-equal than the length of the comments list
     */
    public Comment getComment(int commentIndex) {
        return (Comment) comments.get(commentIndex);
    }

	/**
     * <p>
     * Gets all the comments associated with this <code>Review</code>.
     * </p>
     *
     * @return All the comments associated with this item, it may be a 0-length array
     */
	public List<Comment> getComments() {
		return comments;
	}

    /**
     * <p>
     * Sets all the comments associated with this <code>Review</code>.
     * </p>
     *
     * @param comments all the comments to set
     * 
     * @since 1.1
     */
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	/**
     * <p>
     * Gets all the comments associated with this <code>Review</code>.
     * </p>
     *
     * @return All the comments associated with this item, it may be a 0-length array
     */
    public Comment[] getAllComments() {
        return (Comment[]) comments.toArray(new Comment[comments.size()]);
    }

    /**
     * <p>
     * Gets the number of comments associated with this <code>Review</code>.
     * </p>
     *
     * @return The number of comments associated with this <code>Review</code>
     */
    public int getNumberOfComments() {
        return comments.size();
    }

    /**
     * <p>
     * Sets the user that is responsible for the creation of the <code>Review</code>.
     * </p>
     *
     * @param creationUser
     *            The user responsible for <code>Review</code> creation, it can be null
     */
    public void setCreationUser(String creationUser) {
        this.creationUser = creationUser;
    }

    /**
     * <p>
     * Gets the user that is responsible for the creation of the <code>Review</code>.
     * </p>
     * <p>
     * The return may be null which indicates that the creation user has not been set.
     * </p>
     *
     * @return The user that is responsible for the creation of the <code>Review</code>
     */
    public String getCreationUser() {
        return creationUser;
    }

    /**
     * <p>
     * Sets the datetime at which the <code>Review</code> was created.
     * </p>
     *
     * @param creationTimestamp
     *            The datetime the <code>Review</code> was created, it can be null
     */
    public void setCreationTimestamp(Date creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    /**
     * <p>
     * Gets the datetime the <code>Review</code> was created.
     * </p>
     * <p>
     * The return may be null which indicates that the creation datetime has not been set.
     * </p>
     *
     * @return The datetime the <code>Review</code> was created
     */
    public Date getCreationTimestamp() {
        return creationTimestamp;
    }

    /**
     * <p>
     * Sets the user that is responsible for the last modification to the the <code>Review</code>.
     * </p>
     *
     * @param modificationUser
     *            The user responsible for the last <code>Review</code> modification, it can be null
     */
    public void setModificationUser(String modificationUser) {
        this.modificationUser = modificationUser;
    }

    /**
     * <p>
     * Gets the user that is responsible for the last modification to the the <code>Review</code>.
     * </p>
     * <p>
     * The return may be null which indicates that the modification user has not been set.
     * </p>
     *
     * @return The user that is responsible for the last modification to the the <code>Review</code>
     */
    public String getModificationUser() {
        return modificationUser;
    }

    /**
     * <p>
     * Sets the datetime at which the <code>Review</code> was last modified.
     * </p>
     *
     * @param modificationTimestamp
     *            The datetime the <code>Review</code> was last modified, it can be null
     */
    public void setModificationTimestamp(Date modificationTimestamp) {
        this.modificationTimestamp = modificationTimestamp;
    }

    /**
     * <p>
     * Gets the datetime at which the <code>Review</code> was last modified.
     * </p>
     * <p>
     * The return may be null which indicates that the modification datetime has not been set.
     * </p>
     *
     * @return The datetime the <code>Review</code> was last modified
     */
    public Date getModificationTimestamp() {
        return modificationTimestamp;
    }
}
