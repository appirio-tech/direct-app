/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.After;
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
import com.topcoder.direct.services.copilot.dto.CopilotPoolMember;
import com.topcoder.direct.services.copilot.dto.CopilotProfileDTO;
import com.topcoder.direct.services.copilot.model.CopilotProfile;
import com.topcoder.direct.services.copilot.model.CopilotProject;
import com.topcoder.direct.services.copilot.model.CopilotProjectPlan;
import com.topcoder.direct.services.copilot.model.CopilotProjectStatus;
import com.topcoder.direct.services.copilot.model.PlannedContest;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectManager;
import com.topcoder.management.project.link.ProjectLinkManager;

/**
 * Unit tests for {@link CopilotProfileServiceImpl}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CopilotProfileServiceImplUnitTests {

    /**
     * Represents {@link CopilotProfileServiceImpl} instance for testing.
     */
    private CopilotProfileServiceImpl instance;

    /**
     * Sets up the test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        instance = new CopilotProfileServiceImpl();
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
     * Accuracy test for {@link CopilotProfileServiceImpl#CopilotProfileServiceImpl()}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testCtor() throws Exception {
        assertNotNull("Unable to instantiate this object", instance);
    }

    /**
     * Accuracy test for {@link CopilotProfileServiceImpl#getCopilotPoolMembers}.
     *
     * @throws Exception to JUnit
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetCopilotPoolMembers() throws Exception {
        instance =
            EasyMock.createMockBuilder(CopilotProfileServiceImpl.class).addMockedMethod("calculateContestsStat")
                .addMockedMethod("countPlannedContestInStats").createMock();
        CopilotProfileDAO copilotProfileDAO = EasyMock.createMock(CopilotProfileDAO.class);
        instance.setGenericDAO(copilotProfileDAO);
        List<CopilotProfile> copilotProfiles = new ArrayList<CopilotProfile>();
        CopilotProfile copilotProfile = new CopilotProfile();

        copilotProfiles.add(copilotProfile);
        EasyMock.expect(copilotProfileDAO.retrieveAll()).andReturn(copilotProfiles);

        List<CopilotProject> copilotProjects = new ArrayList<CopilotProject>();
        CopilotProject copilotProject = new CopilotProject();
        copilotProjects.add(copilotProject);
        copilotProject.setStatus(new CopilotProjectStatus());
        CopilotProjectDAO copilotProjectDAO = EasyMock.createMock(CopilotProjectDAO.class);
        instance.setCopilotProjectDAO(copilotProjectDAO);
        EasyMock.expect(copilotProjectDAO.getCopilotProjects(0)).andReturn(copilotProjects);

        UtilityDAO utilityDAO = EasyMock.createMock(UtilityDAO.class);
        instance.setUtilityDAO(utilityDAO);
        EasyMock.expect(utilityDAO.getCopilotProjectContests(0, 0)).andReturn(new long[] {1});
        EasyMock.expect(
            instance.calculateContestsStat(EasyMock.capture(new Capture<List>()), EasyMock
                .capture(new Capture<Map>()))).andReturn(new ContestsStat());
        EasyMock.replay(copilotProfileDAO, copilotProjectDAO, utilityDAO, instance);
        List<CopilotPoolMember> result = instance.getCopilotPoolMembers();
        assertTrue("size should be 1.", result.size() == 1);
        assertTrue("should be same with copilotProfile.", result.get(0).getCopilotProfile() == copilotProfile);
        EasyMock.verify(copilotProfileDAO, copilotProjectDAO, utilityDAO, instance);
    }

    /**
     * Failure test for {@link CopilotProfileServiceImpl#getCopilotPoolMembers}.Expected CopilotServiceException.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void testGetCopilotPoolMembersFailure() throws Exception {
        CopilotProfileDAO copilotProfileDAO = EasyMock.createMock(CopilotProfileDAO.class);
        instance.setGenericDAO(copilotProfileDAO);
        EasyMock.expect(copilotProfileDAO.retrieveAll()).andThrow(new CopilotDAOException("error"));
        EasyMock.replay(copilotProfileDAO);
        instance.getCopilotPoolMembers();
        EasyMock.verify(copilotProfileDAO);
    }

    /**
     * Accuracy test for {@link CopilotProfileServiceImpl#getCopilotProfileDTO}.
     *
     * @throws Exception to JUnit
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetCopilotProfileDTO1() throws Exception {
        instance =
            EasyMock.createMockBuilder(CopilotProfileServiceImpl.class).addMockedMethod("calculateContestsStat")
                .addMockedMethod("countPlannedContestInStats").createMock();
        CopilotProfileDAO copilotProfileDAO = EasyMock.createMock(CopilotProfileDAO.class);
        instance.setGenericDAO(copilotProfileDAO);
        CopilotProfile copilotProfile = new CopilotProfile();
        EasyMock.expect(copilotProfileDAO.getCopilotProfile(1l)).andReturn(copilotProfile);
        List<CopilotProject> copilotProjects = new ArrayList<CopilotProject>();
        CopilotProject copilotProject = new CopilotProject();
        copilotProjects.add(copilotProject);
        copilotProject.setStatus(new CopilotProjectStatus());
        CopilotProjectDAO copilotProjectDAO = EasyMock.createMock(CopilotProjectDAO.class);
        instance.setCopilotProjectDAO(copilotProjectDAO);
        EasyMock.expect(copilotProjectDAO.getCopilotProjects(0)).andReturn(copilotProjects);
        UtilityDAO utilityDAO = EasyMock.createMock(UtilityDAO.class);
        instance.setUtilityDAO(utilityDAO);
        EasyMock.expect(utilityDAO.getCopilotProjectContests(0, 0)).andReturn(new long[] {1});
        EasyMock.expect(utilityDAO.getCopilotEarnings(0)).andReturn(100d);
        EasyMock.expect(
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
        CopilotProfileDTO result = instance.getCopilotProfileDTO(1);
        assertTrue("should be same with copilotProfile.", result.getCopilotProfile() == copilotProfile);
        assertTrue("should be 100.", result.getEarnings() == 100);
        EasyMock.verify(copilotProfileDAO, copilotProjectDAO, utilityDAO, copilotProjectPlanDAO, instance);
    }

    /**
     * Accuracy test for {@link CopilotProfileServiceImpl#getCopilotProfileDTO}.
     *
     * @throws Exception to JUnit
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetCopilotProfileDTO2() throws Exception {
        instance =
            EasyMock.createMockBuilder(CopilotProfileServiceImpl.class).addMockedMethod("calculateContestsStat")
                .addMockedMethod("countPlannedContestInStats").createMock();
        CopilotProfileDAO copilotProfileDAO = EasyMock.createMock(CopilotProfileDAO.class);
        instance.setGenericDAO(copilotProfileDAO);
        CopilotProfile copilotProfile = new CopilotProfile();
        EasyMock.expect(copilotProfileDAO.getCopilotProfile(1l)).andReturn(copilotProfile);

        List<CopilotProject> copilotProjects = new ArrayList<CopilotProject>();
        CopilotProject copilotProject = new CopilotProject();
        copilotProjects.add(copilotProject);
        copilotProject.setStatus(new CopilotProjectStatus());
        CopilotProjectDAO copilotProjectDAO = EasyMock.createMock(CopilotProjectDAO.class);
        instance.setCopilotProjectDAO(copilotProjectDAO);
        EasyMock.expect(copilotProjectDAO.getCopilotProjects(0)).andReturn(copilotProjects);
        UtilityDAO utilityDAO = EasyMock.createMock(UtilityDAO.class);
        instance.setUtilityDAO(utilityDAO);
        EasyMock.expect(utilityDAO.getCopilotProjectContests(0, 0)).andReturn(new long[] {1});
        EasyMock.expect(utilityDAO.getCopilotEarnings(0)).andReturn(100d);
        EasyMock.expect(
            instance.calculateContestsStat(EasyMock.capture(new Capture<List>()), EasyMock
                .capture(new Capture<Map>()))).andReturn(new ContestsStat());
        CopilotProjectPlanDAO copilotProjectPlanDAO = EasyMock.createMock(CopilotProjectPlanDAO.class);
        instance.setCopilotProjectPlanDAO(copilotProjectPlanDAO);
        CopilotProjectPlan copilotProjectPlan = new CopilotProjectPlan();
        EasyMock.expect(copilotProjectPlanDAO.getCopilotProjectPlan(0)).andReturn(copilotProjectPlan);

        EasyMock.replay(copilotProfileDAO, copilotProjectDAO, utilityDAO, copilotProjectPlanDAO, instance);
        CopilotProfileDTO result = instance.getCopilotProfileDTO(1);
        assertTrue("should be same with copilotProfile.", result.getCopilotProfile() == copilotProfile);
        assertTrue("should be 100.", result.getEarnings() == 100);
        EasyMock.verify(copilotProfileDAO, copilotProjectDAO, utilityDAO, copilotProjectPlanDAO, instance);
    }

    /**
     * Accuracy test for {@link CopilotProfileServiceImpl#getCopilotProfileDTO}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testGetCopilotProfileDTO3() throws Exception {
        instance =
            EasyMock.createMockBuilder(CopilotProfileServiceImpl.class).addMockedMethod("calculateContestsStat")
                .addMockedMethod("countPlannedContestInStats").createMock();
        CopilotProfileDAO copilotProfileDAO = EasyMock.createMock(CopilotProfileDAO.class);
        instance.setGenericDAO(copilotProfileDAO);
        EasyMock.expect(copilotProfileDAO.getCopilotProfile(1l)).andReturn(null);
        EasyMock.replay(copilotProfileDAO, instance);
        assertNull("should return null.", instance.getCopilotProfileDTO(1l));
        EasyMock.verify(copilotProfileDAO, instance);
    }

    /**
     * Failure test for {@link CopilotProfileServiceImpl#getCopilotProfileDTO}.Expected CopilotServiceException.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void testGetCopilotProfileDTOFailure1() throws Exception {
        instance =
            EasyMock.createMockBuilder(CopilotProfileServiceImpl.class).addMockedMethod("calculateContestsStat")
                .addMockedMethod("countPlannedContestInStats").createMock();
        CopilotProfileDAO copilotProfileDAO = EasyMock.createMock(CopilotProfileDAO.class);
        instance.setGenericDAO(copilotProfileDAO);
        EasyMock.expect(copilotProfileDAO.getCopilotProfile(1l)).andThrow(new CopilotDAOException("error"));
        EasyMock.replay(copilotProfileDAO, instance);
        instance.getCopilotProfileDTO(1l);
        EasyMock.verify(copilotProfileDAO, instance);
    }

    /**
     * Failure test for {@link CopilotProfileServiceImpl#getCopilotProfileDTO}.Expected CopilotServiceException.
     *
     * @throws Exception to JUnit
     */
    @SuppressWarnings("unchecked")
    @Test(expected = CopilotServiceException.class)
    public void testGetCopilotProfileDTOFailure2() throws Exception {
        instance =
            EasyMock.createMockBuilder(CopilotProfileServiceImpl.class).addMockedMethod("calculateContestsStat")
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
        EasyMock.expect(utilityDAO.getCopilotProjectContests(0, 0)).andReturn(new long[] {1});
        EasyMock.expect(utilityDAO.getCopilotEarnings(0)).andReturn(100d);
        EasyMock.expect(
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
     * Failure test for {@link CopilotProfileServiceImpl#getCopilotProfileDTO}.Expected CopilotServiceException.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void testGetCopilotProfileDTOFailure3() throws Exception {
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
        EasyMock.expect(utilityDAO.getCopilotProjectContests(0, 0)).andReturn(new long[] {1});
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
     * Failure test for {@link CopilotProfileServiceImpl#getCopilotProfileDTO}.Expected CopilotServiceException.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void testGetCopilotProfileDTOFailure4() throws Exception {
        instance =
            EasyMock.createMockBuilder(CopilotProfileServiceImpl.class).addMockedMethod("calculateContestsStat")
                .addMockedMethod("countPlannedContestInStats").createMock();
        CopilotProfileDAO copilotProfileDAO = EasyMock.createMock(CopilotProfileDAO.class);
        instance.setGenericDAO(copilotProfileDAO);
        CopilotProfile copilotProfile = new CopilotProfile();
        EasyMock.expect(copilotProfileDAO.getCopilotProfile(1l)).andReturn(copilotProfile);
        List<CopilotProject> copilotProjects = new ArrayList<CopilotProject>();
        CopilotProject copilotProject = new CopilotProject();
        copilotProjects.add(copilotProject);
        copilotProject.setStatus(new CopilotProjectStatus());
        CopilotProjectDAO copilotProjectDAO = EasyMock.createMock(CopilotProjectDAO.class);
        instance.setCopilotProjectDAO(copilotProjectDAO);
        EasyMock.expect(copilotProjectDAO.getCopilotProjects(0)).andThrow(new CopilotDAOException("error"));
        EasyMock.replay(copilotProfileDAO, copilotProjectDAO, instance);
        instance.getCopilotProfileDTO(1);
        EasyMock.verify(copilotProfileDAO, copilotProjectDAO, instance);
    }

    /**
     * Accuracy test for {@link CopilotProfileServiceImpl#getCopilotProfile}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testGetCopilotProfile() throws Exception {
        CopilotProfileDAO copilotProfileDAO = EasyMock.createMock(CopilotProfileDAO.class);
        instance.setGenericDAO(copilotProfileDAO);
        CopilotProfile copilotProfile = new CopilotProfile();
        EasyMock.expect(copilotProfileDAO.getCopilotProfile(1l)).andReturn(copilotProfile);
        EasyMock.replay(copilotProfileDAO);
        CopilotProfile result = instance.getCopilotProfile(1l);
        assertTrue("should be same with copilotProfile.", result == copilotProfile);
        EasyMock.verify(copilotProfileDAO);
    }

    /**
     * Failure test for {@link CopilotProfileServiceImpl#getCopilotProfile}.Expected CopilotServiceException.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void testGetCopilotProfileFailure() throws Exception {
        CopilotProfileDAO copilotProfileDAO = EasyMock.createMock(CopilotProfileDAO.class);
        instance.setGenericDAO(copilotProfileDAO);
        EasyMock.expect(copilotProfileDAO.getCopilotProfile(1l)).andThrow(new CopilotDAOException("error"));
        EasyMock.replay(copilotProfileDAO);
        instance.getCopilotProfile(1l);
        EasyMock.verify(copilotProfileDAO);
    }

    /**
     * Accuracy test for {@link CopilotProfileServiceImpl#setCopilotProjectDAO(CopilotProjectDAO)}. The
     * copilotProjectDAO should be set correctly.
     */
    @Test
    public void testSetCopilotProjectDAO() {
        CopilotProjectDAO value = EasyMock.createMock(CopilotProjectDAO.class);
        instance.setCopilotProjectDAO(value);
        assertTrue("Incorrect value after set to a new one.", value == instance.getCopilotProjectDAO());
    }

    /**
     * Accuracy test for {@link CopilotProfileServiceImpl#getCopilotProjectDAO()}. The default copilotProjectDAO
     * should be returned correctly.
     */
    @Test
    public void testGetCopilotProjectDAO() {
        assertNull("Incorrect default value", instance.getCopilotProjectDAO());
    }

    /**
     * Accuracy test for {@link CopilotProfileServiceImpl#checkInit}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testCheckInit() throws Exception {
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
        instance.setGenericDAO(EasyMock.createMock(CopilotProfileDAO.class));
        instance.setLoggerName("loggerName");
        instance.setCopilotProjectDAO(EasyMock.createMock(CopilotProjectDAO.class));
        try {
            instance.checkInit();
            EasyMock.verify(projectManager);
        } catch (Exception e) {
            fail("should not stand here.");
        }
    }

    /**
     * Failure test for {@link CopilotProfileServiceImpl#checkInit}.Expected CopilotServiceInitializationException.
     *
     * @throws Exception to JUnit
     */
    @SuppressWarnings("unchecked")
    @Test(expected = CopilotServiceInitializationException.class)
    public void testCheckInitFailure() throws Exception {
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
}
