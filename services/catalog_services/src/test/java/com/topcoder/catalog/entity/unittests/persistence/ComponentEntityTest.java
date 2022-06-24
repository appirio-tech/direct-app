/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.unittests.persistence;

import com.topcoder.catalog.entity.CompVersion;
import com.topcoder.catalog.entity.Component;
import com.topcoder.catalog.entity.TestHelper;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import java.util.Set;

/**
 * <p>This test checks all attributes of Component entity correspond database's columns.</p>
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
public class ComponentEntityTest extends BaseComponentEntityTest {

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
        final Component component = getEntity();
        final CompVersion currentVersion = component.getCurrentVersion();

        tx.begin();
        currentVersion.setComponent(null);
        component.setVersions(null);
        component.setUsers(null);
        component.setCategories(null);
        component.setClients(null);
        em.merge(currentVersion);
        em.remove(em.getReference(Component.class, component.getId()));
        em.remove(em.getReference(CompVersion.class, currentVersion.getId()));
        tx.commit();
        final Set<Component> items = getEntities("select e from Component e");
        assertTrue(
            "There should be no entities after removing", items.isEmpty());
    }

    /**
     * <p>Test <code>currentVersion</code> relation.</p>
     */
    public void testCompVersionRelation() {
        final Set<Component> items = getEntities("select e from Component e join e.currentVersion f where f.comments=?",
            BaseCompVersionEntityTest.COMMENTS);
        assertFalse(
            "Not found entities by the following query:"
                + " select e from Component e join e.currentVersion f where f.comments='"
                + BaseCompVersionEntityTest.COMMENTS + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>description</code> attribute stored properly.</p>
     */
    public void testDescription() {
        final Set<Component> items = getEntities("select e from Component e where description=?", DESCRIPTION);
        assertFalse(
            "Not found entities by the following query: select e from Component e where description='"
                + DESCRIPTION + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>functionalDesc</code> attribute stored properly.</p>
     */
    public void testFunctionalDesc() {
        final Set<Component> items = getEntities("select e from Component e where functionalDesc=?", FUNCTIONALDESC);
        assertFalse(
            "Not found entities by the following query: select e from Component e where functionalDesc='"
                + FUNCTIONALDESC + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>name</code> attribute stored properly.</p>
     */
    public void testName() {
        final Set<Component> items = getEntities("select e from Component e where name=?", NAME);
        assertFalse(
            "Not found entities by the following query: select e from Component e where name='"
                + NAME + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>shortDesc</code> attribute stored properly.</p>
     */
    public void testShortDesc() {
        final Set<Component> items = getEntities("select e from Component e where shortDesc=?", SHORTDESC);
        assertFalse(
            "Not found entities by the following query: select e from Component e where shortDesc='"
                + SHORTDESC + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>status</code> attribute stored properly.</p>
     */
    public void testStatus() {
        final Set<Component> items = getEntities("select e from Component e where status=?", STATUS);
        assertFalse(
            "Not found entities by the following query: select e from Component e where status='"
                + STATUS + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>rootCategory</code> join.</p>
     */
    public void testRootCategoryJoin() {
        final Set<Component> items =
            getEntities("select e from Component e join e.rootCategory f where f.catalogName=?",
                "catalog 2");
        assertFalse("Not found entities by the following query: select e from Component"
            + " e join e.rootCategory f where f.catalogName='"
            + CategoryEntityTest.CATALOGNAME + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>rootCategory</code> relation.</p>
     */
    public void testCategoryRelation() {
        final Component component = TestHelper.getEntityManager().find(Component.class, getEntity().getId());
        assertNotNull("There should be the root category", component.getRootCategory());
        assertEquals("Wrong number of categories", 2, component.getCategories().size());
    }

    /**
     * <p>Test <code>versions</code> relation.</p>
     */
    public void testVersionsRelation() {
        final Component component = TestHelper.getEntityManager().find(Component.class, getEntity().getId());
        assertNotNull("Current version must present", component.getCurrentVersion());
    }

    /**
     * <p>Test <code>clients</code> relation.</p>
     */
    public void testClientsRelation() {
        final Component component = TestHelper.getEntityManager().find(Component.class, getEntity().getId());
        assertEquals("Wrong number of clients", 3, component.getClients().size());
    }

    /**
     * <p>Test <code>users</code> relation.</p>
     */
    public void testUsersRelation() {
        final Component component = TestHelper.getEntityManager().find(Component.class, getEntity().getId());
        assertEquals("Wrong number of users", 2, component.getUsers().size());
    }
}
