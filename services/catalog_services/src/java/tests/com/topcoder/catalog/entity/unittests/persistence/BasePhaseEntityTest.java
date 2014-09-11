/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.unittests.persistence;

import com.topcoder.catalog.entity.Phase;
import com.topcoder.catalog.entity.TestHelper;

import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

/**
 * <p>This is a base class for PhaseEntity JPA tests.</p>
 * <p>It provide basic operations for concrete implementation
 * {@link PhaseEntityTest}.</p>
 * <p>It creates an entity instance with default fields values (including foreign entities).</p>
 * <p>Stores all necessary foreign entities to the database to prepare environment for Phase
 * entity testing.</p>
 * <p>After all test's cases are performed the method <code>tearDown</code> cleans up database
 * and provides tests isolation.</p>
 *
 * @author Retunsky
 * @version 1.0
 */
public abstract class BasePhaseEntityTest extends CommonEntityTest<Phase> {
    /**
     * Default Phase.description value.
     */
    public static final String DESCRIPTION = "Submission";


    /**
     * <p>Constructor. No additional actions to the super constructor.</p>
     */
    protected BasePhaseEntityTest() {
    }

    /**
     * <p>Create a Phase entity instance with default constant values defined above.</p>
     *
     * @return a Phase entity instance
     */
    static Phase createPhase() {
        final Phase phase = new Phase();
        phase.setDescription(DESCRIPTION);

        return phase;
    }

    /**
     * <p>Persists Phase foreign entities to store. Is called before saving Phase itself.</p>
     *
     * @param phase               entity instance
     * @param separateTransaction <ul><li><code>true</code> - persist objects in a separate transaction</li>
     *                            <li><code>false</code> - already in a transaction context</li></ul>
     */
    protected static void persistPhaseForeignObjects(
        final Phase phase, boolean separateTransaction) {
        if (separateTransaction) { // within its own transaction
            final EntityTransaction entityTransaction = TestHelper.getEntityTransaction();
            try {
                entityTransaction.begin();
                persistForeignObjects(phase);
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
            persistForeignObjects(phase); // or relying on current context
        }
    }

    /**
     * <p>Creates a default entity.</p>
     *
     * @return instance with test parameters
     */
    protected final Phase createEntity() {
        return createPhase();
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
                persistPhaseForeignObjects(getEntity(), false);
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
     * <p>Persists Phase foreign entities to store. Is called before saving Phase itself.</p>
     *
     * @param phase entity instance
     */
    private static void persistForeignObjects(
        final Phase phase) {

    }
}
