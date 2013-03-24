/*
 * Copyright (C) 2012 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.enterpriseDashboard;

import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.enterpriseDashboard.ContestPipelineDrillInDTO;
import com.topcoder.direct.services.view.dto.enterpriseDashboard.EnterpriseDashboardMonthPipelineDTO;
import com.topcoder.direct.services.view.dto.enterpriseDashboard.EnterpriseDashboardMonthProjectPipelineDTO;
import com.topcoder.direct.services.view.dto.enterpriseDashboard.ProjectPipelineDrillInDTO;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>
 * The action handles the ajax requests for pipeline part of the enterprise dashboard.
 * </p>
 * <p/>
 * <p>
 * Version 1.1 (Release Assembly - TC Cockpit Enterprise Dashboard Project Pipeline and Project Completion Date Update)
 * <ol>
 * <li>
 * Add method {@link #getProjectsPipeline()} to handle the ajax request to get projects pipeline data.
 * </li>
 * </ol>
 * </p>
 * <p/>
 * <p>
 * Version 1.2 (Release Assembly - Cockpit Enterprise Dashboard Chart Drill-In)
 * <ul>
 * <li>
 * Add method {@link #getContestsPipelineDrillIn()} to handle the ajax request to drill in contest pipeline
 * </li>
 * <li>
 * Add method {@link #getProjectsPipelineDrillIn()} to handle the ajax request to drill in project pipeline
 * </li>
 * </ul>
 * </p>
 * <p/>
 * <p>
 * Version 1.3 (Release Assembly - TC Cockpit New Enterprise Dashboard Release 2)
 * <ul>
 * <li>Add property {@link #pipelineExport} and its getter</li>
 * <li>Add method {@link #getPipelineExportAll()}</li>
 * </ul>
 * </p>
 *
 * @author GreatKevin
 * @version 1.3
 * @since Module Assembly - TC Cockpit Enterprise Dashboard Pipeline Part
 */
