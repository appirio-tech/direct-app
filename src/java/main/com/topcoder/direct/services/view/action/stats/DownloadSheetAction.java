/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.stats;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;

/**
 * <p>A <code>Struts</code> action to be used for download the excel file.</p>
 *
 * @author notpad
 * @version 1.0
 */
public class DownloadSheetAction extends BaseDirectStrutsAction {

    /**
     * Represents the path to excel file.
     */
    private String excelFile;
      
    /**
     * The name of the excel file.
     */
    private String fileName;
    
    /**
     * Set the path to excel file.
     * 
     * @param excelFile  the path to excel file
     */
    public void setExcelFile(String excelFile) {
        this.excelFile = excelFile;
    }
    
    /**
     * Gets the excel file name.
     * 
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the excel file name.
     * 
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    /**
     * Perform the action logic. Sets the excel file name. 
     */
    protected void executeAction() throws Exception {
    	this.fileName = this.excelFile.substring(this.excelFile.lastIndexOf("/") + 1);
    }
    
    /**
     * Gets the input stream from the excel file.
     *
     * @return the input stream from the excel file
     * @throws FileNotFoundException if the excel file can not be found
     */
    public InputStream getInputStream() throws FileNotFoundException {
        return new FileInputStream(excelFile);
    }

    /**
     * Gets the contents disposition for the document.
     * @return the contents disposition for the document
     */
    public String getContentDisposition() {
        String dis = "attachment;filename=" + fileName;
        return dis.replaceAll(" ", "%20");
    }
}
