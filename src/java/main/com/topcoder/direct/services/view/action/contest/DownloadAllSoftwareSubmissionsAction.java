/*
 * Copyright (C) 2012 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest;

import com.topcoder.direct.services.view.action.contest.launch.ContestAction;
import com.topcoder.direct.services.view.dto.contest.ContestRoundType;
import com.topcoder.direct.services.view.dto.contest.ContestType;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.servlet.request.FileUpload;
import com.topcoder.servlet.request.UploadedFile;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * <p>
 * This struts action is used to download all software checkpoint submissions for the checkpoint round or final
 * round of the software contest.
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TopCoder Cockpit - Software Contest Download All)
 * <ol>
 *  <li>Update the action class to new name 'DownloadAllSoftwareSubmissionsAction'</li>
 *  <li>Update the action to support download all for checkpoint and final round of software contest./li>
 *  <li>Add special handle for the copilot posting contest</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.2 (Release Assembly - TC Cockpit Misc Bug Fixes)
 * <ul>
 *     <li>Updated {@link #getInputStream()} to implement TCCC-5110 to prefix copilot handle to each copilot posting
 *     submissions and each file in the copilot posting submission. So reviewer can open multiple spreadsheets with
 *     different names.
 *     </li>
 * </ul>
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.2
 */
public class DownloadAllSoftwareSubmissionsAction extends ContestAction {

    /**
     * The id of the final submission type.
     *
     * @since 1.1
     */
    private static final long FINAL_SUBMISSION_TYPE_ID = 1L;

    /**
     * The id of the checkpoint submission type.
     *
     * @since 1.1
     */
    private static final long CHECKPOINT_SUBMISSION_TYPE_ID = 3L;

    /**
     * The id of the upload type - submission.
     *
     * @since 1.1
     */
    private static final long SUBMISSION_UPLOAD_TYPE_ID = 1L;

    /**
     * The id of the upload status - active.
     *
     * @since 1.1
     */
    private static final long UPLOAD_ACTIVE_STATUS_ID = 1L;

    /**
     * The id of the submission status - active.
     *
     * @since 1.1
     */
    private static final long ACTIVE_SUBMISSION_STATUS_ID = 1L;

    /**
     * The id of the submission status - completed without win.
     *
     * @since 1.1
     */
    private static final long COMPLETED_WITHOUT_WIN_SUBMISSION_STATUS_ID = 4L;

    /**
     * Suffix for single copilot posting submission inside the copilot posting all.
     *
     * @since 1.1
     */
    private static final String COPILOT_POSTING_SUBMISSION = "-Copilot Posting Submission";

    /**
     * Suffix name for download all zip of checkpoint round submissions.
     *
     * @since 1.1
     */
    private static final String CHECKPOINT_SUBMISSIONS = "All_Checkpoint_Submissions.zip";

    /**
     * Suffix name for download all zip of final round submissions.
     *
     * @since 1.1
     */
    private static final String FINAL_SUBMISSIONS = "All_Final_Submissions.zip";

    /**
     * Suffix name for download all zip of single round submissions.
     *
     * @since 1.1
     */
    private static final String ALL_SUBMISSIONS = "All_Submissions.zip";

    /**
     * Represents the upload parameters which are used to retrieve the uploaded files.
     */
    private List<Submission> submissionsToDownload;

    /**
     * Represents the <code>FileUpload</code> service. It will be injected by Spring.
     */
    private FileUpload fileUpload;


    /**
     * The round type of the software contest.
     *
     * @since 1.1
     */
    private ContestRoundType roundType;

    /**
     * Whether the contest has checkpoint round.
     *
     * @since 1.1
     */
    private boolean hasCheckpointRound;

    /**
     * Whether the contest is of type "Copilot Posting"
     *
     * @since 1.1
     */
    private boolean isCopilotPosting;

    /**
     * The software competition contest presenting the contest this action deals with.
     *
     * @since 1.1
     */
    private SoftwareCompetition contest;

    /**
     * <p>
     * Creates a <code>DownloadAllSoftwareSubmissionsAction</code> instance.
     * </p>
     */
    public DownloadAllSoftwareSubmissionsAction() {

    }

    /**
     * <p>
     * Executes the action. It will get the uploaded files.
     * </p>
     *
     * <p>
     * Update in version 1.1 (Release Assembly - TopCoder Cockpit - Software Contest Download All)
     * - remove using DataProvider.setSoftwareCheckpointSubmissionsData(dto) to get checkpoint submission ids. The method
     * gets a lot of unneeded data. Instead, it checks the submission and upload to find out submissions of different
     * types.
     * - Add support for checkpoint and final round.
     * </p>
     * 
     * @throws Exception if any error occurred
     */
    @Override
    protected void executeAction() throws Exception {
        // get the submissions of the project
        Submission[] submissions = getContestServiceFacade().getSoftwareProjectSubmissions(
                getCurrentUser(), getProjectId());
        contest = getContestServiceFacade().getSoftwareContestByProjectId(getCurrentUser(), getProjectId());

        hasCheckpointRound = DirectUtils.isMultiRound(contest);

        if(hasCheckpointRound == false && roundType == ContestRoundType.CHECKPOINT) {
            throw new IllegalArgumentException("The contest does not have checkpoint round");
        }

        // check if the contest is copilot posting
        isCopilotPosting = contest.getProjectHeader().getProjectCategory().getId() == ContestType.COPILOT_POSTING.getId();

        submissionsToDownload = new ArrayList<Submission>();
        for (Submission sub : submissions) {
            final Upload upload = sub.getUpload();

            if(isSubmissionToInclude(sub)) {
                submissionsToDownload.add(sub);
            }
        }
    }

