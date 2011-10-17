/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.report;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.IdNamePair;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.dto.dashboard.billingcostreport.BillingCostReportDTO;
import com.topcoder.direct.services.view.dto.dashboard.billingcostreport.BillingCostReportEntryDTO;
import com.topcoder.direct.services.view.dto.dashboard.billingcostreport.PaymentType;
import com.topcoder.direct.services.view.dto.dashboard.costreport.CostAggregationType;
import com.topcoder.direct.services.view.dto.dashboard.costreport.CostDetailsDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.form.DashboardBillingCostReportForm;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.security.TCSubject;
import com.topcoder.service.project.ProjectData;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>A <code>Struts</code> action to be used for handling the requests for viewing the billing cost report.</p>
 * <p/>
 *
 * @author Blues
 * @version 1.0 (TC Cockpit Billing Cost Report Assembly)
 */
public class DashboardBillingCostReportAction extends BaseDirectStrutsAction {

    /**
     * The status used by billing cost report. There are 3 status used for billing cost report. it's initialized in the
     * static constructor of this action class.
     */
    private static final Map<Long, String> BILLING_COST_REPORT_CONTEST_STATUS;

    /**
     * The status used by billing cost report. There are 3 status used for billing cost report. it's initialized in the
     * static constructor of this action class.
     */
    private static final Map<String, Long> BILLING_COST_REPORT_CONTEST_STATUS_IDS;

    /**
     * All the payment types used by billing cost report. There are 10 payment types used for billing cost report. it's
     * initialized in the static constructor of this action class.
     */
    private static final Map<Long, String> BILLING_COST_REPORT_PAYMENT_TYPES;

    /**
     * All the payment types used by billing cost report. There are 10 payment types used for billing cost report. it's
     * initialized in the static constructor of this action class.
     */
    private static final Map<String, Long> BILLING_COST_REPORT_PAYMENT_TYPES_IDS;

    /**
     * The default duration used for date filter.
     */
    private static final long DEFAULT_DURATION = 182 * 24 * 3600 * 1000L;

    /**
     * Status constructor of this action.
     */
    static {

        // initialize the billing cost report contest status
        BILLING_COST_REPORT_CONTEST_STATUS = new HashMap<Long, String>();
        BILLING_COST_REPORT_CONTEST_STATUS_IDS = new HashMap<String, Long>();

        IdNamePair scheduled = new IdNamePair();
        IdNamePair active = new IdNamePair();
        IdNamePair finished = new IdNamePair();
        scheduled.setId(1);
        scheduled.setName("Scheduled");
        active.setId(2);
        active.setName("Active");
        finished.setId(3);
        finished.setName("Finished");
        BILLING_COST_REPORT_CONTEST_STATUS.put(scheduled.getId(), scheduled.getName());
        BILLING_COST_REPORT_CONTEST_STATUS.put(active.getId(), active.getName());
        BILLING_COST_REPORT_CONTEST_STATUS.put(finished.getId(), finished.getName());
        BILLING_COST_REPORT_CONTEST_STATUS_IDS.put(scheduled.getName().toLowerCase(), scheduled.getId());
        BILLING_COST_REPORT_CONTEST_STATUS_IDS.put(active.getName().toLowerCase(), active.getId());
        BILLING_COST_REPORT_CONTEST_STATUS_IDS.put(finished.getName().toLowerCase(), finished.getId());

        // initialize the payment types mappings
        BILLING_COST_REPORT_PAYMENT_TYPES = new HashMap<Long, String>();
        BILLING_COST_REPORT_PAYMENT_TYPES_IDS = new HashMap<String, Long>();
        PaymentType[] allPaymentTypes = PaymentType.values();
        for (PaymentType pt : allPaymentTypes) {
            BILLING_COST_REPORT_PAYMENT_TYPES.put(pt.getId(), pt.getDescription());
            BILLING_COST_REPORT_PAYMENT_TYPES_IDS.put(pt.getDescription().toLowerCase(), pt.getId());
        }
    }

    /**
     * <p>A <code>SessionData</code> providing interface to current session.</p>
     */
    private SessionData sessionData;

