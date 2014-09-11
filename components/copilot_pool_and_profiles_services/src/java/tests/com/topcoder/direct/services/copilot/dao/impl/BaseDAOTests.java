/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.copilot.dao.impl;

import com.topcoder.direct.services.copilot.TestHelper;
import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.CopilotDAOInitializationException;
import com.topcoder.direct.services.copilot.model.CopilotProfile;
import com.topcoder.direct.services.copilot.model.MockIdentifiableEntity;
import junit.framework.JUnit4TestAdapter;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>Tests <code>{@link BaseDAO}</code> class.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class BaseDAOTests {

    /**
     * <p>Represents logger name used for testing.</p>
     */
    private static final String LOGGER_NAME = "loggerName";

    /**
     * <p>Represents instance of tested class.</p>
     */
    @Autowired
    @Qualifier("baseDAO")
    private BaseDAO instance;

    /**
     * <p>Represents instance of {@link HibernateTemplate} used for testing.</p>
     */
    private HibernateTemplate hibernateTemplate;

    /**
     * <p>Sets instance of {@link SessionFactory} used by this test case.</p>
     *
     * @param sessionFactory the hibernate session factory to set
     */
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    /**
     * <p>Retrieves instance of {@link SessionFactory} used by this test case.</p>
     *
     * @return the hibernate session factory
     */
    public SessionFactory getSessionFactory() {
        return hibernateTemplate != null ? hibernateTemplate.getSessionFactory() : null;
    }

    /**
     * <p>Returns test suite for this class.</p>
     *
     * @return test suite for this class
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(BaseDAOTests.class);
    }

    /**
     * <p>Tests <code>{@link BaseDAO#BaseDAO()}</code> constructor.</p>
     */
    @Test
    public void testCtor() {

        Assert.assertNotNull("Object not instantiated.", instance);
    }

    /**
     * <p>Tests <code>{@link BaseDAO#checkInit()}</code> method.</p>
     */
    @Test
    public void testCheckInit1() {
        BaseDAO baseDAO = new MockBaseDAO();

        baseDAO.setLoggerName(LOGGER_NAME);
        baseDAO.setSessionFactory(getSessionFactory());

        baseDAO.checkInit();

        Assert.assertNotNull("Property log should be not null.", baseDAO.getLog());
    }

    /**
     * <p>Tests <code>{@link BaseDAO#checkInit()}</code> method.</p>
     */
    @Test
    public void testCheckInit2() {
        BaseDAO baseDAO = new MockBaseDAO();

        baseDAO.setLoggerName(null);
        baseDAO.setSessionFactory(getSessionFactory());

        baseDAO.checkInit();
    }

    /**
     * <p>Tests <code>{@link BaseDAO#checkInit()}</code> method.</p>
     *
     * <p>{@link CopilotDAOInitializationException} is expected</p>
     */
    @Test(expected = CopilotDAOInitializationException.class)
    public void testCheckInitFailure1() {
        BaseDAO baseDAO = new MockBaseDAO();

        baseDAO.setLoggerName(" ");
        baseDAO.setSessionFactory(getSessionFactory());

        baseDAO.checkInit();
    }

    /**
     * <p>Tests <code>{@link BaseDAO#checkInit()}</code> method.</p>
     *
     * <p>{@link CopilotDAOInitializationException} is expected</p>
     */
    @Test(expected = CopilotDAOInitializationException.class)
    public void testCheckInitFailure2() {
        BaseDAO baseDAO = new MockBaseDAO();

        baseDAO.setLoggerName(LOGGER_NAME);
        baseDAO.setSessionFactory(null);

        baseDAO.checkInit();
    }

    /**
     * <p>Tests <code>{@link BaseDAO#setLoggerName(String)}</code> method.</p>
     *
     * @throws Exception if any error occurs
     */
    @Test
    public void testSetLoggerName() throws Exception {
        BaseDAO baseDAO = new MockBaseDAO();

        baseDAO.setLoggerName(LOGGER_NAME);
        baseDAO.setSessionFactory(getSessionFactory());

        baseDAO.checkInit();

        Assert.assertEquals("Invalid loggerName field value", LOGGER_NAME,
                TestHelper.getFieldValue(BaseDAO.class, baseDAO, "loggerName"));
        Assert.assertNotNull("Property log should be not null.", baseDAO.getLog());
    }

    /**
     * <p>Tests <code>{@link BaseDAO#getLog()}</code> method.</p>
     */
    @Test
    public void testGetLog() {
        BaseDAO baseDAO = new MockBaseDAO();
        Assert.assertNull("Property log should be null.", baseDAO.getLog());

        baseDAO.setLoggerName(LOGGER_NAME);
        baseDAO.setSessionFactory(getSessionFactory());

        baseDAO.checkInit();

        Assert.assertNotNull("Property log should be not null.", baseDAO.getLog());
    }

    /**
     * <p>Tests <code>getSessionFactory()</code> property, tests both <code>{@link
     * BaseDAO#setSessionFactory(SessionFactory)}</code>
     * and <code>{@link BaseDAO#getSessionFactory()()}</code> methods.</p>
     */
    @Test
    public void testSessionFactoryProperty() {
        BaseDAO baseDAO = new MockBaseDAO();
        Assert.assertNull("Property getSessionFactory() should be null.", baseDAO.getSessionFactory());

        baseDAO.setSessionFactory(getSessionFactory());

        Assert.assertEquals("Invalid getSessionFactory() property value.", getSessionFactory(),
                baseDAO.getSessionFactory());
    }

    /**
     * <p>Tests <code>{@link BaseDAO#getSession()}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void testGetSession() throws CopilotDAOException {

        Assert.assertNotNull("Method getSession returned null.", instance.getSession());
    }

    /**
     * <p>Tests <code>{@link BaseDAO#getSession()}</code> method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testGetSessionFailure() throws CopilotDAOException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("invalidApplicationContext.xml");
        BaseDAO baseDAO = new MockBaseDAO();
        baseDAO.setSessionFactory((SessionFactory) applicationContext.getBean("sessionFactory"));
        baseDAO.getSession();
    }

    /**
     * <p>Tests <code>{@link BaseDAO#getAllEntities(Class)}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void testGetAllEntities1() throws CopilotDAOException {
        List<CopilotProfile> result = instance.getAllEntities(CopilotProfile.class);

        Assert.assertNotNull("Method getAllEntities returned null.", result);
        Assert.assertEquals("0 result were expected.", 0, result.size());
    }

    /**
     * <p>Tests <code>{@link BaseDAO#getAllEntities(Class)}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void testGetAllEntities2() throws CopilotDAOException {

        CopilotProfile copilotProfile = TestHelper.createCopilotProfile();
        hibernateTemplate.save(copilotProfile.getStatus());
        hibernateTemplate.save(copilotProfile);

        List<CopilotProfile> result = instance.getAllEntities(CopilotProfile.class);

        Assert.assertEquals("1 result was expected.", 1, result.size());

        TestHelper.assertCopilotProfile(copilotProfile, result.get(0));
    }

    /**
     * <p>Tests <code>{@link BaseDAO#getAllEntities(Class)}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void testGetAllEntities3() throws CopilotDAOException {

        CopilotProfile copilotProfile1 = TestHelper.createCopilotProfile();
        hibernateTemplate.save(copilotProfile1.getStatus());
        hibernateTemplate.save(copilotProfile1);

        CopilotProfile copilotProfile2 = TestHelper.createCopilotProfile();
        hibernateTemplate.save(copilotProfile2.getStatus());
        hibernateTemplate.save(copilotProfile2);

        List<CopilotProfile> result = instance.getAllEntities(CopilotProfile.class);

        Assert.assertEquals("2 result was expected.", 2, result.size());

        TestHelper.assertCopilotProfile(copilotProfile1, result.get(0));
        TestHelper.assertCopilotProfile(copilotProfile2, result.get(1));
    }

    /**
     * <p>Tests <code>{@link BaseDAO#getAllEntities(Class)}</code> method.</p>
     *
     * <p>{@link IllegalArgumentException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetAllEntitiesNull() throws CopilotDAOException {

        instance.getAllEntities(null);
    }

    /**
     * <p>Tests <code>{@link BaseDAO#getAllEntities(Class)}</code> method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testGetAllEntitiesFailure1() throws CopilotDAOException {
        instance.getAllEntities(MockIdentifiableEntity.class);
    }

    /**
     * <p>Tests <code>{@link BaseDAO#getAllEntities(Class)}</code> method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testGetAllEntitiesFailure2() throws CopilotDAOException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("invalidApplicationContext.xml");
        MockBaseDAO baseDAO = new MockBaseDAO();
        baseDAO.setSessionFactory((SessionFactory) applicationContext.getBean("sessionFactory"));

        baseDAO.getAllEntities(CopilotProfile.class);
    }
}
