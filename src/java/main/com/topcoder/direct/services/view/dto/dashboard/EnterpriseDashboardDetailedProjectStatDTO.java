/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>A DTO class providing detailed statistical data for a single project to be displayed in <code>Enterprise Dashboard
 * </code> page view.</p>
 *
 * @author isv
 * @version 1.0 (Direct Enterprise Dashboard Assembly 1.0)
 */
public class EnterpriseDashboardDetailedProjectStatDTO implements Serializable {

    /**
     * <p>A <code>Date</code> providing the date which the statistics apply to.</p>
     */
    private Date date;

    /**
     * <p>A <code>double</code> providing the statistical value for projects.</p>
     */
    private double value;

    /**
     * <p>A <code>int</code> providing the number of contests which the stats were calculated on.</p>
     */
    private int contestsCount;

    /**
     * <p>A <code>EnterpriseDashboardStatType</code> providing the type of the statistics.</p>
     */
    private EnterpriseDashboardStatType statsType;

    /**
     * <p>Constructs new <code>EnterpriseDashboardDetailedProjectStatDTO</code> instance. This implementation does
     * nothing.</p>
     */
    public EnterpriseDashboardDetailedProjectStatDTO() {
    }

    /**
     * <p>Gets the type of the statistics.</p>
     *
     * @return a <code>EnterpriseDashboardStatType</code> providing the type of the statistics.
     */
    public EnterpriseDashboardStatType getStatsType() {
        return this.statsType;
    }

    /**
     * <p>Sets the type of the statistics.</p>
     *
     * @param statsType a <code>EnterpriseDashboardStatType</code> providing the type of the statistics.
     */
    public void setStatsType(EnterpriseDashboardStatType statsType) {
        this.statsType = statsType;
    }

    /**
     * <p>Gets the statistical value for projects.</p>
     *
     * @return a <code>double</code> providing the statistical value for projects.
     */
    public double getValue() {
        return this.value;
    }

    /**
     * <p>Sets the statistical value for projects.</p>
     *
     * @param value a <code>double</code> providing the statistical value for projects.
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * <p>Gets the date which the statistics apply to.</p>
     *
     * @return a <code>Date</code> providing the date which the statistics apply to.
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * <p>Sets the date which the statistics apply to.</p>
     *
     * @param date a <code>Date</code> providing the date which the statistics apply to.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * <p>Gets the number of contests which the stats were calculated on.</p>
     *
     * @return a <code>int</code> providing the number of contests which the stats were calculated on.
     */
    public int getContestsCount() {
        return this.contestsCount;
    }

    /**
     * <p>Sets the number of contests which the stats were calculated on.</p>
     *
     * @param contestsCount a <code>int</code> providing the number of contests which the stats were calculated on.
     */
    public void setContestsCount(int contestsCount) {
        this.contestsCount = contestsCount;
    }
}
