/*
 * Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.milestone.hibernate;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.direct.services.project.milestone.EntityNotFoundException;
import com.topcoder.direct.services.project.milestone.ProjectMilestoneManagementConfigurationException;
import com.topcoder.direct.services.project.milestone.ProjectMilestoneManagementException;
import com.topcoder.direct.services.project.milestone.ResponsiblePersonService;
import com.topcoder.direct.services.project.milestone.model.ResponsiblePerson;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * This class is an implementation of ResponsiblePersonService.
 * </p>
 * <p>
 * <b>Thread Safety:</b> Technically this class is not thread safe as it is mutable. But in practice, its state
 * won't change after the fields being injected through Spring, hence it is effectively thread safe when used in
 * Spring environment and initialized only once.
 * </p>
 * *
 * <p>
 * <b>Sample Config:</b>
 * <p/>
 * <pre>
 * &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
 * &lt;beans xmlns=&quot;http://www.springframework.org/schema/beans&quot;
 *     xmlns:xsi=&quot;http://www.w3.org/2001/XMLSchema-instance&quot; xmlns:util=&quot;
 *     http://www.springframework.org/schema/util&quot;
 *     xmlns:aop=&quot;http://www.springframework.org/schema/aop&quot; xmlns:tx=&quot;
 *     http://www.springframework.org/schema/tx&quot;
 *     xsi:schemaLocation=&quot;http://www.springframework.org/schema/beans
 *        http://www.springframework.org/schema/beans/spring-beans-2.5.xsd
 *        http://www.springframework.org/schema/util
 *        http://www.springframework.org/schema/util/spring-util-2.5.xsd
 *        http://www.springframework.org/schema/aop
 *        http://www.springframework.org/schema/aop/spring-aop-2.5.xsd
 *        http://www.springframework.org/schema/tx
 *        http://www.springframework.org/schema/tx/spring-tx-2.5.xsd&quot;&gt;
 *
 *     &lt;bean id=&quot;dataSource&quot; class=&quot;org.apache.commons.dbcp.BasicDataSource&quot;&gt;
 *         &lt;property name=&quot;driverClassName&quot; value=&quot;com.informix.jdbc.IfxDriver&quot; /&gt;
 *         &lt;property name=&quot;url&quot;
 *             value=&quot;jdbc:informix-sqli://192.168.1.107:9088/Test:INFORMIXSERVER=ol_svr_custom&quot; /&gt;
 *         &lt;property name=&quot;username&quot; value=&quot;informix&quot; /&gt;
 *         &lt;property name=&quot;password&quot; value=&quot;123456&quot; /&gt;
 *     &lt;/bean&gt;
 *
 *     &lt;bean id=&quot;sessionFactory&quot;
 *         class=&quot;org.springframework.orm.hibernate3.LocalSessionFactoryBean&quot;&gt;
 *         &lt;property name=&quot;dataSource&quot; ref=&quot;dataSource&quot; /&gt;
 *         &lt;property name=&quot;mappingResources&quot;&gt;
 *             &lt;list&gt;
 *                 &lt;value&gt;mapping/ResponsiblePerson.hbm.xml&lt;/value&gt;
 *                 &lt;value&gt;mapping/Milestone.hbm.xml&lt;/value&gt;
 *             &lt;/list&gt;
 *         &lt;/property&gt;
 *         &lt;property name=&quot;hibernateProperties&quot;&gt;
 *             &lt;props&gt;
 *                 &lt;prop key=&quot;hibernate.dialect&quot;&gt;org.hibernate.dialect.InformixDialect&lt;/prop&gt;
 *                 &lt;!-- prop key=&quot;hibernate.show_sql&quot;&gt;true&lt;/prop --&gt;
 *             &lt;/props&gt;
 *         &lt;/property&gt;
 *     &lt;/bean&gt;
 *
 *     &lt;bean id=&quot;logger&quot; class=&quot;com.topcoder.util.log.LogManager&quot;
 *         factory-method=&quot;getLog&quot;&gt;
 *         &lt;constructor-arg value=&quot;myLogger&quot; /&gt;
 *     &lt;/bean&gt;
 *
 *     &lt;bean id=&quot;directProjectMetadataService&quot;
 *         class=&quot;com.topcoder.direct.services.project.metadata.MockDirectProjectMetadataService&quot;&gt;
 *     &lt;/bean&gt;
 *
 *     &lt;bean id=&quot;milestoneService&quot;
 *         class=&quot;com.topcoder.direct.services.project.milestone.hibernate.HibernateMilestoneService&quot;&gt;
 *         &lt;property name=&quot;logger&quot; ref=&quot;logger&quot; /&gt;
 *         &lt;property name=&quot;sessionFactory&quot; ref=&quot;sessionFactory&quot; /&gt;
 *     &lt;/bean&gt;
 *
 *     &lt;bean id=&quot;responsiblePersonService&quot;
 *         class=&quot;com.topcoder.direct.services.project.milestone
 *         .hibernate.HibernateResponsiblePersonService&quot;&gt;
 *         &lt;property name=&quot;logger&quot; ref=&quot;logger&quot; /&gt;
 *         &lt;property name=&quot;directProjectMetadataService&quot;
 *          ref=&quot;directProjectMetadataService&quot; /&gt;
 *     &lt;/bean&gt;
 *
 *
 *     &lt;!-- the transactional advice (what 'happens'; see the &lt;aop:advisor/&gt; bean
 *         below) --&gt;
 *     &lt;tx:advice id=&quot;txAdvice&quot; transaction-manager=&quot;txManager&quot;&gt;
 *         &lt;!-- the transactional semantics... --&gt;
 *         &lt;tx:attributes&gt;
 *             &lt;!-- all methods starting with 'get' are read-only --&gt;
 *             &lt;tx:method name=&quot;get*&quot; read-only=&quot;true&quot; /&gt;
 *             &lt;!-- other methods use the default transaction settings --&gt;
 *             &lt;tx:method name=&quot;*&quot; /&gt;
 *         &lt;/tx:attributes&gt;
 *     &lt;/tx:advice&gt;
 *
 *     &lt;bean id=&quot;txManager&quot;
 *         class=&quot;org.springframework.orm.hibernate3.HibernateTransactionManager&quot;&gt;
 *         &lt;property name=&quot;sessionFactory&quot; ref=&quot;sessionFactory&quot; /&gt;
 *     &lt;/bean&gt;
 *
 * &lt;/beans&gt;
 * </pre>
 * <p/>
 * </p>
 * <p>
 * <b>Sample Usage:</b>
 * <p/>
 * <pre>
 * // ResponsiblePersonService
 * ResponsiblePersonService responsiblePersonService = (ResponsiblePersonService) APP_CONTEXT
 *     .getBean(&quot;responsiblePersonService&quot;);
 *
 * List&lt;ResponsiblePerson&gt; people = responsiblePersonService.getAllResponsiblePeople(1);
 * </pre>
 * <p/>
 * </p>
 *
 * <p>
 *     Version 1.1 (Module Assembly - TC Cockpit Project Milestones Service Integration and Testing) update:
 *      - removes the reference to DataProvider and DirectProjectMetadataService.
 *      - in {@link #getAllResponsiblePeople(long)} use native sql query to get data
 *      - add method {@link #deleteResponsiblePerson(long)} (long)} to delete responsible person.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.1
 */
