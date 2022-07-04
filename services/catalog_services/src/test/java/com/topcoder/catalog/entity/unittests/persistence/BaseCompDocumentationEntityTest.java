/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.unittests.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import com.topcoder.catalog.entity.CompDocumentation;
import com.topcoder.catalog.entity.CompVersion;
import com.topcoder.catalog.entity.TestHelper;
/**
 * <p>This is a base class for CompDocumentation JPA tests.</p>
 * <p>It provide basic operations for concrete implementation
 * {@link CompDocumentationEntityTest}.</p>
 * <p>It creates an entity instance with default fields values (including foreign entities).</p>
 * <p>Stores all necessary foreign entities to the database to prepare environment for CompDocumentation
 * entity testing.</p>
 * <p>After all test's cases are performed the method <code>tearDown</code> cleans up database
 * and provides tests isolation.</p>
 *
 * @author KingStone
 * @version 1.1
 */
public class BaseCompDocumentationEntityTest extends CommonEntityTest<CompDocumentation> {
    /**
     * Default CompDocumentation.documentTypeId value.
     */
    static final Long DOCUMENT_TYPE_ID = 300L;

    /**
     * Default CompDocumentation.url value.
     */
    static final String URL = "http://www.google.com/doc/good.rtf";

    /**
     * Default CompDocumentation.documentName value.
     */
    static final String DOCUMENT_NAME = "xxx";

    /**
     * <p>Constructor. No additional actions to the super constructor.</p>
     */
    protected BaseCompDocumentationEntityTest() {
    }

    /**
     * <p>Create a CompDocumentation entity instance with default constant values defined above and
     * a CompVersion instance.</p>
     *
     * @return a CompDocumentation entity instance
     */
    static CompDocumentation createCompDocumentation() {
        final CompDocumentation myCompDocumentation = new CompDocumentation();
        // set fields
        myCompDocumentation.setDocumentName(DOCUMENT_NAME);
        myCompDocumentation.setUrl(URL);
        myCompDocumentation.setDocumentTypeId(DOCUMENT_TYPE_ID);

        // add CompVersion
        final CompVersion compVersion = BaseCompVersionEntityTest.createCompVersion();
        compVersion.setComponent(null);
        List<CompDocumentation> list = new ArrayList<CompDocumentation>();
        list.add(myCompDocumentation);
        compVersion.setDocumentation(list);
        myCompDocumentation.setCompVersion(compVersion);
        return myCompDocumentation;
    }

    /**
     * <p>Persists CompDocumentation foreign entities to store. Is called before saving CompDocumentation itself.</p>
     *
     * @param compDocumentation           entity instance
     * @param separateTransaction <ul><li><code>true</code> - persist objects in a separate transaction</li>
     *                            <li><code>false</code> - already in a transaction context</li></ul>
     */
    protected static void persistCompDocumentationForeignObjects(
        final CompDocumentation compDocumentation, boolean separateTransaction) {
        if (separateTransaction) { // within its own transaction
            final EntityTransaction entityTransaction = TestHelper.getEntityTransaction();
            try {
                entityTransaction.begin();
                persistForeignObjects(compDocumentation);
                entityTransaction.commit();
            } catch (PersistenceException e) {
                if (entityTransaction.isActive()) {
                    entityTransaction.rollback();
                }
                throw e; // re-throw the exception
            } finally {
                TestHelper.getEntityManager().clear(); // clears persistence context (caches etc.)
            }
        } else {
            persistForeignObjects(compDocumentation); // or relying on current context
        }
    }

    /**
     * <p>Creates a default entity.</p>
     *
     * @return instance with test parameters
     */
    protected final CompDocumentation createEntity() {
        return createCompDocumentation();
    }

    /**
     * <p>Stores the {@link #entity} to database.</p>
     *
     * @param saveForeignObjects indicates whether save the entity only, or all foreign entities as well
     *                           <code>true</code> - save all foreign entities
     *                           <code>false</code> - save only the entity itself
     */
    protected void persistEntity(boolean saveForeignObjects) {
        final EntityTransaction entityTransaction = TestHelper.getEntityTransaction();
        try {
            entityTransaction.begin();
            if (saveForeignObjects) {
                persistCompDocumentationForeignObjects(getEntity(), false);
            }
            persistEntity(getEntity());
            entityTransaction.commit();
        } catch (PersistenceException e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw e;
        } finally {
            TestHelper.getEntityManager().clear();
        } // clears persistence context (caches etc.)
    }

    /**
     * <p>Persists CompDocumentation foreign entities to store. Is called before saving CompDocumentation itself.</p>
     *
     * @param compDocumentation entity instance
     */
    private static void persistForeignObjects(
        final CompDocumentation compDocumentation) {
        if (compDocumentation.getCompVersion() != null) {
            BaseCompVersionEntityTest
                .persistCompVersionForeignObjects(compDocumentation.getCompVersion(), false);
            persistEntity(compDocumentation.getCompVersion());
        }
    }
}
