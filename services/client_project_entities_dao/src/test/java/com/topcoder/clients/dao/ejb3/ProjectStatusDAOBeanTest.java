/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.ejb3;

import javax.persistence.EntityManager;

import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.dao.ProjectStatusDAO;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;

/**
 * <p>
 * Test class: <code>ProjectStatusDAOBean</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProjectStatusDAOBeanTest extends TestBase {
    /**
     * An EntityManager instance used in tests.
     */
    private EntityManager entityManager;

    /**
     * <p>
     * An instance of <code>ProjectStatusDAOBean</code> which is tested.
     * </p>
     */
    private ProjectStatusDAOBean target = null;

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

        target = new ProjectStatusDAOBean();
        target.setEntityManager(entityManager);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>ProjectStatusDAOBean</code> subclasses
     * <code>GenericEJB3DAO</code>.
     * </p>
     */
    public void testInheritance1() {
        assertTrue("ProjectStatusDAOBean does not subclasses GenericEJB3DAO.",
                target instanceof GenericEJB3DAO<?, ?>);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>ProjectStatusDAOBean</code> implements
     * <code>ProjectStatusDAO</code>.
     * </p>
     */
    public void testInheritance2() {
        assertTrue(
                "ProjectStatusDAOBean does not implements ProjectStatusDAO.",
                target instanceof ProjectStatusDAO);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>ProjectStatusDAOBean</code> implements
     * <code>ProjectStatusDAOLocal</code>.
     * </p>
     */
    public void testInheritance3() {
        assertTrue(
                "ProjectStatusDAOBean does not implements ProjectStatusDAOLocal.",
                target instanceof ProjectStatusDAOLocal);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>ProjectStatusDAOBean</code> implements
     * <code>ProjectStatusDAORemote</code>.
     * </p>
     */
    public void testInheritance4() {
        assertTrue(
                "ProjectStatusDAOBean does not implements ProjectStatusDAORemote.",
                target instanceof ProjectStatusDAORemote);
    }

    /**
     * <p>
     * Tests the
     * <code>com.topcoder.clients.dao.ejb3.ProjectStatusDAOBean()</code> for
     * proper behavior.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void testConstructor() throws Exception {
        assertNotNull("ProjectStatusDAOBean() failed.", target);
    }

    /**
     * <p>
     * Tests the <code>getProjectsWithStatus(ProjectStatus)</code> for proper
     * behavior.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_getProjectsWithStatus_1() throws Exception {
        Client client = createClient(100);
        createProjectWithClient(1000, client);
        Project project = createProjectWithClient(1001, client);
        target.getProjectsWithStatus(project.getProjectStatus());
    }

    /**
     * <p>
     * Tests the <code>getProjectsWithStatus(ProjectStatus)</code> for proper
     * behavior. IllegalArgumentException if status is null.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_getProjectsWithStatus_failure_1() throws Exception {
        try {
            target.getProjectsWithStatus(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>getProjectsWithStatus(ProjectStatus)</code> for proper
     * behavior. EntityNotFoundException if status is not found in the
     * persistence.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_getProjectsWithStatus_failure_2() throws Exception {
        try {
            ProjectStatus projectStatus = new ProjectStatus();
            projectStatus.setId(10L);
            target.getProjectsWithStatus(projectStatus);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>getProjectsWithStatus(ProjectStatus)</code> for proper
     * behavior. DAOConfigurationException if the configured entityManager is
     * invalid (invalid means null here).
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_getProjectsWithStatus_failure_3() throws Exception {
        try {
            Client client = createClient(100);
            createProjectWithClient(1000, client);
            Project project = createProjectWithClient(1001, client);
            target = new ProjectStatusDAOBean();
            target.getProjectsWithStatus(project.getProjectStatus());
            fail("DAOConfigurationException expected.");
        } catch (DAOConfigurationException e) {
            // ok
        }
    }
}
