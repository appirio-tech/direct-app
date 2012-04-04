/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import java.io.Serializable;

import com.topcoder.direct.services.view.dto.TcJiraIssue;

/**
 * <p>A DTO providing information of JIRA issue. It can be used to
 * serialize data to JSON format in AJAX calls.</p>
 *
 * @author TCSASSEMBER
 * @version 1.0
 */
public class JIRAIssueDTO implements Serializable {
    /**
     * <p>Represents the serial version unique id.</p>
     */
    private static final long serialVersionUID = 32132421279L;

    /**
     * <p>Represents the issue summary.</p>
     */
    private String issueSummary;
    
    /**
     * <p>Represents the description.</p>
     */
    private String description;
    
    /**
     * <p>Represents the environment.</p>
     */
    private String environment;
    
    /**
     * <p>Represents the first place payment.</p>
     */
    private float prize;
    
    /**
     * <p>Represents the payment status.</p>
     */
    private String paymentStatus;
    
    /**
     * <p>Represents the TCO points.</p>
     */
    private int tcoPoints;
    
    /**
     * <p>Represents the link of the JIRA issue.</p>
     */
    private String issueLink;
    
    /**
     * <p>Represents the ID of the JIRA issue.</p>
     */
    private String issueId;
    
    /**
     * <p>Represents the issue key.</p>
     */
    private String issueKey;
    
    /**
     * <p>Represents the JIRA project name of the JIRA issue.</p>
     */
    private String projectName;
    
    /**
     * <p>Represents the class name used to render issue status.</p>
     */
    private String issueStatusClass;
    
    /**
     * <p>Represents the issue status.</p>
     */
    private String statusName;
    
    /**
     * <p>Represents the creation date.</p>
     */
    private String creationDateString;
    
    /**
     * <p>Represents the resolution.</p>
     */
    private String resolutionName;
    
    /**
     * <p>Represents the votes number.</p>
     */
    private long votesNumber;
    
    /**
     * <p>Represents the link of the reporter profile page.</p>
     */
    private String reporterProfile;
    
    /**
     * <p>Represents the reporter.</p>
     */
    private String reporter;
    
    /**
     * <p>Represents the assignee.</p>
     */
    private String assignee;
    
    /**
     * <p>Represents the link of the assignee profile page.</p>
     */
    private String assigneeProfile;
    
    /**
     * <p>Represents the update date.</p>
     */
    private String updateDateString;
    
    /**
     * <p>Represents the due date.</p>
     */
    private String dueDateString;
    
    /**
     * <p>Empty constructor.</p>
     */
    public JIRAIssueDTO() {
        
    }
    
    /**
     * <p>
     * Construct a new <code>JIRAIssueDTO</code> instance from a <code>TcJiraIssue</code> instance.
     * </p>
     * 
     * @param issue the <code>TcJiraIssue</code> instance. 
     */
    public JIRAIssueDTO(TcJiraIssue issue) {
        this.issueSummary = issue.getIssueSummary();
        this.description = issue.getDescription();
        this.environment = issue.getEnvironment();
        this.prize = issue.getPrize();
        this.paymentStatus = issue.getPaymentStatus();
        this.tcoPoints = issue.getTCOPoints();
        this.issueId = issue.getIssueId();
        this.issueKey = issue.getIssueKey();
        this.issueLink = issue.getIssueLink();
        this.projectName = issue.getProjectName();
        this.issueStatusClass = issue.getIssueStatusClass();
        this.statusName = issue.getStatusName();
        this.creationDateString = issue.getCreationDateString();
        this.resolutionName = issue.getResolutionName();
        this.votesNumber = issue.getVotesNumber();
        this.reporterProfile = issue.getReporterProfile();
        this.reporter = issue.getReporter();
        this.assignee = issue.getAssignee();
        this.assigneeProfile = issue.getAssigneeProfile();
        this.updateDateString = issue.getUpdateDateString();
        this.dueDateString = issue.getDueDateString();
    }

    /**
     * <p>Gets the issue summary.</p>
     * 
     * @return the issue summary.
     */
    public String getIssueSummary() {
        return issueSummary;
    }

    /**
     * <p>Sets the issue summary.</p>
     * 
     * @param issueSummary the issue summary.
     */
    public void setIssueSummary(String issueSummary) {
        this.issueSummary = issueSummary;
    }

    /**
     * <p>Gets the description.</p>
     * 
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>Sets the description.</p>
     * 
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * <p>Gets the environment.</p>
     * 
     * @return the environment
     */
    public String getEnvironment() {
        return environment;
    }

    /**
     * <p>Sets the environment.</p>
     * 
     * @param environment the environment
     */
    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    /**
     * <p>Gets the first place payment.</p>
     * 
     * @return the first place payment
     */
    public float getPrize() {
        return prize;
    }

    /**
     * <p>Sets the first place payment.</p>
     * 
     * @param prize the first place payment
     */
    public void setPrize(float prize) {
        this.prize = prize;
    }

    /**
     * <p>Gets the payment status.</p>
     * 
     * @return the payment status
     */
    public String getPaymentStatus() {
        return paymentStatus;
    }

    /**<p>Sets the payment status.</p>
     * 
     * @param paymentStatus the payment status
     */
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    /**
     * <p>Gets the TCO points.</p>
     * 
     * @return the TCo points
     */
    public int getTcoPoints() {
        return tcoPoints;
    }

