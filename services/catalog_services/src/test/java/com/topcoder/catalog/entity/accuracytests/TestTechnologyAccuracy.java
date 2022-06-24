/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.accuracytests;

import com.topcoder.catalog.entity.Status;
import com.topcoder.catalog.entity.Technology;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;


/**
 * Accuracy test cases for Entity <code>Technology</code>.
 *
 * @author Chenhong
 * @version 1.0
 */
public class TestTechnologyAccuracy extends BaseTest {
    /**
     * Test the entity creation, delete and find.
     *
     * @throws Exception to junit.
     */
    public void testCreate() throws Exception {
        EntityManager manager = this.getEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        if (transaction.isActive() == false) {
            transaction.begin();
        }

        Technology t = new Technology();
        t.setDescription("tech");
        t.setName("name1");
        t.setStatus(Status.ACTIVE);

        manager.persist(t);
        transaction.commit();

        if (transaction.isActive() == false) {
            transaction.begin();
        }

        Technology ret = manager.find(Technology.class, t.getId());
        assertEquals("Equal is expected.", "tech", ret.getDescription());
        assertEquals("Equal is expected.", "name1", ret.getName());
        assertEquals("Equal is expected.", Status.ACTIVE, ret.getStatus());

        manager.remove(t);

        ret = manager.find(Technology.class, t.getId());
        assertNull("The entity should be removed.", ret);
    }

    /**
     * Test if the entity can be updated or not.
     * 
     * <p>
     * First persist in an Entity and then do the update.
     * </p>
     *
     * @throws Exception to junit.
     */
    public void testUpdate() throws Exception {
        EntityManager manager = this.getEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        if (transaction.isActive() == false) {
            transaction.begin();
        }

        Technology t = new Technology();
        t.setDescription("tech");
        t.setName("name1");
        t.setStatus(Status.ACTIVE);

        manager.persist(t);
        transaction.commit();

        t.setDescription("upate tech");
        t.setName("name2");
        t.setStatus(Status.APPROVED);

        Technology ret = manager.find(Technology.class, t.getId());
        assertEquals("Equal is expected.", "upate tech", ret.getDescription());
        assertEquals("Equal is expected.", "name2", ret.getName());
        assertEquals("Equal is expected.", Status.APPROVED, ret.getStatus());
    }
}
