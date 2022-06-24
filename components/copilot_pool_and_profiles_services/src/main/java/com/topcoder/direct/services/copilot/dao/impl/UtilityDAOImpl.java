/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.dao.impl;

import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.CopilotDAOInitializationException;
import com.topcoder.direct.services.copilot.dao.UtilityDAO;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>This class is an implementation of UtilityDAO that uses Hibernate sessions to access databases. It uses 3 data
 * sources for the following databases: TCS Catalog (session factory for this DB is stored in BaseDAO), TopCoder DW
 * (contains payments data) and JIRA DB. Data from these databases are accessed by this class using native SQL queries.
 * This class uses Logging Wrapper logger to log errors and debug information.</p>
 *
 * <p><strong>Configuration: </strong>This class will be used with Spring IoC and will be configured using Spring xml
 * file, sample bean definition:
 *
 * <pre>
 * &lt;bean id="utilityDAO"
 *        class="com.topcoder.direct.services.copilot.dao.impl.UtilityDAOImpl"&gt;
 *      &lt;property name="loggerName" value="myLogger"/&gt;
 *      &lt;property name="sessionFactory" ref="tcsCatalogSessionFactory"/&gt;
 *      &lt;property name="jiraSessionFactory" ref="jiraSessionFactory"/&gt;
 *      &lt;property name="paymentSessionFactory" ref="paymentSessionFactory"/&gt;
 *      &lt;property name="copilotPaymentTypeId"&gt;
 *          &lt;value&gt;20&lt;/value&gt;
 *      &lt;/property&gt;
 *      &lt;property name="copilotResourceRoleId"&gt;
 *          &lt;value&gt;14&lt;/value&gt;
 *      &lt;/property&gt;
 *      &lt;property name="userResourceInfoTypeId"&gt;
 *          &lt;value&gt;1&lt;/value&gt;
 *      &lt;/property&gt;
 *  &lt;/bean&gt;
 * </pre>
 * </p>
 *
 * <p><strong>Sample usage:</strong>
 * <pre>
 * // Retrieves LookupDAO from the Spring application context
 * ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
 * UtilityDAO utilityDAO = (UtilityDAO) context.getBean("lookupDAO");
 *
 * // Retrieves the bug count for contest with ID=1001
 * int contestBugCount = utilityDAO.getContestBugCount(1001);
 *
 * // Retrieves the copilot earnings for copilot user with ID=1
 * double copilotEarnings = utilityDAO.getCopilotEarnings(1);
 *
 * // Retrieves the latest bug resolution date for contest with ID=1
 * Date contestLatestBugResolutionDate = utilityDAO.getContestLatestBugResolutionDate(1);
 *
 * // Retrieves the IDs of OR contests for copilot with user ID=1
 * // and TC direct project with ID=101
 * long[] contestIds = utilityDAO.getCopilotProjectContests(1, 101);
 * </pre>
 * </p>
 *
 * <p><strong>Thread safety:</strong> This class has mutable attributes, thus it's not thread safe. But it's assumed
 * that it will be initialized via Spring IoC before calling any business method, this way it's always used in thread
 * safe manner. It uses thread safe SessionFactory, Session and Log instances.</p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class UtilityDAOImpl extends BaseDAO implements UtilityDAO {

    /**
     * <p>Represents class name.</p>
     */
    private static final String CLASS_NAME = "UtilityDAOImpl";

    /**
     * <p>Represents a sql query used for retrieving bug count for given contest from jira database.</p>
     */
    private static final String SELECT_BUG_COUNT =
            "select count(i.id) from jiraissue i join customfieldvalue fv on fv.issue = i.id"
                    + " join customfield cf on cf.id = fv.customfield where cf.cfname = 'ProjectID'"
                    + " and fv.numbervalue = :contestId";

    /**
     * <p>Represents a sql query used for retrieving copilot earnings from payment database.</p>
     */
    private static final String SELECT_EARNINGS =
            "select sum(up.gross_amount) from payment p join user_payment up on p.payment_id = up.payment_id"
                    + " where p.payment_type_id = :copilotPaymentTypeId and up.user_id = :userId";

    /**
     * <p>Represents a sql query used for retrieving latest bug resolution date from jira database.</p>
     */
    private static final String SELECT_LATEST_BUG_RESOLUTION_DATE =
            "select max(fv2.datevalue) from jiraissue ji "
                    + " join customfieldvalue fv1 on fv1.issue = ji.id "
                    + " join customfield cf1 on cf1.id = fv1.customfield "
                    + " join customfieldvalue fv2 on fv2.issue = ji.id "
                    + " join customfield cf2 on cf2.id = fv2.customfield "
                    + " where cf1.cfname = 'ProjectID' and fv1.numbervalue = :contestId"
                    + " and cf2.cfname = 'Resolution Date'";

    /**
     * <p>Represents a sql query used for retrieving project associated with specified contest id from database.</p>
     */
    private static final String SELECT_COPILOT_PROJECT_CONTESTS =
            "select distinct(p.project_id) from project p "
                    + " join resource r on r.project_id = p.project_id "
                    + " join resource_info ri on ri.resource_id = r.resource_id "
                    + " where p.tc_direct_project_id = :tcDirectProjectId"
                    + " and r.resource_role_id = :copilotResourceRoleId"
                    + " and ri.resource_info_type_id = :userResourceInfoTypeId and ri.value = :copilotUserId";

    /**
     * <p>The Hibernate session factory to be used when retrieving session for accessing JIRA database. Cannot be null
     * after initialization (validation is performed in checkInit() method). Initialized by Spring setter injection. Has
     * protected getter and public setter. Is used in checkInit(), getContestBugCount() and
     * getContestLatestBugResolutionDate().</p>
     */
    private SessionFactory jiraSessionFactory;

    /**
     * <p>The Hibernate session factory to be used when retrieving session for accessing database with payments data.
     * Cannot be null after initialization (validation is performed in checkInit() method). Initialized by Spring setter
     * injection. Has protected getter and public setter. Is used in checkInit() and getCopilotEarnings().</p>
     */
    private SessionFactory paymentSessionFactory;

    /**
     * <p>The ID of the copilot payment type. Must be positive after initialization (validation is performed in
     * checkInit() method). Initialized by Spring setter injection. Has getter and setter. Is used in checkInit() and
     * getCopilotEarnings().</p>
     */
    private long copilotPaymentTypeId;

    /**
     * <p>The ID of the copilot resource role. Must be positive after initialization (validation is performed in
     * checkInit() method). Initialized by Spring setter injection. Has getter and setter. Is used in checkInit() and
     * getCopilotProjectContests().</p>
     */
    private long copilotResourceRoleId;

    /**
     * <p>The ID of the user resource info type. Must be positive after initialization (validation is performed in
     * checkInit() method). Initialized by Spring setter injection. Has getter and setter. Is used in checkInit() and
     * getCopilotProjectContests().</p>
     */
    private long userResourceInfoTypeId;

    /**
     * <p>Creates new instance of <code>{@link UtilityDAOImpl}</code> class.</p>
     */
    public UtilityDAOImpl() {
        // empty constructor
    }

    /**
     * <p>Retrieves the Hibernate session factory to be used when retrieving session for accessing JIRA database.</p>
     *
     * @return the Hibernate session factory to be used when retrieving session for accessing JIRA database
     */
    protected SessionFactory getJiraSessionFactory() {
        return jiraSessionFactory;
    }

    /**
     * <p>Sets the Hibernate session factory to be used when retrieving session for accessing JIRA database.</p>
     *
     * @param jiraSessionFactory the Hibernate session factory to be used when retrieving session for accessing JIRA
     *                           database
     */
    public void setJiraSessionFactory(SessionFactory jiraSessionFactory) {
        this.jiraSessionFactory = jiraSessionFactory;
    }

    /**
     * <p>Retrieves the Hibernate session factory to be used when retrieving session for accessing database with
     * payments data.</p>
     *
     * @return the Hibernate session factory to be used when retrieving session for accessing database with payments
     *         data
     */
    protected SessionFactory getPaymentSessionFactory() {
        return paymentSessionFactory;
    }

    /**
     * <p>Sets the Hibernate session factory to be used when retrieving session for accessing database with payments
     * data.</p>
     *
     * @param paymentSessionFactory the Hibernate session factory to be used when retrieving session for accessing
     *                              database with payments data
     */
    public void setPaymentSessionFactory(SessionFactory paymentSessionFactory) {
        this.paymentSessionFactory = paymentSessionFactory;
    }

    /**
     * <p>Retrieves the ID of the copilot payment type.</p>
     *
     * @return the ID of the copilot payment type
     */
    public long getCopilotPaymentTypeId() {
        return copilotPaymentTypeId;
    }

    /**
     * <p>Sets the ID of the copilot payment type.</p>
     *
     * @param copilotPaymentTypeId the ID of the copilot payment type
     */
    public void setCopilotPaymentTypeId(long copilotPaymentTypeId) {
        this.copilotPaymentTypeId = copilotPaymentTypeId;
    }

    /**
     * <p>Retrieves the ID of the copilot resource role.</p>
     *
     * @return the ID of the copilot resource role
     */
    public long getCopilotResourceRoleId() {
        return copilotResourceRoleId;
    }

    /**
     * <p>Sets the ID of the copilot resource role.</p>
     *
     * @param copilotResourceRoleId the ID of the copilot resource role
     */
    public void setCopilotResourceRoleId(long copilotResourceRoleId) {
        this.copilotResourceRoleId = copilotResourceRoleId;
    }

    /**
     * <p>Retrieves the ID of the user resource info type.</p>
     *
     * @return the ID of the user resource info type
     */
    public long getUserResourceInfoTypeId() {
        return userResourceInfoTypeId;
    }

    /**
     * <p>Sets the ID of the user resource info type.</p>
     *
     * @param userResourceInfoTypeId the ID of the user resource info type
     */
    public void setUserResourceInfoTypeId(long userResourceInfoTypeId) {
        this.userResourceInfoTypeId = userResourceInfoTypeId;
    }

    /**
     * <p>Checks whether this class was initialized by Spring properly.</p>
     *
     * @throws CopilotDAOInitializationException
     *          if the class was not initialized properly - when one of sessionFactory, jiraSessionFactory or
     *          paymentSessionFactory is null or when loggerName is empty string or any of copilotPaymentTypeId,
     *          copilotResourceRoleId, userResourceInfoTypeId is not positive
     */
    @PostConstruct
    protected void checkInit() {
        // delegate to base class method to check inhabited properties
        super.checkInit();

        // check if all properties were set properly
        Helper.checkPropertyNotNull(jiraSessionFactory, "jiraSessionFactory");
        Helper.checkPropertyNotNull(paymentSessionFactory, "paymentSessionFactory");
        checkPropertyIsPositive(copilotPaymentTypeId, "copilotPaymentTypeId");
        checkPropertyIsPositive(copilotResourceRoleId, "copilotResourceRoleId");
        checkPropertyIsPositive(userResourceInfoTypeId, "userResourceInfoTypeId");
    }

    /**
     * <p>Retrieves the number of bugs for contest with the specified ID. Returns 0 if contest with the given ID doesn't
     * exist.</p>
     *
     * @param contestId the ID of the contest
     *
     * @return the retrieved contest bug count (not negative)
     *
     * @throws IllegalArgumentException if contestId <= 0
     * @throws CopilotDAOException      if any error occurs during persistence operation
     */
    public int getContestBugCount(long contestId) throws CopilotDAOException {
        final String methodName = "getContestBugCount";
        final long executionStart = System.currentTimeMillis();
        Helper.logMethodEntered(getLog(), CLASS_NAME, methodName, new String[]{"contestId"},
                new Object[]{contestId});

        Helper.checkIsPositive(getLog(), contestId, "contestId", CLASS_NAME, methodName);

        try {
            // get current session from jira session factory
            Session session = jiraSessionFactory.getCurrentSession();

            // execute query which returns count of contest bug
            Number result = (Number) session.createSQLQuery(SELECT_BUG_COUNT)
                    .setParameter("contestId", contestId)
                    .uniqueResult();

            // log method exit
            Helper.logMethodExited(getLog(), CLASS_NAME, methodName, executionStart, result.intValue());

            // return result as integer
            return result.intValue();
        } catch (HibernateException e) {
            CopilotDAOException exc = new CopilotDAOException(
                    MessageFormat.format("Error occurred when retrieving bug count for contestId {0}", contestId), e);

            Helper.logError(getLog(),
                    MessageFormat.format(Helper.METHOD_ERROR, CLASS_NAME, methodName), exc);

            throw exc;
        }
    }

    /**
     * <p>Retrieves the earnings of the copilot with the specified user ID.</p>
     *
     * @param userId the ID of the copilot user
     *
     * @return the retrieved copilot earnings (not negative)
     *
     * @throws IllegalArgumentException if userId <= 0
     * @throws CopilotDAOException      if any error occurs during persistence operation
     */
    public double getCopilotEarnings(long userId) throws CopilotDAOException {
        final String methodName = "getCopilotEarnings";
        final long executionStart = System.currentTimeMillis();
        Helper.logMethodEntered(getLog(), CLASS_NAME, methodName, new String[]{"userId"},
                new Object[]{userId});

        Helper.checkIsPositive(getLog(), userId, "userId", CLASS_NAME, methodName);

        try {
            // get current session from payment session factory
            Session session = paymentSessionFactory.getCurrentSession();

            // retrieve the copilot earnings
            Number numberResult = (Number) session.createSQLQuery(SELECT_EARNINGS)
                    .setParameter("copilotPaymentTypeId", copilotPaymentTypeId)
                    .setParameter("userId", userId)
                    .uniqueResult();

            // if numberResult was null then set return value to 0
            double result = numberResult != null ? numberResult.doubleValue() : 0D;

            // log method exit
            Helper.logMethodExited(getLog(), CLASS_NAME, methodName, executionStart, result);

            // return the result
            return result;
        } catch (HibernateException e) {
            CopilotDAOException exc = new CopilotDAOException(
                    MessageFormat.format("Error occurred when retrieving copilot earnings for userId {0}", userId), e);

            Helper.logError(getLog(),
                    MessageFormat.format(Helper.METHOD_ERROR, CLASS_NAME, methodName), exc);

            throw exc;
        }
    }

    /**
     * <p>Retrieves the latest bug resolution date for the contest. Returns null if contest with the given ID doesn't
     * exist.</p>
     *
     * @param contestId the ID of the contest
     *
     * @return the latest bug resolution date for the contest or null if the contest has no bugs
     *
     * @throws IllegalArgumentException if contestId <= 0
     * @throws CopilotDAOException      if any error occurs during persistence operation
     */
    public Date getContestLatestBugResolutionDate(long contestId) throws CopilotDAOException {
        final String methodName = "getContestLatestBugResolutionDate";
        final long executionStart = System.currentTimeMillis();
        Helper.logMethodEntered(getLog(), CLASS_NAME, methodName, new String[]{"contestId"},
                new Object[]{contestId});

        Helper.checkIsPositive(getLog(), contestId, "contestId", CLASS_NAME, methodName);

        try {
            // gets current session from jira session factory
            Session session = jiraSessionFactory.getCurrentSession();

            // executes the query and retrieves single result
            Date result = (Date) session.createSQLQuery(SELECT_LATEST_BUG_RESOLUTION_DATE)
                    .setParameter("contestId", contestId)
                    .uniqueResult();

            // logs method exit
            Helper.logMethodExited(getLog(), CLASS_NAME, methodName, executionStart, result);

            // returns the result
            return result;
        } catch (HibernateException e) {
            CopilotDAOException exc = new CopilotDAOException(MessageFormat.format(
                    "Error occurred when retrieving latest bug resolution date for contestId {0}", contestId), e);

            Helper.logError(getLog(),
                    MessageFormat.format(Helper.METHOD_ERROR, CLASS_NAME, methodName), exc);

            throw exc;
        }
    }

    /**
     * <p>Retrieves IDs of all contests for the specified copilot user and TC direct project. Returns an empty array if
     * copilot user or TC direct project with the specified ID doesn't exist.</p>
     *
     * @param copilotUserId     the ID of the copilot user
     * @param tcDirectProjectId the ID of the TC direct project
     *
     * @return the retrieved IDs of copilot project contests (not null)
     *
     * @throws IllegalArgumentException if copilotUserId <= 0 or tcDirectProjectId <= 0
     * @throws CopilotDAOException      if any error occurs during persistence operation
     */
    @SuppressWarnings("unchecked")
    public long[] getCopilotProjectContests(long copilotUserId, long tcDirectProjectId) throws CopilotDAOException {
        final String methodName = "getCopilotProjectContests";
        final long executionStart = System.currentTimeMillis();
        Helper.logMethodEntered(getLog(), CLASS_NAME, methodName, new String[]{"copilotUserId", "tcDirectProjectId"},
                new Object[]{copilotUserId, tcDirectProjectId});

        Helper.checkIsPositive(getLog(), copilotUserId, "copilotUserId", CLASS_NAME, methodName);
        Helper.checkIsPositive(getLog(), tcDirectProjectId, "tcDirectProjectId", CLASS_NAME, methodName);

        try {
            // gets current session
            Session session = getSession();

            // retrieves ids of projects
            List<Number> projectIds = session.createSQLQuery(SELECT_COPILOT_PROJECT_CONTESTS)
                    .setParameter("tcDirectProjectId", tcDirectProjectId)
                    .setParameter("copilotResourceRoleId", copilotResourceRoleId)
                    .setParameter("userResourceInfoTypeId", userResourceInfoTypeId)
                    .setParameter("copilotUserId", Long.toString(copilotUserId))
                    .list();

            // converts id list into array
            long[] result = new long[projectIds.size()];

            int i = 0;
            for (Number projectId : projectIds) {
                result[i] = projectId.longValue();
                i++;
            }

            // logs method exit
            Helper.logMethodExited(getLog(), CLASS_NAME, methodName, executionStart, result);

            // return result
            return result;
        } catch (HibernateException e) {
            CopilotDAOException exc = new CopilotDAOException(MessageFormat.format(
                    "Error occurred when retrieving copilot contests with copilotUserId {0} and tcDirectProjectId {1}",
                    copilotUserId, tcDirectProjectId), e);

            Helper.logError(getLog(),
                    MessageFormat.format(Helper.METHOD_ERROR, CLASS_NAME, methodName), exc);

            throw exc;
        } catch (CopilotDAOException e) {

            Helper.logError(getLog(), MessageFormat.format(Helper.METHOD_ERROR, CLASS_NAME, methodName), e);

            throw e;
        }
    }

    /**
     * <p>Checks if passed parameter is positive. If property is less or equal to 0, then
     * CopilotDAOInitializationException is thrown.</p>
     *
     * @param property     property to check
     * @param propertyName property name used in exception error message
     *
     * @throws CopilotDAOInitializationException if the passed property is not positive
     */
    private static void checkPropertyIsPositive(long property, String propertyName) {

        if (property <= 0) {
            throw new CopilotDAOInitializationException(MessageFormat.format("Property {0} must be positive.",
                    propertyName));
        }
    }
}
