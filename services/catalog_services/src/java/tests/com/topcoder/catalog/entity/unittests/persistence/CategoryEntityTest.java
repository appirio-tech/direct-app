/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.unittests.persistence;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.Status;

import java.util.Set;

/**
 * <p>This test checks all attributes of Category entity correspond database's columns.</p>
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
public class CategoryEntityTest extends CommonEntityTest<Category> {
    /**
     * Default Category.catalogName value.
     */
    public static final String CATALOGNAME = "catalog 1";
    /**
     * Default Category.name value.
     */
    public static final String NAME = "1";
    /**
     * Default Category.description value.
     */
    public static final String DESCRIPTION = "1";
    /**
     * Default Category.status value.
     */
    public static final Status STATUS = Status.ACTIVE;
    /**
     * Default Category.viewable value.
     */
    public static final boolean VIEWABLE = true;

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
     * <p>Test <code>catalogName</code> attribute stored properly.</p>
     */
    public void testCatalogName() {
        final Set<Category> items = getEntities("select e from Category e where catalogName=?", CATALOGNAME);
        assertFalse(
            "Not found entities by the following query: select e from Category e where catalogName='"
                + CATALOGNAME + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>name</code> attribute stored properly.</p>
     */
    public void testName() {
        final Set<Category> items = getEntities("select e from Category e where name=?", NAME);
        assertFalse(
            "Not found entities by the following query: select e from Category e where name='"
                + NAME + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>description</code> attribute stored properly.</p>
     */
    public void testDescription() {
        final Set<Category> items = getEntities("select e from Category e where description=?", DESCRIPTION);
        assertFalse(
            "Not found entities by the following query: select e from Category e where description='"
                + DESCRIPTION + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>parentCategory</code> relation.</p>
     */
    public void testCategoryRelation() {
        final Set<Category> items = getEntities("select e from Category e join e.parentCategory f where f.name=?",
            NAME);
        assertFalse(
            "Not found entities by the following query: select e from Category e join e.parentCategory f"
                + " where f.name='" + NAME + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>status</code> attribute stored properly.</p>
     */
    public void testStatus() {
        final Set<Category> items = getEntities("select e from Category e where status=?", STATUS);
        assertFalse(
            "Not found entities by the following query: select e from Category e where status='"
                + STATUS + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>viewable</code> attribute stored properly.</p>
     */
    public void testViewable() {
        final Set<Category> items = getEntities("select e from Category e where viewable=?", VIEWABLE);
        assertFalse(
            "Not found entities by the following query: select e from Category e where viewable='"
                + VIEWABLE + "'", items.isEmpty());
    }
}
