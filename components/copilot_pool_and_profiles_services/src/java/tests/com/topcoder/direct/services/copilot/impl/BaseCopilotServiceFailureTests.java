/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.impl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.copilot.CopilotServiceException;
import com.topcoder.direct.services.copilot.CopilotServiceInitializationException;
import com.topcoder.direct.services.copilot.TestHelper;
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
import com.topcoder.management.project.link.ProjectLinkManager;

/**
 * Failure tests for {@link BaseCopilotService}.
 * 
 * @author morehappiness
 * @version 1.0
 */
public class BaseCopilotServiceFailureTests {

    /**
     * BaseCopilotService instance for testing.
     */
    private BaseCopilotService<IdentifiableEntity> instance;

    /**
     * Sets up the test environment.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Before
    public void setUp() throws Exception {
        instance = new BaseCopilotService<IdentifiableEntity>() {
        };
    }

    /**
     * Failure test for checkInit.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = CopilotServiceInitializationException.class)
    public void test_checkInit_failure1() throws Exception {
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
     * Failure test for checkInit.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = CopilotServiceInitializationException.class)
    public void test_checkInit_failure2() throws Exception {
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
     * Failure test for checkInit.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = CopilotServiceInitializationException.class)
    public void test_checkInit_failure3() throws Exception {
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
     * Failure test for countPlannedContestInStats.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void test_countPlannedContestInStats_failure1() throws Exception {
        long projectCategoryId = 2;
        Map<Long, ProjectCategory> projectCategories = new HashMap<Long, ProjectCategory>();
        TestHelper.setField(BaseCopilotService.class, instance, "projectCategories", projectCategories);
        Map<String, ContestTypeStat> contestTypeStats = new HashMap<String, ContestTypeStat>();
        PlannedContest plannedContest = new PlannedContest();
        plannedContest.setProjectCategoryId(projectCategoryId);
        instance.countPlannedContestInStats(contestTypeStats, plannedContest);
    }

    /**
     * Failure test for calculateContestsStat.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_calculateContestsStat_failure1() throws Exception {
        instance.calculateContestsStat(null, null);
    }

    /**
     * Failure test for calculateContestsStat.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_calculateContestsStat_failure2() throws Exception {
        instance.calculateContestsStat(new ArrayList<Long>(), null);
    }

    /**
     * Failure test for calculateContestsStat.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_calculateContestsStat_failure3() throws Exception {
        instance.calculateContestsStat(Arrays.asList(-1l), null);
    }

    /**
     * Failure test for calculateContestsStat.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void test_calculateContestsStat_failure4() throws Exception {
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
        EasyMock.expect(projectManager.getProjects(EasyMock.aryEq(new long[]{1}))).andThrow(
            new PersistenceException("error"));
        EasyMock.replay(projectManager, projectLinkManager, utilityDAO);
        instance.calculateContestsStat(Arrays.asList(1l), null);
        EasyMock.verify(projectManager, projectLinkManager, utilityDAO);
    }
}
