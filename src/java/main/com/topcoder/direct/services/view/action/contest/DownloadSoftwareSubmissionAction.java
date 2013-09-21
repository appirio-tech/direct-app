/*
 * Copyright (C) 2011 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.contest.ContestType;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.resource.Resource;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.servlet.request.FileUpload;
import com.topcoder.servlet.request.UploadedFile;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * <p>
 * This struts action is used to download software submission.
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TC Cockpit Misc Bug Fixes)
 * <ul>
 *     <li>Updated {@link #getInputStream()} to implement TCCC-5110 to prefix copilot handle each file in the
 *     copilot posting submission. So reviewer can open multiple spreadsheets with different names.
 *     </li>
 *     <li>Updated {@link #getContentDisposition()} to prefix copilot handle to the copilot posting submission name</li>
 * </ul>
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.1 (Release Assembly - TC Cockpit Misc Bug Fixes)
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
     * The SoftwareCompetition instance representing the contest the submission is downloaded from.
     *
     * @since 1.1
     */
    private SoftwareCompetition contest;

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

        contest = getContestServiceFacade().getSoftwareContestByProjectId(getCurrentUser(), projectId);

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

        if(contest.getProjectHeader().getProjectCategory().getId() == ContestType.COPILOT_POSTING.getId()) {
            // it's copilot posting, append user handle to each file in the copilot posting submission
            Resource[] resources = contest.getResources();
            long userId = 0;
            for(Resource r : resources) {
                if(r.getId() == submission.getUpload().getOwner()) {
                    userId = Long.parseLong(r.getProperty("External Reference ID"));
                    break;
                }
            }

            return prefixHandleToSubmissionFile(uploadedFile, userId);
        } else {
            return uploadedFile.getInputStream();
        }
    }

    /**
     * Gets the content disposition of the uploaded file.
     * 
     * @return the content disposition of the upload file.
     * @throws Exception
     *             if any error occurs when getting the file name of the uploaded file.
     */
    public String getContentDisposition() throws Exception {

        if (contest.getProjectHeader().getProjectCategory().getId() == ContestType.COPILOT_POSTING.getId()) {
            // it's copilot posting, append user handle to each file in the copilot posting submission
            Resource[] resources = contest.getResources();
            long userId = 0;
            for (Resource r : resources) {
                if (r.getId() == submission.getUpload().getOwner()) {
                    userId = Long.parseLong(r.getProperty("External Reference ID"));
                    break;
                }
            }

            return "attachment; filename=\"submission-" + getUserService().getUserHandle(userId) + "-" +
                    uploadedFile.getRemoteFileName()
                    + "\"";
        } else {
            return "attachment; filename=\"submission-" + submission.getId() + "-" + uploadedFile.getRemoteFileName()
                    + "\"";
        }
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
     * @param submissionId
     *            the submission id the user want to download.
     */
    public void setSubmissionId(long submissionId) {
        this.submissionId = submissionId;
    }

    /** Gets the project id of the upload.
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

    /**
     * Helper method to prefix the user handle to each file in a submission archive for copilot posting submission.
     * It assumes all copilot posting submissions in a zip archive.
     *
     * @param submissionFile the submission file representing by a <code>UploadedFile</code> instance
     * @param userId the user id
     * @return the input stream of the new zip
     * @throws Exception if any error
     * @since 1.1
     */
    private InputStream prefixHandleToSubmissionFile(UploadedFile submissionFile, long userId) throws Exception {
        return DirectUtils.appendStringToFilesInZip(submissionFile, getUserService().getUserHandle(userId));
    }
}
