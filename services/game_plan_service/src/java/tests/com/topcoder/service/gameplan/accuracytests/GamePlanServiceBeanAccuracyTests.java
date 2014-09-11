/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.gameplan.accuracytests;

import java.sql.Connection;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.topcoder.security.RolePrincipal;
import com.topcoder.security.TCSubject;
import com.topcoder.service.gameplan.ejb.GamePlanServiceBean;
import com.topcoder.service.util.gameplan.SoftwareProjectData;
import com.topcoder.service.util.gameplan.StudioProjectData;
import com.topcoder.service.util.gameplan.TCDirectProjectGamePlanData;

/**
 * Set of tests for testing GamePlanServiceBean class.
 * @author vilain
 * @version 1.0
 */
public class GamePlanServiceBeanAccuracyTests {

    /**
     * Connection to tcs_catalog DB for testing.
     */
    private Connection tcsCatalogConnection;

    /**
     * Connection to studio_oltp DB for testing.
     */
    private Connection studioOltpConnection;

    /**
     * Instance of GamePlanServiceBean for testing.
     */
    private GamePlanServiceBean gamePlanServiceBean;

    /**
     * Setting up environment.
     * @throws Exception wraps all exceptions
     */
    @Before
    public void setUp() throws Exception {
        tcsCatalogConnection = AccuracyTestHelper.createConnection(false);
        studioOltpConnection = AccuracyTestHelper.createConnection(true);
        AccuracyTestHelper.clearTables(tcsCatalogConnection, false);
        AccuracyTestHelper.clearTables(studioOltpConnection, true);
        AccuracyTestHelper.addTestData(tcsCatalogConnection, false);
        AccuracyTestHelper.addTestData(studioOltpConnection, true);
        gamePlanServiceBean = new GamePlanServiceBean();
        AccuracyTestHelper.setFieldValue(gamePlanServiceBean, "softwareEntityManager", AccuracyTestHelper
            .createEntityManager(false));
        AccuracyTestHelper.setFieldValue(gamePlanServiceBean, "studioEntityManager", AccuracyTestHelper
            .createEntityManager(true));
    }

