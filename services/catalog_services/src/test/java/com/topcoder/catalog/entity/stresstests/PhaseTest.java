/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.stresstests;

import com.topcoder.catalog.entity.Phase;
import javax.persistence.EntityManager;

import junit.framework.TestCase;

/**
 * <p>
 * Stress tests for Component class.
 * </p>
 * 
 * @author Retunsky
 * @version 1.0
 */
public class PhaseTest extends TestCase {

    /**
     * This instance is used in the test.
     */

    private EntityManager entityManager;

    /**
     * This instance is used in the test.
     */
    private Phase phase;

    /**
     * <p>
     * Persists entity instance to the database before each test case.
     * </p>
     * 
     * @throws Exception
     *             to jUnit
     */
    protected void setUp() throws Exception {
        entityManager = StressTestHelper.getEntityManager();
        StressTestHelper.runSQL("test_files/stresstests/clear.sql");
        StressTestHelper.runSQL("test_files/stresstests/insert.sql");
        phase = new Phase();
        // catalogName is read only
        phase.setDescription("test");
    }

    /**
     * Test CRUD operation of Phase entity.
     */
    public void testPhaseCRUD() {
        // save it
        long[] ids = new long[10];
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            entityManager.getTransaction().begin();
            entityManager.persist(phase);
            entityManager.getTransaction().commit();
            ids[i] = phase.getId();
        }
        // check that it is saved
        for (int i = 0; i < 0; i++) {
            Phase savedPhase = entityManager.find(Phase.class, ids[i]);
            assertNotNull("Phase is not saved", phase);
            savedPhase.setDescription("new");
            entityManager.refresh(savedPhase);
            savedPhase = entityManager.find(Phase.class, ids[i]);
            assertEquals("name is not changed.", "new", savedPhase.getDescription());
            entityManager.remove(savedPhase);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("create/retrive/update/remove 10 phase entities takes " + (endTime - startTime) + "ms");
    }
}
