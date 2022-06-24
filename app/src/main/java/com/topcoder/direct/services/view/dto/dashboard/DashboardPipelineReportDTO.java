/*
 * Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard;

import com.topcoder.direct.services.view.dto.CommonDTO;
import com.topcoder.direct.services.view.dto.Report;
import com.topcoder.direct.services.view.dto.ReportType;
import com.topcoder.direct.services.view.dto.contest.ContestStatus;
import com.topcoder.direct.services.view.dto.contest.ContestType;
import com.topcoder.direct.services.view.dto.dashboard.pipeline.PipelineLaunchedContestsDTO;
import com.topcoder.direct.services.view.dto.dashboard.pipeline.PipelineNumericalFilterType;
import com.topcoder.direct.services.view.dto.dashboard.pipeline.PipelineSummaryDTO;
import com.topcoder.direct.services.view.util.JSPHelper;
import com.topcoder.excel.Row;
import com.topcoder.excel.Sheet;
import com.topcoder.excel.Workbook;
import com.topcoder.excel.impl.ExcelSheet;
import com.topcoder.excel.impl.ExcelWorkbook;
import com.topcoder.excel.output.Biff8WorkbookSaver;
import com.topcoder.excel.output.WorkbookSaver;
import com.topcoder.excel.output.WorkbookSavingException;
import com.topcoder.service.pipeline.CommonPipelineData;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>A <code>DTO</code> for Pipeline report view.</p>
 *
 * <p>
 *  Version 1.1 (Release Assembly - TC Cockpit Report Filters Group By Metadata Feature and Coordination Improvement) change notes:
 *   <ol>
 *       <li>Add {@link #groupKeys}, {@link #groupValues}</li> and therir getters and setters.
 *   </ol>
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.1
 */
public class DashboardPipelineReportDTO extends CommonDTO implements Serializable, Report {

    /**
     * <p>A static <code>Map</code> mapping the existing contest statuses to their textual presentations.</p>
     */
    private static final Map<ContestStatus, String> CONTEST_STATUSES;

    /**
     * <p>A static <code>Map</code> mapping the existing contest types to their textual presentations.</p>
     */
    private static final Map<ContestType, String> CONTEST_TYPES;

    /**
     * <p>A static <code>Map</code> mapping the existing numerical filter types to their textual presentations.</p>
     */
    private static final Map<PipelineNumericalFilterType, String> NUMERICAL_FILTER_TYPES;

    /**
     * <p>This static initializer initializes the static maps.</p>
     */
    static {
        CONTEST_STATUSES = new LinkedHashMap<ContestStatus, String>();
        CONTEST_STATUSES.put(ContestStatus.DRAFT, "Draft");
        CONTEST_STATUSES.put(ContestStatus.SCHEDULED, "Scheduled");
        CONTEST_STATUSES.put(ContestStatus.ACTIVE, "Active");
        CONTEST_STATUSES.put(ContestStatus.COMPLETED, "Completed");
		CONTEST_STATUSES.put(ContestStatus.FAILED, "Failed");
        CONTEST_STATUSES.put(ContestStatus.CANCELLED, "Cancelled");

        CONTEST_TYPES = new LinkedHashMap<ContestType, String>();
                
        CONTEST_TYPES.put(ContestType.WEB_DESIGN, "Web Design");
        CONTEST_TYPES.put(ContestType.LOGO_DESIGN, "Logo Design");
        CONTEST_TYPES.put(ContestType.BANNERS_ICONS, "Banners/Icons");
        CONTEST_TYPES.put(ContestType.APP_FRONT_END_DESIGN, "Application Front-End Design");
        CONTEST_TYPES.put(ContestType.WIDGET_MOBILE_SCREEN_DESIGN, "Widget or Mobile Screen Design");
        CONTEST_TYPES.put(ContestType.FRONT_END_FLASH, "Front-End Flash");
        CONTEST_TYPES.put(ContestType.PRINT_DESIGN, "Print/Presentation");
        CONTEST_TYPES.put(ContestType.WIREFRAME, "Wireframes");
        CONTEST_TYPES.put(ContestType.IDEA_GENERATION, "Idea Generation");
        CONTEST_TYPES.put(ContestType.OTHER, "Other Studio");

        CONTEST_TYPES.put(ContestType.CONCEPTUALIZATION, "Conceptualization");
        CONTEST_TYPES.put(ContestType.SPECIFICATION, "Software Specification");
        CONTEST_TYPES.put(ContestType.ARCHITECTURE, "Architecture");
        CONTEST_TYPES.put(ContestType.COMPONENT_DESIGN, "Component Design");
        CONTEST_TYPES.put(ContestType.COMPONENT_DEVELOPMENT, "Component Development");
        CONTEST_TYPES.put(ContestType.RIA_COMPONENT, "RIA Component");
        CONTEST_TYPES.put(ContestType.RIA_BUILD, "RIA Build");
        CONTEST_TYPES.put(ContestType.UI_PROTOTYPE, "UI Prototype");
        CONTEST_TYPES.put(ContestType.ASSEMBLY, "Software Assembly");
        CONTEST_TYPES.put(ContestType.TEST_SUITES, "Test Suites");
        CONTEST_TYPES.put(ContestType.TEST_SCENARIOS, "Test Scenarios");
        CONTEST_TYPES.put(ContestType.COPILOT_POSTING, "Copilot Posting");
        CONTEST_TYPES.put(ContestType.CONTENT_CREATION, "Content Creation");
        CONTEST_TYPES.put(ContestType.REPORTING, "Reporting");
		CONTEST_TYPES.put(ContestType.MARATHON_MATCH, "Marathon Match");
		CONTEST_TYPES.put(ContestType.BUG_HUNT, "Bug Hunt");
		CONTEST_TYPES.put(ContestType.FIRST2FINISH, "First2Finish");
		CONTEST_TYPES.put(ContestType.CODE, "Code");


        NUMERICAL_FILTER_TYPES = new LinkedHashMap<PipelineNumericalFilterType, String>();
		NUMERICAL_FILTER_TYPES.put(PipelineNumericalFilterType.NONE, "None");
        NUMERICAL_FILTER_TYPES.put(PipelineNumericalFilterType.PRIZE, "Member Costs");
        NUMERICAL_FILTER_TYPES.put(PipelineNumericalFilterType.CONTEST_FEE, "Contest Fee");
        NUMERICAL_FILTER_TYPES.put(PipelineNumericalFilterType.DURATION, "Duration");
    }

