/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard.participationreport;

import java.io.Serializable;

/**
 * <p>
 * The DTO stores the basic participation metrics report data.
 * </p>
 *
 * @author TCSASSEMBER
 * @version  1.0 (TC Cockpit Participation Metrics Report Part One Assembly 1)
 */
public class ParticipationBasicReportDTO implements Serializable {

    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = -4530498498781800437L;

    /**
     * Represents the number of total projects.
     */
    private int totalProjects;
    
    /**
     * Represents the number of total contests.
     */
    private int totalContests;
    
    /**
     * Represents the nubmer of total copilots.
     */
    private int totalCopilots;

    /**
     * Empty constructor.
     */
    public ParticipationBasicReportDTO() {
        
    }

    /**
     * Gets the number of total projects.
     * 
     * @return the number of total projects.
     */
    public int getTotalProjects() {
        return totalProjects;
    }

    /**
     * Sets the number of total projects.
     * 
     * @param totalProjects the number of total projects.
     */
    public void setTotalProjects(int totalProjects) {
        this.totalProjects = totalProjects;
    }

    /**
     * Gets the number of total contests.
     * 
     * @return the number of total contests.
     */
    public int getTotalContests() {
        return totalContests;
    }

    /**
     * Sets the number of total contests.
     * 
     * @param totalContests the number of total contests.
     */
    public void setTotalContests(int totalContests) {
        this.totalContests = totalContests;
    }

    /**
     * Gets the nubmer of total copilots.
     * 
     * @return the nubmer of total copilots.
     */
    public int getTotalCopilots() {
        return totalCopilots;
    }

    /**
     * Sets the nubmer of total copilots.
     * 
     * @param totalCopilots the nubmer of total copilots.
     */
    public void setTotalCopilots(int totalCopilots) {
        this.totalCopilots = totalCopilots;
    }
}
