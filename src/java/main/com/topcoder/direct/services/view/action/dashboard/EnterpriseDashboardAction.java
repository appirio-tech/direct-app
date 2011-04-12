/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.dashboard;

import com.topcoder.clients.model.AuditableEntity;
import com.topcoder.clients.model.Project;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.contest.ContestDashboardDTO;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.dto.dashboard.DashboardCostBreakDownDTO;
import com.topcoder.direct.services.view.dto.dashboard.DashboardStatusColor;
import com.topcoder.direct.services.view.dto.dashboard.EnterpriseDashboardAggregatedStatDTO;
import com.topcoder.direct.services.view.dto.dashboard.EnterpriseDashboardDTO;
import com.topcoder.direct.services.view.dto.dashboard.EnterpriseDashboardDetailedProjectStatDTO;
import com.topcoder.direct.services.view.dto.dashboard.EnterpriseDashboardProjectStatDTO;
import com.topcoder.direct.services.view.dto.dashboard.EnterpriseDashboardStatPeriodType;
import com.topcoder.direct.services.view.dto.dashboard.EnterpriseDashboardStatType;
import com.topcoder.direct.services.view.dto.dashboard.EnterpriseDashboardContestStatDTO;
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
import java.util.*;
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
 * <p>
 * Version 1.0.3 (Cockpit - Enterprise Dashboard 3 Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Add the logic to get the coustomer contest status and all contest status</li>
 *     <li>Added admin attribute.</li>
 *   </ol>
 * </p>
 * <p>
 * Version 1.0.4 (TC Cockpit Billing Cost Report Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Refactor methods which get mappings for client, billing account, and direct projects to DirectUtils</li>
 *   </ol>
 * </p>
 * 
 * <p>
 * Version 1.0.5 (TC Cockpit Enterprise Dashboard Update Cost Breakdown Assembly) Change notes:
 *   <ol>
 *     <li>Added {@link #getCostBreakDown()} method to get the cost break down data for contests or market.</li>
 *     <li>Updated {@link #executeAction()} method to return the average cost data of contest types to client browser.</li>
 *   </ol>
 * </p>
 *
 * @author isv, xjtufreeman, Blues, flexme
 * @version 1.0.5
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
     * <p>The direct projects the user has access to.</p>
     */
    private List<ProjectData> directProjectsData;

    /**
     * <p>A <code>boolean</code> providing the flag indicating if this action instance is to handle AJAX calls or
     * not.</p>
     * 
     * @since 1.0.2
     */
    private boolean isAJAX;

    /**
     * <p>Whether current user is admin or not.</p>
     *
     * @since 1.0.3
     */
    private boolean admin;

    /**
     * <p>Constructs new <code>EnterpriseDashboardAction</code> instance. This implementation does nothing.</p>
     */
    public EnterpriseDashboardAction() {
        this.viewData = new EnterpriseDashboardDTO();
        this.formData = new EnterpriseDashboardForm();
    }

    /**
     * <p>Tells whether user is admin or not.</p>
     *
     * @return true if admin
     * @since 1.0.3
     */
    public boolean isAdmin() {
        return admin;
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

        boolean isTableViewCall = request.getServletPath().equalsIgnoreCase("/dashboardEnterpriseTableViewCall");
        boolean isDrillTableCall = request.getServletPath().equalsIgnoreCase("/dashboardEnterpriseDrillTableCall");

        admin = DirectUtils.isTcOperations(currentUser);
        // Get the list of available project categories
        Map<Long, String> projectCategories = DataProvider.getAllProjectCategories();

        // set all the project categories to view data to populate project category selection
        getViewData().setProjectCategories(projectCategories);

        // Get the list of TC Direct Projects accessible to current user
        List<ProjectData> tcDirectProjects = getDashboardDirectProjects();

        List<EnterpriseDashboardProjectStatDTO> enterpriseProjectStats = new ArrayList<EnterpriseDashboardProjectStatDTO>();

        if (!isAJAXCall) {
        // Get the overall stats for user projects
            enterpriseProjectStats
            = DataProvider.getEnterpriseProjectStats(tcDirectProjects);
        sortEnterpriseDashboardProjectStatDTOByName(enterpriseProjectStats);
        }

        getViewData().setProjects(enterpriseProjectStats);

        Map<Long, String> customers = DirectUtils.getAllClients(currentUser);

        // Analyze form parameters
        EnterpriseDashboardForm form = getFormData();
        long[] projectIds = form.getProjectIds();
        long[] categoryIds = form.getProjectCategoryIds();
        long[] billingAccountIds = form.getBillingAccountIds();
        long[] customerIds = form.getCustomerIds();
        Date startDate = DirectUtils.getDate(form.getStartDate());
        Date endDate = DirectUtils.getDate(form.getEndDate());

         // If client account IDs are not specified then use the first client account id
        boolean customerIdsAreSet = (customerIds != null) && (customerIds.length > 0);
        if (isFirstCall && !customerIdsAreSet) {
            customerIds = new long[1];
            for (long clientId : customers.keySet()) {
                customerIds[0] = clientId;
                break;
            }
            form.setCustomerIds(customerIds);
            customerIdsAreSet = true;
        }

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
            // set to all by default
            billingAccountIds = new long[] {0};
            form.setBillingAccountIds(billingAccountIds);
            billingAccountIdsAreSet = true;
        }

        // If project IDs are not specified then use the first available from the projects assigned to user
        boolean projectIdsAreSet = (projectIds != null) && (projectIds.length > 0); 
        if (isFirstCall && !projectIdsAreSet) {
            // set to all by default
            projectIds = new long[] {0};
                form.setProjectIds(projectIds);
                projectIdsAreSet = true;
            }

        // set view data for clients
        getViewData().setClientAccounts(customers);

        // set view data for billings
        if (getFormData().getCustomerIds() != null && getFormData().getCustomerIds().length > 0) {
            getViewData().setClientBillingProjects(DirectUtils.getBillingsForClient(currentUser, getFormData().getCustomerIds()[0]));
        } else {
            getViewData().setClientBillingProjects(new HashMap<Long, String>());
        }

        // add the default all for billings
        getViewData().getClientBillingProjects().put(0L, "All Billing Accounts");

        // set view data for projects
        if (getFormData().getBillingAccountIds()[0] <= 0) {
            if (getFormData().getCustomerIds() != null && getFormData().getCustomerIds().length > 0) {
                getViewData().setProjectsLookupMap(DirectUtils.getProjectsForClient(currentUser, getFormData().getCustomerIds()[0]));
            } else {
                getViewData().setProjectsLookupMap(new HashMap<Long, String>());
            }
        } else {
            getViewData().setProjectsLookupMap(DirectUtils.getProjectsForBilling(currentUser, getFormData().getBillingAccountIds()[0]));
        }

        // add the default all for projects
        getViewData().getProjectsLookupMap().put(0L, "All Projects");

//        System.out.println("projectIds length:" + projectIds.length);
//        System.out.println("categoryIds length:" + categoryIds.length);
//        System.out.println("billingAccountIds length:" + billingAccountIds.length);
//        System.out.println("customerIds length:" + customerIds.length);
//
//        System.out.println("projectIds:" + projectIds[0]);
//        System.out.println("billingAccountsIds:" + billingAccountIds[0]);
//        System.out.println("customerIds:" + customerIds[0]);

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

        Map<String, String> contestTypeAvgCost = new HashMap<String, String>();
        // Get the detailed stats for specific project, categories and time frame (only if project is specified)
        if (projectIdsAreSet && categoryIdsAreSet && billingAccountIdsAreSet && customerIdsAreSet) {
            Map<String, List<EnterpriseDashboardAggregatedStatDTO>> costStats = createEmptyStats();
            Map<String, List<EnterpriseDashboardAggregatedStatDTO>> durationStats = createEmptyStats();
            Map<String, List<EnterpriseDashboardAggregatedStatDTO>> fulfillmentStats = createEmptyStats();
            EnterpriseDashboardAggregatedStatDTO averageCustomerCost = new EnterpriseDashboardAggregatedStatDTO();
            EnterpriseDashboardAggregatedStatDTO averageCustomerDuration = new EnterpriseDashboardAggregatedStatDTO();
            EnterpriseDashboardAggregatedStatDTO averageCustomerFulfillment 
                = new EnterpriseDashboardAggregatedStatDTO();

            //Get contest avarage info
            Map<Integer, List<Double>> contestTypeAvgMap = DataProvider.getEnterpriseContestsAvgStatus(categoryIds,
                    startDate, endDate);
            NumberFormat format = new DecimalFormat("###,##0.##");
            for (Map.Entry<Integer, List<Double>> entry : contestTypeAvgMap.entrySet()) {
                contestTypeAvgCost.put(entry.getKey().toString(), format.format(entry.getValue().get(1)));
            }
            //Get the date info of the dirll in pointer
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
            Date drillStartDate = null;
            Date drillEndDate = null;
            if(request.getParameter("drillStartDate") != null) {
                drillStartDate = df.parse(request.getParameter("drillStartDate"));
            }
            if(request.getParameter("drillEndDate") != null) {
                drillEndDate = df.parse(request.getParameter("drillEndDate"));
            }
            if(isTableViewCall) {
                Map<String, Object> contestResult = new HashMap<String, Object>();
                if(drillStartDate != null && drillEndDate != null) {
                    startDate = drillStartDate;
                    endDate = drillEndDate;    
                }
                // Get the contest status info
                List<EnterpriseDashboardContestStatDTO> contestStats = DataProvider.getEnterpriseStatsForContests(projectIds,
                        categoryIds, startDate, endDate, customerIds,billingAccountIds);
                // fill the average data for each contest
                for(int i=0; i<contestStats.size(); i++){
                    EnterpriseDashboardContestStatDTO contestDTO = contestStats.get(i);
                    contestDTO.setMarketAvgFullfilment(contestTypeAvgMap.get(contestDTO.getProjectCategoryId()).get(0));
                    contestDTO.setMarketAvgCost(contestTypeAvgMap.get(contestDTO.getProjectCategoryId()).get(1));
                    contestDTO.setMarketAvgDuration(contestTypeAvgMap.get(contestDTO.getProjectCategoryId()).get(2));
                }
                contestResult.put("contestStatus",buildContestStatResults(contestStats));
                contestResult.put("contestTypeAvgCost", contestTypeAvgCost);
                setResult(contestResult);
                return;
            }
            if(isDrillTableCall) {
                Map<String, Object> allContestResult = new HashMap<String, Object>();
                // Get all contest status info
                List<EnterpriseDashboardContestStatDTO> allContestStats = DataProvider.getEnterpriseStatsForAllContests(categoryIds,
                        drillStartDate, drillEndDate);
                // fill the average data for each contest
                for(int i=0; i<allContestStats.size(); i++){
                    EnterpriseDashboardContestStatDTO contestDTO = allContestStats.get(i);
                    contestDTO.setMarketAvgFullfilment(contestTypeAvgMap.get(contestDTO.getProjectCategoryId()).get(0));
                    contestDTO.setMarketAvgCost(contestTypeAvgMap.get(contestDTO.getProjectCategoryId()).get(1));
                    contestDTO.setMarketAvgDuration(contestTypeAvgMap.get(contestDTO.getProjectCategoryId()).get(2));
                }
                allContestResult.put("allContestStatus",buildContestStatResults(allContestStats));
                allContestResult.put("contestTypeAvgCost", contestTypeAvgCost);
                setResult(allContestResult);
                return;
            }


            // Get data from weekly_contest_status and aggregate the average calculated values for client
            List<EnterpriseDashboardDetailedProjectStatDTO> clientStats
                = DataProvider.getEnterpriseStatsForProject(projectIds, categoryIds, startDate, endDate, customerIds,
                                                            billingAccountIds, false);
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
            }
            // Get data from monthly_contest_status and aggregate the average calculated values for client
            List<EnterpriseDashboardDetailedProjectStatDTO> clientStatsMonthly
                = DataProvider.getEnterpriseStatsForProject(projectIds, categoryIds, startDate, endDate, customerIds,
                                                            billingAccountIds, true);
            for (EnterpriseDashboardDetailedProjectStatDTO stat : clientStatsMonthly) {
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
                aggregateClientStat(EnterpriseDashboardStatPeriodType.MONTH, getMonthLabel(statDate), stat,
                                    targetStats);
                aggregateClientStat(EnterpriseDashboardStatPeriodType.QUARTER, getQuarterLabel(statDate), stat,
                                    targetStats);
                aggregateClientStat(EnterpriseDashboardStatPeriodType.YEAR, getYearLabel(statDate), stat, targetStats);
            }


            // Get data from weekly_contest_status and aggregate the average calculated values for all projects
            List<EnterpriseDashboardDetailedProjectStatDTO> overallStats
                = DataProvider.getEnterpriseStatsForAllProjects(categoryIds, startDate, endDate, false);
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
            }

            // Get data from monthly_contest_status and aggregate the average calculated values for all projects
            List<EnterpriseDashboardDetailedProjectStatDTO> overallStatsMonthly
                = DataProvider.getEnterpriseStatsForAllProjects(categoryIds, startDate, endDate, true);
            for (EnterpriseDashboardDetailedProjectStatDTO stat : overallStatsMonthly) {
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
            getViewData().setAverageDuration(averageCustomerDuration.getClientValue());
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
            result.put("contestTypeAvgCost", contestTypeAvgCost);

            setResult(result);
        }
    }

    /**
     * Gets the billing and direct project dropdown options for the request client id.
     *
     * @return the result
     * @throws Exception if any error occurs
     */
    public String getOptionsForClient() throws Exception {
        TCSubject currentUser = getCurrentUser();
        Map<String, Object> result = new HashMap<String, Object>();

        result.put("billings", convertMapKeyToString(DirectUtils.getBillingsForClient(currentUser, getFormData().getCustomerIds()[0])));
        result.put("projects", convertMapKeyToString(DirectUtils.getProjectsForClient(currentUser, getFormData().getCustomerIds()[0])));
        setResult(result);

        return SUCCESS;
    }

    /**
     * Gets the cost break down data for contests or market. The result data will parsed into JSON format
     * and used by the client browser. 
     *
     * @return the result
     * @throws Exception if any error occurs
     * @since 1.0.5
     */
    public String getCostBreakDown() throws Exception {
        Date startDate = DirectUtils.getDate(formData.getStartDate());
        Date endDate = DirectUtils.getDate(formData.getEndDate());
        setResult(buildContestBreakDownResults(DataProvider.getDashboardCostBreakDown(formData.getProjectIds(), formData.getProjectCategoryIds(), startDate, endDate)));
        return SUCCESS;
    }
    
    /**
     * Gets the project dropdown options for the request billing id.
     *
     * @return the result
     * @throws Exception if any error occurs.
     */
    public String getOptionsForBilling() throws Exception {
        TCSubject currentUser = getCurrentUser();
        Map<String, Object> result = new HashMap<String, Object>();

        result.put("projects", convertMapKeyToString(DirectUtils.getProjectsForBilling(currentUser,
                getFormData().getBillingAccountIds()[0])));
        setResult(result);

        return SUCCESS;
    }

    /**
     * Gets the direct project the user has access to.
     *
     * @return the list of project data
     * @throws Exception if any error occurs.
     */
    public List<ProjectData> getDashboardDirectProjects() throws Exception {
        if(this.directProjectsData == null) {
            this.directProjectsData = getProjects();
        }
        return this.directProjectsData;
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
     * <p>Builds the map providing the cost break down data to be fed to JSON serializer.</p>
     *
     * @param costBreakDown a <code>List</code> providing the cost break down data.
     * @return a <code>List</code> providing the mapping data of cost break down data to be fed to JSON serializer.
     * @since 1.0.5
     */
    private List<Map<String, Object>> buildContestBreakDownResults(List<DashboardCostBreakDownDTO> costBreakDown) {
        NumberFormat numberFormat1 = new DecimalFormat("###,##0.00");
        NumberFormat numberFormat2 = new DecimalFormat("###,##0.##");
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        for (DashboardCostBreakDownDTO breakdown : costBreakDown) {
            Map<String, Object> statData = new HashMap<String, Object>();
            statData.put("id", breakdown.getId());
            statData.put("contestFee", numberFormat2.format(breakdown.getContestFee()));
            statData.put("prizes", numberFormat1.format(breakdown.getPrizes()));
            statData.put("specReview", numberFormat1.format(breakdown.getSpecReview()));
            statData.put("review", numberFormat1.format(breakdown.getReview()));
            statData.put("reliability", numberFormat1.format(breakdown.getReliability()));
            statData.put("digitalRun", numberFormat1.format(breakdown.getDigitalRun()));
            statData.put("copilot", numberFormat1.format(breakdown.getCopilot()));
            statData.put("build", numberFormat1.format(breakdown.getBuild()));
            statData.put("bugs", numberFormat1.format(breakdown.getBugs()));
            statData.put("misc", numberFormat1.format(breakdown.getMisc()));
            statData.put("fullfillment", breakdown.getFullfillment());
            list.add(statData);
        }
        return list;
    }

    /**
     * <p>Builds the list of contest stats.</p>
     *
     * @param contestsStat a <code>List</code> list of the contest status results.
     * @return a <code>List</code>
     */
    private List<Map<String, Object>> buildContestStatResults(List<EnterpriseDashboardContestStatDTO> contestsStat) {
        NumberFormat numberFormat1 = new DecimalFormat("##0.##");
        NumberFormat numberFormat2 = new DecimalFormat("#,##0.00");
        NumberFormat numberFormat3 = new DecimalFormat("##0.#");
        DateFormat dateFormat = new SimpleDateFormat("MMM dd,yyyy", Locale.ENGLISH);
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        for (EnterpriseDashboardContestStatDTO stat : contestsStat) {
            Map<String, Object> statData = new HashMap<String, Object>();
            statData.put("postingDate", dateFormat.format(stat.getPostingDate()));
            if (stat.getDate() != null)
            {
                statData.put("date", dateFormat.format(stat.getDate()));
            }
            else
            {
                statData.put("date", "");
            }
            
            statData.put("customerName", stat.getCustomerName());
            statData.put("projectName", stat.getProjectName());
            statData.put("contestType", stat.getContestType());
            statData.put("contestName", stat.getContestName());
            statData.put("projectId", stat.getProjectId());
            statData.put("directProjectId", stat.getDirectProjectId());
            statData.put("contestFullfilment", numberFormat1.format(stat.getContestFullfilment()));
            statData.put("marketAvgFullfilment", numberFormat1.format(stat.getMarketAvgFullfilment()));
            statData.put("contestCost", numberFormat2.format(stat.getContestCost()));
            statData.put("marketAvgCost", numberFormat2.format(stat.getMarketAvgCost()));
            statData.put("contestDuration", numberFormat3.format(stat.getContestDuration()));
            statData.put("marketAvgDuration", numberFormat3.format(stat.getMarketAvgDuration()));
            list.add(statData);
        }
        return list;
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


    private void sortClientProjectByName(List<Project> projects) {
        Collections.sort(projects, new Comparator() {
			public int compare(Object obj1, Object obj2) {
                Project p1 = (Project) obj1;
                Project p2 = (Project) obj2;

                if(p1.getName() == null) return -1;
                if(p2.getName() == null) return 1;

				String name1 = p1.getName().trim();
				String name2 = p2.getName().trim();
				return name1.compareTo(name2);
			}
        });
    }

    private void sortClientProjectByClientName(List<Project> projects) {
        Collections.sort(projects, new Comparator() {
			public int compare(Object obj1, Object obj2) {
                Project p1 = (Project) obj1;
                Project p2 = (Project) obj2;

                if(p1.getClient().getName() == null) return -1;
                if(p2.getClient().getName() == null) return 1;

				String name1 = p1.getClient().getName().trim();
				String name2 = p2.getClient().getName().trim();
				return name1.compareTo(name2);
			}
        });
    }

    private void sortEnterpriseDashboardProjectStatDTOByName(List<EnterpriseDashboardProjectStatDTO> values) {
         Collections.sort(values, new Comparator() {
			public int compare(Object obj1, Object obj2) {
                EnterpriseDashboardProjectStatDTO e1 = (EnterpriseDashboardProjectStatDTO) obj1;
                EnterpriseDashboardProjectStatDTO e2 = (EnterpriseDashboardProjectStatDTO) obj2;

                if(e1.getProject().getName() == null) return -1;
                if(e2.getProject().getName() == null) return 1;

				String name1 = e1.getProject().getName().trim().toLowerCase();
				String name2 = e2.getProject().getName().trim().toLowerCase();
				return name1.compareTo(name2);
			}
        });
    }


    /**
     * Helper method to convert the key of Map<Long, String> to String, returns a Map<String, String>.
     *
     * @param toConvert the map to convert.
     * @return the converted Map<String, String> instance.
     */
    static Map<String, String> convertMapKeyToString(Map<Long, String> toConvert) {
        Map<String, String> result = new HashMap<String, String>();
        for(Map.Entry<Long, String> e : toConvert.entrySet()) {
            result.put(String.valueOf(e.getKey()), e.getValue());
        }
        return  result;
    }

}
