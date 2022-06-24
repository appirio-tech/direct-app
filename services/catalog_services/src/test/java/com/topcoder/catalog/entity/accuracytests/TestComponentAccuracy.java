/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.accuracytests;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.CompClient;
import com.topcoder.catalog.entity.CompUser;
import com.topcoder.catalog.entity.CompVersion;
import com.topcoder.catalog.entity.Component;
import com.topcoder.catalog.entity.Phase;
import com.topcoder.catalog.entity.Status;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;


/**
 * Accuracy test cases for Entity <code>Component </code>.
 *
 * @author Chenhong
 * @version 1.0
 */
public class TestComponentAccuracy extends BaseTest {
    /**
     * Test if the entity can be created.
     * 
     * <p>
     * In this test case, no client, no user and no category is defined.
     * </p>
     *
     * @throws Exception to junit.
     */
    public void testCreate() throws Exception {
        EntityManager manager = this.getEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        DBUtil.initDB();
        if (transaction.isActive() == false) {
            transaction.begin();
        }


        // first get a CompVersion entity.
        CompVersion compVersion = manager.find(CompVersion.class, new Long(0));

        Component c = new Component();
        c.setDescription("c");
        c.setStatus(Status.APPROVED);
        c.setFunctionalDesc("f");

        c.setCurrentVersion(compVersion);
        c.setName("name");

        manager.persist(c);

        transaction.commit();
    }

    /**
     * Test if the entity can be created.
     * 
     * <p>
     * The version list will be set and commit.
     * </p>
     *
     * @throws Exception to junit.
     */
    public void testCreate_2() throws Exception {
        EntityManager manager = this.getEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        DBUtil.initDB();
        if (transaction.isActive() == false) {
            transaction.begin();
        }


        CompVersion version = new CompVersion();
        version.setComments("version1.0");
        version.setVersionText("text");
        version.setVersion(new Long(3));

        Phase phase = new Phase();
        phase.setId(new Long(0));
        phase.setDescription("");

        version.setPhase(phase);
        version.setPhaseTime(new Date());
        version.setPhasePrice(111.0);

        CompVersion version2 = new CompVersion();
        version2.setComments("version2.0");
        version2.setVersionText("text2");
        version2.setVersion(new Long(5));

        version2.setPhase(phase);
        version2.setPhasePrice(200.0);
        version2.setPhaseTime(new Date());

        manager.persist(version);
        manager.persist(version2);

        if (transaction.isActive() == false) {
            transaction.begin();
        }

        transaction.commit();

        if (transaction.isActive() == false) {
            transaction.begin();
        }

        // first get a CompVersion entity.
        CompVersion compVersion = manager.find(CompVersion.class, new Long(0));

        Component c = new Component();
        c.setDescription("c");
        c.setStatus(Status.APPROVED);
        c.setFunctionalDesc("f");

        c.setCurrentVersion(compVersion);
        c.setName("name");

        List list = new ArrayList();
        list.add(version);
        list.add(version2);

        c.setVersions(list);

        manager.persist(c);

        transaction.commit();

        Component ret = manager.find(Component.class, c.getId());
        assertEquals("Equal to 2.", 2, ret.getVersions().size());
    }

    /**
     * Test if the entity can be created.
     * 
     * <p>
     * In this test case, comp client will be set.
     * </p>
     *
     * @throws Exception to junit.
     */
    public void testUpdate_1() throws Exception {
        EntityManager manager = this.getEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        DBUtil.initDB();
        if (transaction.isActive() == false) {
            transaction.begin();
        }


        // first get a CompVersion entity.
        CompVersion compVersion = manager.find(CompVersion.class, new Long(0));

        Component c = new Component();
        c.setDescription("c");
        c.setStatus(Status.APPROVED);
        c.setFunctionalDesc("f");

        c.setCurrentVersion(compVersion);
        c.setName("name");

        manager.persist(c);

        transaction.commit();

        CompClient client = new CompClient();
        client.setComponent(c);
        client.setClientId(new Long(1));

        CompClient client2 = new CompClient();
        client2.setComponent(c);
        client2.setClientId(new Long(2));

        manager.persist(client);
        manager.persist(client2);

        if (transaction.isActive() == false) {
            transaction.begin();
        }

        transaction.commit();

        if (transaction.isActive() == false) {
            transaction.begin();
        }

        Set set = new HashSet();
        set.add(client);
        set.add(client2);

        c.setClients(set);

        Component ret = manager.find(Component.class, c.getId());
        assertEquals("Equal to 2.", 2, ret.getClients().size());

        transaction.commit();
    }

