/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.report;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.IdNamePair;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.dto.dashboard.costreport.CostAggregationDTO;
import com.topcoder.direct.services.view.dto.dashboard.costreport.CostAggregationType;
import com.topcoder.direct.services.view.dto.dashboard.costreport.CostDetailsDTO;
import com.topcoder.direct.services.view.dto.dashboard.costreport.CostReportDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.form.DashboardCostReportForm;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.security.TCSubject;
import com.topcoder.service.project.ProjectData;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>A <code>Struts</code> action to be used for handling the requests for viewing the cost report.</p>
 * <p/>
 *
 * <p>Version 1.1 TC Cockpit Billing Cost Report Assembly change notes:
 * <ol>
 *     <li>Refactor methods which get mappings from client, billing account, and direct projects to DirectUtils</li>
 *   </ol>
 * </p>
 *
 * @author Blues
 * @version 1.0 (TopCoder Cockpit - Cost Report Assembly)
 */
public class DashboardCostReportAction extends BaseDirectStrutsAction {

    /**
     * The status used by cost report. There are 3 status used for cost report. it's initialized in the static
     * constructor of this action class.
     */
    private static final Map<Long, String> COST_REPORT_CONTEST_STATUS;

    /**
     * The status used by cost report. There are 3 status used for cost report. it's initialized in the static
     * constructor of this action class.
     */
    private static final Map<String, Long> COST_REPORT_CONTEST_STATUS_IDS;

    /**
     * The mapping between studio contest type id and studio contest types.
     */
    private static final Map<Long, String> STUDIO_CONTEST_TYPES;

    /**
     * The default duration used for date filter.
     */
    private static final long DEFAULT_DURATION = 182 * 24 * 3600 * 1000L;

    private static final String projectIdParam = "formData.projectIds=";
    private static final String startDateParam = "formData.startDate=";
    private static final String endDateParam = "formData.endDate=";
    private static final String customerParam = "formData.customerIds=";
    private static final String billingIdParam = "formData.billingAccountIds=";
    private static final String contestTypeIdParam = "formData.projectCategoryIds=";
    private static final String statusIdParam = "formData.statusIds=";

    /**
     * Status constructor of this action.
     */
    static {

        // initialize the cost report contest status
        COST_REPORT_CONTEST_STATUS = new HashMap<Long, String>();
        COST_REPORT_CONTEST_STATUS_IDS = new HashMap<String, Long>();

        IdNamePair scheduled = new IdNamePair();
        IdNamePair active = new IdNamePair();
        IdNamePair finished = new IdNamePair();
        scheduled.setId(1);
        scheduled.setName("Scheduled");
        active.setId(2);
        active.setName("Active");
        finished.setId(3);
        finished.setName("Finished");
        COST_REPORT_CONTEST_STATUS.put(scheduled.getId(), scheduled.getName());
        COST_REPORT_CONTEST_STATUS.put(active.getId(), active.getName());
        COST_REPORT_CONTEST_STATUS.put(finished.getId(), finished.getName());
        COST_REPORT_CONTEST_STATUS_IDS.put(scheduled.getName().toLowerCase(), scheduled.getId());
        COST_REPORT_CONTEST_STATUS_IDS.put(active.getName().toLowerCase(), active.getId());
        COST_REPORT_CONTEST_STATUS_IDS.put(finished.getName().toLowerCase(), finished.getId());

        STUDIO_CONTEST_TYPES = new HashMap<Long, String>();

        STUDIO_CONTEST_TYPES.put(1L, "Web Design");
        STUDIO_CONTEST_TYPES.put(3L, "Logo Design");
        STUDIO_CONTEST_TYPES.put(4L, "Banners/Icons");
        STUDIO_CONTEST_TYPES.put(5L, "Application Front-End Design");
        STUDIO_CONTEST_TYPES.put(6L, "Widget or Mobile Screen Design");
        STUDIO_CONTEST_TYPES.put(8L, "Front-End Flash");
        STUDIO_CONTEST_TYPES.put(12L, "Print/Presentation");
        STUDIO_CONTEST_TYPES.put(18L, "Other");
        STUDIO_CONTEST_TYPES.put(25L, "Wireframes");
        STUDIO_CONTEST_TYPES.put(26L, "Idea Generation");
    }

