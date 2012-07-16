/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.topcoder.direct.services.view.action.contest.launch.ContestAction;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.servlet.request.FileUpload;
import com.topcoder.servlet.request.UploadedFile;
import com.topcoder.shared.util.logging.Logger;

/**
 * <p>
 * An action to be used for servicing the requests for exporting contests
 * results.
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0 (Release Assembly - TopCoder Cockpit Project Contest Results
 *          Export Part 1)
 */
public class ProjectContestsResultsExportAction extends ContestAction {

    /**
     * <p>
     * Represents the string constant for submission.
     * </p>
     */
    private static final String SUBMISSION = "Submission";

    /**
     * <p>
     * Represents the string constant for second place prefix.
     * </p>
     */
    private static final String SECOND_PLACE_PREFIX = "SecondPlace.";

    /**
     * <p>
     * Represents the string constant for first place prefix.
     * </p>
     */
    private static final String FIRST_PLACE_PREFIX = "FirstPlace.";

    /**
     * <p>
     * Represents the string constant for contests.
     * </p>
     */
    private static final String CONTESTS = "Contests";

    /**
     * <p>
     * Represents the unique serial version id.
     * </p>
     */
    private static final long serialVersionUID = 3320767591427458154L;

    /**
     * <p>
     * A <code>Logger</code> to be used for logging the events encountered while
     * processing the requests.
     * </p>
     */
    private static final Logger log = Logger.getLogger(ProjectContestsResultsExportAction.class);

    /**
     * <p>
     * Represents the start date of the exported contests. It is optional.
     * </p>
     */
    private Date startDate;

    /**
     * <p>
     * Represents the end date of the exported contests. It is optional.
     * </p>
     */
    private Date endDate;

    /**
     * <p>A <code>String</code> providing the URL for Login controller for Online Review application.</p>
     */
    private String loginUrl;

    /**
     * <p>A <code>String</code> providing the username to login to Online Review application.</p>
     */
    private String username;

    /**
     * <p>A <code>String</code> providing the password to login to Online Review application.</p>
     */
    private String password;

    /**
     * <p>A <code>String</code> providing the base URL for Online Review application.</p>
     */
    private String onlineReviewBaseURL;

    /**
     * <p>A <code>String</code> providing the base for Online Review application controller for getting the composite
     * review scorecard.</p>
     */
    private String compositeReviewBaseURL;

    /**
     * <p>
     * The file upload tool to access the submissions.
     * </p>
     */
    private FileUpload fileUpload;

    /**
     * The contest ids.
     */
    private Map<Long, Boolean> contestIds;

    /**
     * The first and second place (if exists) submissions.
     */
    private Map<Long, List<Submission>> submissions;

    /**
     * The studio submission download base.
     */
    private String studioSubmissionBase;

    /**
     * <p>
     * This is the template method where the action logic will be performed by
     * children classes.
     * </p>
     * 
     * @throws Exception
     *             if any error occurs
     */
    @Override
    protected void executeAction() throws Exception {

        log.info("Starting the contests results exporting for project : " + getProjectId() + " from :" + startDate
                + " to : " + endDate);

        // project id is required, it won't be zero if it is presented in the
        // request
        if (getProjectId() == 0) {
            addActionError("projectId is not set");
            return;
        }

        try {

            // set the start date to 1970/01/01 if it is not presented
            if (startDate == null) {
                startDate = new Date(0);
            }
            // set the end date to the maximum value if it not presented
            if (endDate == null) {
                Calendar c = Calendar.getInstance();
                c.set(9999, 11, 30);
                endDate = c.getTime();
            }

            // retrieve all ids of the contests to download
            contestIds = DataProvider.getContestIdsToExport(getProjectId(), getCurrentUser().getUserId(), startDate,
                    endDate);
            if (contestIds == null || contestIds.size() == 0) {
                return;
            }

            submissions = new HashMap<Long, List<Submission>>();

            // get the first and second place submission
            for (Map.Entry<Long, Boolean> entry : contestIds.entrySet()) {

                Long contestId = entry.getKey();
                Boolean isStudio = entry.getValue();

                // get all the submissions for this contest, the map is sorted
                // ASC (0th element is first place, 1st element is second place)
                List<Submission> ss = DataProvider.getContesSubmissionIdsToExport(contestId, isStudio);

                if (ss.size() == 0) {
                    // escape this contest if it does not have any submissions
                    // to export (this is unlikely to happen)
                    continue;
                }

                List<Submission> ids = new ArrayList<Submission>();
                submissions.put(contestId, ids);

                for (Submission submission : ss) {
                    ids.add(submission);
                }
            }
        } catch (Exception e) {
            DirectUtils.getServletResponse().setStatus(500);
            log.error("Got an error during exporting", e);
        } finally {
            log.info("Finished the contests result exporting");
        }
    }