    /**
     * <p>A <code>DashboardBillingCostReportForm</code> providing the billing cost report form parameters submitted by
     * user.</p>
     */
    private DashboardBillingCostReportForm formData;

    /**
     * <p>Direct projects data accessible by current user.
     */
    private List<ProjectData> directProjectsData;

    /**
     * <p>A <code>BillingCostReportDTO</code> providing the view data for displaying by <code>Billing Cost
     * Report</code> view. </p>
     */
    private BillingCostReportDTO viewData;

    /**
     * <p>Constructs new <code>DashboardBillingCostReportAction</code> instance</p>
     */
    public DashboardBillingCostReportAction() {
        this.viewData = new BillingCostReportDTO();
        this.formData = new DashboardBillingCostReportForm();
    }

    /**
     * <p>Gets the form data.</p>
     *
     * @return an <code>DashboardBillingCostReportForm</code> providing the data for billing cost report form
     * submitted by user.
     */
    public DashboardBillingCostReportForm getFormData() {
        return this.formData;
    }

    /**
     * <p>Gets the data to be displayed by view mapped to this action.</p>
     *
     * @return an <code>BillingCostReportDTO</code> providing the storage for data to be rendered by the view mapped to this
     *         action.
     */
    public BillingCostReportDTO getViewData() {
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
     * <p>Handles the incoming request. Retrieves data for Billing Cost Report and binds it to request.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        // Get current session
        HttpServletRequest request = DirectUtils.getServletRequest();
        this.sessionData = new SessionData(request.getSession());
        TCSubject currentUser = getCurrentUser();

        // Get the list of available project categories
        Map<Long, String> projectCategories = DataProvider.getAllProjectCategories();

        // Get all the clients accessible by current user
        Map<Long, String> customers = DirectUtils.getAllClients(currentUser);

        // Analyze form parameters
        DashboardBillingCostReportForm form = getFormData();

        long[] projectIds = form.getProjectIds();
        long[] categoryIds = form.getProjectCategoryIds();
        long[] billingAccountIds = form.getBillingAccountIds();
        long[] customerIds = form.getCustomerIds();
        long[] statusIds = form.getStatusIds();
        long[] paymentTypeIds = form.getPaymentTypeIds();

        long contestId = 0;

        Date startDate = DirectUtils.getDate(form.getStartDate());
        Date endDate = DirectUtils.getDate(form.getEndDate());

        boolean isFirstCall = this.viewData.isShowJustForm();

        // if user is TcOperations
        boolean isTcOperations = DirectUtils.isTcOperations(sessionData.getCurrentUser());

        // If client account IDs are not specified then use the first client account id
        boolean customerIdsAreSet = (customerIds != null) && (customerIds.length > 0);
        if (isFirstCall && !customerIdsAreSet) {
            customerIds = new long[1];
            for (long clientId : customers.keySet()) {
                customerIds[0] = clientId;
                break;
            }
            form.setCustomerIds(customerIds);

            // if user is in the admin group change it to all customers
            if(isTcOperations) {
               customerIds[0] = 0;
            }

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
            statusIds = new long[BILLING_COST_REPORT_CONTEST_STATUS.size()];
            int count = 0;
            for (Long l : BILLING_COST_REPORT_CONTEST_STATUS.keySet()) {
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

        // If payment type IDs are not specified then use all the payment types by default
        boolean  paymentTypeIdsAreSet = (paymentTypeIds != null) && (paymentTypeIds.length > 0);
        if (isFirstCall && !paymentTypeIdsAreSet) {
            // set to all payment types by default
            PaymentType[] paymentTypes = PaymentType.values();
            long[] allPaymentTypeIds = new long[paymentTypes.length];
            for(int i = 0; i < allPaymentTypeIds.length; ++i) {
                allPaymentTypeIds[i] = paymentTypes[i].getId();
            }
            form.setPaymentTypeIds(allPaymentTypeIds);
            paymentTypeIdsAreSet = true;
        }

        // set all the project categories to view data to populate project category selection
        getViewData().setProjectCategories(projectCategories);

        // set all the payment types to view data to populate payment type selection
        getViewData().setPaymentTypes(BILLING_COST_REPORT_PAYMENT_TYPES);

        // set all the report status to view data to populate report contest status (3 total: active, scheduled, finished)
        getViewData().setContestStatus(BILLING_COST_REPORT_CONTEST_STATUS);

        // set view data for clients

        // if current user is cockpit admin, add "all customers" option
        if (isTcOperations) {
            Map<Long, String> tmpCustomers = new LinkedHashMap<Long, String>();

            tmpCustomers.put(0L, "All Customers");

            for(Map.Entry<Long, String> c : customers.entrySet()) {
                tmpCustomers.put(c.getKey(), c.getValue());
            }

            customers = tmpCustomers;
        }

        getViewData().setClientAccounts(customers);

        // set view data for billings
        if (getFormData().getCustomerIds() != null && getFormData().getCustomerIds().length > 0) {

            if (getFormData().getCustomerIds()[0] != 0) {

                getViewData().setClientBillingProjects(DirectUtils.getBillingsForClient(currentUser,
                        getFormData().getCustomerIds()[0]));
            } else {
                getViewData().setClientBillingProjects(new HashMap<Long, String>());
            }

        } else {
            getViewData().setClientBillingProjects(new HashMap<Long, String>());
        }

        // add the default all for billings
        getViewData().getClientBillingProjects().put(0L, "All Billing Accounts");

        // set view data for projects
        if (getFormData().getBillingAccountIds()[0] <= 0) {

            // billing account is set to all or negative
            if (getFormData().getCustomerIds() != null && getFormData().getCustomerIds().length > 0) {

                if (getFormData().getCustomerIds()[0] != 0) {
                    getViewData().setProjectsLookupMap(DirectUtils.getProjectsForClient(currentUser,
                            getFormData().getCustomerIds()[0]));
                } else {
                    getViewData().setProjectsLookupMap(new HashMap<Long, String>());
                }


            } else {
                getViewData().setProjectsLookupMap(new HashMap<Long, String>());
            }
        } else {

            getViewData().setProjectsLookupMap(DirectUtils.getProjectsForBilling(currentUser, getFormData().getBillingAccountIds()[0]));

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

        if (!getViewData().isShowJustForm()) {

            try {
                contestId = Long.parseLong(form.getContestId());
            } catch (Exception ex) {
                addActionError("Contest Id is not valid");
                return;
            }
        }


        // parse out studio categories ids
        List<Long> softwareProjectCategoriesList = new ArrayList<Long>();
        List<Long> studioProjectCategoriesList = new ArrayList<Long>();

        for (Long categoriesId : form.getProjectCategoryIds()) {
            if (categoriesId > 100) {
                studioProjectCategoriesList.add(categoriesId - 100);
            } else softwareProjectCategoriesList.add(categoriesId);
        }

        long[] softwareProjectCategories = DirectUtils.covertLongListToArray(softwareProjectCategoriesList);
        long[] studioProjectCategories = DirectUtils.covertLongListToArray(studioProjectCategoriesList);


        // If necessary get and process report data
        if (!getViewData().isShowJustForm()) {

            Map<Long, List<BillingCostReportEntryDTO>> billingCosts = DataProvider.getDashboardBillingCostReport
                    (projectIds,
                    softwareProjectCategories, studioProjectCategories, paymentTypeIds,
                    customerIds, billingAccountIds, statusIds, contestId, startDate, endDate,
                    BILLING_COST_REPORT_CONTEST_STATUS_IDS, BILLING_COST_REPORT_PAYMENT_TYPES_IDS);

            List<BillingCostReportEntryDTO> viewData = new ArrayList<BillingCostReportEntryDTO>();

            for(List<BillingCostReportEntryDTO> contestEntries : billingCosts.values()) {
                viewData.addAll(contestEntries);
            }

            Collections.sort(viewData, new Comparator<BillingCostReportEntryDTO>() {
                public int compare(BillingCostReportEntryDTO one, BillingCostReportEntryDTO other) {
                    return (int) (one.getContest().getId() - other.getContest().getId());
                }
            });

            getViewData().setEntries(viewData);

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
        if (this.directProjectsData == null) {
            this.directProjectsData = getProjects();
        }
        return this.directProjectsData;
    }

}
