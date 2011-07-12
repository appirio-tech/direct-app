/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import com.topcoder.direct.services.configs.ConfigUtils;
import com.topcoder.direct.services.view.dto.CommonDTO;
import com.topcoder.direct.services.view.dto.TcJiraIssue;
import com.topcoder.direct.services.view.form.ContestIdForm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * <p>
 * The DTO class used for storing the data and rendering the contest issue tracking page.
 * </p>
 *
 * <p>Version 1.1 (TC Cockpit Bug Tracking R1 Cockpit Project Tracking assembly) change notes:
 * - add method getUnresolvedIssues.
 * - add private static comparator to compare TcJiraIssue by creation date.
 * </p>
 *
 * <p>Version 1.2 (TC Direct Contest Dashboard Update Assembly) change notes:
 * - change to extend from BaseContestCommonDTO.
 * - remove ContestStatsDTO and corresponding get/set methods.
 * </p>
 *
 * @author Veve, TCSASSEMBLER
 * @version  1.2
 */
public class ContestIssuesTrackingDTO extends BaseContestCommonDTO implements ContestStatsDTO.Aware, ContestIdForm.Aware {

    /**
     * Comparator to compare TcJiraIssue by creation date.
     *
     * @since 1.1
     */
    private static final Comparator<TcJiraIssue> CREATION_DATE_SORTER =
                                 new Comparator<TcJiraIssue>() {
        public int compare(TcJiraIssue e1, TcJiraIssue e2) {
            return e2.getCreationDate().compareTo(e1.getCreationDate());
        }
    };

    /**
     * <p>A <code>long</code> providing the ID of contest.</p>
     */
    private long contestId;

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
     * Gets the list of unresolved issues. The list of unresolved is ordered by creation date decreasingly.
     *
     * @return the list of unresolved issues.
     * @since 1.1
     */
    public List<TcJiraIssue> getUnresolvedIssues() {
        List<TcJiraIssue> unresolvedIssues = new ArrayList<TcJiraIssue>();
        List<Long> resolvedStatusIds = ConfigUtils.getIssueTrackingConfig().getResolvedStatusIds();
        for(TcJiraIssue issue : issues) {
            if (!resolvedStatusIds.contains(Long.parseLong(issue.getStatusId()))) {
                 unresolvedIssues.add(issue);
            }
        }

        // sort by creation date
        Collections.sort(unresolvedIssues, CREATION_DATE_SORTER);

        return unresolvedIssues;
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


