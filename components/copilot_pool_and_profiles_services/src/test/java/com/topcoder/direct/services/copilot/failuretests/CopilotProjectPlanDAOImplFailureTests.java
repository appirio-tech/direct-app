/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.failuretests;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.CopilotProjectPlanDAO;

/**
 * Failure test for class CopilotProjectPlanDAOImpl.
 *
 * @author extra
 * @version 1.0
 */
public class CopilotProjectPlanDAOImplFailureTests {

    /**
     * The dao for failure test.
     */
    private CopilotProjectPlanDAO dao;

    /**
     * The dao for failure test.
     */
    private CopilotProjectPlanDAO failDao;

    /**
     * <p>
     * Gets test which is compatible with JUnit3 runner.
     * </p>
     *
     * @return The test which is compatible with JUnit3 runner.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(CopilotProjectPlanDAOImplFailureTests.class);
    }

    /**
     * <p>
     * Gets <code>CopilotProjectPlanDAO</code> instance from Spring bean factory.
     * </p>
     */
    @Before
    public void setUp() {
        dao = (CopilotProjectPlanDAO) FailureTestHelper.getBeanFactory().getBean("copilotProjectPlanDAO");

        failDao = (CopilotProjectPlanDAO) FailureTestHelper.getFailBeanFactory().getBean("copilotProjectPlanDAO");
    }

    /**
     * Failure test for method getCopilotProjectPlan.
     *
     * @throws Exception
     *             to Junit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetCopilotProjectPlanFail1() throws Exception {
        dao.getCopilotProjectPlan(-1);
    }

    /**
     * Failure test for method getCopilotProjectPlan.
     *
     * @throws Exception
     *             to Junit
     */
    @Test(expected = CopilotDAOException.class)
    public void testGetCopilotProjectPlanFail2() throws Exception {
        failDao.getCopilotProjectPlan(1);
    }
}
