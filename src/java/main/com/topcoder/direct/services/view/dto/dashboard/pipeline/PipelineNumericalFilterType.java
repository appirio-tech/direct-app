/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard.pipeline;

/**
 * <p>An enumeration over the possible types of criteria to search from the dashboard.</p>
 *
 * @author isv
 * @version 1.0 (Direct Pipeline Integration Assembly)
 */
public enum PipelineNumericalFilterType {

    /**
     * <p>An <code>PipelineNumericalFilterType</code> corresponding to search for contest prize.</p>
     */
    PRIZE,

    /**
     * <p>An <code>PipelineNumericalFilterType</code> corresponding to search for contest second place prize.</p>
     */
    SECOND_PLACE_PRIZE,

    /**
     * <p>An <code>PipelineNumericalFilterType</code> corresponding to search for contest DR points.</p>
     */
    DR_POINTS,

    /**
     * <p>An <code>PipelineNumericalFilterType</code> corresponding to search for contest fee.</p>
     */
    CONTEST_FEE,

    /**
     * <p>An <code>PipelineNumericalFilterType</code> corresponding to search for contest review cost.</p>
     */
    REVIEW_COST,

    /**
     * <p>An <code>PipelineNumericalFilterType</code> corresponding to search for contest specification review cost.</p>
     */
    SPEC_REVIEW_COST,

    /**
     * <p>An <code>PipelineNumericalFilterType</code> corresponding to search for contest duration.</p>
     */
    DURATION
}