    /**
     * Helper methods to check whether the specified submission should be included in the download.
     *
     * @param sub the <code>Submission</code> instance to check
     * @return true if the submission should be included, false otherwise.
     *
     * @since 1.1
     */
    private boolean isSubmissionToInclude(Submission sub) {
        // check the round type to use different submission type id
        long submissionTypeId =
                (roundType == ContestRoundType.CHECKPOINT ? CHECKPOINT_SUBMISSION_TYPE_ID : FINAL_SUBMISSION_TYPE_ID);
        final Upload upload = sub.getUpload();

        // Checks
        // 1) the active upload with type 'submission'
        // 2) the submission should be either 'active' or 'completed without win'
        // 3) the submission type matches the round type
        if(upload.getUploadStatus().getId() == UPLOAD_ACTIVE_STATUS_ID
                && upload.getUploadType().getId() == SUBMISSION_UPLOAD_TYPE_ID
                && sub.getSubmissionType().getId() == submissionTypeId
                && (sub.getSubmissionStatus().getId() == ACTIVE_SUBMISSION_STATUS_ID
                || sub.getSubmissionStatus().getId() == COMPLETED_WITHOUT_WIN_SUBMISSION_STATUS_ID)) {
            return true;
        }

        return false;
    }

    /**
     * Gets the <code>InputStream</code> of the download.
     *
     * <p>
     * Update in version 1.1 (Release Assembly - TopCoder Cockpit - Software Contest Download All)
     * - Adds special handle for copilot posting
     * </p>
     * 
     * @return the <code>InputStream</code> of the download
     * @throws Exception if any error occurred
     */
    public InputStream getInputStream() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        final ZipOutputStream zos = new ZipOutputStream(out);
        new Thread(new Runnable(){
            public void run() {
                byte[] buffer = new byte[8192];
                int read;
                InputStream is = null;
                try {
                    for (Submission sub : submissionsToDownload) {
                        UploadedFile file = fileUpload.getUploadedFile(sub.getUpload().getParameter());
                        is = file.getInputStream();
                        String submissionFileZipName;

                        if(isCopilotPosting) {
                            // special handling for the copilot posting submission, prefix the submitter's handle
                            final String copilotHandle = getUserService().getUserHandle(Long.parseLong(sub.getUpload().getCreationUser()));
                            String ext = FilenameUtils.getExtension(file.getRemoteFileName());
                            if(ext != null && ext.trim().length() > 0) {
                                ext = "." + ext;
                            } else {
                                ext = "";
                            }
                            submissionFileZipName = copilotHandle + COPILOT_POSTING_SUBMISSION
                                    + ext;

                            is = DirectUtils.appendStringToFilesInZip(file, copilotHandle);
                        } else {
                            submissionFileZipName = "Submission-" + sub.getId() + "-" + file.getRemoteFileName();
                        }

                        // create an entry for each file
                        ZipEntry outputEntry = new ZipEntry(submissionFileZipName);

                        zos.putNextEntry(outputEntry);
                        while ((read = is.read(buffer)) != -1) {
                            zos.write(buffer, 0, read);
                        }
                        zos.closeEntry(); 
                        is.close();
                    }
                    zos.finish();
                } catch (Exception e) {
                    if (is != null) {
                        try {
                            is.close();                            
                        } catch (Exception ex) {
                            // ignore
                        }
                    }
                }
                try {
                    zos.close();
                } catch (Exception e) {
                    // ignore
                }
            }
        }).start();        
        return in;
    }

    /**
     * Gets the content disposition of the zip file to download.
     *
     * <p>
     * Update in version 1.1 (Release Assembly - TopCoder Cockpit - Software Contest Download All)
     *
     * </p>
     * 
     * @return the content disposition of the zip file to download
     */
    public String getContentDisposition() {

        if(isCopilotPosting) {
            return "attachment; filename=\"Copilot Posting_" + getProjectId() + "_All_Submissions.zip\"";
        } else {
            String template = "attachment; filename=\"" + contest.getProjectHeader().getProjectCategory().getName()
                    + "_" + getProjectId() + "_%s\"";

            if(hasCheckpointRound) {
                if(roundType == ContestRoundType.CHECKPOINT) {
                    return String.format(template, CHECKPOINT_SUBMISSIONS);
                } else {
                    return String.format(template, FINAL_SUBMISSIONS);
                }
            } else {
                return String.format(template, ALL_SUBMISSIONS);
            }
        }
    }

    /**
     * Gets the <code>FileUpload</code> service.
     * 
     * @return the <code>FileUpload</code> service
     */
    public FileUpload getFileUpload() {
        return fileUpload;
    }

    /**
     * Sets the <code>FileUpload</code> service.
     * 
     * @param fileUpload the <code>FileUpload</code> service
     */
    public void setFileUpload(FileUpload fileUpload) {
        this.fileUpload = fileUpload;
    }

    /**
     * Sets the round type.
     *
     * @param roundType the contest round type.
     *
     * @since 1.1
     */
    public void setRoundType(ContestRoundType roundType) {
        this.roundType = roundType;
    }
}
