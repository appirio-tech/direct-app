/*
 * Copyright (C) 2010 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.copilot;

import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.copilot.CopilotPostingContestsListDTO;
import com.topcoder.direct.services.view.dto.project.ProjectContestDTO;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.security.TCSubject;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <p>A <code>Struts</code> action to be used for handling the requests for viewing the list of <code>Copilot Posting
 * </code> contests accessible to current user.</p>
 *
 * <p>
 *     Version 1.1 (Release Assembly - TopCoder Cockpit DataTables Filter Panel and Search Bar) changes:
 *     <ul>
 *         <li>Adds the customer id for the copilot postings</li>
 *     </ul>
 * </p>
 *
 * <p>
 * Version 1.2 (TopCoder Direct - Change Right Sidebar to pure Ajax)
 * - Removes the statements to populate the right sidebar direct projects and project contests. It's changed to
 * load these data via ajax instead after the page finishes loading.
 * </p>
 *
 * @author Veve
 * @version 1.2
 */
public class ListCopilotContestsAction extends BaseDirectStrutsAction {

    /**
     * <p>A <code>CopilotPostingContestsListDTO</code> providing the data to be displayed by Manage Copilot Postings
     * view.</p>
     */
    private CopilotPostingContestsListDTO viewData;

    /**
     * <p>A <code>SessionData</code> providing interface to current session.</p>
     */
    private SessionData sessionData;

    /**
     * <p>Constructs new <code>ListCopilotContestsAction</code> instance. This implementation does nothing.</p>
     */
    public ListCopilotContestsAction() {
        this.viewData = new CopilotPostingContestsListDTO();
    }

    /**
     * <p>Gets the current session associated with the incoming request from client.</p>
     *
     * @return a <code>SessionData</code> providing access to current session.
     */
    public SessionData getSessionData() {
        return this.sessionData;
    }

    /**
     * <p>Gets the data to be displayed by Manage Copilot Postings view.</p>
     *
     * @return a <code>CopilotPostingContestsListDTO</code> providing the data to be displayed by Manage Copilot
     *         Postings view.
     */
    public CopilotPostingContestsListDTO getViewData() {
        return this.viewData;
    }

    /**
     * <p>Handles the incoming request.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        // get current session
        HttpServletRequest request = DirectUtils.getServletRequest();
        this.sessionData = new SessionData(request.getSession());
        TCSubject currentUser = getCurrentUser();

        // Get list of Copilot Posting contests available to current user
        getViewData().setContests(DataProvider.getCopilotPostingContests(currentUser));

        // gets the id of all the projects
        Set<Long> projectIds = new HashSet<Long>();

        for(ProjectContestDTO c : getViewData().getContests()) {
            projectIds.add(c.getContest().getProject().getId());
        }

        long[] ids = new long[projectIds.size()];
        int k = 0;
        for(Long l : projectIds) {
            ids[k++] = l;
        }

        // gets the customer id for the projects
        Map<Long, Long> projectsCustomerIdsMap = DataProvider.getProjectsCustomers(ids);

        // adds customer id to copilot postings
        for(ProjectContestDTO c : getViewData().getContests()) {
            long projectId = c.getContest().getProject().getId();
            c.getContest().setCustomerId(projectsCustomerIdsMap.get(projectId) == null ? -1 : projectsCustomerIdsMap.get(projectId));
        }

    }
}
