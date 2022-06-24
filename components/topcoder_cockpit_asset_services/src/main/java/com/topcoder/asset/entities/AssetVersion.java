/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.entities;

import java.util.Date;

/**
 * <p>
 * This class is a container for a version of an assert. It is a simple JavaBean (POJO) that provides getters and
 * setters for all private attributes and performs no argument validation in the setters.
 * </p>
 *
 * <p>
 * Asset version details and asset version file content are managed separately, because file content may be large.
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong> This class is mutable and not thread safe.
 * </p>
 *
 * @author LOY, sparemax
 * @version 1.0
 */
public class AssetVersion {
    /**
     * The id. Can be any value. Has getter and setter.
     */
    private long id;

    /**
     * The version. Can be any value. Has getter and setter.
     */
    private String version;

    /**
     * The file name. Can be any value. Has getter and setter.
     */
    private String fileName;

    /**
     * The file type. Can be any value. Has getter and setter.
     */
    private String fileType;

    /**
     * The file size bytes. Can be any value. Has getter and setter.
     */
    private long fileSizeBytes;

    /**
     * The uploader. Can be any value. Has getter and setter.
     */
    private User uploader;

    /**
     * The upload time. Can be any value. Has getter and setter.
     */
    private Date uploadTime;

    /**
     * The description. Can be any value. Has getter and setter.
     */
    private String description;

    /**
     * The asset id. Can be any value. Has getter and setter.
     */
    private long assetId;

    /**
     * The file path. Can be any value. Has getter and setter.
     */
    private String filePath;

    /**
     * The preview image path. Can be any value. Has getter and setter.
     */
    private String previewImagePath;

    /**
     * Creates an instance of this class.
     */
    public AssetVersion() {
        // Empty
    }

    /**
     * Retrieves the id.
     *
     * @return the id.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the id.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Retrieves the version.
     *
     * @return the version.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the version.
     *
     * @param version
     *            the version.
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Retrieves the file name.
     *
     * @return the file name.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the file name.
     *
     * @param fileName
     *            the file name.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Retrieves the file type.
     *
     * @return the file type.
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * Sets the file type.
     *
     * @param fileType
     *            the file type.
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * Retrieves the file size bytes.
     *
     * @return the file size bytes.
     */
    public long getFileSizeBytes() {
        return fileSizeBytes;
    }

    /**
     * Sets the file size bytes.
     *
     * @param fileSizeBytes
     *            the file size bytes.
     */
    public void setFileSizeBytes(long fileSizeBytes) {
        this.fileSizeBytes = fileSizeBytes;
    }

    /**
     * Retrieves the uploader.
     *
     * @return the uploader.
     */
    public User getUploader() {
        return uploader;
    }

    /**
     * Sets the uploader.
     *
     * @param uploader
     *            the uploader.
     */
    public void setUploader(User uploader) {
        this.uploader = uploader;
    }

    /**
     * Retrieves the upload time.
     *
     * @return the upload time.
     */
    public Date getUploadTime() {
        return uploadTime;
    }

    /**
     * Sets the upload time.
     *
     * @param uploadTime
     *            the upload time.
     */
    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    /**
     * Retrieves the description.
     *
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description
     *            the description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the asset id.
     *
     * @return the asset id.
     */
    public long getAssetId() {
        return assetId;
    }

    /**
     * Sets the asset id.
     *
     * @param assetId
     *            the asset id.
     */
    public void setAssetId(long assetId) {
        this.assetId = assetId;
    }

    /**
     * Retrieves the file path.
     *
     * @return the file path.
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Sets the file path.
     *
     * @param filePath
     *            the file path.
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Retrieves the preview image path.
     *
     * @return the preview image path.
     */
    public String getPreviewImagePath() {
        return previewImagePath;
    }

    /**
     * Sets the preview image path.
     *
     * @param previewImagePath
     *            the preview image path.
     */
    public void setPreviewImagePath(String previewImagePath) {
        this.previewImagePath = previewImagePath;
    }
}
