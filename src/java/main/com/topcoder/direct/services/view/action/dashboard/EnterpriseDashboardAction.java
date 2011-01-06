/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.dashboard;

import com.topcoder.clients.model.AuditableEntity;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.contest.ContestDashboardDTO;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.dto.dashboard.DashboardStatusColor;
import com.topcoder.direct.services.view.dto.dashboard.EnterpriseDashboardAggregatedStatDTO;
import com.topcoder.direct.services.view.dto.dashboard.EnterpriseDashboardDTO;
import com.topcoder.direct.services.view.dto.dashboard.EnterpriseDashboardDetailedProjectStatDTO;
import com.topcoder.direct.services.view.dto.dashboard.EnterpriseDashboardProjectStatDTO;
import com.topcoder.direct.services.view.dto.dashboard.EnterpriseDashboardStatPeriodType;
import com.topcoder.direct.services.view.dto.dashboard.EnterpriseDashboardStatType;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.dto.project.ProjectContestDTO;
import com.topcoder.direct.services.view.dto.project.ProjectContestsListDTO;
import com.topcoder.direct.services.view.form.EnterpriseDashboardForm;
import com.topcoder.direct.services.view.util.DashboardHelper;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.security.TCSubject;
import com.topcoder.service.project.ProjectData;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * A <code>Struts</code> action to be used for handling requests for viewing the
 * <code>Enterprise Dashboard</code> page.
 * </p>
 * <p>
 * Version 1.0.1 - Direct - Project Dashboard Assembly Change Note
 * <ul>
 * <li>Added {@link #setProjectStatusColor()} method. Updated
 * {@link #executeAction()} method to set project status color.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.0.2 (Cockpit - Enterprise Dashboard 2 Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Updated the stats calculation logic to collect stats per halves of the year slices.</li>
 *     <li>Added logic for handling AJAX calls.</li>
 *   </ol>
 * </p>
 * 
 * @author isv, TCSASSEMBLER
 * @version 1.0.2
 */
public class EnterpriseDashboardAction extends BaseDirectStrutsAction {

    /**
     * <p>A <code>String</code> array listing the names of the months to be used for textual presentations of the months
     * on the chart.</p>
     */
    private static final String[] MONTH_NAMES = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct",
                                                 "Nov", "Dec"};

    /**
     * <p>A <code>String</code> array listing the names of the quarters to be used for textual presentations of the
     * quarters on the chart.</p>
     */
    private static final String[] QUARTER_NAMES = {"Q1", "Q2", "Q3", "Q4"};

    /**
     * <p>A <code>SessionData</code> providing interface to current session.</p>
     */
    private SessionData sessionData;

    /**
     * <p>A <code>DashboardDTO</code> providing the viewData for displaying by <code>Dashboard</code> view.</p>
     */
    private EnterpriseDashboardDTO viewData;

    /**
     * <p>A <code>EnterpriseDashboardForm</code> providing the form parameters submitted by user.</p>
     */
    private EnterpriseDashboardForm formData;

    /**
     * <p>A <code>boolean</code> providing the flag indicating if this action instance is to handle AJAX calls or
     * not.</p>
     * 
     * @since 1.0.2
     */
    private boolean isAJAX;

    /**
     * <p>Constructs new <code>EnterpriseDashboardAction</code> instance. This implementation does nothing.</p>
     */
    public EnterpriseDashboardAction() {
        this.viewData = new EnterpriseDashboardDTO();
        this.formData = new EnterpriseDashboardForm();
    }

    /**
     * <p>Gets the flag indicating if this action instance is to handle AJAX calls or not.</p>
     *
     * @return a <code>boolean</code> providing the flag indicating if this action instance is to handle AJAX calls or
     *         not.
     * @since 1.0.2
     */
    public boolean getIsAJAX() {
        return this.isAJAX;
    }

    /**
     * <p>Sets the flag indicating if this action instance is to handle AJAX calls or not.</p>
     *
     * @param isAJAX a <code>boolean</code> providing the flag indicating if this action instance is to handle AJAX
     *               calls or not.
     * @since 1.0.2
     */
    public void setIsAJAX(boolean isAJAX) {
        this.isAJAX = isAJAX;
    }

    /**
     * <p>Gets the current session associated with the incoming request from client.</p>
     *
     * @return a <code>SessionData</code> providing access to current session.
     */
    public SessionData getSessionData() {
        return this.sessionData;
    }

    /**
     * <p>Gets the form data.</p>
     *
     * @return an <code>EnterpriseDashboardForm</code> providing the data for form submitted by user..
     */
    public EnterpriseDashboardForm getFormData() {
        return this.formData;
    }

    /**
     * <p>Gets the viewData to be displayed by <code>Enterprise Dashboard</code> view.</p>
     *
     * @return a <code>EnterpriseDashboardDTO</code> providing the view data for displaying by <code>Enterprise
     *         Dashboard</code> view.
     */
    public EnterpriseDashboardDTO getViewData() {
        return this.viewData;
    }

    /**
     * <p>Processes the incoming request.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        // Get current session
        HttpServletRequest request = DirectUtils.getServletRequest();
        this.sessionData = new SessionData(request.getSession());
        TCSubject currentUser = getCurrentUser();

        boolean isAJAXCall = getIsAJAX();
        boolean isFirstCall = !isAJAXCall;

        // Get the list of available project categories
        Map<Long, String> projectCategories = DataProvider.getAllProjectCategories();
        getViewData().setProjectCategories(projectCategories);

        // Get the list of TC Direct Projects accessible to current user
        List<ProjectData> tcDirectProjects = getProjects();

        // Get the overall stats for user projects
        List<EnterpriseDashboardProjectStatDTO> enterpriseProjectStats
            = DataProvider.getEnterpriseProjectStats(tcDirectProjects);
        getViewData().setProjects(enterpriseProjectStats);
        
        // Get the list of all available billing accounts for user
        List<Project> clientBillingProjects = getProjectServiceFacade().getClientProjectsByUser(currentUser);
        getViewData().setClientBillingProjects(convertToMap(clientBillingProjects));
        
        // Get the list of available client accounts
        Map<Long, String> clientAccountsMap = new LinkedHashMap<Long, String>();
        for (Project clientBillingAccount : clientBillingProjects) {
            Client client = clientBillingAccount.getClient();
            clientAccountsMap.put(client.getId(), client.getName());
        }
        getViewData().setClientAccounts(clientAccountsMap);

        // Analyze form parameters
        EnterpriseDashboardForm form = getFormData();
        long[] projectIds = form.getProjectIds();
        long[] categoryIds = form.getProjectCategoryIds();
        long[] billingAccountIds = form.getBillingAccountIds();
        long[] customerIds = form.getCustomerIds();
        Date startDate = DirectUtils.getDate(form.getStartDate());
        Date endDate = DirectUtils.getDate(form.getEndDate());

        // If project category IDs are not specified then use all project category Ids
        boolean categoryIdsAreSet = (categoryIds != null) && (categoryIds.length > 0); 
        if (isFirstCall && !categoryIdsAreSet) {
            Set<Long> keySet = projectCategories.keySet();
            int index = 0;
            categoryIds = new long[keySet.size()];
            for (Long id : keySet) {
                categoryIds[index++] = id;
            }
            form.setProjectCategoryIds(categoryIds);
            categoryIdsAreSet = true;
        }
        
        // If billing account IDs are not specified then use all billing account Ids
        boolean billingAccountIdsAreSet = (billingAccountIds != null) && (billingAccountIds.length > 0); 
        if (isFirstCall && !billingAccountIdsAreSet) {
            int index = 0;
            billingAccountIds = new long[clientBillingProjects.size()];
            for (Project clientBillingProject : clientBillingProjects) {
                billingAccountIds[index++] = clientBillingProject.getId();
            }
            form.setBillingAccountIds(billingAccountIds);
            billingAccountIdsAreSet = true;
        }
        
        // If client account IDs are not specified then use all client account Ids
        boolean customerIdsAreSet = (customerIds != null) && (customerIds.length > 0); 
        if (isFirstCall && !customerIdsAreSet) {
            int index = 0;
            customerIds = new long[clientAccountsMap.size()];
            for (long clientId : clientAccountsMap.keySet()) {
                customerIds[index++] = clientId;
            }
            form.setCustomerIds(customerIds);
            customerIdsAreSet = true;
        }

        // If project IDs are not specified then use the first available from the projects assigned to user
        boolean projectIdsAreSet = (projectIds != null) && (projectIds.length > 0); 
        if (isFirstCall && !projectIdsAreSet) {
            if (!enterpriseProjectStats.isEmpty()) {
                projectIds = new long[] {enterpriseProjectStats.get(0).getProject().getId()};
                form.setProjectIds(projectIds);
                projectIdsAreSet = true;
            }
        }

        // If start date is not set then use date for half of a year before current time
        SimpleDateFormat dateFormat = new SimpleDateFormat(DirectUtils.DATE_FORMAT);
        Date now = new Date();
        if (startDate == null) {
            startDate = new Date(now.getTime() - (182 * 24 * 3600 * 1000L));
            form.setStartDate(dateFormat.format(startDate));
        }

        // If end date is not set then use current time
        if (endDate == null) {
            endDate = now;
            form.setEndDate(dateFormat.format(endDate));
        }

        // Validate the dates range
        if (startDate.compareTo(endDate) > 0) {
            addActionError("Start date must not be after end date");
            return;
        }

        // Get the detailed stats for specific project, categories and time frame (only if project is specified)
        if (projectIdsAreSet && categoryIdsAreSet && billingAccountIdsAreSet && customerIdsAreSet) {
            Map<String, List<EnterpriseDashboardAggregatedStatDTO>> costStats = createEmptyStats();
            Map<String, List<EnterpriseDashboardAggregatedStatDTO>> durationStats = createEmptyStats();
            Map<String, List<EnterpriseDashboardAggregatedStatDTO>> fulfillmentStats = createEmptyStats();
            EnterpriseDashboardAggregatedStatDTO averageCustomerCost = new EnterpriseDashboardAggregatedStatDTO();
            EnterpriseDashboardAggregatedStatDTO averageCustomerDuration = new EnterpriseDashboardAggregatedStatDTO();
            EnterpriseDashboardAggregatedStatDTO averageCustomerFulfillment 
                = new EnterpriseDashboardAggregatedStatDTO();
            
            // Get and aggregate the average calculated values for client
            List<EnterpriseDashboardDetailedProjectStatDTO> clientStats
                = DataProvider.getEnterpriseStatsForProject(projectIds, categoryIds, startDate, endDate, customerIds,
                                                            billingAccountIds);
            for (EnterpriseDashboardDetailedProjectStatDTO stat : clientStats) {
                Date statDate = stat.getDate();
                EnterpriseDashboardStatType statType = stat.getStatsType();

                Map<String, List<EnterpriseDashboardAggregatedStatDTO>> targetStats;
                if (statType == EnterpriseDashboardStatType.COST) {
                    targetStats = costStats;
                    averageCustomerCost.aggregateClientValue(stat.getValue(), stat.getContestsCount());
                } else if (statType == EnterpriseDashboardStatType.DURATION) {
                    targetStats = durationStats;
                    averageCustomerDuration.aggregateClientValue(stat.getValue(), stat.getContestsCount());
                } else {
                    targetStats = fulfillmentStats;
                    averageCustomerFulfillment.aggregateClientValue(stat.getValue(), stat.getContestsCount());
                }

                aggregateClientStat(EnterpriseDashboardStatPeriodType.WEEK, getWeekLabel(statDate), stat, targetStats);
                aggregateClientStat(EnterpriseDashboardStatPeriodType.MONTH, getMonthLabel(statDate), stat,
                                    targetStats);
                aggregateClientStat(EnterpriseDashboardStatPeriodType.QUARTER, getQuarterLabel(statDate), stat,
                                    targetStats);
                aggregateClientStat(EnterpriseDashboardStatPeriodType.YEAR, getYearLabel(statDate), stat, targetStats);
            }

            // Get and aggregate the average calculated values for all projects
            List<EnterpriseDashboardDetailedProjectStatDTO> overallStats
                = DataProvider.getEnterpriseStatsForAllProjects(categoryIds, startDate, endDate);
            for (EnterpriseDashboardDetailedProjectStatDTO stat : overallStats) {
                Date statDate = stat.getDate();
                EnterpriseDashboardStatType statType = stat.getStatsType();

                Map<String, List<EnterpriseDashboardAggregatedStatDTO>> targetStats;
                if (statType == EnterpriseDashboardStatType.COST) {
                    targetStats = costStats;
                } else if (statType == EnterpriseDashboardStatType.DURATION) {
                    targetStats = durationStats;
                } else {
                    targetStats = fulfillmentStats;
                }

                aggregateOverallStat(EnterpriseDashboardStatPeriodType.WEEK, getWeekLabel(statDate), stat, targetStats);
                aggregateOverallStat(EnterpriseDashboardStatPeriodType.MONTH, getMonthLabel(statDate), stat,
                                     targetStats);
                aggregateOverallStat(EnterpriseDashboardStatPeriodType.QUARTER, getQuarterLabel(statDate), stat,
                                     targetStats);
                aggregateOverallStat(EnterpriseDashboardStatPeriodType.YEAR, getYearLabel(statDate), stat, targetStats);
            }

            // Sort the stats in ascending order of periods
            sort(costStats);
            sort(durationStats);
            sort(fulfillmentStats);
            
            // Set 0 stats for empty stats
            checkEmptyStats(costStats);
            checkEmptyStats(durationStats);
            checkEmptyStats(fulfillmentStats);

            // Set view data with aggregated stats
            getViewData().setCostStats(costStats);
            getViewData().setDurationStats(durationStats);
            getViewData().setFulfillmentStats(fulfillmentStats);
            
            getViewData().setAverageCost(averageCustomerCost.getClientValue());
            getViewData().setAverageDuration(averageCustomerDuration.getClientValue() / 24);
            getViewData().setAverageFulfillment(averageCustomerFulfillment.getClientValue());
        }

        // For normal request flow prepare various data to be displayed to user
        // Set projects data
        List<ProjectBriefDTO> projects = DataProvider.getUserProjects(currentUser.getUserId());
        Collections.sort(projects, new Comparator<ProjectBriefDTO>() {
            public int compare(ProjectBriefDTO e1, ProjectBriefDTO e2) {
                return e1.getName().compareTo(e2.getName());
            }
        });
        UserProjectsDTO userProjectsDTO = new UserProjectsDTO();
        userProjectsDTO.setProjects(projects);
        getViewData().setUserProjects(userProjectsDTO);

        // Set current project contests
        ProjectBriefDTO currentProject = this.sessionData.getCurrentProjectContext();
        if (currentProject != null) {
            List<TypedContestBriefDTO> contests
                = DataProvider.getProjectTypedContests(currentUser.getUserId(), currentProject.getId());
            this.sessionData.setCurrentProjectContests(contests);
        }

        // set projects status color
        setProjectStatusColor();
        
        // Build the result for consumption by JSON serializer in case of AJAX calls
        if (isAJAXCall) {
            Map<String, Object> durations = buildStats(getViewData().getDurationStats(),
                                                       new String[]{"date", "Customer Avg Contest Duration",
                                                                    "Market Avg Contest Duration"});
            Map<String, Object> costs = buildStats(getViewData().getCostStats(),
                                                   new String[]{"date", "Customer Avg Cost", "Market Avg Cost"});
            Map<String, Object> fulfillment = buildStats(getViewData().getFulfillmentStats(),
                                                         new String[]{"date", "Customer Avg Fulfillment",
                                                                      "Market Avg Fulfillment"});

            Map<String, Object> result = new HashMap<String, Object>();

            result.put("contest", durations);
            result.put("cost", costs);
            result.put("fulfill", fulfillment);

            DateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
            DateFormat dateFormat2 = new SimpleDateFormat("MMM dd,yyyy");
            result.put("periodStart", dateFormat2.format(startDate));
            result.put("periodEnd", dateFormat2.format(endDate));
            result.put("periodStartCalendar", dateFormat1.format(startDate));
            result.put("periodEndCalendar", dateFormat1.format(endDate));

            NumberFormat format1 = new DecimalFormat("##0.##");
            NumberFormat format2 = new DecimalFormat("#,##0.00");
            NumberFormat format3 = new DecimalFormat("##0.#");
            result.put("avg1", format1.format(getViewData().getAverageFulfillment()));
            result.put("avg2", format2.format(getViewData().getAverageCost()));
            result.put("avg3", format3.format(getViewData().getAverageDuration()));

            setResult(result);
        }
    }

    /**
     * <p>Builds the map providing the stats per various periods to be fed to JSON serializer.</p>
     * 
     * @param stats a <code>Map</code> mapping the period types to list of stats. 
     * @param columnNames a <code>String</code> array listing the column names.
     * @return a <code>Map</code> mapping the period types to stats. 
     */
    private Map<String, Object> buildStats(Map<String, List<EnterpriseDashboardAggregatedStatDTO>> stats,
                                           String[] columnNames) {
        Map<String, Object> statsResult = new HashMap<String, Object>();
        statsResult.put("column", columnNames);
        if (stats != null) {
            statsResult.put("week", buildStatResults(stats, EnterpriseDashboardStatPeriodType.WEEK));
            statsResult.put("month", buildStatResults(stats, EnterpriseDashboardStatPeriodType.MONTH));
            statsResult.put("quarter", buildStatResults(stats, EnterpriseDashboardStatPeriodType.QUARTER));
            statsResult.put("year", buildStatResults(stats, EnterpriseDashboardStatPeriodType.YEAR));
        }
        return statsResult;
    }

    /**
     * <p>Builds the list of stats for specified period type.</p>
     * 
     * @param stats a <code>Map</code> mapping the period types to list of stats. 
     * @param periodType an <code>EnterpriseDashboardStatPeriodType</code> referencing the desired period type to get
     *        stats for. 
     * @return a <code>List</code> 
     */
    private List<Map<String, Object>> buildStatResults(Map<String, List<EnterpriseDashboardAggregatedStatDTO>> stats,
                                                       EnterpriseDashboardStatPeriodType periodType) {
        NumberFormat numberFormat = new DecimalFormat("0.##");
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        List<EnterpriseDashboardAggregatedStatDTO> periodStats = stats.get(periodType.toString());
        for (EnterpriseDashboardAggregatedStatDTO stat : periodStats) {
            Map<String, Object> statData = new HashMap<String, Object>();
            statData.put("date", stat.getTimePeriodLabel());
            statData.put("customer", new Double(numberFormat.format(stat.getClientValue())));
            statData.put("tc", new Double(numberFormat.format(stat.getOverallValue())));
            list.add(statData);
        }
        return list;
    }

    /**
     * <p>Sets the specified stats with zero stats if there are no statistical records available.</p>
     * 
     * @param stats a <code>Map</code> providing the stats per period types. 
     */
    private void checkEmptyStats(Map<String, List<EnterpriseDashboardAggregatedStatDTO>> stats) {
        for (EnterpriseDashboardStatPeriodType periodType : EnterpriseDashboardStatPeriodType.values()) {
            String periodTypeName = periodType.toString();
            if (!stats.containsKey(periodTypeName)) {
                stats.put(periodTypeName, new ArrayList<EnterpriseDashboardAggregatedStatDTO>());
            }
            List<EnterpriseDashboardAggregatedStatDTO> periodStats = stats.get(periodTypeName);
            if (periodStats.isEmpty()) {
                EnterpriseDashboardAggregatedStatDTO zeroDTO = new EnterpriseDashboardAggregatedStatDTO();
                zeroDTO.setClientValue(0);
                zeroDTO.setOverallValue(0);
                zeroDTO.setTimePeriodLabel("NOT ENOUGH STATISTICS TO RENDER THE CHART");
                periodStats.add(zeroDTO);
            }
        }
    }


    /**
     * <p>Aggregates the specified statistics for specified period type and time period.</p>
     *
     * @param periodType a <code>EnterpriseDashboardStatPeriodType</code> specifying the type of the period to aggregate
     *        the statistics for.
     * @param timePeriodLabel a <code>String</code> providing the label for time period to aggregate statistics for.
     * @param stat a <code>EnterpriseDashboardDetailedProjectStatDTO</code> providing the statistics to be aggregated.
     * @param targetStats a <code>Map</code> collecting the statistics.
     */
    private void aggregateClientStat(EnterpriseDashboardStatPeriodType periodType, String timePeriodLabel,
                                     EnterpriseDashboardDetailedProjectStatDTO stat,
                                     Map<String, List<EnterpriseDashboardAggregatedStatDTO>> targetStats) {
        List<EnterpriseDashboardAggregatedStatDTO> statList = targetStats.get(periodType.toString());
        EnterpriseDashboardAggregatedStatDTO weekStat = getStat(timePeriodLabel, statList);
        weekStat.aggregateClientValue(stat.getValue(), stat.getContestsCount());
    }

    /**
     * <p>Aggregates the specified statistics for specified period type and time period.</p>
     *
     * @param periodType a <code>EnterpriseDashboardStatPeriodType</code> specifying the type of the period to aggregate
     *        the statistics for.
     * @param timePeriodLabel a <code>String</code> providing the label for time period to aggregate statistics for.
     * @param stat a <code>EnterpriseDashboardDetailedProjectStatDTO</code> providing the statistics to be aggregated.
     * @param targetStats a <code>Map</code> collecting the statistics.
     */
    private void aggregateOverallStat(EnterpriseDashboardStatPeriodType periodType, String timePeriodLabel,
                                      EnterpriseDashboardDetailedProjectStatDTO stat,
                                      Map<String, List<EnterpriseDashboardAggregatedStatDTO>> targetStats) {
        List<EnterpriseDashboardAggregatedStatDTO> statList = targetStats.get(periodType.toString());
        EnterpriseDashboardAggregatedStatDTO weekStat = getStat(timePeriodLabel, statList);
        weekStat.aggregateOverallValue(stat.getValue(), stat.getContestsCount());
    }

    /**
     * <p>Gets the textual presentation of the week of the year corresponding to specified date.</p>
     *
     * @param statDate a <code>Date</code> providing the date which the statistics was collected for.
     * @return a <code>String</code> providing the textual description of the week of the year corresponding to
     *         specified date.
     */
    private String getWeekLabel(Date statDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(statDate);
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        int year = calendar.get(Calendar.YEAR);
        return weekOfYear + getSuffix(weekOfYear) + " week " + year;
    }

    /**
     * <p>Gets the textual presentation of the month corresponding to specified date.</p>
     *
     * @param statDate a <code>Date</code> providing the date which the statistics was collected for.
     * @return a <code>String</code> providing the textual description of the month of the year corresponding to
     *         specified date.
     */
    private String getMonthLabel(Date statDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(statDate);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        return MONTH_NAMES[month] + " " + year;
    }

    /**
     * <p>Gets the textual presentation of the quarter corresponding to specified date.</p>
     *
     * @param statDate a <code>Date</code> providing the date which the statistics was collected for.
     * @return a <code>String</code> providing the textual description of the quarter of the year corresponding to
     *         specified date.
     */
    private String getQuarterLabel(Date statDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(statDate);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        return QUARTER_NAMES[month  / 3] + " " + year;
    }

    /**
     * <p>Gets the textual presentation of the year corresponding to specified date.</p>
     *
     * @param statDate a <code>Date</code> providing the date which the statistics was collected for.
     * @return a <code>String</code> providing the textual description of the year corresponding to specified date.
     */
    private String getYearLabel(Date statDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(statDate);
        int year = calendar.get(Calendar.YEAR);
        return String.valueOf(year);
    }

    /**
     * <p>Gets the suffix for textual presentation of specified value.</p>
     *
     * @param value an <code>int</code> providing the numeric value.
     * @return a <code>String</code> providing the suffix.
     */
    private String getSuffix(int value) {
        int mod = value % 10;
        boolean isSecondTenth = (value >= 10 && value < 20);
        if ((mod == 2) && !isSecondTenth) {
            return "nd";
        } else if ((mod == 3) && !isSecondTenth) {
            return "rd";
        } else if ((mod == 1) && !isSecondTenth) {
            return "st";
        } else {
            return "th";
        }
    }

    /**
     * <p>Creates new map to be used for collecting the stats.</p>
     *
     * @return a <code>Map</code> to be used for collecting the stats.
     */
    private Map<String, List<EnterpriseDashboardAggregatedStatDTO>> createEmptyStats() {
        Map<String, List<EnterpriseDashboardAggregatedStatDTO>> stats
            = new LinkedHashMap<String, List<EnterpriseDashboardAggregatedStatDTO>>();
        stats.put(EnterpriseDashboardStatPeriodType.WEEK.toString(),
                  new ArrayList<EnterpriseDashboardAggregatedStatDTO>());
        stats.put(EnterpriseDashboardStatPeriodType.MONTH.toString(),
                  new ArrayList<EnterpriseDashboardAggregatedStatDTO>());
        stats.put(EnterpriseDashboardStatPeriodType.QUARTER.toString(),
                  new ArrayList<EnterpriseDashboardAggregatedStatDTO>());
        stats.put(EnterpriseDashboardStatPeriodType.YEAR.toString(),
                  new ArrayList<EnterpriseDashboardAggregatedStatDTO>());
        return stats;
    }

    /**
     * <p>Gets the stats for specified time period if such stats are set in specified list. If not then create empty
     * stats and adds it to specified list.</p>
     *
     * @param label a <code>String</code> providing the textual presentation for time period for aggregated stats to be
     *        looked for.
     * @param stats a <code>List</code> listing the existing stats collected so far.
     * @return an <code>EnterpriseDashboardAggregatedStatDTO</code> to be used
     */
    private EnterpriseDashboardAggregatedStatDTO getStat(String label,
                                                         List<EnterpriseDashboardAggregatedStatDTO> stats) {

        for (EnterpriseDashboardAggregatedStatDTO stat : stats) {
            if (label.equals(stat.getTimePeriodLabel())) {
                return stat;
            }
        }
        EnterpriseDashboardAggregatedStatDTO stat = new EnterpriseDashboardAggregatedStatDTO();
        stat.setTimePeriodLabel(label);
        stats.add(stat);
        return stat;
    }

    /**
     * <p>Sorts the specified statistics in ascending order of their time period labels.</p>
     *
     * @param stats a <code>Map</code> providing the stats to be sorted.
     */
    private void sort(Map<String, List<EnterpriseDashboardAggregatedStatDTO>> stats) {
        Collections.sort(stats.get(EnterpriseDashboardStatPeriodType.WEEK.toString()),
                         new LabelComparator(EnterpriseDashboardStatPeriodType.WEEK));
        Collections.sort(stats.get(EnterpriseDashboardStatPeriodType.MONTH.toString()),
                         new LabelComparator(EnterpriseDashboardStatPeriodType.MONTH));
        Collections.sort(stats.get(EnterpriseDashboardStatPeriodType.QUARTER.toString()),
                         new LabelComparator(EnterpriseDashboardStatPeriodType.QUARTER));
        Collections.sort(stats.get(EnterpriseDashboardStatPeriodType.YEAR.toString()),
                         new LabelComparator(EnterpriseDashboardStatPeriodType.YEAR));
    }

    /**
     * <p>A comparator for time period labels for collected statistics.</p>
     *
     * @author isv
     */
    private static class LabelComparator implements Comparator<EnterpriseDashboardAggregatedStatDTO> {

        /**
         * <p>An <code>EnterpriseDashboardStatPeriodType</code> referencing the type of the stats.</p>
         */
        private EnterpriseDashboardStatPeriodType type;

        /**
         * <p>A <code>Map</code> mapping month names to month number.</p>
         */
        private Map<String, Integer> monthNumbers;

        /**
         * <p>Constructs new <code>EnterpriseDashboardAction$LabelComparator</code> instance. This implementation does
         * nothing.</p>
         *
         * @param type an <code>EnterpriseDashboardStatPeriodType</code> referencing the type of the stats.
         */
        private LabelComparator(EnterpriseDashboardStatPeriodType type) {
            this.type = type;
            if (type == EnterpriseDashboardStatPeriodType.MONTH) {
                this.monthNumbers = new HashMap<String, Integer>();
                for (int i = 0; i < MONTH_NAMES.length; i++) {
                    this.monthNumbers.put(MONTH_NAMES[i], i);
                }
            }
        }

        /**
         * <p>Compares the specified statistics based on their time period labels.</p>
         *
         * @param o1 a <code>EnterpriseDashboardAggregatedStatDTO</code> to be compared.
         * @param o2 a <code>EnterpriseDashboardAggregatedStatDTO</code> to be compared.
         * @return an <code>int</code> providing the comparison result.
         */
        public int compare(EnterpriseDashboardAggregatedStatDTO o1, EnterpriseDashboardAggregatedStatDTO o2) {
            String label1 = o1.getTimePeriodLabel();
            String label2 = o2.getTimePeriodLabel();
            int value1 = parseLabel(label1);
            int value2 = parseLabel(label2);
            return value1 - value2;
        }

        /**
         * <p>Parses the specified time period label into numeric value which could be used for comparison.</p>
         *
         * @param label a <code>String</code> providing the label.
         * @return an <code>int</code> providing the numeric value for label.
         */
        private int parseLabel(String label) {
            label = label.trim();
            
            if (this.type == EnterpriseDashboardStatPeriodType.WEEK) {
                String[] split = label.split(" ");
                String weekLabel = split[0];
                String yearLabel = split[2];
                return Integer.parseInt(yearLabel) * 1000
                       + Integer.parseInt(weekLabel.substring(0, weekLabel.length() - 2));
            } else if (this.type == EnterpriseDashboardStatPeriodType.MONTH) {
                String[] split = label.split(" ");
                String monthName = split[0];
                return this.monthNumbers.get(monthName) + 12 * Integer.parseInt(split[1]);
            } else if (this.type == EnterpriseDashboardStatPeriodType.QUARTER) {
                String[] split = label.split(" ");
                String quarterLabel = split[0];
                String yearLabel = split[1];
                return Integer.parseInt(yearLabel) * 1000 + Integer.parseInt(quarterLabel.substring(1));
            } else {
                return Integer.parseInt(label);
            }
        }
    }

    /**
     * <p>Converts the specified list of audible entities to mappings from IDs to entity names.</p>
     * 
     * @param items a <code>List</code> providing the collection of data. 
     * @return a <code>Map</code> mapping the IDs to names of specified items.  
     */
    private Map<Long, String> convertToMap(List<? extends AuditableEntity> items) {
        Map<Long, String> map = new LinkedHashMap<Long, String>();
        for (AuditableEntity item : items) {
            map.put(item.getId(), item.getName());
        }
        return map;
    }

    private void setProjectStatusColor() throws Exception {
        for (EnterpriseDashboardProjectStatDTO project : viewData.getProjects()) {
            ProjectContestsListDTO contests = DataProvider.getProjectContests(
                    getSessionData().getCurrentUserId(), project.getProject()
                            .getId());
            boolean hasRed = false;
            boolean hasOrange = false;

            for (ProjectContestDTO projectContest : contests.getContests()) {
                // just call all running and scheduled contest's query
                if (DashboardHelper.SCHEDULED.equalsIgnoreCase(projectContest
                        .getStatus().getShortName())
                        || DashboardHelper.RUNNING
                                .equalsIgnoreCase(projectContest.getStatus()
                                        .getShortName())) {
                    ContestDashboardDTO contest = DataProvider
                            .getContestDashboardData(projectContest
                                    .getContest().getId(), projectContest
                                    .getIsStudio(), true);

                    DashboardHelper.setContestStatusColor(contest);
                    if (contest.getContestStatusColor() == DashboardStatusColor.RED) {
                        hasRed = true;
                        break;
                    } else if (contest.getContestStatusColor() == DashboardStatusColor.ORANGE) {
                        hasOrange = true;
                    }
                }
            }
            if (hasRed) {
                project.setProjectStatusColor(DashboardStatusColor.RED);
            } else if (hasOrange) {
                project.setProjectStatusColor(DashboardStatusColor.ORANGE);
            } else {
                project.setProjectStatusColor(DashboardStatusColor.GREEN);
            }
        }
    }
}
