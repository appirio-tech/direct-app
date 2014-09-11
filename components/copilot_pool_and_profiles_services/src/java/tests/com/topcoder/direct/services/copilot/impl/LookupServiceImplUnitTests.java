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
 * Unit tests for {@link LookupServiceImpl}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class LookupServiceImplUnitTests {

    /**
     * Represents {@link LookupServiceImpl} instance for testing.
     */
    private LookupServiceImpl instance;

    /**
     * Sets up the test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        instance = new LookupServiceImpl();
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
     * Accuracy test for {@link LookupServiceImpl#LookupServiceImpl()}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testCtor() throws Exception {
        assertNotNull("Unable to instantiate this object", instance);
    }

    /**
     * Accuracy test for {@link LookupServiceImpl#getAllCopilotProfileStatuses}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testGetAllCopilotProfileStatuses() throws Exception {
        LookupDAO lookupDAO = EasyMock.createMock(LookupDAO.class);
        instance.setLookupDAO(lookupDAO);
        List<CopilotProfileStatus> copilotProfileStatus = new ArrayList<CopilotProfileStatus>();
        EasyMock.expect(lookupDAO.getAllCopilotProfileStatuses()).andReturn(copilotProfileStatus);
        EasyMock.replay(lookupDAO);
        assertTrue("should be same with the copilotProfileStatus.",
            instance.getAllCopilotProfileStatuses() == copilotProfileStatus);
        EasyMock.verify(lookupDAO);
    }

    /**
     * Failure test for {@link LookupServiceImpl#getAllCopilotProfileStatuses}.Expected CopilotServiceException.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void testGetAllCopilotProfileStatusesFailure() throws Exception {
        LookupDAO lookupDAO = EasyMock.createMock(LookupDAO.class);
        instance.setLookupDAO(lookupDAO);
        EasyMock.expect(lookupDAO.getAllCopilotProfileStatuses()).andThrow(new CopilotDAOException("error"));
        EasyMock.replay(lookupDAO);
        instance.getAllCopilotProfileStatuses();
        EasyMock.verify(lookupDAO);
    }

    /**
     * Accuracy test for {@link LookupServiceImpl#getAllCopilotProjectStatuses}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testGetAllCopilotProjectStatuses() throws Exception {
        LookupDAO lookupDAO = EasyMock.createMock(LookupDAO.class);
        instance.setLookupDAO(lookupDAO);
        List<CopilotProjectStatus> copilotProjectStatus = new ArrayList<CopilotProjectStatus>();
        EasyMock.expect(lookupDAO.getAllCopilotProjectStatuses()).andReturn(copilotProjectStatus);
        EasyMock.replay(lookupDAO);
        assertTrue("should be same with the copilotProjectStatus.",
            instance.getAllCopilotProjectStatuses() == copilotProjectStatus);
        EasyMock.verify(lookupDAO);
    }

    /**
     * Failure test for {@link LookupServiceImpl#getAllCopilotProjectStatuses}.Expected CopilotServiceException.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void testGetAllCopilotProjectStatusessFailure() throws Exception {
        LookupDAO lookupDAO = EasyMock.createMock(LookupDAO.class);
        instance.setLookupDAO(lookupDAO);
        EasyMock.expect(lookupDAO.getAllCopilotProjectStatuses()).andThrow(new CopilotDAOException("error"));
        EasyMock.replay(lookupDAO);
        instance.getAllCopilotProjectStatuses();
        EasyMock.verify(lookupDAO);
    }

    /**
     * Accuracy test for {@link LookupServiceImpl#getAllCopilotTypes}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testGetAllCopilotTypes() throws Exception {
        LookupDAO lookupDAO = EasyMock.createMock(LookupDAO.class);
        instance.setLookupDAO(lookupDAO);
        List<CopilotType> copilotTypes = new ArrayList<CopilotType>();
        EasyMock.expect(lookupDAO.getAllCopilotTypes()).andReturn(copilotTypes);
        EasyMock.replay(lookupDAO);
        assertTrue("should be same with the copilotTypes.", instance.getAllCopilotTypes() == copilotTypes);
        EasyMock.verify(lookupDAO);
    }

    /**
     * Failure test for {@link LookupServiceImpl#getAllCopilotTypes}.Expected CopilotServiceException.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void testGetAllCopilotTypesFailure() throws Exception {
        LookupDAO lookupDAO = EasyMock.createMock(LookupDAO.class);
        instance.setLookupDAO(lookupDAO);
        EasyMock.expect(lookupDAO.getAllCopilotTypes()).andThrow(new CopilotDAOException("error"));
        EasyMock.replay(lookupDAO);
        instance.getAllCopilotTypes();
        EasyMock.verify(lookupDAO);
    }

    /**
     * Accuracy test for {@link LookupServiceImpl#setLookupDAO(LookupDAO)}. The lookupDAO should be set correctly.
     */
    @Test
    public void testSetLookupDAO() {
        LookupDAO value = EasyMock.createMock(LookupDAO.class);
        instance.setLookupDAO(value);
        assertTrue("Incorrect value after set to a new one.", value == instance.getLookupDAO());
    }

    /**
     * Accuracy test for {@link LookupServiceImpl#getLookupDAO()}. The default lookupDAO should be returned
     * correctly.
     */
    @Test
    public void testGetLookupDAO() {
        assertNull("Incorrect default value", instance.getLookupDAO());
    }

    /**
     * Accuracy test for {@link LookupServiceImpl#checkInit}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testCheckInit() throws Exception {
        instance.setLookupDAO(EasyMock.createMock(LookupDAO.class));
        try {
            instance.checkInit();
        } catch (Exception e) {
            fail("should not stand here.");
        }
    }

    /**
     * Failure test for {@link LookupServiceImpl#checkInit}.Expected CopilotServiceInitializationException.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = CopilotServiceInitializationException.class)
    public void testCheckInitFailure() throws Exception {
        instance.checkInit();
    }
}
