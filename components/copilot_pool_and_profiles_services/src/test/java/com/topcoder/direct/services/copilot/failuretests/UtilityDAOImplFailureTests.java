/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.failuretests;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.UtilityDAO;

/**
 * Failure test for class UtilityDAOImpl.
 *
 * @author extra
 * @version 1.0
 */
public class UtilityDAOImplFailureTests {

    /**
     * The dao for failure test.
     */
    private UtilityDAO dao;

    /**
     * The dao for failure test.
     */
    private UtilityDAO failDao;

    /**
     * <p>
     * Gets test which is compatible with JUnit3 runner.
     * </p>
     *
     * @return The test which is compatible with JUnit3 runner.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(UtilityDAOImplFailureTests.class);
    }

    /**
     * <p>
     * Gets <code>UtilityDAO</code> instance from Spring bean factory.
     * </p>
     */
    @Before
    public void setUp() {
        dao = (UtilityDAO) FailureTestHelper.getBeanFactory().getBean("utilityDAO");

        failDao = (UtilityDAO) FailureTestHelper.getFailBeanFactory().getBean("utilityDAO");
    }

    /**
     * Failure test for method getContestBugCount.
     *
     * @throws Exception
     *             to Junit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetContestBugCountFail1() throws Exception {
        dao.getContestBugCount(-1);
    }

    /**
     * Failure test for method getContestBugCount.
     *
     * @throws Exception
     *             to Junit
     */
    @Test(expected = CopilotDAOException.class)
    public void testGetContestBugCountFail2() throws Exception {
        failDao.getContestBugCount(1);
    }

    /**
     * Failure test for method getContestLatestBugResolutionDate.
     *
     * @throws Exception
     *             to Junit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetContestLatestBugResolutionDateFail1() throws Exception {
        dao.getContestLatestBugResolutionDate(-1);
    }

    /**
     * Failure test for method getContestLatestBugResolutionDate.
     *
     * @throws Exception
     *             to Junit
     */
    @Test(expected = CopilotDAOException.class)
    public void testGetContestLatestBugResolutionDateFail2() throws Exception {
        failDao.getContestLatestBugResolutionDate(1);
    }

    /**
     * Failure test for method getCopilotEarnings.
     *
     * @throws Exception
     *             to Junit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetCopilotEarningsFail1() throws Exception {
        dao.getCopilotEarnings(-1);
    }

    /**
     * Failure test for method getCopilotEarnings.
     *
     * @throws Exception
     *             to Junit
     */
    @Test(expected = CopilotDAOException.class)
    public void testGetCopilotEarningsFail2() throws Exception {
        failDao.getCopilotEarnings(1);
    }

    /**
     * Failure test for method getCopilotProjectContests.
     *
     * @throws Exception
     *             to Junit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetCopilotProjectContestsFail1() throws Exception {
        dao.getCopilotProjectContests(-1, 1);
    }

    /**
     * Failure test for method getCopilotProjectContests.
     *
     * @throws Exception
     *             to Junit
     */
    @Test(expected = CopilotDAOException.class)
    public void testGetCopilotProjectContestsFail2() throws Exception {
        failDao.getCopilotProjectContests(1, 1);
    }

}
