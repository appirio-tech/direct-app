/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.topcoder.asset.entities.Asset;
import com.topcoder.asset.entities.AssetSearchCriteria;
import com.topcoder.asset.entities.AssetVersion;
import com.topcoder.asset.entities.Category;
import com.topcoder.asset.entities.PagedResult;
import com.topcoder.asset.exceptions.AssetConfigurationException;
import com.topcoder.asset.exceptions.EntityNotFoundException;
import com.topcoder.asset.exceptions.PersistenceException;
import com.topcoder.asset.exceptions.ServiceException;
import com.topcoder.asset.services.AssetService;
import com.topcoder.asset.services.AssetVersionService;
import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This class is an implementation of AssetService that uses injected JPA EntityManager interface for accessing Asset
 * data in database. This class uses Logging Wrapper logger to perform logging. It's assumed that this class is
 * initialized with Spring setter dependency injection only.
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
 *     &lt;bean id=&quot;assetService&quot;
 *       class=&quot;com.topcoder.asset.services.impl.AssetServiceImpl&quot;&gt;
 *       &lt;property name=&quot;entityManager&quot; ref=&quot;entityManager&quot;/&gt;
 *       &lt;property name=&quot;log&quot; ref=&quot;log&quot;/&gt;
 *       &lt;property name=&quot;assetVersionService&quot; ref=&quot;assetVersionService&quot;/&gt;
 *     &lt;/bean&gt;
 *
 *     &lt;bean id=&quot;assetVersionService&quot;
 *       class=&quot;com.topcoder.asset.services.impl.AssetVersionServiceImpl&quot;&gt;
 *       &lt;property name=&quot;entityManager&quot; ref=&quot;entityManager&quot;/&gt;
 *       &lt;property name=&quot;log&quot; ref=&quot;log&quot;/&gt;
 *       &lt;property name=&quot;basePath&quot; value=&quot;test_files/cockpit&quot;/&gt;
 *       &lt;property name=&quot;imageTypes&quot;&gt;
 *         &lt;list&gt;
 *           &lt;value&gt;jpg&lt;/value&gt;
 *           &lt;value&gt;png&lt;/value&gt;
 *           &lt;value&gt;bmp&lt;/value&gt;
 *         &lt;/list&gt;
 *       &lt;/property&gt;
 *       &lt;property name=&quot;previewImageWidth&quot; value=&quot;10&quot;/&gt;
 *       &lt;property name=&quot;previewImageHeight&quot; value=&quot;20&quot;/&gt;
 *     &lt;/bean&gt;
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
 *
 *     &lt;!-- ensure that the above transactional advice runs for any execution of an operation
 *             defined by the service interfaces --&gt;
 *     &lt;aop:config&gt;
 *       &lt;aop:pointcut id=&quot;serviceOperation&quot;
 *           expression=&quot;execution(* com.topcoder.asset.services.impl.*Service*.*(..))&quot;/&gt;
 *       &lt;aop:advisor advice-ref=&quot;txAdvice&quot; pointcut-ref=&quot;serviceOperation&quot;/&gt;
 *     &lt;/aop:config&gt;
 *
 *  &lt;bean id=&quot;txManager&quot; class=&quot;org.springframework.orm.jpa.JpaTransactionManager&quot;&gt;
 *      &lt;property name=&quot;entityManagerFactory&quot; ref=&quot;entityManagerFactory&quot; /&gt;
 *  &lt;/bean&gt;
 *
 * &lt;/beans&gt;
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
 * // Retrieve AssetService from the Spring application context
 * AssetService assetService = (AssetService) context.getBean(&quot;assetService&quot;);
 *
 * // Prepare the id of user who performs the action
 * long userId = 12;
 *
 * // Prepare asset to create
 * List&lt;Category&gt; categories = new ArrayList&lt;Category&gt;();
 * Category category1 = new Category();
 * category1.setId(100);
 * categories.add(category1);
 *
 * Category category2 = new Category();
 * category2.setId(101);
 * categories.add(category2);
 *
 * Asset asset = new Asset();
 * asset.setName(&quot;design document&quot;);
 * asset.setCategories(categories);
 * asset.setContainerType(&quot;containerType1&quot;);
 * asset.setContainerId(1);
 *
 * // Create asset
 * assetService.createAsset(userId, asset);
 * // The asset will be persisted in database,
 * // Its child entities currentVersion and categories won’t be managed,
 * // but relationship will be managed.
 *
 * // Prepare asset to update
 * asset.setName(&quot;new design document&quot;);
 * asset.getCategories().get(1).setId(102);
 *
 * // Update asset
 * assetService.updateAsset(userId, asset);
 * // The asset will be updated in database. Its child entities currentVersion and categories won’t be managed,
 * // but relationship will be managed.
 *
 * long assetId = asset.getId();
 *
 * // Get asset
 * Asset result = assetService.getAsset(assetId);
 * // The asset will be retrieved from database. It will also retrieve its child entity
 * // currentVersion:AssetVersion and categories:List&lt;Category&gt;.
 *
 * // Prepare AssetSearchCriteria
 * AssetSearchCriteria criteria = new AssetSearchCriteria();
 * criteria.setVersion(&quot;Version 1.0&quot;);
 * // set other properties of criteria similarly
 *
 * // Search assets
 * PagedResult&lt;Asset&gt; assets = assetService.searchAssets(criteria);
 * // The matched assets will be fetched.
 *
 * Asset asset2 = new Asset();
 * asset2.setName(&quot;design document 2&quot;);
 * asset2.setContainerType(&quot;containerType2&quot;);
 * asset2.setContainerId(2);
 *
 * // Prepare assets to create
 * List&lt;Asset&gt; assetList = new ArrayList&lt;Asset&gt;();
 * assetList.add(asset2);
 *
 * // Batch create assets
 * assetService.createAssets(userId, assetList);
 *
 * // Prepare asset ids
 * List&lt;Long&gt; assetIds = new ArrayList&lt;Long&gt;();
 * assetIds.add(asset2.getId());
 *
 * // Prepare assets to update
 * asset2.setName(&quot;design document 3&quot;);
 *
 * // Batch update assets
 * assetService.updateAssets(userId, assetList);
 *
 * // Batch delete assets
 * assetService.deleteAssets(userId, assetIds);
 *
 * // Delete asset
 * assetService.deleteAsset(userId, assetId);
 * // The asset will be deleted in database. All version files and all versions will also be deleted.
 * </pre>
 *
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong> This class is mutable since it provides public setters for its properties. But it
 * doesn't change its state and is thread safe when the following condition is met: this class is initialized by
 * Spring right after construction and its parameters are never changed after that. All entities passed to this class
 * are used by the caller in thread safe manner (accessed from a single thread only).
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TopCoder Cockpit Asset View And File Version)
 * <ul>
 *     <li>Fix the asset category mapping table name.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.2 (Release Assembly - TopCoder Cockpit Asset View Release 3) changes:
 * <ul>
 *     <li>Adds method {@link #getAssets(long[])}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.3 (Release Assembly - TopCoder Cockpit Asset View Release 4 - Resource restriction update)
 * <ul>
 *     <li>Removes the permission checking in {@link #searchAssets(com.topcoder.asset.entities.AssetSearchCriteria)}.
 *     The permission checking will be handled in action level based on the isPublic flag of asset</li>
 *     <li>Updated signature of {@link #searchAssets(javax.persistence.EntityManager, com.topcoder.asset.entities.AssetSearchCriteria, int, int)}</li>
 *     <li>Updated signature of {@link #buildWhere(com.topcoder.asset.entities.AssetSearchCriteria, java.util.List, java.util.List)}</li>
 * </ul>
 * </p>
 *
 * @author LOY, sparemax, GreatKevin, TCSASSEMBLER
 * @version 1.3
 */
public class AssetServiceImpl extends BaseAssetService implements AssetService {
    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = AssetServiceImpl.class.getName();

    /**
     * The JPQL to query the asset version id.
     */
    private static final String JPQL_QUERY_ASSET_VERSION_ID =
        "SELECT e.id FROM AssetVersion e WHERE e.assetId = :assetId";

    /**
     * The SQL to delete the asset category mapping.
     */
    private static final String SQL_DELETE_ASSET_CATEGORY_MAPPING =
        "DELETE FROM Asset_Category_Mapping WHERE asset_id = :assetId";

    /**
     * The JPQL to query the asset.
     */
    private static final String JPQL_QUERY_ASSET = "SELECT DISTINCT e FROM Asset e LEFT OUTER JOIN e.categories c"
        + " LEFT OUTER JOIN e.currentVersion.uploader u";

    /**
     * The JPQL to query the count of assets.
     */
    private static final String JPQL_QUERY_ASSET_COUNT = "SELECT COUNT(DISTINCT e) FROM Asset e"
        + " LEFT OUTER JOIN e.categories c LEFT OUTER JOIN e.currentVersion.uploader u";

    /**
     * <p>
     * Represents the fields of Asset.
     * </p>
     */
    private static final List<String> ASSET_FIELDS = Arrays.asList("id", "name", "currentVersion.id",
        "currentVersion.id", "currentVersion.version", "currentVersion.fileName", "currentVersion.fileType",
        "currentVersion.fileSizeBytes", "currentVersion.uploader", "currentVersion.uploadTime",
        "currentVersion.description", "currentVersion.filePath", "currentVersion.previewImagePath", "containerType",
        "containerId", "isPublic");

    /**
     * The AssetVersionService instance used to access asset version data in persistence. Is initialized with Spring
     * setter dependency injection. Cannot be null after initialization, assuming that property is initialized via
     * Spring setter-based dependency injection and is never changed after that (note that check is performed in
     * checkInit() method instead of the setter). Has a setter.
     */
    private AssetVersionService assetVersionService;

    /**
     * Creates an instance of AssetServiceImpl.
     */
    public AssetServiceImpl() {
        // Empty
    }

    /**
     * Checks whether this class was initialized by Spring properly.
     *
     * @throws AssetConfigurationException
     *             if the class was not initialized properly (entityManager or assetVersionService is null)
     */
    @PostConstruct
    protected void checkInit() {
        super.checkInit();

        ValidationUtility.checkNotNull(assetVersionService, "assetVersionService", AssetConfigurationException.class);
    }

    /**
     * This method will create the asset. Its child entities currentVersion and categories needn't be managed, but
     * relationship should be managed.
     *
     * @param userId
     *            the id of the user who performs the action
     * @param asset
     *            the asset to create
     *
     * @throws IllegalArgumentException
     *             if userId is not positive or asset is null.
     * @throws EntityNotFoundException
     *             if the corresponding entity is not found.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public void createAsset(long userId, Asset asset) throws ServiceException {
        String signature = CLASS_NAME + ".createAsset(long userId, Asset asset)";
        Log log = getLog();

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"userId", "asset"},
            new Object[] {userId, Helper.toString(asset)});

        try {
            ParameterCheckUtility.checkPositive(userId, "userId");
            ParameterCheckUtility.checkNotNull(asset, "asset");

            EntityManager entityManager = getEntityManager();

            // Check categories
            checkCategories(log, signature, entityManager, asset.getCategories());

            // Manage relationship between asset and current version:
            AssetVersion currentVersion = asset.getCurrentVersion();
            AssetVersion assetVersion = null;
            if (currentVersion != null) {
                assetVersion = Helper.getEntity(log, signature, "Existing AssetVersion", entityManager,
                    AssetVersion.class, currentVersion.getId());
            }

            // Persist asset:
            entityManager.persist(asset);
            entityManager.flush();

            long assetId = asset.getId();

            if (currentVersion != null) {
                assetVersion.setAssetId(assetId);
                entityManager.merge(assetVersion);
            }

            Helper.performAudit(entityManager, userId, "Create Asset", "Asset", assetId, null, Helper.toString(log,
                signature, asset));

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
            throw LoggingWrapperUtility.logException(log, signature, new ServiceException(
                "The entity manager has been closed.", e));
        }
    }

    /**
     * This method will update the asset. Its child entities currentVersion and categories need not be managed, but
     * relationship should be managed.
     *
     * @param userId
     *            the id of the user who performs the action
     * @param asset
     *            the asset to update
     *
     * @throws IllegalArgumentException
     *             if userId is not positive, asset is null, or asset.id is not positive.
     * @throws EntityNotFoundException
     *             if the corresponding entity is not found.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public void updateAsset(long userId, Asset asset) throws ServiceException {
        String signature = CLASS_NAME + ".updateAsset(long userId, Asset asset)";
        Log log = getLog();

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"userId", "asset"},
            new Object[] {userId, Helper.toString(asset)});

        try {
            ParameterCheckUtility.checkPositive(userId, "userId");
            ParameterCheckUtility.checkNotNull(asset, "asset");

            long assetId = asset.getId();
            ParameterCheckUtility.checkPositive(assetId, "asset.getId()");


            EntityManager entityManager = getEntityManager();

            // Get existing asset:
            Asset existingAsset = Helper.getEntity(log, signature, "Existing Asset", entityManager,
                Asset.class, assetId);

            // Manage relationship between asset and categories:
            // Remove original relationships
            entityManager.createNativeQuery(SQL_DELETE_ASSET_CATEGORY_MAPPING)
                .setParameter("assetId", assetId)
                .executeUpdate();

            // Check categories
            checkCategories(log, signature, entityManager, asset.getCategories());

            AssetVersion currentVersion = asset.getCurrentVersion();
            // Manage relationship between asset and current version:
            if (currentVersion != null) {
                AssetVersion assetVersion = Helper.getEntity(log, signature, "Existing AssetVersion", entityManager,
                    AssetVersion.class, currentVersion.getId());

                assetVersion.setAssetId(assetId);
                entityManager.merge(assetVersion);
            }

            entityManager.merge(asset);

            Helper.performAudit(entityManager, userId, "Update Asset", "Asset", assetId, Helper.toString(log,
                signature, existingAsset), Helper.toString(log, signature, asset));

            // Log Exit
            LoggingWrapperUtility.logExit(log, signature, null);
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new PersistenceException(
                "Failed to update the asset.", e));
        } catch (IllegalStateException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ServiceException(
                "The entity manager has been closed.", e));
        }
    }

    /**
     * This method will delete the asset. All version files and all versions should also be deleted.
     *
     * @param userId
     *            the id of the user who performs the action
     * @param assetId
     *            the asset id to delete
     *
     * @throws IllegalArgumentException
     *             if userId or assetId is not positive.
     * @throws EntityNotFoundException
     *             if the corresponding entity is not found.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    @SuppressWarnings("unchecked")
    public void deleteAsset(long userId, long assetId) throws ServiceException {
        String signature = CLASS_NAME + ".deleteAsset(long userId, long assetId)";
        Log log = getLog();

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"userId", "assetId"},
            new Object[] {userId, assetId});

        try {
            ParameterCheckUtility.checkPositive(userId, "userId");
            ParameterCheckUtility.checkPositive(assetId, "assetId");

            EntityManager entityManager = getEntityManager();
            // Get existing asset:
            Asset existingAsset = Helper.getEntity(log, signature, "Existing Asset", entityManager,
                Asset.class, assetId);

            // Remove related asset versions and version files:
            List<Long> assetVersionIds = entityManager.createQuery(JPQL_QUERY_ASSET_VERSION_ID)
                .setParameter("assetId", assetId)
                .getResultList();

            for (long assetVersionId : assetVersionIds) {
                assetVersionService.deleteAssetVersion(userId, assetVersionId);
            }

            // Remove asset:
            entityManager.remove(existingAsset);

            Helper.performAudit(entityManager, userId, "Delete Asset", "Asset", assetId, Helper.toString(log,
                signature, existingAsset), null);

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
            throw LoggingWrapperUtility.logException(log, signature, new ServiceException(
                "The entity manager has been closed.", e));
        }
    }

    /**
     * This method will retrieve the asset with the given asset id. It will also retrieve its child entity
     * currentVersion:AssetVersion and categories:List&lt;Category&gt;.
     *
     * @param assetId
     *            the asset id
     *
     * @return the asset with the given id or null if none is found
     *
     * @throws IllegalArgumentException
     *             if assetId is not positive.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public Asset getAsset(long assetId) throws ServiceException {
        String signature = CLASS_NAME + ".getAsset(long assetId)";
        Log log = getLog();

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"assetId"},
            new Object[] {assetId});

        try {
            ParameterCheckUtility.checkPositive(assetId, "assetId");

            EntityManager entityManager = getEntityManager();
            // Get asset:
            Asset asset = entityManager.find(Asset.class, assetId);

            if (asset != null) {
                // since we're using lazy fetching,
                // so we should make sure categories and currentVersion are also fetched.
                asset.getCategories().size();
                asset.getCurrentVersion();
            }

            // Log Exit
            LoggingWrapperUtility.logExit(log, signature, new Object[] {Helper.toString(asset)});
            return asset;
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new PersistenceException(
                "An error has occurred while accessing the persistence.", e));
        } catch (IllegalStateException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ServiceException(
                "The entity manager has been closed.", e));
        }
    }


    /**
     * This method will retrieve the asset with the given asset id. It will also retrieve its child entity
     * currentVersion:AssetVersion and categories:List&lt;Category&gt;.
     *
     * @param assetIds
     *            the array of asset id
     *
     * @return the assets with the specified ids or empty list if none if found
     *
     * @throws IllegalArgumentException
     *             if assetId is not positive.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     * @since 1.1
     */
    public List<Asset> getAssets(long[] assetIds) throws ServiceException {
        String signature = CLASS_NAME + ".getAssets(long[] assetIds)";
        Log log = getLog();

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
                new String[] {"assetIds"},
                new Object[] {assetIds});

        try {

            if(assetIds == null || assetIds.length == 0) {
                throw new IllegalArgumentException("The assetIds are empty");
            }

            List<Asset> assets = new ArrayList<Asset>();

            for(long assetId : assetIds) {
                Asset asset = getAsset(assetId);
                if(asset != null) {
                    assets.add(asset);
                }
            }

            return assets;

        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new PersistenceException(
                    "An error has occurred while accessing the persistence.", e));
        } catch (IllegalStateException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ServiceException(
                    "The entity manager has been closed.", e));
        }
    }

    /**
     * This method will search the asset with the given criteria. Any field of AssetSearchCriteria is optional, null
     * means a condition is ignored. AssetSearchCriteria.name is partial matching condition, it is matched if asset
     * name contains the given name. Other string condition matching is exact match.. If criteria.page = 0, all
     * matched result should be returned. If criteria.sortingColumn is null, or not any field of Asset, the result
     * will be sorted by name.
     *
     * @param criteria
     *            the asset search criteria.
     *
     * @return the paged result of the matched asset.
     *
     * @throws IllegalArgumentException
     *             if criteria is null, or criteria.page &lt; 0, or criteria.page &gt; 0 and criteria.pageSize &lt;=
     *             0.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    @SuppressWarnings("unchecked")
    public PagedResult<Asset> searchAssets(AssetSearchCriteria criteria) throws ServiceException {
        String signature = CLASS_NAME + ".searchAssets(AssetSearchCriteria criteria)";
        Log log = getLog();

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"criteria"},
            new Object[] {Helper.toString(criteria)});

        try {
            ParameterCheckUtility.checkNotNull(criteria, "criteria");

            int page = criteria.getPage();
            ParameterCheckUtility.checkNotNegative(page, "criteria.getPage()");

            int pageSize = criteria.getPageSize();
            if ((page > 0) && (pageSize <= 0)) {
                throw new IllegalArgumentException("criteria.getPageSize() should be positive"
                    + " when criteria.getPage() is positive.");
            }

            EntityManager entityManager = getEntityManager();

            // Retrieve matched assets:
            PagedResult<Asset> result = searchAssets(entityManager, criteria, page, pageSize);

            // Log Exit
            LoggingWrapperUtility.logExit(log, signature, new Object[] {Helper.toString(result)});
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
            throw LoggingWrapperUtility.logException(log, signature, new ServiceException(
                "The entity manager has been closed.", e));
        }
    }

    /**
     * This method will batch create the assets.
     *
     * @param userId
     *            the id of the user who performs the action
     * @param assets
     *            the list of assets to create
     *
     * @throws IllegalArgumentException
     *             if userId is not positive, assets is null, or any element is null.
     * @throws EntityNotFoundException
     *             if the corresponding entity is not found.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public void createAssets(long userId, List<Asset> assets) throws ServiceException {
        String signature = CLASS_NAME + ".createAssets(long userId, List<Asset> assets)";
        Log log = getLog();

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"userId", "assets"},
            new Object[] {userId, Helper.toString(assets)});

        try {
            ParameterCheckUtility.checkPositive(userId, "userId");
            ParameterCheckUtility.checkNotNull(assets, "assets");
            ParameterCheckUtility.checkNotNullElements(assets, "assets");
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        }

        for (Asset asset : assets) {
            createAsset(userId, asset);
        }

        // Log Exit
        LoggingWrapperUtility.logExit(log, signature, null);
    }

    /**
     * This method will batch delete the assets.
     *
     * @param userId
     *            the id of the user who performs the action
     * @param assetIds
     *            the list of asset ids to delete.
     *
     * @throws IllegalArgumentException
     *             if userId is not positive, assetIds is null, or any element is null or not positive.
     * @throws EntityNotFoundException
     *             if the corresponding entity is not found.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public void deleteAssets(long userId, List<Long> assetIds) throws ServiceException {
        String signature = CLASS_NAME + ".deleteAssets(long userId, List<Long> assetIds)";
        Log log = getLog();

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"userId", "assetIds"},
            new Object[] {userId, assetIds});

        try {
            ParameterCheckUtility.checkPositive(userId, "userId");
            ParameterCheckUtility.checkNotNull(assetIds, "assetIds");
            for (Long assetId : assetIds) {
                ParameterCheckUtility.checkNotNull(assetId, "assetIds[i]");
                ParameterCheckUtility.checkPositive(assetId, "assetIds[i]");
            }
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        }

        for (long assetId : assetIds) {
            deleteAsset(userId, assetId);
        }

        // Log Exit
        LoggingWrapperUtility.logExit(log, signature, null);
    }

    /**
     * This method will batch update the assets.
     *
     * @param userId
     *            the id of the user who performs the action
     * @param assets
     *            the list of assets to update
     *
     * @throws IllegalArgumentException
     *             if userId is not positive, assets is null, or any element is null, or the id of any element is not
     *             positive.
     * @throws EntityNotFoundException
     *             if the corresponding entity is not found.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public void updateAssets(long userId, List<Asset> assets) throws ServiceException {
        String signature = CLASS_NAME + ".updateAssets(long userId, List<Asset> assets)";
        Log log = getLog();

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"userId", "assets"},
            new Object[] {userId, Helper.toString(assets)});

        try {
            ParameterCheckUtility.checkPositive(userId, "userId");
            ParameterCheckUtility.checkNotNull(assets, "assets");
            for (Asset asset : assets) {
                ParameterCheckUtility.checkNotNull(asset, "assets[i]");
                ParameterCheckUtility.checkPositive(asset.getId(), "assets[i].getId()");
            }
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        }

        for (Asset asset : assets) {
            updateAsset(userId, asset);
        }

        // Log Exit
        LoggingWrapperUtility.logExit(log, signature, null);
    }

    /**
     * Sets the AssetVersionService instance used to access asset version data in persistence.
     *
     * @param assetVersionService
     *            the AssetVersionService instance used to access asset version data in persistence.
     */
    public void setAssetVersionService(AssetVersionService assetVersionService) {
        this.assetVersionService = assetVersionService;
    }

    /**
     * Searches assets.
     *
     * @param entityManager
     *            the entity manager
     * @param criteria
     *            the criteria
     * @param page
     *            the page
     * @param pageSize
     *            the page size
     *
     * @return the result
     *
     * @throws javax.persistence.PersistenceException
     *             if an error occurs while accessing the persistence
     * @throws IllegalStateException
     *             if the entity manager has been closed
     */
    @SuppressWarnings("unchecked")
    private static PagedResult<Asset> searchAssets(EntityManager entityManager,
        AssetSearchCriteria criteria, int page, int pageSize) {
        List<String> paramNames = new ArrayList<String>();
        List<Object> paramValues = new ArrayList<Object>();
        String whereClause = buildWhere(criteria, paramNames, paramValues);

        String orderByClause = buildOrderBy(criteria);

        // Create query
        Query query = entityManager.createQuery(JPQL_QUERY_ASSET + whereClause + orderByClause);

        // Set parameters
        setParameters(query, paramNames, paramValues);

        // Set pagination
        if (page > 0) {
            query.setFirstResult((page - 1) * pageSize);
            query.setMaxResults(pageSize);
        }

        // Execute the query:
        List<Asset> assets = query.getResultList();

        for (Asset asset : assets) {
            // Since we're using lazy fetching, so we should make sure categories and currentVersion are also
            // fetched.
            asset.getCategories().size();
            asset.getCurrentVersion();
        }

        // Build paged result:
        // Create result instance
        PagedResult<Asset> result = new PagedResult<Asset>();

        // Set page, page size, records:
        result.setPage(page);
        result.setPageSize(pageSize);
        result.setRecords(assets);

        // Set total pages
        if (page == 0) {
            if (!assets.isEmpty()) {
                result.setTotalPages(1);
            }
        } else {
            // Create query
            query = entityManager.createQuery(JPQL_QUERY_ASSET_COUNT + whereClause);
            // Set parameters
            setParameters(query, paramNames, paramValues);

            // Execute the query
            int totalRecordsNum = ((Number) query.getSingleResult()).intValue();

            // Set total pages
            result.setTotalPages((totalRecordsNum + pageSize - 1) / pageSize);
        }

        return result;
    }

    /**
     * Sets the parameters.
     *
     * @param query
     *            the query
     * @param paramNames
     *            the parameter names
     * @param paramValues
     *            the parameter values
     */
    private static void setParameters(Query query, List<String> paramNames, List<Object> paramValues) {
        int paramSize = paramNames.size();
        for (int i = 0; i < paramSize; i++) {
            query.setParameter(paramNames.get(i), paramValues.get(i));
        }
    }

    /**
     * Builds the ORDER BY string.
     *
     * @param criteria
     *            the criteria
     *
     * @return the ORDER BY string.
     */
    private static String buildOrderBy(AssetSearchCriteria criteria) {
        StringBuilder sb = new StringBuilder();

        String sortingColumn = criteria.getSortingColumn();
        if (!ASSET_FIELDS.contains(sortingColumn)) {
            sb.append(" ORDER BY e.name");
        } else {
            sb.append(" ORDER BY e.").append(sortingColumn);
        }

        sb.append(" ").append(criteria.getAscending() ? "ASC" : "DESC");

        return sb.toString();
    }

    /**
     * Builds the WHERE string.
     *
     * @param criteria
     *            the criteria
     * @param paramNames
     *            the parameter name
     * @param paramValues
     *            the parameter values
     *
     * @return the WHERE string.
     */
    private static String buildWhere(AssetSearchCriteria criteria,
        List<String> paramNames, List<Object> paramValues) {
        StringBuilder sb = new StringBuilder(" WHERE 1 = 1 ");

        String name = criteria.getName();
        if (name != null) {
            name = "%" + name + "%";
        }
        appendCondition(sb, "e.name LIKE :name", name, "name", paramNames, paramValues);

        appendCondition(sb, "e.currentVersion.version = :version", criteria.getVersion(), "version", paramNames,
            paramValues);
        appendCondition(sb, "e.currentVersion.fileName = :fileName", criteria.getFileName(), "fileName", paramNames,
            paramValues);
        appendCondition(sb, "c.name IN (:categories)", criteria.getCategories(), "categories", paramNames,
            paramValues);
        appendCondition(sb, "e.currentVersion.uploader.name IN (:uploaders)", criteria.getUploaders(), "uploaders",
            paramNames, paramValues);
        appendCondition(sb, "e.containerType = :containerType", criteria.getContainerType(), "containerType",
            paramNames, paramValues);
        appendCondition(sb, "e.containerId = :containerId", criteria.getContainerId(), "containerId", paramNames,
            paramValues);
        appendCondition(sb, "e.currentVersion.uploadTime >= :startTime", criteria.getStartTime(), "startTime",
            paramNames, paramValues);
        appendCondition(sb, "e.currentVersion.uploadTime <= :endTime", criteria.getEndTime(), "endTime", paramNames,
            paramValues);

        return sb.toString();
    }

    /**
     * Appends the condition string.
     *
     * @param sb
     *            the string builder
     * @param str
     *            the condition string.
     * @param value
     *            the value
     * @param name
     *            the name
     * @param paramNames
     *            the parameter name
     * @param paramValues
     *            the parameter values
     */
    private static void appendCondition(StringBuilder sb, String str, Object value, String name,
        List<String> paramNames, List<Object> paramValues) {
        if ((value == null) || ((value instanceof Collection<?>) && ((Collection<?>) value).isEmpty())) {
            return;
        }

        if (sb.length() != 0) {
            sb.append(" AND ");
        }

        sb.append(str);

        paramNames.add(name);
        paramValues.add(value);
    }

    /**
     * Checks the categories.
     *
     * @param log
     *            the log
     * @param signature
     *            the signature
     * @param entityManager
     *            the entity manager
     * @param categories
     *            the categories
     *
     * @throws EntityNotFoundException
     *             if the corresponding entity is not found.
     */
    private static void checkCategories(Log log, String signature, EntityManager entityManager,
        List<Category> categories) throws EntityNotFoundException {
        if (categories != null) {
            for (Category category : categories) {
                if (category != null) {
                    Helper.getEntity(log, signature, "Existing Category", entityManager, Category.class, category
                        .getId());
                }
            }
        }
    }
}
