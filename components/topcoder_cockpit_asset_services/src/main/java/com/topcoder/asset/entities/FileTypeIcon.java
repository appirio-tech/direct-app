/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.entities;


/**
 * <p>
 * This class is a container for file type icon. It is a simple JavaBean (POJO) that provides getters and setters for
 * all private attributes and performs no argument validation in the setters.
 * </p>
 *
 * <p>
 * FileTypeIcon.fileTypeCategory may be like "COMPRESSED", "DOCUMENT", "IMAGE", etc. See the storyboard page
 * File_type_icon_set.png.
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong> This class is mutable and not thread safe.
 * </p>
 *
 * @author LOY, sparemax
 * @version 1.0
 */
public class FileTypeIcon {
    /**
     * The id of file type icon. Can be any value. Has getter and setter.
     */
    private long id;

    /**
     * The file type. Can be any value. Has getter and setter.
     */
    private String fileType;

    /**
     * The file type category. Can be any value. Has getter and setter.
     */
    private String fileTypeCategory;

    /**
     * The icon path. Can be any value. Has getter and setter.
     */
    private String iconPath;

    /**
     * Creates an instance of FileTypeIcon.
     */
    public FileTypeIcon() {
        // Empty
    }

    /**
     * Retrieves the id of file type icon.
     *
     * @return the id of file type icon.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id of file type icon.
     *
     * @param id
     *            the id of file type icon.
     */
    public void setId(long id) {
        this.id = id;
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
     * Retrieves the file type category.
     *
     * @return the file type category.
     */
    public String getFileTypeCategory() {
        return fileTypeCategory;
    }

    /**
     * Sets the file type category.
     *
     * @param fileTypeCategory
     *            the file type category.
     */
    public void setFileTypeCategory(String fileTypeCategory) {
        this.fileTypeCategory = fileTypeCategory;
    }

    /**
     * Retrieves the icon path.
     *
     * @return the icon path.
     */
    public String getIconPath() {
        return iconPath;
    }

    /**
     * Sets the icon path.
     *
     * @param iconPath
     *            the icon path.
     */
    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }
}
