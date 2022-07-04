/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.dao.impl;


import org.hibernate.SessionFactory;

import com.topcoder.direct.services.copilot.dao.impl.BaseDAO;

import junit.framework.TestCase;

/**
 * Accuracy tests for <code>BaseDAO</code>.
 * 
 * @author morehappiness
 * @version 1.0
 */
public class BaseDAOAccuracyTests extends TestCase {
    /**
     * Instance used for tests.
     */
    private BaseDAO instance;

    /**
     * Sets up the environment.
     * 
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        instance = new BaseDAO() {
        };
    }

    /**
     * Clears down the environment.
     * 
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
    }

    /**
     * Tests for the constructor.
     */
    public void test_ctor() {
        assertNotNull("Should not be null", instance);
    }

    /**
     * Tests for the sessionFactory field.
     */
    public void test_sessionFactory() {
        SessionFactory sessionFactory = null;
        instance.setSessionFactory(sessionFactory);
        assertEquals("Should be the same", sessionFactory, instance.getSessionFactory());
    }
}
