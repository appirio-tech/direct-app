/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.stresstests;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import junit.framework.TestCase;

import org.hibernate.ejb.Ejb3Configuration;

import com.topcoder.clients.dao.ejb3.ProjectDAOBean;


/**
 * <p>
 * Stress Tests for ProjectDAOBean.
 * </p>
 *
 * @author onsky
 * @version 1.0
 */
public class ProjectDAOBeanStressTests extends TestCase {

    /**
     * <p>
     * This constant represents the test count used for testing.
     * </p>
     */
    private static final long TOTAL_RUNS = 100;

    /**
     * <p>
     * This constant represents the current time used for testing.
     * </p>
     */
    private long current = -1;

    /**
     * <p>
     * This constant represents the ProjectDAOBean instance to test.
     * </p>
     */
    private ProjectDAOBean instance;

	/**
	 * An EntityManager instance used in tests.
	 */
	private static EntityManager entityManager;

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

        instance = new ProjectDAOBean();
        instance.setEntityManager(getEntityManager());
    }

    /**
     * <p>The stress test for addUserToBillingProjects() method.</p>
     * @throws Exception to JUnit
     */
    public void testAddUserToBillingProjects() throws Exception {
        start();

        for (int i = 0; i < TOTAL_RUNS; i++) {
        	try {
        		instance.addUserToBillingProjects("ivern", new long[]{1, 2, 3});
        	} catch (Exception e) {
        	}
        }

        this.printResult("ProjectDAOBean#addUserToBillingProjects", TOTAL_RUNS);
    }

    /**
     * <p>The stress test for removeUserFromBillingProjects() method.</p>
     * @throws Exception to JUnit
     */
    public void testRemoveUserFromBillingProjects() throws Exception {
        start();

        for (int i = 0; i < TOTAL_RUNS; i++) {
        	try {
        		instance.removeUserFromBillingProjects("ivern", new long[]{1, 2, 3});
        	} catch (Exception e) {
        	}
        }

        this.printResult("ProjectDAOBean#removeUserFromBillingProjects", TOTAL_RUNS);
    }

    /**
     * <p>The stress test for getProjectsByClientId() method.</p>
     * @throws Exception to JUnit
     */
    public void testGetProjectsByClientId() throws Exception {
        start();

        for (int i = 0; i < TOTAL_RUNS; i++) {
        	try {
        		instance.getProjectsByClientId(10l);
        	} catch (Exception e) {
        	}
        }

        this.printResult("ProjectDAOBean#getProjectsByClientId", TOTAL_RUNS);
    }

    /**
     * <p>
     * Starts to count time.
     * </p>
     */
    private void start() {
        current = System.currentTimeMillis();
    }

    /**
     * <p>
     * Prints test result.
     * </p>
     *
     * @param name the test name
     * @param count the run count
     */
    private void printResult(String name, long count) {
        System.out.println("The test [" + name + "] run " + count + " times, took time: "
            + (System.currentTimeMillis() - current) + " ms");
    }

	/**
	 * Get EntityManager.
	 * 
	 * @return EntityManager
	 */
	private EntityManager getEntityManager() {
		if (entityManager == null || !entityManager.isOpen()) {
			// create entityManager
			try {
				Ejb3Configuration cfg = new Ejb3Configuration();
				cfg.configure("hibernate.cfg.xml");

				EntityManagerFactory emf = cfg.buildEntityManagerFactory();
				entityManager = emf.createEntityManager();

			} catch (Exception e) {
			}
		}

		entityManager.clear();
		if (!entityManager.getTransaction().isActive()) {
			entityManager.getTransaction().begin();
		}
		return entityManager;
	}
}
