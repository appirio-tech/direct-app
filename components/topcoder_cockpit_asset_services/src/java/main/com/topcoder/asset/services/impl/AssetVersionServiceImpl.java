/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.services.impl;

import com.topcoder.asset.entities.Asset;
import com.topcoder.asset.entities.AssetVersion;
import com.topcoder.asset.exceptions.AssetConfigurationException;
import com.topcoder.asset.exceptions.EntityNotFoundException;
import com.topcoder.asset.exceptions.PersistenceException;
import com.topcoder.asset.exceptions.ServiceException;
import com.topcoder.asset.services.AssetVersionService;
import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.util.log.Log;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <p>
 * This class is an implementation of AssetVersionService that uses injected JPA EntityManager interface for accessing
 * AssetVersion data in database. This class uses Logging Wrapper logger to perform logging. It's assumed that this
 * class is initialized with Spring setter dependency injection only.
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
 * // Retrieve AssetVersionService from the Spring application context
 * AssetVersionService assetVersionService = (AssetVersionService) context.getBean(&quot;assetVersionService&quot;);
 *
 * // Prepare user id
 * Long userId = 12L;
 *
 * // Prepare asset version to create
 * AssetVersion assetVersion = new AssetVersion();
 * assetVersion.setVersion(&quot;Version 1.0&quot;);
 * assetVersion.setAssetId(1);
 * assetVersion.setFileName(&quot;erd.png&quot;);
 * assetVersion.setFileType(&quot;png&quot;);
 * assetVersion.setFileSizeBytes(57527);
 *
 * User uploader = new User();
 * uploader.setId(1);
 * assetVersion.setUploader(uploader);
 * assetVersion.setUploadTime(new Date());
 * assetVersion.setDescription(&quot;The description.&quot;);
 * assetVersion.setFilePath(&quot;filePath&quot;);
 * assetVersion.setPreviewImagePath(&quot;previewImagePath&quot;);
 *
 * // Prepare File
 * File file = new File(TEST_FILES + &quot;erd.png&quot;);
 *
 * // Create asset version
 * assetVersionService.createAssetVersion(userId, assetVersion, file, true);
 * // The asset version will be persisted in database. The file will be saved to configured location, and preview
 * // image will also be generated.
 *
 * // Prepare asset version to update
 * assetVersion.setVersion(&quot;Version 1.1&quot;);
 *
 * // Update asset version
 * assetVersionService.updateAssetVersion(userId, assetVersion);
 * // The asset version will be updated in database. The related file will be move to new file location, and
 * // preview image will also be moved.
 *
 * long assetVersionId = assetVersion.getId();
 *
 * // Get asset version
 * assetVersion = assetVersionService.getAssetVersion(assetVersionId);
 * // The asset version will be retrieved from database
 *
 * // Get asset versions of asset
 * List&lt;AssetVersion&gt; assetVersions = assetVersionService.getAssetVersionsOfAsset(1);
 * // The asset versions of the specific asset will be retrieved from database
 *
 * // Prepare asset versions to update
 * assetVersions = new ArrayList&lt;AssetVersion&gt;();
 *
 * assetVersion.setVersion(&quot;Version 1.2&quot;);
 * assetVersions.add(assetVersion);
 *
 * // Batch update asset versions
 * assetVersionService.updateAssetVersions(userId, assetVersions);
 *
 * // Prepare asset version ids to retrieve
 * List&lt;Long&gt; assetVersionIds = new ArrayList&lt;Long&gt;();
 * assetVersionIds.add(assetVersionId);
 *
 * // Prepare output stream
 * OutputStream outputStream = new ByteArrayOutputStream();
 * // Batch get asset version contents
 * assetVersionService.batchGetAssetVersionContents(assetVersionIds, outputStream);
 * // The file contents of these asset version will be zipped and written to output stream.
 *
 * // Delete asset version
 * assetVersionService.deleteAssetVersion(userId, assetVersionId);
 * // The asset version will be deleted in database. The related files will also be deleted.
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
 * Version 1.1 (Release Assembly - TopCoder Cockpit Asset View And Basic Upload)
 * - Sets the file type to the extension name.
 * </p>
 *
 * <p>
 * Version 1.2 (Release Assembly - TopCoder Cockpit Asset View And File Version)
 * <ul>
 *     <li>Updates {@link #deleteAssetVersion(long, long)} to set current version of asset to previous one
 *     if current version is removed</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.3 (Release Assembly - TopCoder Cockpit Asset View Release 4 - Bug Fixes)
 * <ul>
 *     <li>Updated method {@link #batchGetAssetVersionContents(java.util.List, java.io.OutputStream)}
 *     to handle the case there are different assets with the same file name. We append number like
 *     FILE_NAME(2), FILE_NAME(3) to the duplicated files name.</li>
 * </ul>
 * </p>
 *
 * @author LOY, sparemax, GreatKevin, TCSASSEMBLER
 * @version 1.3
 */
public class AssetVersionServiceImpl extends BaseAssetService implements AssetVersionService {
    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = AssetVersionServiceImpl.class.getName();

    /**
     * The JPQL to query the asset version.
     */
    private static final String JPQL_QUERY_ASSET_VERSION = "SELECT e FROM AssetVersion e WHERE e.assetId = :assetId";

    /**
     * The JPQL to query the file path.
     */
    private static final String JPQL_QUERY_FILE_PATH = "SELECT filePath FROM AssetVersion e"
        + " WHERE e.id IN (:assetVersionIds)";

    /**
     * The buffer size.
     */
    private static final int BUFFER_SIZE = 1024;

    /**
     * The base path for asset files and preview images. Is initialized with Spring setter dependency injection.
     * Cannot be null/empty after initialization, assuming that property is initialized via Spring setter-based
     * dependency injection and is never changed after that (note that check is performed in checkInit() method
     * instead of the setter). Has a setter.
     */
    private String basePath;

    /**
     * The file types that should generate preview images. Is initialized with Spring setter dependency injection.
     * Cannot be null, or contains any null/empty element after initialization, assuming that property is initialized
     * via Spring setter-based dependency injection and is never changed after that (note that check is performed in
     * checkInit() method instead of the setter). Has a setter.
     */
    private List<String> imageTypes;

    /**
     * The width of preview image. Is initialized with Spring setter dependency injection. Should be positive after
     * initialization, assuming that property is initialized via Spring setter-based dependency injection and is never
     * changed after that (note that check is performed in checkInit() method instead of the setter). Has a setter.
     */
    private int previewImageWidth;

    /**
     * The height of preview image. Is initialized with Spring setter dependency injection. Should be positive after
     * initialization, assuming that property is initialized via Spring setter-based dependency injection and is never
     * changed after that (note that check is performed in checkInit() method instead of the setter). Has a setter.
     */
    private int previewImageHeight;

    /**
     * Creates an instance of AssetVersionServiceImpl.
     */
    public AssetVersionServiceImpl() {
        // Empty
    }

    /**
     * Checks whether this class was initialized by Spring properly.
     *
     * @throws AssetConfigurationException
     *             if the class was not initialized properly (entityManager is null; basePath is null/empty;
     *             imageTypes is null, or contains null/empty element; previewImageWidth or previewImageHeight is not
     *             positive)
     */
    @PostConstruct
    protected void checkInit() {
        super.checkInit();

        ValidationUtility.checkNotNullNorEmptyAfterTrimming(basePath, "basePath", AssetConfigurationException.class);

        ValidationUtility.checkNotNull(imageTypes, "imageTypes", AssetConfigurationException.class);
        for (String imageType : imageTypes) {
            ValidationUtility.checkNotNullNorEmptyAfterTrimming(imageType, "imageTypes[i]",
                AssetConfigurationException.class);
        }

        ValidationUtility.checkPositive(previewImageWidth, "previewImageWidth", AssetConfigurationException.class);
        ValidationUtility.checkPositive(previewImageHeight, "previewImageHeight", AssetConfigurationException.class);
    }

    /**
     * This method will create the asset version. If the assetVersion.fileType is one of configured previewFileTypes,
     * then generate preview image for the file content, and it should be set back to the given entity.
     *
     * @param userId
     *            the id of the user who performs the action
     * @param assetVersion
     *            the asset version to create
     * @param file
     *            the asset version file to be added, if it is null, then the service won't handle file persistence,
     *            it is assumed that file persistence is handled by caller. If it is not null, then the file content
     *            will be copied to configured location. Assigned location should be set back to the given entity.
     * @param currentVersion
     *            the flag specified whether the new asset version is the current version of the asset.
     *
     * @throws IllegalArgumentException
     *             if userId is not positive, assetVersion is null, or assetVersion.assetId is not positive.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public void createAssetVersion(long userId, AssetVersion assetVersion, File file, boolean currentVersion)
        throws ServiceException {
        String signature = CLASS_NAME
            + ".createAssetVersion(long userId, AssetVersion assetVersion, File file, boolean currentVersion)";
        Log log = getLog();

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"userId", "assetVersion", "file", "currentVersion"},
            new Object[] {userId, Helper.toString(assetVersion), file, currentVersion});

        try {
            ParameterCheckUtility.checkPositive(userId, "userId");
            ParameterCheckUtility.checkNotNull(assetVersion, "assetVersion");

            long assetId = assetVersion.getAssetId();
            ParameterCheckUtility.checkPositive(assetId, "assetVersion.getAssetId()");

            EntityManager entityManager = getEntityManager();

            Asset asset = null;

            if (file != null) {
                asset = Helper.getEntity(log, signature, "Existing Asset", entityManager, Asset.class, assetId);

                // Handle file persistence:
                handleFile(log, signature, assetVersion, asset, file);
            }

            // Persist assetVersion:
            entityManager.persist(assetVersion);
            entityManager.flush();

            if (currentVersion) {
                if (asset == null) {
                    asset = Helper.getEntity(log, signature, "Existing Asset", entityManager, Asset.class, assetId);
                }

                // Update current_version_id in Asset table
                asset.setCurrentVersion(assetVersion);
                entityManager.merge(asset);
            }

            Helper.performAudit(entityManager, userId, "Create Asset Version", "AssetVersion", assetVersion.getId(),
                null, Helper.toString(assetVersion));

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
        } catch (SecurityException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ServiceException(
                "A security error has occurred.", e));
        } catch (IOException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new PersistenceException(
                "An I/O error has occurred.", e));
        }
    }

    /**
     * This method will update the asset version. If file path is updated, the service will move the file to new
     * location before updating the entity.
     *
     * @param userId
     *            the id of the user who performs the action
     * @param assetVersion
     *            the asset version to update.
     *
     * @throws IllegalArgumentException
     *             if userId is not positive, assetVersion is null, assetVersion.id or assetVersion.assetId is not
     *             positive, or assetVersion.filePath is null/empty.
     * @throws EntityNotFoundException
     *             if the corresponding entity is not found.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public void updateAssetVersion(long userId, AssetVersion assetVersion) throws ServiceException {
        String signature = CLASS_NAME + ".updateAssetVersion(long userId, AssetVersion assetVersion)";
        Log log = getLog();

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"userId", "assetVersion"},
            new Object[] {userId, Helper.toString(assetVersion)});

        try {
            ParameterCheckUtility.checkPositive(userId, "userId");
            ParameterCheckUtility.checkNotNull(assetVersion, "assetVersion");

            long assetVersionId = assetVersion.getId();
            ParameterCheckUtility.checkPositive(assetVersionId, "assetVersion.getId()");

            ParameterCheckUtility.checkPositive(assetVersion.getAssetId(), "assetVersion.getAssetId()");

            String filePath = assetVersion.getFilePath();
            ParameterCheckUtility.checkNotNullNorEmptyAfterTrimming(filePath, "assetVersion.getFilePath()");

            EntityManager entityManager = getEntityManager();

            // Check if assetVersion with the given ID exists:
            AssetVersion existingAssetVersion = Helper.getEntity(log, signature, "Existing AssetVersion",
                entityManager, AssetVersion.class, assetVersionId);

            String existingFilePath = existingAssetVersion.getFilePath();
            // Handle file persistence:
            copyFile(log, signature, existingFilePath, filePath, true);

            // Get file extension
            String ext = getFileExtension(new File(existingFilePath).getName());

            if (contain(imageTypes, ext)) {
                if (isNullOrEmpty(assetVersion.getPreviewImagePath())) {
                    Asset asset = Helper.getEntity(log, signature, "Existing Asset", entityManager, Asset.class,
                        assetVersion.getAssetId());

                    assetVersion.setPreviewImagePath(getFilePath(asset, assetVersion, true));
                }

                if (isNullOrEmpty(existingAssetVersion.getPreviewImagePath())) {
                    generatePreviewImage(log, signature, filePath, assetVersion.getPreviewImagePath(), ext);
                } else {
                    copyFile(log, signature, existingAssetVersion.getPreviewImagePath(), assetVersion
                        .getPreviewImagePath(), true);
                }
            }

            // Update assetVersion:
            entityManager.merge(assetVersion);

            Helper.performAudit(entityManager, userId, "Update Asset Version", "AssetVersion", assetVersion.getId(),
                Helper.toString(log, signature, existingAssetVersion), Helper.toString(log, signature, assetVersion));

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
        } catch (SecurityException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ServiceException(
                "A security error has occurred.", e));
        } catch (IOException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new PersistenceException(
                "An I/O error has occurred.", e));
        }
    }

    /**
     * This method will delete the asset version. The related files will also be deleted. If it is current version of
     * the asset, then the current version of asset should be set to null.
     *
     * @param userId
     *            the id of the user who performs the action
     * @param assetVersionId
     *            the id of the asset version to delete.
     *
     * @throws IllegalArgumentException
     *             if userId or assetVersionId is not positive.
     * @throws EntityNotFoundException
     *             if the corresponding entity is not found.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public void deleteAssetVersion(long userId, long assetVersionId) throws ServiceException {
        String signature = CLASS_NAME + ".deleteAssetVersion(long userId, long assetVersionId)";
        Log log = getLog();

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"userId", "assetVersionId"},
            new Object[] {userId, assetVersionId});

        try {
            ParameterCheckUtility.checkPositive(userId, "userId");
            ParameterCheckUtility.checkPositive(assetVersionId, "assetVersionId");

            EntityManager entityManager = getEntityManager();

            // Check if assetVersion with the given ID exists:
            AssetVersion existingAssetVersion = Helper.getEntity(log, signature, "Existing AssetVersion",
                entityManager, AssetVersion.class, assetVersionId);

            // Handle file persistence:

            if (!isNullOrEmpty(existingAssetVersion.getFilePath())) {
                new File(existingAssetVersion.getFilePath()).delete();
            }

            if (!isNullOrEmpty(existingAssetVersion.getPreviewImagePath())) {
                new File(existingAssetVersion.getPreviewImagePath()).delete();
            }

            // If it is current version of the asset,
            // then the current version of asset should be set to the second highest version.
            Asset asset = entityManager.find(Asset.class, existingAssetVersion.getAssetId());

            AssetVersion currentVersion = asset.getCurrentVersion();
            if ((currentVersion != null) && (currentVersion.getId() == assetVersionId)) {

                List<AssetVersion> assetVersionsOfAsset = getAssetVersionsOfAsset(asset.getId());

                TreeMap<Double, AssetVersion> assetVersionsMap = new TreeMap<Double, AssetVersion>();

                for(AssetVersion av : assetVersionsOfAsset) {
                    if(av.getId() != currentVersion.getId()) {
                        assetVersionsMap.put(Double.parseDouble(av.getVersion()), av);
                    }
                }

                if(assetVersionsMap.size() > 0) {
                    asset.setCurrentVersion(assetVersionsMap.get(assetVersionsMap.lastKey()));
                } else {
                    asset.setCurrentVersion(null);
                }

                entityManager.merge(asset);
            }

            // Delete assetVersion:
            entityManager.remove(existingAssetVersion);

            Helper.performAudit(entityManager, userId, "Delete Asset Version", "AssetVersion", assetVersionId,
                Helper.toString(log, signature, existingAssetVersion), null);

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
        } catch (SecurityException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ServiceException(
                "A security error has occurred.", e));
        }
    }

    /**
     * This method will retrieve the asset version with the given id.
     *
     * @param assetVersionId
     *            the id of the asset version.
     *
     * @return the asset version with the given id.
     *
     * @throws IllegalArgumentException
     *             if assetVersionId is not positive.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public AssetVersion getAssetVersion(long assetVersionId) throws ServiceException {
        String signature = CLASS_NAME + ".getAssetVersion(long assetVersionId)";
        Log log = getLog();

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"assetVersionId"},
            new Object[] {assetVersionId});

        try {
            ParameterCheckUtility.checkPositive(assetVersionId, "assetVersionId");

            EntityManager entityManager = getEntityManager();

            AssetVersion assetVersion = entityManager.find(AssetVersion.class, assetVersionId);

            // Log Exit
            LoggingWrapperUtility.logExit(log, signature, new Object[] {Helper.toString(assetVersion)});
            return assetVersion;
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
     * This method will retrieve the asset versions of a specific asset.
     *
     * @param assetId
     *            the id of the asset.
     *
     * @return the asset versions of a specific asset.
     *
     * @throws IllegalArgumentException
     *             if assetId is not positive.
     * @throws EntityNotFoundException
     *             if the corresponding entity is not found.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    @SuppressWarnings("unchecked")
    public List<AssetVersion> getAssetVersionsOfAsset(long assetId) throws ServiceException {
        String signature = CLASS_NAME + ".getAssetVersionsOfAsset(long assetId)";
        Log log = getLog();

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"assetId"},
            new Object[] {assetId});

        try {
            ParameterCheckUtility.checkPositive(assetId, "assetId");

            EntityManager entityManager = getEntityManager();

            Helper.getEntity(log, signature, "Existing Asset", entityManager, Asset.class, assetId);

            // Retrieve the asset versions:
            List<AssetVersion> assetVersions = entityManager.createQuery(JPQL_QUERY_ASSET_VERSION)
                .setParameter("assetId", assetId)
                .getResultList();

            // Log Exit
            LoggingWrapperUtility.logExit(log, signature, new Object[] {Helper.toString(assetVersions)});
            return assetVersions;
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
     * This method will batch update the asset versions.
     *
     * @param userId
     *            the id of the user who performs the action
     * @param assetVersions
     *            the asset versions to update.
     *
     * @throws IllegalArgumentException
     *             if userId is not positive, assetVersions is null, or any assetVersion is null, or any
     *             assetVersion.id/assetVersion.assetId is not positive, or any assetVersion.filePath is null/empty.
     *             (assetVersion is the element in assetVersions)
     * @throws EntityNotFoundException
     *             if the corresponding entity is not found.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public void updateAssetVersions(long userId, List<AssetVersion> assetVersions) throws ServiceException {
        String signature = CLASS_NAME + ".updateAssetVersions(long userId, List<AssetVersion> assetVersions)";
        Log log = getLog();

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"userId", "assetVersions"},
            new Object[] {userId, Helper.toString(assetVersions)});

        try {
            ParameterCheckUtility.checkPositive(userId, "userId");
            ParameterCheckUtility.checkNotNull(assetVersions, "assetVersions");
            for (AssetVersion assetVersion : assetVersions) {
                ParameterCheckUtility.checkNotNull(assetVersion, "assetVersions[i]");
                ParameterCheckUtility.checkPositive(assetVersion.getId(), "assetVersions[i].getId()");
                ParameterCheckUtility.checkPositive(assetVersion.getAssetId(), "assetVersions[i].getAssetId()");
                ParameterCheckUtility.checkNotNullNorEmptyAfterTrimming(assetVersion.getFilePath(),
                    "assetVersions[i].getFilePath()");
            }
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        }

        for (AssetVersion assetVersion : assetVersions) {
            // Delegate to updateAssetVersion(long, AssetVersion)
            updateAssetVersion(userId, assetVersion);
        }

        // Log Exit
        LoggingWrapperUtility.logExit(log, signature, null);
    }

    /**
     * This method will batch retrieve asset version contents. The asset version files contents are archived as a ZIP
     * file and written to the output stream.
     *
     * @param assetVersionIds
     *            the asset version ids
     * @param output
     *            the output stream of asset version contents
     *
     * @throws IllegalArgumentException
     *             if assetVersionIds is null, or any element in assetVersionIds is null, or output is null
     * @throws EntityNotFoundException
     *             if the corresponding entity is not found.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    @SuppressWarnings("unchecked")
    public void batchGetAssetVersionContents(List<Long> assetVersionIds, OutputStream output) throws ServiceException {
        String signature = CLASS_NAME
            + ".batchGetAssetVersionContents(List<Long> assetVersionIds, OutputStream output)";
        Log log = getLog();

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"assetVersionIds", "output"},
            new Object[] {assetVersionIds, output});

        try {
            ParameterCheckUtility.checkNotNull(assetVersionIds, "assetVersionIds");
            for (Long assetVersionId : assetVersionIds) {
                ParameterCheckUtility.checkNotNull(assetVersionId, "assetVersionIds[i]");
                ParameterCheckUtility.checkPositive(assetVersionId, "assetVersionIds[i]");
            }
            ParameterCheckUtility.checkNotNull(output, "output");

            EntityManager entityManager = getEntityManager();

            // Retrieve the asset version file paths:
            List<String> filePaths = entityManager.createQuery(JPQL_QUERY_FILE_PATH)
                .setParameter("assetVersionIds", assetVersionIds)
                .getResultList();

            if (filePaths.size() != assetVersionIds.size()) {
                // Log exception
                throw LoggingWrapperUtility.logException(log, signature, new EntityNotFoundException(
                    "Some asset version doesn't exist."));
            }

            // The asset version files contents are archived as a ZIP file and written to the output stream:
            // first, we create ZipOutputStream wrapping the given output stream
            ZipOutputStream outputStream = new ZipOutputStream(output);

            Map<String, Integer> namesAdded = new HashMap<String, Integer>();

            try {
                for (String filePath : filePaths) {
                    // create zip entry
                    File zipFile = new File(filePath);

                    String zipFileName = zipFile.getName();

                    // check if the duplicate name exists
                    if(namesAdded.containsKey(zipFileName)) {
                        String originalName = zipFileName;

                        // rename zip file
                        int duplicatedFileNameCount = namesAdded.get(originalName);
                        duplicatedFileNameCount++;
                        zipFileName = renameDuplicatedFileName(originalName, duplicatedFileNameCount);

                        while(namesAdded.containsKey(zipFileName)) {
                            duplicatedFileNameCount++;

                            if(duplicatedFileNameCount >= 10000) {
                                // to avoid extreme case - would not happen in real case
                                break;
                            }

                            zipFileName = renameDuplicatedFileName(originalName, duplicatedFileNameCount);
                        }

                        namesAdded.put(zipFileName, 1);
                        namesAdded.put(originalName, duplicatedFileNameCount);


                    } else {
                        namesAdded.put(zipFileName, 1);
                    }


                    outputStream.putNextEntry(new ZipEntry(zipFileName));

                    // Write entry content
                    byte[] buf = new byte[BUFFER_SIZE];
                    int num;

                    InputStream inputStream = new BufferedInputStream(new FileInputStream(new File(filePath)));
                    try {
                        while ((num = inputStream.read(buf)) != -1) {
                            outputStream.write(buf, 0, num);
                        }
                    } finally {
                        // Close the input stream
                        closeObj(log, signature, inputStream);
                    }
                }
            } finally {
                // Close the output stream
                // (The error can't be ignored)
                outputStream.close();
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
            throw LoggingWrapperUtility.logException(log, signature, new ServiceException(
                "The entity manager has been closed.", e));
        } catch (SecurityException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ServiceException(
                "A security error has occurred.", e));
        } catch (IOException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new PersistenceException(
                "An I/O error has occurred.", e));
        }
    }

    /**
     * Sets the base path for asset files and preview images.
     *
     * @param basePath
     *            the base path for asset files and preview images.
     */
    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    /**
     * Sets the file types that should generate preview images.
     *
     * @param imageTypes
     *            the file types that should generate preview images.
     */
    public void setImageTypes(List<String> imageTypes) {
        this.imageTypes = imageTypes;
    }

    /**
     * Sets the width of preview image.
     *
     * @param previewImageWidth
     *            the width of preview image.
     */
    public void setPreviewImageWidth(int previewImageWidth) {
        this.previewImageWidth = previewImageWidth;
    }

    /**
     * Sets the height of preview image.
     *
     * @param previewImageHeight
     *            the height of preview image.
     */
    public void setPreviewImageHeight(int previewImageHeight) {
        this.previewImageHeight = previewImageHeight;
    }

    /**
     * Handles the file.
     *
     * @param log
     *            the log
     * @param signature
     *            the signature
     * @param assetVersion
     *            the asset version
     * @param asset
     *            the asset
     * @param file
     *            the file
     *
     * @throws IOException
     *             if some error occurred while perform file persistence.
     * @throws SecurityException
     *             if a security error occurs
     */
    private void handleFile(Log log, String signature, AssetVersion assetVersion, Asset asset, File file)
        throws IOException {
        // Get original file path
        String originalFilePath = file.getPath();

        // Get destination file path
        String destinationFilePath = getFilePath(asset, assetVersion, false);

        // Copy file to the configured location
        copyFile(log, signature, originalFilePath, destinationFilePath, false);

        // Set back to assetVersion
        assetVersion.setFilePath(destinationFilePath);
        assetVersion.setFileSizeBytes(file.length());

        // Get file extension
        String ext = getFileExtension(file.getName());

        assetVersion.setFileType(ext);

        if (contain(imageTypes, ext)) {
            // Generate preview image
            String previewImageFilePath = getFilePath(asset, assetVersion, true);
            generatePreviewImage(log, signature, destinationFilePath, previewImageFilePath, ext);

            // Set back to assetVersion
            assetVersion.setPreviewImagePath(previewImageFilePath);
        }
    }

    /**
     * This method will copy the file from original file path to destination file path.
     *
     * @param log
     *            the log
     * @param signature
     *            the signature
     * @param originalFilePath
     *            the original file path
     * @param destinationFilePath
     *            the destination file path
     * @param deleteOriginalFile
     *            the flag indicates whether the original file should be deleted
     *
     * @throws IOException
     *             if some error occurred while perform file persistence.
     * @throws SecurityException
     *             if a security error occurs
     */
    private static void copyFile(Log log, String signature, String originalFilePath, String destinationFilePath,
        boolean deleteOriginalFile) throws IOException {
        if (originalFilePath.equalsIgnoreCase(destinationFilePath)) {
            // No need to copy
            return;
        }

        // Copy file:
        InputStream inputStream = new FileInputStream(originalFilePath);
        try {
            OutputStream outputStream = new FileOutputStream(destinationFilePath);
            try {
                byte[] buff = new byte[BUFFER_SIZE];
                int num;

                while ((num = inputStream.read(buff)) != -1) {
                    outputStream.write(buff, 0, num);
                }
            } finally {
                // Close the output stream
                // (The error can't be ignored)
                outputStream.close();
            }
        } finally {
            // Close the input stream
            closeObj(log, signature, inputStream);
        }

        // Delete file if deleteOriginalFile is true:
        if (deleteOriginalFile) {
            File file = new File(originalFilePath);
            file.delete();
        }
    }

    /**
     * This method will build the file path for asset file or preview image.
     *
     * @param assetVersion
     *            the asset version
     * @param asset
     *            the asset
     * @param isPreviewImage
     *            the flag indicates whether the file path is for preview image
     *
     * @return the file path for asset file or preview image.
     *
     * @throws SecurityException
     *             if a security error occurs
     */
    private String getFilePath(Asset asset, AssetVersion assetVersion, boolean isPreviewImage) {
        StringBuilder sb = new StringBuilder();
        sb.append(basePath).append(File.separator).append(asset.getContainerType()).append(File.separator)
            .append(asset.getContainerId()).append(File.separator).append(asset.getId()).append(File.separator)
            .append(assetVersion.getVersion()).append(File.separator);

        File dir = new File(sb.toString());
        dir.mkdirs();

        if (isPreviewImage) {
            sb.append("preview_");
        }
        sb.append(assetVersion.getFileName());

        return sb.toString();
    }

    /**
     * This method will generate preview image. The format of generated preview image will be the same with original
     * image.
     *
     * @param log
     *            the log
     * @param signature
     *            the signature
     * @param originalImageFilePath
     *            the path of original image file
     * @param previewImageFilePath
     *            the path of generated preview image file
     * @param imageFormat
     *            the format of original image
     *
     * @throws IOException
     *             if some error occurred while perform file persistence.
     */
    private void generatePreviewImage(Log log, String signature, String originalImageFilePath,
        String previewImageFilePath, String imageFormat) throws IOException {
        // Get original image
        BufferedImage originalImage = ImageIO.read(new File(originalImageFilePath));

        // Get image type
        int type = originalImage.getType();
        if (type == 0) {
            type = BufferedImage.TYPE_INT_ARGB;
        }

        // Draw preview image
        BufferedImage previewImage = new BufferedImage(previewImageWidth, previewImageHeight, type);
        Graphics2D graphics2D = previewImage.createGraphics();
        try {
            graphics2D.drawImage(originalImage, 0, 0, previewImageWidth, previewImageHeight, null);
        } finally {
            graphics2D.dispose();
        }

        // Write preview image to file
        ImageIO.write(previewImage, imageFormat, new File(previewImageFilePath));
    }

    /**
     * This method will get the file extension of the given file name.
     *
     * @param fileName
     *            the given file name
     *
     * @return the file extension of the given file name
     */
    private static String getFileExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        if ((index > 0) && (index < (fileName.length() - 1))) {
            return fileName.substring(index + 1).toLowerCase();
        }
        return null;
    }

    /**
     * Checks if the list contains the given value (case insensitive).
     *
     * @param list
     *            the list
     * @param value
     *            the value
     *
     * @return true if the list contains the given value (case insensitive); false otherwise.
     */
    private static boolean contain(List<String> list, String value) {
        for (String element : list) {
            if (element.equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Closes the object.
     *
     * @param log
     *            the log
     * @param signature
     *            the signature
     * @param obj
     *            the object
     */
    private static void closeObj(Log log, String signature, Closeable obj) {
        try {
            obj.close();
        } catch (IOException e) {
            // Log exception
            LoggingWrapperUtility.logException(log, signature, e);
        }
    }

    /**
     * <p>
     * Validates the value of a string.
     * </p>
     *
     * @param value
     *            the value of the variable to be validated.
     *
     * @return <code>true</code> if value is <code>null</code> or empty
     */
    private static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }

    /**
     * <p>
     * Helper method to generate a new name for a file when there is already a same name in the zip archive.
     * </p>
     *
     * @param fileName the original file name.
     * @param duplicationCount the duplication count
     * @return the generated new  file name.
     */
    private static String renameDuplicatedFileName(String fileName, int duplicationCount) {
        String extension = "";
        String basename = fileName;

        int i = fileName.lastIndexOf('.');

        if (i > 0) {
            extension = fileName.substring(i+1);
            basename = fileName.substring(0, i);
        }

        return basename + "(" + duplicationCount + ")" + (extension.length() > 0 ? ("." + extension) : extension);
    }
}
