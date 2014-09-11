/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management;

/**
 * <p>
 * This interface defines the contract for managing screening tasks. It provides the methods to
 * initiate a screening task, and query the screening task and its details. Details refer to the
 * screening results here. Normally, one upload is used to initiate one screening task.
 * </p>
 * <p>
 * Note that the update of screening task in the persistence (e.g. add/remove screening results,
 * update screening status, etc) is provided by another component. These two should work together on
 * the same persistence.
 * </p>
 * <p>
 * Users can get an instance of ScreeningManager using the ScreeningManagerFactory. Implementations
 * do not need to provide configuration support, since it is implicitly provided by the
 * ScreeningManagerFactory through the Object Factory.
 * </p>
 * <p>
 * Implementations of this interface are not required to be thread-safe.
 * </p>
 *
 * @author colau, haozhangr
 * @version 1.0
 */
public interface ScreeningManager {
    /**
     * <p>
     * Initiates a screening task with the specified upload and operator. The task should be set to
     * pending once it's initiated. The screener and start timestamp of the task should be left
     * unspecified since it's not determined yet.
     * </p>
     *
     *
     *
     * @param upload
     *            the upload identifier
     * @param operator
     *            the creation user of the screening task
     * @throws IllegalArgumentException
     *             if upload is non-positive, or operator is null or empty String
     * @throws PersistenceException
     *             if any error occurs in persistence
     * @throws ScreeningTaskAlreadyExistsException
     *             if the upload has been used to initiate a screening task
     */
    public void initiateScreening(long upload, String operator) throws PersistenceException,
            ScreeningTaskAlreadyExistsException;

    /**
     * <p>
     * Gets the screening task with the single upload, with the screening results included as
     * details.
     * </p>
     *
     *
     *
     * @param upload
     *            the upload identifier
     * @return the screening task with details
     * @throws IllegalArgumentException
     *             if upload is non-positive
     * @throws PersistenceException
     *             if any error occurs in persistence
     * @throws ScreeningTaskDoesNotExistException
     *             if the upload was not initiated a screening task
     */
    public ScreeningTask getScreeningDetails(long upload) throws PersistenceException,
            ScreeningTaskDoesNotExistException;

    /**
     * <p>
     * Gets the screening tasks with the multiple uploads, without the screening results included.
     * Each screening task will be returned in the position corresponding to the respective upload
     * in the array.
     * </p>
     * <p>
     * If any of the uploads was not initiated a screening task, the default behavior would throw a
     * ScreeningTaskDoesNotExistException.
     * </p>
     *
     *
     *
     * @param uploads
     *            the upload identifiers
     * @return the screening tasks without details
     * @throws IllegalArgumentException
     *             if uploads is null, or any of the uploads is non-positive, or uploads contains
     *             duplicated elements
     * @throws PersistenceException
     *             if any error occurs in persistence
     * @throws ScreeningTaskDoesNotExistException
     *             if any of the uploads was not initiated a screening task
     */
    public ScreeningTask[] getScreeningTasks(long[] uploads) throws PersistenceException,
            ScreeningTaskDoesNotExistException;

    /**
     * <p>
     * Gets the screening tasks with the multiple uploads, without the screening results included.
     * Each screening task will be returned in the position corresponding to the respective upload
     * in the array.
     * </p>
     * <p>
     * If any of the uploads was not initiated a screening task, the behavior depends on the
     * allowNotExist flag. If it is true, null will be set in the corresponding position in the
     * array. If it is false, a ScreeningTaskDoesNotExistException will be thrown.
     * </p>
     *
     *
     *
     * @param uploads
     *            the upload identifiers
     * @param allowNonExist
     *            whether it allows upload with no screening task initiated
     * @return the screening tasks without details
     * @throws IllegalArgumentException
     *             if uploads is null, or any of the uploads is non-positive, or uploads contains
     *             duplicated elements
     * @throws PersistenceException
     *             if any error occurs in persistence
     * @throws ScreeningTaskDoesNotExistException
     *             if any of the uploads was not initiated a screening task, and allowNotExist is
     *             false
     */
    public ScreeningTask[] getScreeningTasks(long[] uploads, boolean allowNonExist)
        throws PersistenceException, ScreeningTaskDoesNotExistException;
}
