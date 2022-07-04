/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.specification.accuracytests;

import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.SubmissionStatus;
import com.topcoder.management.deliverable.SubmissionType;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.UploadManager;
import com.topcoder.management.deliverable.UploadStatus;
import com.topcoder.management.deliverable.UploadType;

import com.topcoder.search.builder.filter.Filter;


/**
 * A mock implementation of UploadManager.
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class MockUploadManager implements UploadManager {
    /**
     * A static state variable for the mock.
     */
    private static int state = 0;

    /**
     * Used for testing purpose.
     */
    private Submission createdSubmission;

    /**
     * Used for testing purpose.
     */
    private Upload createdUpload;

    /**
     * Used for testing purpose.
     */
    private String createdUploadUserId;

    /**
     * Used for testing purpose.
     */
    private String createdSubmissionUserId;

    /**
     * Used for testing purpose.
     */
    private Submission submission = new Submission(10);

    /**
     * Used for testing purpose.
     */
    private Upload updatedUpload;

    /**
     * Mock implementation.
     *
     * @param arg0 upload
     * @param arg1 operator
     */
    public void createUpload(Upload arg0, String arg1) {
        createdUpload = arg0;
        createdUploadUserId = arg1;
    }

    /**
     * Mock implementation.
     *
     * @param upload upload
     * @param arg1 operator
     */
    public void updateUpload(Upload upload, String arg1) {
        this.updatedUpload = upload;
    }

    /**
     * Not implemented.
     *
     * @param arg0 upload
     * @param arg1 operator
     */
    public void removeUpload(Upload arg0, String arg1) {
    }

    /**
     * Not implemented.
     *
     * @param arg0 upload
     *
     * @return always null
     */
    public Upload getUpload(long arg0) {
        return null;
    }

    /**
     * Mock implementation.
     *
     * @param arg0 filter
     *
     * @return array of upload.
     */
    public Upload[] searchUploads(Filter arg0) {
        return new Upload[] {new Upload(1)};
    }

    /**
     * Not implemented.
     *
     * @param arg0 upload type
     * @param arg1 operator
     */
    public void createUploadType(UploadType arg0, String arg1) {
    }

    /**
     * Not implemented.
     *
     * @param arg0 upload type
     * @param arg1 operator
     */
    public void updateUploadType(UploadType arg0, String arg1) {
    }

    /**
     * Not implemented.
     *
     * @param arg0 upload type
     * @param arg1 operator
     */
    public void removeUploadType(UploadType arg0, String arg1) {
    }

    /**
     * Mock implementation.
     *
     * @return upload types
     */
    public UploadType[] getAllUploadTypes() {
        if (getState() == 0) {
            UploadType[] types = new UploadType[4];

            for (int i = 0; i < types.length; i++) {
                // modified in version 1.1 - because upload type id needs to be grated then zero
                types[i] = new UploadType(i + 1);
            }

            types[0].setName("Submission");
            types[1].setName("Review");
            types[2].setName("Final Fix");
            // added in version 1.1
            types[3].setName("Test Case");

            return types;
        }

        return null;
    }

    /**
     * Not implemented.
     *
     * @param arg0 upload status
     * @param arg1 operator
     */
    public void createUploadStatus(UploadStatus arg0, String arg1) {
    }

    /**
     * Not implemented.
     *
     * @param upload upload status
     * @param arg1 operator
     */
    public void updateUploadStatus(UploadStatus upload, String arg1) {
        // does nothing
    }

    /**
     * Not implemented.
     *
     * @param arg0 upload status
     * @param arg1 operator
     */
    public void removeUploadStatus(UploadStatus arg0, String arg1) {
    }

    /**
     * Mock implementation. Will throw exception if the throwError is set.
     *
     * @return upload status
     */
    public UploadStatus[] getAllUploadStatuses() {
        if (getState() == 0) {
            UploadStatus[] status = new UploadStatus[2];

            // modified in version 1.1 - because upload id needs to be grated then zero
            status[0] = new UploadStatus(1);
            status[0].setName("Active");

            status[1] = new UploadStatus(2);
            status[1].setName("Deleted");

            return status;
        }

        return null;
    }

    /**
     * Mock implementation.
     *
     * @param arg0 submission
     * @param arg1 operator
     */
    public void createSubmission(Submission arg0, String arg1) {
        arg0.setId(1001);
        createdSubmission = arg0;
        createdSubmissionUserId = arg1;
    }

    /**
     * Not implemented.
     *
     * @param arg0 submission
     * @param arg1 operator
     */
    public void updateSubmission(Submission arg0, String arg1) {
    }

    /**
     * Not implemented.
     *
     * @param arg0 submission
     * @param arg1 operator
     */
    public void removeSubmission(Submission arg0, String arg1) {
    }

    /**
     * Mock implementation.
     *
     * @param arg0 id
     *
     * @return submission
     */
    public Submission getSubmission(long arg0) {
        if (getState() == 0) {
            return submission;
        }

        return null;
    }

    /**
     * Mock implementation.
     *
     * @param arg0 filter
     *
     * @return submission array
     */
    public Submission[] searchSubmissions(Filter arg0) {
        Submission[] submissions = new Submission[1];
        submissions[0] = submission;

        return submissions;
    }

    /**
     * Not implemented.
     *
     * @param arg0 submission status
     * @param arg1 operator
     */
    public void createSubmissionStatus(SubmissionStatus arg0, String arg1) {
    }

    /**
     * Not implemented.
     *
     * @param arg0 submission status
     * @param arg1 operator
     */
    public void updateSubmissionStatus(SubmissionStatus arg0, String arg1) {
    }

    /**
     * Not implemented.
     *
     * @param arg0 submission status
     * @param arg1 operator
     */
    public void removeSubmissionStatus(SubmissionStatus arg0, String arg1) {
    }

    /**
     * Mock implementation.
     *
     * @return submission status
     */
    public SubmissionStatus[] getAllSubmissionStatuses() {
        if (getState() == 0) {
            SubmissionStatus[] status = new SubmissionStatus[2];
            status[0] = new SubmissionStatus(1);
            status[0].setName("Active");
            status[1] = new SubmissionStatus(2);
            status[1].setName("Deleted");

            return status;
        }

        return null;
    }

    /**
     * Not implemented.
     *
     * @param submissionType submission type
     * @param operator operator
     */
    public void createSubmissionType(SubmissionType submissionType, String operator) {
        // does nothing
    }

    /**
     * Not implemented.
     *
     * @param submissionType submission type
     * @param operator operator
     */
    public void updateSubmissionType(SubmissionType submissionType, String operator) {
        // does nothing
    }

    /**
     * Not implemented.
     *
     * @param submissionType submission type
     * @param operator operator
     */
    public void removeSubmissionType(SubmissionType submissionType, String operator) {
        // does nothing
    }

    /**
     * Mock implementation.
     *
     * @return array of submission types
     */
    public SubmissionType[] getAllSubmissionTypes() {
        if (getState() == 0) {
            SubmissionType[] status = new SubmissionType[2];
            status[0] = new SubmissionType(1);
            status[0].setName("Contest Submission");
            status[1] = new SubmissionType(2);
            status[1].setName("Specification Submission");

            return status;
        }

        return new SubmissionType[0];
    }

    /**
     * Sets the state.
     *
     * @param state the state to set
     */
    public static void setState(int state) {
        MockUploadManager.state = state;
    }

    /**
     * Gets the state.
     *
     * @return the state
     */
    static int getState() {
        return state;
    }

    /**
     * Gets the created upload.
     *
     * @return the created upload
     */
    Upload getCreatedUpload() {
        return createdUpload;
    }

    /**
     * Gets the created upload user id.
     *
     * @return the created user id
     */
    String getCreatedUploadUserId() {
        return createdUploadUserId;
    }

    /**
     * Gets the created submission.
     *
     * @return the created submission
     */
    Submission getCreatedSubmission() {
        return createdSubmission;
    }

    /**
     * Gets the created submission user id.
     *
     * @return the created submission user id
     */
    String getCreatedSubmissionUserId() {
        return createdSubmissionUserId;
    }

    /**
     * Gets the updated upload.
     *
     * @return the updated upload
     */
    public Upload getUpdatedUpload() {
        return updatedUpload;
    }

    /**
     * Sets the updated upload.
     *
     * @param updatedUpload the updated upload
     */
    public void setUpdatedUpload(Upload updatedUpload) {
        this.updatedUpload = updatedUpload;
    }
}
