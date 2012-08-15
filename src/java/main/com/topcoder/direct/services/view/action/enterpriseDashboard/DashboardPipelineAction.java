/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.enterpriseDashboard;

import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.enterpriseDashboard.EnterpriseDashboardMonthPipelineDTO;
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
 * The action handles the ajax requests for pipeline part of the enterprise dashboard.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0
 * @since Module Assembly - TC Cockpit Enterprise Dashboard Pipeline Part
 */
public class DashboardPipelineAction extends BaseDirectStrutsAction implements FormAction<EnterpriseDashboardFilterForm> {
    /**
     * The date format to display the X axis of pipeline chart.
     */
    private static final DateFormat AXIS_DATE_FORMAT = new SimpleDateFormat("yyyy MMMMM");

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
     * Handles the ajax request to get the data for the contests pipeline chart.
     *
     * @return the result code.
     */
    public String getContestsPipeline() {
        try {
            List<Map<String, String>> result = new ArrayList<Map<String, String>>();

            // get the date from data provider via query
            final List<EnterpriseDashboardMonthPipelineDTO> enterpriseDashboardPipelines =
                    DataProvider.getEnterpriseDashboardContestsPipeline(getFormData());

            for(EnterpriseDashboardMonthPipelineDTO item : enterpriseDashboardPipelines) {
                Map<String, String> m = new HashMap<String, String>();
                m.put("date", AXIS_DATE_FORMAT.format(item.getDate()));
                m.put("activeContests", String.valueOf(item.getTotalActiveContests()));
                m.put("scheduledContests", String.valueOf(item.getTotalScheduledContests()));
                m.put("draftContests", String.valueOf(item.getTotalDraftContests()));
                m.put("completedContests", String.valueOf(item.getTotalCompletedContests()));
                m.put("failedContests", String.valueOf(item.getTotalFailedContests()));
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
}
