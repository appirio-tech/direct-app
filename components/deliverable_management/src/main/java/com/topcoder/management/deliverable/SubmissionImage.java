/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * This class represents an image associated with a submission. It is a simple JavaBean that provides getters and
 * setters for all private attributes. Additionally it provides isValidToPersist() method to be used by upload managers.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.2
 * @since 1.2
 */
public class SubmissionImage implements Serializable {

    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 5540440936257181172L;

    /**
     * Represents The ID of the submission.
     */
    private long submissionId;

    /**
     * Represents the ID of the submission image.
     */
    private int imageId;

    /**
     * Represents the sort order of the submission images.
     */
    private int sortOrder;

    /**
     * Represents the last modification date.
     */
    private Date modifyDate;

    /**
     * Represents the creation date.
     */
    private Date createDate;

    /**
     * Creates an instance of SubmissionImage.
     */
    public SubmissionImage() {
    }

    /**
     * Retrieves the ID of the submission.
     *
     * @return the ID of the submission.
     */
    public long getSubmissionId() {
        return submissionId;
    }

    /**
     * Sets the ID of the submission.
     *
     * @param submissionId
     *            the ID of the submission.
     */
    public void setSubmissionId(long submissionId) {
        this.submissionId = submissionId;
    }

    /**
     * Retrieves the ID of the submission image.
     *
     * @return the ID of the submission image.
     */
    public int getImageId() {
        return imageId;
    }

    /**
     * Sets the ID of the submission image.
     *
     * @param imageId
     *            the ID of the submission image.
     */
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    /**
     * Retrieves the sort order of the submission images.
     *
     * @return the sort order of the submission images.
     */
    public int getSortOrder() {
        return sortOrder;
    }

    /**
     * Sets the sort order of the submission images.
     *
     * @param sortOrder
     *            the sort order of the submission images.
     */
    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * Retrieves the last modification date.
     *
     * @return the last modification date.
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * Sets the last modification date.
     *
     * @param modifyDate
     *            the last modification date.
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * Retrieves the creation date.
     *
     * @return the creation date.
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Sets the creation date.
     *
     * @param createDate
     *            the creation date.
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * <p>
     * Tells whether all the required fields of this structure have values set.
     * </p>
     *
     * @return true if all fields required for persistence are present
     */
    public boolean isValidToPersist() {
        return (submissionId > 0) && (imageId > 0);
    }
}