    /**
     * <p>A <code>Map</code> providing the client id and names for the clients available to user.</p>
     */
    private Map<Long, String> clients;

    /**
     * <p>A <code>Map</code> provided the group by id and names for the chosen client if to user</p>
     * @since 1.1
     */
    private Map<Long, String> groupKeys;

    /**
     * <p>
     *  A <code>Set</code> provided the group values for the chosen group id to user.
     * </p>
     * @since 1.1
     */
    private Set<String> groupValues;

    /**
     * <p>A <code>List</code> providing the data for weekly summaries.</p>
     */
    private List<PipelineSummaryDTO> summaries;

    /**
     * <p>A <code>List</code> providing the details for the contests for Pipeline report.</p>
     */
    private List<CommonPipelineData> contests;

    /**
     * The project filter value applied on the pipeline details.
     */
    private Map<CommonPipelineData, String> contestsProjectFilterValues;

    /**
     * <p>A <code>boolean</code> providing the flag indicating whether the pipeline report data is to be calculated and
     * displayed.</p>
     */
    private boolean showJustForm;

    /**
     * <p>A <code>List</code> providing the stats on contests scheduled/launched by clients.</p>
     */
    private List<PipelineLaunchedContestsDTO> clientScheduledLaunchedContestStats;

    /**
     * <p>A <code>List</code> providing the stats on contests scheduled/launched by persons.</p>
     */
    private List<PipelineLaunchedContestsDTO> personScheduledLaunchedContestStats;

    /**
     * <p>A <code>List</code> providing the stats on contests scheduled/launched by billing projects.</p>
     */
    private List<PipelineLaunchedContestsDTO> billingprojectScheduledLaunchedContestStats;

    /**
     * <p>A <code>List</code> providing the stats on contests scheduled/launched by copilots.</p>
     */
    private List<PipelineLaunchedContestsDTO> copilotScheduledLaunchedContestStats;

    /**
     * <p>A <code>List</code> providing the stats on contests scheduled/launched by categories.</p>
     */
    private List<PipelineLaunchedContestsDTO> categoriesScheduledLaunchedContestStats;

    /**
     * <p>A <code>List</code> providing the stats on contests scheduled/launched by projects.</p>
     */
    private List<PipelineLaunchedContestsDTO> projectScheduledLaunchedContestStats;

    /**
     * <p>Constructs new <code>DashboardPipelineReportDTO</code> instance. This implementation does nothing.</p>
     */
    public DashboardPipelineReportDTO() {
    }

