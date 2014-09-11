/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.copilot.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.model.CopilotProfile;
import com.topcoder.direct.services.copilot.model.CopilotProject;

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
 * Accuracy tests for <code>CopilotProjectDAOImpl</code>.
 * 
 * @author morehappiness
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class CopilotProjectDAOImplAccuracyTests {
    /**
     * Instance used for tests.
     */
    @Autowired
    private CopilotProjectDAOImpl instance;

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
        return new JUnit4TestAdapter(CopilotProjectDAOImplAccuracyTests.class);
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
        CopilotProfile copilotProfile1 = AccuracyTestHelper.createCopilotProfile();
        CopilotProject copilotProject1 = AccuracyTestHelper.createCopilotProject();

        AccuracyTestHelper.persistCopilotProjectDependencies(hibernateTemplate, copilotProfile1, copilotProject1);
        hibernateTemplate.save(copilotProject1);

        CopilotProfile copilotProfile2 = AccuracyTestHelper.createCopilotProfile();
        CopilotProject copilotProject2 = AccuracyTestHelper.createCopilotProject();

        AccuracyTestHelper.persistCopilotProjectDependencies(hibernateTemplate, copilotProfile2, copilotProject2);
        hibernateTemplate.save(copilotProject2);

        List<CopilotProject> result = instance.getCopilotProjects(copilotProject1.getCopilotProfileId());

        assertEquals("Should be the same", 1, result.size());
    }
}
