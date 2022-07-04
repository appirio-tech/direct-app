/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.unittests.persistence;

import com.topcoder.catalog.entity.CompUser;
import com.topcoder.catalog.entity.Component;
import com.topcoder.catalog.entity.TestHelper;

import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import java.util.Arrays;
import java.util.HashSet;

/**
 * <p>This is a base class for CompUserEntity JPA tests.</p>
 * <p>It provide basic operations for concrete implementation
 * {@link CompUserEntityTest}.</p>
 * <p>It creates an entity instance with default fields values (including foreign entities).</p>
 * <p>Stores all necessary foreign entities to the database to prepare environment for CompUser
 * entity testing.</p>
 * <p>After all test's cases are performed the method <code>tearDown</code> cleans up database
 * and provides tests isolation.</p>
 *
 * @author Retunsky
 * @version 1.0
 */
public abstract class BaseCompUserEntityTest extends CommonEntityTest<CompUser> {
    /**
     * Default CompUser.userId value.
     */
    public static final Long USERID = 1L;


    /**
     * <p>Constructor. No additional actions to the super constructor.</p>
     */
    protected BaseCompUserEntityTest() {
    }

    /**
     * <p>Create a CompUser entity instance with default constant values defined above.</p>
     *
     * @return a CompUser entity instance
     */
    static CompUser createCompUser() {
        final CompUser compUser = new CompUser();
        final Component component = BaseComponentEntityTest.createComponent();
        component.setUsers(new HashSet<CompUser>(Arrays.asList(compUser)));
        compUser.setComponent(component);
        compUser.setUserId(USERID);
        return compUser;
    }

    /**
     * <p>Persists CompUser foreign entities to store. Is called before saving CompUser itself.</p>
     *
     * @param compUser            entity instance
     * @param separateTransaction <ul><li><code>true</code> - persist objects in a separate transaction</li>
     *                            <li><code>false</code> - already in a transaction context</li></ul>
     */
    protected static void persistCompUserForeignObjects(
        final CompUser compUser, boolean separateTransaction) {
        if (separateTransaction) { // within its own transaction
            final EntityTransaction entityTransaction = TestHelper.getEntityTransaction();
            try {
                entityTransaction.begin();
                persistForeignObjects(compUser);
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
            persistForeignObjects(compUser); // or relying on current context
        }
    }

    /**
     * <p>Creates a default entity.</p>
     *
     * @return instance with test parameters
     */
    protected final CompUser createEntity() {
        return createCompUser();
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
                persistCompUserForeignObjects(getEntity(), false);
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
     * <p>Persists CompUser foreign entities to store. Is called before saving CompUser itself.</p>
     *
     * @param compUser entity instance
     */
    private static void persistForeignObjects(
        final CompUser compUser) {
        final Component component = compUser.getComponent();
        BaseComponentEntityTest.persistComponentForeignObjects(component, false);
        persistEntity(component);
    }
}
