/*
 * Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.report;

import com.topcoder.clients.invoices.dao.LookupDAO;
import com.topcoder.direct.services.project.metadata.entities.dao.TcDirectProject;
import com.topcoder.direct.services.project.metadata.entities.dto.MetadataKeyIdValueFilter;
import com.topcoder.direct.services.project.metadata.entities.dto.MetadataValueOperator;
import com.topcoder.direct.services.view.dto.IdNamePair;
import com.topcoder.direct.services.view.dto.dashboard.billingcostreport.BillingCostReportDTO;
import com.topcoder.direct.services.view.dto.dashboard.billingcostreport.BillingCostReportEntryDTO;
import com.topcoder.direct.services.view.dto.dashboard.billingcostreport.PaymentType;
import com.topcoder.direct.services.view.form.DashboardBillingCostReportForm;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>A <code>Struts</code> action to be used for handling the requests for viewing the billing cost report.</p>
 * <p/>
 *
 * <p>
 * Version 1.1 (TC Cockpit Permission and Report Update One) change log:
 * <ol>
 *   <li>This class has been refactoring. It's extended from <code>DashboardReportBaseAction</code>. The base class will
 *   parse and validate the parameters, prepare for the common data used by the report page.</li>
 *   <li>Updated method {@link #executeAction()()} to pass <code>TCSubject</code> object to method
 *     <code>DataProvided.getDashboardBillingCostReport</code> which will use <code>TCSubject</code> to
 *     check the user's permission.</li>
 * </ol>
 * </p>
 * 
 * <p>
 * Version 1.1 (TC Accounting Tracking Invoiced Payments) change notes:
 * <ol>
 *   <li>Updated {@link #executeAction()} method to pass extra parameters to <code>DataProvider.getDashboardBillingCostReport</code>.</li>
 * </ol>
 * </p>
 * 
 * <p>
 * Version 1.2 (TC Accounting Tracking Invoiced Payments Part 2) change notes:
 * <ol>
 *   <li>Added {@link #getCurrentDate()} method to get the current date of server.</li>
 *   <li>Removed field <code>invoiceRecordDAO</code>.</li>
 *   <li>Updated method {@link #executeAction()} to remove invoiceRecordDAO from parameter list
 *   when calling DataProvider.getDashboardBillingCostReport.</li>
 * </ol>
 * </p>
 *
 * <p>
  * Version 1.3 (Release Assembly - TC Cockpit Report Filters Group By Metadata Feature and Coordination
 *  Improvement) change notes:
  * <ol>
  *   <li>Added {@link #filterByGroups(java.util.List)} ()} method to filter by group by and group values</li>
  *   <li>Updated method {@link #executeAction()} to apply filters with group by and group values.</li>
  * </ol>
  * </p>
 * 
 * <p>
 * Version 1.4: (Module Assembly - Add Monthly Platform Fee Feature to Admin Page) change notes:
 * <ol>
 *   <li>Updated method {@link #executeAction()} and {@link #filterByGroups(java.util.List)}
 *   to support customer Platform Fee records. </li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.5 (Release Assembly - TC Direct Cockpit Release Six) changes:
 * <ol>
 *     <li>
 *   Updated method {@link #executeAction()} to handle invoice number drop down and calculate data for the aggregation
 *   statistics of billing cost report.
 *     </li>
 * </ol>
 * </p>
 * 
 * @author Blues, GreatKevin
 * @version 1.5
 */