public class HibernateResponsiblePersonService implements InitializingBean, ResponsiblePersonService {
    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = HibernateResponsiblePersonService.class.getName();

    /**
     * The query name for getting copilots data for the direct project.
     *
     * @since 1.1
     */
    private static final String GET_COPILOTS_FOR_PROJECT = "getCopilotsForProject";

    /**
     * The query name for getting managers data for the direct project.
     *
     * @since 1.1
     */
    private static final String GET_MANAGERS_FOR_PROJECT = "getManagersForProject";

    /**
     * the parameter used in query for direct project id.
     *
     * @since 1.1
     */
    private static final String DIRECT_PROJECT_ID_PARAM = "directProjectId";

    /**
     * <p>
     * The logger to be used for logging errors and debug information.
     * </p>
     */
    private Log logger;

    /**
     * The session factory.
     *
     * @since 1.1
     */
    private SessionFactory sessionFactory;

    /**
     * Empty constructor.
     */
    public HibernateResponsiblePersonService() {
        // Empty
    }

    /**
     * This method checks that all required injection fields are in fact provided.
     *
     * @throws ProjectMilestoneManagementConfigurationException
     *          If there are required injection fields that are not injected
     */
    public void afterPropertiesSet() {
        ValidationUtility.checkNotNull(logger, "logger", ProjectMilestoneManagementConfigurationException.class);
    }

