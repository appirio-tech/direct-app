/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.report;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.dashboard.pipeline.PipelineDraftsRatioDTO;
import com.topcoder.direct.services.view.dto.dashboard.pipeline.PipelineScheduledContestsViewType;
import com.topcoder.direct.services.view.form.DashboardPipelineDraftsRatioForm;
import com.topcoder.direct.services.view.util.DataProvider;

import java.util.List;

/**
 * <p>A <code>Struts</code> action to be used for handling the AJAX requests for getting the drafts to launched contests
 * ratio for specified type of <code>Dashboard Pipeline</code> report view.</p>
 *
 * @author isv
 * @version 1.0 (Direct Pipeline Stats Update assembly)
 */
public class DashboardPipelineDraftsRatioAction extends BaseDirectStrutsAction {

    /**
     * <p>A <code>DashboardPipelineDraftsRatioForm</code> providing the form parameters submitted by user.</p>
     */
    private DashboardPipelineDraftsRatioForm formData;

    /**
     * <p>Constructs new <code>DashboardPipelineDraftsRatioAction</code> instance. This implementation does nothing.</p>
     */
    public DashboardPipelineDraftsRatioAction() {
        this.formData = new DashboardPipelineDraftsRatioForm();
    }

    /**
     * <p>Gets the form data.</p>
     *
     * @return an <code>DashboardPipelineDraftsRatioForm</code> providing the data for form submitted by user..
     */
    public DashboardPipelineDraftsRatioForm getFormData() {
        return this.formData;
    }

    /**
     * <p> This is the template method where the action logic will be performed by children classes. </p>
     *
     * @throws Exception if any error occurs
     */
    @Override
    protected void executeAction() throws Exception {
        PipelineScheduledContestsViewType viewType = getFormData().getViewType();
        List<PipelineDraftsRatioDTO> data = DataProvider.getPipelineDraftsRatioStats(viewType,
                                                                                     getCurrentUser().getUserId());
        setResult(data);
    }
}
