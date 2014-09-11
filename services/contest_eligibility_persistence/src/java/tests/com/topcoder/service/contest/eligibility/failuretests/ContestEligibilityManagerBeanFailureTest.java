/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibility.failuretests;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.service.contest.eligibility.ContestEligibility;
import com.topcoder.service.contest.eligibility.GroupContestEligibility;
import com.topcoder.service.contest.eligibility.dao.ContestEligibilityManagerBean;

/**
 * <p>
 * Unit tests for <code>ContestEligibilityManagerBean</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestEligibilityManagerBeanFailureTest {
    /**
     * <p>
     * Represents the <code>EntityManager</code> instance.
     * </p>
     */
    private static EntityManager entityManager = Persistence.createEntityManagerFactory(
            "ContestEligibilityPersistence").createEntityManager();
    /**
     * <p>
     * Represents the <code>EntityManager</code> instance.
     * </p>
     */
    private static MockEntityManager mockEntityManager;
    /**
     * <p>
     * Represents the <code>ContestEligibilityManager</code> instance used to test against.
     * </p>
     */
    private ContestEligibilityManagerBean contestEligibilityManager;

    /**
     * <p>
     * Sets up the test environment. This method is called before a test is executed.
     * </p>
     *
     * @throws Exception if any error occurs.
     */
    @Before
    public void setUp() throws Exception {
        contestEligibilityManager = new ContestEligibilityManagerBean();
        TestHelper.inject(ContestEligibilityManagerBean.class, "entityManager",
                contestEligibilityManager, entityManager);
        TestHelper.inject(ContestEligibilityManagerBean.class, "logger",
                contestEligibilityManager, com.topcoder.util.log.LogManager.getLog());
        mockEntityManager = new MockEntityManager(entityManager);
        mockEntityManager.enablePersistenceException(false);
    }

    /**
     * <p>
     * Tears down the environment. This method is called after a test is executed.
     * </p>
     *
     * @throws Exception if any error occurs.
     */
    @After
    public void tearDown() throws Exception {
        contestEligibilityManager = null;
        rollbackTransaction();
        TestHelper.clearDB(entityManager);
    }
    /**
     * <p>
     * Begin the transaction.
     * </p>
     */
    public static void beginTransaction() {

    }

    /**
     * <p>
     * Commit the transaction.
     * </p>
     */
    public static void commitTransaction() {
    }

    /**
     * <p>
     * Rollback the transaction.
     * </p>
     */
    public static void rollbackTransaction() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
    }
    /**
     * <p>
     * Failure test for the method <code>create(ContestEligibility cg)</code> against null input.
     * </p>
     * <p>
     * Expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateNull() throws Exception {
        contestEligibilityManager.create(null);
    }
    /**
     * <p>
     * Failure test for the method <code>remove(ContestEligibility cg)</code> against null input.
     * </p>
     * <p>
     * Expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveNull() throws Exception {
        contestEligibilityManager.remove(null);
    }
    /**
     * <p>
     * Failure test for the method <code>remove(ContestEligibility cg)</code> against
     * entity which does not exist.
     * </p>
     * <p>
     * Expects ContestEligibilityPersistenceException.
     * </p>
     * @throws Exception to JUnit
     */
    @Test
    public void testRemoveNotExist() throws Exception {
        beginTransaction();
        ContestEligibility contestEligibility = createContestEligibility();
        contestEligibility.setId(1);
        contestEligibilityManager.remove(contestEligibility);
        commitTransaction();
    }
    /**
     * <p>
     * Failure test for the method <code>save(List list)</code> against null list.
     * </p>
     * <p>
     * An list with one element should return.
     * </p>
     * @throws Exception to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSaveNull() throws Exception {
        contestEligibilityManager.save(null);
    }

    /**
     * <p>
     * Failure test for the method <code>save(List list)</code> against empty list.
     * </p>
     * <p>
     * An list with one element should return.
     * </p>
     * @throws Exception to JUnit
     */
    @Test
    public void testSaveEmpty() throws Exception {
        List<ContestEligibility> list = new ArrayList<ContestEligibility>();
        contestEligibilityManager.save(list);
    }
    /**
     * <p>
     * Failure test for the method <code>save(List list)</code> against list containing null.
     * </p>
     * <p>
     * An list with one element should return.
     * </p>
     * @throws Exception to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSaveContaingingNull() throws Exception {
        List<ContestEligibility> list = new ArrayList<ContestEligibility>();
        list.add(null);
        contestEligibilityManager.save(list);
    }
    /**
     * <p>
     * Failure test for the method <code>save(List list)</code> against
     * deleting an entity which does not exist.
     * </p>
     * <p>
     * Expects ContestEligibilityPersistenceException.
     * </p>
     * @throws Exception to JUnit
     */
    @Test
    public void testSaveDeletingNotExist() throws Exception {
        beginTransaction();
        ContestEligibility contestEligibility = createContestEligibility();
        contestEligibility.setDeleted(true);
        List<ContestEligibility> list = new ArrayList<ContestEligibility>();
        list.add(contestEligibility);
        contestEligibilityManager.save(list);
        commitTransaction();
    }
    /**
     * Create an GroupContestEligibility for test.
     *
     * @return the created entity.
     */
    private ContestEligibility createContestEligibility() {
        GroupContestEligibility group = new GroupContestEligibility();
        group.setStudio(false);
        group.setDeleted(false);
        group.setContestId(1);
        group.setGroupId(1);
        return group;
    }
}

