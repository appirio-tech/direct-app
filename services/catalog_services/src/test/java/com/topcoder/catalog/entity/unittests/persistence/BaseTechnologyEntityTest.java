/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.unittests.persistence;

import com.topcoder.catalog.entity.Status;
import com.topcoder.catalog.entity.Technology;
import com.topcoder.catalog.entity.TestHelper;

import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

/**
 * <p>This is a base class for TechnologyEntity JPA tests.</p>
 * <p>It provide basic operations for concrete implementation
 * {@link TechnologyEntityTest}.</p>
 * <p>It creates an entity instance with default fields values (including foreign entities).</p>
 * <p>Stores all necessary foreign entities to the database to prepare environment for Technology
 * entity testing.</p>
 * <p>After all test's cases are performed the method <code>tearDown</code> cleans up database
 * and provides tests isolation.</p>
 *
 * @author Retunsky
 * @version 1.0
 */
public abstract class BaseTechnologyEntityTest extends CommonEntityTest<Technology> {
    /**
     * Default Technology.description value.
     */
    public static final String DESCRIPTION = "46";
    /**
     * Default Technology.name value.
     */
    public static final String NAME = "47";
    /**
     * Default Technology.status value.
     */
    public static final Status STATUS = Status.REPOST;


    /**
     * <p>Constructor. No additional actions to the super constructor.</p>
     */
    protected BaseTechnologyEntityTest() {
    }

    /**
     * <p>Create a Technology entity instance with default constant values defined above.</p>
     *
     * @return a Technology entity instance
     */
    static Technology createTechnology() {
        final Technology technology = new Technology();
        technology.setDescription(DESCRIPTION);
        technology.setName(NAME);
        technology.setStatus(STATUS);

        return technology;
    }

    /**
     * <p>Persists Technology foreign entities to store. Is called before saving Technology itself.</p>
     *
     * @param technology          entity instance
     * @param separateTransaction <ul><li><code>true</code> - persist objects in a separate transaction</li>
     *                            <li><code>false</code> - already in a transaction context</li></ul>
     */
    protected static void persistTechnologyForeignObjects(
        final Technology technology, boolean separateTransaction) {
        if (separateTransaction) { // within its own transaction
            final EntityTransaction entityTransaction = TestHelper.getEntityTransaction();
            try {
                entityTransaction.begin();
                persistForeignObjects(technology);
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
            persistForeignObjects(technology); // or relying on current context
        }
    }

    /**
     * <p>Creates a default entity.</p>
     *
     * @return instance with test parameters
     */
    protected final Technology createEntity() {
        return createTechnology();
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
                persistTechnologyForeignObjects(getEntity(), false);
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
     * <p>Persists Technology foreign entities to store. Is called before saving Technology itself.</p>
     *
     * @param technology entity instance
     */
    private static void persistForeignObjects(
        final Technology technology) {

    }
}
