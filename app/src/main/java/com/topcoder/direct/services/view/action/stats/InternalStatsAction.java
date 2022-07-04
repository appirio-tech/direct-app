/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.stats;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.topcoder.direct.services.view.action.ForwardAction;
import com.topcoder.direct.services.view.util.excel2html.ToHtml;

/**
 * <p>A <code>Struts</code> action to be used for generating the internal stats page from an excel file. This action will return the raw HTML data in plain text.</p>
 * <p/>
 * <b>Thread Safety</b>: In <b>Struts 2</b> framework, the action is constructed for every request so the thread
 * safety is not required (instead in Struts 1 the thread safety is required because the action instances are reused).
 * This class is mutable and stateful: it's not thread safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class InternalStatsAction extends ForwardAction {
    /**
     * The Error.
     */
    private static final String WORKBOOK_NO_FOUND_ERROR = "Can't find any Workbook, or it is protected by password.";

    /**
     * Represents the excel file to convert
     */
    private String excelFile;

    /**
     * Represents the index of sheet to display.
     */
    private int sheetIndex;
    
    /**
     * Represents the name of sheets in the excel.
     */
    private List<String> sheetTabs;

    /**
     * Represents the generated table data to embed.
     */
    private String tableData;

    /**
     * Represents the error message.
     */
    private String excelOpenError;

    /**
     * Gets the index of sheet to display.
     * 
     * @return the sheetIndex the index of sheet to display.
     */
    public int getSheetIndex() {
        return sheetIndex;
    }
    
    /**
     * Sets the index of sheet to display.
     * 
     * @param sheetIndex the index of sheet to display.
     */
    public void setSheetIndex(int sheetIndex) {
        this.sheetIndex = sheetIndex;
    }
    
    /**
     * Gets the name of sheets in the excel.
     * 
     * @return the sheetTabs the name of sheets in the excel.
     */
    public List<String> getSheetTabs() {
        return sheetTabs;
    }
    
    /**
     * Set the path to excel file
     * @param excelFile  the path to excel file
     */
    public void setExcelFile(String excelFile) {
        this.excelFile = excelFile;
    }
    
    /**
     * Returns the table data generated.
     * @return the table data generated.
     */
    public String getTableData() {
        return tableData;
    }

    /**
     * Return the excelOpenError.
     *
     * @return the excelOpenError
     */
    public String getExcelOpenError() {
        return excelOpenError;
    }
    
    /**
     * Generate the internal stats page from excel file.
     *
     * @return the execution response.
     * @throws Exception if any error occurs.
     */
    public String execute() throws Exception {
        try {
            if (new File(excelFile).canRead()) {
                sheetTabs = new ArrayList<String>();
                tableData = ToHtml.generateStatsPage(sheetIndex, excelFile, sheetTabs);
            }
            excelOpenError = "";
        } catch (IllegalArgumentException e){
            excelOpenError = WORKBOOK_NO_FOUND_ERROR;

            File excel = new File(excelFile);
            if (excel.exists()) {
                excel.delete();
            }
        }

        return SUCCESS;
    }
}
