/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.IdNamePair;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.dto.dashboard.participationreport.ParticipationAggregationReportDTO;
import com.topcoder.direct.services.view.dto.dashboard.participationreport.ParticipationAggregationType;
import com.topcoder.direct.services.view.dto.dashboard.participationreport.ParticipationBasicReportDTO;
import com.topcoder.direct.services.view.dto.dashboard.participationreport.ParticipationContestCopilotDTO;
import com.topcoder.direct.services.view.dto.dashboard.participationreport.ParticipationContestDetailDTO;
import com.topcoder.direct.services.view.dto.dashboard.participationreport.ParticipationReportDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.form.DashboardParticipationMetricsReportForm;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.security.TCSubject;
import com.topcoder.service.project.ProjectData;

/**
 * <p>A <code>Struts</code> action to be used for handling the requests for viewing the
 * Participation Metrics Report.</p>
 *
 * @author TCSASSEMBER
 * @version 1.0 (TC Cockpit Participation Metrics Report Part One Assembly 1)
 */
public class DashboardParticipationMetricsReportAction extends BaseDirectStrutsAction {
    
    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 3386028267770976602L;
    
    /**
     * The contest status used by report. There are 3 status used for report. It's initialized in the
     * static constructor of this action class.
     */
    private static final Map<Long, String> REPORT_CONTEST_STATUS;
    
    /**
     * The default duration used for date filter.
     */
    private static final long DEFAULT_DURATION = 182 * 24 * 3600 * 1000L;
    
    /**
     * Status constructor of this action.
     */
    static {

        // initialize the contest status
        REPORT_CONTEST_STATUS = new HashMap<Long, String>();
        REPORT_CONTEST_STATUS.put(1L, "Scheduled");
        REPORT_CONTEST_STATUS.put(2L, "Active");
        REPORT_CONTEST_STATUS.put(3L, "Finished");
    }
    
    /**
     * <p>A <code>SessionData</code> providing interface to current session.</p>
     */
    private SessionData sessionData;
    
    /**
     * <p>A <code>DashboardParticipationMetricsReportForm</code> providing the participation metrics report
     * form parameters submitted by user.</p>
     */
    private DashboardParticipationMetricsReportForm formData;
    
    /**
     * <p>Direct projects data accessible by current user.</p>
     */
    private List<ProjectData> directProjectsData;
    
    /**
     * <p>A <code>ParticipationReportDTO</code> providing the view data for displaying by
     * <code>Participation Metrics Report</code> view. </p>
     */
    private ParticipationReportDTO viewData;
    
    /**
     * <p>Constructs new <code>DashboardParticipationMetricsReportAction</code> instance.</p>
     */
    public DashboardParticipationMetricsReportAction() {
        this.viewData = new ParticipationReportDTO();
        this.formData = new DashboardParticipationMetricsReportForm();
    }
    
