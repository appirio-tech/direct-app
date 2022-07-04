/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.unittests.persistence;

import com.topcoder.catalog.entity.TestHelper;
import junit.framework.TestCase;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>This is a parent for all entities tests. It provides basic functionality such as {@link #persistEntity}, {@link
 * #getEntities}, {@link #merge}.</p>
 * <p>Also it creates {@link javax.persistence.EntityManager} and
 * {@link javax.persistence.EntityTransaction} instances.</p>
 *
 * @author Retunsky
 * @version 1.0
 * @param <T> class which represents entity to test
 */
public abstract class CommonEntityTest<T extends Serializable> extends TestCase {
    /**
     * An entity instance for testing.
     */
    private T entity;

    /**
     * Constructor. Does nothing.
     */
    protected CommonEntityTest() {
    }

    /**
     * Clears persistence context.
     *
     * @throws Exception if something goes wrong
     */
    protected void setUp() throws Exception {
        super.setUp();
        TestHelper.bindIdGenerator();
        final EntityTransaction tx = TestHelper.getEntityTransaction();
        if (tx != null && tx.isActive()) {
            tx.rollback();
        }
        TestHelper.prepareTables();
        entity = createEntity();
    }

    /**
     * finally removes the entity.
     *
     * @throws Exception if something goes wrong
     */
    protected void tearDown() throws Exception {
        final EntityTransaction tx = TestHelper.getEntityTransaction();
        if (tx != null && tx.isActive()) {
            tx.rollback();
        }
        TestHelper.unbindIdGenerator();
        TestHelper.clearTables();
        super.tearDown();
    }

    /**
     * Creates a default entity.
     *
     * @return instance with test parameters
     */
    protected T createEntity() {
        return null;
    }

    /**
     * Persists an entity in the database. Should be called inside a transaction context.
     *
     * @param entity test entity instance
     */
    protected static void persistEntity(Serializable entity) {
        TestHelper.getEntityManager().persist(entity);
    }

    /**
     * Stores the {@link #entity} to database.
     *
     * @param saveForeignObjects indicates whether save the entity only, or all foreign entities as well
     *                           <code>true</code> - save all foreign entities
     *                           <code>false</code> - save only the entity itself
     */
    protected void persistEntity(boolean saveForeignObjects) {
        // do nothing
    }

    /**
     * Retrieves entities from the database by given query and parameters.
     *
     * @param query  a query like 'select e from Entity where a=? and b=?'
     * @param params parameters of the query
     * @return list of entities match the given criteria
     */
    @SuppressWarnings("unchecked")
    protected Set<T> getEntities(String query, Object... params) {
        Query q = TestHelper.getEntityManager().createQuery(query);
        for (int i = 0; i < params.length; i++) {
            q.setParameter(i + 1, params[i]);
        }
        return new HashSet<T>(q.getResultList());
    }

    /**
     * Updates an entity in the database. Performs action within a transaction to guarantee flushing the data to the
     * database.
     *
     * @param entity test entity instance
     */
    protected void merge(T entity) {
        final EntityTransaction entityTransaction = TestHelper.getEntityTransaction();
        final EntityManager manager = TestHelper.getEntityManager();
        try {
            entityTransaction.begin();
            manager.merge(entity);
            entityTransaction.commit();
        } catch (PersistenceException e) {
            entityTransaction.rollback();
            throw e;
        }
    }

    /**
     * Provides a test entity instance.
     *
     * @return entity which were created by {@link #createEntity} method
     */
    public T getEntity() {
        return entity;
    }

    /**
     * <p>Unsets the mandatory field (around it's setter, to verify the ORM setting).</p>
     *
     * @param fieldName field name
     * @param entity    the entity which is the field's owner
     */
    protected void unset(String fieldName, T entity) {
        try {
            Field field = entity.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(entity, null);
        } catch (NoSuchFieldException e) {
            // impossible. make compiler happy
            throw new RuntimeException("Check the test", e);
        } catch (IllegalAccessException e) {
            // impossible. make compiler happy
            throw new RuntimeException("Check the test", e);
        }
    }
}
