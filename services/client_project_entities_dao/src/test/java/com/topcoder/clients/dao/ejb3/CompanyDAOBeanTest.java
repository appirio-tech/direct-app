/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.ejb3;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.topcoder.clients.dao.CompanyDAO;
import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;

/**
 * <p>
 * Test class: <code>CompanyDAOBean</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CompanyDAOBeanTest extends TestBase {
    /**
     * An EntityManager instance used in tests.
     */
    private EntityManager entityManager;

    /**
     * <p>
     * An instance of <code>CompanyDAOBean</code> which is tested.
     * </p>
     */
    private CompanyDAOBean target = null;

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

        target = new CompanyDAOBean();
        target.setEntityManager(entityManager);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>CompanyDAOBean</code> subclasses
     * <code>GenericEJB3DAO</code>.
     * </p>
     */
    public void testInheritance1() {
        assertTrue("CompanyDAOBean does not subclasses GenericEJB3DAO.",
                target instanceof GenericEJB3DAO<?, ?>);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>CompanyDAOBean</code> implements <code>CompanyDAO</code>.
     * </p>
     */
    public void testInheritance2() {
        assertTrue("CompanyDAOBean does not implements CompanyDAO.",
                target instanceof CompanyDAO);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>CompanyDAOBean</code> implements
     * <code>CompanyDAOLocal</code>.
     * </p>
     */
    public void testInheritance3() {
        assertTrue("CompanyDAOBean does not implements CompanyDAOLocal.",
                target instanceof CompanyDAOLocal);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>CompanyDAOBean</code> implements
     * <code>CompanyDAORemote</code>.
     * </p>
     */
    public void testInheritance4() {
        assertTrue("CompanyDAOBean does not implements CompanyDAORemote.",
                target instanceof CompanyDAORemote);
    }

    /**
     * <p>
     * Tests the <code>com.topcoder.clients.dao.ejb3.CompanyDAOBean()</code>
     * for proper behavior.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void testConstructor() throws Exception {
        assertNotNull("CompanyDAOBean() failed.", target);
    }

    /**
     * <p>
     * Tests the <code>getClientsForCompany(Company)</code> for proper
     * behavior.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_getClientsForCompany_1() throws Exception {
        createClient(100);
        Client client = createClient(101);

        List<Client> res = target.getClientsForCompany(client.getCompany());
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
     * Tests the <code>getClientsForCompany(Company)</code> for proper
     * behavior. IllegalArgumentException if company is null.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_getClientsForCompany_failure_1() throws Exception {
        try {
            target.getClientsForCompany(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>getClientsForCompany(Company)</code> for proper
     * behavior. EntityNotFoundException if company is not found in the
     * persistence.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_getClientsForCompany_failure_2() throws Exception {
        try {
            Company company = new Company();
            company.setId(100L);
            target.getClientsForCompany(company);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>getClientsForCompany(Company)</code> for proper
     * behavior. DAOConfigurationException if the configured entityManager is
     * invalid (invalid means null here).
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_getClientsForCompany_failure_3() throws Exception {
        try {
            Client client = createClient(101);
            target = new CompanyDAOBean();
            target.getClientsForCompany(client.getCompany());
            fail("DAOConfigurationException expected.");
        } catch (DAOConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>getProjectsForCompany(Company)</code> for proper
     * behavior.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_getProjectsForCompany_1() throws Exception {
        Client client = createClient(101);
        createProjectWithClient(10, client);
        createProjectWithClient(11, client);
        createProjectWithClient(12, client);

        List<Project> res = target.getProjectsForCompany(client.getCompany());
        assertEquals("The number of Project", 3, res.size());
        // verify data
        List<Long> ids = new ArrayList<Long>();
        ids.add(res.get(0).getId());
        ids.add(res.get(1).getId());
        ids.add(res.get(2).getId());
        assertTrue("should be returned with correct id", ids.contains(10L));
        assertTrue("should be returned with correct id", ids.contains(11L));
        assertTrue("should be returned with correct id", ids.contains(11L));
    }

    /**
     * <p>
     * Tests the <code>getProjectsForCompany(Company)</code> for proper
     * behavior. IllegalArgumentException if company is null.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_getProjectsForCompany_failure_1() throws Exception {
        try {
            target.getProjectsForCompany(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>getProjectsForCompany(Company)</code> for proper
     * behavior. EntityNotFoundException if company is not found in the
     * persistence.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_getProjectsForCompany_failure_2() throws Exception {
        try {
            Company company = new Company();
            company.setId(100L);
            target.getProjectsForCompany(company);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>getProjectsForCompany(Company)</code> for proper
     * behavior. DAOConfigurationException if the configured entityManager is
     * invalid (invalid means null here).
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_getProjectsForCompany_failure_3() throws Exception {
        try {
            Client client = createClient(101);
            createProjectWithClient(10, client);
            createProjectWithClient(11, client);
            createProjectWithClient(12, client);
            target = new CompanyDAOBean();
            target.getProjectsForCompany(client.getCompany());
            fail("DAOConfigurationException expected.");
        } catch (DAOConfigurationException e) {
            // ok
        }
    }
}
