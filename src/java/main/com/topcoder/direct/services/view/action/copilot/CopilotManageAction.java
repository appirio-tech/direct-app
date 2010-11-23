/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.copilot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.topcoder.direct.services.copilot.dao.CopilotProfileDAO;
import com.topcoder.direct.services.copilot.dao.CopilotProjectDAO;
import com.topcoder.direct.services.copilot.model.CopilotProfile;
import com.topcoder.direct.services.copilot.model.CopilotProject;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.contest.ContestBriefDTO;
import com.topcoder.direct.services.view.dto.copilot.CopilotBriefDTO;
import com.topcoder.direct.services.view.dto.copilot.CopilotContestDTO;
import com.topcoder.direct.services.view.dto.copilot.CopilotManageDTO;
import com.topcoder.direct.services.view.dto.copilot.CopilotProjectDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.dto.project.ProjectContestDTO;
import com.topcoder.direct.services.view.dto.project.ProjectContestsListDTO;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.management.resource.Resource;
import com.topcoder.security.TCSubject;
import com.topcoder.service.project.SoftwareCompetition;

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
     * The copilot project DAO.
     */
    private CopilotProjectDAO copilotProjectDAO;

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
     * Retrieves the copilotProjectDAO field.
     * 
     * @return the copilotProjectDAO
     */
    public CopilotProjectDAO getCopilotProjectDAO() {
        return copilotProjectDAO;
    }

    /**
     * Sets the copilotProjectDAO field.
     * 
     * @param copilotProjectDAO
     *            the copilotProjectDAO to set
     */
    public void setCopilotProjectDAO(CopilotProjectDAO copilotProjectDAO) {
        this.copilotProjectDAO = copilotProjectDAO;
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
        Map<Long, CopilotProjectDTO> copilotProjectMap = new HashMap<Long, CopilotProjectDTO>();

        List<ProjectBriefDTO> projects = DataProvider
                .getUserProjects(currentUser.getUserId());

        // set user projects
        UserProjectsDTO userProjectsDTO = new UserProjectsDTO();
        userProjectsDTO.setProjects(projects);
        viewData.setUserProjects(userProjectsDTO);

        for (ProjectBriefDTO projectBriefDTO : projects) {
            CopilotProjectDTO copilotProjectDTO = new CopilotProjectDTO();

            // set project
            copilotProjectDTO.setProject(projectBriefDTO);

            // set project contests
            List<CopilotContestDTO> contests = new ArrayList<CopilotContestDTO>();
            ProjectContestsListDTO contestsListDTO = DataProvider
                    .getProjectContests(currentUser.getUserId(),
                            projectBriefDTO.getId());
            for (ProjectContestDTO projectContest : contestsListDTO
                    .getContests()) {
                CopilotContestDTO contest = new CopilotContestDTO();
                
                contest.setContest(projectContest.getContest());
                contest.setCopilots(new ArrayList<String>());
                
                // retrieve copilots
                if (!projectContest.getIsStudio()) {
                    SoftwareCompetition softwareCompetition = getContestServiceFacade().getSoftwareContestByProjectId(currentUser, projectContest.getContest().getId());
                    
                    for (Resource resource : softwareCompetition.getResources()) {
                        if (resource.getResourceRole().getId() == 14L) {
                            if (resource.getProperty("Handle") != null) {
                                contest.getCopilots().add(resource.getProperty("Handle"));
                            }
                        }
                    }
                }
                
                contests.add(contest);
            }
            copilotProjectDTO.setContests(contests);

            // initiate copilots
            copilotProjectDTO.setCopilots(new ArrayList<CopilotBriefDTO>());

            // add to copilot project map
            copilotProjectMap.put(projectBriefDTO.getId(), copilotProjectDTO);
        }

        // retrieve copilot projects
        List<CopilotProject> copilotProjects = copilotProjectDAO.retrieveAll();

        // set copilot profile to copilot project
        for (CopilotProject copilotProject : copilotProjects) {
            if (copilotProjectMap.containsKey(copilotProject
                    .getTcDirectProjectId())) {
                // associate with user project
                CopilotBriefDTO copilotBriefDTO = new CopilotBriefDTO();
                copilotBriefDTO.setCopilotProfileId(copilotProject
                        .getCopilotProfileId());

                CopilotProfile copilotProfile = copilotProfileDAO
                        .retrieve(copilotProject.getCopilotProfileId());
                copilotBriefDTO.setHandle(getUserService().getUserHandle(
                        copilotProfile.getUserId()));
                copilotBriefDTO.setCopilotProjectId(copilotProject.getId());
                copilotBriefDTO.setCopilotType(copilotProject.getCopilotType().getName());

                copilotProjectMap.get(copilotProject.getTcDirectProjectId())
                        .getCopilots().add(copilotBriefDTO);
            }
        }

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
        viewData.setCopilotProjects(copilotProjectMap);
        viewData.setCopilots(copilots);
    }

}
