/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.copilot.dao.impl;

import com.topcoder.direct.services.copilot.TestHelper;
import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.EntityNotFoundException;
import com.topcoder.direct.services.copilot.model.CopilotProfile;
import com.topcoder.direct.services.copilot.model.CopilotProfileInfo;
import com.topcoder.direct.services.copilot.model.CopilotProfileInfoType;
import junit.framework.JUnit4TestAdapter;
import org.hibernate.Session;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * <p>Tests <code>{@link CopilotProfileDAOImpl}</code> class.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class CopilotProfileDAOImplTests {

    /**
     * <p>Represents instance of tested class.</p>
     */
    @Autowired
    private CopilotProfileDAOImpl instance;

    /**
     * <p>Represents instance of {@link HibernateTemplate} used for testing.</p>
     */
    private HibernateTemplate hibernateTemplate;

    /**
     * <p>Sets instance of {@link SessionFactory} used by this test case.</p>
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
        return new JUnit4TestAdapter(CopilotProfileDAOImplTests.class);
    }

    /**
     * <p>Tests <code>{@link CopilotProfileDAOImpl#CopilotProfileDAOImpl()}</code> constructor.</p>
     */
    @Test
    public void testCtor() {
        Assert.assertNotNull("Object not instantiated.", instance);
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProfileDAOImpl#create(com.topcoder.direct.services.copilot.model.IdentifiableEntity)}</code>
     * method.</p>
     *
     * @throws com.topcoder.direct.services.copilot.dao.CopilotDAOException
     *          if any error occurs
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testCreate1() throws CopilotDAOException {

        CopilotProfile copilotProfile = TestHelper.createCopilotProfile();
        hibernateTemplate.save(copilotProfile.getStatus());

        instance.create(copilotProfile);

        List<CopilotProfile> result = (List<CopilotProfile>) hibernateTemplate.find("from CopilotProfile where id = ?",
                copilotProfile.getId());

        TestHelper.assertCopilotProfile(copilotProfile, result.get(0));
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProfileDAOImpl#create(com.topcoder.direct.services.copilot.model.IdentifiableEntity)}</code>
     * method.</p>
     *
     * @throws com.topcoder.direct.services.copilot.dao.CopilotDAOException
     *          if any error occurs
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testCreate2() throws CopilotDAOException {

        CopilotProfile copilotProfile = TestHelper.createCopilotProfile();
        hibernateTemplate.save(copilotProfile.getStatus());

        CopilotProfileInfoType copilotProfileInfoType = TestHelper.createCopilotProfileInfoType();
        hibernateTemplate.save(copilotProfileInfoType);

        CopilotProfileInfo copilotProfileInfo = TestHelper.createCopilotProfileInfo();
        copilotProfileInfo.setInfoType(copilotProfileInfoType);
        copilotProfile.getProfileInfos().add(copilotProfileInfo);

        instance.create(copilotProfile);

        List<CopilotProfile> result = (List<CopilotProfile>) hibernateTemplate.find("from CopilotProfile where id = ?",
                copilotProfile.getId());

        TestHelper.assertCopilotProfile(copilotProfile, result.get(0));
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProfileDAOImpl#create(com.topcoder.direct.services.copilot.model.IdentifiableEntity)}</code>
     * method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testCreateFailure1() throws CopilotDAOException {

        CopilotProfile copilotProfile = TestHelper.createCopilotProfile();
        copilotProfile.setId(1);
        hibernateTemplate.save(copilotProfile.getStatus());

        instance.create(copilotProfile);
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProfileDAOImpl#create(com.topcoder.direct.services.copilot.model.IdentifiableEntity)}</code>
     * method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testCreateFailure3() throws CopilotDAOException {

        CopilotProfile copilotProfile = TestHelper.createCopilotProfile();

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("invalidApplicationContext.xml");
        CopilotProfileDAOImpl copilotProfileDAO = new CopilotProfileDAOImpl();
        copilotProfileDAO.setSessionFactory((SessionFactory) applicationContext.getBean("sessionFactory"));

        copilotProfileDAO.create(copilotProfile);
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProfileDAOImpl#create(com.topcoder.direct.services.copilot.model.IdentifiableEntity)}</code>
     * method when entity is null.</p>
     *
     * <p>{@link IllegalArgumentException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateNull() throws CopilotDAOException {

        instance.create(null);
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProfileDAOImpl#update(com.topcoder.direct.services.copilot.model.IdentifiableEntity)}</code>
     * method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testUpdate() throws CopilotDAOException {

        CopilotProfile copilotProfile = TestHelper.createCopilotProfile();
        hibernateTemplate.save(copilotProfile.getStatus());
        hibernateTemplate.save(copilotProfile);

        copilotProfile.setActivationDate(new Date());
        copilotProfile.setReliability(50F);
        copilotProfile.setShowCopilotEarnings(false);
        copilotProfile.setSuspensionCount(22);
        copilotProfile.setUserId(30L);

        CopilotProfileInfoType copilotProfileInfoType = TestHelper.createCopilotProfileInfoType();
        hibernateTemplate.save(copilotProfileInfoType);

        CopilotProfileInfo copilotProfileInfo = TestHelper.createCopilotProfileInfo();
        copilotProfileInfo.setInfoType(copilotProfileInfoType);
        copilotProfile.getProfileInfos().add(copilotProfileInfo);

        instance.update(copilotProfile);

        List<CopilotProfile> result = (List<CopilotProfile>) hibernateTemplate.find("from CopilotProfile where id = ?",
                copilotProfile.getId());

        TestHelper.assertCopilotProfile(copilotProfile, result.get(0));
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProfileDAOImpl#update(com.topcoder.direct.services.copilot.model.IdentifiableEntity)}</code>
     * method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testUpdateFailure1() throws CopilotDAOException {

        CopilotProfile copilotProfile = TestHelper.createCopilotProfile();

        Session session = instance.getSession();
        session.save(copilotProfile.getStatus());
        session.save(copilotProfile);
        session.delete(copilotProfile);

        instance.update(copilotProfile);
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProfileDAOImpl#update(com.topcoder.direct.services.copilot.model.IdentifiableEntity)}</code>
     * method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testUpdateFailure2() throws CopilotDAOException {

        CopilotProfile copilotProfile = TestHelper.createCopilotProfile();
        copilotProfile.setId(1L);

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("invalidApplicationContext.xml");
        CopilotProfileDAOImpl copilotProfileDAO = new CopilotProfileDAOImpl();
        copilotProfileDAO.setSessionFactory((SessionFactory) applicationContext.getBean("sessionFactory"));

        copilotProfileDAO.update(copilotProfile);
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProfileDAOImpl#update(com.topcoder.direct.services.copilot.model.IdentifiableEntity)}</code>
     * method when entity is null.</p>
     *
     * <p>{@link IllegalArgumentException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateNull() throws CopilotDAOException {

        instance.update(null);
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProfileDAOImpl#update(com.topcoder.direct.services.copilot.model.IdentifiableEntity)}</code>
     * method when entity has negative id.</p>
     *
     * <p>{@link IllegalArgumentException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateNegative() throws CopilotDAOException {
        CopilotProfile copilotProfile = new CopilotProfile();
        copilotProfile.setId(-1);
        instance.update(copilotProfile);
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProfileDAOImpl#update(com.topcoder.direct.services.copilot.model.IdentifiableEntity)}</code>
     * method when entity has id set to 0.</p>
     *
     * <p>{@link IllegalArgumentException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateZero() throws CopilotDAOException {
        CopilotProfile copilotProfile = new CopilotProfile();

        instance.update(copilotProfile);
    }

    /**
     * <p>Tests <code>{@link CopilotProfileDAOImpl#delete(long)}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testDelete1() throws CopilotDAOException {

        CopilotProfile copilotProfile1 = TestHelper.createCopilotProfile();
        hibernateTemplate.save(copilotProfile1.getStatus());
        hibernateTemplate.save(copilotProfile1);
        CopilotProfile copilotProfile2 = TestHelper.createCopilotProfile();
        hibernateTemplate.save(copilotProfile2.getStatus());
        hibernateTemplate.save(copilotProfile2);

        instance.delete(copilotProfile1.getId());

        List<CopilotProfile> result =
                hibernateTemplate.find("from CopilotProfile where id = ?", copilotProfile1.getId());

        Assert.assertEquals("One entity in database should still exist.", 1,
                hibernateTemplate.find("from CopilotProfile").size());
        Assert.assertEquals("Entity was not deleted.", 0, result.size());

        instance.delete(copilotProfile2.getId());

        result = hibernateTemplate.find("from CopilotProfile where id = ?", copilotProfile2.getId());

        Assert.assertEquals("None entity in database should exist.", 0,
                hibernateTemplate.find("from CopilotProfile").size());
        Assert.assertEquals("Entity was not deleted.", 0, result.size());
    }

    /**
     * <p>Tests <code>{@link CopilotProfileDAOImpl#delete(long)}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testDelete2() throws CopilotDAOException {

        CopilotProfile copilotProfile = TestHelper.createCopilotProfile();
        hibernateTemplate.save(copilotProfile.getStatus());

        CopilotProfileInfoType copilotProfileInfoType = TestHelper.createCopilotProfileInfoType();
        hibernateTemplate.save(copilotProfileInfoType);

        CopilotProfileInfo copilotProfileInfo = TestHelper.createCopilotProfileInfo();
        copilotProfileInfo.setInfoType(copilotProfileInfoType);
        copilotProfile.getProfileInfos().add(copilotProfileInfo);
        hibernateTemplate.save(copilotProfile);

        instance.delete(copilotProfile.getId());

        Assert.assertEquals("None entity in database should exist.", 0,
                hibernateTemplate.find("from CopilotProfile").size());
        Assert.assertEquals("None entity in database should exist.", 0,
                hibernateTemplate.find("from CopilotProfileInfo").size());
    }


    /**
     * <p>Tests <code>{@link CopilotProfileDAOImpl#delete(long)}</code> method.</p>
     *
     * <p>{@link com.topcoder.direct.services.copilot.dao.EntityNotFoundException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = EntityNotFoundException.class)
    public void testDeleteFailure1() throws CopilotDAOException {

        instance.delete(1L);
    }

    /**
     * <p>Tests <code>{@link CopilotProfileDAOImpl#delete(long)}</code> method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testDeleteFailure2() throws CopilotDAOException {

        MockIdentifiableEntityDAO dao = new MockIdentifiableEntityDAO();
        dao.setSessionFactory(hibernateTemplate.getSessionFactory());
        dao.delete(1L);
    }

    /**
     * <p>Tests <code>{@link CopilotProfileDAOImpl#delete(long)}</code> method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testDeleteFailure3() throws CopilotDAOException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("invalidApplicationContext.xml");
        CopilotProfileDAOImpl copilotProfileDAO = new CopilotProfileDAOImpl();
        copilotProfileDAO.setSessionFactory((SessionFactory) applicationContext.getBean("sessionFactory"));

        copilotProfileDAO.delete(1L);
    }

    /**
     * <p>Tests <code>{@link CopilotProfileDAOImpl#delete(long)}</code> method when entity id is negative.</p>
     *
     * <p>{@link IllegalArgumentException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDeleteNegative() throws CopilotDAOException {

        instance.delete(-1L);
    }

    /**
     * <p>Tests <code>{@link CopilotProfileDAOImpl#delete(long)}</code> method when entity id is 0.</p>
     *
     * <p>{@link IllegalArgumentException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDeleteZero() throws CopilotDAOException {

        instance.delete(0L);
    }

    /**
     * <p>Tests <code>{@link CopilotProfileDAOImpl#retrieve(long)}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void testRetrieve1() throws CopilotDAOException {

        CopilotProfile copilotProfile1 = TestHelper.createCopilotProfile();
        hibernateTemplate.save(copilotProfile1.getStatus());
        hibernateTemplate.save(copilotProfile1);
        CopilotProfile copilotProfile2 = TestHelper.createCopilotProfile();
        hibernateTemplate.save(copilotProfile2.getStatus());
        hibernateTemplate.save(copilotProfile2);

        CopilotProfile result = instance.retrieve(copilotProfile1.getId());

        TestHelper.assertCopilotProfile(copilotProfile1, result);

        result = instance.retrieve(copilotProfile2.getId());

        TestHelper.assertCopilotProfile(copilotProfile2, result);
    }

    /**
     * <p>Tests <code>{@link CopilotProfileDAOImpl#retrieve(long)}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void testRetrieve2() throws CopilotDAOException {

        Assert.assertNull("Null was expected.", instance.retrieve(1L));
    }

    /**
     * <p>Tests <code>{@link CopilotProfileDAOImpl#retrieve(long)}</code> method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testRetrieveFailure1() throws CopilotDAOException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("invalidApplicationContext.xml");
        CopilotProfileDAOImpl copilotProfileDAO = new CopilotProfileDAOImpl();
        copilotProfileDAO.setSessionFactory((SessionFactory) applicationContext.getBean("sessionFactory"));

        copilotProfileDAO.retrieve(1L);
    }

    /**
     * <p>Tests <code>{@link CopilotProfileDAOImpl#retrieve(long)}</code> method when entity id is negative.</p>
     *
     * <p>{@link IllegalArgumentException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRetrieveNegative() throws CopilotDAOException {

        instance.retrieve(-1L);
    }

    /**
     * <p>Tests <code>{@link CopilotProfileDAOImpl#retrieve(long)}</code> method when entity id is 0.</p>
     *
     * <p>{@link IllegalArgumentException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRetrieveZero() throws CopilotDAOException {

        instance.retrieve(0L);
    }

    /**
     * <p>Tests <code>{@link CopilotProfileDAOImpl#retrieveAll()}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void testRetrieveAll1() throws CopilotDAOException {

        CopilotProfile copilotProfile1 = TestHelper.createCopilotProfile();
        hibernateTemplate.save(copilotProfile1.getStatus());
        hibernateTemplate.save(copilotProfile1);
        CopilotProfile copilotProfile2 = TestHelper.createCopilotProfile();
        hibernateTemplate.save(copilotProfile2.getStatus());
        hibernateTemplate.save(copilotProfile2);

        List<CopilotProfile> result = instance.retrieveAll();

        Assert.assertEquals("Two entities were expected.", 2, result.size());
        TestHelper.assertCopilotProfile(copilotProfile1, result.get(0));
        TestHelper.assertCopilotProfile(copilotProfile2, result.get(1));
    }

    /**
     * <p>Tests <code>{@link CopilotProfileDAOImpl#retrieveAll()}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void testRetrieveAll2() throws CopilotDAOException {

        CopilotProfile copilotProfile1 = TestHelper.createCopilotProfile();
        hibernateTemplate.save(copilotProfile1.getStatus());
        hibernateTemplate.save(copilotProfile1);
        CopilotProfile copilotProfile2 = TestHelper.createCopilotProfile();
        hibernateTemplate.save(copilotProfile2.getStatus());
        hibernateTemplate.save(copilotProfile2);

        List<CopilotProfile> result = instance.retrieveAll();

        Assert.assertEquals("Two entities were expected.", 2, result.size());
        TestHelper.assertCopilotProfile(copilotProfile1, result.get(0));
        TestHelper.assertCopilotProfile(copilotProfile2, result.get(1));

        hibernateTemplate.delete(copilotProfile1);

        result = instance.retrieveAll();

        Assert.assertEquals("One entity was expected.", 1, result.size());
        TestHelper.assertCopilotProfile(copilotProfile2, result.get(0));

        hibernateTemplate.delete(copilotProfile2);

        Assert.assertEquals("No entities were expected.", 0, instance.retrieveAll().size());
    }

    /**
     * <p>Tests <code>{@link CopilotProfileDAOImpl#retrieveAll()}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void testRetrieveAll3() throws CopilotDAOException {

        Assert.assertEquals("No entities were expected.", 0, instance.retrieveAll().size());
    }

    /**
     * <p>Tests <code>{@link CopilotProfileDAOImpl#retrieveAll()}</code> method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testRetrieveAllFailure1() throws CopilotDAOException {

        MockIdentifiableEntityDAO dao = new MockIdentifiableEntityDAO();
        dao.setSessionFactory(hibernateTemplate.getSessionFactory());

        dao.retrieveAll();
    }

    /**
     * <p>Tests <code>{@link CopilotProfileDAOImpl#retrieveAll()}</code> method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testRetrieveAllFailure2() throws CopilotDAOException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("invalidApplicationContext.xml");
        CopilotProfileDAOImpl copilotProfileDAO = new CopilotProfileDAOImpl();
        copilotProfileDAO.setSessionFactory((SessionFactory) applicationContext.getBean("sessionFactory"));

        copilotProfileDAO.retrieveAll();
    }

    /**
     * <p>Tests <code>{@link CopilotProfileDAOImpl#getCopilotProfile(long)}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void testGetCopilotProfile1() throws CopilotDAOException {

        CopilotProfile copilotProfile1 = TestHelper.createCopilotProfile();
        copilotProfile1.setUserId(1L);
        hibernateTemplate.save(copilotProfile1.getStatus());
        hibernateTemplate.save(copilotProfile1);
        CopilotProfile copilotProfile2 = TestHelper.createCopilotProfile();
        copilotProfile2.setUserId(2L);
        hibernateTemplate.save(copilotProfile2.getStatus());
        hibernateTemplate.save(copilotProfile2);

        CopilotProfile result = instance.getCopilotProfile(copilotProfile1.getUserId());

        TestHelper.assertCopilotProfile(copilotProfile1, result);

        result = instance.getCopilotProfile(copilotProfile2.getUserId());

        TestHelper.assertCopilotProfile(copilotProfile2, result);
    }

    /**
     * <p>Tests <code>{@link CopilotProfileDAOImpl#getCopilotProfile(long)}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void testGetCopilotProfile2() throws CopilotDAOException {

        Assert.assertNull("No result was expected.", instance.getCopilotProfile(1L));
    }

    /**
     * <p>Tests <code>{@link CopilotProfileDAOImpl#getCopilotProfile(long)}</code> method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testGetCopilotProfileFailure1() throws CopilotDAOException {

        CopilotProfile copilotProfile1 = TestHelper.createCopilotProfile();
        copilotProfile1.setUserId(1L);
        hibernateTemplate.save(copilotProfile1.getStatus());
        hibernateTemplate.save(copilotProfile1);
        CopilotProfile copilotProfile2 = TestHelper.createCopilotProfile();
        copilotProfile2.setUserId(1L);
        hibernateTemplate.save(copilotProfile2.getStatus());
        hibernateTemplate.save(copilotProfile2);

        instance.getCopilotProfile(1L);
    }

    /**
     * <p>Tests <code>{@link CopilotProfileDAOImpl#getCopilotProfile(long)}</code> method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testGetCopilotProfileFailure2() throws CopilotDAOException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("invalidApplicationContext.xml");
        CopilotProfileDAOImpl copilotProfileDAO = new CopilotProfileDAOImpl();
        copilotProfileDAO.setSessionFactory((SessionFactory) applicationContext.getBean("sessionFactory"));

        copilotProfileDAO.getCopilotProfile(1L);
    }

    /**
     * <p>Tests <code>{@link CopilotProfileDAOImpl#getCopilotProfile(long)}</code> method when userId is negative.</p>
     *
     * <p>{@link IllegalArgumentException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetCopilotProfileNegative() throws CopilotDAOException {

        instance.getCopilotProfile(-1L);
    }

    /**
     * <p>Tests <code>{@link CopilotProfileDAOImpl#getCopilotProfile(long)}</code> method when userId is zero.</p>
     *
     * <p>{@link IllegalArgumentException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetCopilotProfileZero() throws CopilotDAOException {

        instance.getCopilotProfile(0L);
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProfileDAOImpl#updateAuditTimestamp(com.topcoder.direct.services.copilot.model.IdentifiableEntity
     * , boolean)}</code> method.</p>
     *
     * @throws Exception if any error occurs
     */
    @Test
    public void testUpdateAuditTimestamp1() throws Exception {

        CopilotProfile copilotProfile = TestHelper.createCopilotProfile();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2010-01-30");
        copilotProfile.setCreateDate(date);
        copilotProfile.setModifyDate(date);

        instance.updateAuditTimestamp(copilotProfile, true);

        Assert.assertFalse("Creation date was not changed", copilotProfile.getCreateDate().equals(date));
        Assert.assertEquals("Modification date should be not changed", date, copilotProfile.getModifyDate());
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProfileDAOImpl#updateAuditTimestamp(com.topcoder.direct.services.copilot.model.IdentifiableEntity
     * , boolean)}</code> method.</p>
     *
     * @throws Exception if any error occurs
     */
    @Test
    public void testUpdateAuditTimestamp2() throws Exception {

        CopilotProfile copilotProfile = TestHelper.createCopilotProfile();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2010-01-30");
        copilotProfile.setCreateDate(date);
        copilotProfile.setModifyDate(date);

        instance.updateAuditTimestamp(copilotProfile, false);

        Assert.assertEquals("Create date should be not changed", date, copilotProfile.getCreateDate());
        Assert.assertFalse("Modification date was not changed", copilotProfile.getModifyDate().equals(date));
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProfileDAOImpl#updateAuditTimestamp(com.topcoder.direct.services.copilot.model.IdentifiableEntity
     * , boolean)}</code> method.</p>
     *
     * @throws Exception if any error occurs
     */
    @Test
    public void testUpdateAuditTimestamp3() throws Exception {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2010-01-30");

        CopilotProfileInfo copilotProfileInfo = new CopilotProfileInfo();
        copilotProfileInfo.setInfoType(new CopilotProfileInfoType());
        copilotProfileInfo.setValue("value");
        copilotProfileInfo.setCopilotProfileId(10L);

        copilotProfileInfo.setCreateDate(date);
        copilotProfileInfo.setModifyDate(date);
        copilotProfileInfo.setCreateUser("user");
        copilotProfileInfo.setModifyUser("user");

        CopilotProfile copilotProfile = TestHelper.createCopilotProfile();
        copilotProfile.setProfileInfos(new HashSet<CopilotProfileInfo>());
        copilotProfile.getProfileInfos().add(copilotProfileInfo);

        copilotProfile.setCreateDate(date);
        copilotProfile.setModifyDate(date);

        instance.updateAuditTimestamp(copilotProfile, true);

        Assert.assertFalse("Creation date was not changed", copilotProfile.getCreateDate().equals(date));
        Assert.assertEquals("Modification date should be not changed", date, copilotProfile.getModifyDate());

        Assert.assertFalse("Creation date was not changed", copilotProfileInfo.getCreateDate().equals(date));
        Assert.assertEquals("Modification date should be not changed", date, copilotProfileInfo.getModifyDate());
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProfileDAOImpl#updateAuditTimestamp(com.topcoder.direct.services.copilot.model.IdentifiableEntity
     * , boolean)}</code> method.</p>
     *
     * @throws Exception if any error occurs
     */
    @Test
    public void testUpdateAuditTimestamp4() throws Exception {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2010-01-30");

        CopilotProfileInfo copilotProfileInfo = new CopilotProfileInfo();
        copilotProfileInfo.setInfoType(new CopilotProfileInfoType());
        copilotProfileInfo.setValue("value");
        copilotProfileInfo.setId(1L);
        copilotProfileInfo.setCopilotProfileId(10L);

        copilotProfileInfo.setCreateDate(date);
        copilotProfileInfo.setModifyDate(date);
        copilotProfileInfo.setCreateUser("user");
        copilotProfileInfo.setModifyUser("user");

        CopilotProfile copilotProfile = TestHelper.createCopilotProfile();
        copilotProfile.setProfileInfos(new HashSet<CopilotProfileInfo>());
        copilotProfile.getProfileInfos().add(copilotProfileInfo);

        copilotProfile.setCreateDate(date);
        copilotProfile.setModifyDate(date);

        instance.updateAuditTimestamp(copilotProfile, true);

        Assert.assertFalse("Creation date was not changed", copilotProfile.getCreateDate().equals(date));
        Assert.assertEquals("Modification date should be not changed", date, copilotProfile.getModifyDate());

        Assert.assertEquals("Create date should be not changed", date, copilotProfileInfo.getCreateDate());
        Assert.assertFalse("Modification date was not changed", copilotProfileInfo.getModifyDate().equals(date));
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProfileDAOImpl#updateAuditTimestamp(com.topcoder.direct.services.copilot.model.IdentifiableEntity
     * , boolean)}</code> method.</p>
     *
     * @throws Exception if any error occurs
     */
    @Test
    public void testUpdateAuditTimestamp5() throws Exception {

        CopilotProfile copilotProfile = TestHelper.createCopilotProfile();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2010-01-30");
        copilotProfile.setCreateDate(date);
        copilotProfile.setModifyDate(date);
        copilotProfile.setProfileInfos(null);

        instance.updateAuditTimestamp(copilotProfile, true);

        Assert.assertFalse("Creation date was not changed", copilotProfile.getCreateDate().equals(date));
        Assert.assertEquals("Modification date should be not changed", date, copilotProfile.getModifyDate());
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProfileDAOImpl#updateAuditTimestamp(com.topcoder.direct.services.copilot.model.IdentifiableEntity
     * , boolean)}</code> method is entity is null.</p>
     *
     * <p>{@link IllegalArgumentException} is expected.</p>
     *
     * @throws Exception if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateAuditTimestampNull() throws Exception {

        instance.updateAuditTimestamp(null, true);
    }
}
