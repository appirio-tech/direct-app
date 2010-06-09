/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.topcoder.service.studio.PersistenceException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;

import com.topcoder.direct.services.view.dto.ActivityDTO;
import com.topcoder.direct.services.view.dto.ActivityType;
import com.topcoder.direct.services.view.dto.CoPilotStatsDTO;
import com.topcoder.direct.services.view.dto.LatestActivitiesDTO;
import com.topcoder.direct.services.view.dto.TopCoderDirectFactsDTO;
import com.topcoder.direct.services.view.dto.UpcomingActivitiesDTO;
import com.topcoder.direct.services.view.dto.contest.ContestBriefDTO;
import com.topcoder.direct.services.view.dto.contest.ContestDTO;
import com.topcoder.direct.services.view.dto.contest.ContestRegistrantDTO;
import com.topcoder.direct.services.view.dto.contest.ContestStatsDTO;
import com.topcoder.direct.services.view.dto.contest.ContestStatus;
import com.topcoder.direct.services.view.dto.contest.ContestType;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.dto.dashboard.DashboardContestSearchResultDTO;
import com.topcoder.direct.services.view.dto.dashboard.DashboardMemberSearchResultDTO;
import com.topcoder.direct.services.view.dto.dashboard.DashboardProjectSearchResultDTO;
import com.topcoder.direct.services.view.dto.project.LatestProjectActivitiesDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.dto.project.ProjectContestDTO;
import com.topcoder.direct.services.view.dto.project.ProjectContestsListDTO;
import com.topcoder.direct.services.view.dto.project.ProjectStatsDTO;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.CommonProjectContestData;
import com.topcoder.service.facade.contest.ProjectSummaryData;
import com.topcoder.shared.dataAccess.DataAccess;
import com.topcoder.shared.dataAccess.Request;
import com.topcoder.shared.dataAccess.resultSet.ResultSetContainer;
import com.topcoder.shared.util.DBMS;
import com.topcoder.web.common.CachedDataAccess;
import com.topcoder.web.common.cache.MaxAge;

