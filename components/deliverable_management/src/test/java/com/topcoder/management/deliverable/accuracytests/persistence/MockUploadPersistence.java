/*
 * Copyright (C) 2006-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.accuracytests.persistence;

import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.SubmissionStatus;
import com.topcoder.management.deliverable.SubmissionType;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.UploadStatus;
import com.topcoder.management.deliverable.UploadType;
import com.topcoder.management.deliverable.persistence.UploadPersistence;
import com.topcoder.management.deliverable.persistence.UploadPersistenceException;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;


/**
 * A mock class implements <code>UploadPersistence</code> interface. Just records which implemented method was last
 * called.
 *
 * @author skatou, FireIce
 * @version 1.1
 */
public class MockUploadPersistence implements UploadPersistence {
    /** Represents the name of implemented method last called. */
    private static String lastCalled = null;

    /** Logger instance using the class name as category. */
    private static final Log logger = LogFactory.getLog(MockUploadPersistence.class.getName());

    /**
     * Gets the name of implemented method last called.
     *
     * @return the name of the method last called.
     */
    public static String getLastCalled() {
        return lastCalled;
    }

    /**
     * Records this method is called.
     *
     * @param uploadType parameter 1.
     *
     * @throws UploadPersistenceException never thrown.
     */
    public void addUploadType(UploadType uploadType) throws UploadPersistenceException {
        lastCalled = "addUploadType" + uploadType;
    }

    /**
     * Records this method is called.
     *
     * @param uploadType parameter 1.
     *
     * @throws UploadPersistenceException never thrown.
     */
    public void removeUploadType(UploadType uploadType)
        throws UploadPersistenceException {
        lastCalled = "removeUploadType" + uploadType;
    }

    /**
     * Records this method is called.
     *
     * @param uploadType parameter 1.
     *
     * @throws UploadPersistenceException never thrown.
     */
    public void updateUploadType(UploadType uploadType)
        throws UploadPersistenceException {
        lastCalled = "updateUploadType" + uploadType;
    }

    /**
     * Records this method is called.
     *
     * @param uploadTypeId parameter 1.
     *
     * @return null.
     *
     * @throws UploadPersistenceException never thrown.
     */
    public UploadType loadUploadType(long uploadTypeId)
        throws UploadPersistenceException {
        lastCalled = "loadUploadType" + uploadTypeId;

        return null;
    }

    /**
     * Records this method is called.
     *
     * @return null.
     *
     * @throws UploadPersistenceException never thrown.
     */
    public long[] getAllUploadTypeIds() throws UploadPersistenceException {
        lastCalled = "getAllUploadTypeIds";

        return null;
    }

    /**
     * Records this method is called.
     *
     * @param uploadStatus parameter 1.
     *
     * @throws UploadPersistenceException never thrown.
     */
    public void addUploadStatus(UploadStatus uploadStatus)
        throws UploadPersistenceException {
        lastCalled = "addUploadStatus" + uploadStatus;
    }

    /**
     * Records this method is called.
     *
     * @param uploadStatus parameter 1.
     *
     * @throws UploadPersistenceException never thrown.
     */
    public void removeUploadStatus(UploadStatus uploadStatus)
        throws UploadPersistenceException {
        lastCalled = "removeUploadStatus" + uploadStatus;
    }

    /**
     * Records this method is called.
     *
     * @param uploadStatus parameter 1.
     *
     * @throws UploadPersistenceException never thrown.
     */
    public void updateUploadStatus(UploadStatus uploadStatus)
        throws UploadPersistenceException {
        lastCalled = "updateUploadStatus" + uploadStatus;
    }

    /**
     * Records this method is called.
     *
     * @param uploadStatusId parameter 1.
     *
     * @return null.
     *
     * @throws UploadPersistenceException never thrown.
     */
    public UploadStatus loadUploadStatus(long uploadStatusId)
        throws UploadPersistenceException {
        lastCalled = "loadUploadStatus" + uploadStatusId;

        return null;
    }

    /**
     * Records this method is called.
     *
     * @return null.
     *
     * @throws UploadPersistenceException never thrown.
     */
    public long[] getAllUploadStatusIds() throws UploadPersistenceException {
        lastCalled = "getAllUploadStatusIds";

        return null;
    }

