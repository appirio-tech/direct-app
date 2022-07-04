/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.copilot.dao.impl;

import com.topcoder.direct.services.copilot.TestHelper;
import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.EntityNotFoundException;
import com.topcoder.direct.services.copilot.model.CopilotProfile;
import com.topcoder.direct.services.copilot.model.CopilotProject;
import com.topcoder.direct.services.copilot.model.CopilotProjectPlan;
import com.topcoder.direct.services.copilot.model.PlannedContest;
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
 * <p>Tests <code>{@link CopilotProjectPlanDAOImpl}</code> class.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class CopilotProjectPlanDAOImplTests {

    /**
     * <p>Represents instance of tested class.</p>
     */
    @Autowired
    private CopilotProjectPlanDAOImpl instance;

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
        return new JUnit4TestAdapter(CopilotProjectPlanDAOImplTests.class);
    }

    /**
     * <p>Tests <code>{@link CopilotProjectPlanDAOImpl#CopilotProjectPlanDAOImpl()}</code> constructor.</p>
     */
    @Test
    public void testCtor() {
        Assert.assertNotNull("Object not instantiated.", instance);
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProjectPlanDAOImpl#create(com.topcoder.direct.services.copilot.model.IdentifiableEntity)}</code>
     * method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testCreate1() throws CopilotDAOException {

        CopilotProfile copilotProfile = TestHelper.createCopilotProfile();
        CopilotProject copilotProject = TestHelper.createCopilotProject();

        TestHelper.persistCopilotProjectDependencies(hibernateTemplate, copilotProfile, copilotProject);
        hibernateTemplate.save(copilotProject);

        CopilotProjectPlan copilotProjectPlan = createCopilotProjectPlan();
        copilotProjectPlan.setCopilotProjectId(copilotProject.getId());

        instance.create(copilotProjectPlan);

        List<CopilotProjectPlan> result = (List<CopilotProjectPlan>) hibernateTemplate.find(
                "from CopilotProjectPlan where id = ?", copilotProjectPlan.getId());

        assertCopilotProjectPlan(copilotProjectPlan, result.get(0));
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProjectPlanDAOImpl#create(com.topcoder.direct.services.copilot.model.IdentifiableEntity)}</code>
     * method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testCreate2() throws CopilotDAOException {

        CopilotProfile copilotProfile = TestHelper.createCopilotProfile();
        CopilotProject copilotProject = TestHelper.createCopilotProject();

        TestHelper.persistCopilotProjectDependencies(hibernateTemplate, copilotProfile, copilotProject);
        hibernateTemplate.save(copilotProject);

        CopilotProjectPlan copilotProjectPlan = createCopilotProjectPlan();
        copilotProjectPlan.setCopilotProjectId(copilotProject.getId());

        PlannedContest plannedContest = TestHelper.createPlannedContest();
        copilotProjectPlan.getPlannedContests().add(plannedContest);

        instance.create(copilotProjectPlan);

        List<CopilotProjectPlan> result = (List<CopilotProjectPlan>) hibernateTemplate.find(
                "from CopilotProjectPlan where id = ?", copilotProjectPlan.getId());

        assertCopilotProjectPlan(copilotProjectPlan, result.get(0));
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProjectPlanDAOImpl#create(com.topcoder.direct.services.copilot.model.IdentifiableEntity)}</code>
     * method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testCreateFailure1() throws CopilotDAOException {

        CopilotProjectPlan copilotProjectPlan = createCopilotProjectPlan();
        copilotProjectPlan.setId(1);

        instance.create(copilotProjectPlan);
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProjectPlanDAOImpl#create(com.topcoder.direct.services.copilot.model.IdentifiableEntity)}</code>
     * method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testCreateFailure2() throws CopilotDAOException {

        CopilotProjectPlan copilotProjectPlan = createCopilotProjectPlan();

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("invalidApplicationContext.xml");
        CopilotProjectPlanDAOImpl copilotProjectPlanDAO = new CopilotProjectPlanDAOImpl();
        copilotProjectPlanDAO.setSessionFactory((SessionFactory) applicationContext.getBean("sessionFactory"));

        copilotProjectPlanDAO.create(copilotProjectPlan);
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProjectPlanDAOImpl#create(com.topcoder.direct.services.copilot.model.IdentifiableEntity)}</code>
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
     * CopilotProjectPlanDAOImpl#update(com.topcoder.direct.services.copilot.model.IdentifiableEntity)}</code>
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

        CopilotProjectPlan copilotProjectPlan = createCopilotProjectPlan();
        copilotProjectPlan.setCopilotProjectId(copilotProject.getId());
        hibernateTemplate.save(copilotProjectPlan);

        PlannedContest plannedContest = TestHelper.createPlannedContest();
        copilotProjectPlan.getPlannedContests().add(plannedContest);

        copilotProjectPlan.setPlannedBugRaces(20);
        copilotProjectPlan.setPlannedDuration(40);

        instance.update(copilotProjectPlan);

        List<CopilotProjectPlan> result = (List<CopilotProjectPlan>) hibernateTemplate.find(
                "from CopilotProjectPlan where id = ?", copilotProjectPlan.getId());

        assertCopilotProjectPlan(copilotProjectPlan, result.get(0));
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProjectPlanDAOImpl#update(com.topcoder.direct.services.copilot.model.IdentifiableEntity)}</code>
     * method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testUpdateFailure1() throws CopilotDAOException {

        CopilotProjectPlan copilotProjectPlan = createCopilotProjectPlan();

        Session session = instance.getSession();
        session.save(copilotProjectPlan);
        session.delete(copilotProjectPlan);

        instance.update(copilotProjectPlan);
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProjectPlanDAOImpl#update(com.topcoder.direct.services.copilot.model.IdentifiableEntity)}</code>
     * method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testUpdateFailure2() throws CopilotDAOException {

        CopilotProjectPlan copilotProjectPlan = createCopilotProjectPlan();
        copilotProjectPlan.setId(1L);

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("invalidApplicationContext.xml");
        CopilotProjectPlanDAOImpl copilotProjectPlanDAO = new CopilotProjectPlanDAOImpl();
        copilotProjectPlanDAO.setSessionFactory((SessionFactory) applicationContext.getBean("sessionFactory"));

        copilotProjectPlanDAO.update(copilotProjectPlan);
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProjectPlanDAOImpl#update(com.topcoder.direct.services.copilot.model.IdentifiableEntity)}</code>
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
     * CopilotProjectPlanDAOImpl#update(com.topcoder.direct.services.copilot.model.IdentifiableEntity)}</code>
     * method when entity has negative id.</p>
     *
     * <p>{@link IllegalArgumentException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateNegative() throws CopilotDAOException {
        CopilotProjectPlan copilotProjectPlan = new CopilotProjectPlan();
        copilotProjectPlan.setId(-1);
        instance.update(copilotProjectPlan);
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProjectPlanDAOImpl#update(com.topcoder.direct.services.copilot.model.IdentifiableEntity)}</code>
     * method when entity has id set to 0.</p>
     *
     * <p>{@link IllegalArgumentException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateZero() throws CopilotDAOException {
        CopilotProjectPlan copilotProjectPlan = new CopilotProjectPlan();

        instance.update(copilotProjectPlan);
    }

    /**
     * <p>Tests <code>{@link CopilotProjectPlanDAOImpl#delete(long)}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testDelete1() throws CopilotDAOException {

        CopilotProfile copilotProfile = TestHelper.createCopilotProfile();
        CopilotProject copilotProject = TestHelper.createCopilotProject();

        TestHelper.persistCopilotProjectDependencies(hibernateTemplate, copilotProfile, copilotProject);
        hibernateTemplate.save(copilotProject);

        CopilotProjectPlan copilotProjectPlan1 = createCopilotProjectPlan();
        copilotProjectPlan1.setCopilotProjectId(copilotProject.getId());
        hibernateTemplate.save(copilotProjectPlan1);
        CopilotProjectPlan copilotProjectPlan2 = createCopilotProjectPlan();
        copilotProjectPlan2.setCopilotProjectId(copilotProject.getId());
        hibernateTemplate.save(copilotProjectPlan2);

        instance.delete(copilotProjectPlan1.getId());

        List<CopilotProjectPlan> result =
                hibernateTemplate.find("from CopilotProjectPlan where id = ?", copilotProjectPlan1.getId());

        Assert.assertEquals("One entity in database should still exist.", 1,
                hibernateTemplate.find("from CopilotProjectPlan").size());
        Assert.assertEquals("Entity was not deleted.", 0, result.size());

        instance.delete(copilotProjectPlan2.getId());

        result = hibernateTemplate.find("from CopilotProjectPlan where id = ?", copilotProjectPlan2.getId());

        Assert.assertEquals("None entity in database should exist.", 0,
                hibernateTemplate.find("from CopilotProjectPlan").size());
        Assert.assertEquals("Entity was not deleted.", 0, result.size());
    }

    /**
     * <p>Tests <code>{@link CopilotProjectPlanDAOImpl#delete(long)}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testDelete2() throws CopilotDAOException {

        CopilotProfile copilotProfile = TestHelper.createCopilotProfile();
        CopilotProject copilotProject = TestHelper.createCopilotProject();

        TestHelper.persistCopilotProjectDependencies(hibernateTemplate, copilotProfile, copilotProject);
        hibernateTemplate.save(copilotProject);

        CopilotProjectPlan copilotProjectPlan = createCopilotProjectPlan();
        copilotProjectPlan.setCopilotProjectId(copilotProject.getId());

        PlannedContest plannedContest = TestHelper.createPlannedContest();
        copilotProjectPlan.getPlannedContests().add(plannedContest);
        hibernateTemplate.save(copilotProjectPlan);

        instance.delete(copilotProjectPlan.getId());

        Assert.assertEquals("None entity in database should exist.", 0,
                hibernateTemplate.find("from CopilotProjectPlan").size());
        Assert.assertEquals("None entity in database should exist.", 0,
                hibernateTemplate.find("from PlannedContest").size());
    }

    /**
     * <p>Tests <code>{@link CopilotProjectPlanDAOImpl#delete(long)}</code> method.</p>
     *
     * <p>{@link EntityNotFoundException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = EntityNotFoundException.class)
    public void testDeleteFailure1() throws CopilotDAOException {

        instance.delete(1L);
    }

    /**
     * <p>Tests <code>{@link CopilotProjectPlanDAOImpl#delete(long)}</code> method.</p>
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
     * <p>Tests <code>{@link CopilotProjectPlanDAOImpl#delete(long)}</code> method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testDeleteFailure3() throws CopilotDAOException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("invalidApplicationContext.xml");
        CopilotProjectPlanDAOImpl copilotProjectPlanDAO = new CopilotProjectPlanDAOImpl();
        copilotProjectPlanDAO.setSessionFactory((SessionFactory) applicationContext.getBean("sessionFactory"));

        copilotProjectPlanDAO.delete(1L);
    }

    /**
     * <p>Tests <code>{@link CopilotProjectPlanDAOImpl#delete(long)}</code> method when entity id is negative.</p>
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
     * <p>Tests <code>{@link CopilotProjectPlanDAOImpl#delete(long)}</code> method when entity id is 0.</p>
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
     * <p>Tests <code>{@link CopilotProjectPlanDAOImpl#retrieve(long)}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void testRetrieve1() throws CopilotDAOException {

        CopilotProfile copilotProfile = TestHelper.createCopilotProfile();
        CopilotProject copilotProject = TestHelper.createCopilotProject();

        TestHelper.persistCopilotProjectDependencies(hibernateTemplate, copilotProfile, copilotProject);
        hibernateTemplate.save(copilotProject);

        CopilotProjectPlan copilotProjectPlan1 = createCopilotProjectPlan();
        copilotProjectPlan1.setCopilotProjectId(copilotProject.getId());
        hibernateTemplate.save(copilotProjectPlan1);
        CopilotProjectPlan copilotProjectPlan2 = createCopilotProjectPlan();
        copilotProjectPlan2.setCopilotProjectId(copilotProject.getId());
        hibernateTemplate.save(copilotProjectPlan2);

        CopilotProjectPlan result = instance.retrieve(copilotProjectPlan1.getId());

        assertCopilotProjectPlan(copilotProjectPlan1, result);

        result = instance.retrieve(copilotProjectPlan2.getId());

        assertCopilotProjectPlan(copilotProjectPlan2, result);
    }

    /**
     * <p>Tests <code>{@link CopilotProjectPlanDAOImpl#retrieve(long)}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void testRetrieve2() throws CopilotDAOException {

        Assert.assertNull("Null was expected.", instance.retrieve(1L));
    }

    /**
     * <p>Tests <code>{@link CopilotProjectPlanDAOImpl#retrieve(long)}</code> method.</p>
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
     * <p>Tests <code>{@link CopilotProjectPlanDAOImpl#retrieve(long)}</code> method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testRetrieveFailure2() throws CopilotDAOException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("invalidApplicationContext.xml");
        CopilotProjectPlanDAOImpl copilotProjectPlanDAO = new CopilotProjectPlanDAOImpl();
        copilotProjectPlanDAO.setSessionFactory((SessionFactory) applicationContext.getBean("sessionFactory"));

        copilotProjectPlanDAO.retrieve(1L);
    }

    /**
     * <p>Tests <code>{@link CopilotProjectPlanDAOImpl#retrieve(long)}</code> method when entity id is negative.</p>
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
     * <p>Tests <code>{@link CopilotProjectPlanDAOImpl#retrieve(long)}</code> method when entity id is 0.</p>
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
     * <p>Tests <code>{@link CopilotProjectPlanDAOImpl#retrieveAll()}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void testRetrieveAll1() throws CopilotDAOException {

        CopilotProfile copilotProfile = TestHelper.createCopilotProfile();
        CopilotProject copilotProject = TestHelper.createCopilotProject();

        TestHelper.persistCopilotProjectDependencies(hibernateTemplate, copilotProfile, copilotProject);
        hibernateTemplate.save(copilotProject);

        CopilotProjectPlan copilotProjectPlan1 = createCopilotProjectPlan();
        copilotProjectPlan1.setCopilotProjectId(copilotProject.getId());
        hibernateTemplate.save(copilotProjectPlan1);
        CopilotProjectPlan copilotProjectPlan2 = createCopilotProjectPlan();
        copilotProjectPlan2.setCopilotProjectId(copilotProject.getId());
        hibernateTemplate.save(copilotProjectPlan2);

        List<CopilotProjectPlan> result = instance.retrieveAll();

        Assert.assertEquals("Two entities were expected.", 2, result.size());
        assertCopilotProjectPlan(copilotProjectPlan1, result.get(0));
        assertCopilotProjectPlan(copilotProjectPlan2, result.get(1));
    }

    /**
     * <p>Tests <code>{@link CopilotProjectPlanDAOImpl#retrieveAll()}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void testRetrieveAll2() throws CopilotDAOException {

        CopilotProfile copilotProfile = TestHelper.createCopilotProfile();
        CopilotProject copilotProject = TestHelper.createCopilotProject();

        TestHelper.persistCopilotProjectDependencies(hibernateTemplate, copilotProfile, copilotProject);
        hibernateTemplate.save(copilotProject);

        CopilotProjectPlan copilotProjectPlan1 = createCopilotProjectPlan();
        copilotProjectPlan1.setCopilotProjectId(copilotProject.getId());
        hibernateTemplate.save(copilotProjectPlan1);
        CopilotProjectPlan copilotProjectPlan2 = createCopilotProjectPlan();
        copilotProjectPlan2.setCopilotProjectId(copilotProject.getId());
        hibernateTemplate.save(copilotProjectPlan2);

        List<CopilotProjectPlan> result = instance.retrieveAll();

        Assert.assertEquals("Two entities were expected.", 2, result.size());
        assertCopilotProjectPlan(copilotProjectPlan1, result.get(0));
        assertCopilotProjectPlan(copilotProjectPlan2, result.get(1));

        hibernateTemplate.delete(copilotProjectPlan1);

        result = instance.retrieveAll();

        Assert.assertEquals("One entity was expected.", 1, result.size());
        assertCopilotProjectPlan(copilotProjectPlan2, result.get(0));

        hibernateTemplate.delete(copilotProjectPlan2);

        Assert.assertEquals("No entities were expected.", 0, instance.retrieveAll().size());
    }

    /**
     * <p>Tests <code>{@link CopilotProjectPlanDAOImpl#retrieveAll()}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void testRetrieveAll3() throws CopilotDAOException {

        Assert.assertEquals("No entities were expected.", 0, instance.retrieveAll().size());
    }

    /**
     * <p>Tests <code>{@link CopilotProjectPlanDAOImpl#retrieveAll()}</code> method.</p>
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
     * <p>Tests <code>{@link CopilotProjectPlanDAOImpl#retrieveAll()}</code> method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testRetrieveAllFailure2() throws CopilotDAOException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("invalidApplicationContext.xml");
        CopilotProjectPlanDAOImpl copilotProjectPlanDAO = new CopilotProjectPlanDAOImpl();
        copilotProjectPlanDAO.setSessionFactory((SessionFactory) applicationContext.getBean("sessionFactory"));

        copilotProjectPlanDAO.retrieveAll();
    }

    /**
     * <p>Tests <code>{@link CopilotProjectPlanDAOImpl#getCopilotProjectPlan(long)}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void testGetCopilotProjectPlan1() throws CopilotDAOException {

        CopilotProfile copilotProfile1 = TestHelper.createCopilotProfile();
        CopilotProject copilotProject1 = TestHelper.createCopilotProject();

        TestHelper.persistCopilotProjectDependencies(hibernateTemplate, copilotProfile1, copilotProject1);
        hibernateTemplate.save(copilotProject1);

        CopilotProfile copilotProfile2 = TestHelper.createCopilotProfile();
        CopilotProject copilotProject2 = TestHelper.createCopilotProject();

        TestHelper.persistCopilotProjectDependencies(hibernateTemplate, copilotProfile2, copilotProject2);
        hibernateTemplate.save(copilotProject2);

        CopilotProjectPlan copilotProjectPlan1 = createCopilotProjectPlan();
        copilotProjectPlan1.setCopilotProjectId(copilotProject1.getId());
        hibernateTemplate.save(copilotProjectPlan1);
        CopilotProjectPlan copilotProjectPlan2 = createCopilotProjectPlan();
        copilotProjectPlan2.setCopilotProjectId(copilotProject2.getId());
        hibernateTemplate.save(copilotProjectPlan2);

        CopilotProjectPlan result = instance.getCopilotProjectPlan(copilotProjectPlan1.getCopilotProjectId());

        assertCopilotProjectPlan(copilotProjectPlan1, result);

        result = instance.getCopilotProjectPlan(copilotProjectPlan2.getCopilotProjectId());

        assertCopilotProjectPlan(copilotProjectPlan2, result);
    }

    /**
     * <p>Tests <code>{@link CopilotProjectPlanDAOImpl#getCopilotProjectPlan(long)}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void testGetCopilotProjectPlan2() throws CopilotDAOException {

        Assert.assertNull("No result was expected.", instance.getCopilotProjectPlan(1L));
    }

    /**
     * <p>Tests <code>{@link CopilotProjectPlanDAOImpl#getCopilotProjectPlan(long)}</code> method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testGetCopilotProjectPlanFailure1() throws CopilotDAOException {

        CopilotProfile copilotProfile = TestHelper.createCopilotProfile();
        CopilotProject copilotProject = TestHelper.createCopilotProject();

        TestHelper.persistCopilotProjectDependencies(hibernateTemplate, copilotProfile, copilotProject);
        hibernateTemplate.save(copilotProject);

        CopilotProjectPlan copilotProjectPlan1 = createCopilotProjectPlan();
        copilotProjectPlan1.setCopilotProjectId(copilotProject.getId());
        hibernateTemplate.save(copilotProjectPlan1);
        CopilotProjectPlan copilotProjectPlan2 = createCopilotProjectPlan();
        copilotProjectPlan2.setCopilotProjectId(copilotProject.getId());
        hibernateTemplate.save(copilotProjectPlan2);

        instance.getCopilotProjectPlan(copilotProject.getId());
    }

    /**
     * <p>Tests <code>{@link CopilotProjectPlanDAOImpl#getCopilotProjectPlan(long)}</code> method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testGetCopilotProjectPlanFailure2() throws CopilotDAOException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("invalidApplicationContext.xml");
        CopilotProjectPlanDAOImpl copilotProjectPlanDAO = new CopilotProjectPlanDAOImpl();
        copilotProjectPlanDAO.setSessionFactory((SessionFactory) applicationContext.getBean("sessionFactory"));

        copilotProjectPlanDAO.getCopilotProjectPlan(1L);
    }

    /**
     * <p>Tests <code>{@link CopilotProjectPlanDAOImpl#getCopilotProjectPlan(long)}</code> method when copilotProjectId
     * is negative.</p>
     *
     * <p>{@link IllegalArgumentException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetCopilotProjectPlanNegative() throws CopilotDAOException {

        instance.getCopilotProjectPlan(-1L);
    }

    /**
     * <p>Tests <code>{@link CopilotProjectPlanDAOImpl#getCopilotProjectPlan(long)}</code> method when copilotProjectId
     * is 0.</p>
     *
     * <p>{@link IllegalArgumentException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetCopilotProjectPlanZero() throws CopilotDAOException {

        instance.getCopilotProjectPlan(0L);
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProjectPlanDAOImpl#updateAuditTimestamp(com.topcoder.direct.services.copilot.model.IdentifiableEntity
     * , boolean)}</code> method.</p>
     *
     * @throws Exception if any error occurs
     */
    @Test
    public void testUpdateAuditTimestamp1() throws Exception {

        CopilotProjectPlan copilotProjectPlan = createCopilotProjectPlan();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2010-01-30");
        copilotProjectPlan.setCreateDate(date);
        copilotProjectPlan.setModifyDate(date);

        instance.updateAuditTimestamp(copilotProjectPlan, true);

        Assert.assertFalse("Creation date was not changed", copilotProjectPlan.getCreateDate().equals(date));
        Assert.assertEquals("Modification date should be not changed", date, copilotProjectPlan.getModifyDate());
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProjectPlanDAOImpl#updateAuditTimestamp(com.topcoder.direct.services.copilot.model.IdentifiableEntity
     * , boolean)}</code> method.</p>
     *
     * @throws Exception if any error occurs
     */
    @Test
    public void testUpdateAuditTimestamp2() throws Exception {

        CopilotProjectPlan copilotProjectPlan = createCopilotProjectPlan();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2010-01-30");
        copilotProjectPlan.setCreateDate(date);
        copilotProjectPlan.setModifyDate(date);

        instance.updateAuditTimestamp(copilotProjectPlan, false);

        Assert.assertEquals("Create date should be not changed", date, copilotProjectPlan.getCreateDate());
        Assert.assertFalse("Modification date was not changed", copilotProjectPlan.getModifyDate().equals(date));
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProjectPlanDAOImpl#updateAuditTimestamp(com.topcoder.direct.services.copilot.model.IdentifiableEntity
     * , boolean)}</code> method.</p>
     *
     * @throws Exception if any error occurs
     */
    @Test
    public void testUpdateAuditTimestamp3() throws Exception {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2010-01-30");

        PlannedContest plannedContest = new PlannedContest();
        plannedContest.setId(0);
        plannedContest.setName("test");
        plannedContest.setCreateDate(date);
        plannedContest.setModifyDate(date);
        plannedContest.setCreateUser("user");
        plannedContest.setModifyUser("user");

        CopilotProjectPlan copilotProjectPlan = createCopilotProjectPlan();
        copilotProjectPlan.getPlannedContests().add(plannedContest);

        copilotProjectPlan.setCreateDate(date);
        copilotProjectPlan.setModifyDate(date);

        instance.updateAuditTimestamp(copilotProjectPlan, true);

        Assert.assertFalse("Creation date was not changed", copilotProjectPlan.getCreateDate().equals(date));
        Assert.assertEquals("Modification date should be not changed", date, copilotProjectPlan.getModifyDate());

        Assert.assertFalse("Creation date was not changed", plannedContest.getCreateDate().equals(date));
        Assert.assertEquals("Modification date should be not changed", date, plannedContest.getModifyDate());
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProjectPlanDAOImpl#updateAuditTimestamp(com.topcoder.direct.services.copilot.model.IdentifiableEntity
     * , boolean)}</code> method.</p>
     *
     * @throws Exception if any error occurs
     */
    @Test
    public void testUpdateAuditTimestamp4() throws Exception {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2010-01-30");

        PlannedContest plannedContest = new PlannedContest();
        plannedContest.setId(1);
        plannedContest.setName("test");
        plannedContest.setCreateDate(date);
        plannedContest.setModifyDate(date);
        plannedContest.setCreateUser("user");
        plannedContest.setModifyUser("user");

        CopilotProjectPlan copilotProjectPlan = createCopilotProjectPlan();
        copilotProjectPlan.getPlannedContests().add(plannedContest);

        copilotProjectPlan.setCreateDate(date);
        copilotProjectPlan.setModifyDate(date);

        instance.updateAuditTimestamp(copilotProjectPlan, true);

        Assert.assertFalse("Creation date was not changed", copilotProjectPlan.getCreateDate().equals(date));
        Assert.assertEquals("Modification date should be not changed", date, copilotProjectPlan.getModifyDate());

        Assert.assertEquals("Create date should be not changed", date, plannedContest.getCreateDate());
        Assert.assertFalse("Modification date was not changed", plannedContest.getModifyDate().equals(date));
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProjectPlanDAOImpl#updateAuditTimestamp(com.topcoder.direct.services.copilot.model.IdentifiableEntity
     * , boolean)}</code> method.</p>
     *
     * @throws Exception if any error occurs
     */
    @Test
    public void testUpdateAuditTimestamp5() throws Exception {

        CopilotProjectPlan copilotProjectPlan = createCopilotProjectPlan();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2010-01-30");
        copilotProjectPlan.setCreateDate(date);
        copilotProjectPlan.setModifyDate(date);
        copilotProjectPlan.setPlannedContests(null);

        instance.updateAuditTimestamp(copilotProjectPlan, true);

        Assert.assertFalse("Creation date was not changed", copilotProjectPlan.getCreateDate().equals(date));
        Assert.assertEquals("Modification date should be not changed", date, copilotProjectPlan.getModifyDate());
    }

    /**
     * <p>Tests <code>{@link
     * CopilotProjectPlanDAOImpl#updateAuditTimestamp(com.topcoder.direct.services.copilot.model.IdentifiableEntity
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
     * <p>Asserts that the passed {@link CopilotProjectPlan} instances are exactly the same.</p>
     *
     * @param copilotProjectPlan project plan to check
     * @param result             project plan to check
     */
    private void assertCopilotProjectPlan(CopilotProjectPlan copilotProjectPlan, CopilotProjectPlan result) {
        Assert.assertEquals("Invalid CopilotProjectPlan id", copilotProjectPlan.getId(), result.getId());
        Assert.assertEquals("Invalid CopilotProjectPlan copilotProjectId", copilotProjectPlan.getCopilotProjectId(),
                result.getCopilotProjectId());
        Assert.assertEquals("Invalid CopilotProjectPlan plannedBugRaces", copilotProjectPlan.getPlannedBugRaces(),
                result.getPlannedBugRaces());
        Assert.assertEquals("Invalid CopilotProjectPlan plannedDuration", copilotProjectPlan.getPlannedDuration(),
                result.getPlannedDuration());

        Assert.assertEquals("Invalid CopilotProjectPlan create user", copilotProjectPlan.getCreateUser(),
                result.getCreateUser());
        Assert.assertEquals("Invalid CopilotProjectPlan modify user", copilotProjectPlan.getModifyUser(),
                result.getModifyUser());
    }

    /**
     * <p>Creates a test instance of {@link CopilotProjectPlan} filled with dummy data.</p>
     *
     * @return {@link CopilotProjectPlan} filled with dummy data
     */
    private CopilotProjectPlan createCopilotProjectPlan() {

        CopilotProjectPlan copilotProjectPlan = new CopilotProjectPlan();
        copilotProjectPlan.setCopilotProjectId(1L);
        copilotProjectPlan.setPlannedBugRaces(5);
        copilotProjectPlan.setPlannedDuration(10);
        copilotProjectPlan.setPlannedContests(new HashSet<PlannedContest>());

        copilotProjectPlan.setCreateDate(new Date());
        copilotProjectPlan.setModifyDate(new Date());
        copilotProjectPlan.setCreateUser("user");
        copilotProjectPlan.setModifyUser("user");

        return copilotProjectPlan;
    }
}
