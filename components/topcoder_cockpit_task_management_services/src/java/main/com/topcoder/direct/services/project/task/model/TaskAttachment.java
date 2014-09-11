/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.model;

/**
 * <p>
 * This entity represents a task attachment.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is not thread safe because it is mutable.
 * </p>
 *
 * @author albertwang, sparemax
 * @version 1.0
 */
public class TaskAttachment extends AuditableEntity {
    /**
     * The serial version ID.
     */
    private static final long serialVersionUID = -7290387856053876913L;
    /**
     * <p>
     * Represents the file name.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private String fileName;
    /**
     * <p>
     * Represents the mime type.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private String mimeType;
    /**
     * <p>
     * Represents the task id.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private long taskId;

    /**
     * Creates an instance of TaskAttachment.
     */
    public TaskAttachment() {
        // Empty
    }

    /**
     * Gets the file name.
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
     * Gets the mime type.
     *
     * @return the mime type.
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * Sets the mime type.
     *
     * @param mimeType
     *            the mime type.
     */
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * Gets the task id.
     *
     * @return the task id.
     */
    public long getTaskId() {
        return taskId;
    }

    /**
     * Sets the task id.
     *
     * @param taskId
     *            the task id.
     */
    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }
}