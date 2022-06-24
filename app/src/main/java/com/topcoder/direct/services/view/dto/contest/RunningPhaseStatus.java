/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

/**
 * <p>An enumeration over the statuses of the single running phase of the project.</p>
 *
 * @author isv
 * @version 1.0 (Direct Contest Dashboard assembly)
 */
public enum RunningPhaseStatus {

    /**
     * <p>A <code>RunningPhaseStatus</code> item corresponding to case when the phase is running and there are more than
     * 2 hours left before phase's deadline.</p>
     */
    RUNNING,

    /**
     * <p>A <code>RunningPhaseStatus</code> item corresponding to case when the phase is running and there are less than
     * 2 hours left before phase's deadline.</p>
     */
    CLOSING,

    /**
     * <p>A <code>RunningPhaseStatus</code> item corresponding to case when the phase is running and it's deadline has
     * already passed.</p>
     */
    LATE

}
