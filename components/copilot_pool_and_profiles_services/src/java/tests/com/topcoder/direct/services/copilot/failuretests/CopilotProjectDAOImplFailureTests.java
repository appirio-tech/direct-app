/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.failuretests;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.CopilotProjectDAO;

/**
 * Failure test for class CopilotProjectDAOImpl.
 *
 * @author extra
 * @version 1.0
 */
public class CopilotProjectDAOImplFailureTests {

    /**
     * The dao for failure test.
     */
    private CopilotProjectDAO dao;

    /**
     * The dao for failure test.
     */
    private CopilotProjectDAO failDao;

    /**
     * <p>
     * Gets test which is compatible with JUnit3 runner.
     * </p>
     *
     * @return The test which is compatible with JUnit3 runner.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(CopilotProjectDAOImplFailureTests.class);
    }

    /**
     * <p>
     * Gets <code>CopilotProjectDAO</code> instance from Spring bean factory.
     * </p>
     */
    @Before
    public void setUp() {
        dao = (CopilotProjectDAO) FailureTestHelper.getBeanFactory().getBean("copilotProjectDAO");

        failDao = (CopilotProjectDAO) FailureTestHelper.getFailBeanFactory().getBean("copilotProjectDAO");
    }

    /**
     * Failure test for method getCopilotProjects.
     *
     * @throws Exception
     *             to Junit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetCopilotProjectsFail1() throws Exception {
        dao.getCopilotProjects(-1);
    }

    /**
     * Failure test for method getCopilotProjects.
     *
     * @throws Exception
     *             to Junit
     */
    @Test(expected = CopilotDAOException.class)
    public void testGetCopilotProjectsFail2() throws Exception {
        failDao.getCopilotProjects(1);
    }
}
