/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.dao.ejb3.CompanyDAOBean;
import com.topcoder.clients.model.Company;

/**
 * Failure test for CompanyDAOBean class.
 *
 * @author AK_47
 * @version 1.0
 */
public class CompanyDAOBeanFailureTest extends TestCase {
    /**
     * <p>
     * An instance of <code>CompanyDAOBean</code> which is tested.
     * </p>
     */
    private CompanyDAOBean bean = null;

    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(CompanyDAOBeanFailureTest.class);
    }

    /**
     * <p>
     * setUp() routine.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        bean = new CompanyDAOBean();
        bean.setEntityManager(TestHelper.getEntityManager());
    }

    /**
     * Failure test of <code>getClientsForCompany(Company)</code> method.
     *
     * <p>
     * Company is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetClientsForCompany_Null_Company() throws Exception {
        try {
            bean.getClientsForCompany(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getProjectsForCompany(Company)</code> method.
     *
     * <p>
     * Company is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetProjectsForCompany_Null_Company() throws Exception {
        try {
            bean.getProjectsForCompany(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of <code>getClientsForCompany(Company)</code> method.
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
    public void testGetClientsForCompany_Null_EntityManager() throws Exception {
        try {
            bean = new CompanyDAOBean();

            bean.getClientsForCompany(new Company());

            fail("Expect DAOConfigurationException.");
        } catch (DAOConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getProjectsForCompany(Company)</code> method.
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
    public void testGetProjectsForCompany_Null_EntityManager() throws Exception {
        try {
            bean = new CompanyDAOBean();

            bean.getProjectsForCompany(new Company());

            fail("Expect DAOConfigurationException.");
        } catch (DAOConfigurationException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the <code>getClientsForCompany(Company)</code> for proper
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
    public void testGetClientsForCompany_Invalid_Client() throws Exception {
        try {
            Company company = new Company();
            company.setId(100L);
            // Note: this client doesn't exist in db
            bean.getClientsForCompany(company);

            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the <code>getProjectsForCompany(Company)</code> for proper
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
    public void testGetProjectsForCompany_Invalid_Client() throws Exception {
        try {
            Company company = new Company();
            company.setId(100L);
            // Note: this client doesn't exist in db
            bean.getProjectsForCompany(company);

            bean.getProjectsForCompany(company);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException e) {
            // expect
        }
    }
}
