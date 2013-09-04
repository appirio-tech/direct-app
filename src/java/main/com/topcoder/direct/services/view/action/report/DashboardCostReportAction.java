/*
 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.report;

import com.topcoder.direct.services.project.metadata.entities.dao.TcDirectProject;
import com.topcoder.direct.services.project.metadata.entities.dto.MetadataKeyIdValueFilter;
import com.topcoder.direct.services.project.metadata.entities.dto.MetadataValueOperator;
import com.topcoder.direct.services.view.dto.IdNamePair;
import com.topcoder.direct.services.view.dto.dashboard.costreport.CostAggregationDTO;
import com.topcoder.direct.services.view.dto.dashboard.costreport.CostAggregationType;
import com.topcoder.direct.services.view.dto.dashboard.costreport.CostDetailsDTO;
import com.topcoder.direct.services.view.dto.dashboard.costreport.CostReportDTO;
import com.topcoder.direct.services.view.form.DashboardCostReportForm;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
 * <p>Version 1.1.1 (TC Cockpit Cost Report Update Cost Breakdown Assembly) change notes:
 * <ol>
 *     <li>Update {@link #execute()} to support exporting cost breakdown report to excel.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.2 (TC Cockpit Permission and Report Update One) change log:
 * <ol>
 *   <li>This class has been refactoring. It's extended from <code>DashboardReportBaseAction</code>. The base class will
 *   parse and validate the parameters, prepare for the common data used by the report page.</li>
 *   <li>Updated method {@link #aggregateCostDetails(List, CostAggregationType)} to return <code>List</code>
 *   instead of <code>Map</code>.</li>
 *   <li>Updated method {@link #generateAggregationDrillInQuery(DashboardCostReportForm, CostAggregationType, long)} to
 *    use the new parameters name.</li>
 *   <li>Updated method {@link #executeAction()()} to pass <code>TCSubject</code> object to method
 *     <code>DataProvided.getDashboardCostReportDetails</code> which will use <code>TCSubject</code> to
 *     check the user's permission.</li>
 * </ol>
 * </p>
 *
 *  <p>
  * Version 1.3 (Release Assembly - TC Cockpit Report Filters Group By Metadata Feature and Coordination Improvement) change log:
  * <ol>
  *   <li>Added method {@link #filterByGroups(java.util.List)}  to filter the report result by group by and group values.
  *   <li>Updated method {@link #executeAction()()} to apply filter of group vy and group values</li>
  * </ol>
  * </p>
 *
 * <p>
 * Version 1.4 (Release Assembly - TopCoder Cockpit Copilot Selection Update and Other Fixes Assembly)
 * <ul>
 *     <li>Updates the cost report contest status to divide finished status into 3 status: completed, failed, cancelled</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.5 (Release Assembly - TC Cockpit Project Total Cost Fixes)
 * <ul>
 *     <li>Updates methods {@link #aggregateCostDetails(java.util.List, com.topcoder.direct.services.view.dto.dashboard.costreport.CostAggregationType)}
 *     and {@link #getAggregationKey(com.topcoder.direct.services.view.dto.dashboard.costreport.CostDetailsDTO, com.topcoder.direct.services.view.dto.dashboard.costreport.CostAggregationType)}
 *     to handle the project level cost case.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.6 (Cockpit - Incorrect Cost Report Result - BUGR-9455)
 * <ul>
 *     <li>Add method {@link #filterBugRace(java.util.List)}</li>
 *     <li>Update method {@link #executeAction()}</li>
 * </ul>
 * </p>
 * @author Blues, flexme, GreatKevin
 * @version 1.6
 */
public class DashboardCostReportAction extends DashboardReportBaseAction<DashboardCostReportForm, CostReportDTO> {

