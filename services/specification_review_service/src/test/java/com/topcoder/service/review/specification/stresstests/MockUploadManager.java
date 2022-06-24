/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.specification.stresstests;

import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.SubmissionStatus;
import com.topcoder.management.deliverable.SubmissionType;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.UploadManager;
import com.topcoder.management.deliverable.UploadStatus;
import com.topcoder.management.deliverable.UploadType;
import com.topcoder.management.deliverable.persistence.UploadPersistenceException;

import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.filter.Filter;


/**
 * Mock implementation of UploadManager.
 *
 * @author onsky
 * @version 1.0
 */
public class MockUploadManager implements UploadManager {
    /**
     * Mock!
     *
     * @param arg0 Mock!
     * @param arg1 Mock!
     *
     * @throws UploadPersistenceException Mock!
     */
    public void createSubmission(Submission arg0, String arg1)
        throws UploadPersistenceException {
    }

    /**
     * Mock!
     *
     * @param arg0 Mock!
     * @param arg1 Mock!
     *
     * @throws UploadPersistenceException Mock!
     */
    public void createSubmissionStatus(SubmissionStatus arg0, String arg1)
        throws UploadPersistenceException {
    }

    /**
     * Mock!
     *
     * @param arg0 Mock!
     * @param arg1 Mock!
     *
     * @throws UploadPersistenceException Mock!
     */
    public void createSubmissionType(SubmissionType arg0, String arg1)
        throws UploadPersistenceException {
    }

    /**
     * Mock!
     *
     * @param arg0 Mock!
     * @param arg1 Mock!
     *
     * @throws UploadPersistenceException Mock!
     */
    public void createUpload(Upload arg0, String arg1)
        throws UploadPersistenceException {
    }

    /**
     * Mock!
     *
     * @param arg0 Mock!
     * @param arg1 Mock!
     *
     * @throws UploadPersistenceException Mock!
     */
    public void createUploadStatus(UploadStatus arg0, String arg1)
        throws UploadPersistenceException {
    }

    /**
     * Mock!
     *
     * @param arg0 Mock!
     * @param arg1 Mock!
     *
     * @throws UploadPersistenceException Mock!
     */
    public void createUploadType(UploadType arg0, String arg1)
        throws UploadPersistenceException {
    }

    /**
     * Mock!
     *
     * @return Mock!
     *
     * @throws UploadPersistenceException Mock!
     */
    public SubmissionStatus[] getAllSubmissionStatuses()
        throws UploadPersistenceException {
        return null;
    }

    /**
     * Mock!
     *
     * @return Mock!
     *
     * @throws UploadPersistenceException Mock!
     */
    public SubmissionType[] getAllSubmissionTypes() throws UploadPersistenceException {
        return null;
    }

    /**
     * Mock!
     *
     * @return Mock!
     *
     * @throws UploadPersistenceException Mock!
     */
    public UploadStatus[] getAllUploadStatuses() throws UploadPersistenceException {
        return null;
    }

    /**
     * Mock!
     *
     * @return Mock!
     *
     * @throws UploadPersistenceException Mock!
     */
    public UploadType[] getAllUploadTypes() throws UploadPersistenceException {
        return null;
    }

    /**
     * Mock!
     *
     * @param arg0 Mock!
     *
     * @return Mock!
     *
     * @throws UploadPersistenceException Mock!
     */
    public Submission getSubmission(long arg0) throws UploadPersistenceException {
        return null;
    }

    /**
     * Mock!
     *
     * @param arg0 Mock!
     *
     * @return Mock!
     *
     * @throws UploadPersistenceException Mock!
     */
    public Upload getUpload(long arg0) throws UploadPersistenceException {
        return null;
    }

    /**
     * Mock!
     *
     * @param arg0 Mock!
     * @param arg1 Mock!
     *
     * @throws UploadPersistenceException Mock!
     */
    public void removeSubmission(Submission arg0, String arg1)
        throws UploadPersistenceException {
    }

    /**
     * Mock!
     *
     * @param arg0 Mock!
     * @param arg1 Mock!
     *
     * @throws UploadPersistenceException Mock!
     */
    public void removeSubmissionStatus(SubmissionStatus arg0, String arg1)
        throws UploadPersistenceException {
    }

    /**
     * Mock!
     *
     * @param arg0 Mock!
     * @param arg1 Mock!
     *
     * @throws UploadPersistenceException Mock!
     */
    public void removeSubmissionType(SubmissionType arg0, String arg1)
        throws UploadPersistenceException {
    }

    /**
     * Mock!
     *
     * @param arg0 Mock!
     * @param arg1 Mock!
     *
     * @throws UploadPersistenceException Mock!
     */
    public void removeUpload(Upload arg0, String arg1)
        throws UploadPersistenceException {
    }

    /**
     * Mock!
     *
     * @param arg0 Mock!
     * @param arg1 Mock!
     *
     * @throws UploadPersistenceException Mock!
     */
    public void removeUploadStatus(UploadStatus arg0, String arg1)
        throws UploadPersistenceException {
    }

    /**
     * Mock!
     *
     * @param arg0 Mock!
     * @param arg1 Mock!
     *
     * @throws UploadPersistenceException Mock!
     */
    public void removeUploadType(UploadType arg0, String arg1)
        throws UploadPersistenceException {
    }

    /**
     * Mock!
     *
     * @param arg0 Mock!
     *
     * @return Mock!
     *
     * @throws UploadPersistenceException Mock!
     * @throws SearchBuilderException Mock!
     */
    public Submission[] searchSubmissions(Filter arg0)
        throws UploadPersistenceException, SearchBuilderException {
        return null;
    }

    /**
     * Mock!
     *
     * @param arg0 Mock!
     *
     * @return Mock!
     *
     * @throws UploadPersistenceException Mock!
     * @throws SearchBuilderException Mock!
     */
    public Upload[] searchUploads(Filter arg0) throws UploadPersistenceException, SearchBuilderException {
        return null;
    }

    /**
     * Mock!
     *
     * @param arg0 Mock!
     * @param arg1 Mock!
     *
     * @throws UploadPersistenceException Mock!
     */
    public void updateSubmission(Submission arg0, String arg1)
        throws UploadPersistenceException {
    }

    /**
     * Mock!
     *
     * @param arg0 Mock!
     * @param arg1 Mock!
     *
     * @throws UploadPersistenceException Mock!
     */
    public void updateSubmissionStatus(SubmissionStatus arg0, String arg1)
        throws UploadPersistenceException {
    }

    /**
     * Mock!
     *
     * @param arg0 Mock!
     * @param arg1 Mock!
     *
     * @throws UploadPersistenceException Mock!
     */
    public void updateSubmissionType(SubmissionType arg0, String arg1)
        throws UploadPersistenceException {
    }

    /**
     * Mock!
     *
     * @param arg0 Mock!
     * @param arg1 Mock!
     *
     * @throws UploadPersistenceException Mock!
     */
    public void updateUpload(Upload arg0, String arg1)
        throws UploadPersistenceException {
    }

    /**
     * Mock!
     *
     * @param arg0 Mock!
     * @param arg1 Mock!
     *
     * @throws UploadPersistenceException Mock!
     */
    public void updateUploadStatus(UploadStatus arg0, String arg1)
        throws UploadPersistenceException {
    }

    /**
     * Mock!
     *
     * @param arg0 Mock!
     * @param arg1 Mock!
     *
     * @throws UploadPersistenceException Mock!
     */
    public void updateUploadType(UploadType arg0, String arg1)
        throws UploadPersistenceException {
    }
}
