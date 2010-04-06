/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.util;

import com.topcoder.direct.services.view.dto.CoPilotStatsDTO;
import com.topcoder.direct.services.view.dto.LatestActivitiesDTO;
import com.topcoder.direct.services.view.dto.contest.ContestDTO;
import com.topcoder.direct.services.view.dto.contest.ContestRegistrantDTO;
import com.topcoder.direct.services.view.dto.contest.ContestStatsDTO;
import com.topcoder.direct.services.view.dto.contest.ContestType;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.dto.project.LatestProjectActivitiesDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.dto.TopCoderDirectFactsDTO;
import com.topcoder.direct.services.view.dto.UpcomingActivitiesDTO;
import com.topcoder.direct.services.view.dto.dashboard.DashboardContestSearchResultDTO;
import com.topcoder.direct.services.view.dto.dashboard.DashboardMemberSearchResultDTO;
import com.topcoder.direct.services.view.dto.dashboard.DashboardProjectSearchResultDTO;
import com.topcoder.direct.services.view.dto.project.ProjectContestDTO;
import com.topcoder.direct.services.view.dto.project.ProjectContestsListDTO;
import com.topcoder.direct.services.view.dto.project.ProjectStatsDTO;
import com.topcoder.shared.dataAccess.Request;
import com.topcoder.shared.dataAccess.resultSet.ResultSetContainer;
import com.topcoder.shared.util.DBMS;
import com.topcoder.web.common.CachedDataAccess;
import com.topcoder.web.common.cache.MaxAge;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>An utility class providing the methods for getting various data from persistent data store. Such a data is usually
 * obtained using pre-defined queries from <code>Query Tool</code> and this utility class provides the methods which
 * translate into calls to appropriate queries from <code>Query Tool</code>.</p>
 *
 * <p>Sub-sequent assemblies may expand this class with additional methods for calling other queries when there is a
 * need or they can fully re-work the concept of data provider.</p>
 *
 * @author isv
 * @version 1.0
 */
public class DataProvider {

    /**
     * <p>Constructs new <code>DataProvider</code> instance. This implementation does nothing.</p>
     */
    private DataProvider() {
    }

    /**
     * <p>Gets the current number of registered <code>TopCoder</code> members.</p>
     *
     * @return an <code>int</code> providing the current number of registered <code>TopCoder</code> members.  
     * @throws Exception if an unexpected error occurs while communicating to persistent data store.
     */
    public static int getMemberCount() throws Exception {
        CachedDataAccess countDai = new CachedDataAccess(MaxAge.QUARTER_HOUR, DBMS.DW_DATASOURCE_NAME);
        Request countReq = new Request();
        countReq.setContentHandle("member_count");
        return countDai.getData(countReq).get("member_count").getIntItem(0, "member_count");
    }