    /**
     * <p>Handles the incoming request. Retrieves data for Participation Metrics Report
     * and binds it to request.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        
        // Get current session
        HttpServletRequest request = DirectUtils.getServletRequest();
        this.sessionData = new SessionData(request.getSession());
        TCSubject currentUser = getCurrentUser();
        
        // Analyze form parameters
        DashboardParticipationMetricsReportForm form = getFormData();
        long projectId = form.getProjectId();
        long[] categoryIds = form.getProjectCategoryIds();
        long billingAccountId = form.getBillingAccountId();
        long customerId = form.getCustomerId();
        long[] statusIds = form.getStatusIds();
        Date startDate = DirectUtils.getDate(form.getStartDate());
        Date endDate = DirectUtils.getDate(form.getEndDate());
        
        // If start date is not set then use date for half of a year before current time
        SimpleDateFormat dateFormat = new SimpleDateFormat(DirectUtils.DATE_FORMAT);
        Date now = new Date();
        if (startDate == null) {
            startDate = new Date(now.getTime() - (DEFAULT_DURATION));
            form.setStartDate(dateFormat.format(startDate));
        }

        // If end date is not set then use current time
        if (endDate == null) {
            endDate = now;
            form.setEndDate(dateFormat.format(endDate));
        }
        
        // Get the list of available project categories
        Map<Long, String> projectCategories = DataProvider.getAllProjectCategories();

        // Get all the clients accessible by current user
        Map<Long, String> customers = DirectUtils.getAllClients(currentUser);
        
        boolean isFirstCall = this.viewData.isShowJustForm();
        
        // If client account IDs are not specified then use the first client account id
        boolean customerIdIsSet = customerId > 0;
        if (isFirstCall && !customerIdIsSet) {
            for (long clientId : customers.keySet()) {
                customerId = clientId;
                form.setCustomerId(customerId);
                customerIdIsSet = true;
                break;
            }
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
        
        // if status IDs are not specified then use all status ids
        boolean statusIdsAreSet = (statusIds != null) && (statusIds.length > 0);
        if (isFirstCall && !statusIdsAreSet) {
            statusIds = new long[REPORT_CONTEST_STATUS.size()];
            int count = 0;
            for (Long l : REPORT_CONTEST_STATUS.keySet()) {
                statusIds[count] = l;
                count++;
            }
            getFormData().setStatusIds(statusIds);
            statusIdsAreSet = true;
        }
        
        // set all the project categories to view data to populate project category selection
        getViewData().setProjectCategories(projectCategories);

        // set all the report status to view data to populate report contest status (3 total: active, scheduled, finished)
        getViewData().setContestStatus(REPORT_CONTEST_STATUS);

        // set view data for clients
        getViewData().setClientAccounts(customers);
        
        // set view data for billings
        if (getFormData().getCustomerId() > 0) {
            getViewData().setClientBillingProjects(DirectUtils.getBillingsForClient(currentUser, getFormData().getCustomerId()));
        } else {
            getViewData().setClientBillingProjects(new HashMap<Long, String>());
        }
        
        getViewData().getClientBillingProjects().put(0L, "All Billing Accounts");
        
        // set view data for projects
        if (getFormData().getBillingAccountId() <= 0) {
            if (getFormData().getCustomerId() > 0) {
                getViewData().setProjectsLookupMap(DirectUtils.getProjectsForClient(currentUser, getFormData().getCustomerId()));
            } else {
                getViewData().setProjectsLookupMap(new HashMap<Long, String>());
            }
        } else {
            getViewData().setProjectsLookupMap(DirectUtils.getProjectsForBilling(currentUser,
                    getFormData().getBillingAccountId()));
        }

        // add the default all for projects
        getViewData().getProjectsLookupMap().put(0L, "All Projects");

        // For normal request flow prepare various data to be displayed to user
        // Set projects data
        List<ProjectBriefDTO> projects = DataProvider.getUserProjects(currentUser.getUserId());
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
        
        // Validate the dates range
        if (startDate.compareTo(endDate) > 0) {
            addActionError("Start date must not be after end date");
            return;
        }
        
        // If necessary get and process report data
        if (!getViewData().isShowJustForm()) {
            String[] status = new String[statusIds.length];
            for (int i = 0; i < statusIds.length; i++) {
                status[i] = REPORT_CONTEST_STATUS.get(statusIds[i]);
            }
            // The copilots data of Participation Metrics Report
            List<ParticipationContestCopilotDTO> contestCopilots = new ArrayList<ParticipationContestCopilotDTO>();
            // The contest detail data of Participation Metrics Report
            List<ParticipationContestDetailDTO> contestDetails = new ArrayList<ParticipationContestDetailDTO>();
            DataProvider.getDashboardParticipationReport(
                    projectId, categoryIds, customerId, billingAccountId, status, startDate, endDate, contestCopilots, contestDetails);
            
            // Get the basic metrics data
            getViewData().setParticipationBasicReport(getBasicMetrics(contestCopilots));
            // Aggregate the contest detail data
            getViewData().setBillingAggregation(aggregateParticipationDetails(contestDetails, ParticipationAggregationType.BILLING_ACCOUNT_AGGREGATION));
            getViewData().setProjectAggregation(aggregateParticipationDetails(contestDetails, ParticipationAggregationType.DIRECT_PROJECT_AGGREGATION));
            getViewData().setContestTypeAggregation(aggregateParticipationDetails(contestDetails, ParticipationAggregationType.CONTEST_TYPE_AGGREGATION));
            getViewData().setStatusAggregation(aggregateParticipationDetails(contestDetails, ParticipationAggregationType.STATUS_AGGREGATION));
        }
    }

    /**
     * Get the participation basic metrics data from the contest copilots data.
     * 
     * @param contestCopilots a <code>List</code> providing the contest copilots data.
     * @return the participation basic metrics data.
     */
    private ParticipationBasicReportDTO getBasicMetrics(List<ParticipationContestCopilotDTO> contestCopilots) {
        ParticipationBasicReportDTO basicMetrics = new ParticipationBasicReportDTO();
        Set<Long> projects = new HashSet<Long>();
        Set<Long> contests = new HashSet<Long>();
        Set<Long> copilots = new HashSet<Long>(); 
        for (ParticipationContestCopilotDTO copilot : contestCopilots) {
            projects.add(copilot.getProjectId());
            contests.add(copilot.getContestId());
            if (copilot.getCopilot() > 0) {
                copilots.add(copilot.getCopilot());
            }
        }
        basicMetrics.setTotalProjects(projects.size());
        basicMetrics.setTotalContests(contests.size());
        basicMetrics.setTotalCopilots(copilots.size());
        return basicMetrics;
    }

