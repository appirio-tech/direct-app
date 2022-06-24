/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.accuracytests;

import com.topcoder.catalog.entity.CompLink;
import com.topcoder.catalog.entity.CompVersion;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;


/**
 * Accuracy test cases for Entity <code>CompLink</code>.
 *
 * @author Chenhong
 * @version 1.0
 */
public class TestCompLinkAccuracy extends BaseTest {
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

        // the CompVersion must existed.
        assertNotNull("The CompVersion entity should exist.", compVersion);

        CompLink compLink = new CompLink();
        compLink.setCompVersion(compVersion);
        compLink.setLink("www.topcoder.com");

        manager.persist(compLink);
        transaction.commit();

        CompLink ret = manager.find(CompLink.class, compLink.getId());
        assertNotNull("should not be null.", ret);

        assertEquals("Equal is expected.", compVersion, ret.getCompVersion());
        assertEquals("Equal is expected.", "www.topcoder.com", ret.getLink());

        manager.remove(compLink);
        assertNull("The CompLink should be removed.", manager.find(CompLink.class, compLink.getId()));
    }

    /**
     * Test if the entity can be updated.
     *
     * @throws Exception to junit.
     */
    public void testUpdate() throws Exception {
        EntityManager manager = this.getEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        
        DBUtil.initDB();

        if (transaction.isActive() == false) {
            transaction.begin();
        }


        // first get a CompVersion entity.
        CompVersion compVersion = manager.find(CompVersion.class, new Long(0));

        // the CompVersion must existed.
        assertNotNull("The CompVersion entity should exist.", compVersion);

        CompLink compLink = new CompLink();
        compLink.setCompVersion(compVersion);
        compLink.setLink("www.topcoder.com");

        manager.persist(compLink);
        transaction.commit();

        compLink.setLink("www.yahoo.com");

        CompLink ret = manager.find(CompLink.class, compLink.getId());
        assertEquals("Equal is expected.", "www.yahoo.com", ret.getLink());
    }
}
