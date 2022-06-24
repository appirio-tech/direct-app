/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

/**
 * <p>
 * The SubmissionStatus class is a support class in the modeling classes. It is used to tag a
 * submission as having a certain status.
 * </p>
 * <p>
 * This class is mutable because its base class is mutable.
 * </p>
 *
 * @author aubergineanode, singlewood
 * @version 1.0.2
 */
public class SubmissionStatus extends NamedDeliverableStructure {
    /**
     * <p>
     * The serial version id.
     * </p>
     */
    private static final long serialVersionUID = 6108593621423946848L;

    /**
     * Creates a new SubmissionStatus.
     */
    public SubmissionStatus() {
        super();
    }

    /**
     * Creates a new SubmissionStatus.
     *
     * @param id The id of the submission status
     * @throws IllegalArgumentException If id is <= 0
     */
    public SubmissionStatus(long id) {
        super(id);
    }

    /**
     * Creates a new SubmissionStatus.
     *
     * @param id The id of the submission status
     * @param name The name of the submission status
     * @throws IllegalArgumentException If id is <= 0
     */
    public SubmissionStatus(long id, String name) {
        super(id, name);
    }
}
