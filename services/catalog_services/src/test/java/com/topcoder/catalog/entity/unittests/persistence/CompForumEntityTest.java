/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.unittests.persistence;

import com.topcoder.catalog.entity.CompForum;
import com.topcoder.catalog.entity.TestHelper;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Set;

/**
 * <p>This test checks all attributes of CompForum entity correspond database's columns.</p>
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
public class CompForumEntityTest extends BaseCompForumEntityTest {

    /**
     * <p>Persists entity instance to the database before each test case.</p>
     *
     * @throws Exception to jUnit
     */
    protected void setUp() throws Exception {
        super.setUp(); // always call super method
        persistEntity(true); // pointed true to indicate that all foreign entities are to be stored as well
    }

    /**
     * <p>Test removing.</p>
     */
    public void testRemove() {
        final EntityManager em = TestHelper.getEntityManager();
        final EntityTransaction tx = TestHelper.getEntityTransaction();
        final CompForum compForum = getEntity();

        tx.begin();
        em.remove(em.getReference(CompForum.class, compForum.getJiveCategoryId()));
        tx.commit();
        final Set<CompForum> items = getEntities("select e from CompForum e");
        assertTrue(
            "There should be no entities after removing", items.isEmpty());
    }

    /**
     * <p>Test <code>jiveCategoryId</code> attribute stored properly.</p>
     */
    public void testJiveCategoryId() {
        final Set<CompForum> items = getEntities("select e from CompForum e where jiveCategoryId=?",
            getEntity().getJiveCategoryId());
        assertFalse(
            "Not found entities by the following query: select e from CompForum e where jiveCategoryId='"
                + getEntity().getJiveCategoryId() + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>version</code> relation.</p>
     */
    public void testCompForumRelation() {
        final Set<CompForum> items = getEntities("select e from CompForum e join e.compVersion f where f.version=?",
            getEntity().getCompVersion().getVersion());
        assertFalse(
            "Not found entities by the following query:"
                + " select e from CompForum e join e.compVersion f where f.version='"
                + getEntity().getCompVersion().getVersion() + "'", items.isEmpty());
    }
}
