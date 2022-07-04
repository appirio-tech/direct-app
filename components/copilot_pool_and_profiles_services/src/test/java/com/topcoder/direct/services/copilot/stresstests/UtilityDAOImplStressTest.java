/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.stresstests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.topcoder.direct.services.copilot.dao.impl.UtilityDAOImpl;

/**
 * <p>
 * Stress tests for class <code>UtilityDAOImpl</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class UtilityDAOImplStressTest extends StressBaseTest {
    /**
     * <p>
     * Represent the UtilityDAOImpl instance that used to call its method for test. It will be
     * initialized in setUp().
     * </p>
     */
    @Autowired
    @Qualifier("utilityDAO")
    private UtilityDAOImpl impl;

    /**
     * <p>
     * Set the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Before
    public void setUp() throws Exception {
        executeBatch(getSessionFactory(), "test_files" + File.separator + "stresstests" + File.separator
            + "sql_insert.sql");
    }

    /**
     * <p>
     * Clear the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @After
    public void tearDown() throws Exception {
        executeBatch(getSessionFactory(), "test_files" + File.separator + "stresstests" + File.separator
            + "sql_delete.sql");
    }

    /**
     * <p>
     * Stress test for the method <code>getContestBugCount()</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetContestBugCount() throws Exception {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < STRESS_TEST_TIME; i++) {

            int result = impl.getContestBugCount(1);

            assertEquals("The return value should be same as ", 2, result);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("The stress test of getContestBugCount spend : " + (endTime - startTime) + " ms");
    }

    /**
     * <p>
     * Stress test for the method <code>getCopilotEarnings()</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetCopilotEarnings() throws Exception {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < STRESS_TEST_TIME; i++) {
            impl.setCopilotPaymentTypeId(1L);

            double result = impl.getCopilotEarnings(1);

            assertEquals("The return value should be same as ", 76, result, 0.0001);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("The stress test of testGetCopilotEarnings spend : " + (endTime - startTime)
            + " ms");
    }

    /**
     * <p>
     * Stress test for the method <code>getContestLatestBugResolutionDate()</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetContestLatestBugResolutionDate() throws Exception {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < STRESS_TEST_TIME; i++) {

            Date result = impl.getContestLatestBugResolutionDate(1);

            assertNotNull("The return value should be not null", result);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("The stress test of testGetContestLatestBugResolutionDate spend : "
            + (endTime - startTime) + " ms");
    }

    /**
     * <p>
     * Stress test for the method <code>getCopilotProjectContests()</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetCopilotProjectContests() throws Exception {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < STRESS_TEST_TIME; i++) {
            impl.setCopilotResourceRoleId(1L);
            impl.setUserResourceInfoTypeId(1L);

            long[] result = impl.getCopilotProjectContests(1, 1);

            assertEquals("The return value should be same as ", 1, result.length);
            assertEquals("The return value should be same as ", 1, result[0]);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("The stress test of testGetCopilotProjectContests spend : "
            + (endTime - startTime) + " ms");
    }

}
