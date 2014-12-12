package com.topcoder.direct.services.view.action.contest;

import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;
import com.topcoder.servlet.request.FileUpload;
import com.topcoder.servlet.request.UploadedFile;

import java.io.InputStream;

/**
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DownloadFinalFixAction extends BaseDirectStrutsAction {

    /**
     * Represents the submission id the user want to download.
     */
    private String finalFix;
    /**
     * Represents the uploaded file the user want to download.
     */
    private UploadedFile uploadedFile;

    /**
     * Represents the <code>FileUpload</code> service. It will be injected by Spring.
     */
    private FileUpload fileUpload;

    private long contestId;

    private int version;

    public long getContestId() {
        return contestId;
    }

    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

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
        uploadedFile = fileUpload.getUploadedFile(finalFix);
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
        return "attachment; filename=\"finalFix-" + getVersion() + "-" + uploadedFile.getRemoteFileName()
                + "\"";
    }

    /**
     * Gets the submission id the user want to download.
     *
     * @return the submission id the user want to download.
     */
    public String getFinalFix() {
        return finalFix;
    }

    /**
     * Sets the final fix id the user want to download.
     *
     * @param finalFix
     *            the submission id the user want to download.
     */
    public void setFinalFix(String finalFix) {
        this.finalFix = finalFix;
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
