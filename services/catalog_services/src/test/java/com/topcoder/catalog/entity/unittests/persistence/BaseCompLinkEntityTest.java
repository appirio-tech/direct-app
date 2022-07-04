/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.unittests.persistence;

import com.topcoder.catalog.entity.CompLink;
import com.topcoder.catalog.entity.CompVersion;
import com.topcoder.catalog.entity.TestHelper;

import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

/**
 * <p>This is a base class for CompLinkEntity JPA tests.</p>
 * <p>It provide basic operations for concrete implementation
 * {@link CompLinkEntityTest}.</p>
 * <p>It creates an entity instance with default fields values (including foreign entities).</p>
 * <p>Stores all necessary foreign entities to the database to prepare environment for CompLink
 * entity testing.</p>
 * <p>After all test's cases are performed the method <code>tearDown</code> cleans up database
 * and provides tests isolation.</p>
 *
 * @author Retunsky
 * @version 1.0
 */
public abstract class BaseCompLinkEntityTest extends CommonEntityTest<CompLink> {
    /**
     * Default CompLink.link value.
     */
    public static final String LINK = "8";


    /**
     * <p>Constructor. No additional actions to the super constructor.</p>
     */
    protected BaseCompLinkEntityTest() {
    }

    /**
     * <p>Create a CompLink entity instance with default constant values defined above.</p>
     *
     * @return a CompLink entity instance
     */
    static CompLink createCompLink() {
        final CompLink compLink = new CompLink();
        compLink.setLink(LINK);
        return compLink;
    }

    /**
     * <p>Persists CompLink foreign entities to store. Is called before saving CompLink itself.</p>
     *
     * @param compLink            entity instance
     * @param separateTransaction <ul><li><code>true</code> - persist objects in a separate transaction</li>
     *                            <li><code>false</code> - already in a transaction context</li></ul>
     */
    protected static void persistCompLinkForeignObjects(
        final CompLink compLink, boolean separateTransaction) {
        if (separateTransaction) { // within its own transaction
            final EntityTransaction entityTransaction = TestHelper.getEntityTransaction();
            try {
                entityTransaction.begin();
                persistForeignObjects(compLink);
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
            persistForeignObjects(compLink); // or relying on current context
        }
    }

    /**
     * <p>Creates a default entity.</p>
     *
     * @return instance with test parameters
     */
    protected final CompLink createEntity() {
        return createCompLink();
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
                persistCompLinkForeignObjects(getEntity(), false);
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
     * <p>Persists CompLink foreign entities to store. Is called before saving CompLink itself.</p>
     *
     * @param compLink entity instance
     */
    private static void persistForeignObjects(
        final CompLink compLink) {
        final CompVersion compVersion = BaseCompVersionEntityTest.createCompVersion();
        compVersion.setComponent(null);
        compLink.setCompVersion(compVersion);
        compVersion.setLink(compLink);
        BaseCompVersionEntityTest.persistCompVersionForeignObjects(compVersion, false);
        persistEntity(compVersion);
    }
}
