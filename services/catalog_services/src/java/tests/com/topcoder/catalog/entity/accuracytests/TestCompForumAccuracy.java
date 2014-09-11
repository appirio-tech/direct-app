/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.accuracytests;

import com.topcoder.catalog.entity.CompForum;
import com.topcoder.catalog.entity.CompVersion;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;


/**
 * Accuracy test cases for Entity <code>CompForum</code>.
 *
 * @author Chenhong
 * @version 1.0
 */
public class TestCompForumAccuracy extends BaseTest {
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

        CompForum forum = new CompForum();
        forum.setCompVersion(compVersion);

        manager.persist(forum);

        transaction.commit();

        // check if the CompForum is persisted.
        this.checkCompForumPersisted();
    }

    /**
     * check if the CompForum is persisted.
     *
     * @throws Exception to junit.
     */
    private void checkCompForumPersisted() throws Exception {
        final List resultList =
            getEntityManager().createNativeQuery("select comp_vers_id from comp_jive_category_xref").getResultList();
        assertTrue("should have one row.", 1 == resultList.size());

        int compVersionId = ((Number) resultList.get(0)).intValue();
        assertEquals("Equal to 0.", 0, compVersionId);

    }
}
