/*
 * Copyright (C) 2006-2007,2010-2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.persistence;

import com.topcoder.management.deliverable.MimeType;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.SubmissionDeclaration;
import com.topcoder.management.deliverable.SubmissionImage;
import com.topcoder.management.deliverable.SubmissionStatus;
import com.topcoder.management.deliverable.SubmissionType;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.UploadStatus;
import com.topcoder.management.deliverable.UploadType;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;

/**
 * <p>
 * The UploadPersistence interface defines the methods for persisting and retrieving the object model in this
 * component. This interface handles the persistence of the upload related classes that make up the object model
 * Uploads, Submissions, UploadTypes, UploadStatuses, SubmissionStatuses. This interface is not responsible for
 * searching the persistence for the various entities. This is instead handled by an UploadManager implementation.
 * </p>
 *
 * <p>
 * <em>Changes in 1.1: </em>
 * <ul>
 * <li>Added methods for managing submission types in persistence.</li>
 * </ul>
 * </p>
 * <p>
 * Changes in 1.2:
 * <ul>
 * <li>Added methods for managing SubmissionImage entities.</li>
 * <li>Added methods for retrieving MimeType entities.</li>
 * <li>Added methods for retrieving project/user submissions.</li>
 * <li>Added method for retrieving images associated with submission.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Changes in 1.3:
 * <ul>
 * <li>Remove <code>getUploadsForSubmission</code> method because a submission can only have a upload now.</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Changes in 1.4 (TC Direct Replatforming Release 5):
 * <ul>
 * <li>Added {@link #getSubmissionDeclaration(long)} method to retrieve SubmissionDeclaration for submission.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> Implementations of this interface are not required to be thread-safe or immutable.
 * </p>
 *
 * @author aubergineanode, singlewood, George1
 * @author saarixx, sparemax
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.4
 */
public interface UploadPersistence {
    /**
     * Adds the given uploadType to the persistence. The id of the upload type must already be
     * assigned, as must all the other fields needed for persistence.
     *
     * @param uploadType The upload type to add
     * @throws IllegalArgumentException If uploadType is null
     * @throws IllegalArgumentException If isValidToPersist returns false
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void addUploadType(UploadType uploadType) throws UploadPersistenceException;

    /**
     * Removes the given upload type (by id) from the persistence. The id of the upload type can not
     * be UNSET_ID, but the other fields do not matter.
     *
     * @param uploadType The upload type to remove
     * @throws IllegalArgumentException If uploadType is null
     * @throws IllegalArgumentException If the id is UNSET_ID or the modification user/date is not
     *             set (not all implementations are required to enforce this last part)
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void removeUploadType(UploadType uploadType) throws UploadPersistenceException;

    /**
     * Updates the given upload type in the persistence. The id of the uploadType can not be
     * UNSET_ID, and all other fields needed for persistence must also be assigned.
     *
     * @param uploadType The upload type to update
     * @throws IllegalArgumentException If uploadType is null
     * @throws IllegalArgumentException If isValidToPersist returns false
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void updateUploadType(UploadType uploadType) throws UploadPersistenceException;

    /**
     * Loads the UploadType with the given id from persistence. Returns null if there is no
     * UploadType with the given id.
     *
     * @return The loaded UploadType or null
     * @param uploadTypeId The id of the item to retrieve
     * @throws IllegalArgumentException if uploadTypeId is <= 0
     * @throws UploadPersistenceException if there is an error reading the persistence data
     */
    public UploadType loadUploadType(long uploadTypeId) throws UploadPersistenceException;

    /**
     * Gets the ids of all upload types in the persistence. The individual upload types can then be
     * loaded with the loadUploadType method.
     *
     * @return The ids of all upload types
     * @throws UploadPersistenceException If there is an error reading the persistence store
     */
    public long[] getAllUploadTypeIds() throws UploadPersistenceException;

