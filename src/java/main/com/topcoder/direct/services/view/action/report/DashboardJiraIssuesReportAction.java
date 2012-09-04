/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.report;

import com.topcoder.direct.services.view.dto.dashboard.jirareport.JiraIssuePaymentStatus;
import com.topcoder.direct.services.view.dto.dashboard.jirareport.JiraIssuesReportDTO;
import com.topcoder.direct.services.view.dto.dashboard.jirareport.JiraIssuesReportEntryDTO;
import com.topcoder.direct.services.view.form.DashboardReportForm;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  This action handles the request for dashboard jira issues report.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - JIRA issues loading update and report creation)
 */
public class DashboardJiraIssuesReportAction extends DashboardReportBaseAction<DashboardReportForm, JiraIssuesReportDTO> {

    /**
     * The map to store the payment status of TopCoder Jira issue. There are two payment status now: "Not Paid" and "Paid"
     */
    private static final Map<Long, String> JIRA_ISSUE_PAYMENT_STATUS;

    /**
     * Static initializer. It adds the two jira payment status into the static final map.
     */
    static {
        JIRA_ISSUE_PAYMENT_STATUS = new LinkedHashMap<Long, String>();
        JIRA_ISSUE_PAYMENT_STATUS.put(JiraIssuePaymentStatus.NOT_PAID.getStatusId(), JiraIssuePaymentStatus.NOT_PAID.getStatusName());
        JIRA_ISSUE_PAYMENT_STATUS.put(JiraIssuePaymentStatus.PAID.getStatusId(), JiraIssuePaymentStatus.PAID.getStatusName());
    }

    /**
     * Action constructor.
     */
    public DashboardJiraIssuesReportAction() {
        super();

        setViewData(new JiraIssuesReportDTO());
        setFormData(new DashboardReportForm());
    }

    /**
     * Action execution logic.
     *
     * @return the result code
     * @throws Exception if there is any error.
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
     * Core execution logic. Read the form data and generate the report response.
     *
     * @throws Exception if there is any error.
     */
    @Override
    protected void executeAction() throws Exception {
        super.executeAction();

        boolean isFirstCall = getViewData().isShowJustForm();
        long[] jiraIssueStatusIds = getFormData().getProjectStatusIds();

        // if status IDs are not specified then use all status ids
        boolean jiraStatusIdsAreSet = (jiraIssueStatusIds != null) && (jiraIssueStatusIds.length > 0);
        if (isFirstCall && !jiraStatusIdsAreSet) {
            jiraIssueStatusIds = new long[JIRA_ISSUE_PAYMENT_STATUS.size()];
            int count = 0;
            for (Long l : JIRA_ISSUE_PAYMENT_STATUS.keySet()) {
                jiraIssueStatusIds[count++] = l;
            }
            getFormData().setProjectStatusIds(jiraIssueStatusIds);
        }
        // set all the report status to view data to populate jira payment status (2 total: Not Paid, Paid)
        getViewData().setProjectStatus(JIRA_ISSUE_PAYMENT_STATUS);

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
            // Query for report data
            final List<JiraIssuesReportEntryDTO> entries = DataProvider.getDashboardJiraIssuesReport(
                    getCurrentUser(), projectId, customerId, billingAccountId, jiraIssueStatusIds, startDate, endDate);

            // Set view data with report data
            getViewData().setEntries(entries);
        }
    }

}
