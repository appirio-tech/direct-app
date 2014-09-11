/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.copilot.CopilotServiceException;
import com.topcoder.direct.services.copilot.CopilotServiceInitializationException;
import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.CopilotProjectPlanDAO;
import com.topcoder.direct.services.copilot.dao.GenericDAO;
import com.topcoder.direct.services.copilot.model.CopilotProjectPlan;

/**
 * Failure tests for {@link CopilotProjectPlanServiceImpl}.
 * 
 * @author morehappiness
 * @version 1.0
 */
public class CopilotProjectPlanServiceImplFailureTests {

    /**
     * CopilotProjectPlanServiceImpl instance for testing.
     */
    private CopilotProjectPlanServiceImpl instance;

    /**
     * Sets up the test environment.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Before
    public void setUp() throws Exception {
        instance = new CopilotProjectPlanServiceImpl();
    }


    /**
     * Failure test for checkInit.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = CopilotServiceInitializationException.class)
    public void testCheckInitFailure() throws Exception {
        instance.setGenericDAO(EasyMock.createMock(GenericDAO.class));
        instance.checkInit();
    }

    /**
     * Failure test for getCopilotProjectPlan.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void test_getCopilotProjectPlan_failure1() throws Exception {
        CopilotProjectPlanDAO copilotProjectPlanDAO = EasyMock.createMock(CopilotProjectPlanDAO.class);
        instance.setGenericDAO(copilotProjectPlanDAO);
        long copilotProjectId = 2l;
        EasyMock.expect(copilotProjectPlanDAO.getCopilotProjectPlan(copilotProjectId)).andThrow(
            new CopilotDAOException("error"));
        EasyMock.replay(copilotProjectPlanDAO);
        instance.getCopilotProjectPlan(copilotProjectId);
        EasyMock.verify(copilotProjectPlanDAO);
    }
}
