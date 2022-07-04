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
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.direct.services.copilot.CopilotServiceException;
import com.topcoder.direct.services.copilot.CopilotServiceInitializationException;
import com.topcoder.direct.services.copilot.TestHelper;
import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.CopilotProfileDAO;
import com.topcoder.direct.services.copilot.dao.CopilotProjectDAO;
import com.topcoder.direct.services.copilot.dao.CopilotProjectPlanDAO;
import com.topcoder.direct.services.copilot.dao.GenericDAO;
import com.topcoder.direct.services.copilot.dao.UtilityDAO;
import com.topcoder.direct.services.copilot.dto.CopilotProjectDTO;
import com.topcoder.direct.services.copilot.model.CopilotProfile;
import com.topcoder.direct.services.copilot.model.CopilotProject;
import com.topcoder.direct.services.copilot.model.CopilotProjectPlan;
import com.topcoder.direct.services.copilot.model.CopilotProjectStatus;
import com.topcoder.direct.services.copilot.model.PlannedContest;
import com.topcoder.management.phase.PhaseManagementException;
import com.topcoder.management.phase.PhaseManager;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectManager;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.project.link.ProjectLinkManager;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.Project;

/**
 * Unit tests for {@link CopilotProjectServiceImpl}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CopilotProjectServiceImplUnitTests {

    /**
     * Represents {@link CopilotProjectServiceImpl} instance for testing.
     */
    private CopilotProjectServiceImpl instance;

    /**
     * Sets up the test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        instance = new CopilotProjectServiceImpl();
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
     * Accuracy test for {@link CopilotProjectServiceImpl#CopilotProjectServiceImpl()}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testCtor() throws Exception {
        assertNotNull("Unable to instantiate this object", instance);
    }

    /**
     * Accuracy test for {@link CopilotProjectServiceImpl#getCopilotProjects}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testGetCopilotProjects1() throws Exception {
        long copilotProfileId = 1l;
        List<CopilotProject> copilotProjects = new ArrayList<CopilotProject>();
        CopilotProject copilotProject = new CopilotProject();
        copilotProjects.add(copilotProject);

        CopilotProjectDAO copilotProjectDAO = EasyMock.createMock(CopilotProjectDAO.class);
        instance.setGenericDAO(copilotProjectDAO);
        EasyMock.expect(copilotProjectDAO.getCopilotProjects(copilotProfileId)).andReturn(copilotProjects);

        CopilotProjectPlanDAO copilotProjectPlanDAO = EasyMock.createMock(CopilotProjectPlanDAO.class);
        instance.setCopilotProjectPlanDAO(copilotProjectPlanDAO);
        // return null for copilotProjectPlan
        EasyMock.expect(copilotProjectPlanDAO.getCopilotProjectPlan(copilotProject.getId())).andReturn(null);
        EasyMock.replay(copilotProjectDAO, copilotProjectPlanDAO);
        List<CopilotProjectDTO> list = instance.getCopilotProjects(copilotProfileId);
        assertTrue("Should return one result.", list.size() == 1);
        assertTrue("Should same with copilotProject.", list.get(0).getCopilotProject() == copilotProject);
        EasyMock.verify(copilotProjectDAO, copilotProjectPlanDAO);
    }

    /**
     * Accuracy test for {@link CopilotProjectServiceImpl#getCopilotProjects}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testGetCopilotProjects2() throws Exception {
        long copilotProfileId = 1l;
        List<CopilotProject> copilotProjects = new ArrayList<CopilotProject>();
        CopilotProject copilotProject = new CopilotProject();
        copilotProjects.add(copilotProject);

        CopilotProjectDAO copilotProjectDAO = EasyMock.createMock(CopilotProjectDAO.class);
        instance.setGenericDAO(copilotProjectDAO);
        EasyMock.expect(copilotProjectDAO.getCopilotProjects(copilotProfileId)).andReturn(copilotProjects);

        CopilotProjectPlanDAO copilotProjectPlanDAO = EasyMock.createMock(CopilotProjectPlanDAO.class);
        instance.setCopilotProjectPlanDAO(copilotProjectPlanDAO);
        // return null for plannedContests
        EasyMock.expect(copilotProjectPlanDAO.getCopilotProjectPlan(copilotProject.getId())).andReturn(
            new CopilotProjectPlan());
        EasyMock.replay(copilotProjectDAO, copilotProjectPlanDAO);
        List<CopilotProjectDTO> list = instance.getCopilotProjects(copilotProfileId);
        assertTrue("Should return one result.", list.size() == 1);
        assertTrue("Should same with copilotProject.", list.get(0).getCopilotProject() == copilotProject);
        EasyMock.verify(copilotProjectDAO, copilotProjectPlanDAO);
    }

    /**
     * Accuracy test for {@link CopilotProjectServiceImpl#getCopilotProjects}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testGetCopilotProjects3() throws Exception {
        long copilotProfileId = 1l;
        List<CopilotProject> copilotProjects = new ArrayList<CopilotProject>();
        CopilotProject copilotProject = new CopilotProject();
        copilotProjects.add(copilotProject);

        CopilotProjectDAO copilotProjectDAO = EasyMock.createMock(CopilotProjectDAO.class);
        instance.setGenericDAO(copilotProjectDAO);
        EasyMock.expect(copilotProjectDAO.getCopilotProjects(copilotProfileId)).andReturn(copilotProjects);

        CopilotProjectPlanDAO copilotProjectPlanDAO = EasyMock.createMock(CopilotProjectPlanDAO.class);
        instance.setCopilotProjectPlanDAO(copilotProjectPlanDAO);
        CopilotProjectPlan copilotProjectPlan = new CopilotProjectPlan();
        copilotProjectPlan.setPlannedContests(new HashSet<PlannedContest>());
        copilotProjectPlan.getPlannedContests().add(new PlannedContest());
        EasyMock.expect(copilotProjectPlanDAO.getCopilotProjectPlan(copilotProject.getId())).andReturn(
            copilotProjectPlan);

        ProjectCategory projectCategory = new ProjectCategory(1l, "n1", new ProjectType(1l, "n2"));
        Map<Long, ProjectCategory> projectCategories = new HashMap<Long, ProjectCategory>();
        projectCategories.put(0l, projectCategory);
        TestHelper.setField(BaseCopilotService.class, instance, "projectCategories", projectCategories);
        CopilotProfileDAO copilotProfileDAO = EasyMock.createMock(CopilotProfileDAO.class);
        instance.setCopilotProfileDAO(copilotProfileDAO);
        EasyMock.expect(copilotProfileDAO.retrieve(0l)).andReturn(null);

        EasyMock.replay(copilotProjectDAO, copilotProjectPlanDAO, copilotProfileDAO);
        List<CopilotProjectDTO> list = instance.getCopilotProjects(copilotProfileId);
        assertTrue("Should return one result.", list.size() == 1);
        assertTrue("Should same with copilotProject.", list.get(0).getCopilotProject() == copilotProject);
        assertTrue("Should return 1.", list.get(0).getTotalPlannedContests() == 1);
        EasyMock.verify(copilotProjectDAO, copilotProjectPlanDAO, copilotProfileDAO);
    }

    /**
     * Failure test for {@link CopilotProjectServiceImpl#getCopilotProjects}.Expected CopilotServiceException.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void testGetCopilotProjectsFailure() throws Exception {
        long copilotProfileId = 1l;
        List<CopilotProject> copilotProjects = new ArrayList<CopilotProject>();
        CopilotProject copilotProject = new CopilotProject();
        copilotProjects.add(copilotProject);
        CopilotProjectDAO copilotProjectDAO = EasyMock.createMock(CopilotProjectDAO.class);
        instance.setGenericDAO(copilotProjectDAO);
        EasyMock.expect(copilotProjectDAO.getCopilotProjects(copilotProfileId)).andThrow(
            new CopilotDAOException("error"));
        EasyMock.replay(copilotProjectDAO);
        instance.getCopilotProjects(copilotProfileId);
        EasyMock.verify(copilotProjectDAO);
    }

    /**
     * Set up for testing getCopilotProjectDTO.
     *
     * @return the CopilotProject instance
     * @throws Exception to JUnit
     */
    @SuppressWarnings("unchecked")
    private CopilotProject setUpForGetCopilotProjectDTO() throws Exception {
        CopilotProjectDAO copilotProjectDAO = EasyMock.createMock(CopilotProjectDAO.class);

        instance.setGenericDAO(copilotProjectDAO);
        CopilotProjectPlanDAO copilotProjectPlanDAO = EasyMock.createMock(CopilotProjectPlanDAO.class);
        CopilotProject copilotProject = new CopilotProject();
        EasyMock.expect(copilotProjectDAO.retrieve(1)).andReturn(copilotProject);
        copilotProjectPlanDAO = EasyMock.createMock(CopilotProjectPlanDAO.class);
        instance.setCopilotProjectPlanDAO(copilotProjectPlanDAO);
        CopilotProjectPlan copilotProjectPlan = new CopilotProjectPlan();
        copilotProjectPlan.setPlannedContests(new HashSet<PlannedContest>());
        copilotProjectPlan.getPlannedContests().add(new PlannedContest());
        EasyMock.expect(copilotProjectPlanDAO.getCopilotProjectPlan(copilotProject.getId())).andReturn(
            copilotProjectPlan);
        CopilotProfileDAO copilotProfileDAO = EasyMock.createMock(CopilotProfileDAO.class);
        instance.setCopilotProfileDAO(copilotProfileDAO);
        CopilotProfile copilotProfile = new CopilotProfile();
        EasyMock.expect(copilotProfileDAO.retrieve(0l)).andReturn(copilotProfile);
        UtilityDAO utilityDAO = EasyMock.createMock(UtilityDAO.class);
        instance.setUtilityDAO(utilityDAO);
        EasyMock.expect(utilityDAO.getCopilotProjectContests(0, 0)).andReturn(new long[] {1});
        EasyMock.expect(
            instance.calculateContestsStat(EasyMock.capture(new Capture<List>()), EasyMock
                .capture(new Capture<Map>()))).andReturn(new ContestsStat());
        instance.countPlannedContestInStats(EasyMock.capture(new Capture<Map>()), EasyMock
            .capture(new Capture<PlannedContest>()));
        return copilotProject;
    }

    /**
     * Accuracy test for {@link CopilotProjectServiceImpl#getCopilotProjectDTO}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testGetCopilotProjectDTO1() throws Exception {
        instance =
            EasyMock.createMockBuilder(CopilotProjectServiceImpl.class).addMockedMethod("calculateContestsStat")
                .addMockedMethod("countPlannedContestInStats").createMock();
        setUpForGetCopilotProjectDTO();
        PhaseManager phaseManager = EasyMock.createMock(PhaseManager.class);
        instance.setPhaseManager(phaseManager);
        Project[] phaseProjects = new Project[1];
        phaseProjects[0] = EasyMock.createMock(Project.class);
        Phase[] phase = new Phase[0];
        EasyMock.expect(phaseProjects[0].getAllPhases()).andReturn(phase);
        EasyMock.expect(phaseManager.getPhases(EasyMock.aryEq(new long[] {1}))).andReturn(phaseProjects);
        EasyMock.replay(phaseProjects[0], instance.getCopilotProfileDAO(), instance.getGenericDAO(), instance
            .getCopilotProjectPlanDAO(), instance.getPhaseManager(), instance.getUtilityDAO(), instance);
        CopilotProjectDTO copilotProjectDTO = instance.getCopilotProjectDTO(1l);
        assertNotNull("Should not be null.", copilotProjectDTO.getCopilotProject());
        assertTrue("Should return 1.", copilotProjectDTO.getTotalPlannedContests() == 1);
        EasyMock.verify(phaseProjects[0], instance.getCopilotProfileDAO(), instance.getGenericDAO(), instance
            .getCopilotProjectPlanDAO(), instance.getPhaseManager(), instance.getUtilityDAO(), instance);
    }

    /**
     * Accuracy test for {@link CopilotProjectServiceImpl#getCopilotProjectDTO}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testGetCopilotProjectDTO2() throws Exception {
        instance =
            EasyMock.createMockBuilder(CopilotProjectServiceImpl.class).addMockedMethod("calculateContestsStat")
                .addMockedMethod("countPlannedContestInStats").createMock();
        CopilotProject copilotProject = setUpForGetCopilotProjectDTO();
        copilotProject.setStatus(new CopilotProjectStatus());
        copilotProject.getStatus().setId(2l);
        copilotProject.setCompletionDate(new Date());
        PhaseManager phaseManager = EasyMock.createMock(PhaseManager.class);
        instance.setPhaseManager(phaseManager);
        Project[] phaseProjects = new Project[1];
        phaseProjects[0] = EasyMock.createMock(Project.class);
        Phase[] phase = new Phase[2];
        EasyMock.expect(phaseProjects[0].getAllPhases()).andReturn(phase);
        EasyMock.expect(phaseManager.getPhases(EasyMock.aryEq(new long[] {1}))).andReturn(phaseProjects);
        phase[0] = createPhase(new Date(), new Date());
        phase[1] = createPhase(new Date(), new Date());
        EasyMock.replay(phaseProjects[0], instance.getCopilotProfileDAO(), instance.getGenericDAO(), instance
            .getCopilotProjectPlanDAO(), instance.getPhaseManager(), instance.getUtilityDAO(), instance);
        CopilotProjectDTO copilotProjectDTO = instance.getCopilotProjectDTO(1l);
        assertNotNull("Should not be null.", copilotProjectDTO.getCopilotProject());
        assertTrue("Should return 1.", copilotProjectDTO.getTotalPlannedContests() == 1);
        EasyMock.verify(phaseProjects[0], instance.getCopilotProfileDAO(), instance.getGenericDAO(), instance
            .getCopilotProjectPlanDAO(), instance.getPhaseManager(), instance.getUtilityDAO(), instance);
    }

    /**
     * Accuracy test for {@link CopilotProjectServiceImpl#getCopilotProjectDTO}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testGetCopilotProjectDTO3() throws Exception {
        CopilotProjectDAO copilotProjectDAO = EasyMock.createMock(CopilotProjectDAO.class);
        EasyMock.expect(copilotProjectDAO.retrieve(1)).andReturn(null);
        instance.setGenericDAO(copilotProjectDAO);
        EasyMock.replay(copilotProjectDAO);
        assertNull("Should return null.", instance.getCopilotProjectDTO(1l));
        EasyMock.verify(copilotProjectDAO);
    }

    /**
     * Accuracy test for {@link CopilotProjectServiceImpl#getCopilotProjectDTO}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testGetCopilotProjectDTO4() throws Exception {
        instance =
            EasyMock.createMockBuilder(CopilotProjectServiceImpl.class).addMockedMethod("calculateContestsStat")
                .addMockedMethod("countPlannedContestInStats").createMock();
        CopilotProject copilotProject = setUpForGetCopilotProjectDTO();
        copilotProject.setStatus(new CopilotProjectStatus());
        copilotProject.setCompletionDate(new Date());
        PhaseManager phaseManager = EasyMock.createMock(PhaseManager.class);
        instance.setPhaseManager(phaseManager);
        Project[] phaseProjects = new Project[1];
        phaseProjects[0] = EasyMock.createMock(Project.class);
        EasyMock.expect(phaseProjects[0].getId()).andReturn(1l);
        Phase[] phase = new Phase[2];
        EasyMock.expect(phaseProjects[0].getAllPhases()).andReturn(phase);
        EasyMock.expect(phaseManager.getPhases(EasyMock.aryEq(new long[] {1}))).andReturn(phaseProjects);
        phase[0] = createPhase(new Date(), new Date());
        phase[1] = createPhase(new Date(), new Date());
        EasyMock.expect(instance.getUtilityDAO().getContestLatestBugResolutionDate(1l)).andReturn(new Date());
        EasyMock.replay(phaseProjects[0], instance.getCopilotProfileDAO(), instance.getGenericDAO(), instance
            .getCopilotProjectPlanDAO(), instance.getPhaseManager(), instance.getUtilityDAO(), instance);
        CopilotProjectDTO copilotProjectDTO = instance.getCopilotProjectDTO(1l);
        assertNotNull("Should not be null.", copilotProjectDTO.getCopilotProject());
        assertTrue("Should return 1.", copilotProjectDTO.getTotalPlannedContests() == 1);
        EasyMock.verify(phaseProjects[0], instance.getCopilotProfileDAO(), instance.getGenericDAO(), instance
            .getCopilotProjectPlanDAO(), instance.getPhaseManager(), instance.getUtilityDAO(), instance);
    }

    /**
     * Failure test for {@link CopilotProjectServiceImpl#getCopilotProjectDTO}.Expected CopilotServiceException.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void testGetCopilotProjectDTOFailure1() throws Exception {
        CopilotProjectDAO copilotProjectDAO = EasyMock.createMock(CopilotProjectDAO.class);
        EasyMock.expect(copilotProjectDAO.retrieve(1)).andThrow(new CopilotDAOException("error"));
        instance.setGenericDAO(copilotProjectDAO);
        EasyMock.replay(copilotProjectDAO);
        instance.getCopilotProjectDTO(1l);
        EasyMock.verify(copilotProjectDAO);
    }

    /**
     * Failure test for {@link CopilotProjectServiceImpl#getCopilotProjectDTO}.Expected CopilotServiceException.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void testGetCopilotProjectDTOFailure2() throws Exception {
        CopilotProjectDAO copilotProjectDAO = EasyMock.createMock(CopilotProjectDAO.class);
        EasyMock.expect(copilotProjectDAO.retrieve(1)).andReturn(new CopilotProject());
        instance.setGenericDAO(copilotProjectDAO);
        CopilotProjectPlanDAO copilotProjectPlanDAO = EasyMock.createMock(CopilotProjectPlanDAO.class);
        CopilotProject copilotProject = new CopilotProject();
        copilotProjectPlanDAO = EasyMock.createMock(CopilotProjectPlanDAO.class);
        instance.setCopilotProjectPlanDAO(copilotProjectPlanDAO);
        CopilotProjectPlan copilotProjectPlan = new CopilotProjectPlan();
        copilotProjectPlan.setPlannedContests(new HashSet<PlannedContest>());
        copilotProjectPlan.getPlannedContests().add(new PlannedContest());
        EasyMock.expect(copilotProjectPlanDAO.getCopilotProjectPlan(copilotProject.getId())).andThrow(
            new CopilotDAOException("error"));
        EasyMock.replay(copilotProjectDAO, copilotProjectPlanDAO);
        instance.getCopilotProjectDTO(1l);
        EasyMock.verify(copilotProjectDAO, copilotProjectPlanDAO);
    }

    /**
     * Failure test for {@link CopilotProjectServiceImpl#getCopilotProjectDTO}.Expected CopilotServiceException.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void testGetCopilotProjectDTOFailure3() throws Exception {
        instance =
            EasyMock.createMockBuilder(CopilotProjectServiceImpl.class).addMockedMethod("calculateContestsStat")
                .addMockedMethod("countPlannedContestInStats").createMock();
        CopilotProject copilotProject = setUpForGetCopilotProjectDTO();
        copilotProject.setStatus(new CopilotProjectStatus());
        copilotProject.getStatus().setId(2l);
        copilotProject.setCompletionDate(new Date());
        PhaseManager phaseManager = EasyMock.createMock(PhaseManager.class);
        instance.setPhaseManager(phaseManager);
        Project[] phaseProjects = new Project[1];
        phaseProjects[0] = EasyMock.createMock(Project.class);
        Phase[] phase = new Phase[2];
        EasyMock.expect(phaseProjects[0].getAllPhases()).andReturn(phase);
        EasyMock.expect(phaseManager.getPhases(EasyMock.aryEq(new long[] {1}))).andThrow(
            new PhaseManagementException("error"));
        phase[0] = createPhase(new Date(), new Date());
        phase[1] = createPhase(new Date(), new Date());
        EasyMock.replay(phaseProjects[0], instance.getCopilotProfileDAO(), instance.getGenericDAO(), instance
            .getCopilotProjectPlanDAO(), instance.getPhaseManager(), instance.getUtilityDAO(), instance);
        instance.getCopilotProjectDTO(1l);
        EasyMock.verify(phaseProjects[0], instance.getCopilotProfileDAO(), instance.getGenericDAO(), instance
            .getCopilotProjectPlanDAO(), instance.getPhaseManager(), instance.getUtilityDAO(), instance);
    }

    /**
     * Failure test for {@link CopilotProjectServiceImpl#getCopilotProjectDTO}.Expected CopilotServiceException.
     *
     * @throws Exception to JUnit
     */
    @SuppressWarnings("unchecked")
    @Test(expected = CopilotServiceException.class)
    public void testGetCopilotProjectDTOFailure4() throws Exception {
        instance =
            EasyMock.createMockBuilder(CopilotProjectServiceImpl.class).addMockedMethod("calculateContestsStat")
                .addMockedMethod("countPlannedContestInStats").createMock();
        CopilotProjectDAO copilotProjectDAO = EasyMock.createMock(CopilotProjectDAO.class);
        EasyMock.expect(copilotProjectDAO.retrieve(1)).andReturn(new CopilotProject());
        instance.setGenericDAO(copilotProjectDAO);
        CopilotProjectPlanDAO copilotProjectPlanDAO = EasyMock.createMock(CopilotProjectPlanDAO.class);
        CopilotProject copilotProject = new CopilotProject();
        copilotProjectPlanDAO = EasyMock.createMock(CopilotProjectPlanDAO.class);
        instance.setCopilotProjectPlanDAO(copilotProjectPlanDAO);
        CopilotProjectPlan copilotProjectPlan = new CopilotProjectPlan();
        copilotProjectPlan.setPlannedContests(new HashSet<PlannedContest>());
        copilotProjectPlan.getPlannedContests().add(new PlannedContest());
        EasyMock.expect(copilotProjectPlanDAO.getCopilotProjectPlan(copilotProject.getId())).andReturn(
            copilotProjectPlan);
        CopilotProfileDAO copilotProfileDAO = EasyMock.createMock(CopilotProfileDAO.class);
        instance.setCopilotProfileDAO(copilotProfileDAO);
        CopilotProfile copilotProfile = new CopilotProfile();
        EasyMock.expect(copilotProfileDAO.retrieve(0l)).andReturn(copilotProfile);
        UtilityDAO utilityDAO = EasyMock.createMock(UtilityDAO.class);
        instance.setUtilityDAO(utilityDAO);
        EasyMock.expect(utilityDAO.getCopilotProjectContests(0, 0)).andReturn(new long[] {1});
        EasyMock.expect(
            instance.calculateContestsStat(EasyMock.capture(new Capture<List>()), EasyMock
                .capture(new Capture<Map>()))).andThrow(new CopilotServiceException("error"));
        instance.countPlannedContestInStats(EasyMock.capture(new Capture<Map>()), EasyMock
            .capture(new Capture<PlannedContest>()));
        EasyMock.replay(instance.getCopilotProfileDAO(), instance.getGenericDAO(), instance
            .getCopilotProjectPlanDAO(), instance.getUtilityDAO(), instance);
        instance.getCopilotProjectDTO(1l);
        EasyMock.verify(instance.getCopilotProfileDAO(), instance.getGenericDAO(), instance
            .getCopilotProjectPlanDAO(), instance.getUtilityDAO(), instance);
    }

    /**
     * Failure test for {@link CopilotProjectServiceImpl#getCopilotProjectDTO}.Expected CopilotServiceException.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void testGetCopilotProjectDTOFailure5() throws Exception {
        instance =
            EasyMock.createMockBuilder(CopilotProjectServiceImpl.class).addMockedMethod("calculateContestsStat")
                .addMockedMethod("countPlannedContestInStats").createMock();
        CopilotProject copilotProject = setUpForGetCopilotProjectDTO();
        copilotProject.setStatus(new CopilotProjectStatus());
        copilotProject.getStatus().setId(2l);
        instance.setActiveCopilotProjectStatusId(2l);
        copilotProject.setCompletionDate(new Date());
        PhaseManager phaseManager = EasyMock.createMock(PhaseManager.class);
        instance.setPhaseManager(phaseManager);
        Project[] phaseProjects = new Project[1];
        phaseProjects[0] = EasyMock.createMock(Project.class);
        Phase[] phase = new Phase[2];
        EasyMock.expect(phaseProjects[0].getAllPhases()).andReturn(phase);
        EasyMock.expect(phaseManager.getPhases(EasyMock.aryEq(new long[] {1}))).andReturn(phaseProjects);
        phase[0] = createPhase(null, new Date());
        phase[1] = createPhase(new Date(), new Date());
        EasyMock.replay(phaseProjects[0], instance.getCopilotProfileDAO(), instance.getGenericDAO(), instance
            .getCopilotProjectPlanDAO(), instance.getPhaseManager(), instance.getUtilityDAO(), instance);
        instance.getCopilotProjectDTO(1l);
        EasyMock.verify(phaseProjects[0], instance.getCopilotProfileDAO(), instance.getGenericDAO(), instance
            .getCopilotProjectPlanDAO(), instance.getPhaseManager(), instance.getUtilityDAO(), instance);
    }

    /**
     * Create Phase instance.
     *
     * @param actualStartDate the actual start date
     * @param actualEndDate the actual end date
     * @return Phase instance
     */
    private static Phase createPhase(Date actualStartDate, Date actualEndDate) {
        Phase phase = new Phase(new Project(new Date(), new DefaultWorkdays()), 2);
        phase.setActualEndDate(actualEndDate);
        phase.setActualStartDate(actualStartDate);
        return phase;
    }

    /**
     * Accuracy test for {@link CopilotProjectServiceImpl#checkInit}.
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
        instance.setGenericDAO(EasyMock.createMock(CopilotProjectDAO.class));
        instance.setLoggerName("loggerName");
        instance.setCopilotProfileDAO(EasyMock.createMock(CopilotProfileDAO.class));
        instance.setPhaseManager(EasyMock.createMock(PhaseManager.class));
        try {
            instance.checkInit();
            EasyMock.verify(projectManager);
        } catch (Exception e) {
            fail("should not stand here.");
        }
    }

    /**
     * Failure test for {@link CopilotProjectServiceImpl#checkInit}.Expected CopilotServiceInitializationException.
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
        instance.setGenericDAO(EasyMock.createMock(GenericDAO.class));
        instance.checkInit();
    }

    /**
     * Accuracy test for {@link CopilotProjectServiceImpl#setCopilotProfileDAO(CopilotProfileDAO)}. The
     * copilotProfileDAO should be set correctly.
     */
    @Test
    public void testSetCopilotProfileDAO() {
        CopilotProfileDAO value = EasyMock.createMock(CopilotProfileDAO.class);
        instance.setCopilotProfileDAO(value);
        assertTrue("Incorrect value after set to a new one.", value == instance.getCopilotProfileDAO());
    }

    /**
     * Accuracy test for {@link CopilotProjectServiceImpl#getCopilotProfileDAO()}. The default copilotProfileDAO
     * should be returned correctly.
     */
    @Test
    public void testGetCopilotProfileDAO() {
        assertNull("Incorrect default value", instance.getCopilotProfileDAO());
    }

    /**
     * Accuracy test for {@link CopilotProjectServiceImpl#setPhaseManager(PhaseManager)}. The phaseManager should be
     * set correctly.
     */
    @Test
    public void testSetPhaseManager() {
        PhaseManager value = EasyMock.createMock(PhaseManager.class);
        instance.setPhaseManager(value);
        assertTrue("Incorrect value after set to a new one.", value == instance.getPhaseManager());
    }

    /**
     * Accuracy test for {@link CopilotProjectServiceImpl#getPhaseManager()}. The default phaseManager should be
     * returned correctly.
     */
    @Test
    public void testGetPhaseManager() {
        assertNull("Incorrect default value", instance.getPhaseManager());
    }

}
