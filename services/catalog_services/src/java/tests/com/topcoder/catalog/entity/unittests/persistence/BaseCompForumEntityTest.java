/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.unittests.persistence;

import com.topcoder.catalog.entity.CompForum;
import com.topcoder.catalog.entity.CompVersion;
import com.topcoder.catalog.entity.TestHelper;

import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

/**
 * <p>This is a base class for CompForumEntity JPA tests.</p>
 * <p>It provide basic operations for concrete implementation
 * {@link CompForumEntityTest}.</p>
 * <p>It creates an entity instance with default fields values (including foreign entities).</p>
 * <p>Stores all necessary foreign entities to the database to prepare environment for CompForum
 * entity testing.</p>
 * <p>After all test's cases are performed the method <code>tearDown</code> cleans up database
 * and provides tests isolation.</p>
 *
 * @author Retunsky
 * @version 1.0
 */
public abstract class BaseCompForumEntityTest extends CommonEntityTest<CompForum> {

    /**
     * <p>Constructor. No additional actions to the super constructor.</p>
     */
    protected BaseCompForumEntityTest() {
    }

    /**
     * <p>Create a CompForum entity instance with default constant values defined above.</p>
     *
     * @return a CompForum entity instance
     */
    static CompForum createCompForum() {
        return new CompForum();
    }

    /**
     * <p>Persists CompForum foreign entities to store. Is called before saving CompForum itself.</p>
     *
     * @param compForum           entity instance
     * @param separateTransaction <ul><li><code>true</code> - persist objects in a separate transaction</li>
     *                            <li><code>false</code> - already in a transaction context</li></ul>
     */
    protected static void persistCompForumForeignObjects(
        final CompForum compForum, boolean separateTransaction) {
        if (separateTransaction) { // within its own transaction
            final EntityTransaction entityTransaction = TestHelper.getEntityTransaction();
            try {
                entityTransaction.begin();
                persistForeignObjects(compForum);
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
            persistForeignObjects(compForum); // or relying on current context
        }
    }

    /**
     * <p>Creates a default entity.</p>
     *
     * @return instance with test parameters
     */
    protected final CompForum createEntity() {
        return createCompForum();
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
                persistCompForumForeignObjects(getEntity(), false);
            }
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
     * <p>Persists CompForum foreign entities to store. Is called before saving CompForum itself.</p>
     *
     * @param compForum entity instance
     */
    private static void persistForeignObjects(
        final CompForum compForum) {
        final CompVersion compVersion = BaseCompVersionEntityTest.createCompVersion();
        compVersion.setComponent(null);
        compForum.setCompVersion(compVersion);
        compVersion.setForum(compForum);

        BaseCompVersionEntityTest.persistCompVersionForeignObjects(compVersion, false);
        persistEntity(compVersion);
        compForum.setJiveCategoryId(compVersion.getForum().getJiveCategoryId());
    }
}
