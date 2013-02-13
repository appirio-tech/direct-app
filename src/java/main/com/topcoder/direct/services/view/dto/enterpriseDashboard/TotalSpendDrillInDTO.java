/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.enterpriseDashboard;

/**
 * The DTO for the enterprise dashboard total spend chart drill-in.
 *
 * @author GreatKevin
 * @version 1.0 (Release Assembly - Cockpit Enterprise Dashboard Chart Drill-In)
 */
public class TotalSpendDrillInDTO extends EnterpriseDashboardTotalSpendDTO {
    /**
     * The direct project id.
     */
    private long directProjectId;

    /**
     * The direct project name.
     */
    private String directProjectName;

    /**
     * Gets the direct project id.
     *
     * @return the direct project id.
     */
    public long getDirectProjectId() {
        return directProjectId;
    }

    /**
     * Sets the direct project id.
     *
     * @param directProjectId the direct project id.
     */
    public void setDirectProjectId(long directProjectId) {
        this.directProjectId = directProjectId;
    }

    /**
     * Gets the direct project name.
     *
     * @return the direct project name.
     */
    public String getDirectProjectName() {
        return directProjectName;
    }

    /**
     * Sets the direct project name.
     *
     * @param directProjectName the direct project name.
     */
    public void setDirectProjectName(String directProjectName) {
        this.directProjectName = directProjectName;
    }
}
