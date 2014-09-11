/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.services.impl;

import java.util.List;

import com.topcoder.asset.entities.User;
import com.topcoder.asset.exceptions.PersistenceException;
import com.topcoder.asset.services.ManagerService;

/**
 * <p>
 * This class is an implementation of ManagerService that uses injected JPA EntityManager interface for accessing data
 * in database. This class uses Logging Wrapper logger to perform logging. It's assumed that this class is initialized
 * with Spring setter dependency injection only.
 * </p>
 *
 * <p>
 * <em>Sample Configuration:</em>
 * <pre>
 * &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
 * &lt;beans xmlns=&quot;http://www.springframework.org/schema/beans&quot;
 *        xmlns:xsi=&quot;http://www.w3.org/2001/XMLSchema-instance&quot;
 *        xmlns:context=&quot;http://www.springframework.org/schema/context&quot;
 *        xmlns:tx=&quot;http://www.springframework.org/schema/tx&quot;
 *        xmlns:aop=&quot;http://www.springframework.org/schema/aop&quot;
 *        xsi:schemaLocation=&quot;http://www.springframework.org/schema/beans
 *        http://www.springframework.org/schema/beans/spring-beans-2.5.xsd
 *        http://www.springframework.org/schema/context
 *        http://www.springframework.org/schema/context/spring-context-2.5.xsd
 *        http://www.springframework.org/schema/tx
 *        http://www.springframework.org/schema/tx/spring-tx-2.5.xsd
 *        http://www.springframework.org/schema/aop
 *        http://www.springframework.org/schema/aop/spring-aop-2.5.xsd&quot;&gt;
 *
 *     &lt;context:annotation-config/&gt;
 *     &lt;bean class=&quot;org.springframework.context.annotation.CommonAnnotationBeanPostProcessor&quot;/&gt;
 *
 *     &lt;bean id=&quot;managerService&quot;
 *         class=&quot;com.topcoder.asset.services.impl.ManagerServiceImpl&quot;&gt;
 *         &lt;property name=&quot;entityManager&quot; ref=&quot;entityManager&quot;/&gt;
 *         &lt;property name=&quot;log&quot; ref=&quot;log&quot;/&gt;
 *     &lt;/bean&gt;
 *
 *
 *     &lt;!-- Log --&gt;
 *     &lt;bean id=&quot;log&quot;
 *         class=&quot;org.springframework.beans.factory.config.MethodInvokingFactoryBean&quot;&gt;
 *         &lt;property name=&quot;staticMethod&quot;&gt;
 *             &lt;value&gt;com.topcoder.util.log.LogManager.getLog&lt;/value&gt;
 *         &lt;/property&gt;
 *         &lt;property name=&quot;arguments&quot;&gt;
 *             &lt;list&gt;
 *                 &lt;value&gt;com.topcoder.asset.logger&lt;/value&gt;
 *             &lt;/list&gt;
 *         &lt;/property&gt;
 *     &lt;/bean&gt;
 *
 *
 *     &lt;bean id=&quot;entityManagerFactory&quot;
 *       class=&quot;org.springframework.orm.jpa.LocalEntityManagerFactoryBean&quot; &gt;
 *       &lt;property name=&quot;persistenceUnitName&quot; value=&quot;persistenceUnit&quot; /&gt;
 *     &lt;/bean&gt;
 *     &lt;bean id=&quot;entityManager&quot;
 *         class = &quot;org.springframework.orm.jpa.support.SharedEntityManagerBean&quot;&gt;
 *             &lt;property name = &quot;entityManagerFactory&quot; ref=&quot;entityManagerFactory&quot;/&gt;
 *     &lt;/bean&gt;
 *
 *     &lt;bean class=&quot;org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor&quot; /&gt;
 *     &lt;context:annotation-config/&gt;
 *     &lt;context:component-scan base-package=&quot;com.topcoder.asset.entities&quot; /&gt;
 *
 *     &lt;!-- the transactional advice (what 'happens'; see the &lt;aop:advisor/&gt; bean below) --&gt;
 *     &lt;tx:advice id=&quot;txAdvice&quot; transaction-manager=&quot;txManager&quot;&gt;
 *       &lt;tx:attributes&gt;
 *         &lt;tx:method name=&quot;create*&quot;/&gt;
 *         &lt;tx:method name=&quot;remove*&quot;/&gt;
 *         &lt;tx:method name=&quot;set*&quot;/&gt;
 *         &lt;tx:method name=&quot;update*&quot;/&gt;
 *         &lt;tx:method name=&quot;delete*&quot;/&gt;
 *         &lt;tx:method name=&quot;*&quot; read-only=&quot;true&quot;/&gt;
 *       &lt;/tx:attributes&gt;
 *     &lt;/tx:advice&gt;
 *
 *     &lt;!-- ensure that the above transactional advice runs for any execution of an operation
 *         defined by the service interfaces --&gt;
 *     &lt;aop:config&gt;
 *       &lt;aop:pointcut id=&quot;serviceOperation&quot;
 *       expression=&quot;execution(* com.topcoder.asset.services.impl.*Service*.*(..))&quot;/&gt;
 *       &lt;aop:advisor advice-ref=&quot;txAdvice&quot; pointcut-ref=&quot;serviceOperation&quot;/&gt;
 *     &lt;/aop:config&gt;
 *
 *  &lt;bean id=&quot;txManager&quot; class=&quot;org.springframework.orm.jpa.JpaTransactionManager&quot;&gt;
 *      &lt;property name=&quot;entityManagerFactory&quot; ref=&quot;entityManagerFactory&quot; /&gt;
 *  &lt;/bean&gt;
 *
 * &lt;/beans&gt;
 *
 *
 * </pre>
 *
 * </p>
 *
 * <p>
 * <em>Sample Code:</em>
 * <pre>
 * // Load application context
 * ApplicationContext context = new ClassPathXmlApplicationContext(&quot;beans.xml&quot;);
 *
 * // Retrieve ManagerService from the Spring application context
 * ManagerService managerService = (ManagerService) context.getBean(&quot;managerService&quot;);
 *
 * // Get client managers
 * List&lt;User&gt; clientManagers = managerService.getClientManagers(11);
 *
 * // The client managers of the project will be retrieved.
 *
 * // Get TopCoder managers
 * List&lt;User&gt; topcoderManagers = managerService.getTopCoderManagers(11);
 *
 * // The TopCoder managers of the project will be retrieved.
 *
 * // Get copilots
 * List&lt;User&gt; copilots = managerService.getCopilots(11);
 * // The copilots of the project will be retrieved.
 * </pre>
 *
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong> This class is mutable since it provides public setters for its properties. But it
 * doesn't change its state and is thread safe when the following condition is met: this class is initialized by
 * Spring right after construction and its parameters are never changed after that.
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TopCoder Cockpit Asset View And Basic Upload)
 * - Fixes SQL_QUERY_CLIENT_MANAGER and SQL_QUERY_TOPCODER_MANAGER
 * </p>
 *
 * @author LOY, sparemax, GreatKevin
 * @version 1.1
 */
