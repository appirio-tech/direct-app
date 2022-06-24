/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.unittests.persistence;

import com.topcoder.catalog.entity.CompUser;

import java.util.Set;

/**
 * <p>This test checks all attributes of CompUser entity correspond database's columns.</p>
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
public class CompUserEntityTest extends BaseCompUserEntityTest {

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
     * <p>Test <code>userId</code> attribute stored properly.</p>
     */
    public void testUserId() {
        final Set<CompUser> items = getEntities("select e from CompUser e where userId=?", getEntity().getUserId());
        assertFalse(
            "Not found entities by the following query: select e from CompUser e where userId='"
                + getEntity().getUserId() + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>component</code> relation.</p>
     */
    public void testComponentRelation() {
        final Set<CompUser> items = getEntities("select e from CompUser e join e.component f where f.name=?",
            BaseComponentEntityTest.NAME);
        assertFalse(
            "Not found entities by the following query: select e from CompUser e join e.component f"
                + " where f.name='" + BaseComponentEntityTest.NAME + "'", items.isEmpty());
    }
}
