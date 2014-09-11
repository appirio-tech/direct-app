/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.dao.ejb3.ProjectStatusDAOBean;
import com.topcoder.clients.model.ProjectStatus;

/**
 * Failure test for ProjectStatusDAOBean class.
 *
 * @author AK_47
 * @version 1.0
 */
public class ProjectStatusDAOBeanFailureTest extends TestCase {
    /**
     * <p>
     * An instance of <code>ProjectStatusDAOBean</code> which is tested.
     * </p>
     */
    private ProjectStatusDAOBean bean = null;

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

        bean = new ProjectStatusDAOBean();
        bean.setEntityManager(TestHelper.getEntityManager());
    }

    /**
     * Failure test of <code>getProjectsWithStatus(ClientStatus)</code> method.
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
    public void testGetProjectsWithStatus_Null_ClientStatus() throws Exception {
        try {
            bean.getProjectsWithStatus(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getProjectsWithStatus(ClientStatus)</code> method.
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
    public void testGetProjectsWithStatus_Null_EntityManager() throws Exception {
        try {
            bean.setEntityManager(null);
            bean.getProjectsWithStatus(new ProjectStatus());

            fail("Expect DAOConfigurationException.");
        } catch (DAOConfigurationException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the <code>getProjectsWithStatus(ClientStatus)</code> for proper
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
    public void testGetProjectsWithStatus_Invalid_Client() throws Exception {
        try {
            ProjectStatus projectStatus = new ProjectStatus();
            // Note: this ProjectStatus doesn't exist in the db
            projectStatus.setId(10L);
            bean.getProjectsWithStatus(projectStatus);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException e) {
            // expect
        }
    }
}
