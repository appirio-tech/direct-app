/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard.pipeline;

import java.io.Serializable;

/**
 * <p>A <code>DTO</code> for drafts/launched contest statistics for pipeline report.</p>
 *
 * @author isv
 * @version 1.0 (Direct Pipeline Stats Update assembly)
 */
public class PipelineDraftsRatioDTO implements Serializable {

    /**
     * <p>A <code>String</code> providing the source of the statistics.</p>
     */
    private String source;

    /**
     * <p>A <code>int</code> providing the number of launched contests.</p>
     */
    private int launchedContestsCount;

    /**
     * <p>A <code>int</code> providing the number of draft contests.</p>
     */
    private int draftContestsCount;

    /**
     * <p>Constructs new <code>PipelineDraftsRatioDTO</code> instance. This implementation does nothing.</p>
     */
    public PipelineDraftsRatioDTO() {
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

    /**
     * Retrieves the draftContestsCount field.
     *
     * @return the draftContestsCount
     */
    public int getDraftContestsCount() {
        return draftContestsCount;
    }

    /**
     * Sets the draftContestsCount field.
     *
     * @param draftContestsCount the draftContestsCount to set
     */
    public void setDraftContestsCount(int draftContestsCount) {
        this.draftContestsCount = draftContestsCount;
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
     * <p>Gets the ratio for launched contests.</p>
     *
     * @return a <code>float</code> providing the ratio for launched contests.
     */
    public float getLaunchedRatio() {
        int sum = getDraftContestsCount() + getLaunchedContestsCount();
        if (sum == 0) {
            return 0;
        } else {
            return getLaunchedContestsCount() * 100.00F / sum;
        }
    }
}
