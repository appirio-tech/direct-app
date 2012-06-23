/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest;

import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.topcoder.direct.services.view.action.contest.launch.ContestAction;
import com.topcoder.direct.services.view.dto.contest.SoftwareContestSubmissionsDTO;
import com.topcoder.direct.services.view.dto.contest.SoftwareSubmissionDTO;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.servlet.request.FileUpload;
import com.topcoder.servlet.request.UploadedFile;

/**
 * <p>
 * This struts action is used to download all software milestone submissions.
 * </p>
 * 
 * @author TCSASSEMBER
 * @version 1.0 (Release Assembly - TopCoder Cockpit Software Milestone Management)
 */
public class DownloadAllSoftwareMilestoneSubmissionsAction extends ContestAction {
    /**
     * Represents the serial verison unique id.
     */
    private static final long serialVersionUID = 1880950815370143413L;

    /**
     * Represents the upload parameters which are used to retrieve the uploaded files.
     */
    private List<String> uploadParams;

    /**
     * Represents the <code>FileUpload</code> service. It will be injected by Spring.
     */
    private FileUpload fileUpload;

    /**
     * <p>
     * Creates a <code>DownloadAllSoftwareMilestoneSubmissionsAction</code> instance.
     * </p>
     */
    public DownloadAllSoftwareMilestoneSubmissionsAction() {
    }

    /**
     * <p>
     * Executes the action. It will get the uploaded files.
     * </p>
     * 
     * @throws Exception if any error occurred
     */
    @Override
    protected void executeAction() throws Exception {
        // get the submissions of the project
        Submission[] submissions = getContestServiceFacade().getSoftwareProjectSubmissions(
            getCurrentUser(), getProjectId()); 
        Map<Long, String> idToUploadParam = new HashMap<Long, String>();
        for (Submission sub : submissions) {
            if (sub.getUpload() != null && sub.getUpload().getParameter() != null
                && sub.getUpload().getParameter().trim().length() > 0) {
                idToUploadParam.put(sub.getId(), sub.getUpload().getParameter());
            }
        }

        // retrieve the milestone submissions
        SoftwareContestSubmissionsDTO dto = new SoftwareContestSubmissionsDTO();
        dto.setProjectId(getProjectId());        
        DataProvider.setSoftwareMilestoneSubmissionsData(dto);
        uploadParams = new ArrayList<String>();
        for (SoftwareSubmissionDTO sub : dto.getSubmissions()) {
            if (!idToUploadParam.containsKey(sub.getSubmissionId())) {
                throw new Exception("Cannot find submission " + sub.getSubmissionId() +
                    " in project " + getProjectId());
            }
            uploadParams.add(idToUploadParam.get(sub.getSubmissionId()));
        }
    }

    /**
     * Gets the <code>InputStream</code> of the download.
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
                    for (String param : uploadParams) {
                        UploadedFile file = fileUpload.getUploadedFile(param);
                        is = file.getInputStream();
                    
                        // create an entry for each file
                        ZipEntry outputEntry = new ZipEntry(file.getRemoteFileName());
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
     * @return the content disposition of the zip file to download
     */
    public String getContentDisposition() {
        return "attachment; filename=\"all_Milestone_submissions_" + getProjectId() + ".zip\"";
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
}
