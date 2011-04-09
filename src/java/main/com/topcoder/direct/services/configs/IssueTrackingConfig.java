/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.configs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * This class stores all the configuration for the cockpit issue tracking. It will use JAXB to load the configs
 * from the XML configuration file.
 *
 * @author TCSDEVELOPER
 * @version 1.0 (TC Cockpit Bug Tracking R1 Contest Tracking assembly)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "IssueTrackingConfiguration")
public class IssueTrackingConfig {

    /**
     * The url to access the jira rpc service.
     */
    @XmlElement
    private String jiraRpcURL;

    /**
     * The url prefix for the link to jira issue.
     */
    @XmlElement
    private String jiraIssueURLPrefix;

    /**
     * The url prefix for the link to jira user profile.
     */
    @XmlElement
    private String jiraProfilePrefix;

    /**
     * The user name for accessing the jira rpc service.
     */
	@XmlElement
    private String user;

    /**
     * The password for accessing the jira rpc service.
     */
	@XmlElement
    private String password;

    /**
     * A list of issue type ids used to tell which issue should be included.
     */
    @XmlElement(name = "includeTypeIds")
    private List<Long> includedIssueTypeIds;

    /**
     * The number of max authentication retry.
     */
    @XmlElement
    private int maxAuthRetry;

    /**
     * The jira project name used to put bug races into.
     */
    @XmlElement
    private String bugRaceProjectName;

    /**
     * The custom field id which is used to store first place prize for bug race in jira.
     */
    @XmlElement
    private String prizeFieldId;

    /**
     * A list of status id that represents an issue is resolved or closed.
     */
    @XmlElement(name = "resolvedStatusIds")
    private List<Long> resolvedStatusIds;

    /**
     * JQL query for software contest.
     */
    @XmlElement
    private String softwareContestJQLQuery;

    /**
     * JQL query for studio contest.
     */
    @XmlElement
    private String studioContestJQLQuery;

    /**
     * The max limit size of the result returned by Jira RPC soap service.
     */
    @XmlElement
    private int maxResultNumber;

    /**
     * Gets the jira RPC url.
     *
     * @return the jira RPC URL.
     */
    public String getJiraRpcURL() {
        return jiraRpcURL;
    }

    /**
     * Sets the jira RPC url.
     *
     * @param jiraRpcURL the jira RPC url to set.
     */
    public void setJiraRpcURL(String jiraRpcURL) {
        this.jiraRpcURL = jiraRpcURL;
    }

    /**
     * Gets the jira issue URL prefix.
     *
     * @return the jira issue URL prefix.
     */
    public String getJiraIssueURLPrefix() {
        return jiraIssueURLPrefix;
    }

    /**
     * Sets the jira issue URL prefix.
     *
     * @param jiraIssueURLPrefix the jira issue URL prefix to set.
     */
    public void setJiraIssueURLPrefix(String jiraIssueURLPrefix) {
        this.jiraIssueURLPrefix = jiraIssueURLPrefix;
    }

    /**
     * Gets the profile prefix for the jira user profile.
     *
     * @return the profile url prefix.
     */
    public String getJiraProfilePrefix() {
        return jiraProfilePrefix;
    }

    /**
     * Sets the profile URL prefix for the jira user profile.
     *
     * @param jiraProfilePrefix the jira profile url prefix to set.
     */
    public void setJiraProfilePrefix(String jiraProfilePrefix) {
        this.jiraProfilePrefix = jiraProfilePrefix;
    }

    /**
     * Gets the user name for jira rpc service.
     *
     * @return the user name for jira rpc service.
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the user name for jira rpc service.
     *
     * @param user the user name for jira rpc service.
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Gets the password for jira rpc service.
     *
     * @return the password for jira rpc service.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for jira rpc service.
     *
     * @param password the password for jira rpc service.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the issue type ids used to determine which issue to include.
     *
     * @return the issue type ids used to determine which issue to include.
     */
    public List<Long> getIncludedIssueTypeIds() {
        return includedIssueTypeIds;
    }

    /**
     * Sets the issue type ids used to determine which issue to include.
     *
     * @param includedIssueTypeIds the issue type ids used to determine which issue to include.
     */
    public void setIncludedIssueTypeIds(List<Long> includedIssueTypeIds) {
        this.includedIssueTypeIds = includedIssueTypeIds;
    }

    /**
     * Gets the max retry number of authentication.
     *
     * @return the max retry number.
     */
    public int getMaxAuthRetry() {
        return maxAuthRetry;
    }

    /**
     * Sets the max retry number of authentication.
     *
     * @param maxAuthRetry the max retry number.
     */
    public void setMaxAuthRetry(int maxAuthRetry) {
        this.maxAuthRetry = maxAuthRetry;
    }

    /**
     * Sets the jira project name used for bug races.
     *
     * @param bugRaceProjectName the jira project used for for bug races.
     */
    public void setBugRaceProjectName(String bugRaceProjectName) {
        this.bugRaceProjectName = bugRaceProjectName;
    }

    /**
     * Gets the jira project name used for bug races.
     *
     * @return the jira project name used for bug races.
     */
    public String getBugRaceProjectName() {
        return bugRaceProjectName;
    }

    /**
     * Gets the custom field id used for first place prize.
     *
     * @return the custom field id used for first place prize.
     */
    public String getPrizeFieldId() {
        return prizeFieldId;
    }

    /**
     * Sets the custom field id used for first place prize.
     *
     * @param prizeFieldId the custom field id used for first place prize.
     */
    public void setPrizeFieldId(String prizeFieldId) {
        this.prizeFieldId = prizeFieldId;
    }

    /**
     * Gets the jira status ids for resolved and closed status.
     *
     * @return the jira status ids for resolved and closed status.
     */
    public List<Long> getResolvedStatusIds() {
        return resolvedStatusIds;
    }

    /**
     * Sets the jira status ids for resolved and closed status.
     *
     * @param resolvedStatusIds the jira status ids for resolved and closed status.
     */
    public void setResolvedStatusIds(List<Long> resolvedStatusIds) {
        this.resolvedStatusIds = resolvedStatusIds;
    }

    /**
     * Gets the JQL query for software contest.
     *
     * @return the JQL query for software contest.
     */
    public String getSoftwareContestJQLQuery() {
        return softwareContestJQLQuery;
    }

    /**
     * Sets the JQL query for software contest.
     *
     * @param softwareContestJQLQuery the JQL query for software contest.
     */
    public void setSoftwareContestJQLQuery(String softwareContestJQLQuery) {
        this.softwareContestJQLQuery = softwareContestJQLQuery;
    }

    /**
     * Gets the JQL query for studio contest.
     *
     * @return the JQL query for studio contest.
     */
    public String getStudioContestJQLQuery() {
        return studioContestJQLQuery;
    }

    /**
     * Sets the JQL query for studio contests.
     *
     * @param studioContestJQLQuery the JQL query for studio contest.
     */
    public void setStudioContestJQLQuery(String studioContestJQLQuery) {
        this.studioContestJQLQuery = studioContestJQLQuery;
    }

    /**
     * Gets the max result number returned by the Jira RPC Service.
     *
     * @return the max result number returned by the Jira RPC Service.
     */
    public int getMaxResultNumber() {
        return maxResultNumber;
    }

    /**
     * Sets the max result number returned by the Jira RPC Service.
     *
     * @param maxResultNumber the max result number.
     */
    public void setMaxResultNumber(int maxResultNumber) {
        this.maxResultNumber = maxResultNumber;
    }
}
