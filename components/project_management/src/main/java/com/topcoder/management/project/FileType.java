/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

/**
 * <p>
 * This is a FileType entity which represents the project file types. It extends from AuditableObject class. Added in
 * version 1.2.
 * </p>
 * <p>
 * <strong>Thread-Safety:</strong> This class is mutable and not thread safe. But it will be used as entity so it'll not
 * cause any thread safe problem.
 * </p>
 *
 * @author flytoj2ee, TCSDEVELOPER
 * @version 1.2
 * @since 1.2
 */
@SuppressWarnings("serial")
public class FileType extends AuditableObject {

    public static final FileType PSD = new FileType(17, "PSD - Photoshop", 17, false, "psd", false);

    public static final FileType AI = new FileType(18, "AI - Illustrator", 18, false, "ai", false);

    public static final FileType EPS = new FileType(19, "EPS - Encapsulated PostScript", 19, false, "eps", false);

    public static final FileType INDD = new FileType(24, "INDD - InDesign", 24, false, "indd", false);

    public static final FileType ICO = new FileType(26, "Icon", 26, false, "ico", false);

    public static final FileType PDF = new FileType(3, "PDF - Adobe Acrobat", 3, false, "pdf", false);

    public static final FileType PPT = new FileType(25, "MS PowerPoint", 25, false, "ppt", false);


    /**
     * Represents the description of FileType. The default value is null. It's changeable. It could not set to null or
     * empty. It's accessed in setter and getter.
     */
    private String description;

    /**
     * Represents the sort of FileType. The default value is 0. It's changeable. It could be any value. It's accessed in
     * setter and getter.
     */
    private int sort;

    /**
     * Represents the imageFile of FileType. The default value is false. It's changeable. It could be any value. It's
     * accessed in setter and getter.
     */
    private boolean imageFile;

    /**
     * Represents the extension of FileType. The default value is null. It's changeable. It could not set to null or
     * empty. It's accessed in setter and getter.
     */
    private String extension;

    /**
     * Represents the bundledFile of FileType. The default value is false. It's changeable. It could be any value. It's
     * accessed in setter and getter.
     */
    private boolean bundledFile;

    /**
     * Represents the id of FileType. The default value is 0. It's changeable. It should be positive. It's accessed in
     * setter and getter. It could not set to non-positive value.
     */
    private long id;

    /**
     * Empty constructor.
     */
    public FileType() {
    }

    /**
     * constructor.
     */
    public FileType(long id, String description, int sort, boolean imageFile, String extension, boolean bundledFile) {
        setId(id);
        setDescription(description);
        setSort(sort);
        setImageFile(imageFile);
        setExtension(extension);
        setBundledFile(bundledFile);
    }

    /**
     * Returns the value of description attribute.
     *
     * @return the value of description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the given value to description attribute.
     *
     * @param description
     *            the given value to set.
     * @throws IllegalArgumentException
     *             if given parameter is null or empty.
     */
    public void setDescription(String description) {
        Helper.checkStringNotNullOrEmpty(description, "description");
        this.description = description;
    }

    /**
     * Returns the value of sort attribute.
     *
     * @return the value of sort.
     */
    public int getSort() {
        return this.sort;
    }

    /**
     * Sets the given value to sort attribute.
     *
     * @param sort
     *            the given value to set.
     */
    public void setSort(int sort) {
        this.sort = sort;
    }

    /**
     * Returns the value of imageFile attribute.
     *
     * @return the value of imageFile.
     */
    public boolean isImageFile() {
        return this.imageFile;
    }

    /**
     * Sets the given value to imageFile attribute.
     *
     * @param imageFile
     *            the given value to set.
     */
    public void setImageFile(boolean imageFile) {
        this.imageFile = imageFile;
    }

    /**
     * Returns the value of extension attribute.
     *
     * @return the value of extension.
     */
    public String getExtension() {
        return this.extension;
    }

    /**
     * Sets the given value to extension attribute.
     *
     * @param extension
     *            the given value to set.
     * @throws IllegalArgumentException
     *             if given parameter is null or empty.
     */
    public void setExtension(String extension) {
        Helper.checkStringNotNullOrEmpty(extension, "extension");

        this.extension = extension;
    }

    /**
     * Returns the value of bundledFile attribute.
     *
     * @return the value of bundledFile.
     */
    public boolean isBundledFile() {
        return this.bundledFile;
    }

    /**
     * Sets the given value to bundledFile attribute.
     *
     * @param bundledFile
     *            the given value to set.
     */
    public void setBundledFile(boolean bundledFile) {
        this.bundledFile = bundledFile;
    }

    /**
     * Returns the value of id attribute.
     *
     * @return the value of id.
     */
    public long getId() {
        return this.id;
    }

    /**
     * Sets the given value to id attribute.
     *
     * @param id
     *            the given value to set.
     * @throws IllegalArgumentException
     *             if the id parameter is non-positive
     */
    public void setId(long id) {
        Helper.checkNumberPositive(id, "id");
        this.id = id;
    }
}
