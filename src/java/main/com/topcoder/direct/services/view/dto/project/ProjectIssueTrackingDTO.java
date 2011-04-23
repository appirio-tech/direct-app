/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.project;

import com.topcoder.direct.services.view.dto.CommonDTO;
import com.topcoder.direct.services.view.dto.contest.ContestBriefDTO;
import com.topcoder.direct.services.view.dto.contest.ContestIssuesTrackingDTO;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>A DTO represents the issue tracking of direct project</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProjectIssueTrackingDTO extends CommonDTO implements Serializable {

    /**
     * <p>A <code>long</code> providing the project ID.</p>
     */
    private long id;

    /**
     * <p>A <code>String</code> providing the project name.</p>
     */
    private String name;

    /**
     * Issues of the direct project.
     */
    private Map<ContestBriefDTO, ContestIssuesTrackingDTO> projectIssues;

    /**
     * Gets the issues of the direct project.
     *
     * @return the issues of the direct project.
     */
    public Map<ContestBriefDTO, ContestIssuesTrackingDTO> getProjectIssues() {
        return projectIssues;
    }

    /**
     * Sets the issues of the direct project.
     *
     * @param projectIssues the issues of the direct project.
     */
    public void setProjectIssues(Map<ContestBriefDTO, ContestIssuesTrackingDTO> projectIssues) {
        this.projectIssues = projectIssues;
    }

    /**
     * Gets the number of unresolved issues.
     *
     * @return the number of unresolved issues.
     */
    public int getUnresolvedIssuesNumber() {
        int count = 0;

        for(ContestIssuesTrackingDTO contestIssues : projectIssues.values()) {
            count  += contestIssues.getUnresolvedIssuesNumber();
        }

        return count;
    }

    /**
     * Gets the number of unresolved bug races.
     *
     * @return the number of unresolved bug races.
     */
    public int getUnresolvedBugRacesNumber() {
        int count = 0;

        for (ContestIssuesTrackingDTO contestIssues : projectIssues.values()) {
            count += contestIssues.getUnresolvedBugRacesNumber();
        }

        return count;
    }

    /**
     * Gets the number of issues.
     *
     * @return the number of issues.
     */
    public int getIssuesNumber() {
        int count = 0;

        for (ContestIssuesTrackingDTO contestIssues : projectIssues.values()) {
            count += contestIssues.getIssuesNumber();
        }

        return count;
    }

    /**
     * Gets the number of bug races.
     *
     * @return the number of bug races.
     */
    public int getBugRacesNumber() {
        int count = 0;

        for (ContestIssuesTrackingDTO contestIssues : projectIssues.values()) {
            count += contestIssues.getBugRacesNumber();
        }

        return count;
    }

     /**
     * Gets the number of resolved issues of the contest.
     *
     * @return the number of the resolved issues of the contest.
     */
    public int getResolvedIssuesNumber() {
        return getIssuesNumber() - getUnresolvedIssuesNumber();
    }

    /**
     * Gets the number of resolved bug races of the contest.
     *
     * @return the number of resolved bug races of the contest.
     */
    public int getResolvedBugRacesNumber() {
        return getBugRacesNumber() - getUnresolvedBugRacesNumber();
    }

     /**
     * <p>Gets the project ID.</p>
     *
     * @return a <code>long</code> providing the project ID.
     */
    public long getId() {
        return id;
    }

    /**
     * <p>Sets the project ID.</p>
     *
     * @param id a <code>long</code> providing the project ID.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * <p>Gets the project name.</p>
     *
     * @return a <code>String</code> providing the project name.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Sets the project name.</p>
     *
     * @param name a <code>String</code> providing the project name.
     */
    public void setName(String name) {
        this.name = name;
    }
}