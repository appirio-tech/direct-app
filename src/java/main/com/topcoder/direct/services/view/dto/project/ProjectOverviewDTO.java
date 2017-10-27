/*
 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.project;

import com.topcoder.clients.model.Project;
import com.topcoder.direct.services.configs.ServerConfiguration;
import com.topcoder.direct.services.view.dto.CommonDTO;
import com.topcoder.direct.services.view.dto.UpcomingActivitiesDTO;
import com.topcoder.direct.services.view.dto.contest.ContestHealthDTO;
import com.topcoder.direct.services.view.dto.dashboard.EnterpriseDashboardProjectStatDTO;
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
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * A DTO class providing the data for <code>Project Overview</code> page view
 * for single project.
 * </p>
 * <p>
 * Version 1.0.1 - Direct - Project Dashboard Assembly Change Note
 * <ul>
 * <li>Added {@link #dashboardProjectStat} and {@link #contests} parameters. </li>
 * </ul>
 * </p>
 * <p/>
 * <p>
 * Version 1.1 (Cockpit Performance Improvement Project Overview and Manage Copilot Posting Assembly 1.0) Change notes:
 * <ol>
 * <li>Updated {@link #contests} variable to map objects of {@link ContestHealthDTO} type instead of
 * <code>ContestDashboardDTO</code> type.</li>
 * </ol>
 * </p>
 * <p/>
 * <p>
 * Version 1.2 (Project Health Update Assembly 1.0) Change notes:
 * <ol>
 * <li>Removed <code>contests</code> variable as project contests health status is now retrieved separately via AJAX
 * call.</li>
 * </ol>
 * </p>
 * <p/>
 * <p>
 * Version 1.3 (TopCoder Cockpit Project Overview R2 Project Forum Backend Assembly 1.0) Change notes:
 * <ol>
 * <li>Added {@link #hasForumThreads} property with respective accessor/mutator methods.</li>
 * </ol>
 * </p>
 * <p/>
 * <p>
 * Version 1.4 (Module Assembly - TC Cockpit Project Overview Project General Info) Change notes:
 * <ol>
 * <li>Added {@link #projectGeneralInfo} property with respective accessor/mutator methods.</li>
 * </ol>
 * </p>
 * <p>
 * Version 1.5 (Release Assembly - TopCoder Direct Cockpit Release Assembly Ten)
 * <ol>
 *     <li>Added TopCoder Account Managers data into the export excel file</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.6 (topcoder Direct - Add Project Billings to project overview) @author deedee @challenge 30045142
 * <ul>
 *     <li>Added {@link #billingAccounts} proprety for billing account</li>
 *     <li>Added {@link #getBillingAccounts()}</li>
 *     <li>Added {@link #setBillingAccounts(java.util.List)}</li>
 *     <li>Updated on {@link #insertSheetData(com.topcoder.excel.Sheet)} to include project billing data</li>
 * </ul>
 * </p>
 * 
 * @author isv, Blues, GreatKevin
 * @version 1.6
 */
