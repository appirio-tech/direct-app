/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.form;

import com.topcoder.direct.services.view.dto.dashboard.pipeline.PipelineScheduledContestsViewType;

import java.io.Serializable;

/**
 * <p>A form bean providing the data submitted by user for filtering the pipeline report records.</p>
 *
 * @author isv
 * @version 1.0 (Direct Pipeline Stats Update assembly)
 */
public class DashboardPipelineDraftsRatioForm implements Serializable {

    /**
     * <p>A <code>PipelineScheduledContestsViewType</code> providing the type of the view to get the drafts ratio
     * for.</p>
     */
    private PipelineScheduledContestsViewType viewType;

    /**
     * <p>Constructs new <code>DashboardPipelineDraftsRatioForm</code> instance. This implementation does nothing.</p>
     */
    public DashboardPipelineDraftsRatioForm() {
    }

    /**
     * <p>Gets the type of the view to get the drafts ratio for.</p>
     *
     * @return a <code>PipelineScheduledContestsViewType</code> providing the type of the view to get the drafts ratio
     *         for.
     */
    public PipelineScheduledContestsViewType getViewType() {
        return this.viewType;
    }

    /**
     * <p>Sets the type of the view to get the drafts ratio for.</p>
     *
     * @param viewType a <code>PipelineScheduledContestsViewType</code> providing the type of the view to get the drafts
     *                 ratio for.
     */
    public void setViewType(PipelineScheduledContestsViewType viewType) {
        this.viewType = viewType;
    }
}