    /**
     * <p>
     * Checks whether the required component is injected.
     * </p>
     * 
     * @throws Exception
     *             if the file upload is not injected
     */
    public void checkInit() throws Exception {
        if (fileUpload == null) {
            log.error("fileUpload is not injected");
            throw new IllegalStateException("fileUpload is not injected");
        }
        if (studioSubmissionBase == null) {
            log.error("studioSubmissionBase is not injected");
            throw new IllegalStateException("studioSubmissionURL is not injected");
        }
    }

    /**
     * <p>
     * Adds submission to the zip stream.
     * </p>
     * 
     * @param zos
     *            the zip stream
     * @param contestId
     *            the contest id
     * @param submission
     *            the submission
     * @param isFirstPlace
     *            whether this submission is first place
     * @param isStudio
     *            whether this is studio contest
     * @throws Exception
     *             if any error happens
     */
    private void addSubmissionToZip(ZipOutputStream zos, long contestId, Submission submission, boolean isFirstPlace,
            boolean isStudio) throws Exception {
        byte[] buffer = new byte[8192];
        InputStream is = null;
        int read;
        InputStream pdfStream = null;
        try {

            StringBuilder sb = new StringBuilder(CONTESTS).append(File.separator).append(Long.toString(contestId))
                    .append(File.separator);
            sb.append(isFirstPlace ? FIRST_PLACE_PREFIX : SECOND_PLACE_PREFIX).append(submission.getId())
                    .append(File.separator).append(SUBMISSION).append(File.separator);

            if (!isStudio) {
                UploadedFile file = fileUpload.getUploadedFile(submission.getUpload().getParameter());
                sb.append(file.getRemoteFileName());
                is = file.getInputStream();
            } else {
                File file = new File(studioSubmissionBase + File.separator + contestId + File.separator
                        + submission.getCreationUser().toLowerCase() + "_" + submission.getUpload().getOwner() + File.separator
                        + submission.getUpload().getParameter());
                is = new FileInputStream(file);
                sb.append(submission.getUpload().getParameter());
            }

            // write the file into the zip stream
            ZipEntry outputEntry = new ZipEntry(sb.toString());
            zos.putNextEntry(outputEntry);
            while ((read = is.read(buffer)) != -1) {
                zos.write(buffer, 0, read);
            }
            zos.closeEntry();

            // write the scorecards
            pdfStream = DataProvider.generateCombinedReviewScorecard(getLoginUrl(), getUsername(), getPassword(), 
                    getCompositeReviewBaseURL(), getCompositeReviewBaseURL(), 
                    submission.getId());
            StringBuilder sbPdf = new StringBuilder(CONTESTS).append(File.separator).append(Long.toString(contestId))
                    .append(File.separator).append(isFirstPlace ? FIRST_PLACE_PREFIX : SECOND_PLACE_PREFIX).append(submission.getId())
                    .append(File.separator).append("Scorecard.pdf");
            ZipEntry outputEntryPdf = new ZipEntry(sbPdf.toString());
            zos.putNextEntry(outputEntryPdf);
            while ((read = pdfStream.read(buffer)) != -1) {
                zos.write(buffer, 0, read);
            }
            zos.closeEntry();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    log.error("Got an error during exporting", e);
                }
            }
            if (pdfStream != null) {
                try {
                    pdfStream.close();
                } catch (Exception e) {
                    log.error("Got an error during exporting", e);
                }
            }
        }
    }

    /**
     * Gets the <code>InputStream</code> of the download.
     * 
     * @return the <code>InputStream</code> of the download.
     * @throws Exception
     *             if any error occurs when getting the input stream of the
     *             uploaded file.
     */
    public InputStream getInputStream() throws Exception {

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        final ZipOutputStream zos = new ZipOutputStream(out);
        final ProjectContestsResultsExportAction self = this;

        // always adds the parent entry - have to use this, instead of
        // File.separator, because
        // on windows, separator will create an empty file inside the package
        // zip file format follows the *nix
        ZipEntry outputEntry = new ZipEntry(CONTESTS + "/");
        zos.putNextEntry(outputEntry);
        zos.closeEntry();

        new Thread(new Runnable() {
            public void run() {
                InputStream is = null;
                try {
                    byte[] buffer = new byte[8192];
                    int read;

                    // get the Results and Winners
                    ZipEntry outputEntry = new ZipEntry(CONTESTS + File.separator + "Winners." + new SimpleDateFormat("MM-dd-yy").format(new Date()) + ".xlsx");
                    zos.putNextEntry(outputEntry);
                    is = DataProvider.generateWinnerSheet(getProjectId());
                    while ((read = is.read(buffer)) != -1) {
                        zos.write(buffer, 0, read);
                    }
                    zos.closeEntry();
                    
                    for (Map.Entry<Long, Boolean> entry : contestIds.entrySet()) {
                        long contestId = entry.getKey();
                        boolean isStudio = entry.getValue();

                        List<Submission> ss = submissions.get(contestId);

                        self.addSubmissionToZip(zos, contestId, ss.get(0), true, isStudio);

                        // deal with the second place submission
                        if (ss.size() > 1) {
                            self.addSubmissionToZip(zos, contestId, ss.get(1), false, isStudio);
                        }
                    }
                    zos.finish();
                } catch (Exception e) {
                    log.error("Got an error during exporting", e);
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (Exception e) {
                            // ignore
                        }
                    }
                    try {
                        zos.close();
                    } catch (Exception e) {
                        log.error("Got an error during exporting", e);
                    }
                }
            }
        }).start();
        return in;
    }

    /**
     * Gets the content disposition of the uploaded file.
     * 
     * @return the content disposition of the upload file.
     * @throws Exception
     *             if any error occurs when getting the file name of the
     *             uploaded file.
     */
    public String getContentDisposition() throws Exception {
        return "attachment; filename=\"COE.Results." + new SimpleDateFormat("MM-dd-yy").format(new Date()) + ".zip\"";
    }

    /**
     * <p>
     * Gets the file upload tool.
     * </p>
     * 
     * @return the file upload
     */
    public FileUpload getFileUpload() {
        return fileUpload;
    }

    /**
     * <p>
     * Sets the file upload tool.
     * </p>
     * 
     * @param fileUpload
     *            the file upload to set
     */
    public void setFileUpload(FileUpload fileUpload) {
        this.fileUpload = fileUpload;
    }

    /**
     * <p>
     * Gets the start date of exported contests.
     * </p>
     * 
     * @return the start date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * <p>
     * Sets the start date of the exported contests.
     * </p>
     * 
     * @param startDate
     *            the start date to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * <p>
     * Gets the end date of the exported contests.
     * </p>
     * 
     * @return the end date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * <p>
     * Sets the end date of the exported contests.
     * </p>
     * 
     * @param endDate
     *            the end date to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the studio submission base.
     * 
     * @return the studio submission base
     */
    public String getStudioSubmissionBase() {
        return studioSubmissionBase;
    }

    /**
     * Sets the studio submission base.
     * 
     * @param studioSubmissionURL
     *            the studioSubmission base to set
     */
    public void setStudioSubmissionBase(String studioSubmissionURL) {
        this.studioSubmissionBase = studioSubmissionURL;
    }
    /**
     * <p>Gets the base URL for Online Review application.</p>
     *
     * @return a <code>String</code> providing the base URL for Online Review application.
     */
    public String getOnlineReviewBaseURL() {
        return this.onlineReviewBaseURL;
    }

    /**
     * <p>Sets the base URL for Online Review application.</p>
     *
     * @param onlineReviewBaseURL a <code>String</code> providing the base URL for Online Review application.
     */
    public void setOnlineReviewBaseURL(String onlineReviewBaseURL) {
        this.onlineReviewBaseURL = onlineReviewBaseURL;
    }

    /**
     * <p>Gets the base for Online Review application controller for getting the composite review scorecard.</p>
     *
     * @return a <code>String</code> providing the base for Online Review application controller for getting the
     *         composite review scorecard.
     */
    public String getCompositeReviewBaseURL() {
        return this.compositeReviewBaseURL;
    }

    /**
     * <p>Sets the base for Online Review application controller for getting the composite review scorecard.</p>
     *
     * @param compositeReviewBaseURL a <code>String</code> providing the base for Online Review application controller
     *                               for getting the composite review scorecard.
     */
    public void setCompositeReviewBaseURL(String compositeReviewBaseURL) {
        this.compositeReviewBaseURL = compositeReviewBaseURL;
    }

    /**
     * <p>Gets the password to login to Online Review application.</p>
     *
     * @return a <code>String</code> providing the password to login to Online Review application.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * <p>Sets the password to login to Online Review application.</p>
     *
     * @param password a <code>String</code> providing the password to login to Online Review application.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * <p>Gets the username to login to Online Review application.</p>
     *
     * @return a <code>String</code> providing the username to login to Online Review application.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * <p>Sets the username to login to Online Review application.</p>
     *
     * @param username a <code>String</code> providing the username to login to Online Review application.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * <p>Gets the URL for Login controller for Online Review application.</p>
     *
     * @return a <code>String</code> providing the URL for Login controller for Online Review application.
     */
    public String getLoginUrl() {
        return this.loginUrl;
    }

    /**
     * <p>Sets the URL for Login controller for Online Review application.</p>
     *
     * @param loginUrl a <code>String</code> providing the URL for Login controller for Online Review application.
     */
    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

}
