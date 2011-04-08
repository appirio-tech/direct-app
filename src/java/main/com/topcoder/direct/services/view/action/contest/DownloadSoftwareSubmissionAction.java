/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest;

import java.io.InputStream;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.servlet.request.FileUpload;
import com.topcoder.servlet.request.UploadedFile;

/**
 * <p>
 * This struts action is used to download software submission.
 * </p>
 * 
 * @author TCSASSEMBER
 * @version 1.0
 * @since TCCC-2802
 */
public class DownloadSoftwareSubmissionAction extends BaseDirectStrutsAction {
    /**
     * Represents the serial verison unique id.
     */
    private static final long serialVersionUID = 1870450815370143413L;

    /**
     * Represents the submission id the user want to download.
     */
    private long submissionId;

    /**
     * Represents the project id of the submission. <code>ProjectAccessInterceptor</code> will check whether the user
     * have permission to download this submission.
     */
    private long projectId;

    /**
     * Represents the submission the user want to download.
     */
    private Submission submission;

    /**
     * Represents the uploaded file the user want to download.
     */
    private UploadedFile uploadedFile;

    /**
     * Represents the <code>FileUpload</code> service. It will be injected by Spring.
     */
    private FileUpload fileUpload;

    /**
     * <p>
     * Executes the action. It will get the uploaded file the user want to download.
     * </p>
     * 
     * @throws Exception
     *             is any error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        // get the submission of the project
        Submission[] submissions = getContestServiceFacade().getSoftwareProjectSubmissions(getCurrentUser(), projectId);
        // check whether the project contains the submission the user want to download
        for (Submission sub : submissions) {
            if (sub.getUpload() != null && sub.getId() == submissionId) {
                submission = sub;
                break;
            }
        }

        if (submission == null) {
            // the user can't download the upload which is not belongs to the project
            throw new Exception("Cannot find submission " + submissionId + " in project " + projectId);
        }

        uploadedFile = fileUpload.getUploadedFile(submission.getUpload().getParameter());
    }

    /**
     * Gets the <code>InputStream</code> of the download.
     * 
     * @return the <code>InputStream</code> of the download.
     * @throws Exception
     *             if any error occurs when getting the input stream of the uploaded file.
     */
    public InputStream getInputStream() throws Exception {
        return uploadedFile.getInputStream();
    }

    /**
     * Gets the content disposition of the uploaded file.
     * 
     * @return the content disposition of the upload file.
     * @throws Exception
     *             if any error occurs when getting the file name of the uploaded file.
     */
    public String getContentDisposition() throws Exception {
        return "attachment; filename=\"submission-" + submission.getId() + "-" + uploadedFile.getRemoteFileName()
                + "\"";
    }

    /**
     * Gets the submission id the user want to download.
     * 
     * @return the submission id the user want to download.
     */
    public long getSubmissionId() {
        return submissionId;
    }

    /**
     * Sets the submission id the user want to download.
     * 
     * @param uploadId
     *            the submission id the user want to download.
     */
    public void setSubmissionId(long submissionId) {
        this.submissionId = submissionId;
    }

    /**
     * Gets the project id of the upload.
     * 
     * @return the project id of the upload.
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Sets the project id of the upload.
     * 
     * @param projectId
     *            the project id of the upload.
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * Gets the <code>FileUpload</code> service.
     * 
     * @return the <code>FileUpload</code> service.
     */
    public FileUpload getFileUpload() {
        return fileUpload;
    }

    /**
     * Sets the <code>FileUpload</code> service.
     * 
     * @param fileUpload
     *            the <code>FileUpload</code> service.
     */
    public void setFileUpload(FileUpload fileUpload) {
        this.fileUpload = fileUpload;
    }
}