    /**
     * <p>Sets the TCO points.</p>
     * 
     * @param tcoPoints the TCo points
     */
    public void setTcoPoints(int tcoPoints) {
        this.tcoPoints = tcoPoints;
    }

    /**
     * <p>Gets the link of the issue.</p>
     * 
     * @return the link of the issue.
     */
    public String getIssueLink() {
        return issueLink;
    }

    /**
     * <p>Sets the link of the issue.</p>
     * 
     * @param issueLink the link of the issue.
     */
    public void setIssueLink(String issueLink) {
        this.issueLink = issueLink;
    }

    /**
     * <p>Gets the ID of the issue.</p>
     * 
     * @return the ID of the issue.
     */
    public String getIssueId() {
        return issueId;
    }

    /**
     * <p>Sets the ID of the issue.</p>
     * 
     * @param issueId the ID of the issue.
     */
    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    /**
     * <p>Gets the issue key.</p>
     * 
     * @return the issue key
     */
    public String getIssueKey() {
        return issueKey;
    }

    /**
     * <p>Sets the issue key.</p>
     * 
     * @param issueKey the issue key
     */
    public void setIssueKey(String issueKey) {
        this.issueKey = issueKey;
    }

    /**
     * <p>Gets the JIRA project name of the issue.</p>
     * 
     * @return the JIRA project name of the issue.
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * <p>Sets the JIRA project name of the issue.</p>
     * 
     * @param projectName the JIRA project name of the issue.
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * <p>Gets the class name used to render the issue status.</p>
     * 
     * @return the class name used to render the issue status.
     */
    public String getIssueStatusClass() {
        return issueStatusClass;
    }

    /**
     * <p>Sets the class name used to render the issue status.</p>
     * 
     * @param issueStatusClass the class name used to render the issue status.
     */
    public void setIssueStatusClass(String issueStatusClass) {
        this.issueStatusClass = issueStatusClass;
    }

    /**
     * <p>Gets the issue status.</p>
     * 
     * @return the issue status.
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * <p>Sets the issue status.</p>
     * 
     * @param statusName the issue status.
     */
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    /**
     * <p>Gets the creation date.</p>
     * 
     * @return the creation date.
     */
    public String getCreationDateString() {
        return creationDateString;
    }

    /**
     * <p>Sets the creation date.</p>
     * 
     * @param creationDateString the creation date.
     */
    public void setCreationDateString(String creationDateString) {
        this.creationDateString = creationDateString;
    }

    /**
     * <p>Gets the resolution.</p>
     * 
     * @return the resolution
     */
    public String getResolutionName() {
        return resolutionName;
    }

    /**
     * <p>Sets the resolution.</p>
     * 
     * @param resolutionName the resolution
     */
    public void setResolutionName(String resolutionName) {
        this.resolutionName = resolutionName;
    }

    /**
     * <p>Gets the votes number.</p>
     * 
     * @return the votes number.
     */
    public long getVotesNumber() {
        return votesNumber;
    }

    /**
     * <p>Sets the votes number.</p>
     * 
     * @param votesNumber the votes number.
     */
    public void setVotesNumber(long votesNumber) {
        this.votesNumber = votesNumber;
    }

    /**
     * <p>Gets the link of reporter profile page.</p>
     * 
     * @return the link of reporter profile page.
     */
    public String getReporterProfile() {
        return reporterProfile;
    }

    /**
     * <p>Sets the link of reporter profile page.</p>
     * 
     * @param reporterProfile the link of reporter profile page.
     */
    public void setReporterProfile(String reporterProfile) {
        this.reporterProfile = reporterProfile;
    }

    /**
     * <p>Gets the reporter.</p>
     * 
     * @return the reporter
     */
    public String getReporter() {
        return reporter;
    }

    /**
     * <p>Sets the reporter.</p>
     * 
     * @param reporter the reporter
     */
    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    /**
     * <p>Gets the assignee.</p>
     * 
     * @return the assignee
     */
    public String getAssignee() {
        return assignee;
    }

    /**
     * <p>Sets the assignee.</p>
     * 
     * @param assignee the assignee
     */
    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    /**
     * <p>Gets the link of assignee profile page.</p>
     * 
     * @return the link of assignee profile page.
     */
    public String getAssigneeProfile() {
        return assigneeProfile;
    }

    /**
     * <p>Sets the link of assignee profile page.</p>
     * 
     * @param assigneeProfile the link of assignee profile page.
     */
    public void setAssigneeProfile(String assigneeProfile) {
        this.assigneeProfile = assigneeProfile;
    }

    /**
     * <p>Gets the update date.</p>
     * 
     * @return the update date.
     */
    public String getUpdateDateString() {
        return updateDateString;
    }

    /**
     * <p>Sets the update date.</p>
     * 
     * @param updateDateString the update date.
     */
    public void setUpdateDateString(String updateDateString) {
        this.updateDateString = updateDateString;
    }

    /**
     * <p>Gets the due date.</p>
     * 
     * @return the due date.
     */
    public String getDueDateString() {
        return dueDateString;
    }

    /**
     * <p>Sets the due date.</p>
     * 
     * @param dueDateString the due date.
     */
    public void setDueDateString(String dueDateString) {
        this.dueDateString = dueDateString;
    }
}