    /**
     * <p>Gets the stats on contests scheduled/launched by persons.</p>
     *
     * @return a <code>List</code> providing the stats on contests scheduled/launched by persons.
     */
    public List<PipelineLaunchedContestsDTO> getPersonScheduledLaunchedContestStats() {
        return this.personScheduledLaunchedContestStats;
    }

    /**
     * <p>Sets the stats on contests scheduled/launched by persons.</p>
     *
     * @param personScheduledLaunchedContestStats
     *         a <code>List</code> providing the stats on contests scheduled/launched by persons.
     */
    public void setPersonScheduledLaunchedContestStats(
        List<PipelineLaunchedContestsDTO> personScheduledLaunchedContestStats) {
        this.personScheduledLaunchedContestStats = personScheduledLaunchedContestStats;
    }

    /**
     * <p>Gets the stats on contests scheduled/launched by clients.</p>
     *
     * @return a <code>List</code> providing the stats on contests scheduled/launched by clients.
     */
    public List<PipelineLaunchedContestsDTO> getClientScheduledLaunchedContestStats() {
        return this.clientScheduledLaunchedContestStats;
    }

    /**
     * <p>Sets the stats on contests scheduled/launched by clients.</p>
     *
     * @param clientScheduledLaunchedContestStats
     *         a <code>List</code> providing the stats on contests scheduled/launched by clients.
     */
    public void setClientScheduledLaunchedContestStats(
        List<PipelineLaunchedContestsDTO> clientScheduledLaunchedContestStats) {
        this.clientScheduledLaunchedContestStats = clientScheduledLaunchedContestStats;
    }

    /**
     * <p>Gets the stats on contests scheduled/launched by copilots.</p>
     *
     * @return a <code>List</code> providing the stats on contests scheduled/launched by copilots.
     */
    public List<PipelineLaunchedContestsDTO> getCopilotScheduledLaunchedContestStats() {
        return this.copilotScheduledLaunchedContestStats;
    }

    /**
     * <p>Sets the stats on contests scheduled/launched by copilots.</p>
     *
     * @param copilotScheduledLaunchedContestStats
     *         a <code>List</code> providing the stats on contests scheduled/launched by copilots.
     */
    public void setCopilotScheduledLaunchedContestStats(
        List<PipelineLaunchedContestsDTO> copilotScheduledLaunchedContestStats) {
        this.copilotScheduledLaunchedContestStats = copilotScheduledLaunchedContestStats;
    }

    /**
     * <p>Gets the stats on contests scheduled/launched by projects.</p>
     *
     * @return a <code>List</code> providing the stats on contests scheduled/launched by projects.
     */
    public List<PipelineLaunchedContestsDTO> getProjectScheduledLaunchedContestStats() {
        return this.projectScheduledLaunchedContestStats;
    }

    /**
     * <p>Sets the stats on contests scheduled/launched by projects.</p>
     *
     * @param projectScheduledLaunchedContestStats
     *         a <code>List</code> providing the stats on contests scheduled/launched by projects.
     */
    public void setProjectScheduledLaunchedContestStats(
        List<PipelineLaunchedContestsDTO> projectScheduledLaunchedContestStats) {
        this.projectScheduledLaunchedContestStats = projectScheduledLaunchedContestStats;
    }

    /**
     * <p>Gets the stats on contests scheduled/launched by categories.</p>
     *
     * @return a <code>List</code> providing the stats on contests scheduled/launched by categories.
     */
    public List<PipelineLaunchedContestsDTO> getCategoriesScheduledLaunchedContestStats() {
        return this.categoriesScheduledLaunchedContestStats;
    }

    /**
     * <p>Sets the stats on contests scheduled/launched by categories.</p>
     *
     * @param categoriesScheduledLaunchedContestStats
     *         a <code>List</code> providing the stats on contests scheduled/launched by categories.
     */
    public void setCategoriesScheduledLaunchedContestStats(
        List<PipelineLaunchedContestsDTO> categoriesScheduledLaunchedContestStats) {
        this.categoriesScheduledLaunchedContestStats = categoriesScheduledLaunchedContestStats;
    }

    /**
     * <p>Gets the stats on contests scheduled/launched by billing projects.</p>
     *
     * @return a <code>List</code> providing the stats on contests scheduled/launched by billing projects.
     */
    public List<PipelineLaunchedContestsDTO> getBillingprojectScheduledLaunchedContestStats() {
        return this.billingprojectScheduledLaunchedContestStats;
    }