    /**
     * <p>A <code>SessionData</code> providing interface to current session.</p>
     */
    private SessionData sessionData;

    /**
     * <p>A <code>DashboardCostReportForm</code> providing the cost report form parameters submitted by user.</p>
     */
    private DashboardCostReportForm formData;

    /**
     * <p>Direct projects data accessible by current user.
     */
    private List<ProjectData> directProjectsData;

    /**
     * <p>A <code>CostReportDTO</code> providing the view data for displaying by
     * <code>Cost Report</code> view. </p>
     */
    private CostReportDTO viewData;

    /**
     * <p>Constructs new <code>DashboardCostReportAction</code> instance</p>
     */
    public DashboardCostReportAction() {
        this.viewData = new CostReportDTO();
        this.formData = new DashboardCostReportForm();
    }

    /**
     * <p>Gets the form data.</p>
     *
     * @return an <code>DashboardCostReportForm</code> providing the data for cost report form submitted by user..
     */
    public DashboardCostReportForm getFormData() {
        return this.formData;
    }

    /**
     * <p>Gets the data to be displayed by view mapped to this action.</p>
     *
     * @return an <code>CostReportDTO</code> providing the storage for data to be rendered by the view
     *         mapped to this action.
     */
    public CostReportDTO getViewData() {
        return this.viewData;
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
     * <p>Handles the incoming request.
     *
     * @return <code>SUCCESS</code> if request is to be forwarded to respective view or <code>download</code> if
     *         response is to be written to client directly.
     */
    @Override
    public String execute() throws Exception {
        String result = super.execute();
        if (SUCCESS.equals(result)) {
            if (getFormData().isExcel()) {
                return "download";
            }
        }
        return result;
    }

    /**
     * <p>Handles the incoming request. Retrieves data for Cost Report and binds it to request.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        // Get current session
        HttpServletRequest request = DirectUtils.getServletRequest();
        this.sessionData = new SessionData(request.getSession());
        TCSubject currentUser = getCurrentUser();

        // admin = DirectUtils.isTcOperations(currentUser);

        // Get the list of available project categories
        Map<Long, String> projectCategories = DataProvider.getAllProjectCategories();

        // add studio project categories
        for(Map.Entry<Long, String> entry : STUDIO_CONTEST_TYPES.entrySet()) {
            projectCategories.put(entry.getKey() + 100L, entry.getValue());
        }

        // Get all the clients accessible by current user
        Map<Long, String> customers = DirectUtils.getAllClients(currentUser);

        // Analyze form parameters
        DashboardCostReportForm form = getFormData();

        long[] projectIds = form.getProjectIds();
        long[] categoryIds = form.getProjectCategoryIds();
        long[] billingAccountIds = form.getBillingAccountIds();
        long[] customerIds = form.getCustomerIds();
        long[] statusIds = form.getStatusIds();
        Date startDate = DirectUtils.getDate(form.getStartDate());
        Date endDate = DirectUtils.getDate(form.getEndDate());

        boolean isFirstCall = this.viewData.isShowJustForm();

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

        // if status IDs are not specified then use all status ids
        boolean statusIdsAreSet = (statusIds != null) && (statusIds.length > 0);
        if (isFirstCall && !statusIdsAreSet) {
            statusIds = new long[COST_REPORT_CONTEST_STATUS.size()];
            int count = 0;
            for (Long l : COST_REPORT_CONTEST_STATUS.keySet()) {
                statusIds[count] = l;
                count++;
            }
            getFormData().setStatusIds(statusIds);
            statusIdsAreSet = true;
        }

        // If billing account IDs are not specified then use all billing account Ids
        boolean billingAccountIdsAreSet = (billingAccountIds != null) && (billingAccountIds.length > 0);
        if (isFirstCall && !billingAccountIdsAreSet) {
            // set to all by default
            billingAccountIds = new long[]{0};
            form.setBillingAccountIds(billingAccountIds);
            billingAccountIdsAreSet = true;
        }

        // If project IDs are not specified then use the first available from the projects assigned to user
        boolean projectIdsAreSet = (projectIds != null) && (projectIds.length > 0);
        if (isFirstCall && !projectIdsAreSet) {
            // set to all by default
            projectIds = new long[]{0};
            form.setProjectIds(projectIds);
            projectIdsAreSet = true;
        }

        // set all the project categories to view data to populate project category selection
        getViewData().setProjectCategories(projectCategories);

        // set all the report status to view data to populate report contest status (3 total: active, scheduled, finished)
        getViewData().setContestStatus(COST_REPORT_CONTEST_STATUS);

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
            getViewData().setProjectsLookupMap(DirectUtils.getProjectsForBilling(currentUser,
                    getFormData().getBillingAccountIds()[0]));
        }

        // add the default all for projects
        getViewData().getProjectsLookupMap().put(0L, "All Projects");

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

        // Validate the dates range
        if (startDate.compareTo(endDate) > 0) {
            addActionError("Start date must not be after end date");
            return;
        }

        // parse out studio categories ids
        List<Long> softwareProjectCategoriesList = new ArrayList<Long>();
        List<Long> studioProjectCategoriesList = new ArrayList<Long>();

        for(Long categoriesId : form.getProjectCategoryIds()) {
            if(categoriesId > 100) {
                studioProjectCategoriesList.add(categoriesId - 100);
            } else softwareProjectCategoriesList.add(categoriesId);
        }

        long[] softwareProjectCategories = DirectUtils.covertLongListToArray(softwareProjectCategoriesList);
        long[] studioProjectCategories = DirectUtils.covertLongListToArray(studioProjectCategoriesList);



        // If necessary get and process report data
        if (!getViewData().isShowJustForm()) {
            List<CostDetailsDTO> costDetails = DataProvider.getDashboardCostReportDetails(projectIds, softwareProjectCategories, studioProjectCategories,
                    customerIds, billingAccountIds, statusIds, startDate, endDate, COST_REPORT_CONTEST_STATUS_IDS);

            getViewData().setCostDetails(costDetails);

            if (!getFormData().isExcel()) {
                // generate aggregation reports from cost details only if the request is not for excel
                getViewData().setBillingAggregation(aggregateCostDetails(costDetails, CostAggregationType.BILLING_ACCOUNT_AGGREGATION));
                getViewData().setProjectAggregation(aggregateCostDetails(costDetails, CostAggregationType.DIRECT_PROJECT_AGGREGATION));
                getViewData().setContestTypeAggregation(aggregateCostDetails(costDetails, CostAggregationType.CONTEST_TYPE_AGGREGATION));
                getViewData().setStatusAggregation(aggregateCostDetails(costDetails, CostAggregationType.STATUS_AGGREGATION));

            }

        }

        // For normal request flow prepare various data to be displayed to user
        if (!getFormData().isExcel()) {
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
        }
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
     * Generates the aggregation cost report from the cost details. The <code>aggregationType</code> indicates which
     * fields of the cost details will be used to group.
     *
     * @param costDetails the cost details data.
     * @param aggregationType the aggregation type of the aggregation cost report..
     * @return the generated aggregation cost report data.
     */
    private Map<String, CostAggregationDTO> aggregateCostDetails(List<CostDetailsDTO> costDetails, CostAggregationType aggregationType) throws Exception {
        // Creates a map to store the aggregation cost report data
        Map<String, CostAggregationDTO> aggregationDTOMap = new HashMap<String, CostAggregationDTO>();

        // iterate all the cost details
        for (CostDetailsDTO c : costDetails) {
            // get the key used for aggregation report
            IdNamePair pair = getAggregationKey(c, aggregationType);
            CostAggregationDTO item = aggregationDTOMap.get(pair.getName());
            if (item == null) {
                // does not exist, create a new CostAggregationDTO
                item = new CostAggregationDTO();
                item.setAggregationType(aggregationType);
                long drillInId = pair.getId();
                if(c.isStudio() && aggregationType == CostAggregationType.CONTEST_TYPE_AGGREGATION) {
                    drillInId += 100;
                }
                item.setDrillInQuery(generateAggregationDrillInQuery(this.formData, aggregationType, drillInId));
                aggregationDTOMap.put(pair.getName(), item);
            }

            // aggregate the contest fee
            item.setTotalContestFees(item.getTotalContestFees() + c.getContestFee());

            if (c.getStatus().equals("Finished")) {
                // if the status is finished, aggregate the actual member cost
                item.setTotalActualMemberCosts(item.getTotalActualMemberCosts() + c.getActualCost());
            }

            // aggregate the estimated member cost
            item.setTotalEstimatedMemberCosts(item.getTotalEstimatedMemberCosts() + c.getEstimatedCost());

            // aggregate the total cost
            item.setTotalCosts(item.getTotalCosts() + c.getTotal());
        }

        return aggregationDTOMap;
    }

    /**
     * Gets the field value in CostDetailsDTO which is used for aggregation by aggregation type.
     *
     * @param costDetail the cost detail
     * @param aggregationType the aggregation type.
     * @return gets the field value in CostDetailsDTO used for aggregation.
     */
    private IdNamePair getAggregationKey(CostDetailsDTO costDetail, CostAggregationType aggregationType) {
        if (aggregationType == CostAggregationType.BILLING_ACCOUNT_AGGREGATION) {
            return costDetail.getBilling();
        } else if (aggregationType == CostAggregationType.DIRECT_PROJECT_AGGREGATION) {
            return costDetail.getProject();
        } else if (aggregationType == CostAggregationType.STATUS_AGGREGATION) {
            IdNamePair status = new IdNamePair();
            status.setName(costDetail.getStatus());
            status.setId(COST_REPORT_CONTEST_STATUS_IDS.get(status.getName().toLowerCase()));
            return status;
        } else return costDetail.getContestType();
    }

    /**
     * Generates the aggregation drill in query for aggregation cost report.
     *
     * @param formData the form data
     * @param aggregationType the aggregation type.
     * @param id the id used for drill in
     * @return the generated drill in url.
     * @throws Exception if any error occurs.
     */
    private static String generateAggregationDrillInQuery(DashboardCostReportForm formData, CostAggregationType aggregationType, long id) throws Exception {

        StringBuffer queryString = new StringBuffer();
        queryString.append(startDateParam + formData.getStartDate());
        queryString.append("&" + endDateParam + formData.getEndDate());
        for(long customerId : formData.getCustomerIds()) {
            queryString.append("&" + customerParam + customerId);
        }

        if (aggregationType == CostAggregationType.DIRECT_PROJECT_AGGREGATION
                || aggregationType == CostAggregationType.BILLING_ACCOUNT_AGGREGATION) {
            // concatenate contest types and status first
            for(long statusId : formData.getStatusIds()) {
                queryString.append("&" + statusIdParam + statusId);
            }
            for(long typeId : formData.getProjectCategoryIds()) {
                queryString.append("&" + contestTypeIdParam + typeId);
            }

            if (aggregationType == CostAggregationType.DIRECT_PROJECT_AGGREGATION) {
                for (long billingId : formData.getBillingAccountIds()) {
                    queryString.append("&" + billingIdParam + billingId);
                }
                // append direct project id
                queryString.append("&" + projectIdParam + id);
            } else {
                for (long projectId : formData.getProjectIds()) {
                    queryString.append("&" + projectIdParam + projectId);
                }
                // append billing account id
                queryString.append("&" + billingIdParam + id);
            }

        } else {
            // for status and contest type aggregation, append project id and billing id first
            for (long projectId : formData.getProjectIds()) {
                queryString.append("&" + projectIdParam + projectId);
            }
            for(long billingId : formData.getBillingAccountIds()) {
                queryString.append("&" + billingIdParam + billingId);
            }

            if (aggregationType == CostAggregationType.STATUS_AGGREGATION) {
                for (long typeId : formData.getProjectCategoryIds()) {
                    queryString.append("&" + contestTypeIdParam + typeId);
                }
                queryString.append("&" + statusIdParam + id);
            } else {
                for (long statusId : formData.getStatusIds()) {
                    queryString.append("&" + statusIdParam + statusId);
                }
                queryString.append("&" + contestTypeIdParam + id);
            }
        }

        return queryString.toString();
    }

}
