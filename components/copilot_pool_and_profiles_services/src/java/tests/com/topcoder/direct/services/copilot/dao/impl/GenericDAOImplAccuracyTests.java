/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.copilot.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.model.CopilotProfile;
import junit.framework.JUnit4TestAdapter;
import junit.framework.TestCase;

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

import java.util.Date;
import java.util.List;

/**
 * Accuracy tests <code>GenericDAOImpl</code>.
 * 
 * @author morehappiness
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class GenericDAOImplAccuracyTests extends TestCase {
    /**
     * Instance used for tests.
     */
    @Autowired
    @Qualifier("genericDAO")
    private GenericDAOImpl<CopilotProfile> instance;

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
        return new JUnit4TestAdapter(GenericDAOImplAccuracyTests.class);
    }

    /**
     * Tests for the constructor.
     */
    @Test
    public void test_ctor() {
        assertNotNull("Should not be null", instance);
    }

    /**
     * Tests for create method.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void test_create() throws Exception {
        CopilotProfile copilotProfile = AccuracyTestHelper.createCopilotProfile();
        hibernateTemplate.save(copilotProfile.getStatus());

        instance.create(copilotProfile);

        List<CopilotProfile> result = hibernateTemplate
            .find("from CopilotProfile where id = ?", copilotProfile.getId());

        assertEquals("Should be the same", 1, result.size());
    }

    /**
     * Tests for update method.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void test_update() throws Exception {
        CopilotProfile copilotProfile = AccuracyTestHelper.createCopilotProfile();
        hibernateTemplate.save(copilotProfile.getStatus());
        hibernateTemplate.save(copilotProfile);

        copilotProfile.setActivationDate(new Date());
        copilotProfile.setReliability(50F);
        copilotProfile.setShowCopilotEarnings(false);
        copilotProfile.setSuspensionCount(22);
        copilotProfile.setUserId(30L);

        instance.update(copilotProfile);

        List<CopilotProfile> result = hibernateTemplate
            .find("from CopilotProfile where id = ?", copilotProfile.getId());

        assertEquals("Should be the same", 1, result.size());
    }

    /**
     * Tests for create method.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void test_delete() throws CopilotDAOException {
        CopilotProfile copilotProfile1 = AccuracyTestHelper.createCopilotProfile();
        hibernateTemplate.save(copilotProfile1.getStatus());
        hibernateTemplate.save(copilotProfile1);
        CopilotProfile copilotProfile2 = AccuracyTestHelper.createCopilotProfile();
        hibernateTemplate.save(copilotProfile2.getStatus());
        hibernateTemplate.save(copilotProfile2);

        instance.delete(copilotProfile1.getId());

        List<CopilotProfile> result = hibernateTemplate.find("from CopilotProfile where id = ?", copilotProfile1
            .getId());

        assertEquals("Should be the same", 0, result.size());
    }

    /**
     * Tests for create method.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void test_retrieve() throws CopilotDAOException {
        CopilotProfile copilotProfile1 = AccuracyTestHelper.createCopilotProfile();
        hibernateTemplate.save(copilotProfile1.getStatus());
        hibernateTemplate.save(copilotProfile1);
        CopilotProfile copilotProfile2 = AccuracyTestHelper.createCopilotProfile();
        hibernateTemplate.save(copilotProfile2.getStatus());
        hibernateTemplate.save(copilotProfile2);

        CopilotProfile result = instance.retrieve(copilotProfile1.getId());
        assertNotNull("Should be the same", result);
    }

    /**
     * Tests for create method.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void test_retrieveAll() throws CopilotDAOException {
        CopilotProfile copilotProfile1 = AccuracyTestHelper.createCopilotProfile();
        hibernateTemplate.save(copilotProfile1.getStatus());
        hibernateTemplate.save(copilotProfile1);
        CopilotProfile copilotProfile2 = AccuracyTestHelper.createCopilotProfile();
        hibernateTemplate.save(copilotProfile2.getStatus());
        hibernateTemplate.save(copilotProfile2);

        List<CopilotProfile> result = instance.retrieveAll();

        assertEquals("Should be the same", 2, result.size());
    }
}

/**
 * This is an implementation of GenericDAOImpl.
 * 
 * @author morehappiness
 * @version 1.0
 */
class ConcreteGenericDAO extends GenericDAOImpl<CopilotProfile> {
}