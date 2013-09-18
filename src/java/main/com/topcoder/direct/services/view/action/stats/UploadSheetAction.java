/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.stats;

import java.io.*;

import org.apache.commons.io.IOUtils;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;

/**
 * <p>A <code>Struts</code> action to be used for upload the excel file.</p>
 *
 * @author flexme
 * @version 1.0
 */
public class UploadSheetAction extends BaseDirectStrutsAction {

    /**
     * Represents the path to excel file.
     */
    private String excelFile;
    
    /**
     * Represents the uploaded file (the link to the temporary file).
     */
    private File file;

    /**
     * Represents the error message.
     */
    private String errorMessage;

    /**
     * Represents the upload file content type.
     */
    private String fileContentType;

    /**
     * Represents the upload file name.
     */
    private String fileFileName;
    
    /**
     * Set the path to excel file.
     * 
     * @param excelFile  the path to excel file
     */
    public void setExcelFile(String excelFile) {
        this.excelFile = excelFile;
    }
    
    /**
     * Sets the uploaded file.
     * 
     * @param file the uploaded file
     */
    public void setFile(File file) {
        this.file = file;
    }


    /**
     * Gets the error message.
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Perform the action logic. Save the uploaded file.
     */
    protected void executeAction() throws Exception {
        // do nothing
    }

    /**
     * <p>
     * Executes the action.
     * </p>
     * <p>
     * It uses <b>Template Method</b> Design Pattern to perform the logic of the concrete action. Each concrete action
     * should implement the {@link #executeAction()} method. If some error occurs then set the exception with the
     * {@link #RESULT_KEY} key to the model.
     * </p>
     *
     * @return <code>SUCCESS</code> always
     */
    @Override
    public String execute() throws Exception {
        if(file == null) {
            errorMessage = "The file to upload can not be empty, please choose a file";
            return ERROR;
        } else {
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                inputStream = new FileInputStream(file);
                outputStream = new FileOutputStream(new File(excelFile));
                IOUtils.copy(inputStream, outputStream);
            } finally {
                IOUtils.closeQuietly(outputStream);
                IOUtils.closeQuietly(inputStream);
            }
        }
        return SUCCESS;
    }

    /**
     * Gets the file content type.
     * @return the file content type.
     */
    public String getFileContentType() {
        return fileContentType;
    }

    /**
     * Sets the file content type.
     * @param fileContentType  the file content type.
     */
    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    /**
     * Gets the file name.
     * @return the file name.
     */
    public String getFileFileName() {
        return fileFileName;
    }

    /**
     * Sets the file name.
     * @param fileFileName the file name.
     */
    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }
}
