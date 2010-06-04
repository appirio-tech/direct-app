/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
 *
 * @author BeBetter,TCSDEVELOPER
 * @version 1.1
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
}
