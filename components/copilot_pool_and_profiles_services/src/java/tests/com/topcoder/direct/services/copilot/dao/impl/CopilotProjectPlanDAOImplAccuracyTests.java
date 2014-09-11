/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.copilot.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.model.CopilotProfile;
import com.topcoder.direct.services.copilot.model.CopilotProject;
import com.topcoder.direct.services.copilot.model.CopilotProjectPlan;

import junit.framework.JUnit4TestAdapter;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Accuracy tests for <code>CopilotProjectPlanDAOImpl</code>.
 * 
 * @author morehappiness
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class CopilotProjectPlanDAOImplAccuracyTests {
    /**
     * Instance used for tests.
     */
    @Autowired
    private CopilotProjectPlanDAOImpl instance;

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
        return new JUnit4TestAdapter(CopilotProjectPlanDAOImplAccuracyTests.class);
    }

    /**
     * Tests for the constructor.
     */
    @Test
    public void test_ctor() {
        assertNotNull("Should not be null", instance);
        assertTrue("Should be equal", GenericDAOImpl.class.isInstance(instance));
    }

    /**
     * Tests for getCopilotProjects method.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void test_getCopilotProjects() throws CopilotDAOException {
        assertNull("Should be the same", instance.getCopilotProjectPlan(1L));
    }
}
