/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.topcoder.direct.services.view.dto.ActivityDTO;
import com.topcoder.direct.services.view.dto.ActivityType;
import com.topcoder.direct.services.view.dto.PermissionType;
import com.topcoder.direct.services.view.dto.contest.ContestBriefDTO;
import com.topcoder.direct.services.view.dto.contest.ContestDTO;
import com.topcoder.direct.services.view.dto.contest.ContestRegistrantDTO;
import com.topcoder.direct.services.view.dto.contest.ContestStatus;
import com.topcoder.direct.services.view.dto.contest.ContestType;
import com.topcoder.direct.services.view.dto.dashboard.DashboardContestSearchResultDTO;
import com.topcoder.direct.services.view.dto.dashboard.DashboardMemberSearchResultDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.dto.project.ProjectContestDTO;

/**
 * <p>This class serves as a collection of mock data to be used for purposes of this <code>TopCoder Direct</code> system
 * assembly.</p>
 *
 * <p>Sub-sequent assemblies must remove this class and have the <code>DataProvider</code> to call respective services
 * for getting the necessary data.</p>
 *
 * <p>
 * Version 1.1 - Direct Search Assembly - remove or adjust some mock data implementation
 * </p>
 *
 * @author isv, BeBetter
 * @version 1.1
 */
public class MockData {

    private static Map<Long, String> users = new HashMap<Long, String>();

    private static Map<Long, List<ProjectBriefDTO>> projects = new HashMap<Long, List<ProjectBriefDTO>>();

    private static Map<Long, List<ContestDTO>> contests = new HashMap<Long, List<ContestDTO>>();

    private static int projectIdGenerator = 3;

    static {
        users.put(132456L, "heffan");
        List<ProjectBriefDTO> heffanProjects = new ArrayList<ProjectBriefDTO>();
        heffanProjects.add(createUserProject(1, "TopCoder 2010"));
        heffanProjects.add(createUserProject(2, "Univision"));
        heffanProjects.add(createUserProject(3, "Online Review"));
        projects.put(132456L, heffanProjects);

        List<ContestDTO> topCoder2010Contests = new ArrayList<ContestDTO>();
        topCoder2010Contests.add(createContest(1, heffanProjects.get(0), "2010 TCO Website Release Assembly"));
        topCoder2010Contests.add(createContest(2, heffanProjects.get(0), "TopCoder 2010 Home Page v1 1.0"));
        topCoder2010Contests.add(createContest(3, heffanProjects.get(0), "TCO10 Website Prototype 1.0"));
        contests.put(1L, topCoder2010Contests);

        List<ContestDTO> univisionContests = new ArrayList<ContestDTO>();
        univisionContests.add(createContest(4, heffanProjects.get(1), "Univision Web Service enhancement 1.0"));
        univisionContests.add(createContest(5, heffanProjects.get(1), "Univision Guess and Win Module Assembly 1.0"));
        contests.put(2L, univisionContests);

        List<ContestDTO> onlineReviewContests = new ArrayList<ContestDTO>();
        onlineReviewContests.add(createContest(6, heffanProjects.get(2), "LDAP Authentication Release Assembly 1.0"));
        onlineReviewContests.add(createContest(7, heffanProjects.get(2), "Member Payment Improvements Release Assembly 1.0"));
        onlineReviewContests.add(createContest(8, heffanProjects.get(2), "Contest Dependency Automation 1.0"));
        onlineReviewContests.add(createContest(9, heffanProjects.get(2), "Online Review Project Management Console Release Assembly 1.0"));
        onlineReviewContests.add(createContest(10, heffanProjects.get(2), "Online Review End Of Project Analysis 1.0"));
        contests.put(3L, onlineReviewContests);

        users.put(124916L, "Yoshi");
        users.put(124856L, "wyzmo");
        users.put(132457L, "super");
        users.put(132458L, "user");
        users.put(124764L, "Hung");
    }

    private static long[] userIds = new long[] {132456L, 124916L, 124856L, 132457L, 132458L, 124764L};

    private static String[] contestTypeNames = new String[] {ContestType.WIREFRAME.getName(),
                                                             ContestType.WEB_DESIGN.getName(),
                                                             ContestType.LOGO_DESIGN.getName(),
                                                             ContestType.IDEA_GENERATION.getName(),
                                                             ContestType.ASSEMBLY.getName(),
                                                             ContestType.TEST_SCENARIOS.getName()};

