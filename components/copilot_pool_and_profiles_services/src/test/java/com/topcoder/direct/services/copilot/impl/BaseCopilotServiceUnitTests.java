/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.copilot.CopilotServiceException;
import com.topcoder.direct.services.copilot.CopilotServiceInitializationException;
import com.topcoder.direct.services.copilot.TestHelper;
import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.CopilotProjectPlanDAO;
import com.topcoder.direct.services.copilot.dao.GenericDAO;
import com.topcoder.direct.services.copilot.dao.UtilityDAO;
import com.topcoder.direct.services.copilot.dto.ContestTypeStat;
import com.topcoder.direct.services.copilot.model.IdentifiableEntity;
import com.topcoder.direct.services.copilot.model.PlannedContest;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectManager;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.project.link.ProjectLink;
import com.topcoder.management.project.link.ProjectLinkManager;
import com.topcoder.management.project.link.ProjectLinkType;

/**
 * Unit tests for {@link BaseCopilotService}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class BaseCopilotServiceUnitTests {

    /**
     * Represents {@link BaseCopilotService} instance for testing.
     */
    private BaseCopilotService<IdentifiableEntity> instance;

    /**
     * Sets up the test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        instance = new BaseCopilotService<IdentifiableEntity>() {
        };
    }

    /**
     * Tears down the test environment.
     *
     * @throws Exception to JUnit
     */
    @After
    public void tearDown() throws Exception {
        instance = null;
    }

    /**
     * Accuracy test for {@link BaseCopilotService#BaseCopilotService()}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testCtor() throws Exception {
        assertNotNull("Unable to instantiate this object", instance);
    }

    /**
     * Accuracy test for {@link BaseCopilotService#checkInit}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testCheckInit() throws Exception {
        long activeCopilotProjectStatusId = 1;
        long activeProjectStatusId = 1;
        List<Long> failedProjectStatusIds = Arrays.asList(1l);
        CopilotProjectPlanDAO copilotProjectPlanDAO = EasyMock.createMock(CopilotProjectPlanDAO.class);
        UtilityDAO utilityDAO = EasyMock.createMock(UtilityDAO.class);
        ProjectManager projectManager = EasyMock.createMock(ProjectManager.class);
        ProjectLinkManager projectLinkManager = EasyMock.createMock(ProjectLinkManager.class);
        instance.setActiveCopilotProjectStatusId(activeCopilotProjectStatusId);
        instance.setActiveProjectStatusId(activeProjectStatusId);
        instance.setFailedProjectStatusIds(failedProjectStatusIds);
        instance.setCopilotProjectPlanDAO(copilotProjectPlanDAO);
        instance.setUtilityDAO(utilityDAO);
        instance.setProjectManager(projectManager);
        instance.setProjectLinkManager(projectLinkManager);
        instance.setGenericDAO(EasyMock.createMock(GenericDAO.class));
        ProjectCategory[] projectCategories = new ProjectCategory[1];
        projectCategories[0] = new ProjectCategory(1l, "a", new ProjectType(1, "name"));
        projectCategories[0].setId(1l);
        EasyMock.expect(projectManager.getAllProjectCategories()).andReturn(projectCategories);
        EasyMock.replay(projectManager);
        instance.checkInit();
        Map<Long, ProjectCategory> map =
            (Map<Long, ProjectCategory>) TestHelper
                .getField(BaseCopilotService.class, instance, "projectCategories");
        assertTrue("size should be 1.", map.size() == 1);
        assertTrue("should be same with projectCategories[0]", map.get(1l) == projectCategories[0]);
        EasyMock.verify(projectManager);
    }

    /**
     * Failure test for {@link BaseCopilotService#checkInit}.Expected CopilotServiceInitializationException.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = CopilotServiceInitializationException.class)
    public void testCheckInitFailure1() throws Exception {
        long activeCopilotProjectStatusId = 1;
        long activeProjectStatusId = 1;
        List<Long> failedProjectStatusIds = new ArrayList<Long>();
        CopilotProjectPlanDAO copilotProjectPlanDAO = EasyMock.createMock(CopilotProjectPlanDAO.class);
        UtilityDAO utilityDAO = EasyMock.createMock(UtilityDAO.class);
        ProjectManager projectManager = EasyMock.createMock(ProjectManager.class);
        ProjectLinkManager projectLinkManager = EasyMock.createMock(ProjectLinkManager.class);
        instance.setActiveCopilotProjectStatusId(activeCopilotProjectStatusId);
        instance.setActiveProjectStatusId(activeProjectStatusId);
        instance.setFailedProjectStatusIds(failedProjectStatusIds);
        instance.setCopilotProjectPlanDAO(copilotProjectPlanDAO);
        instance.setUtilityDAO(utilityDAO);
        instance.setProjectManager(projectManager);
        instance.setProjectLinkManager(projectLinkManager);
        instance.setGenericDAO(EasyMock.createMock(GenericDAO.class));
        ProjectCategory[] projectCategories = new ProjectCategory[1];
        projectCategories[0] = new ProjectCategory(1l, "a", new ProjectType(1, "name"));
        projectCategories[0].setId(1l);
        EasyMock.expect(projectManager.getAllProjectCategories()).andReturn(projectCategories);
        EasyMock.replay(projectManager);
        instance.checkInit();
        EasyMock.verify(projectManager);
    }

    /**
     * Failure test for {@link BaseCopilotService#checkInit}.Expected CopilotServiceInitializationException.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = CopilotServiceInitializationException.class)
    public void testCheckInitFailure2() throws Exception {
        long activeCopilotProjectStatusId = 1;
        long activeProjectStatusId = 1;
        CopilotProjectPlanDAO copilotProjectPlanDAO = EasyMock.createMock(CopilotProjectPlanDAO.class);
        UtilityDAO utilityDAO = EasyMock.createMock(UtilityDAO.class);
        ProjectManager projectManager = EasyMock.createMock(ProjectManager.class);
        ProjectLinkManager projectLinkManager = EasyMock.createMock(ProjectLinkManager.class);
        instance.setActiveCopilotProjectStatusId(activeCopilotProjectStatusId);
        instance.setActiveProjectStatusId(activeProjectStatusId);
        instance.setCopilotProjectPlanDAO(copilotProjectPlanDAO);
        instance.setUtilityDAO(utilityDAO);
        instance.setProjectManager(projectManager);
        instance.setProjectLinkManager(projectLinkManager);
        instance.setGenericDAO(EasyMock.createMock(GenericDAO.class));
        ProjectCategory[] projectCategories = new ProjectCategory[1];
        projectCategories[0] = new ProjectCategory(1l, "a", new ProjectType(1, "name"));
        projectCategories[0].setId(1l);
        EasyMock.expect(projectManager.getAllProjectCategories()).andReturn(projectCategories);
        EasyMock.replay(projectManager);
        instance.checkInit();
        EasyMock.verify(projectManager);
    }

    /**
     * Failure test for {@link BaseCopilotService#checkInit}.Expected CopilotServiceInitializationException.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = CopilotServiceInitializationException.class)
    public void testCheckInitFailure3() throws Exception {
        long activeCopilotProjectStatusId = 1;
        long activeProjectStatusId = 1;
        List<Long> failedProjectStatusIds = Arrays.asList(1l);
        CopilotProjectPlanDAO copilotProjectPlanDAO = EasyMock.createMock(CopilotProjectPlanDAO.class);
        UtilityDAO utilityDAO = EasyMock.createMock(UtilityDAO.class);
        ProjectManager projectManager = EasyMock.createMock(ProjectManager.class);
        ProjectLinkManager projectLinkManager = EasyMock.createMock(ProjectLinkManager.class);
        instance.setActiveCopilotProjectStatusId(activeCopilotProjectStatusId);
        instance.setActiveProjectStatusId(activeProjectStatusId);
        instance.setFailedProjectStatusIds(failedProjectStatusIds);
        instance.setCopilotProjectPlanDAO(copilotProjectPlanDAO);
        instance.setUtilityDAO(utilityDAO);
        instance.setProjectManager(projectManager);
        instance.setProjectLinkManager(projectLinkManager);
        instance.setGenericDAO(EasyMock.createMock(GenericDAO.class));
        ProjectCategory[] projectCategories = new ProjectCategory[1];
        projectCategories[0] = new ProjectCategory(1l, "a", new ProjectType(1, "name"));
        projectCategories[0].setId(1l);
        EasyMock.expect(projectManager.getAllProjectCategories()).andThrow(new PersistenceException("error"));
        EasyMock.replay(projectManager);
        instance.checkInit();
        EasyMock.verify(projectManager);
    }

    /**
     * Failure test for {@link BaseCopilotService#checkInit}.Expected CopilotServiceInitializationException.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = CopilotServiceInitializationException.class)
    public void testCheckInitFailure4() throws Exception {
        long activeProjectStatusId = 1;
        List<Long> failedProjectStatusIds = Arrays.asList(1l);
        CopilotProjectPlanDAO copilotProjectPlanDAO = EasyMock.createMock(CopilotProjectPlanDAO.class);
        UtilityDAO utilityDAO = EasyMock.createMock(UtilityDAO.class);
        ProjectManager projectManager = EasyMock.createMock(ProjectManager.class);
        ProjectLinkManager projectLinkManager = EasyMock.createMock(ProjectLinkManager.class);
        instance.setActiveCopilotProjectStatusId(-1);
        instance.setActiveProjectStatusId(activeProjectStatusId);
        instance.setFailedProjectStatusIds(failedProjectStatusIds);
        instance.setCopilotProjectPlanDAO(copilotProjectPlanDAO);
        instance.setUtilityDAO(utilityDAO);
        instance.setProjectManager(projectManager);
        instance.setProjectLinkManager(projectLinkManager);
        instance.setGenericDAO(EasyMock.createMock(GenericDAO.class));
        ProjectCategory[] projectCategories = new ProjectCategory[1];
        projectCategories[0] = new ProjectCategory(1l, "a", new ProjectType(1, "name"));
        projectCategories[0].setId(1l);
        EasyMock.expect(projectManager.getAllProjectCategories()).andReturn(projectCategories);
        EasyMock.replay(projectManager);
        instance.checkInit();
        EasyMock.verify(projectManager);
    }

    /**
     * Accuracy test for {@link BaseCopilotService#getStatForProjectCategory}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testGetStatForProjectCategory1() throws Exception {
        String projectCategoryName = "n1";
        Map<String, ContestTypeStat> contestTypeStats = new HashMap<String, ContestTypeStat>();
        ProjectCategory projectCategory = new ProjectCategory(1l, projectCategoryName, new ProjectType(1l, "n2"));
        ContestTypeStat stat = new ContestTypeStat();
        contestTypeStats.put(projectCategoryName, stat);
        assertTrue("should be same with stat", stat == instance.getStatForProjectCategory(contestTypeStats,
            projectCategory));
    }

    /**
     * Accuracy test for {@link BaseCopilotService#getStatForProjectCategory}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testGetStatForProjectCategory2() throws Exception {
        Map<String, ContestTypeStat> contestTypeStats = new HashMap<String, ContestTypeStat>();
        ProjectCategory projectCategory = new ProjectCategory(1l, "n1", new ProjectType(1l, "n2"));
        assertTrue("should be 1", projectCategory.getId() == instance.getStatForProjectCategory(contestTypeStats,
            projectCategory).getProjectCategoryId());
    }

    /**
     * Accuracy test for {@link BaseCopilotService#countPlannedContestInStats}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testCountPlannedContestInStats() throws Exception {
        String projectCategoryName = "n1";
        long projectCategoryId = 2;
        ProjectCategory projectCategory = new ProjectCategory(1l, projectCategoryName, new ProjectType(1l, "n2"));
        Map<Long, ProjectCategory> projectCategories = new HashMap<Long, ProjectCategory>();
        projectCategories.put(projectCategoryId, projectCategory);
        TestHelper.setField(BaseCopilotService.class, instance, "projectCategories", projectCategories);
        Map<String, ContestTypeStat> contestTypeStats = new HashMap<String, ContestTypeStat>();
        ContestTypeStat stat = new ContestTypeStat();
        stat.setPlannedContests(2);
        contestTypeStats.put(projectCategoryName, stat);
        PlannedContest plannedContest = new PlannedContest();
        plannedContest.setProjectCategoryId(projectCategoryId);
        instance.countPlannedContestInStats(contestTypeStats, plannedContest);
        assertTrue("should be 3", 3 == stat.getPlannedContests());
    }

    /**
     * Failure test for {@link BaseCopilotService#countPlannedContestInStats}.Expected CopilotServiceException.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void testCountPlannedContestInStatsFailure() throws Exception {
        long projectCategoryId = 2;
        Map<Long, ProjectCategory> projectCategories = new HashMap<Long, ProjectCategory>();
        TestHelper.setField(BaseCopilotService.class, instance, "projectCategories", projectCategories);
        Map<String, ContestTypeStat> contestTypeStats = new HashMap<String, ContestTypeStat>();
        PlannedContest plannedContest = new PlannedContest();
        plannedContest.setProjectCategoryId(projectCategoryId);
        instance.countPlannedContestInStats(contestTypeStats, plannedContest);
    }

    /**
     * Accuracy test for {@link BaseCopilotService#calculateContestsStat}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testCalculateContestsStat1() throws Exception {
        UtilityDAO utilityDAO = EasyMock.createMock(UtilityDAO.class);
        ProjectManager projectManager = EasyMock.createMock(ProjectManager.class);
        ProjectLinkManager projectLinkManager = EasyMock.createMock(ProjectLinkManager.class);
        instance.setUtilityDAO(utilityDAO);
        instance.setProjectManager(projectManager);
        instance.setProjectLinkManager(projectLinkManager);

        Project[] projects = new Project[1];
        ProjectStatus projectStatus = new ProjectStatus(1l, "name");
        projects[0] = new Project(new ProjectCategory(1l, "n", new ProjectType(1, "n")), projectStatus);
        projects[0].setId(2l);
        EasyMock.expect(projectManager.getProjects(EasyMock.aryEq(new long[] {1}))).andReturn(projects);

        ProjectLink[] sourceProjectLinks = new ProjectLink[1];
        sourceProjectLinks[0] = new ProjectLink();
        sourceProjectLinks[0].setType(new ProjectLinkType(5, "REPOST_FOR"));
        EasyMock.expect(projectLinkManager.getSourceProjectLinks(projects[0].getId())).andReturn(sourceProjectLinks);

        instance.setActiveProjectStatusId(1l);
        EasyMock.expect(utilityDAO.getContestBugCount(projects[0].getId())).andReturn(1);
        EasyMock.replay(projectManager, projectLinkManager, utilityDAO);
        ContestsStat contestsStat = instance.calculateContestsStat(Arrays.asList(1l), null);
        assertTrue("current contests should be 1", 1 == contestsStat.getCurrentContests());
        assertTrue("total bug races should be 1", 1 == contestsStat.getTotalBugRaces());
        assertTrue("total contests should be 0", 0 == contestsStat.getTotalContests());
        assertTrue("total failed contests should be 0", 0 == contestsStat.getTotalFailedContests());
        assertTrue("total reposted contests should be 1", 1 == contestsStat.getTotalRepostedContests());
        EasyMock.verify(projectManager, projectLinkManager, utilityDAO);
    }

    /**
     * Accuracy test for {@link BaseCopilotService#calculateContestsStat}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testCalculateContestsStat2() throws Exception {
        UtilityDAO utilityDAO = EasyMock.createMock(UtilityDAO.class);
        ProjectManager projectManager = EasyMock.createMock(ProjectManager.class);
        ProjectLinkManager projectLinkManager = EasyMock.createMock(ProjectLinkManager.class);
        instance.setUtilityDAO(utilityDAO);
        instance.setProjectManager(projectManager);
        instance.setProjectLinkManager(projectLinkManager);

        Project[] projects = new Project[1];
        ProjectStatus projectStatus = new ProjectStatus(1l, "name");
        projects[0] = new Project(new ProjectCategory(1l, "n", new ProjectType(1, "n")), projectStatus);
        projects[0].setId(2l);
        EasyMock.expect(projectManager.getProjects(EasyMock.aryEq(new long[] {1}))).andReturn(projects);

        ProjectLink[] sourceProjectLinks = new ProjectLink[1];
        sourceProjectLinks[0] = new ProjectLink();
        sourceProjectLinks[0].setType(new ProjectLinkType(1l, "NOT_REPOST_FOR"));
        EasyMock.expect(projectLinkManager.getSourceProjectLinks(projects[0].getId())).andReturn(sourceProjectLinks);

        instance.setActiveProjectStatusId(10l);
        instance.setFailedProjectStatusIds(Arrays.asList(1l));
        EasyMock.expect(utilityDAO.getContestBugCount(projects[0].getId())).andReturn(1);
        EasyMock.replay(projectManager, projectLinkManager, utilityDAO);
        ContestsStat contestsStat = instance.calculateContestsStat(Arrays.asList(1l), null);
        assertTrue("current contests should be 0", 0 == contestsStat.getCurrentContests());
        assertTrue("total bug races should be 1", 1 == contestsStat.getTotalBugRaces());
        assertTrue("total contests should be 1", 1 == contestsStat.getTotalContests());
        assertTrue("total failed contests should be 1", 1 == contestsStat.getTotalFailedContests());
        assertTrue("total reposted contests should be 0", 0 == contestsStat.getTotalRepostedContests());
        EasyMock.verify(projectManager, projectLinkManager, utilityDAO);
    }

    /**
     * Accuracy test for {@link BaseCopilotService#calculateContestsStat}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testCalculateContestsStat3() throws Exception {
        Map<String, ContestTypeStat> contestTypeStats = new HashMap<String, ContestTypeStat>();
        UtilityDAO utilityDAO = EasyMock.createMock(UtilityDAO.class);
        ProjectManager projectManager = EasyMock.createMock(ProjectManager.class);
        ProjectLinkManager projectLinkManager = EasyMock.createMock(ProjectLinkManager.class);
        instance.setUtilityDAO(utilityDAO);
        instance.setProjectManager(projectManager);
        instance.setProjectLinkManager(projectLinkManager);
        Project[] projects = new Project[1];
        ProjectStatus projectStatus = new ProjectStatus(1l, "name");
        projects[0] = new Project(new ProjectCategory(1l, "n", new ProjectType(1, "n")), projectStatus);
        projects[0].setId(2l);
        EasyMock.expect(projectManager.getProjects(EasyMock.aryEq(new long[] {1}))).andReturn(projects);
        ProjectLink[] sourceProjectLinks = new ProjectLink[1];
        sourceProjectLinks[0] = new ProjectLink();
        sourceProjectLinks[0].setType(new ProjectLinkType(1l, "NOT_REPOST_FOR"));
        EasyMock.expect(projectLinkManager.getSourceProjectLinks(projects[0].getId())).andReturn(sourceProjectLinks);
        instance.setActiveProjectStatusId(10l);
        instance.setFailedProjectStatusIds(Arrays.asList(1l));
        EasyMock.expect(utilityDAO.getContestBugCount(projects[0].getId())).andReturn(1);
        EasyMock.replay(projectManager, projectLinkManager, utilityDAO);
        ContestsStat contestsStat = instance.calculateContestsStat(Arrays.asList(1l), contestTypeStats);
        assertTrue("current contests should be 0", 0 == contestsStat.getCurrentContests());
        assertTrue("total bug races should be 1", 1 == contestsStat.getTotalBugRaces());
        assertTrue("total contests should be 1", 1 == contestsStat.getTotalContests());
        assertTrue("total failed contests should be 1", 1 == contestsStat.getTotalFailedContests());
        assertTrue("total reposted contests should be 0", 0 == contestsStat.getTotalRepostedContests());
        assertTrue("contestTypeStats'size should be 1", 1 == contestTypeStats.size());
        EasyMock.verify(projectManager, projectLinkManager, utilityDAO);
    }

    /**
     * Accuracy test for {@link BaseCopilotService#calculateContestsStat}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testCalculateContestsStat4() throws Exception {
        Map<String, ContestTypeStat> contestTypeStats = new HashMap<String, ContestTypeStat>();
        UtilityDAO utilityDAO = EasyMock.createMock(UtilityDAO.class);
        ProjectManager projectManager = EasyMock.createMock(ProjectManager.class);
        ProjectLinkManager projectLinkManager = EasyMock.createMock(ProjectLinkManager.class);
        instance.setUtilityDAO(utilityDAO);
        instance.setProjectManager(projectManager);
        instance.setProjectLinkManager(projectLinkManager);
        Project[] projects = new Project[1];
        ProjectStatus projectStatus = new ProjectStatus(1l, "name");
        projects[0] = new Project(new ProjectCategory(1l, "n", new ProjectType(1, "n")), projectStatus);
        projects[0].setId(2l);
        EasyMock.expect(projectManager.getProjects(EasyMock.aryEq(new long[] {1}))).andReturn(projects);
        ProjectLink[] sourceProjectLinks = new ProjectLink[1];
        sourceProjectLinks[0] = new ProjectLink();
        sourceProjectLinks[0].setType(new ProjectLinkType(5, "REPOST_FOR"));
        EasyMock.expect(projectLinkManager.getSourceProjectLinks(projects[0].getId())).andReturn(sourceProjectLinks);
        instance.setActiveProjectStatusId(10l);
        instance.setFailedProjectStatusIds(Arrays.asList(1l));
        EasyMock.expect(utilityDAO.getContestBugCount(projects[0].getId())).andReturn(1);
        EasyMock.replay(projectManager, projectLinkManager, utilityDAO);
        ContestsStat contestsStat = instance.calculateContestsStat(Arrays.asList(1l), contestTypeStats);
        assertTrue("current contests should be 0", 0 == contestsStat.getCurrentContests());
        assertTrue("total bug races should be 1", 1 == contestsStat.getTotalBugRaces());
        assertTrue("total contests should be 0", 0 == contestsStat.getTotalContests());
        assertTrue("total failed contests should be 1", 1 == contestsStat.getTotalFailedContests());
        assertTrue("total reposted contests should be 1", 1 == contestsStat.getTotalRepostedContests());
        assertTrue("contestTypeStats'size should be 1", 1 == contestTypeStats.size());
        EasyMock.verify(projectManager, projectLinkManager, utilityDAO);
    }

    /**
     * Accuracy test for {@link BaseCopilotService#calculateContestsStat}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testCalculateContestsStat5() throws Exception {
        UtilityDAO utilityDAO = EasyMock.createMock(UtilityDAO.class);
        ProjectManager projectManager = EasyMock.createMock(ProjectManager.class);
        ProjectLinkManager projectLinkManager = EasyMock.createMock(ProjectLinkManager.class);
        instance.setUtilityDAO(utilityDAO);
        instance.setProjectManager(projectManager);
        instance.setProjectLinkManager(projectLinkManager);

        Project[] projects = new Project[1];
        ProjectStatus projectStatus = new ProjectStatus(1l, "name");
        projects[0] = new Project(new ProjectCategory(1l, "n", new ProjectType(1, "n")), projectStatus);
        projects[0].setId(2l);
        EasyMock.expect(projectManager.getProjects(EasyMock.aryEq(new long[] {1}))).andReturn(projects);

        ProjectLink[] sourceProjectLinks = new ProjectLink[1];
        sourceProjectLinks[0] = new ProjectLink();
        sourceProjectLinks[0].setType(new ProjectLinkType(1l, "NOT_REPOST_FOR"));
        EasyMock.expect(projectLinkManager.getSourceProjectLinks(projects[0].getId())).andReturn(sourceProjectLinks);

        instance.setActiveProjectStatusId(10l);
        instance.setFailedProjectStatusIds(Arrays.asList(12l));
        EasyMock.expect(utilityDAO.getContestBugCount(projects[0].getId())).andReturn(1);
        EasyMock.replay(projectManager, projectLinkManager, utilityDAO);
        ContestsStat contestsStat = instance.calculateContestsStat(Arrays.asList(1l), null);
        assertTrue("current contests should be 0", 0 == contestsStat.getCurrentContests());
        assertTrue("total bug races should be 1", 1 == contestsStat.getTotalBugRaces());
        assertTrue("total contests should be 1", 1 == contestsStat.getTotalContests());
        assertTrue("total failed contests should be 0", 0 == contestsStat.getTotalFailedContests());
        assertTrue("total reposted contests should be 0", 0 == contestsStat.getTotalRepostedContests());
        EasyMock.verify(projectManager, projectLinkManager, utilityDAO);
    }

    /**
     * Failure test for {@link BaseCopilotService#calculateContestsStat}.Expected IllegalArgumentException.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCalculateContestsStatFailure1() throws Exception {
        instance.calculateContestsStat(null, null);
    }

    /**
     * Failure test for {@link BaseCopilotService#calculateContestsStat}.Expected IllegalArgumentException.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCalculateContestsStatFailure2() throws Exception {
        instance.calculateContestsStat(new ArrayList<Long>(), null);
    }

    /**
     * Failure test for {@link BaseCopilotService#calculateContestsStat}.Expected IllegalArgumentException.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCalculateContestsStatFailure3() throws Exception {
        instance.calculateContestsStat(Arrays.asList(-1l), null);
    }

    /**
     * Failure test for {@link BaseCopilotService#calculateContestsStat}.Expected CopilotServiceException.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void testCalculateContestsStatFailure4() throws Exception {
        UtilityDAO utilityDAO = EasyMock.createMock(UtilityDAO.class);
        ProjectManager projectManager = EasyMock.createMock(ProjectManager.class);
        ProjectLinkManager projectLinkManager = EasyMock.createMock(ProjectLinkManager.class);
        instance.setUtilityDAO(utilityDAO);
        instance.setProjectManager(projectManager);
        instance.setProjectLinkManager(projectLinkManager);
        Project[] projects = new Project[1];
        ProjectStatus projectStatus = new ProjectStatus(1l, "name");
        projects[0] = new Project(new ProjectCategory(1l, "n", new ProjectType(1, "n")), projectStatus);
        projects[0].setId(2l);
        EasyMock.expect(projectManager.getProjects(EasyMock.aryEq(new long[] {1}))).andThrow(
            new PersistenceException("error"));
        EasyMock.replay(projectManager, projectLinkManager, utilityDAO);
        instance.calculateContestsStat(Arrays.asList(1l), null);
        EasyMock.verify(projectManager, projectLinkManager, utilityDAO);
    }

    /**
     * Failure test for {@link BaseCopilotService#calculateContestsStat}.Expected CopilotServiceException.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void testCalculateContestsStatFailure5() throws Exception {
        UtilityDAO utilityDAO = EasyMock.createMock(UtilityDAO.class);
        ProjectManager projectManager = EasyMock.createMock(ProjectManager.class);
        ProjectLinkManager projectLinkManager = EasyMock.createMock(ProjectLinkManager.class);
        instance.setUtilityDAO(utilityDAO);
        instance.setProjectManager(projectManager);
        instance.setProjectLinkManager(projectLinkManager);

        Project[] projects = new Project[1];
        ProjectStatus projectStatus = new ProjectStatus(1l, "name");
        projects[0] = new Project(new ProjectCategory(1l, "n", new ProjectType(1, "n")), projectStatus);
        projects[0].setId(2l);
        EasyMock.expect(projectManager.getProjects(EasyMock.aryEq(new long[] {1}))).andReturn(projects);
        ProjectLink[] sourceProjectLinks = new ProjectLink[1];
        sourceProjectLinks[0] = new ProjectLink();
        sourceProjectLinks[0].setType(new ProjectLinkType(1l, "NOT_REPOST_FOR"));
        EasyMock.expect(projectLinkManager.getSourceProjectLinks(projects[0].getId())).andReturn(sourceProjectLinks);
        instance.setActiveProjectStatusId(10l);
        instance.setFailedProjectStatusIds(Arrays.asList(1l));
        EasyMock.expect(utilityDAO.getContestBugCount(projects[0].getId())).andThrow(
            new CopilotDAOException("error"));
        EasyMock.replay(projectManager, projectLinkManager, utilityDAO);
        instance.calculateContestsStat(Arrays.asList(1l), null);
        EasyMock.verify(projectManager, projectLinkManager, utilityDAO);
    }

    /**
     * Accuracy test for {@link BaseCopilotService#setCopilotProjectPlanDAO(CopilotProjectPlanDAO)}. The
     * copilotProjectPlanDAO should be set correctly.
     */
    @Test
    public void testSetCopilotProjectPlanDAO() {
        CopilotProjectPlanDAO value = EasyMock.createMock(CopilotProjectPlanDAO.class);
        instance.setCopilotProjectPlanDAO(value);
        assertTrue("Incorrect value after set to a new one.", value == instance.getCopilotProjectPlanDAO());
    }

    /**
     * Accuracy test for {@link BaseCopilotService#getCopilotProjectPlanDAO()}. The default copilotProjectPlanDAO
     * should be returned correctly.
     */
    @Test
    public void testGetCopilotProjectPlanDAO() {
        assertNull("Incorrect default value", instance.getCopilotProjectPlanDAO());
    }

    /**
     * Accuracy test for {@link BaseCopilotService#setUtilityDAO(UtilityDAO)}. The utilityDAO should be set
     * correctly.
     */
    @Test
    public void testSetUtilityDAO() {
        UtilityDAO value = EasyMock.createMock(UtilityDAO.class);
        instance.setUtilityDAO(value);
        assertTrue("Incorrect value after set to a new one.", value == instance.getUtilityDAO());
    }

    /**
     * Accuracy test for {@link BaseCopilotService#getUtilityDAO()}. The default utilityDAO should be returned
     * correctly.
     */
    @Test
    public void testGetUtilityDAO() {
        assertNull("Incorrect default value", instance.getUtilityDAO());
    }

    /**
     * Accuracy test for {@link BaseCopilotService#setProjectManager(ProjectManager)}. The projectManager should be
     * set correctly.
     */
    @Test
    public void testSetProjectManager() {
        ProjectManager value = EasyMock.createMock(ProjectManager.class);
        instance.setProjectManager(value);
        assertTrue("Incorrect value after set to a new one.", value == instance.getProjectManager());
    }

    /**
     * Accuracy test for {@link BaseCopilotService#getProjectManager()}. The default projectManager should be
     * returned correctly.
     */
    @Test
    public void testGetProjectManager() {
        assertNull("Incorrect default value", instance.getProjectManager());
    }

    /**
     * Accuracy test for {@link BaseCopilotService#setProjectLinkManager(ProjectLinkManager)}. The projectLinkManager
     * should be set correctly.
     */
    @Test
    public void testSetProjectLinkManager() {
        ProjectLinkManager value = EasyMock.createMock(ProjectLinkManager.class);
        instance.setProjectLinkManager(value);
        assertTrue("Incorrect value after set to a new one.", value == instance.getProjectLinkManager());
    }

    /**
     * Accuracy test for {@link BaseCopilotService#getProjectLinkManager()}. The default projectLinkManager should be
     * returned correctly.
     */
    @Test
    public void testGetProjectLinkManager() {
        assertNull("Incorrect default value", instance.getProjectLinkManager());
    }

    /**
     * Accuracy test for {@link BaseCopilotService#setActiveProjectStatusId(long)}. The activeProjectStatusId should
     * be set correctly.
     */
    @Test
    public void testSetActiveProjectStatusId() {
        instance.setActiveProjectStatusId(65l);
        assertEquals("Incorrect value after set to a new one.", 65l, instance.getActiveProjectStatusId());
    }

    /**
     * Accuracy test for {@link BaseCopilotService#getActiveProjectStatusId()}. The default activeProjectStatusId
     * should be returned correctly.
     */
    @Test
    public void testGetActiveProjectStatusId() {
        assertEquals("Incorrect default value", 0, instance.getActiveProjectStatusId());
    }

    /**
     * Accuracy test for {@link BaseCopilotService#setFailedProjectStatusIds(List)}. The failedProjectStatusIds
     * should be set correctly.
     */
    @Test
    public void testSetFailedProjectStatusIds() {
        List<Long> longs = new ArrayList<Long>();
        longs.add(1l);
        instance.setFailedProjectStatusIds(longs);
        assertTrue("Incorrect value after set to a new one.", 1l == instance.getFailedProjectStatusIds().get(0));
    }

    /**
     * Accuracy test for {@link BaseCopilotService#getFailedProjectStatusIds()}.
     */
    @Test
    public void testGetFailedProjectStatusIds() {
        List<Long> longs = new ArrayList<Long>();
        longs.add(2l);
        instance.setFailedProjectStatusIds(longs);
        assertTrue("Incorrect value after set to a new one.", 2l == instance.getFailedProjectStatusIds().get(0));
    }

    /**
     * Accuracy test for {@link BaseCopilotService#setActiveCopilotProjectStatusId(long)}. The
     * activeCopilotProjectStatusId should be set correctly.
     */
    @Test
    public void testSetActiveCopilotProjectStatusId() {
        instance.setActiveCopilotProjectStatusId(2l);
        assertEquals("Incorrect value after set to a new one.", 2l, instance.getActiveCopilotProjectStatusId());
    }

    /**
     * Accuracy test for {@link BaseCopilotService#getActiveCopilotProjectStatusId()}. The default
     * activeCopilotProjectStatusId should be returned correctly.
     */
    @Test
    public void testGetActiveCopilotProjectStatusId() {
        assertEquals("Incorrect default value", 0, instance.getActiveCopilotProjectStatusId());
    }
}