    /**
     * Test if the entity can be created.
     * 
     * <p>
     * In this test case, the comp user will be updated.
     * </p>
     *
     * @throws Exception to junit.
     */
    public void testUpdate_2() throws Exception {
        EntityManager manager = this.getEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        DBUtil.initDB();

        if (transaction.isActive() == false) {
            transaction.begin();
        }


        // first get a CompVersion entity.
        CompVersion compVersion = manager.find(CompVersion.class, new Long(0));

        Component c = new Component();
        c.setDescription("c");
        c.setStatus(Status.APPROVED);
        c.setFunctionalDesc("f");

        c.setCurrentVersion(compVersion);
        c.setName("name");

        manager.persist(c);

        transaction.commit();

        if (transaction.isActive() == false) {
            transaction.begin();
        }

        CompUser user1 = new CompUser();
        user1.setComponent(c);
        user1.setUserId(new Long(1));

        CompUser user2 = new CompUser();
        user2.setComponent(c);
        user2.setUserId(new Long(2));

        CompUser user3 = new CompUser();
        user3.setComponent(c);
        user3.setUserId(new Long(3));

        manager.persist(user1);
        manager.persist(user2);
        manager.persist(user3);

        transaction.commit();

        if (transaction.isActive() == false) {
            transaction.begin();
        }

        Set set = new HashSet();
        set.add(user1);
        set.add(user2);
        set.add(user3);

        c.setUsers(set);

        Component ret = manager.find(Component.class, c.getId());

        assertEquals("Equal to 3.", 3, ret.getUsers().size());

        transaction.commit();
    }

    /**
     * Test if the entity can be created.
     * 
     * <p>
     * In this test case, no client,  root category will be set.
     * </p>
     *
     * @throws Exception to junit.
     */
    public void testUpdate_3() throws Exception {
        EntityManager manager = this.getEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        DBUtil.initDB();

        if (transaction.isActive() == false) {
            transaction.begin();
        }


        // first get a CompVersion entity.
        CompVersion compVersion = manager.find(CompVersion.class, new Long(0));

        Component c = new Component();
        c.setDescription("c");
        c.setStatus(Status.APPROVED);
        c.setFunctionalDesc("f");

        c.setCurrentVersion(compVersion);
        c.setName("name");

        manager.persist(c);

        transaction.commit();

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

        c.setRootCategory(category);

        Component ret = manager.find(Component.class, c.getId());
        assertEquals("Equal is expected.", "category name", ret.getRootCategory().getCatalogName());

        if (transaction.isActive() == false) {
            transaction.begin();
        }

        transaction.commit();
    }

    /**
     * Test if the entity can be created.
     * 
     * <p>
     * In this test case, no client,  categorys will be set.
     * </p>
     *
     * @throws Exception to junit.
     */
    public void testUpdate_4() throws Exception {
        EntityManager manager = this.getEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        DBUtil.initDB();
        if (transaction.isActive() == false) {
            transaction.begin();
        }


        // first get a CompVersion entity.
        CompVersion compVersion = manager.find(CompVersion.class, new Long(0));

        Component c = new Component();
        c.setDescription("c");
        c.setStatus(Status.APPROVED);
        c.setFunctionalDesc("f");

        c.setCurrentVersion(compVersion);
        c.setName("name");

        manager.persist(c);

        transaction.commit();

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

        c.setRootCategory(category);

        Component ret = manager.find(Component.class, c.getId());
        assertEquals("Equal is expected.", "category name", ret.getRootCategory().getCatalogName());

        if (transaction.isActive() == false) {
            transaction.begin();
        }

        transaction.commit();

        if (transaction.isActive() == false) {
            transaction.begin();
        }

        // create some category more.
        Category c1 = new Category();
        c1.setCatalogName("c1");
        c1.setStatus(Status.ACTIVE);
        c1.setName("c1");
        c1.setViewable(false);
        c1.setDescription("dc1");
        c1.setParentCategory(category);

        Category c2 = new Category();
        c2.setCatalogName("c2");
        c2.setDescription("c2");
        c2.setName("c2");
        c2.setParentCategory(category);
        c2.setViewable(false);
        c2.setStatus(Status.ACTIVE);
        
        
        manager.persist(c1);
        manager.persist(c2);

        transaction.commit();

        List list = new ArrayList();
        list.add(c1);
        list.add(c2);
        c.setCategories(list);

        if (transaction.isActive() == false) {
            transaction.begin();
        }

        transaction.commit();

        ret = manager.find(Component.class, c.getId());
        assertEquals("Equal is expected.", 2, ret.getCategories().size());
    }
}
