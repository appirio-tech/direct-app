/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest;

import java.util.HashMap;
import java.util.Map;

import com.atlassian.jira.rpc.soap.client.RemoteFieldValue;
import com.topcoder.direct.services.configs.ConfigUtils;
import com.topcoder.direct.services.configs.IssueTrackingConfig;
import com.topcoder.direct.services.exception.DirectException;
import com.topcoder.direct.services.view.dto.TcJiraIssue;
import com.topcoder.direct.services.view.form.JIRAIssueForm;
import com.topcoder.direct.services.view.util.jira.JiraRpcServiceWrapper;

/**
 * <p>This class is a Struts action class used to update a JIRA issue.</p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TC Direct Issue Tracking Tab Update Assembly 2 v1.0) change notes:
 *   <ol>
 *     <li>The based class was changed to <code>JIRAAttachmentBaseAction</code>.</li>
 *     <li>Update {@link #executeAction()} to process the JIRA attachments.</li>
 *     <li>Added method {@link #getIssueKey()} to get the corresponding issue key.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.2 (Release Assembly - TC Direct Issue Tracking Tab Update Assembly 3 v1.0) change notes:
 *   <ol>
 *     <li>Update {@link #executeAction()} to process the direct project bugs</li>
 *   </ol>
 * </p>
 * 
 * @author xjtufreeman, TCSASSEMBLER
 * @version 1.2
 */
public class UpdateJIRAIssueAction extends JIRAAttachmentBaseAction {
    /**
     * <p>Represents the serial version unique id.</p>
     */
    private static final long serialVersionUID = -1234123214032L;
    
    /**
     * <p>A <code>JIRAIssueForm</code> instance holding the data submitted by user.</p>
     */
    private JIRAIssueForm issue;
    
    /**
     * <p>Represents the corresponding issue key.</p>
     * 
     * @since 1.1
     */
    private String issueKey;
    
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
        
        // the project id of the JIRA issue must equal to the specified project id and it's not a direct project bug,
        // otherwise the URL must be faked by the user
        if (!issue.isProjectBug() && projectId != jiraIssue.getProjectID()) {
            throw new DirectException("Have no permission to edit the JIRA issue");
        }
        
        this.issueKey = jiraIssue.getIssueKey();
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
                // bug type
                new RemoteFieldValue(config.getBugTypeFieldId(), new String[] {issue.getType()}),
                // Security Level
                new RemoteFieldValue("security", new String[] {String.valueOf(securityLevelId)})
        };
        JiraRpcServiceWrapper.updateIssue(jiraIssue.getIssueKey(), filedValues);
        
        // process the attachments
        try {
            super.executeAction();
        } catch (Exception e) {
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("attachmentError", Boolean.TRUE);
            setResult(result);
        }
    }
    
    /**
     * <p>Gets the corresponding issue key.</p>
     * 
     * @return the corresponding issue key.
     * @since 1.1
     */
    protected String getIssueKey() {
        return this.issueKey;
    }
}
