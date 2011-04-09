/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto;

import com.atlassian.jira.rpc.soap.client.RemoteCustomFieldValue;
import com.atlassian.jira.rpc.soap.client.RemoteIssue;
import com.topcoder.direct.services.configs.ConfigUtils;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The DTO class which is used to store the data for a Jira issue of TopCoder.
 *
 * @author TCSDEVELOPER
 * @version 1.0 (TC Cockpit Bug Tracking R1 Contest Tracking assembly)
 */
public class TcJiraIssue implements Serializable {

    /**
     * The data format used for format the dates for the issues.
     */
    private static final DateFormat ISSUE_DATE_FORMAT = new SimpleDateFormat("MMM dd, yyyy HH:mm");

    /**
     * String constant to display n/a.
     */
    private static final String NA = "N/A";

    /**
     * The remote issue which stores data for the issue.
     */
    private final RemoteIssue issue;

    /**
     * The name of the current status of the issue.
     */
    private String statusName;

    /**
     * The name of the resolution status of the issue.
     */
    private String resolutionName;

    /**
     * Creates a TcJiraIssue instance.
     */
    public TcJiraIssue() {
        issue = new RemoteIssue();
    }

    /**
     * Creates a TcJiraIssue instance with a RemoteIssue.
     *
     * @param issue the RemoteIssue instance.g
     */
    public TcJiraIssue(RemoteIssue issue) {
        this.issue = issue;
    }

    /**
     * Gets the link of the jira issue.
     *
     * @return the link of the jira issue.
     */
    public String getIssueLink() {
        return ConfigUtils.getIssueTrackingConfig().getJiraIssueURLPrefix() + this.issue.getKey();
    }

    /**
     * Gets the jira project name of the jira issue.
     *
     * @return the jira project name of the jira issue.
     */
    public String getProjectName() {
        return this.issue.getProject();
    }

    /**
     * Gets the issue summary of the jira issue.
     *
     * @return the summary of the jira issue.
     */
    public String getIssueSummary() {
        return this.issue.getSummary();
    }

    /**
     * Gets the issue status CSS class to present the status color.
     *
     * @return the status CSS class.
     */
    public String getIssueStatusClass() {
        String statusName = getStatusName().trim().toLowerCase();

        if (statusName.equals("resolved") || statusName.equals("closed")) {
            return "resolved";
        } else if (statusName.equals("in progress")) {
            return "progress";
        } else {
            return "open";
        }
    }

    /**
     * Gets the key of the issue.
     *
     * @return the jira key of the issue.
     */
    public String getIssueKey() {
        return this.issue.getKey();
    }

    /**
     * Gets the status id of the issue.
     *
     * @return the status id of the issue.
     */
    public String getStatusId() {
        return this.issue.getStatus();
    }

    /**
     * Gets the status name of the issue.
     *
     * @return the status name of the issue.
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * Sets the status name of the issue.
     *
     * @param statusName the status name of the issue.
     */
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    /**
     * Gets the resolution id of the issue.
     *
     * @return the resolution id of the issue.
     */
    public String getResolutionId() {
        return this.issue.getResolution();
    }

    /**
     * Gets the resolution name of the issue.
     *
     * @return the resolution name of the issue.
     */
    public String getResolutionName() {
        return resolutionName;
    }

    /**
     * Sets the resolution name of the issue.
     *
     * @param resolutionName the resolution name to set.
     */
    public void setResolutionName(String resolutionName) {
        if (resolutionName == null) {
            this.resolutionName = "Unresolved";
        } else {
            this.resolutionName = resolutionName;
        }
    }

    /**
     * Gets the reporter handle of the issue.
     *
     * @return the reporter handle of the issue or "N/A" if not exists
     */
    public String getReporter() {
        return this.issue.getReporter() == null ? NA : this.issue.getReporter();
    }

    /**
     * Gets the link to the jira profile of the reporter.
     *
     * @return the link to the jira profile of the reporter.
     */
    public String getReporterProfile() {
        return ConfigUtils.getIssueTrackingConfig().getJiraProfilePrefix() + getReporter();
    }


    /**
     * Gets the assignee handle of the issue.
     *
     * @return the assignee handle of the jira issue.
     */
    public String getAssignee() {
        return this.issue.getAssignee() == null ? "Unassigned" : this.issue.getAssignee();
    }

    /**
     * Gets the link to the jira profile of the issue assignee.
     *
     * @return the link to the jira profile of the issue assignee.
     */
    public String getAssigneeProfile() {
        return ConfigUtils.getIssueTrackingConfig().getJiraProfilePrefix() + getAssignee();
    }

    /**
     * Gets the first place prize of the issue.
     *
     * @return the first place prize of the issue.
     */
    public float getPrize() {
        RemoteCustomFieldValue[] customValues = this.issue.getCustomFieldValues();
        for (RemoteCustomFieldValue rcf : customValues) {
            if (rcf.getCustomfieldId().trim().toLowerCase().equals(
                    ConfigUtils.getIssueTrackingConfig().getPrizeFieldId().trim().toLowerCase())) {
                return Float.parseFloat(rcf.getValues()[0]);
            }
        }

        // not found, return 0 by default
        return 0;
    }

    /**
     * Gets the number of votes for the issue.
     *
     * @return the number of votes.
     */
    public long getVotesNumber() {
        return this.issue.getVotes();
    }

    /**
     * Gets the creation date for the issue.
     *
     * @return the creation date for the issue.
     */
    public Date getCreationDate() {
        return this.issue.getCreated() == null ? null : this.issue.getCreated().getTime();
    }

    /**
     * Gets the update date for the issue.
     *
     * @return the update date for the issue.
     */
    public Date getUpdatedDate() {
        return this.issue.getUpdated() == null ? null : this.issue.getUpdated().getTime();
    }

    /**
     * Gets the due date of the issue.
     *
     * @return the due date of the issue.
     */
    public Date getDueDate() {
        return this.issue.getDuedate() == null ? null : this.issue.getDuedate().getTime();
    }

    /**
     * Gets the string representation of the issue creation date.
     *
     * @return the string representation of the issue creation date.
     */
    public String getCreationDateString() {
        Date date = getCreationDate();
        return date == null ? NA : ISSUE_DATE_FORMAT.format(date);
    }

    /**
     * Gets the string representation of the issue update date.
     *
     * @return the string representation of the issue update date.
     */
    public String getUpdateDateString() {
        Date date = getUpdatedDate();
        return date == null ? NA : ISSUE_DATE_FORMAT.format(date);
    }

    /**
     * Gets the string representation of the issue due date.
     *
     * @return the string representation of the issue due date.
     */
    public String getDueDateString() {
        Date date = getDueDate();
        return date == null ? NA : ISSUE_DATE_FORMAT.format(date);
    }
}
