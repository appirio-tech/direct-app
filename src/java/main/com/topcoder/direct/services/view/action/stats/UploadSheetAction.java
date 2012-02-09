/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.stats;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

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
     * Perform the action logic. Save the uploaded file.
     */
    protected void executeAction() throws Exception {
        IOUtils.copy(new FileInputStream(file), new FileOutputStream(new File(excelFile)));
    }
}
