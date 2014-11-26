/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.copilot;

import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.action.project.planner.ProjectPlanUtil;
import com.topcoder.direct.services.view.dto.project.planner.ProjectPlannerTransferDTO;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.servlet.request.FileUpload;
import com.topcoder.servlet.request.UploadedFile;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * <p>
 * This action handles the request to preview the game plan in copilot posting submission.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (TopCoder Cockpit Copilot Posting Submission Game Plan Preview and Stats)
 */
public class PreviewCopilotSubmissionAction extends BaseDirectStrutsAction {

    /**
     * Represents the submission id the user want to view.
     */
    private long submissionId;

    /**
     * Represents the project id of the submission.
     */
    private long projectId;

    /**
     * Represents the submission the user want to view.
     */
    private Submission submission;

    /**
     * Represents the uploaded file the user want to view.
     */
    private UploadedFile uploadedFile;

    /**
     * Represents the <code>FileUpload</code> service. It will be injected by Spring.
     */
    private FileUpload fileUpload;

    /**
     * Gets the submission id.
     *
     * @return the submission id.
     */
    public long getSubmissionId() {
        return submissionId;
    }

    /**
     * Sets the submission id.
     *
     * @param submissionId the submission id.
     */
    public void setSubmissionId(long submissionId) {
        this.submissionId = submissionId;
    }

    /**
     * Gets the project id.
     *
     * @return the project id.
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Sets the project id.
     *
     * @param projectId the project id.
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * Sets the <code>FileUpload</code> instance.
     *
     * @param fileUpload the <code>FileUpload</code> instance
     */
    public void setFileUpload(FileUpload fileUpload) {
        this.fileUpload = fileUpload;
    }

    /**
     * The main action execution logic.
     *
     * @throws Exception if any error.
     */
    @Override
    protected void executeAction() throws Exception {
        ZipInputStream zin = null;
        ByteArrayOutputStream byteOut = null;

        try {
            // get the submission of the project
            Submission[] submissions = getContestServiceFacade().getSoftwareProjectSubmissions(getCurrentUser(),
                                                                                               projectId);
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

            Map<String, Object> result;

            BufferedInputStream bin = new BufferedInputStream(uploadedFile.getInputStream());
            zin = new ZipInputStream(bin);
            ZipEntry ze;
            byteOut = new ByteArrayOutputStream();
            boolean gamePlanFound = false;

            while ((ze = zin.getNextEntry()) != null) {
                if (FilenameUtils.getExtension(ze.getName().toLowerCase()).equals("xls")) {
                    byte[] buffer = new byte[8192];
                    int len;
                    while ((len = zin.read(buffer)) != -1) {
                        byteOut.write(buffer, 0, len);
                    }

                    // we take the first one in the zip as the correct game plan file
                    gamePlanFound = true;
                    break;
                }
            }

            byte[] xlsBytes = byteOut.toByteArray();

            if (!gamePlanFound) {
                throw new IllegalArgumentException(
                        "The copilot posting submission does not have any game plan (xls format) included");
            }

            if (gamePlanFound && xlsBytes.length == 0) {
                throw new IllegalArgumentException(
                        "The game plan (xls format) of the copilot posting submission is empty");
            }


            HSSFWorkbook workbook = new HSSFWorkbook(new ByteArrayInputStream(xlsBytes));

            ProjectPlannerTransferDTO projectPlannerTransferDTO = ProjectPlanUtil.importProjectPlanFromExcel(workbook);

            ObjectMapper m = new ObjectMapper();
            result = m.convertValue(projectPlannerTransferDTO, Map.class);

            setResult(result);
        } finally {
            if (zin != null) {
                try {
                    zin.close();
                } catch (Throwable e) {
                    // ignore
                }
            }
            if (byteOut != null) {
                try {
                    byteOut.close();
                } catch (Throwable e) {
                    // ignore
                }

            }
        }
    }
}
