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
 * <p>Unit test for <code>StudioProjectData</code> class.</p>
 *
 * @author FireIce
 * @version 1.0
 */
public class StudioProjectDataTests {

    /**
     * <p>Represents the <code>StudioProjectData</code> class for testing purpose.</p>
     */
    private StudioProjectData studioProjectData;

    /**
     * <p>Set up the testing environment.</p>
     */
    @Before
    public void setUp() {
        studioProjectData = new StudioProjectData();
    }

    /**
     * <p>Tears down the testing environment.</p>
     */
    @After
    public void tearDown() {
        studioProjectData = null;
    }

    /**
     * <p>Tests the <code>StudioProjectData()</code> constructor.</p>
     *
     * <p>Expected: the instance should be created all the time.</p>
     */
    @Test
    public void testStudioProjectData() {
        assertNotNull("instance should be created.", studioProjectData);

        assertTrue("The class should implements Serializable", studioProjectData instanceof Serializable);
    }

    /**
     * <p>Tests the <code>getTcDirectProjectId()</code> method.</p>
     *
     * <p>Expected: the default value should be 0.</p>
     */
    @Test
    public void testGetTcDirectProjectId_default() {
        assertEquals("The default value is incorrect.", 0, studioProjectData.getTcDirectProjectId());
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
            studioProjectData.setTcDirectProjectId(value);

            assertEquals("field is not set", value, studioProjectData.getTcDirectProjectId());
        }
    }

    /**
     * <p>Tests the <code>getProjectId()</code> method.</p>
     *
     * <p>Expected: the default value should be null.</p>
     */
    @Test
    public void testGetProjectId_default() {
        assertNull("The default value is incorrect.", studioProjectData.getProjectId());
    }

    /**
     * <p>Tests the <code>setProjectId(long)</code> method.</p>
     *
     * <p>Expected: All values are valid to set.</p>
     */
    @Test
    public void testSetProjectId_accuracy() {
        Long[] values = new Long[]{null, 1l};

        for (Long value : values) {
            studioProjectData.setProjectId(value);

            assertEquals("field is not set", value, studioProjectData.getProjectId());
        }
    }

    /**
     * <p>Tests the <code>getContestId()</code> method.</p>
     *
     * <p>Expected: the default value should be 0.</p>
     */
    @Test
    public void testGetContestId_default() {
        assertEquals("The default value is incorrect.", 0, studioProjectData.getContestId());
    }

    /**
     * <p>Tests the <code>setContestId(long)</code> method.</p>
     *
     * <p>Expected: All values are valid to set.</p>
     */
    @Test
    public void testSetContestId_accuracy() {
        long[] values = new long[]{-1, 0, 1};

        for (long value : values) {
            studioProjectData.setContestId(value);

            assertEquals("field is not set", value, studioProjectData.getContestId());
        }
    }

    /**
     * <p>Tests the <code>getProjectName()</code> method.</p>
     *
     * <p>Expected: the default value should be null.</p>
     */
    @Test
    public void testGetProjectName_default() {
        assertNull("The default value is incorrect.", studioProjectData.getProjectName());
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
            studioProjectData.setProjectName(value);

            assertEquals("field is not set", value, studioProjectData.getProjectName());
        }
    }

    /**
     * <p>Tests the <code>getContestName()</code> method.</p>
     *
     * <p>Expected: the default value should be null.</p>
     */
    @Test
    public void testGetContestName_default() {
        assertNull("The default value is incorrect.", studioProjectData.getContestName());
    }

    /**
     * <p>Tests the <code>setContestName(String)</code> method.</p>
     *
     * <p>Expected: All values are valid to set.</p>
     */
    @Test
    public void testSetContestName_accuracy() {
        String[] values = new String[]{null, "", " \t", "valid"};

        for (String value : values) {
            studioProjectData.setContestName(value);

            assertEquals("field is not set", value, studioProjectData.getContestName());
        }
    }

    /**
     * <p>Tests the <code>getStartDate()</code> method.</p>
     *
     * <p>Expected: the default value should be null.</p>
     */
    @Test
    public void testGetStartDate_default() {
        assertNull("The default value is incorrect.", studioProjectData.getStartDate());
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
            studioProjectData.setStartDate(value);

            assertEquals("field is not set", value, studioProjectData.getStartDate());
        }
    }

    /**
     * <p>Tests the <code>getEndDate()</code> method.</p>
     *
     * <p>Expected: the default value should be null.</p>
     */
    @Test
    public void testGetEndDate_default() {
        assertNull("The default value is incorrect.", studioProjectData.getEndDate());
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
            studioProjectData.setEndDate(value);

            assertEquals("field is not set", value, studioProjectData.getEndDate());
        }
    }

    /**
     * <p>Tests the <code>getCreateUserId()</code> method.</p>
     *
     * <p>Expected: the default value should be null.</p>
     */
    @Test
    public void testGetCreateUserId_default() {
        assertNull("The default value is incorrect.", studioProjectData.getCreateUserId());
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
            studioProjectData.setCreateUserId(value);

            assertEquals("field is not set", value, studioProjectData.getCreateUserId());
        }
    }

    /**
     * <p>Tests the <code>getContestStatus()</code> method.</p>
     *
     * <p>Expected: the default value should be null.</p>
     */
    @Test
    public void testGetContestStatus_default() {
        assertNull("The default value is incorrect.", studioProjectData.getContestStatus());
    }

    /**
     * <p>Tests the <code>setContestStatus(String)</code> method.</p>
     *
     * <p>Expected: All values are valid to set.</p>
     */
    @Test
    public void testSetContestStatus_accuracy() {
        String[] values = new String[]{null, "", " \t", "valid"};

        for (String value : values) {
            studioProjectData.setContestStatus(value);

            assertEquals("field is not set", value, studioProjectData.getContestStatus());
        }
    }

    /**
     * <p>Tests the <code>getContestType()</code> method.</p>
     *
     * <p>Expected: the default value should be null.</p>
     */
    @Test
    public void testGetContestType_default() {
        assertNull("The default value is incorrect.", studioProjectData.getContestType());
    }

    /**
     * <p>Tests the <code>setContestType(String)</code> method.</p>
     *
     * <p>Expected: All values are valid to set.</p>
     */
    @Test
    public void testSetContestType_accuracy() {
        String[] values = new String[]{null, "", " \t", "valid"};

        for (String value : values) {
            studioProjectData.setContestType(value);

            assertEquals("field is not set", value, studioProjectData.getContestType());
        }
    }

    /**
     * <p>Tests the <code>isStarted()</code> method.</p>
     *
     * <p>Expected: the default value should be false.</p>
     */
    @Test
    public void testIsStarted_default() {
        assertFalse("The default value is incorrect.", studioProjectData.isStarted());
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
            studioProjectData.setStarted(value);

            assertEquals("field is not set", value, studioProjectData.isStarted());
        }
    }


    /**
     * <p>Tests the <code>isFinished()</code> method.</p>
     *
     * <p>Expected: the default value should be false.</p>
     */
    @Test
    public void testIsFinished_default() {
        assertFalse("The default value is incorrect.", studioProjectData.isFinished());
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
            studioProjectData.setFinished(value);

            assertEquals("field is not set", value, studioProjectData.isFinished());
        }
    }

}