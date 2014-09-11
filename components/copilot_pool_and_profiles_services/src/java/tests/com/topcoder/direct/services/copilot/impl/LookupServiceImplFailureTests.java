/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.copilot.CopilotServiceException;
import com.topcoder.direct.services.copilot.CopilotServiceInitializationException;
import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.LookupDAO;
import com.topcoder.direct.services.copilot.model.CopilotProfileStatus;
import com.topcoder.direct.services.copilot.model.CopilotProjectStatus;
import com.topcoder.direct.services.copilot.model.CopilotType;

/**
 * Failure tests for {@link LookupServiceImpl}.
 * 
 * @author morehappiness
 * @version 1.0
 */
public class LookupServiceImplFailureTests {

    /**
     * LookupServiceImpl instance for testing.
     */
    private LookupServiceImpl instance;

    /**
     * Sets up the test environment.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Before
    public void setUp() throws Exception {
        instance = new LookupServiceImpl();
    }

    /**
     * Failure test for checkInit.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = CopilotServiceInitializationException.class)
    public void test_checkInit_failure1() throws Exception {
        instance.checkInit();
    }

    /**
     * Failure test for getAllCopilotProfileStatuses.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void test_getAllCopilotProfileStatuses_failure1() throws Exception {
        LookupDAO lookupDAO = EasyMock.createMock(LookupDAO.class);
        instance.setLookupDAO(lookupDAO);
        EasyMock.expect(lookupDAO.getAllCopilotProfileStatuses()).andThrow(new CopilotDAOException("error"));
        EasyMock.replay(lookupDAO);
        instance.getAllCopilotProfileStatuses();
        EasyMock.verify(lookupDAO);
    }

    /**
     * Failure test for getAllCopilotProjectStatuses.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void test_getAllCopilotProjectStatuses_failure1() throws Exception {
        LookupDAO lookupDAO = EasyMock.createMock(LookupDAO.class);
        instance.setLookupDAO(lookupDAO);
        EasyMock.expect(lookupDAO.getAllCopilotProjectStatuses()).andThrow(new CopilotDAOException("error"));
        EasyMock.replay(lookupDAO);
        instance.getAllCopilotProjectStatuses();
        EasyMock.verify(lookupDAO);
    }

    /**
     * Failure test for getAllCopilotTypes.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void test_getAllCopilotTypes_failure1() throws Exception {
        LookupDAO lookupDAO = EasyMock.createMock(LookupDAO.class);
        instance.setLookupDAO(lookupDAO);
        EasyMock.expect(lookupDAO.getAllCopilotTypes()).andThrow(new CopilotDAOException("error"));
        EasyMock.replay(lookupDAO);
        instance.getAllCopilotTypes();
        EasyMock.verify(lookupDAO);
    }
}
