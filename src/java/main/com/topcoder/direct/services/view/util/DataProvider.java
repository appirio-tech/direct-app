/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;
import java.util.LinkedHashMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.Map.Entry;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.direct.services.view.dto.*;
import com.topcoder.direct.services.view.dto.contest.ContestCopilotDTO;
import com.topcoder.direct.services.view.dto.contest.ContestDashboardDTO;
import com.topcoder.direct.services.view.dto.contest.ContestReceiptDTO;
import com.topcoder.direct.services.view.dto.contest.DependenciesStatus;
import com.topcoder.direct.services.view.dto.contest.DependencyDTO;
import com.topcoder.direct.services.view.dto.contest.ForumPostDTO;
import com.topcoder.direct.services.view.dto.contest.PhasedContestDTO;
import com.topcoder.direct.services.view.dto.contest.ProjectPhaseDTO;
import com.topcoder.direct.services.view.dto.contest.RegistrationStatus;
import com.topcoder.direct.services.view.dto.contest.ReviewersSignupStatus;
import com.topcoder.direct.services.view.dto.contest.RunningPhaseStatus;
import com.topcoder.direct.services.view.dto.contest.SoftwareContestSubmissionsDTO;
import com.topcoder.direct.services.view.dto.contest.SoftwareSubmissionDTO;
import com.topcoder.direct.services.view.dto.contest.SoftwareSubmissionReviewDTO;
import com.topcoder.direct.services.view.dto.dashboard.EnterpriseDashboardDetailedProjectStatDTO;
import com.topcoder.direct.services.view.dto.dashboard.EnterpriseDashboardProjectStatDTO;
import com.topcoder.direct.services.view.dto.dashboard.EnterpriseDashboardStatType;
import com.topcoder.direct.services.view.dto.dashboard.costreport.CostDetailsDTO;
import com.topcoder.direct.services.view.dto.dashboard.pipeline.PipelineDraftsRatioDTO;
import com.topcoder.direct.services.view.dto.dashboard.pipeline.PipelineScheduledContestsViewType;
import com.topcoder.security.RolePrincipal;
import com.topcoder.service.project.IllegalArgumentFault;
import com.topcoder.service.project.ProjectData;
import com.topcoder.shared.dataAccess.resultSet.TCResultItem;
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
import com.topcoder.direct.services.view.dto.copilot.CopilotBriefDTO;
import com.topcoder.direct.services.view.dto.copilot.CopilotContestDTO;
import com.topcoder.direct.services.view.dto.copilot.CopilotProjectDTO;
import com.topcoder.direct.services.view.dto.dashboard.DashboardContestSearchResultDTO;
import com.topcoder.direct.services.view.dto.dashboard.DashboardMemberSearchResultDTO;
import com.topcoder.direct.services.view.dto.dashboard.DashboardProjectSearchResultDTO;
import com.topcoder.direct.services.view.dto.dashboard.EnterpriseDashboardContestStatDTO;
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
 *
 * @author isv, BeBetter, tangzx, xjtufreeman, TCSDEVELOPER
 * @version 2.4.0
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
        final Map<Long, TypedContestBriefDTO> contests = new HashMap<Long, TypedContestBriefDTO>();//here

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("direct_latest_activities");
        final int recordNum = resultContainer.size();
        for (int i = 0; i < recordNum; i++) {
            String activityTypeText = resultContainer.getStringItem(i, "activity_type");
            long tcDirectProjectId = resultContainer.getLongItem(i, "tc_direct_project_id");
            String tcDirectProjectName = resultContainer.getStringItem(i, "tc_direct_project_name");
            long contestId = resultContainer.getLongItem(i, "contest_id");
            String contestName = resultContainer.getStringItem(i, "contest_name");
            String contestType = resultContainer.getStringItem(i, "contest_type");
            long contestTypeId = resultContainer.getLongItem(i, "contest_type_id"); // BUGR-3913
            Boolean isStudio = (resultContainer.getIntItem(i, "is_studio") == 1);

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

            final TypedContestBriefDTO contest;
            if (contests.containsKey(contestId)) {
                contest = contests.get(contestId);
            } else {
                contest = createTypedContest(contestId, contestName, project, ContestType.forIdAndFlag(contestTypeId, isStudio), null, !isStudio);//here
                contests.put(contestId, contest);
            }

            ActivityType activityType = ActivityType.forName(activityTypeText);
            ActivityDTO activity = createActivity(contest, date, originatorHandle, originatorId, activityType);
            projectActivities.add(activity);
            
        }

        LatestActivitiesDTO result = new LatestActivitiesDTO();


        // start bugr-3901
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
        // end bugr-3901

        result.setActivities(sortedActivities);
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
        final Map<Long, TypedContestBriefDTO> contests = new HashMap<Long, TypedContestBriefDTO>();//here
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
            Boolean isStudio = (resultContainer.getIntItem(i, "is_studio") == 1);

            final ProjectBriefDTO project;
            if (!projects.containsKey(tcDirectProjectId)) {
                project = createProject(tcDirectProjectId, tcDirectProjectName);
                projects.put(tcDirectProjectId, project);
            } else {
                project = projects.get(tcDirectProjectId);
            }

	    
            TypedContestBriefDTO contest;
            if (contests.containsKey(contestId)) {
                contest = contests.get(contestId);
            } else {
                contest = createTypedContest(contestId, contestName, project, null, null, !isStudio);
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
     * @param projectId a <code>long</code> providing the ID for requested project.
     * @return a <code>ProjectStatsDTO</code> providing the stats for requested project.
     */
    public static ProjectStatsDTO getProjectStats(TCSubject tcSubject, long projectId) throws Exception {

        ProjectStatsDTO stats = new ProjectStatsDTO();
        ProjectBriefDTO project = new ProjectBriefDTO();

        double draftFee = 0, activeFee = 0, scheduledFee = 0, finishedFee = 0;
        int draftNum = 0, activeNum = 0, scheduledNum = 0, finishedNum = 0;
    
        String tcDirectProjectName = "";

        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("direct_my_contests");
        request.setProperty("uid", String.valueOf(tcSubject.getUserId()));
        request.setProperty("tcdirectid", String.valueOf(projectId));

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("direct_my_contests");
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

        // contests fees
        stats.setFeesFinalized(finishedFee);
        stats.setFeesRunning(activeFee);
        stats.setFeesDraft(draftFee);
        stats.setFeesScheduled(scheduledFee);
        

        stats.setProject(project);

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

            ContestBriefDTO contestBrief = createContest(contestId, contestName, project, !isStudio);
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

            ContestBriefDTO contestBrief = createContest(contestId, contestName, project, !isStudio);
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
            submission.setSubmissionId(submissionRow.getLongItem("submission_id"));
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
            projectStatDTO.setTotalMemberCost(row.getDoubleItem("total_member_cost"));
            projectStatDTO.setAverageMemberCostPerContest(row.getDoubleItem("average_member_cost"));
            projectStatDTO.setTotalContestFee(row.getDoubleItem("total_contest_fee"));
            projectStatDTO.setAverageContestFeePerContest(row.getDoubleItem("average_contest_fee"));
            projectStatDTO.setAverageFulfillment(row.getDoubleItem("fullfillment"));
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
                projectStatDTO.setTotalMemberCost(0);
                projectStatDTO.setAverageCostPerContest(0);
                projectStatDTO.setTotalContestFee(0);
                projectStatDTO.setAverageContestFeePerContest(0);
                projectStatDTO.setAverageFulfillment(0);
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
     * @return a <code>List</code> of statistical data.
     * @throws Exception if an unexpected error occurs.
     * @throws IllegalArgumentException if any of specified arrays is <code>null</code> or empty.
     * @since 2.1.2
     */
    public static List<EnterpriseDashboardDetailedProjectStatDTO> getEnterpriseStatsForProject(long[] projectIds,
        long[] projectCategoryIDs, Date startDate, Date endDate, long[] clientIds, long[] billingAccountIds)
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
     * @return a <code>List</code> of statistical data.
     * @throws Exception if an unexpected error occurs.
     * @throws IllegalArgumentException if specified <code>projectCategoryIDs</code> array is <code>null</code> or
     *         empty.
     * @since 2.1.2
     */
    public static List<EnterpriseDashboardDetailedProjectStatDTO> getEnterpriseStatsForAllProjects(
        long[] projectCategoryIDs, Date startDate, Date endDate) throws Exception {
        
        if ((projectCategoryIDs == null) || (projectCategoryIDs.length == 0)) {
            throw new IllegalArgumentException("Project category IDs are not specified");
        }

        String projectCategoryIDsList = concatenate(projectCategoryIDs, ", ");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        List<EnterpriseDashboardDetailedProjectStatDTO> data
            = new ArrayList<EnterpriseDashboardDetailedProjectStatDTO>();

        final String queryName = "direct_dashboard_enterprise_detailed_stats_overall";
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
     * @param user a <code>TCSubject</code> referencing the user.
     * @return a <code>List</code> of <code>Copilot Posting</code> contests accessible to specified user.
     * @throws Exception if an unexpected error occurs.
     * @since 2.1.7 
     */
    public static List<PhasedContestDTO> getCopilotPostingContests(TCSubject user) throws Exception {
        StringBuilder contestIds = new StringBuilder();
        List<PhasedContestDTO> result = new ArrayList<PhasedContestDTO>();
        List<DashboardContestSearchResultDTO> contests = searchUserContests(user, null, null, null);
        for (DashboardContestSearchResultDTO contest : contests) {
            if (ContestType.COPILOT_POSTING.getName().equals(contest.getContestType())) {
                PhasedContestDTO dto = new PhasedContestDTO();
                dto.setId(contest.getContest().getId());
                dto.setContestType(ContestType.COPILOT_POSTING);
                dto.setProject(contest.getContest().getProject());
                dto.setSoftware(true);
                dto.setStatus(contest.getStatus());
                dto.setTitle(contest.getContest().getTitle());

                result.add(dto);
                if (contestIds.length() > 0) {
                    contestIds.append(", ");
                }
                contestIds.append(contest.getContest().getId());
            }
        }

        // Get current phases for Copilot Posting contests
        if (!result.isEmpty()) {
            DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);

            Request request = new Request();
            request.setContentHandle("current_project_phases");
            request.setProperty("pids", contestIds.toString());

            Map<String, ResultSetContainer> results = dataAccessor.getData(request);

            // Analyze current and next phases
            final ResultSetContainer projectPhases = results.get("current_project_phases");
            for (ResultSetContainer.ResultSetRow row : projectPhases) {
                long projectId = row.getLongItem("project_id");
                String phaseTypeName = row.getStringItem("phase_type_name");
                ProjectPhaseDTO phase = new ProjectPhaseDTO();
                phase.setPhaseName(phaseTypeName);

                for (PhasedContestDTO contest : result) {
                    if (contest.getId() == projectId) {
                        if (contest.getCurrentPhases() == null) {
                            contest.setCurrentPhases(new ArrayList<ProjectPhaseDTO>());
                        }
                        contest.getCurrentPhases().add(phase);
                    }
                }
            }
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
     * @param tcSubject the tcSubject
     * @param projects  the list of direct projects
     * @return the list of billing projects.
     * @throws Exception if any error occurs.
     */
    public static Map<String, Object> getDashboardClientBillingProjectMappingsForAdmin(TCSubject tcSubject, List<ProjectData> projects)
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

        if (DirectUtils.isTcOperations(tcSubject)) {
            System.out.println("query the cockpit admin...");
            request.setContentHandle("admin_client_billing_accounts");
            resultContainer = dataAccess.getData(request).get(
                    "admin_client_billing_accounts");
        } else {
            System.out.println("query non admin...");
            long[] projectIds = new long[projects.size()];
            int index = 0;
            for(ProjectData data : projects) {
                projectIds[index] = data.getProjectId();
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
        contestReceipt.setFinished(result.getStringItem(row, "status").equals("Finished"));
        
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
    private static ContestBriefDTO createContest(long id, String name, ProjectBriefDTO project, Boolean isSoftware) {
        ContestBriefDTO contest = new ContestBriefDTO();
        contest.setId(id);
        contest.setTitle(name);
        contest.setProject(project);
        contest.setSoftware(isSoftware);
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
}
