/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.ejb3;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.topcoder.clients.dao.ClientStatusDAO;
import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;

/**
 * <p>
 * Test class: <code>ClientStatusDAOBean</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ClientStatusDAOBeanTest extends TestBase {
    /**
     * An EntityManager instance used in tests.
     */
    private EntityManager entityManager;

    /**
     * <p>
     * An instance of <code>ClientStatusDAOBean</code> which is tested.
     * </p>
     */
    private ClientStatusDAOBean target = null;

    /**
     * <p>
     * setUp() routine.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        entityManager = getEntityManager();

        target = new ClientStatusDAOBean();
        target.setEntityManager(entityManager);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>ClientStatusDAOBean</code> subclasses
     * <code>GenericEJB3DAO</code>.
     * </p>
     */
    public void testInheritance1() {
        assertTrue("ClientStatusDAOBean does not subclasses GenericEJB3DAO.",
                target instanceof GenericEJB3DAO<?, ?>);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>ClientStatusDAOBean</code> implements
     * <code>ClientStatusDAO</code>.
     * </p>
     */
    public void testInheritance2() {
        assertTrue("ClientStatusDAOBean does not implements ClientStatusDAO.",
                target instanceof ClientStatusDAO);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>ClientStatusDAOBean</code> implements
     * <code>ClientStatusDAOLocal</code>.
     * </p>
     */
    public void testInheritance3() {
        assertTrue(
                "ClientStatusDAOBean does not implements ClientStatusDAOLocal.",
                target instanceof ClientStatusDAOLocal);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>ClientStatusDAOBean</code> implements
     * <code>ClientStatusDAORemote</code>.
     * </p>
     */
    public void testInheritance4() {
        assertTrue(
                "ClientStatusDAOBean does not implements ClientStatusDAORemote.",
                target instanceof ClientStatusDAORemote);
    }

    /**
     * <p>
     * Tests the
     * <code>com.topcoder.clients.dao.ejb3.ClientStatusDAOBean()</code> for
     * proper behavior.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void testConstructor() throws Exception {
        assertNotNull("ClientStatusDAOBean() failed.", target);
    }

    /**
     * <p>
     * Tests the <code>getClientsWithStatus(ClientStatus)<</code> for proper
     * behavior.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void testMethod_getClientsWithStatus_1() throws Exception {
        createClient(100);
        Client client = createClient(101);
        List<Client> res = target.getClientsWithStatus(client.getClientStatus());
        assertEquals("The number of clients", 2, res.size());
        // verify data
        List<Long> ids = new ArrayList<Long>();
        ids.add(res.get(0).getId());
        ids.add(res.get(1).getId());
        assertTrue("should be returned with correct id", ids.contains(100L));
        assertTrue("should be returned with correct id", ids.contains(101L));
    }

    /**
     * <p>
     * Tests the <code>getClientsWithStatus(ClientStatus)<</code> for proper
     * behavior. IllegalArgumentException if status is null.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void testMethod_getClientsWithStatus_failure_1() throws Exception {
        try {
            target.getClientsWithStatus(null);
            fail("IllegalArgumentException if status is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>getClientsWithStatus(ClientStatus)</code> for proper
     * behavior. EntityNotFoundException if status is not found in the
     * persistence.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void testMethod_getClientsWithStatus_failure_2() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setId(1L);
            target.getClientsWithStatus(status);
            fail("EntityNotFoundException if status is not found.");
        } catch (EntityNotFoundException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>getClientsWithStatus(ClientStatus)</code> for proper
     * behavior. DAOConfigurationException if the configured entityManager is
     * invalid (invalid means null here).
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void testMethod_getClientsWithStatus_failure_3() throws Exception {
        try {
            Client client = createClient(101);
            target = new ClientStatusDAOBean();
            target.getClientsWithStatus(client.getClientStatus());
            fail("DAOConfigurationException expected.");
        } catch (DAOConfigurationException e) {
            // ok
        }
    }
}
