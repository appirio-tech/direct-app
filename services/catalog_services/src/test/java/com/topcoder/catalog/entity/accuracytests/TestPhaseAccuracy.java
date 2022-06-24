/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.accuracytests;

import com.topcoder.catalog.entity.Phase;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;


/**
 * Accuracy test cases for Entity <code>Phase </code>.
 *
 * @author Chenhong
 * @version 1.0
 */
public class TestPhaseAccuracy extends BaseTest {
    /**
     * Test if Phase entity can be created.
     * 
     * <p>
     * Test Entity persist, find, remove.
     * </p>
     *
     * @throws Exception to junit.
     */
    public void testCreate() throws Exception {
        EntityManager manager = this.getEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        if (transaction.isActive() == false) {
            transaction.begin();
        }

        Phase phase = new Phase();
        phase.setDescription("phase");

        // persist the Phase entity
        manager.persist(phase);
        transaction.commit();

        // find the Phase entity.
        Phase ret = manager.find(Phase.class, phase.getId());

        assertEquals("Equal is expected.", "phase", ret.getDescription());

        // remove the Phase entity.
        manager.remove(phase);

        // verify the entity removed.
        ret = manager.find(Phase.class, phase.getId());
        assertNull("The phase entity should be removed.", ret);
    }

    /**
     * Test if the Phase entity can be updated or not.
     * <p>
     * Test entity update.
     * </p>
     * @throws Exception to junit
     */
    public void testUpdate() throws Exception {
        EntityManager manager = this.getEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        if (transaction.isActive() == false) {
            transaction.begin();
        }

        Phase phase = new Phase();
        phase.setDescription("phase");

        manager.persist(phase);
        transaction.commit();

        Phase ret = manager.find(Phase.class, phase.getId());

        assertEquals("Equal is expected.", "phase", ret.getDescription());

        if (transaction.isActive() == false) {
            transaction.begin();
        }

        // update the Phase.
        phase.setDescription("updated");

        ret = manager.find(Phase.class, phase.getId());
        assertEquals("Equal is expected.", "updated", ret.getDescription());
    }
}