    /**
     * <p>Sets the stats on contests scheduled/launched by billing projects.</p>
     *
     * @param billingprojectScheduledLaunchedContestStats
     *         a <code>List</code> providing the stats on contests scheduled/launched by billing projects.
     */
    public void setBillingprojectScheduledLaunchedContestStats(
        List<PipelineLaunchedContestsDTO> billingprojectScheduledLaunchedContestStats) {
        this.billingprojectScheduledLaunchedContestStats = billingprojectScheduledLaunchedContestStats;
    }

    /**
     * <p>Gets the options for drop-down with contest types.</p>
     *
     * @return a <code>Map</code> mapping the existing contest type IDs to contest type names.
     */
    public Map<ContestType, String> getContestTypeOptions() {
        return CONTEST_TYPES;
    }

    /**
     * <p>Gets the options for drop-down with contest statuses.</p>
     *
     * @return a <code>Map</code> mapping the existing contest status IDs to contest status names.
     */
    public Map<ContestStatus, String> getContestStatusOptions() {
        return CONTEST_STATUSES;
    }

    /**
     * <p>Gets the options for drop-down with numerical filter types.</p>
     *
     * @return a <code>Map</code> mapping the existing numerical filter types to contest status names.
     */
    public Map<PipelineNumericalFilterType, String> getNumericalFilterOptions() {
        return NUMERICAL_FILTER_TYPES;
    }

    /**
     * <p>Gets the names for the clients available to user.</p>
     *
     * @return a <code>Map</code> providing the ids and names for the clients available to user.
     */
    public Map<Long, String> getClients() {
        return this.clients;
    }

    /**
     * <p>Sets the names for the clients available to user.</p>
     *
     * @param clients a <code>Map</code> providing the ids and names for the clients available to user.
     */
    public void setClients(Map<Long, String> clients) {
        this.clients = clients;
    }

    /**
     * Gets the group keys.
     *
     * @return the group keys.
     * @since 1.1
     */
    public Map<Long, String> getGroupKeys() {
        return groupKeys;
    }

    /**
     * Sets the group keys.
     *
     * @param groupKeys the group keys.
     * @since 1.1
     */
    public void setGroupKeys(Map<Long, String> groupKeys) {
        this.groupKeys = groupKeys;
    }

    /**
     * Gets the group values.
     *
     * @return the group values.
     * @since 1.1
     */
    public Set<String> getGroupValues() {
        return groupValues;
    }

    /**
     * Sets the group values.
     *
     * @param groupValues the groups values.
     */
    public void setGroupValues(Set<String> groupValues) {
        this.groupValues = groupValues;
    }

    /**
     * <p>Gets the type of this report.</p>
     *
     * @return a <code>ReportType</code> specifying the type of this report.
     */
    public ReportType getReportType() {
        return ReportType.PIPELINE;
    }

    /**
     * <p>Gets the data for weekly summaries.</p>
     *
     * @return a <code>List</code> providing the data for weekly summaries.
     */
    public List<PipelineSummaryDTO> getSummaries() {
        return this.summaries;
    }

    /**
     * <p>Sets the data for weekly summaries.</p>
     *
     * @param summaries a <code>List</code> providing the data for weekly summaries.
     */
    public void setSummaries(List<PipelineSummaryDTO> summaries) {
        this.summaries = summaries;
    }

    /**
     * <p>Gets the details for the contests for Pipeline report.</p>
     *
     * @return a <code>List</code> providing the details for the contests for Pipeline report.
     */
    public List<CommonPipelineData> getContests() {
        return this.contests;
    }

    /**
     * <p>Sets the details for the contests for Pipeline report.</p>
     *
     * @param contests a <code>List</code> providing the details for the contests for Pipeline report.
     */
    public void setContests(List<CommonPipelineData> contests) {
        this.contests = contests;
    }

    /**
     * <p>Gets the flag indicating whether the pipeline report data is to be calculated and displayed.</p>
     *
     * @return a <code>boolean</code> providing the flag indicating whether the pipeline report data is to be calculated
     *         and displayed.
     */
    public boolean getShowJustForm() {
        return this.showJustForm;
    }

    /**
     * <p>Sets the flag indicating whether the pipeline report data is to be calculated and displayed.</p>
     *
     * @param showJustForm a <code>boolean</code> providing the flag indicating whether the pipeline report data is to
     *                     be calculated and displayed.
     */
    public void setShowJustForm(boolean showJustForm) {
        this.showJustForm = showJustForm;
    }

    /**
     * Gets the project filter mappings to pipeline details.
     *
     * @return  the project filter mappings to contest details.
     */
    public Map<CommonPipelineData, String> getContestsProjectFilterValues() {
        return contestsProjectFilterValues;
    }

