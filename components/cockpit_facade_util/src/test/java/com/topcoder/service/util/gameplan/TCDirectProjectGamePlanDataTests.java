/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.util.gameplan;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * <p>Unit test for <code>TCDirectProjectGamePlanData</code> class.</p>
 *
 * @author FireIce
 * @version 1.0
 */
public class TCDirectProjectGamePlanDataTests {

    /**
     * <p>Represents the <code>TCDirectProjectGamePlanData</code> class for testing purpose.</p>
     */
    private TCDirectProjectGamePlanData tcDirectProjectGamePlanData;

    /**
     * <p>Set up the testing environment.</p>
     */
    @Before
    public void setUp() {
        tcDirectProjectGamePlanData = new TCDirectProjectGamePlanData();
    }

    /**
     * <p>Tears down the testing environment.</p>
     */
    @After
    public void tearDown() {
        tcDirectProjectGamePlanData = null;
    }

    /**
     * <p>Tests the <code>TCDirectProjectGamePlanData()</code> constructor.</p>
     *
     * <p>Expected: the instance should be created all the time.</p>
     */
    @Test
    public void testTCDirectProjectGamePlanData() {
        assertNotNull("instance should be created.", tcDirectProjectGamePlanData);

        assertTrue("The class should implements Serializable", tcDirectProjectGamePlanData instanceof Serializable);
    }

    /**
     * <p>Tests the <code>getTcDirectProjectId()</code> method.</p>
     *
     * <p>Expected: the default value should be 0.</p>
     */
    @Test
    public void testGetTcDirectProjectId_default() {
        assertEquals("The default value is incorrect.", 0, tcDirectProjectGamePlanData.getTcDirectProjectId());
    }

    /**
     * <p>Tests the <code>setTcDirectProjectId(long)</code> method.</p>
     *
     * <p>Expected: All values are valid to set.</p>
     */
    @Test
    public void testSetTcDirectProjectId_accuracy() {
        long[] values = new long[]{-1, 0, 1};

        for (long value : values) {
            tcDirectProjectGamePlanData.setTcDirectProjectId(value);

            assertEquals("field is not set", value, tcDirectProjectGamePlanData.getTcDirectProjectId());
        }
    }

    /**
     * <p>Tests the <code>getSoftwareProjects()</code> method.</p>
     *
     * <p>Expected: the default value should be not null and empty.</p>
     */
    @Test
    public void testGetSoftwareProjects_default() {
        assertNotNull("The default value is incorrect.", tcDirectProjectGamePlanData.getSoftwareProjects());
        assertTrue("The list should be empty.", tcDirectProjectGamePlanData.getSoftwareProjects().isEmpty());
    }

    /**
     * <p>Tests the <code>setSoftwareProjects(List&lt;SoftwareProjectData&gt;)</code> method.</p>
     *
     * <p>Expected: All values are valid to set.</p>
     */
    @Test
    public void testSetSoftwareProjects_accuracy() {
        tcDirectProjectGamePlanData.setSoftwareProjects(null);
        assertNull("field is not set", tcDirectProjectGamePlanData.getSoftwareProjects());


        List<SoftwareProjectData> value = new ArrayList<SoftwareProjectData>();
        tcDirectProjectGamePlanData.setSoftwareProjects(value);
        assertSame(value, tcDirectProjectGamePlanData.getSoftwareProjects());
    }

    /**
     * <p>Tests the <code>getStudioProjects()</code> method.</p>
     *
     * <p>Expected: the default value should be not null and empty.</p>
     */
    @Test
    public void testGetStudioProjects_default() {
        assertNotNull("The default value is incorrect.", tcDirectProjectGamePlanData.getStudioProjects());
        assertTrue("The list should be empty.", tcDirectProjectGamePlanData.getStudioProjects().isEmpty());
    }

    /**
     * <p>Tests the <code>setStudioProjects(List&lt;StudioProjectData&gt;)</code> method.</p>
     *
     * <p>Expected: All values are valid to set.</p>
     */
    @Test
    public void testSetStudioProjects_accuracy() {
        tcDirectProjectGamePlanData.setStudioProjects(null);
        assertNull("field is not set", tcDirectProjectGamePlanData.getStudioProjects());


        List<StudioProjectData> value = new ArrayList<StudioProjectData>();
        tcDirectProjectGamePlanData.setStudioProjects(value);
        assertSame(value, tcDirectProjectGamePlanData.getStudioProjects());
    }
}