public class DashboardBillingCostReportAction extends DashboardReportBaseAction<DashboardBillingCostReportForm, BillingCostReportDTO> {

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
     * Status constructor of this action.
     */
    static {

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
     * <p>The instance of <code>LookupDAO</code>. Used to retrieve <code>InvoiceType</code> data. Will
     * be injected by Spring IoC.</p>
     * 
     * @since 1.1
     */
    private LookupDAO lookupDAO;

    /**
     * <p>Constructs new <code>DashboardBillingCostReportAction</code> instance</p>
     */
    public DashboardBillingCostReportAction() {
        super();
        
        setViewData(new BillingCostReportDTO());
        setFormData(new DashboardBillingCostReportForm());
    }

    /**
     * <p>Sets the instance of <code>LookupDAO</code>.</p>
     * 
     * @param lookupDAO the instance of <code>LookupDAO</code>.
     * @since 1.1
     */
    public void setLookupDAO(LookupDAO lookupDAO) {
        this.lookupDAO = lookupDAO;
    }
    
    /**
     * <p>Gets the current date.</p>
     *  
     * @return the current date
     * @since 1.2
     */
    public Date getCurrentDate() {
        return new Date();
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
     * <p>
     * Version 1.3 updates:
     * - Add the filter of group by and group values.
     * </p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        super.executeAction();
        
        if (hasActionErrors()) {
            return;
        }

        // Analyze form parameters
        DashboardBillingCostReportForm form = getFormData();

        long projectId = form.getProjectId();
        long billingAccountId = form.getBillingAccountId();
        long customerId = form.getCustomerId();
        long[] statusIds = form.getStatusIds();
        long[] paymentTypeIds = form.getPaymentTypeIds();
        String invoiceNumberInput = form.getInvoiceNumber();
        String invoiceNumberSelection = form.getInvoiceNumberSelection();
        String invoiceNumber = null;

        if(invoiceNumberInput != null && invoiceNumberInput.trim().length() > 0) {
            invoiceNumber = invoiceNumberInput;
        } else if(invoiceNumberSelection != null && invoiceNumberSelection.trim().length() > 0) {
            invoiceNumber = invoiceNumberSelection;
        }

        long contestId = 0;

        Date startDate = DirectUtils.getDate(form.getStartDate());
        Date endDate = DirectUtils.getDate(form.getEndDate());

        boolean isFirstCall = getViewData().isShowJustForm();

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

        // set all the payment types to view data to populate payment type selection
        getViewData().setPaymentTypes(BILLING_COST_REPORT_PAYMENT_TYPES);

        boolean invoiceNumberSelectionIsSet = invoiceNumberSelection != null && (invoiceNumberSelection.trim().length() > 0);

        if(isFirstCall && !invoiceNumberSelectionIsSet) {
            invoiceNumberSelection = "";
        }

        List<String> invoiceNumbers = new ArrayList<String>();

        final List<String> invoiceNumbersFromBilling = DataProvider.getInvoiceNumbersFromBilling(billingAccountId);

        if(invoiceNumbersFromBilling != null && invoiceNumbersFromBilling.size() > 0) {
            invoiceNumbers.add("");
            invoiceNumbers.addAll(invoiceNumbersFromBilling);
        }

        getViewData().setInvoiceNumbers(invoiceNumbers);

        // narrow down project if invoiceNumberSelectionIsSet
        if(invoiceNumberSelectionIsSet && billingAccountId > 0) {
            Map<Long, String> invoicedProjects = new HashMap<Long, String>();
            invoicedProjects.put(0L, "All projects");
            final List<IdNamePair> projectsForInvoiceNumberAndBilling =
                    DataProvider.getProjectsForInvoiceNumberAndBilling(invoiceNumberSelection, billingAccountId);
            for(IdNamePair item : projectsForInvoiceNumberAndBilling) {
                invoicedProjects.put(item.getId(), item.getName());
            }
            getViewData().setProjectsLookupMap(invoicedProjects);
        }

        if (!getViewData().isShowJustForm() && form.getContestId() != null && form.getContestId().trim().length() > 0) {

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

        if (form.getProjectCategoryIds() != null) {
            for (Long categoriesId : form.getProjectCategoryIds()) {
                if (categoriesId > 100) {
                    studioProjectCategoriesList.add(categoriesId - 100);
                } else softwareProjectCategoriesList.add(categoriesId);
            }
        }

        long[] softwareProjectCategories = DirectUtils.covertLongListToArray(softwareProjectCategoriesList);
        long[] studioProjectCategories = DirectUtils.covertLongListToArray(studioProjectCategoriesList);

        // If necessary get and process report data
        if (!getViewData().isShowJustForm()) {

            Map<Long, List<BillingCostReportEntryDTO>> billingCosts = DataProvider.getDashboardBillingCostReport
                    (lookupDAO.getAllInvoiceTypes(), getCurrentUser(), projectId,
                            softwareProjectCategories, studioProjectCategories, paymentTypeIds,
                            customerId, billingAccountId, statusIds, contestId, invoiceNumber, startDate, endDate,
                            REPORT_CONTEST_STATUS_IDS, BILLING_COST_REPORT_PAYMENT_TYPES_IDS);

            List<BillingCostReportEntryDTO> viewData = new ArrayList<BillingCostReportEntryDTO>();

            for (List<BillingCostReportEntryDTO> contestEntries : billingCosts.values()) {
                viewData.addAll(contestEntries);
            }

            if((form.getInvoiceNumber() == null || form.getInvoiceNumber().trim().length() <= 0) && contestId <= 0) {
                viewData = filterByGroups(viewData);
            }
            
            // filter out the platform fee items
            final boolean ignorePlatformFee = 
                (contestId > 0 || projectId > 0 || billingAccountId > 0 || customerId <= 0);
            if (ignorePlatformFee) {
                List<BillingCostReportEntryDTO> viewData2 = new ArrayList<BillingCostReportEntryDTO>();
                for (BillingCostReportEntryDTO item : viewData) {
                    if (!PaymentType.PLATFORM_FEE.getDescription().equalsIgnoreCase(item.getPaymentType())) {
                        viewData2.add(item);
                    }
                }
                viewData = viewData2;
            }

            Collections.sort(viewData, new Comparator<BillingCostReportEntryDTO>() {
                public int compare(BillingCostReportEntryDTO one, BillingCostReportEntryDTO other) {
                    if (ignorePlatformFee) {
                        return (int) (one.getContest().getId() - other.getContest().getId());
                    } else {
                        boolean isPlatformFee1 = PaymentType.PLATFORM_FEE.getDescription().
                            equalsIgnoreCase(one.getPaymentType());
                        boolean isPlatformFee2 = PaymentType.PLATFORM_FEE.getDescription().
                            equalsIgnoreCase(other.getPaymentType());
                        if (isPlatformFee1 == isPlatformFee2) {
                            return (int) (one.getContest().getId() - other.getContest().getId());
                        } else {
                            return isPlatformFee1 ? 1 : -1;
                        }
                    }
                }
            });

            getViewData().setEntries(viewData);
            
		        // count the total contest number and  bug race number
		        Set<Long> uniqueContestSet = new HashSet<Long>();
		        Set<String> uniqueBugRaces = new HashSet<String>();
		
		        for(BillingCostReportEntryDTO entry : getViewData().getEntries()) {
		            if(entry.getContest() != null) {
		                uniqueContestSet.add(entry.getContest().getId());
		            }
		            if(entry.getPaymentType().trim().equalsIgnoreCase("bugs")) {
		                uniqueBugRaces.add(entry.getReferenceId());
		            }
		        }
		
		        // set aggregation stats
		        getViewData().setTotalContestsNumber(uniqueContestSet.size());
		        getViewData().setTotalBugRacesNumber(uniqueBugRaces.size());

        }


        this.getViewData().setCanProcessInvoices(DirectUtils.canPerformInvoiceRecords(getCurrentUser()));
    }

    /**
     * Filter the list of BillingCostReportEntryDTO by the group by and group values.
     *
     * @param listToFilter
     * @return the filtered list of <code>BillingCostReportEntryDTO</code>
     * @throws Exception if there is error
     * @since 1.3
     */
    private List<BillingCostReportEntryDTO> filterByGroups(List<BillingCostReportEntryDTO> listToFilter) throws Exception {

        List<BillingCostReportEntryDTO> result = listToFilter;
        Set<Long> projectIdsHasGroupId = null;

        if (getFormData().getCustomerId() > 0 && getFormData().getGroupId() > 0) {
            Map<Long, String> projectIdsFilterWithValue = getMetadataService().searchProjectIdsWithMetadataValues(
                    getFormData().getGroupId(), getFormData().getGroupValues());

            if (getFormData().getGroupValues() != null) {
                boolean hasNone = false;
                for (String v : getFormData().getGroupValues()) {
                    if (v.toLowerCase().equals("none")) {
                        hasNone = true;
                        break;
                    }
                }

                if (hasNone) {
                    MetadataKeyIdValueFilter idValueFilter = new MetadataKeyIdValueFilter();
                    idValueFilter.setMetadataValue("");
                    idValueFilter.setMetadataValueOperator(MetadataValueOperator.LIKE);
                    idValueFilter.setProjectMetadataKeyId(getFormData().getGroupId());
                    final List<TcDirectProject> projectsHasGroupId = getMetadataService().searchProjects(idValueFilter);
                    projectIdsHasGroupId = new HashSet<Long>();
                    if (projectsHasGroupId != null) {
                        for (TcDirectProject p : projectsHasGroupId) {
                            projectIdsHasGroupId.add(p.getProjectId());
                        }
                    }
                }
            }

            result = new ArrayList<BillingCostReportEntryDTO>();

            for (BillingCostReportEntryDTO dto : listToFilter) {
                if (PaymentType.PLATFORM_FEE.getDescription().equalsIgnoreCase(dto.getPaymentType())) {
                    result.add(dto);
                } else {
                    if (projectIdsFilterWithValue.containsKey(dto.getProject().getId())) {
                        dto.setProjectFilterValue(projectIdsFilterWithValue.get(dto.getProject().getId()));
                        result.add(dto);
                    }
    
                    if (projectIdsHasGroupId != null) {
                        if (!projectIdsHasGroupId.contains(dto.getProject().getId())) {
                            dto.setProjectFilterValue("None");
                            result.add(dto);
                        }
                    }
                }
            }
        }

        return result;
    }
}
