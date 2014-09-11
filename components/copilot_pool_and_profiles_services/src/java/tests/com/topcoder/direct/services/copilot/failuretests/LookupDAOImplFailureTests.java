/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.failuretests;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.LookupDAO;

/**
 * Failure test for class LookupDAOImpl.
 *
 * @author extra
 * @version 1.0
 */
public class LookupDAOImplFailureTests {

    /**
     * The dao for failure test.
     */
    private LookupDAO failDao;

    /**
     * <p>
     * Gets test which is compatible with JUnit3 runner.
     * </p>
     *
     * @return The test which is compatible with JUnit3 runner.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(LookupDAOImplFailureTests.class);
    }

    /**
     * <p>
     * Gets <code>LookupDAO</code> instance from Spring bean factory.
     * </p>
     */
    @Before
    public void setUp() {
        failDao = (LookupDAO) FailureTestHelper.getFailBeanFactory().getBean("lookupDAO");
    }

    /**
     * Failure test for method getAllCopilotProfileStatuses.
     *
     * @throws Exception
     *             to Junit
     */
    @Test(expected = CopilotDAOException.class)
    public void testGetAllCopilotProfileStatusesFail1() throws Exception {
        failDao.getAllCopilotProfileStatuses();
    }

    /**
     * Failure test for method getAllCopilotProjectStatuses.
     *
     * @throws Exception
     *             to Junit
     */
    @Test(expected = CopilotDAOException.class)
    public void testGetAllCopilotProjectStatusesFail2() throws Exception {
        failDao.getAllCopilotProjectStatuses();
    }

    /**
     * Failure test for method getAllCopilotTypes.
     *
     * @throws Exception
     *             to Junit
     */
    @Test(expected = CopilotDAOException.class)
    public void testGetAllCopilotTypesFail3() throws Exception {
        failDao.getAllCopilotTypes();
    }
}
