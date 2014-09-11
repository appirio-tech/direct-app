/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.unittests.persistence;

import com.topcoder.catalog.entity.CompClient;
import com.topcoder.catalog.entity.Component;
import com.topcoder.catalog.entity.TestHelper;

import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import java.util.Arrays;
import java.util.HashSet;

/**
 * <p>This is a base class for CompClientEntity JPA tests.</p>
 * <p>It provide basic operations for concrete implementation
 * {@link CompClientEntityTest}.</p>
 * <p>It creates an entity instance with default fields values (including foreign entities).</p>
 * <p>Stores all necessary foreign entities to the database to prepare environment for CompClient
 * entity testing.</p>
 * <p>After all test's cases are performed the method <code>tearDown</code> cleans up database
 * and provides tests isolation.</p>
 *
 * @author Retunsky
 * @version 1.0
 */
public abstract class BaseCompClientEntityTest extends CommonEntityTest<CompClient> {
    /**
     * Default CompClient.clientId value.
     */
    public static final Long CLIENTID = 1L;


    /**
     * <p>Constructor. No additional actions to the super constructor.</p>
     */
    protected BaseCompClientEntityTest() {
    }

    /**
     * <p>Create a CompClient entity instance with default constant values defined above.</p>
     *
     * @return a CompClient entity instance
     */
    static CompClient createCompClient() {
        final CompClient client = new CompClient();
        final Component component = BaseComponentEntityTest.createComponent();
        component.setClients(new HashSet<CompClient>(Arrays.asList(client)));
        client.setComponent(component);
        client.setClientId(CLIENTID);
        return client;
    }

    /**
     * <p>Persists CompClient foreign entities to store. Is called before saving CompClient itself.</p>
     *
     * @param compClient          entity instance
     * @param separateTransaction <ul><li><code>true</code> - persist objects in a separate transaction</li>
     *                            <li><code>false</code> - already in a transaction context</li></ul>
     */
    protected static void persistCompClientForeignObjects(
        final CompClient compClient, boolean separateTransaction) {
        if (separateTransaction) { // within its own transaction
            final EntityTransaction entityTransaction = TestHelper.getEntityTransaction();
            try {
                entityTransaction.begin();
                persistForeignObjects(compClient);
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
            persistForeignObjects(compClient); // or relying on current context
        }
    }

    /**
     * <p>Creates a default entity.</p>
     *
     * @return instance with test parameters
     */
    protected final CompClient createEntity() {
        return createCompClient();
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
                persistCompClientForeignObjects(getEntity(), false);
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
     * <p>Persists CompClient foreign entities to store. Is called before saving CompClient itself.</p>
     *
     * @param compClient entity instance
     */
    private static void persistForeignObjects(
        final CompClient compClient) {
        final Component component = compClient.getComponent();
        BaseComponentEntityTest.persistComponentForeignObjects(component, false);
        persistEntity(component);
    }
}