    /**
     * Sets the project filter mappings to pipeline details.
     *
     * @param contestsProjectFilterValues the project filter mappings to pipeline details.
     */
    public void setContestsProjectFilterValues(Map<CommonPipelineData, String> contestsProjectFilterValues) {
        this.contestsProjectFilterValues = contestsProjectFilterValues;
    }

    /**
     * <p>Gets the workbook download stream.</p>
     *
     * @return the download stream.
     * @throws WorkbookSavingException if any error occurs when generating workbook.
     * @throws IOException if an I/O error occurs.
     */
    public InputStream getInputStream() throws WorkbookSavingException, IOException {
        try {
            Workbook workbook = new ExcelWorkbook();
            Sheet sheet = new ExcelSheet("Pipeline Details", (ExcelWorkbook) workbook);
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
    private void insertSheetData(Sheet sheet) {
        DateFormat dateFormatter1 = new SimpleDateFormat("yyyy-MM-dd (EEE)");
        DateFormat dateFormatter2 = new SimpleDateFormat("yyyy-MM-dd");
        NumberFormat moneyFormatter = new DecimalFormat("###,##0.00");

        NumberFormat ratingFormatter = new DecimalFormat("#######0");

        Row row = sheet.getRow(1);
        row.getCell(1).setStringValue("Date");
        row.getCell(2).setStringValue("Member Costs");
        row.getCell(3).setStringValue("Contest Fee");
        row.getCell(4).setStringValue("Category");
        row.getCell(5).setStringValue("Client");
        row.getCell(6).setStringValue("Copilot");
        row.getCell(7).setStringValue("Billing");
        row.getCell(8).setStringValue("Project");
        row.getCell(9).setStringValue("Name");
        row.getCell(10).setStringValue("Status");
        row.getCell(11).setStringValue("Repost");
        row.getCell(12).setStringValue("Resources");
        row.getCell(13).setStringValue("Added");
        row.getCell(14).setStringValue("Changed");

        int rowIndex = 2;
        for (CommonPipelineData contest : getContests()) {
            row = sheet.getRow(rowIndex++);

            row.getCell(1)
                .setStringValue(dateFormatter1.format(contest.getStartDate().toGregorianCalendar().getTime()));
            row.getCell(2).setStringValue(moneyFormatter.format(JSPHelper.getMemberCosts(contest)));
            if (contest.getContestFee() != null) {
                row.getCell(3).setStringValue(moneyFormatter.format(contest.getContestFee()));
            }
            row.getCell(4).setStringValue(contest.getContestCategory());
            row.getCell(5).setStringValue(contest.getClientName());

            String[] copilots = contest.getCopilots();
            if (copilots != null && copilots.length > 0) {
                StringBuilder b = new StringBuilder();
                for (int i = 0; i < copilots.length; i++) {
                    String copilot = copilots[i];
                    if (i > 0) {
                        b.append(" ");
                    }
                    b.append(copilot);
                }
                String cp = b.toString();
                if (!cp.trim().equals(""))
                {
                    row.getCell(6).setStringValue(cp);
                }
                
            }
            if (contest.getCpname() != null && !contest.getCpname().trim().equals(""))
            {
                 row.getCell(7).setStringValue(contest.getCpname());
            }
           

            row.getCell(8).setStringValue(contest.getPname());
            row.getCell(9).setStringValue(contest.getCname());
            row.getCell(10).setStringValue(contest.getSname());

            if (contest.getWasReposted() != null && contest.getWasReposted())
            {
                row.getCell(11).setStringValue("Yes");
            }
            else 
            {
                row.getCell(11).setStringValue("No");
            }

            StringBuilder resourceBuilder = new StringBuilder();
            
            if (contest.getManager() != null) {
                if (resourceBuilder.length() > 0) {
                    resourceBuilder.append(" ");
                }
                resourceBuilder.append(contest.getManager());
            }
           

            String resources = resourceBuilder.toString();
            if (resources.trim().length() > 0) {
                row.getCell(12).setStringValue(resources);
            }

            if (contest.getCreateTime() != null) {
                Date createTime = contest.getCreateTime().toGregorianCalendar().getTime();
                row.getCell(13).setStringValue(dateFormatter2.format(createTime));
            }

            if (contest.getModifyTime() != null) {
                String changed = JSPHelper.getPastTimeText(contest.getModifyTime().toGregorianCalendar().getTime());
                if (changed != null && changed.trim().length() > 0) {
                    row.getCell(14).setStringValue(changed);
                }
            }
        }
    }
}
