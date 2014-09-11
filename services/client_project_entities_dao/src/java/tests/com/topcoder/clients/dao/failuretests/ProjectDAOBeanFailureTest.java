/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.failuretests;

import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.ejb3.ProjectDAOBean;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Failure test for {@link ProjectDAOBean}.
 * </p>
 *
 * @author Beijing2008
 * @version 1.0
 */
public class ProjectDAOBeanFailureTest extends TestCase {
    /**
     * <p>
     * Represents the ProjectDAOBean instance to test against.
     * </p>
     */
    private ProjectDAOBean instance;
    /**
     * <p>
     * Represents an empty string.
     * </p>
     */
    private String EMPTY = " \n\t";
    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a TestSuite for this test case.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(ProjectDAOBeanFailureTest.class);
        return suite;
    }
    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void setUp() throws Exception {
        instance = new ProjectDAOBean();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        instance = null;
    }
    /**
     * Failure test for method addUserToBillingProjects() with null username.
     * Expects IAE.
     */
    public void test_addUserToBillingProjectsNull() throws Exception {
        try {
            instance.addUserToBillingProjects(null, new long[]{1, 2} );
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
    /**
     * Failure test for method addUserToBillingProjects() with null id list.
     * Expects IAE.
     */
    public void test_addUserToBillingProjectsNullList() throws Exception {
        try {
            instance.addUserToBillingProjects("username", null);
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
    /**
     * Failure test for method addUserToBillingProjects() with empty name.
     * Expects IAE.
     */
    public void test_addUserToBillingProjectsEmptyName() throws Exception {
        try {
            instance.addUserToBillingProjects(EMPTY, new long[]{1, 2} );
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
    /**
     * Failure test for method addUserToBillingProjects() with empty list
     * Expects IAE.
     */
    public void test_addUserToBillingProjectsEmptyList() throws Exception {
        try {
            instance.addUserToBillingProjects("username", new long[]{} );
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
    /**
     * Failure test for method addUserToBillingProjects() with id less than 0.
     * Expects IAE.
     */
    public void test_addUserToBillingProjectsIdLessThan0() throws Exception {
        try {
            instance.addUserToBillingProjects("username", new long[]{3, 2, 0} );
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
    /**
     * Failure test for method addUserToBillingProjects() with id less than 0.
     * Expects IAE.
     */
    public void test_addUserToBillingProjectsIdLessThan02() throws Exception {
        try {
            instance.addUserToBillingProjects("username", new long[]{3, -1, 2} );
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
    /**
     * Failure test for method addUserToBillingProjects() with null entity manager.
     * Expects DAOConfigurationException.
     */
    public void test_addUserToBillingProjectsNullEntityManager() throws Exception {
        try {
            instance.addUserToBillingProjects("username", new long[]{1, 2} );
            fail("Expects DAOConfigurationException");
        } catch (DAOConfigurationException e) {
            // good
        }
    }

    /**
     * Failure test for method removeUserFromBillingProjects() with null username.
     * Expects IAE.
     */
    public void test_removeUserFromBillingProjectsNull() throws Exception {
        try {
            instance.removeUserFromBillingProjects(null, new long[]{1, 2} );
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
    /**
     * Failure test for method removeUserFromBillingProjects() with null id list.
     * Expects IAE.
     */
    public void test_removeUserFromBillingProjectsNullList() throws Exception {
        try {
            instance.removeUserFromBillingProjects("username", null);
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
    /**
     * Failure test for method removeUserFromBillingProjects() with empty name.
     * Expects IAE.
     */
    public void test_removeUserFromBillingProjectsEmptyName() throws Exception {
        try {
            instance.removeUserFromBillingProjects(EMPTY, new long[]{1, 2} );
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
    /**
     * Failure test for method removeUserFromBillingProjects() with empty list
     * Expects IAE.
     */
    public void test_removeUserFromBillingProjectsEmptyList() throws Exception {
        try {
            instance.removeUserFromBillingProjects("username", new long[]{} );
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
    /**
     * Failure test for method removeUserFromBillingProjects() with id less than 0.
     * Expects IAE.
     */
    public void test_removeUserFromBillingProjectsIdLessThan0() throws Exception {
        try {
            instance.removeUserFromBillingProjects("username", new long[]{3, 2, 0} );
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
    /**
     * Failure test for method removeUserFromBillingProjects() with id less than 0.
     * Expects IAE.
     */
    public void test_removeUserFromBillingProjectsIdLessThan02() throws Exception {
        try {
            instance.removeUserFromBillingProjects("username", new long[]{3, -1, 2} );
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
    /**
     * Failure test for method removeUserFromBillingProjects() with null entity manager.
     * Expects DAOConfigurationException.
     */
    public void test_removeUserFromBillingProjectsNullEntityManager() throws Exception {
        try {
            instance.removeUserFromBillingProjects("username", new long[]{1, 2} );
            fail("Expects DAOConfigurationException");
        } catch (DAOConfigurationException e) {
            // good
        }
    }
    /**
     * Failure test for method getProjectsByClientId() with id = 0.
     * Expects IAE.
     */
    public void test_getProjectsByClientIdIdEqualsZero() throws Exception {
        try {
            instance.getProjectsByClientId(0);
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
    /**
     * Failure test for method getProjectsByClientId() with id less than 0.
     * Expects IAE.
     */
    public void test_getProjectsByClientIdIdLessThan0() throws Exception {
        try {
            instance.getProjectsByClientId(-1);
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
    /**
     * Failure test for method getProjectsByClientId() with null entity manager.
     * Expects DAOConfigurationException.
     */
    public void test_getProjectsByClientIdNullEntityManager() throws Exception {
        try {
            instance.getProjectsByClientId(1);
            fail("Expects DAOConfigurationException");
        } catch (DAOConfigurationException e) {
            // good
        }
    }
}