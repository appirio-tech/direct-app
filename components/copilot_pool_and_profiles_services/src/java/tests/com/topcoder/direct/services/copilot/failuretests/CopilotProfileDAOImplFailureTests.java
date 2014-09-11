/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.failuretests;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.CopilotProfileDAO;

/**
 * Failure test for class CopilotProfileDAOImpl.
 *
 * @author extra
 * @version 1.0
 */
public class CopilotProfileDAOImplFailureTests {

    /**
     * The dao for failure test.
     */
    private CopilotProfileDAO dao;

    /**
     * The dao for failure test.
     */
    private CopilotProfileDAO failDao;

    /**
     * <p>
     * Gets test which is compatible with JUnit3 runner.
     * </p>
     *
     * @return The test which is compatible with JUnit3 runner.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(CopilotProfileDAOImplFailureTests.class);
    }

    /**
     * <p>
     * Gets <code>CopilotProfileDAO</code> instance from Spring bean factory.
     * </p>
     */
    @Before
    public void setUp() {
        dao = (CopilotProfileDAO) FailureTestHelper.getBeanFactory().getBean("copilotProfileDAO");

        failDao = (CopilotProfileDAO) FailureTestHelper.getFailBeanFactory().getBean("copilotProfileDAO");
    }

    /**
     * Failure test for method getCopilotProfile.
     *
     * @throws Exception
     *             to Junit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetCopilotProfileFail1() throws Exception {
        dao.getCopilotProfile(-1);
    }

    /**
     * Failure test for method getCopilotProfile.
     *
     * @throws Exception
     *             to Junit
     */
    @Test(expected = CopilotDAOException.class)
    public void testGetCopilotProfileFail2() throws Exception {
        failDao.getCopilotProfile(1);
    }
}
