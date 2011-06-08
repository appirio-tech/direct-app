/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.util;

import com.topcoder.direct.services.configs.ConfigUtils;
import com.topcoder.direct.services.view.dto.*;
import com.topcoder.direct.services.view.dto.contest.*;
import com.topcoder.direct.services.view.dto.copilot.CopilotBriefDTO;
import com.topcoder.direct.services.view.dto.copilot.CopilotContestDTO;
import com.topcoder.direct.services.view.dto.copilot.CopilotProjectDTO;
import com.topcoder.direct.services.view.dto.dashboard.*;
import com.topcoder.direct.services.view.dto.dashboard.billingcostreport.BillingCostReportEntryDTO;
import com.topcoder.direct.services.view.dto.dashboard.costreport.CostDetailsDTO;
import com.topcoder.direct.services.view.dto.dashboard.pipeline.PipelineDraftsRatioDTO;
import com.topcoder.direct.services.view.dto.dashboard.pipeline.PipelineScheduledContestsViewType;
import com.topcoder.direct.services.view.dto.project.*;
import com.topcoder.direct.services.view.util.jira.JiraRpcServiceWrapper;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.CommonProjectContestData;
import com.topcoder.service.facade.contest.ProjectSummaryData;
import com.topcoder.service.project.ProjectData;
import com.topcoder.shared.dataAccess.DataAccess;
import com.topcoder.shared.dataAccess.Request;
import com.topcoder.shared.dataAccess.resultSet.ResultSetContainer;
import com.topcoder.shared.dataAccess.resultSet.TCResultItem;
import com.topcoder.shared.util.DBMS;
import com.topcoder.web.common.CachedDataAccess;
import com.topcoder.web.common.cache.MaxAge;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

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
 * <p>
 * Version 2.1 - Edit Software Contests Assembly - identify if it is software or studio in search contest result
 * </p>
 *
 * <p>
 * Version 2.1.1 (Direct Software Submisison Viewer Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #setSoftwareSubmissionsData(SoftwareContestSubmissionsDTO)} method.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 2.1.2 (Direct Enterprise Dashboard Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #getEnterpriseProjectStats(List)} method.</li>
 *     <li>Added {@link #getAllProjectCategories()} method.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 2.1.3 (Direct Contest Dashboard Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #getContestDashboardData(long, boolean, boolean)} method.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 2.1.4 (Direct Project Dashboard Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Updated {@link #getContestDashboardData(long, boolean, boolean)},
 *     {@link #getSoftwareContestDashboardData(long, boolean)} and {@link #getStudioContestDashboardData(long, boolean)}
 *     methods to add cached model.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 2.1.5 (Direct Pipeline Stats Update Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #getPipelineDraftsRatioStats(PipelineScheduledContestsViewType, long)} method.</li>
 *   </ol>
 * </p>
 * <p>
 * Version 2.1.6 (TC Direct Manage Copilots Assembly 1.0) Change notes:
 * <ol>
 * <li>Added getCopilotProjects method.</li>
 * </ol>
 * </p>
 *
 *  <p>
 * Version 2.1.7 (Direct Release 6 Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #getTopCoderDirectFacts()} method to calculate stats for bug races.</li>
 *   </ol>
 * </p>
 * <p>
 * Version 2.1.8 (Direct Manage Copilot Postings Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #getCopilotPostingContests(TCSubject)} method.</li>
 *     <li>Added {@link #getCurrentPhases(long)} method.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 2.1.9 (Cockpit - Enterprise Dashboard 2 Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Renamed <code>getEnterpriseStatsForProject</code> method to <code>getEnterpriseStatsForProjects</code> and 
 *     updated it to accept additional parameters for client and billing account IDs.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 2.2.0 (Cockpit - Enterprise Dashboard 3 Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added <code>{@link #getEnterpriseStatsForContests(long[],long[], Date, Date, long[], long[])}</code> method </li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 2.2.0 (Cockpit - Enterprise Dashboard 3 Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added <code>{@link #getEnterpriseStatsForAllContests(long[], Date, Date)}</code> method </li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 2.2.0 (Cockpit - Enterprise Dashboard 3 Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added <code>{@link #getEnterpriseContestsAvgStatus(long[], Date, Date)}</code> method </li>
 *   </ol>
 * </p>
 * <p>
 * Version 2.3.0 (TC Direct - Software Creation Update Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Add method getCopilotsForDirectProject to get copilots of the direct project</li>
 *   </ol>
 * </p>
 * <p>
 * Version 2.4.0 (TC Cockpit - Cost Report Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Add method getDashboardCostReportDetails to get cost details of each contest with specified filters.</li>
 *   </ol>
 * </p>
 * <p>
 * Version 2.5.0 (Cockpit Performance Improvement Project Overview and Manage Copilot Posting) Change notes:
 *   <ol>
 *     <li>Update method getLatestActivitiesForUserProjects - a new request input 'tcdirectid' is added to
 *      query 'direct_latest_activities', we set it to 0 in this method to get data for all user projects</li>
 *     <li>Update method getLatestActivitiesForProject - update the method to use query 'direct_latest_activities'
 *     with a new request input 'tcdirectid', is set to the id of the direct project. The method is no longer
 *     depends on getLatestActivitiesForUserProjects which affected the performance.</li>
 *     <li>Update method getUpcomingActivitiesForUserProjects - a new request input 'tcdirectid' is added to
 *      query 'direct_upcoming_activities', we set it to 0 in this method to get data for all user projects</li>
 *     <li>Add a new method getUpcomingActivitiesForProject - the method uses query 'direct_upcoming_activities'
 *     with a new request input 'tcdirectid', is set to the id of the direct project.</li>
 *     <li>Add a new method getProjectContestsHealth - the method uses a new query
 *     'direct_project_overview_contests_health' to get the contest health data of all the active and scheduled
 *     contests of the specified project.</li>
 *     <li>update method getProjectStats to use a simplified query 'direct_project_overview_statistics'.</li>
 *     <li>update method getCopilotPostingContests to use query 'direct_my_copilot_postings' to improve the
 *     performance</li>
 *   </ol>
 * </p>
 * <p>
 * Version 2.6.0 (TC Cockpit Billing Cost Report Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Rename method getDashboardClientBillingProjectMappingsForAdmin to
 *      getDashboardClientBillingProjectMappings, because it's not only for admin user. Removes the argument projects
 *      which is not needed.</li>
 *     <li>Add method.</li>
 *   </ol>
 * </p>
 * 
 * <p>
 * Version 2.6.1 (TC Cockpit Enterprise Dashboard Update Cost Breakdown Assembly) Change notes:
 *   <ol>
 *     <li>Added {@link #getDashboardCostBreakDown(long[], long[])} method to get the cost break down data for contests or
 *     market.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 2.6.2 (TC Cockpit Bug Tracking R1 Cockpit Project Tracking version 1.0) Change notes:
 *   <ol>
 *       <li>Added {@link #getContestIssues(long, boolean)} to get issues of the contest.<li>
 *       <li>Added (@link #getDirectProjectIssues(List<? extends ContestBriefDTO>)} to get issues of the direct project.</li>
 *   </ol>
 * </p>
 * 
 * <p>
 * Version 2.6.3 (TC Direct - Page Layout Update Assembly) Change notes:
 *   <ol>
 *     <li>Updated {@link #getCopilotPostingContests(TCSubject)} method to get a list of ProjectContestDTO.</li>
 *   </ol>
 * </p>
 * <p>
 *
 * Version 2.6.4 (TC Cockpit Contest Duration Calculation Updates Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Updated {@link #getDirectProjectStats(List, long)} method to calculate average contest duration for project.
 *     </li>
 *   </ol>
 * </p>
 * 
 * @author isv, BeBetter, tangzx, xjtufreeman, Blues, flexme, Veve
 * @version 2.6.4
 */
public class DataProvider {

    /**
     * The suffiex for 'monthly'
     */
    private static final String MONTHLY_SUFFIX = "_monthly";
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
     * Gets the copilot user ids for the given direct project.
     *
     * @param directProjectId the direct project id.
     * @return an array of copilot user ids of the given direct project.
     * @throws Exception if an unexpected error occurs while communicating to persistence data store.
     * @since 2.2.0
     */
    public static List<ContestCopilotDTO> getCopilotsForDirectProject(long directProjectId) throws Exception {
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("direct_project_copilots");
        request.setProperty("tcdirectid", String.valueOf(directProjectId));

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("direct_project_copilots");
        List<ContestCopilotDTO> result = new ArrayList<ContestCopilotDTO>();

        final int recordNum = resultContainer.size();

        for (int i = 0; i < recordNum; i++) {
            long copilotUserId = resultContainer.getLongItem(i, "copilot_user_id");
            long copilotProfileId = resultContainer.getLongItem(i, "copilot_profile_id");
            long projectId = resultContainer.getLongItem(i, "project_id");
            String projectName = resultContainer.getStringItem(i, "project_name");
            String handle = resultContainer.getStringItem(i, "handle");

            ContestCopilotDTO copilot = new ContestCopilotDTO();
            copilot.setUserId(copilotUserId);
            copilot.setHandle(handle);
            copilot.setCopilotProfileId(copilotProfileId);
            ProjectBriefDTO project = new ProjectBriefDTO();
            project.setId(projectId);
            project.setName(projectName);

            copilot.setCopilotProject(project);

            // add to result
            result.add(copilot);
        }

        return result;
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
        if (tcDirectFactsResult.getItem(0, "prize_purse").getResultData() != null) {
            result.setPrizePurse(tcDirectFactsResult.getDoubleItem(0, "prize_purse"));
        }

        CachedDataAccess dai = new CachedDataAccess(MaxAge.QUARTER_HOUR, DBMS.JIRA_DATASOURCE_NAME);
        Request dataRequest = new Request();
        dataRequest.setContentHandle("bug_race_active_contests_summary");

        result.setBugRacesNumber(0);
        result.setBugRacesPrizes(0);

        try
        {
            ResultSetContainer rsc = dai.getData(dataRequest).get("bug_race_active_contests_summary");
            if (!rsc.isEmpty()) {
                result.setBugRacesNumber(rsc.get(0).getIntItem("total_contests"));
                if (rsc.get(0).getItem("total_prizes").getResultData()!=null) {
                    result.setBugRacesPrizes(rsc.get(0).getFloatItem("total_prizes"));
                }
            }
        }
        catch (Exception e)
        {
            // ignore, if we dont have the query
        }
        

        return result;
    }

    /**
     * <p>Gets the details on latest activities on projects associated with specified user.</p>
     *
     * <p>Updates in version 2.5.0:
     * - Add a query request input 'tcdirectid' and set it to 0. It represents to query on all the direct projects the
     * user has access to or created.
     * - Refactoring the latest activity creation process into helper method fo reuse.
     * </p>
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
        // Setting to 0 means getting all the direct projects of the user
        request.setProperty("tcdirectid", "0");
        request.setProperty("uid", String.valueOf(userId));
        request.setProperty("days", String.valueOf(days));

        final Map<ProjectBriefDTO, List<ActivityDTO>> activities = new HashMap<ProjectBriefDTO, List<ActivityDTO>>();
        final Map<Long, ProjectBriefDTO> projects = new HashMap<Long, ProjectBriefDTO>();
        final Map<Long, TypedContestBriefDTO> contests = new HashMap<Long, TypedContestBriefDTO>();//here

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("direct_latest_activities");
        final int recordNum = resultContainer.size();

        for (int i = 0; i < recordNum; i++) {

            // create ActivityDTO from result row
            ActivityDTO activity = createLatestActivity(resultContainer, i, projects, contests);

            ProjectBriefDTO project = activity.getContest().getProject();
            List<ActivityDTO> projectActivities;

            if (!activities.containsKey(project)) {
                projectActivities = new ArrayList<ActivityDTO>();
                activities.put(project, projectActivities);
            } else {
                projectActivities = activities.get(project);
            }

            projectActivities.add(activity);
        }

        LatestActivitiesDTO result = new LatestActivitiesDTO();


        // sort the activities via activity date
        for (List<ActivityDTO> la : activities.values()) {
            Collections.sort(la, new Comparator<ActivityDTO>() {
                public int compare(ActivityDTO e1, ActivityDTO e2) {
                    return -e1.getDate().compareTo(e2.getDate());
                }
            });
        }

        // sort the map by project's latest activity date
        List<Map.Entry<ProjectBriefDTO, List<ActivityDTO>>> list =
                new LinkedList<Map.Entry<ProjectBriefDTO, List<ActivityDTO>>>(activities.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<ProjectBriefDTO, List<ActivityDTO>>>() {
            public int compare(Map.Entry<ProjectBriefDTO, List<ActivityDTO>> o1, Map.Entry<ProjectBriefDTO, List<ActivityDTO>> o2) {
                ActivityDTO a1 = o1.getValue().get(0);
                ActivityDTO a2 = o2.getValue().get(0);

                if (a1 == null || a2 == null) return 0;
                else {
                    return -a1.getDate().compareTo(a2.getDate());
                }

            }
        });

        Map<ProjectBriefDTO, List<ActivityDTO>> sortedActivities = new LinkedHashMap<ProjectBriefDTO, List<ActivityDTO>>();

        for(Map.Entry<ProjectBriefDTO, List<ActivityDTO>> e : list) {
            sortedActivities.put(e.getKey(),e.getValue());
        }

        result.setActivities(sortedActivities);

        return result;
    }


    /**
     * Helper method to get data from result of query 'direct_latest_activities' and instantiate an ActivityDTO
     * instance to represent latest activity and return it as result.
     *
     *
     * @param result the ResultSetContainer returned by running the query
     * @param resultIndex the index to retrieve the result row
     * @param directProjectsMap the map to store direct projects
     * @param contestsMap the map to store the contests
     * @return the instantiated ActivityDTO instance
     * @since 2.5.0 - (Cockpit Performance Improvement Project Overview and Manage Copilot Posting Assembly)
     */
    private static ActivityDTO createLatestActivity(ResultSetContainer result, int resultIndex, Map<Long,
            ProjectBriefDTO> directProjectsMap, Map<Long, TypedContestBriefDTO> contestsMap) {
        
            String activityTypeText = result.getStringItem(resultIndex, "activity_type");
            long tcDirectProjectId = result.getLongItem(resultIndex, "tc_direct_project_id");
            String tcDirectProjectName = result.getStringItem(resultIndex, "tc_direct_project_name");
            long contestId = result.getLongItem(resultIndex, "contest_id");
            String contestName = result.getStringItem(resultIndex, "contest_name");
            String contestType = result.getStringItem(resultIndex, "contest_type");
            long contestTypeId = result.getLongItem(resultIndex, "contest_type_id");
            Boolean isStudio = (result.getIntItem(resultIndex, "is_studio") == 1);

            long originatorId = Long.parseLong(result.getStringItem(resultIndex, "user_id"));
            String originatorHandle = result.getStringItem(resultIndex, "user");
            Timestamp date = result.getTimestampItem(resultIndex, "activity_time");


            // Sets up the direct project
            final ProjectBriefDTO project;

            if (!directProjectsMap.containsKey(tcDirectProjectId)) {
                project = createProject(tcDirectProjectId, tcDirectProjectName);
                directProjectsMap.put(tcDirectProjectId, project);
            } else {
                project = directProjectsMap.get(tcDirectProjectId);
            }

            final TypedContestBriefDTO contest;
            if (contestsMap.containsKey(contestId)) {
                contest = contestsMap.get(contestId);
            } else {
                contest = createTypedContest(contestId, contestName, project, ContestType.forIdAndFlag(contestTypeId, isStudio), null, !isStudio);//here
                contestsMap.put(contestId, contest);
            }

            ActivityType activityType = ActivityType.forName(activityTypeText);
            ActivityDTO activity = createActivity(contest, date, originatorHandle, originatorId, activityType);

            return activity;
    }

    /**
     * <p>Gets the details on upcoming activities on projects associated with specified user.</p>
     *
     * <p>Updates in version 2.5.0:
     * - Add a new query into for query direct_upcoming_activities, and set it to 0 to represents getting upcoming
     * activities of all the direct projects the user has access to or created.
     * - Refactoring the upcoming activity creation into the helper method for reuse.
     * </p>
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
        request.setProperty("tcdirectid", "0");
        request.setProperty("uid", String.valueOf(userId));
        request.setProperty("days", String.valueOf(days));

        final Map<Long, ProjectBriefDTO> projects = new HashMap<Long, ProjectBriefDTO>();
        final Map<Long, TypedContestBriefDTO> contests = new HashMap<Long, TypedContestBriefDTO>();
        final List<ActivityDTO> activities = new ArrayList<ActivityDTO>();

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("direct_upcoming_activities");
        final int recordNum = resultContainer.size();
        for (int i = 0; i < recordNum; i++) {

            ActivityDTO activity = createUpcomingActivity(resultContainer, i, projects, contests);

            activities.add(activity);
        }

        UpcomingActivitiesDTO result = new UpcomingActivitiesDTO();
        result.setActivities(activities);
        return result;
    }

    /**
     * <p>
     * Gets te details on upcoming activities of the specified direct project and specified user.
     * </p>
     *
     * @param userId userId a <code>long</code> providing the user to get the upcoming activities on associated projects for.
     * @param projectId a <code>long</code> providing the id of the direct project.
     * @param days  days an <code>int</code> providing the number of days from current time for selecting activities.
     * @return an <code>UpcomingActivitiesDTO</code> providing the details on upcoming activities on specified direct
     *  project.
     * @throws Exception if an unexpected error occurs while communicating to persistent data store.
     * @since 2.5.0 (Cockpit Performance Improvement Project Overview and Manage Copilot Posting)
     */
    public static UpcomingActivitiesDTO getUpcomingActivitiesForProject(long userId, long projectId, int days) throws Exception {
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);

        // initialize the request
        Request request = new Request();
        request.setContentHandle("direct_upcoming_activities");
        // set the query input value tcdirectid with the specified direct project id
        request.setProperty("tcdirectid", String.valueOf(projectId));
        request.setProperty("uid", String.valueOf(userId));
        request.setProperty("days", String.valueOf(days));

        final Map<Long, ProjectBriefDTO> projects = new HashMap<Long, ProjectBriefDTO>();
        final Map<Long, TypedContestBriefDTO> contests = new HashMap<Long, TypedContestBriefDTO>();
        final List<ActivityDTO> activities = new ArrayList<ActivityDTO>();

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("direct_upcoming_activities");

        final int recordNum = resultContainer.size();
        for (int i = 0; i < recordNum; i++) {

            ActivityDTO activity = createUpcomingActivity(resultContainer, i, projects, contests);

            activities.add(activity);
        }

        UpcomingActivitiesDTO result = new UpcomingActivitiesDTO();
        result.setActivities(activities);
        return result;
    }


    /**
     * Helper method to get data from result of query 'direct_upcoming_activities' and instantiate an ActivityDTO
     * instance to represent upcoming activity and return it as result.
     *
     *
     * @param result the ResultSetContainer returned by running the query
     * @param resultIndex the index to retrieve the result row
     * @param directProjectsMap the map to store direct projects
     * @param contestsMap the map to store the contests
     * @return the instantiated ActivityDTO instance
     * @since 2.5.0 - (Cockpit Performance Improvement Project Overview and Manage Copilot Posting Assembly)
     */
    private static ActivityDTO createUpcomingActivity(ResultSetContainer result, int resultIndex, Map<Long,
            ProjectBriefDTO> directProjectsMap, Map<Long, TypedContestBriefDTO> contestsMap) {

        String activityTypeText = result.getStringItem(resultIndex, "activity_type");
        long tcDirectProjectId = result.getLongItem(resultIndex, "tc_direct_project_id");
        String tcDirectProjectName = result.getStringItem(resultIndex, "tc_direct_project_name");
        long contestId = result.getLongItem(resultIndex, "contest_id");
        String contestName = result.getStringItem(resultIndex, "contest_name");
        Boolean isStudio = (result.getIntItem(resultIndex, "is_studio") == 1);

        long originatorId = Long.parseLong(result.getStringItem(resultIndex, "user_id"));
        String originatorHandle = result.getStringItem(resultIndex, "user");
        Timestamp date = result.getTimestampItem(resultIndex, "activity_time");


        // Sets up the direct project
        final ProjectBriefDTO project;

        if (!directProjectsMap.containsKey(tcDirectProjectId)) {
            project = createProject(tcDirectProjectId, tcDirectProjectName);
            directProjectsMap.put(tcDirectProjectId, project);
        } else {
            project = directProjectsMap.get(tcDirectProjectId);
        }

        // set up the contest
        final TypedContestBriefDTO contest;
        if (contestsMap.containsKey(contestId)) {
            contest = contestsMap.get(contestId);
        } else {
            contest = createTypedContest(contestId, contestName, project, null, null, !isStudio);
            contestsMap.put(contestId, contest);
        }

        ActivityType activityType = ActivityType.forName(activityTypeText);
        ActivityDTO activity = createActivity(contest, date, originatorHandle, originatorId, activityType);

        return activity;
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
        List<ProjectSummaryData> filteredProjects;

        if (StringUtils.isBlank(searchFor)) {
            filteredProjects = projects;
        } else {
            final String searchForLowerCase = searchFor.toLowerCase();
            filteredProjects = (List<ProjectSummaryData>) CollectionUtils.select(projects, new Predicate() {
                //@Override
                public boolean evaluate(Object data) {
                    ProjectSummaryData project = (ProjectSummaryData) data;
                    return project.getProjectName() != null
                        && project.getProjectName().toLowerCase().contains(searchForLowerCase);
                }
            });
        }

        return (List<DashboardProjectSearchResultDTO>) CollectionUtils.collect(filteredProjects, new Transformer() {
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
                brief.setSoftware(!"Studio".equals(contest.getType()));
                brief.setContestTypeName(contest.getType());
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
                if (contest.getForumId() != null)
                {
                    dto.setForumId(contest.getForumId());
                }
                else
                {
                    dto.setForumId(-1);
                }
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
     * <p>Update notes in version 2.5.0:
     * Update to use a new simplified query 'direct_project_overview_statistics' to improvement the performance.
     * </p>
     *
     * @param projectId a <code>long</code> providing the ID for requested project.
     * @return a <code>ProjectStatsDTO</code> providing the stats for requested project.
     */
    public static ProjectStatsDTO getProjectStats(TCSubject tcSubject, long projectId) throws Exception {

        ProjectStatsDTO stats = new ProjectStatsDTO();
        ProjectBriefDTO project = new ProjectBriefDTO();

        double draftFee = 0, activeFee = 0, scheduledFee = 0, finishedFee = 0, cancelledFee = 0;
        int draftNum = 0, activeNum = 0, scheduledNum = 0, finishedNum = 0, cancelledNum = 0;
    
        String tcDirectProjectName = "";

        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("direct_project_overview_statistics");
        request.setProperty("uid", String.valueOf(tcSubject.getUserId()));
        request.setProperty("tcdirectid", String.valueOf(projectId));

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("direct_project_overview_statistics");
        final int recordNum = resultContainer.size();
        for (int i = 0; i < recordNum; i++) {
             String statusName = resultContainer.getStringItem(i, "status");

             double fee = 0;

             try {
                fee = resultContainer.getDoubleItem(i, "contest_payment");
             } catch(IllegalArgumentException ex) {
                 // ignore the IllegalArgumentException if query result does not have contest_payment column
             } catch(NullPointerException ex) {
                 // ignore the NullPointerException if query result does not have contest_payment column
             }
            
             tcDirectProjectName = resultContainer.getStringItem(i, "tc_direct_project_name");
            
             if(DirectUtils.ACTIVE_STATUS.contains(statusName)) {
                 activeNum++;
                 activeFee += fee;
             } else if (DirectUtils.DRAFT_STATUS.contains(statusName)) {
                 draftNum++;
                 draftFee += fee;
             } else if (DirectUtils.SCHEDULED_STATUS.contains(statusName)) {
                 scheduledNum++;
                 scheduledFee += fee;
             } else if (DirectUtils.FINISHED_STATUS.contains(statusName)) {
                 finishedNum++;
                 finishedFee += fee;
             } else if (DirectUtils.CANCELLED_STATUS.contains(statusName)) {
                 cancelledNum++;
                 cancelledFee += fee;
             }

        }
        
        // direct project info
        project.setId(projectId);
        project.setName(tcDirectProjectName);

        // contests numbers
        stats.setDraftContestsNumber(draftNum);
        stats.setRunningContestsNumber(activeNum);
        stats.setPipelineContestsNumber(scheduledNum);
        stats.setFinishedContestsNumber(finishedNum);
        stats.setCancelledContestsNumber(cancelledNum);

        // contests fees
        stats.setFeesFinalized(finishedFee);
        stats.setFeesRunning(activeFee);
        stats.setFeesDraft(draftFee);
        stats.setFeesScheduled(scheduledFee);
        stats.setFeesCancelled(cancelledFee);
        

        stats.setProject(project);

        return stats;
    }

    /**
     * <p>Gets the details on latest activities on contests associated with specified project.</p>
     *
     * <p>
     * Updates in version 2.5.0: Remove the logic of calling of getLatestActivitiesForUserProjects and filter by
     * direct project id. This causes performance issue. In the new implementation, the direct project id is set as
     * input of the query to improve the performance.
     * .</p>
     *
     * @param userId a <code>long</code> providing the user ID.
     * @param projectId a <code>long</code> providing the ID for project to get the latest activities on associated
     *        contests for.
     * @return an <code>LatestProjectActivitiesDTO</code> providing the details on latest activities on contests
     *         associated with the specified project.
     * @throws Exception if an unexpected error occurs.
     */
    public static LatestProjectActivitiesDTO getLatestActivitiesForProject(long userId, long projectId)
        throws Exception {

        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("direct_latest_activities");

        // set the value of direct project id
        request.setProperty("tcdirectid", String.valueOf(projectId));
        request.setProperty("uid", String.valueOf(userId));

        // get the activities of last 15 days
        request.setProperty("days", "15");

        final Map<ContestBriefDTO, List<ActivityDTO>> activities = new HashMap<ContestBriefDTO, List<ActivityDTO>>();
        final Map<Long, ProjectBriefDTO> projects = new HashMap<Long, ProjectBriefDTO>();
        final Map<Long, TypedContestBriefDTO> contests = new HashMap<Long, TypedContestBriefDTO>();

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("direct_latest_activities");
        final int recordNum = resultContainer.size();

        for (int i = 0; i < recordNum; i++) {
            ActivityDTO activity = createLatestActivity(resultContainer, i, projects, contests);

            List<ActivityDTO> contestActivities;

            if (activities.containsKey(activity.getContest())) {
                contestActivities = activities.get(activity.getContest());
            } else {
                contestActivities = new ArrayList<ActivityDTO>();
                activities.put(activity.getContest(), contestActivities);
            }

            contestActivities.add(activity);
        }

        // sort the activities via activity date
        for (List<ActivityDTO> la : activities.values()) {
            Collections.sort(la, new Comparator<ActivityDTO>() {
                public int compare(ActivityDTO e1, ActivityDTO e2) {
                    return -e1.getDate().compareTo(e2.getDate());
                }
            });
        }

        // sort the map by contest's latest activity date
        List<Map.Entry<ContestBriefDTO, List<ActivityDTO>>> list =
                new LinkedList<Map.Entry<ContestBriefDTO, List<ActivityDTO>>>(activities.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<ContestBriefDTO, List<ActivityDTO>>>() {
            public int compare(Map.Entry<ContestBriefDTO, List<ActivityDTO>> o1, Map.Entry<ContestBriefDTO, List<ActivityDTO>> o2) {
                ActivityDTO a1 = o1.getValue().get(0);
                ActivityDTO a2 = o2.getValue().get(0);

                if (a1 == null || a2 == null) return 0;
                else {
                    return -a1.getDate().compareTo(a2.getDate());
                }

            }
        });

        Map<ContestBriefDTO, List<ActivityDTO>> sortedActivities = new LinkedHashMap<ContestBriefDTO, List<ActivityDTO>>();

        for (Map.Entry<ContestBriefDTO, List<ActivityDTO>> e : list) {
            sortedActivities.put(e.getKey(), e.getValue());
        }


        LatestProjectActivitiesDTO dto = new LatestProjectActivitiesDTO();
        dto.setActivities(sortedActivities);

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

            //TODO, this is to not affecting existing VMs, will change later
            long contestTypeId = 1;
            try
            {
                contestTypeId = resultContainer.getLongItem(i, "contest_type_id"); // BUGR-3913
            }
            catch (Exception e)
            {
            }

            Timestamp startDate = resultContainer.getTimestampItem(i, "start_date");
            Timestamp endDate = resultContainer.getTimestampItem(i, "end_date");
            int forumPostsCount = resultContainer.getIntItem(i, "number_of_forum");
            int registrantsCount = resultContainer.getIntItem(i, "number_of_registration");
            int submissionsCount = resultContainer.getIntItem(i, "number_of_submission");
            Boolean isStudio = (resultContainer.getIntItem(i, "is_studio") == 1);
            int forumId = -1;
            try
                {
            if (resultContainer.getStringItem(i, "forum_id") != null
                        && !resultContainer.getStringItem(i, "forum_id").equals(""))
                forumId = Integer.parseInt(resultContainer.getStringItem(i, "forum_id"));
            }
            catch (NumberFormatException ne)
            {
		    // ignore
            }
				
			
            final ProjectBriefDTO project;
            if (!projects.containsKey(tcDirectProjectId)) {
                project = createProject(tcDirectProjectId, tcDirectProjectName);
                projects.put(tcDirectProjectId, project);
            } else {
                project = projects.get(tcDirectProjectId);
            }

            ContestBriefDTO contestBrief = createContest(contestId, contestName, project, !isStudio, typeName);
            ContestType type = ContestType.forIdAndFlag(contestTypeId, isStudio);
            ContestStatus status = ContestStatus.forName(statusName);

            ProjectContestDTO contest = createProjectContest(contestBrief, type, status, startDate, endDate,
                                                             forumPostsCount, registrantsCount, submissionsCount, forumId, isStudio);
            contests.add(contest);
        }

        ProjectContestsListDTO dto = new ProjectContestsListDTO();
        dto.setContests(contests);
        return dto;
    }

    /**
     * <p>Gets the details for active contests from the projects assigned to specified user.</p>
     *
     * @param userId a <code>long</code> providing the user ID.
     * @return a <code>ProjectContestsListDTO</code> providing the details for active contests.
     * @throws Exception if an unexpected error occurs.
     */
    public static ProjectContestsListDTO getActiveContests(long userId) throws Exception {
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("direct_active_contests");
        request.setProperty("uid", String.valueOf(userId));

        final Map<Long, ProjectBriefDTO> projects = new HashMap<Long, ProjectBriefDTO>();
        final List<ProjectContestDTO> contests = new ArrayList<ProjectContestDTO>();

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("direct_active_contests");
        final int recordNum = resultContainer.size();
        for (int i = 0; i < recordNum; i++) {
            long tcDirectProjectId = resultContainer.getLongItem(i, "tc_direct_project_id");
            String tcDirectProjectName = resultContainer.getStringItem(i, "tc_direct_project_name");
            long contestId = resultContainer.getLongItem(i, "contest_id");
            String contestName = resultContainer.getStringItem(i, "contest_name");
            String statusName = resultContainer.getStringItem(i, "status");
            String typeName = resultContainer.getStringItem(i, "contest_type");
            long contestTypeId = resultContainer.getLongItem(i, "contest_type_id"); // BUGR-3913
            Timestamp startDate = resultContainer.getTimestampItem(i, "start_date");
            Timestamp endDate = resultContainer.getTimestampItem(i, "end_date");
            int forumPostsCount = resultContainer.getIntItem(i, "number_of_forum");
            int registrantsCount = resultContainer.getIntItem(i, "number_of_registration");
            int submissionsCount = resultContainer.getIntItem(i, "number_of_submission");
            Boolean isStudio = (resultContainer.getIntItem(i, "is_studio") == 1);
            int forumId = -1;
            try
                {
            if (resultContainer.getStringItem(i, "forum_id") != null
                        && !resultContainer.getStringItem(i, "forum_id").equals(""))
                forumId = Integer.parseInt(resultContainer.getStringItem(i, "forum_id"));
            }
            catch (NumberFormatException ne)
            {
		    // ignore
            }
				
			
            final ProjectBriefDTO project;
            if (!projects.containsKey(tcDirectProjectId)) {
                project = createProject(tcDirectProjectId, tcDirectProjectName);
                projects.put(tcDirectProjectId, project);
            } else {
                project = projects.get(tcDirectProjectId);
            }

            ContestBriefDTO contestBrief = createContest(contestId, contestName, project, !isStudio, typeName);
            ContestType type =  ContestType.forIdAndFlag(contestTypeId, isStudio);
            ContestStatus status = ContestStatus.forName(statusName);

            ProjectContestDTO contest = createProjectContest(contestBrief, type, status, startDate, endDate,
                                                             forumPostsCount, registrantsCount, submissionsCount, forumId, isStudio);
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
        request.setContentHandle("direct_my_typed_contests");
        request.setProperty("uid", String.valueOf(userId));
        request.setProperty("tcdirectid", String.valueOf(projectId));

        final Map<Long, ProjectBriefDTO> projects = new HashMap<Long, ProjectBriefDTO>();
        final List<TypedContestBriefDTO> contests = new ArrayList<TypedContestBriefDTO>();

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("direct_my_typed_contests");
        final int recordNum = resultContainer.size();
        for (int i = 0; i < recordNum; i++) {
            long tcDirectProjectId = resultContainer.getLongItem(i, "tc_direct_project_id");
            String tcDirectProjectName = resultContainer.getStringItem(i, "tc_direct_project_name");
            long contestId = resultContainer.getLongItem(i, "contest_id");
            String contestName = resultContainer.getStringItem(i, "contest_name");
            String statusName = resultContainer.getStringItem(i, "status");

            //TODO, this is to not affecting existing VMs, will change later
            long contestTypeId = 1;
            try
            {
                contestTypeId = resultContainer.getLongItem(i, "contest_type_id"); // BUGR-3913
            }
            catch (Exception e)
            {
            }
            

            final ProjectBriefDTO project;
            if (!projects.containsKey(tcDirectProjectId)) {
                project = createProject(tcDirectProjectId, tcDirectProjectName);
                projects.put(tcDirectProjectId, project);
            } else {
                project = projects.get(tcDirectProjectId);
            }

	        Boolean isStudio = (resultContainer.getIntItem(i, "is_studio") == 1);
            ContestType type = ContestType.forIdAndFlag(contestTypeId, isStudio);
            ContestStatus status = ContestStatus.forName(statusName);

            TypedContestBriefDTO contest;
            contest = createTypedContest(contestId, contestName, project, type, status, !isStudio);
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
     * <p>Sets the specified DTO with data for requested project submissions.</p>
     *
     * @param dto a <code>SoftwareContestSubmissionsDTO</code> to be set with data for project submissions.
     * @throws Exception if an unexpected error occurs.
     * @since 1.2.1
     */
    public static void setSoftwareSubmissionsData(SoftwareContestSubmissionsDTO dto) throws Exception {
        final String queryName = "direct_software_submissions_view";
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle(queryName);
        request.setProperty("pj", String.valueOf(dto.getProjectId()));

        // Set reviewers for project and collect them to map for faster lookup on sub-sequent steps
        Map<Long, UserDTO> reviewersMap = new HashMap<Long, UserDTO>();
        final ResultSetContainer reviewersContainer
            = dataAccessor.getData(request).get("direct_software_project_reviewers");
        List<UserDTO> reviewers = new ArrayList<UserDTO>();
        for (ResultSetContainer.ResultSetRow reviewerRow : reviewersContainer) {
            UserDTO reviewer = new UserDTO();
            reviewer.setId(Long.parseLong(reviewerRow.getStringItem("reviewer_id")));
            reviewer.setHandle(reviewerRow.getStringItem("reviewer_handle"));
            long reviewerResourceId = reviewerRow.getLongItem("reviewer_resource_id");
            reviewersMap.put(reviewerResourceId, reviewer);
            reviewers.add(reviewer);
        }
        dto.setReviewers(reviewers);

        // Get the reviews for project submissions and collect them to map for faster lookup on sub-sequent steps
        final ResultSetContainer reviewsContainer
            = dataAccessor.getData(request).get("direct_software_project_reviews");
        Map<Long, List<SoftwareSubmissionReviewDTO>> reviewsMap
            = new HashMap<Long, List<SoftwareSubmissionReviewDTO>>();
        Map<Long, SoftwareSubmissionReviewDTO> screeningReviewsMap = new HashMap<Long, SoftwareSubmissionReviewDTO>();
        for (ResultSetContainer.ResultSetRow reviewRow : reviewsContainer) {
            SoftwareSubmissionReviewDTO review = new SoftwareSubmissionReviewDTO();
            review.setReviewer(reviewersMap.get(reviewRow.getLongItem("resource_id")));
            review.setSubmissionId(reviewRow.getLongItem("submission_id"));
            review.setFinalScore(reviewRow.getFloatItem("final_score"));
            review.setInitialScore(reviewRow.getFloatItem("initial_score"));
            review.setReviewId(reviewRow.getLongItem("review_id"));
            review.setCommitted(reviewRow.getBooleanItem("is_committed"));
            long reviewerRoleId = reviewRow.getLongItem("reviewer_role_id");

            long submissionId = review.getSubmissionId();
            if (reviewerRoleId != 2) { // Not screening review
                List<SoftwareSubmissionReviewDTO> submissionReviews;
                if (reviewsMap.containsKey(submissionId)) {
                    submissionReviews = reviewsMap.get(submissionId);
                } else {
                    submissionReviews = new ArrayList<SoftwareSubmissionReviewDTO>();
                    reviewsMap.put(submissionId, submissionReviews);
                }
                submissionReviews.add(review);
            } else {
                screeningReviewsMap.put(submissionId, review);
            }
        }

        // Set submissions
        final ResultSetContainer submissionsContainer
            = dataAccessor.getData(request).get("direct_software_project_submissions");
        List<SoftwareSubmissionDTO> submissions = new ArrayList<SoftwareSubmissionDTO>();
        for (ResultSetContainer.ResultSetRow submissionRow : submissionsContainer) {
            UserDTO submitter = new UserDTO();
            submitter.setId(Long.parseLong(submissionRow.getStringItem("submitter_id")));
            submitter.setHandle(submissionRow.getStringItem("submitter_handle"));

            SoftwareSubmissionDTO submission = new SoftwareSubmissionDTO();
            long submissionId = submissionRow.getLongItem("submission_id");
            submission.setSubmissionId(submissionId);
            submission.setSubmissionDate(submissionRow.getTimestampItem("create_date"));
            submission.setScreeningScore((Float) submissionRow.getItem("screening_score").getResultData());
            submission.setInitialScore((Float) submissionRow.getItem("initial_score").getResultData());
            submission.setFinalScore((Float) submissionRow.getItem("final_score").getResultData());
            submission.setPlacement((Integer) submissionRow.getItem("placement").getResultData());
            submission.setPassedScreening(!submissionRow.getBooleanItem("failed_screening"));
            submission.setPassedReview(!submissionRow.getBooleanItem("failed_review"));
            submission.setUploadId(submissionRow.getLongItem("upload_id"));
            submission.setSubmitter(submitter);

            submission.setReviews(reviewsMap.get(submission.getSubmissionId()));
            submission.setScreeningReview(screeningReviewsMap.get(submission.getSubmissionId()));
            
            submissions.add(submission);

            Integer placement = submission.getPlacement();
            if (placement != null && placement < 3) {
                SoftwareContestWinnerDTO winner = new SoftwareContestWinnerDTO();
                winner.setFinalScore(submission.getFinalScore());
                winner.setHandle(submitter.getHandle());
                winner.setId(submitter.getId());
                winner.setPlacement(placement);
                winner.setProjectId(dto.getProjectId());
                winner.setSubmissionId(submissionId);
                if (placement == 1) {
                    dto.setFirstPlaceWinner(winner);
                } else if (winner.getPlacement() == 2) {
                    dto.setSecondPlaceWinner(winner);
                }
            }

        }
        dto.setSubmissions(submissions);
    }

    /**
     * <p>Gets the enterprise level statistics for projects assigned to current user.</p>
     *
     * @param tcDirectProjects a <code>List</code> listing details for TC Direct projects accessible to user.
     * @return a <code>List</code> providing the details for all projects accessible to current user.
     * @throws Exception if an unexpected error occurs.
     * @since 2.1.2
     */
    public static List<EnterpriseDashboardProjectStatDTO> getEnterpriseProjectStats(List<ProjectData> tcDirectProjects)
        throws Exception {
        List<EnterpriseDashboardProjectStatDTO> data = new ArrayList<EnterpriseDashboardProjectStatDTO>();
        if ((tcDirectProjects == null) || (tcDirectProjects.isEmpty())) {
            return data;
        }

        String projectIds = getConcatenatedIdsString(tcDirectProjects);
        Set<Long> projectsWithStats = new HashSet<Long>();

        final String queryName = "direct_dashboard_enterprise_health";
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_DW_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle(queryName);
        request.setProperty("tdpis", projectIds);

        final ResultSetContainer resultSetContainer = dataAccessor.getData(request).get(queryName);
        for (ResultSetContainer.ResultSetRow row : resultSetContainer) {
            long projectId = row.getLongItem("tc_direct_project_id");

            ProjectBriefDTO project = new ProjectBriefDTO();
            project.setId(projectId);
            for (ProjectData tcDirectProject : tcDirectProjects) {
                if (tcDirectProject.getProjectId() == projectId) {
                    project.setName(tcDirectProject.getName());
                    projectsWithStats.add(tcDirectProject.getProjectId());
                    break;
                }
            }

            EnterpriseDashboardProjectStatDTO projectStatDTO = new EnterpriseDashboardProjectStatDTO();
            projectStatDTO.setProject(project);
            projectStatDTO.setAverageContestDuration(row.getDoubleItem("average_duration"));
            projectStatDTO.setAverageCostPerContest(row.getDoubleItem("average_cost_per_contest"));
            projectStatDTO.setAverageFulfillment(row.getDoubleItem("average_fulfillment"));
            projectStatDTO.setTotalProjectCost(row.getDoubleItem("total_cost"));

            data.add(projectStatDTO);
        }

        for (ProjectData tcDirectProject : tcDirectProjects) {
            if (!projectsWithStats.contains(tcDirectProject.getProjectId())) {
                ProjectBriefDTO project = new ProjectBriefDTO();
                project.setId(tcDirectProject.getProjectId());
                project.setName(tcDirectProject.getName());

                EnterpriseDashboardProjectStatDTO projectStatDTO = new EnterpriseDashboardProjectStatDTO();
                projectStatDTO.setProject(project);
                projectStatDTO.setAverageContestDuration(0);
                projectStatDTO.setAverageCostPerContest(0);
                projectStatDTO.setAverageFulfillment(0);
                projectStatDTO.setTotalProjectCost(0);

                data.add(projectStatDTO);
            }
        }

        return data;
    }


    /**
     * <p>Gets the enterprise level statistics for projects assigned to current user.</p>
     *
     * @param tcDirectProjects a <code>List</code> listing details for TC Direct projects accessible to user.
     * @param userId a <code>long</code> providing the ID for the user to get data for.
     * @return a <code>List</code> providing the details for all projects accessible to current user.
     * @throws Exception if an unexpected error occurs.
     * @since 2.1.2
     */
    public static List<EnterpriseDashboardProjectStatDTO> getDirectProjectStats(List<ProjectData> tcDirectProjects,
                                                                                           long userId)
        throws Exception {
        List<EnterpriseDashboardProjectStatDTO> data = new ArrayList<EnterpriseDashboardProjectStatDTO>();
        if ((tcDirectProjects == null) || (tcDirectProjects.isEmpty())) {
            return data;
        }

        String projectIds = getConcatenatedIdsString(tcDirectProjects);
        Set<Long> projectsWithStats = new HashSet<Long>();

        final String queryName = "direct_project_stat";
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle(queryName);
        request.setProperty("tcdirectid", projectIds);
        request.setProperty("uid", String.valueOf(userId));

        final ResultSetContainer resultSetContainer = dataAccessor.getData(request).get(queryName);
        for (ResultSetContainer.ResultSetRow row : resultSetContainer) {
            long projectId = row.getLongItem("tc_direct_project_id");

            ProjectBriefDTO project = new ProjectBriefDTO();
            project.setId(projectId);
            for (ProjectData tcDirectProject : tcDirectProjects) {
                if (tcDirectProject.getProjectId() == projectId) {
                    project.setName(tcDirectProject.getName());
                    projectsWithStats.add(tcDirectProject.getProjectId());
                    break;
                }
            }

            EnterpriseDashboardProjectStatDTO projectStatDTO = new EnterpriseDashboardProjectStatDTO();
            projectStatDTO.setProject(project);
            projectStatDTO.setCompletedNumber(row.getIntItem("completed_number"));
            projectStatDTO.setCancelledNumber(row.getIntItem("cancelled_number"));
            projectStatDTO.setTotalMemberCost(row.getDoubleItem("total_member_cost"));
            projectStatDTO.setAverageMemberCostPerContest(row.getDoubleItem("average_member_cost"));
            projectStatDTO.setTotalContestFee(row.getDoubleItem("total_contest_fee"));
            projectStatDTO.setAverageContestFeePerContest(row.getDoubleItem("average_contest_fee"));
            projectStatDTO.setAverageFulfillment(row.getDoubleItem("fullfillment"));
            projectStatDTO.setTotalProjectCost(row.getDoubleItem("total_cost"));
            projectStatDTO.setAverageContestDuration(row.getDoubleItem("average_duration"));

            data.add(projectStatDTO);
        }

        for (ProjectData tcDirectProject : tcDirectProjects) {
            if (!projectsWithStats.contains(tcDirectProject.getProjectId())) {
                ProjectBriefDTO project = new ProjectBriefDTO();
                project.setId(tcDirectProject.getProjectId());
                project.setName(tcDirectProject.getName());

                EnterpriseDashboardProjectStatDTO projectStatDTO = new EnterpriseDashboardProjectStatDTO();
                projectStatDTO.setProject(project);
                projectStatDTO.setTotalMemberCost(0);
                projectStatDTO.setAverageCostPerContest(0);
                projectStatDTO.setTotalContestFee(0);
                projectStatDTO.setAverageContestFeePerContest(0);
                projectStatDTO.setAverageFulfillment(0);
                projectStatDTO.setAverageContestDuration(0);
                projectStatDTO.setTotalProjectCost(0);

                data.add(projectStatDTO);
            }
        }

        return data;
    }

    /**
     * <p>Gets the mapping to be used for looking up the project categories by IDs.</p>
     *
     * @return a <code>Map</code> mapping the project category IDs to category names.
     * @throws Exception if an unexpected error occurs.
     * @since 2.1.2
     */
    public static Map<Long, String> getAllProjectCategories() throws Exception {
        Map<Long, String> map = new LinkedHashMap<Long, String>();

        final String queryName = "project_categories";
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle(queryName);

        final ResultSetContainer resultSetContainer = dataAccessor.getData(request).get(queryName);
        for (ResultSetContainer.ResultSetRow row : resultSetContainer) {
            map.put(row.getLongItem("project_category_id"), row.getStringItem("name"));
        }

        return map;
    }

    /**
     * <p>Gets the cost breakdown data for contests or markets.</p>
     *
     * @param projectIds the project ids of the contests
     * @param projectCategoriesIds the project category ids of the market
     * @param startDate the start date when getting market cost breakdown data
     * @param endDate the end date when getting market cost breakdown data
     * @return If projectIds is not null or empty, the cost breakdown data for contests will be returned. If
     * projectCategoriesIds is not null or empty, the cost breakdown data for the market will be returned.
     * @throws Exception if an unexpected error occurs.
     * @since 2.6.1
     */
    public static List<DashboardCostBreakDownDTO> getDashboardCostBreakDown(long[] projectIds, long[] projectCategoriesIds, Date startDate, Date endDate) throws Exception {
        List <DashboardCostBreakDownDTO> data = new ArrayList<DashboardCostBreakDownDTO>();
        if ( (projectIds == null || projectIds.length == 0) && (projectCategoriesIds == null || projectCategoriesIds.length == 0) ) {
            return data;
        }
        
        DataAccess dataAccessor;
        Request request = new Request();
        String queryName;
        if (projectCategoriesIds == null || projectCategoriesIds.length == 0) {
            queryName = "dashboard_contest_cost_breakdown";
            request.setProperty("pids", concatenate(projectIds, ", "));
            dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        } else {
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            queryName = "dashboard_market_cost_breakdown";
            request.setProperty("pcids", concatenate(projectCategoriesIds, ", "));
            request.setProperty("sdt", dateFormatter.format(startDate));
            request.setProperty("edt", dateFormatter.format(endDate));
            dataAccessor = new CachedDataAccess(MaxAge.THREE_HOUR, DBMS.TCS_OLTP_DATASOURCE_NAME);
        }
        request.setContentHandle(queryName);
        final ResultSetContainer resultSetContainer = dataAccessor.getData(request).get(queryName);
        for (ResultSetContainer.ResultSetRow row : resultSetContainer) {
            DashboardCostBreakDownDTO dto = new DashboardCostBreakDownDTO();
            dto.setId(row.getLongItem("id"));
            dto.setContestFee(row.getDoubleItem("contest_fee"));
            dto.setPrizes(row.getDoubleItem("prizes"));
            dto.setSpecReview(row.getDoubleItem("spec_review"));
            dto.setReview(row.getDoubleItem("review"));
            dto.setReliability(row.getDoubleItem("reliability"));
            dto.setDigitalRun(row.getDoubleItem("digital_run"));
            dto.setCopilot(row.getDoubleItem("copilot"));
            dto.setBuild(row.getDoubleItem("build"));
            dto.setBugs(row.getDoubleItem("bugs"));
            dto.setMisc(row.getDoubleItem("misc"));
            if (queryName.equals("dashboard_contest_cost_breakdown")) {
                dto.setFullfillment(row.getIntItem("fullfillment"));
            }
            data.add(dto);
        }
        
        return data;
    }
    
    /**
     * <p>Gets the enterprise-level statistics of contests posted within specified period of time.</p>
     *
     *
     * @param projectIds a <code>long</code> array providing the IDs for a project.
     * @param projectCategoryIDs a <code>long</code> array providing the IDs for project categories.
     * @param startDate a <code>Date</code> providing the beginning date for period.
     * @param endDate a <code>Date</code> providing the ending date for period.
     * @param clientIds a <code>long</code> array listing the IDs for clients.
     * @param billingAccountIds a <code>long</code> array listing the IDs for billing accounts.
     * @return a <code>List</code> of statistical data.
     * @throws Exception if an unexpected error occurs.
     * @since 2.2.0
     */
    public static List<EnterpriseDashboardContestStatDTO> getEnterpriseStatsForContests(long[] projectIds,
        long[] projectCategoryIDs, Date startDate, Date endDate, long[] clientIds, long[] billingAccountIds)
        throws Exception {
        List<EnterpriseDashboardContestStatDTO> data
            = new ArrayList<EnterpriseDashboardContestStatDTO>();
        if ((projectIds == null) || (projectIds.length == 0)) {
            return data;
        }
        if ((projectCategoryIDs == null) || (projectCategoryIDs.length == 0)) {
            return data;
        }
        if ((clientIds == null) || (clientIds.length == 0)) {
            return data;
        }
        if ((billingAccountIds == null) || (billingAccountIds.length == 0)) {
            return data;
        }

        String projectIDsList = concatenate(projectIds, ", ");
        String projectCategoryIDsList = concatenate(projectCategoryIDs, ", ");
        String clientIdsList = concatenate(clientIds, ", ");
        String billingAccountIdsList = concatenate(billingAccountIds, ", ");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        // query for contest status
        String queryName;
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_DW_DATASOURCE_NAME);
        Request request = new Request();
        if(projectIds[0] != 0) {
            queryName = "direct_dashboard_enterprise_contest_stats_project";
            request.setProperty("tdpis", String.valueOf(projectIDsList));
        } else if (billingAccountIds[0] != 0) {
            queryName = "direct_dashboard_enterprise_contest_stats_billing";
            request.setProperty("bpids", billingAccountIdsList);
        } else if (clientIds[0] != 0) {
            queryName = "direct_dashboard_enterprise_contest_stats_client";
            request.setProperty("clids", clientIdsList);
        } else {
            return data;
        }
        request.setContentHandle(queryName);
        request.setProperty("sdt", dateFormatter.format(startDate));
        request.setProperty("edt", dateFormatter.format(endDate));
        request.setProperty("pcids", projectCategoryIDsList);

        final ResultSetContainer resultSetContainer = dataAccessor.getData(request).get(queryName);
        for (ResultSetContainer.ResultSetRow row : resultSetContainer) {
            EnterpriseDashboardContestStatDTO contestDTO = new EnterpriseDashboardContestStatDTO();
            contestDTO.setPostingDate(row.getTimestampItem("posting_date"));
            contestDTO.setDate(row.getTimestampItem("contest_date"));
            contestDTO.setCustomerName(row.getStringItem("customer_name"));
            contestDTO.setProjectName(row.getStringItem("project_name"));
            contestDTO.setContestName(row.getStringItem("contest_name"));
            contestDTO.setContestType(row.getStringItem("contest_type"));
            contestDTO.setProjectCategoryId(row.getIntItem("project_category_id"));
            contestDTO.setProjectId(row.getIntItem("project_id"));
            contestDTO.setDirectProjectId(row.getIntItem("direct_project_id"));
            contestDTO.setContestFullfilment(row.getDoubleItem("contest_fulfillment"));
            contestDTO.setContestCost(row.getDoubleItem("contest_cost"));
            contestDTO.setContestDuration(row.getDoubleItem("contest_duration"));
            data.add(contestDTO);
        }

        return data;
    }


    /**
     * <p>Gets the enterprise-level statistics of all contests posted within specified period of time.</p>
     *
     * @param projectCategoryIDs a <code>long</code> array providing the IDs for project categories.
     * @param startDate a <code>Date</code> providing the beginning date for period.
     * @param endDate a <code>Date</code> providing the ending date for period.
     * @return a <code>List</code> of statistical data.
     * @throws Exception if an unexpected error occurs.
     * @since 2.2.0
     */
    public static List<EnterpriseDashboardContestStatDTO> getEnterpriseStatsForAllContests(long[] projectCategoryIDs,
              Date startDate, Date endDate)
        throws Exception {
        List<EnterpriseDashboardContestStatDTO> data
            = new ArrayList<EnterpriseDashboardContestStatDTO>();

        if ((projectCategoryIDs == null) || (projectCategoryIDs.length == 0)) {
            return data;
        }

        String projectCategoryIDsList = concatenate(projectCategoryIDs, ", ");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        // query for contest status
        final String queryName = "direct_dashboard_enterprise_contest_stats_overall";
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_DW_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle(queryName);
        request.setProperty("sdt", dateFormatter.format(startDate));
        request.setProperty("edt", dateFormatter.format(endDate));
        request.setProperty("pcids", projectCategoryIDsList);
        final ResultSetContainer resultSetContainer = dataAccessor.getData(request).get(queryName);
        for (ResultSetContainer.ResultSetRow row : resultSetContainer) {
            EnterpriseDashboardContestStatDTO contestDTO = new EnterpriseDashboardContestStatDTO();
            contestDTO.setPostingDate(row.getTimestampItem("posting_date"));
            contestDTO.setDate(row.getTimestampItem("contest_date"));
            contestDTO.setCustomerName(row.getStringItem("customer_name"));
            contestDTO.setProjectName(row.getStringItem("project_name"));
            contestDTO.setContestName(row.getStringItem("contest_name"));
            contestDTO.setContestType(row.getStringItem("contest_type"));
            contestDTO.setProjectCategoryId(row.getIntItem("project_category_id"));
            contestDTO.setProjectId(row.getIntItem("project_id"));
            contestDTO.setDirectProjectId(row.getIntItem("direct_project_id"));
            contestDTO.setContestFullfilment(row.getDoubleItem("contest_fulfillment"));
            contestDTO.setContestCost(row.getDoubleItem("contest_cost"));
            contestDTO.setContestDuration(row.getDoubleItem("contest_duration"));
            data.add(contestDTO);
        }

        return data;
    }


    /**
     * <p>Gets the enterprise-level avarage statistics of all contests posted within specified period of time.</p>
     *
     * @param projectCategoryIDs a <code>long</code> array providing the IDs for project categories.
     * @param startDate a <code>Date</code> providing the beginning date for period.
     * @param endDate a <code>Date</code> providing the ending date for period.
     * @return a <code>List</code> of statistical data.
     * @throws Exception if an unexpected error occurs.
     * @since 2.2.0
     */
     public static Map<Integer, List<Double>> getEnterpriseContestsAvgStatus(long[] projectCategoryIDs,
              Date startDate, Date endDate)
        throws Exception {

        String projectCategoryIDsList = concatenate(projectCategoryIDs, ", ");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        // query for contest average status
        Map<Integer, List<Double>> contestTypeAvgMap = new HashMap<Integer, List<Double>>();
        final String contestAvgQuery = "direct_dashboard_enterprise_contest_avg";
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_DW_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle(contestAvgQuery);
        request.setProperty("sdt", dateFormatter.format(startDate));
        request.setProperty("edt", dateFormatter.format(endDate));
        request.setProperty("pcids", projectCategoryIDsList);

        final ResultSetContainer avgResultSetContainer = dataAccessor.getData(request).get(contestAvgQuery);
        for (ResultSetContainer.ResultSetRow row : avgResultSetContainer) {
            List<Double> avgDataList = new ArrayList<Double>();
            avgDataList.add(row.getDoubleItem("marketavg_fulfillment"));
            avgDataList.add(row.getDoubleItem("marketavg_cost"));
            avgDataList.add(row.getDoubleItem("marketavg_duration"));
            contestTypeAvgMap.put(row.getIntItem("project_category_id"), avgDataList);
        }

        return contestTypeAvgMap;
    }


    /**
     * <p>Gets the enterprise-level statistics for specified client project for contests of specified categories
     * completed within specified period of time.</p>
     *
     *
     * @param projectIds a <code>long</code> array providing the IDs for a project.
     * @param projectCategoryIDs a <code>long</code> array providing the IDs for project categories.
     * @param startDate a <code>Date</code> providing the beginning date for period.
     * @param endDate a <code>Date</code> providing the ending date for period.
     * @param clientIds a <code>long</code> array listing the IDs for clients.
     * @param billingAccountIds a <code>long</code> array listing the IDs for billing accounts.
     * @param monthly a<code>boolean</code> whether to get monthly or weekly data
     * @return a <code>List</code> of statistical data.
     * @throws Exception if an unexpected error occurs.
     * @throws IllegalArgumentException if any of specified arrays is <code>null</code> or empty.
     * @since 2.1.2
     */
    public static List<EnterpriseDashboardDetailedProjectStatDTO> getEnterpriseStatsForProject(long[] projectIds,
        long[] projectCategoryIDs, Date startDate, Date endDate, long[] clientIds, long[] billingAccountIds, boolean monthly)
        throws Exception {

        List<EnterpriseDashboardDetailedProjectStatDTO> data
            = new ArrayList<EnterpriseDashboardDetailedProjectStatDTO>();

        if ((projectIds == null) || (projectIds.length == 0)) {
            return data;
        }
        if ((projectCategoryIDs == null) || (projectCategoryIDs.length == 0)) {
            return data;
        }
        if ((clientIds == null) || (clientIds.length == 0)) {
            return data;
        }
        if ((billingAccountIds == null) || (billingAccountIds.length == 0)) {
            return data;
        }


        String projectIDsList = concatenate(projectIds, ", ");
        String projectCategoryIDsList = concatenate(projectCategoryIDs, ", ");
        String clientIdsList = concatenate(clientIds, ", ");
        String billingAccountIdsList = concatenate(billingAccountIds, ", ");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_DW_DATASOURCE_NAME);
        Request request = new Request();

        String queryName;
        if(projectIds[0] != 0) {
            queryName = "direct_dashboard_enterprise_detailed_stats_project";
            request.setProperty("tdpis", String.valueOf(projectIDsList));
        } else if (billingAccountIds[0] != 0) {
            queryName = "direct_dashboard_enterprise_detailed_stats_billing";
            request.setProperty("bpids", billingAccountIdsList);
        } else if (clientIds[0] != 0) {
            queryName = "direct_dashboard_enterprise_detailed_stats_client";
            request.setProperty("clids", clientIdsList);
        } else {
            return data;
        }
        
        if(monthly) {
            queryName += MONTHLY_SUFFIX;
        }

        request.setContentHandle(queryName);
        request.setProperty("sdt", dateFormatter.format(startDate));
        request.setProperty("edt", dateFormatter.format(endDate));
        request.setProperty("pcids", projectCategoryIDsList);

        final ResultSetContainer resultSetContainer = dataAccessor.getData(request).get(queryName);
        for (ResultSetContainer.ResultSetRow row : resultSetContainer) {

            EnterpriseDashboardDetailedProjectStatDTO costDTO = new EnterpriseDashboardDetailedProjectStatDTO();
            costDTO.setDate(row.getTimestampItem("stat_date"));
            costDTO.setValue(row.getDoubleItem("cost"));
            costDTO.setContestsCount(row.getIntItem("total_completed_project"));
            costDTO.setStatsType(EnterpriseDashboardStatType.COST);

            EnterpriseDashboardDetailedProjectStatDTO durationDTO = new EnterpriseDashboardDetailedProjectStatDTO();
            durationDTO.setDate(row.getTimestampItem("stat_date"));
            durationDTO.setValue(row.getDoubleItem("duration"));
            durationDTO.setContestsCount(row.getIntItem("total_completed_project"));
            durationDTO.setStatsType(EnterpriseDashboardStatType.DURATION);

            EnterpriseDashboardDetailedProjectStatDTO fulfillmentDTO = new EnterpriseDashboardDetailedProjectStatDTO();
            fulfillmentDTO.setDate(row.getTimestampItem("stat_date"));
            fulfillmentDTO.setValue(row.getDoubleItem("fulfillment"));
            fulfillmentDTO.setContestsCount(row.getIntItem("total_project"));
            fulfillmentDTO.setStatsType(EnterpriseDashboardStatType.FULFILLMENT);

            data.add(costDTO);
            data.add(durationDTO);
            data.add(fulfillmentDTO);
        }

        return data;
    }

    /**
     * <p>Gets the enterprise-level statistics for all contests of specified categories completed within specified
     * period of time.</p>
     *
     * @param projectCategoryIDs a <code>long</code> array providing the IDs for project categories.
     * @param startDate a <code>Date</code> providing the beginning date for period.
     * @param endDate a <code>Date</code> providing the ending date for period.
     * @param monthly <code>boolean</code> whether to get monthly or weekly data
     * @return a <code>List</code> of statistical data.
     * @throws Exception if an unexpected error occurs.
     * @throws IllegalArgumentException if specified <code>projectCategoryIDs</code> array is <code>null</code> or
     *         empty.
     * @since 2.1.2
     */
    public static List<EnterpriseDashboardDetailedProjectStatDTO> getEnterpriseStatsForAllProjects(
        long[] projectCategoryIDs, Date startDate, Date endDate, boolean monthly) throws Exception {
        
        if ((projectCategoryIDs == null) || (projectCategoryIDs.length == 0)) {
            throw new IllegalArgumentException("Project category IDs are not specified");
        }

        String projectCategoryIDsList = concatenate(projectCategoryIDs, ", ");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        List<EnterpriseDashboardDetailedProjectStatDTO> data
            = new ArrayList<EnterpriseDashboardDetailedProjectStatDTO>();
        String queryName = "direct_dashboard_enterprise_detailed_stats_overall";
        if(monthly) {
            queryName += MONTHLY_SUFFIX;
        }
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_DW_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle(queryName);
        request.setProperty("sdt", dateFormatter.format(startDate));
        request.setProperty("edt", dateFormatter.format(endDate));
        request.setProperty("pcids", projectCategoryIDsList);

        final ResultSetContainer resultSetContainer = dataAccessor.getData(request).get(queryName);
        for (ResultSetContainer.ResultSetRow row : resultSetContainer) {

            EnterpriseDashboardDetailedProjectStatDTO costDTO = new EnterpriseDashboardDetailedProjectStatDTO();
            costDTO.setDate(row.getTimestampItem("stat_date"));
            costDTO.setValue(row.getDoubleItem("cost"));
            costDTO.setContestsCount(row.getIntItem("total_completed_project"));
            costDTO.setStatsType(EnterpriseDashboardStatType.COST);

            EnterpriseDashboardDetailedProjectStatDTO durationDTO = new EnterpriseDashboardDetailedProjectStatDTO();
            durationDTO.setDate(row.getTimestampItem("stat_date"));
            durationDTO.setValue(row.getDoubleItem("duration"));
            durationDTO.setContestsCount(row.getIntItem("total_completed_project"));
            durationDTO.setStatsType(EnterpriseDashboardStatType.DURATION);

            EnterpriseDashboardDetailedProjectStatDTO fulfillmentDTO = new EnterpriseDashboardDetailedProjectStatDTO();
            fulfillmentDTO.setDate(row.getTimestampItem("stat_date"));
            fulfillmentDTO.setValue(row.getDoubleItem("fulfillment"));
            fulfillmentDTO.setContestsCount(row.getIntItem("total_project"));
            fulfillmentDTO.setStatsType(EnterpriseDashboardStatType.FULFILLMENT);

            data.add(costDTO);
            data.add(durationDTO);
            data.add(fulfillmentDTO);
        }

        return data;
    }

    /**
     * <p>
     * Gets the dashboard data for specified contest.
     * </p>
     * 
     * @param contestId
     *            a <code>long</code> providing the ID of a contest to get
     *            dashboard data for.
     * @param isStudio
     *            <code>true</code> if specified contest is <code>Studio</code>
     *            contest; <code>false</code> otherwise.
     * @return a <code>ContestDashboardDTO</code> providing the data for
     *         dashboard for specified contest.
     * @param cached
     *            whether should apply to cached model
     * @throws Exception
     *             if an unexpected error occurs.
     * @since 2.1.3
     */
    public static ContestDashboardDTO getContestDashboardData(long contestId,
            boolean isStudio, boolean cached) throws Exception {
        if (isStudio) {
            return getStudioContestDashboardData(contestId, cached);
        } else {
            return getSoftwareContestDashboardData(contestId, cached);
        }
    }
    

    /**
     * Retrieve copilot project data.
     * 
     * @param userId the user id
     * @return copilot projects
     * @throws Exception if any exception occurs
     */
    public static List<CopilotProjectDTO> getCopilotProjects(long userId)
            throws Exception {
        Map<Long, CopilotProjectDTO> copilotProjects = new HashMap<Long, CopilotProjectDTO>();

        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("tc_copilot_projects");
        request.setProperty("uid", String.valueOf(userId));

        // set copilot project
        ResultSetContainer resultContainer = dataAccessor.getData(request).get(
                "tc_copilot_projects");
        for (ResultSetContainer.ResultSetRow row : resultContainer) {
            long tcDirectProjectId = row.getLongItem("tc_direct_project_id");
            String tcDirectProjectName = row
                    .getStringItem("tc_direct_project_name");
            String handle = row.getStringItem("handle");
            String copilotType = row.getStringItem("name");
            String copilotProjectId = row.getStringItem("copilot_project_id");
            String copilotProfileId = row.getStringItem("copilot_profile_id");

            if (!copilotProjects.containsKey(tcDirectProjectId)) {
                CopilotProjectDTO copilotProject = new CopilotProjectDTO();

                ProjectBriefDTO project = new ProjectBriefDTO();
                project.setId(tcDirectProjectId);
                project.setName(tcDirectProjectName);
                copilotProject.setProject(project);

                copilotProject.setCopilots(new ArrayList<CopilotBriefDTO>());
                copilotProject.setContests(new ArrayList<CopilotContestDTO>());

                copilotProjects.put(tcDirectProjectId, copilotProject);
            }

            if (copilotType != null) {
                CopilotBriefDTO copilot = new CopilotBriefDTO();
                copilot.setCopilotProfileId(Long.parseLong(copilotProfileId.trim()));
                copilot.setCopilotProjectId(Long.parseLong(copilotProjectId.trim()));
                copilot.setCopilotType(copilotType);
                copilot.setHandle(handle);

                copilotProjects.get(tcDirectProjectId).getCopilots().add(
                        copilot);
            }
        }

        // set all contests
        Map<Long, CopilotContestDTO> contests = new HashMap<Long, CopilotContestDTO>();
        for (long projectId : copilotProjects.keySet()) {
            ProjectContestsListDTO contestsListDTO = getProjectContests(userId,
                    projectId);

            for (ProjectContestDTO projectContest : contestsListDTO
                    .getContests()) {
                CopilotContestDTO contest = new CopilotContestDTO();
                contest.setContest(projectContest.getContest());
                contest.setCopilots(new ArrayList<String>());
                
                contests.put(contest.getContest().getId(), contest);
            }

        }

        // set contest copilot
        resultContainer = dataAccessor.getData(request).get(
                "tc_copilot_contests");
        for (ResultSetContainer.ResultSetRow row : resultContainer) {
            long contestId = row.getLongItem("contest_id");
            String handle = row.getStringItem("copilot_handle");

            // System.out.println("copilot contest: contestId:" + contestId + " copilot:" + handle);

            if (handle != null) {
                contests.get(contestId).getCopilots().add(handle);
            }
        }

        // set contests to project
        for (Entry<Long, CopilotContestDTO> contest : contests.entrySet()) {
            copilotProjects.get(
                    contest.getValue().getContest().getProject().getId())
                    .getContests().add(contest.getValue());
        }

        return new ArrayList<CopilotProjectDTO>(copilotProjects.values());
    }

    /**
     * <p>Gets the statistics on drafts to launched contests ratio.</p>
     *
     * @param viewType a <code>PipelineScheduledContestsViewType</code> referencing the type of the pipeline report view
     *        to get data for.
     * @param userId a <code>long</code> providing the ID for the user to get data for.
     * @return a <code>List</code> listing the drafts ratio for specified report view type.
     * @throws Exception if an unexpected error occurs.
     * @since 2.1.5
     */
    public static List<PipelineDraftsRatioDTO> getPipelineDraftsRatioStats(PipelineScheduledContestsViewType viewType,
                                                                           long userId) throws Exception {
        if (viewType == null) {
            throw new IllegalArgumentException("The parameter [viewType] is NULL");
        }

        String queryName = "pipeline_drafts_ratio_" + viewType.toString().toLowerCase();

        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle(queryName);
        request.setProperty("uid", String.valueOf(userId));

        Map<String, ResultSetContainer> results = dataAccessor.getData(request);
        ResultSetContainer statsResultContainer = results.get(queryName);

        Map<String, PipelineDraftsRatioDTO> result = new HashMap<String, PipelineDraftsRatioDTO>();
        for (ResultSetContainer.ResultSetRow row : statsResultContainer) {
            PipelineDraftsRatioDTO dto;
            String source = row.getStringItem("source");
            if (!result.containsKey(source)) {
                dto = new PipelineDraftsRatioDTO();
                dto.setSource(source);
                result.put(source, dto);
            } else {
                dto = result.get(source);
            }

            String status = row.getStringItem("status");
            if (status != null) {
                status = status.trim();
            }
            if ("Launched".equalsIgnoreCase(status)) {
                dto.setLaunchedContestsCount(row.getIntItem("contests_count"));
            } else {
                dto.setDraftContestsCount(row.getIntItem("contests_count"));
            }
        }
        return new ArrayList<PipelineDraftsRatioDTO>(result.values());
    }

    /**
     * <p>Gets the list of <code>Copilot Posting</code> </p>
     *
     * <p>Updates in version 2.5.0:
     * Use the new query 'direct_my_copilot_postings' to get the copilot postings of the user.
     * </p>
     *
     * <p>Updates in version 2.6.3:
     * Use the new query 'direct_my_copilot_postings' to get a list of ProjectContestDTO.
     * </p>
     *
     * @param user a <code>TCSubject</code> referencing the user.
     * @return a <code>List</code> of <code>Copilot Posting</code> contests accessible to specified user.
     * @throws Exception if an unexpected error occurs.
     * @since 2.1.7
     */
    public static List<ProjectContestDTO> getCopilotPostingContests(TCSubject user) throws Exception {
        List<ProjectContestDTO> result = new ArrayList<ProjectContestDTO>();
        Map<Long, ProjectBriefDTO> directProjects = new HashMap<Long, ProjectBriefDTO>();

        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);

        Request request = new Request();
        request.setContentHandle("direct_my_copilot_postings");
        request.setProperty("uid", String.valueOf(user.getUserId()));

        Map<String, ResultSetContainer> results = dataAccessor.getData(request);

        final ResultSetContainer copilotPostings = results.get("direct_my_copilot_postings");

        for (ResultSetContainer.ResultSetRow cp : copilotPostings) {
            long contestId = cp.getLongItem("contest_id");
            String contestName = cp.getStringItem("contest_name");
            long directProjectId = cp.getLongItem("tc_direct_project_id");
            String directProjectName = cp.getStringItem("tc_direct_project_name");
            String contestStatus = cp.getStringItem("status");
            String currentPhaseName = cp.getStringItem("current_phase_name");
            Timestamp startDate = cp.getTimestampItem("start_date");
            Timestamp endDate = cp.getTimestampItem("end_date");
            int forumPostsCount = cp.getIntItem("number_of_forum");
            int registrantsCount = cp.getIntItem("number_of_registration");
            int submissionsCount = cp.getIntItem("number_of_submission");
            int forumId = -1;
            String forumIdStr = cp.getStringItem("forum_id");
            
            if (!StringUtils.isEmpty(forumIdStr)) {
                forumId = Integer.parseInt(forumIdStr);
            }

            final ProjectBriefDTO project;

            // check if we have the direct project initialized
            if (directProjects.containsKey(directProjectId)) {
                project = directProjects.get(directProjectId);
            } else {
                project = createProject(directProjectId, directProjectName);
                
                directProjects.put(directProjectId, project);
            }

            ContestBriefDTO contestBrief = createContest(contestId, contestName, project);
            ContestStatus status = ContestStatus.forName(contestStatus);
            if (status == ContestStatus.ACTIVE) {
                status = ContestStatus.forName(currentPhaseName);
            }

            ProjectContestDTO contest = createProjectContest(contestBrief,
                    ContestType.COPILOT_POSTING, status, startDate, endDate,
                    forumPostsCount, registrantsCount, submissionsCount, forumId,
                    false);

            result.add(contest);
        }

        return result;
    }
	
    /**
     * <p>Gets the currently open phases for specified project.</p>
     *
     * @param projectId a <code>long</code> providing the project ID.
     * @return a <code>List</code> of current phases for specified project.
     * @throws Exception if an unexpected error occurs.
     * @since 2.1.7
     */
    public static List<ProjectPhaseDTO> getCurrentPhases(long projectId) throws Exception {
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);

        Request request = new Request();
        request.setContentHandle("current_project_phases");
        request.setProperty("pids", String.valueOf(projectId));

        Map<String, ResultSetContainer> results = dataAccessor.getData(request);

        // Get current phases
        List<ProjectPhaseDTO> result = new ArrayList<ProjectPhaseDTO>();
        final ResultSetContainer projectPhases = results.get("current_project_phases");
        for (ResultSetContainer.ResultSetRow row : projectPhases) {
            String phaseTypeName = row.getStringItem("phase_type_name");
            ProjectPhaseDTO phase = new ProjectPhaseDTO();
            phase.setPhaseName(phaseTypeName);
            result.add(phase);
        }

        return result;
    }

    /**
     * Gets the clients and billing accounts for the admin user. All the clients and billing accounts
     * in the tcs_dw:client_project_dim will be returned. This method is used by EnterpriseDashboardAction
     * to populate the clients and customers dropdown for admin user. And it's a workaround for now and
     * should be replaced with a more formal approach in the following assembly.
     *
     * <p>Updates in 2.6.0:
     * <li>Remove the "ForAdmin" from the method name, and remove the argument projects. The projects list can be
     * got by calling method getUserProjects(long userId).</li>
     *
     * @param tcSubject the tcSubject
     * @return the list of billing projects.
     * @throws Exception if any error occurs.
     */
    public static Map<String, Object> getDashboardClientBillingProjectMappings(TCSubject tcSubject)
            throws Exception {

        Map<String, Object> result = new HashMap<String, Object>();
        Map<Long, Map<Long, String>> clientBillingMap = new HashMap<Long, Map<Long, String>>();
        Map<Long, Map<Long, String>> clientProjectMap = new HashMap<Long, Map<Long, String>>();
        Map<Long, Map<Long, String>> billingProjectMap = new HashMap<Long, Map<Long, String>>();
        Map<Long, String> clientsMap = new HashMap<Long, String>();

        DataAccess dataAccess = new DataAccess(DBMS.TCS_DW_DATASOURCE_NAME);

        Request request = new Request();

        // set to null first
        ResultSetContainer resultContainer = null;

        if (DirectUtils.isTcOperations(tcSubject) || DirectUtils.isTcStaff(tcSubject)) {
            //System.out.println("query the cockpit admin...");
            request.setContentHandle("admin_client_billing_accounts");
            resultContainer = dataAccess.getData(request).get(
                    "admin_client_billing_accounts");
        } else {
            //System.out.println("query non admin...");
            List<ProjectBriefDTO> projects = getUserProjects(tcSubject.getUserId());
            long[] projectIds = new long[projects.size()];
            int index = 0;
            for(ProjectBriefDTO data : projects) {
                projectIds[index] = data.getId();
                index++;
            }
            if (projects.size() > 0) {
                request.setContentHandle("non_admin_client_billing_accounts");
                request.setProperty("tdpis", concatenate(projectIds, ", "));
                resultContainer = dataAccess.getData(request).get(
                        "non_admin_client_billing_accounts");
            }
        }


        if (resultContainer != null) {

            for (ResultSetContainer.ResultSetRow row : resultContainer) {

                long billingId = row.getLongItem("billing_account_id");
                String billingName = row.getStringItem("billing_account_name");
                long clientId = row.getLongItem("client_id");
                String clientName = row.getStringItem("client_name");
                long directProjectId = row.getLongItem("direct_project_id");
                String directProjectName = row.getStringItem("direct_project_name");
                // put into clients map
                clientsMap.put(clientId, clientName);

                // put into clientBillingMap
                Map<Long, String> billingsForClient = clientBillingMap.get(clientId);
                if (billingsForClient == null) {
                    billingsForClient = new HashMap<Long, String>();
                    clientBillingMap.put(clientId, billingsForClient);
                }

                billingsForClient.put(billingId, billingName);

                // put into clientProjectMap
                Map<Long, String> projectForClient = clientProjectMap.get(clientId);
                if (projectForClient == null) {
                    projectForClient = new HashMap<Long, String>();
                    clientProjectMap.put(clientId, projectForClient);
                }

                projectForClient.put(directProjectId, directProjectName);

                // put into billingProjectMap
                Map<Long, String> projectForBilling = billingProjectMap.get(billingId);
                if (projectForBilling == null) {
                    projectForBilling = new HashMap<Long, String>();
                    billingProjectMap.put(billingId, projectForBilling);
                }

                projectForBilling.put(directProjectId, directProjectName);
            }

        }

        result.put("client.billing", clientBillingMap);
        result.put("client.project", clientProjectMap);
        result.put("billing.project", billingProjectMap);
        result.put("clients", clientsMap);

        return result;
    }

    /**
     * Gets the cost report details with the given paramters. The method returns a list of CostDetailsDTO. Each
     * CostDetailDTO represents cost details of one contest.
     *
     * @param projectIds the direct project ids.
     * @param projectCategoryIds the software project category ides.
     * @param studioProjectCategoryIds the studio project category ids.
     * @param clientIds the client ids.
     * @param billingAccountIds the billing accounts ids.
     * @param projectStatusIds the project status ids.
     * @param startDate the start date.
     * @param endDate the end date.
     * @return the generated cost report.
     * @throws Exception if any error occurs.
     * @since 2.4.0
     */
    public static List<CostDetailsDTO> getDashboardCostReportDetails(long[] projectIds, long[] projectCategoryIds, long[] studioProjectCategoryIds,
                long[] clientIds, long[] billingAccountIds, long[] projectStatusIds,  Date startDate, Date endDate, Map<String, Long> statusMapping) throws Exception {
        // create an empty list first to store the result data
        List<CostDetailsDTO> data
                = new ArrayList<CostDetailsDTO>();

        if ((projectIds == null) || (projectIds.length == 0)) {
            return data;
        }
        if ((projectCategoryIds == null && studioProjectCategoryIds == null)) {
            return data;
        }

        if((projectCategoryIds == null ? 0 : projectCategoryIds.length) + (studioProjectCategoryIds == null ? 0 : studioProjectCategoryIds.length) == 0) {
            return data;
        }

        if ((clientIds == null) || (clientIds.length == 0)) {
            return data;
        }
        if ((billingAccountIds == null) || (billingAccountIds.length == 0)) {
            return data;
        }
        if (projectStatusIds == null || (projectStatusIds.length == 0)) {
            return data;
        }

        // concatenate the filters
        String projectCategoryIDsList = "-1";
        if (projectCategoryIds != null && projectCategoryIds.length > 0) {
          projectCategoryIDsList = concatenate(projectCategoryIds, ", ");
        }
        String studioProjectCategoryIdsList = "-1";
        if (studioProjectCategoryIds != null && studioProjectCategoryIds.length > 0) {
           studioProjectCategoryIdsList = concatenate(studioProjectCategoryIds, ", ");
        }


        String clientIdsList = concatenate(clientIds, ", ");
        String billingAccountIdsList = concatenate(billingAccountIds, ", ");
        String projectIDsList = concatenate(projectIds, ", ");

        // date format to prepare date for query input
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();

        String queryName = "dashboard_cost_report";

        if (projectIds[0] != 0) {

            request.setProperty("tcdirectid", projectIDsList);
            request.setProperty("billingaccountid", "0");
            request.setProperty("clientid", "0");

        } else if (billingAccountIds[0] != 0) {

            request.setProperty("tcdirectid", "0");
            request.setProperty("billingaccountid", billingAccountIdsList);
            request.setProperty("clientid", "0");

        } else if (clientIds[0] != 0) {

            request.setProperty("tcdirectid", "0");
            request.setProperty("billingaccountid", "0");
            request.setProperty("clientid", clientIdsList);

        } else {
            return data;
        }

        request.setContentHandle(queryName);
        request.setProperty("sdt", dateFormatter.format(startDate));
        request.setProperty("edt", dateFormatter.format(endDate));
        request.setProperty("pcids", projectCategoryIDsList);
        request.setProperty("scids", studioProjectCategoryIdsList);

        final ResultSetContainer resultSetContainer = dataAccessor.getData(request).get(queryName);

        // prepare status filter
        Set<Long> statusFilter = new HashSet<Long>();
        for(long statusId : projectStatusIds) {
            statusFilter.add(statusId);
        }

        for (ResultSetContainer.ResultSetRow row : resultSetContainer) {

            CostDetailsDTO costDTO = new CostDetailsDTO();

            // set status first
            costDTO.setStatus(row.getStringItem("contest_status").trim());

            // filter by status
            if (!statusFilter.contains(statusMapping.get(costDTO.getStatus().toLowerCase()))) continue;

            IdNamePair client = new IdNamePair();
            IdNamePair billing = new IdNamePair();
            IdNamePair directProject = new IdNamePair();
            IdNamePair contest = new IdNamePair();
            IdNamePair contestCategory = new IdNamePair();

            if (row.getItem("client_id").getResultData() != null) {
                client.setId(row.getLongItem("client_id"));
            }
            if (row.getItem("client").getResultData() != null) {
                client.setName(row.getStringItem("client"));
            }
            if (row.getItem("billing_project_id").getResultData() != null) {
                billing.setId(row.getLongItem("billing_project_id"));
            }
            if (row.getItem("billing_project_name").getResultData() != null) {
                billing.setName(row.getStringItem("billing_project_name"));
            }
            if (row.getItem("direct_project_id").getResultData() != null) {
                directProject.setId(row.getLongItem("direct_project_id"));
            }
            if (row.getItem("direct_project_name").getResultData() != null) {
                directProject.setName(row.getStringItem("direct_project_name"));
            }
            if (row.getItem("project_id").getResultData() != null) {
                 contest.setId(row.getLongItem("project_id"));
            }
            if (row.getItem("contest_name").getResultData() != null) {
                contest.setName(row.getStringItem("contest_name"));
            }
            if (row.getItem("project_category_id").getResultData() != null) {
                contestCategory.setId(row.getLongItem("project_category_id"));
            }
            if (row.getItem("category").getResultData() != null) {
                contestCategory.setName(row.getStringItem("category"));
            }
            if (row.getItem("contest_fee").getResultData() != null) {
                costDTO.setContestFee(row.getDoubleItem("contest_fee"));
            }
            if (row.getItem("estimated_member_costs").getResultData() != null) {
                costDTO.setEstimatedCost(row.getDoubleItem("estimated_member_costs"));
            }
            if (row.getItem("actual_member_costs").getResultData() != null) {
                costDTO.setActualCost(row.getDoubleItem("actual_member_costs"));
            }
            if (row.getItem("completion_date").getResultData() != null) {
                costDTO.setCompletionDate(row.getTimestampItem("completion_date"));
            }

            costDTO.setStudio(row.getIntItem("is_studio") == 1);

            costDTO.setClient(client);
            costDTO.setBilling(billing);
            costDTO.setProject(directProject);
            costDTO.setContest(contest);
            costDTO.setContestType(contestCategory);

            if(costDTO.getStatus().equals("Finished")) {
                // for finished contest, total cost = actual cost + contest fee
                costDTO.setTotal(costDTO.getActualCost() + costDTO.getContestFee());
            } else {
                // for unfinished contest, total cost = estimated cost + contest fee
                costDTO.setTotal(costDTO.getEstimatedCost() + costDTO.getContestFee());
            }


            data.add(costDTO);
        }

        return data;
    }

    /**
     * Gets the billing cost report entries with the given parameters. The method returns a map,
     * the key is the contest id, the value is a list of billing cost entries.
     *
     * @param projectIds the direct project ids.
     * @param projectCategoryIds the software project category ides.
     * @param studioProjectCategoryIds the studio project category ids.
     * @param paymentTypeIds the payment type ids.
     * @param clientIds the client ids.
     * @param billingAccountIds the billing accounts ids.
     * @param projectStatusIds the project status ids.
     * @param startDate the start date.
     * @param endDate the end date.
     * @param statusMapping the mapping of all the contest status.
     * @param paymentTypesMapping the mapping of all the payment types.
     * @return the generated cost report.
     * @throws Exception if any error occurs.
     * @since 2.5.0
     */
    public static Map<Long, List<BillingCostReportEntryDTO>> getDashboardBillingCostReport(long[] projectIds,
                                                                                           long[] projectCategoryIds,
                                                                                           long[] studioProjectCategoryIds,
                                                                                           long[] paymentTypeIds,
                                                                                           long[] clientIds, long[] billingAccountIds, long[] projectStatusIds, Date startDate, Date endDate,
                                                                                           Map<String, Long> statusMapping, Map<String, Long> paymentTypesMapping) throws Exception {
        // create an empty map first to store the result data
        Map<Long, List<BillingCostReportEntryDTO>> data = new HashMap<Long, List<BillingCostReportEntryDTO>>();

        if ((projectIds == null) || (projectIds.length == 0)) {
            return data;
        }
        if ((projectCategoryIds == null && studioProjectCategoryIds == null)) {
            return data;
        }

        if ((projectCategoryIds == null ? 0 : projectCategoryIds.length) + (studioProjectCategoryIds == null ? 0 : studioProjectCategoryIds.length) == 0) {
            return data;
        }

        if (paymentTypeIds == null || paymentTypeIds.length == 0) {
            return data;
        }

        if ((clientIds == null) || (clientIds.length == 0)) {
            return data;
        }
        if ((billingAccountIds == null) || (billingAccountIds.length == 0)) {
            return data;
        }
        if (projectStatusIds == null || (projectStatusIds.length == 0)) {
            return data;
        }

        // concatenate the filters
        String projectCategoryIDsList = "-1";
        if (projectCategoryIds != null && projectCategoryIds.length > 0) {
            projectCategoryIDsList = concatenate(projectCategoryIds, ", ");
        }
        String studioProjectCategoryIdsList = "-1";
        if (studioProjectCategoryIds != null && studioProjectCategoryIds.length > 0) {
            studioProjectCategoryIdsList = concatenate(studioProjectCategoryIds, ", ");
        }

        String clientIdsList = concatenate(clientIds, ", ");
        String billingAccountIdsList = concatenate(billingAccountIds, ", ");
        String projectIDsList = concatenate(projectIds, ", ");

        // date format to prepare date for query input
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();

        String queryName = "dashboard_billing_cost_report";

        if (projectIds[0] != 0) {

            request.setProperty("tcdirectid", projectIDsList);
            request.setProperty("billingaccountid", "0");
            request.setProperty("clientid", "0");

        } else if (billingAccountIds[0] != 0) {

            request.setProperty("tcdirectid", "0");
            request.setProperty("billingaccountid", billingAccountIdsList);
            request.setProperty("clientid", "0");

        } else if (clientIds[0] != 0) {

            request.setProperty("tcdirectid", "0");
            request.setProperty("billingaccountid", "0");
            request.setProperty("clientid", clientIdsList);

        } else {
            return data;
        }

        request.setContentHandle(queryName);
        request.setProperty("sdt", dateFormatter.format(startDate));
        request.setProperty("edt", dateFormatter.format(endDate));
        request.setProperty("pcids", projectCategoryIDsList);
        request.setProperty("scids", studioProjectCategoryIdsList);

        final ResultSetContainer resultSetContainer = dataAccessor.getData(request).get(queryName);

        // prepare status filter
        Set<Long> statusFilter = new HashSet<Long>();
        for (long statusId : projectStatusIds) {
            statusFilter.add(statusId);
        }

        // prepare payment type filter
        Set<Long> paymentTypeFilter = new HashSet<Long>();
        for (long paymentTypeId : paymentTypeIds) {
            paymentTypeFilter.add(paymentTypeId);
        }


        for (ResultSetContainer.ResultSetRow row : resultSetContainer) {

            BillingCostReportEntryDTO costDTO = new BillingCostReportEntryDTO();

            // set status first
            costDTO.setStatus(row.getStringItem("contest_status").trim());

            // filter by status
            if (!statusFilter.contains(statusMapping.get(costDTO.getStatus().toLowerCase()))) continue;

            // get payment type
            String paymentType = row.getStringItem("line_item_category");

            IdNamePair client = new IdNamePair();
            IdNamePair billing = new IdNamePair();
            IdNamePair directProject = new IdNamePair();
            IdNamePair contest = new IdNamePair();
            IdNamePair contestCategory = new IdNamePair();

            if (row.getItem("contest_id").getResultData() != null) {
                contest.setId(row.getLongItem("contest_id"));
            }
            if (row.getItem("contest_name").getResultData() != null) {
                contest.setName(row.getStringItem("contest_name"));
            }

            if (row.getItem("client_id").getResultData() != null) {
                client.setId(row.getLongItem("client_id"));
            }
            if (row.getItem("client").getResultData() != null) {
                client.setName(row.getStringItem("client"));
            }
            if (row.getItem("billing_project_id").getResultData() != null) {
                billing.setId(row.getLongItem("billing_project_id"));
            }
            if (row.getItem("billing_project_name").getResultData() != null) {
                billing.setName(row.getStringItem("billing_project_name"));
            }
            if (row.getItem("direct_project_id").getResultData() != null) {
                directProject.setId(row.getLongItem("direct_project_id"));
            }
            if (row.getItem("direct_project_name").getResultData() != null) {
                directProject.setName(row.getStringItem("direct_project_name"));
            }

            if (row.getItem("project_category_id").getResultData() != null) {
                contestCategory.setId(row.getLongItem("project_category_id"));
            }
            if (row.getItem("category").getResultData() != null) {
                contestCategory.setName(row.getStringItem("category"));
            }
            if (row.getItem("actual_total_member_costs").getResultData() != null) {
                costDTO.setActualTotalMemberCost(row.getDoubleItem("actual_total_member_costs"));
            }
            if (row.getItem("completion_date").getResultData() != null) {
                costDTO.setCompletionDate(row.getTimestampItem("completion_date"));
            }
            if (row.getItem("launch_date").getResultData() != null) {
                costDTO.setLaunchDate(row.getTimestampItem("launch_date"));
            }
            if (row.getItem("payment_date").getResultData() != null) {
                costDTO.setPaymentDate(row.getTimestampItem("payment_date"));
            }
            if (row.getItem("line_item_amount").getResultData() != null) {
                costDTO.setPaymentAmount(row.getDoubleItem("line_item_amount"));
            }
            if (row.getItem("reference_id").getResultData() != null) {
                costDTO.setReferenceId(row.getStringItem("reference_id"));
            }

            if (row.getItem("is_studio").getResultData() != null) {
                if (row.getIntItem("is_studio") == 1)
                {
                    costDTO.setStudio(true);
                }
            }


            costDTO.setClient(client);
            costDTO.setBilling(billing);
            costDTO.setProject(directProject);
            costDTO.setContest(contest);
            costDTO.setContestType(contestCategory);
            costDTO.setPaymentType(paymentType);

            double contestFee = 0;
            double digitalRun = 0;
            double reliability = 0;
            Date reliabilityPayDate = null;

            // get the contest fee of the contest
            if (row.getItem("contest_fee").getResultData() != null) {
                contestFee = row.getDoubleItem("contest_fee");
            }

            // get the digital run points of the contest
            if (row.getItem("digital_run").getResultData() != null) {
                digitalRun = row.getDoubleItem("digital_run");
            }

            // get the reliability payment of the contest
            if (row.getItem("reliability").getResultData() != null) {
                reliability = row.getDoubleItem("reliability");
            }

            // get the reliability payment date of the contest
            if(row.getItem("reliability_payment_date").getResultData() != null) {
                reliabilityPayDate = row.getTimestampItem("reliability_payment_date");
            }

            List<BillingCostReportEntryDTO> entries;

            if (!data.containsKey(costDTO.getContest().getId())) {
                entries = new ArrayList<BillingCostReportEntryDTO>();
                data.put(costDTO.getContest().getId(), entries);

                // add entry for contest fee if the contest is first time processed
                BillingCostReportEntryDTO contestFeeEntry = (BillingCostReportEntryDTO) BeanUtils.cloneBean(costDTO);
                contestFeeEntry.setPaymentType("Contest Fee");
                contestFeeEntry.setPaymentAmount(contestFee);
                contestFeeEntry.setPaymentDate(contestFeeEntry.getLaunchDate());

                BillingCostReportEntryDTO digitalRunEntry = (BillingCostReportEntryDTO) BeanUtils.cloneBean(costDTO);
                digitalRunEntry.setPaymentType("Digital Run");
                digitalRunEntry.setPaymentAmount(digitalRun);
                digitalRunEntry.setPaymentDate(digitalRunEntry.getCompletionDate());

                BillingCostReportEntryDTO reliabilityEntry = (BillingCostReportEntryDTO) BeanUtils.cloneBean(costDTO);
                reliabilityEntry.setPaymentType("Reliability");
                reliabilityEntry.setPaymentAmount(reliability);
                reliabilityEntry.setPaymentDate(reliabilityPayDate);

                // add contest fee if the payment type filter allows
                if (paymentTypeFilter.contains(1L)) {
                    // check date range
                    if (contestFeeEntry.getPaymentDate().after(startDate) && contestFeeEntry.getPaymentDate().before(endDate)) {
                        entries.add(contestFeeEntry);
                    }

                }
                // add digital run entry if the payment type filter allows
                if (paymentTypeFilter.contains(6L)) {
                    // check date range
                    if (digitalRunEntry.getPaymentDate().after(startDate) && digitalRunEntry.getPaymentDate().before(endDate)) {
                        entries.add(digitalRunEntry);
                    }
                }

                // add reliability payment if the payment type filter allows
                if (paymentTypeFilter.contains(5L) && reliability > 0 && reliabilityPayDate != null) {
                    // check the date range
                    if (reliabilityEntry.getPaymentDate().after(startDate) && reliabilityEntry.getPaymentDate().before(endDate)) {
                        entries.add(reliabilityEntry);
                    }
                }
            } else {
                entries = data.get(costDTO.getContest().getId());
            }

            // add the entry if payment type filter allows
            if (paymentTypeFilter.contains(paymentTypesMapping.get(paymentType.trim().toLowerCase()))) {
                entries.add(costDTO);
            }

        }

        return data;
    }

    /**
     * Gets the contest receipt data for a contest.
     * 
     * @param contestId the contest id.
     * @param isStudio true if the contest is a studio contest, false otherwise.
     * @return a <code>ContestReceiptDTO</code> instance providing the contest receipt data.
     * @throws Exception if any error occurs.
     */
    public static ContestReceiptDTO getContestReceipt(long contestId, boolean isStudio) throws Exception {
        DataAccess dataAccess = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        
        Request request = new Request();
        request.setContentHandle("direct_contest_receipt");
        request.setProperty("pj", String.valueOf(contestId));

        ResultSetContainer result = dataAccess.getData(request).get("direct_contest_receipt");
        if (result.size() == 0) {
            return null;
        }
        int row = 0;
        
        ContestReceiptDTO contestReceipt = new ContestReceiptDTO();
        contestReceipt.setFirstPlacePrize(result.getDoubleItem(row, "first_place_prize"));
        contestReceipt.setSecondPlacePrize(result.getDoubleItem(row, "second_place_prize"));
        contestReceipt.setThirdPlacePrize(result.getDoubleItem(row, "third_place_prize"));
        contestReceipt.setFourthPlacePrize(result.getDoubleItem(row, "fourth_place_prize"));
        contestReceipt.setFifthPlacePrize(result.getDoubleItem(row, "fifth_place_prize"));
        contestReceipt.setMilestonePrize(result.getDoubleItem(row, "milestone_prize"));
        contestReceipt.setDrPoints(result.getDoubleItem(row, "dr_points"));
        contestReceipt.setContestFee(result.getDoubleItem(row, "contest_fee"));
        contestReceipt.setReliabilityBonus(result.getDoubleItem(row, "reliability_bonus"));
        contestReceipt.setSpecReviewCost(result.getDoubleItem(row, "spec_review_cost"));
        contestReceipt.setReviewCost(result.getDoubleItem(row, "review_cost"));
        contestReceipt.setCopilotCost(result.getDoubleItem(row, "copilot_cost"));
        contestReceipt.setBugFixCost(result.getDoubleItem(row, "bug_fix_cost"));
        contestReceipt.setTotalCost(result.getDoubleItem(row, "total_cost"));
        contestReceipt.setFinished(result.getStringItem(row, "status").trim().equals("Finished"));
        
        return contestReceipt;
    }

    /**
     * <p>
     * Gets the dashboard data for specified software project.
     * </p>
     * 
     * @param projectId
     *            a <code>long</code> providing the ID of a software project to
     *            get dashboard data for.
     * @return a <code>ContestDashboardDTO</code> providing the data for
     *         dashboard for specified contest.
     * @param cached
     *            whether should apply to cached model
     * @throws Exception
     *             if an unexpected error occurs.
     * @since 2.1.3
     */
    private static ContestDashboardDTO getSoftwareContestDashboardData(
            long projectId, boolean cached) throws Exception {
        // Prepare request to database
        DataAccess dataAccessor;

        if (cached) {
            dataAccessor = new CachedDataAccess(MaxAge.QUARTER_HOUR,
                    DBMS.TCS_OLTP_DATASOURCE_NAME);
        } else {
            dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        }

        Request request = new Request();
        request.setContentHandle("direct_software_contest_dashboard");
        request.setProperty("pj", String.valueOf(projectId));

        // Query database for contest dashboard data
        Map<String, ResultSetContainer> results = dataAccessor.getData(request);
        ContestDashboardDTO dto = new ContestDashboardDTO();

        // Analyze current and next phases
        final ResultSetContainer projectPhasesStats = results.get("project_phases_status");
        if (!projectPhasesStats.isEmpty()) {
            int currentPhaseStatusId = getInt(projectPhasesStats.getRow(0), "phase_status_id");
            if (currentPhaseStatusId == 2) { // If current phase is already running
                ProjectPhaseDTO currentPhase = new ProjectPhaseDTO();
                currentPhase.setStartTime(projectPhasesStats.getTimestampItem(0, "start_time"));
                currentPhase.setEndTime(projectPhasesStats.getTimestampItem(0, "end_time"));
                currentPhase.setPhaseName(projectPhasesStats.getStringItem(0, "phase_name"));
                dto.setCurrentPhase(currentPhase);
                Date now = new Date();
                Date currentPhaseEndTime = currentPhase.getEndTime();
                if (now.compareTo(currentPhaseEndTime) > 0) {
                    dto.setCurrentPhaseStatus(RunningPhaseStatus.LATE);
                } else {
                    long diff = currentPhaseEndTime.getTime() - now.getTime();
                    long hoursLeft = diff / (3600 * 1000);
                    if (hoursLeft < 2) {
                        dto.setCurrentPhaseStatus(RunningPhaseStatus.CLOSING);
                    } else {
                        dto.setCurrentPhaseStatus(RunningPhaseStatus.RUNNING);
                    }
                }

                if (projectPhasesStats.size() > 1) {
                    ProjectPhaseDTO nextPhase = new ProjectPhaseDTO();
                    nextPhase.setStartTime(projectPhasesStats.getTimestampItem(1, "start_time"));
                    nextPhase.setEndTime(projectPhasesStats.getTimestampItem(1, "end_time"));
                    nextPhase.setPhaseName(projectPhasesStats.getStringItem(1, "phase_name"));
                    dto.setNextPhase(nextPhase);
                }
            }
        }

        // Analyze registration status
        final ResultSetContainer registrationStats = results.get("registration_status");
        if (!registrationStats.isEmpty()) {
            ResultSetContainer.ResultSetRow row = registrationStats.getRow(0);
            dto.setNumberOfRegistrants(getInt(row, "registrants_count"));
            dto.setNumberOfSubmissions(getInt(row, "number_of_submissions"));
            dto.setPredictedNumberOfSubmissions(getInt(row, "predicted_number_of_submissions"));

            double reliabilityTotal = getDouble(row, "reliability_total");
            long registrationPhaseStatus = getLong(row, "registration_phase_status");

            if (registrationPhaseStatus == 2) {
                if (reliabilityTotal >= 200) {
                    dto.setRegistrationStatus(RegistrationStatus.HEALTHY);
                } else {
                    dto.setRegistrationStatus(RegistrationStatus.REGISTRATION_LESS_IDEAL_ACTIVE);
                }
            } else if (registrationPhaseStatus == 3) {
                if (reliabilityTotal >= 200) {
                    dto.setRegistrationStatus(RegistrationStatus.HEALTHY);
                } else if (reliabilityTotal >= 100) {
                    dto.setRegistrationStatus(RegistrationStatus.REGISTRATION_LESS_IDEAL_CLOSED);
                } else {
                    dto.setRegistrationStatus(RegistrationStatus.REGISTRATION_POOR);
                }
            }
        }


        String latestHandle = "";
        long latestUserId = 0;
        long latestThreadId = 0;
        int totalForum = 0;
        long forumId = 0;
        int unansweredForumPostsNumber = 0;
        Date latestTime = null;

        final ResultSetContainer forumStats = results.get("contest_forum_stats");
        if (!forumStats.isEmpty()) {
            if (forumStats.getRow(0).getStringItem("latest_handle") != null)
            {
                latestHandle = forumStats.getRow(0).getStringItem("latest_handle");
            }
            latestUserId = getLong(forumStats.getRow(0), "latest_userid");
            latestThreadId = getLong(forumStats.getRow(0), "latest_threadid");
            if (forumStats.getRow(0).getStringItem("forum_id") != null)
            {
                String frm = forumStats.getRow(0).getStringItem("forum_id");
                forumId = new Long(frm);
            }
            
            totalForum = getInt(forumStats.getRow(0), "number_of_forum");
            unansweredForumPostsNumber = getInt(forumStats.getRow(0), "unanswered_threads");
            latestTime = getDate(forumStats.getRow(0), "latest_time");
        }


        UserDTO latestForumPostAuthor = new UserDTO();
        latestForumPostAuthor.setHandle(latestHandle);
        latestForumPostAuthor.setId(latestUserId);

        ForumPostDTO latestForumPost = new ForumPostDTO();
        latestForumPost.setAuthor(latestForumPostAuthor);
        latestForumPost.setUrl("http://forums.topcoder.com/?module=Thread&threadID=" + latestThreadId);
        latestForumPost.setTimestamp(new Date());
        
        if (latestUserId != 0)
        {
            dto.setLatestForumPost(latestForumPost);
        }
        
        dto.setForumURL("http://forums.topcoder.com/?module=Category&categoryID=" + forumId);
        dto.setTotalForumPostsCount(totalForum);
        dto.setUnansweredForumPostsNumber(unansweredForumPostsNumber);

        // Analyze the status for reviewers signup and collect the list of reviewers
        final ResultSetContainer reviewSignupStats = results.get("review_signup_stats");
        if (reviewSignupStats.isEmpty()) {
            dto.setReviewersSignupStatus(ReviewersSignupStatus.ALL_REVIEW_POSITIONS_FILLED);
        } else {
            long hoursLeft = getLong(reviewSignupStats.getRow(0), "hours_left");
            int requiredReviewersCount = getInt(reviewSignupStats.getRow(0), "required_reviewers_count");
            List<UserDTO> reviewers = new ArrayList<UserDTO>();
            for (ResultSetContainer.ResultSetRow row : reviewSignupStats) {
                String reviewerHandle = row.getStringItem("reviewer_handle");
                if (reviewerHandle != null) {
                    UserDTO reviewer = new UserDTO();
                    reviewer.setId(getLong(row, "reviewer_id"));
                    reviewer.setHandle(reviewerHandle);
                    reviewers.add(reviewer);
                }
            }
            dto.setReviewers(reviewers);
            dto.setRequiredReviewersNumber(requiredReviewersCount);
            if (requiredReviewersCount > reviewers.size()) {
                if (hoursLeft < 24) {
                    dto.setReviewersSignupStatus(ReviewersSignupStatus.REVIEW_POSITIONS_NON_FILLED_DANGER);
                } else {
                    dto.setReviewersSignupStatus(ReviewersSignupStatus.REVIEW_POSITIONS_NON_FILLED_WARNING);
                }
            } else {
                dto.setReviewersSignupStatus(ReviewersSignupStatus.ALL_REVIEW_POSITIONS_FILLED);
            }
        }

        // Analyze the overall status of dependencies for project
        final ResultSetContainer projectDependenciesStatuses = results.get("project_dependencies_statuses");
        if (projectDependenciesStatuses.isEmpty()) {
            dto.setDependenciesStatus(DependenciesStatus.NO_DEPENDENCIES);
        } else {
            List<DependencyDTO> dependencies = new ArrayList<DependencyDTO>();
            boolean thereAreIncompleteDependencies = false;
            for (ResultSetContainer.ResultSetRow row : projectDependenciesStatuses) {
                long parentProjectStatusId = getLong(row, "project_status_id");
                if ((parentProjectStatusId == 1) || (parentProjectStatusId == 2)) {
                    thereAreIncompleteDependencies = true;
                }
                ProjectBriefDTO dependencyProject = new ProjectBriefDTO();
                dependencyProject.setId(getLong(row, "project_id"));
                dependencyProject.setName(row.getStringItem("project_name"));

                DependencyDTO dependency = new DependencyDTO();
                dependency.setDependencyProject(dependencyProject);
                dependency.setDependencyType(row.getStringItem("link_type_name"));

                dependencies.add(dependency);
            }
            dto.setDependencies(dependencies);

            if (thereAreIncompleteDependencies) {
                dto.setDependenciesStatus(DependenciesStatus.DEPENDENCIES_NON_SATISFIED);
            } else {
                dto.setDependenciesStatus(DependenciesStatus.DEPENDENCIES_SATISFIED);
            }
        }

        return dto;
    }

    /**
     * <p>
     * Gets the dashboard data for specified Studio contest.
     * </p>
     * 
     * @param contestId
     *            a <code>long</code> providing the ID of a Studio contest to
     *            get dashboard data for.
     * @param cached
     *            whether should apply to cached model
     * @return a <code>ContestDashboardDTO</code> providing the data for
     *         dashboard for specified contest.
     * @throws Exception
     *             if an unexpected error occurs.
     * @since 2.1.3
     */
    private static ContestDashboardDTO getStudioContestDashboardData(
            long contestId, boolean cached) throws Exception {
        // Prepare request to database
        DataAccess dataAccessor;

        if (cached) {
            dataAccessor = new CachedDataAccess(MaxAge.QUARTER_HOUR,
                    DBMS.STUDIO_DATASOURCE_NAME);
        } else {
            dataAccessor = new DataAccess(DBMS.STUDIO_DATASOURCE_NAME);
        }

        Request request = new Request();
        request.setContentHandle("direct_studio_contest_dashboard");
        request.setProperty("ct", String.valueOf(contestId));

        // Query database for contest dashboard data
        Map<String, ResultSetContainer> results = dataAccessor.getData(request);
        ContestDashboardDTO dto = new ContestDashboardDTO();

        // Analyze registration status
        // For now the registration status is always assumed to be Healthy for Studio contests
        dto.setRegistrationStatus(RegistrationStatus.HEALTHY);
        final ResultSetContainer registrationStats = results.get("studio_registration_status");
        if (!registrationStats.isEmpty()) {
            ResultSetContainer.ResultSetRow row = registrationStats.getRow(0);
            dto.setNumberOfRegistrants(getInt(row, "registrants_count"));
            dto.setNumberOfSubmissions(getInt(row, "number_of_submissions"));
            dto.setPredictedNumberOfSubmissions(getInt(row, "predicted_number_of_submissions"));
        }

        String latestHandle = "";
        long latestUserId = 0;
        long latestThreadId = 0;
        int totalForum = 0;
        long forumId = 0;
        int unansweredForumPostsNumber = 0;
        Date latestTime = null;

        final ResultSetContainer forumStats = results.get("studio_contest_forum_stats");
        if (!forumStats.isEmpty()) {
            if (forumStats.getRow(0).getStringItem("latest_handle") != null)
            {
                latestHandle = forumStats.getRow(0).getStringItem("latest_handle");
            }
            latestUserId = getLong(forumStats.getRow(0), "latest_userid");
            latestThreadId = getLong(forumStats.getRow(0), "latest_threadid");
            if (forumStats.getRow(0).getStringItem("forum_id") != null)
            {
                String frm = forumStats.getRow(0).getStringItem("forum_id");
                forumId = new Long(frm);
            }
            
            totalForum = getInt(forumStats.getRow(0), "number_of_forum");
            latestTime = getDate(forumStats.getRow(0), "latest_time");
        }


        UserDTO latestForumPostAuthor = new UserDTO();
        latestForumPostAuthor.setHandle(latestHandle);
        latestForumPostAuthor.setId(latestUserId);

        ForumPostDTO latestForumPost = new ForumPostDTO();
        latestForumPost.setAuthor(latestForumPostAuthor);
        latestForumPost.setUrl("http://studio.topcoder.com/forums?module=Thread&threadID=" + latestThreadId);
        latestForumPost.setTimestamp(new Date());
        
        if (latestUserId != 0)
        {
            dto.setLatestForumPost(latestForumPost);
        }
        
        dto.setForumURL("http://studio.topcoder.com/forums?module=ThreadList&forumID=" + forumId);
        dto.setTotalForumPostsCount(totalForum);


        return dto;
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
    private static ContestBriefDTO createContest(long id, String name, ProjectBriefDTO project, ContestType contestType) {
        TypedContestBriefDTO contest = new TypedContestBriefDTO();
        contest.setId(id);
        contest.setTitle(name);
        contest.setProject(project);

        contest.setContestType(contestType);

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
    private static ContestBriefDTO createContest(long id, String name, ProjectBriefDTO project, Boolean isSoftware, String contestType) {
        ContestBriefDTO contest = new ContestBriefDTO();
        contest.setId(id);
        contest.setTitle(name);
        contest.setProject(project);
        contest.setSoftware(isSoftware);
        contest.setContestTypeName(contestType);
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
                                                           ContestType type, ContestStatus status, Boolean isSoftware) {
        TypedContestBriefDTO contest = new TypedContestBriefDTO();
        contest.setId(id);
        contest.setTitle(name);
        contest.setProject(project);
        contest.setContestType(type);
        contest.setStatus(status);
        contest.setSoftware(isSoftware);
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
    private static ActivityDTO createActivity(TypedContestBriefDTO contest,
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
                                                          int submissionsCount, int forumId,
														  Boolean isStudio) {
        ProjectContestDTO dto = new ProjectContestDTO();
        dto.setContestType(type);
        dto.setContest(contestBrief);
        dto.setEndTime(endTime);
        dto.setForumPostsNumber(forumPostsCount);
        dto.setRegistrantsNumber(registrantsCount);
        dto.setStartTime(startTime);
        dto.setStatus(status);
        dto.setSubmissionsNumber(submissionsCount);
		dto.setForumId(forumId);
		dto.setIsStudio(isStudio);
        return dto;
    }

    /**
     * <p>Concatenates the IDs for specified projects into a single string value.</p>
     *
     * @param tcDirectProjects a <code>List</code> providing the details for projects.
     * @return a <code>String</code> providing the IDs for specified projects separated with commas.
     */
    private static String getConcatenatedIdsString(List<ProjectData> tcDirectProjects) {
        StringBuilder b = new StringBuilder();
        for (ProjectData project : tcDirectProjects) {
            if (b.length() > 0) {
                b.append(", ");
            }
            b.append(project.getProjectId());
        }
        return b.toString();
    }

    /**
     * <p>Gets the <code>int</code> value for specified column from specified resultset row.</p>
     *
     * @param row a <code>ResultSetRow</code> providing the data.
     * @param columnName a <code>String</code> providing the column name.
     * @return an <code>int</code> providing tge value for specified column or 0 if the value is <code>null</code>.
     */
    private static int getInt(ResultSetContainer.ResultSetRow row, String columnName) {
        TCResultItem resultItem = row.getItem(columnName);
        boolean isNull = resultItem.getResultData() == null;
        if (isNull) {
            return 0;
        } else {
            return row.getIntItem(columnName);
        }
    }

    /**
     * <p>Gets the <code>long</code> value for specified column from specified resultset row.</p>
     *
     * @param row a <code>ResultSetRow</code> providing the data.
     * @param columnName a <code>String</code> providing the column name.
     * @return a <code>long</code> providing tge value for specified column or 0 if the value is <code>null</code>.
     */
    private static long getLong(ResultSetContainer.ResultSetRow row, String columnName) {
        TCResultItem resultItem = row.getItem(columnName);
        boolean isNull = resultItem.getResultData() == null;
        if (isNull) {
            return 0;
        } else {
            return row.getLongItem(columnName);
        }
    }

    /**
     * <p>Gets the <code>double</code> value for specified column from specified resultset row.</p>
     *
     * @param row a <code>ResultSetRow</code> providing the data.
     * @param columnName a <code>String</code> providing the column name.
     * @return a <code>double</code> providing tge value for specified column or 0 if the value is <code>null</code>.
     */
    private static double getDouble(ResultSetContainer.ResultSetRow row, String columnName) {
        TCResultItem resultItem = row.getItem(columnName);
        boolean isNull = resultItem.getResultData() == null;
        if (isNull) {
            return 0;
        } else {
            return row.getDoubleItem(columnName);
        }
    }


    /**
     * <p>Gets the <code>Date</code> value for specified column from specified resultset row.</p>
     *
     * @param row a <code>ResultSetRow</code> providing the data.
     * @param columnName a <code>String</code> providing the column name.
     * @return a <code>Date</code> providing tge value for specified column or 0 if the value is <code>null</code>.
     */
    private static Date getDate(ResultSetContainer.ResultSetRow row, String columnName) throws  ParseException{
        SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        TCResultItem resultItem = row.getItem(columnName);
        boolean isNull = resultItem.getResultData() == null;
        if (isNull) {
            return null;
        } else {
            String dt = row.getStringItem(columnName);
            return myFmt.parse(dt);
        }
    }

    /**
     * <p>Build a string concatenating the specified values separated with specified delimiter.</p>
     *  
     * @param items a <code>long</code> array providing the values to be concatenated. 
     * @param delimiter a <code>String</code> providing the delimiter to be inserted between concatenated items.
     * @return a <code>String</code> providing the concatenated item values.
     * @since 2.1.9 
     */
    private static String concatenate(long[] items, String delimiter) {
        StringBuilder b = new StringBuilder();
        for (Long id : items) {
            if (b.length() > 0) {
                b.append(delimiter);
            }
            b.append(id);
        }
        return b.toString();
    }

    /**
     * <p>Gets the details on contests associated with the specified project.</p>
     *
     * <p>Sub-sequent assemblies must implement this method to use the appropriate logic for getting the details on
     * project. Current implementation uses mock data.</p>
     *
     * @param userId a <code>long</code> providing the ID of a user associated with project.
     * @param projectId a <code>long</code> providing the ID for project to get the details for associated contests for.
     * @param cached a flag indicates whether to cache the query result.
     * @return a <code>ProjectContestsListDTO</code> providing the details on contests associated with specified
     *         project.
     * @throws Exception if an unexpected error occurs.
     * @since 2.5.0
     */
    public static Map<ContestBriefDTO, ContestHealthDTO> getProjectContestsHealth(long userId, long projectId,
                                                                                  boolean cached)
        throws Exception {

        DataAccess dataAccessor;

        if (cached) {
            dataAccessor = new CachedDataAccess(MaxAge.HOUR, DBMS.TCS_OLTP_DATASOURCE_NAME);
        } else {
            dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        }

        Request request = new Request();
        request.setContentHandle("direct_project_overview_contests_health");
        request.setProperty("uid", String.valueOf(userId));
        request.setProperty("tcdirectid", String.valueOf(projectId));

        final Map<Long, ProjectBriefDTO> projects = new HashMap<Long, ProjectBriefDTO>();

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("direct_project_overview_contests_health");
        final int recordNum = resultContainer.size();

        Map<ContestBriefDTO, ContestHealthDTO> contests = new HashMap<ContestBriefDTO, ContestHealthDTO>();
        for (int i = 0; i < recordNum; i++) {

                ContestHealthDTO contestHealthDTO = new ContestHealthDTO();

                boolean isStudio = (resultContainer.getIntItem(i, "is_studio") == 1);

                if (isStudio) {
                    contestHealthDTO.setRegistrationStatus(RegistrationStatus.HEALTHY);
                } else {
                    // Evaluate current registration status
                    double reliabilityTotal = getDouble(resultContainer.getRow(i), "reliability_total");
                    long registrationPhaseStatus = getLong(resultContainer.getRow(i), "registration_phase_status");
                    setRegistrationPhaseStatus(contestHealthDTO, reliabilityTotal, registrationPhaseStatus);

                    // Evaluate current phase status
                    Date currentPhaseEndTime = null;
                    if (resultContainer.getItem(i, "current_phase_end_time").getResultData() != null) {
                        currentPhaseEndTime = resultContainer.getTimestampItem(i, "current_phase_end_time");
                    }
                    setCurrentPhaseStatus(contestHealthDTO, currentPhaseEndTime);

                    // Evaluate forum activity status
                    int unAnsweredThreadsCount = getInt(resultContainer.getRow(i), "unanswered_threads");
                    contestHealthDTO.setUnansweredForumPostsNumber(unAnsweredThreadsCount);

                    // Evaluate review sign-up status
                    int requiredReviewersCount = getInt(resultContainer.getRow(i), "required_reviewers_count");
                    int registeredReviewersCount = getInt(resultContainer.getRow(i), "registered_reviewers_count");
                    long hoursLeft = getInt(resultContainer.getRow(i), "review_hours_left");
                    setReviewSignupStatus(contestHealthDTO, hoursLeft, requiredReviewersCount,
                                          registeredReviewersCount);

                    // Evaluate dependencies status
                    Integer dependenciesCount
                        = resultContainer.getIntItem(i, "dependencies_count");
                    Integer incompleteDependenciesCount
                        = resultContainer.getIntItem(i, "incomplete_dependencies_count");

                    if (dependenciesCount == 0) {
                        contestHealthDTO.setDependenciesStatus(DependenciesStatus.NO_DEPENDENCIES);
                    } else if (incompleteDependenciesCount > 0) {
                        contestHealthDTO.setDependenciesStatus(DependenciesStatus.DEPENDENCIES_NON_SATISFIED);
                    } else {
                        contestHealthDTO.setDependenciesStatus(DependenciesStatus.DEPENDENCIES_SATISFIED);
                    }
                }

                // Set colors based on evaluated statuses
                DashboardHelper.setContestStatusColor(contestHealthDTO);

                // Get details for TC Direct Project
                long tcDirectProjectId = resultContainer.getLongItem(i, "tc_direct_project_id");
                String tcDirectProjectName = resultContainer.getStringItem(i, "tc_direct_project_name");

                final ProjectBriefDTO project;
                if (projects.containsKey(tcDirectProjectId)) {
                    project = projects.get(tcDirectProjectId);
                } else {
                    project = createProject(tcDirectProjectId, tcDirectProjectName);
                    projects.put(tcDirectProjectId, project);
                }

                // Get details for contest
                long contestId = resultContainer.getLongItem(i, "contest_id");
                String contestName = resultContainer.getStringItem(i, "contest_name");
                String contestType = resultContainer.getStringItem(i, "contest_type");

                ContestBriefDTO contestBrief = createContest(contestId, contestName, project, !isStudio, contestType);

                // Map contest to health status
                contests.put(contestBrief, contestHealthDTO);
        }

        return contests;
    }

    /**
     * <p>Sets the specified contest health DTO with status of running phase (Late, Closing, Running).</p>
     *
     * @param dto a <code>ContestHealthDTO</code> providing the DTO to be set with current phase status.
     * @param currentPhaseEndTime a <code>Date</code> providing the end time for current phase or <code>null</code> if
     *        there is no active phase for contest.
     * @since 2.5.0
     */
    private static void setCurrentPhaseStatus(ContestHealthDTO dto, Date currentPhaseEndTime) {
        if (currentPhaseEndTime != null) {
            Date now = new Date();
            if (now.compareTo(currentPhaseEndTime) > 0) {
                dto.setCurrentPhaseStatus(RunningPhaseStatus.LATE);
            } else {
                long diff = currentPhaseEndTime.getTime() - now.getTime();
                long hoursLeft = diff / (3600 * 1000);
                if (hoursLeft < 2) {
                    dto.setCurrentPhaseStatus(RunningPhaseStatus.CLOSING);
                } else {
                    dto.setCurrentPhaseStatus(RunningPhaseStatus.RUNNING);
                }
            }
        }
    }

    /**
     * <p>Sets the specified contest health DTO with status of registration phase (Healthy, Poor, Less Than Ideal).</p>
     *
     * @param dto a <code>ContestHealthDTO</code> providing the DTO to be set with registration phase status.
     * @param reliabilityTotal a <code>double</code> providing the total reliability rating for registered users.
     * @param registrationPhaseStatus a <code>long</code> referencing the current status of registration phase.
     * @since 2.5.0
     */
    private static void setRegistrationPhaseStatus(ContestHealthDTO dto, double reliabilityTotal,
                                                   long registrationPhaseStatus) {
        if (registrationPhaseStatus == 2) {
            if (reliabilityTotal >= 200) {
                dto.setRegistrationStatus(RegistrationStatus.HEALTHY);
            } else {
                dto.setRegistrationStatus(RegistrationStatus.REGISTRATION_LESS_IDEAL_ACTIVE);
            }
        } else if (registrationPhaseStatus == 3) {
            if (reliabilityTotal >= 200) {
                dto.setRegistrationStatus(RegistrationStatus.HEALTHY);
            } else if (reliabilityTotal >= 100) {
                dto.setRegistrationStatus(RegistrationStatus.REGISTRATION_LESS_IDEAL_CLOSED);
            } else {
                dto.setRegistrationStatus(RegistrationStatus.REGISTRATION_POOR);
            }
        }
    }

    /**
     * <p>Sets the specified contest health DTO with status of review sign-up (All Filled, Danger, Warning).</p>
     *
     * @param dto a <code>ContestHealthDTO</code> providing the DTO to be set with review sign-up status.
     * @param hoursLeft a <code>long</code> providing the hours left for review phase to complete.
     * @param requiredReviewersCount an <code>int</code> providing the required number of reviewers for contest.
     * @param registeredReviewersCount an <code>int</code> providing the actual number of registered reviewers for
     *        contest.
     * @since 2.5.0
     */
    private static void setReviewSignupStatus(ContestHealthDTO dto, long hoursLeft, int requiredReviewersCount,
                                              int registeredReviewersCount) {
        if (requiredReviewersCount > registeredReviewersCount) {
            if (hoursLeft < 24) {
                dto.setReviewersSignupStatus(ReviewersSignupStatus.REVIEW_POSITIONS_NON_FILLED_DANGER);
            } else {
                dto.setReviewersSignupStatus(ReviewersSignupStatus.REVIEW_POSITIONS_NON_FILLED_WARNING);
            }
        } else {
            dto.setReviewersSignupStatus(ReviewersSignupStatus.ALL_REVIEW_POSITIONS_FILLED);
        }
    }

    /**
     * <p>Gets the issues of the contest. The result is returned in a ContestIssuesTrackingDTO object.</p>
     *
     * @param contestId the id of the contest
     * @param isStudio the boolean to tell if the contest is a studio contest
     * @return the ContestIssuesTrackingDTO object
     * @throws Exception if an unexpected error occurs.
     * @since 2.6.2
     */
    public static ContestIssuesTrackingDTO getContestIssues(long contestId, boolean isStudio) throws Exception {

        // get issues and bug races from the Jira RPC soap service
        List<TcJiraIssue> results = JiraRpcServiceWrapper.getIssuesForContest(contestId, isStudio);

        // use one list to store issues, another list to store bug races
        List<TcJiraIssue> issues = new ArrayList<TcJiraIssue>();
        List<TcJiraIssue> bugRaces = new ArrayList<TcJiraIssue>();

        // get the jira project name for bug race from the configuration. It will be used to tell which issue
        // is a bug race
        String bugRaceProjectName = ConfigUtils.getIssueTrackingConfig().getBugRaceProjectName().trim().toLowerCase();


        // filter out the jira issues and bug races
        for (TcJiraIssue item : results) {
            if(item.getProjectName().trim().toLowerCase().equals(bugRaceProjectName)) {
                bugRaces.add(item);
            } else {
                issues.add(item);
            }
        }

        // populate result
        ContestIssuesTrackingDTO result = new ContestIssuesTrackingDTO();
        result.setContestId(contestId);
        result.setIssues(issues);
        result.setBugRaces(bugRaces);

        return result;
    }


    /**
     * <p>Gets the issues of the direct project. The list of contests belong to the project will be passed in.</p>
     *
     * @param contests the list of the contests
     * @return map of contest to contest issues.
     * @throws Exception if an unexpected error occurs.
     * @since 2.6.2
     */
    public static Map<ContestBriefDTO, ContestIssuesTrackingDTO> getDirectProjectIssues(List<? extends ContestBriefDTO> contests) throws Exception {

        // Gets result from jira service
        List<TcJiraIssue> issues = JiraRpcServiceWrapper.getIssuesForDirectProject(contests);

        // Creates map to store result
        Map<ContestBriefDTO, ContestIssuesTrackingDTO> issuesMap = new HashMap<ContestBriefDTO, ContestIssuesTrackingDTO>();

        // Creates another assistant map
        Map<Long, ContestIssuesTrackingDTO> idsMap = new HashMap<Long, ContestIssuesTrackingDTO>();

        // Initializes the maps first
        for(ContestBriefDTO contest : contests) {
            ContestIssuesTrackingDTO contestIssues = new ContestIssuesTrackingDTO();
            contestIssues.setBugRaces(new ArrayList<TcJiraIssue>());
            contestIssues.setIssues(new ArrayList<TcJiraIssue>());
            contestIssues.setContestId(contest.getId());
            issuesMap.put(contest, contestIssues);
            idsMap.put(contest.getId(), contestIssues);
        }

        // Puts result into the map
        for(TcJiraIssue issue : issues) {
            Long projectId = issue.getProjectID();
            Long studioId = issue.getStudioID();
            ContestIssuesTrackingDTO dto;

            if(issue.isBugRace()) {
                if(projectId != null) {
                    dto = idsMap.get(projectId);
                    if (dto != null) {
                        dto.getBugRaces().add(issue);
                    }
                }
                if (studioId != null) {
                     dto = idsMap.get(studioId);
                     if (dto != null) {
                        dto.getBugRaces().add(issue);
                     }
                }
            } else {
                 if(projectId != null) {
                    dto = idsMap.get(projectId);
                    if (dto != null) {
                        dto.getIssues().add(issue);
                    }
                }
                if (studioId != null) {
                    dto = idsMap.get(studioId);
                    if (dto != null) {
                        dto.getIssues().add(issue);
                    }
                }
            }


        }

        return issuesMap;
    }
}

