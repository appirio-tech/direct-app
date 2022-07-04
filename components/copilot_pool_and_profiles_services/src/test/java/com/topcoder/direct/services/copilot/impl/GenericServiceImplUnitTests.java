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
 * Unit tests for {@link GenericServiceImpl}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class GenericServiceImplUnitTests {

    /**
     * Represents {@link GenericServiceImpl} instance for testing.
     */
    private GenericServiceImpl<IdentifiableEntity> instance;

    /**
     * Sets up the test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        instance = new GenericServiceImpl<IdentifiableEntity>();
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
     * Accuracy test for {@link GenericServiceImpl#GenericServiceImpl()}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testCtor() throws Exception {
        assertNotNull("Unable to instantiate this object", instance);
    }

    /**
     * Accuracy test for {@link GenericServiceImpl#create}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testCreate() throws Exception {
        CopilotProfile copilotProfile = new CopilotProfile();
        GenericDAO<IdentifiableEntity> genericDAO = EasyMock.createMock(GenericDAO.class);
        instance.setGenericDAO(genericDAO);
        EasyMock.expect(genericDAO.create(copilotProfile)).andReturn(1l);
        EasyMock.replay(genericDAO);
        assertEquals("the return value should be 1", 1, instance.create(copilotProfile));
        EasyMock.verify(genericDAO);
    }

    /**
     * Failure test for {@link GenericServiceImpl#create}.Expected CopilotServiceException.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void testCreateFailure() throws Exception {
        CopilotProfile copilotProfile = new CopilotProfile();
        GenericDAO<IdentifiableEntity> genericDAO = EasyMock.createMock(GenericDAO.class);
        instance.setGenericDAO(genericDAO);
        EasyMock.expect(genericDAO.create(copilotProfile)).andThrow(new CopilotDAOException("error"));
        EasyMock.replay(genericDAO);
        instance.create(copilotProfile);
        EasyMock.verify(genericDAO);
    }

    /**
     * Accuracy test for {@link GenericServiceImpl#update}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testUpdate() throws Exception {
        CopilotProfile copilotProfile = new CopilotProfile();
        copilotProfile.setId(1l);
        GenericDAO<IdentifiableEntity> genericDAO = EasyMock.createMock(GenericDAO.class);
        instance.setGenericDAO(genericDAO);
        genericDAO.update(copilotProfile);
        EasyMock.replay(genericDAO);
        instance.update(copilotProfile);
        EasyMock.verify(genericDAO);
    }

    /**
     * Failure test for {@link GenericServiceImpl#update}.Expected CopilotServiceException.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void testUpdateFailure1() throws Exception {
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
     * Failure test for {@link GenericServiceImpl#update}.Expected CopilotServiceException.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void testUpdateFailure2() throws Exception {
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
     * Accuracy test for {@link GenericServiceImpl#delete}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testDelete() throws Exception {
        long entityId = 1l;
        GenericDAO<IdentifiableEntity> genericDAO = EasyMock.createMock(GenericDAO.class);
        instance.setGenericDAO(genericDAO);
        genericDAO.delete(entityId);
        EasyMock.replay(genericDAO);
        instance.delete(entityId);
        EasyMock.verify(genericDAO);
    }

    /**
     * Failure test for {@link GenericServiceImpl#delete}.Expected CopilotServiceException.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void testDeleteFailure1() throws Exception {
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
     * Failure test for {@link GenericServiceImpl#delete}.Expected CopilotServiceException.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void testDeleteFailure2() throws Exception {
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
     * Accuracy test for {@link GenericServiceImpl#retrieve}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testRetrieve() throws Exception {
        CopilotProfile copilotProfile = new CopilotProfile();
        long entityId = 1l;
        GenericDAO<IdentifiableEntity> genericDAO = EasyMock.createMock(GenericDAO.class);
        instance.setGenericDAO(genericDAO);
        EasyMock.expect(genericDAO.retrieve(entityId)).andReturn(copilotProfile);
        EasyMock.replay(genericDAO);
        assertTrue("should be same with the copilotProfile.", copilotProfile == instance.retrieve(entityId));
        EasyMock.verify(genericDAO);
    }

    /**
     * Failure test for {@link GenericServiceImpl#retrieve}.Expected CopilotServiceException.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void testRetrieveFailure() throws Exception {
        long entityId = 1l;
        GenericDAO<IdentifiableEntity> genericDAO = EasyMock.createMock(GenericDAO.class);
        instance.setGenericDAO(genericDAO);
        EasyMock.expect(genericDAO.retrieve(entityId)).andThrow(new CopilotDAOException("error"));
        EasyMock.replay(genericDAO);
        instance.retrieve(entityId);
        EasyMock.verify(genericDAO);
    }

    /**
     * Accuracy test for {@link GenericServiceImpl#retrieveAll}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testRetrieveAll() throws Exception {
        List<CopilotProfile> copilotProfiles = new ArrayList<CopilotProfile>();
        GenericDAO<IdentifiableEntity> genericDAO = EasyMock.createMock(GenericDAO.class);
        instance.setGenericDAO(genericDAO);
        EasyMock.expect(genericDAO.retrieveAll()).andReturn((List) copilotProfiles);
        EasyMock.replay(genericDAO);
        assertTrue("should be same with the copilotProfiles.", copilotProfiles == (List) instance.retrieveAll());
        EasyMock.verify(genericDAO);
    }

    /**
     * Failure test for {@link GenericServiceImpl#retrieveAll}.Expected CopilotServiceException.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = CopilotServiceException.class)
    public void testRetrieveAllFailure() throws Exception {
        GenericDAO<IdentifiableEntity> genericDAO = EasyMock.createMock(GenericDAO.class);
        instance.setGenericDAO(genericDAO);
        EasyMock.expect(genericDAO.retrieveAll()).andThrow(new CopilotDAOException("error"));
        EasyMock.replay(genericDAO);
        instance.retrieveAll();
        EasyMock.verify(genericDAO);
    }

    /**
     * Accuracy test for {@link GenericServiceImpl#checkInit}.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testCheckInit() throws Exception {
        instance.setGenericDAO(EasyMock.createMock(GenericDAO.class));
        instance.setLoggerName("loggerName");
        try {
            instance.checkInit();
        } catch (Exception e) {
            fail("should not stand here.");
        }
    }

    /**
     * Failure test for {@link GenericServiceImpl#checkInit}.Expected CopilotServiceInitializationException.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = CopilotServiceInitializationException.class)
    public void testCheckInitFailure() throws Exception {
        instance.checkInit();
    }

    /**
     * Accuracy test for {@link GenericServiceImpl#setGenericDAO(GenericDAO)}. The genericDAO should be set
     * correctly.
     */
    @Test
    public void testSetGenericDAO() {
        GenericDAO value = EasyMock.createMock(GenericDAO.class);
        instance.setGenericDAO(value);
        assertTrue("Incorrect value after set to a new one.", value == instance.getGenericDAO());
    }

    /**
     * Accuracy test for {@link GenericServiceImpl#getGenericDAO()}. The default genericDAO should be returned
     * correctly.
     */
    @Test
    public void testGetGenericDAO() {
        assertNull("Incorrect default value", instance.getGenericDAO());
    }

}
