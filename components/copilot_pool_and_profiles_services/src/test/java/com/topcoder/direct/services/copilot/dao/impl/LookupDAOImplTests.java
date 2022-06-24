/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.copilot.dao.impl;

import com.topcoder.direct.services.copilot.TestHelper;
import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.model.CopilotProfileStatus;
import com.topcoder.direct.services.copilot.model.CopilotProjectStatus;
import com.topcoder.direct.services.copilot.model.CopilotType;
import junit.framework.JUnit4TestAdapter;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>Tests <code>{@link LookupDAOImpl}</code> class.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class LookupDAOImplTests {

    /**
     * <p>Represents instance of tested class.</p>
     */
    @Autowired
    private LookupDAOImpl instance;

    /**
     * <p>Represents instance of {@link org.springframework.orm.hibernate3.HibernateTemplate} used for testing.</p>
     */
    private HibernateTemplate hibernateTemplate;

    /**
     * <p>Sets instance of {@link org.hibernate.SessionFactory} used by this test case.</p>
     *
     * @param sessionFactory the session factory to set
     */
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    /**
     * <p>Returns test suite for this class.</p>
     *
     * @return test suite for this class
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(LookupDAOImplTests.class);
    }

    /**
     * <p>Tests <code>{@link LookupDAOImpl#LookupDAOImpl()}</code> constructor.</p>
     */
    @Test
    public void testCtor() {
        Assert.assertNotNull("Object not instantiated.", instance);
    }

    /**
     * <p>Tests <code>{@link LookupDAOImpl#getAllCopilotProfileStatuses()}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void testGetAllCopilotProfileStatuses1() throws CopilotDAOException {

        Assert.assertEquals("No entities were expected.", 0, instance.getAllCopilotProfileStatuses().size());
    }

    /**
     * <p>Tests <code>{@link LookupDAOImpl#getAllCopilotProfileStatuses()}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void testGetAllCopilotProfileStatuses2() throws CopilotDAOException {

        CopilotProfileStatus copilotProfileStatus1 = TestHelper.createCopilotProfileStatus();
        CopilotProfileStatus copilotProfileStatus2 = TestHelper.createCopilotProfileStatus();

        copilotProfileStatus1.setName("status 1");
        copilotProfileStatus1.setName("status 2");

        hibernateTemplate.save(copilotProfileStatus1);
        hibernateTemplate.save(copilotProfileStatus2);

        List<CopilotProfileStatus> result = instance.getAllCopilotProfileStatuses();

        Assert.assertEquals("Two entities were expected.", 2, result.size());
        Assert.assertEquals("Invalid entity id.", copilotProfileStatus1.getId(), result.get(0).getId());
        Assert.assertEquals("Invalid entity name.", copilotProfileStatus1.getName(), result.get(0).getName());

        Assert.assertEquals("Invalid entity id.", copilotProfileStatus2.getId(), result.get(1).getId());
        Assert.assertEquals("Invalid entity name.", copilotProfileStatus2.getName(), result.get(1).getName());
    }

    /**
     * <p>Tests <code>{@link LookupDAOImpl#getAllCopilotProfileStatuses()}</code> method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testGetAllCopilotProfileStatusesFailure() throws CopilotDAOException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("invalidApplicationContext.xml");
        LookupDAOImpl lookupDAO = new LookupDAOImpl();
        lookupDAO.setSessionFactory((SessionFactory) applicationContext.getBean("sessionFactory"));

        lookupDAO.getAllCopilotProfileStatuses();
    }

    /**
     * <p>Tests <code>{@link LookupDAOImpl#getAllCopilotProjectStatuses()}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void testGetAllCopilotProjectStatuses1() throws CopilotDAOException {

        Assert.assertEquals("No entities were expected.", 0, instance.getAllCopilotProjectStatuses().size());
    }

    /**
     * <p>Tests <code>{@link LookupDAOImpl#getAllCopilotProjectStatuses()}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void testGetAllCopilotProjectStatuses2() throws CopilotDAOException {

        CopilotProjectStatus copilotProjectStatus1 = TestHelper.createCopilotProjectStatus();
        CopilotProjectStatus copilotProjectStatus2 = TestHelper.createCopilotProjectStatus();

        copilotProjectStatus1.setName("status 1");
        copilotProjectStatus1.setName("status 2");

        hibernateTemplate.save(copilotProjectStatus1);
        hibernateTemplate.save(copilotProjectStatus2);

        List<CopilotProjectStatus> result = instance.getAllCopilotProjectStatuses();

        Assert.assertEquals("Two entities were expected.", 2, result.size());
        Assert.assertEquals("Invalid entity id.", copilotProjectStatus1.getId(), result.get(0).getId());
        Assert.assertEquals("Invalid entity name.", copilotProjectStatus1.getName(), result.get(0).getName());

        Assert.assertEquals("Invalid entity id.", copilotProjectStatus2.getId(), result.get(1).getId());
        Assert.assertEquals("Invalid entity name.", copilotProjectStatus2.getName(), result.get(1).getName());
    }

    /**
     * <p>Tests <code>{@link LookupDAOImpl#getAllCopilotProjectStatuses()}</code> method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testGetAllCopilotProjectStatusesFailure() throws CopilotDAOException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("invalidApplicationContext.xml");
        LookupDAOImpl lookupDAO = new LookupDAOImpl();
        lookupDAO.setSessionFactory((SessionFactory) applicationContext.getBean("sessionFactory"));

        lookupDAO.getAllCopilotProjectStatuses();
    }

    /**
     * <p>Tests <code>{@link LookupDAOImpl#getAllCopilotTypes()}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void testGetAllCopilotTypes1() throws CopilotDAOException {

        Assert.assertEquals("No entities were expected.", 0, instance.getAllCopilotTypes().size());
    }

    /**
     * <p>Tests <code>{@link LookupDAOImpl#getAllCopilotTypes()}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void testGetAllCopilotTypes2() throws CopilotDAOException {

        CopilotType copilotType1 = TestHelper.createCopilotType();
        CopilotType copilotType2 = TestHelper.createCopilotType();

        copilotType1.setName("type 1");
        copilotType1.setName("type 2");

        hibernateTemplate.save(copilotType1);
        hibernateTemplate.save(copilotType2);

        List<CopilotType> result = instance.getAllCopilotTypes();

        Assert.assertEquals("Two entities were expected.", 2, result.size());
        Assert.assertEquals("Invalid entity id.", copilotType1.getId(), result.get(0).getId());
        Assert.assertEquals("Invalid entity name.", copilotType1.getName(), result.get(0).getName());

        Assert.assertEquals("Invalid entity id.", copilotType2.getId(), result.get(1).getId());
        Assert.assertEquals("Invalid entity name.", copilotType2.getName(), result.get(1).getName());
    }

    /**
     * <p>Tests <code>{@link LookupDAOImpl#getAllCopilotTypes()}</code> method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testGetAllCopilotTypesFailure() throws CopilotDAOException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("invalidApplicationContext.xml");
        LookupDAOImpl lookupDAO = new LookupDAOImpl();
        lookupDAO.setSessionFactory((SessionFactory) applicationContext.getBean("sessionFactory"));

        lookupDAO.getAllCopilotTypes();
    }
}