public class DashboardPipelineAction extends BaseDirectStrutsAction implements
        FormAction<EnterpriseDashboardFilterForm> {
    /**
     * The date format to display the X axis of pipeline chart.
     */
    private final DateFormat AXIS_DATE_FORMAT = new SimpleDateFormat("yyyy MMMMM");

    /**
     * The date format for the drill-in data.
     *
     * @since 1.2
     */
    private final DateFormat DRILL_IN_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

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
     * The export stream for the project pipeline export.
     *
     * @since 1.3
     */
    private InputStream pipelineExport;

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
     * Gets the pipeline export all stream.
     *
     * @return the pipeline export all stream.
     * @since 1.3
     */
    public InputStream getPipelineExport() {
        return pipelineExport;
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

            for (EnterpriseDashboardMonthPipelineDTO item : enterpriseDashboardPipelines) {
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
                    DataProvider.getEnterpriseDashboardContestsPipelineDrillIn(getFormData(), true);

            if (export) {
                Workbook workbook = new ExcelWorkbook();

                workbook.addSheet(createContestPipelineDrillInSheet(workbook, "Contest Pipeline " + getFormData()
                        .getStartMonth(), data));

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

            for (EnterpriseDashboardMonthProjectPipelineDTO item : enterpriseDashboardPipelines) {
                Map<String, String> m = new HashMap<String, String>();
                m.put("date", AXIS_DATE_FORMAT.format(item.getDate()));
                m.put("startedProjectsNumber", String.valueOf(item.getTotalStartedProjects()));
                m.put("completedProjectsNumber", String.valueOf(item.getTotalCompletedProjects()));
                result.add(m);
            }

            setResult(result);
        } catch (Throwable e) {
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
            List<ProjectPipelineDrillInDTO> data = DataProvider.getEnterpriseDashboardProjectsPipelineDrillIn
                    (getFormData(), true);

            if (export) {
                Workbook workbook = new ExcelWorkbook();

                workbook.addSheet(createProjectPipelineDrillInSheet(workbook, "Project Pipeline " + getFormData()
                        .getStartMonth(), data));

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
                    m.put("endDate", item.getProjectCompletionDate() == null ? "" : DRILL_IN_DATE_FORMAT.format(item
                            .getProjectStartDate()));
                    result.add(m);
                }

                setResult(result);
            }
        } catch (Throwable e) {
            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }

    /**
     * Handles the request to export all project and contest pipeline data into excel file
     *
     * @return the result code.
     * @since 1.3
     */
    public String getPipelineExportAll() {
        try {
            final List<ProjectPipelineDrillInDTO> projectPipelineData =
                    DataProvider.getEnterpriseDashboardProjectsPipelineDrillIn(getFormData(), false);

            Workbook workbook = new ExcelWorkbook();

            Map<Date, List<ProjectPipelineDrillInDTO>> projectSheetsMap = new TreeMap<Date,
                    List<ProjectPipelineDrillInDTO>>();
            Map<Date, List<ContestPipelineDrillInDTO>> contestSheetsMap = new TreeMap<Date,
                    List<ContestPipelineDrillInDTO>>();

            // initialize the project sheets map and contest sheets map
            DateFormat formDateFormatter = new SimpleDateFormat("MMM''yy");
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(formDateFormatter.parse(getFormData().getStartMonth()));
            long startMonthCount = calendar.get(Calendar.YEAR) * 12 + calendar.get(Calendar.MONTH);
            calendar.setTime(formDateFormatter.parse(getFormData().getEndMonth()));
            long endMonthCount = calendar.get(Calendar.YEAR) * 12 + calendar.get(Calendar.MONTH);

            for (long m = startMonthCount; m <= endMonthCount; m++) {
                calendar.set(Calendar.YEAR, (int) m / 12);
                calendar.set(Calendar.MONTH, (int) m % 12);
                // put in an empty array list first
                projectSheetsMap.put(calendar.getTime(), new ArrayList<ProjectPipelineDrillInDTO>());
                contestSheetsMap.put(calendar.getTime(), new ArrayList<ContestPipelineDrillInDTO>());
            }

            for (ProjectPipelineDrillInDTO projectData : projectPipelineData) {
                projectSheetsMap.get(dateFormatter.parse(projectData.getYearMonthLabel())).add(projectData);
            }

            for (Map.Entry<Date, List<ProjectPipelineDrillInDTO>> entry : projectSheetsMap.entrySet()) {
                workbook.addSheet(createProjectPipelineDrillInSheet(workbook, "Project Pipeline " + formDateFormatter
                        .format(entry.getKey()), entry.getValue()));
            }

            List<ContestPipelineDrillInDTO> contestPipelineData =
                    DataProvider.getEnterpriseDashboardContestsPipelineDrillIn(getFormData(), false);

            for (ContestPipelineDrillInDTO contestData : contestPipelineData) {
                contestSheetsMap.get(dateFormatter.parse(contestData.getYearMonthLabel())).add(contestData);
            }

            for (Map.Entry<Date, List<ContestPipelineDrillInDTO>> entry : contestSheetsMap.entrySet()) {
                workbook.addSheet(createContestPipelineDrillInSheet(workbook, "Contest Pipeline " + formDateFormatter
                        .format(entry.getKey()), entry.getValue()));
            }

            // Create a new WorkBookSaver
            WorkbookSaver saver = new Biff8WorkbookSaver();
            ByteArrayOutputStream saveTo = new ByteArrayOutputStream();
            saver.save(workbook, saveTo);
            this.pipelineExport = new ByteArrayInputStream(saveTo.toByteArray());
            return "download";
        } catch (Throwable e) {
            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }

    /**
     * Creates the excel sheet for a specified workbook with the given sheet name and sheet data for
     * contest pipeline drill in.
     *
     * @param workbook  the work book the sheet is in
     * @param sheetName the name of the sheet.
     * @param sheetData the sheet data
     * @return the created sheet
     * @throws Exception if any error
     * @since 1.3
     */
    private Sheet createContestPipelineDrillInSheet(Workbook workbook, String sheetName,
                                                    List<ContestPipelineDrillInDTO> sheetData) throws Exception {
        Sheet sheet = new ExcelSheet(sheetName, (ExcelWorkbook) workbook);

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

        for (ContestPipelineDrillInDTO line : sheetData) {
            // project name
            row = sheet.getRow(rowIndex++);
            index = 1;
            row.getCell(index++).setStringValue(line.getDirectProjectName());
            row.getCell(index++).setStringValue(line.getContestName());
            row.getCell(index++).setStringValue(line.getContestStatus());
            row.getCell(index++).setStringValue(DRILL_IN_DATE_FORMAT.format(line.getStartDate()));
            row.getCell(index++).setStringValue(line.getEndDate() == null ? "None" : DRILL_IN_DATE_FORMAT.format(line
                    .getEndDate()));
            row.getCell(index++).setStringValue(line.getCopilotHandle() == null ? "None" : line.getCopilotHandle());
        }

        return sheet;
    }

    /**
     * Creates the excel sheet for a specified workbook with the given sheet name and sheet data for
     * project pipeline drill in.
     *
     * @param workbook  the work book the sheet is in
     * @param sheetName the name of the sheet.
     * @param sheetData the sheet data
     * @return the created sheet
     * @throws Exception if any error
     * @since 1.3
     */
    private Sheet createProjectPipelineDrillInSheet(Workbook workbook, String sheetName,
                                                    List<ProjectPipelineDrillInDTO> sheetData) throws Exception {
        Sheet sheet = new ExcelSheet(sheetName, (ExcelWorkbook) workbook);

        // set up the sheet header first
        Row row = sheet.getRow(1);
        int index = 1;

        row.getCell(index++).setStringValue("Project Name");
        row.getCell(index++).setStringValue("Project Status");
        row.getCell(index++).setStringValue("Project Creation Time");
        row.getCell(index++).setStringValue("Project Completion Time");

        // insert sheet data from 2nd row
        int rowIndex = 2;

        for (ProjectPipelineDrillInDTO line : sheetData) {
            // project name
            row = sheet.getRow(rowIndex++);
            index = 1;
            row.getCell(index++).setStringValue(line.getDirectProjectName());
            row.getCell(index++).setStringValue(line.getDirectProjectStatus());
            row.getCell(index++).setStringValue(DRILL_IN_DATE_FORMAT.format(line.getProjectStartDate()));
            row.getCell(index++).setStringValue(line.getProjectCompletionDate() == null ?
                    "None" : DRILL_IN_DATE_FORMAT.format(line.getProjectCompletionDate()));
        }

        return sheet;
    }
}
