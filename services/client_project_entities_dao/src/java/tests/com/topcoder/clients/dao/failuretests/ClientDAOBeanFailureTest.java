/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.dao.ejb3.ClientDAOBean;
import com.topcoder.clients.model.Client;

/**
 * Failure test for ClientDAOBean class.
 *
 * @author AK_47
 * @version 1.0
 */
public class ClientDAOBeanFailureTest extends TestCase {
    /**
     * <p>
     * An instance of <code>ClientDAOBean</code> which is tested.
     * </p>
     */
    private ClientDAOBean bean = null;

    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ClientDAOBeanFailureTest.class);
    }


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

        bean = new ClientDAOBean();
        bean.setEntityManager(TestHelper.getEntityManager());
    }

    /**
     * Failure test of <code>getProjectsForClient(Client)</code> method.
     *
     * <p>
     * client is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetProjectsForClient_Null_Client() throws Exception {
        try {
            bean.getProjectsForClient(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getProjectsForClient(Client)</code> method.
     *
     * <p>
     * entityManager is null.
     * </p>
     *
     * <p>
     * Expect DAOConfigurationException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetProjectsForClient_Null_EntityManager() throws Exception {
        try {
            bean = new ClientDAOBean();

            Client client = new Client();
            bean.getProjectsForClient(client);

            fail("Expect DAOConfigurationException.");
        } catch (DAOConfigurationException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the <code>getProjectsForClient(Client)</code> for proper
     * behavior.
     * </p>
     *
     * <p>
     * Expect EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void testGetProjectsForClient_Invalid_Client() throws Exception {
        try {
            Client client = new Client();
            // Note: this client doesn't exist in db
            client.setId(1000L);
            bean.getProjectsForClient(client);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException e) {
            // expect
        }
    }
}