public class ProjectOverviewDTO extends CommonDTO implements Serializable, ProjectStatsDTO.Aware,
        UpcomingActivitiesDTO.Aware,
        LatestProjectActivitiesDTO.Aware {

    /**
     * <p>A <code>ProjectStatsDTO</code> providing statistics on project.</p>
     */
    private ProjectStatsDTO projectStats;

    /**
     * <p>An <code>UpcomingActivitiesDTO</code> providing details on upcoming activities on projects associated with
     * current user.</p>
     */
    private UpcomingActivitiesDTO upcomingActivities;

    /**
     * <p>A <code>LatestProjectActivitiesDTO</code> providing details on latest activities on requested project.</p>
     */
    private LatestProjectActivitiesDTO latestProjectActivities;

    /**
     * <p>A <code>boolean</code> providing the flag indicating whether the project forum has any threads or not.</p>
     *
     * @since 1.3
     */
    private boolean hasForumThreads;

    /**
     * <p>
     * A <code>EnterpriseDashboardProjectStatDTO</code> providing statistics on
     * project.
     * </p>
     *
     * @since 1.0.1
     */
    private EnterpriseDashboardProjectStatDTO dashboardProjectStat;

    /**
     * <p>
     * Provides the general information of the project.
     * </p>
     *
     * @since 1.4
     */
    private ProjectGeneralInfoDTO projectGeneralInfo = new ProjectGeneralInfoDTO();

    /**
     * The billing accounts the project has.
     *
     * @since 1.6
     */
    private List<Project> billingAccounts;

    /**
     * <p>
     * Constructs new <code>ProjectOverviewDTO</code> instance. This
     * implementation does nothing.
     * </p>
     */
    public ProjectOverviewDTO() {
    }

    /**
     * <p>Gets the statistics for requested project.</p>
     *
     * @return a <code>ProjectStatsDTO</code> providing statistics on project.
     */
    public ProjectStatsDTO getProjectStats() {
        return this.projectStats;
    }

    /**
     * <p>Sets the statistics for single project.</p>
     *
     * @param projectStats a <code>ProjectStatsDTO</code> providing statistics on project.
     */
    public void setProjectStats(ProjectStatsDTO projectStats) {
        this.projectStats = projectStats;
    }

    /**
     * <p>Gets the details on upcoming activities for projects associated with current user.</p>
     *
     * @return a <code>LatestActivitiesDTO</code> providing the details on latest activities for projects associated
     *         with current user.
     */
    public UpcomingActivitiesDTO getUpcomingActivities() {
        return upcomingActivities;
    }

    /**
     * <p>Sets the details on upcoming activities for projects associated with current user.</p>
     *
     * @param upcomingActivities a <code>LatestActivitiesDTO</code> providing the details on latest activities for
     *                           projects associated with current user.
     */
    public void setUpcomingActivities(UpcomingActivitiesDTO upcomingActivities) {
        this.upcomingActivities = upcomingActivities;
    }

    /**
     * <p>Gets the latest activities on requested project.</p>
     *
     * @return a <code>LatestProjectActivitiesDTO</code> providing details on latest activities on requested project.
     */
    public LatestProjectActivitiesDTO getLatestProjectActivities() {
        return this.latestProjectActivities;
    }

    /**
     * <p>Sets the latest activities on requested project.</p>
     *
     * @param latestProjectActivities a <code>LatestProjectActivitiesDTO</code> providing details on latest activities on
     *                                requested project.
     */
    public void setLatestProjectActivities(LatestProjectActivitiesDTO latestProjectActivities) {
        this.latestProjectActivities = latestProjectActivities;
    }

    /**
     * Retrieves the dashboardProjectStat field.
     *
     * @return the dashboardProjectStat
     * @since 1.0.1
     */
    public EnterpriseDashboardProjectStatDTO getDashboardProjectStat() {
        return dashboardProjectStat;
    }

    /**
     * Sets the dashboardProjectStat field.
     *
     * @param dashboardProjectStat the dashboardProjectStat to set
     * @since 1.0.1
     */
    public void setDashboardProjectStat(
            EnterpriseDashboardProjectStatDTO dashboardProjectStat) {
        this.dashboardProjectStat = dashboardProjectStat;
    }

    /**
     * <p>Gets the flag indicating whether the project forum has any threads or not.</p>
     *
     * @return a <code>boolean</code> providing the flag indicating whether the project forum has any threads or not.
     * @since 1.3
     */
    public boolean getHasForumThreads() {
        return this.hasForumThreads;
    }

    /**
     * <p>Sets the flag indicating whether the project forum has any threads or not.</p>
     *
     * @param hasForumThreads a <code>boolean</code> providing the flag indicating whether the project forum has any
     *                        threads or not.
     * @since 1.3
     */
    public void setHasForumThreads(boolean hasForumThreads) {
        this.hasForumThreads = hasForumThreads;
    }

    /**
     * Gets the general information of the project.
     *
     * @return the general information of the project.
     * @since 1.4
     */
    public ProjectGeneralInfoDTO getProjectGeneralInfo() {
        return projectGeneralInfo;
    }

    /**
     * Sets the general information of the project.
     *
     * @param projectGeneralInfo the general information of the project.
     * @since 1.4
     */
    public void setProjectGeneralInfo(ProjectGeneralInfoDTO projectGeneralInfo) {
        this.projectGeneralInfo = projectGeneralInfo;
    }

    /**
     * Getter for <code>billingAccounts</code>
     *
     * @return billingAccounts
     * @since 1.6
     */
    public List<Project> getBillingAccounts() {
        return billingAccounts;
    }

    /**
     * Setter for <code>billingAcconunts</code>
     *
     * @param billingAccounts
     * @since 1.6
     */
    public void setBillingAccounts(List<Project> billingAccounts) {
        this.billingAccounts = billingAccounts;
    }

    public InputStream getExportExcel() {
        try {

            Workbook workbook = new ExcelWorkbook();
            Sheet sheet = new ExcelSheet(getProjectGeneralInfo().getProject().getName(), (ExcelWorkbook) workbook);
            insertSheetData(sheet);
            workbook.addSheet(sheet);

            // Create a new WorkBookSaver
            WorkbookSaver saver = new Biff8WorkbookSaver();
            ByteArrayOutputStream saveTo = new ByteArrayOutputStream();
            saver.save(workbook, saveTo);
            return new ByteArrayInputStream(saveTo.toByteArray());

        } catch (Throwable e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    /**
     * <p>Inserts the sheet data.</p>
     *
     * @param sheet the sheet.
     */
    private void insertSheetData(Sheet sheet) throws Exception {
        // the date format used for displaying 'completion date'
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        final String NOT_SET = "NOT SET";

        // set up the sheet header first
        Row row = sheet.getRow(1);
        int index = 1;

        row.getCell(index++).setStringValue("Field");
        row.getCell(index++).setStringValue("Value1");
        row.getCell(index++).setStringValue("Value1");
        row.getCell(index++).setStringValue("Value2");
        row.getCell(index++).setStringValue("Value3");
        row.getCell(index++).setStringValue("Value4");
        row.getCell(index++).setStringValue("Value5");
        row.getCell(index++).setStringValue("Value6");

        // insert sheet data from 2nd row
        int rowIndex = 2;

        // project name
        row = sheet.getRow(rowIndex++);

        row.getCell(1).setStringValue("Project Name");
        row.getCell(2).setStringValue(getProjectGeneralInfo().getProject().getName());

        // project description
        row = sheet.getRow(rowIndex++);
        row.getCell(1).setStringValue("Project Description");
        row.getCell(2).setStringValue(getProjectGeneralInfo().getProject().getDescription() == null
                ? NOT_SET : getProjectGeneralInfo().getProject().getDescription());

        // project type
        row = sheet.getRow(rowIndex++);
        row.getCell(1).setStringValue("Project Type");
        row.getCell(2).setStringValue(getProjectGeneralInfo().getProject().getProjectType() == null
                ? NOT_SET : getProjectGeneralInfo().getProject().getProjectType().getName());

        // project status
        row = sheet.getRow(rowIndex++);
        row.getCell(1).setStringValue("Project Status");
        row.getCell(2).setStringValue(getProjectGeneralInfo().getStatusLabel());

        // total budget
        row = sheet.getRow(rowIndex++);
        row.getCell(1).setStringValue("Total Budget");
        if(getProjectGeneralInfo().getTotalBudget() == null) {
            row.getCell(2).setStringValue(NOT_SET);
        } else {
            row.getCell(2).setNumberValue(getProjectGeneralInfo().getTotalBudget());
        }

        // Actual Cost
        row = sheet.getRow(rowIndex++);
        row.getCell(1).setStringValue("Actual Cost");
        row.getCell(2).setNumberValue(getProjectGeneralInfo().getActualCost());

        // Projected Cost
        row = sheet.getRow(rowIndex++);
        row.getCell(1).setStringValue("Projected Cost");
        row.getCell(2).setNumberValue(getProjectGeneralInfo().getProjectedCost());

        // Planned duration
        row = sheet.getRow(rowIndex++);
        row.getCell(1).setStringValue("Planned Duration");
        if(getProjectGeneralInfo().getPlannedDuration() == null) {
            row.getCell(2).setStringValue(NOT_SET);
        } else {
            row.getCell(2).setNumberValue(getProjectGeneralInfo().getPlannedDuration());
        }

        // Actual Duration
        row = sheet.getRow(rowIndex++);
        row.getCell(1).setStringValue("Actual Duration");
        row.getCell(2).setNumberValue(getProjectGeneralInfo().getActualDuration());

        // Projected Duration
        row = sheet.getRow(rowIndex++);
        row.getCell(1).setStringValue("Projected Duration");
        row.getCell(2).setNumberValue(getProjectGeneralInfo().getProjectedDuration());

        //Billing Accounts
        row = sheet.getRow(rowIndex++);
        row.getCell(1).setStringValue("Project Billings");
        if(getBillingAccounts() == null || getBillingAccounts().size() <= 0) {
            row.getCell(2).setStringValue(NOT_SET);
        } else {
            index = 2;

            for(Project billing : getBillingAccounts()) {
                row.getCell(index++).setStringValue(billing.getName());
            }
        }

        // Jira link
        row = sheet.getRow(rowIndex++);
        row.getCell(1).setStringValue("Bug Tracker");
        if(getProjectGeneralInfo().getJira() == null) {
            row.getCell(2).setStringValue(NOT_SET);
        } else {
            row.getCell(2).setStringValue(getProjectGeneralInfo().getJira());
        }

        // SVN link
        row = sheet.getRow(rowIndex++);
        row.getCell(1).setStringValue("SVN Link");
        if(getProjectGeneralInfo().getSvn() == null) {
            row.getCell(2).setStringValue(NOT_SET);
        } else {
            row.getCell(2).setStringValue(getProjectGeneralInfo().getSvn());
        }

        // Forum
        row = sheet.getRow(rowIndex++);
        row.getCell(1).setStringValue("Forum Link");
        if(getProjectGeneralInfo().getProject().getForumCategoryId() == null) {
            row.getCell(2).setStringValue(NOT_SET);
        } else {
            row.getCell(2).setStringValue("https://" + ServerConfiguration.FORUMS_SERVER_NAME + "?module=Category&categoryID=" + getProjectGeneralInfo().getProject().getForumCategoryId());
        }

        // client managers
        row = sheet.getRow(rowIndex++);
        row.getCell(1).setStringValue("Client Managers");
        if(getProjectGeneralInfo().getClientManagers() == null || getProjectGeneralInfo().getClientManagers().size() <= 0) {
            row.getCell(2).setStringValue(NOT_SET);
        } else {
            index = 2;

            for(String handle : getProjectGeneralInfo().getClientManagersHandles().values()) {
                row.getCell(index++).setStringValue(handle);
            }
        }

        // TopCoder Platform Specialists
        row = sheet.getRow(rowIndex++);
        row.getCell(1).setStringValue("TopCoder Platform Specialists");
        if(getProjectGeneralInfo().getTopcoderManagers() == null || getProjectGeneralInfo().getTopcoderManagers().size() <= 0) {
            row.getCell(2).setStringValue(NOT_SET);
        } else {
            index = 2;

            for(String handle : getProjectGeneralInfo().getTopcoderManagersHandles().values()) {
                row.getCell(index++).setStringValue(handle);
            }
        }

        // TopCoder Account Managers
        row = sheet.getRow(rowIndex++);
        row.getCell(1).setStringValue("TopCoder Account Managers");
        if(getProjectGeneralInfo().getAccountManagers() == null || getProjectGeneralInfo().getAccountManagers().size() <= 0) {
            row.getCell(2).setStringValue(NOT_SET);
        } else {
            index = 2;

            for(String handle : getProjectGeneralInfo().getAccountManagersHandles().values()) {
                row.getCell(index++).setStringValue(handle);
            }
        }

        // Copilots
        row = sheet.getRow(rowIndex++);
        row.getCell(1).setStringValue("Copilots");
        if(getProjectGeneralInfo().getCopilotHandles() == null || getProjectGeneralInfo().getCopilotHandles().size() <= 0) {
            row.getCell(2).setStringValue(NOT_SET);
        } else {
            index = 2;

            for(String handle : getProjectGeneralInfo().getCopilotHandles().values()) {
                row.getCell(index++).setStringValue(handle);
            }
        }

        // Business Impact
        row = sheet.getRow(rowIndex++);
        row.getCell(1).setStringValue("Business Impact");
        if(getProjectGeneralInfo().getBusinessImpactRating() == null) {
            row.getCell(2).setStringValue(NOT_SET);
        } else {
            row.getCell(2).setNumberValue(getProjectGeneralInfo().getBusinessImpactRating());
        }

        // Risk Level
        row = sheet.getRow(rowIndex++);
        row.getCell(1).setStringValue("Risk Level");
        if(getProjectGeneralInfo().getRiskLevelRating() == null) {
            row.getCell(2).setStringValue(NOT_SET);
        } else {
            row.getCell(2).setNumberValue(getProjectGeneralInfo().getRiskLevelRating());
        }

        // Cost Level
        row = sheet.getRow(rowIndex++);
        row.getCell(1).setStringValue("Cost Level");
        if(getProjectGeneralInfo().getCostRating() == null) {
            row.getCell(2).setStringValue(NOT_SET);
        } else {
            row.getCell(2).setNumberValue(getProjectGeneralInfo().getCostRating());
        }

        // Difficulty
        row = sheet.getRow(rowIndex++);
        row.getCell(1).setStringValue("Difficulty");
        if(getProjectGeneralInfo().getDifficultyRating() == null) {
            row.getCell(2).setStringValue(NOT_SET);
        } else {
            row.getCell(2).setNumberValue(getProjectGeneralInfo().getDifficultyRating());
        }

        // ROI
        row = sheet.getRow(rowIndex++);
        row.getCell(1).setStringValue("ROI");
        if(getProjectGeneralInfo().getRoiRating() == null) {
            row.getCell(2).setStringValue(NOT_SET);
        } else {
            row.getCell(2).setNumberValue(getProjectGeneralInfo().getRoiRating());
        }

        // additional information
        final Map<String, List<String>> additionalProjectInfo = getProjectGeneralInfo().getAdditionalProjectInfo();

        for(String fieldName : additionalProjectInfo.keySet()) {
            row = sheet.getRow(rowIndex++);
            row.getCell(1).setStringValue(fieldName);

            List<String> values = additionalProjectInfo.get(fieldName);

            if(values == null || values.size() <= 0) {
                row.getCell(2).setStringValue(NOT_SET);
            } else {
                index = 2;

                for(String value : values) {
                    row.getCell(index++).setStringValue(value);
                }
            }
        }

    }

    public String getExportFileName() {
        return this.projectGeneralInfo.getProject().getName().replaceAll(" ", "_") + ".xls";
    }
}
