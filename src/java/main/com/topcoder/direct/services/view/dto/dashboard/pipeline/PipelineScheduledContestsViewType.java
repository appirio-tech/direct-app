/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard.pipeline;

/**
 * <p>An enumeration over the possible types of view of pipeline report for scheduled contests.</p>
 *
 * <p>
 * Version 1.0.1 (Direct Pipeline Stats Update Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Updated to conform to latest requirements for possible view types.</li>
 *   </ol>
 * </p>
 *
 * @author isv
 * @version 1.0.1 (Direct Pipeline Integration Assembly)
 */
public enum PipelineScheduledContestsViewType {

    /**
     * <p>A <code>PipelineScheduledContestsViewType</code> corresponding to <code>Client</code> view type.</p>
     */
    Client,

    /**
     * <p>A <code>PipelineScheduledContestsViewType</code> corresponding to <code>Manager</code> view type.</p>
     */
    Manager,

    /**
     * <p>A <code>PipelineScheduledContestsViewType</code> corresponding to <code>Copilot</code> view type.</p>
     */
    Copilot,

    /**
     * <p>A <code>PipelineScheduledContestsViewType</code> corresponding to <code>Project</code> view type.</p>
     */
    Project,

    /**
     * <p>A <code>PipelineScheduledContestsViewType</code> corresponding to <code>Category</code> view type.</p>
     */
    ContestType,

    /**
     * <p>A <code>PipelineScheduledContestsViewType</code> corresponding to <code>Billing</code> view type.</p>
     */
    Billing
}
