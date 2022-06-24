/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.enterpriseDashboard;

import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.enterpriseDashboard.EnterpriseDashboardProjectsWidgetDTO;
import com.topcoder.direct.services.view.form.enterpriseDashboard.EnterpriseDashboardFilterForm;
import com.topcoder.direct.services.view.util.DataProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * The action handles the ajax requests for projects part of the enterprise dashboard.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0
 */
public class DashboardProjectsAction extends BaseDirectStrutsAction implements FormAction<EnterpriseDashboardFilterForm> {
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
     * Execution logic, do nothing here.
     *
     * @throws Exception if there is any error.
     */
    @Override
    protected void executeAction() throws Exception {
        // do nothing
    }

    /**
     * Gets the projects widgets data by ajax.
     *
     * @return the result code.
     */
    public String getProjectsWidget() {
        try {
            List<Map<String, String>> result = new ArrayList<Map<String, String>>();

            List<EnterpriseDashboardProjectsWidgetDTO> projects = DataProvider.getEnterpriseDashboardProjectsWidgetData(formData);

            for(EnterpriseDashboardProjectsWidgetDTO item : projects) {
                Map<String, String> data = new HashMap<String, String>();
                data.put("id", String.valueOf(item.getDirectProjectId()));
                data.put("name", item.getDirectProjectName());
                data.put("activeContestsCount", String.valueOf(item.getActiveContestsNumber()));
                if (item.getMilestoneDueDate() != null) {
                    data.put("milestoneName", item.getMilestoneName());
                }

                result.add(data);
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
}
