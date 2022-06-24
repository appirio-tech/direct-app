/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard.pipeline;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>A <code>DTO</code> for pipeline report summary.</p>
 *
 * @author isv
 * @version 1.0 (Direct Pipeline Integration Assembly)
 */
public class PipelineSummaryDTO implements Serializable {

    /**
     * <p>A <code>boolean</code> providing the flag indicating whether this sumary is a total summary for report or
     * not.</p>
     */
    private boolean isTotal;

    /**
     * <p>A <code>Date</code> providing the first day of the week which this summary corresponds to.</p>
     */
    private Date weekOf;

    /**
     * <p>A <code>double</code> providing the member costs.</p>
     */
    private double memberCosts;

    /**
     * <p>A <code>double</code> providing the contests fee.</p>
     */
    private double contestsFee;

    /**
     * <p>A <code>int</code> providing the number of all contests.</p>
     */
    private int allContestsCount;

    /**
     * <p>A <code>int</code> providing the number of launched contests.</p>
     */
    private int launchedContestsCount;

    /**
     * <p>Constructs new <code>PipelineSummaryDTO</code> instance. This implementation does nothing.</p>
     */
    public PipelineSummaryDTO() {
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
     * <p>Gets the number of all contests.</p>
     *
     * @return a <code>int</code> providing the number of all contests.
     */
    public int getAllContestsCount() {
        return this.allContestsCount;
    }

    /**
     * <p>Sets the number of all contests.</p>
     *
     * @param allContestsCount a <code>int</code> providing the number of all contests.
     */
    public void setAllContestsCount(int allContestsCount) {
        this.allContestsCount = allContestsCount;
    }

    /**
     * <p>Gets the contests fee.</p>
     *
     * @return a <code>double</code> providing the contests fee.
     */
    public double getContestsFee() {
        return this.contestsFee;
    }

    /**
     * <p>Sets the contests fee.</p>
     *
     * @param contestsFee a <code>double</code> providing the contests fee.
     */
    public void setContestsFee(double contestsFee) {
        this.contestsFee = contestsFee;
    }

    /**
     * <p>Gets the member costs.</p>
     *
     * @return a <code>double</code> providing the member costs.
     */
    public double getMemberCosts() {
        return this.memberCosts;
    }

    /**
     * <p>Sets the member costs.</p>
     *
     * @param memberCosts a <code>double</code> providing the member costs.
     */
    public void setMemberCosts(double memberCosts) {
        this.memberCosts = memberCosts;
    }


    /**
     * <p>Gets the first day of the week which this summary corresponds to.</p>
     *
     * @return a <code>Date</code> providing the first day of the week which this summary corresponds to.
     */
    public Date getWeekOf() {
        return this.weekOf;
    }

    /**
     * <p>Sets the first day of the week which this summary corresponds to.</p>
     *
     * @param weekOf a <code>Date</code> providing the first day of the week which this summary corresponds to.
     */
    public void setWeekOf(Date weekOf) {
        this.weekOf = weekOf;
    }

    /**
     * <p>Gets the flag indicating whether this sumary is a total summary for report or not.</p>
     *
     * @return a <code>boolean</code> providing the flag indicating whether this sumary is a total summary for report or
     *         not.
     */
    public boolean getIsTotal() {
        return this.isTotal;
    }

    /**
     * <p>Sets the flag indicating whether this sumary is a total summary for report or not.</p>
     *
     * @param isTotal a <code>boolean</code> providing the flag indicating whether this sumary is a total summary for
     *                report or not.
     */
    public void setIsTotal(boolean isTotal) {
        this.isTotal = isTotal;
    }
}