    /**
     * Adds the given uploadStatus to the persistence. The id of the upload status must already be
     * assigned, as must all the other fields needed for persistence.
     *
     * @param uploadStatus The upload status to add
     * @throws IllegalArgumentException If uploadStatus is null
     * @throws IllegalArgumentException If isValidToPersist returns false
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void addUploadStatus(UploadStatus uploadStatus) throws UploadPersistenceException;

    /**
     * Removes the given upload status (by id) from the persistence. The id of the upload status can
     * not be UNSET_ID, but the other fields do not matter.
     *
     * @param uploadStatus The upload status to remove
     * @throws IllegalArgumentException If uploadStatus is null
     * @throws IllegalArgumentException If the id is UNSET_ID or the modification user/date is not
     *             set (not all implementations are required to enforce this last part)
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void removeUploadStatus(UploadStatus uploadStatus) throws UploadPersistenceException;

    /**
     * Updates the given upload status in the persistence. The id of the uploadStatus can not be
     * UNSET_ID, and all other fields needed for persistence must also be assigned.
     *
     * @param uploadStatus The upload status to update
     * @throws IllegalArgumentException If uploadStatus is null
     * @throws IllegalArgumentException If isValidToPersist returns false
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void updateUploadStatus(UploadStatus uploadStatus) throws UploadPersistenceException;

    /**
     * Loads the UploadStatus with the given id from persistence. Returns null if there is no
     * UploadStatus with the given id.
     *
     * @return The loaded UploadStatus or null
     * @param uploadStatusId The id of the item to retrieve
     * @throws IllegalArgumentException if uploadStatusId is <= 0
     * @throws UploadPersistenceException if there is an error reading the persistence data
     */
    public UploadStatus loadUploadStatus(long uploadStatusId) throws UploadPersistenceException;

    /**
     * Gets the ids of all upload statuses in the persistence. The individual upload statuses can
     * then be loaded with the loadUploadStatus method.
     *
     * @return The ids of all upload statuses
     * @throws UploadPersistenceException If there is an error reading the persistence store
     */
    public long[] getAllUploadStatusIds() throws UploadPersistenceException;

    /**
     * Adds the given submission status to the persistence. The id of the submission status must
     * already be assigned, as must all the other fields needed for persistence.
     *
     * @param submissionStatus The submission status to add
     * @throws IllegalArgumentException If submissionStatus is null
     * @throws IllegalArgumentException If isValidToPersist returns false
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void addSubmissionStatus(SubmissionStatus submissionStatus) throws UploadPersistenceException;

    /**
     * Removes the given submission status (by id) from the persistence. The id of the submission
     * status can not be UNSET_ID, but the other fields do not matter.
     *
     * @param submissionStatus The submission status to remove
     * @throws IllegalArgumentException If submissionStatus is null
     * @throws IllegalArgumentException If the id is UNSET_ID or the modification user/date is not
     *             set (not all implementations are required to enforce this last part)
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void removeSubmissionStatus(SubmissionStatus submissionStatus) throws UploadPersistenceException;

    /**
     * Updates the given submission status in the persistence. The id of the submissionStats can not
     * be UNSET_ID, and all other fields needed for persistence must also be assigned.
     *
     * @param submissionStatus The submissionStatus to update
     * @throws IllegalArgumentException If submissionStatus is null
     * @throws IllegalArgumentException If isValidToPersist returns false
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void updateSubmissionStatus(SubmissionStatus submissionStatus) throws UploadPersistenceException;

    /**
     * Loads the SubmissionStatus with the given id from persistence. Returns null if there is no
     * SubmissionStatus with the given id.
     *
     * @return The loaded SubmissionStatus or null
     * @param submissionStatusId The id of the item to retrieve
     * @throws IllegalArgumentException if submissionStatusId is <= 0
     * @throws UploadPersistenceException if there is an error reading the persistence data
     */
    public SubmissionStatus loadSubmissionStatus(long submissionStatusId) throws UploadPersistenceException;

    /**
     * Gets the ids of all submission statuses in the persistence. The individual submission
     * statuses can then be loaded with the loadSubmissionStatus method.
     *
     * @return The ids of all submission statuses
     * @throws UploadPersistenceException If there is an error reading the persistence store
     */
    public long[] getAllSubmissionStatusIds() throws UploadPersistenceException;

    /**
     * <p>
     * Adds the given submission type to the persistence. The id of the submission type must already be assigned, as
     * must all the other fields needed for persistence.
     * </p>
     *
     * @param submissionType
     *            the submission type to add.
     *
     * @throws IllegalArgumentException
     *             if submissionType is <code>null</code> or isValidToPersist returns <code>false</code>.
     * @throws UploadPersistenceException
     *             if there is an error making the change in the persistence.
     *
     * @since 1.1
     */
    public void addSubmissionType(SubmissionType submissionType) throws UploadPersistenceException;

