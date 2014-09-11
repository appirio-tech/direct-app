/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.dao.ejb3.ClientStatusDAOBean;
import com.topcoder.clients.model.ClientStatus;

/**
 * Failure test for ClientStatusDAOBean class.
 *
 * @author AK_47
 * @version 1.0
 */
public class ClientStatusDAOBeanFailureTest extends TestCase {
    /**
     * <p>
     * An instance of <code>ClientStatusDAOBean</code> which is tested.
     * </p>
     */
    private ClientStatusDAOBean bean = null;

    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ClientStatusDAOBeanFailureTest.class);
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

        bean = new ClientStatusDAOBean();
        bean.setEntityManager(TestHelper.getEntityManager());
    }

    /**
     * Failure test of <code>getClientsWithStatus(ClientStatus)</code> method.
     *
     * <p>
     * ClientStatus is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetClientsWithStatus_Null_ClientStatus() throws Exception {
        try {
            bean.getClientsWithStatus(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getClientsWithStatus(ClientStatus)</code> method.
     *
     * <p>
     * entityManager is null.
     * </p>
     *
     * <p>
     * Expect DAOConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetClientsWithStatus_Null_EntityManager() throws Exception {
        try {
            bean = new ClientStatusDAOBean();

            bean.getClientsWithStatus(new ClientStatus());

            fail("Expect DAOConfigurationException.");
        } catch (DAOConfigurationException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the <code>getClientsWithStatus(ClientStatus)</code> for proper
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
    public void testGetClientsWithStatus_Invalid_Client() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            // Note: this status doesn't exist in the db
            status.setId(1L);
            bean.getClientsWithStatus(status);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException e) {
            // expect
        }
    }
}
