/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.service.stresstests;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 * <p>Mockup of <code>EntityManager</code> which always throws <code>javax.persistence.PersistenceException</code>
 * on all operations.</p>
 * <p>The purpose is to test persistence layer exceptions wrapping.</p>
 *
 * @author Retunsky
 * @version 1.0
 */
public class FailTestEntityManager implements EntityManager {
    /**
     * <p>Emulate persistence failure.</p>
     *
     * @param object ignored
     * @throws PersistenceException always
     */
    public void persist(Object object) {
        throw new PersistenceException("Exception handling test");
    }

    /**
     * <p>Emulate persistence failure.</p>
     *
     * @param <T> ignored
     * @param t ignored
     * @throws PersistenceException always
     * @return nothing
     */
    public <T> T merge(T t) {
        throw new PersistenceException("Exception handling test");
    }

    /**
     * <p>Emulate persistence failure.</p>
     *
     * @param object ignored
     * @throws PersistenceException always
     */
    public void remove(Object object) {
        throw new PersistenceException("Exception handling test");
    }

    /**
     * <p>Emulate persistence failure.</p>
     *
     * @param <T>    ignored
     * @param object ignored
     * @param aClass ignored
     * @throws PersistenceException always
     * @return nothing
     */
    public <T> T find(Class<T> aClass, Object object) {
        throw new PersistenceException("Exception handling test");
    }

    /**
     * <p>Emulate persistence failure.</p>
     *
     * @param <T>    ignored
     * @param object ignored
     * @param aClass ignored
     * @throws PersistenceException always
     * @return nothing
     */
    public <T> T getReference(Class<T> aClass, Object object) {
        throw new PersistenceException("Exception handling test");
    }

    /**
     * <p>Emulate persistence failure.</p>
     *
     * @throws PersistenceException always
     */
    public void flush() {
        throw new PersistenceException("Exception handling test");
    }

    /**
     * <p>Emulate persistence failure.</p>
     *
     * @param flushModeType ignored
     * @throws PersistenceException always
     */
    public void setFlushMode(FlushModeType flushModeType) {
        throw new PersistenceException("Exception handling test");
    }

    /**
     * <p>Emulate persistence failure.</p>
     *
     * @throws PersistenceException always
     * @return nothing
     */
    public FlushModeType getFlushMode() {
        throw new PersistenceException("Exception handling test");
    }

    /**
     * <p>Emulate persistence failure.</p>
     *
     * @param object       ignored
     * @param lockModeType ignored
     * @throws PersistenceException always
     */
    public void lock(Object object, LockModeType lockModeType) {
        throw new PersistenceException("Exception handling test");
    }

    /**
     * <p>Emulate persistence failure.</p>
     *
     * @param object ignored
     * @throws PersistenceException always
     */
    public void refresh(Object object) {
        throw new PersistenceException("Exception handling test");
    }

    /**
     * <p>Emulate persistence failure.</p>
     *
     * @throws PersistenceException always
     */
    public void clear() {
        throw new PersistenceException("Exception handling test");
    }

    /**
     * <p>Emulate persistence failure.</p>
     *
     * @param object ignored
     * @throws PersistenceException always
     * @return nothing
     */
    public boolean contains(Object object) {
        throw new PersistenceException("Exception handling test");
    }

    /**
     * <p>Emulate persistence failure.</p>
     *
     * @param string ignored
     * @throws PersistenceException always
     * @return nothing
     */
    public Query createQuery(String string) {
        throw new PersistenceException("Exception handling test");
    }

    /**
     * <p>Emulate persistence failure.</p>
     *
     * @param string ignored
     * @throws PersistenceException always
     * @return nothing
     */
    public Query createNamedQuery(String string) {
        throw new PersistenceException("Exception handling test");
    }

    /**
     * <p>Emulate persistence failure.</p>
     *
     * @param string ignored
     * @throws PersistenceException always
     * @return nothing
     */
    public Query createNativeQuery(String string) {
        throw new PersistenceException("Exception handling test");
    }

    /**
     * <p>Emulate persistence failure.</p>
     *
     * @param string ignored
     * @param aClass ignored
     * @throws PersistenceException always
     * @return nothing
     */
    public Query createNativeQuery(String string, Class aClass) {
        throw new PersistenceException("Exception handling test");
    }

    /**
     * <p>Emulate persistence failure.</p>
     *
     * @param string  ignored
     * @param string1 ignored
     * @throws PersistenceException always
     * @return nothing
     */
    public Query createNativeQuery(String string, String string1) {
        throw new PersistenceException("Exception handling test");
    }

    /**
     * <p>Emulate persistence failure.</p>
     *
     * @throws PersistenceException always
     */
    public void joinTransaction() {
        throw new PersistenceException("Exception handling test");
    }

    /**
     * <p>Emulate persistence failure.</p>
     *
     * @throws PersistenceException always
     * @return nothing
     */
    public Object getDelegate() {
        throw new PersistenceException("Exception handling test");
    }

    /**
     * <p>Emulate persistence failure.</p>
     *
     * @throws PersistenceException always
     */
    public void close() {
        throw new PersistenceException("Exception handling test");
    }

    /**
     * <p>Emulate persistence failure.</p>
     *
     * @throws PersistenceException always
     * @return nothing
     */
    public boolean isOpen() {
        throw new PersistenceException("Exception handling test");
    }

    /**
     * <p>Emulate persistence failure.</p>
     *
     * @throws PersistenceException always
     * @return nothing
     */
    public EntityTransaction getTransaction() {
        throw new PersistenceException("Exception handling test");
    }
}