    /**
     * <p>
     * Removes the given submission type (by id) from the persistence. The id of the submission type can not be
     * UNSET_ID, but the other fields do not matter.
     * </p>
     *
     * @param submissionType
     *            the submission type to remove.
     *
     * @throws IllegalArgumentException
     *             if submissionType is <code>null</code> or the id is UNSET_ID or the modification user/date is not
     *             set (not all implementations are required to enforce this last part).
     * @throws UploadPersistenceException
     *             if there is an error making the change in the persistence
     *
     * @since 1.1
     */
    public void removeSubmissionType(SubmissionType submissionType) throws UploadPersistenceException;

    /**
     * <p>
     * Updates the given submission type in the persistence. The id of the submissionType can not be UNSET_ID, and all
     * other fields needed for persistence must also be assigned.
     * </p>
     *
     * @param submissionType
     *            the submissionType to update.
     *
     * @throws IllegalArgumentException
     *             if submissionType is <code>null</code> or isValidToPersist returns <code>false</code>.
     * @throws UploadPersistenceException
     *             if there is an error making the change in the persistence
     *
     * @since 1.1
     */
    public void updateSubmissionType(SubmissionType submissionType) throws UploadPersistenceException;

    /**
     * <p>
     * Loads the SubmissionType with the given id from persistence. Returns null if there is no SubmissionType with
     * the given id.
     * </p>
     *
     * @param submissionTypeId
     *            the id of the item to retrieve.
     *
     * @return the loaded SubmissionType or <code>null</code>.
     *
     * @throws IllegalArgumentException
     *             if submissionTypeId is not positive.
     * @throws UploadPersistenceException
     *             if there is an error reading the persistence data.
     *
     * @since 1.1
     */
    public SubmissionType loadSubmissionType(long submissionTypeId) throws UploadPersistenceException;

    /**
     * <p>
     * Loads all SubmissionTypes with the given ids from persistence. May return a 0-length array.
     * </p>
     *
     * @param submissionTypeIds
     *            the ids of the objects to load.
     *
     * @return the loaded SubmissionTypes
     *
     * @throws IllegalArgumentException
     *             if any id is not positive.
     * @throws UploadPersistenceException
     *             if there is an error reading the persistence data.
     *
     * @since 1.1
     */
    public SubmissionType[] loadSubmissionTypes(long[] submissionTypeIds) throws UploadPersistenceException;

    /**
     * <p>
     * Gets the ids of all submission types in the persistence. The individual submission types can then be loaded
     * with the loadSubmissionType method.
     * </p>
     *
     * @return the ids of all submission types.
     *
     * @throws UploadPersistenceException
     *             If there is an error reading the persistence store.
     *
     * @since 1.1
     */
    public long[] getAllSubmissionTypeIds() throws UploadPersistenceException;

    /**
     * Adds the given upload to the persistence. The id of the upload must already be assigned, as
     * must all the other fields needed for persistence.
     *
     * @param upload The upload to add
     * @throws IllegalArgumentException If upload is null
     * @throws IllegalArgumentException If isValidToPersist returns false
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void addUpload(Upload upload) throws UploadPersistenceException;

    /**
     * Removes the given upload (by id) from the persistence. The id of the upload can not be
     * UNSET_ID, but the other fields do not matter.
     *
     * @param upload The upload to remove
     * @throws IllegalArgumentException If upload is null
     * @throws IllegalArgumentException If the id is UNSET_ID or the modification user/date is not
     *             set (not all implementations are required to enforce this last part)
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void removeUpload(Upload upload) throws UploadPersistenceException;

    /**
     * Updates the given upload in the persistence. The id of the upload can not be UNSET_ID, and
     * all other fields needed for persistence must also be assigned.
     *
     * @param upload The upload to update
     * @throws IllegalArgumentException If upload is null
     * @throws IllegalArgumentException If isValidToPersist returns false
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void updateUpload(Upload upload) throws UploadPersistenceException;

    /**
     * Loads the Upload with the given id from persistence. Returns null if there is no Upload with
     * the given id.
     *
     * @return The loaded Upload or null
     * @param uploadId The id of the item to retrieve
     * @throws IllegalArgumentException if uploadId is <= 0
     * @throws UploadPersistenceException if there is an error reading the persistence data
     */
    public Upload loadUpload(long uploadId) throws UploadPersistenceException;