    /**
     * Records this method is called.
     *
     * @param submissionStatus parameter 1.
     *
     * @throws UploadPersistenceException never thrown.
     */
    public void addSubmissionStatus(SubmissionStatus submissionStatus)
        throws UploadPersistenceException {
        lastCalled = "addSubmissionStatus" + submissionStatus;
    }

    /**
     * Records this method is called.
     *
     * @param submissionStatus parameter 1.
     *
     * @throws UploadPersistenceException never thrown.
     */
    public void removeSubmissionStatus(SubmissionStatus submissionStatus)
        throws UploadPersistenceException {
        lastCalled = "removeSubmissionStatus" + submissionStatus;
    }

    /**
     * Records this method is called.
     *
     * @param submissionStatus parameter 1.
     *
     * @throws UploadPersistenceException never thrown.
     */
    public void updateSubmissionStatus(SubmissionStatus submissionStatus)
        throws UploadPersistenceException {
        lastCalled = "updateSubmissionStatus" + submissionStatus;
    }

    /**
     * Records this method is called.
     *
     * @param submissionStatusId parameter 1.
     *
     * @return null.
     *
     * @throws UploadPersistenceException never thrown.
     */
    public SubmissionStatus loadSubmissionStatus(long submissionStatusId)
        throws UploadPersistenceException {
        lastCalled = "loadSubmissionStatus" + submissionStatusId;

        return null;
    }

    /**
     * Records this method is called.
     *
     * @return null.
     *
     * @throws UploadPersistenceException never thrown.
     */
    public long[] getAllSubmissionStatusIds() throws UploadPersistenceException {
        lastCalled = "getAllSubmissionStatusIds";

        return null;
    }

    /**
     * Records this method is called.
     *
     * @param upload null.
     *
     * @throws UploadPersistenceException never thrown.
     */
    public void addUpload(Upload upload) throws UploadPersistenceException {
        lastCalled = "addUpload" + upload;
    }

    /**
     * Records this method is called.
     *
     * @param upload parameter 1.
     *
     * @throws UploadPersistenceException never thrown.
     */
    public void removeUpload(Upload upload) throws UploadPersistenceException {
        lastCalled = "removeUpload" + upload;
    }

    /**
     * Records this method is called.
     *
     * @param upload parameter 1.
     *
     * @throws UploadPersistenceException never thrown.
     */
    public void updateUpload(Upload upload) throws UploadPersistenceException {
        lastCalled = "updateUpload" + upload;
    }

    /**
     * Records this method is called.
     *
     * @param uploadId parameter 1.
     *
     * @return null.
     *
     * @throws UploadPersistenceException never thrown.
     */
    public Upload loadUpload(long uploadId) throws UploadPersistenceException {
        lastCalled = "loadUpload" + uploadId;

        return null;
    }

    /**
     * Records this method is called.
     *
     * @param submission parameter 1.
     *
     * @throws UploadPersistenceException never thrown.
     */
    public void addSubmission(Submission submission) throws UploadPersistenceException {
        lastCalled = "addSubmission" + submission;
    }

    /**
     * Records this method is called.
     *
     * @param submission parameter 1.
     *
     * @throws UploadPersistenceException never thrown.
     */
    public void removeSubmission(Submission submission)
        throws UploadPersistenceException {
        lastCalled = "removeSubmission" + submission;
    }

    /**
     * Records this method is called.
     *
     * @param submission parameter 1.
     *
     * @throws UploadPersistenceException never thrown.
     */
    public void updateSubmission(Submission submission)
        throws UploadPersistenceException {
        lastCalled = "updateSubmission" + submission;
    }

    /**
     * Records this method is called.
     *
     * @param submissionId parameter 1.
     *
     * @return null.
     *
     * @throws UploadPersistenceException never thrown.
     */
    public Submission loadSubmission(long submissionId)
        throws UploadPersistenceException {
        lastCalled = "loadSubmission" + submissionId;

        return null;
    }

    /**
     * Records this method is called and return an array of submission with given ids.
     *
     * @param submissionIds submission ids of the return submissions.
     *
     * @return an array of submission with given ids.
     *
     * @throws UploadPersistenceException never thrown.
     */
    public Submission[] loadSubmissions(long[] submissionIds)
        throws UploadPersistenceException {
        lastCalled = "loadSubmissions" + submissionIds;

        Submission[] submissions = new Submission[submissionIds.length];

        for (int i = 0; i < submissions.length; ++i) {
            submissions[i] = new Submission(submissionIds[i]);
        }

        return submissions;
    }

