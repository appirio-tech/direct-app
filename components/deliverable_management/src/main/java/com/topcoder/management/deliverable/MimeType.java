/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

import com.topcoder.management.project.FileType;

/**
 * <p>
 * This class represents a MIME type. It is a simple JavaBean that provides getters and setters for all private
 * attributes.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.2
 * @since 1.2
 */
public class MimeType extends IdentifiableDeliverableStructure {

    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = -4523244722546608111L;

    /**
     * Represents the description of the MIME type.
     */
    private String description;

    /**
     * Represents the file type that corresponds to this MIME type.
     */
    private FileType fileType;

    /**
     * Creates an instance of MimeType.
     */
    public MimeType() {
    }

    /**
     * Creates an instance of MimeType with the given ID.
     *
     * @param id
     *            the id of the MIME type
     * @throws IllegalArgumentException
     *             If id is <= 0
     */
    public MimeType(long id) {
        super(id);
    }

    /**
     * Retrieves the description of the MIME type.
     *
     * @return the description of the MIME type.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the MIME type.
     *
     * @param description the description of the MIME type.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the file type that corresponds to this MIME type.
     *
     * @return the file type that corresponds to this MIME type.
     */
    public FileType getFileType() {
        return fileType;
    }

    /**
     * Sets the file type that corresponds to this MIME type.
     * @param fileType the file type that corresponds to this MIME type.
     */
    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

}
