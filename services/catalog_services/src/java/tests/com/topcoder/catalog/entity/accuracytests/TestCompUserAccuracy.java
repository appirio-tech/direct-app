/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.accuracytests;

import com.topcoder.catalog.entity.CompUser;
import com.topcoder.catalog.entity.CompVersion;
import com.topcoder.catalog.entity.Component;
import com.topcoder.catalog.entity.Status;


import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;


/**
 * Accuracy test cases for Entity <code>CompUser </code>.
 *
 * @author Chenhong
 * @version 1.0
 */
public class TestCompUserAccuracy extends BaseTest {
    /**
     * Test if the CompUser entity can be created.
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

        CompUser user = new CompUser();
        user.setComponent(componentRet);

        user.setUserId(new Long(1));

        manager.persist(user);

        transaction.commit();

        CompUser ret = manager.find(CompUser.class, user);
        assertNotNull("The CompUser should not be null.", ret);
        assertEquals("Equal to 1.", new Long(1), ret.getUserId());
        assertEquals("Equal is expected.", componentRet, ret.getComponent());
    }
}
