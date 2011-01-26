/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.topcoder.direct.services.view.dto.contest.ContestCopilotDTO;
import org.apache.commons.collections.iterators.ArrayListIterator;
import org.apache.struts2.ServletActionContext;

import com.topcoder.direct.services.view.dto.CommonDTO;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.SessionData;

/**
 * <p>
 * The launch contest action. It will trigger to load the launch contest page and provide getters for some data.
 * </p>
 * <p>
 * Version 1.1 - Direct - View/Edit/Activate Studio Contests Assembly Change Note
 * - remove useless imports
 * </p>
 * <p>
 * Version 1.2 - TC Direct - Software Contest Creation Update Assembly Change Note
 * - add method getCurrentProjectCopilots to return copilots of current selected project.
 * </p>
 *
 * @author BeBetter,TCSDEVELOPER
 * @version 1.2
 */
public class LaunchContestAction extends ContestAction {
    private CommonDTO viewData =  new CommonDTO();

    private SessionData sessionData;

    /**
     * <p>
     * Executes the action. Does nothing for now.
     * </p>
     */
    @Override
    protected void executeAction() throws Exception {
        // it is to conform to existing source codes
        HttpServletRequest request = ServletActionContext.getRequest();

        HttpSession session = request.getSession(false);
        if (session != null) {
            sessionData = new SessionData(session);
        }

        List<ProjectBriefDTO> projects = DataProvider.getUserProjects(sessionData.getCurrentUserId());

        UserProjectsDTO userProjectsDTO = new UserProjectsDTO();
        userProjectsDTO.setProjects(projects);
        viewData.setUserProjects(userProjectsDTO);
    }

    public CommonDTO getViewData() {
        return viewData;
    }

    public SessionData getSessionData() {
        return sessionData;
    }

    /**
     * Gets the copilots assigned to current selected project.
     *
     * @return the copilots assigned to current selected project.
     * @throws Exception if there is any error.
     * @since  1.2
     */
    public List<ContestCopilotDTO> getCurrentProjectCopilots() throws Exception {
        // get current selected project from session first
        Long currentProjectId = sessionData.getCurrentSelectDirectProjectID();

        if (currentProjectId == null) {
            // check project context again
            if (sessionData.getCurrentProjectContext() != null) {
                currentProjectId = Long.valueOf(sessionData.getCurrentProjectContext().getId());
            }
        }

        List<ContestCopilotDTO> result = new ArrayList();

        if (currentProjectId != null) {
            // get copilots of the project
            result = DataProvider.getCopilotsForDirectProject(currentProjectId.longValue());
        }

        return result;
    }
}
