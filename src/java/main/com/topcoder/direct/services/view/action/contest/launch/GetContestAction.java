/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.topcoder.direct.services.exception.DirectException;
import com.topcoder.direct.services.view.dto.CoPilotStatsDTO;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.contest.ContestBriefDTO;
import com.topcoder.direct.services.view.dto.contest.ContestDTO;
import com.topcoder.direct.services.view.dto.contest.ContestDetailsDTO;
import com.topcoder.direct.services.view.dto.contest.ContestStatsDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.service.project.StudioCompetition;
import com.topcoder.shared.dataAccess.DataAccess;
import com.topcoder.shared.dataAccess.Request;
import com.topcoder.shared.dataAccess.resultSet.ResultSetContainer;
import com.topcoder.shared.util.DBMS;
import com.topcoder.web.common.CachedDataAccess;
import com.topcoder.web.common.cache.MaxAge;

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
 * Version 1.1 - Direct - View/Edit/Activate Studio Contests Assembly Change Note
 * <ul>
 * <li>Adds the legacy code to show other parts of the contest detail page.</li>
 * <li>Preserves the studio competition to fill the details later.</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.2 - View/Edit/Activate Software Contests v1.0 Assembly Change Note
 * <ul>
 * <li>Preserves the software competition to fill the details later.</li>
 * </ul>
 * </p>
 *
 * @author fabrizyo, FireIce, TCSDEVELOPER
 * @version 1.2
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
     * <code>softwareCompetition</code> to hold the software competition.
     * </p>
     */
    private SoftwareCompetition softwareCompetition;

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
    protected void executeAction() throws Exception {
        ContestServiceFacade contestServiceFacade = getContestServiceFacade();

        if (null == contestServiceFacade) {
            throw new IllegalStateException("The contest service facade is not initialized.");
        }

        if (contestId <= 0 && projectId <= 0) {
            throw new DirectException("contestId and projectId both less than 0 or not defined.");
        }

        if (contestId > 0) {
            studioCompetition = contestServiceFacade.getContest(DirectStrutsActionsHelper.getTCSubjectFromSession(),
                contestId);
            setResult(studioCompetition);
        } else {
            softwareCompetition = contestServiceFacade.getSoftwareContestByProjectId(DirectStrutsActionsHelper
                .getTCSubjectFromSession(), projectId);
            setResult(softwareCompetition);
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
     * Determines if it is software contest or not.
     * </p>
     *
     * @return true if it is software contest
     */
    public boolean isSoftware() {
        return (softwareCompetition != null);
    }

    /**
     * <p>
     * Gets the view data.
     * </p>
     *
     * @return the view data
     * @throws Exception if any error occurs
     */
    public ContestDetailsDTO getViewData() throws Exception {
        if (viewData == null) {
            viewData = new ContestDetailsDTO();

            // real data
            ContestStatsDTO contestStats = new ContestStatsDTO();
            ContestBriefDTO contest = new ContestBriefDTO();
            if (studioCompetition != null) {
                contest.setId(studioCompetition.getContestData().getContestId());
                contest.setTitle(studioCompetition.getContestData().getName());
            }
            if (softwareCompetition != null) {
                contest.setId(softwareCompetition.getProjectHeader().getId());
                contest.setTitle(softwareCompetition.getProjectHeader().getProperty("Project Name"));
            }
            contestStats.setContest(contest);
            fillContestStats(contestStats);
            viewData.setContestStats(contestStats);

            final long testContestId = 4;
            ContestDTO contestDTO = DataProvider.getContest(testContestId);
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

    private void fillContestStats(ContestStatsDTO contestStats) throws Exception {
        // TODO: fill software stats
        if (studioCompetition == null) {
            return;
        }
        DataAccess dao = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("direct_studio_contest_stats");
        request.setProperty("ct", String.valueOf(studioCompetition.getContestData().getContestId()));

        ResultSetContainer result = dao.getData(request).get("direct_studio_contest_stats");
        contestStats.setRegistrantsNumber(result.getIntItem(0, "number_of_registration"));
        contestStats.setSubmissionsNumber(result.getIntItem(0, "number_of_submission"));
        contestStats.setForumPostsNumber(result.getIntItem(0, "number_of_forum"));
        contestStats.setForumId(studioCompetition.getContestData().getForumId());
    }

    /**
     * <p>
     * Gets the session data.
     * </p>
     *
     * @return the session data
     * @throws Exception if any error occurs
     */
    public SessionData getSessionData() throws Exception {
        if (sessionData == null) {
            HttpServletRequest request = ServletActionContext.getRequest();

            HttpSession session = request.getSession(false);
            if (session != null) {
                sessionData = new SessionData(session);
                ProjectBriefDTO project = new ProjectBriefDTO();
                if (studioCompetition != null) {
                    project.setId(studioCompetition.getContestData().getContestId());
                    project.setName(studioCompetition.getContestData().getTcDirectProjectName());
                }
                if (softwareCompetition != null) {
                    project.setId(softwareCompetition.getProjectHeader().getId());
                    project.setName(getProjectName(softwareCompetition.getProjectHeader().getTcDirectProjectId()));

                }
                sessionData.setCurrentProjectContext(project);
            }
        }
        return sessionData;
    }

    /**
     * <p>
     * Gets project name. NOTE: it is fixing some bug which software competition project header is missing project
     * name population.
     * </p>
     *
     * @param projectId client project id
     * @return the project name. It could be null if no match is found.
     * @throws Exception if any error occurs
     */
    private String getProjectName(long projectId) throws Exception {
        try {
            for (ProjectData project : getProjects()) {
                if (projectId == project.getProjectId()) {
                    return project.getName();
                }
            }

            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
