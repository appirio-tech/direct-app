/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.stats;

import com.topcoder.direct.services.view.action.AbstractAction;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.ForwardAction;
import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.direct.services.view.dto.CommonDTO;
import com.topcoder.direct.services.view.form.ProjectIdForm;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.direct.services.view.util.excel2html.ToHtml;
import com.topcoder.security.TCSubject;
import com.topcoder.service.gameplan.GamePlanService;
import com.topcoder.service.util.gameplan.SoftwareProjectData;
import com.topcoder.service.util.gameplan.StudioProjectData;
import com.topcoder.service.util.gameplan.TCDirectProjectGamePlanData;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
     * Represents the excel file to convert
     */
    private String excelFile;

    /**
     * Set the path to excel file
     * @param excelFile  the path to excel file
     */
    public void setExcelFile(String excelFile) {
        this.excelFile = excelFile;
    }

    /**
     * Represents the generated table data to embed.
     */
    private String tableData;

    /**
     * Returns the table data generated.
     * @return the table data generated.
     */
    public String getTableData() {
        return tableData;
    }
    /**
     * Generate the internal stats page from excel file.
     *
     * @return the execution response.
     * @throws Exception if any error occurs.
     */
    public String execute() throws Exception {
        tableData = ToHtml.generateStatsPage(this.excelFile);

        return SUCCESS;
    }
}
