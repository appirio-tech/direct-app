/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.accuracytests;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.Status;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;


/**
 * Accuracy test cases for Entity <code>Category </code>.
 *
 * @author Chenhong
 * @version 1.0
 */
public class TestCategoryAccuracy extends BaseTest {
    /**
     * Test if this entity can be created.
     * 
     * <p>
     * In this test case, no parent Category is set.
     * </p>
     *
     * @throws Exception to junit.
     */
    public void testCreate_1() throws Exception {
        EntityManager manager = this.getEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        if (transaction.isActive() == false) {
            transaction.begin();
        }

        Category category = new Category();
        category.setCatalogName("category name");
        category.setName("name");
        category.setStatus(Status.NEW_POST);
        category.setViewable(true);
        category.setDescription("category description");

        manager.persist(category);
        transaction.commit();

        Category ret = manager.find(Category.class, category.getId());

        assertEquals("Equal is expected.", "category name", ret.getCatalogName());
        assertEquals("Equal is expected.", "category description", ret.getDescription());
        assertTrue("True is expected.", ret.isViewable());
        assertNull("Null is expected.", ret.getParentCategory());

        // remove this entity.
        manager.remove(category);

        // verify if the entity is removed.
        ret = manager.find(Category.class, category.getId());
        assertNull("The entity should be removed.", ret);
    }

    /**
     * Test if this entity can be created.
     * 
     * <p>
     * In this test case, parent Category is set.
     * </p>
     *
     * @throws Exception to junit.
     */
    public void testCreate_2() throws Exception {
        EntityManager manager = this.getEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        if (transaction.isActive() == false) {
            transaction.begin();
        }

        Category category = new Category();
        category.setCatalogName("category name");
        category.setName("name");
        category.setStatus(Status.NEW_POST);
        category.setViewable(true);
        category.setDescription("category description");

        manager.persist(category);
        transaction.commit();

        Category ret = manager.find(Category.class, category.getId());

        assertEquals("Equal is expected.", "category name", ret.getCatalogName());
        assertEquals("Equal is expected.", "category description", ret.getDescription());
        assertTrue("True is expected.", ret.isViewable());
        assertNull("Null is expected.", ret.getParentCategory());

        if (transaction.isActive() == false) {
            transaction.begin();
        }

        Category category2 = new Category();
        category2.setCatalogName("2");
        category2.setName("name2");
        category2.setDescription("des");
        category2.setStatus(Status.DECLINED);
        category2.setParentCategory(ret);

        manager.persist(category2);
        transaction.commit();

        Category ret2 = manager.find(Category.class, category2.getId());
        assertEquals("Equal is expected.", "2", ret2.getCatalogName());
        assertEquals("Equal is expected.", "des", ret2.getDescription());

        assertNotNull("The parent category is set.", ret2.getParentCategory());
        assertEquals("Equal is expected.", ret.getId(), ret2.getParentCategory().getId());
    }

    /**
     * Test if the entity can be updated or not.
     *
     * @throws Exception to junit.
     */
    public void testUpdate() throws Exception {
        EntityManager manager = this.getEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        if (transaction.isActive() == false) {
            transaction.begin();
        }

        Category category = new Category();
        category.setCatalogName("category name");
        category.setName("name");
        category.setStatus(Status.NEW_POST);
        category.setViewable(true);
        category.setDescription("category description");

        manager.persist(category);
        transaction.commit();

        Category ret = manager.find(Category.class, category.getId());

        assertEquals("Equal is expected.", "category name", ret.getCatalogName());
        assertEquals("Equal is expected.", "category description", ret.getDescription());
        assertTrue("True is expected.", ret.isViewable());
        assertNull("Null is expected.", ret.getParentCategory());

        // update some field.
        category.setCatalogName("update");
        category.setDescription("des");
        category.setName("name_update");
        category.setStatus(Status.DELETED);

        // verify if the entity is updated.
        ret = manager.find(Category.class, category.getId());

        assertEquals("Equal is expected.", "update", ret.getCatalogName());
        assertEquals("Equal is expected.", "des", ret.getDescription());
        assertEquals("Equal is expected.", "name_update", ret.getName());
        assertEquals("Equal is expected.", Status.DELETED, ret.getStatus());
    }
}