    /**
     * Method under test GamePlanServiceBean#retrieveGamePlanData(...). No game
     * plans are expected for user 996.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testRetrieveGamePlanDataByUserNone() throws Exception {
        TCSubject subject = new TCSubject(996);
        List<TCDirectProjectGamePlanData> gamePlanData = gamePlanServiceBean.retrieveGamePlanData(subject);
        Assert.assertTrue("no game plans are expected for user 996", gamePlanData.isEmpty());
    }

    /**
     * Method under test GamePlanServiceBean#retrieveGamePlanData(...). One game
     * plan with 1/1 software/studio projects is expected for user 997.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testRetrieveGamePlanDataByUserFirst() throws Exception {
        TCSubject subject = new TCSubject(997);
        List<TCDirectProjectGamePlanData> gamePlanData = gamePlanServiceBean.retrieveGamePlanData(subject);
        Assert.assertEquals("one game plan is expected for user 997", 1, gamePlanData.size());
        List<SoftwareProjectData> softwareProjects = gamePlanData.get(0).getSoftwareProjects();
        Assert.assertEquals("one software project is expected for user 997", 1, softwareProjects.size());
        assertSoftwareProject(softwareProjects.get(0), 997l, 1l, "GLUI", "Cancelled - Failed Screening",
            "Security", 1l);
        List<StudioProjectData> studioProjects = gamePlanData.get(0).getStudioProjects();
        Assert.assertEquals("one studio project is expected for user 997", 1, studioProjects.size());
        assertStudioProject(studioProjects.get(0), 1, "First Studio Contest", "Active - Public", 997l, 1l,
            "GLUI", 1);
    }

    /**
     * Method under test GamePlanServiceBean#retrieveGamePlanData(...). One game
     * plan with 1/3 software/studio projects is expected for user 998.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testRetrieveGamePlanDataByUserSecond() throws Exception {
        TCSubject subject = new TCSubject(998);
        List<TCDirectProjectGamePlanData> gamePlanData = gamePlanServiceBean.retrieveGamePlanData(subject);
        Assert.assertEquals("one game plan is expected for user 998", 1, gamePlanData.size());
        List<SoftwareProjectData> softwareProjects = gamePlanData.get(0).getSoftwareProjects();
        Assert.assertEquals("one software project is expected for user 998", 1, softwareProjects.size());
        assertSoftwareProject(softwareProjects.get(0), 998l, 2l, "Vesta", "Completed", "Architecture", 2l);
        List<StudioProjectData> studioProjects = gamePlanData.get(0).getStudioProjects();
        Assert.assertEquals("three studio projects are expected for user 998", 3, studioProjects.size());
        assertStudioProject(studioProjects.get(0), 2, "Second Studio Contest", "No Winner Chosen", 998l, 2l,
            "Vesta", 2);
        assertStudioProject(studioProjects.get(1), 3, "Third Studio Contest", "In Danger", 998l, 2l, "Vesta",
            2);
        assertStudioProject(studioProjects.get(2), 4, "Fourth Studio Contest",
            "Insufficient Submissions - ReRun Possible", 998l, 2l, "Vesta", 2);
    }

    /**
     * Method under test GamePlanServiceBean#retrieveGamePlanData(...). Two game
     * plans are expected for admin user.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testRetrieveGamePlanDataByAdmin() throws Exception {
        TCSubject subject = new TCSubject(999);
        subject.addPrincipal(new RolePrincipal("Cockpit Administrator", 1));
        List<TCDirectProjectGamePlanData> gamePlanData = gamePlanServiceBean.retrieveGamePlanData(subject);
        Assert.assertEquals("two game plans are expected for admin user", 2, gamePlanData.size());
        // Active - Public
        // No Winner Chosen
        // In Danger
        // Insufficient Submissions - ReRun Possible
    }

    /**
     * Asserts software project.
     * @param softwareProject software project under consideration
     * @param createUserId create user id
     * @param projectId project id
     * @param projectName project name
     * @param projectStatus project status
     * @param projectType project type
     * @param tcDirectProjectId tc direct project id
     */
    private static void assertSoftwareProject(SoftwareProjectData softwareProject, Long createUserId,
        long projectId, String projectName, String projectStatus, String projectType, long tcDirectProjectId) {
        Assert.assertEquals("create user ids must be equal", createUserId, softwareProject.getCreateUserId());
        Assert.assertEquals("project ids must be equal", projectId, softwareProject.getProjectId());
        Assert.assertEquals("project names must be equal", projectName, softwareProject.getProjectName());
        Assert.assertEquals("project statuses must be equal", projectStatus, softwareProject
            .getProjectStatus());
        Assert.assertEquals("project types must be equal", projectType, softwareProject.getProjectType());
        Assert.assertEquals("tc direct project ids must be equal", tcDirectProjectId, softwareProject
            .getTcDirectProjectId());
    }

    /**
     * Asserts studio project.
     * @param studioProject studio project
     * @param contestId contest id
     * @param contestName contest name
     * @param contestStatus contest status
     * @param createUserId create user id
     * @param projectId project id
     * @param projectName project name
     * @param tcDirectProjectId tc direct project id
     */
    private static void assertStudioProject(StudioProjectData studioProject, long contestId,
        String contestName, String contestStatus, Long createUserId, Long projectId, String projectName,
        long tcDirectProjectId) {
        Assert.assertEquals("contest ids must be equal", contestId, studioProject.getContestId());
        Assert.assertEquals("contest names must be equal", contestName, studioProject.getContestName());
        Assert
            .assertEquals("contest statuses must be equal", contestStatus, studioProject.getContestStatus());
        Assert.assertEquals("create user ids must be equal", createUserId, studioProject.getCreateUserId());
        Assert.assertEquals("project ids must be equal", projectId, studioProject.getProjectId());
        Assert.assertEquals("project names must be equal", projectName, studioProject.getProjectName());
        Assert.assertEquals("tc direct project ids must be equal", tcDirectProjectId, studioProject
            .getTcDirectProjectId());
        Assert.assertNotNull("start date must not be null", studioProject.getStartDate());
        Assert.assertNotNull("end date must not be null", studioProject.getEndDate());
    }

    /**
     * Tearing down environment.
     * @throws Exception wraps all exceptions
     */
    @After
    public void tearDown() throws Exception {
        if (tcsCatalogConnection != null) {
            AccuracyTestHelper.clearTables(tcsCatalogConnection, false);
            tcsCatalogConnection.close();
        }
        if (studioOltpConnection != null) {
            AccuracyTestHelper.clearTables(studioOltpConnection, true);
            studioOltpConnection.close();
        }
    }
}