    /**
     * Represents the project ID parameter string.
     */
    private static final String projectIdParam = "formData.projectId=";
    /**
     * Represents the start date parameter string.
     */
    private static final String startDateParam = "formData.startDate=";
    /**
     * Represents the end date parameter string.
     */
    private static final String endDateParam = "formData.endDate=";
    /**
     * Represents the customer ID parameter string.
     */
    private static final String customerParam = "formData.customerId=";
    /**
     * Represents the billing account ID parameter string.
     */
    private static final String billingIdParam = "formData.billingAccountId=";
    /**
     * Represents the project category IDs parameter string.
     */
    private static final String contestTypeIdParam = "formData.projectCategoryIds=";
    /**
     * Represents the contest status IDs parameter string.
     */
    private static final String statusIdParam = "formData.statusIds=";

    /**
     * <p>The contest status used by report. There are 5 status used for report. It's initialized in the
     * static constructor of this action class.</p>
     *
     * @since 1.4
     */
    protected static final Map<Long, String> COST_REPORT_CONTEST_STATUS;

    /**
     * <p>The status used by report. There are 5 status used for billing cost report. it's initialized in the
     * static constructor of this action class.</p>
     *
     * @since 1.4
     */
    protected static final Map<String, Long> COST_REPORT_CONTEST_STATUS_IDS;

    static {

        // initialize the cost report contest status
        COST_REPORT_CONTEST_STATUS = new HashMap<Long, String>();
        COST_REPORT_CONTEST_STATUS_IDS = new HashMap<String, Long>();

        IdNamePair scheduled = new IdNamePair();
        IdNamePair active = new IdNamePair();
        IdNamePair completed = new IdNamePair();
        IdNamePair failed = new IdNamePair();
        IdNamePair cancelled = new IdNamePair();
        scheduled.setId(1);
        scheduled.setName("Scheduled");
        active.setId(2);
        active.setName("Active");
        completed.setId(3);
        completed.setName("Completed");
        failed.setId(4);
        failed.setName("Failed");
        cancelled.setId(5);
        cancelled.setName("Cancelled");
        COST_REPORT_CONTEST_STATUS.put(scheduled.getId(), scheduled.getName());
        COST_REPORT_CONTEST_STATUS.put(active.getId(), active.getName());
        COST_REPORT_CONTEST_STATUS.put(completed.getId(), completed.getName());
        COST_REPORT_CONTEST_STATUS.put(failed.getId(), failed.getName());
        COST_REPORT_CONTEST_STATUS.put(cancelled.getId(), cancelled.getName());

        COST_REPORT_CONTEST_STATUS_IDS.put(scheduled.getName().toLowerCase(), scheduled.getId());
        COST_REPORT_CONTEST_STATUS_IDS.put(active.getName().toLowerCase(), active.getId());
        COST_REPORT_CONTEST_STATUS_IDS.put(completed.getName().toLowerCase(), completed.getId());
        COST_REPORT_CONTEST_STATUS_IDS.put(failed.getName().toLowerCase(), failed.getId());
        COST_REPORT_CONTEST_STATUS_IDS.put(cancelled.getName().toLowerCase(), cancelled.getId());
    }

