/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.stresstests;

import javax.persistence.EntityManager;
import javax.naming.InitialContext;
import javax.naming.Context;

import junit.framework.TestCase;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.Status;
import com.topcoder.util.idgenerator.ejb.IDGeneratorBean;
import com.topcoder.util.idgenerator.ejb.IDGeneratorHome;
import com.topcoder.util.idgenerator.ejb.IDGenerator;
import com.topcoder.util.config.ConfigManager;
import org.mockejb.jndi.MockContextFactory;
import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;

import java.io.File;

/**
 * <p>
 * Stress tests for Component class.
 * </p>
 * 
 * @author Retunsky
 * @version 1.0
 */
public class CategoryTest extends TestCase {

    /**
     * This instance is used in the test.
     */

    private EntityManager entityManager;

    /**
     * This instance is used in the test.
     */
    private Category category;

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
        category = new Category();
        // catalogName is read only
        category.setName("test");
        category.setCatalogName("test");
        category.setDescription("test");
        category.setStatus(Status.NEW_POST);
        category.setViewable(true);
    }

    /**
     * Test CRUD operation of Category entity.
     */
    public void testCategoryCRUD() {
        // save it
        long[] ids = new long[10];
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            entityManager.getTransaction().begin();
            entityManager.persist(category);
            entityManager.getTransaction().commit();
            ids[i] = category.getId();
        }
        // check that it is saved
        for (int i = 0; i < 0; i++) {
            Category savedCategory = entityManager.find(Category.class, ids[i]);
            assertNotNull("Phase is not saved", category);
            savedCategory.setName("new");
            entityManager.refresh(savedCategory);
            savedCategory = entityManager.find(Category.class, ids[i]);
            assertEquals("name is not changed.", "new", savedCategory.getName());
            entityManager.remove(savedCategory);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("create/retrive/update/remove 10 category entities takes " + (endTime - startTime)
            + "ms");
    }
}