    /**
     * Generates the aggregation participation metrics report from the contest participation details.
     * The <code>aggregationType</code> indicates which fields of the contest participation details will be used to group.
     *
     * @param participationDetails the contest participation details data.
     * @param aggregationType the aggregation type of the aggregation report.
     * @return the generated aggregation report data.
     */
    private List<ParticipationAggregationReportDTO> aggregateParticipationDetails(List<ParticipationContestDetailDTO> participationDetails, ParticipationAggregationType aggregationType)
        throws Exception {
        Map<String, AggregationRow> aggregationDTOMap = new HashMap<String, DashboardParticipationMetricsReportAction.AggregationRow>();
        
        for (ParticipationContestDetailDTO detail : participationDetails) {
            // get the key used for aggregation report
            IdNamePair pair = getAggregationKey(detail, aggregationType);
            AggregationRow item = aggregationDTOMap.get(pair.getName() + "-" + pair.getId());
            if (item == null) {
                // does not exist, create a new AggregationRow
                item = new AggregationRow();
                item.groupName = pair.getName();
                
                aggregationDTOMap.put(pair.getName() + "-" + pair.getId(), item);
            }
            
            if (detail.getRegistrant() > 0) {
                item.totalRegistrants++;
                item.uniqueRegistrants.add(detail.getRegistrant());
            }
            if (detail.getCountry() != null) {
                item.registrantCountries.add(detail.getCountry());
            }
            if (detail.isHasSubmit()) {
                // The resource has submitted a submission
                item.totalSubmitters++;
                item.uniqueSubmitters.add(detail.getRegistrant());
                if (detail.getCountry() != null) {
                    item.submitterCountries.add(detail.getCountry());
                }
            }
            if (detail.isHasWin()) {
                // THe resource is the winner
                item.totalWinners++;
                if (detail.getCountry() != null) {
                    item.winnerCountries.add(detail.getCountry());
                }
            }
        }
        
        List<ParticipationAggregationReportDTO> aggregationReport = new ArrayList<ParticipationAggregationReportDTO>();
        for (Map.Entry<String, AggregationRow> entry : aggregationDTOMap.entrySet()) {
            ParticipationAggregationReportDTO report = new ParticipationAggregationReportDTO();
            AggregationRow row = entry.getValue();
            report.setGroupName(row.groupName);
            report.setTotalRegistrants(row.totalRegistrants);
            report.setUniqueRegistrants(row.uniqueRegistrants.size());
            report.setRegistrantCountries(row.registrantCountries.size());
            report.setTotalSubmitters(row.totalSubmitters);
            report.setUniqueSubmitters(row.uniqueSubmitters.size());
            report.setSubmitterContries(row.submitterCountries.size());
            report.setTotalWinners(row.totalWinners);
            report.setWinnerCountries(row.winnerCountries.size());
            aggregationReport.add(report);
        }
        
        return aggregationReport;
    }
    