/**
 * <p>An utility class providing the methods for getting various data from persistent data store. Such a data is usually
 * obtained using pre-defined queries from <code>Query Tool</code> and this utility class provides the methods which
 * translate into calls to appropriate queries from <code>Query Tool</code>.</p>
 *
 * <p>Sub-sequent assemblies may expand this class with additional methods for calling other queries when there is a
 * need or they can fully re-work the concept of data provider.</p>
 *
 * <p>
 * Version 2.0 - Direct Search Assembly - add search and filtering for searchUserProjects,searchUserContests
 * </p>
 *
 * @author isv, BeBetter
 * @version 2.0
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
     * @param days an <code>int</code> providing the number of days from current time for selecting activities. 
     * @return an <code>LatestActivitiesDTO</code> providing the details on latest activities on projects associated
     *         with the specified user.
     * @throws Exception if an unexpected error occurs while communicating to persistent data store.
     */
    public static LatestActivitiesDTO getLatestActivitiesForUserProjects(long userId, int days) throws Exception {
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("direct_latest_activities");
        request.setProperty("uid", String.valueOf(userId));
        request.setProperty("days", String.valueOf(days));

        final Map<ProjectBriefDTO, List<ActivityDTO>> activities = new HashMap<ProjectBriefDTO, List<ActivityDTO>>();
        final Map<Long, ProjectBriefDTO> projects = new HashMap<Long, ProjectBriefDTO>();
        final Map<Long, ContestBriefDTO> contests = new HashMap<Long, ContestBriefDTO>();

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("direct_latest_activities");
        final int recordNum = resultContainer.size();
        for (int i = 0; i < recordNum; i++) {
            String activityTypeText = resultContainer.getStringItem(i, "activity_type");
            long tcDirectProjectId = resultContainer.getLongItem(i, "tc_direct_project_id");
            String tcDirectProjectName = resultContainer.getStringItem(i, "tc_direct_project_name");
            long contestId = resultContainer.getLongItem(i, "contest_id");
            String contestName = resultContainer.getStringItem(i, "contest_name");
            // String contestType = resultContainer.getStringItem(i, "contest_type");

            // System.out.println("#############contestType:"+contestType);

            long originatorId = Long.parseLong(resultContainer.getStringItem(i, "user_id"));
            String originatorHandle = resultContainer.getStringItem(i, "user");
            Timestamp date = resultContainer.getTimestampItem(i, "activity_time");

            final ProjectBriefDTO project;
            final List<ActivityDTO> projectActivities;
            if (!projects.containsKey(tcDirectProjectId)) {
                projectActivities = new ArrayList<ActivityDTO>();
                project = createProject(tcDirectProjectId, tcDirectProjectName);
                projects.put(tcDirectProjectId, project);
                activities.put(project, projectActivities);
            } else {
                project = projects.get(tcDirectProjectId);
                projectActivities = activities.get(project);
            }

            final ContestBriefDTO contest;
            if (contests.containsKey(contestId)) {
                contest = contests.get(contestId);
            } else {
                contest = createContest(contestId, contestName, project);
                contests.put(contestId, contest);
            }

            ActivityType activityType = ActivityType.forName(activityTypeText);
            ActivityDTO activity = createActivity(contest, date, originatorHandle, originatorId, activityType);
            projectActivities.add(activity);
        }

        LatestActivitiesDTO result = new LatestActivitiesDTO();
        result.setActivities(activities);
        return result;
    }

    /**
     * <p>Gets the details on upcoming activities on projects associated with specified user.</p>
     *
     * <p>Sub-sequent assemblies must implement this method to use the appropriate logic for getting the details on
     * upcoming activities. Current implementation uses mock data.</p>
     *
     * @param userId a <code>long</code> providing the user to get the upcoming activities on associated projects for.
     * @param days an <code>int</code> providing the number of days from current time for selecting activities.
     * @return an <code>UpcomingActivitiesDTO</code> providing the details on upcoming activities on projects associated
     *         with the specified user.
     * @throws Exception if an unexpected error occurs while communicating to persistent data store.
     */
    public static UpcomingActivitiesDTO getUpcomingActivitiesForUserProjects(long userId, int days) throws Exception {
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("direct_upcoming_activities");
        request.setProperty("uid", String.valueOf(userId));
        request.setProperty("days", String.valueOf(days));

        final Map<Long, ProjectBriefDTO> projects = new HashMap<Long, ProjectBriefDTO>();
        final Map<Long, ContestBriefDTO> contests = new HashMap<Long, ContestBriefDTO>();
        final List<ActivityDTO> activities = new ArrayList<ActivityDTO>();

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("direct_upcoming_activities");
        final int recordNum = resultContainer.size();
        for (int i = 0; i < recordNum; i++) {
            String activityTypeText = resultContainer.getStringItem(i, "activity_type");
            long tcDirectProjectId = resultContainer.getLongItem(i, "tc_direct_project_id");
            String tcDirectProjectName = resultContainer.getStringItem(i, "tc_direct_project_name");
            long contestId = resultContainer.getLongItem(i, "contest_id");
            String contestName = resultContainer.getStringItem(i, "contest_name");
            long originatorId = Long.parseLong(resultContainer.getStringItem(i, "user_id"));
            String originatorHandle = resultContainer.getStringItem(i, "user");
            Timestamp date = resultContainer.getTimestampItem(i, "activity_time");

            final ProjectBriefDTO project;
            if (!projects.containsKey(tcDirectProjectId)) {
                project = createProject(tcDirectProjectId, tcDirectProjectName);
                projects.put(tcDirectProjectId, project);
            } else {
                project = projects.get(tcDirectProjectId);
            }

            ContestBriefDTO contest;
            if (contests.containsKey(contestId)) {
                contest = contests.get(contestId);
            } else {
                contest = createContest(contestId, contestName, project);
                contests.put(contestId, contest);
            }

            ActivityType activityType = ActivityType.forName(activityTypeText);
            ActivityDTO activity = createActivity(contest, date, originatorHandle, originatorId, activityType);
            activities.add(activity);
        }

        UpcomingActivitiesDTO result = new UpcomingActivitiesDTO();
        result.setActivities(activities);
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
     * @param tcSubject the <code>TCSubject</code> entity
     * @param searchFor the value which will be searched against
     * @return a <code>List</code> providing the details on projects associated with the specified user.
     *
     * @throws Exception if any error occurs
     */
    public static List<DashboardProjectSearchResultDTO> searchUserProjects(TCSubject tcSubject, String searchFor)
        throws Exception {
        List<ProjectSummaryData> projects = DirectUtils.getContestServiceFacade().getProjectData(tcSubject);
        List<ProjectSummaryData> filterdProjects;

        if (StringUtils.isBlank(searchFor)) {
            filterdProjects = projects;
        } else {
            final String searchForLowerCase = searchFor.toLowerCase();
            filterdProjects = (List<ProjectSummaryData>) CollectionUtils.select(projects, new Predicate() {
                //@Override
                public boolean evaluate(Object data) {
                    ProjectSummaryData project = (ProjectSummaryData) data;
                    return project.getProjectName() != null
                        && project.getProjectName().toLowerCase().contains(searchForLowerCase);
                }
            });
        }

        return (List<DashboardProjectSearchResultDTO>) CollectionUtils.collect(filterdProjects, new Transformer() {
            //@Override
            public Object transform(Object data) {
                ProjectSummaryData project = (ProjectSummaryData) data;
                DashboardProjectSearchResultDTO dto = new DashboardProjectSearchResultDTO();
                dto.setData(project);
                return dto;
            }
        });
    }

    /**
     * <p>Gets the details on contests associated with specified user and matching the specified criteria.</p>
     *
     * <p>Sub-sequent assemblies must implement this method to use the appropriate logic for getting the matching
     * records. Current implementation uses mock data.</p>
     *
     * @param tcSubject the <code>TCSubject</code> entity
     * @param searchFor the value which will be searched against
     * @param begin the begin date for contest start date
     * @param end the end date for contest start date
     * @return a <code>List</code> providing the details on contests associated with the specified user.
     *
     * @throws Exception if any error occurs
     */
    public static List<DashboardContestSearchResultDTO> searchUserContests(TCSubject tcSubject, String searchFor,
        final Date begin, final Date end) throws Exception {
        List<CommonProjectContestData> contests = DirectUtils.getContestServiceFacade().getCommonProjectContestData(
            tcSubject);
        List<CommonProjectContestData> filteredContests;
        if (StringUtils.isBlank(searchFor) && begin == null && end == null) {
            filteredContests = contests;
        } else {
            final String searchForLowerCase = searchFor.toLowerCase();
            filteredContests = (List<CommonProjectContestData>) CollectionUtils.select(contests, new Predicate() {
                //@Override
                public boolean evaluate(Object data) {
                    CommonProjectContestData contest = (CommonProjectContestData) data;
                    return isMatched(contest, searchForLowerCase, begin, end);
                }
            });
        }

        return (List<DashboardContestSearchResultDTO>) CollectionUtils.collect(filteredContests, new Transformer() {
            //@Override
            public Object transform(Object data) {
                CommonProjectContestData contest = (CommonProjectContestData) data;
                DashboardContestSearchResultDTO dto = new DashboardContestSearchResultDTO();

                ContestBriefDTO brief = new ContestBriefDTO();
                brief.setId(contest.getContestId());
                brief.setTitle(contest.getCname());
                dto.setContest(brief);

                ProjectBriefDTO project = new ProjectBriefDTO();
                project.setId(contest.getProjectId());
                project.setName(contest.getPname());
                brief.setProject(project);

                dto.setContestType(contest.getType());
                dto.setStartTime(DirectUtils.getDate(contest.getStartDate()));
                dto.setEndTime(DirectUtils.getDate(contest.getEndDate()));
                dto.setRegistrantsNumber(contest.getNum_reg());
                dto.setSubmissionsNumber(contest.getNum_sub());
                dto.setForumPostsNumber(contest.getNum_for());
                dto.setStatus(ContestStatus.forName(contest.getSname()));
                return dto;
            }
        });
    }

    /**
     * Tests to see if given contest is matched against all conditions.
     *
     * @param contest the contest object
     * @param searchFor the search for string
     * @param begin the beging date
     * @param end the end date
     * @return true if matched otherwise false
     */
    private static boolean isMatched(CommonProjectContestData contest, String searchFor, Date begin, Date end) {
        if (!StringUtils.isBlank(searchFor) && contest.getCname() != null
            && !contest.getCname().toLowerCase().contains(searchFor)) {
            return false;
        }

        Date contestStartDate = DirectUtils.getDateWithoutTime((DirectUtils.getDate(contest.getStartDate())));
        if (begin != null && contestStartDate != null && begin.compareTo(contestStartDate) > 0) {
            return false;
        }

        if (end != null && contestStartDate != null && end.compareTo(contestStartDate) < 0) {
            return false;
        }

        return true;
    }

    /**
     * <p>
     * Gets the details on users assigned to projects associated with specified user and matching the specified
     * criteria.
     * </p>
     *
     * <p>
     * Sub-sequent assemblies must implement this method to use the appropriate logic for getting the matching
     * records. Current implementation uses mock data.
     * </p>
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
     * @throws Exception if an unexpected error occurs.
     */
    public static List<ProjectBriefDTO> getUserProjects(long userId) throws Exception {
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("direct_my_projects");
        request.setProperty("uid", String.valueOf(userId));

        List<ProjectBriefDTO> projects = new ArrayList<ProjectBriefDTO>();

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("direct_my_projects");
        final int recordNum = resultContainer.size();
        for (int i = 0; i < recordNum; i++) {
            long tcDirectProjectId = resultContainer.getLongItem(i, "tc_direct_project_id");
            String tcDirectProjectName = resultContainer.getStringItem(i, "tc_direct_project_name");
            projects.add(createProject(tcDirectProjectId, tcDirectProjectName));
        }

        return projects;
    }

    /**
     * <p>
     * Gets the stats on specified project.
     * </p>
     *
     * @param projectId a <code>long</code> providing the ID for requested project.
     * @return a <code>ProjectStatsDTO</code> providing the stats for requested project.
     */
    public static ProjectStatsDTO getProjectStats(long projectId) {
        ProjectStatsDTO stats = new ProjectStatsDTO();
        ProjectBriefDTO project = new ProjectBriefDTO();
        project.setId(projectId);
        project.setName("Project #" + projectId);
        stats.setDraftContestsNumber(1 + (int) projectId);
        stats.setFeesFinalized(10000 + projectId);
        stats.setFeesRunning(4563 + projectId);
        stats.setFinishedContestsNumber(2 + (int) projectId);
        stats.setPipelineContestsNumber(3 + (int) projectId);
        stats.setProject(project);
        stats.setRunningContestsNumber(4 + (int) projectId);
        stats.setTaskedContestsNumber(5 + (int) projectId);
        stats.setStartDate(new Date(System.currentTimeMillis() + 24 * projectId * 3600 * 1000L));

        stats.setFeesDraft(stats.getDraftContestsNumber() * 150);
        stats.setFeesScheduled(stats.getPipelineContestsNumber() * 150);

        return stats;
    }

    /**
     * <p>Gets the details on latest activities on contests associated with specified project.</p>
     *
     * <p>Sub-sequent assemblies must implement this method to use the appropriate logic for getting the details on
     * latest activities. Current implementation uses mock data.</p>
     *
     * @param userId a <code>long</code> porividing the user ID.
     * @param projectId a <code>long</code> providing the ID for project to get the latest activities on associated
     *        contests for.
     * @return an <code>LatestProjectActivitiesDTO</code> providing the details on latest activities on contests
     *         associated with the specified project.
     * @throws Exception if an unexpected error occurs.
     */
    public static LatestProjectActivitiesDTO getLatestActivitiesForProject(long userId, long projectId) 
        throws Exception {
        // TODO : this is temporary implementation
        LatestActivitiesDTO data = getLatestActivitiesForUserProjects(userId, 15);
        Map<ProjectBriefDTO, List<ActivityDTO>> map = data.getActivities();
        Iterator<ProjectBriefDTO> dtoIterator = map.keySet().iterator();
        Map<ContestBriefDTO, List<ActivityDTO>> activities = new HashMap<ContestBriefDTO, List<ActivityDTO>>();
        while (dtoIterator.hasNext()) {
            ProjectBriefDTO project = dtoIterator.next();
            if (project.getId() == projectId) {
                List<ActivityDTO> list = map.get(project);
                for (ActivityDTO a : list) {
                    List<ActivityDTO> d;
                    ContestBriefDTO c = a.getContest();
                    if (!activities.containsKey(c)) {
                        d = new ArrayList<ActivityDTO>();
                        activities.put(c, d);
                    } else {
                        d = activities.get(c);
                    }
                    d.add(a);
                }
            }
        }

        LatestProjectActivitiesDTO dto = new LatestProjectActivitiesDTO();
        dto.setActivities(activities);
        return dto;
    }

    /**
     * <p>Gets the details on contests associated with the specified project.</p>
     *
     * <p>Sub-sequent assemblies must implement this method to use the appropriate logic for getting the details on
     * project. Current implementation uses mock data.</p>
     *
     * @param userId a <code>long</code> providing the ID of a user associated with project.
     * @param projectId a <code>long</code> providing the ID for project to get the details for associated contests for.
     * @return a <code>ProjectContestsListDTO</code> providing the details on contests associated with specified
     *         project.
     * @throws Exception if an unexpected error occurs.
     */
    public static ProjectContestsListDTO getProjectContests(long userId, long projectId) throws Exception {
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("direct_my_contests");
        request.setProperty("uid", String.valueOf(userId));
        request.setProperty("tcdirectid", String.valueOf(projectId));

        final Map<Long, ProjectBriefDTO> projects = new HashMap<Long, ProjectBriefDTO>();
        final List<ProjectContestDTO> contests = new ArrayList<ProjectContestDTO>();

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("direct_my_contests");
        final int recordNum = resultContainer.size();
        for (int i = 0; i < recordNum; i++) {
            long tcDirectProjectId = resultContainer.getLongItem(i, "tc_direct_project_id");
            String tcDirectProjectName = resultContainer.getStringItem(i, "tc_direct_project_name");
            long contestId = resultContainer.getLongItem(i, "contest_id");
            String contestName = resultContainer.getStringItem(i, "contest_name");
            String statusName = resultContainer.getStringItem(i, "status");
            String typeName = resultContainer.getStringItem(i, "contest_type");
            Timestamp startDate = resultContainer.getTimestampItem(i, "start_date");
            Timestamp endDate = resultContainer.getTimestampItem(i, "end_date");
            int forumPostsCount = resultContainer.getIntItem(i, "number_of_forum");
            int registrantsCount = resultContainer.getIntItem(i, "number_of_registration");
            int submissionsCount = resultContainer.getIntItem(i, "number_of_submission");

            final ProjectBriefDTO project;
            if (!projects.containsKey(tcDirectProjectId)) {
                project = createProject(tcDirectProjectId, tcDirectProjectName);
                projects.put(tcDirectProjectId, project);
            } else {
                project = projects.get(tcDirectProjectId);
            }

            ContestBriefDTO contestBrief = createContest(contestId, contestName, project);
            ContestType type = ContestType.forName(typeName);
            ContestStatus status = ContestStatus.forName(statusName);

            ProjectContestDTO contest = createProjectContest(contestBrief, type, status, startDate, endDate,
                                                             forumPostsCount, registrantsCount, submissionsCount);
            contests.add(contest);
        }

        ProjectContestsListDTO dto = new ProjectContestsListDTO();
        dto.setContests(contests);
        return dto;
    }

    /**
     * <p>Gets the details on contests associated with the specified project.</p>
     *
     * <p>Sub-sequent assemblies must implement this method to use the appropriate logic for getting the details on
     * project. Current implementation uses mock data.</p>
     *
     * @param userId a <code>long</code> providing the ID for user associated with project.
     * @param projectId a <code>long</code> providing the ID for project to get the details for associated contests for.
     * @return a <code>ProjectContestsListDTO</code> providing the details on contests associated with specified
     *         project.
     * @throws Exception if an unexpected error occurs.
     */
    public static List<TypedContestBriefDTO> getProjectTypedContests(long userId, long projectId) throws Exception {
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("direct_my_contests");
        request.setProperty("uid", String.valueOf(userId));
        request.setProperty("tcdirectid", String.valueOf(projectId));

        final Map<Long, ProjectBriefDTO> projects = new HashMap<Long, ProjectBriefDTO>();
        final List<TypedContestBriefDTO> contests = new ArrayList<TypedContestBriefDTO>();

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("direct_my_contests");
        final int recordNum = resultContainer.size();
        for (int i = 0; i < recordNum; i++) {
            long tcDirectProjectId = resultContainer.getLongItem(i, "tc_direct_project_id");
            String tcDirectProjectName = resultContainer.getStringItem(i, "tc_direct_project_name");
            long contestId = resultContainer.getLongItem(i, "contest_id");
            String contestName = resultContainer.getStringItem(i, "contest_name");
            String statusName = resultContainer.getStringItem(i, "status");
            String typeName = resultContainer.getStringItem(i, "contest_type");

            final ProjectBriefDTO project;
            if (!projects.containsKey(tcDirectProjectId)) {
                project = createProject(tcDirectProjectId, tcDirectProjectName);
                projects.put(tcDirectProjectId, project);
            } else {
                project = projects.get(tcDirectProjectId);
            }

            ContestType type = ContestType.forName(typeName);
            ContestStatus status = ContestStatus.forName(statusName);

            TypedContestBriefDTO contest;
            contest = createTypedContest(contestId, contestName, project, type, status);
            contests.add(contest);
        }

        return contests;
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
//        dto.setContest(MockData.getContest(contestId));
        dto.setContest(MockData.getContest(4));
        return dto;
    }

    /**
     * <p>Gets the details on specified contest.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest.
     * @return a <code>ContestStatsDTO</code> providing the details on contest.
     */
    public static ContestDTO getContest(long contestId) {
//        return MockData.getContest(contestId);
        return MockData.getContest(4);
    }

    /**
     * <p>Gets the list of registrants to specified contest.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest.
     * @return a <code>List</code> providing the details for contest registrants.
     */
    public static List<ContestRegistrantDTO> getContestRegistrants(long contestId) {
//        return MockData.getContestRegistrants(contestId);
        return MockData.getContestRegistrants(4);
    }

    /**
     * <p>Constructs new <code>ProjectBriefDTO</code> instance based on specified properties.</p>
     *
     * @param id a <code>long</code> providing the project ID.
     * @param name a <code>String</code> providing the project name.
     * @return an <code>ProjectBriefDTO</code> providing the details for a single project.
     */
    private static ProjectBriefDTO createProject(long id, String name) {
        ProjectBriefDTO project = new ProjectBriefDTO();
        project.setId(id);
        project.setName(name);
        return project;
    }

    /**
     * <p>Constructs new <code>ContestBriefDTO</code> instance based on specified properties.</p>
     *
     * @param id a <code>long</code> providing the contest ID.
     * @param name a <code>String</code> providing the contest name.
     * @param project a <code>ProjectBriefDTO</code> providing the details for project contest belongs to.
     * @return an <code>ContestBriefDTO</code> providing the details for a single contest.
     */
    private static ContestBriefDTO createContest(long id, String name, ProjectBriefDTO project) {
        ContestBriefDTO contest = new ContestBriefDTO();
        contest.setId(id);
        contest.setTitle(name);
        contest.setProject(project);
        return contest;
    }

    /**
     * <p>Constructs new <code>ContestBriefDTO</code> instance based on specified properties.</p>
     *
     * @param id a <code>long</code> providing the contest ID.
     * @param name a <code>String</code> providing the contest name.
     * @param project a <code>ProjectBriefDTO</code> providing the details for project contest belongs to.
     * @return an <code>ContestBriefDTO</code> providing the details for a single contest.
     */
    private static ContestBriefDTO createContest(long id, String name, ProjectBriefDTO project, String contestType) {
        TypedContestBriefDTO contest = new TypedContestBriefDTO();
        contest.setId(id);
        contest.setTitle(name);
        contest.setProject(project);

        contest.setContestType(ContestType.forName(contestType));

        return contest;
    }

    /**
     * <p>Constructs new <code>TypedContestBriefDTO</code> instance based on specified properties.</p>
     *
     * @param id a <code>long</code> providing the contest ID.
     * @param name a <code>String</code> providing the contest name.
     * @param project a <code>ProjectBriefDTO</code> providing the details for project contest belongs to.
     * @param type a <code>ContestType</code> providing the contest type.
     * @param status a <code>ContestStatus</code> providing the contest status.
     * @return an <code>TypedContestBriefDTO</code> providing the details for a single contest.
     */
    private static TypedContestBriefDTO createTypedContest(long id, String name, ProjectBriefDTO project,
                                                           ContestType type, ContestStatus status) {
        TypedContestBriefDTO contest = new TypedContestBriefDTO();
        contest.setId(id);
        contest.setTitle(name);
        contest.setProject(project);
        contest.setContestType(type);
        contest.setStatus(status);
        return contest;
    }

    /**
     * <p>Constructs new <code>ActivityDTO</code> instance based on specified properties.</p>
     *
     * @param contest a <code>ContestBriefDTO</code> providing the details for the contest associated with activity.
     * @param date a <code>Date</code> providing the timestamp for the activity.
     * @param handle a <code>String</code> providing the handle for the user who is the originator of the activity.
     * @param userId a <code>long</code> providing the
     * @param type an <code>ActivityType</code> referencing the type of the activity.
     * @return an <code>ActivityDTO</code> providing the details for a single activity.
     */
    private static ActivityDTO createActivity(ContestBriefDTO contest,
                                              Date date, String handle, long userId,
                                              ActivityType type) {
        ActivityDTO activity = new ActivityDTO();
        activity.setContest(contest);
        activity.setDate(date);
        activity.setOriginatorHandle(handle);
        activity.setOriginatorId(userId);
        activity.setType(type);
        return activity;
    }

    /**
     * <p>
     * Constructs new <code>ProjectContestDTO</code> instance based on specified properties.
     * </p>
     *
     * @param contestBrief a <code>ContestBriefDTO</code> providing the details for the contest associated with activity.
     * @param type a <code>Date</code> providing the timestamp for the activity.
     * @param status a <code>String</code> providing the handle for the user who is the originator of the activity.
     * @param startTime a <code>long</code> providing the
     * @param endTime an <code>ActivityType</code> referencing the type of the activity.
     * @return an <code>ProjectContestDTO</code> providing the details for a single project contest.
     */
    private static ProjectContestDTO createProjectContest(ContestBriefDTO contestBrief, ContestType type,
                                                          ContestStatus status, Date startTime, Date endTime,
                                                          int forumPostsCount, int registrantsCount,
                                                          int submissionsCount) {
        ProjectContestDTO dto = new ProjectContestDTO();
        dto.setContestType(type);
        dto.setContest(contestBrief);
        dto.setEndTime(endTime);
        dto.setForumPostsNumber(forumPostsCount);
        dto.setRegistrantsNumber(registrantsCount);
        dto.setStartTime(startTime);
        dto.setStatus(status);
        dto.setSubmissionsNumber(submissionsCount);
        return dto;
    }
}
