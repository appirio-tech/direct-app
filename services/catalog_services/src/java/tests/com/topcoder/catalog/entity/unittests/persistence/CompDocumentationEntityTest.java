/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.unittests.persistence;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.topcoder.catalog.entity.CompDocumentation;
import com.topcoder.catalog.entity.TestHelper;

/**
 * <p>This test checks all attributes of CompDocumentation entity correspond database's columns.</p>
 * <p>Before each test the {@link #setUp} method is called. This method saves all necessary foreign
 * entities to database and the entity itself.</p>
 * <p>If the entity saved successfully, it indicates that all constraints are satisfied.</p>
 * <p>After that each method tries to find the entity by a field value pointed in where clause.</p>
 * <p>If the list is empty, then the test fails. It guarantees that each entity attribute mapped to
 * a separate database column (no values in all entities tests are duplicated).</p>
 *
 * @author KingStone
 * @version 1.1
 */
public class CompDocumentationEntityTest extends BaseCompDocumentationEntityTest {
    /**
     * <p>Persists entity instance to the database before each test case.</p>
     *
     * @throws Exception to jUnit
     */
    protected void setUp() throws Exception {
        super.setUp(); // always call super method
        // pointed true to indicate that all foreign entities are to be stored as well
        // note: doc_types table goes beyond this component, so this table is not affected.
        persistEntity(true);
    }

    /**
     * <p>Test removing.</p>
     */
    public void testRemove() {
        // set up set utility
        final EntityManager em = TestHelper.getEntityManager();
        final EntityTransaction tx = TestHelper.getEntityTransaction();
        final CompDocumentation compDocumentation = getEntity();

        tx.begin();
        em.remove(em.getReference(CompDocumentation.class, compDocumentation.getId()));
        tx.commit();
        final Set<CompDocumentation> items = getEntities("select e from CompDocumentation e");
        assertTrue("There should be no entities after removing", items.isEmpty());
    }

    /**
     * <p>Test <code>id</code> attribute stored properly.</p>
     */
    public void testId() {
        String query = "select e from CompDocumentation e where document_id=";
        final Set<CompDocumentation> items = getEntities(query + "?", getEntity().getId());
        assertFalse("Not found entities by the following query: " + query + "'"
                + getEntity().getId() + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>version</code> relation.</p>
     */
    public void testCompDocumentationRelation() {
        String query = "select e from CompDocumentation e join e.compVersion f where f.version=";
        final Set<CompDocumentation> items = getEntities(query + "?", getEntity().getCompVersion()
                .getVersion());
        assertFalse("Not found entities by the following query: " + query + "'"
                + getEntity().getCompVersion().getVersion() + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>documentTypeId</code> attribute stored properly.</p>
     */
    public void testDocumentTypeId() {
        String query = "select e from CompDocumentation e where documentTypeId=";
        final Set<CompDocumentation> items = getEntities(query + "?", getEntity()
                .getDocumentTypeId());
        assertFalse("Not found entities by the following query: " + query + "'"
                + getEntity().getDocumentTypeId() + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>documentType</code> can be retrieved correctly.</p>
     */
    public void testDocumentType() {
        String query = "select e from CompDocumentation e where document_id=";
        final Set<CompDocumentation> items = getEntities(query + "?", getEntity().getId());
        assertEquals("The DocumentType is not correct.", "type1", items.iterator().next()
                .getDocumentType());
    }

    /**
     * <p>Test <code>documentName</code> attribute stored properly.</p>
     */
    public void testDocumentName() {
        String query = "select e from CompDocumentation e where documentName=";
        final Set<CompDocumentation> items = getEntities(query + "?", getEntity().getDocumentName());
        assertFalse("Not found entities by the following query: " + query + "'"
                + getEntity().getDocumentName() + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>url</code> attribute stored properly.</p>
     */
    public void testUrl() {
        String query = "select e from CompDocumentation e where url=";
        final Set<CompDocumentation> items = getEntities(query + "?", getEntity().getUrl());
        assertFalse("Not found entities by the following query: " + query + "'"
                + getEntity().getUrl() + "'", items.isEmpty());
    }
}
