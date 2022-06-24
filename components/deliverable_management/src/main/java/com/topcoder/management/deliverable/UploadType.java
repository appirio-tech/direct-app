/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

/**
 * <p>
 * The UploadType class is a support class in the modeling classes. It is used to tag an upload as
 * being of a certain type.
 * </p>
 * <p>
 * This class is mutable because its base class is mutable.
 * </p>
 *
 * @author aubergineanode, singlewood
 * @version 1.0.2
 */
public class UploadType extends NamedDeliverableStructure {
    /**
     * <p>
     * The serial version id.
     * </p>
     */
    private static final long serialVersionUID = -5972928139203193770L;

    /**
     * Creates a new UploadType.
     */
    public UploadType() {
        super();
    }

    /**
     * Creates a new UploadType.
     *
     * @param id The id of the upload type
     * @throws IllegalArgumentException If id is <= 0
     */
    public UploadType(long id) {
        super(id);
    }

    /**
     * Creates a new UploadType.
     *
     * @param id The id of the upload type
     * @param name The name of the upload type
     * @throws IllegalArgumentException If id is <= 0
     */
    public UploadType(long id, String name) {
        super(id, name);
    }
}
