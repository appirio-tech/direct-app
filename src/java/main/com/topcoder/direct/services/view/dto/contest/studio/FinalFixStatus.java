/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest.studio;

/**
 * <p>An enumeration over the possible statuses of the <code>Final Fix</code> submission for <code>Studio</code>
 * contests.</p>
 * 
 * @author isv
 * @version 1.0 (Module Assembly - TC Cockpit - Studio - Final Fixes Integration Part One)
 */
public enum FinalFixStatus {

    /**
     * <p>A <code>FinalFixStatus</code> corresponding to case when Final Fix hasn't started yet.</p>
     */
    NOT_STARTED,

    /**
     * <p>A <code>FinalFixStatus</code> corresponding to case when Final Fix is in progress.</p>
     */
    IN_PROGRESS,

    /**
     * <p>A <code>FinalFixStatus</code> corresponding to case when Final Fix is being reviewed.</p>
     */
    REVIEW,

    /**
     * <p>A <code>FinalFixStatus</code> corresponding to case when Final Fix has been completed.</p>
     */
    COMPLETED
}