    /**
     * Gets the field value in ParticipationContestDetailDTO which is used for aggregation by aggregation type.
     *
     * @param detail the cost participation detail
     * @param aggregationType the aggregation type.
     * @return the field value in ParticipationContestDetailDTO which is used for aggregation by aggregation type.
     */
    private IdNamePair getAggregationKey(ParticipationContestDetailDTO detail, ParticipationAggregationType aggregationType) {
        if (aggregationType == ParticipationAggregationType.BILLING_ACCOUNT_AGGREGATION) {
            return detail.getBilling();
        } else if (aggregationType == ParticipationAggregationType.DIRECT_PROJECT_AGGREGATION) {
            return detail.getProject();
        } else if (aggregationType == ParticipationAggregationType.STATUS_AGGREGATION) {
            IdNamePair status = new IdNamePair();
            status.setName(detail.getStatus());
            return status;
        } else return detail.getContestType();
    }
    
    /**
     * <p>Gets the form data.</p>
     *
     * @return an <code>DashboardParticipationMetricsReportForm</code> providing the data for report form submitted by user.
     */
    public DashboardParticipationMetricsReportForm getFormData() {
        return formData;
    }

    /**
     * <p>Gets the current session associated with the incoming request from client.</p>
     *
     * @return a <code>SessionData</code> providing access to current session.
     */
    public SessionData getSessionData() {
        return sessionData;
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
     * <p>Gets the data to be displayed by view mapped to this action.</p>
     *
     * @return an <code>CostReportDTO</code> providing the storage for data to be rendered by the view
     *         mapped to this action.
     */
    public ParticipationReportDTO getViewData() {
        return viewData;
    }
    
    /**
     * <p>A simple POJO class holding the data for an aggregation row of a specified aggregation type on
     * the contest participation details data.</p>
     * 
     * @author TCSASSEMBER
     * @version 1.0
     */
    class AggregationRow {
        /**
         * <p>A <code>String</code> providing the name of the aggregation group.</p>
         */
        String groupName;
        
        /**
         * <p>An <code>int</code> providing the number of total registrants of the aggregation group.</p>
         */
        int totalRegistrants;
        
        /**
         * <p>A <code>Set</code> providing all the registrants of the aggregation group.</p>
         */
        Set<Long> uniqueRegistrants;
        
        /**
         * <p>A <code>Set</code> providing all the registrant countries of the aggregation group.</p>
         */
        Set<String> registrantCountries;
        
        /**
         * <p>An <code>int</code> providing the number of total submitters of the aggregation group.</p>
         */
        int totalSubmitters;
        
        /**
         * <p>A <code>Set</code> providing all the submitters of the aggregation group.</p>
         */
        Set<Long> uniqueSubmitters;
        
        /**
         * <p>A <code>Set</code> providing all the submitter countries of the aggregation group.</p>
         */
        Set<String> submitterCountries;
        
        /**
         * <p>An <code>int</code> providing the number of winners of the aggregation type.</p>
         */
        int totalWinners;
        
        /**
         * <p>A <code>Set</code> providing all the winner countries of the aggregation group.</p>
         */
        Set<String> winnerCountries;
        
        /**
         * <p>Construct a new <code>AggregationRow</code> instance.</p>
         */
        AggregationRow() {
            uniqueRegistrants = new HashSet<Long>();
            registrantCountries = new HashSet<String>();
            uniqueSubmitters = new HashSet<Long>();
            submitterCountries = new HashSet<String>();
            winnerCountries = new HashSet<String>();
        }
    }
}
