/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>This class represents a file to be uploaded.</p>
 * <p>Validation of parameters is not performed in this class. It's supposed to be a caller's responsibility.</p>
 *
 * <p>
 * Changes in v1.1 (Cockpit Upload Attachment):
 * - Added uploadedFileDesc and UploadedFileType attributes with corresponding getters and setters
 * - Refreshed serialVersionUID
 * </p>
 * <p><strong>Thread safety: </strong></p> <p>This class is mutable and not thread safe.</p>
 *
 * @author Margarita, pulky
 * @version 1.1
 * @since BUGR-1600
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "compUploadedFile", propOrder = { "fileData", "uploadedFileName", "uploadedFileDesc",
    "uploadedFileType"})
public class CompUploadedFile implements Serializable {

    /**
     * Serial version id
     */
    private static final long serialVersionUID = -7510052490892895232L;

    private byte[] fileData;

    private String uploadedFileName;

    /**
     * The uploaded file description
     *
     * @since 1.1
     */
    private String uploadedFileDesc;

    /**
     * The uploaded file type id
     *
     * @since 1.1
     */
    private Long uploadedFileType;

    public CompUploadedFile() {

    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public String getUploadedFileName() {
        return uploadedFileName;
    }

    public void setUploadedFileName(String uploadedFileName) {
        this.uploadedFileName = uploadedFileName;
    }

    /**
     * Sets the uploaded file description
     *
     * @param uploadedFileDesc the file description to set
     *
     * @since 1.1
     */
    public void setUploadedFileDesc(String uploadedFileDesc) {
        this.uploadedFileDesc = uploadedFileDesc;
    }

    /**
     * Gets the uploaded file description.
     *
     * @return the file description
     *
     * @since 1.1
     */
    public String getUploadedFileDesc() {
        return uploadedFileDesc;
    }

    /**
     * Sets the uploaded file type id
     *
     * @param uploadedFileType the file type id to set
     *
     * @since 1.1
     */
    public void setUploadedFileType(Long uploadedFileType) {
        this.uploadedFileType = uploadedFileType;
    }

    /**
     * Gets the uploaded file type id.
     *
     * @return the file type id
     *
     * @since 1.1
     */
    public Long getUploadedFileType() {
        return uploadedFileType;
    }
}
