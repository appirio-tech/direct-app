/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard;

import java.io.Serializable;

/**
 * <p>A DTO class providing detailed statistical data for a single project to be displayed in <code>Enterprise Dashboard
 * </code> page view.</p>
 *
 * @author isv
 * @version 1.0 (Direct Enterprise Dashboard Assembly 1.0)
 */
public class EnterpriseDashboardAggregatedStatDTO implements Serializable {

    /**
     * <p>A <code>double</code> providing the statistical value for client projects.</p>
     */
    private double clientValue = 0;

    /**
     * <p>A <code>double</code> providing the statistical value for all projects from all clients.</p>
     */
    private double overallValue = 0;

    /**
     * <p>A <code>String</code> providing the text for the time period which this stat belongs to.</p>
     */
    private String timePeriodLabel;

    /**
     * <p>A <code>int</code> providing the number of client contests which the stats were calculated on.</p>
     */
    private int clientContestsCount;

    /**
     * <p>A <code>int</code> providing the number of overall contests which the stats were calculated on.</p>
     */
    private int overallContestsCount;

    /**
     * <p>Constructs new <code>EnterpriseDashboardAggregatedStatDTO</code> instance. This implementation does nothing.
     * </p>
     */
    public EnterpriseDashboardAggregatedStatDTO() {
    }

    /**
     * <p>Gets the statistical value for all projects from all clients.</p>
     *
     * @return a <code>double</code> providing the statistical value for all projects from all clients.
     */
    public double getOverallValue() {
        return this.overallValue;
    }

    /**
     * <p>Sets the statistical value for all projects from all clients.</p>
     *
     * @param overallValue a <code>double</code> providing the statistical value for all projects from all clients.
     */
    public void setOverallValue(double overallValue) {
        this.overallValue = overallValue;
    }

    /**
     * <p>Gets the statistical value for client projects.</p>
     *
     * @return a <code>double</code> providing the statistical value for client projects.
     */
    public double getClientValue() {
        return this.clientValue;
    }

    /**
     * <p>Sets the statistical value for client projects.</p>
     *
     * @param clientValue a <code>double</code> providing the statistical value for client projects.
     */
    public void setClientValue(double clientValue) {
        this.clientValue = clientValue;
    }

    /**
     * <p>Gets the text for the time period which this stat belongs to.</p>
     *
     * @return a <code>String</code> providing the text for the time period which this stat belongs to.
     */
    public String getTimePeriodLabel() {
        return this.timePeriodLabel;
    }

    /**
     * <p>Sets the text for the time period which this stat belongs to.</p>
     *
     * @param timePeriodLabel a <code>String</code> providing the text for the time period which this stat belongs to.
     */
    public void setTimePeriodLabel(String timePeriodLabel) {
        this.timePeriodLabel = timePeriodLabel;
    }

    /**
     * <p>Aggregates specified value for client projects.</p>
     *
     * @param clientValue a <code>double</code> providing the value for client project to be aggregated.
     * @param clientContestsCount an <code>int</code> providing the number of client contests which provided client
     *        value has been calculated on.
     */
    public void aggregateClientValue(double clientValue, int clientContestsCount) {
        double currentTotalValue = getClientValue() * getClientContestsCount();
        double additionalTotalValue = clientValue * clientContestsCount;

        boolean isZero = (getClientContestsCount()  + clientContestsCount) == 0;
        double newAggregatedValue;
        if (isZero) {
            newAggregatedValue = (currentTotalValue + additionalTotalValue);
        } else {
            newAggregatedValue = (currentTotalValue + additionalTotalValue)
                                        / (getClientContestsCount()  + clientContestsCount);
        }
        setClientValue(newAggregatedValue);
        setClientContestsCount(getClientContestsCount()  + clientContestsCount);
    }

    /**
     * <p>Aggregates specified value for all projects.</p>
     *
     * @param overallValue a <code>double</code> providing the value for all projects to be aggregated.
     * @param overallContestsCount an <code>int</code> providing the number of overall contests which provided overall 
     *        value has been calculated on.
     */
    public void aggregateOverallValue(double overallValue, int overallContestsCount) {
        double currentTotalValue = getOverallValue() * getOverallContestsCount();
        double additionalTotalValue = overallValue * overallContestsCount;
        boolean isZero = (getOverallContestsCount()  + overallContestsCount) == 0;
        double newAggregatedValue;
        if (isZero) {
            newAggregatedValue = (currentTotalValue + additionalTotalValue);
        } else {
            newAggregatedValue = (currentTotalValue + additionalTotalValue)
                                        / (getOverallContestsCount()  + overallContestsCount);
        }
        setOverallValue(newAggregatedValue);
        setOverallContestsCount(getOverallContestsCount()  + overallContestsCount);
    }

    /**
     * <p>Gets the number of contests which the stats were calculated on.</p>
     *
     * @return a <code>int</code> providing the number of contests which the stats were calculated on.
     */
    public int getClientContestsCount() {
        return this.clientContestsCount;
    }

    /**
     * <p>Sets the number of contests which the stats were calculated on.</p>
     *
     * @param clientContestsCount a <code>int</code> providing the number of contests which the stats were calculated on.
     */
    public void setClientContestsCount(int clientContestsCount) {
        this.clientContestsCount = clientContestsCount;
    }

    /**
     * <p>Gets the number of overall contests which the stats were calculated on.</p>
     *
     * @return a <code>int</code> providing the number of overall contests which the stats were calculated on.
     */
    public int getOverallContestsCount() {
        return this.overallContestsCount;
    }

    /**
     * <p>Sets the number of overall contests which the stats were calculated on.</p>
     *
     * @param overallContestsCount a <code>int</code> providing the number of overall contests which the stats were
     *                             calculated on.
     */
    public void setOverallContestsCount(int overallContestsCount) {
        this.overallContestsCount = overallContestsCount;
    }
}