    /**
     * <p>Gets the statistics on available co-piloting projects and co-pilots.</p>
     *
     * @return a <code>CoPilotStatsDTO</code> providing the statistics on available co-piloting projects and available
     *         co-pilots.
     * @throws Exception if an unexpected error occurs while communicating to persistent data store.
     */
    public static CoPilotStatsDTO getCopilotStats() throws Exception {
        CachedDataAccess countDai = new CachedDataAccess(MaxAge.QUARTER_HOUR, DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request countReq = new Request();
        countReq.setContentHandle("copilot_stats");

        ResultSetContainer coPilotStatsResult = countDai.getData(countReq).get("copilot_stats");
        CoPilotStatsDTO result = new CoPilotStatsDTO();
        result.setAvailableCopilots(coPilotStatsResult.getIntItem(0, "available_copilots_count"));
        result.setAvailableCopilotProjects(coPilotStatsResult.getIntItem(0, "available_copilot_projects_count"));
        return result;
    }

    /**
     * <p>Gets the details on <code>TopCoder Direct</code> facts.</p>
     *
     * @return a <code>TopCoderDirectFactsDTO</code> providing the details on <code>TopCoder Direct</code> facts.
     * @throws Exception if an unexpected error occurs while communicating to persistent data store.
     */
    public static TopCoderDirectFactsDTO getTopCoderDirectFacts() throws Exception {
        CachedDataAccess countDai = new CachedDataAccess(MaxAge.QUARTER_HOUR, DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request countReq = new Request();
        countReq.setContentHandle("tc_direct_facts");

        ResultSetContainer tcDirectFactsResult = countDai.getData(countReq).get("tc_direct_facts");
        TopCoderDirectFactsDTO result = new TopCoderDirectFactsDTO();
        result.setActiveContestsNumber(tcDirectFactsResult.getIntItem(0, "active_contests_count"));
        result.setActiveMembersNumber(tcDirectFactsResult.getIntItem(0, "active_members_count"));
        result.setActiveProjectsNumber(tcDirectFactsResult.getIntItem(0, "active_projects_count"));
        result.setCompletedProjectsNumber(tcDirectFactsResult.getIntItem(0, "completed_projects_count"));
        result.setPrizePurse(tcDirectFactsResult.getDoubleItem(0, "prize_purse"));
        return result;
    }

    /**
     * <p>Gets the details on latest activities on projects associated with specified user.</p>
     *
     * <p>Sub-sequent assemblies must implement this method to use the appropriate logic for getting the details on
     * latest activities. Current implementation uses mock data.</p>
     *
     * @param userId a <code>long</code> providing the user to get the latest activities on associated projects for.
     * @return an <code>LatestActivitiesDTO</code> providing the details on latest activities on projects associated
     *         with the specified user.
     * @throws Exception if an unexpected error occurs while communicating to persistent data store.
     */
    public static LatestActivitiesDTO getLatestActivitiesForUserProjects(long userId) throws Exception {
        LatestActivitiesDTO result = new LatestActivitiesDTO();
        result.setActivities(MockData.getLatestProjectActivities(userId));
        return result;
    }

    /**
     * <p>Gets the details on upcoming activities on projects associated with specified user.</p>
     *
     * <p>Sub-sequent assemblies must implement this method to use the appropriate logic for getting the details on
     * upcoming activities. Current implementation uses mock data.</p>
     *
     * @param userId a <code>long</code> providing the user to get the upcoming activities on associated projects for.
     * @return an <code>UpcomingActivitiesDTO</code> providing the details on upcoming activities on projects associated
     *         with the specified user.
     * @throws Exception if an unexpected error occurs while communicating to persistent data store.
     */
    public static UpcomingActivitiesDTO getUpcomingActivitiesForUserProjects(long userId) throws Exception {
        UpcomingActivitiesDTO result = new UpcomingActivitiesDTO();
        result.setActivities(MockData.getUpcomingActivities(userId));
        return result;
    }

    /**
     * <p>Creates new project with specified properties. The project will be associated with the specified user.</p>
     *
     * <p>Sub-sequent assemblies must implement this method to use the appropriate logic for creating projects. Current
     * implementation uses mock data.</p>
     *
     * @param userId a <code>long</code> providing the user ID.
     * @param projectName a <code>String</code> providing the project name.
     * @param projectDescription a <code>String</code> providing the project description.
     */
    public static void createProject(long userId, String projectName, String projectDescription) {
        MockData.createProject(userId, projectName, projectDescription);
    }

    /**
     * <p>Gets the details on projects associated with specified user and matching the specified criteria.</p>
     *
     * <p>Sub-sequent assemblies must implement this method to use the appropriate logic for getting the matching
     * records. Current implementation uses mock data.</p>
     *
     * @param userId a <code>long</code> providing the user to get the list of matching projects for.
     * @return a <code>List</code> providing the details on projects associated with the specified user.
     */
    public static List<DashboardProjectSearchResultDTO> searchUserProjects(long userId, String searchFor) {
        return MockData.searchUserProjects(userId, searchFor);
    }

    /**
     * <p>Gets the details on contests associated with specified user and matching the specified criteria.</p>
     *
     * <p>Sub-sequent assemblies must implement this method to use the appropriate logic for getting the matching
     * records. Current implementation uses mock data.</p>
     *
     * @param userId a <code>long</code> providing the user to get the list of matching contests for.
     * @return a <code>List</code> providing the details on contests associated with the specified user.
     */
    public static List<DashboardContestSearchResultDTO> searchUserContests(long userId, String searchFor) {
        return MockData.searchUserContests(userId, searchFor);
    }

    /**
     * <p>Gets the details on users assigned to projects associated with specified user and matching the specified
     * criteria.</p>
     *
     * <p>Sub-sequent assemblies must implement this method to use the appropriate logic for getting the matching
     * records. Current implementation uses mock data.</p>
     *
     * @param userId a <code>long</code> providing the user to get the list of matching users for.
     * @return a <code>List</code> providing the details on users assigned to projects associated with the specified
     *         user.
     */
    public static List<DashboardMemberSearchResultDTO> searchUserProjectMembers(long userId, String searchFor) {
        return MockData.searchUserProjectMembers(userId, searchFor);
    }

    /**
     * <p>Gets the list of projects associated with specified user.</p>
     *
     * @param userId a <code>long</code> providing the user ID.
     * @return a <code>List</code> listing the details for user projects.
     */
    public static List<ProjectBriefDTO> getUserProjects(long userId) {
        return MockData.getUserProjects(userId);
    }

    /**
     * <p>Gets the stats on specified project.</p>
     *
     * @param projectId a <code>long</code> providing the ID for requested project.
     * @return a <code>ProjectStatsDTO</code> providing the stats for requested project.
     */
    public static ProjectStatsDTO getProjectStats(long projectId) {
        ProjectStatsDTO stats = new ProjectStatsDTO();
        ProjectBriefDTO project = MockData.getProject(projectId);
        stats.setDraftContestsNumber(1 + (int) projectId);
        stats.setFeesFinalized(10000 + projectId);
        stats.setFeesRunning(4563 + projectId);
        stats.setFinishedContestsNumber(2 + (int) projectId);
        stats.setPipelineContestsNumber(3 + (int) projectId);
        stats.setProject(project);
        stats.setRunningContestsNumber(4 + (int) projectId);
        stats.setTaskedContestsNumber(5 + (int) projectId);
        stats.setStartDate(new Date(System.currentTimeMillis() + 24 * projectId * 3600 * 1000L));

        return stats;
    }

    /**
     * <p>Gets the details on latest activities on contests associated with specified project.</p>
     *
     * <p>Sub-sequent assemblies must implement this method to use the appropriate logic for getting the details on
     * latest activities. Current implementation uses mock data.</p>
     *
     * @param projectId a <code>long</code> providing the ID for project to get the latest activities on associated
     *        contests for.
     * @return an <code>LatestProjectActivitiesDTO</code> providing the details on latest activities on contests
     *         associated with the specified project.
     */
    public static LatestProjectActivitiesDTO getLatestActivitiesForProject(long projectId) {
        LatestProjectActivitiesDTO dto = new LatestProjectActivitiesDTO();
        dto.setActivities(MockData.getLatestProjectContestActivities(projectId));
        return dto;
    }

    /**
     * <p>Gets the details on contests associated with the specified project.</p>
     *
     * <p>Sub-sequent assemblies must implement this method to use the appropriate logic for getting the details on
     * project. Current implementation uses mock data.</p>
     *
     * @param projectId a <code>long</code> providing the ID for project to get the details for associated contests for.
     * @return a <code>ProjectContestsListDTO</code> providing the details on contests associated with specified
     *         project.
     */
    public static ProjectContestsListDTO getProjectContests(long projectId) {
        ProjectContestsListDTO dto = new ProjectContestsListDTO();
        dto.setContests(MockData.getProjectContests(projectId));
        return dto;
    }

    /**
     * <p>Gets the details on contests associated with the specified project.</p>
     *
     * <p>Sub-sequent assemblies must implement this method to use the appropriate logic for getting the details on
     * project. Current implementation uses mock data.</p>
     *
     * @param projectId a <code>long</code> providing the ID for project to get the details for associated contests for.
     * @return a <code>ProjectContestsListDTO</code> providing the details on contests associated with specified
     *         project.
     */
    public static List<TypedContestBriefDTO> getProjectTypedContests(long projectId) {
        List<TypedContestBriefDTO> data = new ArrayList<TypedContestBriefDTO>();
        List<ProjectContestDTO> list = MockData.getProjectContests(projectId);
        for (ProjectContestDTO p : list) {
            TypedContestBriefDTO dto = new TypedContestBriefDTO();
            dto.setContestType(p.getContestType());
            dto.setId(p.getContest().getId());
            dto.setProject(p.getContest().getProject());
            dto.setTitle(p.getContest().getTitle());
            dto.setStatus(p.getStatus());
            data.add(dto);
        }

        return data;
    }

    /**
     * <p>Gets the statistics on specified contest.</p>
     * 
     * @param contestId a <code>long</code> providing the ID of a contest.
     * @return a <code>ContestStatsDTO</code> providing the statistics on contest.
     */
    public static ContestStatsDTO getContestStats(long contestId) {
        ContestStatsDTO dto = new ContestStatsDTO();
        dto.setEndTime(new Date(System.currentTimeMillis() + 24 * contestId * 3600 * 1000L));
        dto.setStartTime(new Date());
        dto.setSubmissionsNumber((int) contestId + 1);
        dto.setRegistrantsNumber((int) contestId + 7);
        dto.setForumPostsNumber((int) contestId + 17);
        dto.setContest(MockData.getContest(contestId));
        return dto;
    }

    /**
     * <p>Gets the details on specified contest.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest.
     * @return a <code>ContestStatsDTO</code> providing the details on contest.
     */
    public static ContestDTO getContest(long contestId) {
        return MockData.getContest(contestId);
    }

    /**
     * <p>Gets the list of registrants to specified contest.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest.
     * @return a <code>List</code> providing the details for contest registrants.
     */
    public static List<ContestRegistrantDTO> getContestRegistrants(long contestId) {
        return MockData.getContestRegistrants(contestId);
    }
}
