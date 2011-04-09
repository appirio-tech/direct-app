/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import com.topcoder.direct.services.configs.ConfigUtils;
import com.topcoder.direct.services.view.dto.CommonDTO;
import com.topcoder.direct.services.view.dto.TcJiraIssue;
import com.topcoder.direct.services.view.form.ContestIdForm;

import java.util.List;

/**
 * <p>
 * The DTO class used for storing the data and rendering the contest issue tracking page.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version  1.0 (TC Cockpit Bug Tracking R1 Contest Tracking assembly)
 */
public class ContestIssuesTrackingDTO extends CommonDTO implements ContestStatsDTO.Aware, ContestIdForm.Aware {

    /**
     * <p>A <code>long</code> providing the ID of contest.</p>
     */
    private long contestId;

    /**
     * <p>A <code>ContestStatsDTO </code> providing the statistics on contest.</p>
     */
    private ContestStatsDTO contestStats;

    /**
     * The jira issues of the contest.
     */
    private List<TcJiraIssue> issues;

    /**
     * The bug races of the contest.
     */
    private List<TcJiraIssue> bugRaces;

    /**
     * Gets the contest id.
     *
     * @return the contest id.
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * Sets the contest id.
     *
     * @param contestId the contest id to set.
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * Gets the contest stats.
     *
     * @return the contest statistics.
     */
    public ContestStatsDTO getContestStats() {
        return contestStats;
    }

    /**
     * Sets the contest stats.
     *
     * @param contestStats the contest stats to set.
     */
    public void setContestStats(ContestStatsDTO contestStats) {
        this.contestStats = contestStats;
    }

    /**
     * Gets the list of jira issues of the contest.
     *
     * @return the list of jira issues of the contest.
     */
    public List<TcJiraIssue> getIssues() {
        return issues;
    }

    /**
     * Sets the list of jira issues of the contest.
     *
     * @param issues the list of jira issues of the contest.
     */
    public void setIssues(List<TcJiraIssue> issues) {
        this.issues = issues;
    }

    /**
     * Gets the list of bug races of the contest.
     *
     * @return the list of the bug races of the contest.
     */
    public List<TcJiraIssue> getBugRaces() {
        return bugRaces;
    }

    /**
     * Sets the list of bug races of the contest.
     *
     * @param bugRaces the list of bug races of the contest.
     */
    public void setBugRaces(List<TcJiraIssue> bugRaces) {
        this.bugRaces = bugRaces;
    }

    /**
     * Gets the number of the unresolved issues of the contest.
     *
     * @return the number of the unresolved issues.
     */
    public int getUnresolvedIssuesNumber() {
        int count = 0;
        List<Long> resolvedStatusIds = ConfigUtils.getIssueTrackingConfig().getResolvedStatusIds();
        for(TcJiraIssue issue : issues) {
            if (!resolvedStatusIds.contains(Long.parseLong(issue.getStatusId()))) {
                 count ++;
            }
        }
        return count;
    }

    /**
     * Gets the number of unresolved bug races of the contest.
     *
     * @return the number of the unresolved bug races.
     */
    public int getUnresolvedBugRacesNumber() {
        int count = 0;
        List<Long> resolvedStatusIds = ConfigUtils.getIssueTrackingConfig().getResolvedStatusIds();
        for(TcJiraIssue bugRace : bugRaces) {
            if (!resolvedStatusIds.contains(Long.parseLong(bugRace.getStatusId()))) {
                 count ++;
            }
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
     * Gets the number of all the issues of the contest.
     *
     * @return the number of all the issues of the contest.
     */
    public int getIssuesNumber() {
        return issues.size();
    }

    /**
     * Gets the number of all the bug races of the contest.
     *
     * @return the number of all the bug races of the contest.
     */
    public int getBugRacesNumber() {
        return bugRaces.size();
    }
}


