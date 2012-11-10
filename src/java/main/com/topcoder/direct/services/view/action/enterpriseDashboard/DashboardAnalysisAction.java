/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.enterpriseDashboard;

import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.enterpriseDashboard.EnterpriseDashboardAnalysisDTO;
import com.topcoder.direct.services.view.form.enterpriseDashboard.AnalysisFilterForm;
import com.topcoder.direct.services.view.form.enterpriseDashboard.AnalysisGroupType;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * This action handles the first enter of new Enterprise Dashboard analysis page.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0
 */
public class DashboardAnalysisAction extends BaseDirectStrutsAction implements FormAction<AnalysisFilterForm> {

    /**
     * The default billing account drop down option - All billing accounts.
     */
    private static long ALL_BILLING_ACCOUNT_ID = 0L;

    /**
     * The default project drop down option - All projects.
     */
    private static long ALL_PROJECTS_ID = 0L;

    /**
     * The form data.
     */
    private AnalysisFilterForm formData = new AnalysisFilterForm();

    /**
     * The view data.
     */
    private EnterpriseDashboardAnalysisDTO viewData = new EnterpriseDashboardAnalysisDTO();

    /**
     * Action main logic.
     *
     * @throws Exception if there is any error.
     */
    @Override
    protected void executeAction() throws Exception {
        TCSubject currentUser = getCurrentUser();

        // 1) customers the user can access
        getViewData().setClients(DirectUtils.getAllClients(currentUser));

        long customerId = 0;

        for (long cid : getViewData().getClients().keySet()) {
            customerId = cid;
            getFormData().setCustomerId(customerId);
            break;
        }

        // 2) set all billing accounts
        Map<Long, String> billingAccounts = new LinkedHashMap<Long, String>();
        if (customerId > 0) {
            billingAccounts.put(ALL_BILLING_ACCOUNT_ID, "All Billing Accounts");
            billingAccounts.putAll(DirectUtils.getBillingsForClient(currentUser, customerId));
        }
        getViewData().setBillingAccounts(billingAccounts);
        getFormData().setBillingAccountId(ALL_BILLING_ACCOUNT_ID);

        // 3) set all projects
        Map<Long, String> directProjects = new LinkedHashMap<Long, String>();
        if (customerId > 0) {
            directProjects.put(ALL_PROJECTS_ID, "All Projects");
            directProjects.putAll(DirectUtils.getProjectsForClient(currentUser, customerId));
        }
        getViewData().setProjects(directProjects);
        getFormData().setProjectId(ALL_PROJECTS_ID);

        // 4) set all contest types
        getViewData().setContestTypes(DataProvider.getAllProjectCategories());
        getFormData().setCategoryIds(new long[getViewData().getContestTypes().size()]);
        int i = 0;
        for(Long tid : getViewData().getContestTypes().keySet()) {
            getFormData().getCategoryIds()[i++] = tid;
        }

        // 5) set group types
        getViewData().setGroupTypes(AnalysisGroupType.values());
        getFormData().setGroupType(AnalysisGroupType.MONTH);

    }

    /**
     * Gets the form data.
     *
     * @return the form data.
     */
    public AnalysisFilterForm getFormData() {
        return formData;
    }

    /**
     * Sets the form data.
     *
     * @param formData the form data.
     */
    public void setFormData(AnalysisFilterForm formData) {
        this.formData = formData;
    }

    /**
     * Gets the view data.
     *
     * @return the view data.
     */
    public EnterpriseDashboardAnalysisDTO getViewData() {
        return viewData;
    }

    /**
     * Sets the view data.
     *
     * @param viewData the view data.
     */
    public void setViewData(EnterpriseDashboardAnalysisDTO viewData) {
        this.viewData = viewData;
    }
}
