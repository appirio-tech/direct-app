/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.impl;

import static org.junit.Assert.assertEquals;
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
import com.topcoder.direct.services.copilot.dao.EntityNotFoundException;
import com.topcoder.direct.services.copilot.dao.GenericDAO;
import com.topcoder.direct.services.copilot.model.CopilotProfile;
import com.topcoder.direct.services.copilot.model.IdentifiableEntity;

/**
 * Failure tests for {@link GenericServiceImpl}.
 * 
 * @author morehappiness
 * @version 1.0
 */
public class GenericServiceImplFailureTests {

    /**
     * GenericServiceImpl instance for testing.
     */
    private GenericServiceImpl<IdentifiableEntity> instance;

    /**
     * Sets up the test environment.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Before
    public void setUp() throws Exception {
        instance = new GenericServiceImpl<IdentifiableEntity>();
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
     * Failure test for create.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void test_create_failure1() throws Exception {
        CopilotProfile copilotProfile = new CopilotProfile();
        GenericDAO<IdentifiableEntity> genericDAO = EasyMock.createMock(GenericDAO.class);
        instance.setGenericDAO(genericDAO);
        EasyMock.expect(genericDAO.create(copilotProfile)).andThrow(new CopilotDAOException("error"));
        EasyMock.replay(genericDAO);
        instance.create(copilotProfile);
        EasyMock.verify(genericDAO);
    }

    /**
     * Failure test for update.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void test_update_failure1() throws Exception {
        CopilotProfile copilotProfile = new CopilotProfile();
        copilotProfile.setId(1l);
        GenericDAO<IdentifiableEntity> genericDAO = EasyMock.createMock(GenericDAO.class);
        instance.setGenericDAO(genericDAO);
        genericDAO.update(copilotProfile);
        EasyMock.expectLastCall().andThrow(new CopilotDAOException("error"));
        EasyMock.replay(genericDAO);
        instance.update(copilotProfile);
        EasyMock.verify(genericDAO);
    }

    /**
     * Failure test for update.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void test_update_failure2() throws Exception {
        CopilotProfile copilotProfile = new CopilotProfile();
        copilotProfile.setId(1l);
        GenericDAO<IdentifiableEntity> genericDAO = EasyMock.createMock(GenericDAO.class);
        instance.setGenericDAO(genericDAO);
        genericDAO.update(copilotProfile);
        EasyMock.expectLastCall().andThrow(new EntityNotFoundException("error", "d", 1l));
        EasyMock.replay(genericDAO);
        instance.update(copilotProfile);
        EasyMock.verify(genericDAO);
    }

    /**
     * Failure test for delete.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void test_delete_failure1() throws Exception {
        long entityId = 1l;
        GenericDAO<IdentifiableEntity> genericDAO = EasyMock.createMock(GenericDAO.class);
        instance.setGenericDAO(genericDAO);
        genericDAO.delete(entityId);
        EasyMock.expectLastCall().andThrow(new CopilotDAOException("Error"));
        EasyMock.replay(genericDAO);
        instance.delete(entityId);
        EasyMock.verify(genericDAO);
    }

    /**
     * Failure test for delete.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void test_delete_failure2() throws Exception {
        long entityId = 1l;
        GenericDAO<IdentifiableEntity> genericDAO = EasyMock.createMock(GenericDAO.class);
        instance.setGenericDAO(genericDAO);
        genericDAO.delete(entityId);
        EasyMock.expectLastCall().andThrow(new EntityNotFoundException("error", "d", 1l));
        EasyMock.replay(genericDAO);
        instance.delete(entityId);
        EasyMock.verify(genericDAO);
    }

    /**
     * Failure test for retrieve.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void test_retrieve_failure1() throws Exception {
        long entityId = 1l;
        GenericDAO<IdentifiableEntity> genericDAO = EasyMock.createMock(GenericDAO.class);
        instance.setGenericDAO(genericDAO);
        EasyMock.expect(genericDAO.retrieve(entityId)).andThrow(new CopilotDAOException("error"));
        EasyMock.replay(genericDAO);
        instance.retrieve(entityId);
        EasyMock.verify(genericDAO);
    }

    /**
     * Failure test for retrieveAll.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void test_retrieveAll_failure1() throws Exception {
        GenericDAO<IdentifiableEntity> genericDAO = EasyMock.createMock(GenericDAO.class);
        instance.setGenericDAO(genericDAO);
        EasyMock.expect(genericDAO.retrieveAll()).andThrow(new CopilotDAOException("error"));
        EasyMock.replay(genericDAO);
        instance.retrieveAll();
        EasyMock.verify(genericDAO);
    }

}
