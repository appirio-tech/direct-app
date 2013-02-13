/*
 * Copyright (C) 2012 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.enterpriseDashboard;

import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.enterpriseDashboard.ContestPipelineDrillInDTO;
import com.topcoder.direct.services.view.dto.enterpriseDashboard.ProjectPipelineDrillInDTO;
import com.topcoder.direct.services.view.dto.enterpriseDashboard.EnterpriseDashboardMonthPipelineDTO;
import com.topcoder.direct.services.view.dto.enterpriseDashboard.EnterpriseDashboardMonthProjectPipelineDTO;
import com.topcoder.direct.services.view.form.enterpriseDashboard.EnterpriseDashboardFilterForm;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.excel.Row;
import com.topcoder.excel.Sheet;
import com.topcoder.excel.Workbook;
import com.topcoder.excel.impl.ExcelSheet;
import com.topcoder.excel.impl.ExcelWorkbook;
import com.topcoder.excel.output.Biff8WorkbookSaver;
import com.topcoder.excel.output.WorkbookSaver;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * The action handles the ajax requests for pipeline part of the enterprise dashboard.
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TC Cockpit Enterprise Dashboard Project Pipeline and Project Completion Date Update)
 * <ol>
 *     <li>
 *      Add method {@link #getProjectsPipeline()} to handle the ajax request to get projects pipeline data.
 *     </li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.2 (Release Assembly - Cockpit Enterprise Dashboard Chart Drill-In)
 * <ul>
 *     <li>
 *     Add method {@link #getContestsPipelineDrillIn()} to handle the ajax request to drill in contest pipeline
 *     </li>
 *     <li>
 *     Add method {@link #getProjectsPipelineDrillIn()} to handle the ajax request to drill in project pipeline
 *     </li>
 * </ul>
 * </p>
 *
 * @author GreatKevin
 * @version 1.2
 * @since Module Assembly - TC Cockpit Enterprise Dashboard Pipeline Part
 */
public class DashboardPipelineAction extends BaseDirectStrutsAction implements FormAction<EnterpriseDashboardFilterForm> {
    /**
     * The date format to display the X axis of pipeline chart.
     */
    private static final DateFormat AXIS_DATE_FORMAT = new SimpleDateFormat("yyyy MMMMM");

    /**
     * The date format for the drill-in data.
     *
     * @since 1.2
     */
    private static final DateFormat DRILL_IN_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    /**
     * The export stream for the contest pipeline drill-in.
     *
     * @since 1.2
     */
    private InputStream contestPipelineDrillInStream;

    /**
     * The export stream for the project pipeline drill-in.
     *
     * @since 1.2
     */
    private InputStream projectPipelineDrillInStream;

    /**
     * The export flag for the pipeline drill-in.
     *
     * @since 1.2
     */
    private boolean export;

    /**
     * The form data of the action.
     */
    private EnterpriseDashboardFilterForm formData = new EnterpriseDashboardFilterForm();

    /**
     * Gets the form data of the action.
     *
     * @return the form data of the action.
     */
    public EnterpriseDashboardFilterForm getFormData() {
        return this.formData;
    }

    /**
     * Sets the form data of the action.
     *
     * @param formData the form data of the action.
     */
    public void setFormData(EnterpriseDashboardFilterForm formData) {
        this.formData = formData;
    }

    /**
     * Sets the export flag.
     *
     * @param export the export flag.
     *
     * @since 1.2
     */
    public void setExport(boolean export) {
        this.export = export;
    }


    /**
     * Gets the contests pipeline drill-in export stream.
     *
     * @return the contests pipeline drill-in export stream.
     * @since 1.2
     */
    public InputStream getContestPipelineDrillInStream() {
        return contestPipelineDrillInStream;
    }

    /**
     * Gets the projects pipeline drill-in export stream.
     *
     * @return the projects pipeline drill-in export stream.
     * @since 1.2
     */
    public InputStream getProjectPipelineDrillInStream() {
        return projectPipelineDrillInStream;
    }

    /**
     * Empty, ajax requests are handled via action methods invocation.
     *
     * @throws Exception if there is any error.
     */
    @Override
    protected void executeAction() throws Exception {
        // do nothing
    }

