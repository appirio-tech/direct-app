/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management;

/**
 * <p>
 * This exception indicates that the screening task was not initiated with the upload id, when
 * trying to query the screening task with it. It is thrown from the query methods of the
 * ScreeningManager.
 * </p>
 *
 * @author colau, haozhangr
 * @version 1.0
 */
public class ScreeningTaskDoesNotExistException extends ScreeningManagementException {

    /**
     * <p>
     * Represents the uploads that do not have the screening task initiated.
     * </p>
     * <p>
     * It is set in the constructor and not changed afterwards. The size must be at least one. Each
     * integer element should be positive. It is accessed in the getter method.
     * </p>
     *
     *
     */
    private final long[] uploads;

    /**
     * <p>
     * Creates the exception with the specified upload.
     * </p>
     *
     *
     *
     * @param upload
     *            the upload that does not have the screening task initiated
     */
    public ScreeningTaskDoesNotExistException(long upload) {
        this(new long[] {upload});
    }

    /**
     * <p>
     * Creates the exception with the specified uploads.
     * </p>
     *
     *
     *
     * @param uploads
     *            the uploads that do not have the screening task initiated
     * @throws IllegalArgumentException
     *             if the uploads is null or contains no element
     */
    public ScreeningTaskDoesNotExistException(long[] uploads) {
        super("The screening task does not exist.");
        Helper.checkNull(uploads, "uploads");
        if (uploads.length == 0) {
            throw new IllegalArgumentException("The 'uploads' should contain at least one element.");
        }
        this.uploads = (long[]) uploads.clone();
    }

    /**
     * <p>
     * Returns the uploads that do not have the screening task initiated.
     * </p>
     *
     *
     * @return the uploads that do not have the screening task initiated
     */
    public long[] getUploads() {

        return (long[]) uploads.clone();
    }
}
