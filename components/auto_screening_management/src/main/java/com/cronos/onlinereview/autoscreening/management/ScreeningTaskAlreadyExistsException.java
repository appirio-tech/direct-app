/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management;

/**
 * <p>
 * This exception indicates that a screening task has already been initiated with the upload id,
 * when trying to initiate a screening task with it. It is thrown from the initiate method of the
 * ScreeningManager.
 * </p>
 *
 * @author colau, haozhangr
 * @version 1.0
 */
public class ScreeningTaskAlreadyExistsException extends ScreeningManagementException {

    /**
     * <p>
     * Represents the upload that already has the screening task initiated.
     * </p>
     * <p>
     * It is set in the constructor and not changed afterwards. It should be positive. It is
     * accessed in the getter method.
     * </p>
     *
     *
     */
    private final long upload;

    /**
     * <p>
     * Creates the exception with the specified upload.
     * </p>
     *
     *
     *
     * @param upload
     *            the upload that already has the screening task initiated
     */
    public ScreeningTaskAlreadyExistsException(long upload) {
        super("The screening task for " + upload + " has already existed.");
        this.upload = upload;
    }

    /**
     * <p>
     * Returns the upload that already has the screening task initiated.
     * </p>
     *
     *
     * @return the upload that already has the screening task initiated
     */
    public long getUpload() {

        return upload;
    }
}
