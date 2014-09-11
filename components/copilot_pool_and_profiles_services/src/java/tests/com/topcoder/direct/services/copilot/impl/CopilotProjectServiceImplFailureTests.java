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
 * Failure tests for {@link CopilotProjectServiceImpl}.
 * 
 * @author morehappiness
 * @version 1.0
 */
public class CopilotProjectServiceImplFailureTests {

    /**
     * CopilotProjectServiceImpl instance for testing.
     */
    private CopilotProjectServiceImpl instance;

    /**
     * Sets up the test environment.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Before
    public void setUp() throws Exception {
        instance = new CopilotProjectServiceImpl();
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
        instance.setGenericDAO(EasyMock.createMock(GenericDAO.class));
        instance.checkInit();
    }

    /**
     * Failure test for getCopilotProjects.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void test_getCopilotProjects_failre1() throws Exception {
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
     * @throws Exception
     *             to JUnit
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
        EasyMock.expect(utilityDAO.getCopilotProjectContests(0, 0)).andReturn(new long[]{1});
        EasyMock
            .expect(
                instance.calculateContestsStat(EasyMock.capture(new Capture<List>()), EasyMock
                    .capture(new Capture<Map>()))).andReturn(new ContestsStat());
        instance.countPlannedContestInStats(EasyMock.capture(new Capture<Map>()), EasyMock
            .capture(new Capture<PlannedContest>()));
        return copilotProject;
    }

    /**
     * Failure test for getCopilotProjectDTO.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void test_getCopilotProjectDTO_failre1() throws Exception {
        CopilotProjectDAO copilotProjectDAO = EasyMock.createMock(CopilotProjectDAO.class);
        EasyMock.expect(copilotProjectDAO.retrieve(1)).andThrow(new CopilotDAOException("error"));
        instance.setGenericDAO(copilotProjectDAO);
        EasyMock.replay(copilotProjectDAO);
        instance.getCopilotProjectDTO(1l);
        EasyMock.verify(copilotProjectDAO);
    }

    /**
     * Failure test for getCopilotProjectDTO.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void test_getCopilotProjectDTO_failre2() throws Exception {
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
     * Failure test for getCopilotProjectDTO.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void test_getCopilotProjectDTO_failre3() throws Exception {
        instance = EasyMock.createMockBuilder(CopilotProjectServiceImpl.class).addMockedMethod("calculateContestsStat")
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
        EasyMock.expect(phaseManager.getPhases(EasyMock.aryEq(new long[]{1}))).andThrow(
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
     * Create Phase instance.
     * 
     * @param actualStartDate
     *            the actual start date
     * @param actualEndDate
     *            the actual end date
     * @return Phase instance
     */
    private static Phase createPhase(Date actualStartDate, Date actualEndDate) {
        Phase phase = new Phase(new Project(new Date(), new DefaultWorkdays()), 2);
        phase.setActualEndDate(actualEndDate);
        phase.setActualStartDate(actualStartDate);
        return phase;
    }

}
