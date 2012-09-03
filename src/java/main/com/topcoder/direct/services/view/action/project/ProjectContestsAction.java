/*
 * Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;

import com.topcoder.direct.services.view.action.AbstractAction;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.direct.services.view.action.dashboard.ActiveContestsAction;
import com.topcoder.direct.services.view.dto.IdNamePair;
import com.topcoder.direct.services.view.dto.TcJiraIssue;
import com.topcoder.direct.services.view.dto.project.ProjectContestDTO;
import com.topcoder.direct.services.view.dto.project.ProjectContestsDTO;
import com.topcoder.direct.services.view.form.ProjectIdForm;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.jira.JiraRpcServiceWrapper;
import com.topcoder.security.TCSubject;
import com.topcoder.shared.dataAccess.DataAccess;
import com.topcoder.shared.dataAccess.Request;
import com.topcoder.shared.dataAccess.resultSet.ResultSetContainer;
import com.topcoder.shared.util.DBMS;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>A <code>Struts</code> action to be used for handling requests for viewing the <code>Project Contests</code> page
 * for requested project.</p>
 *
 * <p>
 *     Version 1.1 - Release Assembly - 24hrs TopCoder Cockpit Project Contests Calendar View change:
 *     - Add getter to return current date on server
 * </p>
 *
 * <p>
 *     Version 1.2 - TopCoder Cockpit - Bug Race Project Contests View changes:
 *     - Add bug races as project contests to contest list view.
 * </p>
 *
 * <p>
 *     Version 1.3 - Release Assembly - TopCoder Cockpit Project Contest Results
 *          Export Part 1
 *     - Add checking for showing contest download panel
 * </p>
 *
 * @author isv, GreatKevin, TCSASSEMBLER
 * @version 1.3
 */
public class ProjectContestsAction extends AbstractAction implements FormAction<ProjectIdForm>,
                                                                     ViewAction<ProjectContestsDTO> {

    /**
     * <p>The constant string for show_contests_download.</p>
     */
    private static final String SHOW_CONTESTS_DOWNLOAD = "show_contests_download";

    /**
     * <p>A <code>ProjectIdForm</code> providing the ID of a requested project.</p>
     */
    private ProjectIdForm formData = new ProjectIdForm();

    /**
     * <p>A <code>ProjectContestsDTO</code> providing the view data for displaying by <code>Project Contests</code>
     * view.</p>
     */
    private ProjectContestsDTO viewData = new ProjectContestsDTO();

    /**
     * <p>Constructs new <code>ProjectContestsAction</code> instance. This implementation does nothing.</p>
     */
    public ProjectContestsAction() {
    }

    /**
     * <p>Gets the form data.</p>
     *
     * @return an <code>Object</code> providing the data for form submitted by user..
     */
    public ProjectIdForm getFormData() {
        return this.formData;
    }

    /**
     * <p>Gets the data to be displayed by view mapped to this action.</p>
     *
     * @return an <code>Object</code> providing the collector for data to be rendered by the view mapped to this action.
     */
    public ProjectContestsDTO getViewData() {
        return this.viewData;
    }

    /**
     * Gets current date on server.
     *
     * @return current date on server.
     * @since 1.1
     */
    public Date getCalendarToday() {
        return new Date();
    }

    /**
     * <p>Handles the incoming request. If action is executed successfully then changes the current project context to
     * project requested for this action.</p>
     *
     * @return a <code>String</code> referencing the next view or action to route request to. This implementation
     *         returns {@link #SUCCESS} always.
     * @throws Exception if an unexpected error occurs while processing the request.
     * @since 1.0
     */
    @Override
    public String execute() throws Exception {
        String result = super.execute();

        if (SUCCESS.equals(result)) {
            List<ProjectContestDTO> contests = getViewData().getProjectContests().getContests();
            TCSubject currentUser = DirectUtils.getTCSubjectFromSession();
            if (contests.isEmpty()) {
                getSessionData().setCurrentProjectContext(getViewData().getProjectStats().getProject());

            } else {
                if (this instanceof ActiveContestsAction)
                {

                    DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
                    Request request = new Request();
                    String queryName = "my_active_projects_contest_ids";
                    request.setContentHandle(queryName);
                    request.setProperty("uid", String.valueOf(currentUser.getUserId()));

                    Map<Long, IdNamePair> contestProjectMapping = new HashMap<Long, IdNamePair>();
                    final ResultSetContainer resultContainer = dataAccessor.getData(request).get(queryName);
                    final int recordNum = resultContainer.size();
                    for (int i = 0; i < recordNum; i++) {
                        long contestId = resultContainer.getLongItem(i, "project_id");
                        long projectId = resultContainer.getLongItem(i, "direct_project_id");
                        String projectName = resultContainer.getStringItem(i, "direct_project_name");

                        IdNamePair project = new IdNamePair();
                        project.setId(projectId);
                        project.setName(projectName);

                        contestProjectMapping.put(contestId, project);
                    }

                    final List<TcJiraIssue> bugRaceForDirectProject = JiraRpcServiceWrapper.getBugRaceForDirectProject(new HashSet<Long>(), "status = Open OR status = \"In Progress\"");

                    List<TcJiraIssue> filteredIssue = new ArrayList<TcJiraIssue>();

                    for(TcJiraIssue issue : bugRaceForDirectProject) {

                        if(contestProjectMapping.containsKey(issue.getProjectID())) {
                            IdNamePair project = contestProjectMapping.get(issue.getProjectID());
                            issue.setDirectProjectId(project.getId());
                            issue.setDirectProjectName(project.getName());
                            if(DirectUtils.getClientIdForProject(currentUser, project.getId()) != null) {
                                issue.setClientId(DirectUtils.getClientIdForProject(currentUser, project.getId()));
                            }
                            filteredIssue.add(issue);

                        }
                    }


                    getViewData().setProjectBugRaces(filteredIssue);

                }
                else if (this instanceof ProjectContestsAction)
                {

                    // add bug races to the contests
                    Set<Long> contestIds = new HashSet<Long>();

                    for(ProjectContestDTO c : contests) {
                        contestIds.add(c.getContest().getId());
                    }

                    final List<TcJiraIssue> bugRaceForDirectProject = JiraRpcServiceWrapper.getBugRaceForDirectProject(contestIds, null);

                    getViewData().setProjectBugRaces(bugRaceForDirectProject);
                }

                getSessionData().setCurrentProjectContext(contests.get(0).getContest().getProject());




            }

            // set the current direct project id in session, the contest details codes incorrectly
            // use setCurrentProjectContext to override the current chosen direct project with current
            // chosen contest, for the safe, we put the direct project id into session separately again
            getSessionData().setCurrentSelectDirectProjectID(getSessionData().getCurrentProjectContext().getId());

            // set the attribute of flag whether to show the contests download panel
            getSessionData().getSession().setAttribute(SHOW_CONTESTS_DOWNLOAD, 
                      DataProvider.showContestsDownload(getSessionData().getCurrentProjectContext().getId()));

            return SUCCESS;
        } else {
            return result;
        }
    }
}
