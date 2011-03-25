/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard.costreport;

import java.io.Serializable;

/**
 * <p>
 * The DTO stores the aggregation cost report data.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version  1.0 (TopCoder Cockpit - Cost Report Assembly)
 */
public class CostAggregationDTO implements Serializable {

    /**
     * The total contest fees.
     */
    private double totalContestFees;

    /**
     * The total actual member costs.
     */
    private double totalActualMemberCosts;

    /**
     * The total estimated member costs.
     */
    private double totalEstimatedMemberCosts;

    /**
     * The total costs.
     */
    private double totalCosts;

    /**
     * The drill in query for this aggregation
     */
    private String drillInQuery;

    /**
     * aggregation name, if aggregation is project, then it is project name, if billing then it is billing project name
     */
    private String name;

    /**
     * The type of the aggregation cost report.
     */
    CostAggregationType aggregationType;

    /**
     * Gets the total contest fees
     *
     * @return the total contest fees.
     */
    public double getTotalContestFees() {
        return totalContestFees;
    }

    /**
     * Sets the total contest fees.
     *
     * @param totalContestFees the total contest fees to set.
     */
    public void setTotalContestFees(double totalContestFees) {
        this.totalContestFees = totalContestFees;
    }

    /**
     * Gets the total actual member costs.
     *
     * @return the total actual member costs.
     */
    public double getTotalActualMemberCosts() {
        return totalActualMemberCosts;
    }

    /**
     * Sets the total actual member costs.
     *
     * @param totalActualMemberCosts the total actual member costs to set.
     */
    public void setTotalActualMemberCosts(double totalActualMemberCosts) {
        this.totalActualMemberCosts = totalActualMemberCosts;
    }

    /**
     * Gets the total estimated member costs.
     *
     * @return the total estimated member costs.
     */
    public double getTotalEstimatedMemberCosts() {
        return totalEstimatedMemberCosts;
    }

    /**
     * Sets the total estimated member costs.
     *
     * @param totalEstimatedMemberCosts the total estimated member cost to set.
     */
    public void setTotalEstimatedMemberCosts(double totalEstimatedMemberCosts) {
        this.totalEstimatedMemberCosts = totalEstimatedMemberCosts;
    }

    /**
     * Gets the total cost.
     *
     * @return the total cost.
     */
    public double getTotalCosts() {
        return totalCosts;
    }

    /**
     * Sets the total costs.
     *
     * @param totalCosts the total costs to set.
     */
    public void setTotalCosts(double totalCosts) {
        this.totalCosts = totalCosts;
    }

    /**
     * Gets the cost aggregation type.
     *
     * @return the cost aggregation type.
     */
    public CostAggregationType getAggregationType() {
        return aggregationType;
    }

    /**
     * Sets the cost aggregation type.
     *
     * @param aggregationType the cost aggregation type to set.
     */
    public void setAggregationType(CostAggregationType aggregationType) {
        this.aggregationType = aggregationType;
    }

    /**
     * Gets the drill in query.
     *
     * @return the drill in query.
     */
    public String getDrillInQuery() {
        return drillInQuery;
    }

    /**
     * Sets the drill in query.
     *
     * @param drillInQuery the drill in query to set.
     */
    public void setDrillInQuery(String drillInQuery) {
        this.drillInQuery = drillInQuery;
    }

    /**
     * Gets the name.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
}
