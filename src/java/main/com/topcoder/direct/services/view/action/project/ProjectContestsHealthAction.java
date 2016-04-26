/*
 * Copyright (C) 2011 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;

import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.direct.services.view.dto.contest.ContestBriefDTO;
import com.topcoder.direct.services.view.dto.contest.ContestDashboardDTO;
import com.topcoder.direct.services.view.dto.contest.ContestHealthDTO;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;
import com.topcoder.shared.util.logging.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>An action to be used for servicing the requests for getting the details on contests health for selected 
 * <code>TC Direct Project</code>.</p>
 *
 * <p>
 * Version 1.1 (BUGR-8693 TC Cockpit Add active bug races of project to the project overview page)
 * <ul>
 *     <li>Adds property {@link #activeBugRaces} and its getter</li>
 *     <li>Updates method {@link #executeAction()} to include project bug races into the project contests health data</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.2 (TopCoder Direct - Update jira issues retrieval to Ajax) @author -jacob- @challenge 30044583
 * <ul>
 *      <li>Remove activeBugRaces and all the related codes</li>
 * </ul>
 * </p>
 *  
 * @author isv, Veve, -jacob-
 * @version 1.2
 */
public class ProjectContestsHealthAction extends BaseDirectStrutsAction {

    /**
     * <p>A <code>Logger</code> to be used for logging the events encountered while processing the requests.</p>
     */
    private static final Logger log = Logger.getLogger(ProjectContestsHealthAction.class);

    /**
     * <p>A <code>long</code> providing the ID of a project to get contests health for.</p>
     */
    private long projectId;

    /**
     * <p>Constructs new <code>ProjectContestsHealthAction</code> instance. This implementation does nothing.</p>
     */
    public ProjectContestsHealthAction() {
    }

    /**
     * <p>Gets the ID of a project to get contests health for.</p>
     *
     * @return a <code>long</code> providing the ID of a project to get contests health for.
     */
    public long getProjectId() {
        return this.projectId;
    }

    /**
     * <p>Sets the ID of a project to get contests health for.</p>
     *
     * @param projectId a <code>long</code> providing the ID of a project to get contests health for.
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }


    /**
     * <p> This is the template method where the action logic will be performed by children classes. </p>
     *
     * @throws Exception if any error occurs
     */
    @Override
    protected void executeAction() {
        log.info("Started");
        try {
            TCSubject currentUser = DirectStrutsActionsHelper.getTCSubjectFromSession();

            // Retrieve the contests health for project
            log.info("Retrieving contests health data for project " + getProjectId());
            Map<ContestBriefDTO, ContestHealthDTO> contests =
               DataProvider.getProjectContestsHealth(currentUser.getUserId(), getProjectId(), true);
            log.info("Retrieved contests health data for project " + getProjectId() 
                      + ", contests count = " + contests.size());

            List<Map<String, Object> > results = new ArrayList<Map<String, Object> >();
            // Get dashboard data for each contest
            for (ContestBriefDTO contest : contests.keySet()) {
                ContestDashboardDTO contestDashboardData =
                    DataProvider.getContestDashboardData(contest.getId(), !contest.isSoftware(), false);
                contests.get(contest).setDashboardData(contestDashboardData);

                Map<String, Object> contestResult = new HashMap<String, Object>();
                contestResult.put("contest", contest);
                contestResult.put("healthData", contests.get(contest));
                results.add(contestResult);
            }

            setResult(results);
        } catch (Exception e) {
            DirectUtils.getServletResponse().setStatus(500);
            log.error("Got an error", e);
        } finally {
            log.info("Finished");
        }
    }
}