    public static Map<ProjectBriefDTO, List<ActivityDTO>> getLatestProjectActivities(long userId) {
        Random r = new Random(12121);
        Map<ProjectBriefDTO, List<ActivityDTO>> data = new HashMap<ProjectBriefDTO, List<ActivityDTO>>();
        ActivityType[] types = ActivityType.values();
        List<ProjectBriefDTO> userProjects = projects.get(userId);
        if (userProjects != null) {
            for (ProjectBriefDTO project : userProjects) {
                List<ActivityDTO> activities = new ArrayList<ActivityDTO>();
                List<ContestDTO> userContests = contests.get(project.getId());
                if (userContests != null) {
                    for (ContestBriefDTO contest : userContests) {
                        Date date = new Date(System.currentTimeMillis() - contest.getId() * 12 * 3600 * 1000L);
                        long originatorId = userIds[r.nextInt(10000) % userIds.length];
                        activities.add(createActivity(contest, date, users.get(originatorId), originatorId,
                                                      types[r.nextInt(10000) % types.length]));
                    }
                }

                data.put(project, activities);
            }
        }
        return data;
    }

    public static Map<ContestBriefDTO, List<ActivityDTO>> getLatestProjectContestActivities(long projectId) {
        Random r = new Random(12121);
        Map<ContestBriefDTO, List<ActivityDTO>> data = new HashMap<ContestBriefDTO, List<ActivityDTO>>();
        ActivityType[] types = ActivityType.values();
        ProjectBriefDTO project = getProject(projectId);
        if (project != null) {
            List<ActivityDTO> activities = new ArrayList<ActivityDTO>();
            List<ContestDTO> userContests = contests.get(project.getId());
            if (userContests != null) {
                for (ContestBriefDTO contest : userContests) {
                    Date date = new Date(System.currentTimeMillis() - contest.getId() * 12 * 3600 * 1000L);
                    long originatorId = userIds[r.nextInt(10000) % userIds.length];
                    activities.add(createActivity(contest, date, users.get(originatorId), originatorId,
                                                  types[r.nextInt(10000) % types.length]));
                    data.put(contest, activities);
                }
            }
        }
        return data;
    }

    public static List<ActivityDTO> getUpcomingActivities(long userId) {
        Random r = new Random(12121);
        List<ActivityDTO> data = new ArrayList<ActivityDTO>();
        ActivityType[] types = ActivityType.values();
        List<ProjectBriefDTO> userProjects = projects.get(userId);
        if (userProjects != null) {
            for (ProjectBriefDTO project : userProjects) {
                List<ContestDTO> userContests = contests.get(project.getId());
                if (userContests != null) {
                    for (ContestBriefDTO contest : userContests) {
                        Date date = new Date(System.currentTimeMillis() + contest.getId() * 12 * 3600 * 1000L);
                        long originatorId = userIds[r.nextInt(10000) % userIds.length];
                        data.add(createActivity(contest, date, users.get(originatorId), originatorId,
                                                types[r.nextInt(10000) % types.length]));
                    }
                }
            }
        }
        return data;
    }

    public static void createProject(long userId, String projectName, String projectDescription) {
        ProjectBriefDTO project = new ProjectBriefDTO();
        project.setId(++projectIdGenerator);
        project.setName(projectName);
        List<ProjectBriefDTO> userProjects = projects.get(userId);
        if (userProjects == null) {
            userProjects = new ArrayList<ProjectBriefDTO>();
            projects.put(userId, userProjects);
        }
        userProjects.add(project);
    }

    private static ProjectBriefDTO createUserProject(long projectId, String projectName) {
        ProjectBriefDTO project = new ProjectBriefDTO();
        project.setId(projectId);
        project.setName(projectName);
        return project;
    }

    private static ContestDTO createContest(long contestId, ProjectBriefDTO project, String title) {
        ContestDTO contest = new ContestDTO();
        contest.setId(contestId);
        contest.setProject(project);
        contest.setTitle(title);

        contest.setContestType(ContestType.values()[(int) ((ContestType.values().length - 1 )* Math.random())]);
        return contest;
    }

    private static ActivityDTO createActivity(ContestBriefDTO contest, Date date, String handle, long userId,
                                              ActivityType type) {
        ActivityDTO activity = new ActivityDTO();
        activity.setContest(contest);
        activity.setDate(date);
        activity.setOriginatorHandle(handle);
        activity.setOriginatorId(userId);
        activity.setType(type);
        return activity;
    }

    public static List<DashboardContestSearchResultDTO> searchUserContests(long currentUserId, String searchFor) {
        Random r = new Random(12121);
        List<DashboardContestSearchResultDTO> result = new ArrayList<DashboardContestSearchResultDTO>();
        List<ProjectBriefDTO> userProjects = projects.get(currentUserId);
        if (userProjects != null) {
            for (ProjectBriefDTO project : userProjects) {
                List<ContestDTO> userContests = contests.get(project.getId());
                if (userContests != null) {
                    for (ContestBriefDTO contest : userContests) {
                        DashboardContestSearchResultDTO dto = new DashboardContestSearchResultDTO();
                        dto.setContest(contest);
                        dto.setForumPostsNumber(r.nextInt(10));
                        dto.setRegistrantsNumber(r.nextInt(10));
                        dto.setSubmissionsNumber(r.nextInt(10));
                        ContestType[] types = ContestType.values();
                        dto.setContestType(types[r.nextInt(10000) % types.length].getName());
                        dto.setStartTime(new Date());
                        dto.setEndTime(new Date(System.currentTimeMillis() + r.nextInt(5) * 24 * 3600 * 1000L));
                        ContestStatus[] statuses = ContestStatus.values();
                        dto.setStatus(statuses[r.nextInt(10000) % statuses.length]);
                        result.add(dto);
                    }
                }
            }
        }
        return result;
    }

