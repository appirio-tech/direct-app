/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.topcoder.direct.services.view.dto.CommonDTO;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.service.configs.ConfigUtils;
import com.topcoder.service.configs.StudioContestType;

/**
 * <p>
 * The launch contest action. It will trigger to load the launch contest page and provide getters for some data.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class LaunchContestAction extends BaseDirectStrutsAction {
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

    /**
     * <p>
     * Gets a list of studio contest types.
     * </p>
     *
     * @return the list of studio contest types.
     */
    public List<StudioContestType> getStudioContestTypes() {
        return ConfigUtils.getStudioContestTypes();
    }

    public CommonDTO getViewData() {
        return viewData;
    }

    public SessionData getSessionData() {
        return sessionData;
    }
}
