/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard.pipeline;

import java.io.Serializable;

/**
 * <p>A <code>DTO</code> for scheduled/launched contest statistics for pipeline report.</p>
 *
 * @author isv
 * @version 1.0 (Direct Pipeline Integration Assembly)
 */
public class PipelineLaunchedContestsDTO implements Serializable {

    /**
     * <p>A <code>String</code> providing the source of the statistics.</p>
     */
    private String source;

    /**
     * <p>A <code>int</code> providing the number of scheduled contests.</p>
     */
    private int scheduledContestsCount;

    /**
     * <p>A <code>int</code> providing the number of launched contests.</p>
     */
    private int launchedContestsCount;

    /**
     * <p>Constructs new <code>PipelineLaunchedContestsDTO</code> instance. This implementation does nothing.</p>
     */
    public PipelineLaunchedContestsDTO() {
    }

    /**
     * <p>Gets the number of launched contests.</p>
     *
     * @return a <code>int</code> providing the number of launched contests.
     */
    public int getLaunchedContestsCount() {
        return this.launchedContestsCount;
    }

    /**
     * <p>Sets the number of launched contests.</p>
     *
     * @param launchedContestsCount a <code>int</code> providing the number of launched contests.
     */
    public void setLaunchedContestsCount(int launchedContestsCount) {
        this.launchedContestsCount = launchedContestsCount;
    }

    /**
     * <p>Gets the number of scheduled contests.</p>
     *
     * @return a <code>int</code> providing the number of scheduled contests.
     */
    public int getScheduledContestsCount() {
        return this.scheduledContestsCount;
    }

    /**
     * <p>Sets the number of scheduled contests.</p>
     *
     * @param scheduledContestsCount a <code>int</code> providing the number of scheduled contests.
     */
    public void setScheduledContestsCount(int scheduledContestsCount) {
        this.scheduledContestsCount = scheduledContestsCount;
    }

    /**
     * <p>Gets the source of the statistics.</p>
     *
     * @return a <code>String</code> providing the source of the statistics.
     */
    public String getSource() {
        return this.source;
    }

    /**
     * <p>Sets the source of the statistics.</p>
     *
     * @param source a <code>String</code> providing the source of the statistics.
     */
    public void setSource(String source) {
        this.source = source;
    }
}
