/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.configs;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Contest cost class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 * @since Direct Launch Software Assembly
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contestCost")
public class ContestCost {
    /**
     * <p>
     * A list of contest cost billing levels
     * </p>
     */
    @XmlElement(name = "billingLevel")
    private List<ContestCostBillingLevel> contestCostBillingLevels;

    /**
     * <p>
     * Gets the cost billing levels
     * </p>
     *
     * @return the contest cost billing levels
     */
    public List<ContestCostBillingLevel> getContestCostBillingLevels() {
        if (contestCostBillingLevels == null) {
            contestCostBillingLevels = new ArrayList<ContestCostBillingLevel>();
        }
        return contestCostBillingLevels;
    }

    /**
     * <p>
     * Sets the cost billing levels
     * </p>
     *
     * @param contestCostBillingLevels the contest cost billing levels to set
     */
    public void setContestCostBillingLevels(List<ContestCostBillingLevel> contestCostBillingLevels) {
        this.contestCostBillingLevels = contestCostBillingLevels;
    }
}
