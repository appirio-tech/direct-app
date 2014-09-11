/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.copilot.dao.impl;

import com.topcoder.direct.services.copilot.TestHelper;
import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.CopilotDAOInitializationException;
import junit.framework.JUnit4TestAdapter;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
import java.util.HashSet;
import java.util.Set;

/**
 * <p>Tests <code>{@link UtilityDAOImpl}</code> class.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class UtilityDAOImplTests {

    /**
     * <p>Represents logger name used for testing.</p>
     */
    private static final String LOGGER_NAME = "loggerName";

    /**
     * <p>Represents instance of tested class.</p>
     */
    @Autowired
    private UtilityDAOImpl instance;

    /**
     * <p>Represents instance of {@link org.springframework.orm.hibernate3.HibernateTemplate} used for testing.</p>
     */
    private HibernateTemplate hibernateTemplate;

    /**
     * <p>Sets instance of {@link org.hibernate.SessionFactory} used by this test case.</p>
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
        return new JUnit4TestAdapter(UtilityDAOImplTests.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception if any error occurs
     */
    @Before
    public void setUp() throws Exception {

        TestHelper.executeBatch(getSessionFactory(), "test_files/jira_delete.sql");
        TestHelper.executeBatch(getSessionFactory(), "test_files/tc_delete.sql");
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception if any error occurs
     */
    @After
    public void tearDown() throws Exception {

        TestHelper.executeBatch(getSessionFactory(), "test_files/jira_delete.sql");
        TestHelper.executeBatch(getSessionFactory(), "test_files/tc_delete.sql");
    }

    /**
     * <p>Tests <code>{@link UtilityDAOImpl#UtilityDAOImpl()}</code> constructor.</p>
     */
    @Test
    public void testCtor() {

        Assert.assertNotNull("Object not instantiated.", instance);
    }

    /**
     * <p>Tests <code>{@link UtilityDAOImpl#checkInit()}</code> method.</p>
     */
    @Test
    public void testCheckInit1() {

        instance.checkInit();
    }

    /**
     * <p>Tests <code>{@link UtilityDAOImpl#checkInit()}</code> method.</p>
     */
    @Test
    public void testCheckInit2() {

        UtilityDAOImpl utilityDAO = createUtilityDAOImpl();

        utilityDAO.setLoggerName(null);

        utilityDAO.checkInit();
    }

    /**
     * <p>Tests <code>{@link UtilityDAOImpl#checkInit()}</code> method.</p>
     *
     * <p>{@link CopilotDAOInitializationException} is expected.</p>
     *
     * @throws CopilotDAOInitializationException
     *          is the dao was not initialized properly
     */
    @Test(expected = CopilotDAOInitializationException.class)
    public void testCheckInitFailure1() {

        UtilityDAOImpl utilityDAO = createUtilityDAOImpl();

        utilityDAO.setSessionFactory(null);

        utilityDAO.checkInit();
    }

    /**
     * <p>Tests <code>{@link UtilityDAOImpl#checkInit()}</code> method.</p>
     *
     * <p>{@link CopilotDAOInitializationException} is expected.</p>
     *
     * @throws CopilotDAOInitializationException
     *          is the dao was not initialized properly
     */
    @Test(expected = CopilotDAOInitializationException.class)
    public void testCheckInitFailure2() {

        UtilityDAOImpl utilityDAO = createUtilityDAOImpl();

        utilityDAO.setLoggerName(" ");

        utilityDAO.checkInit();
    }

    /**
     * <p>Tests <code>{@link UtilityDAOImpl#checkInit()}</code> method.</p>
     *
     * <p>{@link CopilotDAOInitializationException} is expected.</p>
     *
     * @throws CopilotDAOInitializationException
     *          is the dao was not initialized properly
     */
    @Test(expected = CopilotDAOInitializationException.class)
    public void testCheckInitFailure3() {

        UtilityDAOImpl utilityDAO = createUtilityDAOImpl();

        utilityDAO.setJiraSessionFactory(null);

        utilityDAO.checkInit();
    }


    /**
     * <p>Tests <code>{@link UtilityDAOImpl#checkInit()}</code> method.</p>
     *
     * <p>{@link CopilotDAOInitializationException} is expected.</p>
     *
     * @throws CopilotDAOInitializationException
     *          is the dao was not initialized properly
     */
    @Test(expected = CopilotDAOInitializationException.class)
    public void testCheckInitFailure4() {

        UtilityDAOImpl utilityDAO = createUtilityDAOImpl();

        utilityDAO.setPaymentSessionFactory(null);

        utilityDAO.checkInit();
    }


    /**
     * <p>Tests <code>{@link UtilityDAOImpl#checkInit()}</code> method.</p>
     *
     * <p>{@link CopilotDAOInitializationException} is expected.</p>
     *
     * @throws CopilotDAOInitializationException
     *          is the dao was not initialized properly
     */
    @Test(expected = CopilotDAOInitializationException.class)
    public void testCheckInitFailure5() {

        UtilityDAOImpl utilityDAO = createUtilityDAOImpl();

        utilityDAO.setCopilotPaymentTypeId(-1);

        utilityDAO.checkInit();
    }


    /**
     * <p>Tests <code>{@link UtilityDAOImpl#checkInit()}</code> method.</p>
     *
     * <p>{@link CopilotDAOInitializationException} is expected.</p>
     *
     * @throws CopilotDAOInitializationException
     *          is the dao was not initialized properly
     */
    @Test(expected = CopilotDAOInitializationException.class)
    public void testCheckInitFailure6() {

        UtilityDAOImpl utilityDAO = createUtilityDAOImpl();

        utilityDAO.setCopilotPaymentTypeId(0);

        utilityDAO.checkInit();
    }


    /**
     * <p>Tests <code>{@link UtilityDAOImpl#checkInit()}</code> method.</p>
     *
     * <p>{@link CopilotDAOInitializationException} is expected.</p>
     *
     * @throws CopilotDAOInitializationException
     *          is the dao was not initialized properly
     */
    @Test(expected = CopilotDAOInitializationException.class)
    public void testCheckInitFailure7() {

        UtilityDAOImpl utilityDAO = createUtilityDAOImpl();

        utilityDAO.setCopilotResourceRoleId(-1);

        utilityDAO.checkInit();
    }


    /**
     * <p>Tests <code>{@link UtilityDAOImpl#checkInit()}</code> method.</p>
     *
     * <p>{@link CopilotDAOInitializationException} is expected.</p>
     *
     * @throws CopilotDAOInitializationException
     *          is the dao was not initialized properly
     */
    @Test(expected = CopilotDAOInitializationException.class)
    public void testCheckInitFailure8() {

        UtilityDAOImpl utilityDAO = createUtilityDAOImpl();

        utilityDAO.setCopilotResourceRoleId(0);

        utilityDAO.checkInit();
    }


    /**
     * <p>Tests <code>{@link UtilityDAOImpl#checkInit()}</code> method.</p>
     *
     * <p>{@link CopilotDAOInitializationException} is expected.</p>
     *
     * @throws CopilotDAOInitializationException
     *          is the dao was not initialized properly
     */
    @Test(expected = CopilotDAOInitializationException.class)
    public void testCheckInitFailure9() {

        UtilityDAOImpl utilityDAO = createUtilityDAOImpl();

        utilityDAO.setUserResourceInfoTypeId(-1);

        utilityDAO.checkInit();
    }


    /**
     * <p>Tests <code>{@link UtilityDAOImpl#checkInit()}</code> method.</p>
     *
     * <p>{@link CopilotDAOInitializationException} is expected.</p>
     *
     * @throws CopilotDAOInitializationException
     *          is the dao was not initialized properly
     */
    @Test(expected = CopilotDAOInitializationException.class)
    public void testCheckInitFailure10() {

        UtilityDAOImpl utilityDAO = createUtilityDAOImpl();

        utilityDAO.setUserResourceInfoTypeId(0);

        utilityDAO.checkInit();
    }

    /**
     * <p>Tests <code>jiraSessionFactory</code> property, tests both <code>{@link
     * UtilityDAOImpl#setJiraSessionFactory(SessionFactory)}</code>
     * and <code>{@link UtilityDAOImpl#getJiraSessionFactory()}</code> methods.</p>
     */
    @Test
    public void testJiraSessionFactoryProperty() {
        UtilityDAOImpl utilityDAO = new UtilityDAOImpl();
        Assert.assertNull("Property jiraSessionFactory should be null.", utilityDAO.getJiraSessionFactory());

        utilityDAO.setJiraSessionFactory(getSessionFactory());

        Assert.assertEquals("Invalid jiraSessionFactory property value.", getSessionFactory(),
                utilityDAO.getJiraSessionFactory());
    }

    /**
     * <p>Tests <code>paymentSessionFactory</code> property, tests both <code>{@link
     * UtilityDAOImpl#setPaymentSessionFactory(SessionFactory)}</code> and <code>{@link
     * UtilityDAOImpl#getPaymentSessionFactory()}</code> methods.</p>
     */
    @Test
    public void testPaymentSessionFactoryProperty() {
        UtilityDAOImpl utilityDAO = new UtilityDAOImpl();
        Assert.assertNull("Property paymentSessionFactory should be null.", utilityDAO.getPaymentSessionFactory());

        utilityDAO.setPaymentSessionFactory(getSessionFactory());

        Assert.assertEquals("Invalid paymentSessionFactory property value.", getSessionFactory(),
                utilityDAO.getPaymentSessionFactory());
    }

    /**
     * <p>Tests <code>copilotPaymentTypeId</code> property, tests both <code>{@link
     * UtilityDAOImpl#setCopilotPaymentTypeId(long)}</code> and <code>{@link
     * UtilityDAOImpl#getCopilotPaymentTypeId()}</code> methods.</p>
     */
    @Test
    public void testCopilotPaymentTypeIdProperty() {
        UtilityDAOImpl utilityDAO = new UtilityDAOImpl();
        Assert.assertEquals("Property copilotPaymentTypeId should be 0.", 0, utilityDAO.getCopilotPaymentTypeId());

        long copilotPaymentTypeId = 10l;
        utilityDAO.setCopilotPaymentTypeId(copilotPaymentTypeId);

        Assert.assertEquals("Invalid copilotPaymentTypeId property value.", copilotPaymentTypeId,
                utilityDAO.getCopilotPaymentTypeId());
    }

    /**
     * <p>Tests <code>copilotResourceRoleId</code> property, tests both <code>{@link
     * UtilityDAOImpl#setCopilotResourceRoleId(long)}</code> and <code>{@link
     * UtilityDAOImpl#getCopilotResourceRoleId()}</code> methods.</p>
     */
    @Test
    public void testCopilotResourceRoleIdProperty() {
        UtilityDAOImpl utilityDAO = new UtilityDAOImpl();
        Assert.assertEquals("Property copilotResourceRoleId should be 0.", 0, utilityDAO.getCopilotResourceRoleId());

        long copilotResourceRoleId = 10l;
        utilityDAO.setCopilotResourceRoleId(copilotResourceRoleId);

        Assert.assertEquals("Invalid copilotResourceRoleId property value.", copilotResourceRoleId,
                utilityDAO.getCopilotResourceRoleId());
    }

    /**
     * <p>Tests <code>userResourceInfoTypeId</code> property, tests both <code>{@link
     * UtilityDAOImpl#setUserResourceInfoTypeId(long)}</code> and <code>{@link
     * UtilityDAOImpl#getUserResourceInfoTypeId()}</code> methods.</p>
     */
    @Test
    public void testUserResourceInfoTypeIdProperty() {
        UtilityDAOImpl utilityDAO = new UtilityDAOImpl();
        Assert.assertEquals("Property userResourceInfoTypeId should be 0.", 0, utilityDAO.getUserResourceInfoTypeId());

        long userResourceInfoTypeId = 10l;
        utilityDAO.setUserResourceInfoTypeId(userResourceInfoTypeId);

        Assert.assertEquals("Invalid userResourceInfoTypeId property value.", userResourceInfoTypeId,
                utilityDAO.getUserResourceInfoTypeId());
    }

    /**
     * <p>Tests <code>{@link UtilityDAOImpl#getContestBugCount(long)}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void testGetContestBugCount1() throws CopilotDAOException {

        Assert.assertEquals("Expected 0 bugs for contest", 0, instance.getContestBugCount(1L));
    }

    /**
     * <p>Tests <code>{@link UtilityDAOImpl#getContestBugCount(long)}</code> method.</p>
     *
     * @throws Exception if any error occurs
     */
    @Test
    public void testGetContestBugCount2() throws Exception {
        TestHelper.executeBatch(getSessionFactory(), "test_files/jira_insert.sql");

        Assert.assertEquals("Expected 1 bug for contest", 1, instance.getContestBugCount(2L));
    }

    /**
     * <p>Tests <code>{@link UtilityDAOImpl#getContestBugCount(long)}</code> method.</p>
     *
     * @throws Exception if any error occurs
     */
    @Test
    public void testGetContestBugCount3() throws Exception {
        TestHelper.executeBatch(getSessionFactory(), "test_files/jira_insert.sql");

        Assert.assertEquals("Expected 0 bug for contest", 0, instance.getContestBugCount(3L));
    }

    /**
     * <p>Tests <code>{@link UtilityDAOImpl#getContestBugCount(long)}</code> method.</p>
     *
     * @throws Exception if any error occurs
     */
    @Test
    public void testGetContestBugCount4() throws Exception {
        TestHelper.executeBatch(getSessionFactory(), "test_files/jira_insert.sql");

        Assert.assertEquals("Expected 2 bug contests", 2, instance.getContestBugCount(1L));
    }

    /**
     * <p>Tests <code>{@link UtilityDAOImpl#getContestBugCount(long)}</code> method when contest id is negative.</p>
     *
     * <p>{@link IllegalArgumentException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetContestBugCountNegative() throws CopilotDAOException {

        instance.getContestBugCount(-1L);
    }

    /**
     * <p>Tests <code>{@link UtilityDAOImpl#getContestBugCount(long)}</code> method when contest id is 0.</p>
     *
     * <p>{@link IllegalArgumentException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetContestBugCountZero() throws CopilotDAOException {

        instance.getContestBugCount(0L);
    }

    /**
     * <p>Tests <code>{@link UtilityDAOImpl#getContestBugCount(long)}</code> method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws Exception if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testGetContestBugCountFailure() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("invalidApplicationContext.xml");
        UtilityDAOImpl utilityDAO = createUtilityDAOImpl();
        utilityDAO.setJiraSessionFactory((SessionFactory) applicationContext.getBean("sessionFactory"));

        utilityDAO.getContestBugCount(1L);
    }

    /**
     * <p>Tests <code>{@link UtilityDAOImpl#getContestLatestBugResolutionDate(long)}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void testGetContestLatestBugResolutionDate1() throws CopilotDAOException {

        Assert.assertNull("Null was expected.", instance.getContestLatestBugResolutionDate(1L));
    }

    /**
     * <p>Tests <code>{@link UtilityDAOImpl#getContestLatestBugResolutionDate(long)}</code> method.</p>
     *
     * @throws Exception if any error occurs
     */
    @Test
    public void testGetContestLatestBugResolutionDate2() throws Exception {
        TestHelper.executeBatch(getSessionFactory(), "test_files/jira_insert.sql");

        Assert.assertNull("Null was expected.", instance.getContestLatestBugResolutionDate(2L));
    }

    /**
     * <p>Tests <code>{@link UtilityDAOImpl#getContestLatestBugResolutionDate(long)}</code> method.</p>
     *
     * @throws Exception if any error occurs
     */
    @Test
    public void testGetContestLatestBugResolutionDate3() throws Exception {
        TestHelper.executeBatch(getSessionFactory(), "test_files/jira_insert.sql");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Assert.assertEquals("Method returned invalid date.", simpleDateFormat.parse("2010-09-02 12:00:00"),
                instance.getContestLatestBugResolutionDate(1L));
    }

    /**
     * <p>Tests <code>{@link UtilityDAOImpl#getContestLatestBugResolutionDate(long)}</code> method when contestId is
     * negative.</p>
     *
     * <p>{@link IllegalArgumentException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetContestLatestBugResolutionDateNegative() throws CopilotDAOException {

        instance.getContestLatestBugResolutionDate(-1L);
    }

    /**
     * <p>Tests <code>{@link UtilityDAOImpl#getContestLatestBugResolutionDate(long)}</code> method when contestId is
     * 0.</p>
     *
     * <p>{@link IllegalArgumentException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetContestLatestBugResolutionDateZero() throws CopilotDAOException {

        instance.getContestLatestBugResolutionDate(0L);
    }

    /**
     * <p>Tests <code>{@link UtilityDAOImpl#getContestLatestBugResolutionDate(long)}</code> method.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testGetContestLatestBugResolutionDateFailure() throws CopilotDAOException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("invalidApplicationContext.xml");
        UtilityDAOImpl utilityDAO = createUtilityDAOImpl();
        utilityDAO.setJiraSessionFactory((SessionFactory) applicationContext.getBean("sessionFactory"));

        utilityDAO.getContestLatestBugResolutionDate(1L);
    }

    /**
     * <p>Tests <code>{@link UtilityDAOImpl#getCopilotEarnings(long)}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void testGetCopilotEarnings1() throws CopilotDAOException {

        Assert.assertEquals("Expected earnings was 0.", 0, instance.getCopilotEarnings(1L), 0D);
    }

    /**
     * <p>Tests <code>{@link UtilityDAOImpl#getCopilotEarnings(long)}</code> method.</p>
     *
     * @throws Exception if any error occurs
     */
    @Test
    public void testGetCopilotEarnings2() throws Exception {
        TestHelper.executeBatch(getSessionFactory(), "test_files/tc_insert.sql");
        instance.setCopilotPaymentTypeId(1L);

        Assert.assertEquals("Expected earnings was 76.0.", 76D, instance.getCopilotEarnings(1L), 0D);
    }


    /**
     * <p>Tests <code>{@link UtilityDAOImpl#getCopilotEarnings(long)}</code> method.</p>
     *
     * @throws Exception if any error occurs
     */
    @Test
    public void testGetCopilotEarnings3() throws Exception {
        TestHelper.executeBatch(getSessionFactory(), "test_files/tc_insert.sql");
        instance.setCopilotPaymentTypeId(1L);

        Assert.assertEquals("Expected earnings was 25.5.", 25.5D, instance.getCopilotEarnings(2L), 0D);
    }

    /**
     * <p>Tests <code>{@link UtilityDAOImpl#getCopilotEarnings(long)}</code> method.</p>
     *
     * @throws Exception if any error occurs
     */
    @Test
    public void testGetCopilotEarnings4() throws Exception {
        TestHelper.executeBatch(getSessionFactory(), "test_files/tc_insert.sql");
        instance.setCopilotPaymentTypeId(2L);

        Assert.assertEquals("Expected earnings was 55.0.", 50.5D, instance.getCopilotEarnings(2L), 0D);
    }

    /**
     * <p>Tests <code>{@link UtilityDAOImpl#getCopilotEarnings(long)}</code> method when userId is negative.</p>
     *
     * <p>{@link IllegalArgumentException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetCopilotEarningsNegative() throws CopilotDAOException {

        instance.getCopilotEarnings(-1L);
    }

    /**
     * <p>Tests <code>{@link UtilityDAOImpl#getCopilotEarnings(long)}</code> method when userId is 0.</p>
     *
     * <p>{@link IllegalArgumentException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetCopilotEarningsZero() throws CopilotDAOException {

        instance.getCopilotEarnings(0L);
    }

    /**
     * <p>Tests <code>{@link UtilityDAOImpl#getCopilotEarnings(long)}</code> method when userId is negative.</p>
     *
     * <p>{@link CopilotDAOException} is expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testGetCopilotEarningsFailure() throws CopilotDAOException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("invalidApplicationContext.xml");
        UtilityDAOImpl utilityDAO = createUtilityDAOImpl();
        utilityDAO.setPaymentSessionFactory((SessionFactory) applicationContext.getBean("sessionFactory"));

        utilityDAO.getCopilotEarnings(1L);
    }

    /**
     * <p>Tests <code>{@link UtilityDAOImpl#getCopilotProjectContests(long, long)}</code> method.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void testGetCopilotProjectContests1() throws CopilotDAOException {

        long[] result = instance.getCopilotProjectContests(1L, 1L);
        Assert.assertNotNull("Result should not be null.", result);
        Assert.assertEquals("Expected contents count was 0.", 0, result.length);
    }

    /**
     * <p>Tests <code>{@link UtilityDAOImpl#getCopilotProjectContests(long, long)}</code> method.</p>
     *
     * @throws Exception if any error occurs
     */
    @Test
    public void testGetCopilotProjectContests2() throws Exception {
        TestHelper.executeBatch(getSessionFactory(), "test_files/tc_insert.sql");
        instance.setCopilotResourceRoleId(1L);
        instance.setUserResourceInfoTypeId(1L);

        long[] result = instance.getCopilotProjectContests(1L, 1L);
        Assert.assertNotNull("Result should not be null.", result);
        Assert.assertEquals("Expected contents count was 1.", 1, result.length);
        Assert.assertEquals("Expected project id was 1.", 1, result[0]);
    }

    /**
     * <p>Tests <code>{@link UtilityDAOImpl#getCopilotProjectContests(long, long)}</code> method.</p>
     *
     * @throws Exception if any error occurs
     */
    @Test
    public void testGetCopilotProjectContests3() throws Exception {
        TestHelper.executeBatch(getSessionFactory(), "test_files/tc_insert.sql");
        instance.setCopilotResourceRoleId(2L);
        instance.setUserResourceInfoTypeId(2L);

        long[] result = instance.getCopilotProjectContests(2L, 2L);
        Set<Long> resultSet = new HashSet<Long>();

        for (long id : result) {
            resultSet.add(id);
        }

        Assert.assertNotNull("Result should not be null.", result);
        Assert.assertEquals("Expected contents count was 2.", 2, result.length);
        Assert.assertTrue("Expected project id 2 is missing.", resultSet.contains(2L));
        Assert.assertTrue("Expected project id 3 is missing.", resultSet.contains(3L));
    }

    /**
     * <p>Tests <code>{@link UtilityDAOImpl#getCopilotProjectContests(long, long)}</code> method.</p>
     *
     * @throws Exception if any error occurs
     */
    @Test
    public void testGetCopilotProjectContests4() throws Exception {
        TestHelper.executeBatch(getSessionFactory(), "test_files/tc_insert.sql");

        long[] result = instance.getCopilotProjectContests(2L, 1L);
        Assert.assertNotNull("Result should not be null.", result);
        Assert.assertEquals("Expected contents count was 0.", 0, result.length);
    }

    /**
     * <p>Tests <code>{@link UtilityDAOImpl#getCopilotProjectContests(long, long)}</code> method when copilotUserId is
     * negative.</p>
     *
     * <p>{@link IllegalArgumentException} was expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetCopilotProjectContestsNegative1() throws CopilotDAOException {

        instance.getCopilotProjectContests(-1L, 1L);
    }

    /**
     * <p>Tests <code>{@link UtilityDAOImpl#getCopilotProjectContests(long, long)}</code> method when tcDirectProjectId
     * is negative.</p>
     *
     * <p>{@link IllegalArgumentException} was expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetCopilotProjectContestsNegative2() throws CopilotDAOException {

        instance.getCopilotProjectContests(1L, -1L);
    }

    /**
     * <p>Tests <code>{@link UtilityDAOImpl#getCopilotProjectContests(long, long)}</code> method when copilotUserId is
     * 0.</p>
     *
     * <p>{@link IllegalArgumentException} was expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetCopilotProjectContestsZero1() throws CopilotDAOException {

        instance.getCopilotProjectContests(0L, 1L);
    }

    /**
     * <p>Tests <code>{@link UtilityDAOImpl#getCopilotProjectContests(long, long)}</code> method when tcDirectProjectId
     * is 0.</p>
     *
     * <p>{@link IllegalArgumentException} was expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetCopilotProjectContestsZero2() throws CopilotDAOException {

        instance.getCopilotProjectContests(1L, 0L);
    }

    /**
     * <p>Tests <code>{@link UtilityDAOImpl#getCopilotProjectContests(long, long)}</code> method.</p>
     *
     * <p>{@link CopilotDAOException} was expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testGetCopilotProjectContestsFailure1() throws CopilotDAOException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("invalidApplicationContext.xml");
        UtilityDAOImpl utilityDAO = createUtilityDAOImpl();
        utilityDAO.setSessionFactory((SessionFactory) applicationContext.getBean("sessionFactory"));

        utilityDAO.getCopilotProjectContests(1L, 1L);
    }

    /**
     * <p>Tests <code>{@link UtilityDAOImpl#getCopilotProjectContests(long, long)}</code> method.</p>
     *
     * <p>{@link CopilotDAOException} was expected.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test(expected = CopilotDAOException.class)
    public void testGetCopilotProjectContestsFailure2() throws CopilotDAOException {

        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("invalidConnectionApplicationContext.xml");
        UtilityDAOImpl utilityDAO = createUtilityDAOImpl();
        utilityDAO.setSessionFactory((SessionFactory) applicationContext.getBean("sessionFactory"));

        utilityDAO.getCopilotProjectContests(1L, 1L);
    }

    /**
     * <p>Creates a test instance of {@link UtilityDAOImpl} filled with dummy data.</p>
     *
     * @return {@link UtilityDAOImpl} filled with dummy data
     */
    private UtilityDAOImpl createUtilityDAOImpl() {
        UtilityDAOImpl utilityDAO = new UtilityDAOImpl();
        utilityDAO.setSessionFactory(getSessionFactory());
        utilityDAO.setLoggerName(LOGGER_NAME);
        utilityDAO.setCopilotPaymentTypeId(1L);
        utilityDAO.setCopilotResourceRoleId(2L);
        utilityDAO.setUserResourceInfoTypeId(3L);
        utilityDAO.setJiraSessionFactory(getSessionFactory());
        utilityDAO.setPaymentSessionFactory(getSessionFactory());

        return utilityDAO;
    }
}