    /**
     * Handles the ajax request to get the data for the contests pipeline chart.
     *
     * @return the result code.
     */
    public String getContestsPipeline() {
        try {
            List<Map<String, String>> result = new ArrayList<Map<String, String>>();

            // get the date from data provider via query
            final List<EnterpriseDashboardMonthPipelineDTO> enterpriseDashboardPipelines =
                    DataProvider.getEnterpriseDashboardContestsPipeline(getFormData());

            for(EnterpriseDashboardMonthPipelineDTO item : enterpriseDashboardPipelines) {
                Map<String, String> m = new HashMap<String, String>();
                m.put("date", AXIS_DATE_FORMAT.format(item.getDate()));
                m.put("activeContests", String.valueOf(item.getTotalActiveContests()));
                m.put("scheduledContests", String.valueOf(item.getTotalScheduledContests()));
                m.put("draftContests", String.valueOf(item.getTotalDraftContests()));
                m.put("completedContests", String.valueOf(item.getTotalCompletedContests()));
                m.put("failedContests", String.valueOf(item.getTotalFailedContests()));
                result.add(m);
            }

            setResult(result);
        } catch (Throwable e) {

            e.printStackTrace(System.err);

            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }

    /**
     * Handles the request for the contests pipeline drill-in.
     *
     * @return result code.
     * @since 1.2
     */
    public String getContestsPipelineDrillIn() {
        try {
            // get the date from data provider via query
            List<ContestPipelineDrillInDTO> data =
                    DataProvider.getEnterpriseDashboardContestsPipelineDrillIn(getFormData());

            if (export) {
                Workbook workbook = new ExcelWorkbook();
                Sheet sheet = new ExcelSheet("Contest Pipeline " + getFormData().getStartMonth(), (ExcelWorkbook) workbook);

                // set up the sheet header first
                Row row = sheet.getRow(1);
                int index = 1;

                row.getCell(index++).setStringValue("Project Name");
                row.getCell(index++).setStringValue("Contest Name");
                row.getCell(index++).setStringValue("Status");
                row.getCell(index++).setStringValue("Start Date");
                row.getCell(index++).setStringValue("End Date");
                row.getCell(index++).setStringValue("Copilot");

                // insert sheet data from 2nd row
                int rowIndex = 2;

                for (ContestPipelineDrillInDTO line : data) {
                    // project name
                    row = sheet.getRow(rowIndex++);
                    row.getCell(1).setStringValue(line.getDirectProjectName());
                    row.getCell(2).setStringValue(line.getContestName());
                    row.getCell(3).setStringValue(line.getContestStatus());
                    row.getCell(4).setStringValue(DRILL_IN_DATE_FORMAT.format(line.getStartDate()));
                    row.getCell(5).setStringValue(line.getEndDate() == null ? "None" : DRILL_IN_DATE_FORMAT.format(line.getEndDate()));
                    row.getCell(6).setStringValue(line.getCopilotHandle() == null ? "None" : line.getCopilotHandle());
                }

                workbook.addSheet(sheet);

                // Create a new WorkBookSaver
                WorkbookSaver saver = new Biff8WorkbookSaver();
                ByteArrayOutputStream saveTo = new ByteArrayOutputStream();
                saver.save(workbook, saveTo);
                this.contestPipelineDrillInStream = new ByteArrayInputStream(saveTo.toByteArray());

                return "download";

            } else {
                List<Map<String, String>> result = new ArrayList<Map<String, String>>();

                for (ContestPipelineDrillInDTO item : data) {
                    Map<String, String> m = new HashMap<String, String>();
                    m.put("directProjectName", item.getDirectProjectName());
                    m.put("directProjectId", String.valueOf(item.getDirectProjectId()));
                    m.put("contestId", String.valueOf(item.getContestId()));
                    m.put("contestName", item.getContestName());
                    m.put("contestStatus", item.getContestStatus());
                    m.put("startDate", DRILL_IN_DATE_FORMAT.format(item.getStartDate()));
                    m.put("endDate", item.getEndDate() == null ? "" : DRILL_IN_DATE_FORMAT.format(item.getEndDate()));
                    m.put("copilot", item.getCopilotHandle());
                    result.add(m);
                }
                setResult(result);
            }

        } catch (Throwable e) {

            e.printStackTrace(System.err);

            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }

    /**
     * Handles the ajax request to get the data for the projects pipeline chart.
     *
     * @return the result code.
     * @since 1.1
     */
    public String getProjectsPipeline() {
        try {
            List<Map<String, String>> result = new ArrayList<Map<String, String>>();

            // get the date from data provider via query
            final List<EnterpriseDashboardMonthProjectPipelineDTO> enterpriseDashboardPipelines =
                    DataProvider.getEnterpriseDashboardProjectsPipeline(getFormData());

            for(EnterpriseDashboardMonthProjectPipelineDTO item : enterpriseDashboardPipelines) {
                Map<String, String> m = new HashMap<String, String>();
                m.put("date", AXIS_DATE_FORMAT.format(item.getDate()));
                m.put("startedProjectsNumber", String.valueOf(item.getTotalStartedProjects()));
                m.put("completedProjectsNumber", String.valueOf(item.getTotalCompletedProjects()));
                result.add(m);
            }

            setResult(result);
        } catch (Throwable e) {
            e.printStackTrace(System.err);
            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }

    /**
     * Handles the request for the projects pipeline drill-in.
     *
     * @return result code.
     * @since 1.2
     */
    public String getProjectsPipelineDrillIn() {
        try {
            // get the date from data provider via query
            List<ProjectPipelineDrillInDTO> data = DataProvider.getEnterpriseDashboardProjectsPipelineDrillIn(getFormData());

            if (export) {
                Workbook workbook = new ExcelWorkbook();
                Sheet sheet = new ExcelSheet("Projects Pipeline " + getFormData().getStartMonth(), (ExcelWorkbook) workbook);

                // set up the sheet header first
                Row row = sheet.getRow(1);
                int index = 1;

                row.getCell(index++).setStringValue("Project Name");
                row.getCell(index++).setStringValue("Project Status");
                row.getCell(index++).setStringValue("Project Creation Time");
                row.getCell(index++).setStringValue("Project Completion Time");

                // insert sheet data from 2nd row
                int rowIndex = 2;

                for (ProjectPipelineDrillInDTO line : data) {
                    // project name
                    row = sheet.getRow(rowIndex++);
                    row.getCell(1).setStringValue(line.getDirectProjectName());
                    row.getCell(2).setStringValue(line.getDirectProjectStatus());
                    row.getCell(3).setStringValue(DRILL_IN_DATE_FORMAT.format(line.getProjectStartDate()));
                    row.getCell(4).setStringValue(line.getProjectCompletionDate() == null ?
                            "None" : DRILL_IN_DATE_FORMAT.format(line.getProjectCompletionDate()));
                }

                workbook.addSheet(sheet);

                // Create a new WorkBookSaver
                WorkbookSaver saver = new Biff8WorkbookSaver();
                ByteArrayOutputStream saveTo = new ByteArrayOutputStream();
                saver.save(workbook, saveTo);
                this.projectPipelineDrillInStream = new ByteArrayInputStream(saveTo.toByteArray());

                return "download";

            } else {
                List<Map<String, String>> result = new ArrayList<Map<String, String>>();
                for (ProjectPipelineDrillInDTO item : data) {
                    Map<String, String> m = new HashMap<String, String>();
                    m.put("directProjectId", String.valueOf(item.getDirectProjectId()));
                    m.put("directProjectName", item.getDirectProjectName());
                    m.put("projectStatus", item.getDirectProjectStatus());
                    m.put("startDate", DRILL_IN_DATE_FORMAT.format(item.getProjectStartDate()));
                    m.put("endDate", item.getProjectCompletionDate() == null ? "" : DRILL_IN_DATE_FORMAT.format(item.getProjectStartDate()));
                    result.add(m);
                }

                setResult(result);
            }
        } catch (Throwable e) {
            e.printStackTrace(System.err);
            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }
}
