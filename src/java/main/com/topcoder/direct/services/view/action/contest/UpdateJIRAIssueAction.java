/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest;

import com.atlassian.jira.rpc.soap.client.RemoteFieldValue;
import com.topcoder.direct.services.configs.ConfigUtils;
import com.topcoder.direct.services.configs.IssueTrackingConfig;
import com.topcoder.direct.services.exception.DirectException;
import com.topcoder.direct.services.view.action.contest.launch.StudioOrSoftwareContestAction;
import com.topcoder.direct.services.view.dto.TcJiraIssue;
import com.topcoder.direct.services.view.form.JIRAIssueForm;
import com.topcoder.direct.services.view.util.jira.JiraRpcServiceWrapper;

/**
 * <p>This class is a Struts action class used to update a JIRA issue.</p>
 *
 * @author TCSASSEMBLER
 * @version 1.0
 */
public class UpdateJIRAIssueAction extends StudioOrSoftwareContestAction {
    /**
     * <p>Represents the serial version unique id.</p>
     */
    private static final long serialVersionUID = -1234123214032L;
    
    /**
     * <p>A <code>JIRAIssueForm</code> instance holding the data submitted by user.</p>
     */
    private JIRAIssueForm issue;
    
    /**
     * <p>Gets the <code>JIRAIssueForm</code> instance holding the data submitted by user.</p>
     * 
     * @return the <code>JIRAIssueForm</code> instance holding the data submitted by user.
     */
    public JIRAIssueForm getIssue() {
        return issue;
    }

    /**
     * <p>Sets the <code>JIRAIssueForm</code> instance holding the data submitted by user.</p>
     * 
     * @param issue the <code>JIRAIssueForm</code> instance holding the data submitted by user.
     */
    public void setIssue(JIRAIssueForm issue) {
        this.issue = issue;
    }

    /**
     * <p>Empty constructor.</p>
     */
    public UpdateJIRAIssueAction() {
        
    }
    
    /**
     * <p>Handles the incoming request. It will update a JIRA issue.</p>
     *
     * @throws Exception if an unexpected error occurs while processing the request.
     */
    public void executeAction() throws Exception {
        long projectId = getProjectId();
        
        TcJiraIssue jiraIssue;
        try {
            jiraIssue = JiraRpcServiceWrapper.getIssueById(issue.getIssueId());
        } catch (Exception e) {
            throw new DirectException("Can't retrieve the JIRA issue", e);
        }
        
        // the project id of the JIRA issue must equal to the specified project id, otherwise
        // the URL must be faked by the user
        if (projectId != jiraIssue.getProjectID()) {
            throw new DirectException("Have no permission to edit the JIRA issue");
        }
        
        IssueTrackingConfig config = ConfigUtils.getIssueTrackingConfig();
        Long securityLevelId = issue.isCca() ? config.getSecurityNDAId() : config.getSecurityOpenId();
        RemoteFieldValue[] filedValues = new RemoteFieldValue[] {
                // environment
                new RemoteFieldValue("environment", new String[] {String.valueOf(issue.getEnvironment())}),
                // description
                new RemoteFieldValue("description", new String[] {String.valueOf(issue.getDescription())}),
                // issue summary
                new RemoteFieldValue("summary", new String[] {String.valueOf(issue.getName())}),
                // First Place Payment
                new RemoteFieldValue(config.getPrizeFieldId(), new String[] {String.valueOf(issue.getFirstPlacePayment())}),
                // TCO Points
                new RemoteFieldValue(config.getTcoPointsFieldId(), new String[] {String.valueOf(issue.getTcoPoints())}),
                // Security Level
                new RemoteFieldValue("security", new String[] {String.valueOf(securityLevelId)})
        };
        JiraRpcServiceWrapper.updateIssue(jiraIssue.getIssueKey(), filedValues);
    }
}
