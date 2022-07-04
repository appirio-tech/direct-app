/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.copilot.dao.impl;

import com.topcoder.direct.services.copilot.TestHelper;
import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.EntityNotFoundException;
import com.topcoder.direct.services.copilot.model.CopilotProfile;
import com.topcoder.direct.services.copilot.model.CopilotProject;
import com.topcoder.direct.services.copilot.model.CopilotProjectInfo;
import com.topcoder.direct.services.copilot.model.CopilotProjectInfoType;
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
import java.util.List;

/**
 * <p>Tests <code>{@link CopilotProjectDAOImpl}</code> class.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class CopilotProjectDAOImplTests {

    /**
     * <p>Represents instance of tested class.</p>
     */
    @Autowired
    private CopilotProjectDAOImpl instance;

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
        return new JUnit4TestAdapter(CopilotProjectDAOImplTests.class);
    }

    /**
     * <p>Tests <code>{@link CopilotProjectDAOImpl#CopilotProjectDAOImpl()}</code> constructor.</p>
     */
    @Test
    public void testCtor() {
        Assert.assertNotNull("Object not instantiated.", instance);
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProjectDAOImpl#create(com.topcoder.direct.services.copilot.model.IdentifiableEntity)}</code>
     * method.</p>
     *
     * @throws com.topcoder.direct.services.copilot.dao.CopilotDAOException
     *          if any error occurs
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testCreate1() throws CopilotDAOException {

        CopilotProfile copilotProfile = TestHelper.createCopilotProfile();
        CopilotProject copilotProject = TestHelper.createCopilotProject();

        TestHelper.persistCopilotProjectDependencies(hibernateTemplate, copilotProfile, copilotProject);

        instance.create(copilotProject);

        List<CopilotProject> result = (List<CopilotProject>) hibernateTemplate.find("from CopilotProject where id = ?",
                copilotProject.getId());

        assertCopilotProject(copilotProject, result.get(0));
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProjectDAOImpl#create(com.topcoder.direct.services.copilot.model.IdentifiableEntity)}</code>
     * method.</p>
     *
     * @throws com.topcoder.direct.services.copilot.dao.CopilotDAOException
     *          if any error occurs
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testCreate2() throws CopilotDAOException {

        CopilotProfile copilotProfile = TestHelper.createCopilotProfile();
        CopilotProject copilotProject = TestHelper.createCopilotProject();

        TestHelper.persistCopilotProjectDependencies(hibernateTemplate, copilotProfile, copilotProject);

        CopilotProjectInfoType copilotProjectInfoType = TestHelper.createCopilotProjectInfoType();
        hibernateTemplate.save(copilotProjectInfoType);

        CopilotProjectInfo copilotProjectInfo = TestHelper.createCopilotProjectInfo();
        copilotProjectInfo.setInfoType(copilotProjectInfoType);
        copilotProject.getProjectInfos().add(copilotProjectInfo);

        instance.create(copilotProject);

        List<CopilotProject> result = (List<CopilotProject>) hibernateTemplate.find("from CopilotProject where id = ?",
                copilotProject.getId());

        assertCopilotProject(copilotProject, result.get(0));
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProjectDAOImpl#create(com.topcoder.direct.services.copilot.model.IdentifiableEntity)}</code>
     * method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testCreateFailure1() throws CopilotDAOException {

        CopilotProject copilotProject = TestHelper.createCopilotProject();
        copilotProject.setId(1);
        hibernateTemplate.save(copilotProject.getStatus());

        instance.create(copilotProject);
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProjectDAOImpl#create(com.topcoder.direct.services.copilot.model.IdentifiableEntity)}</code>
     * method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testCreateFailure2() throws CopilotDAOException {

        CopilotProject copilotProject = TestHelper.createCopilotProject();

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("invalidApplicationContext.xml");
        CopilotProjectDAOImpl copilotProjectDAO = new CopilotProjectDAOImpl();
        copilotProjectDAO.setSessionFactory((SessionFactory) applicationContext.getBean("sessionFactory"));

        copilotProjectDAO.create(copilotProject);
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProjectDAOImpl#create(com.topcoder.direct.services.copilot.model.IdentifiableEntity)}</code>
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
     * CopilotProjectDAOImpl#update(com.topcoder.direct.services.copilot.model.IdentifiableEntity)}</code>
     * method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testUpdate() throws CopilotDAOException {

        CopilotProfile copilotProfile = TestHelper.createCopilotProfile();
        CopilotProject copilotProject = TestHelper.createCopilotProject();

        TestHelper.persistCopilotProjectDependencies(hibernateTemplate, copilotProfile, copilotProject);
        hibernateTemplate.save(copilotProject);

        copilotProject.setCompletionDate(new Date());
        copilotProject.setCustomerFeedback("test customer feedback");
        copilotProject.setCustomerRating(0.9F);
        copilotProject.setPmFeedback("test pm feedback");
        copilotProject.setPmRating(0.9F);

        CopilotProjectInfoType copilotProjectInfoType = TestHelper.createCopilotProjectInfoType();
        hibernateTemplate.save(copilotProjectInfoType);

        CopilotProjectInfo copilotProjectInfo = TestHelper.createCopilotProjectInfo();
        copilotProjectInfo.setInfoType(copilotProjectInfoType);
        copilotProject.getProjectInfos().add(copilotProjectInfo);

        instance.update(copilotProject);

        List<CopilotProject> result = (List<CopilotProject>) hibernateTemplate.find("from CopilotProject where id = ?",
                copilotProject.getId());

        assertCopilotProject(copilotProject, result.get(0));
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProjectDAOImpl#update(com.topcoder.direct.services.copilot.model.IdentifiableEntity)}</code>
     * method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testUpdateFailure1() throws CopilotDAOException {

        CopilotProject copilotProject = TestHelper.createCopilotProject();

        Session session = instance.getSession();
        session.save(copilotProject.getStatus());
        session.save(copilotProject);
        session.delete(copilotProject);

        instance.update(copilotProject);
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProjectDAOImpl#update(com.topcoder.direct.services.copilot.model.IdentifiableEntity)}</code>
     * method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testUpdateFailure2() throws CopilotDAOException {

        CopilotProject copilotProject = TestHelper.createCopilotProject();
        copilotProject.setId(1L);

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("invalidApplicationContext.xml");
        CopilotProjectDAOImpl copilotProjectDAO = new CopilotProjectDAOImpl();
        copilotProjectDAO.setSessionFactory((SessionFactory) applicationContext.getBean("sessionFactory"));

        copilotProjectDAO.update(copilotProject);
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProjectDAOImpl#update(com.topcoder.direct.services.copilot.model.IdentifiableEntity)}</code>
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
     * CopilotProjectDAOImpl#update(com.topcoder.direct.services.copilot.model.IdentifiableEntity)}</code>
     * method when entity has negative id.</p>
     *
     * <p>{@link IllegalArgumentException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateNegative() throws CopilotDAOException {
        CopilotProject copilotProject = new CopilotProject();
        copilotProject.setId(-1);
        instance.update(copilotProject);
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProjectDAOImpl#update(com.topcoder.direct.services.copilot.model.IdentifiableEntity)}</code>
     * method when entity has id set to 0.</p>
     *
     * <p>{@link IllegalArgumentException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateZero() throws CopilotDAOException {
        CopilotProject copilotProject = new CopilotProject();

        instance.update(copilotProject);
    }

    /**
     * <p>Tests <code>{@link CopilotProjectDAOImpl#delete(long)}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testDelete1() throws CopilotDAOException {

        CopilotProfile copilotProfile1 = TestHelper.createCopilotProfile();
        CopilotProject copilotProject1 = TestHelper.createCopilotProject();

        TestHelper.persistCopilotProjectDependencies(hibernateTemplate, copilotProfile1, copilotProject1);
        hibernateTemplate.save(copilotProject1);

        CopilotProfile copilotProfile2 = TestHelper.createCopilotProfile();
        CopilotProject copilotProject2 = TestHelper.createCopilotProject();

        TestHelper.persistCopilotProjectDependencies(hibernateTemplate, copilotProfile2, copilotProject2);
        hibernateTemplate.save(copilotProject2);

        instance.delete(copilotProject1.getId());

        List<CopilotProject> result =
                hibernateTemplate.find("from CopilotProject where id = ?", copilotProject1.getId());

        Assert.assertEquals("One entity in database should still exist.", 1,
                hibernateTemplate.find("from CopilotProject").size());
        Assert.assertEquals("Entity was not deleted.", 0, result.size());

        instance.delete(copilotProject2.getId());

        result = hibernateTemplate.find("from CopilotProject where id = ?", copilotProject2.getId());

        Assert.assertEquals("None entity in database should exist.", 0,
                hibernateTemplate.find("from CopilotProject").size());
        Assert.assertEquals("Entity was not deleted.", 0, result.size());
    }

    /**
     * <p>Tests <code>{@link CopilotProjectDAOImpl#delete(long)}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testDelete2() throws CopilotDAOException {

        CopilotProfile copilotProfile = TestHelper.createCopilotProfile();
        CopilotProject copilotProject = TestHelper.createCopilotProject();

        TestHelper.persistCopilotProjectDependencies(hibernateTemplate, copilotProfile, copilotProject);

        CopilotProjectInfoType copilotProjectInfoType = TestHelper.createCopilotProjectInfoType();
        hibernateTemplate.save(copilotProjectInfoType);

        CopilotProjectInfo copilotProjectInfo = TestHelper.createCopilotProjectInfo();
        copilotProjectInfo.setCopilotProjectId(copilotProject.getId());
        copilotProjectInfo.setInfoType(copilotProjectInfoType);
        copilotProject.getProjectInfos().add(copilotProjectInfo);
        hibernateTemplate.save(copilotProject);

        instance.delete(copilotProject.getId());

        Assert.assertEquals("None entity in database should exist.", 0,
                hibernateTemplate.find("from CopilotProject").size());
        Assert.assertEquals("None entity in database should exist.", 0,
                hibernateTemplate.find("from CopilotProjectInfo").size());
    }

    /**
     * <p>Tests <code>{@link CopilotProjectDAOImpl#delete(long)}</code> method.</p>
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
     * <p>Tests <code>{@link CopilotProjectDAOImpl#delete(long)}</code> method.</p>
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
     * <p>Tests <code>{@link CopilotProjectDAOImpl#delete(long)}</code> method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testDeleteFailure3() throws CopilotDAOException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("invalidApplicationContext.xml");
        CopilotProjectDAOImpl copilotProjectDAO = new CopilotProjectDAOImpl();
        copilotProjectDAO.setSessionFactory((SessionFactory) applicationContext.getBean("sessionFactory"));

        copilotProjectDAO.delete(1L);
    }

    /**
     * <p>Tests <code>{@link CopilotProjectDAOImpl#delete(long)}</code> method when entity id is negative.</p>
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
     * <p>Tests <code>{@link CopilotProjectDAOImpl#delete(long)}</code> method when entity id is 0.</p>
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
     * <p>Tests <code>{@link CopilotProjectDAOImpl#retrieve(long)}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void testRetrieve1() throws CopilotDAOException {

        CopilotProfile copilotProfile1 = TestHelper.createCopilotProfile();
        CopilotProject copilotProject1 = TestHelper.createCopilotProject();

        TestHelper.persistCopilotProjectDependencies(hibernateTemplate, copilotProfile1, copilotProject1);
        hibernateTemplate.save(copilotProject1);

        CopilotProfile copilotProfile2 = TestHelper.createCopilotProfile();
        CopilotProject copilotProject2 = TestHelper.createCopilotProject();

        TestHelper.persistCopilotProjectDependencies(hibernateTemplate, copilotProfile2, copilotProject2);
        hibernateTemplate.save(copilotProject2);

        CopilotProject result = instance.retrieve(copilotProject1.getId());

        assertCopilotProject(copilotProject1, result);

        result = instance.retrieve(copilotProject2.getId());

        assertCopilotProject(copilotProject2, result);
    }

    /**
     * <p>Tests <code>{@link CopilotProjectDAOImpl#retrieve(long)}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void testRetrieve2() throws CopilotDAOException {

        Assert.assertNull("Null was expected.", instance.retrieve(1L));
    }

    /**
     * <p>Tests <code>{@link CopilotProjectDAOImpl#retrieve(long)}</code> method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testRetrieveFailure1() throws CopilotDAOException {

        MockIdentifiableEntityDAO dao = new MockIdentifiableEntityDAO();
        dao.setSessionFactory(hibernateTemplate.getSessionFactory());

        dao.retrieve(1L);
    }

    /**
     * <p>Tests <code>{@link CopilotProjectDAOImpl#retrieve(long)}</code> method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testRetrieveFailure2() throws CopilotDAOException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("invalidApplicationContext.xml");
        CopilotProjectDAOImpl copilotProjectDAO = new CopilotProjectDAOImpl();
        copilotProjectDAO.setSessionFactory((SessionFactory) applicationContext.getBean("sessionFactory"));

        copilotProjectDAO.retrieve(1L);
    }

    /**
     * <p>Tests <code>{@link CopilotProjectDAOImpl#retrieve(long)}</code> method when entity id is negative.</p>
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
     * <p>Tests <code>{@link CopilotProjectDAOImpl#retrieve(long)}</code> method when entity id is 0.</p>
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
     * <p>Tests <code>{@link CopilotProjectDAOImpl#retrieveAll()}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void testRetrieveAll1() throws CopilotDAOException {

        CopilotProfile copilotProfile1 = TestHelper.createCopilotProfile();
        CopilotProject copilotProject1 = TestHelper.createCopilotProject();

        TestHelper.persistCopilotProjectDependencies(hibernateTemplate, copilotProfile1, copilotProject1);
        hibernateTemplate.save(copilotProject1);

        CopilotProfile copilotProfile2 = TestHelper.createCopilotProfile();
        CopilotProject copilotProject2 = TestHelper.createCopilotProject();

        TestHelper.persistCopilotProjectDependencies(hibernateTemplate, copilotProfile2, copilotProject2);
        hibernateTemplate.save(copilotProject2);

        List<CopilotProject> result = instance.retrieveAll();

        Assert.assertEquals("Two entities were expected.", 2, result.size());
        assertCopilotProject(copilotProject1, result.get(0));
        assertCopilotProject(copilotProject2, result.get(1));
    }

    /**
     * <p>Tests <code>{@link CopilotProjectDAOImpl#retrieveAll()}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void testRetrieveAll2() throws CopilotDAOException {


        CopilotProfile copilotProfile1 = TestHelper.createCopilotProfile();
        CopilotProject copilotProject1 = TestHelper.createCopilotProject();

        TestHelper.persistCopilotProjectDependencies(hibernateTemplate, copilotProfile1, copilotProject1);
        hibernateTemplate.save(copilotProject1);

        CopilotProfile copilotProfile2 = TestHelper.createCopilotProfile();
        CopilotProject copilotProject2 = TestHelper.createCopilotProject();

        TestHelper.persistCopilotProjectDependencies(hibernateTemplate, copilotProfile2, copilotProject2);
        hibernateTemplate.save(copilotProject2);

        List<CopilotProject> result = instance.retrieveAll();

        Assert.assertEquals("Two entities were expected.", 2, result.size());
        assertCopilotProject(copilotProject1, result.get(0));
        assertCopilotProject(copilotProject2, result.get(1));

        hibernateTemplate.delete(copilotProject1);

        result = instance.retrieveAll();

        Assert.assertEquals("One entity was expected.", 1, result.size());
        assertCopilotProject(copilotProject2, result.get(0));

        hibernateTemplate.delete(copilotProject2);

        Assert.assertEquals("No entities were expected.", 0, instance.retrieveAll().size());
    }

    /**
     * <p>Tests <code>{@link CopilotProjectDAOImpl#retrieveAll()}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void testRetrieveAll3() throws CopilotDAOException {

        Assert.assertEquals("No entities were expected.", 0, instance.retrieveAll().size());
    }

    /**
     * <p>Tests <code>{@link CopilotProjectDAOImpl#retrieveAll()}</code> method.</p>
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
     * <p>Tests <code>{@link CopilotProjectDAOImpl#retrieveAll()}</code> method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testRetrieveAllFailure2() throws CopilotDAOException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("invalidApplicationContext.xml");
        CopilotProjectDAOImpl copilotProjectDAO = new CopilotProjectDAOImpl();
        copilotProjectDAO.setSessionFactory((SessionFactory) applicationContext.getBean("sessionFactory"));

        copilotProjectDAO.retrieveAll();
    }


    /**
     * <p>Tests <code>{@link CopilotProjectDAOImpl#getCopilotProjects(long)}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void testGetCopilotProjects1() throws CopilotDAOException {

        CopilotProfile copilotProfile1 = TestHelper.createCopilotProfile();
        CopilotProject copilotProject1 = TestHelper.createCopilotProject();

        TestHelper.persistCopilotProjectDependencies(hibernateTemplate, copilotProfile1, copilotProject1);
        hibernateTemplate.save(copilotProject1);

        CopilotProfile copilotProfile2 = TestHelper.createCopilotProfile();
        CopilotProject copilotProject2 = TestHelper.createCopilotProject();

        TestHelper.persistCopilotProjectDependencies(hibernateTemplate, copilotProfile2, copilotProject2);
        hibernateTemplate.save(copilotProject2);

        List<CopilotProject> result = instance.getCopilotProjects(copilotProject1.getCopilotProfileId());

        Assert.assertEquals("Only one result was expected.", 1, result.size());
        assertCopilotProject(copilotProject1, result.get(0));

        result = instance.getCopilotProjects(copilotProject2.getCopilotProfileId());

        Assert.assertEquals("Only one result was expected.", 1, result.size());
        assertCopilotProject(copilotProject2, result.get(0));
    }

    /**
     * <p>Tests <code>{@link CopilotProjectDAOImpl#getCopilotProjects(long)}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void testGetCopilotProjects2() throws CopilotDAOException {

        CopilotProfile copilotProfile = TestHelper.createCopilotProfile();
        CopilotProject copilotProject1 = TestHelper.createCopilotProject();
        CopilotProject copilotProject2 = TestHelper.createCopilotProject();

        hibernateTemplate.save(copilotProfile.getStatus());
        hibernateTemplate.save(copilotProfile);

        copilotProject1.setCopilotProfileId(copilotProfile.getId());
        hibernateTemplate.save(copilotProject1.getStatus());
        hibernateTemplate.save(copilotProject1.getCopilotType());
        hibernateTemplate.save(copilotProject1);

        copilotProject2.setCopilotProfileId(copilotProfile.getId());
        hibernateTemplate.save(copilotProject2.getStatus());
        hibernateTemplate.save(copilotProject2.getCopilotType());
        hibernateTemplate.save(copilotProject2);

        List<CopilotProject> result = instance.getCopilotProjects(copilotProject1.getCopilotProfileId());

        Assert.assertEquals("Two results were expected.", 2, result.size());
        assertCopilotProject(copilotProject1, result.get(0));
        assertCopilotProject(copilotProject2, result.get(1));
    }

    /**
     * <p>Tests <code>{@link CopilotProjectDAOImpl#getCopilotProjects(long)}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void testGetCopilotProjects3() throws CopilotDAOException {

        Assert.assertEquals("No results were expected.", 0, instance.getCopilotProjects(1L).size());
    }

    /**
     * <p>Tests <code>{@link CopilotProjectDAOImpl#getCopilotProjects(long)}</code> method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testGetCopilotProjectsFailure1() throws CopilotDAOException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("invalidApplicationContext.xml");
        CopilotProjectDAOImpl copilotProjectDAO = new CopilotProjectDAOImpl();
        copilotProjectDAO.setSessionFactory((SessionFactory) applicationContext.getBean("sessionFactory"));

        copilotProjectDAO.getCopilotProjects(1L);
    }

    /**
     * <p>Tests <code>{@link CopilotProjectDAOImpl#getCopilotProjects(long)}</code> method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testGetCopilotProjectsFailure2() throws CopilotDAOException {

        CopilotProject copilotProject = TestHelper.createCopilotProject();

        hibernateTemplate.save(copilotProject);

        instance.getCopilotProjects(1L);
    }

    /**
     * <p>Tests <code>{@link CopilotProjectDAOImpl#getCopilotProjects(long)}</code> method when copilotProfileId is
     * negative.</p>
     *
     * <p>{@link IllegalArgumentException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetCopilotProjectsNegative() throws CopilotDAOException {

        instance.getCopilotProjects(-1L);
    }

    /**
     * <p>Tests <code>{@link CopilotProjectDAOImpl#getCopilotProjects(long)}</code> method when copilotProfileId is
     * zero.</p>
     *
     * <p>{@link IllegalArgumentException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetCopilotProjectsZero() throws CopilotDAOException {

        instance.getCopilotProjects(0L);
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProjectDAOImpl#updateAuditTimestamp(com.topcoder.direct.services.copilot.model.IdentifiableEntity
     * , boolean)}</code> method.</p>
     *
     * @throws Exception if any error occurs
     */
    @Test
    public void testUpdateAuditTimestamp1() throws Exception {

        CopilotProject copilotProject = TestHelper.createCopilotProject();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2010-01-30");
        copilotProject.setCreateDate(date);
        copilotProject.setModifyDate(date);

        instance.updateAuditTimestamp(copilotProject, true);

        Assert.assertFalse("Creation date was not changed", copilotProject.getCreateDate().equals(date));
        Assert.assertEquals("Modification date should be not changed", date, copilotProject.getModifyDate());
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProjectDAOImpl#updateAuditTimestamp(com.topcoder.direct.services.copilot.model.IdentifiableEntity
     * , boolean)}</code> method.</p>
     *
     * @throws Exception if any error occurs
     */
    @Test
    public void testUpdateAuditTimestamp2() throws Exception {

        CopilotProject copilotProject = TestHelper.createCopilotProject();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2010-01-30");
        copilotProject.setCreateDate(date);
        copilotProject.setModifyDate(date);

        instance.updateAuditTimestamp(copilotProject, false);

        Assert.assertEquals("Create date should be not changed", date, copilotProject.getCreateDate());
        Assert.assertFalse("Modification date was not changed", copilotProject.getModifyDate().equals(date));
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProjectDAOImpl#updateAuditTimestamp(com.topcoder.direct.services.copilot.model.IdentifiableEntity
     * , boolean)}</code> method.</p>
     *
     * @throws Exception if any error occurs
     */
    @Test
    public void testUpdateAuditTimestamp3() throws Exception {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2010-01-30");

        CopilotProjectInfo copilotProjectInfo = new CopilotProjectInfo();
        copilotProjectInfo.setId(0);
        copilotProjectInfo.setCreateDate(date);
        copilotProjectInfo.setModifyDate(date);

        CopilotProject copilotProject = TestHelper.createCopilotProject();
        copilotProject.getProjectInfos().add(copilotProjectInfo);

        copilotProject.setCreateDate(date);
        copilotProject.setModifyDate(date);

        instance.updateAuditTimestamp(copilotProject, true);

        Assert.assertFalse("Creation date was not changed", copilotProject.getCreateDate().equals(date));
        Assert.assertEquals("Modification date should be not changed", date, copilotProject.getModifyDate());

        Assert.assertFalse("Creation date was not changed", copilotProjectInfo.getCreateDate().equals(date));
        Assert.assertEquals("Modification date should be not changed", date, copilotProjectInfo.getModifyDate());
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProjectDAOImpl#updateAuditTimestamp(com.topcoder.direct.services.copilot.model.IdentifiableEntity
     * , boolean)}</code> method.</p>
     *
     * @throws Exception if any error occurs
     */
    @Test
    public void testUpdateAuditTimestamp4() throws Exception {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2010-01-30");

        CopilotProjectInfo copilotProjectInfo = new CopilotProjectInfo();
        copilotProjectInfo.setId(1L);
        copilotProjectInfo.setCreateDate(date);
        copilotProjectInfo.setModifyDate(date);

        CopilotProject copilotProject = TestHelper.createCopilotProject();
        copilotProject.getProjectInfos().add(copilotProjectInfo);

        copilotProject.setCreateDate(date);
        copilotProject.setModifyDate(date);

        instance.updateAuditTimestamp(copilotProject, true);

        Assert.assertFalse("Creation date was not changed", copilotProject.getCreateDate().equals(date));
        Assert.assertEquals("Modification date should be not changed", date, copilotProject.getModifyDate());

        Assert.assertEquals("Create date should be not changed", date, copilotProjectInfo.getCreateDate());
        Assert.assertFalse("Modification date was not changed", copilotProjectInfo.getModifyDate().equals(date));
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProjectDAOImpl#updateAuditTimestamp(com.topcoder.direct.services.copilot.model.IdentifiableEntity
     * , boolean)}</code> method.</p>
     *
     * @throws Exception if any error occurs
     */
    @Test
    public void testUpdateAuditTimestamp5() throws Exception {

        CopilotProject copilotProject = TestHelper.createCopilotProject();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2010-01-30");
        copilotProject.setCreateDate(date);
        copilotProject.setModifyDate(date);
        copilotProject.setProjectInfos(null);

        instance.updateAuditTimestamp(copilotProject, true);

        Assert.assertFalse("Creation date was not changed", copilotProject.getCreateDate().equals(date));
        Assert.assertEquals("Modification date should be not changed", date, copilotProject.getModifyDate());
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProjectDAOImpl#updateAuditTimestamp(com.topcoder.direct.services.copilot.model.IdentifiableEntity
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

    /**
     * <p>Asserts that the passed {@link CopilotProject} instances are exactly the same.</p>
     *
     * @param copilotProject project to compare
     * @param result         project to compare
     */
    private void assertCopilotProject(CopilotProject copilotProject, CopilotProject result) {
        Assert.assertEquals("Invalid CopilotProject id", copilotProject.getId(), result.getId());
        Assert.assertEquals("Invalid CopilotProject status", copilotProject.getStatus(), result.getStatus());

        Assert.assertEquals("Invalid CopilotProject name", copilotProject.getName(),
                result.getName());
        Assert.assertEquals("Invalid CopilotProject completion date", copilotProject.getCompletionDate(),
                result.getCompletionDate());
        Assert.assertEquals("Invalid CopilotProject copilot type", copilotProject.getCopilotType(),
                result.getCopilotType());
        Assert.assertEquals("Invalid CopilotProject customer feedback", copilotProject.getCustomerFeedback(),
                result.getCustomerFeedback());
        Assert.assertEquals("Invalid CopilotProject customer rating", copilotProject.getCustomerRating(),
                result.getCustomerRating());
        Assert.assertEquals("Invalid CopilotProject pm feedback", copilotProject.getPmFeedback(),
                result.getPmFeedback());
        Assert.assertEquals("Invalid CopilotProject pm rating", copilotProject.getPmRating(),
                result.getPmRating());
        Assert.assertEquals("Invalid CopilotProject tcDirectProjectId", copilotProject.getTcDirectProjectId(),
                result.getTcDirectProjectId());

        Assert.assertEquals("Invalid CopilotProject create user", copilotProject.getCreateUser(),
                result.getCreateUser());
        Assert.assertEquals("Invalid CopilotProject modify user", copilotProject.getModifyUser(),
                result.getModifyUser());
    }
}
