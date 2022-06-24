/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import com.topcoder.asset.entities.Asset;
import com.topcoder.asset.entities.AssetPermission;
import com.topcoder.asset.entities.User;
import com.topcoder.asset.exceptions.AssetConfigurationException;
import com.topcoder.asset.exceptions.EntityNotFoundException;
import com.topcoder.asset.exceptions.PersistenceException;
import com.topcoder.asset.exceptions.ServiceException;
import com.topcoder.asset.services.AssetPermissionService;
import com.topcoder.asset.services.AssetService;
import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This class is an implementation of AssetPermissionService that uses injected JPA EntityManager interface for
 * accessing AssetPermission data in database. This class uses Logging Wrapper logger to perform logging. It's assumed
 * that this class is initialized with Spring setter dependency injection only.
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
 *     &lt;bean id=&quot;assetPermissionService&quot;
 *         class=&quot;com.topcoder.asset.services.impl.AssetPermissionServiceImpl&quot;&gt;
 *         &lt;property name=&quot;entityManager&quot; ref=&quot;entityManager&quot;/&gt;
 *         &lt;property name=&quot;log&quot; ref=&quot;log&quot;/&gt;
 *         &lt;property name=&quot;assetService&quot; ref=&quot;assetService&quot;/&gt;
 *     &lt;/bean&gt;
 *
 *     &lt;bean id=&quot;assetService&quot;
 *         class=&quot;com.topcoder.asset.MockAssetService&quot;&gt;
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
 * // Retrieve AssetPermissionService from the Spring application context
 * AssetPermissionService assetPermissionService =
 *     (AssetPermissionService) context.getBean(&quot;assetPermissionService&quot;);
 *
 * // Create Permission
 * assetPermissionService.createPermission(1, 1);
 *
 * // Check if asset is allowed for the specific user
 * boolean isAllowed = assetPermissionService.isAllowed(1, 1);
 * // isAllowed will be true.
 *
 * // Get allowed users for asset
 * List&lt;User&gt; users = assetPermissionService.getAllowedUsersForAsset(1);
 * // users will contain 1 records, with user.id = 1
 *
 * // Remove Permission
 * assetPermissionService.removePermission(1, 1);
 * // The corresponding record will be removed in database
 *
 * // Prepare assetIds and userIds
 * List&lt;Long&gt; assetIds = new ArrayList&lt;Long&gt;();
 * assetIds.add(1L);
 * assetIds.add(2L);
 *
 * List&lt;Long&gt; userIds = new ArrayList&lt;Long&gt;();
 * userIds.add(1L);
 * userIds.add(2L);
 *
 * // Set permissions
 * assetPermissionService.setPermissions(assetIds, userIds);
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
 * @author LOY, sparemax
 * @version 1.0
 */
public class AssetPermissionServiceImpl extends BaseMiscService implements AssetPermissionService {
    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = AssetPermissionServiceImpl.class.getName();

    /**
     * The JPQL to delete the asset permission.
     */
    private static final String JPQL_DELETE_ASSET_PERMISSION = "DELETE FROM AssetPermission e"
        + " WHERE e.assetId = :assetId AND e.user.id = :userId";

    /**
     * The JPQL to query the asset permission by asset id.
     */
    private static final String JPQL_QUERY_ASSET_PERMISSION_BY_ASSET = "SELECT e FROM AssetPermission e"
        + " WHERE e.assetId = :assetId";

    /**
     * The JPQL to query the count of asset permission.
     */
    private static final String JPQL_QUERY_ASSET_PERMISSION_COUNT = "SELECT COUNT(e) FROM AssetPermission e"
        + " WHERE e.assetId = :assetId AND e.user.id = :userId";

    /**
     * The JPQL to delete the asset permission by asset ids.
     */
    private static final String JPQL_DELETE_ASSET_PERMISSION_BY_ASSETS = "DELETE FROM AssetPermission e"
        + " WHERE e.assetId IN (:assetIds)";

    /**
     * The AssetService instance used for accessing asset data. Initialized with Spring setter dependency injection.
     * Cannot be null after initialization, assuming that property is initialized via Spring setter-based dependency
     * injection and is never changed after that (note that check is performed in checkInit() method instead of the
     * setter). Has a setter.
     */
    private AssetService assetService;

    /**
     * Creates an instance of AssetPermissionServiceImpl.
     */
    public AssetPermissionServiceImpl() {
        // Empty
    }

