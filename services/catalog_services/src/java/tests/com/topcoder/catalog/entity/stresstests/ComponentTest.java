/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.stresstests;

import com.topcoder.catalog.entity.CompVersion;
import com.topcoder.catalog.entity.Component;
import com.topcoder.catalog.entity.Status;
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
public class ComponentTest extends TestCase {

    /**
     * This instance is used in the test.
     */

    private EntityManager entityManager;

    /**
     * This instance is used in the test.
     */
    private Component component;

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
        String stringValue = "test";
        CompVersion compVersion = StressTestHelper.retrieveEntity(CompVersion.class, new Long(2));
        component = new Component();
        component.setCurrentVersion(compVersion);
        component.setShortDesc(stringValue);
        component.setName(stringValue);
        component.setStatus(Status.ACTIVE);
    }

    /**
     * Test CRUD operation of Component entity.
     */
    public void testComponentCRUD() {
        // save it
        long[] ids = new long[10];
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            entityManager.getTransaction().begin();
            entityManager.persist(component);
            entityManager.getTransaction().commit();
            ids[i] = component.getId();
        }
        // check that it is saved
        for (int i = 0; i < 0; i++) {
            Component savedComponent = entityManager.find(Component.class, ids[i]);
            assertNotNull("Phase is not saved", component);
            savedComponent.setName("new");
            entityManager.refresh(savedComponent);
            savedComponent = entityManager.find(Component.class, ids[i]);
            assertEquals("name is not changed.", "new", savedComponent.getName());
            entityManager.remove(savedComponent);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("create/retrive/update/remove 10 component entities takes " + (endTime - startTime)
            + "ms");
    }
}
