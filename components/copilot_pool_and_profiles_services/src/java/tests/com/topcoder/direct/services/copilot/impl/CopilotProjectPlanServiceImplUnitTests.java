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
 * Unit tests for {@link CopilotProjectPlanServiceImpl}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CopilotProjectPlanServiceImplUnitTests {

    /**
     * Represents {@link CopilotProjectPlanServiceImpl} instance for testing.
     */
    private CopilotProjectPlanServiceImpl instance;

    /**
     * Sets up the test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        instance = new CopilotProjectPlanServiceImpl();
    }

    /**
     * Tears down the test environment.
     *
     * @throws Exception to JUnit
     */
    @After
    public void tearDown() throws Exception {
        instance = null;
    }

    /**
     * Accuracy test for {@link CopilotProjectPlanServiceImpl#CopilotProjectPlanServiceImpl()}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testCtor() throws Exception {
        assertNotNull("Unable to instantiate this object", instance);
    }

    /**
     * Accuracy test for {@link CopilotProjectPlanServiceImpl#getCopilotProjectPlan}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testGetCopilotProjectPlan() throws Exception {
        CopilotProjectPlanDAO copilotProjectPlanDAO = EasyMock.createMock(CopilotProjectPlanDAO.class);
        instance.setGenericDAO(copilotProjectPlanDAO);
        long copilotProjectId = 2l;
        CopilotProjectPlan copilotProjectPlan = new CopilotProjectPlan();
        EasyMock.expect(copilotProjectPlanDAO.getCopilotProjectPlan(copilotProjectId)).andReturn(copilotProjectPlan);
        EasyMock.replay(copilotProjectPlanDAO);
        assertTrue("should be same with the copilotProjectPlan.",
            instance.getCopilotProjectPlan(copilotProjectId) == copilotProjectPlan);
        EasyMock.verify(copilotProjectPlanDAO);
    }

    /**
     * Failure test for {@link CopilotProjectPlanServiceImpl#getCopilotProjectPlan}.Expected CopilotServiceException.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void testGetCopilotProjectPlanFailure1() throws Exception {
        CopilotProjectPlanDAO copilotProjectPlanDAO = EasyMock.createMock(CopilotProjectPlanDAO.class);
        instance.setGenericDAO(copilotProjectPlanDAO);
        long copilotProjectId = 2l;
        EasyMock.expect(copilotProjectPlanDAO.getCopilotProjectPlan(copilotProjectId)).andThrow(
            new CopilotDAOException("error"));
        EasyMock.replay(copilotProjectPlanDAO);
        instance.getCopilotProjectPlan(copilotProjectId);
        EasyMock.verify(copilotProjectPlanDAO);
    }

    /**
     * Accuracy test for {@link CopilotProjectPlanServiceImpl#checkInit}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testCheckInit() throws Exception {
        instance.setGenericDAO(EasyMock.createMock(CopilotProjectPlanDAO.class));
        try {
            instance.checkInit();
        } catch (Exception e) {
            fail("should not stand here.");
        }
    }

    /**
     * Failure test for {@link CopilotProjectPlanServiceImpl#checkInit}.Expected
     * CopilotServiceInitializationException.
     *
     * @throws Exception to JUnit
     */
    @SuppressWarnings("unchecked")
    @Test(expected = CopilotServiceInitializationException.class)
    public void testCheckInitFailure() throws Exception {
        instance.setGenericDAO(EasyMock.createMock(GenericDAO.class));
        instance.checkInit();
    }
}
