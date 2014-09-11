/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.accuracytests;

import com.topcoder.catalog.entity.CompClient;
import com.topcoder.catalog.entity.CompVersion;
import com.topcoder.catalog.entity.Component;
import com.topcoder.catalog.entity.Status;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;


/**
 * Accuracy test cases for entity <code>CompClient</code>.
 *
 * @author Chenhong
 * @version 1.0
 */
public class TestCompClientAccuracy extends BaseTest {
    /**
     * Test if the entity can be created.
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

        Component componentRet = manager.find(Component.class, c.getId());

        CompClient client = new CompClient();
        client.setComponent(componentRet);
        client.setClientId(new Long(1));

        manager.persist(client);
        transaction.commit();

        if (transaction.isActive() == false) {
            transaction.begin();
        }

        manager.clear();

        CompClient ret = manager.find(CompClient.class, client);
        assertNotNull("The CompClient should not be null.", ret);
        assertEquals("Equal to 1.", new Long(1), ret.getClientId());

        // the user list should be retrieved from the user_client table.
        assertEquals("Equal to 3.", 3, ret.getUsers().size());
        assertTrue("True is expected.", ret.getUsers().contains(new Long(1)));
        assertTrue("True is expected.", ret.getUsers().contains(new Long(2)));

        assertTrue("True is expected.", ret.getUsers().contains(new Long(3)));
    }
}