    public static List<DashboardMemberSearchResultDTO> searchUserProjectMembers(long currentUserId, String searchFor) {
        Random r = new Random(12121);
        List<DashboardMemberSearchResultDTO> result = new ArrayList<DashboardMemberSearchResultDTO>();
        List<ProjectBriefDTO> userProjects = projects.get(currentUserId);
        if (userProjects != null) {
            for (ProjectBriefDTO project : userProjects) {
                List<ContestDTO> userContests = contests.get(project.getId());
                if (userContests != null) {
                    for (ContestBriefDTO contest : userContests) {
                        for (int i = 0; i < userIds.length; i++) {
                            long userId = userIds[i];
                            DashboardMemberSearchResultDTO dto = new DashboardMemberSearchResultDTO();
                            dto.setContest(contest);
                            dto.setHandle(users.get(userId));
                            dto.setUserId(userId);
                            PermissionType[] permissions = PermissionType.values();
                            dto.setPermissions(new PermissionType[] {permissions[r.nextInt(10000) % permissions.length]});
                            result.add(dto);
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * <p>Gets the list of projects associated with specified user.</p>
     *
     * @param userId a <code>long</code> providing the user ID.
     * @return a <code>List</code> listing the details for user projects.
     */
    public static List<ProjectBriefDTO> getUserProjects(long userId) {
        return projects.get(userId);
    }


    public static ProjectBriefDTO getProject(long projectId) {
        Iterator<Long> iterator = projects.keySet().iterator();
        while (iterator.hasNext()) {
            Long userId = iterator.next();
            List<ProjectBriefDTO> userProjects = projects.get(userId);
            for (ProjectBriefDTO p : userProjects) {
                if (p.getId() == projectId) {
                    return p;
                }
            }
        }
        return null;
    }

    public static List<ProjectContestDTO> getProjectContests(long projectId) {
        Random r = new Random(12121);
        List<ProjectContestDTO> result = new ArrayList<ProjectContestDTO>();
        ProjectBriefDTO project = getProject(projectId);
        if (project != null) {
            List<ContestDTO> userContests = contests.get(project.getId());
            if (userContests != null) {
                for (ContestBriefDTO contest : userContests) {
                    ProjectContestDTO dto = new ProjectContestDTO();
                    dto.setContest(contest);
                    dto.setForumPostsNumber(r.nextInt(10));
                    dto.setRegistrantsNumber(r.nextInt(10));
                    dto.setSubmissionsNumber(r.nextInt(10));
                    dto.setStartTime(new Date());
                    dto.setEndTime(new Date(System.currentTimeMillis() + r.nextInt(10) * 3600 * 1000L));
                    ContestStatus[] statuses = ContestStatus.values();
                    dto.setStatus(statuses[r.nextInt(10000) % statuses.length]);
                    ContestType[] types = ContestType.values();
                    dto.setContestType(types[r.nextInt(10000) % types.length]);
                    result.add(dto);
                }
            }
        }
        return result;
    }

    public static ContestDTO getContest(long contestId) {
        Iterator<Long> iterator = contests.keySet().iterator();
        while (iterator.hasNext()) {
            Long projectId = iterator.next();
            List<ContestDTO> userContests = contests.get(projectId);
            for (ContestDTO c : userContests) {
                if (c.getId() == contestId) {
                    return c;
                }
            }
        }
        return null;
    }

    /**
     * <p>Gets the list of registrants to specified contest.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest.
     * @return a <code>List</code> providing the details for contest registrants.
     */
    public static List<ContestRegistrantDTO> getContestRegistrants(long contestId) {
        List<ContestRegistrantDTO> data = new ArrayList<ContestRegistrantDTO>();
        Iterator<Long> iterator = users.keySet().iterator();
        while (iterator.hasNext()) {
            Long userId = iterator.next();
            String handle = users.get(userId);
            ContestRegistrantDTO dto = new ContestRegistrantDTO();
            dto.setRated(handle.equals("heffan"));
            dto.setContest(getContest(contestId));
            dto.setRegistrationDate(new Date(System.currentTimeMillis() - userId * 1000L));
            dto.setSubmissionDate(handle.equals("heffan") ? new Date() : null);
            dto.setUserId(userId);
            dto.setUsername(handle);
            data.add(dto);
        }
        return data;
    }
}
