/*
 * Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.direct.services.view.dto.IdNamePair;
import com.topcoder.direct.services.view.dto.dashboard.projectreport.ProjectMetricsReportDTO;
import com.topcoder.direct.services.view.dto.dashboard.projectreport.ProjectMetricsReportEntryDTO;
import com.topcoder.direct.services.view.form.DashboardReportForm;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;

/**
 * <p>A <code>Struts</code> action to be used for handling the requests for viewing the
 * Project Metrics Report.</p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0 (TC Cockpit Project Metrics Report)
 */
public class DashboardProjectMetricsReportAction extends DashboardReportBaseAction<DashboardReportForm, ProjectMetricsReportDTO> {
    
    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 3386028267770976602L;
    
    /**
     * <p>The project status used by report. There are 4 status used for report. It's initialized in the
     * static constructor of this action class.</p>
     */
    protected static final Map<Long, String> REPORT_PROJECT_STATUS;

    /**
     * <p>The project status used by report. There are 4 status used for report. It's initialized in the
     * static constructor of this action class.</p>
     */
    protected static final Map<String, Long> REPORT_PROJECT_STATUS_IDS;
    
    /**
     * <p>Status constructor of this action.</p>
     */
    static {
        // initialize the contest status
        REPORT_PROJECT_STATUS = new LinkedHashMap<Long, String>();
        REPORT_PROJECT_STATUS_IDS = new LinkedHashMap<String, Long>();

        IdNamePair active = new IdNamePair();
        IdNamePair inactive = new IdNamePair();
        IdNamePair completed = new IdNamePair();
        IdNamePair draft = new IdNamePair();
        
        active.setId(1);
        active.setName("Active");
        inactive.setId(2);
        inactive.setName("Archived");
        completed.setId(4);
        completed.setName("Completed");
        draft.setId(5);
        draft.setName("Draft");
        

        REPORT_PROJECT_STATUS.put(active.getId(), active.getName());
        REPORT_PROJECT_STATUS.put(inactive.getId(), inactive.getName());
        REPORT_PROJECT_STATUS.put(completed.getId(), completed.getName());
        REPORT_PROJECT_STATUS.put(draft.getId(), draft.getName()); 
       
        REPORT_PROJECT_STATUS_IDS.put(active.getName().toLowerCase(), active.getId());
        REPORT_PROJECT_STATUS_IDS.put(inactive.getName().toLowerCase(), inactive.getId());
        REPORT_PROJECT_STATUS_IDS.put(completed.getName().toLowerCase(), completed.getId());
        REPORT_PROJECT_STATUS_IDS.put(draft.getName().toLowerCase(), draft.getId());
    }
    
    /**
     * <p>Constructs new <code>DashboardProjectMetricsReportAction</code> instance.</p>
     */
    public DashboardProjectMetricsReportAction() {
        super();
        
        setViewData(new ProjectMetricsReportDTO());
        setFormData(new DashboardReportForm());
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
     * <p>Handles the incoming request. Retrieves data for Project Metrics Report
     * and binds it to request.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        super.executeAction();
        
        boolean isFirstCall = getViewData().isShowJustForm();
        long[] projectStatusIds = getFormData().getProjectStatusIds();
        
        // if status IDs are not specified then use all status ids
        boolean projectStatusIdsAreSet = (projectStatusIds != null) && (projectStatusIds.length > 0);
        if (isFirstCall && !projectStatusIdsAreSet) {
        	projectStatusIds = new long[REPORT_PROJECT_STATUS.size()];
            int count = 0;
            for (Long l : REPORT_PROJECT_STATUS.keySet()) {
            	projectStatusIds[count] = l;
                count++;
            }
            getFormData().setProjectStatusIds(projectStatusIds);
            projectStatusIdsAreSet = true;
        }
        // set all the report status to view data to populate report contest status (4 total: active, archived, completed, draft) 
        getViewData().setProjectStatus(REPORT_PROJECT_STATUS);
        
        if (hasActionErrors()) {
            return;
        }
        
        // Analyze form parameters
        DashboardReportForm form = getFormData();
        long projectId = form.getProjectId();
        long customerId = form.getCustomerId();
        long billingAccountId = form.getBillingAccountId();

        Date startDate = DirectUtils.getDate(form.getStartDate());
        Date endDate = DirectUtils.getDate(form.getEndDate());
        
        // If necessary get and process report data
        if (!getViewData().isShowJustForm()) {
            List<ProjectMetricsReportEntryDTO> statuses = new ArrayList<ProjectMetricsReportEntryDTO>();

            // Query for report data
            DataProvider.getDashboardProjectMetricsReport(
                getCurrentUser(), projectId, customerId, billingAccountId, projectStatusIds, startDate, endDate,
                statuses);

            // Set view data with report data
            getViewData().setEntries(statuses);
        }
    }
}
