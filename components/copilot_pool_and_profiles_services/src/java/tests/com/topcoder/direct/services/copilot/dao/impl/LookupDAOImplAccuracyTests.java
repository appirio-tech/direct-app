/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.copilot.dao.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


import com.topcoder.direct.services.copilot.dao.CopilotDAOException;

import junit.framework.JUnit4TestAdapter;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Accuracy tests for <code>LookupDAOImpl</code>.
 * 
 * @author morehappiness
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class LookupDAOImplAccuracyTests {
    /**
     * Instance used for tests.
     */
    @Autowired
    @Qualifier("lookupDAO")
    private LookupDAOImpl instance;

    /**
     * HibernateTemplate used for tests.
     */
    private HibernateTemplate hibernateTemplate;

    /**
     * Sets instance of SessionFactory.
     * 
     * @param sessionFactory
     *            the session factory
     */
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    /**
     * Returns test suite.
     * 
     * @return test suite for this class
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(LookupDAOImplAccuracyTests.class);
    }

    /**
     * Tests for the constructor.
     */
    @Test
    public void test_ctor() {
        assertNotNull("Should not be null", instance);
        assertTrue("Should be equal", BaseDAO.class.isInstance(instance));
    }

    /**
     * Tests for getAllCopilotProfileStatuses method.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void test_getAllCopilotProfileStatuses() throws CopilotDAOException {
        assertNotNull("Should be the same", instance.getAllCopilotProfileStatuses().size());
    }

    /**
     * Tests for getAllCopilotProjectStatuses method.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void test_getAllCopilotProjectStatuses() throws CopilotDAOException {
        assertNotNull("Should be the same", instance.getAllCopilotProjectStatuses().size());
    }

    /**
     * Tests for getAllCopilotTypes method.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void test_getAllCopilotTypes() throws CopilotDAOException {
        assertNotNull("Should be the same", instance.getAllCopilotTypes().size());
    }
}