public class ManagerServiceImpl extends BaseMiscService implements ManagerService {
    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = ManagerServiceImpl.class.getName();

    /**
     * The SQL to query the client manager.
     */
    private static final String SQL_QUERY_CLIENT_MANAGER = "SELECT u.user_id, u.handle FROM user u"
        + " JOIN direct_project_metadata mv ON u.user_id = mv.metadata_value"
        + " WHERE mv.project_metadata_key_id = 1 AND mv.tc_direct_project_id = :projectId";

    /**
     * The SQL to query the TopCoder manager.
     */
    private static final String SQL_QUERY_TOPCODER_MANAGER = "SELECT u.user_id, u.handle FROM user u"
            + " JOIN direct_project_metadata mv ON u.user_id = mv.metadata_value"
            + " WHERE mv.project_metadata_key_id IN (2, 14) AND mv.tc_direct_project_id = :projectId";

    /**
     * The SQL to query the copilot.
     */
    private static final String SQL_QUERY_COPILOT = "SELECT u.user_id, u.handle FROM user u"
        + " JOIN copilot_profile cp1 ON u.user_id = cp1.user_id"
        + " JOIN copilot_project cp2 ON cp1.copilot_profile_id = cp2.copilot_profile_id"
        + " WHERE cp2.tc_direct_project_id = :projectId";

    /**
     * Creates an instance of ManagerServiceImpl.
     */
    public ManagerServiceImpl() {
        // Empty
    }

    /**
     * This method will retrieve client managers of a specific project.
     *
     * @param projectId
     *            the project id
     *
     * @return client managers of a specific project
     *
     * @throws IllegalArgumentException
     *             if projectId is not positive.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     */
    public List<User> getClientManagers(long projectId) throws PersistenceException {
        String signature = CLASS_NAME + ".getClientManagers(long projectId)";

        return MiscHelper.getEntities(getLog(), signature, getEntityManager(), SQL_QUERY_CLIENT_MANAGER, projectId);
    }

    /**
     * This method will retrieve Topcoder managers of a specific project.
     *
     * @param projectId
     *            the project id
     *
     * @return topcoder managers of a specific project
     *
     * @throws IllegalArgumentException
     *             if projectId is not positive.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     */
    public List<User> getTopCoderManagers(long projectId) throws PersistenceException {
        String signature = CLASS_NAME + ".getClientManagers(long projectId)";

        return MiscHelper.getEntities(getLog(), signature, getEntityManager(), SQL_QUERY_TOPCODER_MANAGER, projectId);
    }

    /**
     * This method will retrieve copilots of a specific project.
     *
     * @param projectId
     *            the project id
     *
     * @return copilots of a specific project
     *
     * @throws IllegalArgumentException
     *             if projectId is not positive.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     */
    public List<User> getCopilots(long projectId) throws PersistenceException {
        String signature = CLASS_NAME + ".getCopilots(long projectId)";

        return MiscHelper.getEntities(getLog(), signature, getEntityManager(), SQL_QUERY_COPILOT, projectId);
    }
}
