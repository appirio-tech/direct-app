/*
 * Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
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
 * <p>
 *     Version 1.1 TC Cockpit Bug Tracking R1 Cockpit Project Tracking change notes:
 *     - Added projectIDField, and badContestHealthIssuesNumber.
 * </p>
 *
 * <p>
 * Version 1.2 (TC Direct Issue Tracking Tab Update Assembly 1) change notes:
 *   <ol>
 *     <li>Added {@link #paymentStatusFieldId}, {@link #tcoPointsFieldId}, {@link #applicationNameFieldId}
 *     , {@link #bugTypeFieldId}, {@link #securityOpenId}, {@link #securityNDAId} fields.
 *     Also the getters/setters were added.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.3 (TC Direct Issue Tracking Tab Update Assembly 3) change notes:
 *   <ol>
 *     <li>Added {@link #directProjectIDField}, {@link #directProjectJQLQuery} fields.
 *     Also the getters/setters were added.</li>
 *   </ol>
 * </p>
 * 
 * @author Veve, xjtufreeman, TCSASSEMBER
 * @version 1.3
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
     * The custom field id for projectID.
     *
     * @since 1.1
     */
    @XmlElement
    private String projectIDField;

    /**
     * The custom field id for direct project id.
     *
     * @since 1.3
     */
    @XmlElement
    private String directProjectIDField;

    /**
     * The custom field id for Payment Status.
     * 
     * @since 1.2
     */
    @XmlElement
    private String paymentStatusFieldId;
    
    /**
     * The custom field id for TCO Points.
     * 
     * @since 1.2
     */
    @XmlElement
    private String tcoPointsFieldId;

    /**
     * The custom field id for Application or Component Name.
     * 
     * @since 1.2
     */
    @XmlElement
    private String applicationNameFieldId;
    
    /**
     * The custom field id for Bug Type.
     * 
     * @since 1.2
     */
    @XmlElement
    private String bugTypeFieldId;
    
    /**
     * The id of security "Open" level.
     * 
     * @since 1.2
     */
    @XmlElement
    private Long securityOpenId;
    
    /**
     * The id of security "NDA Members Only" level.
     * 
     * @since 1.2
     */
    @XmlElement
    private Long securityNDAId;
    
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
     * JQL query for direct project.
     *
     * @since 1.3
     */
    @XmlElement
    private String directProjectJQLQuery;


    /**
     * The max limit size of the result returned by Jira RPC soap service.
     */
    @XmlElement
    private int maxResultNumber;

    /**
     * The number of issues that represents the contest is in a bad 'health'
     *
     * @since 1.1
     */
    @XmlElement
    private int badContestHealthIssuesNumber;

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
     * Gets the JQL query for direct project.
     *
     * @return the JQL query for direct project.
     *
     * @since 1.3
     */
    public String getDirectProjectJQLQuery() {
        return directProjectJQLQuery;
    }

    /**
     * Sets the JQL query for direct project.
     *
     * @param directProjectJQLQuery the JQL query for direct project.
     *
     * @since 1.3
     */
    public void setDirectProjectJQLQuery(String directProjectJQLQuery) {
        this.directProjectJQLQuery = directProjectJQLQuery;
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

    /**
     * Gets the number of unresolved issues which represents a contest in a bad health.
     *
     * @return the bad contest health issues number.
     * @since 1.1
     */
    public int getBadContestHealthIssuesNumber() {
        return badContestHealthIssuesNumber;
    }

    /**
     * Sets the number of unresolved issues which represents a contest in a bad health.
     *
     * @param badContestHealthIssuesNumber the bad contest health issues number.
     * @since 1.1
     */
    public void setBadContestHealthIssuesNumber(int badContestHealthIssuesNumber) {
        this.badContestHealthIssuesNumber = badContestHealthIssuesNumber;
    }

    /**
     * Gets the custom projectIDField.
     *
     * @return the custom projectIDField.
     * @since 1.1
     */
    public String getProjectIDField() {
        return projectIDField;
    }

    /**
     * Sets the custom projectIDField.
     *
     * @param projectIDField the custom projectIDField
     * @since 1.1
     */
    public void setProjectIDField(String projectIDField) {
        this.projectIDField = projectIDField;
    }

    /**
     * Gets the custom directProjectIDField.
     *
     * @return the custom direct Project ID Field.
     * @since 1.3
     */
    public String getDirectProjectIDField() {
        return directProjectIDField;
    }

    /**
     * Sets the custom directProjectIDField.
     *
     * @param directProjectIDField the custom direct Project ID Field
     * @since 1.3
     */
    public void setDirectProjectIDField(String directProjectIDField) {
        this.directProjectIDField = directProjectIDField;
    }

    /**
     * Gets the custom field id of Payment Status.
     * 
     * @return the custom field id of Payment Status.
     * @since 1.2
     */
    public String getPaymentStatusFieldId() {
        return paymentStatusFieldId;
    }

    /**
     * Sets the custom field id of Payment Status.
     * 
     * @param paymentStatusFieldId the custom field id of Payment Status.
     * @since 1.2
     */
    public void setPaymentStatusFieldId(String paymentStatusFieldId) {
        this.paymentStatusFieldId = paymentStatusFieldId;
    }

    /**
     * Gets the custom field id of TCO Points.
     * 
     * @return the custom field id of TCO Points.
     * @since 1.2
     */
    public String getTcoPointsFieldId() {
        return tcoPointsFieldId;
    }

    /**
     * Sets the custom field id of TCO Points.
     * 
     * @param tcoPointsFieldId the custom field id of TCO Points.
     * @since 1.2
     */
    public void setTcoPointsFieldId(String tcoPointsFieldId) {
        this.tcoPointsFieldId = tcoPointsFieldId;
    }

    /**
     * Gets the custom field id for Application or Component Name.
     * 
     * @return the custom field id for Application or Component Name.
     * @since 1.2
     */
    public String getApplicationNameFieldId() {
        return applicationNameFieldId;
    }

    /**
     * Sets the custom field id for Application or Component Name.
     * 
     * @param applicationNameFieldId the custom field id for Application or Component Name.
     * @since 1.2
     */
    public void setApplicationNameFieldId(String applicationNameFieldId) {
        this.applicationNameFieldId = applicationNameFieldId;
    }

    /**
     * Gets the custom field for Bug Type.
     * 
     * @return the custom field for Bug Type.
     * @since 1.2
     */
    public String getBugTypeFieldId() {
        return bugTypeFieldId;
    }

    /**
     * Sets the custom field for Bug Type.
     * 
     * @param bugTypeFieldId the custom field for Bug Type.
     * @since 1.2
     */
    public void setBugTypeFieldId(String bugTypeFieldId) {
        this.bugTypeFieldId = bugTypeFieldId;
    }

    /**
     * Gets the id of of security "Open" level.
     * 
     * @return the id of of security "Open" level.
     * @since 1.2
     */
    public Long getSecurityOpenId() {
        return securityOpenId;
    }

    /**
     * Sets the id of of security "Open" level.
     * 
     * @param securityOpenId the id of of security "Open" level.
     * @since 1.2
     */
    public void setSecurityOpenId(Long securityOpenId) {
        this.securityOpenId = securityOpenId;
    }

    /**
     * Gets the id of of security "NDA Members Only" level.
     * 
     * @return the id of of security "NDA Members Only" level.
     * @since 1.2
     */
    public Long getSecurityNDAId() {
        return securityNDAId;
    }

    /**
     * Sets the id of of security "NDA Members Only" level.
     * 
     * @param securityNDAId the id of of security "NDA Members Only" level.
     * @since 1.2
     */
    public void setSecurityNDAId(Long securityNDAId) {
        this.securityNDAId = securityNDAId;
    }
}