    /**
     * This method gets all responsible people for the given project ID. If none found, returns an empty list.
     *
     * <p>
     *     Updates in version 1.1 - removes the reference to DataProvider and DirectProjectMetadataService. Use
     *     native hibernate query to get the copilots and managers.
     * </p>
     *
     * @param projectId the ID of the direct project
     * @return the responsible people
     * @throws ProjectMilestoneManagementException
     *          If there are any errors during the execution of this method
     */
    @Transactional(readOnly = true)
    public List<ResponsiblePerson> getAllResponsiblePeople(long projectId)
            throws ProjectMilestoneManagementException {
        final String signature = CLASS_NAME + ".getAllResponsiblePeople(long projectId)";
        final Date entranceTimestamp = new Date();
        // Log entrance
        LoggingWrapperUtility.logEntrance(logger, signature, new String[]{"projectId"}, new Object[]{projectId},
                true, Level.DEBUG);

        try {

            // Create list for responsible people:
            List<ResponsiblePerson> people = new ArrayList<ResponsiblePerson>();
            Set<Long> duplicationChecking = new HashSet<Long>();

            List copilotList = getSession().getNamedQuery(GET_COPILOTS_FOR_PROJECT)
                    .setParameter(DIRECT_PROJECT_ID_PARAM, projectId).list();


            for (Object o : copilotList) {
                ResponsiblePerson p = (ResponsiblePerson) o;
                if (!duplicationChecking.contains(p.getUserId())) {
                    people.add(p);
                    duplicationChecking.add(p.getUserId());
                }

            }

            List managersList = sessionFactory.getCurrentSession().getNamedQuery(GET_MANAGERS_FOR_PROJECT)
                    .setParameter(DIRECT_PROJECT_ID_PARAM, projectId).list();

            for (Object o : managersList) {
                ResponsiblePerson p = (ResponsiblePerson) o;
                if (!duplicationChecking.contains(p.getUserId())) {
                    people.add(p);
                    duplicationChecking.add(p.getUserId());
                }
            }

            // update all the responsible person id to 0, because they are using for looking up
            for (ResponsiblePerson p : people) {
                p.setId(0);
            }

            // Log exit
            LoggingWrapperUtility.logExit(logger, signature, new Object[]{people}, entranceTimestamp);

            // Return people
            return people;
        } catch (NumberFormatException e) {
            throw LoggingWrapperUtility.logException(logger, signature, new ProjectMilestoneManagementException(
                    "NumberFormatException occurs while parsing long to string", e), true, Level.ERROR);
        } catch (Exception e) {
            throw LoggingWrapperUtility.logException(logger, signature, new ProjectMilestoneManagementException(
                    "Exception occurs while getting all ResponsiblePeople", e), true, Level.ERROR);
        }

    }

    /**
     * Deletes the responsible person with the specified responsible person id.
     *
     * @param responsiblePersonId the id of the responsible person.
     * @throws ProjectMilestoneManagementException
     *          If there are any errors during the execution of this method
     * @since 1.1
     */
    @Transactional
    public void deleteResponsiblePerson(long responsiblePersonId) throws ProjectMilestoneManagementException {
        final String signature = CLASS_NAME + ".delete(long responsiblePersonId)";
        final Date entranceTimestamp = new Date();
        // Log entrance
        LoggingWrapperUtility.logEntrance(logger, signature, new String[]{"responsiblePersonId"},
                new Object[]{responsiblePersonId}, true, Level.DEBUG);

        try {
            // Get the entity
            ResponsiblePerson person = (ResponsiblePerson) getSession().get(ResponsiblePerson.class, responsiblePersonId);

            if (person == null) {
                // throw not found exception
                throw new EntityNotFoundException("The entity is not found with id[" + responsiblePersonId + "]");
            }
            // Delete entity
            getSession().delete(person);

            // Log exit
            LoggingWrapperUtility.logExit(logger, signature, null, entranceTimestamp);
        } catch (EntityNotFoundException e) {
            throw LoggingWrapperUtility.logException(logger, signature, e, true, Level.ERROR);
        } catch (HibernateException e) {
            throw LoggingWrapperUtility.logException(logger, signature, new ProjectMilestoneManagementException(
                    "HibernateException occurs while accessing to db", e), true, Level.ERROR);
        }

    }

    /**
     * <p>
     * Getter method for logger, simply return the namesake instance variable.
     * </p>
     *
     * @return the logger
     */
    public Log getLogger() {
        return logger;
    }

    /**
     * <p>
     * Setter method for logger, simply assign the value to the instance variable.
     * </p>
     *
     * @param logger the logger to set
     */
    public void setLogger(Log logger) {
        this.logger = logger;
    }

    /**
     * Gets the session factory.
     *
     * @return the session factory.
     * @since 1.1
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Sets the session factory.
     *
     * @param sessionFactory the session factory.
     * @since 1.1
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * <p>
     * Retrieves the current session from the session factory.
     * </p>
     *
     * @return the retrieved current session (not null)
     * @throws HibernateException if any error occurred
     * @since 1.1
     */
    private Session getSession() {
        // return current session from session factory
        return sessionFactory.getCurrentSession();
    }
}
