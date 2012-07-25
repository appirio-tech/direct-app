/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.enterpriseDashboard;

import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.enterpriseDashboard.EnterpriseDashboardProjectFinancialDTO;
import com.topcoder.direct.services.view.dto.enterpriseDashboard.EnterpriseDashboardTotalSpendDTO;
import com.topcoder.direct.services.view.form.enterpriseDashboard.EnterpriseDashboardFilterForm;
import com.topcoder.direct.services.view.util.DataProvider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * The action handles the ajax requests for financial part of the enterprise dashboard.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0
 */
public class DashboardFinancialAction extends BaseDirectStrutsAction implements FormAction<EnterpriseDashboardFilterForm> {

    /**
     * The date format to display the X axis of total spend chart.
     */
    private static final DateFormat AXIS_DATE_FORMAT = new SimpleDateFormat("MMM''yy");

    /**
     * The form data of the action.
     */
    private EnterpriseDashboardFilterForm formData = new EnterpriseDashboardFilterForm();

    /**
     * Gets the form data of the action.
     *
     * @return the form data of the action.
     */
    public EnterpriseDashboardFilterForm getFormData() {
        return this.formData;
    }

    /**
     * Sets the form data of the action.
     *
     * @param formData the form data of the action.
     */
    public void setFormData(EnterpriseDashboardFilterForm formData) {
        this.formData = formData;
    }

    /**
     * Empty, ajax requests are handled via action methods invocation.
     *
     * @throws Exception if there is any error.
     */
    @Override
    protected void executeAction() throws Exception {
        // do nothing
    }

    /**
     * Handles the ajax request to get the data for the total spend chart.
     *
     * @return the result code.
     */
    public String getTotalSpend() {
        try {
            List<Map<String, String>> result = new ArrayList<Map<String, String>>();

            final List<EnterpriseDashboardTotalSpendDTO> enterpriseDashboardTotalSpend =
                    DataProvider.getEnterpriseDashboardTotalSpend(getFormData());

            for(EnterpriseDashboardTotalSpendDTO item : enterpriseDashboardTotalSpend) {
                Map<String, String> m = new HashMap<String, String>();
                m.put("label", AXIS_DATE_FORMAT.format(item.getDate()));
                m.put("spend", String.valueOf(item.getTotalSpend()));
                m.put("average", String.valueOf(item.getAverageSpend()));
                result.add(m);
            }

            setResult(result);
        } catch (Throwable e) {

            e.printStackTrace(System.err);

            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }

    /**
     * Handles the ajax request to get the data for the financial section.
     *
     * @return the result code.
     */
    public String getFinancialStatistics() {
        try {
            Map<String, List<Map<String, String>>> result = new HashMap<String, List<Map<String,String>>>();
            List<Map<String, String>> financialList = new ArrayList<Map<String, String>>();

            final List<EnterpriseDashboardProjectFinancialDTO> projects = DataProvider.getEnterpriseDashboardProjectsFinancialInfo(getFormData());

            for(EnterpriseDashboardProjectFinancialDTO item : projects) {
                Map<String, String> itemResult = new HashMap<String, String>();
                itemResult.put("projectId", String.valueOf(item.getProjectId()));
                itemResult.put("projectName", item.getProjectName());
                itemResult.put("totalBudget", String.valueOf(item.getBudget()));
                itemResult.put("actualCost", String.valueOf(item.getActualCost()));
                itemResult.put("plannedCost", String.valueOf(item.getPlannedCost()));
                itemResult.put("projectedCost", String.valueOf(item.getProjectedCost()));
                financialList.add(itemResult);
            }

            result.put("financials", financialList);

            setResult(result);
        } catch (Throwable e) {

            e.printStackTrace(System.err);

            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }

}
