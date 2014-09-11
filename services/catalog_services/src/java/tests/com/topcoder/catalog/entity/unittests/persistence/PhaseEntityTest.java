/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.unittests.persistence;

import com.topcoder.catalog.entity.Phase;
import com.topcoder.catalog.entity.TestHelper;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Set;

/**
 * <p>This test checks all attributes of Phase entity correspond database's columns.</p>
 * <p>Before each test the {@link #setUp} method is called. This method saves all necessary foreign
 * entities to database and the entity itself.</p>
 * <p>If the entity saved successfully, it indicates that all constraints are satisfied.</p>
 * <p>After that each method tries to find the entity by a field value pointed in where clause.</p>
 * <p>If the list is empty, then the test fails. It guarantees that each entity attribute mapped to
 * a separate database column (no values in all entities tests are duplicated).</p>
 *
 * @author Retunsky
 * @version 1.0
 */
public class PhaseEntityTest extends BasePhaseEntityTest {

    /**
     * <p>Persists entity instance to the database before each test case.</p>
     *
     * @throws Exception to jUnit
     */
    protected void setUp() throws Exception {
        super.setUp(); // always call super method
        TestHelper.clearTables(); // clear predefinded phases
        persistEntity(true); // pointed true to indicate that all foreign entities are to be stored as well
    }

    /**
     * <p>Test removing.</p>
     */
    public void testRemove() {
        final EntityManager em = TestHelper.getEntityManager();
        final EntityTransaction tx = TestHelper.getEntityTransaction();
        final Phase entity = getEntity();

        tx.begin();
        em.remove(em.getReference(Phase.class, entity.getId()));
        tx.commit();
        final Set<Phase> items = getEntities("select e from Phase e");
        assertTrue(
            "There should be no entities after removing", items.isEmpty());
    }

    /**
     * <p>Test <code>description</code> attribute stored properly.</p>
     */
    public void testDescription() {
        final Set<Phase> items = getEntities("select e from Phase e where description=?", DESCRIPTION);
        assertFalse(
            "Not found entities by the following query: select e from Phase e where description='"
                + DESCRIPTION + "'", items.isEmpty());
    }


}
