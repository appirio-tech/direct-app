/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.validator.annotations.ExpressionValidator;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.contest.ContestBriefDTO;
import com.topcoder.direct.services.view.dto.contest.ContestDTO;
import com.topcoder.direct.services.view.dto.contest.ContestDetailsDTO;
import com.topcoder.direct.services.view.dto.contest.ContestStatsDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.project.StudioCompetition;

/**
 * <p>
 * This action will get a contest with the given id. This action requires <code>projectId</code> or
 * <code>contestId</code> parameter (mutual exclusive) be present for each call.
 * </p>
 * <p>
 * <b>Thread Safety</b>: In <b>Struts 2</b> framework, the action is constructed for every request so the thread
 * safety is not required (instead in Struts 1 the thread safety is required because the action instances are reused).
 * This class is mutable and stateful: it's not thread safe.
 * </p>
 * <p>
 * Version 1.1 - Direct - View/Edit/Activate Studio Contests Assembly Change Note - Adds the legacy code to show other
 * parts of the contest detail page - Preserves the studio competition to fill the details later
 * </p>
 *
 * @author fabrizyo, FireIce, TCSDEVELOPER
 * @version 1.1
 */
public class GetContestAction extends ContestAction {
    /**
     * <p>
     * Represents the unique serial version id.
     * </p>
     */
    private static final long serialVersionUID = 7980735050638514625L;

    /**
     * <p>
     * This is the id of project of contest.
     * </p>
     * <p>
     * It's used to retrieve the software competition. It can be 0 (it means not present) or greater than 0 if it's
     * present. It's changed by the setter and returned by the getter.
     * </p>
     */
    private long projectId;

    /**
     * <p>
     * This is the id of contest.
     * </p>
     * <p>
     * It's used to retrieve the studio competition. It can be 0 (it means not present) or greater than 0 if it's
     * present. It's changed by the setter and returned by the getter.
     * </p>
     */
    private long contestId;

    /**
     * <p>
     * view data. It is copied from old details page to preserve some portion of the existing page.
     * </p>
     */
    private ContestDetailsDTO viewData;

    /**
     * <p>
     * Session data. It is copied from old details page to preserve some portion of the existing page.
     * </p>
     */
    private SessionData sessionData;

    /**
     * <p>
     * Preserve the retrieved contest.
     * </p>
     */
    private StudioCompetition studioCompetition;

    /**
     * <p>
     * Creates a <code>GetContestAction</code> instance.
     * </p>
     */
    public GetContestAction() {
    }

    /**
     * <p>
     * Executes the action.
     * </p>
     * <p>
     * The returned software or studio contest will be available as result.
     * </p>
     *
     * @throws IllegalStateException if the contest service facade is not set.
     * @throws Exception if any other error occurs
     * @see ContestServiceFacade#getContest(com.topcoder.security.TCSubject, long)
     * @see ContestServiceFacade#getSoftwareContestByProjectId(com.topcoder.security.TCSubject, long)
     */
    @ExpressionValidator(message = "Only one of projectId and contestId should be set", key = "i18n.GetContestAction.projectIdOrContestIdRequiredSet", expression = "(projectId == 0 && contestId >= 1) || (projectId >= 1 && contestId == 0)")
    protected void executeAction() throws Exception {
        ContestServiceFacade contestServiceFacade = getContestServiceFacade();

        if (null == contestServiceFacade) {
            throw new IllegalStateException("The contest service facade is not initialized.");
        }

        if (contestId > 0) {
            studioCompetition = contestServiceFacade.getContest(DirectStrutsActionsHelper.getTCSubjectFromSession(),
                contestId);
            setResult(studioCompetition);
        } else {
            setResult(contestServiceFacade.getSoftwareContestByProjectId(DirectStrutsActionsHelper
                .getTCSubjectFromSession(), projectId));
        }
    }

    /**
     * <p>
     * Gets the project id.
     * </p>
     *
     * @return the project id
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * <p>
     * Sets the project id.
     * </p>
     * <p>
     * Don't perform argument checking by the usual exception.
     * </p>
     *
     * @param projectId the project id to set
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * <p>
     * Gets the contest id.
     * </p>
     *
     * @return the contest id
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * <p>
     * Sets the contest id.
     * </p>
     * <p>
     * Don't perform argument checking by the usual exception.
     * </p>
     *
     * @param contestId the contest id to set
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * <p>
     * Gets the view data.
     * </p>
     *
     * @return the view data
     * @throws Exception
     */
    public ContestDetailsDTO getViewData() throws Exception {
        if (viewData == null) {
            viewData = new ContestDetailsDTO();

            ContestStatsDTO contestStats = new ContestStatsDTO();
            ContestBriefDTO contest = new ContestBriefDTO();
            contest.setId(studioCompetition.getContestData().getContestId());
            contest.setTitle(studioCompetition.getContestData().getName());
            contestStats.setContest(contest);
            viewData.setContestStats(contestStats);

            ContestDTO contestDTO = DataProvider.getContest(4);
            viewData.setContest(contestDTO);

            // project

            // right side
            List<ProjectBriefDTO> projects = DataProvider.getUserProjects(getSessionData().getCurrentUserId());

            UserProjectsDTO userProjectsDTO = new UserProjectsDTO();
            userProjectsDTO.setProjects(projects);
            viewData.setUserProjects(userProjectsDTO);
        }
        return viewData;
    }

    /**
     * <p>
     * Gets the session data.
     * </p>
     *
     * @return the session data
     */
    public SessionData getSessionData() {
        if (sessionData == null) {
            HttpServletRequest request = ServletActionContext.getRequest();

            HttpSession session = request.getSession(false);
            if (session != null) {
                sessionData = new SessionData(session);
                if (studioCompetition != null) {
                    ProjectBriefDTO project = new ProjectBriefDTO();
                    project.setId(studioCompetition.getContestData().getTcDirectProjectId());
                    project.setName(studioCompetition.getContestData().getTcDirectProjectName());
                    sessionData.setCurrentProjectContext(project);
                }
            }
        }
        return sessionData;
    }
}