    /**
     * Checks whether this class was initialized by Spring properly.
     *
     * @throws AssetConfigurationException
     *             if the class was not initialized properly (entityManager or assetService is null)
     */
    @Override
    @PostConstruct
    protected void checkInit() {
        super.checkInit();

        ValidationUtility.checkNotNull(assetService, "assetService", AssetConfigurationException.class);
    }

    /**
     * This method will create the asset permission.
     *
     * @param assetId
     *            the asset id
     * @param userId
     *            the user id
     *
     * @throws IllegalArgumentException
     *             if assetId is not positive, or userId is not positive.
     * @throws EntityNotFoundException
     *             if the corresponding entity is not found.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public void createPermission(long assetId, long userId) throws ServiceException {
        String signature = CLASS_NAME + ".createPermission(long assetId, long userId)";
        Log log = getLog();

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"assetId", "userId"},
            new Object[] {assetId, userId});

        try {
            ParameterCheckUtility.checkPositive(assetId, "assetId");
            ParameterCheckUtility.checkPositive(userId, "userId");

            EntityManager entityManager = getEntityManager();

            // Retrieve asset and user
            Asset asset;

            try {
                asset = assetService.getAsset(assetId);
                ValidationUtility.checkNotNull(asset, "Existing Asset", EntityNotFoundException.class);
            } catch (ServiceException e) {
                // Log exception
                throw LoggingWrapperUtility.logException(log, signature, e);
            }

            User user = MiscHelper.getEntity(log, signature, "Existing User", entityManager, User.class, userId);

            // Create AssetPermission:
            AssetPermission assetPermission = new AssetPermission();
            assetPermission.setAssetId(assetId);
            assetPermission.setUser(user);

            entityManager.persist(assetPermission);

            // Log Exit
            LoggingWrapperUtility.logExit(log, signature, null);
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new PersistenceException(
                "An error has occurred while accessing the persistence.", e));
        } catch (IllegalStateException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new PersistenceException(
                "The entity manager has been closed.", e));
        }
    }

    /**
     * This method will remove the asset permission.
     *
     * @param assetId
     *            the asset id
     * @param userId
     *            the user id
     *
     * @throws IllegalArgumentException
     *             if assetId is not positive, or userId is not positive.
     * @throws EntityNotFoundException
     *             if the corresponding entity is not found.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     */
    public void removePermission(long assetId, long userId) throws PersistenceException {
        String signature = CLASS_NAME + ".removePermission(long assetId, long userId)";
        Log log = getLog();

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"assetId", "userId"},
            new Object[] {assetId, userId});

        try {
            ParameterCheckUtility.checkPositive(assetId, "assetId");
            ParameterCheckUtility.checkPositive(userId, "userId");

            EntityManager entityManager = getEntityManager();

            MiscHelper.getEntity(log, signature, "Existing Asset", entityManager, Asset.class, assetId);
            MiscHelper.getEntity(log, signature, "Existing User", entityManager, User.class, userId);

            // Execute update;
            int count = entityManager.createQuery(JPQL_DELETE_ASSET_PERMISSION)
                .setParameter("assetId", assetId)
                .setParameter("userId", userId)
                .executeUpdate();

            if (count == 0) {
                // Log exception
                throw LoggingWrapperUtility.logException(log, signature,
                    new EntityNotFoundException("The AssetPermission entity is not found."));
            }

            // Log Exit
            LoggingWrapperUtility.logExit(log, signature, null);
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new PersistenceException(
                "An error has occurred while accessing the persistence.", e));
        } catch (IllegalStateException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new PersistenceException(
                "The entity manager has been closed.", e));
        }
    }

    /**
     * This method will check if the asset is public or the user has permission to access it.
     *
     * @param assetId
     *            the asset id
     * @param userId
     *            the user id
     *
     * @return true, if the asset is public or the user has permission to access it; false, otherwise.
     *
     * @throws IllegalArgumentException
     *             if assetId is not positive, or userId is not positive.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public boolean isAllowed(long assetId, long userId) throws ServiceException {
        String signature = CLASS_NAME + ".isAllowed(long assetId, long userId)";
        Log log = getLog();

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"assetId", "userId"},
            new Object[] {assetId, userId});

        try {
            ParameterCheckUtility.checkPositive(assetId, "assetId");
            ParameterCheckUtility.checkPositive(userId, "userId");

            // Check if the asset is public:
            Asset asset = assetService.getAsset(assetId);

            boolean result = false;

            if (asset != null) {
                result = asset.isPublic();
                if (!result) {
                    EntityManager entityManager = getEntityManager();

                    // Execute query;
                    Number count = (Number) entityManager.createQuery(JPQL_QUERY_ASSET_PERMISSION_COUNT)
                        .setParameter("assetId", assetId)
                        .setParameter("userId", userId)
                        .getSingleResult();

                    result = (count.intValue() != 0);
                }
            }

            // Log Exit
            LoggingWrapperUtility.logExit(log, signature, new Object[] {result});
            return result;
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new PersistenceException(
                "An error has occurred while accessing the persistence.", e));
        } catch (IllegalStateException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new PersistenceException(
                "The entity manager has been closed.", e));
        } catch (ServiceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        }
    }

    /**
     * This method will get allowed users for the given asset.
     *
     * @param assetId
     *            the asset id
     *
     * @return the allowed users for the given asset.
     *
     * @throws IllegalArgumentException
     *             if assetId is not positive.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     */
    @SuppressWarnings("unchecked")
    public List<User> getAllowedUsersForAsset(long assetId) throws PersistenceException {
        String signature = CLASS_NAME + ".getAllowedUsersForAsset(long assetId)";
        Log log = getLog();

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"assetId"},
            new Object[] {assetId});

        try {
            ParameterCheckUtility.checkPositive(assetId, "assetId");

            EntityManager entityManager = getEntityManager();

            // Execute query;
            List<AssetPermission> assetPermissions = entityManager.createQuery(JPQL_QUERY_ASSET_PERMISSION_BY_ASSET)
                .setParameter("assetId", assetId)
                .getResultList();

            List<User> result = new ArrayList<User>();
            for (AssetPermission assetPermission : assetPermissions) {
                result.add(assetPermission.getUser());
            }

            // Log Exit
            LoggingWrapperUtility.logExit(log, signature, new Object[] {MiscHelper.toString(result)});
            return result;
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new PersistenceException(
                "An error has occurred while accessing the persistence.", e));
        } catch (IllegalStateException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new PersistenceException(
                "The entity manager has been closed.", e));
        }
    }

    /**
     * This method will set permissions for the given assets and users.
     *
     * @param assetIds
     *            the given asset ids
     * @param userIds
     *            the given user ids
     *
     * @throws IllegalArgumentException
     *             if assetIds or userIds is null, or contains null or not positive element.
     * @throws EntityNotFoundException
     *             if the corresponding entity is not found.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public void setPermissions(List<Long> assetIds, List<Long> userIds) throws ServiceException {
        String signature = CLASS_NAME + ".setPermissions(List<Long> assetIds, List<Long> userIds)";
        Log log = getLog();

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"assetIds", "userIds"},
            new Object[] {assetIds, userIds});

        try {
            checkList(assetIds, "assetIds");
            checkList(userIds, "userIds");

            EntityManager entityManager = getEntityManager();

            for (Long assetId : assetIds) {
                MiscHelper.getEntity(log, signature, "Existing Asset", entityManager, Asset.class, assetId);
            }
            for (Long userId : userIds) {
                MiscHelper.getEntity(log, signature, "Existing User", entityManager, User.class, userId);
            }

            // Execute update;
            entityManager.createQuery(JPQL_DELETE_ASSET_PERMISSION_BY_ASSETS)
                .setParameter("assetIds", assetIds)
                .executeUpdate();

            for (Long assetId : assetIds) {
                for (Long userId : userIds) {
                    createPermission(assetId, userId);
                }
            }

            // Log Exit
            LoggingWrapperUtility.logExit(log, signature, null);
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new PersistenceException(
                "An error has occurred while accessing the persistence.", e));
        } catch (IllegalStateException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new PersistenceException(
                "The entity manager has been closed.", e));
        }
    }

    /**
     * Sets the AssetService instance used for accessing asset data.
     *
     * @param assetService
     *            the AssetService instance used for accessing asset data.
     */
    public void setAssetService(AssetService assetService) {
        this.assetService = assetService;
    }

    /**
     * Checks the list.
     *
     * @param list
     *            the list.
     * @param name
     *            the name
     *
     * @throws IllegalArgumentException
     *             if list is null, or contains null or not positive element.
     */
    private static void checkList(List<Long> list, String name) {
        ParameterCheckUtility.checkNotNull(list, name);
        for (Long element : list) {
            ParameterCheckUtility.checkNotNull(element, name + "[i]");
            ParameterCheckUtility.checkPositive(element, name + "[i]");
        }
    }
}
