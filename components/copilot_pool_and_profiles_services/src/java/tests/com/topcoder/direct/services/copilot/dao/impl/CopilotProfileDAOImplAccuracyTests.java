/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.copilot.dao.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.model.CopilotProfile;
import junit.framework.JUnit4TestAdapter;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Accuracy tests for <code>CopilotProfileDAOImpl</code>.
 * 
 * @author morehappiness
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class CopilotProfileDAOImplAccuracyTests {
    /**
     * Instance used for tests.
     */
    @Autowired
    private CopilotProfileDAOImpl instance;

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
        return new JUnit4TestAdapter(CopilotProfileDAOImplAccuracyTests.class);
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
     * Tests for getCopilotProfile method.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void test_getCopilotProfile() throws CopilotDAOException {
        CopilotProfile copilotProfile1 = AccuracyTestHelper.createCopilotProfile();
        copilotProfile1.setUserId(1L);
        hibernateTemplate.save(copilotProfile1.getStatus());
        hibernateTemplate.save(copilotProfile1);
        CopilotProfile copilotProfile2 = AccuracyTestHelper.createCopilotProfile();
        copilotProfile2.setUserId(2L);
        hibernateTemplate.save(copilotProfile2.getStatus());
        hibernateTemplate.save(copilotProfile2);

        CopilotProfile result = instance.getCopilotProfile(copilotProfile1.getUserId());

        assertNotNull("Should be the same", result);
    }
}
