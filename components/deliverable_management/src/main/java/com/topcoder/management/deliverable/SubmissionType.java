/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

/**
 * <p>
 * The SubmissionType class is a support class in the modeling classes. It is used to tag a submission as having a
 * certain type.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable because its base class is mutable.
 * </p>
 *
 * @author saarixx, sparemax
 * @version 1.1
 * @since 1.1
 */
public class SubmissionType extends NamedDeliverableStructure {
    /**
     * <p>
     * The serial version id.
     * </p>
     */
    private static final long serialVersionUID = 3469864628029176554L;

    /**
     * <p>
     * Constructs a new instance of <code>SubmissionType</code>.
     * </p>
     */
    public SubmissionType() {
        // Empty
    }

    /**
     * <p>
     * Constructs a new instance of <code>SubmissionType</code> with id of the submission type.
     * </p>
     *
     * @param id
     *            the id of the submission type.
     *
     * @throws IllegalArgumentException
     *             if id is not positive.
     */
    public SubmissionType(long id) {
        super(id);
    }

    /**
     * <p>
     * Constructs a new instance of <code>SubmissionType</code> with id and name of the submission type.
     * </p>
     *
     * @param id
     *            the id of the submission type
     * @param name
     *            the name of the submission type
     *
     * @throws IllegalArgumentException
     *             if id is not positive.
     */
    public SubmissionType(long id, String name) {
        super(id, name);
    }
}
