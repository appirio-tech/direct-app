/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads.failuretests;

import com.cronos.onlinereview.services.uploads.TestHelper;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.SubmissionStatus;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.UploadManager;
import com.topcoder.management.deliverable.UploadStatus;
import com.topcoder.management.deliverable.UploadType;
import com.topcoder.management.deliverable.persistence.UploadPersistenceException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.filter.Filter;

/**
 * A mock implementation of UploadManager.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockUploadManager implements UploadManager {
    /**
     * A static state variable for the mock.
     */
    private static int state = 0;

    /**
     * A flag to indicate whether to throw error.
     */
    private static boolean throwError = false;

    /**
     * A flag to indicate whether to throw error on create upload.
     */
    private static boolean throwOnCreateError = false;

    /**
     * Will throw exception if the throwOnCreateError is set.
     *
     * @param arg0
     *            upload
     * @param arg1
     *            operator
     * @throws UploadPersistenceException
     *             if the flag is set
     */
    public void createUpload(Upload arg0, String arg1) throws UploadPersistenceException {
        if (isThrowOnCreateError()) {
            throw new UploadPersistenceException("Mock");
        }
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            upload
     * @param arg1
     *            operator
     * @throws UploadPersistenceException
     *             not thrown
     */
    public void updateUpload(Upload arg0, String arg1) throws UploadPersistenceException {
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            upload
     * @param arg1
     *            operator
     * @throws UploadPersistenceException
     *             not thrown
     */
    public void removeUpload(Upload arg0, String arg1) throws UploadPersistenceException {
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            upload
     * @return always null
     * @throws UploadPersistenceException
     *             not thrown
     */
    public Upload getUpload(long arg0) throws UploadPersistenceException {
        return null;
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            filter
     * @return always null
     * @throws UploadPersistenceException
     *             not thrown
     * @throws SearchBuilderException
     *             not thrown
     */
    public Upload[] searchUploads(Filter arg0) throws UploadPersistenceException, SearchBuilderException {
        return null;
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            upload type
     * @param arg1
     *            operator
     * @throws UploadPersistenceException
     *             not thrown
     */
    public void createUploadType(UploadType arg0, String arg1) throws UploadPersistenceException {
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            upload type
     * @param arg1
     *            operator
     * @throws UploadPersistenceException
     *             not thrown
     */
    public void updateUploadType(UploadType arg0, String arg1) throws UploadPersistenceException {
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            upload type
     * @param arg1
     *            operator
     * @throws UploadPersistenceException
     *             not thrown
     */
    public void removeUploadType(UploadType arg0, String arg1) throws UploadPersistenceException {
    }

    /**
     * Mock implementation.
     *
     * @return upload types
     * @throws UploadPersistenceException
     *             not thrown
     */
    public UploadType[] getAllUploadTypes() throws UploadPersistenceException {
        if (getState() == 0) {
            UploadType[] types = new UploadType[3];
            for (int i = 0; i < types.length; i++) {
                types[i] = new UploadType();
            }
            types[0].setName("Submission");
            types[1].setName("Review");
            types[2].setName("Final Fix");
            return types;
        }
        return null;
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            upload status
     * @param arg1
     *            operator
     * @throws UploadPersistenceException
     *             not thrown
     */
    public void createUploadStatus(UploadStatus arg0, String arg1) throws UploadPersistenceException {
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            upload status
     * @param arg1
     *            operator
     * @throws UploadPersistenceException
     *             not thrown
     */
    public void updateUploadStatus(UploadStatus arg0, String arg1) throws UploadPersistenceException {
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            upload status
     * @param arg1
     *            operator
     * @throws UploadPersistenceException
     *             not thrown
     */
    public void removeUploadStatus(UploadStatus arg0, String arg1) throws UploadPersistenceException {
    }

    /**
     * Mock implementation. Will throw exception if the throwError is set.
     *
     * @return upload status
     * @throws UploadPersistenceException
     *             will be thrown if the flag is set
     */
    public UploadStatus[] getAllUploadStatuses() throws UploadPersistenceException {
        if (isThrowError()) {
            throw new UploadPersistenceException("Mock");
        }

        if (getState() == 0) {
            UploadStatus[] status = new UploadStatus[1];
            status[0] = new UploadStatus();
            status[0].setName("Active");
            return status;
        }
        return null;
    }

    /**
     * Mock implementation.
     *
     * @param arg0
     *            submission
     * @param arg1
     *            operator
     * @throws UploadPersistenceException
     *             not thrown
     */
    public void createSubmission(Submission arg0, String arg1) throws UploadPersistenceException {
        arg0.setId(TestHelper.SUBMISSION_ID);
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            submission
     * @param arg1
     *            operator
     * @throws UploadPersistenceException
     *             not thrown
     */
    public void updateSubmission(Submission arg0, String arg1) throws UploadPersistenceException {
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            submission
     * @param arg1
     *            operator
     * @throws UploadPersistenceException
     *             not thrown
     */
    public void removeSubmission(Submission arg0, String arg1) throws UploadPersistenceException {
    }

    /**
     * Mock implementation.
     *
     * @param arg0
     *            id
     * @return submission
     * @throws UploadPersistenceException
     *             not thrown
     */
    public Submission getSubmission(long arg0) throws UploadPersistenceException {
        if (isThrowError()) {
            throw new UploadPersistenceException("Mock");
        }
        if (getState() == 0) {
            Submission submission = new Submission(arg0);
            return submission;
        }
        return null;
    }

    /**
     * Mock implementation.
     *
     * @param arg0
     *            filter
     * @return submission array
     * @throws UploadPersistenceException
     *             not thrown
     * @throws SearchBuilderException
     *             not thrown
     */
    public Submission[] searchSubmissions(Filter arg0) throws UploadPersistenceException, SearchBuilderException {
        if (getState() == 0) {
            Submission[] submissions = new Submission[1];
            submissions[0] = new Submission(10);
            return submissions;
        }
        return null;
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            submission status
     * @param arg1
     *            operator
     * @throws UploadPersistenceException
     *             not thrown
     */
    public void createSubmissionStatus(SubmissionStatus arg0, String arg1) throws UploadPersistenceException {
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            submission status
     * @param arg1
     *            operator
     * @throws UploadPersistenceException
     *             not thrown
     */
    public void updateSubmissionStatus(SubmissionStatus arg0, String arg1) throws UploadPersistenceException {
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            submission status
     * @param arg1
     *            operator
     * @throws UploadPersistenceException
     *             not thrown
     */
    public void removeSubmissionStatus(SubmissionStatus arg0, String arg1) throws UploadPersistenceException {
    }

    /**
     * Mock implementation.
     *
     * @return submission status
     * @throws UploadPersistenceException
     *             not thrown
     */
    public SubmissionStatus[] getAllSubmissionStatuses() throws UploadPersistenceException {
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
     * Sets the state.
     *
     * @param state
     *            the state to set
     */
    static void setState(int state) {
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
     * Sets the throwError.
     *
     * @param throwError
     *            the throwError to set
     */
    static void setThrowError(boolean throwError) {
        MockUploadManager.throwError = throwError;
    }

    /**
     * Gets the throwError.
     *
     * @return the throwError
     */
    static boolean isThrowError() {
        return throwError;
    }

    /**
     * Sets the throwOnCreateError.
     *
     * @param throwOnCreateError
     *            the throwOnCreateError to set
     */
    static void setThrowOnCreateError(boolean throwOnCreateError) {
        MockUploadManager.throwOnCreateError = throwOnCreateError;
    }

    /**
     * Gets the throwOnCreateError.
     *
     * @return the throwOnCreateError
     */
    static boolean isThrowOnCreateError() {
        return throwOnCreateError;
    }

}
