/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.gameplan.ejb;

import com.topcoder.security.RolePrincipal;
import com.topcoder.security.TCSubject;
import com.topcoder.service.gameplan.GamePlanService;
import com.topcoder.service.util.gameplan.SoftwareProjectData;
import com.topcoder.service.util.gameplan.StudioProjectData;
import com.topcoder.service.util.gameplan.TCDirectProjectGamePlanData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * <p>Unit test for <code>GamePlanServiceBean</code> class with JBoss Application Server.</p>
 *
 * @author FireIce
 * @version 1.0
 */
public class GamePlanServiceBeanWithJBossTests {

    /**
     * <p>Represents the <code>GamePlanService</code> instance for testing.</p>
     */
    private GamePlanService gamePlanService;

    /**
     * <p>Set up the testing environment.</p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        // Get game plan service
        Context context = new InitialContext();
        gamePlanService =
                (GamePlanServiceRemote) context.lookup("GamePlanServiceBean/remote");
    }

    /**
     * <p>Tears down the testing environment.</p>
     */
    @After
    public void tearDown() {
        gamePlanService = null;
    }

    /**
     * <p>Tests the <code>retrieveGamePlanData(TCSubject)</code> method.</p>
     *
     * <p>Condition: no data associated with the given user id.</p>
     *
     * <p>Expected: an empty list should return</p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    @Test
    public void testRetrieveGamePlanData_accuracy1() throws Exception {
        TCSubject tcSubject = new TCSubject(100l);

        List<TCDirectProjectGamePlanData> result = gamePlanService.retrieveGamePlanData(tcSubject);

        assertNotNull("The list should never null.", result);
        assertTrue("The list should be empty.", result.isEmpty());
    }

    /**
     * <p>Tests the <code>retrieveGamePlanData(TCSubject)</code> method.</p>
     *
     * <p>Condition: Try to retrieve game plan data for the given user id.</p>
     *
     * <p>Expected: Retrieves all the valid project data for the given user id.</p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    @Test
    public void testRetrieveGamePlanData_accuracy2() throws Exception {
        TCSubject tcSubject = new TCSubject(1l);

        List<TCDirectProjectGamePlanData> result = gamePlanService.retrieveGamePlanData(tcSubject);
        assertNotNull("The list should never null.", result);
        assertEquals("The list should contain one element.", 1, result.size());

        List<SoftwareProjectData> softwareProjectDataList = result.get(0).getSoftwareProjects();
        assertNotNull("The list should never null.", softwareProjectDataList);
        assertEquals("The list should contain one element.", 1, softwareProjectDataList.size());
        // verify the data
        SoftwareProjectData softwareProjectData = softwareProjectDataList.get(0);
        assertNotNull("The element should not be null", softwareProjectData);
        assertEquals("incorrect data", 1, softwareProjectData.getTcDirectProjectId());
        assertEquals("incorrect data", 1, softwareProjectData.getProjectId());
        assertEquals("incorrect data", "Project Name 1", softwareProjectData.getProjectName());
        assertNotNull("incorrect data", softwareProjectData.getStartDate());
        assertNotNull("incorrect data", softwareProjectData.getEndDate());
        assertEquals("incorrect data", new Long(1), softwareProjectData.getCreateUserId());
        assertNotNull("incorrect data", softwareProjectData.getDependencyProjectIds());
        // assertTrue("incorrect data", softwareProjectData.getDependencyProjectIds().length == 1);
        assertFalse("incorrect data", softwareProjectData.isRepost());
        assertTrue("incorrect data", softwareProjectData.isStarted());
        assertTrue("incorrect data", softwareProjectData.isFinished());
        assertEquals("incorrect data", "Active", softwareProjectData.getProjectStatus());
        assertNull("incorrect data", softwareProjectData.getCurrentPhase());
        assertEquals("incorrect data", "Design", softwareProjectData.getProjectType());


        List<StudioProjectData> studioProjectDataList = result.get(0).getStudioProjects();
        assertNotNull("The list should never null.", studioProjectDataList);
        assertTrue("The list should be empty.", studioProjectDataList.isEmpty());
    }

    /**
     * <p>Tests the <code>retrieveGamePlanData(TCSubject)</code> method.</p>
     *
     * <p>Condition: Try to retrieve game plan data for the given user id.</p>
     *
     * <p>Expected: Retrieves all the valid project data for the given user id.</p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    @Test
    public void testRetrieveGamePlanData_accuracy3() throws Exception {
        TCSubject tcSubject = new TCSubject(2l);
        tcSubject.addPrincipal(new RolePrincipal("Copilot", 1l));

        List<TCDirectProjectGamePlanData> result = gamePlanService.retrieveGamePlanData(tcSubject);
        assertNotNull("The list should never null.", result);
        assertEquals("The list should contain one element.", 1, result.size());

        List<SoftwareProjectData> softwareProjectDataList = result.get(0).getSoftwareProjects();
        assertNotNull("The list should never null.", softwareProjectDataList);
        assertTrue("The list should be empty.", softwareProjectDataList.isEmpty());

        List<StudioProjectData> studioProjectDataList = result.get(0).getStudioProjects();
        assertNotNull("The list should never null.", studioProjectDataList);
        assertEquals("The list should contain one element.", 1, studioProjectDataList.size());
        // verify the data
        StudioProjectData studioProjectData = studioProjectDataList.get(0);
        assertNotNull("The element should not be null", studioProjectData);
        assertEquals("incorrect data", 1, studioProjectData.getTcDirectProjectId());
        assertEquals("incorrect data", new Long(1), studioProjectData.getProjectId());
        assertEquals("incorrect data", 1, studioProjectData.getContestId());
        assertEquals("incorrect data", "Project Name 1", studioProjectData.getProjectName());
        assertEquals("incorrect data", "Contest Name 1", studioProjectData.getContestName());
        assertNotNull("incorrect data", studioProjectData.getStartDate());
        assertNotNull("incorrect data", studioProjectData.getEndDate());
        assertEquals("incorrect data", new Long(2), studioProjectData.getCreateUserId());
        assertTrue("incorrect data", studioProjectData.isStarted());
        assertTrue("incorrect data", studioProjectData.isFinished());
        assertEquals("incorrect data", "Active - Public", studioProjectData.getContestStatus());
        assertNull("incorrect data", studioProjectData.getContestType());
    }

    /**
     * <p>Tests the <code>retrieveGamePlanData(TCSubject)</code> method.</p>
     *
     * <p>Condition: Admin mode is used.</p>
     *
     * <p>Expected: Retrieves all the valid project data.</p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    @Test
    public void testRetrieveGamePlanData_accuracy4() throws Exception {
        TCSubject tcSubject = new TCSubject(2l);
        tcSubject.addPrincipal(new RolePrincipal("Cockpit Administrator", 1l));

        List<TCDirectProjectGamePlanData> result = gamePlanService.retrieveGamePlanData(tcSubject);
        assertNotNull("The list should never null.", result);
        assertEquals("The list should contain one element.", 2, result.size());

        // verify the data
        for (TCDirectProjectGamePlanData tcDirectProjectGamePlanData : result) {
            assertNotNull("The element should not be null", tcDirectProjectGamePlanData);
            assertTrue("incorrect data", tcDirectProjectGamePlanData.getTcDirectProjectId() == 1
                    || tcDirectProjectGamePlanData.getTcDirectProjectId() == 2);

            assertFalse("the list should not be empty", tcDirectProjectGamePlanData.getSoftwareProjects().isEmpty());
            assertFalse("the list should not be empty", tcDirectProjectGamePlanData.getStudioProjects().isEmpty());

            // detail field retrieval are verified in
            // testRetrieveGamePlanData_accuracy2 and testRetrieveGamePlanData_accuracy3.
        }
    }

    /**
     * <p>Tests the <code>retrieveGamePlanData(TCSubject, long)</code> method.</p>
     *
     * <p>Condition: no data associated with the given user id.</p>
     *
     * <p>Expected: an empty list should return</p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    @Test
    public void testRetrieveGamePlanData2_accuracy1() throws Exception {
        TCSubject tcSubject = new TCSubject(100l);

        TCDirectProjectGamePlanData result = gamePlanService.retrieveGamePlanData(tcSubject, -1);

        assertNull("The return value should null.", result);
    }

    /**
     * <p>Tests the <code>retrieveGamePlanData(TCSubject, long)</code> method.</p>
     *
     * <p>Condition: Try to retrieve game plan data for the given user id and TC Direct project ID.</p>
     *
     * <p>Expected: Retrieves The specific TC Direct Project game plan data for the given user.</p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    @Test
    public void testRetrieveGamePlanData2_accuracy2() throws Exception {
        TCSubject tcSubject = new TCSubject(1l);

        TCDirectProjectGamePlanData result = gamePlanService.retrieveGamePlanData(tcSubject, 1L);
        assertNotNull("The list should never null.", result);
        List<SoftwareProjectData> softwareProjectDataList = result.getSoftwareProjects();
        assertNotNull("The list should never null.", softwareProjectDataList);
        assertEquals("The list should contain one element.", 1, softwareProjectDataList.size());
        // verify the data
        SoftwareProjectData softwareProjectData = softwareProjectDataList.get(0);
        assertNotNull("The element should not be null", softwareProjectData);
        assertEquals("incorrect data", 1, softwareProjectData.getTcDirectProjectId());
        assertEquals("incorrect data", 1, softwareProjectData.getProjectId());
        assertEquals("incorrect data", "Project Name 1", softwareProjectData.getProjectName());
        assertNotNull("incorrect data", softwareProjectData.getStartDate());
        assertNotNull("incorrect data", softwareProjectData.getEndDate());
        assertEquals("incorrect data", new Long(1), softwareProjectData.getCreateUserId());
        assertNotNull("incorrect data", softwareProjectData.getDependencyProjectIds());
        // assertTrue("incorrect data", softwareProjectData.getDependencyProjectIds().length == 1);
        assertFalse("incorrect data", softwareProjectData.isRepost());
        assertTrue("incorrect data", softwareProjectData.isStarted());
        assertTrue("incorrect data", softwareProjectData.isFinished());
        assertEquals("incorrect data", "Active", softwareProjectData.getProjectStatus());
        assertNull("incorrect data", softwareProjectData.getCurrentPhase());
        assertEquals("incorrect data", "Design", softwareProjectData.getProjectType());


        List<StudioProjectData> studioProjectDataList = result.getStudioProjects();
        assertNotNull("The list should never null.", studioProjectDataList);
        assertTrue("The list should be empty.", studioProjectDataList.isEmpty());
    }

    /**
     * <p>Tests the <code>retrieveGamePlanData(TCSubject, long)</code> method.</p>
     *
     * <p>Condition: Try to retrieve game plan data for the given user id.</p>
     *
     * <p>Expected: Retrieves all the valid project data for the given user id.</p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    @Test
    public void testRetrieveGamePlanData2_accuracy3() throws Exception {
        TCSubject tcSubject = new TCSubject(1l);
        tcSubject.addPrincipal(new RolePrincipal("Copilot", 1l));

        TCDirectProjectGamePlanData result = gamePlanService.retrieveGamePlanData(tcSubject, 3L);
        assertNull("The return value is null.", result);
    }

    /**
     * <p>Tests the <code>retrieveGamePlanData(TCSubject, long)</code> method.</p>
     *
     * <p>Condition: Admin mode is used.</p>
     *
     * <p>Expected: Retrieves all the valid project data.</p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    @Test
    public void testRetrieveGamePlanData2_accuracy4() throws Exception {
        TCSubject tcSubject = new TCSubject(2l);
        tcSubject.addPrincipal(new RolePrincipal("Cockpit Administrator", 1l));

        TCDirectProjectGamePlanData result = gamePlanService.retrieveGamePlanData(tcSubject, 1L);
        assertNotNull("The list should never null.", result);

        assertNotNull("The element should not be null", result);
        assertEquals("incorrect data", 1, result.getTcDirectProjectId());

        assertFalse("the list should not be empty", result.getSoftwareProjects().isEmpty());
        assertFalse("the list should not be empty", result.getStudioProjects().isEmpty());
    }
}