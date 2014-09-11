/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.copilot.CopilotServiceException;
import com.topcoder.direct.services.copilot.CopilotServiceInitializationException;
import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.CopilotProfileDAO;
import com.topcoder.direct.services.copilot.dao.CopilotProjectDAO;
import com.topcoder.direct.services.copilot.dao.CopilotProjectPlanDAO;
import com.topcoder.direct.services.copilot.dao.GenericDAO;
import com.topcoder.direct.services.copilot.dao.UtilityDAO;
import com.topcoder.direct.services.copilot.model.CopilotProfile;
import com.topcoder.direct.services.copilot.model.CopilotProject;
import com.topcoder.direct.services.copilot.model.CopilotProjectPlan;
import com.topcoder.direct.services.copilot.model.PlannedContest;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectManager;
import com.topcoder.management.project.link.ProjectLinkManager;

/**
 * Failure tests for {@link CopilotProfileServiceImpl}.
 * 
 * @author morehappienss
 * @version 1.0
 */
public class CopilotProfileServiceImplFailureTests {
    /**
     * CopilotProfileServiceImpl instance for testing.
     */
    private CopilotProfileServiceImpl instance;

    /**
     * Sets up the test environment.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Before
    public void setUp() throws Exception {
        instance = new CopilotProfileServiceImpl();
    }

    /**
     * Failure test for checkInit.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = CopilotServiceInitializationException.class)
    public void test_checkInit_failure1() throws Exception {
        instance.setCopilotProjectPlanDAO(EasyMock.createMock(CopilotProjectPlanDAO.class));
        instance.setUtilityDAO(EasyMock.createMock(UtilityDAO.class));
        ProjectManager projectManager = EasyMock.createMock(ProjectManager.class);
        instance.setProjectManager(projectManager);
        instance.setProjectLinkManager(EasyMock.createMock(ProjectLinkManager.class));
        instance.setActiveProjectStatusId(1);
        instance.setFailedProjectStatusIds(Arrays.asList(1l));
        instance.setActiveCopilotProjectStatusId(1l);
        EasyMock.expect(projectManager.getAllProjectCategories()).andReturn(new ProjectCategory[0]);
        EasyMock.replay(projectManager);
        instance.setLoggerName("loggerName");
        instance.setCopilotProjectDAO(EasyMock.createMock(CopilotProjectDAO.class));

        instance.setGenericDAO(EasyMock.createMock(GenericDAO.class));
        instance.checkInit();
    }

    /**
     * Failure test for getCopilotPoolMembers.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void test_getCopilotPoolMembers_failure1() throws Exception {
        CopilotProfileDAO copilotProfileDAO = EasyMock.createMock(CopilotProfileDAO.class);
        instance.setGenericDAO(copilotProfileDAO);
        EasyMock.expect(copilotProfileDAO.retrieveAll()).andThrow(new CopilotDAOException("error"));
        EasyMock.replay(copilotProfileDAO);
        instance.getCopilotPoolMembers();
        EasyMock.verify(copilotProfileDAO);
    }

    /**
     * Failure test for getCopilotProfileDTO.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void test_getCopilotProfileDTO_failure1() throws Exception {
        instance = EasyMock.createMockBuilder(CopilotProfileServiceImpl.class).addMockedMethod("calculateContestsStat")
            .addMockedMethod("countPlannedContestInStats").createMock();
        CopilotProfileDAO copilotProfileDAO = EasyMock.createMock(CopilotProfileDAO.class);
        instance.setGenericDAO(copilotProfileDAO);
        EasyMock.expect(copilotProfileDAO.getCopilotProfile(1l)).andThrow(new CopilotDAOException("error"));
        EasyMock.replay(copilotProfileDAO, instance);
        instance.getCopilotProfileDTO(1l);
        EasyMock.verify(copilotProfileDAO, instance);
    }

    /**
     * Failure test for getCopilotProfileDTO.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void test_getCopilotProfileDTO_failure2() throws Exception {
        instance = EasyMock.createMockBuilder(CopilotProfileServiceImpl.class).addMockedMethod("calculateContestsStat")
            .addMockedMethod("countPlannedContestInStats").createMock();
        CopilotProfileDAO copilotProfileDAO = EasyMock.createMock(CopilotProfileDAO.class);
        instance.setGenericDAO(copilotProfileDAO);
        CopilotProfile copilotProfile = new CopilotProfile();
        EasyMock.expect(copilotProfileDAO.getCopilotProfile(1l)).andReturn(copilotProfile);
        List<CopilotProject> copilotProjects = new ArrayList<CopilotProject>();
        CopilotProject copilotProject = new CopilotProject();
        copilotProjects.add(copilotProject);
        CopilotProjectDAO copilotProjectDAO = EasyMock.createMock(CopilotProjectDAO.class);
        instance.setCopilotProjectDAO(copilotProjectDAO);
        EasyMock.expect(copilotProjectDAO.getCopilotProjects(0)).andReturn(copilotProjects);
        UtilityDAO utilityDAO = EasyMock.createMock(UtilityDAO.class);
        instance.setUtilityDAO(utilityDAO);
        EasyMock.expect(utilityDAO.getCopilotProjectContests(0, 0)).andReturn(new long[]{1});
        EasyMock.expect(utilityDAO.getCopilotEarnings(0)).andReturn(100d);
        EasyMock
            .expect(
                instance.calculateContestsStat(EasyMock.capture(new Capture<List>()), EasyMock
                    .capture(new Capture<Map>()))).andReturn(new ContestsStat());
        instance.countPlannedContestInStats(EasyMock.capture(new Capture<Map>()), EasyMock
            .capture(new Capture<PlannedContest>()));

        CopilotProjectPlanDAO copilotProjectPlanDAO = EasyMock.createMock(CopilotProjectPlanDAO.class);
        instance.setCopilotProjectPlanDAO(copilotProjectPlanDAO);
        CopilotProjectPlan copilotProjectPlan = new CopilotProjectPlan();
        Set<PlannedContest> plannedContests = new HashSet<PlannedContest>();
        plannedContests.add(new PlannedContest());
        copilotProjectPlan.setPlannedContests(plannedContests);
        EasyMock.expect(copilotProjectPlanDAO.getCopilotProjectPlan(0)).andReturn(copilotProjectPlan);

        EasyMock.replay(copilotProfileDAO, copilotProjectDAO, utilityDAO, copilotProjectPlanDAO, instance);
        instance.getCopilotProfileDTO(1);
        EasyMock.verify(copilotProfileDAO, copilotProjectDAO, utilityDAO, copilotProjectPlanDAO, instance);
    }

    /**
     * Failure test for getCopilotProfileDTO.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void test_getCopilotProfileDTO_failure3() throws Exception {
        CopilotProfileDAO copilotProfileDAO = EasyMock.createMock(CopilotProfileDAO.class);
        instance.setGenericDAO(copilotProfileDAO);
        CopilotProfile copilotProfile = new CopilotProfile();
        EasyMock.expect(copilotProfileDAO.getCopilotProfile(1l)).andReturn(copilotProfile);
        List<CopilotProject> copilotProjects = new ArrayList<CopilotProject>();
        CopilotProjectDAO copilotProjectDAO = EasyMock.createMock(CopilotProjectDAO.class);
        instance.setCopilotProjectDAO(copilotProjectDAO);
        EasyMock.expect(copilotProjectDAO.getCopilotProjects(0)).andReturn(copilotProjects);
        UtilityDAO utilityDAO = EasyMock.createMock(UtilityDAO.class);
        instance.setUtilityDAO(utilityDAO);
        EasyMock.expect(utilityDAO.getCopilotProjectContests(0, 0)).andReturn(new long[]{1});
        EasyMock.expect(utilityDAO.getCopilotEarnings(0)).andReturn(100d);
        CopilotProjectPlanDAO copilotProjectPlanDAO = EasyMock.createMock(CopilotProjectPlanDAO.class);
        instance.setCopilotProjectPlanDAO(copilotProjectPlanDAO);
        CopilotProjectPlan copilotProjectPlan = new CopilotProjectPlan();
        Set<PlannedContest> plannedContests = new HashSet<PlannedContest>();
        plannedContests.add(new PlannedContest());
        copilotProjectPlan.setPlannedContests(plannedContests);
        EasyMock.expect(copilotProjectPlanDAO.getCopilotProjectPlan(0)).andReturn(copilotProjectPlan);
        EasyMock.replay(copilotProfileDAO, copilotProjectDAO, utilityDAO, copilotProjectPlanDAO);
        instance.getCopilotProfileDTO(1);
        EasyMock.verify(copilotProfileDAO, copilotProjectDAO, utilityDAO, copilotProjectPlanDAO);
    }

    /**
     * Failure test for getCopilotProfile.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void test_getCopilotProfile_failure1() throws Exception {
        CopilotProfileDAO copilotProfileDAO = EasyMock.createMock(CopilotProfileDAO.class);
        instance.setGenericDAO(copilotProfileDAO);
        EasyMock.expect(copilotProfileDAO.getCopilotProfile(1l)).andThrow(new CopilotDAOException("error"));
        EasyMock.replay(copilotProfileDAO);
        instance.getCopilotProfile(1l);
        EasyMock.verify(copilotProfileDAO);
    }
}
