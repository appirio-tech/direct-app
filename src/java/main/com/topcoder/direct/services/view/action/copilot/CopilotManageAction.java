/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.copilot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.topcoder.direct.services.copilot.dao.CopilotProfileDAO;
import com.topcoder.direct.services.copilot.model.CopilotProfile;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.copilot.CopilotBriefDTO;
import com.topcoder.direct.services.view.dto.copilot.CopilotManageDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.security.TCSubject;

/**
 * <p>
 * A <code>Struts</code> action used for handling access copilot manage page
 * request.
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0
 * @since TC Direct Manage Copilots Assembly
 */
public class CopilotManageAction extends BaseDirectStrutsAction {
    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = 228684015789715658L;

    /**
     * <p>
     * A <code>SessionData</code> providing interface to current session.
     * </p>
     */
    private SessionData sessionData;

    /**
     * <p>
     * A <code>CopilotManageDTO</code> providing the view data for displaying by
     * <code>Copilot Manage</code> view.
     * </p>
     */
    private CopilotManageDTO viewData;

    /**
     * The copilot profile DAO.
     */
    private CopilotProfileDAO copilotProfileDAO;

    /**
     * Retrieves the sessionData field.
     * 
     * @return the sessionData
     */
    public SessionData getSessionData() {
        return sessionData;
    }

    /**
     * Sets the sessionData field.
     * 
     * @param sessionData
     *            the sessionData to set
     */
    public void setSessionData(SessionData sessionData) {
        this.sessionData = sessionData;
    }

    /**
     * Retrieves the viewData field.
     * 
     * @return the viewData
     */
    public CopilotManageDTO getViewData() {
        return viewData;
    }

    /**
     * Sets the viewData field.
     * 
     * @param viewData
     *            the viewData to set
     */
    public void setViewData(CopilotManageDTO viewData) {
        this.viewData = viewData;
    }

    /**
     * Retrieves the copilotProfileDAO field.
     * 
     * @return the copilotProfileDAO
     */
    public CopilotProfileDAO getCopilotProfileDAO() {
        return copilotProfileDAO;
    }

    /**
     * Sets the copilotProfileDAO field.
     * 
     * @param copilotProfileDAO
     *            the copilotProfileDAO to set
     */
    public void setCopilotProfileDAO(CopilotProfileDAO copilotProfileDAO) {
        this.copilotProfileDAO = copilotProfileDAO;
    }

    /**
     * <p>
     * Handles the incoming request. Retrieve user projects and related copilot
     * information.
     * </p>
     * 
     * @throws Exception
     *             if an unexpected error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        // get current session
        HttpServletRequest request = DirectUtils.getServletRequest();
        this.sessionData = new SessionData(request.getSession());

        // get current user
        TCSubject currentUser = getCurrentUser();

        // populate copilot values
        viewData = new CopilotManageDTO();

        // set user projects
        List<ProjectBriefDTO> projects = DataProvider
                .getUserProjects(currentUser.getUserId());
        UserProjectsDTO userProjectsDTO = new UserProjectsDTO();
        userProjectsDTO.setProjects(projects);
        viewData.setUserProjects(userProjectsDTO);

        // set all copilots
        List<CopilotBriefDTO> copilots = new ArrayList<CopilotBriefDTO>();

        List<CopilotProfile> copilotProfiles = copilotProfileDAO.retrieveAll();
        for (CopilotProfile copilotProfile : copilotProfiles) {
            CopilotBriefDTO copilot = new CopilotBriefDTO();

            copilot.setCopilotProfileId(copilotProfile.getId());
            copilot.setHandle(getUserService().getUserHandle(
                    copilotProfile.getUserId()));
            copilots.add(copilot);

        }

        // set view data
        viewData.setCopilotProjects(DataProvider.getCopilotProjects(currentUser
                .getUserId()));
        viewData.setCopilots(copilots);
        
        Collections.sort(viewData.getCopilots());
        Collections.sort(viewData.getCopilotProjects());
    }

}
