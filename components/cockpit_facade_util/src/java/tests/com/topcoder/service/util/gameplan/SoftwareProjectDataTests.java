/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.util.gameplan;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * <p>Unit test for <code>SoftwareProjectData</code> class.</p>
 *
 * @author FireIce
 * @version 1.0
 */
public class SoftwareProjectDataTests {

    /**
     * <p>Represents the <code>SoftwareProjectData</code> class for testing purpose.</p>
     */
    private SoftwareProjectData softwareProjectData;

    /**
     * <p>Set up the testing environment.</p>
     */
    @Before
    public void setUp() {
        softwareProjectData = new SoftwareProjectData();
    }

    /**
     * <p>Tears down the testing environment.</p>
     */
    @After
    public void tearDown() {
        softwareProjectData = null;
    }

    /**
     * <p>Tests the <code>SoftwareProjectData()</code> constructor.</p>
     *
     * <p>Expected: the instance should be created all the time.</p>
     */
    @Test
    public void testSoftwareProjectData() {
        assertNotNull("instance should be created.", softwareProjectData);

        assertTrue("The class should implements Serializable", softwareProjectData instanceof Serializable);
    }

    /**
     * <p>Tests the <code>getTcDirectProjectId()</code> method.</p>
     *
     * <p>Expected: the default value should be 0.</p>
     */
    @Test
    public void testGetTcDirectProjectId_default() {
        assertEquals("The default value is incorrect.", 0, softwareProjectData.getTcDirectProjectId());
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
            softwareProjectData.setTcDirectProjectId(value);

            assertEquals("field is not set", value, softwareProjectData.getTcDirectProjectId());
        }
    }

    /**
     * <p>Tests the <code>getProjectId()</code> method.</p>
     *
     * <p>Expected: the default value should be 0.</p>
     */
    @Test
    public void testGetProjectId_default() {
        assertEquals("The default value is incorrect.", 0, softwareProjectData.getProjectId());
    }

    /**
     * <p>Tests the <code>setProjectId(long)</code> method.</p>
     *
     * <p>Expected: All values are valid to set.</p>
     */
    @Test
    public void testSetProjectId_accuracy() {
        long[] values = new long[]{-1, 0, 1};

        for (long value : values) {
            softwareProjectData.setProjectId(value);

            assertEquals("field is not set", value, softwareProjectData.getProjectId());
        }
    }

    /**
     * <p>Tests the <code>getProjectName()</code> method.</p>
     *
     * <p>Expected: the default value should be null.</p>
     */
    @Test
    public void testGetProjectName_default() {
        assertNull("The default value is incorrect.", softwareProjectData.getProjectName());
    }

    /**
     * <p>Tests the <code>setProjectName(String)</code> method.</p>
     *
     * <p>Expected: All values are valid to set.</p>
     */
    @Test
    public void testSetProjectName_accuracy() {
        String[] values = new String[]{null, "", " \t", "valid"};

        for (String value : values) {
            softwareProjectData.setProjectName(value);

            assertEquals("field is not set", value, softwareProjectData.getProjectName());
        }
    }

    /**
     * <p>Tests the <code>getStartDate()</code> method.</p>
     *
     * <p>Expected: the default value should be null.</p>
     */
    @Test
    public void testGetStartDate_default() {
        assertNull("The default value is incorrect.", softwareProjectData.getStartDate());
    }

    /**
     * <p>Tests the <code>setStartDate(Date)</code> method.</p>
     *
     * <p>Expected: All values are valid to set.</p>
     */
    @Test
    public void testSetStartDate_accuracy() {
        Date[] values = new Date[]{null, new Date()};

        for (Date value : values) {
            softwareProjectData.setStartDate(value);

            assertEquals("field is not set", value, softwareProjectData.getStartDate());
        }
    }

    /**
     * <p>Tests the <code>getEndDate()</code> method.</p>
     *
     * <p>Expected: the default value should be null.</p>
     */
    @Test
    public void testGetEndDate_default() {
        assertNull("The default value is incorrect.", softwareProjectData.getEndDate());
    }

    /**
     * <p>Tests the <code>setEndDate(Date)</code> method.</p>
     *
     * <p>Expected: All values are valid to set.</p>
     */
    @Test
    public void testSetEndDate_accuracy() {
        Date[] values = new Date[]{null, new Date()};

        for (Date value : values) {
            softwareProjectData.setEndDate(value);

            assertEquals("field is not set", value, softwareProjectData.getEndDate());
        }
    }

    /**
     * <p>Tests the <code>getCreateUserId()</code> method.</p>
     *
     * <p>Expected: the default value should be null.</p>
     */
    @Test
    public void testGetCreateUserId_default() {
        assertNull("The default value is incorrect.", softwareProjectData.getCreateUserId());
    }

    /**
     * <p>Tests the <code>setCreateUserId(Date)</code> method.</p>
     *
     * <p>Expected: All values are valid to set.</p>
     */
    @Test
    public void testSetCreateUserId_accuracy() {
        Long[] values = new Long[]{null, 1l};

        for (Long value : values) {
            softwareProjectData.setCreateUserId(value);

            assertEquals("field is not set", value, softwareProjectData.getCreateUserId());
        }
    }

    /**
     * <p>Tests the <code>getDependencyProjectIds()</code> method.</p>
     *
     * <p>Expected: the default value should be null.</p>
     */
    @Test
    public void testGetDependencyProjectIds_default() {
        assertNull("The default value is incorrect.", softwareProjectData.getDependencyProjectIds());
    }

    /**
     * <p>Tests the <code>setDependencyProjectIds(long[])</code> method.</p>
     *
     * <p>Expected: All values are valid to set.</p>
     */
    @Test
    public void testSetDependencyProjectIds_accuracy() {
        long[][] values = new long[][]{null, new long[0], new long[]{1, -1}};
        for (long[] value : values) {
            softwareProjectData.setDependencyProjectIds(value);

            assertEquals("field is not set", value, softwareProjectData.getDependencyProjectIds());
        }
    }

    /**
     * <p>Tests the <code>isRepost()</code> method.</p>
     *
     * <p>Expected: the default value should be false.</p>
     */
    @Test
    public void testIsRepost_default() {
        assertFalse("The default value is incorrect.", softwareProjectData.isRepost());
    }

    /**
     * <p>Tests the <code>setRepost(boolean)</code> method.</p>
     *
     * <p>Expected: All values are valid to set.</p>
     */
    @Test
    public void testSetRepost_accuracy() {
        boolean[] values = new boolean[]{true, false};

        for (boolean value : values) {
            softwareProjectData.setRepost(value);

            assertEquals("field is not set", value, softwareProjectData.isRepost());
        }
    }

    /**
     * <p>Tests the <code>getProjectStatus()</code> method.</p>
     *
     * <p>Expected: the default value should be null.</p>
     */
    @Test
    public void testGetProjectStatus_default() {
        assertNull("The default value is incorrect.", softwareProjectData.getProjectStatus());
    }

    /**
     * <p>Tests the <code>setProjectStatus(String)</code> method.</p>
     *
     * <p>Expected: All values are valid to set.</p>
     */
    @Test
    public void testSetProjectStatus_accuracy() {
        String[] values = new String[]{null, "", " \t", "valid"};

        for (String value : values) {
            softwareProjectData.setProjectStatus(value);

            assertEquals("field is not set", value, softwareProjectData.getProjectStatus());
        }
    }

    /**
     * <p>Tests the <code>getCurrentPhase()</code> method.</p>
     *
     * <p>Expected: the default value should be null.</p>
     */
    @Test
    public void testGetCurrentPhase_default() {
        assertNull("The default value is incorrect.", softwareProjectData.getCurrentPhase());
    }

    /**
     * <p>Tests the <code>setCurrentPhase(String)</code> method.</p>
     *
     * <p>Expected: All values are valid to set.</p>
     */
    @Test
    public void testSetCurrentPhase_accuracy() {
        String[] values = new String[]{null, "", " \t", "valid"};

        for (String value : values) {
            softwareProjectData.setCurrentPhase(value);

            assertEquals("field is not set", value, softwareProjectData.getCurrentPhase());
        }
    }

    /**
     * <p>Tests the <code>getProjectType()</code> method.</p>
     *
     * <p>Expected: the default value should be null.</p>
     */
    @Test
    public void testGetProjectType_default() {
        assertNull("The default value is incorrect.", softwareProjectData.getProjectType());
    }

    /**
     * <p>Tests the <code>setProjectType(String)</code> method.</p>
     *
     * <p>Expected: All values are valid to set.</p>
     */
    @Test
    public void testSetProjectType_accuracy() {
        String[] values = new String[]{null, "", " \t", "valid"};

        for (String value : values) {
            softwareProjectData.setProjectType(value);

            assertEquals("field is not set", value, softwareProjectData.getProjectType());
        }
    }

    /**
     * <p>Tests the <code>isStarted()</code> method.</p>
     *
     * <p>Expected: the default value should be false.</p>
     */
    @Test
    public void testIsStarted_default() {
        assertFalse("The default value is incorrect.", softwareProjectData.isStarted());
    }

    /**
     * <p>Tests the <code>setStarted(boolean)</code> method.</p>
     *
     * <p>Expected: All values are valid to set.</p>
     */
    @Test
    public void testSetStarted_accuracy() {
        boolean[] values = new boolean[]{true, false};

        for (boolean value : values) {
            softwareProjectData.setStarted(value);

            assertEquals("field is not set", value, softwareProjectData.isStarted());
        }
    }


    /**
     * <p>Tests the <code>isFinished()</code> method.</p>
     *
     * <p>Expected: the default value should be false.</p>
     */
    @Test
    public void testIsFinished_default() {
        assertFalse("The default value is incorrect.", softwareProjectData.isFinished());
    }

    /**
     * <p>Tests the <code>setFinished(boolean)</code> method.</p>
     *
     * <p>Expected: All values are valid to set.</p>
     */
    @Test
    public void testSetFinished_accuracy() {
        boolean[] values = new boolean[]{true, false};

        for (boolean value : values) {
            softwareProjectData.setFinished(value);

            assertEquals("field is not set", value, softwareProjectData.isFinished());
        }
    }

}