    /**
     * Adds the given submission to the persistence. The id of the submission must already be
     * assigned, as must all the other fields needed for persistence.
     *
     * @param submission The submission to add
     * @throws IllegalArgumentException If submission is null
     * @throws IllegalArgumentException If isValidToPersist returns false
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void addSubmission(Submission submission) throws UploadPersistenceException;

    /**
     * Removes the given submission (by id) from the persistence. The id of the submission can not
     * be UNSET_ID, but the other fields do not matter.
     *
     * @param submission The submission to remove
     * @throws IllegalArgumentException If submission is null
     * @throws IllegalArgumentException If the id is UNSET_ID or the modification user/date is not
     *             set (not all implementations are required to enforce this last part)
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void removeSubmission(Submission submission) throws UploadPersistenceException;

    /**
     * Updates the given submission in the persistence. The id of the submission can not be
     * UNSET_ID, and all other fields needed for persistence must also be assigned.
     *
     * @param submission The submission to add
     * @throws IllegalArgumentException If submission is null
     * @throws IllegalArgumentException If isValidToPersist returns false
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void updateSubmission(Submission submission) throws UploadPersistenceException;

    /**
     * Loads the Submission with the given id from persistence. Returns null if there is no
     * Submission with the given id.
     *
     * @return The loaded Submission or null
     * @param submissionId The id of the item to retrieve
     * @throws IllegalArgumentException if submissionId is <= 0
     * @throws UploadPersistenceException if there is an error reading the persistence data
     */
    public Submission loadSubmission(long submissionId) throws UploadPersistenceException;

    /**
     * Loads all Submissions with the given ids from persistence. May return a 0-length array.
     *
     * @param submissionIds The ids of submissions to load
     * @return The loaded submissions
     * @throws IllegalArgumentException if any id is <= 0
     * @throws UploadPersistenceException if there is an error reading the persistence data
     */
    public Submission[] loadSubmissions(long[] submissionIds) throws UploadPersistenceException;

    /**
     * Loads all Submissions from the result of the SELECT operation. May return an empty array.
     *
     * @param resultSet
     * Result of the SELECT operation. This result should contain the following columns:
     * <ul>
     * <li></li>
     * </ul>
     * @return The loaded submissions
     * @throws IllegalArgumentException if any id is <= 0
     * @throws UploadPersistenceException if there is an error reading the persistence data
     */
    public Submission[] loadSubmissions(CustomResultSet resultSet) throws UploadPersistenceException;

    /**
     * Loads all Uploads with the given ids from persistence. May return a 0-length array.
     *
     * @param uploadIds The ids of uploads to load
     * @return The loaded uploads
     * @throws IllegalArgumentException if any id is <= 0
     * @throws UploadPersistenceException if there is an error reading the persistence data
     */
    public Upload[] loadUploads(long[] uploadIds) throws UploadPersistenceException;

    /**
     * Loads all Uploads from the result of the SELECT operation. May return an empty array.
     *
     * @param resultSet
     * Result of the SELECT operation.
     * @return The loaded uploads
     * @throws IllegalArgumentException if any id is <= 0
     * @throws UploadPersistenceException if there is an error reading the persistence data
     */
    public Upload[] loadUploads(CustomResultSet resultSet) throws UploadPersistenceException;

    /**
     * Loads all UploadStatuses with the given ids from persistence. May return a 0-length array.
     *
     * @param uploadStatusIds The ids of the objects to load
     * @return the loaded UploadStatuses
     * @throws IllegalArgumentException if any id is <= 0
     * @throws UploadPersistenceException if there is an error reading the persistence data
     */
    public UploadStatus[] loadUploadStatuses(long[] uploadStatusIds) throws UploadPersistenceException;

    /**
     * Loads all SubmissionStatuses with the given ids from persistence. May return a 0-length
     * array.
     *
     * @param submissionStatusIds The ids of the objects to load
     * @return the loaded SubmissionStatuses
     * @throws IllegalArgumentException if any id is <= 0
     * @throws UploadPersistenceException if there is an error reading the persistence data
     */
    public SubmissionStatus[] loadSubmissionStatuses(long[] submissionStatusIds) throws UploadPersistenceException;

    /**
     * Loads all UploadTypes with the given ids from persistence. May return a 0-length array.
     *
     * @param uploadTypeIds The ids of the objects to load
     * @return the loaded UploadTypes
     * @throws IllegalArgumentException if any id is <= 0
     * @throws UploadPersistenceException if there is an error reading the persistence data
     */
    public UploadType[] loadUploadTypes(long[] uploadTypeIds) throws UploadPersistenceException;


    /**
     * Adds the given submission image to persistence.
     *
     * @param submissionImage
     *            the submission image to be created in persistence
     * @throws IllegalArgumentException
     *             If submissionImage is null, or submissionImage.isValidToPersist() returns false
     * @throws UploadPersistenceException
     *             If some error occurred when accessing the persistence
     *
     * @since 1.2
     */
    public void addSubmissionImage(SubmissionImage submissionImage) throws UploadPersistenceException;