    /**
     * Records this method is called and return an array of upload with given ids.
     *
     * @param uploadIds upload ids of the return uploads.
     *
     * @return an array of upload with given ids.
     *
     * @throws UploadPersistenceException never thrown.
     */
    public Upload[] loadUploads(long[] uploadIds) throws UploadPersistenceException {
        lastCalled = "loadUploads" + uploadIds;

        Upload[] uploads = new Upload[uploadIds.length];
        for (int i = 0; i < uploads.length; ++i) {
            uploads[i] = new Upload(uploadIds[i]);
        }

        return uploads;
    }

    /**
     * Records this method is called.
     *
     * @param uploadStatusIds parameter 1.
     *
     * @return null.
     *
     * @throws UploadPersistenceException never thrown.
     */
    public UploadStatus[] loadUploadStatuses(long[] uploadStatusIds)
        throws UploadPersistenceException {
        lastCalled = "loadUploadStatuses" + uploadStatusIds;

        return null;
    }

    /**
     * Records this method is called.
     *
     * @param submissionStatusIds parameter 1.
     *
     * @return null.
     *
     * @throws UploadPersistenceException never thrown.
     */
    public SubmissionStatus[] loadSubmissionStatuses(long[] submissionStatusIds)
        throws UploadPersistenceException {
        lastCalled = "loadSubmissionStatuses" + submissionStatusIds;

        return null;
    }

    /**
     * Records this method is called.
     *
     * @param uploadTypeIds parameter 1.
     *
     * @return null.
     *
     * @throws UploadPersistenceException never thrown.
     */
    public UploadType[] loadUploadTypes(long[] uploadTypeIds)
        throws UploadPersistenceException {
        lastCalled = "loadUploadTypes" + uploadTypeIds;

        return null;
    }

    public Submission[] loadSubmissions(CustomResultSet resultSet) throws UploadPersistenceException {
        try {
            lastCalled = "loadSubmissions";

            Submission[] submissionArray = new Submission[resultSet.getRecordCount()];

            logger.log(Level.INFO, " Loading: " + submissionArray.length);

            int index = 0;
            while (resultSet.next()) {
                submissionArray[index] = new Submission();
                Submission submission = submissionArray[index++];
                submission.setId(resultSet.getInt("submission_id"));
            }

            return submissionArray;

        } catch (Exception ex) {
            throw new UploadPersistenceException("Error occurs while retrieving submission.", ex);
        }
    }

    public Upload[] loadUploads(CustomResultSet resultSet) throws UploadPersistenceException {

        try {

            lastCalled = "loadUploads";

            Upload[] uploadArray = new Upload[resultSet.getRecordCount()];

            int index = 0;
            while (resultSet.next()) {
                uploadArray[index] = new Upload();
                Upload upload = uploadArray[index++];
                upload.setId(resultSet.getInt("upload_id"));
            }

            return uploadArray;

        } catch (Exception ex) {
            throw new UploadPersistenceException("Error occurs while retrieving Uploads.", ex);
        }
    }

    public void addSubmissionType(SubmissionType submissionType) throws UploadPersistenceException {
        lastCalled = "addSubmissionType" + submissionType;

    }

    public long[] getAllSubmissionTypeIds() throws UploadPersistenceException {
        lastCalled = "getAllSubmissionTypeIds";
        return null;
    }

    public SubmissionType loadSubmissionType(long submissionTypeId) throws UploadPersistenceException {
        lastCalled = "loadSubmissionType" + submissionTypeId;

        return null;
    }

    public SubmissionType[] loadSubmissionTypes(long[] submissionTypeIds) throws UploadPersistenceException {
        lastCalled = "loadSubmissionTypes" + submissionTypeIds;
        return null;
    }

    public void removeSubmissionType(SubmissionType submissionType) throws UploadPersistenceException {
        lastCalled = "removeSubmissionType" + submissionType;
    }

    public void updateSubmissionType(SubmissionType submissionType) throws UploadPersistenceException {
        lastCalled = "updateSubmissionType" + submissionType;
    }
}
