/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

/**
 * <p>
 * The UploadStatus class is a support class in the modeling classes. It is used to tag an upload as
 * having a certain status.
 * </p>
 * <p>
 * This class is mutable because its base class is mutable.
 * </p>
 *
 * @author aubergineanode, singlewood
 * @version 1.0.2
 */
public class UploadStatus extends NamedDeliverableStructure {
    /**
     * <p>
     * The serial version id.
     * </p>
     */
    private static final long serialVersionUID = 5221988704031646468L;

    /**
     * Creates a new UploadStatus.
     */
    public UploadStatus() {
        super();
    }

    /**
     * Creates a new UploadStatus.
     *
     * @param id The id of the upload status
     * @throws IllegalArgumentException If id is <= 0
     */
    public UploadStatus(long id) {
        super(id);
    }

    /**
     * Creates a new UploadStatus.
     *
     * @param id The id of the upload status
     * @param name The name of the upload status
     * @throws IllegalArgumentException If id is <= 0
     */
    public UploadStatus(long id, String name) {
        super(id, name);
    }
}
