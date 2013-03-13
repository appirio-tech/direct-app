/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;

import com.topcoder.direct.services.view.action.contest.JIRAAttachmentBaseAction;
import com.atlassian.jira.rpc.soap.client.RemoteCustomFieldValue;
import com.atlassian.jira.rpc.soap.client.RemoteIssue;
import com.topcoder.direct.services.configs.ConfigUtils;
import com.topcoder.direct.services.configs.IssueTrackingConfig;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.direct.services.view.dto.TcJiraIssue;
import com.topcoder.direct.services.view.dto.contest.JIRAIssueDTO;
import com.topcoder.direct.services.view.form.JIRAIssueForm;
import com.topcoder.direct.services.view.util.AuthorizationProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.jira.JiraRpcServiceWrapper;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.facade.project.ProjectServiceFacade;
import com.topcoder.service.project.SoftwareCompetition;

/**
 * <p>This class is a Struts action class used to create a JIRA issue associated to Cockpit project ID.
 * The created JIRA issue will be stored in <code>result</code> instance. So it can be used in an AJAX way.</p>
 * 
 * @author notpad
 * @version 1.0
 */
public class CreateProjectJIRAIssueAction extends JIRAAttachmentBaseAction {
    /**
     * <p>Represents the serial version unique id.</p>
     */
    private static final long serialVersionUID = 101024028591L;
    
    /**
     * <p>A <code>JIRAIssueForm</code> instance holding the data submitted by user.</p>
     */
    private JIRAIssueForm issue;
    
    /**
     * <p>The JIRA project name where the JIRA issue will be created in. It will be injected by Spring IoC.</p>
     */
    private String jiraProject;
    
    /**
     * <p>The ID of the issue type to create JIRA issue. It will be injected by Spring IoC.</p>
     */
    private int issueTypeId;
    
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
     * <p>Sets the JIRA project name where the JIRA issue will be created in.</p>
     * 
     * @param jiraProject JIRA project name where the JIRA issue will be created in.
     */
    public void setJiraProject(String jiraProject) {
        this.jiraProject = jiraProject;
    }

    /**
     * <p>Sets the ID of the issue type to create JIRA issue.</p>
     * 
     * @param issueTypeId ID of the issue type to create JIRA issue.
     */
    public void setIssueTypeId(int issueTypeId) {
        this.issueTypeId = issueTypeId;
    }

    /**
     * <p>Empty constructor.</p>
     */
    public CreateProjectJIRAIssueAction() {
        
    }
    
    /**
     * <p>Handles the incoming request. It will create a JIRA issue. If action is executed successfully then the created
     * JIRA issue will be stored in <code>result</code> instance.</p>
     *
     * @throws Exception if an unexpected error occurs while processing the request.
     */
    public void executeAction() throws Exception {
        long projectId = getProjectId();
        boolean granted = AuthorizationProvider.isUserGrantedWriteAccessToProject(DirectUtils.getTCSubjectFromSession(), projectId);
        if (!granted) {
            setResult("Sorry, you don't have permission to access this project.");
            return;
        }
        IssueTrackingConfig config = ConfigUtils.getIssueTrackingConfig();
        TCSubject currentUser = DirectStrutsActionsHelper.getTCSubjectFromSession();
        
        String tcDirectProjectName;
        // the projectId field
        String projectOrContestId = config.getProjectIDField();
        projectOrContestId = config.getDirectProjectIDField();
        ProjectServiceFacade projectServiceFacade = getProjectServiceFacade();
        tcDirectProjectName = projectServiceFacade.getProject(currentUser, projectId).getName();

        // create a new JIRA issue
        RemoteIssue remoteIssue = new RemoteIssue();
        // set the JIRA project
        remoteIssue.setProject(jiraProject);
        // set the issue type
        remoteIssue.setType(String.valueOf(issueTypeId));
        // set the issue summary
        remoteIssue.setSummary(issue.getName());
        // set the environment
        remoteIssue.setEnvironment(issue.getEnvironment());
        // set the description
        remoteIssue.setDescription(issue.getDescription());
        // set reporter
        remoteIssue.setReporter(getSessionData().getCurrentUserHandle());

        // set the custom fields
        remoteIssue.setCustomFieldValues(new RemoteCustomFieldValue[] {
                // First Place Payment
                createRemoteCustomFieldValue(config.getPrizeFieldId(), String.valueOf(issue.getFirstPlacePayment())),
                // Payment Status
                createRemoteCustomFieldValue(config.getPaymentStatusFieldId(), "Payment Required"),
                // TCO Points
                createRemoteCustomFieldValue(config.getTcoPointsFieldId(), String.valueOf(issue.getTcoPoints())),
                // Contest Id or Project Id
                createRemoteCustomFieldValue(projectOrContestId, String.valueOf(projectId)),
                // Application or Component Name
                createRemoteCustomFieldValue(config.getApplicationNameFieldId(), tcDirectProjectName),
                // Bug Type
                createRemoteCustomFieldValue(config.getBugTypeFieldId(), issue.getType())
        });
        
        TcJiraIssue newIssue = JiraRpcServiceWrapper.createIssue(
                remoteIssue, issue.isCca() ? config.getSecurityNDAId() : config.getSecurityOpenId());
        newIssue.setResolutionName(JiraRpcServiceWrapper.getResolutionNames().get(newIssue.getResolutionId()));
        newIssue.setStatusName(JiraRpcServiceWrapper.getIssueStatusNames().get(newIssue.getStatusId()));
        
        setResult(new JIRAIssueDTO(newIssue));
        
        // process the attachments
        try {
            super.executeAction();
        } catch (Exception e) {
            ((JIRAIssueDTO) getResult()).setAttachmentError(true);
        }
    }
    
    /**
     * <p>Create a <code>RemoteCustomFieldValue</code> instance holding the custom field value.</p>
     * 
     * @param fieldId the custom field ID
     * @param value the custom field value
     * @return the <code>RemoteCustomFieldValue</code> instance holding the custom field value.
     */
    private static RemoteCustomFieldValue createRemoteCustomFieldValue(String fieldId, String value) {
        RemoteCustomFieldValue fieldValue = new RemoteCustomFieldValue();
        fieldValue.setCustomfieldId(fieldId);
        fieldValue.setValues(new String[] {value});
        return fieldValue;
    }
    
    /**
     * <p>Gets the corresponding issue key.</p>
     * 
     * @return the corresponding issue key.
     * @since 1.1
     */
    protected String getIssueKey() {
        return ((JIRAIssueDTO) getResult()).getIssueKey();
    }
}
