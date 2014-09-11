/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.ejb3;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.topcoder.clients.dao.ClientDAO;
import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;

/**
 * <p>
 * Test class: <code>ClientDAOBean</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ClientDAOBeanTest extends TestBase {
    /**
     * An EntityManager instance used in tests.
     */
    private EntityManager entityManager;

    /**
     * <p>
     * An instance of <code>ClientDAOBean</code> which is tested.
     * </p>
     */
    private ClientDAOBean target = null;

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

        target = new ClientDAOBean();
        target.setEntityManager(entityManager);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>ClientDAOBean</code> subclasses
     * <code>GenericEJB3DAO</code>.
     * </p>
     */
    public void testInheritance1() {
        assertTrue("ClientDAOBean does not subclasses GenericEJB3DAO.",
                target instanceof GenericEJB3DAO<?, ?>);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>ClientDAOBean</code> implements <code>ClientDAO</code>.
     * </p>
     */
    public void testInheritance2() {
        assertTrue("ClientDAOBean does not implements ClientDAO.",
                target instanceof ClientDAO);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>ClientDAOBean</code> implements
     * <code>ClientDAOLocal</code>.
     * </p>
     */
    public void testInheritance3() {
        assertTrue("ClientDAOBean does not implements ClientDAOLocal.",
                target instanceof ClientDAOLocal);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>ClientDAOBean</code> implements
     * <code>ClientDAORemote</code>.
     * </p>
     */
    public void testInheritance4() {
        assertTrue("ClientDAOBean does not implements ClientDAORemote.",
                target instanceof ClientDAORemote);
    }

    /**
     * <p>
     * Tests the <code>com.topcoder.clients.dao.ejb3.ClientDAOBean()</code>
     * for proper behavior.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void testConstructor() throws Exception {
        assertNotNull("ClientDAOBean() failed.", target);
    }

    /**
     * <p>
     * Tests the <code>getProjectsForClient(Client)</code> for proper
     * behavior.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void testMethodGetProjectsForClient_Client1() throws Exception {
        Client client = createClient(1000);
        createProjectWithClient(10000, client);
        createProjectWithClient(10001, client);
        List<Project> projects = target.getProjectsForClient(client);
        assertEquals("The number of projects is not right.", 2, projects.size());
        // verify data
        List<Long> ids = new ArrayList<Long>();
        ids.add(projects.get(0).getId());
        ids.add(projects.get(1).getId());
        assertTrue("Project should be returned with correct id", ids.contains(10000L));
        assertTrue("Project should be returned with correct id", ids.contains(10001L));
    }

    /**
     * <p>
     * Tests the <code>getProjectsForClient(Client)</code> for proper
     * behavior.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void testMethodGetProjectsForClient_Client2() throws Exception {
        Client client = createClient(1000);
        List<Project> projects = target.getProjectsForClient(client);
        assertEquals("The number of projects is not right.", 0, projects.size());
    }

    /**
     * <p>
     * Tests the <code>getProjectsForClient(Client)</code> for proper
     * behavior. IllegalArgumentException if client is null.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void testMethodGetProjectsForClient_Client_failure1()
        throws Exception {
        try {
            target.getProjectsForClient(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>getProjectsForClient(Client)</code> for proper
     * behavior. EntityNotFoundException if client is not found in the
     * persistence.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void testMethodGetProjectsForClient_Client_failure2()
        throws Exception {
        try {
            Client client = new Client();
            client.setId(12L);
            target.getProjectsForClient(client);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>getProjectsForClient(Client)</code> for proper
     * behavior. DAOConfigurationException if the configured entityManager is
     * invalid (invalid means null here).
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void testMethodGetProjectsForClient_Client_failure3()
        throws Exception {
        try {
            target = new ClientDAOBean();
            Client client = createClient(1000);
            target.getProjectsForClient(client);
            fail("DAOConfigurationException expected.");
        } catch (DAOConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>getProjectsForClient(Client)</code> for proper
     * behavior. DAOException if any error occurs while performing this
     * operation.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void testMethodGetProjectsForClient_Client_failure4()
        throws Exception {
        try {
            Client client = createClient(1000);
            client.setId(102L);
            target.getProjectsForClient(client);
            fail("DAOException expected.");
        } catch (DAOException e) {
            // ok
        }
    }
}