    /**
     * Updates the submission image to persistence.
     *
     * @param submissionImage
     *            the submission image with updated data
     * @throws IllegalArgumentException
     *             If submissionImage is null, or submissionImage.isValidToPersist() returns false
     * @throws UploadPersistenceException
     *             If some error occurred when accessing the persistence
     *
     * @since 1.2
     */
    public void updateSubmissionImage(SubmissionImage submissionImage) throws UploadPersistenceException;

    /**
     * Removes the submission image to persistence.
     *
     * @param submissionImage
     *            the submission image to be removed from persistence
     * @throws IllegalArgumentException
     *             If submissionImage is null, submissionImage.getSubmissionId() <= 0 or submissionImage.getImageId() <=
     *             0
     * @throws UploadPersistenceException
     *             If some error occurred when accessing the persistence
     * @since 1.2
     */
    public void removeSubmissionImage(SubmissionImage submissionImage) throws UploadPersistenceException;


    /**
     * Loads the MimeType with the given id from persistence. Returns null if there is no MimeType with the given id.
     *
     * @param mimeTypeId
     *            The id of the item to retrieve
     * @return the loaded MimeType or null
     * @throws IllegalArgumentException
     *             if mimeTypeId <= 0
     * @throws UploadPersistenceException
     *             if there is an error reading the persistence data
     * @since 1.2
     */
    public MimeType loadMimeType(long mimeTypeId) throws UploadPersistenceException;

    /**
     * Gets the ids of all MIME types in the persistence. The individual MIME types can then be loaded with the
     * loadMimeType method.
     *
     * @return The ids of all MIME types
     * @throws UploadPersistenceException
     *             if there is an error reading the persistence store
     * @since 1.2
     */
    public long[] getAllMimeTypeIds() throws UploadPersistenceException;

    /**
     * Loads all MimeTypes with the given ids from persistence. May return an empty array.
     *
     * @param mimeTypeIds
     *            The ids of the objects to load
     * @return the loaded MimeTypes
     * @throws IllegalArgumentException
     *             if any id is <= 0
     * @throws UploadPersistenceException
     *             if there is an error reading the persistence data
     * @since 1.2
     */
    public MimeType[] loadMimeTypes(long[] mimeTypeIds) throws UploadPersistenceException;

    /**
     * Retrieves the user submissions for project with the given ID. If projectId or ownerId is unknown, this method
     * returns an empty array.
     *
     * @param projectId
     *            the ID of the project
     * @param ownerId
     *            the ID of the submission owner
     * @return the retrieved user submissions for project (not null, doesn't contain null)
     * @throws IllegalArgumentException
     *             If projectId <= 0 or ownerId <= 0
     * @throws UploadPersistenceException
     *             If some error occurred when accessing the persistence
     * @since 1.2
     */
    public Submission[] getUserSubmissionsForProject(long projectId, long ownerId) throws UploadPersistenceException;


    /**
     * Retrieves the project submissions. If projectId is unknown, this method returns an empty array.
     *
     * @param projectId
     *            the ID of the project
     * @return the retrieved project submissions (not null, doesn't contain null)
     * @throws IllegalArgumentException
     *             If projectId <= 0
     * @throws UploadPersistenceException
     *             If some error occurred when accessing the persistence
     * @since 1.2
     */
    public Submission[] getProjectSubmissions(long projectId) throws UploadPersistenceException;

    /**
     * Retrieves the images for submission with the given ID. If submissionId is unknown, this method returns an empty
     * array.
     *
     * @param submissionId
     *            the ID of the submission
     * @return the retrieved images for submission (not null, doesn't contain null)
     * @throws IllegalArgumentException
     *             If submissionId <= 0
     * @throws UploadPersistenceException
     *             If some error occurred when accessing the persistence
     * @since 1.2
     */
    public SubmissionImage[] getImagesForSubmission(long submissionId) throws UploadPersistenceException;
    
    /**
     * Retrieves the <code>SubmissionDeclaration</code> for submission with the given ID. If submissionId is unknown, this method returns null.
     * 
     * @param submissionId the ID of the submission
     * @return the retrieved <code>SubmissionDeclaration</code>, null if not found
     * @throws IllegalArgumentException
     *             If submissionId <= 0
     * @throws UploadPersistenceException
     *             If some error occurred when accessing the persistence
     * @since 1.4
     */
    public SubmissionDeclaration getSubmissionDeclaration(long submissionId) throws UploadPersistenceException;
}