    /**
     * <p>Constructs new <code>DashboardCostReportAction</code> instance</p>
     */
    public DashboardCostReportAction() {
        super();
        setViewData(new CostReportDTO());
        setFormData(new DashboardCostReportForm());
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
                getViewData().setShowBreakdown(getFormData().isShowBreakdown());
                return "download";
            }
        }
        return result;
    }

    /**
     * <p>Handles the incoming request. Retrieves data for Cost Report and binds it to request.</p>
     *
     * <p>
     *  Version 1.3 updates:
     *  - Apply the filters of group by and group values.
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
        DashboardCostReportForm form = getFormData();

        long projectId = form.getProjectId();
        long billingAccountId = form.getBillingAccountId();
        long customerId = form.getCustomerId();
        long[] statusIds = form.getStatusIds();
        Date startDate = DirectUtils.getDate(form.getStartDate());
        Date endDate = DirectUtils.getDate(form.getEndDate());

        boolean statusIdsAreSet = (statusIds != null) && (statusIds.length > 0);
        if (getViewData().isShowJustForm() || !statusIdsAreSet) {
            statusIds = new long[COST_REPORT_CONTEST_STATUS.size()];
            int count = 0;
            for (Long l : COST_REPORT_CONTEST_STATUS.keySet()) {
                statusIds[count] = l;
                count++;
            }
            getFormData().setStatusIds(statusIds);
        }

        getViewData().setContestStatus(COST_REPORT_CONTEST_STATUS);

    	//add bug race top level filter
    	getViewData().getProjectCategories().put(DirectUtils.BUGR_CONTEST_TYPE_ID, DirectUtils.BUG_RACE_CONTEST_NAME);
    	
        // parse out studio categories ids
        List<Long> softwareProjectCategoriesList = new ArrayList<Long>();
        List<Long> studioProjectCategoriesList = new ArrayList<Long>();

        boolean isBugRaceHide = true;

        long[] formProjectCategoryIds = form.getProjectCategoryIds();
        if (formProjectCategoryIds != null ) {
	        for(Long categoriesId : formProjectCategoryIds) {
	        	if (categoriesId == DirectUtils.BUGR_CONTEST_TYPE_ID) {
	        		isBugRaceHide = false;
	        	}
	        	if(categoriesId > 100) {
	                studioProjectCategoriesList.add(categoriesId - 100);
	            } else {
	            	softwareProjectCategoriesList.add(categoriesId);
	            }
	        }
        }

        long[] softwareProjectCategories = DirectUtils.covertLongListToArray(softwareProjectCategoriesList);
        long[] studioProjectCategories = DirectUtils.covertLongListToArray(studioProjectCategoriesList);

        // If necessary get and process report data
        if (!getViewData().isShowJustForm()) {
            List<CostDetailsDTO> costDetails = DataProvider.getDashboardCostReportDetails(getCurrentUser(), projectId, softwareProjectCategories, studioProjectCategories,
                    customerId, billingAccountId, statusIds, startDate, endDate, COST_REPORT_CONTEST_STATUS_IDS);

            costDetails = filterByGroups(costDetails);

            if (isBugRaceHide) {
            	costDetails = filterBugRace(costDetails);
            }
            
            getViewData().setCostDetails(costDetails);

            if (!getFormData().isExcel()) {
                // generate aggregation reports from cost details only if the request is not for excel
                getViewData().setBillingAggregation(aggregateCostDetails(costDetails, CostAggregationType.BILLING_ACCOUNT_AGGREGATION));
                getViewData().setProjectAggregation(aggregateCostDetails(costDetails, CostAggregationType.DIRECT_PROJECT_AGGREGATION));
                getViewData().setContestTypeAggregation(aggregateCostDetails(costDetails, CostAggregationType.CONTEST_TYPE_AGGREGATION));
                getViewData().setStatusAggregation(aggregateCostDetails(costDetails, CostAggregationType.STATUS_AGGREGATION));
            }
        } else {
        	//add bug race top filter at first time
        	long[] oldProjectCategoryIds = getFormData().getProjectCategoryIds();
        	int len = oldProjectCategoryIds.length;
        	long[] newProjectCategoryIds = new long[len+1];
        	System.arraycopy(oldProjectCategoryIds, 0, newProjectCategoryIds, 0, len);
        	newProjectCategoryIds[len] = DirectUtils.BUGR_CONTEST_TYPE_ID;
        	getFormData().setProjectCategoryIds(newProjectCategoryIds);
        }
    }
    /**
     * Filters the result list of <code>CostDetailsDTO</code> for not showing the bug race cost.
     *
     * @param listToFilter the list of <code>CostDetailsDTO</code> to filter.
     * @return the filtered list of <code>CostDetailsDTO</code>
     * @throws Exception if there is error
     * @since 1.6
     */
    private List<CostDetailsDTO> filterBugRace(List<CostDetailsDTO> listToFilter) throws Exception {
    	List<CostDetailsDTO> result = new ArrayList<CostDetailsDTO>();
    	
    	for (CostDetailsDTO dto : listToFilter) {
    		if (!dto.getContestType().getName().equals(DirectUtils.BUG_RACE_CONTEST_NAME)) {
    			result.add(dto);
        	}
    	}
        
        return result;
    }
    /**
     * Filters the result list of <code>CostDetailsDTO</code> with group by and group values.
     *
     * @param listToFilter the list of <code>CostDetailsDTO</code> to filter.
     * @return the filtered list of <code>CostDetailsDTO</code>
     * @throws Exception if there is error
     * @since 1.3
     */
    private List<CostDetailsDTO> filterByGroups(List<CostDetailsDTO> listToFilter) throws Exception {

        List<CostDetailsDTO> result = listToFilter;
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

            result = new ArrayList<CostDetailsDTO>();

            for (CostDetailsDTO dto : listToFilter) {
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

        return result;
    }

    /**
     * Generates the aggregation cost report from the cost details. The <code>aggregationType</code> indicates which
     * fields of the cost details will be used to group.
     *
     * @param costDetails the cost details data.
     * @param aggregationType the aggregation type of the aggregation cost report..
     * @return the generated aggregation cost report data.
     */
    private List<CostAggregationDTO> aggregateCostDetails(List<CostDetailsDTO> costDetails, CostAggregationType aggregationType) throws Exception {
        // Creates a map to store the aggregation cost report data
        Map<String, CostAggregationDTO> aggregationDTOMap = new HashMap<String, CostAggregationDTO>();

        // iterate all the cost details
        for (CostDetailsDTO c : costDetails) {
            // get the key used for aggregation report
            IdNamePair pair = getAggregationKey(c, aggregationType);

            if(pair.getName() == null) {
                continue;
            }

            CostAggregationDTO item = aggregationDTOMap.get(pair.getName()+"-"+pair.getId());
            if (item == null) {
                // does not exist, create a new CostAggregationDTO
                item = new CostAggregationDTO();
                item.setAggregationType(aggregationType);
                item.setName(pair.getName());
                long drillInId = pair.getId();
                if(c.isStudio() && aggregationType == CostAggregationType.CONTEST_TYPE_AGGREGATION) {
                    drillInId += 100;
                }
                item.setDrillInQuery(generateAggregationDrillInQuery(getFormData(), aggregationType, drillInId));
                aggregationDTOMap.put(pair.getName()+"-"+pair.getId(), item);
            }

            // aggregate the contest fee
            item.setTotalContestFees(item.getTotalContestFees() + c.getContestFee());

            //if (c.getStatus().equals("Finished")) {
                // if the status is finished, aggregate the actual member cost
                item.setTotalActualMemberCosts(item.getTotalActualMemberCosts() + c.getActualCost());
            //}

            // aggregate the estimated member cost
            item.setTotalEstimatedMemberCosts(item.getTotalEstimatedMemberCosts() + c.getEstimatedCost());

            // aggregate the total cost
            item.setTotalCosts(item.getTotalCosts() + c.getTotal());
        }

        return new LinkedList<CostAggregationDTO>(aggregationDTOMap.values());
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
            if(costDetail.getStatus() != null) {
                status.setName(costDetail.getStatus());
                status.setId(COST_REPORT_CONTEST_STATUS_IDS.get(status.getName().toLowerCase()));
            }
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
        if (formData.getCustomerId() > 0) {
            queryString.append("&" + customerParam + formData.getCustomerId());
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
                if (formData.getBillingAccountId() > 0) {
                    queryString.append("&" + billingIdParam + formData.getBillingAccountId());
                }
                // append direct project id
                queryString.append("&" + projectIdParam + id);
            } else {
                if (formData.getProjectId() > 0) {
                    queryString.append("&" + projectIdParam + formData.getProjectId());
                }
                // append billing account id
                queryString.append("&" + billingIdParam + id);
            }

        } else {
            // for status and contest type aggregation, append project id and billing id first
            if (formData.getProjectId() > 0) {
                queryString.append("&" + projectIdParam + formData.getProjectId());
            }
            if (formData.getBillingAccountId() > 0) {
                queryString.append("&" + billingIdParam + formData.getBillingAccountId());
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
